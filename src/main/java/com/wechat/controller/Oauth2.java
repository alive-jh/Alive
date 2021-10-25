package com.wechat.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.entity.Member;
import com.wechat.entity.MemberAccessToken;
import com.wechat.service.MemberService;
import com.wechat.service.RedisService;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("oauth2")
public class Oauth2 {

	public final String GET_USER_INFO_API = "https://api.weixin.qq.com/sns/userinfo";
	
	public final String REFRESH_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public final String NORMAL_SERVER_SITE = "https://wechat.fandoutech.com.cn/";

	@Resource
	MemberService memberService;
	
	@Resource
	RedisService redisService;
	
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse respons){
		
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		String redirectUrl = "";
		
		try {
			
			MemberAccessToken memberAccessToken = WeChatUtil.userOauth(Keys.APP_ID,Keys.APP_SECRET, code);
			String accessToken = memberAccessToken.getAccessToken();
			
			String openId = memberAccessToken.getOpenid();
			
			String getUserinfoParamets = "access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";// 设置参数
			
			JSONObject userInfo = HttpRequest.doGet(GET_USER_INFO_API,getUserinfoParamets);
			
			//System.out.println(userInfo.toString());
			
			Member member = null;
			
			if(userInfo.get("errcode")==null){
				
				//根据openid获取member信息
				member = memberService.getMemberByOpenIdWithType(openId,0);
				
				if(member.getId()==null){
					member = new Member();
					member.setType(5);
					member.setStatus(1);
					member.setCreateDate(new Date());
				}
				
				member.setOpenId(userInfo.getString("openid"));
				member.setNickName(userInfo.getString("nickname"));

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
				member.setHeadImgUrl(userInfo.getString("headimgurl"));
				
				memberService.saveMember(member);
				
			}else{
				//System.out.println("获取用户信息失败");
			}
			
			
			if(state.indexOf("classRoom")!=-1){
				
				String[] paramets = state.split("@");
				
				redirectUrl = Keys.SERVER_SITE + "/wechat/h5/article/trialIntro?classRoomId="+paramets[1]
							+"&recommendMID="+paramets[2]+"&memberId="+member.getId();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return "redirect:"+redirectUrl;
	}
	
	public void test(){
		memberService.getMemberByOpenIdWithType("org-Fwzean9TrrZaq3cpjyZTmH9I",0);
	}
}
