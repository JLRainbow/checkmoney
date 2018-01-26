package com.citic.controller.system;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.annotation.SystemLog;
import com.citic.controller.index.BaseController;
import com.citic.entity.BudgettypeFormMap;
import com.citic.mapper.BudgettypeMapper;
import com.citic.plugin.PageView;
import com.citic.util.Common;
import com.citic.util.JSONUtil;
import com.citic.util.POIUtils;

@Controller
@RequestMapping("/budgettype")
public class BudgettypeController extends BaseController {
	@Autowired
	private BudgettypeMapper budgettypeMapper;
	
	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/budgettype/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		BudgettypeFormMap budgettypeFormMap = getFormMap(BudgettypeFormMap.class);
		budgettypeFormMap=toFormMap(budgettypeFormMap, pageNow, pageSize,budgettypeFormMap.getStr("orderby"));
		budgettypeFormMap.put("column", column);
		budgettypeFormMap.put("sort", sort);
        pageView.setRecords(budgettypeMapper.findBudgettypePage(budgettypeFormMap));
        return pageView;
	}
	
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "收支类型列表";
		BudgettypeFormMap budgettypeFormMap = findHasHMap(BudgettypeFormMap.class);
		
		String exportData = budgettypeFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JSONUtil.parseJSONList(exportData);

		List<BudgettypeFormMap> lis = budgettypeMapper.findBudgettypePage(budgettypeFormMap);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}

	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/budgettype/add";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="收支类型管理-新增收支类型")//凡需要处理业务逻辑的.都需要记录操作日志
	public String addEntity() throws Exception {
		BudgettypeFormMap budgettypeFormMap = getFormMap(BudgettypeFormMap.class);
		budgettypeMapper.addEntity(budgettypeFormMap);
		return "success";
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="收支类型管理-删除收支类型")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			budgettypeMapper.deleteByAttribute("id", id, BudgettypeFormMap.class);
		}
		return "success";
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			model.addAttribute("budgettype", budgettypeMapper.findbyFrist("id", id, BudgettypeFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/budgettype/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="收支类型管理-修改收支类型")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity() throws Exception {
		BudgettypeFormMap budgettypeFormMap = getFormMap(BudgettypeFormMap.class);
		budgettypeMapper.editEntity(budgettypeFormMap);
		return "success";
	}
	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("isExistBudgettypekey")
	@ResponseBody
	public boolean isExistBudgettypekey(String budgettypekey) {
		BudgettypeFormMap budgettype = budgettypeMapper.findbyFrist("budgettypekey", budgettypekey, BudgettypeFormMap.class);
		if (budgettype == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("isExistBudgettypename")
	@ResponseBody
	public boolean isExistBudgettypename(String name) {
		BudgettypeFormMap budgettype = budgettypeMapper.findbyFrist("name", name, BudgettypeFormMap.class);
		if (budgettype == null) {
			return true;
		} else {
			return false;
		}
	}
}