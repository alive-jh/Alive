package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "bookcollection")
public class BookCollection {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	
	@Column(name = "memberid",length = 32)
	private Integer memberId;
	
	
	@Column(name = "cateid")
	private String cateId;


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
	
	
	
	
}
