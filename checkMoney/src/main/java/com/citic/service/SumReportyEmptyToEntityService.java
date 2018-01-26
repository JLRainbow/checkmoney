package com.citic.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface SumReportyEmptyToEntityService {

	void exportWalletDetailExcel(HttpServletResponse response, Map<String, Object> hm);

	void exportBankDetailExcel(HttpServletResponse response, Map<String, Object> hm);

}
