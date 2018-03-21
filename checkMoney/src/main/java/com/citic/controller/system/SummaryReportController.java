package com.citic.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.citic.service.SummaryReportService;
import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.entity.ChannelManagementFormMap;
import com.citic.entity.WxGigtCardBuyRecordFormMap;
import com.citic.entity.WxWeBankFormMap;
import com.citic.mapper.AccountPaymentChkMapper;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.mapper.ChannelManagementMapper;
import com.citic.mapper.WxGiftCardMapper;
import com.citic.mapper.WxWebankMapper;
import com.citic.plugin.PageView;
import com.citic.util.Common;
import com.citic.util.ExcelUtil1;
/**
 * 
 *汇总报表生成(虚虚对账)
 */
@Controller
@RequestMapping("/summary_report")
public class SummaryReportController extends BaseController {
	
	@Autowired
	private SummaryReportService summaryReportService;
	
	@Autowired
	private AccountPaymentChkMapper accountPaymentChkMapper;

	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;
	
	@Autowired
	private ChannelManagementMapper channelManagementMapper;
	
	@Autowired
	private WxWebankMapper wxWebankMapper;
	
	@Autowired
	private WxGiftCardMapper wxGiftCardMapper;
	
	@RequestMapping("/cm_information")
	public String cmInformation(Model model) throws Exception {
		
		return Common.BACKGROUND_PATH + "/system/summaryReport/cm_information";
	}
	
	@RequestMapping("/create_summary_report")
	public String createSummaryReport(Model model) throws Exception {
		
		return Common.BACKGROUND_PATH + "/system/summaryReport/create_summary_report";
	}
	
