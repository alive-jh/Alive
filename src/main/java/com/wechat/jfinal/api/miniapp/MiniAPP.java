package com.wechat.jfinal.api.miniapp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.i18n.Res;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.render.RenderException;
import com.jfinal.upload.UploadFile;
import com.wechat.entity.MallProduct;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.interceptor.FandouLibraryAccessInterceptor;
import com.wechat.jfinal.model.AppidSecret;
import com.wechat.jfinal.model.Bookshop;
import com.wechat.jfinal.model.MallProductBanner;
import com.wechat.jfinal.model.MallProductMiniapp;
import com.wechat.jfinal.model.MallProductPicture;
import com.wechat.jfinal.model.MallProductSpecification;
import com.wechat.jfinal.model.Mallspecifications;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.model.MiniappMember;
import com.wechat.util.AES;
import com.wechat.util.CommonUtils;
import com.wechat.util.GraphicsUtils;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.SecurityUtil;
import com.wechat.util.TAOBAOSMS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MiniAPP extends Controller {

	
	private final String GET_OPPEN_ID_API = "https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * 根据loginCode获取member信息
	 * 
	 *            小程序登录code 必填
	 * 
	 */
	public void login() {

		String loginCode = getPara("loginCode");

		if (xx.isEmpty(loginCode)) {
			Result.error(203, this);
			return;
		}

		// 拼接微信获取用户信息接口所需参数
		String prams = "appid=" + Keys.FANDDOU_MINIAPP_APP_ID + "&secret=" + Keys.FANDDOU_MINIAPP_APP_SECRET
				+ "&js_code=" + loginCode + "&grant_type=authorization_code";

		JSONObject result = null;
		String openid = null;

		try {

			// 请求并接受返回结果
			result = HttpRequest.doGet(Keys.MINIAPP_GET_OPPEN_ID_API, prams);
			openid = result.getString("openid");

		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205, "获取用户信息失败", this);
			return;
		}

		Member member = Member.dao.findFirst("select * from member where miniapp_openid = ?", openid);

		JSONObject json = new JSONObject();

		if (member == null) {

			json.put("access_token", 0);
			json.put("memberId", 0);

		} else {

			Memberaccount memberaccount = Memberaccount.dao.findFirst(
					"select * from memberaccount where memberid = ? and (type<=2 or type =5)", member.getId());
			if (memberaccount == null) {

				json.put("access_token", 0);
				json.put("memberId", 0);

			} else {
				String tokenStr = "device#" + memberaccount.getAccount() + "#" + memberaccount.getPassword() + "#"
						+ System.currentTimeMillis();
				String access_token = null;

				try {
					access_token = SecurityUtil.encrypt(tokenStr, "fandou");
				} catch (Exception e) {
					e.printStackTrace();
					Result.error(500, this);
					return;
				}

				Cache redis = Redis.use();
				redis.set("device#" + memberaccount.getAccount(), access_token);
				json.put("access_token", access_token);
				json.put("memberId", member.getId());
			}

		}

		json.put("openid", openid);
		Result.ok(json, this);

	}

	/**
	 * 检查用户存不存在
	 * 
	 *            密码 必填
	 * 
	 */
	public void userCheck() {

		String account = getPara("account");
		String password = getPara("password");

		String openid = getPara("openid");

		if (xx.isOneEmpty(account, password, openid)) {
			Result.error(203, this);
			return;
		}

		// 根据帐号密码获取账户信息
		Memberaccount memberaccount = Memberaccount.dao.findFirst(
				"select * from memberaccount where `account` = ? and `password` = ? and (type <=2 or type = 5)",
				account, password);

		JSONObject json = new JSONObject();

		if (memberaccount == null) {
			Result.error(20501, "查询不到用户信息", this);
			return;
		} else {

			Db.update("update member set miniapp_openid = ? where id = ?", openid, memberaccount.getMemberid());

			String tokenStr = "device#" + account + "#" + password + "#" + System.currentTimeMillis();

			String access_token = null;

			try {
				access_token = SecurityUtil.encrypt(tokenStr, "fandou");
			} catch (Exception e) {
				e.printStackTrace();
				Result.error(500, this);
				return;
			}

			Cache redis = Redis.use();
			redis.set("device#" + account, access_token);

			json.put("memberId", memberaccount.getMemberid());
			json.put("access_token", access_token);
		}

		Result.ok(json, this);

	}

	/**
	 * 获取学生学习记录
	 * 
	 *            指定时间
	 */
	public void studentStudyList() {

		Integer memberId = getParaToInt("memberId", 0);
		String account = getPara("account");

		String time = getPara("time");

		if (xx.isOneEmpty(memberId, time)) {
			Result.error(203, this);
			return;
		}

		// 声明返回json容器
		JSONObject result = new JSONObject();

		// 获取该帐号与memberid所有学生id
		StringBuffer studentSql = new StringBuffer(
				"SELECT GROUP_CONCAT(a.id) AS student_ids FROM (SELECT a.id FROM class_student a,device_relation b,device c");
		studentSql.append(" WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id = ? and b.");
		studentSql.append(
				"isbind = 1 UNION SELECT a.id FROM `class_student` a,device b WHERE a.epal_id = b.epal_id AND a.member_id = ?) a");

		// 所有学生id
		Record studentIds = Db.findFirst(studentSql.toString(), account, memberId);

		// 获取学习记录
		StringBuffer studyRecordSql = new StringBuffer(
				"SELECT d.id AS student_id,d.`name` AS student_name,a.do_day AS study_day,c.wordList as word_list,b.used_time,a.class_room_id");
		studyRecordSql
				.append(" AS study_time,a.class_room_id FROM class_course a, (SELECT * FROM `class_course_record`");
		studyRecordSql.append("WHERE student_id in (?) AND DATE_FORMAT(complete_time,'%Y%m%d') = ?");
		studyRecordSql.append(
				" ORDER BY complete_time DESC LIMIT 1) b,class_course_score_record c,class_student d WHERE a.id = b.");
		studyRecordSql.append(
				"class_course_id AND b.id = c.classCourseRecord_id AND b.student_id = d.id ORDER BY c.id DESC LIMIT 1");

		Record studyRecord = Db.findFirst(studyRecordSql.toString(), studentIds.getStr("student_ids"), time);

		// 学习记录为空返回空的json
		if (studyRecord == null) {
			result.put("studyRecord", new JSONObject());
		} else {

			// 学习的课堂
			int classRoomId = studyRecord.getInt("class_room_id");

			// 获取课堂信息
			Record classRoomInfo = Db.findFirst("SELECT a.*,c.url AS audio FROM (SELECT a.id AS class_room_id ,"
					+ "a.class_name,b.`name` AS teacher_name FROM class_room a,class_teacher b WHERE a.teacher_id = "
					+ "b.id AND a.id = ?) a LEFT JOIN class_room_audio_rel b ON a.class_room_id = "
					+ "b.class_room_id LEFT JOIN material_file c ON b.audio_id = c.audioinfo_id", classRoomId);

			studyRecord.set("class_name", classRoomInfo.getStr("class_name"));
			studyRecord.set("teacher_name", classRoomInfo.getStr("teacher_name"));
			studyRecord.set("audio", classRoomInfo.getStr("audio"));

			result.put("studyRecord", Result.toJson(studyRecord.getColumns()));
		}

		Result.ok(result, this);

	}

	public void onLogin() {

		String loginCode = getPara("loginCode");
		String appId = getPara("loginKey");

		if (xx.isOneEmpty(loginCode, appId)) {
			Result.error(203, this);
			return;
		}

		AppidSecret appidSecret = AppidSecret.dao.findFirst("select appid, secret from appid_secret where appid = ?",
				appId);

		if (appidSecret == null) {
			Result.error(20301, "loginKey无效", this);
			return;
		}

		// 拼接微信获取用户信息接口所需参数
		String prams = "appid=" + appId + "&secret=" + appidSecret.getSecret() + "&js_code=" + loginCode
				+ "&grant_type=authorization_code";

		JSONObject result = null;
		String openid = null;

		try {
			// 请求并接受返回结果
			result = HttpRequest.doGet(Keys.MINIAPP_GET_OPPEN_ID_API, prams);

			if (result.has("openid")) {
				openid = result.getString("openid");
			} else {
				Result.error(20501, "获取用户信息失败", this);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Result.error(20501, "获取用户信息失败", this);
			return;
		}

		MiniappMember member = MiniappMember.dao.findFirst("SELECT * FROM miniapp_member WHERE miniapp_openid = ?",
				openid);

		JSONObject json = new JSONObject();

		String tokenStr = "";

		if (member == null) {
			member = new MiniappMember();
			member.setMiniappOpenid(openid).save();
		}

		member.setLastLogin(new Date()).update();

		tokenStr = "miniapp#" + member.getId() + "#" + member.getMiniappOpenid() + "#" + System.currentTimeMillis();

		String access_token = null;

		try {
			access_token = SecurityUtil.encrypt(tokenStr, "miniapp");
			Cache redis = Redis.use();
			redis.setex(access_token, 60 * 60 * 60 * 12, openid + "#" + appId);
		} catch (Exception e) {
			Result.error(20501, "用户信息加密失败", this);
			return;
		}

		json.put("access_token", access_token);
		json.put("mobile", member.getMobile() == null ? "" : member.getMobile());
		Result.ok(json, this);

	}

	@Before(FandouLibraryAccessInterceptor.class)
	public void saveMember() {

		MiniappMember memberBean = getBean(MiniappMember.class, "");

		String redisToken = (String) getRequest().getAttribute("redisToken");
		MiniappMember memberDb = MiniappMember.dao.findFirst("SELECT * FROM miniapp_member WHERE miniapp_openid = ?",
				redisToken.split("#")[0]);

		if (memberDb == null) {
			memberBean.save();
		} else {
			memberBean.setId(memberDb.getId());
			memberBean.update();
		}

		Result.ok(this);
	}

	@Before(FandouLibraryAccessInterceptor.class)
	public void getMember() {

		String redisToken = (String) getRequest().getAttribute("redisToken");

		Result.ok(Result.toJson(MiniappMember.dao.findFirst(
				"SELECT id,nick_name,avatar_url,gender,IFNULL(mobile,'') AS mobile,country,province,city,language,create_time,update_time,last_login FROM miniapp_member WHERE miniapp_openid = ?",
				redisToken.split("#")[0])), this);
	}

	@Before(FandouLibraryAccessInterceptor.class)
	public void getBookShop() {

		Cache redis = Redis.use();

		String tokenValue = redis.get(getPara("access_token"));

		if (tokenValue == null) {
			Result.error(209, "未登录，请重新登录", this);
			return;
		}

		Bookshop bookshop = Bookshop.dao.findFirst(
				"SELECT a.* FROM `bookshop` a JOIN appid_secret b ON a.id = b.library_id WHERE b.appid = ?",
				tokenValue.split("#")[1]);

		JSONObject json = new JSONObject();
		json.put("bookshop", Result.toJson(bookshop));

		Result.ok(bookshop, this);

	}

	public JSONObject decryptUserInfo(String encryptedData, String sessionKey, String iv)
			throws InvalidAlgorithmParameterException, UnsupportedEncodingException {

		String _userInfo = "";

		byte[] resultByte = AES.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey),
				Base64.decodeBase64(iv));

		if (null != resultByte && resultByte.length > 0) {
			_userInfo = new String(resultByte, "UTF-8");
		}

		return JSONObject.fromObject(_userInfo);

	}

	public void captcha() {

		String captchaKey = getPara("captchaKey");
		String random = CommonUtils.getRandomNumber(4);

		Cache redis = Redis.use();
		redis.setex(captchaKey, 60 * 5, random);
		HttpServletResponse response = getResponse();

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = null;
		try {
			BufferedImage image = new BufferedImage(108, 40, BufferedImage.TYPE_INT_RGB);
			GraphicsUtils.drawGraphic(random, image);

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

	public void sendBindSMS()   {

		String captcha = getPara("captcha");
		String captchaKey = getPara("captchaKey");
		String mobile = getPara("mobile");

		Cache redis = Redis.use();

		String redisCaptcha = redis.get(captchaKey);

		if (redisCaptcha != null && redisCaptcha.equals(captcha)) {
			redis.del(captchaKey);
		} else {
			Result.error(20501, "验证码错误", this);
			return;
		}

		String smsCode = CommonUtils.getRandomNumber(6);
		redis.setex("miniapp#bind#" + mobile, 60 * 15, smsCode);

		TAOBAOSMS.sendClassInvite(smsCode, smsCode, mobile);

		Result.ok(this);

	}

	@Before(FandouLibraryAccessInterceptor.class)
	public void bindMobile() {

		String mobile = getPara("mobile");
		String smsCode = getPara("smsCode");

		if (xx.isOneEmpty(mobile, smsCode)) {
			Result.error(203, this);
			return;
		}

		Cache redis = Redis.use();

		String errorKey = "miniapp#smscode#error#" + mobile;

		Integer errorCount = redis.get(errorKey);

		if (errorCount == null) {
			redis.setex(errorKey, 60 * 60 * 6, 1);
		} else if (errorCount >= 6) {
			Result.error(20501, "验证码错误次数过多,帐号已被锁定,请6小时候再试", this);
			return;
		} else {
			redis.set(errorKey, errorCount + 1);
		}

		if (redis.get("miniapp#bind#" + mobile) != null && smsCode.equals(redis.get("miniapp#bind#" + mobile))) {

		} else {
			Result.error(20501, "短信验证码错误", this);
			return;
		}

		Db.update("update miniapp_member set mobile = ? where miniapp_openid = ?", mobile,
				((String) getRequest().getAttribute("redisToken")).split("#")[0]);

		Result.ok(this);

	}

	@Before(FandouLibraryAccessInterceptor.class)
	public void customerSvcInfo() {

		Bookshop bookshop = Bookshop.dao.findFirst(
				"SELECT b.email,b.telephone,b.contacts FROM `appid_secret` a JOIN bookshop b ON a.library_id = b.id WHERE a.appid = ?",
				((String) getRequest().getAttribute("redisToken")).split("#")[1]);

		JSONObject json = new JSONObject();

		json.put("bookshop", Result.toJson(bookshop));

		Result.ok(json, this);
	}

	public void libraryProductList() {

		Integer libraryId = getParaToInt("libraryId");

		int pageNumber = getParaToInt("pageSize", 1);
		int pageSize = getParaToInt("", 20);

		StringBuffer sql = new StringBuffer();
		sql.append(
				"FROM `library_product` a JOIN mall_product_miniapp b ON a.mall_product_miniapp_id = b.id LEFT JOIN");
		sql.append(" mall_product_picture c ON b.id = c.mall_produuct_id WHERE a.library_id = ? GROUP BY b.id ");

		Page<Record> page = Db.paginate(pageNumber, pageSize,
				"SELECT b.*,GROUP_CONCAT(c.pic_url) AS mall_product_pictures", sql.toString(), libraryId);

		for (Record record : page.getList()) {

			String mallProductPictures = record.getStr("mall_product_pictures");

			JSONArray array = new JSONArray();
			;

			if (mallProductPictures != null) {

				String[] mallProductPictureArray = mallProductPictures.split(",");

				for (String pic : mallProductPictureArray) {
					array.add(pic);
				}

			}

			record.set("pictures", array);
			record.remove("mall_product_pictures");

		}

		Result.ok(page, this);

	}

	public void saveProduct() {

		MallProductMiniapp product = getBean(MallProductMiniapp.class, "product");

		Integer specificationCount = getParaToInt("specificationCount", 1);

		List<MallProductSpecification> mallProductSpecifications = getBeans(MallProductSpecification.class,
				"specification", specificationCount);

		String[] banners = getParaValues("banners");
		String[] pictures = getParaValues("pictures");

		product.save();

		for (MallProductSpecification specification : mallProductSpecifications) {
			specification.setMallProductId(product.getId());
		}

		Db.batchSave(mallProductSpecifications, 200);

		List<MallProductBanner> mallProductBanners = new ArrayList<MallProductBanner>(banners.length);

		for (int i = 0; i < banners.length; i++) {
			MallProductBanner mallProductBanner = new MallProductBanner();
			mallProductBanner.setMallProductId(product.getId());
			mallProductBanner.setBannerUrl(banners[i]);
			mallProductBanners.add(mallProductBanner);
		}

		Db.batchSave(mallProductBanners, 200);

		List<MallProductPicture> mallProductPictures = new ArrayList<MallProductPicture>(pictures.length);

		for (int i = 0; i < pictures.length; i++) {
			MallProductPicture mallProductPicture = new MallProductPicture();
			mallProductPicture.setMallProduuctId(product.getId());
			mallProductPicture.setPicUrl(pictures[i]);
			mallProductPictures.add(mallProductPicture);
		}

		Db.batchSave(mallProductPictures, 200);

		Result.ok(this);
	}

	public <T> List<T> getBeans(Class<T> beanClass, String beanName, int cout) {
		List<T> list = new ArrayList<T>();

		for (int i = 0; i < cout; i++) {
			list.add(getBean(beanClass, beanName + "" + i));
		}

		return list;
	}

	public void encrypted() {
		String loginCode = getPara("loginCode");
		String encryptedData = getPara("encryptedData");
		String iv = getPara("iv");

		if (xx.isOneEmpty(loginCode, encryptedData, iv)) {
			Result.error(203, this);
			return;
		}

		String prams = "appid=" + Keys.APP_ID + "&secret=" + Keys.APP_SECRET + "&js_code=" + loginCode
				+ "&grant_type=authorization_code";

		JSONObject result = null;

		try {
			result = HttpRequest.doGet(GET_OPPEN_ID_API, prams);
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205, "获取用户信息失败", this);
		}

		String sessionKey = result.getString("session_key");

		String _userInfo = "";
		try {
			byte[] resultByte = AES.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey),
					Base64.decodeBase64(iv));

			if (null != resultByte && resultByte.length > 0) {
				_userInfo = new String(resultByte, "UTF-8");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205, "解密失败", this);
		}

		JSONObject userInfo = JSONObject.fromObject(_userInfo);

		String openId = userInfo.getString("openId");

		Member member = Member.dao.findFirst("SELECT * FROM member where openid = ?", openId);

		if (member == null) {
			member = new Member();
			member.setType(4);
			member.save();
		}

		member.setNickname(userInfo.getString("nickName"));
		member.setOpenid(userInfo.getString("openId"));

		int gender = userInfo.getInt("gender");
		if (1 == gender) {
			member.setSex("男");
		}
		if (2 == gender) {
			member.setSex("女");
		}
		if (-1 == gender) {
			member.setSex("未知");
		}

		member.setCity(userInfo.getString("city"));
		member.setProvince(userInfo.getString("province"));
		member.setHeadimgurl(userInfo.getString("avatarUrl"));
		member.update();

		String openKey = CommonUtils.getRandomString(6);
		
		Cache redis = Redis.use();
		redis.setex(openKey, 1000 * 60 * 60 * 6, openId);

		result.put("openKey", openKey);
		result.put("memberId", member.getId());
		Result.ok(result, this);
	}

}
