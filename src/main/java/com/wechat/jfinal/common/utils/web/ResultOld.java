package com.wechat.jfinal.common.utils.web;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResultOld {

	public static final Integer codes[] = { 200, 201, 202, 304, 404, 500,203,204,205,206 };

	public static final String msg[] = { "OK", "Parameters Lack", "Parameters Type Error", "Insufficient permissions",
			"Data Not Found", "Server Error","参数无效","帐号或密码错误","未登录","该帐号已被注册" };

	public static Map<Integer, String> status;

	static {

		if (status == null) {
			status = new HashMap<Integer, String>();
			for (int i = 0; i < codes.length; i++)
				status.put(codes[i], msg[i]);
		}
		
	}
	public static void ok(Controller controller) {
		controller.renderJson(result(200));
	}
	
	public static void error(Integer code, Controller controller) {
		controller.renderJson(result(code));
	}
	
	public static void error(Integer code,String msg,Controller controller) {
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("msg", msg);
		controller.renderJson(result);
	}
	
	public static void error(Integer code,String msg,Object data,Controller controller) {
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", data);
		controller.renderJson(result);
	}
	
	
	public static void ok(Object obj,Controller controller){
		JSONObject result = result(200);
		
		if(obj ==null){
			controller.renderJson(result);
			return;
		}else if (obj instanceof Page){
			result.put("data", makeupPage((Page)obj));
		}else if(obj instanceof HashMap){
			result.put("data", makeupMap( (HashMap) obj));
		}else if(obj instanceof List){
			result.put("data", makeupList((List) obj));
		}else {
			result.put("data", obj);
		}
		controller.renderJson(result);
	}
	
	private static Object makeupMap(HashMap<String,Object> map) {
		JSONObject data = new JSONObject();
		
		for(String key:map.keySet()){
			if(map.get(key) instanceof Page){
				data.put(key, makeupPage((Page) map.get(key)));
			}else if(map.get(key) instanceof List){
				data.put(key, makeupList((List) map.get(key)));
			}else{
				data.put(key, map.get(key));
			}
		}
		return data;
	}

	public static Object makeupPage(Page page) {
		
		JSONObject data = new JSONObject();
		JSONArray list = new JSONArray();
		if(page!=null && page.getList().size()>0){
			if((page.getList().get(0)) instanceof Model){
				return page;
			}else if((page.getList().get(0)) instanceof Record){
				
				data.put("pageNumber", page.getPageNumber());//页码
				data.put("pageSize", page.getPageSize());//单页大小
				data.put("totalPage", page.getTotalPage());//总页数
				data.put("totalRow", page.getTotalRow());//数据总数
				data.put("isFirstPage", page.isFirstPage());//是否第一页
				data.put("isLastPage", page.isLastPage());//是否最后一页
				for(Object obj:page.getList()){
					list.add(toJson(((Record)obj).getColumns()));
				}
				
			}
			
		}
		data.put("list", list);
		return  data;
		
	}

	public static JSONObject toJson(Map<String,Object> map){
		
		JSONObject json = new JSONObject();
		
		for(String key:map.keySet()){
			if(null!=map.get(key)){
				if(map.get(key) instanceof Timestamp){
					json.put(key, ((Timestamp)map.get(key)).getTime());
				}else{
					json.put(key, map.get(key));
				}
			}else if(null==map.get(key)){
				//System.out.println(key+"----"+map.get(key));
				json.put(key, "");
			}
				
		}
		return json;
	}

	public static JSONObject result(int code){
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("msg", status.get(code));
		return result;
	}
	

	private static Object makeupList(List list) {
		
		JSONArray jsonArray = new JSONArray();
		for(Object obj:list){
			if(obj instanceof Record){
				jsonArray.add(toJson(((Record)obj).getColumns()));
			}else{
				return list;
			}
		}
		return jsonArray;
	}

	

}
