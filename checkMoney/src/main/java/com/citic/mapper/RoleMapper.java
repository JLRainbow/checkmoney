package com.citic.mapper;

import java.util.List;

import com.citic.entity.RoleFormMap;
import com.citic.entity.UserFormMap;

public interface RoleMapper extends BaseMapper{
	public List<RoleFormMap> seletUserRole(RoleFormMap map);
	
	public List<RoleFormMap> findRolePage(RoleFormMap roleFormMap);
	
	public void deleteById(RoleFormMap map);
}
