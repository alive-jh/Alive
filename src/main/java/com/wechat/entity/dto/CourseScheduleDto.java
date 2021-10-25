package com.wechat.entity.dto;

import java.util.ArrayList;

public class CourseScheduleDto {
	
	
	private Integer catId;
	
	private String catName;

	private ArrayList catCourse;

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public ArrayList getCatCourse() {
		return catCourse;
	}

	public void setCatCourse(ArrayList catCourse) {
		this.catCourse = catCourse;
	}



}
