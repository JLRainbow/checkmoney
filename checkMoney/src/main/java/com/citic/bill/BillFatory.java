package com.citic.bill;

import com.citic.bill.alipay.AlipayBillDownload;
import com.citic.bill.wx.WX302BillDownload;
import com.citic.bill.wx.WX401BillDownload;
/**
 * 下载账单工厂
 * @author jial
 *
 */
public class BillFatory {

	public static IBillDown getBillDownloadImp(String payWay){
		IBillDown billDown = null;
		switch (payWay) {
			case "alipay": billDown = new AlipayBillDownload(); break;
			case "wx_302":  billDown = new WX302BillDownload(); break;
			case "wx_401":  billDown = new WX401BillDownload(); break;
			default : throw new RuntimeException("不支持该支付方式导入");
		}
		return billDown;
	}
}
