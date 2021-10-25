package com.wechat.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassRoom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_room_category_real", catalog = "wechat")
public class ClassTest implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classRoomId;
	private Integer ceategoryId;

	// Constructors

	/** default constructor */
	public ClassTest() {
	}
	
	

	public ClassTest( Integer classRoomId, Integer ceategoryId) {
		this.classRoomId = classRoomId;
		this.ceategoryId = ceategoryId;
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
	public Integer getclassRoomId() {
		return this.classRoomId;
	}

	public void setclassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
	}

	@Column(name = "ceategory_id")
	public Integer getceategoryId() {
		return this.ceategoryId;
	}

	public void setceategoryId(Integer ceategoryId) {
		this.ceategoryId = ceategoryId;
	}

	
}