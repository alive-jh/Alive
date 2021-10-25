package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_course", catalog = "wechat")
public class ClassCourse implements java.io.Serializable {

	// Fields

	private Integer id;
	private String doTitle;
	private Integer doSlot;
	private Integer doDay;
	private Integer classRoomId;
	private Integer classGradesId;
	private String cover;
	private Timestamp createTime;
//	@Transient
//	private List<ClassCourse>  subCourses;

	// Constructors

	/** default constructor */
	public ClassCourse() {
	}

	/** full constructor */
	public ClassCourse(String doTitle, Integer doSlot, Integer doDay,
			Integer classRoomId, Integer classGradesId,String cover, Timestamp createTime) {
		this.doTitle = doTitle;
		this.doSlot = doSlot;
		this.doDay = doDay;
		this.classRoomId = classRoomId;
		this.classGradesId = classGradesId;
		this.cover=cover;
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

	@Column(name = "do_title")
	public String getDoTitle() {
		return this.doTitle;
	}

	public void setDoTitle(String doTitle) {
		this.doTitle = doTitle;
	}

	@Column(name = "do_slot")
	public Integer getDoSlot() {
		return this.doSlot;
	}

	public void setDoSlot(Integer doSlot) {
		this.doSlot = doSlot;
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

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "cover")
	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

//	public List<ClassCourse> getSubCourses() {
//		return subCourses;
//	}
//
//	public void setSubCourses(List<ClassCourse> subCourses) {
//		this.subCourses = subCourses;
//	}
}