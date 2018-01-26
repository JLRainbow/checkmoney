package com.citic.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.annotation.SystemLog;
import com.citic.bean.po.Operationstatus;
import com.citic.bean.po.Wages;
import com.citic.bean.pojo.TellerPojo;
import com.citic.bean.pojo.WagesPojo;
import com.citic.bean.vo.IdNoWalletStatus;
import com.citic.bean.vo.WagesAccountValidationVo;
import com.citic.bean.vo.WagesQueryVo;
import com.citic.bean.vo.WalletAbutmentTransVo;
import com.citic.bean.vo.WalletAbutmentVo;
import com.citic.constants.OperationstatusEnum;
import com.citic.constants.WagesStatusEnum;
import com.citic.entity.WagesFormMap;
import com.citic.exception.SystemException;
import com.citic.mapper.OperationstatusMapper;
import com.citic.mapper.WagesMapper;
import com.citic.util.CalendarUtil;
import com.citic.util.HttpClientUtil;
import com.citic.util.JSONUtil;
import com.citic.util.StringUtil;

@Service
public class WagesService {
	@Autowired
	private WagesMapper wagesMapper;
	@Autowired
	private OperationstatusMapper operationstatusMapper;

	@Value("${employeename}")
	private String employeename;
	@Value("${dept}")
	private String dept;
	@Value("${thirdcustid}")
	private String thirdcustid;
	@Value("${idno}")
	private String idno;
	@Value("${tranamount}")
	private String tranamount;

	@Value("${validateURL}")
	private String validateURL;
	@Value("${walletabutmentURL}")
	private String walletabutmentURL;

	// 保存工资信息
	@Transactional(readOnly = false) // 需要事务操作必须加入此注解
	@SystemLog(module = "工资管理", methods = "工资管理-初始化工资明细") // 凡需要处理业务逻辑的.都需要记录操作日志
	public void saveWages(String generateBatchCode, List<Map<String, String>> excelContentMap) throws Exception {

		WagesAccountValidationVo validationVo = new WagesAccountValidationVo();
		Set<IdNoWalletStatus> idNoWalletStatusSet = new HashSet<IdNoWalletStatus>();
		Set<String> idNoValidSet = new HashSet<String>();
		
		/**
		 * 1.首先检验excel中的基本信息
		 */
		// 如果导入的Excel中的记录存在相同身份证的记录，则抛出异常
		IdNoWalletStatus idNoWalletStatus = null;
		for (Map<String, String> map : excelContentMap) {
			String idNoStr = map.get(this.idno);
			if (idNoValidSet.contains(idNoStr)) {
				throw new SystemException("导入的员工工资明细Excel可能存在重复的身份证记录！");
			} else {
				idNoValidSet.add(idNoStr);
				idNoWalletStatus = new IdNoWalletStatus();
				idNoWalletStatus.setIdno(idNoStr);
				idNoWalletStatus.setThirdcustid(Byte.parseByte(map.get(this.thirdcustid)));
				idNoWalletStatus.setStatus((byte)-1);
				idNoWalletStatusSet.add(idNoWalletStatus);
			}
		}

		//将所有Excel中校验通过的数据转换为json，发送至钱包进行开户校验
		validationVo.setCount(idNoWalletStatusSet.size());
		validationVo.setData(idNoWalletStatusSet);
		validationVo.setBatchcode(generateBatchCode);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("json", JSONUtil.getInstance().obj2Json(validationVo));
		String charSet = "UTF-8";
		// 从国安钱包返回的校验完成的所有数据
//		String validatedJson = HttpClientUtil.executePost(validateURL, paramsMap, charSet);

		String validatedJson = "{\"count\":2,\"batchcode\":\"201704-1492075782048\",\"data\":[{\"thirdcustid\":1,\"idno\":\"123456789888888\",\"status\":0},{\"thirdcustid\":1,\"idno\":\"123456789888881\",\"status\":-1}]}";
		if (!StringUtil.isEmpty(validatedJson)) {
			WagesAccountValidationVo validatedJsonOfNotOpenAccount = JSONUtil.getInstance().json2Obj(validatedJson,
					WagesAccountValidationVo.class);

			//将钱包校验过的数据解析，同发送的数据对比，任何不匹配，抛出异常
			int count = validatedJsonOfNotOpenAccount.getCount();
			Set<IdNoWalletStatus> validatedIdNoSet = validatedJsonOfNotOpenAccount.getData();
			String batchcode = validatedJsonOfNotOpenAccount.getBatchcode();

			if (validationVo.getCount() != count /*|| batchcode != generateBatchCode*/ || validatedIdNoSet == null  || validatedIdNoSet.size() != count) {
				throw new SystemException("传输回的Json校验数据记录同原始数据不匹配！");
			}
			//传回的校验状态不为空，则循环校验过的数据，进行状态匹配
			if (validatedIdNoSet != null) {
				StringBuffer sb = new StringBuffer();
				// 全部校验通过后，将所有数据按照传回的状态，进行持久化。
				int lineNo = 1;
				for (Map<String, String> map : excelContentMap) {
					String employeename = map.get(this.employeename);
					if (employeename == null || StringUtil.isBlank(employeename)) {
						sb.append("员工姓名为空！可能在第:" + lineNo + "行附近！");
					}
					String idno = map.get(this.idno);
					if (idno == null || StringUtil.isBlank(idno)) {
						sb.append("身份证号为空！可能在第:" + lineNo + "行附近！");
					}
					String tranamount = map.get(this.tranamount);
					if (tranamount == null || StringUtil.isBlank(tranamount)) {
						sb.append("实发合计为空！可能在第:" + lineNo + "行附近！");
					}
					String dept = map.get(this.dept);
					if (dept == null || StringUtil.isBlank(dept)) {
						sb.append("部门为空！可能在第:" + lineNo + "行附近！");
					}
					lineNo++;
				}

				if (sb.length() > 0) {
					throw new SystemException(sb.toString());
				}

				
				Map<String, Byte> statusMap = new HashMap<String, Byte>();
				for (IdNoWalletStatus idNoStatus : validatedIdNoSet) {
					statusMap.put(idNoStatus.getIdno(), idNoStatus.getStatus());
				}
				
					for (Map<String, String> map : excelContentMap) {
						String employeename = map.get(this.employeename);
						String idno = map.get(this.idno);
						String thirdcustid = map.get(this.thirdcustid);
						String tranamount = map.get(this.tranamount);
						String dept = map.get(this.dept);

						WagesFormMap wagesFormMap = new WagesFormMap();
						wagesFormMap.put("thirdcustid", thirdcustid);
						wagesFormMap.put("batchcode", generateBatchCode);
						wagesFormMap.put("idno", idno);
						wagesFormMap.put("employeename", employeename);
						wagesFormMap.put("dept", dept);
						wagesFormMap.put("tranamount", tranamount);
						wagesFormMap.put("status", statusMap.get(idno));

						wagesMapper.addEntity(wagesFormMap);
					}
			}
		} else {
			throw new SystemException("传输回的Json校验数据为空！");
		}
	}

