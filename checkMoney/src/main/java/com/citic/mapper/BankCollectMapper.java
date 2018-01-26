package com.citic.mapper;

import java.util.List;

import com.citic.entity.BankCollectFromMap;

public interface BankCollectMapper extends BaseMapper {

	void updateEntityToEntityData();

	void updateChkResult();

	List<BankCollectFromMap> getClosingDate();

}
