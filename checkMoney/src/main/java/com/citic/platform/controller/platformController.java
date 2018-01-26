package com.citic.platform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.citic.platform.service.OrderReceiptsService;
import com.citic.platform.service.OrderRefundService;
import com.citic.service.Impl.CheckMoneyServiceImpl;
import com.citic.util.CsvUtil;
import com.mysql.jdbc.Connection;
@Controller
@RequestMapping("/platform")
public class platformController extends BaseController{
	
	@Autowired
	private OrderReceiptsService orderReceiptsService;
	
	@Autowired
	private AccountReceiptChkMapper accountReceiptChkMapper;
	
	
	@Autowired
	private OrderRefundService orderRefundService;
	
	/**
	 * 
	 *方法：获取平台收款数据
	 *创建时间：2017年7月13日
	 *创建者：jial
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/getPlatformPayData",method=RequestMethod.POST)
	@SystemLog(module="财务对账业务",methods="财务对账处理（虚虚对账）-平台DB导入收款数据")//记录操作日志
	public HashMap<String,Object> getPlatformPayData(@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime
			,@RequestParam(value ="DBpayPlatform") String DBpayPlatform) throws IOException {
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		String[] DBpayPlatformArray = DBpayPlatform.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("DBpayPlatformArray", DBpayPlatformArray);
		
		 //手动切换为平台数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_PALTFORMDATASOURCE);  
		List<OrderReceipts> OrderReceiptsList = orderReceiptsService.getPlatformPayData(map);
		//切换为本地数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_DATASOURCE1);
		ArrayList<Object> dataList = new  ArrayList<Object>();
		for (OrderReceipts orderReceipts : OrderReceiptsList) {
			String relation_id = orderReceipts.getCharge_id();
			//替换支付宝扫码的relation_id
			if("支付宝扫码".equals(orderReceipts.getPay_platform())||
				"POS".equals(orderReceipts.getPay_platform())||
				"现金".equals(orderReceipts.getPay_platform())){
				relation_id = orderReceipts.getId();
			}
			int checkResult = 0;
			String chargeId = orderReceipts.getCharge_id();
			String mergeFlag = "";//合并标识
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
				checkResult = 3;//合并支付
			}
			if("POS".equals(orderReceipts.getPay_platform())||
				"现金".equals(orderReceipts.getPay_platform())||
				orderReceipts.getPaySn()!=null){
				chargeId = orderReceipts.getId();
			}
			ArrayList<Object> list = new  ArrayList<Object>();
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
			dataList.add(list);
		}
		//将整理好的数据load到DB中
		 String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "+
					"INTO TABLE t_account_receipt_chk "+
					"CHARACTER SET GBK "+
					"FIELDS TERMINATED by ',' "+
					"LINES TERMINATED by '\r\n' "+
					"(relation_id,check_order,pay_platform,pay_amount,status,receipt_date,"
					+ "store_name,eshop_name,eshop_id,self,check_result,source,fund_type,merge_flag)";	
		int x =DataLoadDB.load(new CsvUtil(), dataList, "/tempDB.csv", sql);
		
		
		TempPo tempPo = new TempPo();
		tempPo.setStartTime(startTime);
		tempPo.setEndTime(endTime);
		//最后根据根据eshop_id更新供应商名称
		accountReceiptChkMapper.matchProviderName(tempPo);
		//插入合并支付数据
		orderReceiptsService.mergePay(map);
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("impDataNum", x);
		result.put("success", "导入成功");
		return result;
	}

	/**
	 * 
	 *方法：获取平台退款数据
	 *创建时间：2017年7月13日
	 *创建者：jial
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/getPlatformReceiptData",method=RequestMethod.POST)
	@SystemLog(module="财务对账业务",methods="财务对账处理（虚虚对账）-平台DB导入退款数据")//记录操作日志
	public HashMap<String,Object> getPlatformReceiptData(@RequestParam(value ="startTime") String startTime
			,@RequestParam(value ="endTime") String endTime
			,@RequestParam(value ="DBpayPlatform") String DBpayPlatform) throws IOException {
		startTime+=" 00:00:00";
		endTime+=" 23:59:59";
		String[] DBpayPlatformArray = DBpayPlatform.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("DBpayPlatformArray", DBpayPlatformArray);
		 //手动切换为平台数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_PALTFORMDATASOURCE);  
		List<OrderRefund> orderRefundList = orderRefundService.getPlatformReceiptData(map);
		//切换为本地数据源
		DataSourceContextHolder. setDbType(DataSourceType.SOURCE_DATASOURCE1);
		ArrayList<Object> dataList = new  ArrayList<Object>();
		for (OrderRefund orderRefund : orderRefundList) {
			String relation_id = orderRefund.getReceiptsChargeId();
			//替换退款对账流水号relation_id
			String refundPlatform = orderRefund.getRefundPlatform();
			if("POS".equals(refundPlatform)||
				"现金".equals(refundPlatform)){
				relation_id = orderRefund.getReceiptId();//拿receiptId替换
			}
			//支付宝并且paySource不是ping的数据拿id作为对账流水号
			if("支付宝".equals(refundPlatform)&&
			   !"ping".equals(orderRefund.getPaySource()) ){
				relation_id = orderRefund.getId();
			}
			//支付宝扫码或者支付宝并且paySource是ping的数据拿refund_charge_id去除前三个字符作为对账流水号
			if("支付宝扫码".equals(refundPlatform)||
			   ("支付宝".equals(refundPlatform)&&"ping".equals(orderRefund.getPaySource()))
			   ){
				relation_id = orderRefund.getRefundChargeId().substring(3); 
			}
			if("国安付".equals(refundPlatform)){
				relation_id = orderRefund.getRefundChargeId();//拿收款表的refund_charge_id替换
			}
			String checkOrder = orderRefund.getId();
			if("POS".equals(refundPlatform)||
				"现金".equals(refundPlatform)){
				checkOrder = orderRefund.getReceiptId();
			}
			ArrayList<Object> list = new  ArrayList<Object>();
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
			dataList.add(list);
		}
		//将整理好的数据load到DB中
		String sql = "LOAD DATA LOCAL INFILE 'xx.csv' "+
				"INTO TABLE t_account_receipt_chk "+
				"CHARACTER SET GBK "+
				"FIELDS TERMINATED by ',' "+
				"LINES TERMINATED by '\r\n' "+
				"(relation_id,check_order,pay_platform,pay_amount,status,receipt_date,"
				+ "store_name,eshop_name,eshop_id,self,comment,check_result,source,fund_type)";		
		int x =DataLoadDB.load(new CsvUtil(), dataList, "/tempDB.csv", sql);
		
		
		TempPo tempPo = new TempPo();
		tempPo.setStartTime(startTime);
		tempPo.setEndTime(endTime);
		//最后根据根据eshop_id更新供应商名称
		accountReceiptChkMapper.matchProviderName(tempPo);
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("impDataNum", x);
		result.put("success", "导入成功");
		
		System.out.println("====获取平台退款数据=====");
		return result;
		
	}
	
}
