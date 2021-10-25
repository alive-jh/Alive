package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bookorder")
public class BookOrder {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;		//会员id
	
	@Column(name = "ordernumber")
	private String orderNumber;		//订单编号
	
	@Column(name = "remarks")
	private String remarks;			//备注
	
	@Column(name = "totalprice")
	private Double totalPrice = new Double(0);		//订单总价格
	
	@Column(name = "createdate")
	private Date createDate;		//创建日期
	
	@Column(name = "status")
	private Integer status = new Integer(1);			//订单状态:1已付款,待发货,2已发货,3退款中,4已退款
	
	@Column(name = "integral")
	private Integer integral = new Integer(0);		//抵扣积分or豆豆币
	
	@Column(name = "operatorid")
	private Integer operatorId;		//操作人id
	
	@Column(name = "addressId")
	private Integer addressId;		//售后地址id
	
	@Column(name = "deposit")
	private Double deposit = new Double(0);		//押金
	
	@Column(name = "freight")
	private Integer freight = new Integer(0);		//运费
	
		
	@Column(name = "bookprice")
	private Integer bookPrice = new Integer(0);		//借书费
	
	@Column(name = "express")		//快递类型
	private String express;
	
	@Column(name = "expressnumber")//快递单号
	private String expressNumber;
	
	
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
	
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Integer getFreight() {
		return freight;
	}
	public void setFreight(Integer freight) {
		this.freight = freight;
	}
	public Integer getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(Integer bookPrice) {
		this.bookPrice = bookPrice;
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
	
	
	
	
	
	
}
