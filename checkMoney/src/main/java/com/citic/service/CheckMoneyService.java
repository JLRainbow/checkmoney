package com.citic.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.citic.bean.po.TempPo;

public interface CheckMoneyService {


	HashMap<String, Object> impPlatformData(ArrayList<ArrayList<Object>> result,String receiptWay) throws IOException;

	void impPlatformRelation(ArrayList<ArrayList<Object>> result, String receiptWay);

	void chkMoney(Map<String, Object> m);

	HashMap<String, Object> getNoChkNum();

	HashMap<String, Object> importWalletFile(ArrayList<ArrayList<Object>> result, String payWay) throws IOException;

	HashMap<String, Object> getPayNoChkNum(String monthPay);

	HashMap<String, Object> getReceiptNoChkNum(String monthReceipt);

	Map<String, Object> importFileLoadData(HttpServletResponse response, InputStream inputStream, String payWay) throws Exception;

	Map<String, Object> payFileAutoImport(String payWay, String startTime, String endTime);

	void chkMoneyByRelationId(String relationId);

	Map<String, Object> importWxWeBankFile(HttpServletResponse response, InputStream inputStream, String payWay) throws Exception;

	

}
