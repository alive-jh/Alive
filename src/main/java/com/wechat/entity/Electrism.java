package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "electrism")
public class Electrism {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;
	@Column(name = "memberid")
	private Integer memberId;			//微信会员ID
	@Column(name = "name")
	private String name;				//真实姓名
	@Column(name = "content")
	private String content;				//个人介绍
	@Column(name = "headimg")
	private String headImg;				//头像
	@Column(name = "hobbies")
	private String hobbies;				//兴趣爱好
	@Column(name = "area")
	private String area;				//籍贯
	@Column(name = "price")
	private Double price = new Double(0);//上门均价
	@Column(name = "createdate")	
	private Date createDate;			//创建时间
	@Column(name = "status")
	private Integer status = new Integer(0);				//状态:0正常,1锁定
	@Column(name = "mobile")
	private String mobile;				//微信号
	
	@Column(name = "didstrict")
	private String didstrict;			//服务商圈
	
	@Column(name = "item")
	private String item;				//服务项目	
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "lng")
	private String lng;					//经度
	
	@Column(name = "lat")
	private String lat;					//纬度
	
	@Column(name = "card")
	private String card;				//银行卡
	
	@Column(name = "bank")
	private String bank;				//开户行
	
	@Column(name = "nickname")
	private String nickName;			//电工名称
	
	@Column(name = "idnumber")
	private String idNumber;			//身份证号码
	
	@Column(name = "wechat")
	private String wechat;				//微信号
	
	@Column(name = "paymentstatus")
	private Integer paymentStatus = new Integer(0);		//是否提现,0不体现,1体现
	
	
	
	
	
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDidstrict() {
		return didstrict;
	}
	public void setDidstrict(String didstrict) {
		this.didstrict = didstrict;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	

}
