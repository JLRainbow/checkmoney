package com.citic.mapper;

import java.util.List;

import com.citic.entity.UserFormMap;


public interface UserMapper extends BaseMapper{

	public List<UserFormMap> findUserPage(UserFormMap userFormMap);
	
}
