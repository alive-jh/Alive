package com.wechat.controller;

import com.wechat.service.RedisService;
import com.wechat.service.VideoService;
import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("api")
public class GetWechatSign {

	@Resource
	VideoService videoService;
	

	@Resource
	RedisService redisService;

	String key = "access_num";

	@RequestMapping("getWechatSign")
	@ResponseBody
	public Map<String, Object> saveVedioInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Iterator<String> it = null;
		String url = request.getParameter("url");
		Map<String, String> map = new HashMap<String, String>();
		String access_token = redisService.get("wechat_access_token");
		if(null==access_token||"".equals(access_token)){
			String data = "&appid=wxbeda1313a1604ddf"
					+ "&secret=c7962d0ff8292a6b96110f1ee26a3ea2";
	        JSONObject jsonMap = WeChatUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential",data);
	        it = jsonMap.keys();  
	        while(it.hasNext()) {  
	            String key = (String) it.next();  
	            String u = jsonMap.get(key).toString();
	            map.put(key, u);  
	        }
	        redisService.set("wechat_access_token", map.get("access_token"), 7200);
	        access_token = map.get("access_token");
		}
		
        //System.out.println("access_token=" + access_token);
        //获取ticket
        String jsapi_ticket = redisService.get("wechat_jsapi_ticket");
        if(null==jsapi_ticket||"".equals(jsapi_ticket)){
        	String data2 = "access_token="+access_token+"&type=jsapi";
            JSONObject jsonMap2 = WeChatUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket",data2); 
            map = new HashMap<String, String>();
            it = jsonMap2.keys();  
            while(it.hasNext()) {  
                String key = (String) it.next();  
                String u = jsonMap2.get(key).toString();
                map.put(key, u);  
            }
            this.redisService.set("wechat_jsapi_ticket", map.get("ticket"), 7200);
            jsapi_ticket = map.get("ticket");
        }
		
        //System.out.println("jsapi_ticket=" + jsapi_ticket);

        //获取签名signature
        String noncestr = UUID.randomUUID().toString().replaceAll("-", "");
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = SHA1(str);
        //System.out.println("url=" + url);
        //System.out.println("noncestr=" + noncestr);
        //System.out.println("timestamp=" + timestamp);
        //System.out.println("signature=" + signature);
        //最终获得调用微信js接口验证需要的三个参数noncestr、timestamp、signature
        result.put("appid", Keys.APP_ID);
        result.put("timestamp",timestamp);
        result.put("noncestr",noncestr);
        result.put("signature",signature);
        return result;
		
	}
	
	public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@Test
	public void test(){
		//System.out.println("1ebac8b1358b6595b65d805ce6fa4460413f21bb".equals("1ebac8b1358b6595b65d805ce6fa4460413f21bb"));
		this.redisService.del("wechat_jsapi_ticket");
	}

}
