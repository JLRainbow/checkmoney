package com.citic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.citic.bean.vo.IdNoWalletStatus;
import com.citic.bean.vo.WagesAccountValidationVo;

import net.sf.json.JSONObject;

public class HttpClientUtil {

	private static final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private HttpClientUtil(){};
	
	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public static String executePostWithJson(String url, String json) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		
		String respContent = null;

		StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		HttpResponse resp = httpClient.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity httpEntity = resp.getEntity();
			respContent = EntityUtils.toString(httpEntity, "UTF-8");
		}
		return respContent;
	}
	
	public static String executePost(String url,Map<String,String> paramsMap,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = HttpClientUtil.getHttpClient(); 
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = paramsMap.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	
	public static void main(String[] args) throws Exception {
		String url = "http://10.16.70.80:8080/api/salary/info";
		String url2 = "http://10.16.70.80:8080/api/salary/payroll";
		
		Map<String, String> map = new HashMap<String, String>();
		
		Set<IdNoWalletStatus> idNoSet = new HashSet<IdNoWalletStatus>();
		IdNoWalletStatus idNo = new IdNoWalletStatus();
		idNo.setIdno("123456789456789");
		idNo.setThirdcustid((byte)1);
		idNoSet.add(idNo);
		
		WagesAccountValidationVo vo = new WagesAccountValidationVo();
		vo.setCount(1);
		vo.setData(idNoSet);
		
		map.put("json", JSONUtil.getInstance().obj2Json(vo));
		
		String httpPostWithJSON = HttpClientUtil.executePost(url, map, "utf8");
		System.out.println(httpPostWithJSON);
	}
}