	/**
	 * 汇总报表生成
	 */
	@ResponseBody
	@RequestMapping(value="/exportExcel")
	@SystemLog(module="汇总报表业务",methods="汇总报表生成（虚虚对账）-报表生成")//记录操作日志
	public void exportExcel(
			@RequestParam(value ="reportType") String reportType
			,@RequestParam(value ="payWay") String payWay
			,@RequestParam(value ="fund_type") String fund_type
			,@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime,
	HttpServletResponse response){
		Map<String,Object> hm =new HashMap<String,Object>();
		hm.put("reportType", reportType);
		hm.put("payWay", payWay);
		hm.put("fund_type", fund_type);
		hm.put("startDate", startTime);
		hm.put("endDate", endTime);
		summaryReportService.exportExcel(response,hm);
	}
	/**
	 * 
	 *条件查询
	 */
	@ResponseBody
	@RequestMapping("/chkMoneyInforFindByPage")
	public PageView chkMoneyInforFindByPage(String pageNow,String pageSize, String column,String sort
			,@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime
			,@RequestParam(value ="chanelType") String chanelType
			,@RequestParam(value ="fund_type") String fund_type
			,@RequestParam(value ="channel_name") String channel_name
			,@RequestParam(value ="pay_platform") String pay_platform
			,@RequestParam(value ="check_order") String check_order
			,@RequestParam(value ="check_result",defaultValue ="") String check_result) throws Exception {
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		if("收款渠道".equals(chanelType)){
			AccountReceiptChkFormMap accountReceiptChkFormMap = getFormMap(AccountReceiptChkFormMap.class);
			String sql="   fund_type in ( "+fund_type+")";
			if(startTime.length()!=9&&endTime.length()!=9){
				
				sql+=" AND receipt_date >='"+startTime+"' AND receipt_date <='"+endTime+"'";
			}
			if(!check_result.isEmpty()){
				sql+=" AND check_result = "+check_result;
			}
			if(!pay_platform.isEmpty()){
				
			   String str = " AND pay_platform in (";
			   String[] split = pay_platform.split("/");
				for (String string : split) {
					str += "'"+string+"',";
					
				}
				String str2 = str.substring(0, str.length()-1)+")";
				sql+=str2;
			}
			if(!check_order.isEmpty()){
				sql+=" AND relation_id LIKE '%"+check_order+"%'";
			}
			accountReceiptChkFormMap.put("where", sql);
			String order = "";
			if(Common.isNotEmpty(column)){
				order = " order by "+column+" "+sort;
			}else{
				order = " order by receipt_date desc";
			}
			accountReceiptChkFormMap.put("orderby", order);
			accountReceiptChkFormMap=toFormMap(accountReceiptChkFormMap, pageNow, pageSize,accountReceiptChkFormMap.getStr("orderby"));
			pageView.setRecords(accountReceiptChkMapper.findByPage(accountReceiptChkFormMap));
			
		}
		if("支付渠道".equals(chanelType)){
			AccountPaymentChkFormMap accountPaymentChkFormMap = getFormMap(AccountPaymentChkFormMap.class);
			
			String sql="    fund_type in ( "+fund_type+")";
			if(startTime.length()!=9&&endTime.length()!=9){
				sql+=" AND pay_date >='"+startTime+"' AND pay_date <='"+endTime+"'";
			}
			if(!check_result.isEmpty()){
				sql+=" AND check_result = "+check_result;
			}
			if(!channel_name.isEmpty()){
				String str = " AND channel_name in (";
				   String[] split = channel_name.split("/");
					for (String string : split) {
						str += "'"+string+"',";
						
					}
					String str2 = str.substring(0, str.length()-1)+")";
					sql+=str2;
			}
			if(!check_order.isEmpty()){
				sql+=" AND check_order LIKE '%"+check_order+"%'";
			}
			accountPaymentChkFormMap.put("where", sql);
			String order = "";
			if(Common.isNotEmpty(column)){
				order = " order by "+column+" "+sort;
			}else{
				order = " order by pay_date desc";
			}
			accountPaymentChkFormMap.put("orderby", order);
			accountPaymentChkFormMap=toFormMap(accountPaymentChkFormMap, pageNow, pageSize,accountPaymentChkFormMap.getStr("orderby"));
			pageView.setRecords(accountPaymentChkMapper.findByPage(accountPaymentChkFormMap));
			
		}
		return pageView;
	}
	/**
	 * 
	 *查询详情
	 */
	@RequestMapping("/queryDetailsById")
	public String queryDetailsById(Model model,@RequestParam(value ="id") String id
			,@RequestParam(value ="chanelType") String chanelType) throws Exception {
		AccountReceiptChkFormMap accountReceiptChkFormMap = null;
		AccountPaymentChkFormMap accountPaymentChkFormMap = null;
		if("收款渠道".equals(chanelType)){
			 accountReceiptChkFormMap = accountReceiptChkMapper.findbyFrist("id", id, AccountReceiptChkFormMap.class);
			 model.addAttribute("accountReceiptChkFormMap", accountReceiptChkFormMap);
			 return Common.BACKGROUND_PATH + "/system/summaryReport/detailsForReceipt";
		}
		if("支付渠道".equals(chanelType)){
			accountPaymentChkFormMap = accountPaymentChkMapper.findbyFrist("id", id, AccountPaymentChkFormMap.class);
			model.addAttribute("accountPaymentChkFormMap", accountPaymentChkFormMap);
			 return Common.BACKGROUND_PATH + "/system/summaryReport/detailsForPay";
		}
		return null;
		
	}
	/**
	 * 获取支付下拉列表
	 */
	@ResponseBody
	@RequestMapping("/getPayWay")
	public List<ChannelManagementFormMap> getPayWay()  {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		channelManagementFormMap.put("where", "where status = 0 and channel_type = 1");
		//查找支付渠道列表，用于前台下拉列表展示
		List<ChannelManagementFormMap> payList = channelManagementMapper.findByWhere(channelManagementFormMap);
		return payList;
	}
	
