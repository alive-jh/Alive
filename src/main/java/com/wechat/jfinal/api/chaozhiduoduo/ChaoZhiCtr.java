package com.wechat.jfinal.api.chaozhiduoduo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.jdom.JDOMException;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.JsonObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.controller.PaymentController;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ChaozhiOrder;
import com.wechat.jfinal.model.ChaozhiStudyFriend;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.Smscode;
import com.wechat.jfinal.service.ChaozhiSvc;
import com.wechat.jfinal.service.WechatPayService;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.pay.util.WXUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.AES;
import com.wechat.util.CommonUtils;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChaoZhiCtr extends Controller {

	private final String GET_OPPEN_ID_API = "https://api.weixin.qq.com/sns/jscode2session";

	private final String CHAOZHI_APP_ID = "wxb233a63b4fa618a8";

	private final String CHAOZHI_SECRET = "a25ed2dc894a6d7d192878e172beb1ef";

	private final String CHAOZHI_MACH_ID = "1501455881";

	private final String CHAOZHI_API_KEY = "15235882245224544116847751503497";

	private final String CHAOZHI_SMS_KEY = "LTAIyD53iRPKLFae";

	private final String CHAOZHI_SMS_SECRET = "IXOn73q9wTarMLjm8rBUw4JiUkGCls";

	public static final String CHAOZHI_MINI_APP_SERVER = "https://app.fandoutech.com.cn/wechat";

	// ????????????:???????????????API??????,?????????????????????
	private static final String product = "Dysmsapi";
	// ????????????,?????????????????????
	private static final String domain = "dysmsapi.aliyuncs.com";
	
	private Cache redis = Redis.use();

	public void goPay() {

		String loginCode = getPara("loginCode");

		String mobile = getPara("mobile");
		
		if (xx.isOneEmpty(loginCode, mobile)) {
			Result.error(203, this);
			return;
		}
		
		

		String prams = "appid=" + CHAOZHI_APP_ID + "&secret=" + CHAOZHI_SECRET + "&js_code=" + loginCode
				+ "&grant_type=authorization_code";

		String openId = "";
		try {

			openId = HttpRequest.doGet(GET_OPPEN_ID_API, prams).getString("openid");

		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205, "??????openid??????", this);
			return;
		}

		ChaozhiSvc chaozhiSvc = new ChaozhiSvc();

		WechatPayService payService = new WechatPayService();

		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = TenpayUtil.buildRandom(4) + "";

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("commodityName", "????????????");
		map.put("totalPrice", 0.01);
		map.put("callBackUrl", CHAOZHI_MINI_APP_SERVER + "/ajax/chaozhi/payResult");
		map.put("spbillCreateIp", getRequest().getRemoteAddr());
		map.put("appId", CHAOZHI_APP_ID);
		map.put("mchId", CHAOZHI_MACH_ID);
		map.put("apiKey", CHAOZHI_API_KEY);
		map.put("openId", openId);
		map.put("orderNumber", strTime + strRandom);
		map.put("mobile", mobile);

		boolean success = chaozhiSvc.createOrder(map);

		SortedMap<Object, Object> params = new TreeMap<Object, Object>();

		try {

			Map wechatResult = payService.miniAppPay(map);

			params.put("appId", CHAOZHI_APP_ID);
			params.put("timeStamp", new Date().getTime() + "");
			params.put("nonceStr", WXUtil.getNonceStr());
			/**
			 * ?????????????????????prepay_id?????????????????????????????????
			 * ????????????????????????????????????package????????????????????????prepay_id=...
			 */
			params.put("package", "prepay_id=" + wechatResult.get("prepay_id"));

			/** ???????????????????????????????????????MD5?????????SHA1 */
			params.put("signType", "MD5");
			/**
			 * ???????????????prepay_id???????????????????????????????????????????????????????????????appId???timeStamp???nonceStr???package???signType.
			 * ????????????????????????????????????. ?????????????????????js???WeixinJSBridge.invoke??????paySign????????????
			 */
			String paySign = payService.createSign("UTF-8", params, CHAOZHI_API_KEY);
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

		//System.out.println(result);

		/** ?????????????????????????????????Map???????????????????????? */
		Map<String, String> map = XMLUtil.doXMLParse(result);
		String return_code = map.get("return_code");

		if (return_code != null && return_code.equals("SUCCESS")) {
			renderText(PaymentController.setXML("SUCCESS", "OK"));

			// ???????????????
			String orderNumber = map.get("out_trade_no");
			// ????????????
			String resultCode = map.get("result_code");

			if ("SUCCESS".equals(resultCode)) {
				Db.update("UPDATE chaozhi_order SET `status` = 1 WHERE order_number = ?", orderNumber);
			}

		} else {
			renderText(PaymentController.setXML("FAIL", "FAIL"));
		}
	}

	public void sendMessage() {

		String mobile = getPara("mobile");
		String type = getPara("type", "miniapp");

		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// ?????????acsClient,????????????region???
		IClientProfile profile = DefaultProfile.getProfile("cn-beijing", CHAOZHI_SMS_KEY, CHAOZHI_SMS_SECRET);
		try {
			DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", product, domain);
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// ??????????????????-????????????????????????-??????????????????
		SendSmsRequest request = new SendSmsRequest();

		// ??????:??????????????????
		request.setPhoneNumbers(mobile);
		// ??????:????????????-??????????????????????????????
		request.setSignName("????????????");
		// ??????:????????????-??????????????????????????????
		if ("miniapp".equals(type)) {
			request.setTemplateCode("SMS_129744281");
		} else if ("login".equals(type)) {
			request.setTemplateCode("SMS_130790029");
			int random = (int) (Math.random() * 1000000);
			String jsonParam = "{\"code\":\"" + random + "\",\"user\":\"" + mobile + "\"}";
			Smscode smsCode = new Smscode();
			smsCode.setMobile(mobile);
			smsCode.setCode(random);
			smsCode.setCreatedate(new Date());
			smsCode.save();
			request.setTemplateParam(jsonParam);
		} else {
			request.setTemplateCode("SMS_130790029");
		}

		// ??????:????????????????????????JSON???,??????????????????"???????????????,??????????????????${code}"???,???????????????

		// hint ????????????????????????????????????catch
		try {
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			//System.out.println(sendSmsResponse.getCode());
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Result.ok(this);

	}

	public void jscode2Session() {

		String loginCode = getPara("loginCode");
		
		if (xx.isEmpty(loginCode)) {
			Result.error(203, this);
			return;
		}

		String prams = "appid=" + CHAOZHI_APP_ID + "&secret=" + CHAOZHI_SECRET + "&js_code=" + loginCode
				+ "&grant_type=authorization_code";

		JSONObject result = null;
		String openId = null;
		
		try {
			result = HttpRequest.doGet(GET_OPPEN_ID_API, prams);
			openId = result.getString("openid");
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205, "????????????????????????", this);
		}
		
		ChaozhiOrder chaozhiOrder = ChaozhiOrder.dao.findFirst("select * from chaozhi_order where open_id = ? and status = 1",openId);
		
		JSONObject json = new JSONObject();
		json.put("openId", openId);
		if(xx.isEmpty(chaozhiOrder)){
			json.put("isPay", 0);
		}else{
			Member member = Member.dao.findFirst("SELECT * FROM member where openid = ?",openId);
			json.put("isPay", 1);
			json.put("memberId", member.getId());
		}
		
		Result.ok(json,this);
		/*String loginCode = getPara("loginCode");
		String encryptedData = getPara("encryptedData");
		String iv = getPara("iv");

		if (xx.isOneEmpty(loginCode, encryptedData, iv)) {
			Result.error(203, this);
			return;
		}

		String prams = "appid=" + CHAOZHI_APP_ID + "&secret=" + CHAOZHI_SECRET + "&js_code=" + loginCode
				+ "&grant_type=authorization_code";

		JSONObject result = null;

		try {
			result = HttpRequest.doGet(GET_OPPEN_ID_API, prams);
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205, "????????????????????????", this);
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
			Result.error(205, "????????????", this);
		}

		JSONObject userInfo = JSONObject.fromObject(_userInfo);
		
		String openId = userInfo.getString("openId");
		
		Member member = Member.dao.findFirst("SELECT * FROM member where openid = ?",openId);
		
		if(member==null){
			member = new Member();
			member.setType(4);
			member.save();
		}
		
		member.setNickname(userInfo.getString("nickName"));
		member.setOpenid(userInfo.getString("openId"));
		
		int gender = userInfo.getInt("gender");
		if (1==gender) {
			member.setSex("???");
		}
		if (2==gender) {
			member.setSex("???");
		}
		if (-1==gender) {
			member.setSex("??????");
		}
		
		member.setCity(userInfo.getString("city"));
		member.setProvince(userInfo.getString("province"));
		member.setHeadimgurl(userInfo.getString("avatarUrl"));
		member.update();
		
		String openKey = CommonUtils.getRandomString(6);
		redis.setex(openKey, 1000*60*60*6, openId);
		
		result.put("openKey", openKey);
		result.put("memberId", member.getId());
		Result.ok(result, this);*/

	}


	public void h5Pay() {
		String mobile = getPara("mobile");
		
		ChaozhiSvc chaozhiSvc = new ChaozhiSvc();

		WechatPayService payService = new WechatPayService();

		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = TenpayUtil.buildRandom(4) + "";
		String orderNumber = strTime + strRandom;

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("commodityName", "????????????");
		map.put("totalPrice", 0.01);
		map.put("callBackUrl", CHAOZHI_MINI_APP_SERVER + "/ajax/chaozhi/payResult");
		map.put("spbillCreateIp", getRequest().getRemoteAddr());
		map.put("appId", Keys.APP_ID);
		map.put("mchId", Keys.MCH_ID);
		map.put("apiKey", Keys.API_KEY);
		map.put("orderNumber", orderNumber);
		map.put("mobile", mobile);
		map.put("payType", "MWEB");
		boolean success = chaozhiSvc.createOrder(map);

		SortedMap<Object, Object> params = new TreeMap<Object, Object>();

		try {

			JSONObject json = new JSONObject();
			String url = payService.pay(map);
			json.put("mwebUrl", url+"&redirect_url="+URLEncoder.encode(CHAOZHI_MINI_APP_SERVER+"/ajax/chaozhi/checkOrder?orderNumber="+orderNumber, "UTF-8"));
			json.put("orderNumber", orderNumber);
			Result.ok(json, this);

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void payPage(){
		
		String mobile = getPara("mobile");
		setAttr("mobile", mobile);
		setAttr("pay_status", -1);
		renderTemplate("/chaozhiduoduo/chaozhiPay.html");
		
	}
	
	public void checkOrder(){
		String orderNumber = getPara("orderNumber");
		
		ChaozhiOrder chaozhiOrder = ChaozhiOrder.dao.findFirst("SELECT * FROM chaozhi_order WHERE order_number = ?",orderNumber);
		
		if(chaozhiOrder!=null&&chaozhiOrder.getStatus()>0){
			setAttr("pay_status", 1);
		}else{
			setAttr("pay_status", 0);
		}
		
		renderTemplate("/chaozhiduoduo/chaozhiPay.html");
		
	}
	
	public void studyFriend(){
		
		Integer memberId = getParaToInt("memberId",0);
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageNumber",10);
		
		
		if(xx.isEmpty(memberId)){
			Result.error(203, this);
			return;
		}
		
		List<ChaozhiStudyFriend> chaozhiStudyFriends = ChaozhiStudyFriend.dao.find("select * from chaozhi_study_friend where member1 = ? or member2 = ?"
												,memberId,memberId); 
		//?????????memberId??????
		List<Integer> friendIds = new ArrayList<Integer>();
		
		for(ChaozhiStudyFriend chaozhiStudyFriend : chaozhiStudyFriends){
			
			Integer member1 = chaozhiStudyFriend.getMember1();
			Integer member2 = chaozhiStudyFriend.getMember2();
			
			if(member1.equals(memberId)){
				friendIds.add(member2);
			}else{
				friendIds.add(member1);
			}
			
		}
		
		String condition = "";
		
		if(friendIds.size()>0){
			condition = friendIds.get(0).toString(); 
			for(int i=1;i<friendIds.size();i++){
				condition += "," + friendIds.get(i);
			}
		}else{
			Result.error(20501, "????????????",this);
			return;
		}
		
		Page<Member> members = Member.dao.paginate(pageNumber, pageSize, "select * ","from member where id in ("+condition+")");
		Result.ok(members,this);
		
	}
	
}
