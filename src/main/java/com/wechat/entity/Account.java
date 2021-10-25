package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {
	
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "account")
	private String account ;						//账号
	
	@Column(name = "name")
	private String name;							//名称
	
	@Column(name = "appid")
	private String appId;							//appid
	
	@Column(name = "appsecret")
	private String appSecret;						//appsecret
	
	@Column(name = "createDate")
	private Date createDate;						//创建时间
	
	@Column(name = "updateDate")
	private Date updateDate;						//accesstoken更新时间
	
	@Column(name = "accessToken")
	private String accessToken;						//accesstoken
	
	@Column(name = "keywordId")
	private Integer keywordId = new Integer(0);	//关注推送关键词

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	
	
	
	
	
	
	

}
