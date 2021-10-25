package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassRoomSign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_room_sign", catalog = "wechat")
public class ClassRoomSign implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classRoomId;
	private String epalId;
	private Timestamp startTime;
	private Timestamp createTime;
	private String pushId;
	// Constructors

	/** default constructor */
	public ClassRoomSign() {
	}

	/** full constructor */
	public ClassRoomSign(Integer classRoomId, String epalId,
			Timestamp startTime, Timestamp createTime,String pushId) {
		this.classRoomId = classRoomId;
		this.epalId = epalId;
		this.startTime = startTime;
		this.createTime = createTime;
		this.pushId = pushId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "class_room_id")
	public Integer getClassRoomId() {
		return this.classRoomId;
	}

	public void setClassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
	}

	@Column(name = "epal_id")
	public String getEpalId() {
		return this.epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "pushId", length = 19)
	public String getPushId() {
		return this.pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

}