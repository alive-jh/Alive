package com.wechat.entity;

public class UnifiedOrderModel {

	private String appid;
	private String mchId;
	private String apiKey;
	
	private String body;
	private String outTradeNo;
	private String totalFee;
	private String spbillCreateIp;
	private String notifyUrl;
	private String tradeType;

	private String openId;

	public UnifiedOrderModel(String appid, String mchId, String apiKey, String body, String outTradeNo, String totalFee,
                             String spbillCreateIp, String notifyUrl, String tradeType) {
		super();
		this.appid = appid;
		this.mchId = mchId;
		this.apiKey = apiKey;
		this.body = body;
		this.outTradeNo = outTradeNo;
		this.totalFee = totalFee;
		this.spbillCreateIp = spbillCreateIp;
		this.notifyUrl = notifyUrl;
		this.tradeType = tradeType;
	}
	
	public UnifiedOrderModel(String appid, String mchId, String apiKey, String body, String outTradeNo, String totalFee,
                             String spbillCreateIp, String notifyUrl, String tradeType, String openId) {
		super();
		this.appid = appid;
		this.mchId = mchId;
		this.apiKey = apiKey;
		this.body = body;
		this.outTradeNo = outTradeNo;
		this.totalFee = totalFee;
		this.spbillCreateIp = spbillCreateIp;
		this.notifyUrl = notifyUrl;
		this.tradeType = tradeType;
		this.openId = openId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "UnifiedOrderModel [appid=" + appid + ", mchId=" + mchId + ", apiKey=" + apiKey + ", body=" + body
				+ ", outTradeNo=" + outTradeNo + ", totalFee=" + totalFee + ", spbillCreateIp=" + spbillCreateIp
				+ ", notifyUrl=" + notifyUrl + ", tradeType=" + tradeType + ", openId=" + openId + "]";
	}

	
}
