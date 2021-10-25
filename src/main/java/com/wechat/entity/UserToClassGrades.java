package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：user_to_class_grades
 * 数据说明：账号和班级对应关系表
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "user_to_class_grades")
public class UserToClassGrades {

	@Id
	@GeneratedValue(generator ="UserToClassGradestableGenerator")       
    @GenericGenerator(name ="UserToClassGradestableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 用户ID
	@Column(name = "user_id")
	private Integer userId;
	
	// 班级ID
	@Column(name = "class_grades_id")
	private Integer classGradesId;
	

	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	public Integer getClassGradesId(){
		return this.classGradesId;
	}
	public void setClassGradesId(Integer classGradesId){
		this.classGradesId = classGradesId;
	}

}
