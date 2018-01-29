package com.citic.bill.alipay;

import java.io.IOException;
import java.util.Date;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.citic.bill.IBillDown;
import com.citic.bill.util.ConfigUtil;
import com.citic.bill.util.FileUtil;
/**
 * 支付宝下载对账单
 * @author jial
 *
 */
public class AlipayBillDownload implements IBillDown{
	//初始化实例请求对象 
    public static AlipayClient alipayClient = new DefaultAlipayClient(ConfigUtil.ALIPAY_DOWNLOAD_BILL_URL, 
    		ConfigUtil.ALIPAY_APP_ID, ConfigUtil.ALIPAY_APP_PRIVATE_KEY, "json", "GBK",  ConfigUtil.ALIPAY_PUBLIC_KEY,"RSA"); 
    
    public  void  billDownload (String billDate)  throws IOException{ 
        
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        JSONObject json = new JSONObject();
        json.put("bill_type", "signcustomer");
        //昨天的数据 new DateTime().minusDays(1).toString("yyyy-MM-dd")
        json.put("bill_date", billDate);
        request.setBizContent(json.toString());
                
		AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
			System.out.println(response.getBillDownloadUrl());

		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
         if(response.isSuccess()){ 
        	// 将接口返回的对账单下载地址传入urlStr
			String urlStr = response.getBillDownloadUrl();
			
			// 开始下载
			try {
				//读取配置路径
				String filePath = FileUtil.getBillPath();
				// 指定希望保存的文件路径
				String newZip = filePath + new Date().getTime() + ".zip";
				FileUtil.downloadNet(urlStr, newZip);
				// 解压到指定目录
				FileUtil.unZip(newZip, filePath);
	            // 删除文件
//	            for (File file : fs) {
//	                file.delete();
//	            }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			  System.out.println("调用成功");
		}else {
             System.out.println("调用失败");
        }
         
         System.out.println(JSON.toJSONString(response));
       } 
}
