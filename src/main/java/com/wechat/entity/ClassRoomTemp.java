package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassRoomTemp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_room_temp", catalog = "wechat")
public class ClassRoomTemp implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer teacherId;
	private String teacherName;
	private String className;
	private String cover;
	private String summary;
	private Integer status;
	private Integer sort;
	private Integer categoryId;
	private Integer bookResId;
	private String groupId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ClassRoomTemp() {
	}

	/** full constructor */
	public ClassRoomTemp(Integer teacherId, String teacherName,
			String className, String cover, String summary, Integer status,
			Integer sort, Integer categoryId, Integer bookResId,
			String groupId, Timestamp createTime) {
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.className = className;
		this.cover = cover;
		this.summary = summary;
		this.status = status;
		this.sort = sort;
		this.categoryId = categoryId;
		this.bookResId = bookResId;
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

	@Column(name = "teacher_id")
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "teacher_name")
	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Column(name = "class_name")
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "cover")
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Column(name = "summary")
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "category_id")
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "book_res_id")
	public Integer getBookResId() {
		return this.bookResId;
	}

	public void setBookResId(Integer bookResId) {
		this.bookResId = bookResId;
	}

	@Column(name = "group_id")
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