package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "memberbookinfo")
public class MemberBookInfo {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	
	
	@Column(name = "memberid")
	private Integer memberId;	//会员ID
	
	
	@Column(name = "cateid")
	private String cateId;		//书籍Id
	
	@Column(name = "bookexpressid")
	private Integer bookExpressId;	//还书表ID
	
	@Column(name = "status")
	private Integer status = new Integer(0);		//还书状态:0未确认,1已确认

	@Column(name = "barcode")
	private String barCode;

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

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public Integer getBookExpressId() {
		return bookExpressId;
	}

	public void setBookExpressId(Integer bookExpressId) {
		this.bookExpressId = bookExpressId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	
	
	
	
	
}


