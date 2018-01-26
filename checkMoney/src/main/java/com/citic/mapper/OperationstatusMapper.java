package com.citic.mapper;

import java.util.List;

import com.citic.bean.po.Operationstatus;

public interface OperationstatusMapper extends BaseMapper{
	
	public List<Operationstatus> findOperstatusList(Operationstatus operationstatus);
	public Byte findOperstatusByAccnameAndBatchcode(Operationstatus operationstatus);
	
	public void insertOperstatus(Operationstatus operationstatus);
	
	public void updateOperstatusByTrandateAndBatchcode(Operationstatus operationstatus);

}
