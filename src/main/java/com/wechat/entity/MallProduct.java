package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "mallproduct")
public class MallProduct {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;				//ID
	
	@Column(name = "name")
	private String name;			//商品名称
	
	@Column(name = "content")
	private String content;			//描述
	
	@Column(name = "createdate")
	private Date createDate;		//创建日期
	
	
	@Column(name = "logo1")		
	private String logo1;			//logo1
	
	@Column(name = "logo2")		
	private String logo2;			//logo2
	
	
	@Column(name = "logo3")		
	private String logo3;			//logo3
	
	@Column(name = "accountid")
	private Integer accountId = new Integer(0);		//公众账号ID
	@Column(name = "status")
	private Integer status = new Integer(0);		//是否上架:0上架,1下架
	@Column(name = "price")
	private String price;		//价格范围
	
	@Column(name = "mp3")
	private String mp3;
	
	@Column(name = "class_room_package")
	private Integer classRoomPackage;
	

//	@Column(name = "teacher")
//	private String teacher;

	
	public Integer getClassRoomPackage() {
		return classRoomPackage;
	}
	public void setClassRoomPackage(Integer classRoomPackage) {
		this.classRoomPackage = classRoomPackage;
	}
	@Column(name = "mp3type")
	private Integer mp3Type =  new Integer(0);//音频类型:中文= 1,英文= 2,中+英=3,粤语=4,中+粤=5,中英 = 8

	@Column(name = "cat_id")
	private Integer catId =  new Integer(0);//商品分类

	
	public String getMp3() {
		return mp3;
	}
	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}
	public Integer getMp3Type() {
		return mp3Type;
	}
	public void setMp3Type(Integer mp3Type) {
		this.mp3Type = mp3Type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getLogo1() {
		return logo1;
	}
	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}
	public String getLogo2() {
		return logo2;
	}
	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}
	public String getLogo3() {
		return logo3;
	}
	public void setLogo3(String logo3) {
		this.logo3 = logo3;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}

//	public String getTeacher() {
//		return teacher;
//	}
//	public void setTeacher(String teacher) {
//		this.teacher = teacher;
//	}
//	

	
	
	
	
	
	

}
