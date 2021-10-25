package com.wechat.interceptor;

import com.wechat.entity.User;
import com.wechat.pay.util.MD5Util;
import com.wechat.util.MyCookie;
import com.wechat.util.RedisKeys;
import com.wechat.util.RedisUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Logger;

public class CommonInterceptor implements HandlerInterceptor {

	private Logger log = Logger.getAnonymousLogger();

	private String APP_SECRET = "ndf7Jyrx";

	public CommonInterceptor() {

	}

	private String mappingURL;// 利用正则映射到需要拦截的路径

	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// //System.out.println("==============执行顺序: 1、preHandle================");
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();
		String path = requestURI + "?" + queryString;

		String userId = RedisKeys.REDIS_USER
				+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User user = RedisUtil.getUser(userId);

//		Boolean flag = true;
		Boolean flag = false;

		// 判断如果没有取到用户信息,就跳转到登陆页面
		if (user.getId() != null) {
			flag = true;
		}
		if (path.lastIndexOf("login") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("eval") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("out") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("toArticle") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("member") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("payment") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("getProduct") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("mallMobileManager") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("electrism") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("knowledge") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("publicOnlineDeviceManager") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("api") != -1) {
			
//			 if(!this.validateSign(request, response)){
//				 
//				 JsonResult.JsonResultError(response, 4005);
//				 return false;
//			 }else{
//				 return true;
//			 }
			flag = true;
		}

		if (path.lastIndexOf("server") != -1) {
			// //System.out.println("path:"+path);
			flag = true;
			// if (user.getAccount() != null) {
			// return true;
			// }else{
			// JsonResult.JsonResultError(response, 10001);
			// return false;
			// }
		}

		if (path.lastIndexOf("Api") != -1) {
			flag = true;
		}
		// if (path.lastIndexOf("book") != -1) {
		// flag = true;
		// }
		if (path.lastIndexOf("bookApi") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("seaechMall") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("showMallView") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("mallBannerManager") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("mallMobileIndex") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("app") != -1) {
			flag = true;
		}
		if (path.lastIndexOf("applet") != -1) {
			flag = true;
		}
		//活动
		if (path.lastIndexOf("campaign") != -1) {
			flag = true;
		}
		//测评
		if (path.lastIndexOf("eval") != -1) {
			flag = true;
		}
		if(path.lastIndexOf("file")!= -1){
			
			flag = true;
		}
		if(path.lastIndexOf("activity")!= -1){
			
			flag = true;
		}	
		
		if (!flag) {
			response.sendRedirect("/wechat/login.jsp");
		}
		return flag;
	}

	public boolean validateSign(HttpServletRequest request,
			HttpServletResponse response) {
		/**
		 * 这里要做请求头参数校验，做安全过滤
		 */
		String sign = request.getParameter("sign");
		//System.out.println(sign);
//		sign = "52272bee0519e3caa8ce601eba4a7489";
		if (sign == null) {
			return false;
		} else {
			Enumeration paraKeys = request.getParameterNames();
			String encodeStr = "";
			HashMap<String, String> paraMaps = new HashMap<String, String>();
			ArrayList<String> sortKeys = new ArrayList<String>();
			while (paraKeys.hasMoreElements()) {
				String paraKey = (String) paraKeys.nextElement();
				if (paraKey.equals("sign"))
					continue;
				if (paraKey.equals("messageId"))
					continue;
				String paraValue = request.getParameter(paraKey);
				paraMaps.put(paraKey, paraValue);
				sortKeys.add(paraKey);
				encodeStr = encodeStr + paraKey + "=" + paraValue + ",";
			}
			encodeStr = encodeStr + "app_secret=" + APP_SECRET;

			Collections.sort(sortKeys);

			String encodeStr2 = "";

			for (int i = 0; i < sortKeys.size(); i++) {
				encodeStr2 = encodeStr2 + sortKeys.get(i) + "="
						+ paraMaps.get(sortKeys.get(i)) + ",";
			}
			encodeStr2 = encodeStr2 + "app_secret=" + APP_SECRET;

			log.info("有序加密字符串1：" + encodeStr2);
			log.info("有序加密字符串2：" + sign);
			log.info("有序加密字符串3：" + MD5Util.MD5Encode(encodeStr2, "utf-8"));
			if (sign.equals(MD5Util.MD5Encode(encodeStr2, "utf-8"))) {
				return true;
			} else {
				return false;
			}
		}

	}

	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// //System.out.println("==============执行顺序: 2、postHandle================");
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		// //System.out.println("==============执行顺序: 3、afterCompletion================");
	}

}
