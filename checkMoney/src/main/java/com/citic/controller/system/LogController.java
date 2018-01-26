package com.citic.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.controller.index.BaseController;
import com.citic.entity.LogFormMap;
import com.citic.mapper.LogMapper;
import com.citic.plugin.PageView;
import com.citic.util.Common;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	@Autowired
	private LogMapper logMapper;

	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/log/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		LogFormMap logFormMap = getFormMap(LogFormMap.class);
		String order = "";
		if(Common.isNotEmpty(column)){
			order = " order by "+column+" "+sort;
		}else{
			order = " order by opertime desc";
		}
		String str=logFormMap.getStr("accountname")==null?"":logFormMap.getStr("accountname");
		logFormMap.put("accountname", "%"+str+"%");
		logFormMap.put("orderby", order);
		logFormMap=toFormMap(logFormMap, pageNow, pageSize,logFormMap.getStr("orderby"));
        pageView.setRecords(logMapper.findByPage(logFormMap));
		return pageView;
	}
}