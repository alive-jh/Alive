package com.wechat.util;

import com.wechat.entity.MemberAccessToken;
import com.wechat.entity.QQAccessToken;

import net.sf.json.JSONObject;

public class QQUtils {

	// 更新用户tokenAccess
	public static QQAccessToken userOauth(String appId, String appSecret, String code, String redirectUri)
			throws Exception {
		QQAccessToken qqAccessToken = new QQAccessToken();
		JSONObject jobj = new JSONObject();
		String url = "https://graph.qq.com/oauth2.0/token";// 请求URL
		String data = "client_id=" + appId + "&client_secret=" + appSecret + "&code=" + code
				+ "&grant_type=authorization_code&redirect_uri=" + redirectUri;// 设置参数
		JSONObject jsonObject = WeChatUtil.httpRequest(url, data); // 接受返回的json格式数据
		//System.out.println("userOauth = " + jsonObject.toString());
		if (jsonObject.toString().indexOf("code") == -1) {
			qqAccessToken.setAccessToken(jsonObject.getString("access_token"));
			qqAccessToken.setRefreshToken(jsonObject.getString("refresh_token"));
			qqAccessToken.setExpiresIn(jsonObject.getString("expires_in"));
		}

		String me_url = "https://graph.qq.com/oauth2.0/me";
		data = "access_token=" + qqAccessToken.getAccessToken();// 设置参数
		jsonObject = WeChatUtil.httpRequest(url, data); // 接受返回的json格式数据
		
		if (jsonObject.toString().indexOf("code") == -1) {
			qqAccessToken.setOpenid(jsonObject.getString("openid"));
			
		}
		
		return qqAccessToken;

	}
}
