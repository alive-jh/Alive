package com.wechat.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.dao.MemberDao;
import com.wechat.entity.Member;
import com.wechat.entity.MemberAccessToken;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

@Service
public class OauthService {

	public final String GET_USER_INFO_API = "https://api.weixin.qq.com/sns/userinfo";

	public final String REFRESH_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public final String NORMAL_SERVER_SITE = "https://wechat.fandoutech.com.cn";
	
	@Resource
	private MemberDao memberDao;

	public Member createMember(String code) throws Exception {

		MemberAccessToken memberAccessToken = WeChatUtil.userOauth(Keys.APP_ID, Keys.APP_SECRET, code);
		String accessToken = memberAccessToken.getAccessToken();

		String openId = memberAccessToken.getOpenid();

		String getUserinfoParamets = "access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";// 设置参数

		JSONObject userInfo = HttpRequest.doGet(GET_USER_INFO_API, getUserinfoParamets);

		//System.out.println(userInfo.toString());

		Member member = null;

		if (userInfo.get("errcode") == null) {

			// 根据openid获取type为5的care用户
			member = memberDao.getMemberByOpenId(openId);

			if (member.getId() == null) {
				member = new Member();
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

			memberDao.saveMember(member);
			
		} else {
			//System.out.println("获取用户信息失败");
		}

		return member;
	}
}
