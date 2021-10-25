package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bookorderinfo")
public class BookOrderInfo {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	
	@Column(name = "orderid")
	private Integer orderId;	//订单ID
	
	
	@Column(name = "barcode")
	private String barCode;	//书籍编码
	
	@Column(name = "status")
	private Integer status = new Integer(0);		///*书籍状态:0借书中,1已还书*/
	
	
	@Column(name = "createdate")
	private Date createDate;	//创建日期
	
	@Column(name = "cateid")
	private String cateId;		//书籍ID

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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	
	
	
	
	
	
	
}
