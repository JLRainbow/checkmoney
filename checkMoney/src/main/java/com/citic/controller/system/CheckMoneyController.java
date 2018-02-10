package com.citic.controller.system;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.citic.annotation.SystemLog;
import com.citic.controller.index.BaseController;
import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.entity.ChannelManagementFormMap;
import com.citic.mapper.AccountPaymentChkMapper;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.mapper.ChannelManagementMapper;
import com.citic.plugin.PageView;
import com.citic.service.CheckMoneyService;
import com.citic.util.Common;
import com.citic.util.ExcelUtil1;

/**
 * 
 * 对账处理（虚虚对账）
 */
@Controller
@RequestMapping("/check_money")
public class CheckMoneyController extends BaseController {

	@Autowired
	private CheckMoneyService checkMoneyService;
	@Autowired
	private ChannelManagementMapper channelManagementMapper;
	@Autowired
	private AccountPaymentChkMapper accountPaymentChkMapper;
	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;

	@RequestMapping("/channel_management")
	public String payChannel(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/checkMoney/channel_management";
	}

	@ResponseBody
	@RequestMapping("/channelManagementFindByPage")
	public PageView channelManagementFindByPage(String pageNow, String pageSize, String column, String sort)
			throws Exception {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		channelManagementFormMap = toFormMap(channelManagementFormMap, pageNow, pageSize,
				channelManagementFormMap.getStr("orderby"));

		channelManagementFormMap.put("column", column);
		channelManagementFormMap.put("sort", sort);
		String str = channelManagementFormMap.getStr("channel_name") == null ? ""
				: channelManagementFormMap.getStr("channel_name");
		channelManagementFormMap.put("channel_name", "%" + str + "%");
		pageView.setRecords(channelManagementMapper.findByPage(channelManagementFormMap));
		return pageView;
	}

	@RequestMapping("/check_money")
	public String checkMoney(Model model) throws Exception {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		channelManagementFormMap.put("where", "where status = 0 and channel_type = 1");
		// 查找支付渠道列表，用于前台下拉列表展示
		List<ChannelManagementFormMap> payList = channelManagementMapper.findByWhere(channelManagementFormMap);
		channelManagementFormMap.put("where", "where status = 0 and channel_type = 2");
		// 查找收款渠道列表，用于前台下拉列表展示
		List<ChannelManagementFormMap> receiptList = channelManagementMapper.findByWhere(channelManagementFormMap);
		model.addAttribute("payList", payList);
		model.addAttribute("receiptList", receiptList);
		return Common.BACKGROUND_PATH + "/system/checkMoney/check_money";
	}

	@RequestMapping("/toAddPayWay")
	public String toAddPayWay(Model model) throws Exception {

		return Common.BACKGROUND_PATH + "/system/checkMoney/addChannelManagement";
	}

	/**
	 * 添加渠道管理
	 */
	@ResponseBody
	@RequestMapping(value = "/addChannelManagement", method = RequestMethod.POST)
	@SystemLog(module = "对账系统设置", methods = "财务渠道设置-添加渠道管理") // 记录操作日志
	public String addPayWay() throws Exception {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);

