package com.citic.mapper;

import java.util.List;

import com.citic.entity.WalletAccountFromMap;

public interface WalletAccountMapper extends BaseMapper {

	void updateMatchData();

	void updateChkResult();

	List<WalletAccountFromMap> getClosingDate();

}
