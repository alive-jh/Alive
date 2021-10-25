package com.wechat.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BaiduUtil {
	
	private String AK = "A8466edaf55ffc9089f6aee0b49c39c7"; //用户密钥
	
	
	public static JSONObject httpRequest(String url,String data) throws Exception
	{			
		try{
			JSONObject jsonObject = null;
	    	DefaultHttpClient httpclient = new DefaultHttpClient();   
	        // 目标地址   
	    	 HttpPost httppost = new HttpPost(url);   
	        // 构造最简单的字符串数据   
	    	 String tempData = data;
	         StringEntity reqEntity = new StringEntity(tempData,HTTP.UTF_8); 	
	         String resultStr = "";	        
	        // 设置类型   
	         reqEntity.setContentType("application/x-www-form-urlencoded");   
	        // 设置请求的数据   
	         httppost.setEntity(reqEntity);   
	        // 执行   	         	        
	         HttpResponse response = httpclient.execute(httppost); 
	         HttpEntity entity = response.getEntity();   	      	          
	        // 显示结果   
	         BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));   
	         String line = null;   
	        while ((line = reader.readLine()) != null) {   
	        	resultStr = line;   
	         }   	        
	        //System.out.println("resultStr = "+resultStr);
	        jsonObject = JSONObject.fromObject(resultStr);	        
	        return jsonObject;
    	}
		catch(Exception e){
			//System.out.println("ex = "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		String url= "http://api.map.baidu.com/location/ip?";
		String data = "ak=A8466edaf55ffc9089f6aee0b49c39c7&ip=113.97.0.168&coor=bd09ll";
		JSONObject str = BaiduUtil.httpRequest(url, data);
		//System.out.println("str = "+str.toString());
		
	}

}
