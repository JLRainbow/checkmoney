package com.citic.controller.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.citic.annotation.SystemLog;
import com.citic.bean.po.TempPo;
import com.citic.controller.index.BaseController;
import com.citic.entity.ChannelManagementFormMap;
import com.citic.mapper.ChannelManagementMapper;
import com.citic.service.CkMoneyWalletBankService;
import com.citic.util.Common;
import com.citic.util.ExcelUtil1;
/**
 * 
 *钱包，银行资金对账处理
 */
@Controller
@RequestMapping("/ckMoneyWalletBank")
public class CkMoneyWalletBankController extends BaseController {

	@Autowired
	private ChannelManagementMapper channelManagementMapper;
	@Autowired
	private CkMoneyWalletBankService ckMoneyWalletBankService;
	@RequestMapping("/ckMoneyWalletBankJsp")
	public String payChannel(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		channelManagementFormMap.put("where", "where status = 0 and channel_type = 3");
		//查找钱包列表，用于前台下拉列表展示
		List<ChannelManagementFormMap> walletBankList = channelManagementMapper.findByWhere(channelManagementFormMap);
		model.addAttribute("walletBankList", walletBankList);
		return Common.BACKGROUND_PATH + "/system/checkMoney/ckMoneyWalletBank";
	}
	
	/**
	 * 导入钱包明细Excel文件
	 */
	
	@ResponseBody
	@RequestMapping("/importWalletFile")
	@SystemLog(module="财务对账业务",methods="钱包-银行资金对账处理-钱包明细Excel导入")//记录操作日志
	public synchronized HashMap<String,Object> importWalletFile(@RequestParam(value = "file") MultipartFile file
			,@RequestParam(value ="fileType") String fileType) throws Exception, IOException {
		HashMap<String,Object> map=new HashMap<String,Object>();
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				String filename = file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				long starTime = System.currentTimeMillis();
				ArrayList<ArrayList<Object>> result = ExcelUtil1.readExcel(inputStream,filename); 
				map = ckMoneyWalletBankService.importWalletFile(result,fileType);
			    long endTime = System.currentTimeMillis();
				System.out.println("导入平台数据用时======》"+(endTime-starTime));
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
			}
			
			return map;
		}
	}
	
	/**
	 * 导入银行结算明细（315账户）Excel文件
	 */
	@ResponseBody
	@RequestMapping("/importBankDetailFile")
	@SystemLog(module="财务对账业务",methods="钱包-银行资金对账处理-银行结算明细（315账户）Excel导入")//记录操作日志
	public synchronized HashMap<String,Object> importBankDetailFile(@RequestParam(value = "file") MultipartFile file
			,@RequestParam(value ="fileType") String fileType) throws Exception, IOException {
		HashMap<String,Object> map=new HashMap<String,Object>();
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				String filename = file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				long starTime = System.currentTimeMillis();
				ArrayList<ArrayList<Object>> result = ExcelUtil1.readExcel(inputStream,filename); 
				map = ckMoneyWalletBankService.importBankDetailFile(result,fileType);
			    long endTime = System.currentTimeMillis();
				System.out.println("导入平台数据用时======》"+(endTime-starTime));
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
			}
			
			return map;
		}
	}
	
	/**
	 * 导入银行结算汇总（140账户）Excel文件
	 */
	@ResponseBody
	@RequestMapping("/importBankCollectFile")
	@SystemLog(module="财务对账业务",methods="钱包-银行资金对账处理-银行结算汇总（140账户）Excel导入")//记录操作日志
	public synchronized HashMap<String,Object> importBankCollectFile(@RequestParam(value = "file") MultipartFile file
			,@RequestParam(value ="fileType") String fileType) throws Exception, IOException {
		HashMap<String,Object> map=new HashMap<String,Object>();
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				String filename = file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				long starTime = System.currentTimeMillis();
				ArrayList<ArrayList<Object>> result = ExcelUtil1.readExcel(inputStream,filename); 
				map = ckMoneyWalletBankService.importBankCollectFile(result,fileType);
			    long endTime = System.currentTimeMillis();
				System.out.println("导入平台数据用时======》"+(endTime-starTime));
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
			}
			
			return map;
		}
	}
	
	/**
	 * 对账处理虚实对账
	 */
	@ResponseBody
	@RequestMapping("/chkMoneyEmptyToEntity")
	@SystemLog(module="财务对账业务",methods="钱包-银行资金对账处理-对账处理虚实对账")//记录操作日志
	public synchronized HashMap<String,Object> chkMoneyEmptyToEntity(){
		long starTime = System.currentTimeMillis();
		
		ckMoneyWalletBankService.chkMoneyEmptyToEntity();
		HashMap<String, Object> map = ckMoneyWalletBankService.getNoChkNum();
		
		
		long endTime = System.currentTimeMillis();
		System.out.println("对账处理用时======》"+(endTime-starTime));
		map.put("success", "对账结束!");
		return map;
	}
	
	/**
	 * 对账处理实实对账
	 */
	@ResponseBody
	@RequestMapping("/chkMoneyEntityToEntity")
	@SystemLog(module="财务对账业务",methods="钱包-银行资金对账处理-对账处理实实对账")//记录操作日志
	public synchronized HashMap<String,Object> chkMoneyEntityToEntity(){
		long starTime = System.currentTimeMillis();
		
		ckMoneyWalletBankService.chkMoneyEntityToEntity();
		HashMap<String, Object> map = ckMoneyWalletBankService.getNoChkNum();
		
		
		long endTime = System.currentTimeMillis();
		System.out.println("对账处理用时======》"+(endTime-starTime));
		map.put("success", "对账结束!");
		return map;
	}
	
	/**
	 * 获取未对账数目
	 */
	@ResponseBody
	@RequestMapping("/getNoChkNum")
	public HashMap<String,Object> getNoChkNum(){
		HashMap<String,Object> map = ckMoneyWalletBankService.getNoChkNum();
		return map;
	}
}
