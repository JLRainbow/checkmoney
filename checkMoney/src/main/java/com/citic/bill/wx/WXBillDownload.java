package com.citic.bill.wx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.citic.bill.alipay.AlipayBillDownload;
import com.citic.bill.util.CommonUtil;
import com.citic.bill.util.ConfigUtil;
import com.citic.bill.util.CsvUtil;
import com.citic.bill.util.FileUtil;
import com.citic.bill.util.WXPayCommonUtil;

/**
 * 微信下载对账单
 * @author jial
 *
 */
public class WXBillDownload {

	private static Logger logger = Logger.getLogger(WXBillDownload.class);
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();

    protected Map<String, Object> billDownload(String appid,String mch_id,String appKey,String billDate) throws IOException{
        SortedMap<Object,Object> parameters =new TreeMap<Object,Object>();
        //设置必传参数
        parameters.put("appid",appid);
        parameters.put("mch_id",mch_id);
        parameters.put("nonce_str",WXPayCommonUtil.CreateNoncestr());
        parameters.put("bill_date",billDate);//下载对账单的日期，格式：20140603，日期不可为当天。
        //bill_type:ALL返回当日所有订单信息,默认值SUCCESS返回当日成功支付的订单。REFUND，返回当日退款订单
        parameters.put("bill_type","ALL");
        parameters.put("sign", WXPayCommonUtil.createSign("utf-8", parameters,appKey));
        try {
			//将参数转换成xml格式发送请求
			String reuqestXml =WXPayCommonUtil.getRequestXml(parameters);
			String result=CommonUtil.httpsRequest(ConfigUtil.WX_DOWNLOAD_BILL_URL, "POST",reuqestXml);

			if(result.startsWith("<xml>")){//查询日期为当天时，错误信息提示日期无效
				logger.error("wx invoking billdown error ==>>"+result);
				resultMap.put("success", false);
			    resultMap.put("errMsg", "微信调用失败");
			    return resultMap;
			}else {  
				logger.info(" ======================>>  wx invoking billdown success ");
				String[] resultArray = result.replace("费率", "费率%").split("%");
				List<String> dataList = new ArrayList<String>();
				for (int i = 0; i < resultArray.length; i++) {
					dataList.add(resultArray[i]);
				}
				dataList.add("`");//生成csv的最后一行是一个` 防止读取csv多读取一行
				CsvUtil csvUtil = new CsvUtil();
				String filePath = FileUtil.getBillPath();
				File file =new File(filePath);    
				//如果文件夹不存在则创建    
				if (!file .exists()){  
					logger.info("================>> bill path is create success");
				    file .mkdirs();    
				} 
				try {
					csvUtil.createCsv(dataList, filePath+billDate+"_wx.csv");
					logger.info("================>> wx createCsv success");
					resultMap.put("success", true);
					return resultMap;
				} catch (Exception e) {
					logger.error("wx createCsv error ==>>",e);
					resultMap.put("success", false);
				    resultMap.put("errMsg", "微信账单生成csv文件错误");
				    return resultMap;
				}
			}
		} catch (Exception e) {
			logger.error("wx httpsRequest() error ==>>"+e);
			resultMap.put("success", false);
		    resultMap.put("errMsg", "微信请求接口异常");
		    return resultMap;
		}
    }
}


