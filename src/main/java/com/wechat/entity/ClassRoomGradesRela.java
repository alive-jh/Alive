package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassRoomGradesRela entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_room_grades_rela", catalog = "wechat")
public class ClassRoomGradesRela implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classRoomId;
	private Integer classGradesId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ClassRoomGradesRela() {
	}

	/** full constructor */
	public ClassRoomGradesRela(Integer classRoomId, Integer classGradesId,
			Timestamp createTime) {
		this.classRoomId = classRoomId;
		this.classGradesId = classGradesId;
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

}