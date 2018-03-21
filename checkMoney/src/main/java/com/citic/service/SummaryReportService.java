package com.citic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.entity.WxGigtCardBuyRecordFormMap;
import com.citic.entity.WxWeBankFormMap;

public interface SummaryReportService {

	void exportExcel(HttpServletResponse response, Map<String, Object> hm);
	
	public ArrayList<ArrayList<Object>> impReceipForExcel(List<AccountReceiptChkFormMap> list);
	
	public ArrayList<ArrayList<Object>> impPayForExcel(List<AccountPaymentChkFormMap> list);

	ArrayList<ArrayList<Object>> exportWxWeBankForExcel(List<WxWeBankFormMap> list);

	ArrayList<ArrayList<Object>> exportWxGigtCardBuyRecordForExcel(List<WxGigtCardBuyRecordFormMap> list);
}
