package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "device_playrecord")
public class DevicePlayRecord implements Serializable{
	
	@Id
	@GeneratedValue(generator ="devicePlayRecordTableGenerator")       
    @GenericGenerator(name ="devicePlayRecordTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "epal_id")
	private String epalId;
	
	@Column(name = "audio_id")
	private String audioId;
	
	@Column(name = "audio_name")
	private String audioName;
	
	@Column(name = "start_time")
	private Timestamp startTime;
	
	@Transient
	private Device device;
	
	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getLastid() {
		return lastid;
	}

	public void setLastid(String lastid) {
		this.lastid = lastid;
	}

	public String getNextid() {
		return nextid;
	}

	public void setNextid(String nextid) {
		this.nextid = nextid;
	}

	@Column(name = "from")
	private String from;
	
	@Column(name = "lastid")
	private String lastid;
	
	@Column(name = "nextid")
	private String nextid;
	
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


	
}
