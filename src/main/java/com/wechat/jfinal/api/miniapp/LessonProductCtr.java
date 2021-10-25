package com.wechat.jfinal.api.miniapp;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;

import com.google.gson.JsonObject;
import com.jfinal.core.Controller;
import com.jfinal.i18n.Res;
import com.jfinal.json.Json;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.render.RenderException;
import com.wechat.controller.PaymentController;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassProductOrder;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.ClassRoomOrder;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.InvitationCode;
import com.wechat.jfinal.model.LessonProductImage;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Mallspecifications;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.model.MiniappTemplateForm;
import com.wechat.jfinal.service.ClassroomPackageService;
import com.wechat.jfinal.service.UserService;
import com.wechat.jfinal.service.WechatPayService;
import com.wechat.pay.util.WXUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.CommonUtils;
import com.wechat.util.GraphicsUtils;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.SecurityUtil;
import com.wechat.util.TAOBAOSMS;

import net.sf.json.JSONObject;

public class LessonProductCtr extends Controller {

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

		if (member == null) {
			member = new Member();
			member.setMiniappOpenid(openid);
			member.setType(0).save();
		}

		Cache redis = Redis.use();

		String access_token = UUID.randomUUID().toString().replaceAll("-", "");
		redis.setex(access_token, 60 * 60 * 12,
				member.getId() + "#" + member.getMiniappOpenid() + "#" + System.currentTimeMillis());

		redis.close(redis.getJedis());