	// 未开户账户再校验
	public String revalidate(String generateBatchCode) throws Exception {
		Operationstatus operstatusVo = new Operationstatus();
		Session session = SecurityUtils.getSubject().getSession();
		String accountName = (String)session.getAttribute("accountname");
		
		operstatusVo.setAccountname(accountName);
		operstatusVo.setBatchcode(generateBatchCode);
		
		Byte operstatus = operationstatusMapper.findOperstatusByAccnameAndBatchcode(operstatusVo);
		
		//如果状态不是初始的导入状态，则返回错误！
		if (operstatus != OperationstatusEnum.NORMAL) {
			return "failure";
		}

		// 将未开户的账户转成钱包验证的json格式，然后再次调用钱包校验的接口，完成再校验。

		WagesQueryVo wagesQueryVo = new WagesQueryVo();
		Wages wages = new Wages();
		wages.setStatus((byte) -1);
		wages.setBatchcode(generateBatchCode);
		
		int monthOfYear = CalendarUtil.getInstance().getMonthofYear();
		wages.setTrandate(monthOfYear);

		wagesQueryVo.setWages(wages);
		//将所有当前批次的当月的未开户的数据取出
		List<Wages> allWagesAccountNotOpend = wagesMapper.findAllWages(wagesQueryVo);
		WagesAccountValidationVo validationVo = new WagesAccountValidationVo();
		int unValidateCount = allWagesAccountNotOpend.size();
		
		Set<IdNoWalletStatus> idNoWalletStatusSet = new HashSet<IdNoWalletStatus>();
		IdNoWalletStatus idNoStatus = null;
		// 将未开户的工资账户取出重新组装校验
		for (Wages tempWage : allWagesAccountNotOpend) {
			String idNoStr = tempWage.getIdno();
			byte thirdcustid = tempWage.getThirdcustid();
			idNoStatus = new IdNoWalletStatus();
			idNoStatus.setIdno(idNoStr);
			idNoStatus.setThirdcustid(thirdcustid);
			idNoStatus.setStatus((byte)-1);
			idNoWalletStatusSet.add(idNoStatus);
		}
		validationVo.setCount(unValidateCount);
		validationVo.setData(idNoWalletStatusSet);
		validationVo.setBatchcode(generateBatchCode);
		
		String validateVoJson = JSONUtil.getInstance().obj2Json(validationVo);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("json", validateVoJson);
		String charSet = "UTF-8";
		// 从国安钱包返回的校验完成的未开户的身份证号
		String validatedFromWalletJson = HttpClientUtil.executePost(validateURL, paramsMap, charSet);

		if (validatedFromWalletJson == null || StringUtil.isBlank(validatedFromWalletJson)) {
			return "failure";
		}

		WagesAccountValidationVo validatedFromWallet = JSONUtil.getInstance().json2Obj(validatedFromWalletJson,
				WagesAccountValidationVo.class);
		if (validatedFromWallet != null) {
			int validatedNotOpenedCount = validatedFromWallet.getCount();
			String batchcode = validatedFromWallet.getBatchcode();
			Set<IdNoWalletStatus> validatedIdNoWalletStatusSet = validatedFromWallet.getData();
			int actualCount = 0;
			if (validatedIdNoWalletStatusSet != null) {
				actualCount = validatedIdNoWalletStatusSet.size();
			}
			if (batchcode != generateBatchCode) {
				throw new SystemException("传输回的校验数据批次错误！");
			}
			
			if (actualCount != validatedNotOpenedCount) {
				throw new SystemException("接受到的校验数据错误或可能丢失部分信息！");
			} 

			// 得到最后的再校验的已经开户的账户，修改wages条目的状态为已开户。
			Wages temp = null;
			for (IdNoWalletStatus idnoStatus : validatedIdNoWalletStatusSet) {
				temp = new Wages();
				temp.setIdno(idnoStatus.getIdno());
				temp.setStatus((byte) 0);
				temp.setThirdcustid(idnoStatus.getThirdcustid());
				temp.setTrandate(monthOfYear);
				temp.setBatchcode(generateBatchCode);
				wagesMapper.updateWagesStatus(temp);
			}
		} else {
			throw new SystemException("返回的json数据转化为object错误！");
		}
		return "success";
	}


