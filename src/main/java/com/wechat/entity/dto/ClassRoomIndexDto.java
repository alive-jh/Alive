package com.wechat.entity.dto;

public class ClassRoomIndexDto {
	
	private Integer id;
	
	private String categoryName;
	
	private Integer parentId;
	
	private String summary;
	
	private String cover;
	
	private Integer sort;
	
	private String classRooms;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getClassRooms() {
		return classRooms;
	}

	public void setClassRooms(String classRooms) {
		this.classRooms = classRooms;
	}
	
	

}
