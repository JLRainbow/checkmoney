package com.citic.controller.system;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.annotation.SystemLog;
import com.citic.controller.index.BaseController;
import com.citic.entity.CompanyFormMap;
import com.citic.entity.ResUserFormMap;
import com.citic.entity.RoleFormMap;
import com.citic.entity.UserFormMap;
import com.citic.entity.UserGroupsFormMap;
import com.citic.exception.SystemException;
import com.citic.mapper.CompanyMapper;
import com.citic.plugin.PageView;
import com.citic.util.Common;
import com.citic.util.JSONUtil;
import com.citic.util.POIUtils;
import com.citic.util.PasswordHelper;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
	@Autowired
	private CompanyMapper companyMapper;
	
	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/company/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		CompanyFormMap companyFormMap = getFormMap(CompanyFormMap.class);
		companyFormMap=toFormMap(companyFormMap, pageNow, pageSize,companyFormMap.getStr("orderby"));
		companyFormMap.put("column", column);
		companyFormMap.put("sort", sort);
        pageView.setRecords(companyMapper.findCompanyPage(companyFormMap));
        return pageView;
	}
	
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "公司列表";
		CompanyFormMap companyFormMap = findHasHMap(CompanyFormMap.class);
		
		String exportData = companyFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JSONUtil.parseJSONList(exportData);

		List<CompanyFormMap> lis = companyMapper.findCompanyPage(companyFormMap);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}

	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/company/add";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="公司管理-新增公司")//凡需要处理业务逻辑的.都需要记录操作日志
	public String addEntity() throws Exception {
		CompanyFormMap companyFormMap = getFormMap(CompanyFormMap.class);
		companyMapper.addEntity(companyFormMap);
		return "success";
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="公司管理-删除公司")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			companyMapper.deleteByAttribute("id", id, CompanyFormMap.class);
		}
		return "success";
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			model.addAttribute("company", companyMapper.findbyFrist("id", id, CompanyFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/company/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="公司管理-修改公司")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity() throws Exception {
		CompanyFormMap companyFormMap = getFormMap(CompanyFormMap.class);
		companyMapper.editEntity(companyFormMap);
		return "success";
	}
	
	@RequestMapping("selectCompany")
	public String selectCompany(Model model) throws Exception {
		CompanyFormMap companyFormMap = getFormMap(CompanyFormMap.class);
		Object userId = companyFormMap.get("userId");
		if(null!=userId){
			List<CompanyFormMap> list = companyMapper.seletUserCompany(companyFormMap);
			String companyid = "";
			for (CompanyFormMap ml : list) {
				companyid += ml.get("id")+",";
			}
			companyid = Common.trimComma(companyid);
			model.addAttribute("txtCompanySelect", companyid);
			model.addAttribute("userCompany", list);
			if(StringUtils.isNotBlank(companyid)){
				companyFormMap.put("where", " where id not in ("+companyid+")");
			}
		}
		List<CompanyFormMap> companies = companyMapper.findByWhere(companyFormMap);
		model.addAttribute("company", companies);
		return Common.BACKGROUND_PATH + "/system/company/companySelect";
	}
	
	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("isExistCompanykey")
	@ResponseBody
	public boolean isExistCompanykey(String companykey) {
		CompanyFormMap company = companyMapper.findbyFrist("companykey", companykey, CompanyFormMap.class);
		if (company == null) {
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
	@RequestMapping("isExistCompanyname")
	@ResponseBody
	public boolean isExistCompanyname(String companyname) {
		CompanyFormMap company = companyMapper.findbyFrist("name", companyname, CompanyFormMap.class);
		if (company == null) {
			return true;
		} else {
			return false;
		}
	}
	
}