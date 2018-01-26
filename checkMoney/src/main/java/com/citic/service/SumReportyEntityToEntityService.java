package com.citic.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface SumReportyEntityToEntityService {

	void exportBankDetailExcel(HttpServletResponse response, Map<String, Object> hm);

	void exportBankCollectExcel(HttpServletResponse response, Map<String, Object> hm);

}
