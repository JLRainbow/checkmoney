package com.citic.factory.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.citic.entity.ConfigInf;
import com.citic.entity.WalletAccountConfig;
import com.citic.factory.inf.IPayFileHandle;
import com.citic.util.CsvUtil;
/**
 * 微信扫码支付文件处理
 * @author jial
 *
 */
public class WXScanCodePayFileHandle implements IPayFileHandle {

	@Override
	public List<Object> getPayFileHandle(ConfigInf configInf,CsvUtil csvUtil) throws IOException {
		int rowNum = csvUtil.getRowNum();// 获取行数
		System.out.println("==========行数：" + rowNum);
		String skp1 = "";
		String skp2 = "";
		if (configInf.getKeywords_num() != 1) {
			String kp = configInf.getKeywords_position();
			String[] split = kp.split("/");
			skp1 = split[0].trim();
			skp2 = split[1].trim();
		} else {
			skp2 = configInf.getKeywords_position();
		}
		List<Object> dataList = new ArrayList<Object>();
		for (int i = configInf.getStart_line(); i <= rowNum - configInf.getEnd_bottom_line() - 1; i++) {
			String row = csvUtil.getRow(i);// 获取行
			String row3 = new String(row.getBytes("gbk"), "utf-8");// 解码,防止乱码
			String a1 = row3.split(",")[Integer.parseInt(skp2) - 1];// 获取商户数据包
			String s1 = a1.substring(1, a1.length()).trim();// 去除数据的 `
			String a = row3.split(",")[Integer.parseInt(skp1) - 1];// 获取商户订单号
			String s = a.substring(1, a.length()).trim();// 去除数据的 `
			//聚合支付和合并支付替换逻辑
			if(s1.contains("聚合支付")|| //如果商户数据包中含有聚合支付就获取商户订单号作为对账流水号
				s.startsWith("com")){	// 将商户订单号中以com开的替换掉商户数据包
				s1 = s1.replace(s1, s);
			}
			String a6 = row3.split(",")[configInf.getPay_date_position() - 1];// 获取发生时间列
			String s6 = a6.substring(1, a6.length()).trim();
			String a8 = row3.split(",")[configInf.getPay_amount_position() - 1];// 获取收入金额列
			String s8 = a8.substring(1, a8.length()).trim();
			int s13 = 0;
			if (s8.contains("-")) {
				s8 = s8.substring(1);
				s13 = 2;
			}else{
				s13 = 1;
			}
			List<Object> list = new ArrayList<Object>();
			list.add(s1);
			list.add(s6);
			list.add(s13);
			list.add(s8);
			list.add("0");
			list.add(configInf.getChannel_name());
			list.add("");
			dataList.add(list);

		}
		return dataList;
	}

	/**
	 * 
	 *方法：和微信的处理方法一样，只有判断聚合支付和合并支付数据和微信扫码一样
	 *创建时间：2018年1月29日
	 *创建者：jial
	 */
	public List<Object> getPayFileHandle2(ConfigInf configInf,CsvUtil csvUtil) throws IOException {
		int rowNum = csvUtil.getRowNum();// 获取行数
		System.out.println("==========行数：" + rowNum);
		String skp1 = "";
		String skp2 = "";
		if (configInf.getKeywords_num() != 1) {
			String kp = configInf.getKeywords_position();
			String[] split = kp.split("/");
			skp1 = split[0].trim();
			skp2 = split[1].trim();
		} else {
			skp2 = configInf.getKeywords_position();
		}
		List<Object> dataList = new ArrayList<Object>();
		for (int i = configInf.getStart_line(); i <= rowNum - configInf.getEnd_bottom_line() - 1; i++) {
			String row3 = csvUtil.getRow(i);// 获取行
			// 解码,防止乱码

			String a2 = row3.split(",")[configInf.getFund_type_position() - 1].trim();//获取交易状态
			String s2 = a2.substring(1, a2.length()).trim();
			if (s2.contains("SUCCESS") || s2.contains("REFUND")) {// 筛选SUCCESS和REFUND的数据

				String a = row3.split(",")[Integer.parseInt(skp2) - 1];// 获取商户数据包
				String s = a.substring(1, a.length()).trim();// 把字符串的首尾部的""去掉;
				String a1 = row3.split(",")[Integer.parseInt(skp1) - 1];// 获取商户订单号
				String s1 = a1.substring(1, a1.length()).trim();// 去除数据的 `
				
				//聚合支付和合并支付替换逻辑
				if(s.contains("聚合支付")|| //如果商户数据包中含有聚合支付就获取商户订单号作为对账流水号
					s1.startsWith("com")){	// 将商户订单号中以com开的替换掉商户数据包
					s = s.replace(s, s1);
				}
				String a6 = row3.split(",")[configInf.getPay_date_position() - 1];// 获取发生时间列
				String s6 = a6.substring(1, a6.length()).trim();
				String a8 = row3.split(",")[configInf.getPay_amount_position() - 1];// 获取收入金额列
				String s8 = a8.substring(1, a8.length()).trim();
				String a9 = row3.split(",")[configInf.getRefund_amount_position() - 1];// 获取支出金额列
				String s9 = a9.substring(1, a9.length()).trim();
				if (Double.parseDouble(s9) != 0) {
					s8 = s8.replace(s8, s9);
				}
				if (s8.contains("-")) {
					s8 = s8.substring(1);
				}
				String a12 = row3.split(",")[configInf.getFund_type_position() - 1];// 获取业务类型列
				String s12 = a12.substring(1, a12.length()).trim();
				Integer s13 = null;
				if ("SUCCESS".equals(s12)) {
					s13 = 1;
				} else {
					s13 = 2;
				}

				List<Object> list = new ArrayList<Object>();
				list.add(s);
				list.add(s6);
				list.add(s13);
				list.add(s8);
				list.add("0");
				list.add(configInf.getChannel_name());
				list.add("");
				dataList.add(list);

			}
		}
		return dataList;
	}
	
	@Override
	public List<Object> getPayFileHandle(WalletAccountConfig configInf, ArrayList<ArrayList<Object>> result) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
