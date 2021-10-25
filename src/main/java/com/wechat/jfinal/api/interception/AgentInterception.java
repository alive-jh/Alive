package com.wechat.jfinal.api.interception;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import net.sf.json.JSONObject;

public class AgentInterception implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		
		HttpSession session = inv.getController().getSession();
		System.out.println(session.getId());
		if(session.getAttribute("miniapp_user")==null){
			JSONObject json = new JSONObject();
			json.put("code", 20401);
			json.put("msg", "未登录，拒绝访问");
			inv.getController().renderJson(json);
		}else{
			inv.invoke();
		}
		
	}
	

}
