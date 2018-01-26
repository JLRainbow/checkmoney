package com.citic.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.entity.BankCollectFromMap;
import com.citic.entity.BankDetailFromMap;
import com.citic.mapper.BankCollectMapper;
import com.citic.mapper.BankDetailMapper;
import com.citic.service.SumReportyEntityToEntityService;
import com.citic.util.ExcelUtil1;
@Service
public class SumReportyEntityToEntityServiceImpl implements SumReportyEntityToEntityService {

	@Autowired
	private BankDetailMapper bankDetailMapper;
	@Autowired
	private	BankCollectMapper bankCollectMapper;
	
	public void exportBankDetailExcel(HttpServletResponse response, Map<String, Object> hm) {
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		String fileName=hm.get("reportType").toString();
		String startDate=hm.get("startDate").toString();
		String endDate=hm.get("endDate").toString();
		//收款导出Excel逻辑
		BankDetailFromMap bankDetailFromMap = new BankDetailFromMap();
		String sql=" WHERE borrow_amount IS NULL AND wallet_amount IS NULL "
				+ "AND trade_date >= '"+startDate+"' AND trade_date <= '"+endDate+"' ORDER BY trade_date";
		
		bankDetailFromMap.put("where", sql);
		List<BankDetailFromMap> list = bankDetailMapper.findByWhere(bankDetailFromMap);
		//设置表头列
		ArrayList<Object> list3=new ArrayList<Object>();
		list3.add("交易时间");
		list3.add("账号");
		list3.add("贷");
		list3.add("银行(140)汇总金额");
		list3.add("账户余额");
		list3.add("对账结果");
		list3.add("对方账户");
		list3.add("对方账户名称");
		list3.add("交易流水号");
		list3.add("摘要");
		list3.add("用途");
		
		result.add(list3);
		for (BankDetailFromMap arrayList : list) {
			ArrayList<Object> list2=new ArrayList<Object>();
			list2.add(arrayList.get("trade_date"));
			list2.add(arrayList.get("account"));
			list2.add(arrayList.get("loan_amount"));
			list2.add(arrayList.get("collect_amount"));
			list2.add(arrayList.get("surplus_amount"));
			String check_result = arrayList.get("check_result").toString();
			if("0".equals(check_result)){
				check_result="未对账";
			}else if("1".equals(check_result)){
				check_result="对账相符";
			}else if("2".equals(check_result)){
				check_result="稽查";
			}
			list2.add(check_result);
			list2.add(arrayList.get("opposite_account"));
			list2.add(arrayList.get("opposite_account_name"));
			list2.add(arrayList.get("check_order"));
			list2.add(arrayList.get("abstract"));
			list2.add(arrayList.get("purpose"));
			
			result.add(list2);
		}
		fileName+=this.getTimeStamp();
		try {
			ExcelUtil1.ExpExs(result, fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void exportBankCollectExcel(HttpServletResponse response, Map<String, Object> hm) {
		ArrayList<ArrayList<Object>> result =new ArrayList<ArrayList<Object>>();
		String fileName=hm.get("reportType").toString();
		String startDate=hm.get("startDate").toString();
		String endDate=hm.get("endDate").toString();
		//收款导出Excel逻辑
		BankCollectFromMap bankCollectFromMap = new BankCollectFromMap();
		String sql=" WHERE trade_date >= '"+startDate+"' AND trade_date <= '"+endDate+"' ORDER BY trade_date";
		
		bankCollectFromMap.put("where", sql);
		List<BankCollectFromMap> list = bankCollectMapper.findByWhere(bankCollectFromMap);
		//设置表头列
		ArrayList<Object> list3=new ArrayList<Object>();
		list3.add("交易时间");
		list3.add("账号");
		list3.add("借");
		list3.add("银行(315)汇总金额");
		list3.add("账户余额");
		list3.add("对账结果");
		list3.add("对方账户");
		list3.add("对方账户名称");
		list3.add("交易流水号");
		list3.add("摘要");
		list3.add("用途");
		
		result.add(list3);
		for (BankCollectFromMap arrayList : list) {
			ArrayList<Object> list2=new ArrayList<Object>();
			list2.add(arrayList.get("trade_date"));
			list2.add(arrayList.get("account"));
			list2.add(arrayList.get("borrow_amount"));
			list2.add(arrayList.get("loan_amount"));
			list2.add(arrayList.get("surplus_amount"));
			String check_result = arrayList.get("check_result").toString();
			if("0".equals(check_result)){
				check_result="未对账";
			}else if("1".equals(check_result)){
				check_result="对账相符";
			}else if("2".equals(check_result)){
				check_result="稽查";
			}
			list2.add(check_result);
			list2.add(arrayList.get("opposite_account"));
			list2.add(arrayList.get("opposite_account_name"));
			list2.add(arrayList.get("check_order"));
			list2.add(arrayList.get("abstract"));
			list2.add(arrayList.get("purpose"));
			
			result.add(list2);
		}
		fileName+=this.getTimeStamp();
		try {
			ExcelUtil1.ExpExs(result, fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private String getTimeStamp() { 
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String str = sdf.format(date);
    	return str;
    }
	
}
