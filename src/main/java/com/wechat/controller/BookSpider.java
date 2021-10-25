package com.wechat.controller;

import com.wechat.spider.Getinfo;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("book")
public class BookSpider {
	
	@RequestMapping("/spider")
	public void Spider(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ISBN = request.getParameter("key");
		JSONObject data = new JSONObject();
		Getinfo temp = new Getinfo();
		data = Getinfo.getInfo(ISBN);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(data);
	}
}
