package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "malllabel")
public class MallLabel {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;

	@Column(name = "labelid")
	private Integer labelId;
	
	@Column(name = "ios")
	private Integer ios = new Integer(0);
	
	@Column(name = "wechat")
	private Integer wechat = new Integer(0);
	
	@Column(name = "android")
	private Integer android = new Integer(0);
	
	
	
	

	public Integer getIos() {
		return ios;
	}

	public void setIos(Integer ios) {
		this.ios = ios;
	}

	public Integer getWechat() {
		return wechat;
	}

	public void setWechat(Integer wechat) {
		this.wechat = wechat;
	}

	public Integer getAndroid() {
		return android;
	}

	public void setAndroid(Integer android) {
		this.android = android;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	
	
	
	
}
	


	
