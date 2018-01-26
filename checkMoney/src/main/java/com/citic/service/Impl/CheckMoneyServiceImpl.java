package com.citic.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.citic.entity.AccountPaymentChkFormMap;
import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.entity.ChannelManagementFormMap;
import com.citic.entity.ConfigInf;
import com.citic.entity.ReceiptConfig;
import com.citic.entity.WalletAccountConfig;
import com.citic.factory.PayFileHandleFactory;
import com.citic.factory.inf.IPayFileHandle;
import com.citic.factory.load.DataLoadDB;
import com.citic.mapper.AccountPaymentChkMapper;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.mapper.ChannelManagementMapper;
import com.citic.service.CheckMoneyService;
import com.citic.util.CsvUtil;
import com.mysql.jdbc.Connection;

@Service
public class CheckMoneyServiceImpl implements CheckMoneyService {

	@Autowired
	private ChannelManagementMapper channelManagementMapper;

	@Autowired
	private AccountPaymentChkMapper accountPaymentChkMapper;

	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;

	public HashMap<String, Object> importFileLoadData(HttpServletResponse response, InputStream inputStream,
			String payWay) throws Exception {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 通过支付方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '" + payWay + "'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper
				.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf = (String) channelManagement.get("config_inf");
		ConfigInf configInf = JSON.parseObject(config_inf, ConfigInf.class);
		System.out.println(configInf.toString());

		CsvUtil csvUtil = new CsvUtil(inputStream);
		//获取处理后数据
		IPayFileHandle payFileHandleImpl = PayFileHandleFactory.getPayFileHandleImpl(configInf.getChannel_name());
		List<Object> dataList = payFileHandleImpl.getPayFileHandle(configInf, csvUtil);
		
		String sql = "LOAD DATA LOCAL INFILE 'xx.csv' " + "INTO TABLE t_account_payment_chk "
				+ "CHARACTER SET GBK " + "FIELDS TERMINATED by ',' " + "LINES TERMINATED by '\r\n' "
				+ "(check_order,pay_date,fund_type,pay_amount,check_result,channel_name,comment)";
		int x =DataLoadDB.load(csvUtil, dataList, "/temp.csv", sql);
		

		map.put("impDataNum", x);
		map.put("success", "导入成功");
		return map;
	}

	/**
	 * 获取未对账的数目
	 */
	public HashMap<String, Object> getNoChkNum() {
		HashMap<String, Object> payChkNumMap = this.getPayChkNum();
		HashMap<String, Object> receiptChkNumMap = this.getReceiptChkNum();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("payChkNumMap", payChkNumMap);
		map.put("receiptChkNumMap", receiptChkNumMap);
		return map;

	}

	private HashMap<String, Object> getPayChkNum() {
		AccountPaymentChkFormMap accountPaymentChkFormMap = new AccountPaymentChkFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<AccountPaymentChkFormMap> closingDateList = accountPaymentChkMapper.getClosingDate();
		String payClosingDate = closingDateList.get(0) == null ? "XXXX年XX月XX日"
				: (String) closingDateList.get(0).get("pay_date");
		map.put("payClosingDate", payClosingDate);

		accountPaymentChkFormMap.put("where", "where check_result = 0");
		List<AccountPaymentChkFormMap> payNoChkNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
		accountPaymentChkFormMap.put("where", "where check_result = 0 AND channel_name = '支付宝'");
		List<AccountPaymentChkFormMap> payAlipayNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
		accountPaymentChkFormMap.put("where", "where check_result = 0 AND channel_name = '微信'");
		List<AccountPaymentChkFormMap> payWCNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
		accountPaymentChkFormMap.put("where", "where check_result = 0 AND channel_name = '国安付'");
		List<AccountPaymentChkFormMap> payGapayNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);

