package com.citic.mapper;

import java.util.List;

import com.citic.entity.BudgettypeFormMap;

public interface BudgettypeMapper extends BaseMapper{
	public List<BudgettypeFormMap> findBudgettypePage(BudgettypeFormMap budgettypeFormMap);
}
