package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassCourseRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_course_record", catalog = "wechat")
public class ClassCourseRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer studentId;
	private Integer classGradesId;
	private Integer classCourseId;
	private Integer classRoomId;
	private Integer complete;
	private String score;
	private String doTitle;
	private Integer usedTime;
	private Timestamp completeTime;

	// Constructors

	/** default constructor */
	public ClassCourseRecord() {
	}

	/** full constructor */
	public ClassCourseRecord(Integer studentId, Integer classGradesId,Integer classCourseId, Integer classRoomId,
			Integer complete, String score,String doTitle,Integer usedTime, Timestamp completeTime) {
		this.studentId = studentId;
		this.classGradesId=classGradesId;
		this.classCourseId=classCourseId;
		this.classRoomId = classRoomId;
		this.complete = complete;
		this.score = score;
		this.doTitle = doTitle;
		this.usedTime=usedTime;
		this.completeTime = completeTime;
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

	@Column(name = "student_id")
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Column(name = "class_room_id")
	public Integer getClassRoomId() {
		return this.classRoomId;
	}

	public void setClassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
	}

	@Column(name = "complete")
	public Integer getComplete() {
		return this.complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	@Column(name = "score")
	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Column(name = "complete_time", length = 19)
	public Timestamp getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "class_grades_id")
	public Integer getClassGradesId() {
		return classGradesId;
	}

	public void setClassGradesId(Integer classGradesId) {
		this.classGradesId = classGradesId;
	}

	@Column(name = "class_course_id")
	public Integer getClassCourseId() {
		return classCourseId;
	}

	public void setClassCourseId(Integer classCourseId) {
		this.classCourseId = classCourseId;
	}

	@Column(name = "used_time")
	public Integer getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}

	@Column(name = "do_title")
	public String getDoTitle() {
		return doTitle;
	}

	public void setDoTitle(String doTitle) {
		this.doTitle = doTitle;
	}

}