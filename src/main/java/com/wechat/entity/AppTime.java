package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "apptime")
public class AppTime {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "device_no")
	private String deviceNo;	//机器人唯一标示
	
	@Column(name = "epal_id")
	private String epalId;	//机器人IM号
	
	@Column(name = "time")
	private String time;		//使用时间,单位秒
	
	
	@Column(name = "createdate")
	private Date createDate;	//创建时间
	
	
	@Column(name = "startdate")
	private Date startDate;		//开始时间
	
	
	@Column(name = "enddate")
	private Date endDate;		//结束时间
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}
	
	
	
}
