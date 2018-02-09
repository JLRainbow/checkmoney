package com.citic.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.citic.bean.po.TempPo;
import com.citic.entity.AccountPaymentChkFormMap;

public interface AccountPaymentChkMapper extends BaseMapper{

	void updateChkResult(@Param("map")Map<String, Object> m);

	void updateMatchData(@Param("map")Map<String, Object> m);

	public List<AccountPaymentChkFormMap> getClosingDate();

	List<AccountPaymentChkFormMap> getMinDate();

	void deleteAll(ArrayList<Object> idList);

	void updateMatchDataByRelationId(@Param(value="relationId")String relationId);

	void updateChkResultByRelationId(@Param(value="relationId")String relationId);

}
