package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassScriptNormal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_script_normal", catalog = "wechat")
public class ClassScriptNormal implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer classRoomId;
	private Integer classScriptTypeId;
	private String classScriptContent;
	private Integer sort;
	private Timestamp createTime;
	private Integer totalTime;
	// Constructors

	/** default constructor */
	public ClassScriptNormal() {
	}

	/** full constructor */
	public ClassScriptNormal(Integer classRoomId, Integer classScriptTypeId,
			String classScriptContent,Integer sort, Timestamp createTime,Integer totalTime) {
		this.classRoomId = classRoomId;
		this.classScriptTypeId = classScriptTypeId;
		this.classScriptContent = classScriptContent;
		this.sort=sort;
		this.createTime = createTime;
		this.totalTime = totalTime;
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

	@Column(name = "class_script_type_id")
	public Integer getClassScriptTypeId() {
		return this.classScriptTypeId;
	}

	public void setClassScriptTypeId(Integer classScriptTypeId) {
		this.classScriptTypeId = classScriptTypeId;
	}

	@Column(name = "class_script_content")
	public String getClassScriptContent() {
		return this.classScriptContent;
	}

	public void setClassScriptContent(String classScriptContent) {
		this.classScriptContent = classScriptContent;
	}
	
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "total_time")
	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		return "ClassScriptNormal [id=" + id + ", classRoomId=" + classRoomId + ", classScriptTypeId="
				+ classScriptTypeId + ", classScriptContent=" + classScriptContent + ", sort=" + sort + ", createTime="
				+ createTime + ", totalTime=" + totalTime + "]";
	}
	
	

}