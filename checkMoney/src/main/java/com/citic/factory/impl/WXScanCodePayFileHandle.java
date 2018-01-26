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

	@Override
	public List<Object> getPayFileHandle(WalletAccountConfig configInf, ArrayList<ArrayList<Object>> result) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
