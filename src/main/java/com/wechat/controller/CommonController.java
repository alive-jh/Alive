package com.wechat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.service.RedisService;

@Controller
@RequestMapping("api")
public class CommonController {

	@Resource
	RedisService redisService;
	
	@RequestMapping("saveLabelIndex")
	@ResponseBody
	public String saveLabelIndex(HttpServletRequest request,Integer index){
		request.getSession().setAttribute("labelIndex", index);
		return "success";
	}
}
