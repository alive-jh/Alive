package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "shoporder")
public class ShopOrder {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;		//会员ID
	
	@Column(name = "shopid")
	private Integer shopId = new Integer(0);			//店铺ID
	
	@Column(name = "status")
	private Integer status = new Integer(0);		//状态0出库,1入库
	
	@Column(name = "ordernumber")
	private String orderNumber;		//订单编号
	
	
	@Column(name = "mobile")
	private String mobile;		//手机号码
	
	
	@Column(name = "createdate")
	private Date createDate;	//创建日期


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getMemberId() {
		return memberId;
	}


	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}


	public Integer getShopId() {
		return shopId;
	}


	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	
	
}
