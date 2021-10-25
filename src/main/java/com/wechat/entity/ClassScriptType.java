package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassScriptType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_script_type", catalog = "wechat")
public class ClassScriptType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ClassScriptType() {
	}

	/** full constructor */
	public ClassScriptType(String name, Timestamp createTime) {
		this.name = name;
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

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}