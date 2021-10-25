package com.wechat.entity;

import java.util.Date;
import java.util.List;



public class BookOrderList {

	
	private Integer id;
	
	
	private Integer memberId;		//会员id
	

	private String orderNumber;		//订单编号
	
	

	private Double totalPrice = new Double(0);		//订单总价格
	

	private Date createDate;		//创建日期
	

	private Integer status = new Integer(0);			//订单状态:0已付款,待发货,1已发货,3退款中,4已退款
	

	private Integer integral = new Integer(0);		//抵扣积分or豆豆币
	
	private Integer count;
	

	private Double deposit = new Double(0);		//押金
	

	private Integer freight = new Integer(0);		//运费
	
		
	private Integer bookPrice = new Integer(0);		//借书费
	

	private String express;
	
	private String expressNumber;
	
	
	private List bookList;

	
	
	

	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
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


	public List getBookList() {
		return bookList;
	}


	public void setBookList(List bookList) {
		this.bookList = bookList;
	}
	
	
	
	
	
}
