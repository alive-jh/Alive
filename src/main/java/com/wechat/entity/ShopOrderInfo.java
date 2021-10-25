package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "shoporderinfo")
public class ShopOrderInfo {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "orderid")
	private Integer orderid;						//订单ID
	
	
	@Column(name = "status")
	private Integer status = new Integer(0);		//状态0出库,1入库
	
	@Column(name = "barcode")
	private String barcode;		//书籍编码
	
	
	@Column(name = "cateid")
	private String cateId;		//图书编码
	
	
	@Column(name = "createdate")
	private Date createDate;	//创建日期


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getOrderid() {
		return orderid;
	}


	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getBarcode() {
		return barcode;
	}


	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public String getCateId() {
		return cateId;
	}


	public void setCateId(String cateId) {
		this.cateId = cateId;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	
	
	
}
