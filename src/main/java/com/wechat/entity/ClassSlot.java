package com.wechat.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassSlot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_slot", catalog = "wechat")
public class ClassSlot implements java.io.Serializable {

	// Fields

	private Integer id;
	private String epalId;
	private String name;
	private String doTime;
	private Integer doSlot;

	// Constructors

	/** default constructor */
	public ClassSlot() {
	}

	/** full constructor */
	public ClassSlot(String epalId, String doTime, Integer doSlot,String name) {
		this.epalId = epalId;
		this.doTime = doTime;
		this.doSlot = doSlot;
		this.name = name;
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

	@Column(name = "epal_id")
	public String getEpalId() {
		return this.epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	@Column(name = "do_time")
	public String getDoTime() {
		return this.doTime;
	}

	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}

	@Column(name = "do_slot")
	public Integer getDoSlot() {
		return this.doSlot;
	}

	public void setDoSlot(Integer doSlot) {
		this.doSlot = doSlot;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}