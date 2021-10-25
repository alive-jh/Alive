package com.wechat.jfinal.Oauth2;

import com.jfinal.core.Controller;
import com.wechat.entity.MemberAccessToken;
import com.wechat.jfinal.model.Member;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

public class QYOauth2Ctr extends Controller {

	public final String GET_USER_INFO_API = "https://api.weixin.qq.com/sns/userinfo";

	public final String REFRESH_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public void login() {

		String code = getPara("code");
		String state = getPara("state");

		Member member = null;

		try {
			MemberAccessToken memberAccessToken = WeChatUtil.userOauth(Keys.QYJY_APP_ID, Keys.QYJY_APP_SECRET, code);

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

		getSession().setAttribute("QY#WX#USER", member.getOpenid() + "#" + member.getId());

		redirect(Keys.SERVER_SITE+"/wechat"+state,true);

	}
	
}
