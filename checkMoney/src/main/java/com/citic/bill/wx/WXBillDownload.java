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
 * ΢�����ض��˵�
 * @author jial
 *
 */
public class WXBillDownload {


    protected void billDownload(String appid,String mch_id,String appKey,String billDate) throws IOException{
        SortedMap<Object,Object> parameters =new TreeMap<Object,Object>();
        //���ñش�����
        parameters.put("appid",appid);
        parameters.put("mch_id",mch_id);
        parameters.put("nonce_str",WXPayCommonUtil.CreateNoncestr());
        parameters.put("bill_date",billDate);//���ض��˵������ڣ���ʽ��20140603�����ڲ���Ϊ���졣
        //bill_type:ALL���ص������ж�����Ϣ,Ĭ��ֵSUCCESS���ص��ճɹ�֧���Ķ�����REFUND�����ص����˿��
        parameters.put("bill_type","ALL");
        parameters.put("sign", WXPayCommonUtil.createSign("utf-8", parameters,appKey));
        //������ת����xml��ʽ��������
        String reuqestXml =WXPayCommonUtil.getRequestXml(parameters);
        String result=CommonUtil.httpsRequest(ConfigUtil.WX_DOWNLOAD_BILL_URL, "POST",reuqestXml);

        if(result.startsWith("<xml>")){//��ѯ����Ϊ����ʱ��������Ϣ��ʾ������Ч
            System.out.println(result);
            System.out.println("���ض��˵�ʧ��");
        }else {  
        	String[] resultArray = result.replace("����", "����%").split("%");
        	List<String> dataList = new ArrayList<String>();
        	for (int i = 0; i < resultArray.length; i++) {
        		dataList.add(resultArray[i]);
			}
        	CsvUtil csvUtil = new CsvUtil();
        	String filePath = FileUtil.getBillPath();
        	File file =new File(filePath);    
    		//����ļ��в������򴴽�    
    		if (!file .exists()){  
    			System.out.println("dill�ļ��в�����");
    		    file .mkdirs();    
    		} 
        	csvUtil.createCsv(dataList, filePath+billDate+"_������ϸ.csv");
//        	System.out.println(str.replace("%", "%\r\n"));
        	
       }
    }
}


