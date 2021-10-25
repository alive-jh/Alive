package com.wechat.entity.dto;

import java.sql.Timestamp;

public class MemberCardDto {
	
	/**
	 * 店铺ID
	 */
	private Integer id;
	/**
	 * 店铺ID
	 */
	private String account;
	/**
	 * 店铺名称
	 */
	private String name;
	/**
	 * 持卡会员昵称
	 */
	private String nickname;
	/**
	 * 会员卡编码
	 */
	private String card;
	/**
	 * 会员卡状态
	 */
	private Integer status;
	/**
	 * 会员卡价格
	 */
	private Integer price;
	/**
	 * 会员卡添加时间
	 */
	private Timestamp createdate;
	
	/**
	 * 会员头像地址
	 */
	private String headimgurl;

	
	/**
	 * 会员ID
	 */
	private Integer memberId;
	
	/**
	 * 会员手机号码
	 */
	private String memberPhone;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	
	
	

}
