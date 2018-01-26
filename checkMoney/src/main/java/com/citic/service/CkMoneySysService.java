package com.citic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;

public interface CkMoneySysService {

	HashMap<String, Object> getPlatformDate();

	HashMap<String, Object> getPayDate();

	HashMap<String, Object> importSupplierFile(ArrayList<ArrayList<Object>> result);

	ArrayList<ArrayList<Object>> backupsAndDelForReceipt(List<AccountReceiptChkFormMap> list);

	ArrayList<ArrayList<Object>> backupsAndDelForPay(List<AccountPaymentChkFormMap> list);

}
