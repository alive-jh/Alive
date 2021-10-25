package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
//机器人心跳实列

@Entity
@Table(name = "device_heart_beat")
public class DeviceHeartBeat {
	@Id
	@GeneratedValue(generator ="deviceHeartBeattableGenerator")       
    @GenericGenerator(name ="deviceHeartBeattableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "epal_id")
	private String epalId;

	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "active_time")
	private long activeTime;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}
	
	public String getDeviceNo() {
		return this.deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public long getActiceTime() {
		return this.activeTime;
	}

	public void setActiceTime(long activeTime) {
		this.activeTime = activeTime;
	}
}
