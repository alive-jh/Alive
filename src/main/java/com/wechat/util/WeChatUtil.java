package com.wechat.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.wechat.entity.AccessToken;
import com.wechat.entity.ExpressInfo;
import com.wechat.entity.MemberAccessToken;
import com.wechat.entity.NoticeInfo;
import com.wechat.service.AccountService;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
public class WeChatUtil extends HttpServlet{

	private ApplicationContext appContext; 
	private ServletContext servletContext;
	

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
	        ////System.out.println("resultStr = "+resultStr);
	        jsonObject = JSONObject.fromObject(resultStr);	        
	        return jsonObject;
    	}
		catch(Exception e){
			//System.out.println("ex = "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static AccessToken createAccessToKen(String appId,String appSecret)throws Exception{	
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/token";//请求URL
		String data = "grant_type=client_credential&appid="+appId+"&secret="+appSecret;//设置参数
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据
		AccessToken accessToken =  new AccessToken();
		String s = jsonObject.toString();
		if(s.lastIndexOf("errcode")== -1)
		{
			accessToken.setAccessToken(jsonObject.getString("access_token"));//获取json属性值
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		else
		{
			System.out.println(s);
			accessToken.setAccessToken("");
		}
		
		return accessToken;//返回结果
	}
	
	public static AccessToken getAccessToKen(String appId,String appSecret,String tempStr)throws Exception{	
			
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
        AccountService accountService = (AccountService)appContext.getBean("accountService");
        
        AccessToken accessToken =  new AccessToken();
        accessToken.setAccessToken(accountService.getAccessToken(appId, appSecret));
        if(accessToken.getAccessToken()== null ||"".equals(accessToken.getAccessToken()) )
		{
        	accessToken = WeChatUtil.updateAccessToKen(appId, appSecret);
		}
		return accessToken;//返回结果
	}
	
	public static AccessToken updateAccessToKen(String appId,String appSecret)throws Exception{	
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
        AccountService accountService = (AccountService)appContext.getBean("accountService");
        
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/token";//请求URL
		String data = "grant_type=client_credential&appid="+appId+"&secret="+appSecret;//设置参数
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据
		AccessToken accessToken =  new AccessToken();
		String s = jsonObject.toString();
		if(s.lastIndexOf("errcode")== -1)
		{
			accessToken.setAccessToken(jsonObject.getString("access_token"));//获取json属性值
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			
			//保存jsapi_ticket
			String ticket="";
			String url2 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//请求URL
			String data2 = "access_token="+accessToken.getAccessToken()+"&type=jsapi";//设置参数
			JSONObject jsonObject2  = WeChatUtil.httpRequest(url2, data2);
			if("ok".equals(jsonObject2.getString("errmsg"))){
				ticket=jsonObject2.getString("ticket");
			}
			
			accountService.updateAccountAccessToken(appId,appSecret, accessToken.getAccessToken(),ticket);
			
			
		}
		else
		{
			//System.out.println("WeChatUtil 获取access_token失败!");
		}
		
		
		return accessToken;//返回结果
	}
	
	//更新用户tokenAccess
	public static MemberAccessToken userOauth(String appId,String appSecret,String code)throws Exception{		
		MemberAccessToken memberAccessToken = new MemberAccessToken();
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";//请求URL
		String data = "appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";//设置参数	
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据		
		//System.out.println("userOauth = "+jsonObject.toString());
		if(jsonObject.toString().indexOf("errcode") ==-1)
		{
			memberAccessToken.setAccessToken(jsonObject.getString("access_token"));
			memberAccessToken.setOpenid(jsonObject.getString("openid"));
			memberAccessToken.setRefreshToken(jsonObject.getString("refresh_token"));
			memberAccessToken.setExpiresIn(jsonObject.getString("expires_in"));
		}		
		return memberAccessToken;

	}
	
	
	//发送提交订单成功通知,模板ID:TTvFJvApAqNfWmoRqHmz1yH2MJaDWFSRnvOtutIfoeQ
	public static void sendOrderNotice(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin9send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append(Keys.SENDORDERNOTICE).append("\",");
		data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword3\":{").append("\"value\":\"").append(noticeInfo.getKeyword3()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		System.out.println(jsonObject.toString());
		

	}
	
	
	public static void sendLessonIntegral(NoticeInfo noticeInfo)throws Exception{		
			
			JSONObject jobj = new JSONObject();
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
			StringBuffer data = new StringBuffer("");
			
			data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
			data.append("\"template_id\":\"").append(Keys.SENDLESSONINGETRAL).append("\",");
			//data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
			data.append("\"data\":{");
			data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
			data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
			data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
			data.append("\"keyword3\":{").append("\"value\":\"").append(noticeInfo.getKeyword3()).append("\",").append("\"color\":\"#173177\"},");
			data.append("\"keyword4\":{").append("\"value\":\"").append(noticeInfo.getKeyword4()).append("\",").append("\"color\":\"#173177\"},");
			data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
			data.append("}}");
			
			JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
			System.out.println(jsonObject.toString());
			

		}
	
	public static void sendFreeLessonMessage(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append(Keys.SENDFREElESSONMESSAGE).append("\",");
		//data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		System.out.println(jsonObject.toString());
		

	}	
	
	public static void sendLessonMessage(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append(Keys.SENDLESSONMESSAGE).append("\",");
		data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		System.out.println(jsonObject.toString());
		

	}	
	
	public static void sendLessonRecord(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append("SS5PIVliWXi1uMSGh_oq8GRZgrmiXxvXfX2fvMRJGWA").append("\",");
		data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword3\":{").append("\"value\":\"").append(noticeInfo.getKeyword3()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		System.out.println(jsonObject.toString());
		

	}
	
	
	
	//发送订单发货通知 ,模板ID:_2_K2G3o9U0GMQEeIPj8B2L3QgLNx_ponEmAYThsHZM
	public static void sendOrderExpress(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append(Keys.SENDORDEREXPRESS).append("\",");
		data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword3\":{").append("\"value\":\"").append(noticeInfo.getKeyword3()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword4\":{").append("\"value\":\"").append(noticeInfo.getKeyword4()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword5\":{").append("\"value\":\"").append(noticeInfo.getKeyword5()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		//System.out.println(jsonObject.toString());
		

	}
	
	//发送预约服务通知 -电工,模板ID:GhgC0ok-4z9Iz4JgHbZWuDVntEOYfZxq4rXv0lUpxAE
	public static void sendServiceOrder(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append(Keys.SERVICEORDEROTICE).append("\",");
		data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword3\":{").append("\"value\":\"").append(noticeInfo.getKeyword3()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword4\":{").append("\"value\":\"").append(noticeInfo.getKeyword4()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword5\":{").append("\"value\":\"").append(noticeInfo.getKeyword5()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		//System.out.println(jsonObject.toString());
		

	}
	
	//发送预约服务通知-会员 ,模板ID:YJA0e1oiXEZAR31xR6MrkYvSVWr-tIPbrYtGRNuFfTA
	public static void sendOrderResult(NoticeInfo noticeInfo)throws Exception{		
		
		JSONObject jobj = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+noticeInfo.getAccessToKen();//请求URL
		StringBuffer data = new StringBuffer("");
		
		data.append("{\"touser\":\"").append(noticeInfo.getOpenId()).append("\",");
		data.append("\"template_id\":\"").append(Keys.ACCEPTANCESERVICE).append("\",");
		data.append("\"url\":\"").append(noticeInfo.getUrl()).append("\",");
		data.append("\"data\":{");
		data.append("\"first\":{").append("\"value\":\"").append(noticeInfo.getFirst()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword1\":{").append("\"value\":\"").append(noticeInfo.getKeyword1()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword2\":{").append("\"value\":\"").append(noticeInfo.getKeyword2()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword3\":{").append("\"value\":\"").append(noticeInfo.getKeyword3()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword4\":{").append("\"value\":\"").append(noticeInfo.getKeyword4()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"keyword5\":{").append("\"value\":\"").append(noticeInfo.getKeyword5()).append("\",").append("\"color\":\"#173177\"},");
		data.append("\"remark\":{").append("\"value\":\"").append(noticeInfo.getRemark()).append("\",").append("\"color\":\"#173177\"}");
		data.append("}}");
		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data.toString());	//接受返回的json格式数据	
		//System.out.println(jsonObject.toString());
		

	}
	
	//商品推荐
	public static String searchKeyword(String keword,String type)throws Exception{				
		JSONObject jobj = new JSONObject();
		String url = "http://146.npulse.cn/client/api_goods.php";//请求URL
		String data = "m="+keword+"&t="+type;//设置参数
		////System.out.println("url = "+url);
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据
		////System.out.println("jsonObject = "+jsonObject.toString());		
		return jsonObject.getString("res");//返回结果
	}

