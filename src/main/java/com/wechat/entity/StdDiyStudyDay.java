package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/*
 * 表名：std_diy_study_day
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "std_diy_study_day")
public class StdDiyStudyDay implements java.io.Serializable{

	public StdDiyStudyDay(int id,int status,
			int stdId,int gradeId,Date creatTime,
			Date editTime,String rule,int ruleType,String week){
		this.id = id;
		this.status = status;
		this.stdId = stdId;
		this.gradeId = gradeId;
		this.creatTime = creatTime;
		this.editTime = editTime;
		this.rule = rule;
		this.ruleType = ruleType;
		this.week = week;
	}


	public StdDiyStudyDay() {
		// TODO Auto-generated constructor stub
	}


	@Id
	@GeneratedValue(generator ="StdDiyStudyDaytableGenerator")       
    @GenericGenerator(name ="StdDiyStudyDaytableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 状态
	@Column(name = "status")
	private Integer status = 1;

	
	// 学生ID
	@Column(name = "std_id")
	private Integer stdId;
	
	
	// 班级ID
	@Column(name = "grade_id")
	private Integer gradeId;
	
	// 创建时间
	@Column(name = "creat_time", insertable =false, updatable = false)
	private Date creatTime;

	
	// 编辑时间
	@Column(name = "edit_time", insertable =false, updatable = false)
	private Date editTime;

	// 规则
	@Column(name = "rule")
	private String rule;
	
	// 规则类型
	@Column(name = "rule_type")
	private Integer ruleType;	

	// 周规则
	@Column(name = "week")
	private String week;

	//是否使用老师默认的 
	@Column(name = "is_teacher_default")
	private Integer isTeacherDefault;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public String getRule(){
		return this.rule;
	}
	public void setRule(String rule){
		this.rule = rule;
	}

	public Integer getRuleType(){
		return this.ruleType;
	}
	public void setRuleType(Integer ruleType){
		this.ruleType = ruleType;
	}
	
	public Date getCreatTime(){
		return this.creatTime;
	}
	public void setCreatTime(Date creatTime){
		this.creatTime = creatTime;
	}

	public Date getEditTime(){
		return this.editTime;
	}
	public void setEditTime(Date editTime){
		this.editTime = editTime;
	}

	public Integer getStdId(){
		return this.stdId;
	}
	public void setStdId(Integer stdId){
		this.stdId = stdId;
	}
	
	public Integer getGradeId(){
		return this.gradeId;
	}
	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}
	public String getWeek() {
		// TODO Auto-generated method stub
		return this.week;
	}
	public void setWeek(String week) {
		// TODO Auto-generated method stub
		this.week = week;
	}

	public void setIsTeacherDefault(int isTeacherDefault) {
		// TODO Auto-generated method stub
		this.isTeacherDefault = isTeacherDefault;
	}
	
	public Integer getIsTeacherDefault() {
		// TODO Auto-generated method stub
		return this.isTeacherDefault;
	}
}
