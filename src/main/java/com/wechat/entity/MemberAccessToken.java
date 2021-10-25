package com.wechat.entity;

public class MemberAccessToken {
	
	private String accessToken;	 	//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	private String expiresIn;	 	//access_token接口调用凭证超时时间，单位（秒）
	private String refreshToken;	//用户刷新access_token
	private String openid;			//用户Id
	private String scope;			//用户授权的作用域，使用逗号（,）分隔
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
	
}