//机场问答（提问部分）
	public static String askKeyword(String keword,String user)throws Exception{
		JSONObject jobj = new JSONObject();
		String url = "http://144.npulse.cn/client/qa/api_air.php";//请求URL
		String data = "m="+keword+"&user="+user;//设置参数
		////System.out.println("url = "+url);
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据
		////System.out.println("jsonObject = "+jsonObject.toString());
		String reslute1=jsonObject.getString("0");
		
		String  result="";
		if("1".equals(reslute1)){  
			 result=jsonObject.getString("0")+"&"+jsonObject.getString("1");
				 
		}
		else if("2".equals(reslute1)){
		     result=jsonObject.getString("0")+"&"+jsonObject.getString("1")+"&"+jsonObject.getString("2")+"&"+jsonObject.getString("3");
		     
		}
		return result;	
		//返回结果
	}
	//机场问答（问题选择部分）
	public static String replyKeyword(String keword,String user)throws Exception{
		JSONObject jobj = new JSONObject();
		String url = "http://144.npulse.cn/client/qa/api_air.php";//请求URL
		String data ="user="+user+"&num="+keword;//设置参数
		////System.out.println("url = "+url);
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据
		////System.out.println("jsonObject = "+jsonObject.toString());
		String result=jsonObject.getString("0")+"&"+jsonObject.getString("1");
		return result;//返回结果
	}
	
	
	//在线翻译
	public static String onlineTranslator(String keword)throws Exception{
		
		JSONObject jobj = new JSONObject();
		String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=7hqqjHMycQaeGsITPjLqmA1Q&from=auto&to=auto";//请求URL
		String data = "q="+keword;//设置参数
		////System.out.println("url = "+url);
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据
		////System.out.println("jsonObject. = "+jsonObject.toString());
		String tempStr = jsonObject.toString();
		tempStr = tempStr.replaceAll("\\[", "");
		tempStr = tempStr.replaceAll("\\]", "");		
		jsonObject = JSONObject.fromObject(tempStr);
		JSONObject dataObject = (JSONObject) jsonObject.get("trans_result");	
		////System.out.println("dataObject. = "+dataObject.get("dst"));		
		return dataObject.getString("dst");//返回结果
	}
	
	
	//天气预报
	public static String weatherForecast(String city)throws Exception{			
		JSONObject jobj = new JSONObject();
		String url = "http://api.36wu.com/Weather/GetMoreWeather";//请求URL
		String data = "district="+city;//设置参数		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据			
		return jsonObject.getString("data");//返回结果
	}
	
	
	//快递查询
	public static List getExpressInfo(String postid)throws Exception{			
		JSONObject jobj = new JSONObject();
		String url = "http://api.36wu.com/Express/GetExpressInfo";//请求URL
		String data = "postid="+postid;//设置参数		
		JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);	//接受返回的json格式数据		
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonArray result = jsonParser.parse(jsonObject.toString()).getAsJsonObject().getAsJsonArray("data");
		List list = new ArrayList();
		ExpressInfo expressInfo = new ExpressInfo();
		for (int i = 0; i < result.size(); i++) {			
			expressInfo  = gson.fromJson(result.get(i), ExpressInfo.class);
			////System.out.println("expressInfo = "+expressInfo.getRemark());
			list.add(expressInfo);
		}		
		return list;//返回结果
	}


	//获取微信官方二维码ticket
	public static String createTiket() throws Exception{
	//先获取AccessToKen
	AccessToken accessToken=WeChatUtil.getAccessToKen("wx3406e142ff1b25ff", "56a7013af38817d190cc09f1a2d038d8","");
	String data= accessToken.getAccessToken();
	
	//拼接获取ticket的链接地址
	String requestUrl= "http://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	requestUrl=requestUrl.replace("TOKEN", data);
		String ticket=null;
	//提交需要的json数据
		String jsonMsg="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 001} }}";
	//创建永久二维码
		JSONObject dataObject=WeChatUtil.httpRequest(requestUrl, jsonMsg);
		if(null!=dataObject){
			try {
				ticket=dataObject.getString("ticket");
				ticket=URLEncoder.encode(ticket,"UTF-8");
			} catch (Exception e) {
				// TODO: handle exception
				int errorCode =dataObject.getInt("errcode");
				String errorMsg=dataObject.getString("errmsg");
				//System.out.println("erro="+errorMsg);
			}
		}
		return ticket;
	}
