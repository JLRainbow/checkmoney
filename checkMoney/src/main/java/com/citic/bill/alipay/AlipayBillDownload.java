package com.citic.bill.alipay;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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
	
	private static Logger logger = Logger.getLogger(AlipayBillDownload.class);
	//初始化实例请求对象 
    public static AlipayClient alipayClient = new DefaultAlipayClient(ConfigUtil.ALIPAY_DOWNLOAD_BILL_URL, 
    		ConfigUtil.ALIPAY_APP_ID, ConfigUtil.ALIPAY_APP_PRIVATE_KEY, "json", "GBK",  ConfigUtil.ALIPAY_PUBLIC_KEY,"RSA"); 
    
    public  Map<String, Object>  billDownload (String billDate)  throws IOException{ 
        
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONObject json = new JSONObject();
        json.put("bill_type", "signcustomer");
        //昨天的数据 new DateTime().minusDays(1).toString("yyyy-MM-dd")
        json.put("bill_date", billDate);
        request.setBizContent(json.toString());
                
		AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
			logger.debug("response.getBillDownloadUrl() ==>>"+response.getBillDownloadUrl());
			
			 if(response.isSuccess()){
				 	logger.debug("alipay invoking billdown success ==>>"+JSON.toJSONString(response));
		        	// 将接口返回的对账单下载地址传入urlStr
					String urlStr = response.getBillDownloadUrl();
					
					// 开始下载
					try {

						//指定希望保存的文件路径
						String newZip = FileUtil.getBillPath() + new Date().getTime() + ".zip";
						//创建这次下载的文件夹
						File thisTimeFile =new File(FileUtil.getBillPath());    
			    		//如果文件夹不存在则创建    
			    		if (!thisTimeFile .exists()){  
			    			thisTimeFile .mkdirs();  
			    		    logger.debug("================>> thisTimeFile path is create success");
			    		} 
						FileUtil.downloadNet(urlStr, newZip);
						// 解压到指定目录
						FileUtil.unZip(newZip, FileUtil.getBillPath(),false);
						logger.debug("================>> bill path unZip success");
						resultMap.put("success", true);
						return resultMap;
					} catch (Exception e) {
						logger.error("alipay zip error ==>>",e);
						resultMap.put("success", false);
						resultMap.put("errMsg", "支付宝对账单文件解压缩失败");
			            return resultMap;
					}
				}else {
					logger.debug("alipay invoking billdown error ==>>"+JSON.toJSONString(response));
					resultMap.put("success", false);
		            resultMap.put("errMsg", "支付宝调用失败");
		            return resultMap;
		        }
		} catch (AlipayApiException e) {
			logger.error("alipayClient execute error ==>>",e);
			resultMap.put("success", false);
            resultMap.put("errMsg", "支付宝请求接口异常");
            return resultMap;
		}
        
         
       } 
}
