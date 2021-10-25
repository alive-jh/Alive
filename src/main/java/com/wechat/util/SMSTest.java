package com.wechat.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SMSTest {

	private static final String addr = "http://api.sms.cn/mt/";

	
	private static final String encode = "utf8";  

	public static void sendCode(String msgContent, String mobile) throws Exception {
		
		String userId = Keys.SMS_ID;
		String pwd = Keys.SMS_PWD; 
		//组建请求
		String straddr = addr + 
						"?uid="+userId+
						"&pwd="+pwd+
						"&mobile="+mobile+
						"&encode="+encode+
						"&content=" + msgContent;
		
		StringBuffer sb = new StringBuffer(straddr);
		////System.out.println("URL:"+sb);
		
		//发送请求
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		
		///返回结果
		String inputline = in.readLine();
		//System.out.println("Response:"+inputline);
		
	}
	public static void sendMessage(String msgContent, String mobile) throws Exception {
		
		String userId = "npulse2014";
		String pwd = "77ba8df6153e6ff46ce5be27f4d09a56"; 
		//组建请求
		String straddr = addr + 
						"?uid="+userId+
						"&pwd="+pwd+
						"&mobile="+mobile+
						"&encode="+encode+
						"&content=" + msgContent;
		
		StringBuffer sb = new StringBuffer(straddr);
		////System.out.println("URL:"+sb);
		
		//发送请求
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		
		///返回结果
		String inputline = in.readLine();
		//System.out.println("Response:"+inputline);
		
	}
	
	public static void main(String[] args) {
		try {
			String content = "";
			String str ="您的验证码是";
			String code ="123123";
			
			
			content = str + code + ".请在页面中提交验证码完成验证。【"+Keys.PROJECT_NAME+"】";
			sendCode(URLEncoder.encode(content, "UTF-8"), "15919429553");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
