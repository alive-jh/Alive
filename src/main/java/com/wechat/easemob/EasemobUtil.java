package com.wechat.easemob;

import com.google.gson.JsonObject;
import com.jfinal.kit.HttpKit;
import com.wechat.entity.EasemobGroupChat;
import com.wechat.service.RedisService;
import com.wechat.util.BeanUtil;
import com.wechat.util.RedisKeys;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EasemobUtil extends HttpServlet {

	private static RedisService redisService;

	public static JSONObject httpRequest(String url, String data, String token) throws Exception {
		try {
			JSONObject jsonObject = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			// 目标地址
			HttpPost httppost = new HttpPost(url);
			// 构造最简单的字符串数据
			String tempData = data;
			StringEntity reqEntity = new StringEntity(tempData, HTTP.UTF_8);
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
			// //System.out.println("resultStr = "+resultStr);
			jsonObject = JSONObject.fromObject(resultStr);
			return jsonObject;
		} catch (Exception e) {
			// System.out.println("ex = " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建token,并且缓存到redis里,每3个小时更新一次
	 * 
	 * @return
	 * @throws Exception
	 */
	public static EasemobToKen createAccessToKen() throws Exception {

		redisService = (RedisService) BeanUtil.getBeanByName("redisService");
		EasemobToKen accessToken = new EasemobToKen();
		if (redisService.exists("easemobToken")) {
			accessToken = (EasemobToKen) redisService.getObject("easemobToken", EasemobToKen.class);
		} else {
			JSONObject jobj = new JSONObject();

			String url = EasemobKey.URR_TOKEN;// 请求URL
			JSONObject data = new JSONObject();// URL参数
			data.put("client_id", EasemobKey.APP_CLIENT_ID);
			data.put("client_secret", EasemobKey.APP_CLIENT_SECRET);
			data.put("grant_type", "client_credentials");

			JSONObject jsonObject = EasemobUtil.httpRequest(url, data.toString(), ""); // 接受返回的json格式数据

			String s = jsonObject.toString();
			if (s.lastIndexOf("errcode") == -1) {

				accessToken.setToken(jsonObject.getString("access_token"));// 获取json属性值
				accessToken.setExpire(jsonObject.getLong("expires_in"));
				redisService.setObject("easemobToken", accessToken, RedisKeys.ADMIN_TIME * 3);

			} else {
				accessToken.setToken("");
			}
		}

		return accessToken;// 返回结果
	}

	/**
	 * 
	 * @param users
	 *            用户账号数组
	 * @param action
	 *            推送内容epal_play
	 * @throws Exception
	 */
	public void sendMessageSign(String[] users, String classScript, int sign) {
		// TODO Auto-generated method stub
		String temp = classScript.replace("epal_play:", "");

		JSONObject temp1 = JSONObject.fromObject(temp);
		temp1.put("pushId", sign);
		String action = "epal_play:" + temp1.toString();
		// //System.out.println(action);
		try {
			this.sendMessage(users, action);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param users
	 *            用户账号数组
	 * @param action
	 *            推送内容epal_play
	 * @throws Exception
	 */
	public void sendMessage(String[] users, String action) throws Exception {

		JSONObject jobj = new JSONObject();

		String url = EasemobKey.URL_MESSAGES;// 请求URL

		JSONObject data = new JSONObject();// URL参数
		JSONObject msg = new JSONObject();// URL参数

		data.put("target", users);
		data.put("from", "admin");

		msg.put("action", action);
		msg.put("type", "cmd");
		data.put("msg", msg);
		data.put("target_type", "users");

		JSONObject ext = new JSONObject();// URL参数
		ext.put("sendtime", System.currentTimeMillis() + "");
		data.put("ext", ext);

		// System.out.println("data = " + data.toString());

		HttpPost post = new HttpPost(EasemobKey.URL_MESSAGES);

		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		post.addHeader("Content-Type", "application/json");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(post);
		String[][] field = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String json = EntityUtils.toString(entity, "UTF-8");
			if (field != null && field.length > 0) {
				for (String[] temp : field) {
					json = json.replace(temp[0], temp[1]);
				}
			}
			// System.out.println("result =====" + json);// TODO 此行可以删除

		}

	}

	public void sendMessage(String account, String[] users, String action) throws Exception {

		JSONObject jobj = new JSONObject();

		String url = EasemobKey.URL_MESSAGES;// 请求URL

		JSONObject data = new JSONObject();// URL参数
		JSONObject msg = new JSONObject();// URL参数

		data.put("target", users);
		data.put("from", account);

		msg.put("action", action);
		msg.put("type", "cmd");
		data.put("msg", msg);
		data.put("target_type", "users");

		JSONObject ext = new JSONObject();// URL参数
		ext.put("sendtime", System.currentTimeMillis() + "");
		data.put("ext", ext);

		// System.out.println("data = " + data.toString());

		HttpPost post = new HttpPost(EasemobKey.URL_MESSAGES);

		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		post.addHeader("Content-Type", "application/json");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(post);
		String[][] field = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String json = EntityUtils.toString(entity, "UTF-8");
			if (field != null && field.length > 0) {
				for (String[] temp : field) {
					json = json.replace(temp[0], temp[1]);
				}
			}
			// System.out.println("result =====" + json);// TODO 此行可以删除

		}

	}

	public void register(String account, String pwd) throws Exception {

		JSONObject jobj = new JSONObject();

		String url = EasemobKey.URL_USER;// 请求URL

		JSONObject data = new JSONObject();// URL参数

		data.put("username", account);
		data.put("password", pwd);

		HttpPost post = new HttpPost(EasemobKey.URL_MESSAGES);

		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		post.addHeader("Content-Type", "application/json");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(post);
		String[][] field = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String json = EntityUtils.toString(entity, "UTF-8");
			if (field != null && field.length > 0) {
				for (String[] temp : field) {
					json = json.replace(temp[0], temp[1]);
				}
			}
			// System.out.println("result =====" + json);// TODO 此行可以删除

		}

	}

	public static void sendTextMessage(String contacts, String message, JSONObject ext) throws Exception {
		JSONObject data = new JSONObject();// URL参数
		JSONObject msg = new JSONObject();// URL参数
		String userName = "[\"" + contacts + "\"]";
		data.put("target", userName);
		data.put("from", "清园教育");
		msg.put("msg", message);
		msg.put("type", "txt");
		data.put("ext", ext);
		data.put("msg", msg);
		data.put("target_type", "users");

		HttpPost post = new HttpPost(EasemobKey.URL_MESSAGES);

		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		post.addHeader("Content-Type", "application/json");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response;
		response = client.execute(post);
		String[][] field = null;
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String json = EntityUtils.toString(entity, "UTF-8");
			if (field != null && field.length > 0) {
				for (String[] temp : field) {
					json = json.replace(temp[0], temp[1]);
				}
			}
		}

	}

	public static void sendTextMessage(String contacts, String message) throws Exception {
		JSONObject data = new JSONObject();// URL参数
		JSONObject msg = new JSONObject();// URL参数
		String userName = "[\"" + contacts + "\"]";
		data.put("target", userName);
		data.put("from", "清园教育");
		msg.put("msg", message);
		msg.put("type", "txt");
		data.put("msg", msg);
		data.put("target_type", "users");

		HttpPost post = new HttpPost(EasemobKey.URL_MESSAGES);

		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		post.addHeader("Content-Type", "application/json");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response;
		response = client.execute(post);
		String[][] field = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String json = EntityUtils.toString(entity, "UTF-8");
			if (field != null && field.length > 0) {
				for (String[] temp : field) {
					json = json.replace(temp[0], temp[1]);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		EasemobUtil easemobUtil = new EasemobUtil();
		easemobUtil.register("", "");
		// EasemobToKen accessToken = easemobUtil.createAccessToKen();
		//
		// //System.out.println(accessToken.getToken());
		// //System.out.println(accessToken.getExpire());

	}

	/*
	 * 发送透传消息
	 * 
	 */

	public static void sendPenetrate(String[] users, String action) throws Exception {

		JSONObject body = new JSONObject();
		body.put("target_type", "users");
		body.put("target", users);
		JSONObject msg = new JSONObject();
		msg.put("type", "cmd");
		msg.put("action", action);
		body.put("msg", msg);
		body.put("from", "admin");

		HttpPost post = new HttpPost(EasemobKey.URL_MESSAGES);

		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		post.setEntity(new StringEntity(body.toString(), "UTF-8"));

		post.addHeader("Content-Type", "application/json");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response;
		response = client.execute(post);
		String[][] field = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String json = EntityUtils.toString(entity, "UTF-8");
			if (field != null && field.length > 0) {
				for (String[] temp : field) {
					json = json.replace(temp[0], temp[1]);
				}
			}
		}

	}

	public static String[] getGroupChatMembers(String groupId) throws Exception {

		String[] members = null;

		HttpGet httpGet = new HttpGet(EasemobKey.URL_GROUP + "/" + groupId + "/users?pagenum=1&pagesize=100000");
		httpGet.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();

		JSONObject result = new JSONObject();

		if (entity != null) {
			result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			System.out.println(result);
		}

		if (result.has("data")) {
			
			JSONArray jsonArray = result.getJSONArray("data");

			members = new String[jsonArray.size()];

			for (int i = 0; i < jsonArray.size(); i++) {

				if (jsonArray.getJSONObject(i).has("member")) {
					members[i] = jsonArray.getJSONObject(i).getString("member");
				}else{
					members[i] = jsonArray.getJSONObject(i).getString("owner");
				}

			}

		}

		return members;

	}

	public static String createGroupChat(EasemobGroupChat easemobGroupChat) throws Exception {

		String groupId = null;

		JSONObject data = new JSONObject();
		data.put("groupname", easemobGroupChat.getGroupName());
		data.put("desc", easemobGroupChat.getDesc());
		data.put("public", easemobGroupChat.isApublic());
		data.put("maxusers", easemobGroupChat.getMaxusers());
		data.put("members_only", easemobGroupChat.isMembersOnly());
		data.put("allowinvites", easemobGroupChat.isAllowinvites());
		data.put("owner", easemobGroupChat.getOwner());

		String[] members = easemobGroupChat.getMembers();
		if (members != null && members.length > 0) {
			data.put("members", easemobGroupChat.getMembers());
		}

		HttpPost post = new HttpPost(EasemobKey.URL_GROUP);
		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());
		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(post);
		HttpEntity entity = response.getEntity();

		JSONObject result = new JSONObject();

		if (entity != null) {
			result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
		}

		if (result.has("data")) {
			groupId = result.getJSONObject("data").getString("groupid");
		}

		return groupId;
	}

	public static void addMember2Group(String groupId, String[] members) throws Exception {
		
		JSONObject data = new JSONObject();
		data.put("usernames", members);
		HttpPost post = new HttpPost(EasemobKey.URL_GROUP+"/"+groupId+"/users");
		post.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());
		post.setEntity(new StringEntity(data.toString(), "UTF-8"));

		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(post);
		HttpEntity entity = response.getEntity();
		JSONObject result = new JSONObject();

		if (entity != null) {
			result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			System.out.println(result.toString());
		}
		
		if(result.has("error")){
			throw new RuntimeException("添加群成员失败");
		}
		
		
	}

	public static List<EasemobGroupChat> getGroupChat(String group) throws Exception {
		
		List<EasemobGroupChat> easemobGroupChats = new ArrayList<EasemobGroupChat>();
		
		HttpGet get = new HttpGet(EasemobKey.URL_GROUP+"/"+group);
		get.addHeader("Authorization", "Bearer " + EasemobUtil.createAccessToKen().getToken());
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null;
		response = client.execute(get);
		HttpEntity entity = response.getEntity();

		JSONObject result = new JSONObject();

		if (entity != null) {
			result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			System.out.println(result);
		}
		
		if(result.has("data")){
			JSONArray jsonArray = result.getJSONArray("data");
			easemobGroupChats = new ArrayList<EasemobGroupChat>(jsonArray.size());
			
			for(int i=0;i<jsonArray.size();i++){
				JSONObject groups = jsonArray.getJSONObject(i);
				EasemobGroupChat easemobGroupChat = new EasemobGroupChat();
				easemobGroupChat.setId(groups.getString("id"));
				easemobGroupChat.setGroupName(groups.getString("name"));
				easemobGroupChat.setDesc(groups.getString("description"));
				easemobGroupChat.setApublic(groups.getBoolean("public"));
				easemobGroupChat.setMembersOnly(groups.getBoolean("membersonly"));
				easemobGroupChat.setAllowinvites(groups.getBoolean("allowinvites"));
				easemobGroupChat.setMaxusers(groups.getInt("maxusers"));
				easemobGroupChat.setAffiliationsCount(groups.getInt("affiliations_count"));
				
				JSONArray affiliations = groups.getJSONArray("affiliations");
				String[] members =  new String[affiliations.size()];
				
				for(int j=0;j<affiliations.size();j++){

					Iterator<String> sIterator = affiliations.getJSONObject(j).keys();
					members[j] = affiliations.getJSONObject(j).getString(sIterator.next());
					
				}
				
				easemobGroupChat.setMembers(members);
				easemobGroupChats.add(easemobGroupChat);
			}
			
		}
		
		
		return easemobGroupChats;
	}

}
