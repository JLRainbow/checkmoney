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
 * 支付宝支付文件处理
 * @author jial
 *
 */
public class AlipayPayFileHandle implements IPayFileHandle {

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

			String row3 = csvUtil.getRow(i);// 获取行
			// String row3 = new String(row.getBytes(), "utf-8");//解码,防止乱码
			String a2 = row3.split(",")[configInf.getFund_type_position() - 1].trim();
			if (!"收费".equals(a2) && !"提现".equals(a2) && !"退费".equals(a2)) {// 选取不包含收费、提现、退费的数据
				String a = row3.split(",")[Integer.parseInt(skp2) - 1].trim();// 获取备注列
				String a1 = row3.split(",")[Integer.parseInt(skp1) - 1].trim();// 获取商户订单号

				if (a.contains("ch_")&&	//备注列是否还有ch_流水号并且不是合并支付的数据（商户订单号以com开头为合并支付）
					!a1.startsWith("com")) {
					String split = a.split(" ")[1];
					a1 = a1.replace(a1, split);// 如果含有ch_就用含有ch_的字符串替换商户订单号
				}

				String a6 = row3.split(",")[configInf.getPay_date_position() - 1].trim();// 获取发生时间列
				String a8 = row3.split(",")[configInf.getPay_amount_position() - 1].trim();// 获取收入金额列
				String a9 = row3.split(",")[configInf.getRefund_amount_position() - 1].trim();// 获取支出金额列
				String a10 = row3.split(",")[configInf.getComment_position() - 1].trim();// 获取备注列
				if (Double.parseDouble(a9) != 0) {
					a8 = a8.replace(a8, a9);
				}
				if (a8.contains("-")) {
					a8 = a8.substring(1);
				}
				String a12 = row3.split(",")[configInf.getFund_type_position() - 1];// 获取业务类型列
				Integer s13 = null;
				if ("在线支付".equals(a12)) {// 支付设为1，退款设为2
					s13 = 1;
				} else {
					s13 = 2;
				}

				List<Object> list = new ArrayList<Object>();
				list.add(a1);
				list.add(a6);
				list.add(s13);
				list.add(a8);
				list.add("0");
				list.add(configInf.getChannel_name());
				list.add(a10);
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
