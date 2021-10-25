package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：ClassCourseRankingCategory
 * 数据说明：排行榜列表获取规则先取book表
 * 
 * */

@Entity
@Table(name = "class_course_ranking_category")
public class ClassCourseRankingCategory {

	@Id
	@GeneratedValue(generator ="ClassCourseRankingCategorytableGenerator")       
    @GenericGenerator(name ="ClassCourseRankingCategorytableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 描述
	@Column(name = "name")
	private String name;
	
	// 开始时间
	@Column(name = "start_time")
	private String startTime;
	
	//结束时间 
	@Column(name = "end_time")
	private String endTime;
	
	// 班级id列表
	@Column(name = "class_grades_ids")
	private String classGradesIds;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getStartTime(){
		return this.startTime;
	}
	public void setStartDate(String startTime){
		this.startTime = startTime;
	}
	
	public String getEndTime(){
		return this.endTime;
	}
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	
	public String getClassGradesIds(){
		return this.classGradesIds;
	}
	public void setClassGradesIds(String classGradesIds){
		this.classGradesIds = classGradesIds;
	}
	
}