	// 提交发工资到国安钱包发工资
	public String send(String generateBatchCode) {
		int monthOfYear = CalendarUtil.getInstance().getMonthofYear();

		Operationstatus operstatusVo = new Operationstatus();
		Session session = SecurityUtils.getSubject().getSession();
		String accountName = (String)session.getAttribute("accountname");
		
		operstatusVo.setAccountname(accountName);
		operstatusVo.setBatchcode(generateBatchCode);
		
		Byte operstatus = operationstatusMapper.findOperstatusByAccnameAndBatchcode(operstatusVo);
		
		String code = "";

		if (operstatus != OperationstatusEnum.NORMAL) {
			code = "failure";
		} else {
			try {
				WagesQueryVo wagesQueryVo = new WagesQueryVo();
				Wages wages = new Wages();
				wages.setStatus((byte)0);
				wages.setBatchcode(generateBatchCode);
				wages.setTrandate(monthOfYear);

				wagesQueryVo.setWages(wages);
				//将所有签章数据取出，进行发工资操作
				List<Wages> allWagesSigned = wagesMapper.findAllWages(wagesQueryVo);
				WalletAbutmentVo walletAbutmentVo = new WalletAbutmentVo();
				walletAbutmentVo.setCount(allWagesSigned.size());
				walletAbutmentVo.setTrandate(monthOfYear);
				walletAbutmentVo.setBatchcode(generateBatchCode);
				List<WagesPojo> data = new ArrayList<WagesPojo>();

				WagesPojo wagePojo = null;
				//将所有已经签章的数据发送钱包进行放款
				for (Wages tempWage : allWagesSigned) {
					String employeeName = tempWage.getEmployeename();
					byte thirdcustid = tempWage.getThirdcustid();
					String idno = tempWage.getIdno();
					BigDecimal tranamount = tempWage.getTranamount();

					wagePojo = new WagesPojo();
					wagePojo.setEmployeename(employeeName);
					wagePojo.setThirdcustid(thirdcustid);
					wagePojo.setIdno(idno);
					wagePojo.setTranamount(tranamount);
					wagePojo.setStatus((byte) 0);
					wagePojo.setRemark("");
					data.add(wagePojo);
				}
				walletAbutmentVo.setData(data);
				String walletAbutmentVoJson = JSONUtil.getInstance().obj2Json(walletAbutmentVo);

				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("json", walletAbutmentVoJson);
				String charSet = "UTF-8";
				// 从国安钱包返回的校验完成的未开户的身份证号
				HttpClientUtil.executePost(walletabutmentURL, paramsMap, charSet);

				code = "success";
			} catch (Exception ex) {
				code = "failure";
			}
		}
		return code;
	}
	
