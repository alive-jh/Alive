package com.wechat.oauth2;

import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.entity.Member;
import com.wechat.entity.MemberAccessToken;
import com.wechat.entity.QQAccessToken;
import com.wechat.service.MemberService;
import com.wechat.service.RedisService;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.QQUtils;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("QqOauth2")
public class QqOauth2 {

	public final String GET_USER_INFO_API = "https://graph.qq.com/user/get_user_info";

	public final String REFRESH_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public final String NORMAL_SERVER_SITE = "https://wechat.fandoutech.com.cn";

	@Resource
	MemberService memberService;

	@Resource
	RedisService redisService;

	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpSession session, HttpServletResponse respons) {

		String code = request.getParameter("code");
		String state = request.getParameter("state");

		String redirectUrl = "";

		try {

			QQAccessToken qqAccessToken = QQUtils.userOauth(Keys.QQ_APP_ID, Keys.QQ_APP_KEY, code, "");
			String accessToken = qqAccessToken.getAccessToken();

			String openId = qqAccessToken.getOpenid();

			String getUserinfoParamets = "access_token=" + accessToken + "&openid=" + openId + "&oauth_consumer_key="
					+ Keys.APP_ID;// 设置参数

			JSONObject userInfo = HttpRequest.doGet(GET_USER_INFO_API, getUserinfoParamets);

			//System.out.println(userInfo.toString());

			
			session.setAttribute("videoVoteMemberId", userInfo);

			redirectUrl = NORMAL_SERVER_SITE + "/wechat" + URLDecoder.decode(state, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:" + redirectUrl;
	}

}
