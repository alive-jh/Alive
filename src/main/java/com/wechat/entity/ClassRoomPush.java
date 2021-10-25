package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassRoomPush entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_room_push", catalog = "wechat")
public class ClassRoomPush implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classRoomId;
	private String epalId;
	private String classRoomScript;
	private Integer cmdDoStatus;
	private String appAccount;
	private String groupId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ClassRoomPush() {
	}

	/** full constructor */
	public ClassRoomPush(Integer classRoomId, String epalId,
			String classRoomScript, Integer cmdDoStatus, String appAccount,
			String groupId, Timestamp createTime) {
		this.classRoomId = classRoomId;
		this.epalId = epalId;
		this.classRoomScript = classRoomScript;
		this.cmdDoStatus = cmdDoStatus;
		this.appAccount = appAccount;
		this.groupId = groupId;
		this.createTime = createTime;
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

	@Column(name = "epal_id", length = 225)
	public String getEpalId() {
		return this.epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	@Column(name = "class_room_script", length = 65535)
	public String getClassRoomScript() {
		return this.classRoomScript;
	}

	public void setClassRoomScript(String classRoomScript) {
		this.classRoomScript = classRoomScript;
	}

	@Column(name = "cmd_do_status")
	public Integer getCmdDoStatus() {
		return this.cmdDoStatus;
	}

	public void setCmdDoStatus(Integer cmdDoStatus) {
		this.cmdDoStatus = cmdDoStatus;
	}

	@Column(name = "app_account", length = 225)
	public String getAppAccount() {
		return this.appAccount;
	}

	public void setAppAccount(String appAccount) {
		this.appAccount = appAccount;
	}

	@Column(name = "group_id", length = 225)
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}