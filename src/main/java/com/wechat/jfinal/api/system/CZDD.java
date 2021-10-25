package com.wechat.jfinal.api.system;

import com.jfinal.core.Controller;
import com.wechat.jfinal.common.utils.web.Result;

import net.sf.json.JSONObject;

public class CZDD extends Controller {
	public void registerControl(){
		int status = 1; //没有登陆页面
		JSONObject result = new JSONObject();
		result.put("status", status);
		Result.ok(result,this);
	}
}
