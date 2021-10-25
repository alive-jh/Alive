package com.wechat.entity;

import java.util.ArrayList;
import java.util.List;

public class BookIndexInfo {
	
	private Integer id;	//ID
	
	private String title;//标题
	
	
	private List<TempBook> bookList = new ArrayList();//书籍集合


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List<TempBook> getBookList() {
		return bookList;
	}


	public void setBookList(List<TempBook> bookList) {
		this.bookList = bookList;
	}
	
	
	
	

}