//获取二维码图片
/** 
*根据ticket获取二维码图片
*  
* @param ticket 
* @param savePath
 * @throws Exception 
*/  
public static String getQrcode(String ticket, String savePath) throws Exception{	
	String filePath= null;
	String requestUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	requestUrl=requestUrl.replace("TICKET",ticket);	
	try {
		URL url =new URL(requestUrl);
		javax.net.ssl.HttpsURLConnection connect= (javax.net.ssl.HttpsURLConnection) url.openConnection();
		//System.out.println("url="+url);		
		connect.setDoInput(true);
		connect.setRequestMethod("GET");		
		if (!savePath.endsWith("/")){
			savePath +="/";
		}
		//保存图片名字为ticket		
		filePath =savePath;
		//System.out.println("filePath="+filePath);	
		//输入流写入文件；
		BufferedInputStream bis= new BufferedInputStream(connect.getInputStream());
		FileOutputStream fos =new FileOutputStream(new File(filePath));
		byte[] buf = new byte[8096];
		int size=0;
		while((size=bis.read(buf))!=-1) 
					fos.write(buf,0,size);
					fos.close();
					bis.close();
					connect.disconnect();					
	} catch (Exception e) {
		// TODO: handle exception
		filePath =null;
	}
	////System.out.println("filePath="+filePath);
	return filePath;
}
//授权认证后发送客服信息
	public static boolean sendCustomMessage( String jsonMsg) throws Exception{
		boolean result=false;
		AccessToken Token=WeChatUtil.getAccessToKen("wx3406e142ff1b25ff", "56a7013af38817d190cc09f1a2d038d8","");
		String data= Token.getAccessToken();
		
		String requestUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=TOKEN";
		requestUrl=requestUrl.replace("TOKEN", data);
		JSONObject jsonObject=WeChatUtil.httpRequest(requestUrl, jsonMsg);
		if(null!=jsonObject){
			int errorCode=jsonObject.getInt("errcode");
			String errorMsg=jsonObject.getString("errmsg");
			if(0==errorCode){
			result=true;
			}
			else{
				//System.out.println(errorMsg);
			}
		}
		return result;
	}	
	
	/** 
	 * 通讯录管理
	 *
	 * @param content 
	 * @return 
	 */  
	//企业号获取token
		public static String createQYtoken()throws Exception{
			String msg ="corpid=wxa48f2375b964241a&corpsecret=Dz3iPzdygjwoGkdBeSvPIRMQoqJvqBNAqNfbMk1jvwP27weXqVa03TVQBvWsDw0y";
			String requestUrl= "https://qyapi.weixin.qq.com/cgi-bin/gettoken";		
			JSONObject dataObject=WeChatUtil.httpRequest(requestUrl, msg);
			String new_token=null;
			new_token=dataObject.getString("access_token");
			return new_token;
			
		}
	
	
	
		public static JSONObject getLngAndLat(String address) throws Exception
		{
			
			String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=EGkQIeFCh2OwlaMZdVy9pD2k";	
	    	
			//double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
	       	//double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
			return httpRequest(url,"");
		}
	
	
	
	/** 
	 * 计算采用utf-8编码方式时字符串所占字节数 
	 *  
	 * @param content 
	 * @return 
	 */  
	public static int getByteSize(String content) {  
	    int size = 0;  
	    if (null != content) {  
	        try {  
	            // 汉字采用utf-8编码时占3个字节  
	            size = content.getBytes("utf-8").length;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return size;  
	} 
	
	public static void main(String[] args) throws Exception{
		

//		Jedis jedis = new Jedis("119.147.144.77",6379);
//		
//		//System.out.println("Server is running: "+jedis.ping());
//		//System.out.println("Stored string in redis:: "+ jedis.get("foo"));
		
		//System.out.println(" value = "+JedisUtil.get("foo"));
	}
	
	
}
