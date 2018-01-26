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
import com.citic.controller.index.BaseController;
import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.mapper.AccountPaymentChkMapper;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.service.CkMoneySysService;
import com.citic.util.Common;
import com.citic.util.ExcelUtil1;
/**
 * 
 *对账系统设置
 */
@Controller
@RequestMapping("/ckMoneySys")
public class CkMoneySysController extends BaseController {

	@Autowired
	private CkMoneySysService ckMoneySysService;
	@Autowired
	private AccountPaymentChkMapper accountPaymentChkMapper;

	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;
	
	
	@RequestMapping("/ImportSupplierJsp")
	public String ImportSupplierJsp(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/ckMoneySys/importSupplier";
	}
	
	@RequestMapping("/backupsJsp")
	public String backupsJsp(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/ckMoneySys/backups";
	}
	
	/**
	 * 导入E店供应商关联表
	 */
	
	@ResponseBody
	@RequestMapping("/importSupplierFile")
	@SystemLog(module="对账系统设置",methods="E店供应商关联设置-导入E店供应商关联表")//记录操作日志
	public synchronized HashMap<String,Object> importSupplierFile(@RequestParam(value = "file") MultipartFile file
			) throws Exception, IOException {
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
				map = ckMoneySysService.importSupplierFile(result);
			    long endTime = System.currentTimeMillis();
				System.out.println("导入E店供应商关联表用时======》"+(endTime-starTime));
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常");
			}
			
			return map;
		}
	}
	
	/**
	 * 获取平台时间
	 */
	
	@ResponseBody
	@RequestMapping("/getPlatformDate")
	public  HashMap<String,Object> getPlatformDate(@RequestParam(value ="tableType") String tableType
			) throws Exception, IOException {
		HashMap<String,Object> map = ckMoneySysService.getPlatformDate();
			
		return map;
	}
	
	/**
	 * 获取支付时间
	 */
	
	@ResponseBody
	@RequestMapping("/getPayDate")
	public  HashMap<String,Object> getPayDate(@RequestParam(value ="tableType") String tableType
			) throws Exception, IOException {
		HashMap<String,Object> map = ckMoneySysService.getPayDate();
			
			return map;
	}
	/**
	 * 备份并删除平台对账表
	 */
	@ResponseBody
	@RequestMapping("/backupsAndDelForReceipt")
	@SystemLog(module="对账系统设置",methods="对账表单备份-备份并删除平台对账表")//记录操作日志
	public void backupsAndDelForReceipt(HttpServletResponse response,
			 @RequestParam(value ="startTime",required=false) String startTime
			,@RequestParam(value ="endTime",required=false) String endTime)  {
			startTime+=" 00:00:00";
			endTime+=" 23:59:59";
			ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
			AccountReceiptChkFormMap accountReceiptChkFormMap =new  AccountReceiptChkFormMap();
			
			String sql=" where receipt_date >='"+startTime+"' AND receipt_date <='"+endTime+"'";
			accountReceiptChkFormMap.put("where", sql);
			List<AccountReceiptChkFormMap> list = accountReceiptChkMapper.findByWhere(accountReceiptChkFormMap);
			if(list.size()!=0){
				result = ckMoneySysService.backupsAndDelForReceipt(list);
				try {
					ExcelUtil1.ExpExs(result, "查询导出", response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
//				model.addAttribute("noData", "该时间段没有数据,请重新选择时间段!");
			}
			
			
	}
	/**
	 * 备份并删除支付渠道对账表
	 */
	@ResponseBody
	@RequestMapping("/backupsAndDelForPay")
	@SystemLog(module="对账系统设置",methods="对账表单备份-备份并删除支付渠道对账表")//记录操作日志
	public void backupsAndDelForPay(HttpServletResponse response,
			 @RequestParam(value ="startTime",required=false) String startTime
			,@RequestParam(value ="endTime",required=false) String endTime)  {
			startTime+=" 00:00:00";
			endTime+=" 23:59:59";
			ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
			AccountPaymentChkFormMap accountPaymentChkFormMap =new  AccountPaymentChkFormMap();
			
			String sql=" where pay_date >='"+startTime+"' AND pay_date <='"+endTime+"'";
			accountPaymentChkFormMap.put("where", sql);
			List<AccountPaymentChkFormMap> list = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
			if(list.size()!=0){
				result = ckMoneySysService.backupsAndDelForPay(list);
				try {
					ExcelUtil1.ExpExs(result, "查询导出", response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
//				model.addAttribute("noData", "该时间段没有数据,请重新选择时间段!");
			}
			
			
	}
	
}
