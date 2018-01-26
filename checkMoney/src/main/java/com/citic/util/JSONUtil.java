package com.citic.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {

	private ObjectMapper objectMapper;
	private static final JSONUtil JSONUtilInstance = new JSONUtil();;
	private static Log log = LogFactory.getLog(JSONUtil.class);

	public static JSONUtil getInstance() {
		return JSONUtilInstance;
	}

	private JSONUtil() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public <T> T json2Obj(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String obj2Json(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
     * 一个Set集合转换为json格式
     * @param jsonStr
     * @return
     */
    public static List<Map<String, Object>> parseJSONList(String jsonStr){  
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);  
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
        Iterator<JSONObject> it = jsonArr.iterator();  
        while(it.hasNext()){  
            JSONObject JSON = it.next();  
            list.add(parseJSONMap(JSON.toString()));  
        }  
        return list;  
    }  
    
    
    /**
     * 一个Set集合转换为json格式
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> parseJSONMap(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        try {
			//最外层解析  
			JSONObject json = JSONObject.fromObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				//如果内层还是数组的话，继续解析  
				if (v instanceof JSONArray) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Iterator<JSONObject> it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						JSONObject JSON = it.next();
						list.add(parseJSONMap(JSON.toString()));
					}
					map.put(k.toString(), list);
				} else {
					map.put(k.toString(), v);
				}
			} 
		} catch (Exception e) {
			map.put("exception", jsonStr);
		}
		return map;  
    }  
      
}
