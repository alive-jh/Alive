package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "memberpayment")
public class MemberPayment {

	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;				//会员编号
	
	@Column(name = "ordernumber")
	private String orderNumber;				//订单编号
	
	@Column(name = "createdate")
	private Date createDate;				//充值日期
	
	@Column(name = "payment")
	private Double payment;					//充值金额
	
	@Column(name = "status")
	private Integer status =new Integer(0);	//状态0已付款,1未付款
	
	private Integer type;					//充值类型:0 IOS,1 安卓,微信
	
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
