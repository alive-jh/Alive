package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：daily_recommend_tag
 * 
 * 
 * 
 * */

@Entity
@Table(name = "daily_recommend_tag")
public class DailyRecommendTag {

	@Id
	@GeneratedValue(generator ="DailyRecommendTagtableGenerator")       
    @GenericGenerator(name ="DailyRecommendTagtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 标签名称
	@Column(name = "tagName")
	private String tagName;
	
	//简介
	@Column(name = "intro")
	private String intro;
		
	// 日期ID
	@Column(name = "dateId")
	private Integer dateId;
	
	
	//创建时间
	@Column(name = "createTime")
	private String createTime;
		
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getTagName(){
		return this.tagName;
	}
	public void setTagName(String tagName){
		this.tagName =tagName;
	}


	public int getDateId(){
		return this.dateId;
	}
	public void setDateId(int dateId){
		this.dateId = dateId;
	}
	
	public String getIntro(){
		return this.intro;
	}
	public void setIntro(String intro){
		this.intro =intro;
	}
	
	
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime =createTime;
	}
}
