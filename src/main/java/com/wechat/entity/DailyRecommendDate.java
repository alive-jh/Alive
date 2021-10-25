package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：daily_recommend_date
 * 
 * 
 * 
 * */

@Entity
@Table(name = "daily_recommend_date")
public class DailyRecommendDate {

	@Id
	@GeneratedValue(generator ="DailyRecommendDatetableGenerator")       
    @GenericGenerator(name ="DailyRecommendDatetableGenerator", strategy ="identity")
	
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 日期名称
	@Column(name = "dateName")
	private String dateName;
	

	// 简介
	@Column(name = "intro")
	private String intro;


	// 创建日期
	@Column(name = "createTime")
	private String createTime;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getDateName(){
		return this.dateName;
	}
	public void setDateName(String dateName){
		this.dateName =dateName;
	}


	public String getIntro(){
		return this.intro;
	}
	public void setIntro(String intro){
		this.intro = intro;
	}
	
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime =createTime;
	}
}
