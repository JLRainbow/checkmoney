package com.citic.mapper;

import java.util.List;

import com.citic.entity.CompanyFormMap;
import com.citic.entity.RoleFormMap;

public interface CompanyMapper extends BaseMapper{
	
	public List<CompanyFormMap> findCompanyPage(CompanyFormMap companyFormMap);
	
	public List<CompanyFormMap> seletUserCompany(CompanyFormMap map);
}
