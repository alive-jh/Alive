package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpRequest {

	public static JSONObject doPost(String url, String params) throws Exception {
		try {
			JSONObject jsonObject = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			// 目标地址
			HttpPost httppost = new HttpPost(url);
			// 构造最简单的字符串数据
			StringEntity reqEntity = new StringEntity(params, HTTP.UTF_8);
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
			//System.out.println("resultStr = " + resultStr);
			jsonObject = JSONObject.fromObject(resultStr);
			return jsonObject;
		} catch (Exception e) {
			//System.out.println("ex = " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * Get请求的方法,可以带上参数
     * 
     * @param url 需要请求的URL
     * @return 将请求URL后返回的数据，转为JSON格式，并return
     */	
	public static JSONObject doGet(String url,String params) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();//获取DefaultHttpClient请求
        HttpGet httpGet = new HttpGet(url+"?"+params);//HttpGet将使用Get方式发送请求URL
        JSONObject jsonObject = null;
        HttpResponse response = client.execute(httpGet);//使用HttpResponse接收client执行httpGet的结果
        HttpEntity entity = response.getEntity();//从response中获取结果，类型为HttpEntity
        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");//HttpEntity转为字符串类型
            //System.out.println(result);
            jsonObject = JSONObject.fromObject(result);//字符串类型转为JSON类型
        }
        return jsonObject;
    }
}
