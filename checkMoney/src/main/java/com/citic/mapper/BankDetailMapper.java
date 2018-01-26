package com.citic.mapper;

import java.util.List;

import com.citic.entity.BankDetailFromMap;

public interface BankDetailMapper extends BaseMapper {

	void updateMatchData();

	void updateEmptyToEntityChkResult();

	void updateEntityToEntityData();

	void updateEntityToEntityChkResult();

	List<BankDetailFromMap> getCashClosingDate();

	List<BankDetailFromMap> getCollectClosingDate();

}
