package com.wechat.controller;

import com.wechat.util.ParameterFilter;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("log")
public class LoggerController {
	
	//获取请求的IP地址
	public String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	} 
	
	/*
	 * 接口访问错误信息上传
	 * */
	@RequestMapping("request/upload")
	public void soundCollect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//	    MongoHandle mongoHandle = new MongoHandle("fandou","log");
	    JSONObject parameter = new JSONObject();

	    parameter.put("url",ParameterFilter.emptyFilter("1", "url", request));
	    parameter.put("method",ParameterFilter.emptyFilter("1", "method", request));
	    parameter.put("reason",ParameterFilter.emptyFilter("1", "reason", request));
	    parameter.put("userId", ParameterFilter.emptyFilter("fandou", "userId", request));
	    parameter.put("userType", ParameterFilter.emptyFilter("fandou", "userType", request));
	    parameter.put("uploadTime", ParameterFilter.emptyFilter("1", "uploadTime", request));
	    parameter.put("clientVersion", ParameterFilter.emptyFilter("1", "clientVersion", request));
	    parameter.put("clientType", ParameterFilter.emptyFilter("1", "clientType", request));
	    parameter.put("remoteIp", getRemortIP(request));
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    parameter.put("insertDate", df.format(new Date()));
//	    mongoHandle.saveDocument(parameter);
	}


	
}


 