		map.put("payNoChkNum", payNoChkNumList.size());
		map.put("payAlipayNum", payAlipayNumList.size());
		map.put("payWCNum", payWCNumList.size());
		map.put("payGapayNum", payGapayNumList.size());
		return map;

	}

	private HashMap<String, Object> getReceiptChkNum() {
		AccountReceiptChkFormMap accountReceiptChkFormMap = new AccountReceiptChkFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<AccountReceiptChkFormMap> closingDateList = accountReceiptChkMapper.getClosingDate();
		String receiptClosingDate = closingDateList.get(0) == null ? "XXXX年XX月XX日"
				: (String) closingDateList.get(0).get("receipt_date");
		map.put("receiptClosingDate", receiptClosingDate);

		accountReceiptChkFormMap.put("where", "where check_result = 0");
		List<AccountReceiptChkFormMap> receiptNoChkNumList = accountReceiptChkMapper
				.findByWhere(accountReceiptChkFormMap);
		accountReceiptChkFormMap.put("where", "WHERE check_result=0 AND pay_platform LIKE '%支付宝%'");
		List<AccountReceiptChkFormMap> receiptAlipayNumList = accountReceiptChkMapper
				.findByWhere(accountReceiptChkFormMap);
		accountReceiptChkFormMap.put("where", "WHERE check_result=0 AND pay_platform LIKE '%微信%'");
		List<AccountReceiptChkFormMap> receiptWCNumList = accountReceiptChkMapper.findByWhere(accountReceiptChkFormMap);
		accountReceiptChkFormMap.put("where", "WHERE check_result=0 AND pay_platform LIKE '%国安付%'");
		List<AccountReceiptChkFormMap> receiptGapayNumList = accountReceiptChkMapper
				.findByWhere(accountReceiptChkFormMap);

		map.put("receiptNoChkNum", receiptNoChkNumList.size());
		map.put("receiptAlipayNum", receiptAlipayNumList.size());
		map.put("receiptWCNum", receiptWCNumList.size());
		map.put("receiptGapayNum", receiptGapayNumList.size());
		return map;

	}

	public HashMap<String, Object> impPlatformData(ArrayList<ArrayList<Object>> result, String receiptWay)
			throws IOException {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		AccountReceiptChkFormMap accountReceiptChkFormMap = null;
		// 通过支付方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '" + receiptWay + "'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper
				.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf = (String) channelManagement.get("config_inf");
		ReceiptConfig configInf = JSON.parseObject(config_inf, ReceiptConfig.class);
		System.out.println(configInf.toString());

		ArrayList<Object> dataList = new ArrayList<Object>();
		if ("平台数据".equals(receiptWay)) {
			// 读取平台数据Excel，并写入DB
			String fund_type = (String) result.get(0).get(0);
			if ("收款单".equals(fund_type)) {
				for (int i = 0; i < result.size(); i++) {
					for (int j = 0; j < result.get(i).size(); j++) {
						if (i >= configInf.getStart_line() - 1) {
							if (j == configInf.getPay_platform_position() - 1) {
								String str = result.get(i).get(j).toString();
								if ("微信".equals(str) || "支付宝".equals(str) || "微信公众号".equals(str) || "微信扫码".equals(str)
										|| "支付宝扫码".equals(str) || "国安付".equals(str)) {
									String a1 = result.get(i).get(configInf.getCheck_order_position() - 1).toString();// 流水号
									String a2 = result.get(i).get(configInf.getPay_amount_position() - 1).toString();// 金额
									String a3 = result.get(i).get(configInf.getStore_name_position() - 1).toString();// 门店名称
									String a4 = result.get(i).get(configInf.getEshop_name_position() - 1).toString();// E店名称
									String eshop_id = result.get(i).get(configInf.getEshop_id_position() - 1)
											.toString();// E店ID
									String a5 = result.get(i).get(configInf.getSelf_position() - 1).toString();// 是否协议
									String a6 = result.get(i).get(configInf.getStatus_position() - 1).toString();// 支付状态
									String a7 = result.get(i).get(configInf.getReceipt_date_position() - 1).toString();// 支付完成时间
									String a8 = result.get(i).get(configInf.getPay_platform_position() - 1).toString();// 支付平台

									ArrayList<Object> list = new ArrayList<Object>();
									list.add(a1);
									list.add(a1);
									list.add(a8);
									list.add(a2);
									list.add(a6);
									list.add(a7);
									list.add(a3);
									list.add(a4);
									list.add(eshop_id);
									list.add(a5);
									list.add("");
									list.add(0);
									list.add(1);
									list.add(1);
									dataList.add(list);

								}
							}
						}
					}
				}
			}

			if ("退款单".equals(fund_type)) {
				for (int i = 0; i < result.size(); i++) {
					for (int j = 0; j < result.get(i).size(); j++) {
						if (i >= configInf.getRefund_start_line() - 1) {
							if (j == configInf.getRefund_pay_platform_position() - 1) {
								String str = result.get(i).get(j).toString();
								if ("微信".equals(str) || "支付宝".equals(str) || "微信公众号".equals(str) || "微信扫码".equals(str)
										|| "支付宝扫码".equals(str) || "国安付".equals(str)) {
									String a1 = result.get(i).get(configInf.getRefund_check_order_position() - 1)
											.toString();// 流水号
									if ("国安付".equals(str)) {
										a1 = result.get(i).get(configInf.getRefund_check_order_position()).toString();
									}
									String a2 = result.get(i).get(configInf.getRefund_amount_position() - 1).toString();// 金额
									String a3 = result.get(i).get(configInf.getRefund_store_name_position() - 1)
											.toString();// 门店名称
									String a4 = result.get(i).get(configInf.getRefund_eshop_name_position() - 1)
											.toString();// E店名称
									String eshop_id = result.get(i).get(configInf.getRefund_eshop_id_position() - 1)
											.toString();// E店ID
									String a5 = result.get(i).get(configInf.getRefund_self_position() - 1).toString();// 是否协议
									String a6 = result.get(i).get(configInf.getRefund_status_position() - 1).toString();// 支付状态
									String a7 = result.get(i).get(configInf.getRefund_date_position() - 1).toString();// 支付完成时间
									String a8 = result.get(i).get(configInf.getRefund_pay_platform_position() - 1)
											.toString();// 支付平台
									String a9 = result.get(i).get(configInf.getRefund_comment_position() - 1).toString()
											.replace(",", "");// 备注

									ArrayList<Object> list = new ArrayList<Object>();
									list.add(a1);
									list.add(a1);
									list.add(a8);
									list.add(a2);
									list.add(a6);
									list.add(a7);
									list.add(a3);
									list.add(a4);
									list.add(eshop_id);
									list.add(a5);
									list.add(a9);
									list.add(0);
									list.add(1);
									list.add(2);
									dataList.add(list);

								}
							}
						}
					}
				}
			}

		}

		if ("微超".equals(receiptWay)) {
			// 微超导入
			for (int i = 0; i < result.size(); i++) {
				for (int j = 0; j < result.get(i).size(); j++) {
					if (i >= configInf.getStart_line() - 1) {
						if (j == 1) {
							String a1 = result.get(i).get(configInf.getStore_name_position() - 1).toString();// 店铺
							String a2 = result.get(i).get(configInf.getCheck_order_position() - 1).toString();// 交易单号
							String a3 = result.get(i).get(configInf.getPay_amount_position() - 1).toString();// 金额
							String a4 = result.get(i).get(configInf.getPay_platform_position() - 1).toString();// 支付方式
							String a5 = result.get(i).get(configInf.getReceipt_date_position() - 1).toString();// 销售时间
							System.out.println(a1 + " " + a2 + " " + a3 + " " + a4 + " " + a5);
							accountReceiptChkFormMap = new AccountReceiptChkFormMap();
							accountReceiptChkFormMap.put("check_order", a2);
							accountReceiptChkFormMap.put("pay_amount", a3);
							accountReceiptChkFormMap.put("store_name", a1);
							accountReceiptChkFormMap.put("receipt_date", a5);
							accountReceiptChkFormMap.put("pay_platform", a4);
							accountReceiptChkFormMap.put("check_result", 0);
							accountReceiptChkFormMap.put("source", 2);
							accountReceiptChkFormMap.put("where", "where check_order = '" + a2 + "'");

						}
					}
				}
			}
		}
		//将处理好的数据load到DB中
		String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "
				+ "INTO TABLE t_account_receipt_chk " + "CHARACTER SET GBK " + "FIELDS TERMINATED by ',' "
				+ "LINES TERMINATED by '\r\n' "
				+ "(relation_id,check_order,pay_platform,pay_amount,status,receipt_date,"
				+ "store_name,eshop_name,eshop_id,self,comment,check_result,source,fund_type)";
		int x =DataLoadDB.load(new CsvUtil(), dataList, "/tempDB.csv", sql);
		
		// 最后根据根据eshop_id更新供应商名称
		accountReceiptChkMapper.matchProviderName1();
		map.put("impDataNum", x);
		map.put("success", "导入成功");
		return map;
	}

	public void impPlatformRelation(ArrayList<ArrayList<Object>> result, String receiptWay) {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		AccountReceiptChkFormMap accountReceiptChkFormMap = new AccountReceiptChkFormMap();
		// 通过收款方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '" + receiptWay + "'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper
				.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf = (String) channelManagement.get("config_inf");
		ReceiptConfig configInf = JSON.parseObject(config_inf, ReceiptConfig.class);
		System.out.println(configInf.toString());
		// 将关系表中的ID通过流水号匹配插入对应的数据中
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).size(); j++) {
				if (i >= configInf.getRelation_start_line() - 1) {
					// if("alipay_qr".equals(result.get(i).get(configInf.getRelation_pay_platform_position()-1).toString())){
					if (j == configInf.getRelation_id_position() - 1
							|| j == configInf.getRelation_chkId_position() - 1) {
						System.out.println(i + "行 " + j + "列" + result.get(i).get(j).toString());
						String ckId = result.get(i).get(configInf.getRelation_chkId_position() - 1).toString();
						accountReceiptChkFormMap.put("where",
								"where check_order = '" + ckId + "' and check_result = 0");
						List<AccountReceiptChkFormMap> accountReceiptChkList = accountReceiptChkMapper
								.findByWhere(accountReceiptChkFormMap);
						if (accountReceiptChkList.size() != 0) {
							for (AccountReceiptChkFormMap formMap : accountReceiptChkList) {

								formMap.set("relation_id",
										result.get(i).get(configInf.getRelation_id_position() - 1).toString());
								try {
									accountReceiptChkMapper.editEntity(formMap);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					// }
				}
			}
		}

	}

	public void chkMoney(Map<String, Object> m) {
		try {
			accountPaymentChkMapper.updateMatchData(m);
			accountReceiptChkMapper.updateMatchData(m);
			accountPaymentChkMapper.updateChkResult(m);
			accountReceiptChkMapper.updateChkResult(m);
			//修改合并支付对账  先查出对账符合的 然后将修改合并明细金额和状态
			AccountReceiptChkFormMap formMap = new AccountReceiptChkFormMap();
			 List list = (List) m.get("list");
			 
			String whereSql = "WHERE merge_flag is not null AND (check_result = 1 OR check_result = 2) and pay_platform in (";
							for (int i = 0; i < list.size(); i++) {
								whereSql += "'"+list.get(i)+"',";
							}
							String substring = whereSql.substring(0, whereSql.length()-1);
					whereSql = substring+") AND receipt_date >= '"+m.get("startTimeChkMoney")+"' AND receipt_date <= '"+m.get("endTimeChkMoney")+"'";
			formMap.put("where", whereSql);
			List<AccountReceiptChkFormMap> findByWhere = accountReceiptChkMapper.findByWhere(formMap);
			if(findByWhere.size()!=0){
				for (AccountReceiptChkFormMap accountReceiptChkFormMap : findByWhere) {
					formMap.put("where", "where relation_id = '"+accountReceiptChkFormMap.get("check_order")+"'");
					List<AccountReceiptChkFormMap> findByWhere2 = accountReceiptChkMapper.findByWhere(formMap);
					if(findByWhere2.size()!=0){
						Map<String,Object> updateFormMap = new HashMap<>();
						updateFormMap.put("recipt_amount", accountReceiptChkFormMap.get("recipt_amount"));
						updateFormMap.put("check_result", accountReceiptChkFormMap.get("check_result").equals("1")?"4":"5");
						updateFormMap.put("relation_id", accountReceiptChkFormMap.get("check_order"));
						updateFormMap.put("list", m.get("list"));
						updateFormMap.put("chkReceiptWay", m.get("chkReceiptWay"));
						updateFormMap.put("startTimeChkMoney", m.get("startTimeChkMoney"));
						updateFormMap.put("endTimeChkMoney", m.get("endTimeChkMoney"));
						accountReceiptChkMapper.updateMergePay(updateFormMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Object> importWalletFile(ArrayList<ArrayList<Object>> result, String payWay)
			throws IOException {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 通过支付方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '" + payWay + "'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper
				.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf = (String) channelManagement.get("config_inf");
		WalletAccountConfig configInf = JSON.parseObject(config_inf, WalletAccountConfig.class);
		System.out.println(configInf.toString());

		IPayFileHandle payFileHandleImpl = PayFileHandleFactory.getPayFileHandleImpl(configInf.getChannel_name());
		List<Object> dataList = payFileHandleImpl.getPayFileHandle(configInf, result);

		//将处理好的数据load到DB中
		String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "
				+ "INTO TABLE t_account_payment_chk " + "CHARACTER SET GBK " + "FIELDS TERMINATED by ',' "
				+ "LINES TERMINATED by '\r\n' "
				+ "(check_order,pay_date,fund_type,pay_amount,check_result,channel_name)";
		int x =DataLoadDB.load(new CsvUtil(), dataList, "/tempGApay.csv", sql);


		map.put("impDataNum", x);
		map.put("success", "导入成功");
		return map;
	}


	public HashMap<String, Object> getPayNoChkNum(String monthPay) {
		AccountPaymentChkFormMap accountPaymentChkFormMap = new AccountPaymentChkFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String startTime = monthPay + "-01 00:00:00";
		String endTime = monthPay + "-31 23:59:59";
		accountPaymentChkFormMap.put("where",
				"where check_result = 0 AND pay_date >= '" + startTime + "' AND pay_date <= '" + endTime + "'");
		List<AccountPaymentChkFormMap> payNoChkNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
		accountPaymentChkFormMap.put("where", "where check_result = 0 AND channel_name = '支付宝' AND pay_date >= '"
				+ startTime + "' AND pay_date <= '" + endTime + "'");
		List<AccountPaymentChkFormMap> payAlipayNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
		accountPaymentChkFormMap.put("where", "where check_result = 0 AND channel_name = '微信' AND pay_date >= '"
				+ startTime + "' AND pay_date <= '" + endTime + "'");
		List<AccountPaymentChkFormMap> payWCNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);
		accountPaymentChkFormMap.put("where", "where check_result = 0 AND channel_name = '国安付' AND pay_date >= '"
				+ startTime + "' AND pay_date <= '" + endTime + "'");
		List<AccountPaymentChkFormMap> payGapayNumList = accountPaymentChkMapper.findByWhere(accountPaymentChkFormMap);

		map.put("payNoChkNum", payNoChkNumList.size());
		map.put("payAlipayNum", payAlipayNumList.size());
		map.put("payWCNum", payWCNumList.size());
		map.put("payGapayNum", payGapayNumList.size());
		return map;
	}

	public HashMap<String, Object> getReceiptNoChkNum(String monthReceipt) {
		AccountReceiptChkFormMap accountReceiptChkFormMap = new AccountReceiptChkFormMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String startTime = monthReceipt + "-01 00:00:00";
		String endTime = monthReceipt + "-31 23:59:59";

		accountReceiptChkFormMap.put("where", "where check_result = 0  AND receipt_date >= '" + startTime
				+ "' AND receipt_date <= '" + endTime + "'");
		List<AccountReceiptChkFormMap> receiptNoChkNumList = accountReceiptChkMapper
				.findByWhere(accountReceiptChkFormMap);
		accountReceiptChkFormMap.put("where", "WHERE check_result=0 AND pay_platform LIKE '%支付宝%' AND receipt_date >= '"
				+ startTime + "' AND receipt_date <= '" + endTime + "'");
		List<AccountReceiptChkFormMap> receiptAlipayNumList = accountReceiptChkMapper
				.findByWhere(accountReceiptChkFormMap);
		accountReceiptChkFormMap.put("where", "WHERE check_result=0 AND pay_platform LIKE '%微信%' AND receipt_date >= '"
				+ startTime + "' AND receipt_date <= '" + endTime + "'");
		List<AccountReceiptChkFormMap> receiptWCNumList = accountReceiptChkMapper.findByWhere(accountReceiptChkFormMap);
		accountReceiptChkFormMap.put("where", "WHERE check_result=0 AND pay_platform LIKE '%国安付%' AND receipt_date >= '"
				+ startTime + "' AND receipt_date <= '" + endTime + "'");
		List<AccountReceiptChkFormMap> receiptGapayNumList = accountReceiptChkMapper
				.findByWhere(accountReceiptChkFormMap);

		map.put("receiptNoChkNum", receiptNoChkNumList.size());
		map.put("receiptAlipayNum", receiptAlipayNumList.size());
		map.put("receiptWCNum", receiptWCNumList.size());
		map.put("receiptGapayNum", receiptGapayNumList.size());
		return map;
	}
}
