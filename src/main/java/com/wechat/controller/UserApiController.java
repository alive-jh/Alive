package com.wechat.controller;

import com.wechat.entity.User;
import com.wechat.service.RedisService;
import com.wechat.service.UserService;
import com.wechat.util.JsonResult;
import com.wechat.util.MD5UTIL;
import com.wechat.util.ParameterFilter;
import com.wechat.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("api")
public class UserApiController {
	
	
	@Resource
	private UserService userService;
	
	
	
	@Resource
	private RedisService redisServer;
	
	/*
	 * 修改用户密码
	 * (缺少参数判断，如果不传参数，需要异常处理)
	 * */
	
	@RequestMapping("/updatePassWord")
	public void updatePassWord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String oldPassWord = ParameterFilter.emptyFilter("", "oldPassWord", request);
		String newPassWord = ParameterFilter.emptyFilter("", "newPassWord", request);
		String oldPassWordMd5  = MD5UTIL.encrypt(oldPassWord);
		String newPassWordMd5  = MD5UTIL.encrypt(newPassWord);
		User user  =  RedisUtil.getUserByCookie(request);
		if(oldPassWordMd5.equals(user.getPassword())){
			this.userService.updateUserPwd(user.getId().toString(), newPassWordMd5);
		}
		JsonResult.JsonResultInfo(response, "ok");
	}
	
}


 
