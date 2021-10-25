package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassCourseReply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_course_reply", catalog = "wechat")
public class ClassCourseReply implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer gradesId;
	private Integer recordId;
	private Integer teacherId;
	private String replyText;
	private String replyVoice;
	private String studentVoice;
	private Timestamp replyTime;

	// Constructors

	/** default constructor */
	public ClassCourseReply() {
	}

	/** full constructor */
	public ClassCourseReply(Integer gradesId, Integer recordId,
			Integer teacherId, String replyText, String replyVoice,
			String studentVoice, Timestamp replyTime) {
		this.gradesId = gradesId;
		this.recordId = recordId;
		this.teacherId = teacherId;
		this.replyText = replyText;
		this.replyVoice = replyVoice;
		this.studentVoice = studentVoice;
		this.replyTime = replyTime;
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

	@Column(name = "grades_id")
	public Integer getGradesId() {
		return this.gradesId;
	}

	public void setGradesId(Integer gradesId) {
		this.gradesId = gradesId;
	}

	@Column(name = "record_id")
	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	@Column(name = "teacher_id")
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "reply_text")
	public String getReplyText() {
		return this.replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	@Column(name = "reply_voice")
	public String getReplyVoice() {
		return this.replyVoice;
	}

	public void setReplyVoice(String replyVoice) {
		this.replyVoice = replyVoice;
	}

	@Column(name = "student_voice")
	public String getStudentVoice() {
		return this.studentVoice;
	}

	public void setStudentVoice(String studentVoice) {
		this.studentVoice = studentVoice;
	}

	@Column(name = "reply_time", length = 19)
	public Timestamp getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}

}