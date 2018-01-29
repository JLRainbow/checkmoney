package com.citic.bill.wx;

import java.io.IOException;

import com.citic.bill.IBillDown;
import com.citic.bill.util.ConfigUtil;
/**
 * 微信302（微信）获取账单类
 * @author jial
 *
 */
public class WX302BillDownload extends WXBillDownload implements IBillDown{
	

	
	public  void billDownload(String billDate) throws IOException{
		super.billDownload(ConfigUtil.WX_APP_ID_302, ConfigUtil.WX_MCH_ID_302, ConfigUtil.WX_API_KEY_302,billDate);
	}
	
}
