package com.wechat.util;

import com.wechat.entity.Member;
import com.wechat.entity.MemberAccessToken;
import com.wechat.service.MemberService;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Freedom 接口权限中设置OAuth2.0网页授权 域名 如：http://wechat.fandoutech.com.cn
 *
 */
public class Oauth2Servlet extends HttpServlet {

	private static final long serialVersionUID = -644518508267758016L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		ApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		MemberService memberService = (MemberService) appContext
				.getBean("memberService");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		//System.out.println("state = " + state + " code = " + code);

		JSONObject jsonObject = null;
		try {
			String appId = Keys.APP_ID;
			String secret = Keys.APP_SECRET;
			String url = "";
			String data = "";
			String openId = "";
			String accessToken = "";
			String headimgurl = "";
			Integer memberStatus = 0;// 会员状态0新会员,1老会员

			MemberAccessToken memberAccessToken = WeChatUtil.userOauth(appId,
					secret, code);
			// 刷新access_token
			if (memberAccessToken.getAccessToken() == null || "".equals(memberAccessToken.getAccessToken())){
				url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
				data = "appid=" + appId
						+ "&grant_type=refresh_token&refresh_token=" + code;
				jsonObject = WeChatUtil.httpRequest(url, data);
				openId = jsonObject.getString("openid");
				accessToken = jsonObject.getString("access_token");
			} else {
				openId = memberAccessToken.getOpenid();
				accessToken = memberAccessToken.getAccessToken();
			}

			url = "https://api.weixin.qq.com/sns/userinfo";
			data = "access_token=" + accessToken + "&openid=" + openId
					+ "&lang=zh_CN";// 设置参数
			jsonObject = WeChatUtil.httpRequest(url, data);
			String memberId = "";
			Member member = new Member();
			if (jsonObject.toString().indexOf("errcode") == -1) {
				member = memberService.getMemberByOpenId(openId);// 保存会员信息
				if (member.getId() == null) {

					member.setOpenId(openId);
					member.setCreateDate(new Date());
				} else {
					memberStatus = 1;
				}
				member.setNickName(jsonObject.getString("nickname"));
				member.setProvince(jsonObject.getString("province"));
				member.setCity(jsonObject.getString("city"));
				member.setOpenId(jsonObject.getString("openid"));
				member.setHeadImgUrl(jsonObject.getString("headimgurl"));

				if ("1".equals(jsonObject.getString("sex"))) {
					member.setSex("男");
				}
				if ("2".equals(jsonObject.getString("sex"))) {
					member.setSex("女");
				}
				if ("0".equals(jsonObject.getString("sex"))) {
					member.setSex("未知");
				}

				try {
					memberService.saveMember(member);
				} catch (Exception e) {
					//System.out.println("error = " + e.getMessage());
				} finally {
					member.setMobile(member.getMobile());
					memberService.saveMember(member);
				}
				memberId = member.getId().toString();

			}
			response.setContentType("text/html; charset=utf-8");
			String path = state;
			if (member.getMobile() != null) {

				request.setAttribute("member", member);
				// 个人中心
				if ("member".equals(path)){
					url = Keys.STAT_NAME + "wechat/member/memberInfo?memberId=" + memberId + "&mobile=" + member.getMobile();
				}
				// 商城
				if ("mall".equals(path)){
					url = Keys.STAT_NAME + "wechat/mall/mallMobileIndex?memberId=" + memberId;
				}
				// 书院订单
				if ("toBookInfo".equals(path)){
					url = Keys.STAT_NAME + "wechat/book/memberBookInfo?memberId=" + memberId;
				}

				if (path.indexOf("toupiao")!=-1)// 投票链接
				{
					String vid = path.substring((path.indexOf(":")+1), path.length());
					url = Keys.STAT_NAME
							+ "/wechat/api/addVote?memberId=" + memberId+"&vid="+vid;

				}
				
				if (path.indexOf("shareVideo")!=-1)// 视频分享链接
				{
					String vid = path.substring((path.indexOf(":")+1), path.length());
					url = Keys.STAT_NAME
							+ "wechat/member/wechatShare?vid="+vid+"&openid="+openId+"&memberid="+memberId;
				}
				
				if (path.indexOf("addBookCard")!=-1)// 图书管理系统，会员申请
				{
					String shopId = path.substring((path.indexOf(":")+1), (path.indexOf("P")));
					String telephone = path.substring((path.indexOf("P")+1), path.length());
					url = Keys.STAT_NAME
							+ "wechat/h5/bookShop/insertShopCard?memberid="+memberId+"&shopid="+shopId+"&tel="+telephone;
				}

				if (path.indexOf("toupiao2")!=-1)// 投票链接
				{
					url = Keys.STAT_NAME
							+ "/wechat/api/getAllVideoCompetitionByVerifSuccess?memberId="+ memberId;
				}

				if (state.split("@")[0] != null) {
					path = state.split("@")[0];
					if ("coupons".equals(path))// 优惠券
					{

						url = Keys.STAT_NAME
								+ "wechat/coupons/couponsView?memberId="
								+ memberId + "&couponsId="
								+ state.split("@")[1] + "&memberImg="
								+ member.getHeadImgUrl();
					}

				}

				if (state.split("@")[0] != null) {

					path = state.split("@")[0];
					if ("toProduct".equals(path))// 登录后跳转商品页面
					{
						if (state.split("@").length > 4)// 登录后跳转书院会员卡页面
						{
							url = Keys.STAT_NAME
									+ "wechat/member/toSaveOrder?path=toProduct&orderType=0&memberId="
									+ memberId + "&productId="
									+ state.split("@")[1]
									+ "&specificationsId="
									+ state.split("@")[2] + "&count="
									+ state.split("@")[3] + "&shopId="
									+ state.split("@")[4];

						} else// 登录后跳转商品页面
						{
							url = Keys.STAT_NAME
									+ "wechat/member/toSaveOrder?path=toProduct&orderType=0&memberId="
									+ memberId + "&productId="
									+ state.split("@")[1]
									+ "&specificationsId="
									+ state.split("@")[2] + "&count="
									+ state.split("@")[3];

						}
						/*System.out.println(new Date().getTime()
								+ " toProduct url  =" + url);
*/
					}
					// 登录后跳转商品页面详情
					if ("toProductView".equals(path)){

						url = Keys.STAT_NAME
								+ "wechat/mall/mallMobileManager?memberId="
								+ memberId + "&mallProductId="
								+ state.split("@")[1];
						//System.out.println(new Date().getTime()+ " toProductView url  =" + url);
								

					}

					if ("toBookIndex".equals(path))// 微信书院首页
					{

						url = Keys.STAT_NAME
								+ "wechat/book/bookMobileManager?memberId="
								+ memberId;
						//System.out.println(new Date().getTime()+ " toBookIndex url  =" + url);
								

					}

					if ("toBookView".equals(path))// 微信书院详情
					{

						url = Keys.STAT_NAME
								+ "wechat/book/bookMobileView?memberId="
								+ memberId + "&cateId=" + state.split("@")[1];
						//System.out.println(new Date().getTime()+ " toBookView url  =" + url);

								
					}
					if ("toBookVehicle".equals(path))// 书院购物车
					{

						url = Keys.STAT_NAME
								+ "wechat/book/bookVehicleManager?memberId="
								+ memberId;
						//System.out.println(new Date().getTime()+ " toBookVehicle url  =" + url);
								

					}

				}

				if ("yaoqing".equals(path))// 邀请活动
				{
					url = Keys.STAT_NAME
							+ "wechat/member/yaoqingMember?memberId="
							+ memberId;

				}
				if ("qiandao".equals(path))// 签到活动
				{
					url = Keys.STAT_NAME
							+ "wechat/member/qiandaoMember?memberId="
							+ memberId;

				}
				if (path.indexOf("toupiao")!=-1)// 投票链接
				{
					String vid = path.substring((path.indexOf(":")+1), path.length());
					url = Keys.STAT_NAME
							+ "/wechat/api/addVote?memberId=" + memberId+"&vid="+vid;

				}

				if (path.indexOf("toupiao2")!=-1)// 投票链接
				{
					url = Keys.STAT_NAME
							+ "/wechat/api/getAllVideoCompetitionByVerifSuccess?memberId="+ memberId;
				}
				
				if (path.indexOf("shareVideo")!=-1)// 视频分享链接
				{
					String vid = path.substring((path.indexOf(":")+1), path.length());
					url = Keys.STAT_NAME
							+ "wechat/member/wechatShare?vid="+vid+"&openid="+openId+"&memberid="+memberId;
				}

				if (path.indexOf("addBookCard")!=-1)// 图书管理系统，会员申请
				{
					String shopId = path.substring((path.indexOf(":")+1), (path.indexOf("P")));
					String telephone = path.substring((path.indexOf("P")+1), path.length());
					url = Keys.STAT_NAME
							+ "wechat/h5/bookShop/insertShopCard?memberid="+memberId+"&shopid="+shopId+"&tel="+telephone;
				}
				
				if ("sign".equals(path))// 活动签到
				{
					url = Keys.STAT_NAME
							+ "wechat/activityAPI/getMemberInfo?memberId="
							+ memberId;

				}
				if ("eval".equals(path))// 微信测评
				{
					url = Keys.STAT_NAME
							+ "wechat/eval/evalLogin.jsp?memberId=" + memberId;

				}

			} else {
				url = Keys.STAT_NAME + "wechat/member/toMember?memberId="
						+ memberId;

				if (state.split("@")[0] != null) {

					path = state.split("@")[0];
					if ("toProduct".equals(path)) {

						if (state.split("@").length > 4) {
							url = Keys.STAT_NAME
									+ "wechat/member/toMember?memberId="
									+ memberId + "&productId="
									+ state.split("@")[1] + "&shopId="
									+ state.split("@")[4];

						} else {
							url = Keys.STAT_NAME
									+ "wechat/member/toMember?memberId="
									+ memberId + "&productId="
									+ state.split("@")[1];

						}

						//System.out.println(new Date().getTime()+ " toProduct url  =" + url);
								
						//System.out.println("state = " + state);

					}

					if ("toBookView".equals(path)) {
						url = Keys.STAT_NAME
								+ "wechat/member/toMember?memberId=" + memberId
								+ "&cateId=" + state.split("@")[1];
					}

				}

				if ("toBookInfo".equals(path)) {
					url = Keys.STAT_NAME + "wechat/book/memberBookInfo?memberId=" + memberId;
				}

				if ("sign".equals(path)){
					url = Keys.STAT_NAME + "wechat/activityAPI/getMemberInfo?memberId="+ memberId;

				}
				if ("eval".equals(path))// 微信测评
				{
					url = Keys.STAT_NAME
							+ "wechat/eval/evalLogin.jsp?memberId=" + memberId;
				}

				if (path.indexOf("toupiao")!=-1)// 投票链接
				{
					String vid = path.substring((path.indexOf(":")+1), path.length());
					url = Keys.STAT_NAME
							+ "/wechat/api/addVote?memberId=" + memberId+"&vid="+vid;

				}

				if (path.indexOf("toupiao2")!=-1)// 投票链接
				{
					url = Keys.STAT_NAME
							+ "/wechat/api/getAllVideoCompetitionByVerifSuccess?memberId="+ memberId;
				}
				
				if (path.indexOf("shareVideo")!=-1)// 视频分享链接
				{
					String vid = path.substring((path.indexOf(":")+1), path.length());
					url = Keys.STAT_NAME
							+ "wechat/member/wechatShare?vid="+vid+"&openid="+openId+"&memberid="+memberId;
				}

				if (path.indexOf("addBookCard")!=-1)// 图书管理系统，会员申请
				{
					String shopId = path.substring((path.indexOf(":")+1), (path.indexOf("P")));
					String telephone = path.substring((path.indexOf("P")+1), path.length());
					url = Keys.STAT_NAME
							+ "wechat/h5/bookShop/insertShopCard?memberid="+memberId+"&shopid="+shopId+"&tel="+telephone;
				}

				//System.out.println(new Date().getTime() + " toMember url  ="+ url);
						
			}
			response.sendRedirect(url);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

	}

	public static void main(String[] args) {

		String s = "data=1@dimensional@1";
		String id = s.split("@")[2].toString();
		//System.out.println(id);

		//System.out.println("toupiao:2015".indexOf("toupiao")!=-1);
		//System.out.println("toupiao:2015".substring(("toupiao:2015".indexOf(":")+1), "toupiao:2015".length()));
	}
}