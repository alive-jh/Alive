package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassStudent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_student", catalog = "wechat")
public class ClassStudent implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String epalId;
	private Timestamp createTime;
	private Integer studentType;
	private float degreeOfDifficulty;
	// Constructors

	/** default constructor */
	public ClassStudent() {
	}

	/** full constructor */
	public ClassStudent(String name, String epalId, Timestamp createTime,
			Integer studentType,float degreeOfDifficulty) {
		this.name = name;
		this.epalId = epalId;
		this.createTime = createTime;
		this.studentType = studentType;
		this.degreeOfDifficulty = degreeOfDifficulty;
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
	@Column(name = "student_type")
	public Integer getStudentType() {
		return this.studentType;
	}

	public void setStudentType(Integer studentType) {
		this.studentType = studentType;
	}
	
	@Column(name = "degree_of_difficulty")
	public float getDegreeOfDifficulty(){
		return this.degreeOfDifficulty;
	}
	
	public void setDegreeOfDifficulty(float degreeOfDifficulty){
		this.degreeOfDifficulty = degreeOfDifficulty;
	}
	


}