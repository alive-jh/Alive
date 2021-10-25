package com.wechat.jfinal.Oauth2;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.wechat.entity.MemberAccessToken;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.Account;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.WechatUser;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

public class Oauth2Ctr extends Controller {

	public final String GET_USER_INFO_API = "https://api.weixin.qq.com/sns/userinfo";

	public final String REFRESH_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	private final String UNION_USER_INFO_API =  "https://api.weixin.qq.com/cgi-bin/user/info";
	
	
	public void login() {

		String code = getPara("code");
		String state = getPara("state");

		Member member = null;

		try {
			MemberAccessToken memberAccessToken = WeChatUtil.userOauth(Keys.APP_ID, Keys.APP_SECRET, code);

			String openId = memberAccessToken.getOpenid();

			String accessToken = memberAccessToken.getAccessToken();
			String getUserinfoParamets = "access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";// 设置参数

			JSONObject userInfo = HttpRequest.doGet(GET_USER_INFO_API, getUserinfoParamets);

			if (userInfo.get("errcode") == null) {

				member = Member.dao.findFirst("select * from member where openid = ?", openId);
				
				

				if (member == null) {
					member = new Member();
					member.setOpenid(userInfo.getString("openid"));
					member.setType(5).save();
				}

				member.setNickname(userInfo.getString("nickname"));

				if ("1".equals(userInfo.getString("sex"))) {
					member.setSex("男");
				}
				if ("2".equals(userInfo.getString("sex"))) {
					member.setSex("女");
				}
				if ("0".equals(userInfo.getString("sex"))) {
					member.setSex("未知");
				}

				member.setCity(userInfo.getString("city"));
				member.setProvince(userInfo.getString("province"));
				member.setHeadimgurl(userInfo.getString("headimgurl"));
				
				if(userInfo.has("unionid")){
					member.setUnionid(userInfo.getString("unionid"));
				}

				member.update();
			} else {
				//System.out.println("获取用户信息失败");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getSession().setAttribute("WX#USER", member.getOpenid() + "#" + member.getId());
		getSession().setAttribute("QY#WX#USER", member.getOpenid() + "#" + member.getId());

		redirect(Keys.SERVER_SITE+state,true);

	}
	
	public void wechatLogin(){
		
		String code = getPara("code");
		String state = getPara("state");
		System.out.println(getSession().getId());

		try {
			
			MemberAccessToken memberAccessToken = WeChatUtil.userOauth(Keys.APP_ID, Keys.APP_SECRET, code);

			String openId = memberAccessToken.getOpenid();
			
			HashMap<String, String> queryParas = new HashMap<String,String>(3);
			
			Account account = Account.dao.findById(0);
			queryParas.put("access_token", memberAccessToken.getAccessToken());
			queryParas.put("openid", openId);
			queryParas.put("lang", "zh_CN");
			
			String result = HttpKit.get(GET_USER_INFO_API, queryParas);
			
			System.out.println(result);
			
			JSONObject userInfo = JSONObject.fromObject(result);

			WechatUser wechatUser = refreshWechatUser(userInfo);
			
			HttpSession session = getSession();
			session.setAttribute("WX#USER", wechatUser.getOpenid() + "#" + wechatUser.getId());

			session.setAttribute("openid", wechatUser.getOpenid());
			session.setAttribute("wechatUserId", wechatUser.getId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		redirect(Keys.SERVER_SITE+state,true);
		
	}
	
	public WechatUser refreshWechatUser(JSONObject userJson){
		
		WechatUser wechatUser = getWechatUser(userJson.getString("openid"));
		
		if(wechatUser.getLastLogin() == null || System.currentTimeMillis() - (wechatUser.getLastLogin().getTime()) > 86400000){
			
			wechatUser.setNickname(userJson.getString("nickname"));
			wechatUser.setSex(userJson.getInt("sex"));
			wechatUser.setHeadimgurl(userJson.getString("headimgurl"));
			wechatUser.setCity(userJson.getString("city"));
			wechatUser.setProvince(userJson.getString("province"));
			wechatUser.setCountry(userJson.getString("country"));
			wechatUser.setUnionid(userJson.getString("unionid"));
			wechatUser.setLastLogin(new Date());
			wechatUser.update();

		}
		
		return wechatUser;  
	}
	
	public WechatUser getWechatUser(String openid){
		
		if(xx.isEmpty(openid)) throw new NullPointerException();
		
		WechatUser wechatUser = WechatUser.dao.findFirst("select * from wechat_user where openid = ?",openid);
		
		if(wechatUser == null){
			wechatUser = new WechatUser();
			wechatUser.setOpenid(openid).save();
		}
		
		return wechatUser;
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Date(1543562776L*1000));
	}
	
}
