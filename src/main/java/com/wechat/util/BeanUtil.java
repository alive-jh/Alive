package com.wechat.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class BeanUtil {

	private static ApplicationContext appContext; 
	
	public static Object getBeanByName(String beanName)throws Exception
	{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
		return appContext.getBean(beanName);
	}
}
