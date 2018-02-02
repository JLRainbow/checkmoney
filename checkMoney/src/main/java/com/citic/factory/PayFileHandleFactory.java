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
			case "alipay": payFileHandle = new AlipayPayFileHandle(); break;
			case "wx_302": payFileHandle = new WXPayFileHandle(); break;
			case "wx_401": payFileHandle = new WXScanCodePayFileHandle(); break;
			case "gapay": payFileHandle = new WalletBankPayFileHandle(); break;
		}
		return payFileHandle;
	}
}
