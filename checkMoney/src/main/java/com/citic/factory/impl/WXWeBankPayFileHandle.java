package com.citic.factory.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.citic.entity.ConfigInf;
import com.citic.entity.WalletAccountConfig;
import com.citic.factory.inf.IPayFileHandle;
import com.citic.util.CsvUtil;

public class WXWeBankPayFileHandle implements IPayFileHandle {

	@Override
	public List<Object> getPayFileHandle(ConfigInf configInf, CsvUtil csvUtil) throws IOException {
		int rowNum = csvUtil.getRowNum();// 获取行数
		System.out.println("==========行数：" + rowNum);
		
		List<Object> dataList = new ArrayList<Object>();
		for (int i = configInf.getStart_line(); i <= rowNum - configInf.getEnd_bottom_line() - 1; i++) {
			String row = csvUtil.getRow(i);// 获取行
			String row3 = new String(row.getBytes("gbk"), "utf-8");//
			// 解码,防止乱码

			String a2 = row3.split(",")[configInf.getFund_type_position() - 1].trim();//获取交易状态
			String fundType = a2.substring(1, a2.length()).trim();
			if ("SUCCESS".equals(fundType) || "REFUND".equals(fundType)) {// 筛选SUCCESS和REFUND的数据

				String a1 = row3.split(",")[Integer.parseInt(configInf.getKeywords_position()) - 1];// 获取商户订单号
				String checkOrder = a1.substring(1, a1.length()).trim();// 去除数据的 `
				String a6 = row3.split(",")[configInf.getPay_date_position() - 1];// 获取发生时间列
				String payDate = a6.substring(1, a6.length()).trim();
				String a8 = row3.split(",")[configInf.getPay_amount_position() - 1];// 获取收入金额列
				String payAmount = a8.substring(1, a8.length()).trim();
				
			
				Integer s13 = null;
				if ("SUCCESS".equals(fundType)) {
					s13 = 1;
				} else {
					s13 = 2;
					String a9 = row3.split(",")[configInf.getRefund_amount_position() - 1];// 获取退款金额列
					String refundAmount = a9.substring(1, a9.length()).trim();
					payAmount = payAmount.replace(payAmount, refundAmount);
				}

				List<Object> list = new ArrayList<Object>();
				list.add(checkOrder);
				list.add(payDate);
				list.add(s13);
				list.add(payAmount);
				list.add("0");
				dataList.add(list);

			}
		}
		return dataList;
	}

	@Override
	public List<Object> getPayFileHandle(WalletAccountConfig configInf, ArrayList<ArrayList<Object>> result)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
