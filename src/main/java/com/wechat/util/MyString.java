package com.wechat.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyString {
	public static boolean isEquals(String tagValue,String value)
	{
		if(isEmpty(tagValue) || isEmpty(value))
			return false;
		else if(tagValue.equals(value))
			return true;
		else
			return false;
	}
	/**
	 * 判断对象是否为空
	 * Object = null
	 * String = "","null","undefined"
	 * List size=0
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object)
	{
		if(object instanceof String){
			if(object == null||object.toString().trim().equals("")||object.toString().trim().equals("null")||object.toString().equals("undefined"))
				return true;
		}else if(object instanceof List<?>){
			if(object == null ||((List<?>)object).size()==0)
				return true;
		}else if(object == null){
			return true;
		}
		return false;
	}
	
	// 从request中获取值
	public static String getValueFromRequest(HttpServletRequest request, String name){
		return getValueFromRequest(request, name, "");
	}
	
	public static String getValueFromRequest(HttpServletRequest request, String name, String defValue){
		if( isEmpty(request.getParameter(name)) ){
			return defValue;
		}else{
			return request.getParameter(name).toString();
		}
	}
	
	// 从session中获取值
	public static String getValueFromSession(HttpServletRequest request, String name){
		return getValueFromSession(request, name, "");
	}
	
	public static String getValueFromSession(HttpServletRequest request, String name, String defValue){
		if( isEmpty(request.getSession().getAttribute(name)) ){
			return defValue;
		}else{
			return request.getSession().getAttribute(name).toString();
		}
	}
}
