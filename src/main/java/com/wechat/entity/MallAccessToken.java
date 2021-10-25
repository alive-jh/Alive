package com.wechat.entity;

public class MallAccessToken {

	private String access_token;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	@Override
	public String toString() {
		return "MallAccessToken [access_token=" + access_token + "]";
	}
	
	
}
