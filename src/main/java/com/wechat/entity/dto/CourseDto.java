package com.wechat.entity.dto;

public class CourseDto {
	
	private Integer productId;
	
	private String parentName;
	
	private Integer courseId;
	
	private String chapterRes;
	
	private String chapterName;
	
	private Integer totalClass;
	
	private Integer courseCatId;
	
	private String  courseCatName;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getChapterRes() {
		return chapterRes;
	}

	public void setChapterRes(String chapterRes) {
		this.chapterRes = chapterRes;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Integer getTotalClass() {
		return totalClass;
	}

	public void setTotalClass(Integer totalClass) {
		this.totalClass = totalClass;
	}

	public Integer getCourseCatId() {
		return courseCatId;
	}

	public void setCourseCatId(Integer courseCatId) {
		this.courseCatId = courseCatId;
	}

	public String getCourseCatName() {
		return courseCatName;
	}

	public void setCourseCatName(String courseCatName) {
		this.courseCatName = courseCatName;
	}

	

}
