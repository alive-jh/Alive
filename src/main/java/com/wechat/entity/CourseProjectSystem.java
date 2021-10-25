package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "courseproject_system")
public class CourseProjectSystem {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "projectname")
	private String projectName;			//计划名称
	
	@Column(name = "lessonList")
	private String lessonList;			//lessonList 课程id列表
	
	@Column(name = "plan_type")
	private String planType;			//planType 计划类型
	
	@Column(name = "createdate")	//创建日期
	private Date createDate;

	@Column(name = "sort")			//计划排序,从小到大
	private Integer sort;
	
	@Column(name = "active")			//课程计划是否需要激活 1为需要激活，0为自动激活
	private Integer active;
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLessonList() {
		return lessonList;
	}

	public void setLessonList(String lessonList) {
		this.lessonList = lessonList;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}
	

}
