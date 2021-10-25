package com.wechat.entity.dto;

public class ChildrenCourseDto {
	
	private Integer productId;
	
	private String parentName;
	
	private String teacher;//老师
	
	private String brief;//简介
	
	private Integer currentClass;//当前课时
	
	private String epalId;
	
	private Integer courseId;
	
	private String chapterRes;
	
	private String chapterName;
	
	private Integer totalClass;//总课时
	
	private Integer courseCatId;
	
	private String  courseCatName;
	
	private String courseLogo;//课程封面
	
	private CourseScheduleInfoDto courseScheduleInfoDto;

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Integer getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(Integer currentClass) {
		this.currentClass = currentClass;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

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

	public CourseScheduleInfoDto getCourseScheduleInfoDto() {
		return courseScheduleInfoDto;
	}

	public void setCourseScheduleInfoDto(CourseScheduleInfoDto courseScheduleInfoDto) {
		this.courseScheduleInfoDto = courseScheduleInfoDto;
	}

	public String getCourseLogo() {
		return courseLogo;
	}

	public void setCourseLogo(String courseLogo) {
		this.courseLogo = courseLogo;
	}
	
	

}
