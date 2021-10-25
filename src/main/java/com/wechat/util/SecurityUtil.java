package com.wechat.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityUtil {
	public static String DES = "AES"; // optional value AES/DES/DESede

	public static String CIPHER_ALGORITHM = "AES"; // optional value
													// AES/DES/DESede

	public static Key getSecretKey(String key) throws Exception {
		SecretKey securekey = null;
		if (key == null) {
			key = "";
		}
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(key.getBytes());
		KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
		keyGenerator.init(secureRandom);
		securekey = keyGenerator.generateKey();
		return securekey;
	}

	public static String encrypt(String data, String key) throws Exception {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		Key securekey = getSecretKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		byte[] bt = cipher.doFinal(data.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		// System.out.println("加密后"+strs);
		return strs;
	}

	public static String detrypt(String message, String key) throws Exception {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		Key securekey = getSecretKey(key);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		byte[] res = new BASE64Decoder().decodeBuffer(message);
		res = cipher.doFinal(res);
		return new String(res);
	}


	public static JSONObject httpRequest(String url, String params) throws Exception {
		try {
			JSONObject jsonObject = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			// 目标地址
			HttpPost httppost = new HttpPost(url);
			// 构造最简单的字符串数据
			StringEntity reqEntity = new StringEntity(params, HTTP.UTF_8);
			String resultStr = "";
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			// 设置请求的数据
			httppost.setEntity(reqEntity);
			// 执行
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			// 显示结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				resultStr = line;
			}
			// System.out.println("resultStr = "+resultStr);
			jsonObject = JSONObject.fromObject(resultStr);
			return jsonObject;
		} catch (Exception e) {
			// System.out.println("ex = "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		SecurityUtil securityUtil = new SecurityUtil();
		securityUtil.appLogin();
	}

	@Test
	public void main() throws Exception {
		SecurityUtil securityUtil = new SecurityUtil();
		securityUtil.appLogin();
	}

	public void miniappLogin() throws Exception {

		String server = "http://office.fandoutech.com:8808/wechat";

		String account = "westson";
		String password = "25D55AD283AA400AF464C76D713C07AD";
		String mini_openid = "oV7vq4tRLviEEHl_vcGq3yMx5pY4";

		JSONObject json = httpRequest(server + "/v2/miniapp/userCheck",
				"account=" + account + "&password=" + password + "&openid=" + mini_openid);

		JSONObject json2 = httpRequest(server + "/v2/user/getUserInfo",
				"access_token=" + json.getJSONObject("data").getString("access_token"));

		System.out.println(json2.toString());

	}

	public void appLogin() throws Exception {

		String server = "http://office.fandoutech.com:8808/wechat";
		//String server = "http://wechat.fandoutech.com.cn/wechat";
		//String server = "http://test.fandoutech.com.cn/wechat";

		String account = "18902483532";
		String password = "E35CF7B66449DF565F93C607D5A81D09";
		
		JSONObject json = httpRequest(server + "/v2/user/login", "account=" + account + "&password=" + password
				+ "&type=device&_sign=19BBAC7BAC29BD919DAA89067A9CA7A4&_time=1525842696825");

		/*JSONObject json2 = httpRequest(server + "/v2/bulletinQuestion/userQuestionList",
				"token=" + URLEncoder.encode(json.getString("access_token"), "UTF-8") + "&memberId=272&studentId=1");*/
		System.out.println(URLEncoder.encode(json.getString("access_token"), "UTF-8"));
		JSONObject json2 = httpRequest(server + "/v2/user/getUserInfo",
				"access_token=" + URLEncoder.encode(json.getString("access_token"), "UTF-8")+"&_sign=9738CFA40FA0C34C9A8326DA51974DD9&_time=1525842696825");

		System.out.println(json2.toString());
	}

}