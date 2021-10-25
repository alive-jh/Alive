package com.wechat.util;

import javax.servlet.http.HttpServletRequest;

public class ParameterFilter {
	
	
	public static String emptyFilter(String def,String key,HttpServletRequest request){
		String result="";
		if(null!=request.getParameter(key)&&!"".equals(request.getParameter(key))){
			result=request.getParameter(key);
		}else{
			result=def;
		}
		return result;
	}

	public static String emptyFilter(String def,String value){
		String result="";
		if(null!=value && value != ""){
			result=value;
		}else{
			result=def;
		}
		return result;
	}
	
	public static Integer nullFilter(String para){
		Integer result=para != null ? Integer.parseInt(para) : null;
		return result;
	}
	public static String nullStringFilter(String para){
		String result=para != null ? para : null;
		return result;
	}

	public static int NumberEmptyFilter(int defalut, Integer value) {
		
		return value==null?defalut:value;
		
	}


}
