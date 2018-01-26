package com.citic.service;

import java.util.ArrayList;
import java.util.HashMap;


public interface CkMoneyWalletBankService {

	HashMap<String, Object> importWalletFile(ArrayList<ArrayList<Object>> result, String fileType);

	HashMap<String, Object> importBankDetailFile(ArrayList<ArrayList<Object>> result, String fileType);

	HashMap<String, Object> importBankCollectFile(ArrayList<ArrayList<Object>> result, String fileType);

	void chkMoneyEmptyToEntity();

	void chkMoneyEntityToEntity();

	HashMap<String, Object> getNoChkNum();




}
