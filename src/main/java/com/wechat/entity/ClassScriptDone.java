package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassScriptDone entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_script_done", catalog = "wechat")
public class ClassScriptDone implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classRoomId;
	private Integer classScriptTypeId;
	private String classScriptContent;
	private String reply;
	private String feedback;
	private String epalId;
	private Integer sort;
	private Timestamp createTime;
	private Integer studentId;
	private Integer classCourseId;

	// Constructors

	/** default constructor */
	public ClassScriptDone() {
	}

	/** full constructor */
	public ClassScriptDone(Integer classRoomId, Integer classScriptTypeId,
			String classScriptContent, String reply,String feedback, String epalId,Integer sort,
			Timestamp createTime,Integer studentId,Integer classCourseId) {
		this.classRoomId = classRoomId;
		this.classScriptTypeId = classScriptTypeId;
		this.classScriptContent = classScriptContent;
		this.reply = reply;
		this.feedback=feedback;
		this.epalId = epalId;
		this.sort=sort;
		this.createTime = createTime;
		this.studentId = studentId;
		this.classCourseId = classCourseId;
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

	@Column(name = "class_script_type_id")
	public Integer getClassScriptTypeId() {
		return this.classScriptTypeId;
	}

	public void setClassScriptTypeId(Integer classScriptTypeId) {
		this.classScriptTypeId = classScriptTypeId;
	}

	@Column(name = "class_script_content")
	public String getClassScriptContent() {
		return this.classScriptContent;
	}

	public void setClassScriptContent(String classScriptContent) {
		this.classScriptContent = classScriptContent;
	}

	@Column(name = "reply")
	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Column(name = "epal_id")
	public String getEpalId() {
		return this.epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "feedback")
	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Column(name = "student_id")
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Column(name = "class_course_id")
	public Integer getClassCourseId() {
		return this.classCourseId;
	}

	public void setClassCourseId(Integer classCourseId) {
		this.classCourseId = classCourseId;
	}
	
}