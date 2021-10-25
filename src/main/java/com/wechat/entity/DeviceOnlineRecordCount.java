package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：device_online_record_count
 * 数据说明：在线机器人数量统计结果
 * 
 * */

@Entity
@Table(name = "device_online_record_count")
public class DeviceOnlineRecordCount {

	@Id
	@GeneratedValue(generator ="DeviceOnlineRecordCounttableGenerator")       
    @GenericGenerator(name ="DeviceOnlineRecordCounttableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 设备总数
	@Column(name = "device_count")
	private Integer deviceCount;
	
	// 在线数
	@Column(name = "online_count")
	private Integer onlineCount;
	
	// 插入时间
	@Column(name = "insert_date")
	private String insertDate;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getDeviceCount(){
		return this.deviceCount;
	}
	public void setDeviceCountr(Integer deviceCount){
		this.deviceCount = deviceCount;
	}
	
	public Integer getOnlineCount(){
		return this.onlineCount;
	}
	public void setOnlineCount(Integer onlineCount){
		this.onlineCount = onlineCount;
	}
	
	public String getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDated(String insertDate){
		this.insertDate = insertDate;
	}
	

}