	/**
	 * 
	 *方法：查询导出
	 *创建时间：2017年7月17日
	 *创建者：jial
	 */
	@ResponseBody
	@RequestMapping("/queryReport")
	@SystemLog(module="对账信息查询",methods="对账信息查询-查询导出")//记录操作日志
	public void queryReport(HttpServletResponse response,String column,String sort
			,@RequestParam(value ="startTime",required=false) String startTime
			,@RequestParam(value ="endTime",required=false) String endTime
			,@RequestParam(value ="chanelType") String chanelType
			,@RequestParam(value ="fund_type") String fund_type
			,@RequestParam(value ="channel_name",required=false) String channel_name
			,@RequestParam(value ="pay_platform",required=false) String pay_platform
			,@RequestParam(value ="check_order") String check_order
			,@RequestParam(value ="weBankType") String weBankType
			,@RequestParam(value ="check_result",defaultValue ="") String check_result)  {
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		if("收款渠道".equals(chanelType)){
			AccountReceiptChkFormMap accountReceiptChkFormMap = getFormMap(AccountReceiptChkFormMap.class);
			
			String sql="   where fund_type in ( "+fund_type+")";
			if(startTime.length()==19&&endTime.length()==19){
				
				sql+=" AND receipt_date >='"+startTime+"' AND receipt_date <='"+endTime+"'";
			}
			if(!check_result.isEmpty()){
				sql+=" AND check_result = "+check_result;
			}
			if(pay_platform!=null&&!pay_platform.isEmpty()){
//				sql+=" AND pay_platform = '"+pay_platform+"'";
				 String str = " AND pay_platform in (";
				   String[] split = pay_platform.split("/");
					for (String string : split) {
						str += "'"+string+"',";
						
					}
					String str2 = str.substring(0, str.length()-1)+")";
					sql+=str2;
			}
			if(!check_order.isEmpty()){
				sql+=" AND relation_id LIKE '%"+check_order+"%'";
			}
			sql+= " order by receipt_date desc";
			accountReceiptChkFormMap.put("where", sql);
			List<AccountReceiptChkFormMap> list = accountReceiptChkMapper.findByWhere(accountReceiptChkFormMap);
			
			result = summaryReportService.impReceipForExcel(list);
			
		}
		if("支付渠道".equals(chanelType)){
			AccountPaymentChkFormMap accountPaymentChkFormMap = getFormMap(AccountPaymentChkFormMap.class);

			String sql="   where fund_type in ( "+fund_type+")";
			if(startTime.length()==19&&endTime.length()==19){
				sql+=" AND pay_date >='"+startTime+"' AND pay_date <='"+endTime+"'";
			}
			if(!check_result.isEmpty()){
				sql+=" AND check_result = "+check_result;
			}
			if(channel_name!=null&&!channel_name.isEmpty()){
//				sql+=" AND channel_name = '"+channel_name+"'";
				String str = " AND channel_name in (";
				   String[] split = channel_name.split("/");
					for (String string : split) {
						str += "'"+string+"',";
						
					}
					String str2 = str.substring(0, str.length()-1)+")";
					sql+=str2;
			}
			if(!check_order.isEmpty()){
				sql+=" AND check_order LIKE '%"+check_order+"%'";
			}
			sql+= " order by pay_date desc";
			accountPaymentChkFormMap.put("where", sql);
			
			List<AccountPaymentChkFormMap> list = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
			result = summaryReportService.impPayForExcel(list);
		}
		if("weBank".equals(chanelType)){
			if("微信支付".equals(weBankType)){
				WxWeBankFormMap wxWeBankFormMap = new WxWeBankFormMap();
				
				String sql="   WHERE fund_type in ( "+fund_type+")";
				if(startTime.length()!=9&&endTime.length()!=9){
					sql+=" AND pay_date >='"+startTime+"' AND pay_date <='"+endTime+"'";
				}
				if(!check_result.isEmpty()){
					sql+=" AND check_result = "+check_result;
				}
				if(!check_order.isEmpty()){
					sql+=" AND check_order LIKE '%"+check_order+"%'";
				}
				sql+= " order by pay_date desc";
				wxWeBankFormMap.put("where", sql);
				
				List<WxWeBankFormMap> list = wxWebankMapper.findByWhere(wxWeBankFormMap);
				result = summaryReportService.exportWxWeBankForExcel(list);
			}
			if("平台收款".equals(weBankType)){
				WxGigtCardBuyRecordFormMap wxGigtCardBuyRecordFormMap = getFormMap(WxGigtCardBuyRecordFormMap.class);
				
				String sql="   WHERE 1=1";
				if(startTime.length()!=9&&endTime.length()!=9){
					sql+=" AND pay_finish_time >='"+startTime+"' AND pay_finish_time <='"+endTime+"'";
				}
				if(!check_result.isEmpty()){
					sql+=" AND check_result = "+check_result;
				}
				
				if(!check_order.isEmpty()){
					sql+=" AND wx_order_id LIKE '%"+check_order+"%'";
				}
				sql+= " order by pay_finish_time desc";
				wxGigtCardBuyRecordFormMap.put("where", sql);
				List<WxGigtCardBuyRecordFormMap> list = wxGiftCardMapper.findByWhere(wxGigtCardBuyRecordFormMap);
				result = summaryReportService.exportWxGigtCardBuyRecordForExcel(list);
			}
		}
		try {
			ExcelUtil1.ExpExs(result, "查询导出", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 *条件查询
	 */
	@ResponseBody
	@RequestMapping("/weBankFindByPage")
	public PageView weBankFindByPage(String pageNow,String pageSize, String column,String sort
			,@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime
			,@RequestParam(value ="fund_type") String fund_type
			,@RequestParam(value ="weBankType") String weBankType
			,@RequestParam(value ="check_order") String check_order
			,@RequestParam(value ="check_result",defaultValue ="") String check_result) throws Exception {
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		if("微信支付".equals(weBankType)){
			WxWeBankFormMap wxWeBankFormMap = getFormMap(WxWeBankFormMap.class);
			
			String sql="    fund_type in ( "+fund_type+")";
			if(startTime.length()!=9&&endTime.length()!=9){
				sql+=" AND pay_date >='"+startTime+"' AND pay_date <='"+endTime+"'";
			}
			if(!check_result.isEmpty()){
				sql+=" AND check_result = "+check_result;
			}
			
			if(!check_order.isEmpty()){
				sql+=" AND check_order LIKE '%"+check_order+"%'";
			}
			wxWeBankFormMap.put("where", sql);
			String order = "";
			if(Common.isNotEmpty(column)){
				order = " order by "+column+" "+sort;
			}else{
				order = " order by pay_date desc";
			}
			wxWeBankFormMap.put("orderby", order);
			wxWeBankFormMap=toFormMap(wxWeBankFormMap, pageNow, pageSize,wxWeBankFormMap.getStr("orderby"));
			pageView.setRecords(wxWebankMapper.findByPage(wxWeBankFormMap));
			
		}
		if("平台收款".equals(weBankType)){
			WxGigtCardBuyRecordFormMap wxGigtCardBuyRecordFormMap = getFormMap(WxGigtCardBuyRecordFormMap.class);
			
			String sql="   1=1";
			if(startTime.length()!=9&&endTime.length()!=9){
				sql+=" AND pay_finish_time >='"+startTime+"' AND pay_finish_time <='"+endTime+"'";
			}
			if(!check_result.isEmpty()){
				sql+=" AND check_result = "+check_result;
			}
			
			if(!check_order.isEmpty()){
				sql+=" AND wx_order_id LIKE '%"+check_order+"%'";
			}
			wxGigtCardBuyRecordFormMap.put("where", sql);
			String order = "";
			if(Common.isNotEmpty(column)){
				order = " order by "+column+" "+sort;
			}else{
				order = " order by pay_finish_time desc";
			}
			wxGigtCardBuyRecordFormMap.put("orderby", order);
			wxGigtCardBuyRecordFormMap=toFormMap(wxGigtCardBuyRecordFormMap, pageNow, pageSize,wxGigtCardBuyRecordFormMap.getStr("orderby"));
			pageView.setRecords(wxGiftCardMapper.findByPage(wxGigtCardBuyRecordFormMap));
			
		}
		return pageView;
	}
	 
}
