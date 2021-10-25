package com.wechat.service;

import com.wechat.entity.Account;

public interface AccountService {

	
	/**
	 * 查询公众账号
	 * @param id
	 * @return
	 */
	 Account getAccount(String id);
	 
	 
	 /**
	  * 更新公众账号关注推送关键词
	  * @param id
	  * @param keywordId
	  */
	 void updateAccountKeywordId(String id, String keywordId);
	 
	 /**
	  * 后去公众账号accessToken
	  * @param appid
	  * @param appSecret
	  * @return
	  */
	 String getAccessToken(String appid, String appSecret);
	 
	 
	 /**
	  * 更新公众账号accessToken
	  * @param appId
	  * @param appSecret
	  * @param accessToken
	  */
	 void updateAccountAccessToken(String appId, String appSecret, String accessToken, String ticket);
	 
	 
	 /**
	  * 查询公众账号Ticket
	  * @param appid
	  * @param appSecret
	  * @return
	  */
	 public String getTicket(String appid, String appSecret);
	 
	 public int updateSubscribeStatus(String openid,int subscribeStatus);
	 
	 public int createDefualtWechatUser(String openid);
	
}
