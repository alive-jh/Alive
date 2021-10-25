package com.wechat.entity;

import java.util.ArrayList;
import java.util.List;

public class BookIndex {


	private String title;	//分类标题
	
	private List<BookIndexInfo> infoList = new ArrayList();	//分类集合


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<BookIndexInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<BookIndexInfo> infoList) {
		this.infoList = infoList;
	}


	
	
	
}
