package com.citic.factory;

import com.citic.factory.impl.AlipayPayFileHandle;
import com.citic.factory.impl.WXPayFileHandle;
import com.citic.factory.impl.WXScanCodePayFileHandle;
import com.citic.factory.impl.WalletBankPayFileHandle;
import com.citic.factory.inf.IPayFileHandle;

/**
 * 支付文件处理工厂类
 * @author jial
 *
 */
public class PayFileHandleFactory {

	private PayFileHandleFactory(){
	}
	/**
	 * 
	 *方法：根据不同支付方式获取支付文件处理实体类
	 *创建时间：2018年1月22日
	 *创建者：jial
	 */
	public static IPayFileHandle getPayFileHandleImpl(String payWay){
		IPayFileHandle payFileHandle = null;
		switch (payWay) {
			case "支付宝": payFileHandle = new AlipayPayFileHandle(); break;
			case "微信": payFileHandle = new WXPayFileHandle(); break;
			case "微信扫码": payFileHandle = new WXScanCodePayFileHandle(); break;
			case "国安付": payFileHandle = new WalletBankPayFileHandle(); break;
		}
		return payFileHandle;
	}
}
