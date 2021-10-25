package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：library
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "library")
public class Library {

	@Id
	@GeneratedValue(generator ="librarytableGenerator")       
    @GenericGenerator(name ="librarytableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	//条形码
	@Column(name = "isbn")
	private String ISBN;
	
	//书名字
	@Column(name = "book_name")
	private String bookName;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getISBN(){
		return this.ISBN;
	}
	public void setISBN(String ISBN){
		this.ISBN = ISBN;
	}
	
	public String getBookName(){
		return this.bookName;
	}
	public void seBookName(String bookName){
		this.bookName = bookName;
	}
}
