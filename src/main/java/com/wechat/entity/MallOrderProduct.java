package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "mallorderproduct")
public class MallOrderProduct {

	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;
	@Column(name = "productid")
	private Integer productId;						//产品ID
	@Column(name = "count")
	private Integer count = new Integer(0);			//产品数量
	@Column(name = "orderid")
	private Integer orderId = new Integer(0);		//订单Id
	@Column(name = "createdate")
	private Date createDate;						//创建日期
	@Column(name = "price")
	private Double price = new Double(0);			//产品价格
	@Column(name = "specifications")
	private String specifications;					//产品规格
	
	@Column(name = "productname")
	private String productName;						//产品名称
	
	@Column(name = "productimg")
	private String productImg;						//产品图片
	
	@Column(name = "commentstatus")
	private Integer commentStatus = new Integer(0);		//是否被评论

	
	
	public Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	 
	 
	 
	
	
}
