package com.wechat.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyCookie {
	
	public static void deleteCookie(String key, HttpServletRequest request, HttpServletResponse response){
		Cookie[] allCookie= request.getCookies();
		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		          String keyname= allCookie[i].getName();
		          if(key.equals(keyname))
		          {
		        	  allCookie[i].setValue(null);
		        	  allCookie[i].setMaxAge(0);
		        	  response.addCookie(allCookie[i]);
		          }
		      }
		 }
	}
	public static void addCookie(String key,String value, HttpServletResponse response){
		addCookie(key,value,false, response);
	}
	public static void addCookie(String key,String value,boolean jiami, HttpServletResponse response){
		
	
		Cookie myCookie=new Cookie(key,value);
		myCookie.setMaxAge(60*60*24*7);
		myCookie.setPath("/");
		response.addCookie(myCookie);
	}
	public static String getCookie(String key,HttpServletRequest request){
		return getCookie(key,false,request);
	}
	public static String getCookie(String key,boolean jiami, HttpServletRequest request){
		Cookie allCookie[]= request.getCookies();
		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		          String keyname= allCookie[i].getName();
		          if((key).equals(keyname))
		          {
					  if(allCookie[i].getValue()==null)
					  {
						  return "";
					  }
					  else
					  {
						 return allCookie[i].getValue();
						
					  }
		          }
		         
		      }
		 }
		return "";
	}
	

	
	

}
