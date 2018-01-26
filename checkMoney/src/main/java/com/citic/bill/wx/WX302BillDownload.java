package com.citic.bill.wx;

import java.io.IOException;

import com.citic.bill.IBillDown;
import com.citic.bill.util.ConfigUtil;

public class WX302BillDownload extends WXBillDownload implements IBillDown{
	
  public static void main(String[]args) throws Exception {

  	WX302BillDownload wX302BillDownload = new WX302BillDownload();
  	wX302BillDownload.billDownload();
  }

	
	public  void billDownload() throws IOException{
		super.billDownload(ConfigUtil.WX_APP_ID_302, ConfigUtil.WX_MCH_ID_302, ConfigUtil.WX_API_KEY_302);
	}
	
}