		JSONObject json = new JSONObject();
		json.put("access_token", access_token);
		json.put("member", member.getId());
		Result.ok(json, this);

	}

	public void createOrder() {

		Integer studentId = getParaToInt("stu");
		Integer productId = getParaToInt("product");
		Integer memberId = getParaToInt("member");

		if (xx.isAllEmpty(studentId, productId,memberId)) {
			Result.error(203, this);
			return;
		}

		JSONObject json = new JSONObject();

		Mallproduct mallProduct = Mallproduct.dao.findById(productId);

		if (mallProduct != null) {
			Mallspecifications mallSpecifications = Mallspecifications.dao
					.findFirst("select * from mallspecifications where productid = ?", productId);

			ClassProductOrder classProductOrder = new ClassProductOrder();
			classProductOrder.setProductId(productId);
			
			classProductOrder.setProductName(mallProduct.getName());

			String currTime = CommonUtils.getCurrTime();
			String strRandom = CommonUtils.buildRandomNumber(8) + "";
			classProductOrder.setOrderNumber(currTime + studentId + strRandom);
			classProductOrder.setStudentId(studentId);

			if (mallProduct.getClassRoomPackage() > 0) {
				classProductOrder.setClassPackId(mallProduct.getClassRoomPackage());
			} else {
				classProductOrder.setClassGradesId(mallProduct.getClassGradeId());
			}

			classProductOrder.setPrice(new BigDecimal(mallSpecifications.getPrice()));
			classProductOrder.setMemberId(memberId);

			classProductOrder.save();

			json.put("order", classProductOrder.getOrderNumber());

		} else {
			Result.error(20501, "商品不存在", this);
			return;
		}

		Result.ok(json, this);

	}

	public void pay() {

		String order = getPara("order");

		String acccess_token = getPara("acccess_token");

		Cache redis = Redis.use();
		String memberInfo = redis.get(acccess_token);

		ClassProductOrder classProductOrder = ClassProductOrder.dao
				.findFirst("select * from class_product_order where order_number = ?", order);

		HashMap<String, Object> map = new HashMap<>();
		map.put("commodityName", classProductOrder.getProductName());
		map.put("totalPrice", classProductOrder.getPrice());
		map.put("callBackUrl", Keys.SERVER_SITE + "/wechat/v2/lessonProduct/payResult");
		
		String ip = getRequest().getRemoteAddr();
		
		map.put("spbillCreateIp", getRequest().getRemoteAddr().split(",")[0]);
		map.put("appId", Keys.FANDDOU_MINIAPP_APP_ID);
		map.put("mchId", Keys.MCH_ID);
		map.put("apiKey", Keys.API_KEY);
		map.put("openId", memberInfo.split("#")[1]);
		map.put("orderNumber", classProductOrder.getOrderNumber());

		WechatPayService payService = new WechatPayService();

		SortedMap<Object, Object> params = new TreeMap<Object, Object>();

		try {

			Map wechatResult = payService.miniAppPay(map);

			params.put("appId", Keys.FANDDOU_MINIAPP_APP_ID);
			params.put("timeStamp", new Date().getTime() + "");
			params.put("nonceStr", WXUtil.getNonceStr());
			/**
			 * 获取预支付单号prepay_id后，需要将它参与签名。
			 * 微信支付最新接口中，要求package的值的固定格式为prepay_id=...
			 */
			params.put("package", "prepay_id=" + wechatResult.get("prepay_id"));

			/** 微信支付新版本签名算法使用MD5，不是SHA1 */
			params.put("signType", "MD5");
			/**
			 * 获取预支付prepay_id之后，需要再次进行签名，参与签名的参数有：appId、timeStamp、nonceStr、package、signType.
			 * 主意上面参数名称的大小写. 该签名用于前端js中WeixinJSBridge.invoke中的paySign的参数值
			 */
			String paySign = payService.createSign("UTF-8", params, Keys.API_KEY);
			params.put("paySign", paySign);

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject json = new JSONObject();
		json.put("payargs", params);
		Result.ok(json, this);

	}

	@EmptyParaValidate(params={"product"})
	public void productImg() {

		Integer productId = getParaToInt("product");

		LessonProductImage lessonProductImage = LessonProductImage.dao
				.findFirst("SELECT * FROM lesson_product_image WHERE product_id = ?", productId);

		JSONObject json = new JSONObject();

		json.put("imgs", Result.toJson(lessonProductImage));

		Result.ok(json, this);
	}

	public void payResult() throws Exception {

		HttpServletRequest request = getRequest();
		String result = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int count = 0;
			while ((count = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, count);
			}
			byte[] strByte = bos.toByteArray();
			result = new String(strByte, 0, strByte.length, "utf-8");
			bos.close();
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** 解析微信返回的信息，以Map形式存储便于取值 */
		Map<String, String> map = XMLUtil.doXMLParse(result);
		String return_code = map.get("return_code");

		if (return_code != null && return_code.equals("SUCCESS")) {
			renderText(PaymentController.setXML("SUCCESS", "OK"));

			// 商户订单号
			String orderNumber = map.get("out_trade_no");
			// 支付结果
			String resultCode = map.get("result_code");  

			if ("SUCCESS".equals(resultCode)) {

				Db.update("UPDATE class_product_order SET `status` = 1 WHERE order_number = ?", orderNumber);

				// 获取商品
				ClassProductOrder classProductOrder = ClassProductOrder.dao
						.findFirst("SELECT * FROM `class_product_order` where order_number = ?", orderNumber);
				ClassroomPackageService classroomPackageService = new ClassroomPackageService();

				try {

					// 绑定班级
					if (classProductOrder.getClassGradesId() > 0) {
						classroomPackageService.addStudent2grade(classProductOrder.getStudentId(),
								classProductOrder.getClassGradesId());
					}

					// 绑定课程包
					if (classProductOrder.getClassPackId() > 0) {

						classroomPackageService.bindStudentWithPack(classProductOrder.getStudentId(),
								classProductOrder.getClassPackId());

					}

				} catch (Exception e) {
					e.printStackTrace();
					Result.error(500, this);
					return;
				}

			}

		} else {
			renderText(PaymentController.setXML("FAIL", "FAIL"));
		}
	}

	public void checkOrder() {

		String orderNumber = getPara("order");

		int payStatus = 0;

		if (xx.isEmpty(orderNumber)) {
			Result.error(203, this);
			return;
		}

		ClassRoomOrder classRoomOrder = ClassRoomOrder.dao
				.findFirst("SELECT * FROM class_product_order WHERE order_number = ?", orderNumber);

		if (classRoomOrder != null) {
			payStatus = classRoomOrder.getStatus();
		} else {
			Result.error(205, "查询不到订单信息", this);
			return;
		}

		JSONObject result = new JSONObject();
		result.put("payStatus", payStatus);
		Result.ok(result, this);

	}

	public void shareLessonInfo() {

		Integer classRoomId = getParaToInt("classRoom");

		Integer memberId = getParaToInt("member");

		if (xx.isOneEmpty(classRoomId, memberId)) {
			Result.error(203, this);
			return;
		}

		Member member = Member.dao.findFirst("SELECT nickname,headimgurl FROM member WHERE id = ?", memberId);

		ClassRoom classRoom = ClassRoom.dao
				.findFirst("SELECT class_name,teacher_name,summary FROM class_room WHERE id = ?", classRoomId);

		JSONObject json = new JSONObject();
		json.put("member", Result.toJson(member));
		json.put("classRoom", Result.toJson(classRoom));

		Result.ok(json, this);

	}

	@EmptyParaValidate(params={"vCode","mobile","product"})
	public void sendSmsWithInvitationCode() {

		String vCode = getPara("vCode");

		String mobile = getPara("mobile");
		
		Integer productId = getParaToInt("product");
		
		System.out.println(getPara("product"));
		System.out.println(productId);

		if (mobile == null || mobile.equals("")) {
			Result.error(20301, "请输入手机号码", this);
			return;
		} else if (CommonUtils.checkMobile(mobile) == false) {
			Result.error(20302, "请输入正确手机号码", this);
			return;
		}

		Cache redis = Redis.use();

		String redisCode = redis.get("SMS#" + vCode.toLowerCase());

		if (redisCode==null) {
			Result.error(20401, "验证码错误", this);
			return;
		}else{
			redis.del(redisCode);
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
			invitationCode.setProductId(productId);
			invitationCode.save();

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

	public void captcha() {

		String code = CommonUtils.getRandomNumber(4);

		Cache redis = Redis.use();

		redis.setex("SMS#"+code.toLowerCase(), 60 * 5, code);

		HttpServletResponse response = getResponse();

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = null;
		try {
			BufferedImage image = new BufferedImage(108, 40, BufferedImage.TYPE_INT_RGB);
			GraphicsUtils.drawGraphic(code, image);

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

		UserService userService = new UserService();
		Memberaccount memberaccount = userService.registerByClassAtrial(invitationCode.getMobile(),
				invitationCode.getMemberId());
		
		ClassStudent classStudent = userService.createDefaultStudent(invitationCode.getMobile(),invitationCode.getMemberId());
		
		ClassroomPackageService classroomPackageService = new ClassroomPackageService();
		classroomPackageService.bindStudentWithClassRoom(classStudent.getId(), classRoomId);

		String tokenStr = "device" + "#" + invitationCode.getMobile() + "#" + memberaccount.getPassword() + "#"
				+ System.currentTimeMillis();

		String accessToken = SecurityUtil.encrypt(tokenStr, "fandou");
		Cache redis = Redis.use();
		redis.set("device" + "#" + memberaccount.getAccount(), accessToken);

		JSONObject json = new JSONObject();
		json.put("classRoomId", classRoomId);
		json.put("productId", productId);
		json.put("accessToken", accessToken);
		json.put("account", memberaccount.getAccount());
		json.put("memberId", invitationCode.getMemberId());
		Result.ok(json, this);

	}

	public void saveForm() {

		String acccess_token = getPara("acccess_token");
		String formId = getPara("form");

		if (xx.isOneEmpty(acccess_token, formId)) {
			Result.error(203, this);
			return;
		}

		Cache redis = Redis.use();
		String memberInfo = redis.get(acccess_token);

		if (memberInfo == null) {
			Result.error(209, this);
			return;
		}

		Db.update(
				"INSERT INTO miniapp_template_form (miniapp_openid,form_id) VALUES (?,?) ON DUPLICATE KEY UPDATE form_id = ?",
				memberInfo.split("#")[1], formId, formId);

		Result.ok(this);

	}
	
	public static void main(String[] args) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT " + " c.student_id, " + " c. NAME, " + " c.score, " + " c.audioUrl, " + " date_format(  "
				+ "  c.recordDate,   " + "  '%Y-%c-%d %h:%i:%s'   " + "   ) recordDate,   " + " c.fluency, "
				+ " c.accuracy, " + " c.integrity, " + " c.timeCost, " + " ifnull( " + "  cc.id, " + "  0 "
				+ " ) classCourseCommendId, " + " cc.teacher_sound, " + " cc.teacher_score, " + " c.class_course_id, "
				+ " ifnull( " + "  date_format( " + " cc.update_date, " + " '%Y-%c-%d %h:%i:%s' " + "  ), "
				+ "  c.recordDate " + " ) teacherRecordDate, " + " c.do_title " + " FROM " + " ( " + "  SELECT "
				+ "   cr.student_id, " + "   s. NAME, " + "   cr.score, " + "   csr.audioUrl, "
				+ "   MAX(cr.complete_time) recordDate, " + "   csr.fluency, " + "   csr.accuracy, "
				+ "   csr.integrity, " + "   csr.timeCost, " + "   cr.id class_course_id, "
				+ "   r.class_name do_title " + "  FROM " + "   class_course_record cr "
				+ "  LEFT JOIN class_course_score_record csr ON cr.id = csr.classCourseRecord_id "
				+ "  AND cr.score = csr.score, " + "  class_student s, " + "  class_room r , " + "  class_course cc "
				+ "  WHERE " + "  cr.class_grades_id = :classGradesId " + " AND cr.complete_time >  :startTime  "
				+ " AND cr.complete_time <  :endTime  " + " AND cr.student_id = s.id "
				+ " AND r.id = cr.class_room_id  AND cr.class_course_id = cc.id " + " AND cc.do_slot = 300 "
				+ " GROUP BY " + "  student_id " + " ) c "
				+ "LEFT JOIN class_course_comment cc ON c.class_course_id = cc.class_course_id "
				+ "AND c.student_id = cc.student_id");
		
		System.out.println(sql.toString());
	}

}
