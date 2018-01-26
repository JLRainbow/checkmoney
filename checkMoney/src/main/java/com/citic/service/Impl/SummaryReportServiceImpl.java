package com.citic.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.mapper.AccountPaymentChkMapper;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.service.SummaryReportService;
import com.citic.util.ExcelUtil1;
@Service
public class SummaryReportServiceImpl implements SummaryReportService {

	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;
	
	@Autowired
	private AccountPaymentChkMapper accountPaymentChkMapper;
	
	public void exportExcel(HttpServletResponse response, Map<String, Object> hm) {
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		String fileName=hm.get("reportType").toString();
		String payWay=hm.get("payWay").toString();
		String fund_type=hm.get("fund_type").toString();
		String startDate=hm.get("startDate").toString()+" 00:00:00";
		String endDate=hm.get("endDate").toString()+" 23:59:59";
		//收款导出Excel逻辑
		if("平台销售核对汇总表".equals(fileName)||"微超销售核对汇总表".equals(fileName)){
			AccountReceiptChkFormMap accountReceiptChkFormMap = new AccountReceiptChkFormMap();
			String sql=" WHERE receipt_date >='"+startDate+"' AND receipt_date <='"+endDate+"' AND fund_type in ( "+fund_type+")";
			if("平台销售核对汇总表".equals(fileName)){
				sql+=" AND source = 1";
			}
			if("微超销售核对汇总表".equals(fileName)){
				sql+=" AND source = 2";
			}
			if(payWay.indexOf("/")!=-1){
				String str = " AND pay_platform in (";
				String[] split = payWay.split("/");
				for (String string : split) {
					str += "'"+string+"',";
					
				}
				String str2 = str.substring(0, str.length()-1)+")";
				sql+=str2;
			}else{
				if(payWay.equals("微信")){
					sql+=" and pay_platform in ( '"+payWay+"') ";
				}else if(payWay.equals("微信扫码")){
					payWay = "微信扫码,微信公众号";
					 String str = " AND pay_platform in (";
					   String[] split = payWay.split(",");
						for (String string : split) {
							str += "'"+string+"',";
							
						}
						String str2 = str.substring(0, str.length()-1)+")";
						sql+=str2;
				}else{
					sql+=" and pay_platform like '"+payWay+"%' ";
					
				}
			}
			
			sql+=" AND id not in (SELECT id FROM `t_account_receipt_chk` WHERE merge_flag IS NOT NULL AND check_result IN (0, 1, 2)) ORDER BY receipt_date";
			accountReceiptChkFormMap.put("where", sql);
			List<AccountReceiptChkFormMap> list = accountReceiptChkMapper.findByWhere(accountReceiptChkFormMap);
			result = impReceipForExcel(list);
			if(!payWay.equals("微信扫码,微信公众号")){
				payWay = (payWay==""?"全部":payWay);
			}else{
				payWay = "微信扫码";
			}
			fileName = fileName +"_"+payWay;
		}
		//支付导出Excel逻辑
		if("支付宝核对汇总表".equals(fileName)||"微信核对汇总表".equals(fileName)||"国安付核对汇总表".equals(fileName)||"微信扫码核对汇总表".equals(fileName)){
			AccountPaymentChkFormMap accountPaymentChkFormMap = new AccountPaymentChkFormMap();
			String sql=" WHERE pay_date >='"+startDate+"' AND pay_date <='"+endDate+"' AND fund_type in ( "+fund_type+") ";
			String str = " AND channel_name in (";
			String[] split = payWay.split("/");
			for (String string : split) {
				str += "'"+string+"',";
				
			}
			String str2 = str.substring(0, str.length()-1)+")";
			sql+=str2;
			sql+= " ORDER BY pay_date";
			accountPaymentChkFormMap.put("where", sql);
			List<AccountPaymentChkFormMap> list = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
			result = impPayForExcel(list);

		}
		fileName+=this.getTimeStamp();
		try {
			ExcelUtil1.ExpExs(result, fileName, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList<Object>> impReceipForExcel(List<AccountReceiptChkFormMap> list){
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		//设置表头列
		ArrayList<Object> list3=new ArrayList<Object>();
		list3.add("时间");
		list3.add("流水号");
		list3.add("收款金额");
		list3.add("支付金额");
		list3.add("对账结果");
		list3.add("店铺名称");
		list3.add("E店名称");
		list3.add("是否协议");
		list3.add("供应商名称");
		list3.add("支付平台");
		list3.add("款单种类");
		result.add(list3);
		for (AccountReceiptChkFormMap arrayList : list) {
			ArrayList<Object> list2=new ArrayList<Object>();
			list2.add(arrayList.get("receipt_date").toString().substring(0,10));
			list2.add(arrayList.get("relation_id"));
			list2.add(arrayList.get("pay_amount"));
			list2.add(arrayList.get("recipt_amount"));
			String check_result = arrayList.get("check_result").toString();
			if("0".equals(check_result)){
				check_result="未对账";
			}else if("1".equals(check_result)){
				check_result="对账相符";
			}else if("2".equals(check_result)){
				check_result="稽查";
			}else if("3".equals(check_result)){
				check_result="合并未对账";
			}else if("4".equals(check_result)){
				check_result="合并对账符合";
			}else if("5".equals(check_result)){
				check_result="合并待稽查";
			}
			list2.add(check_result);
			list2.add(arrayList.get("store_name"));
			list2.add(arrayList.get("eshop_name"));
			list2.add(arrayList.get("self"));
			
			list2.add("".equals(arrayList.get("provider_name"))||arrayList.get("provider_name")==null?"无":arrayList.get("provider_name"));
			list2.add(arrayList.get("pay_platform"));
			String fund_type1 = arrayList.get("fund_type").toString();
			if("1".equals(fund_type1)){
				fund_type1="收款";
			}else if("2".equals(fund_type1)){
				fund_type1="退款";
			}
			list2.add(fund_type1);
			result.add(list2);
		}
		return result;
		
	}

	public ArrayList<ArrayList<Object>> impPayForExcel(List<AccountPaymentChkFormMap> list){
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		//设置表头列
		ArrayList<Object> list3=new ArrayList<Object>();
		list3.add("时间");
		list3.add("流水号");
		list3.add("支付金额");
		list3.add("收款金额");
		list3.add("对账结果");
		list3.add("店铺名称");
		list3.add("E店名称");
		list3.add("是否协议");
		list3.add("供应商名称");
		list3.add("支付平台");
		list3.add("款单种类");
		result.add(list3);
		for (AccountPaymentChkFormMap arrayList : list) {
			ArrayList<Object> list2=new ArrayList<Object>();
			list2.add(arrayList.get("pay_date").toString().substring(0,10));
			list2.add(arrayList.get("check_order"));
			list2.add(arrayList.get("pay_amount"));
			list2.add(arrayList.get("recipt_amount"));
			String check_result = arrayList.get("check_result").toString();
			if("0".equals(check_result)){
				check_result="未对账";
			}else if("1".equals(check_result)){
				check_result="对账相符";
			}else if("2".equals(check_result)){
				check_result="稽查";
			}else if("3".equals(check_result)){
				check_result="合并未对账";
			}else if("4".equals(check_result)){
				check_result="合并对账符合";
			}else if("5".equals(check_result)){
				check_result="合并待稽查";
			}
			list2.add(check_result);
			list2.add(arrayList.get("store_name"));
			list2.add(arrayList.get("eshop_name"));
			list2.add(arrayList.get("self"));
			list2.add("".equals(arrayList.get("provider_name"))||arrayList.get("provider_name")==null?"无":arrayList.get("provider_name"));
			list2.add(arrayList.get("pay_platform"));
			String fund_type1 = arrayList.get("fund_type").toString();
			if("1".equals(fund_type1)){
				fund_type1="收款";
			}else if("2".equals(fund_type1)){
				fund_type1="退款";
			}
			list2.add(fund_type1);
			result.add(list2);
		}
		return result;
	}
	
	private String getTimeStamp() { 
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String str = sdf.format(date);
    	return str;
    }
}
