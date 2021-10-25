package com.wechat.jmessage;

public class JMessage {
	
	
	
	private String account;	//极光账号

	private Long creadaDate;//操作时间
	
	private Integer type;	//操作类型:0绑定,1解绑,2推送音频,3推送课堂,4拍照
	
	private String epalId;	//机器人ID
	
	
	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getCreadaDate() {
		return creadaDate;
	}

	public void setCreadaDate(Long creadaDate) {
		this.creadaDate = creadaDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
	
	
	
	
	
	
	

}
