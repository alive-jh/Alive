package com.wechat.entity;

public class TempCategory {

	
	private Integer catId;	//分类ID
	
	private String title;	//标题
	
	private Integer count;	//计划含有课程数
	
	private String description = "";	//课程简介

	
	
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	
	
}
