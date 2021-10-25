package com.wechat.jfinal.apiRenderPage.classRoom;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.render.RenderException;
import com.wechat.entity.NoticeInfo;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.AgentArticle;
import com.wechat.jfinal.model.Audioinfo;
import com.wechat.jfinal.model.ClassCourse;
import com.wechat.jfinal.model.ClassGradesRela;
import com.wechat.jfinal.model.ClassProductOrder;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.ClassTeacher;
import com.wechat.jfinal.model.FreeLessonInviteRecord;
import com.wechat.jfinal.model.InvitationCode;
import com.wechat.jfinal.model.LessonIntegralRecord;
import com.wechat.jfinal.model.LessonProductImage;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.model.ShareGetLesson;
import com.wechat.jfinal.service.ClassroomPackageService;
import com.wechat.jfinal.service.UserService;


import com.wechat.util.*;
import net.sf.json.JSONObject;

public class Article extends Controller {

	public void shareUrl() {
		Integer classRoomId = getParaToInt("classRoomId", 0);

		AgentArticle agentArticle = AgentArticle.dao.findFirst("select * from agent_article where class_room_id = ?",
				classRoomId);

		Record classRoom = Db.findFirst(
				"SELECT a.*,b.member_id FROM class_room a,class_teacher b WHERE a.teacher_id = b.id AND  a.id = ?",
				classRoomId);

		if (classRoom == null) {
			Result.error(205, "没有找到课堂", this);
			return;
		}

		if (agentArticle == null) {

			agentArticle = new AgentArticle();
			agentArticle.setAuthor(classRoom.getStr("teacher_name"));
			agentArticle.setContent("");
			agentArticle.setClassRoomId(classRoomId);
			agentArticle.setCover(classRoom.getStr("cover"));
			agentArticle.setTitle(classRoom.getStr("class_name"));
			agentArticle.setMemberId(Integer.parseInt(classRoom.getStr("member_id")));
			agentArticle.save();

		}

		/*
		 * String shareUrl =
		 * "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf"
		 * + "&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2/login"
		 * + "&response_type=code&scope=snsapi_userinfo&state=classRoom@"+
		 * classRoomId+"@"+classRoom.getInt("member_id")+"#wechat_redirect";
		 */

		String shareUrl = "/h5/article/trialIntro?classRoomId=" + classRoomId;

		/*
		 * String shareUrl =
		 * "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
		 * Keys.APP_ID +
		 * "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/to_test_classroom_share.html"
		 * + "&response_type=code&scope=snsapi_userinfo&state=classRoom@" +
		 * classRoomId + "@" + classRoom.getInt("member_id") +
		 * "#wechat_redirect";
		 */

		JSONObject json = new JSONObject();
		json.put("url", shareUrl);
		Result.ok(json, this);
	}

	public void getArticleByClass() {

		Integer classRoomId = getParaToInt("classRoomId", 0);

		AgentArticle agentArticle = AgentArticle.dao.findFirst("select * from agent_article where class_room_id = ?",
				classRoomId);

		Record classRoom = Db.findFirst(
				"SELECT a.*,b.member_id FROM class_room a,class_teacher b WHERE a.teacher_id = b.id AND  a.id = ?",
				classRoomId);

		if (classRoom == null) {
			Result.error(205, "没有找到课堂", this);
			return;
		}

		if (agentArticle == null) {

			agentArticle = new AgentArticle();
			agentArticle.setAuthor(classRoom.getStr("teacher_name"));
			agentArticle.setContent("");
			agentArticle.setClassRoomId(classRoomId);
			agentArticle.setCover(classRoom.getStr("cover"));
			agentArticle.setTitle(classRoom.getStr("class_name"));
			agentArticle.setMemberId(Integer.parseInt(classRoom.getStr("member_id")));
			agentArticle.save();

		}

		/*String shareUrl = Keys.SERVER_SITE+"/wechat/h5/article/trialIntro" + "?classRoomId=" + classRoomId
				+ "&recommendMID=" + classRoom.getStr("member_id") + "&memberId=" + 1;*/
		
		String shareUrl = Keys.SERVER_SITE+"/wechat/v2/lesson/lessonInfo?classroom="+classRoomId+"&inviter="+classRoom.getStr("member_id");

		redirect(shareUrl);

	}

