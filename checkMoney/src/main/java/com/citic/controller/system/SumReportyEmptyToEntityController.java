package com.citic.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.annotation.SystemLog;
import com.citic.controller.index.BaseController;
import com.citic.service.SumReportyEmptyToEntityService;
import com.citic.util.Common;
/**
 * 
 *汇总报表生成(虚实对账)
 */
@Controller
@RequestMapping("/sumReportyEmptyToEntity")
public class SumReportyEmptyToEntityController extends BaseController {
	
	@Autowired
	private SumReportyEmptyToEntityService sumReportyEmptyToEntityService;
	

	@RequestMapping("/emptyToEntityJsp")
	public String payChannel(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/summaryReport/emptyToEntity";
	}
	/**
	 * 汇总报表生成（虚实对账）
	 */
	@ResponseBody
	@RequestMapping(value="/exportExcel")
	@SystemLog(module="汇总报表业务",methods="汇总报表生成（虚实对账）-报表生成")//记录操作日志
	public void exportExcel(
			@RequestParam(value ="reportType") String reportType
			,@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime,HttpServletResponse response){
		Map<String,Object> hm =new HashMap<String,Object>();
		hm.put("reportType", reportType);
		hm.put("startDate", startTime);
		hm.put("endDate", endTime);
		if("钱包明细账户核对汇总表".equals(reportType)){
			
			sumReportyEmptyToEntityService.exportWalletDetailExcel(response,hm);
		}
		if("银行明细账户核对汇总表".equals(reportType)){
			
			sumReportyEmptyToEntityService.exportBankDetailExcel(response,hm);
		}
		
	}
}
