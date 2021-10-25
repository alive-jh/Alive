package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassCourseSchedule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_course_schedule", catalog = "wechat")
public class ClassCourseSchedule implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer studentId;
	private Timestamp startTime;
	private Timestamp lastTime;
	private Integer doDay;
	private Integer classRoomId;
	private Integer classCourseId;
	private Integer classGradesId;
	private Integer sort; //序号
 
	// Constructors

	/** default constructor */
	public ClassCourseSchedule() {
	}

	/** full constructor */
	public ClassCourseSchedule(Integer studentId, Timestamp startTime,
			Timestamp lastTime, Integer doDay, Integer classRoomId,Integer classCourseId,
			Integer classGradesId,Integer sort) {
		this.studentId = studentId;
		this.startTime = startTime;
		this.lastTime = lastTime;
		this.doDay = doDay;
		this.classRoomId = classRoomId;
		this.classCourseId=classCourseId;
		this.classGradesId = classGradesId;
		this.sort = sort;
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

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "last_time", length = 19)
	public Timestamp getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	@Column(name = "do_day")
	public Integer getDoDay() {
		return this.doDay;
	}

	public void setDoDay(Integer doDay) {
		this.doDay = doDay;
	}

	@Column(name = "class_room_id")
	public Integer getClassRoomId() {
		return this.classRoomId;
	}

	public void setClassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
	}

	@Column(name = "class_grades_id")
	public Integer getClassGradesId() {
		return this.classGradesId;
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

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}