	// 根据邀请码获取classRoomid
	public void detryptInvitationCode() throws Exception {

		String _invitationCode = getPara("invitationCode");

		if (xx.isEmpty(_invitationCode)) {
			Result.error(203, this);
			return;
		}

		InvitationCode invitationCode = InvitationCode.dao
				.findFirst("select * from invitation_code where random_code = ?", _invitationCode);

		
		int classRoomId = 0;

		int productId = 0;
		
		if (invitationCode != null) {

			if (invitationCode.getInvalidTime().after(new Date())) {
				classRoomId = invitationCode.getClassRoomId();
				productId = invitationCode.getProductId();
			} else {
				Result.error(205, "邀请码已失效", this);
				return;
			}

		} else {
			Result.error(205, "邀请码不存在", this);
			return;
		}

		String mobile = invitationCode.getMobile();
		int invitee = invitationCode.getMemberId(); //被邀请人
		
		
		Memberaccount memberaccount = Memberaccount.dao
				.findFirst("select * from memberaccount where account = ? and (type <=2 or type = 5)", mobile);
		
		if(memberaccount == null){
			Result.error(20505, "未生成默认帐号",this);
			return;
		}

		//把邀请码对应的课堂与帐号绑定
		 
		UserService userService = new UserService();

		ClassStudent classStudent = userService.createDefaultStudent(mobile, invitee);
		ClassroomPackageService classroomPackageService = new ClassroomPackageService();
		classroomPackageService.bindStudentWithClassRoom(classStudent.getId(), classRoomId);


		/*
		 * 
		 
		int integral = 50;
		
		int inviter = invitationCode.getRecommendMemberId(); //邀请人
		
		ClassTeacher classTeacher = ClassTeacher.dao.findFirst("SELECT a.* FROM class_teacher a,class_room b WHERE a.id = b.teacher_id AND b.id = ?",classRoomId);
		
		if (inviter != invitee && invitationCode.getStatus() == 0 && classTeacher.getMemberId().equals(inviter) == false) {
			
			Member inviterMember = Member.dao.findById(invitationCode.getRecommendMemberId());
			
			
			if (productId > 0) { //productId > 0 = 免费获得班级商品的用户
				
				classroomPackageService.addFreeLesson2Member(classStudent.getId(), productId);//添加免费班级商品给被邀请人
				integral = 30;//邀请5人免费上课，每邀请一人获得30积分;
				
				//更新邀请记录
				Db.update("update free_lesson_invite_record set status = 1 where product_id = ? & inviter = ? & invitee = ?",productId,inviter,invitee);
				
				*//**
				 * 赠送积分
				 *//*
				LessonIntegralRecord lessonIntegralRecord = new LessonIntegralRecord();
				lessonIntegralRecord.setMemberId(inviter);
				lessonIntegralRecord.setIntegral(integral).save();
				
				*//**
				 * 发送微信通知
				 *//*
				if(inviterMember != null && inviterMember.getOpenid() != null){
					
					Record wXAccessToken = Db.findFirst("select accesstoken from account where 1=1 and appid = ? and appsecret = ?",Keys.APP_ID,Keys.APP_SECRET);
					Record lessonIntegral = Db.findFirst("SELECT SUM(integral) AS integral_sum FROM lesson_integral_record WHERE member_id = ?",inviter);
					
					NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
					noticeInfo.setAccessToKen(wXAccessToken.getStr("accesstoken"));
					noticeInfo.setOpenId(inviterMember.getOpenid());
					noticeInfo.setMemberId(inviterMember.getId().toString());
					noticeInfo.setFirst("有好友接受您的邀请，系统自动奖励积分");
					
					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date date = new Date();
					
					noticeInfo.setKeyword1(format.format(date));
					
					noticeInfo.setKeyword2(integral+"");
					noticeInfo.setKeyword3("成功邀请好友一起上课");
					noticeInfo.setKeyword4(lessonIntegral.getStr("integral_sum"));
					noticeInfo.setRemark("若有疑问请致电客服中心:"+Keys.CUSTOMER_MOBLIE);
					WeChatUtil.sendLessonIntegral(noticeInfo);
					
				}
				
			}else{
				
				
				 * 赠送积分
				 
				LessonIntegralRecord lessonIntegralRecord = new LessonIntegralRecord();
				lessonIntegralRecord.setMemberId(inviter);
				lessonIntegralRecord.setIntegral(integral).save();
				
				
				 * 发送模版消息通用参数
				 
				Record wXAccessToken = Db.findFirst("select accesstoken from account where 1=1 and appid = ? and appsecret = ?",Keys.APP_ID,Keys.APP_SECRET);
				Record lessonIntegral = Db.findFirst("SELECT SUM(integral) AS integral_sum FROM lesson_integral_record WHERE member_id = ?",inviter);
				
				List<InvitationCode> invitationCodes = InvitationCode.dao.find(
						"select id from invitation_code where class_room_id = ? and recommend_member_id = ? and status = 1",classRoomId,inviter);
				
				if(invitationCodes == null){
					invitationCodes = new ArrayList<>();
				}
				
				if(invitationCodes.size()>=2){
					
					ShareGetLesson shareGetLesson = ShareGetLesson.dao.findFirst("select * from share_get_lesson where inviter = ? and class_room_id = ?",inviter,classRoomId);
					
					if(shareGetLesson!=null){
						classroomPackageService.addFreeLesson(inviter,shareGetLesson.getProductId());//任务达成，给邀请人添加免费班级
					}
					
					NoticeInfo noticeInfo = new NoticeInfo();
					noticeInfo.setAccessToKen(wXAccessToken.getStr("accesstoken"));
					noticeInfo.setOpenId(inviterMember.getOpenid());
					noticeInfo.setMemberId(inviterMember.getId().toString());
					noticeInfo.setFirst("已经成功邀请3个人体验凡豆课堂");
					noticeInfo.setKeyword1("已经成功邀请3个人体验凡豆课堂");
					noticeInfo.setKeyword2("已经成功邀请3个人体验凡豆课堂");
					noticeInfo.setRemark("请打开care查看已经获得的班级");
					WeChatUtil.sendFreeLessonMessage(noticeInfo);
					
				}else{
					
					NoticeInfo noticeInfo = new NoticeInfo();
					noticeInfo.setAccessToKen(wXAccessToken.getStr("accesstoken"));
					noticeInfo.setOpenId(inviterMember.getOpenid());
					noticeInfo.setMemberId(inviterMember.getId().toString());
					noticeInfo.setFirst("成功邀请用户体验凡豆课堂");
					noticeInfo.setKeyword1("还剩下"+(3-invitationCodes.size())+"人即可获得免费课堂");
					noticeInfo.setKeyword2("还剩下"+(3-invitationCodes.size())+"人即可获得免费课堂");
					noticeInfo.setRemark("请再接再厉");
					WeChatUtil.sendFreeLessonMessage(noticeInfo);
					
				}
				
				
				
			}
			
			
		}*/
		
		ClassRoom classRoom = ClassRoom.dao.findById(classRoomId);
		
		//邀请码状态设置为使用过
		invitationCode.setStatus(1).update();
		
		String tokenStr = "device" + "#" + invitationCode.getMobile() + "#" + memberaccount.getPassword() + "#"
				+ System.currentTimeMillis();

		String accessToken = SecurityUtil.encrypt(tokenStr, "fandou");
		Cache redis = Redis.use();
		redis.set("device" + "#" + memberaccount.getAccount(), accessToken);
		redis.close(redis.getJedis());

		JSONObject json = new JSONObject();
		json.put("classRoomId", classRoomId);
		json.put("className", classRoom.getClassName());
		json.put("productId", productId);
		json.put("accessToken", accessToken);
		json.put("account", memberaccount.getAccount());
		json.put("memberId", invitationCode.getMemberId());

	    json.put("isDefaultPwd", memberaccount.isDefaultPwd());

		Result.ok(json, this);

	}

