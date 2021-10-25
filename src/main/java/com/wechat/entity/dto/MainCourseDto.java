package com.wechat.entity.dto;

import java.util.ArrayList;

public class MainCourseDto {
	
	private Integer mainCourseId;
	
	private String mainCourseName;
	
	private String mainCourseLogo;
	
	private ArrayList childrenCourses;

	public Integer getMainCourseId() {
		return mainCourseId;
	}

	public void setMainCourseId(Integer mainCourseId) {
		this.mainCourseId = mainCourseId;
	}

	public String getMainCourseName() {
		return mainCourseName;
	}

	public void setMainCourseName(String mainCourseName) {
		this.mainCourseName = mainCourseName;
	}

	public String getMainCourseLogo() {
		return mainCourseLogo;
	}

	public void setMainCourseLogo(String mainCourseLogo) {
		this.mainCourseLogo = mainCourseLogo;
	}

	public ArrayList getChildrenCourses() {
		return childrenCourses;
	}

	public void setChildrenCourses(ArrayList childrenCourses) {
		this.childrenCourses = childrenCourses;
	}
	
	

}