	/**
	 * 钱包回调，只处理成功的部分，失败的统一交给定时任务处理
	 * @param json
	 */
	public void walletAbutmentCallback(String json) {
		//钱包回调传入的json转化为对象
		WalletAbutmentTransVo waAbutmentTransVo = JSONUtil.getInstance().json2Obj(json, WalletAbutmentTransVo.class);
		Byte code = waAbutmentTransVo.getCode();
		
		//如果钱包处理成功，则进行钱包返回数据的校验入库，并更改相应的操作状态，如果钱包返回的错误，则交给定时任务统一处理，更改操作状态为失败
		if (code == (byte)1) {
			WalletAbutmentVo data = waAbutmentTransVo.getData();
			
			if (data == null) {
				return;
			}
			String batchcode = data.getBatchcode();
			int count = data.getCount();
			int trandate = data.getTrandate();
			
			//获取到回传数据之后，先查看当前操作状态，不匹配则直接return;
			Operationstatus operstatusVo = new Operationstatus();
			Session session = SecurityUtils.getSubject().getSession();
			String accountName = (String)session.getAttribute("accountname");
			
			operstatusVo.setAccountname(accountName);
			operstatusVo.setBatchcode(batchcode);
			
			Byte operstatus = operationstatusMapper.findOperstatusByAccnameAndBatchcode(operstatusVo);
			
			//将工资明细的实发合计取出，核对金额，如果金额不对，则直接设置失败
			WagesQueryVo wagesQueryVo = new WagesQueryVo();
			Wages wages = new Wages();
			wages.setStatus((byte)0);
			wages.setBatchcode(batchcode);
			wages.setTrandate(trandate);

			wagesQueryVo.setWages(wages);
			//将所有当前批次的当月的开户的数据取出
			List<Wages> allwagesOpendedAccount = wagesMapper.findAllWages(wagesQueryVo);
			
			Map<String, BigDecimal> tranamountMap = new HashMap<String, BigDecimal>();
			for (Wages tempWages : allwagesOpendedAccount) {
				tranamountMap.put(tempWages.getIdno(), tempWages.getTranamount());
			}
			
			//实际发工资的数据，如果已经传回，则更改已经传回的状态，其他设置为失败，以传回的数据为准！
			List<WagesPojo> wagesPojoList = data.getData();
			int actualCount = wagesPojoList.size();

			Wages wagesPo = null;
			int failureCount = 0;
			for (WagesPojo tempWagesPojo : wagesPojoList) {
				String tempIdNo = tempWagesPojo.getIdno();
				Byte tempStatus = tempWagesPojo.getStatus();
				if (tempStatus == (byte)2) {
					failureCount++;
				}
				
				String remark = tempWagesPojo.getRemark();
				Byte tempThirdcustid = tempWagesPojo.getThirdcustid();
				BigDecimal tempTranamount = tempWagesPojo.getTranamount();
				
				//如果实际放款的金额同发工资明细表中要求放款的金额不同，则设置为失败，并设置remark
				if (tempTranamount != tranamountMap.get(tempIdNo)) {
					tempStatus = 2;
					remark += "实际放款金额和实发合计不符！实际放款金额为：" + tempTranamount + "|实发合计为：" + tranamountMap.get(tempIdNo);
				}
				
				wagesPo = new Wages();
				wagesPo.setBatchcode(batchcode);
				wagesPo.setIdno(tempIdNo);
				wagesPo.setThirdcustid(tempThirdcustid);
				wagesPo.setRemark(remark);
				wagesPo.setStatus(tempStatus);
				wagesPo.setTrandate(trandate);
				wagesPo.setModifytime(new Date());
				
				wagesMapper.updateWagesStatus(wagesPo);
			}
			
			//默认为成功
			Byte operstatusResult = OperationstatusEnum.NORMAL;
			
			if (allwagesOpendedAccount.size() != count ||count != actualCount) {
				operstatusResult = OperationstatusEnum.NORMAL;
			} else {
				if (failureCount == actualCount) {
					operstatusResult = OperationstatusEnum.NORMAL;
				} else if (failureCount > 0 && failureCount < actualCount) {
					operstatusResult = OperationstatusEnum.NORMAL;
				} else {
					operstatusResult = OperationstatusEnum.NORMAL;
				}
			}
			
			Operationstatus operationstatus = new Operationstatus();
			operationstatus.setStatus(operstatusResult);
			operationstatus.setBatchcode(batchcode);
			operationstatus.setTrandate(trandate);
			operationstatusMapper.updateOperstatusByTrandateAndBatchcode(operationstatus);
			
		} 
	}
	
