package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mallorderservice")
public class MallOrderService {

	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "orderid")
	private String orderId;				//订单id
	
	@Column(name = "memberid")
	private Integer memberId;			//会员id
	
	@Column(name = "status")
	private Integer status = new Integer(0);//状态0待处理,1已处理
	
	@Column(name = "type")
	private Integer type = new Integer(0);//0退款,1退货退款
	
	@Column(name = "createdate")
	private Date createDate;
	
	@Column(name = "ordernumber")
	private String orderNumber;		//订单编号
	
	@Column(name = "remarks")
	private String remarks;			//说明
	
	@Column(name = "payment")
	private Double payment;			//退款金额
	
	@Column(name = "express")
	private String express;			//快递公司
	
	@Column(name = "expressnumber")
	private String expressNumber;	//快递单号
	
	@Column(name = "servicetype")
	private String serviceType;		//退款原因
	
	@Column(name = "operatorid")
	private Integer operatorId = new Integer(0);		//操作人

	
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
}
