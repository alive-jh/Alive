package com.wechat.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MallOrder {

	
	
   
	private Integer id;
	 
	private Integer memberId;						//会员ID

	private Integer status = new Integer(1);		//订单状态,1已付款,2已发货,3已确认收货.4已评价,5退款中,6已退款,7未支付
	 
	private Date createDate;						//创建日期
	
	private Integer addressId;						//收货人
	
	private Double totalPrice = new Double(0);		//订单总金额
	
	private Double freight  = new Double(0);		//运费
	 
	private String remarks;							//备注
	
	private String orderNumber;					    //订单编号
	
	private List<MallOrderProduct> productList = new ArrayList();						//产品集合
	
	private Integer productCount;					//产品数
		
	private String express;							//快递信息
	
	private String expressNumber;					//快递单号
	
	private Double couponsMoney = new Double(0); //优惠券金额
	
	private Integer couponsId = new Integer(0);		//优惠卷id
	
	private Integer operatorId = new Integer(0);	//操作人
	
	private Integer statusBlank = new Integer(1);	//操作状态blank
	
	
	private Integer integralStatus = new Integer(0);	//是否使用积分抵扣,0不使用,1使用
	
	private Integer integral = new Integer(0);			// 抵扣积分
	
	
	private Integer shopId = 0;		//店铺ID
	
	private String shoppingCartId;//购物车商品ID
	
	
	private String productListStr = "";
	
	
	
	
	
	public String getProductListStr() {
		return productListStr;
	}

	public void setProductListStr(String productListStr) {
		this.productListStr = productListStr;
	}

	public Integer getIntegralStatus() {
		return integralStatus;
	}

	public void setIntegralStatus(Integer integralStatus) {
		this.integralStatus = integralStatus;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(String shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getStatusBlank() {
		return statusBlank;
	}

	public void setStatusBlank(Integer statusBlank) {
		this.statusBlank = statusBlank;
	}

	public Integer getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}

	public Double getCouponsMoney() {
		return couponsMoney;
	}

	public void setCouponsMoney(Double couponsMoney) {
		this.couponsMoney = couponsMoney;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public List<MallOrderProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<MallOrderProduct> productList) {
		this.productList = productList;
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



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	
	
	
	
	
	
	
	
	
	

	 
	 
	 
	
	
}
