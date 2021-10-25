package com.wechat.util;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class JsonResult {

	public static void JsonResultError(HttpServletResponse response,
			int errorCode) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", errorCode);
			result.put("error", error);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void JsonResultInfo(HttpServletResponse response, Object data) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", 1);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonTimeStampValueProcessor());
			result.accumulate("data", data, jsonConfig);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void JsonResultInfoNew(HttpServletResponse response, Object data,Object obj) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", 1);
			result.put("unread", obj);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonTimeStampValueProcessor());
			result.accumulate("data", data, jsonConfig);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static void JsonResultInfoDate(HttpServletResponse response, Object data) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", 1);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor());
			result.accumulate("data", data, jsonConfig);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static String JsonResultToStr(Object data) {
		JSONObject result = new JSONObject();
		try {
			result.put("success", 1);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonTimeStampValueProcessor());
			result.accumulate("data", data, jsonConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();

	}
	
	public static void JsonResultOK(HttpServletResponse response,Map<String,Object> map) {
		try {
			JSONObject result = new JSONObject();
			result.put("code", 200);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonTimeStampValueProcessor());
			 for (String key : map.keySet()) {
				   //System.out.println("key= "+ key + " and value= " + map.get(key));
				   result.accumulate(key, map.get(key), jsonConfig);
				  }
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void JsonResultERROR(HttpServletResponse response,int code,String msg,Map<String,Object> map) {
		try {
			JSONObject result = new JSONObject();
			result.put("code", code);
			result.put("msg", msg);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonTimeStampValueProcessor());
			 for (String key : map.keySet()) {
				   //System.out.println("key= "+ key + " and value= " + map.get(key));
				   result.accumulate(key, map.get(key), jsonConfig);
				  }
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
