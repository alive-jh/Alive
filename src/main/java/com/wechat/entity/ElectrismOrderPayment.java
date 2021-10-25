package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "electrismorderpayment")
public class ElectrismOrderPayment {

	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "orderid")
	private Integer orderId;				//订单id
	 
	@Column(name = "payment")
	private Double payment;					//订单金额
	 
	@Column(name = "status")
	private Integer status = new Integer(0);//状态0未提现,1已提现
	
	@Column(name = "electrismid")
	private Integer electrismId;			//电工id
	 
	@Column(name = "ordernumber")			//订单编号
	private String orderNumber;			
	
	@Column(name = "serviceitem")
	private Integer serviceItem;			//服务项目
	@Column(name = "createdate")			
	private Date createDate;				//创建日期

	
	
	
	
	public Integer getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(Integer serviceItem) {
		this.serviceItem = serviceItem;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Integer getElectrismId() {
		return electrismId;
	}

	public void setElectrismId(Integer electrismId) {
		this.electrismId = electrismId;
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

	
	
	 
	
}
