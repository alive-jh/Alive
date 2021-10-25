package com.wechat.entity.dto;

public class CourseScheduleInfoDto {
	
	private String epalId;
	
	private Integer productId;
	
	private String courseIdList;
	
	private String currentClassList;
	
	private String cusFileList;

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

	public String getCourseIdList() {
		return courseIdList;
	}

	public void setCourseIdList(String courseIdList) {
		this.courseIdList = courseIdList;
	}

	public String getCurrentClassList() {
		return currentClassList;
	}

	public void setCurrentClassList(String currentClassList) {
		this.currentClassList = currentClassList;
	}

	public String getCusFileList() {
		return cusFileList;
	}

	public void setCusFileList(String cusFileList) {
		this.cusFileList = cusFileList;
	}
	
	

}
