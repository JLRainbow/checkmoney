package com.citic.controller.system;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.citic.annotation.SystemLog;
import com.citic.bean.po.Operationstatus;
import com.citic.bean.pojo.TellerPojo;
import com.citic.constants.OperationstatusEnum;
import com.citic.controller.index.BaseController;
import com.citic.entity.BudgettypeFormMap;
import com.citic.entity.CompanyFormMap;
import com.citic.entity.WagesFormMap;
import com.citic.exception.SystemException;
import com.citic.mapper.BudgettypeMapper;
import com.citic.mapper.CompanyMapper;
import com.citic.mapper.OperationstatusMapper;
import com.citic.mapper.WagesMapper;
import com.citic.plugin.PageView;
import com.citic.service.FileUploadService;
import com.citic.service.OperationstatusService;
import com.citic.service.WagesService;
import com.citic.util.BatchCodeGenerator;
import com.citic.util.CalendarUtil;
import com.citic.util.Common;
import com.citic.util.POIUtils;
import com.citic.util.StringUtil;

@Controller
@RequestMapping("/wages")
public class WagesController extends BaseController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private WagesService wagesService;
	@Autowired
	private OperationstatusService operationstatusService;
	
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private BudgettypeMapper budgettypeMapper;
	
	@Autowired
	private WagesMapper wagesMapper;
	
	@Autowired
	private OperationstatusMapper operationstatusMapper;

	@RequestMapping("wagesImport")
	public String wagesImport(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		
		CompanyFormMap companyFormMap = new CompanyFormMap();
		companyFormMap.put("where", "where status = 1");
		
		List<CompanyFormMap> companyList = companyMapper.findByWhere(companyFormMap);
		model.addAttribute("companies", companyList);
		
		BudgettypeFormMap budgettypeFormMap = new BudgettypeFormMap();
		budgettypeFormMap.put("where", "where status = 1");
		
		List<BudgettypeFormMap> budgettypeList = budgettypeMapper.findByWhere(budgettypeFormMap);
		model.addAttribute("budgettypes", budgettypeList);
		
		return Common.BACKGROUND_PATH + "/system/wages/wagesImport";
	}
	
	
	@RequestMapping("tellerList")
	public String wagesAccountNotOpened(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		
		List<TellerPojo> tellerList = wagesService.findAllTellerList();
		
		model.addAttribute("tellerList", tellerList);
		
		return Common.BACKGROUND_PATH + "/system/wages/tellerList";
	}
	
	@RequestMapping("historiesSearch")
	public String historiesSearch(Model model) throws Exception {
		CompanyFormMap companyFormMap = new CompanyFormMap();
		companyFormMap.put("where", "where status = 1");
		
		List<CompanyFormMap> companyList = companyMapper.findByWhere(companyFormMap);
		model.addAttribute("companies", companyList);
		
		BudgettypeFormMap budgettypeFormMap = new BudgettypeFormMap();
		budgettypeFormMap.put("where", "where status = 1");
		
		List<BudgettypeFormMap> budgettypeList = budgettypeMapper.findByWhere(budgettypeFormMap);
		model.addAttribute("budgettypes", budgettypeList);
		return Common.BACKGROUND_PATH + "/system/wageshistories/historiesSearch";
	}
	
	@RequestMapping("myWagesHistories")
	public String myWagesHistories(Model model) throws Exception {
		CompanyFormMap companyFormMap = new CompanyFormMap();
		companyFormMap.put("where", "where status = 1");
		
		List<CompanyFormMap> companyList = companyMapper.findByWhere(companyFormMap);
		model.addAttribute("companies", companyList);
		
		BudgettypeFormMap budgettypeFormMap = new BudgettypeFormMap();
		budgettypeFormMap.put("where", "where status = 1");
		
		List<BudgettypeFormMap> budgettypeList = budgettypeMapper.findByWhere(budgettypeFormMap);
		model.addAttribute("budgettypes", budgettypeList);
		return Common.BACKGROUND_PATH + "/system/wages/myWagesHistories";
	}
	
	@ResponseBody
	@RequestMapping("findHistoriesByPage")
	public PageView findHistoryByPage( String pageNow,
				String pageSize,String column,String sort) throws Exception {
			String order = "";
			WagesFormMap wagesFormMap = getFormMap(WagesFormMap.class);
			
			if(Common.isNotEmpty(column)){
				order = " order by "+column+" "+sort;
			}else{
				order = " order by modifytime desc";
			}
			wagesFormMap.put("column", column);
			wagesFormMap.put("orderby", order);
			
			wagesFormMap = toFormMap(wagesFormMap, pageNow, pageSize,wagesFormMap.getStr("orderby"));
	        pageView.setRecords(wagesMapper.findByPage(wagesFormMap));
			return pageView;
		}
	
	@ResponseBody
	@RequestMapping("/initialWagesExcel")
	@SystemLog(module="收支管理",methods="收支管理-清单导入")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String initialWagesExcel(MultipartFile uploadWagesExcelFile, String companykey, String budgettypekey, String description, String trandate) throws Exception {
		
		String generateBatchCode = BatchCodeGenerator.getInstance().generate();
		
		String originalFilename = uploadWagesExcelFile.getOriginalFilename();
		if (originalFilename == null || StringUtil.isBlank(originalFilename)) {
			throw new SystemException("请选择文件！");
		}
		
		if (trandate == null) {
			throw new SystemException("请选择收支月份！");
		}
		//上传文件，作为备份
		String targetFilePath = fileUploadService.upload(uploadWagesExcelFile, String.valueOf(CalendarUtil.getInstance().getMonthofYear()));
		
		//新增操作状态，默认为正常
		 
		Operationstatus operstatusPo = new Operationstatus();
		Session session = SecurityUtils.getSubject().getSession();
		String accountname = (String)session.getAttribute("accountname");
		operstatusPo.setAccountname(accountname);
		operstatusPo.setBatchcode(generateBatchCode);
		operstatusPo.setCompanykey(companykey);
		operstatusPo.setBudgettypekey(budgettypekey);
		operstatusPo.setTrandate(Integer.parseInt(trandate.replace("-", "")));
		operstatusPo.setOriginalfilename(originalFilename);
		operationstatusService.saveOperationstatus(operstatusPo);
		
		//从targetFilePath指定路径获取excel的内容map数据。
		List<Map<String, String>> excelContentMap = POIUtils.readExcelContent(targetFilePath);
		
		if (excelContentMap.isEmpty()) {
			throw new SystemException("传入的文件内容可能有误！");
		}
		
		//获取到数据后同数据库中工资数据对比，如果已经存在当月的数据，则覆盖，如果没有则添加。
		wagesService.saveWages(generateBatchCode, excelContentMap);
	
		return "success";
	}
	
	/**
	 * 添加单条工资明细
	 * 
	 */
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="工资管理",methods="工资管理-导入工资明细Excel")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity() throws Exception {
		try {
			WagesFormMap wagesFormMap = getFormMap(WagesFormMap.class);
			wagesMapper.addEntity(wagesFormMap);//新增后返回新增信息
		} catch (Exception e) {
			 throw new SystemException("添加工资明细异常");
		}
		return "success";
	}
	
	/**
	 * 检查当前操作状态
	 * SCRAP = -1;
	 * NORMAL = 0;
	 */
	@ResponseBody
	@RequestMapping("checkStatus")
	public String checkStatus(String batchCode){
	
		int monthOfYear = CalendarUtil.getInstance().getMonthofYear();
		
		Operationstatus operstatusVo = new Operationstatus();
		Session session = SecurityUtils.getSubject().getSession();
		String accountName = (String)session.getAttribute("accountname");
		
		operstatusVo.setAccountname(accountName);
		operstatusVo.setBatchcode(batchCode);
		
		Byte operstatus = operationstatusMapper.findOperstatusByAccnameAndBatchcode(operstatusVo);
		
		return OperationstatusEnum.getName(operstatus);
	}
	
	/**
	 * 在未签字之前，对未开户账户再校验
	 * 
	 */
	@ResponseBody
	@SystemLog(module="工资管理",methods="工资管理-未开户再校验")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@RequestMapping("reValidate")
	public String reValidate(String batchCode) throws Exception {
		return wagesService.revalidate(batchCode);
	}
	
	/**
	 * 确认后发工资
	 * 
	 */
	@ResponseBody
	@SystemLog(module="工资管理",methods="工资管理-调用钱包发工资接口")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@RequestMapping("send")
	public String send(String batchCode) throws Exception {
		return wagesService.send(batchCode);
	}
	
	/**
	 * 确认后发工资
	 * 
	 */
	@SystemLog(module="工资管理",methods="工资管理-钱包回调")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@RequestMapping("walletAbutmentCallback")
	public void walletAbutmentCallback(String json) throws Exception {
		wagesService.walletAbutmentCallback(json);
	}
}