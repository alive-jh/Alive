package com.wechat.controller;

import com.wechat.util.JsonResult;
import io.goeasy.GoEasy;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goEasy")
public class GoEasyController {
	
	public static final  String appKey = "BC-82bffd0bf6bd4219a509ed45eb162f78";//appKey
	
	@RequestMapping(value="/sendMessage")
	public void sendMessage(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		try {
			
		
			String channel = request.getParameter("channel");
			String message = request.getParameter("message");
			GoEasy goEasy = new GoEasy(request.getRequestURI().toString(),GoEasyController.appKey);
			goEasy.publish(channel,message);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", 0);
			JsonResult.JsonResultInfo(response, jsonObj);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	

}
