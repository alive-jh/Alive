package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "course_plan")
public class CoursePlan {
	
	@Id
	@GeneratedValue(generator ="coursePlanGenerator")       
    @GenericGenerator(name ="coursePlanGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "epalid")
	private String epalId;			//机器人账号
	
	@Column(name = "memberid")
	private Integer memberId;			//会员ID
	
	@Column(name = "plan_name")
	private String planName;			//计划名称
	
	@Column(name = "createtime")	//创建时间
	private Timestamp createTime;

	@Column(name = "sort")			//计划排序,从小到大
	private Integer sort;
	
	@Column(name = "summary")	//课程计划简介
	private String summary;
	
	
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}


	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	

	
	

}
