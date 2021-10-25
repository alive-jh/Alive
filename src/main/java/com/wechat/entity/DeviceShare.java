package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "device_share")
public class DeviceShare implements Serializable{
	
	@Id
	@GeneratedValue(generator ="deviceShareTableGenerator")       
    @GenericGenerator(name ="deviceShareTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "share_url")
	private String shareUrl;
	
	@Column(name = "epal_id")
	private String epalId;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "share_title")
	private String shareTitle;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "path")
	private String path;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



	
}
