package com.citic.platform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.annotation.SystemLog;
import com.citic.bean.po.TempPo;
import com.citic.controller.index.BaseController;
import com.citic.datasource.DataSourceContextHolder;
import com.citic.datasource.DataSourceType;
import com.citic.factory.load.DataLoadDB;
import com.citic.mapper.AccountReceiptChkMapper;
import com.citic.platform.entity.OrderReceipts;
import com.citic.platform.entity.OrderRefund;
import com.citic.platform.entity.WxGiftCardBuyRecord;
import com.citic.platform.service.OrderReceiptsService;
import com.citic.platform.service.OrderRefundService;
import com.citic.platform.service.WxGiftCardBuyRecordService;
import com.citic.util.Common;
import com.citic.util.CsvUtil;
@Controller
@RequestMapping("/platform")
public class platformController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(platformController.class);
	
	@Autowired
	private OrderReceiptsService orderReceiptsService;
	
	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;
	
	@Autowired
	private OrderRefundService orderRefundService;
	
	@Autowired
	private WxGiftCardBuyRecordService wxGiftCardBuyRecordService;
	
	/**
	 * 
	 *方法：获取平台收款数据
	 *创建时间：2017年7月13日
	 *创建者：jial
	 */
	@ResponseBody
	@RequestMapping(value="/getPlatformPayData",method=RequestMethod.POST)
	@SystemLog(module="财务对账业务",methods="财务对账处理（虚虚对账）-平台DB导入收款数据")//记录操作日志
	public Map<String,Object> getPlatformPayData(@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime
			,@RequestParam(value ="DBpayPlatform") String DBpayPlatform) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		String[] DBpayPlatformArray = DBpayPlatform.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("DBpayPlatformArray", DBpayPlatformArray);
		
		List<OrderReceipts> OrderReceiptsList = null;
		List<OrderReceipts> giftCardList = null;
		try {
			//手动切换为平台数据源
			logger.info("get platfrom receipt data start =========>> ");
			DataSourceContextHolder.setDbType(DataSourceType.SOURCE_PALTFORMDATASOURCE);  
			OrderReceiptsList = orderReceiptsService.getPlatformPayData(map);
			//从平台获取礼品卡数据
			for(String str : DBpayPlatformArray){
				if("gift_card".equals(str)){
					giftCardList = orderReceiptsService.getGiftCardFromPlatform(map);
					break;
				}
			}
			logger.info("get platfrom  receipt data end =========>> ");
			resultMap.put("success", true);
		} catch (Exception e) {
			logger.error("getPlatformPayData error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "获取平台支付数据异常");
			return resultMap;
		}
		//切换为本地数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_DATASOURCE1);
		//将数据装到list
		List<Object> dataList = new  ArrayList<Object>();
		for (OrderReceipts orderReceipts : OrderReceiptsList) {
			//替换relation_id业务
			String relation_id = orderReceipts.getCharge_id();
			//将paysource='ping'的支付宝扫码的relation_id替换为id
			if(("支付宝扫码".equals(orderReceipts.getPay_platform())&&"ping".equals(orderReceipts.getPaySource()))||
				"POS".equals(orderReceipts.getPay_platform())||
				"现金".equals(orderReceipts.getPay_platform())){
				relation_id = orderReceipts.getId();
			}
			String chargeId = orderReceipts.getCharge_id();
			int checkResult = 0;
			String mergeFlag = "";//设置合并标识
			//合并数据的relation_id替换业务
			if(orderReceipts.getPaySn()!=null){
				relation_id = orderReceipts.getPaySn(); //对账流水号取pay_sn
				mergeFlag = "paySn";
				//pay_source <> "ping" and pay_status="payed" and charge_id= "" and pay_sn<>""
				if(!"ping".equals(orderReceipts.getPaySource()) && chargeId != null){
					relation_id = orderReceipts.getCharge_id();  //对账流水号取charge_id
					mergeFlag = "chargeId";
				}
				//pay_source = "ping" and   pay_sn<>"" and charge_id is NULL
				if("ping".equals(orderReceipts.getPaySource()) && chargeId == null){
					relation_id = orderReceipts.getPaySn(); //对账流水号取pay_sn
					mergeFlag = "paySn";
				}
				checkResult = 3;//合并支付设置对账结果
				chargeId = orderReceipts.getId();
			}
			List<Object> list = new  ArrayList<Object>();
			list.add(relation_id);
			list.add(chargeId);
			list.add(orderReceipts.getPay_platform());
			list.add(orderReceipts.getPrice());
			list.add(orderReceipts.getPay_status());
			list.add(orderReceipts.getPay_time());
			list.add(orderReceipts.getStore().getName());
			list.add(orderReceipts.getEshop().getName());
			list.add(orderReceipts.getEshop_id());
			list.add(orderReceipts.getEshop().getSelf());
			list.add(checkResult);
			list.add(1);
			list.add(1);
			list.add(mergeFlag);
			list.add(orderReceipts.getOrderSn());
			dataList.add(list);
		}
		//将礼品卡list添加到dataList
		if(giftCardList!=null&&giftCardList.size()>0){
			for(OrderReceipts giftCard : giftCardList){
				List<Object> list = new  ArrayList<Object>();
				list.add(giftCard.getCardNo());
				list.add(giftCard.getOrder_group_id());
				list.add("礼品卡");
				list.add(giftCard.getPrice());
				list.add(giftCard.getPay_status());
				list.add(giftCard.getPay_time());
				list.add(giftCard.getStore().getName());
				list.add(giftCard.getEshop().getName());
				list.add(giftCard.getEshop_id());
				list.add(giftCard.getEshop().getSelf());
				list.add(0);//从t_wx_gift_card_buy_record获取对账结果
				list.add(1);
				list.add(1);
				list.add("");
				list.add("");
				dataList.add(list);
			}
		}
		//将整理好的数据load到DB中
		 String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "+
					"INTO TABLE t_account_receipt_chk "+
					"CHARACTER SET GBK "+
					"FIELDS TERMINATED by ',' "+
					"LINES TERMINATED by '\r\n' "+
					"(relation_id,check_order,pay_platform,pay_amount,status,receipt_date,"
					+ "store_name,eshop_name,eshop_id,self,check_result,source,fund_type,merge_flag,order_sn)";	
		int x = 0;//load数量
		try {
			x = DataLoadDB.load(new CsvUtil(), dataList, "/tempDB.csv", sql);
			resultMap.put("impDataNum", x);
			resultMap.put("success", true);
		} catch (Exception e) {
			logger.error("getPlatformPayData load error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "导入异常");
			return resultMap;
		}
		
		
		TempPo tempPo = new TempPo();
		tempPo.setStartTime(startTime);
		tempPo.setEndTime(endTime);
		try {
			//最后根据根据eshop_id更新供应商名称
			accountReceiptChkMapper.matchProviderName(tempPo);
			//插入合并支付数据
			orderReceiptsService.mergePay(map);
			resultMap.put("success", true);
		} catch (Exception e) {
			logger.error("getPlatformPayData mergePay error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "插入合并支付数据异常");
			return resultMap;
		}
		
		
		return resultMap;
	}

	/**
	 * 
	 *方法：获取平台退款数据
	 *创建时间：2017年7月13日
	 *创建者：jial
	 */
	@ResponseBody
	@RequestMapping(value="/getPlatformReceiptData",method=RequestMethod.POST)
	@SystemLog(module="财务对账业务",methods="财务对账处理（虚虚对账）-平台DB导入退款数据")//记录操作日志
	public Map<String,Object> getPlatformReceiptData(@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime
			,@RequestParam(value ="DBpayPlatform") String DBpayPlatform)  {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		String[] DBpayPlatformArray = DBpayPlatform.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("DBpayPlatformArray", DBpayPlatformArray);
		 List<OrderRefund> orderRefundList = null;;
		try {
			//手动切换为平台数据源
			logger.info("get platfrom refund data start =========>> ");
			DataSourceContextHolder. setDbType(DataSourceType.SOURCE_PALTFORMDATASOURCE);  
			orderRefundList = orderRefundService.getPlatformReceiptData(map);
			logger.info("get platfrom refund data end =========>> ");
			resultMap.put("success", true);
		} catch (Exception e) {
			logger.error("getPlatformReceiptData error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "获取平台退款数据异常");
			return resultMap;
		}
		//切换为本地数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_DATASOURCE1);
		//将数据装到list里面
		List<Object> dataList = new  ArrayList<Object>();
		for (OrderRefund orderRefund : orderRefundList) {
			String relation_id = orderRefund.getReceiptsChargeId();
			//替换退款对账流水号relation_id业务
			String refundPlatform = orderRefund.getPayPlatform();
			if("POS".equals(refundPlatform)||
				"现金".equals(refundPlatform)){
				relation_id = orderRefund.getReceiptId();//拿receiptId替换
			}
			//支付宝并且paySource不是ping的数据拿id作为对账流水号
			if("支付宝".equals(refundPlatform)&&!"ping".equals(orderRefund.getPaySource()) ){
				relation_id = orderRefund.getId();
			}
			//支付宝并且paySource是ping的数据拿receiptId作为对账流水号
			if("支付宝".equals(refundPlatform)&&"ping".equals(orderRefund.getPaySource())){
				relation_id = orderRefund.getReceiptId();
			}
			//支付宝扫码并且paySource是ping的数据拿refund_charge_id去除前三个字符作为对账流水号
			if("支付宝扫码".equals(refundPlatform)&&"ping".equals(orderRefund.getPaySource())){
				relation_id = orderRefund.getRefundChargeId().substring(3); 
			}
			//支付宝扫码并且paySource不是ping的数据拿id作为对账流水号
			if("支付宝扫码".equals(refundPlatform)&&!"ping".equals(orderRefund.getPaySource())){
				relation_id = orderRefund.getId(); 
			}
			//微信 微信扫码 微信公众号的数据拿id作为对账流水号
			if("微信".equals(refundPlatform)||"微信扫码".equals(refundPlatform)||"微信公众号".equals(refundPlatform)){
				relation_id = orderRefund.getId(); 
				//是ping的数据拿refund_charge_id去除前三个字符作为对账流水号
				if("ping".equals(orderRefund.getPaySource())&&orderRefund.getRefundChargeId()!=null){
					relation_id = orderRefund.getRefundChargeId().substring(3); 
				}
			}
			
			//国安付的数据拿收款表的refund_charge_id作为对账流水号
			if("国安付".equals(refundPlatform)){
				relation_id = orderRefund.getRefundChargeId();
			}
			String checkOrder = orderRefund.getId();
			if("POS".equals(refundPlatform)||
				"现金".equals(refundPlatform)){
				checkOrder = orderRefund.getReceiptId();
			}
			List<Object> list = new  ArrayList<Object>();
			list.add(relation_id);
			list.add(checkOrder);
			list.add(orderRefund.getPayPlatform());
			list.add(orderRefund.getPrice());
			list.add(orderRefund.getRefundOrderStatus());
			list.add(orderRefund.getRefundTime());
			list.add(orderRefund.getStore().getName());
			list.add(orderRefund.getEshop().getName());
			list.add(orderRefund.getEshopId());
			list.add(orderRefund.getEshop().getSelf());
			list.add(orderRefund.getRemark()==null?"":orderRefund.getRemark().toString().replace(",", ""));
			list.add(0);
			list.add(1);
			list.add(2);
			list.add(orderRefund.getOrderSn());
			dataList.add(list);
		}
		//将整理好的数据load到DB中
		String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "+
				"INTO TABLE t_account_receipt_chk "+
				"CHARACTER SET GBK "+
				"FIELDS TERMINATED by ',' "+
				"LINES TERMINATED by '\r\n' "+
				"(relation_id,check_order,pay_platform,pay_amount,status,receipt_date,"
				+ "store_name,eshop_name,eshop_id,self,comment,check_result,source,fund_type,order_sn)";		
		int x = 0;
		try {
			x = DataLoadDB.load(new CsvUtil(), dataList, "/tempDB.csv", sql);
			resultMap.put("impDataNum", x);
			resultMap.put("success", true);
		} catch (IOException e) {
			logger.error("getPlatformReceiptData load error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "导入异常");
			return resultMap;
		}
		
		
		TempPo tempPo = new TempPo();
		tempPo.setStartTime(startTime);
		tempPo.setEndTime(endTime);
		//最后根据根据eshop_id更新供应商名称
		try {
			accountReceiptChkMapper.matchProviderName(tempPo);
		} catch (Exception e) {
			logger.error("getPlatformReceiptData load error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "导入异常");
			return resultMap;
		}
		
		
		return resultMap;
		
	}
	/**
	 * 
	 *方法：获取平台微信购卡记录list
	 *创建时间：2018年3月6日
	 *创建者：jial
	 */
	@ResponseBody
	@RequestMapping(value="/getWxGiftCardBuyRecordList",method=RequestMethod.POST)
	@SystemLog(module="财务对账业务",methods="platform-getWxGiftCardBuyRecordList")//记录操作日志
	public Map<String,Object> getWxGiftCardBuyRecordList(@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", Common.Date2TimeStamp(startTime,"yyyy-MM-dd HH:mm:ss"));
		params.put("endTime", Common.Date2TimeStamp(endTime,"yyyy-MM-dd HH:mm:ss"));
		
		List<WxGiftCardBuyRecord> wxGiftCardBuyRecordList =null;
		try {
			//手动切换为平台数据源
			logger.info("getWxGiftCardBuyRecordList start =========>> ");
			DataSourceContextHolder.setDbType(DataSourceType.SOURCE_PALTFORMDATASOURCE);  
			wxGiftCardBuyRecordList = wxGiftCardBuyRecordService.queryWxGiftCardBuyRecord(params);
			logger.info("getWxGiftCardBuyRecordList end =========>> ");
			resultMap.put("success", true);
		} catch (Exception e) {
			logger.error("getWxGiftCardBuyRecordList error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "获取平台微众银行数据异常");
			return resultMap;
		}
		//切换为本地数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_DATASOURCE1);
		//将数据装到list
		List<Object> dataList = new  ArrayList<Object>();
		for(WxGiftCardBuyRecord wxGiftCardBuyRecord:wxGiftCardBuyRecordList){
			List<Object> list = new  ArrayList<Object>();
			list.add(wxGiftCardBuyRecord.getId());
			list.add(wxGiftCardBuyRecord.getWxOrderId());
			list.add(Common.TimeStamp2Date(wxGiftCardBuyRecord.getPayFinishTime(), "yyyy-MM-dd HH:mm:ss"));//需要将时间转换
			list.add(Common.div(wxGiftCardBuyRecord.getTotalPrice(), "100", 2));
			list.add(Common.div(wxGiftCardBuyRecord.getPrice(), "100", 2));
			list.add(wxGiftCardBuyRecord.getCardId());
			list.add(wxGiftCardBuyRecord.getCardCode());
			list.add(Common.fromDateH());
			list.add(0);
			dataList.add(list);
		}
			
		//将整理好的数据load到DB中
		 String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "+
					"INTO TABLE t_wx_gift_card_buy_record "+
					"CHARACTER SET GBK "+
					"FIELDS TERMINATED by ',' "+
					"LINES TERMINATED by '\r\n' "+
					"(id,wx_order_id,pay_finish_time,total_price,price,card_id,"
					+ "card_code,create_time,check_result)";	
		int x = 0;//load数量
		try {
			x = DataLoadDB.load(new CsvUtil(), dataList, "/wx_gift_card_buy_record.csv", sql);
			resultMap.put("impDataNum", x);
			resultMap.put("success", true);
			return resultMap;
		} catch (Exception e) {
			logger.error("getPlatformPayData load error ==>>",e);
			resultMap.put("success", false);
			resultMap.put("errMsg", "导入异常");
			return resultMap;
		}
	}
}
