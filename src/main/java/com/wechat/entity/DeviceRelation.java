package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_relation")
public class DeviceRelation implements Serializable{
	
	@Id
	@GeneratedValue(generator ="deviceRelationTableGenerator")       
    @GenericGenerator(name ="deviceRelationTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "epal_id")
	private String epalId;
	
	@Column(name = "friend_id")
	private String friendId;
	
	@Column(name = "friend_name")
	private String friendName;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "isbind")
	private Integer isbind;
	
	

	@Transient
	private String nickName;

	
	@Transient
	private String deviceType;

	@Transient
	private String deviceUsedType;

	@Transient
	private Integer deviceId;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	private Device device;
	
	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public Integer getIsbind() {
		return isbind;
	}

	public void setIsbind(Integer isbind) {
		this.isbind = isbind;
	}


	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}	
	
	public String getDeviceUsedType() {
		return this.deviceUsedType;
	}

	public void setDeviceUsedType(String deviceUsedType) {
		this.deviceUsedType = deviceUsedType;
	}	
	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}	
}
