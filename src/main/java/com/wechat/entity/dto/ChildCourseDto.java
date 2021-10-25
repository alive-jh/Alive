package com.wechat.entity.dto;

import java.util.ArrayList;

public class ChildCourseDto {
	
	private Integer childCourseId;
	
	private String epalId;
	
	private Integer productid;
	
	private ArrayList periodStudyRecords;

	public Integer getChildCourseId() {
		return childCourseId;
	}

	public void setChildCourseId(Integer childCourseId) {
		this.childCourseId = childCourseId;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public ArrayList getPeriodStudyRecords() {
		return periodStudyRecords;
	}

	public void setPeriodStudyRecords(ArrayList periodStudyRecords) {
		this.periodStudyRecords = periodStudyRecords;
	}
	
	

}
