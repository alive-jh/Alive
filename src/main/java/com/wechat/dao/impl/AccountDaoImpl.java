package com.wechat.dao.impl;

import com.wechat.dao.AccountDao;
import com.wechat.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao{

	
	@Override
	public Account getAccount(String id) {
		
		return (Account)this.getEntity(Account.class, new Integer(id));
	}

	
	@Override
	public void updateAccountKeywordId(String id, String keywordId) {
		
		String sql = " update account set keywordid = "+keywordId+" where id = "+id;
		this.executeUpdateSQL(sql);
	}
	
	@Override
	public String getAccessToken(String appid,String appSecret)
	{
		String sql = "select accesstoken from account where 1=1 and appid ='"+appid + "' and appsecret='"+appSecret+"'";
		List list = this.executeSQL(sql);
		String accessToken = "";
		if(list.size()>0)
		{
			accessToken = list.get(0).toString();
		}
		return accessToken;
	}
	
	@Override
	public String getTicket(String appid,String appSecret)
	{
		String sql = "select ticket from account where 1=1 and appid ='"+appid + "' and appsecret='"+appSecret+"'";
		List list = this.executeSQL(sql);
		String ticket = "";
		if(list.size()>0)
		{
			ticket = list.get(0).toString();
		}
		return ticket;
	}
	
	@Override
	public void updateAccountAccessToken(String appId, String appSecret,String accessToken,String ticket)
	{
		this.executeUpdateSQL(" update account set updatedate = sysdate(),accesstoken ='"+accessToken+"',ticket='"+ticket+"'  where appid = '"+appId+"' and appsecret='"+appSecret+"'");
	}


	@Override
	public int updateSubscribeStatus(String openid,int subscribeStatus) {
		
		this.executeUpdateSQL("update wechat_user SET b_subscribe = "+subscribeStatus+" WHERE openid = '"+openid+"'");

		return 1;
	}


	@Override
	public int createDefualtWechatUser(String openid) {
		// TODO Auto-generated method stub
		
		List result = this.executeSQL("select * from wechat_user where openid = '"+openid+"'");
		
		if (result == null || result.size() == 0 ){
			this.executeUpdateSQL("INSERT into wechat_user SET openid = '"+openid+"'");
		}
		
		return 1;
	}
	
	

}
