package com.wechat.service.impl;

import com.wechat.dao.AccountDao;
import com.wechat.entity.Account;
import com.wechat.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Resource
	private AccountDao accountDao;
	
	
	public AccountDao getAccountDao() {
		return accountDao;
	}


	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}


	@Override
	public Account getAccount(String id) {
		
		return this.accountDao.getAccount(id);
	}

	
	@Override
	public void updateAccountKeywordId(String id, String keywordId) {
		
		this.accountDao.updateAccountKeywordId(id, keywordId);
	}


	
	@Override
	public String getAccessToken(String appid, String appSecret) {
		
		return this.accountDao.getAccessToken(appid, appSecret);
	}


	
	@Override
	public void updateAccountAccessToken(String appId, String appSecret,
			String accessToken,String ticket) {
		
		this.accountDao.updateAccountAccessToken(appId, appSecret, accessToken,ticket);
	}


	
	@Override
	public String getTicket(String appid, String appSecret) {
		
		
		return this.accountDao.getTicket(appid, appSecret);
	}


	@Override
	public int updateSubscribeStatus(String openid,int subscribeStatus) {
		// TODO Auto-generated method stub
		return this.accountDao.updateSubscribeStatus(openid,subscribeStatus);
	}


	@Override
	public int createDefualtWechatUser(String openid) {
		// TODO Auto-generated method stub
		return this.accountDao.createDefualtWechatUser(openid);
	}

}
