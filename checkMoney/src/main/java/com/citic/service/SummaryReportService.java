package com.citic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;

public interface SummaryReportService {

	void exportExcel(HttpServletResponse response, Map<String, Object> hm);
	
	public ArrayList<ArrayList<Object>> impReceipForExcel(List<AccountReceiptChkFormMap> list);
	
	public ArrayList<ArrayList<Object>> impPayForExcel(List<AccountPaymentChkFormMap> list);
}
