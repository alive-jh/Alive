package com.wechat.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.service.AccountService;
import com.wechat.service.RedisService;
import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("api")
public class WeChatUtilController {

	@Resource
	RedisService redisService;
	
	@Resource
	AccountService accountService;

	@RequestMapping("wechatShareSignature")
	@ResponseBody
	public Map<String, Object> wechatShare(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> signatureResult = new HashMap<String, Object>();
		String pageUrl = request.getParameter("pageUrl");

		try {
			signatureResult = toSignature(pageUrl, request.getServerName(),Keys.APP_ID,Keys.APP_SECRET);
			for (String key : signatureResult.keySet()) {
				result.put(key, signatureResult.get(key));
			}
			result.put("code", 200);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping("qYJssdkSignature")
	@ResponseBody
	public Map<String, Object> qYJssdkSignature(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> signatureResult = new HashMap<String, Object>();
		String pageUrl = request.getParameter("pageUrl");

		try {
			signatureResult = toSignature(pageUrl, request.getServerName(),Keys.QYJY_APP_ID,Keys.QYJY_APP_SECRET);
			for (String key : signatureResult.keySet()) {
				result.put(key, signatureResult.get(key));
			}
			result.put("code", 200);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Map<String, Object> toSignature(String url, String serverName,String appId,String appSecret) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		
		String jsapi_ticket = accountService.getTicket(appId, appSecret);
		
		// 获取签名signature
		String noncestr = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ url;
		
		// sha1加密
		String signature = SHA1(str);

		// 最终获得调用微信js接口验证需要的三个参数noncestr、timestamp、signature
		result.put("appid", appId);
		result.put("timestamp", timestamp);
		result.put("noncestr", noncestr);
		result.put("signature", signature);
		return result;

	}

	public static String SHA1(String str) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1"); // 如果是SHA加密只需要将"SHA-1"改成"SHA"即可
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

}
