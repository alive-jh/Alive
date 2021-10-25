package com.wechat.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wechat.entity.Member;
import com.wechat.service.MemberService;
import com.wechat.service.OauthService;
import com.wechat.util.Keys;

@Controller
@RequestMapping("articleOauth2")
public class ArticleOauth2 {

	@Resource
	MemberService memberService;

	@Resource
	OauthService oauthService;

	@RequestMapping("login")
	public String login(@RequestParam(name = "code", required = true) String code,
			@RequestParam(name = "state", required = true) String state, HttpServletRequest request,
			HttpSession session, HttpServletResponse respons) throws UnsupportedEncodingException {

		//System.out.println(code+"----"+state);
		
		try {
			Member member = oauthService.createMember(code);
			session.setAttribute("WX#USER", member.getOpenId() + "#" + member.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:" +Keys.SERVER_SITE+"/wechat"+ URLDecoder.decode(state, "UTF-8");

	}

}
