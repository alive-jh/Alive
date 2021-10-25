package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "electrismordertime")
public class ElectrismOrderTime {


	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;
	

	@Column(name = "electrismid")
	private Integer electrismId;//电工id
	
	
	@Column(name = "createDate")
	private String createDate;// 订单日期
	
	@Column(name = "time")
	private String time;	//订单时间
	
	@Column(name = "status")
	private Integer status = new Integer(0);//订单状态:0有效,1无效
	
	@Column(name = "orderid")
	private Integer orderId;
	
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getElectrismId() {
		return electrismId;
	}

	public void setElectrismId(Integer electrismId) {
		this.electrismId = electrismId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
}
