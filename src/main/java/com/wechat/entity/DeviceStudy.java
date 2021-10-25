package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_study")
public class DeviceStudy implements Serializable{
	
	@Id
	@GeneratedValue(generator ="deviceStudyTableGenerator")       
    @GenericGenerator(name ="deviceStudyTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "epal_id")
	private String epalId;
	
	@Column(name = "mission_id")
	private String missionId;
	
	@Column(name = "mission_name")
	private String missionName;
	
	@Column(name = "content")
	private String content;
	
	@Transient
	private Device device;
	
	public String getMissionId() {
		return missionId;
	}
	
	

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public Integer getIsdone() {
		return isdone;
	}

	public void setIsdone(Integer isdone) {
		this.isdone = isdone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "isdone")
	private Integer isdone;
	
	
	@Column(name = "url")
	private String url;
	
	
	
	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}



	public Device getDevice() {
		return device;
	}



	public void setDevice(Device device) {
		this.device = device;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}


	
}
