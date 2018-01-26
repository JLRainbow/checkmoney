package com.citic.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.citic.bean.po.TempPo;
import com.citic.entity.AccountReceiptChkFormMap;

public interface AccountReceiptChkMapper extends BaseMapper{

	void updateMatchData(@Param("map")Map<String, Object> m);

	void updateChkResult(@Param("map")Map<String, Object> m);

	List<AccountReceiptChkFormMap> getClosingDate();

	void matchProviderName(TempPo tempPo);

	List<AccountReceiptChkFormMap> getMinDate();

	void deleteAll(ArrayList<Object> list2);

	void matchProviderName1();

	void updateMergePay(@Param("map")Map<String, Object> updateFormMap);



}
