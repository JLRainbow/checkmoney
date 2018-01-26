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
import com.citic.mapper.BankDetailMapper;
import com.citic.service.SumReportyEntityToEntityService;
import com.citic.util.Common;
/**
 * 
 *汇总报表生成(实实对账)
 */
@Controller
@RequestMapping("/sumReportyEntityToEntity")
public class SumReportyEntityToEntityController extends BaseController {
	
	@Autowired
	private SumReportyEntityToEntityService sumReportyEntityToEntityService;
	
	@RequestMapping("/entityToEntityJsp")
	public String payChannel(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/summaryReport/entityToEntity";
	}
	/**
	 *汇总报表生成（实实对账） 
	 */
	@ResponseBody
	@RequestMapping(value="/exportExcel")
	@SystemLog(module="汇总报表业务",methods="汇总报表生成（实实对账）-报表生成")//记录操作日志
	public void exportExcel(
			@RequestParam(value ="reportType") String reportType
			,@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime,HttpServletResponse response){
		Map<String,Object> hm =new HashMap<String,Object>();
		hm.put("reportType", reportType);
		hm.put("startDate", startTime);
		hm.put("endDate", endTime);
		if("银行315账户汇总信息核对表".equals(reportType)){
			
			sumReportyEntityToEntityService.exportBankDetailExcel(response,hm);
		}
		if("银行140账户汇总信息核对表".equals(reportType)){
			
			sumReportyEntityToEntityService.exportBankCollectExcel(response,hm);
		}
		
	}
}
