package com.citic.controller.system;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.controller.index.BaseController;
import com.citic.entity.UserLoginFormMap;
import com.citic.mapper.UserLoginMapper;
import com.citic.plugin.PageView;
import com.citic.util.Common;

@Controller
@RequestMapping("/userlogin")
public class UserLoginController extends BaseController {
	@Inject
	private UserLoginMapper userLoginMapper;

	@RequestMapping("listUI")
	public String listUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/userlogin/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(String pageNow,
			String pageSize) throws Exception {
		UserLoginFormMap userLoginFormMap = getFormMap(UserLoginFormMap.class);
		String order = " order by logintime desc";
		userLoginFormMap.put("orderby", order);
		String str=userLoginFormMap.getStr("accountname")==null?"":userLoginFormMap.getStr("accountname");
		userLoginFormMap.put("accountname", "%"+str+"%");
		userLoginFormMap=toFormMap(userLoginFormMap, pageNow, pageSize,userLoginFormMap.getStr("orderby"));
        pageView.setRecords(userLoginMapper.findByPage(userLoginFormMap));
		return pageView;
	}

}