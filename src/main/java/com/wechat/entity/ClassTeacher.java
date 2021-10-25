package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassTeacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_teacher", catalog = "wechat")
public class ClassTeacher implements java.io.Serializable {

	// Fields

	@Override
	public String toString() {
		return "ClassTeacher [id=" + id + ", name=" + name + ", memberId=" + memberId + ", createTime=" + createTime
				+ ", level=" + level + ", agentId=" + agentId + "]";
	}

	private Integer id;
	private String name;
	private String memberId;
	private Timestamp createTime;
	private Integer level;
	private Integer agentId;

	// Constructors

	/** default constructor */
	public ClassTeacher() {
	}

	/** full constructor */
	public ClassTeacher(String name, String memberId, Timestamp createTime,Integer level,Integer agentId) {
		this.name = name;
		this.memberId = memberId;
		this.createTime = createTime;
		this.level = level;
		this.agentId = agentId;
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

	@Column(name = "member_id")
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "agent_id")
	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
}