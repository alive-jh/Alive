package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Test entity. @author zlisten
 */
@Entity
@Table(name = "eval_label", catalog = "wechat")
public class EvalLabel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer status;
	private Integer sort;
	private Timestamp creatTime;
	private Timestamp editTime;
	
	private String type;
	private String groups;
	private String name;
	

	// Constructors

	/** default constructor */
	public EvalLabel() {
	}

	/** full constructor */
	public EvalLabel(Integer status,Integer sort,String type,String groups,String name){
		this.status = status;
		this.sort = sort;
		
		this.type = type;
		this.groups = groups;
		this.name = name;
		
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name = "creat_time", insertable =false, updatable = false)
	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	@Column(name = "edit_time", insertable =false, updatable = false)
	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}	
	
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "groups")
	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}