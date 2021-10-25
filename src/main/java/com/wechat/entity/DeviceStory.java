package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "device_story")
public class DeviceStory implements Serializable {

	@Id
	@GeneratedValue(generator = "deviceStoryTableGenerator")
	@GenericGenerator(name = "deviceStoryTableGenerator", strategy = "identity")
	@Column(name = "id", length = 11)
	private Integer id;

	@Column(name = "device_no")
	private String deviceNo;

	@Column(name = "epal_id")
	private String epalId;

	@Column(name = "story_id")
	private String storyId;

	@Column(name = "story_name")
	private String storyName;
	
	@Column(name = "author_id")
	private String authorId;

	@Column(name = "upload_time")
	private Timestamp uploadTime;
	
	@Column(name = "public_time")
	private Timestamp publicTime;

	@Column(name = "url")
	private String url;
	
	@Column(name = "play_times")
	private Integer playTimes;
	
	@Column(name = "positive_rate")
	private Float positiveRate;
	
	@Column(name = "comment")
	private String comment;
	
	@Transient
	private Device device;


	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getStoryName() {
		return storyName;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Timestamp getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Timestamp publicTime) {
		this.publicTime = publicTime;
	}

	public Integer getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(Integer playTimes) {
		this.playTimes = playTimes;
	}

	public Float getPositiveRate() {
		return positiveRate;
	}

	public void setPositiveRate(Float positiveRate) {
		this.positiveRate = positiveRate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
