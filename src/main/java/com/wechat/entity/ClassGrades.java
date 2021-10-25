package com.wechat.entity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassGrades entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_grades", catalog = "wechat")
@DynamicUpdate(true)
public class ClassGrades implements java.io.Serializable {

	// Fields

	private Integer id;
	private String classGradesName;
	private Integer parentId;
	private String summary;
	private String cover;
	private Integer sort;
	private Timestamp createTime;
	private String mark;
	private String choose;
	private String gradesType;
	private Integer teacherId;
	private Integer status;
	private Integer auditingStatus; // 审核状态
	private Integer price; // 价格

	private String classOpenDate;// 开班时间
	private int joinStatus;// 开班时间
	// Constructors

	/** default constructor */
	public ClassGrades() {
	}

	/** full constructor */
	public ClassGrades(String classGradesName, Integer parentId, String summary, String cover, Integer sort,
			Timestamp createTime, Integer teacherId, String gradesType, Integer status, Integer auditingStatus,
			Integer price, String classOpenDate) {
		this.classGradesName = classGradesName;
		this.parentId = parentId;
		this.summary = summary;
		this.cover = cover;
		this.sort = sort;
		this.createTime = createTime;
		this.teacherId = teacherId;
		this.gradesType = gradesType;
		this.status = status;
		this.auditingStatus = auditingStatus;
		this.price = price;
		this.classOpenDate = classOpenDate;
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

	@Column(name = "class_grades_name")
	public String getClassGradesName() {
		return this.classGradesName;
	}

	public void setClassGradesName(String classGradesName) {
		this.classGradesName = classGradesName;
	}

	@Column(name = "parent_id")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "summary")
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "cover")
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "create_time", length = 19, updatable = false)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "grades_type")
	public String getGradesType() {
		return gradesType;
	}

	public void setGradesType(String gradesType) {
		this.gradesType = gradesType;
	}

	@Column(name = "teacher_id")
	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Transient
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Transient
	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	@Column(name = "auditing_status")
	public Integer getAuditingStatus() {
		return this.auditingStatus;
	}

	public void setAuditingStatus(Integer auditingStatus) {
		this.auditingStatus = auditingStatus;
	}

	@Column(name = "price")
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Column(name = "classOpenDate")
	public String getClassOpenDate() {
		return classOpenDate;
	}

	public void setClassOpenDate(String classOpenDate) {
		this.classOpenDate = classOpenDate;
	}

	@Column(name = "joinStatus")
	public int getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(int joinStatus) {
		this.joinStatus = joinStatus;
	}
}