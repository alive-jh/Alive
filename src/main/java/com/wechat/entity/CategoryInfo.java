package com.wechat.entity;

import java.util.ArrayList;
import java.util.List;

public class CategoryInfo {

	private String title;//一级分类标题
	
	private List<TempCategory> categoryList = new ArrayList();//二级分类集合
	
	private Integer count = 0;//计划含有课程数

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TempCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<TempCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
	
	
	
}
