package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "device_collection")
public class DeviceCollection implements Serializable {

	@Id
	@GeneratedValue(generator = "deviceCollectionTableGenerator")
	@GenericGenerator(name = "deviceCollectionTableGenerator", strategy = "identity")
	@Column(name = "id", length = 11)
	private Integer id;

	@Column(name = "device_no")
	private String deviceNo;

	@Column(name = "epal_id")
	private String epalId;

	@Column(name = "audio_id")
	private String audioId;

	@Column(name = "audio_name")
	private String audioName;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "res_from")
	private String resFrom;

	@Column(name = "url")
	private String url;
	
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getResFrom() {
		return resFrom;
	}

	public void setResFrom(String resFrom) {
		this.resFrom = resFrom;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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
