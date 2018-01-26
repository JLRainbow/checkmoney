package com.citic.test;

public class Test2 {

	public static void main(String[] args) {
		String[] dBpayPlatform = {"alipay","alipay_qr"};
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
		System.out.println(parseDBpayPlatform);
	}
	
}
