package com.wechat.jfinal.common.utils.web;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResult {

	public static final String codes[] = { "200", "201", "202", "304", "404", "500","203","204","207","208","209" };

	public static final String msg[] = { "OK", "Parameters Lack", "Parameters Type Error", "Insufficient permissions",
			"Data Not Found", "Server Error","参数无效","帐号或密码错误","帐号已存在","机器人已被绑定","access_token失效，请重新登陆！" };

	public static Map<String, String> status;

	static {

		if (status == null) {
			status = new HashMap<String, String>();
			for (int i = 0; i < codes.length; i++)
				status.put(codes[i], msg[i]);
		}

	}

	public static JSONObject JsonResultOK() {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		json.put("msg", status.get("200"));
		return json;
	}
	
	public static JSONObject JsonResultOK(List<Record> records) {
		List<String> data = new ArrayList<String>();
		for(Record rc:records){
			data.add(rc.toJson());
		}
		JSONObject json = new JSONObject();
		json.put("code", 200);
		json.put("msg", status.get("200"));
		json.put("data", data);
		return json;
	}
	
	public static JSONObject JsonResultOK(Object data) {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		json.put("msg", status.get("200"));
		json.put("data", data);
		return json;
	}
	
	public static JSONObject JsonResultOK(JSONArray data) {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		json.put("msg", status.get("200"));
		json.put("data", data);
		return json;
	}

	public static JSONObject JsonResultOK(Map<String, Object> dataMap) {
		JSONObject json = new JSONObject();
		for (String key : dataMap.keySet()) {
			json.put(key, dataMap.get(key));
		}
		json.put("code", 200);
		json.put("msg", status.get("200"));
		return json;
	}
	public static JSONObject OK(Object o) {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		if(o == null)
			o = new Object();
		json.put("data", o);
		json.put("msg", status.get("200"));
		return json;
	}

	public static JSONObject JsonResultOK(Page<Record> page) {
		List<String> data = new ArrayList<String>();
		
		for(Record rc:page.getList()){
			data.add(rc.toJson());
		}
		
		JSONObject json = new JSONObject();
		json.put("code", 200);//状态
		json.put("msg", status.get("200"));//描述
		json.put("pageNumber", page.getPageNumber());//页码
		json.put("pageSize", page.getPageSize());//单页大小
		json.put("totalPage", page.getTotalPage());//总页数
		json.put("totalRow", page.getTotalRow());//数据总数
		json.put("isFirstPage", page.isFirstPage());//是否第一页
		json.put("isLastPage", page.isLastPage());//是否最后一页
		json.put("data", data);//数据列表
		return json;
	}
	 
	public static JSONObject JsonResultOK(Page<Record> page,JSONObject param) {
		
		JSONObject json = new JSONObject();
		json.put("code", 200);//状态
		json.put("msg", status.get("200"));//描述
		json.put("pageNumber", page.getPageNumber());//页码
		json.put("pageSize", page.getPageSize());//单页大小
		json.put("totalPage", page.getTotalPage());//总页数
		json.put("totalRow", page.getTotalRow());//数据总数
		json.put("isFirstPage", page.isFirstPage());//是否第一页
		json.put("isLastPage", page.isLastPage());//是否最后一页
		json.put("data", param);//数据列表
		return json;
	}

	public static JSONObject JsonResultOKWithPage(Page page) {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		json.put("msg", status.get("200"));
		json.put("pageNumber", page.getPageNumber());
		json.put("pageSize", page.getPageSize());
		json.put("totalPage", page.getTotalPage());
		json.put("totalRow", page.getTotalRow());
		json.put("isFirstPage", page.isFirstPage());
		json.put("isLastPage", page.isLastPage());
		json.put("data", page.getList());
		return json;
	}

	public static JSONObject JsonResultError(int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", status.get("" + code));
		return json;
	}
	
	public static JSONObject JsonResultError(int code, Object data) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", status.get("" + code));
		json.put("data", data);
		return json;
	}
	
	public static JSONObject JsonResultError(int code, String msg) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg );
		return json;
	}

	public static JSONObject JsonResultError(int code, Map<String, Object> dataMap) {
		JSONObject json = new JSONObject();
		for (String key : dataMap.keySet()) {
			json.put(key, dataMap.get(key));
		}
		json.put("code", code);
		json.put("msg", status.get("" + code));
		return json;
	}
	
	public static JSONObject JsonResultOKwithJson(JSONObject data) {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		json.put("msg", status.get("200"));
		json.put("data", data);
		return json;
	}
	

}
