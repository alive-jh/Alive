package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：device_online_record_count
 * 数据说明：在线机器人数量统计结果
 * 
 * */

@Entity
@Table(name = "device_online_record")
public class DeviceOnlineRecord {

	@Id
	@GeneratedValue(generator ="DeviceOnlineRecordtableGenerator")       
    @GenericGenerator(name ="DeviceOnlineRecordtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 机器人账号
	@Column(name = "epal_id")
	private String epalId;
	
	// 在线时间
	@Column(name = "time")
	private String time;
	

	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getEpalId(){
		return this.epalId;
	}
	public void setEpalId(String epalId){
		this.epalId = epalId;
	}
	
	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time = time;
	}

}
