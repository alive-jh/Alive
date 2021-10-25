package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "history_schedules")
public class HistorySchedules {
	
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "event")
	private String event ;						
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	@Column(name = "sid")
	private String sid ;					
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Column(name = "do_time")
	private String do_time;
	public String getDoTime() {
		return do_time;
	}

	public void setDoTime(String do_time) {
		this.do_time = do_time;
	}
	
	@Column(name = "picture")
	private String picture;
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	
	@Column(name = "content")
	private String content;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	@Column(name = "note")
	private String note;
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "period")
	private String period;
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Column(name = "type")
	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "epalId")
	private String epalId;
	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}	

	@Column(name = "scheduleId")
	private String scheduleId;
	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}	
	
	@Column(name = "title")
	private String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	
	
	@Column(name = "exe_time")
	private Long exe_time;
	public Long getExeTime() {
		return exe_time;
	}

	public void setExeTime(Long exe_time) {
		this.exe_time = exe_time;
	}	

	@Column(name = "description")
	private String description;
	public String description() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
	
	

	
	

}
