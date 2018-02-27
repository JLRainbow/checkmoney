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
 * ΢�����ض��˵�
 * @author jial
 *
 */
public class WXBillDownload {

	private static Logger logger = Logger.getLogger(WXBillDownload.class);
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();

    protected Map<String, Object> billDownload(String appid,String mch_id,String appKey,String billDate) throws IOException{
        SortedMap<Object,Object> parameters =new TreeMap<Object,Object>();
        //���ñش�����
        parameters.put("appid",appid);
        parameters.put("mch_id",mch_id);
        parameters.put("nonce_str",WXPayCommonUtil.CreateNoncestr());
        parameters.put("bill_date",billDate);//���ض��˵������ڣ���ʽ��20140603�����ڲ���Ϊ���졣
        //bill_type:ALL���ص������ж�����Ϣ,Ĭ��ֵSUCCESS���ص��ճɹ�֧���Ķ�����REFUND�����ص����˿��
        parameters.put("bill_type","ALL");
        parameters.put("sign", WXPayCommonUtil.createSign("utf-8", parameters,appKey));
        try {
			//������ת����xml��ʽ��������
			String reuqestXml =WXPayCommonUtil.getRequestXml(parameters);
			String result=CommonUtil.httpsRequest(ConfigUtil.WX_DOWNLOAD_BILL_URL, "POST",reuqestXml);

			if(result.startsWith("<xml>")){//��ѯ����Ϊ����ʱ��������Ϣ��ʾ������Ч
				logger.error("wx invoking billdown error ==>>"+result);
				resultMap.put("success", false);
			    resultMap.put("errMsg", "΢�ŵ���ʧ��");
			    return resultMap;
			}else {  
				logger.info(" ======================>>  wx invoking billdown success ");
				String[] resultArray = result.replace("����", "����%").split("%");
				List<String> dataList = new ArrayList<String>();
				for (int i = 0; i < resultArray.length; i++) {
					dataList.add(resultArray[i]);
				}
				dataList.add("`");//����csv�����һ����һ��` ��ֹ��ȡcsv���ȡһ��
				CsvUtil csvUtil = new CsvUtil();
				String filePath = FileUtil.getBillPath();
				File file =new File(filePath);    
				//����ļ��в������򴴽�    
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
				    resultMap.put("errMsg", "΢���˵�����csv�ļ�����");
				    return resultMap;
				}
			}
		} catch (Exception e) {
			logger.error("wx httpsRequest() error ==>>"+e);
			resultMap.put("success", false);
		    resultMap.put("errMsg", "΢������ӿ��쳣");
		    return resultMap;
		}
    }
}


