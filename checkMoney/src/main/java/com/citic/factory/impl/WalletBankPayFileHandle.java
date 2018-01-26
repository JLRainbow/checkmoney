package com.citic.factory.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.citic.entity.ConfigInf;
import com.citic.entity.WalletAccountConfig;
import com.citic.factory.inf.IPayFileHandle;
import com.citic.util.CsvUtil;
/**
 * 国安付支付文件处理
 * @author jial
 *
 */
public class WalletBankPayFileHandle implements IPayFileHandle {

	@Override
	public List<Object> getPayFileHandle(ConfigInf configInf,CsvUtil csvUtil) throws IOException {
		return null;
	}

	@Override
	public List<Object> getPayFileHandle(WalletAccountConfig configInf, ArrayList<ArrayList<Object>> result) throws IOException {
		ArrayList<Object> dataList = new ArrayList<Object>();
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).size(); j++) {
				if (i >= configInf.getStart_line() - 1) {
					if (j == configInf.getTransaction_type_position() - 1) {
						String a1 = result.get(i).get(configInf.getTransaction_type_position() - 1).toString();
						if ("1".equals(a1) || "2".equals(a1)) {
							String a3 = result.get(i).get(configInf.getPlatform_order_position() - 1).toString();
							String a4 = result.get(i).get(configInf.getTransactionNamePosition() - 1).toString();
							String channelName = configInf.getChannel_name();
							if ("银行卡支付".equals(a4)) {
								a3 = result.get(i).get(configInf.getPlatformUoionPayOrderPosition() - 1).toString();
								channelName = "银行卡支付";
							}
							String a5 = result.get(i).get(configInf.getAmount_position() - 1).toString();
							String a8 = result.get(i).get(configInf.getOrder_finish_date_position() - 1).toString();

							ArrayList<Object> list = new ArrayList<Object>();
							list.add(a3);
							list.add(this.formatDate(a8));
							list.add(a1);
							list.add(this.fromFenToYuan(a5));
							list.add("0");
							list.add(channelName);
							dataList.add(list);

						}
					}
				}
			}
		}
		return dataList;
	}

	/**
	 * 日期格式转换
	 */
	private String formatDate(String str) {
		String[] split = str.split("/");
		StringBuilder sb = new StringBuilder();
		sb.append(split[2]);
		return sb.insert(4, "-" + split[1]).insert(4, "-" + split[0]).toString();
	}

	/**
	 * 分转换为元
	 * 
	 */
	private String fromFenToYuan(final String fen) {
		String yuan = "";
		final int MULTIPLIER = 100;
		yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		return yuan;
	}
}
