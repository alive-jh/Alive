package com.wechat.entity;

import java.util.ArrayList;
import java.util.List;

public class CurriculumIndex {
	
	
	private String title;//一级类型标题

	private List<CurriculumInfo> curriculumList = new ArrayList();//二级课程集合

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<CurriculumInfo> getCurriculumList() {
		return curriculumList;
	}

	public void setCurriculumList(List<CurriculumInfo> curriculumList) {
		this.curriculumList = curriculumList;
	}

	
	
	
}
