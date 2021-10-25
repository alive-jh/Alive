package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
 * 表名：library
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "question")
public class Question {

	@Id
	@GeneratedValue(generator ="questiontableGenerator")       
    @GenericGenerator(name ="questiontableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 问题
	@Column(name = "question")
	private String question;
	
	
	// 插入时间
	@Column(name = "question_type")
	private String questionType;
	
	// 插入时间
	@Column(name = "insert_date")
	private Date insertDate;
	

	@Transient
	private List<Answer> answer;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getQuestion(){
		return this.question;
	}
	public void setQuestion(String question){
		this.question =question;
	}

	public String getQuestionType(){
		return this.questionType;
	}
	public void setQuestionType(String questionType){
		this.questionType =questionType;
	}
	
	public Date getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDate(Date insertDate){
		this.insertDate = insertDate;
	}
	
	public List<Answer> getAnswer(){
		return this.answer;
	}
	
	public void setAnswer(List<Answer> answer){
		this.answer = answer;
	}
}
