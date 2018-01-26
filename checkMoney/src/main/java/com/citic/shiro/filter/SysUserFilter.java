package com.citic.shiro.filter;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import com.citic.entity.UserFormMap;
import com.citic.mapper.UserMapper;


public class SysUserFilter extends PathMatchingFilter {

	@Inject
	private UserMapper userMapper;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        UserFormMap userFormMap = new UserFormMap();
		userFormMap.put("accountname", "" + username + "");
        request.setAttribute("user", userMapper.findByNames(userFormMap));
        return true;
    }
}