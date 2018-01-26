package com.citic.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.entity.SupplierInfoMap;
import com.citic.mapper.AccountPaymentChkMapper;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.mapper.CkMoneySysMapper;
import com.citic.service.CkMoneySysService;
@Service
public class CkMoneySysServiceImpl implements CkMoneySysService {
	@Autowired
	private AccountPaymentChkMapper accountPaymentChkMapper;

	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;

	@Autowired
	private CkMoneySysMapper ckMoneySysMapper;
	
	public HashMap<String, Object> getPlatformDate() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		List<AccountReceiptChkFormMap> closingDateList = accountReceiptChkMapper.getClosingDate();
		String receiptClosingDate = closingDateList.get(0)==null?"XXXX年XX月XX日":(String) closingDateList.get(0).get("receipt_date");
		map.put("receiptClosingDate", receiptClosingDate);
		List<AccountReceiptChkFormMap> minDateList = accountReceiptChkMapper.getMinDate();
		String minDate = minDateList.get(0)==null?"XXXX年XX月XX日":(String)minDateList.get(0).get("receipt_date");
		map.put("receiptClosingDate", receiptClosingDate);
		map.put("minDate", minDate);
		return map;
	}

	public HashMap<String, Object> getPayDate() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		List<AccountPaymentChkFormMap> closingDateList = accountPaymentChkMapper.getClosingDate();
		String payClosingDate = closingDateList.get(0)==null?"XXXX年XX月XX日":(String)closingDateList.get(0).get("pay_date");
		List<AccountPaymentChkFormMap> minDateList = accountPaymentChkMapper.getMinDate();
		String minDate = minDateList.get(0)==null?"XXXX年XX月XX日":(String)minDateList.get(0).get("pay_date");
		map.put("payClosingDate", payClosingDate);
		map.put("minDate", minDate);
		return map;
	}

	public HashMap<String, Object> importSupplierFile(ArrayList<ArrayList<Object>> result) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		SupplierInfoMap supplierInfoMap = null;
	    for(int i = 0 ;i < result.size() ;i++){  
		      for(int j = 0;j<result.get(i).size(); j++){
		    	  if(i>=1){
		    		  if(j==1){
		    			  String a1 = result.get(i).get(1).toString();
		    			  String a2 = result.get(i).get(2).toString();
		    			  String a3 = result.get(i).get(3).toString();
		    			  String a4 = result.get(i).get(4).toString();
		    			  String a5 = result.get(i).get(5).toString();
		    			  String a6 = result.get(i).get(6).toString();
		    			  String a7 = result.get(i).get(7).toString();
		    			  String a8 = result.get(i).get(8).toString();
		    			  String a9 = result.get(i).get(9).toString();
		    			  String a10 = result.get(i).get(10).toString();
		    			  String a11 = result.get(i).get(11).toString();
		    			  String a12 = result.get(i).get(12).toString();
		    			  String a13 = result.get(i).get(13).toString();
		    			  String a14 = result.get(i).get(14).toString();
		    			  System.out.println(a1+" "+a2+" "+a3+" "+a4+" "+a5+" "+a6+" "+a7+" "+a8+" "+a9+" "+a10+" "+a11+" "+a12+" "+a13+" "+a14);
		    			  supplierInfoMap = new SupplierInfoMap();
		    			  supplierInfoMap.put("eshop_id", a1);
		    			  supplierInfoMap.put("eshop_name", a2);
		    			  supplierInfoMap.put("career_group", a3);
		    			  supplierInfoMap.put("channel", a4);
		    			  supplierInfoMap.put("seller_tel", a5);
		    			  supplierInfoMap.put("contractor", a6);
		    			  supplierInfoMap.put("opening_company_name", a7);
		    			  supplierInfoMap.put("opening_bank_name", a8);
		    			  supplierInfoMap.put("bank_location", a9);
		    			  supplierInfoMap.put("account", a10);
		    			  supplierInfoMap.put("archive", a11);
		    			  supplierInfoMap.put("archive_apply", a12);
		    			  supplierInfoMap.put("archive_error_reason", a13);
		    			  supplierInfoMap.put("self", a14);
		    			  Date date = new Date();
		    		      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    		      String update_time = sdf.format(date);
		    			  supplierInfoMap.put("update_time", update_time);
		    			  supplierInfoMap.put("where", "where eshop_id = '"+a1+"'");
		    			  List<SupplierInfoMap> list = ckMoneySysMapper.findByWhere(supplierInfoMap);
		    			  if(list.size() == 0){
								try {
									ckMoneySysMapper.addEntity(supplierInfoMap);
									map.put("success", "导入完成");
								} catch (Exception e) {
									e.printStackTrace();
									map.put("error", "导入失败");
								}
							}else{
								try {
									supplierInfoMap.put("id", list.get(0).get("id").toString());
									ckMoneySysMapper.editEntity(supplierInfoMap);
									map.put("success", "导入完成");
								} catch (Exception e) {
									e.printStackTrace();
									map.put("error", "导入失败");
								}
							}
		    			  
		    		  }
		    	  }
		      }  
	    } 
		return map;
	}

	public ArrayList<ArrayList<Object>> backupsAndDelForReceipt(List<AccountReceiptChkFormMap> list) {
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list2 = null;
		ArrayList<Object> idList = new ArrayList<Object>();
		ArrayList<Object> list3=new ArrayList<Object>();
		list3.add("id");
		list3.add("channel_name");
		list3.add("channel_id");
		list3.add("relation_id");
		list3.add("source");
		list3.add("fund_type");
		list3.add("check_order");
		list3.add("pay_amount");
		list3.add("recipt_amount");
		list3.add("check_result");
		list3.add("status");
		list3.add("receipt_date");
		list3.add("store_name");
		list3.add("eshop_name");
		list3.add("provider_name");
		list3.add("self");
		list3.add("pay_platform");
		list3.add("comment");
		list3.add("eshop_id");
		result.add(list3);
		for (AccountReceiptChkFormMap arrayList : list) {
			list2=new ArrayList<Object>();
			list2.add(arrayList.get("id"));
			list2.add(arrayList.get("channel_name"));
			list2.add(arrayList.get("channel_id"));
			list2.add(arrayList.get("relation_id"));
			list2.add(arrayList.get("source"));
			list2.add(arrayList.get("fund_type"));
			list2.add(arrayList.get("check_order"));
			list2.add(arrayList.get("pay_amount"));
			list2.add(arrayList.get("recipt_amount"));
			list2.add(arrayList.get("check_result"));
			list2.add(arrayList.get("status"));
			list2.add(arrayList.get("receipt_date"));
			list2.add(arrayList.get("store_name"));
			list2.add(arrayList.get("eshop_name"));
			list2.add(arrayList.get("provider_name"));
			list2.add(arrayList.get("self"));
			list2.add(arrayList.get("pay_platform"));
			list2.add(arrayList.get("comment"));
			list2.add(arrayList.get("eshop_id"));
			result.add(list2);
			idList.add(arrayList.get("id"));
		}
		//批量删除
		accountReceiptChkMapper.deleteAll(idList);
		return result;
	}

	public ArrayList<ArrayList<Object>> backupsAndDelForPay(List<AccountPaymentChkFormMap> list) {
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list2 = null;
		ArrayList<Object> idList = new ArrayList<Object>();
		ArrayList<Object> list3=new ArrayList<Object>();
		list3.add("id");
		list3.add("channel_name");
		list3.add("channel_id");
		list3.add("relation_id");
		list3.add("source");
		list3.add("fund_type");
		list3.add("check_order");
		list3.add("pay_amount");
		list3.add("recipt_amount");
		list3.add("check_result");
		list3.add("status");
		list3.add("receipt_date");
		list3.add("store_name");
		list3.add("eshop_name");
		list3.add("provider_name");
		list3.add("self");
		list3.add("pay_platform");
		list3.add("comment");
		list3.add("eshop_id");
		result.add(list3);
		for (AccountPaymentChkFormMap arrayList : list) {
			list2=new ArrayList<Object>();
			list2.add(arrayList.get("id"));
			list2.add(arrayList.get("channel_name"));
			list2.add(arrayList.get("channel_id"));
			list2.add(arrayList.get("fund_type"));
			list2.add(arrayList.get("check_order"));
			list2.add(arrayList.get("pay_amount"));
			list2.add(arrayList.get("recipt_amount"));
			list2.add(arrayList.get("check_result"));
			list2.add(arrayList.get("pay_date"));
			list2.add(arrayList.get("store_name"));
			list2.add(arrayList.get("eshop_name"));
			list2.add(arrayList.get("provider_name"));
			list2.add(arrayList.get("self"));
			list2.add(arrayList.get("pay_platform"));
			list2.add(arrayList.get("comment"));
			result.add(list2);
			idList.add(arrayList.get("id"));
		}
		//批量删除
		accountPaymentChkMapper.deleteAll(idList);
		return result;
	}

}
