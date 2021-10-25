package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：device_activity
 * 数据说明：设备激活时间
 * 补充说明：
 * 
 * */

@Entity
@Table(name = "device_activity")
public class DeviceActivity {

	@Id
	@GeneratedValue(generator ="DeviceActivitytableGenerator")       
    @GenericGenerator(name ="DeviceActivitytableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	// 设备号
	@Column(name = "deviceNo")
	private String deviceNo;
	
	
	public String getDeviceNo(){
		return this.deviceNo;
	}
	public void setDeviceNo(String deviceNo){
		this.deviceNo = deviceNo;
	}
	
	
	// EpalId
	@Column(name = "epalId")
	private String epalId;
	
	public String getEpalId(){
		return this.epalId;
	}
	public void setEpalId(String epalId){
		this.epalId = epalId;
	}
	
	// 激活时间
	@Column(name = "activityTime")
	private String activityTime;
	
	public String getActivityTime(){
		return this.activityTime;
	}
	public void setActivityTime(String activityTime){
		this.activityTime = activityTime;
	}

}