	public List<TellerPojo> findAllTellerList() throws Exception {
		
		Session session = SecurityUtils.getSubject().getSession();
		String accountName = (String)session.getAttribute("accountname");
		
		Operationstatus operstatusVo = new Operationstatus();
		operstatusVo.setAccountname(accountName);
		operstatusVo.setCreatetime(new Date());
		
		List<Operationstatus> allOperationstatus = operationstatusMapper.findOperstatusList(operstatusVo);
		
		TellerPojo tellerPojo = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TellerPojo> tellerList = new ArrayList<TellerPojo>();
		for (Operationstatus tempOperstatus : allOperationstatus) {
			
			tellerPojo = new TellerPojo();
			tellerPojo.setCreateTime(df.format(tempOperstatus.getCreatetime()));
			tellerPojo.setBatchCode(tempOperstatus.getBatchcode());
			tellerPojo.setAccountName(tempOperstatus.getAccountname());
			tellerPojo.setBudgettypeName(tempOperstatus.getBudgettypekey());
			tellerPojo.setCompanyName(tempOperstatus.getCompanykey());
			tellerPojo.setDescription(tempOperstatus.getDescription());
			tellerPojo.setOriginalfilename(tempOperstatus.getOriginalfilename());
			tellerPojo.setStatus(tempOperstatus.getStatus());
			tellerPojo.setTrandate(tempOperstatus.getTrandate());
			tellerPojo.setVersion(tempOperstatus.getVersion());
			tellerPojo.setModifytime(df.format(tempOperstatus.getModifytime()));
			
			
			tellerPojo.setSendCount(tempOperstatus.getSendcount());
			tellerPojo.setValidationCount(tempOperstatus.getValidationcount());
			
			
			WagesQueryVo wagesQueryVo = new WagesQueryVo();
			Wages wages = new Wages();
			wages.setBatchcode(tempOperstatus.getBatchcode());
			wagesQueryVo.setWages(wages);
			List<Wages> tempAllWages = wagesMapper.findAllWages(wagesQueryVo);
			
			int notOpenedAccountCount = 0;
			int openedAccountCount = 0;
			int remittingCount = 0;
			int completedCount = 0;
			int failureCount = 0;
			
			for (Wages tempWages : tempAllWages) {
				
				switch (tempWages.getStatus()) {
				case WagesStatusEnum.NOTOPENEDACCOUNT:
					notOpenedAccountCount++;
					break;
				case WagesStatusEnum.OPENEDACCOUNT:
					openedAccountCount++;
					break;
				case WagesStatusEnum.REMITTING:
					remittingCount++;
					break;
				case WagesStatusEnum.COMPLETED:
					completedCount++;
					break;
				case WagesStatusEnum.FAILURE:
					failureCount++;
					break;
				default:
					break;
				} 
			}
			
			tellerPojo.setCount(tempAllWages.size());
			
			tellerPojo.setOpenedAccountCount(openedAccountCount);
			tellerPojo.setNotOpenedAccountCount(notOpenedAccountCount);
			tellerPojo.setRemittingCount(remittingCount);
			tellerPojo.setCompletedCount(completedCount);
			tellerPojo.setFailureCount(failureCount);
			
			tellerList.add(tellerPojo);
		}
		
		return tellerList;
	}
}
