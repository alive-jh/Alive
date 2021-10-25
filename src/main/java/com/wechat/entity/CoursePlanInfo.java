package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "course_plan_info")
public class CoursePlanInfo {
	
	@Id
	@GeneratedValue(generator ="coursePlanGenerator")       
    @GenericGenerator(name ="coursePlanGenerator", strategy ="identity")
	
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "plan_id")
	private Integer planId;//课程计划ID
	
	@Column(name = "productid")
	private Integer productId;//主课程id
	
	@Column(name = "courseid")
	private Integer courseId;//子课程id
	
	@Column(name = "periodid")
	private Integer periodId;//子课时id
	
	@Column(name = "sort")
	private Integer sort;//课程计划组内排序
	
	@Column(name = "summary")
	private String summary;//子课时简介

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
	
	

}
