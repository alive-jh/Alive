package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "useraddress")
public class UserAddress {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;	//会员id
	@Column(name = "area")
	private String area;		//区域-省份-城市
	@Column(name = "status")
	private Integer status = new Integer(1);		//是否默认地址,0是,1否
	@Column(name = "userName")
	private String userName;	//收货人
	@Column(name = "mobile")
	private String mobile;		//手机号码
	@Column(name = "address")
	private String address;		//详细地址
	@Column(name = "type")
	private Integer type = new Integer(0);		//0正常,1删除
	@Column(name = "lng")
	private String lng;			//经度
	@Column(name = "lat")
	private String lat;			//纬度
	
	
	
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

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
	


}
