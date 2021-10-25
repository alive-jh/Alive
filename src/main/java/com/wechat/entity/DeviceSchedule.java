package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_schedule")
public class DeviceSchedule implements Serializable{
	
	@Id
	@GeneratedValue(generator ="deviceScheduleTableGenerator")       
    @GenericGenerator(name ="deviceScheduleTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "epal_id")
	private String epalId;
	
	@Column(name = "event")
	private String event;
	
	@Column(name = "event_cn")
	private String eventCN;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "period")
	private String period;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "do_time")
	private String doTime;
	
	@Column(name = "state")
	private Integer state;

	@Column(name = "is_def")
	private Integer isDef;
	
	@Column(name = "catalog_file")
	private String catalogFile;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "sid")
	private String sid;
	
	@Transient
	private Device device;

	@Column(name = "group_id")
	private Integer groupId;
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getEventCN() {
		return eventCN;
	}
	public void setEventCN(String eventCN) {
		this.eventCN = eventCN;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDoTime() {
		return doTime;
	}

	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsDef() {
		return isDef;
	}

	public void setIsDef(Integer isDef) {
		this.isDef = isDef;
	}

	public String getCatalogFile() {
		return catalogFile;
	}

	public void setCatalogFile(String catalogFile) {
		this.catalogFile = catalogFile;
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

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public Integer getGroupId(){
		return this.groupId;
	}


	
}
