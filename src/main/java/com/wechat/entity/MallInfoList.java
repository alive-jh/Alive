package com.wechat.entity;

import java.util.List;

public class MallInfoList {

	private String title;
	
	private String id;
	
	
	private List mallProductList;
	
	private Integer sort;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List getMallProductList() {
		return mallProductList;
	}

	public void setMallProductList(List mallProductList) {
		this.mallProductList = mallProductList;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
