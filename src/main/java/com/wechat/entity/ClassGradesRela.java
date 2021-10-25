package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassGradesRela entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_grades_rela", catalog = "wechat")
public class ClassGradesRela implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classGradesId;
	private Integer classStudentId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ClassGradesRela() {
	}

	/** full constructor */
	public ClassGradesRela(Integer classGradesId, Integer classStudentId,
			Timestamp createTime) {
		this.classGradesId = classGradesId;
		this.classStudentId = classStudentId;
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

	@Column(name = "class_grades_id")
	public Integer getClassGradesId() {
		return this.classGradesId;
	}

	public void setClassGradesId(Integer classGradesId) {
		this.classGradesId = classGradesId;
	}

	@Column(name = "class_student_id")
	public Integer getClassStudentId() {
		return this.classStudentId;
	}

	public void setClassStudentId(Integer classStudentId) {
		this.classStudentId = classStudentId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}