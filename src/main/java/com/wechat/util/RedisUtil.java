package com.wechat.util;

import com.wechat.entity.Modular;
import com.wechat.entity.User;
import com.wechat.service.RedisService;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RedisUtil {
	
	private static RedisService redisService;
	
	/**
	 * 管理后台获取当前登录用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static User getUser(String userId) throws Exception
	{
		redisService = (RedisService)BeanUtil.getBeanByName("redisService");
		
		User user = new User();
		user = (User) redisService.getObject(userId,User.class);
		return user;
		
	}
	
	/**
	 * JSP页面获取当前登录用户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static User getUserByCookie(HttpServletRequest request) throws Exception
	{
		redisService = (RedisService)BeanUtil.getBeanByName("redisService");
		String userId = RedisKeys.REDIS_USER+MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User user = new User();
		user = (User) redisService.getObject(userId,User.class);
		return user;
		
	}

	
	
	
	/**
	 * 获取当前登录用户菜单列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List getModulaByCookie(HttpServletRequest request) throws Exception
	{
		redisService = (RedisService)BeanUtil.getBeanByName("redisService");
		String userId = RedisKeys.REDIS_MODULA+MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		List<T> list = redisService.getList(userId,Modular.class);
		
		return list;
		
	}
	
	
	
	/**
	 * JSP页面根据key获取redis数据
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getRedisStr(String key) throws Exception
	{
		redisService = (RedisService)BeanUtil.getBeanByName("redisService");
		return redisService.get(key);
		
	}
	
	public static void main(String[] args) throws Exception{
		
		
			RedisUtil redisUtil = new RedisUtil();
			
			//System.out.println("name = "+RedisUtil.getRedisStr("key"));
			
			
			
		
	}
}
