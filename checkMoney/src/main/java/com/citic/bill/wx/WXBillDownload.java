package com.citic.bill.wx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import java.util.TreeMap;

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


    protected void billDownload(String appid,String mch_id,String appKey,String billDate) throws IOException{
        SortedMap<Object,Object> parameters =new TreeMap<Object,Object>();
        //设置必传参数
        parameters.put("appid",appid);
        parameters.put("mch_id",mch_id);
        parameters.put("nonce_str",WXPayCommonUtil.CreateNoncestr());
        parameters.put("bill_date",billDate);//下载对账单的日期，格式：20140603，日期不可为当天。
        //bill_type:ALL返回当日所有订单信息,默认值SUCCESS返回当日成功支付的订单。REFUND，返回当日退款订单
        parameters.put("bill_type","ALL");
        parameters.put("sign", WXPayCommonUtil.createSign("utf-8", parameters,appKey));
        //将参数转换成xml格式发送请求
        String reuqestXml =WXPayCommonUtil.getRequestXml(parameters);
        String result=CommonUtil.httpsRequest(ConfigUtil.WX_DOWNLOAD_BILL_URL, "POST",reuqestXml);

        if(result.startsWith("<xml>")){//查询日期为当天时，错误信息提示日期无效
            System.out.println(result);
            System.out.println("下载对账单失败");
        }else {  
        	String[] resultArray = result.replace("费率", "费率%").split("%");
        	List<String> dataList = new ArrayList<String>();
        	for (int i = 0; i < resultArray.length; i++) {
        		dataList.add(resultArray[i]);
			}
        	CsvUtil csvUtil = new CsvUtil();
        	String filePath = FileUtil.getBillPath();
        	File file =new File(filePath);    
    		//如果文件夹不存在则创建    
    		if (!file .exists()){  
    			System.out.println("dill文件夹不存在");
    		    file .mkdirs();    
    		} 
        	csvUtil.createCsv(dataList, filePath+billDate+"_账务明细.csv");
//        	System.out.println(str.replace("%", "%\r\n"));
        	
       }
    }
}


