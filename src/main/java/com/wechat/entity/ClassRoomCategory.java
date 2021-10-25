package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassRoomCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_room_category", catalog = "wechat")
public class ClassRoomCategory implements java.io.Serializable {

	// Fields

	private Integer id;
	private String categoryName;
	private Integer parentId;
	private String summary;
	private String cover;
	private Integer sort;
	private Timestamp createTime;
	private String mark;

	// Constructors

	/** default constructor */
	public ClassRoomCategory() {
	}

	/** full constructor */
	public ClassRoomCategory(String categoryName, Integer parentId,
			String summary, String cover, Integer sort, Timestamp createTime) {
		this.categoryName = categoryName;
		this.parentId = parentId;
		this.summary = summary;
		this.cover = cover;
		this.sort = sort;
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

	@Column(name = "category_name")
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "parent_id")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "summary")
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "cover")
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
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

	@Transient
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}