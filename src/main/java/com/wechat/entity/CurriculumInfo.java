package com.wechat.entity;

import java.util.ArrayList;
import java.util.List;

public class CurriculumInfo {

	private Integer catId;//二级分类id
	
	private String title;//标题
	
	private List<TempCurriculum> infoList = new ArrayList();//课程集合

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

	public List<TempCurriculum> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<TempCurriculum> infoList) {
		this.infoList = infoList;
	}
	
	
	
}