		String channel_name = (String) channelManagementFormMap.get("channel_name");
		Date update_time = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		channelManagementFormMap.put("update_time", dateFormat.format(update_time));
		channelManagementFormMap.put("where", "where channel_name = '" + channel_name + "'");
		List<ChannelManagementFormMap> list = channelManagementMapper.findByWhere(channelManagementFormMap);
		if (list.size() == 0) {

			channelManagementMapper.addEntity(channelManagementFormMap);
			return "success";
		} else {
			return "isExist";
		}
	}

	/**
	 * 修改渠道管理
	 */
	@ResponseBody
	@RequestMapping(value = "/updateChannelManagement", method = RequestMethod.POST)
	@SystemLog(module = "对账系统设置", methods = "财务渠道设置-修改渠道管理") // 记录操作日志
	public String updateChannelManagement() throws Exception {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		channelManagementMapper.editEntity(channelManagementFormMap);
		return "success";
	}

	/**
	 * 删除渠道管理
	 */
	@ResponseBody
	@RequestMapping(value = "/delChannel", method = RequestMethod.POST)
	@SystemLog(module = "对账系统设置", methods = "财务渠道设置-删除渠道管理") // 记录操作日志
	public String delChannel() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			channelManagementMapper.deleteByAttribute("id", id, ChannelManagementFormMap.class);
		}
		return "success";
	}

	/**
	 * 获取数据类型
	 */
	@ResponseBody
	@RequestMapping(value = "/getDataTypeByChannelName", method = RequestMethod.POST)
	public String getDataTypeByChannelName() throws Exception {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		String channel_id = (String) channelManagementFormMap.get("channel_id");
		channelManagementFormMap.put("where", "where channel_id = '" + channel_id + "'");
		List<ChannelManagementFormMap> list = channelManagementMapper.findByWhere(channelManagementFormMap);
		String dataType = "";
		if (list.size() != 0) {
			Integer data_type = (Integer) list.get(0).get("data_type");
			switch (data_type) {
			case 1:
				return dataType = "CSV文件";
			case 2:
				return dataType = "DB";
			case 3:
				return dataType = "Excel";
			}
		}
		return dataType;
	}

	/**
	 * 是否存在渠道名称
	 */
	@ResponseBody
	@RequestMapping(value = "/isExistByChannelName", method = RequestMethod.POST)
	public boolean isExistByChannelName() throws Exception {
		ChannelManagementFormMap channelManagementFormMap = getFormMap(ChannelManagementFormMap.class);
		String channel_name = (String) channelManagementFormMap.get("channel_name");
		channelManagementFormMap.put("where", "where channel_name = '" + channel_name + "'");
		List<ChannelManagementFormMap> list = channelManagementMapper.findByWhere(channelManagementFormMap);
		if (list.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 将支付文件数据导入数据库
	 */
	@ResponseBody
	@RequestMapping("/importFileLoadData")
	@SystemLog(module = "财务对账业务", methods = "财务对账处理（虚虚对账）-支付渠道导入") // 记录操作日志
	public synchronized HashMap<String, Object> importFileLoadData(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "payWay") String payWay, HttpServletResponse response) throws Exception, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> importCsv = null;
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				InputStream inputStream = file.getInputStream();
				importCsv = checkMoneyService.importFileLoadData(response, inputStream, payWay);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
				return map;
			}

			return importCsv;
		}
	}

	/**
	 * 导入平台数据Excel
	 */

	@ResponseBody
	@RequestMapping("/impReceiptData")
	@SystemLog(module = "财务对账业务", methods = "财务对账处理（虚虚对账）-平台数据Excel导入") // 记录操作日志
	public synchronized HashMap<String, Object> platformData(@RequestParam(value = "receiptData") MultipartFile file,
			@RequestParam(value = "receiptWay") String receiptWay) throws Exception, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				String filename = file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				ArrayList<ArrayList<Object>> result = ExcelUtil1.readExcel(inputStream, filename);
				map = checkMoneyService.impPlatformData(result, receiptWay);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
			}

			return map;
		}
	}

	/**
	 * 导入关系Excel
	 */
	@ResponseBody
	@RequestMapping("/impReceiptRelation")
	@SystemLog(module = "财务对账业务", methods = "财务对账处理（虚虚对账）-关系数据Excel导入") // 记录操作日志
	public synchronized HashMap<String, Object> impPlatformRelation(
			@RequestParam(value = "receiptRelation") MultipartFile file,
			@RequestParam(value = "receiptWay") String receiptWay) throws Exception, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				String filename = file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				long starTime = System.currentTimeMillis();
				ArrayList<ArrayList<Object>> result = ExcelUtil1.readExcel(inputStream, filename);
				checkMoneyService.impPlatformRelation(result, receiptWay);
				long endTime = System.currentTimeMillis();
				System.out.println("导入平台关系数据用时======》" + (endTime - starTime));
				map.put("success", "导入成功");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
			}
			return map;
		}
	}

	/**
	 * 对账处理
	 */
	@ResponseBody
	@RequestMapping("/chkMoney")
	@SystemLog(module = "财务对账业务", methods = "财务对账处理（虚虚对账）-对账处理") // 记录操作日志
	public synchronized HashMap<String, Object> chkMoney(@RequestParam(value = "chkPayWay") String chkPayWay,
			@RequestParam(value = "chkReceiptWay") String chkReceiptWay,
			@RequestParam(value = "startTimeChkMoney") String startTimeChkMoney,
			@RequestParam(value = "endTimeChkMoney") String endTimeChkMoney) {
		Map<String, Object> m = new HashMap<String, Object>();
		String[] split = chkPayWay.split("/");
		List<String> list = new ArrayList<String>();
		for (String string : split) {
			list.add(string);
			if (string.equals("微信")) {
				list.add("微信扫码");
				list.add("微信公众号");
			}
			if (string.equals("支付宝")) {
				list.add("支付宝扫码");
			}
		}
		m.put("list", list);
		m.put("chkReceiptWay", chkReceiptWay);
		startTimeChkMoney += " 00:00:00";
		endTimeChkMoney += " 23:59:59";
		m.put("startTimeChkMoney", startTimeChkMoney);
		m.put("endTimeChkMoney", endTimeChkMoney);
		checkMoneyService.chkMoney(m);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("success", "对账结束");
		return map;
	}

	/**
	 * 获取未对账数目
	 */
	@ResponseBody
	@RequestMapping("/getNoChkNum")
	public HashMap<String, Object> getNoChkNum() {
		HashMap<String, Object> payChkNumMap = checkMoneyService.getNoChkNum();
		return payChkNumMap;
	}

	@RequestMapping("/queryDetailsById")
	public String queryDetailsById(Model model, @RequestParam(value = "id") String id) throws Exception {
		ChannelManagementFormMap channelManagementFormMap = channelManagementMapper.findbyFrist("id", id,
				ChannelManagementFormMap.class);
		model.addAttribute("channelManagementFormMap", channelManagementFormMap);
		return Common.BACKGROUND_PATH + "/system/checkMoney/detailsForChannelManagement";

	}

	@RequestMapping("/toUpdateChannelManagement")
	public String toUpdateChannelManagement(Model model, @RequestParam(value = "id") String id) throws Exception {
		ChannelManagementFormMap channelManagementFormMap = channelManagementMapper.findbyFrist("id", id,
				ChannelManagementFormMap.class);
		model.addAttribute("channelManagementFormMap", channelManagementFormMap);
		return Common.BACKGROUND_PATH + "/system/checkMoney/updateChannelManagement";

	}

	/**
	 * 导入钱包明细Excel文件
	 */

	@ResponseBody
	@RequestMapping("/importWalletFile")
	@SystemLog(module = "财务对账业务", methods = "财务对账处理（虚虚对账）-钱包明细Excel导入") // 记录操作日志
	public synchronized HashMap<String, Object> importWalletFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "payWay") String payWay) throws Exception, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (file.isEmpty()) {
			map.put("isEmpty", "导入文件内容为空");
			return map;
		} else {
			try {
				String filename = file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				ArrayList<ArrayList<Object>> result = ExcelUtil1.readExcel(inputStream, filename);
				map = checkMoneyService.importWalletFile(result, payWay);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("importError", "导入文件异常,请检查关键字位置配置是否正确或者导入文件是否正确");
			}

			return map;
		}
	}

	/**
	 * 获取支付未对账数目
	 */
	@ResponseBody
	@RequestMapping("/getPayNoChkNum")
	public HashMap<String, Object> getPayNoChkNum(@RequestParam(value = "monthPay") String monthPay) {
		HashMap<String, Object> payChkNumMap = checkMoneyService.getPayNoChkNum(monthPay);
		return payChkNumMap;
	}

	/**
	 * 获取平台未对账数目
	 */
	@ResponseBody
	@RequestMapping("/getReceiptNoChkNum")
	public HashMap<String, Object> getReceiptNoChkNum(@RequestParam(value = "monthReceipt") String monthReceipt) {
		HashMap<String, Object> receiptChkNumMap = checkMoneyService.getReceiptNoChkNum(monthReceipt);
		return receiptChkNumMap;
	}

	/**
	 * 支付文件下载自动导入
	 */
	@ResponseBody
	@RequestMapping("/payFileAutoImport")
	@SystemLog(module = "财务对账业务", methods = "支付文件下载自动导入") // 记录操作日志
	public synchronized Map<String, Object> payFileAutoImport(@RequestParam(value = "payWay") String payWay,
			@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
		Map<String, Object> resultMap = checkMoneyService.payFileAutoImport(payWay, startTime, endTime);
		return resultMap;
	}

	/**
	 * 通过某个流水号对账
	 */
	@ResponseBody
	@RequestMapping("/chkMoneyByRelationId")
	@SystemLog(module = "财务对账业务", methods = "财务对账处理（虚虚对账）-通过流水号对账") // 记录操作日志
	public synchronized Map<String, Object> chkMoneyByRelationId(
			@RequestParam(value = "relationId") String relationId, @RequestParam(value = "fundType") String fundType,
			@RequestParam(value = "chanelType") String chanelType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断数据库有没有这条数据 有的话进行对账
		if (chanelType.equals("支付渠道")) {
			AccountReceiptChkFormMap accountReceiptChkFormMap = new AccountReceiptChkFormMap();
			accountReceiptChkFormMap.put("where", "where check_result = 0 and relation_id = '"+relationId+"' and fund_type = "+fundType+" ");
			List<AccountReceiptChkFormMap> list = accountReceiptChkMapper.findByWhere(accountReceiptChkFormMap);
			if(list.size()==0){
				map.put("isNull", "未找到该条匹配的数据进行对账");
				return map; 
			}
		}
		if (chanelType.equals("收款渠道")) {
			AccountPaymentChkFormMap accountPaymentChkFormMap = new AccountPaymentChkFormMap();
			accountPaymentChkFormMap.put("where", "where check_result = 0 and check_order = '"+relationId+"' and fund_type = "+fundType+" ");
			List<AccountPaymentChkFormMap> list = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
			if(list.size()==0){
				map.put("isNull", "未找到该条匹配的数据进行对账");
				return map; 
			}
		}
		checkMoneyService.chkMoneyByRelationId(relationId);
		map.put("success", "对账结束");
		return map;
	}
}
