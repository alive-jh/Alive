package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassStudentRela entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_student_rela", catalog = "wechat")
public class ClassStudentRela implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer classRoomId;
	private Integer classStudentId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ClassStudentRela() {
	}

	/** full constructor */
	public ClassStudentRela(String name, Integer classRoomId,
			Integer classStudentId, Timestamp createTime) {
		this.name = name;
		this.classRoomId = classRoomId;
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "class_room_id")
	public Integer getClassRoomId() {
		return this.classRoomId;
	}

	public void setClassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
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