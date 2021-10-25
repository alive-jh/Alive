package com.wechat.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.util.Keys;

public class VideoVoteInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean flag = false;
		
		Object videoVoteMemberId = request.getAttribute("videoVoteMemberId");
		
		if(videoVoteMemberId==null){
			
			String state = request.getServletPath();
			
			String params = request.getQueryString();
			
			if(params!=null){
				state+="?"+params;
			}
			
			StringBuffer wxUrl = new StringBuffer();
			wxUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Keys.APP_ID+"&redirect_uri=");
			wxUrl.append("http://wechat.fandoutech.com.cn/wechat/videoOauth2/login&response_type=code&scope=snsapi_userinfo&");
			wxUrl.append("state="+URLEncoder.encode(state, "UTF-8")+"#wechat_redirect");
			
			response.sendRedirect(wxUrl.toString());
			
		}else{
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
