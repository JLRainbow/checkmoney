package com.citic.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.platform.entity.OrderReceipts;
import com.citic.platform.mapper.OrderReceiptsMapper;
import com.citic.platform.service.OrderReceiptsService;
@Service
public class OrderReceiptsServiceImpl implements OrderReceiptsService {
	@Autowired
	private OrderReceiptsMapper orderReceiptsMapper;

	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;

	@Override
	public List<OrderReceipts> getPlatformPayData(Map<String, Object> map) throws Exception {
		try {
			return orderReceiptsMapper.getPlatformPayData(map);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void mergePay(Map<String, Object> map) {
		String[] DBpayPlatform = (String[]) map.get("DBpayPlatformArray");
		map.put("DBpayPlatformArray", parseDBpayPlatform(DBpayPlatform));
		
		// 先查出合并支付的数据
		List<AccountReceiptChkFormMap> mergePayList = orderReceiptsMapper.getMergePay(map);
		// 然后将合并支付的数据金额相加插入到数据库
		AccountReceiptChkFormMap formMap = null;
		for (AccountReceiptChkFormMap accountReceiptChkFormMap : mergePayList) {
			formMap = new AccountReceiptChkFormMap();
			formMap.put("check_order", accountReceiptChkFormMap.get("relation_id"));
			formMap.put("where", "where check_order = '"+accountReceiptChkFormMap.get("relation_id")+"'");
			List<AccountReceiptChkFormMap> findByWhere = accountReceiptChkMapper.findByWhere(formMap);
			if(findByWhere.size()==0){	//防止重复添加合并之后的数据
				//先判断合并标识 设置不同的relation_id
				if("paySn".equals(accountReceiptChkFormMap.get("merge_flag"))){
					formMap.put("relation_id", "com"+accountReceiptChkFormMap.get("relation_id"));
					formMap.put("merge_flag", "paySn");
				}
				if("chargeId".equals(accountReceiptChkFormMap.get("merge_flag"))){
					formMap.put("relation_id", accountReceiptChkFormMap.get("relation_id"));
					formMap.put("merge_flag", "chargeId");
				}
				formMap.put("pay_amount", accountReceiptChkFormMap.get("pay_amount"));
				formMap.put("source", accountReceiptChkFormMap.get("source"));
				formMap.put("fund_type", accountReceiptChkFormMap.get("fund_type"));
				formMap.put("status", accountReceiptChkFormMap.get("status"));
				formMap.put("receipt_date", accountReceiptChkFormMap.get("receipt_date"));
				formMap.put("store_name", accountReceiptChkFormMap.get("store_name"));
				formMap.put("pay_platform", accountReceiptChkFormMap.get("pay_platform"));
				formMap.put("check_result", 0);
				try {
					accountReceiptChkMapper.addEntity(formMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private String[] parseDBpayPlatform(String[] dBpayPlatform){
		String[] parseDBpayPlatform = new String[dBpayPlatform.length];
		for (int i = 0; i < dBpayPlatform.length; i++) {
			switch (dBpayPlatform[i]) {
				case "wx": dBpayPlatform[i] = "微信"; break;
				case "wx_pub_qr": dBpayPlatform[i] = "微信扫码"; break;
				case "wx_pub": dBpayPlatform[i] = "微信公众号"; break;
				case "alipay": dBpayPlatform[i] = "支付宝"; break;
				case "alipay_qr": dBpayPlatform[i] = "支付宝扫码"; break;
				case "gapay": dBpayPlatform[i] = "国安付"; break;
				case "union_pay": dBpayPlatform[i] = "银行卡支付"; break;
				case "pos": dBpayPlatform[i] = "POS"; break;
				case "cash": dBpayPlatform[i] = "现金"; break;
			}
			parseDBpayPlatform[i] =dBpayPlatform[i];
		}
		return parseDBpayPlatform;
	}

}
