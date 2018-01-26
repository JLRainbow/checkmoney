package com.citic.mapper;

import java.util.List;

import com.citic.bean.po.Wages;
import com.citic.bean.vo.WagesQueryVo;
import com.citic.entity.WagesFormMap;


public interface WagesMapper extends BaseMapper{
	
	public List<Wages> findAllWages(WagesQueryVo wagesQueryVo);
	
	public void updateWagesStatus(Wages wages);
	
}
