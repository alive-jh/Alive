package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "bookshop")
public class BookShop implements Serializable{
	
	@Id
	@GeneratedValue(generator ="bookshopTableGenerator")       
    @GenericGenerator(name ="bookshopTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	/**
	 * 店铺ID
	 */
	private Integer id;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * logo图片路径
	 */
	private String logo;
	/**
	 * 店铺类型:0加盟店,1直营店
	 */
	private Integer type;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 店铺名称
	 */
	private String name;
	/**
	 * 店铺Email
	 */
	private String email;
	/**
	 * 店铺会员卡价格
	 */
	private Float memberCardPrice;
	/**
	 * 店铺电话
	 */
	private String telephone;
	/**
	 * 店铺联系地址
	 */
	private String contacts;
	/**
	 * 店铺状态
	 */
	private Integer status;
	/**
	 * 店铺创建日期
	 */
	private Timestamp createdate;
	
	

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}



	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getMemberCardPrice() {
		return memberCardPrice;
	}

	public void setMemberCardPrice(Float memberCardPrice) {
		this.memberCardPrice = memberCardPrice;
	}

	

	
}
