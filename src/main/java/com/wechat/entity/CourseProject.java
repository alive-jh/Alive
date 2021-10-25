package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "courseproject")
public class CourseProject {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;			//会员ID
	
	@Column(name = "epalId")
	private String epalId;			//机器人账号
	
	@Column(name = "projectname")
	private String projectName;			//计划名称
	
	@Column(name = "createdate")	//创建日期
	private Date createDate;

	@Column(name = "sort")			//计划排序,从小到大
	private Integer sort;
	
	@Column(name = "recordcount")			//课程计划内执行课时的次数
	private Integer recordcount;
	
	@Column(name = "system_plan")	//系统内置计划ID
	private Integer systemPlan;
	
	@Column(name = "plan_type")	//课程计划类型
	private String planType;
	
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

	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Integer getSystemPlan() {
		return systemPlan;
	}

	public void setSystemPlan(Integer systemPlan) {
		this.systemPlan = systemPlan;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getRecordcount() {
		return recordcount;
	}

	public void setRecordcount(Integer recordcount) {
		this.recordcount = recordcount;
	}


}