	/**
	 * 发送短信邀请码
	 * 
	 *            课堂id 必填
	 * 
	 * @author zheng
	 * 
	 */
	public void sendSmsWithInvitationCode() {

		String vCode = getPara("vCode");

		String mobile = getPara("mobile");

		if (xx.isAllEmpty(vCode, mobile)) {
			Result.error(203, this);
			return;
		}

		if (mobile == null || mobile.equals("")) {
			Result.error(20301, "请输入手机号码", this);
			return;
		} else if (CommonUtils.checkMobile(mobile) == false) {
			Result.error(20302, "请输入正确手机号码", this);
			return;
		}

		Cache redis = Redis.use();

		String mobileVCode = redis.get("mobileVCode#" + mobile);

		if (mobileVCode.toLowerCase().equals(vCode.toLowerCase()) == false) {
			Result.error(20401, "验证码错误", this);
			return;
		}

		Integer classRoomId = getParaToInt("classRoomId");

		// 邀请人的memberId
		Integer recommendMID = getParaToInt("recommendMID", 0);

		// 当前用户的memberId
		Integer memberId = getParaToInt("memberId");

		// 如果已经发送过邀请码则返回原来的邀请码并延长失效时间
		InvitationCode invitationCode = InvitationCode.dao.findFirst(
				"SELECT * FROM `invitation_code` WHERE class_room_id = ? and mobile = ? order by invalid_time desc limit 1",
				classRoomId, mobile);
		
		// 设置邀请码失效时间
		long invalidTime = 1000 * 60 * 60 * 24 * 3;
		Date endDate = new Date(System.currentTimeMillis() + invalidTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (invitationCode != null) {

			invitationCode.setInvalidTime(endDate).update();

		} else {

			invitationCode = new InvitationCode();
			invitationCode.setClassRoomId(classRoomId);
			invitationCode.setRandomCode(CommonUtils.getRandomNumber(6));
			invitationCode.setInvalidTime(endDate);
			invitationCode.setMemberId(memberId);
			invitationCode.setMobile(mobile);
			invitationCode.setRecommendMemberId(recommendMID);
			invitationCode.save();

		}
		
		
		/*
		 * 绑定课程与帐号学生
		 */
		UserService userService = new UserService();

		Memberaccount memberaccount = userService.registerByClassAtrial(mobile, memberId);

		ClassStudent classStudent = userService.createDefaultStudent(mobile, memberaccount.getMemberid());

		ClassroomPackageService classroomPackageService = new ClassroomPackageService();
		
		
		try {
			classroomPackageService.bindStudentWithClassRoom(classStudent.getId(), classRoomId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			TAOBAOSMS.sendClassInvite(invitationCode.getRandomCode(), sdf.format(endDate), mobile);
		} catch (Exception e1) {
			e1.printStackTrace();
			Result.error(205, "发送短信邀请码失败", this);
			return;
		}

		Result.ok(this);

	}

	/**
	 * 课堂试听简介
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public void trialIntro() throws UnsupportedEncodingException {


		Integer classRoomId = getParaToInt("classRoomId", 0);
		
		//getSession().setAttribute("WX#USER","org-Fwzean9TrrZaq3cpjyZTmH9I#137569");

		// 推荐人的memberId
		Integer recommendMID = getParaToInt("recommendMID");

		// 受邀用户的memberId
		String WX_USER = (String) getSession().getAttribute("WX#USER");

		if (WX_USER == null) {

			String state = "/wechat/h5/article/trialIntro?classRoomId=" + classRoomId + "&recommendMID="
					+ recommendMID;

			redirect("https://open.weixin.qq.com/connect/oauth2/authorize" + "?appid=" + Keys.APP_ID
					+ "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/to_formal_article_login.html"
					+ "&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(state, "UTF-8")
					+ "#wechat_redirect", true);

			return;
		}

		// 获取课堂文章
		AgentArticle agentArticle = AgentArticle.dao.findFirst("select * from agent_article where class_room_id = ?",
				classRoomId);

		if (agentArticle == null) {
			Record classRoom = Db.findFirst(
					"SELECT a.*,b.member_id FROM class_room a,class_teacher b WHERE a.teacher_id = b.id AND  a.id = ?",
					classRoomId);

			if (classRoom == null) {
				Result.error(205, "没有找到课堂", this);
				return;
			}

			agentArticle = new AgentArticle();
			agentArticle.setAuthor(classRoom.getStr("teacher_name"));
			agentArticle.setContent("");
			agentArticle.setClassRoomId(classRoomId);
			agentArticle.setCover(classRoom.getStr("cover"));
			agentArticle.setTitle(classRoom.getStr("class_name"));
			agentArticle.setMemberId(Integer.parseInt(classRoom.getStr("member_id")));
			agentArticle.save();

		}

		String sql = "SELECT a.id AS class_room_id,a.class_name,DATE_FORMAT(a.create_time,'%Y-%m-%d') AS create_time,b.`name`"
				+ ",IFNULL(b.avatar,'') AS avatar,IFNULL(b.remark,'') AS remark,c.content,a.summary,b.avatar FROM class_room a"
				+ ",class_teacher b,agent_article c WHERE a.teacher_id = b.id AND a.id = c.class_room_id AND a.id = ?";

		// 获取课堂信息
		Record classRoomInfo = Db.findFirst(sql, classRoomId);

		Audioinfo audioInfo = Audioinfo.dao.findFirst(
				"SELECT c.* FROM class_room a,class_room_audio_rel b"
						+ ",audioinfo c WHERE a.id = b.class_room_id AND b.audio_id = c.audioid AND a.id = ? ORDER BY c.id ASC LIMIT 1",
				classRoomId);

		String teacherName = classRoomInfo.getStr("name");
		setAttr("teacherName1", teacherName);

		if (teacherName != null && teacherName.indexOf("老师") > -1) {

		} else {
			teacherName += "老师";
		}

		String teacherAvatar = classRoomInfo.getStr("avatar");

		if (teacherAvatar == null || teacherAvatar.trim().equals("")) {
			teacherAvatar = "http://word.fandoutech.com.cn/app/OnlineCourse/18335775869/55745af535a4715174416d49f60fa3c0.jpg";
		}

		setAttr("teacherAvatar", teacherAvatar);
		setAttr("teacherName", teacherName);
		setAttr("classRoomInfo", classRoomInfo);
		setAttr("audioInfo", audioInfo);
		setAttr("memberId", WX_USER.split("#")[1]);
		setAttr("recommendMID", recommendMID);

		renderTemplate("/articles/lesson.html");

	}

	public void share() {

		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Keys.APP_ID
				+ "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/to_test_classroom_share.html"
				+ "&response_type=code&scope=snsapi_userinfo" + "&state=classRoom@" + getPara("classRoomId") + "@"
				+ getPara("memberId") + "#wechat_redirect";

		redirect(url);
	}

	public void captcha() {

		String captcha = getPara("captcha");

		//String vCode = CommonUtils.getRandomString();
		String vCode = CommonUtils.getRandomNumber(4);
		
		Cache redis = Redis.use();
		redis.setex(getSession().getId() + "#captcha", 60 * 5, vCode);

		if (captcha != null) {

			if (captcha.indexOf("@") > -1)
				redis.setex("mobileVCode#" + captcha.split("@")[1], 60 * 5, vCode);

		} else {
			renderCaptcha();
			return;
		}

		redis.close(redis.getJedis());

		HttpServletResponse response = getResponse();

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = null;
		try {
			BufferedImage image = new BufferedImage(108, 40, BufferedImage.TYPE_INT_RGB);
			GraphicsUtils.drawGraphic(vCode, image);

			sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
		} catch (IOException e) {

		} catch (Exception e) {
			throw new RenderException(e);
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
					LogKit.logNothing(e);
				}
			}
		}
	}

	public void freeLessonInfo() throws UnsupportedEncodingException {

		Integer productId = getParaToInt("product");
		Integer inviter = getParaToInt("inviter");
		Integer classRoomId = getParaToInt("classRoom");

		//getSession().setAttribute("WX#USER","org-Fwzean9TrrZaq3cpjyZTmH9I#137569");

		String member = (String) getSession().getAttribute("WX#USER");
		
		if (member == null) {

			String state = "/h5/article/freeLessonInfo?classRoom=" + classRoomId + "&inviter=" + inviter
					+ "&product=" + productId;

			redirect("https://open.weixin.qq.com/connect/oauth2/authorize" + "?appid=" + Keys.APP_ID
					+ "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/to_test_freeLessonInfo.html"
					+ "&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(state, "UTF-8")
					+ "#wechat_redirect", true);

			return;

		}
		
		Integer invitee = Integer.parseInt(member.split("#")[1]);

		ClassProductOrder classProductOrder = ClassProductOrder.dao.findFirst(
				"SELECT * FROM class_product_order WHERE product_id = ? and member_id = ? and status > 0", productId,
				inviter);

		if (classProductOrder == null) {
			System.out.println("你没有邀请资格");
			redirect("/h5/article/trialIntro?classRoomId=" + classRoomId + "&recommendMID=" + inviter);
			return;
		}

		List<FreeLessonInviteRecord> freeLessonInviteRecords = FreeLessonInviteRecord.dao.find(
				"select * from free_lesson_invite_record where product_id = ? and inviter = ?", productId, inviter);

		if (freeLessonInviteRecords != null && freeLessonInviteRecords.size() >= 5) {
			System.out.println("邀请资格已满");
			redirect("/h5/article/trialIntro?classRoomId=" + classRoomId + "&recommendMID=" + inviter);
			return;
		}

		if(inviter.equals(invitee) == false){
			Db.update("INSERT IGNORE INTO free_lesson_invite_record (product_id,inviter,invitee) VALUES (?,?,?)", productId,
					inviter, invitee);
		}

		LessonProductImage lessonProductImage = LessonProductImage.dao
				.findFirst("SELECT * FROM lesson_product_image WHERE product_id = ?", productId);

		Member inviterMember = Member.dao.findById(inviter);
		ClassRoom classRoom = ClassRoom.dao.findById(classRoomId);

		setAttr("lessonProductImage", lessonProductImage);
		setAttr("classRoom", classRoom);
		setAttr("productId", productId);
		setAttr("inviter", inviter);
		setAttr("inviterMember", inviterMember);

		renderTemplate("/articles/freeLesson.html");

	}

	public void sendFreeLessonSms() {

		String member = (String) getSession().getAttribute("WX#USER");

		Integer productId = getParaToInt("product");
		String mobile = getPara("mobile");
		Integer classRoomId = getParaToInt("classRoom");
		Integer inviter = getParaToInt("inviter");
		String captcha = getPara("captcha");

		if (xx.isOneEmpty(member, productId, mobile, classRoomId, inviter, captcha)) {
			Result.error(203, this);
			return;
		}

		if (CommonUtils.checkMobile(mobile) == false) {
			Result.error(20301, "请输入正确手机号码", this);
			return;
		}

		Cache redis = Redis.use();
		System.out.println(getSession().getId());
		String redisCaptcha = redis.get(getSession().getId() + "#captcha");
		redis.close(redis.getJedis());

		if (redisCaptcha == null) {
			Result.error(20302, "验证码走丢了,稍后再尝试下吧~", this);
			return;
		} else if (redisCaptcha.toLowerCase().equals(captcha.toLowerCase()) == false) {
			Result.error(20303, "图形验证码错误", this);
			return;
		}

		// 设置邀请码失效时间
		long invalidTime = 1000 * 60 * 60 * 24 * 3;
		Date endDate = new Date(System.currentTimeMillis() + invalidTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String randomCode = CommonUtils.getRandomNumber(6);

		InvitationCode invitationCode = new InvitationCode();
		invitationCode.setClassRoomId(classRoomId);
		invitationCode.setProductId(productId);
		invitationCode.setRandomCode(randomCode);
		invitationCode.setMemberId(Integer.parseInt(member.split("#")[1]));
		invitationCode.setInvalidTime(endDate);
		invitationCode.setMobile(mobile);
		invitationCode.setRecommendMemberId(inviter).save();

		try {
			TAOBAOSMS.sendClassInvite(randomCode, sdf.format(endDate), mobile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Result.ok(this);

	}

	public void hasGrade(){
		
		Integer productId = getParaToInt("product");
		Integer studentId = getParaToInt("student");
		
		if(xx.isOneEmpty(productId,studentId)){
			Result.error(203, this);
			return;
		}
		
		Mallproduct mallproduct = Mallproduct.dao.findById(productId);
		
		if(mallproduct==null){
			Result.error(20501, "商品不存在",this);
			return;
		}
		
		ClassGradesRela classGradesRela = ClassGradesRela.dao.findFirst(
				"SELECT * FROM `class_grades_rela` WHERE class_grades_id = ? AND class_student_id = ? and gradesStatus > -1",mallproduct.getClassGradeId(),studentId);
		
		JSONObject json = new JSONObject();
		
		if(classGradesRela==null){
			json.put("status", 0);
		} else{
			json.put("status", 1);
		}
		
		Result.ok(json,this);
		
	}

	public void gradeShare(){
		
		Integer gradeId = getParaToInt("grade");
		
		/*Integer memberId = getParaToInt("member",0);
		
		List<ClassRoom> classRooms = ClassRoom.dao.find(
				"select cr.id,ifnull(cr.cover,'http://word.fandoutech.com.cn/app/OnlineCourse/18335775869/55745af535a4715174416d49f60fa3c0.jpg') cover,crc.category_name from class_room cr, class_course cc,class_room_category crc where cr.id = cc.class_room_id and crc.id = cr.category_id AND cc.class_grades_id = ?",gradeId);
		
		HashMap<String, List<ClassRoom>> classRoomMap = new HashMap<String, List<ClassRoom>>();
		
		
		 * 按课堂分类id分组
		 
		for(ClassRoom classRoom : classRooms){
			
			List<ClassRoom> tempList = classRoomMap.get(classRoom.getStr("category_name"));
			 
			if(tempList==null){
				tempList = new ArrayList<ClassRoom>();
				tempList.add(classRoom);
				classRoomMap.put(classRoom.getStr("category_name"), tempList);
			}else{
				tempList.add(classRoom);
			}
			
		}
		
		setAttr("categorys", classRoomMap);
		setAttr("memberId", memberId);
		
		if(classRooms.size()!=0 && classRooms.size()<=2){
			setAttr("padding",1	);
		}
		
		renderTemplate("/articles/gradeShare.html");*/
		
		redirect(Keys.SERVER_SITE+"/wechat/lesson/2c/grade_share.html?grade="+gradeId,true);
		
	}
	/*public static void main(String[] args) {
		NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
		noticeInfo.setAccessToKen("12_lz_j-tDhpnODQrFrU92q9cqLK3WjiF3XTpXmDedgIL6-i8ls4-u4X2sj5J0TG9EQatmiiULf2bkkRkD3UUYhK6AUBuyTOpYDK5dsjkA6Jg0JMHfxA5apktxq-Jzq-NAjPhUzv08nHYBoGG0AGQVdAFAKUZ");
		noticeInfo.setOpenId("org-Fwzean9TrrZaq3cpjyZTmH9I");
		noticeInfo.setMemberId("137569");
		noticeInfo.setFirst("有好友接受您的邀请，系统自动奖励积分");
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		
		noticeInfo.setKeyword1(format.format(date));
		noticeInfo.setKeyword2("80");
		noticeInfo.setKeyword3("邀请成功");
		noticeInfo.setRemark("若有疑问请致电客服中心:"+Keys.CUSTOMER_MOBLIE);
		try {
			WeChatUtil.sendLessonIntegral(noticeInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
}
