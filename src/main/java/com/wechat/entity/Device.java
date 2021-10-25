package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device")
public class Device implements Serializable{
	
	@Id
	@GeneratedValue(generator ="deviceTableGenerator")       
    @GenericGenerator(name ="deviceTableGenerator", strategy ="identity")
	
	/**
	 * DeviceID
	 */
    @Column(name = "id",length = 11)
	private Integer id;
	/**
	 * 设备唯一标示
	 */
	@Column(name = "device_no")
	private String deviceNo;
	/**
	 * EpalId
	 */
	@Column(name = "epal_id")
	private String epalId;
	/**
	 * 序列号
	 */
	@Column(name = "sn")
	private String sn;
	/**
	 * Epal密码
	 */
	@Column(name = "epal_pwd")
	private String epalPwd;
	
	/**
	 * 设备昵称
	 */	
	@Column(name = "nickname")
	private String nickName;	

	/**
	 * 备注
	 */	
	@Column(name = "remark")
	private String remark;
	
	
	/**
	 * 设备类型
	 */	
	@Column(name = "device_type")
	private String deviceType;

	/**
	 * 设备类型
	 */	
	@Column(name = "version")
	private String version;
	
	/**
	 * 设备使用类型
	 * 1为正常用户使用
	 * 2为电教室使用
	 */	
	@Column(name = "device_used_type")
	private String deviceUsedType;
	
	
	
	/**
	 * 状态
	 */	
	@Column(name = "status")
	private Integer status;
	
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

	
	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public String getEpalPwd() {
		return epalPwd;
	}

	public void setEpalPwd(String epalPwd) {
		this.epalPwd = epalPwd;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getNickName(){
		return this.nickName;
	}
	
	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}
	public String getDeviceType(){
		return this.deviceType;
	}

	public void setDeviceUsedType(String deviceUsedType){
		this.deviceUsedType = deviceUsedType;
	}
	public String getDeviceUsedType(){
		return this.deviceUsedType;
	}
	
	public void setVersion(String version){
		this.version = version;
	}
	public String getVersion(){
		return this.version;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	public Integer getStatus(){
		return this.status;
	}

	
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
	

}
