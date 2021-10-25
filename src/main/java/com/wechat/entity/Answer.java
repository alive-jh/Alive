package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/*
 * 表名：library
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "answer")
public class Answer {

	@Id
	@GeneratedValue(generator ="answertableGenerator")       
    @GenericGenerator(name ="answertableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 答案
	@Column(name = "answer")
	private String answer;
	
	// 插入时间
	@Column(name = "insert_date")
	private Date insertDate;
	
	// 对应问题ID
	@Column(name = "question_id")
	private int questionId;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getAnswer(){
		return this.answer;
	}
	public void setAnswer(String answer){
		this.answer = answer;
	}
	
	public Date getInsertDate(){
		return this.insertDate;
	}
	public void seInsertDate(Date insertDate){
		this.insertDate = insertDate;
	}
	
	public int getQuestionId(){
		return this.questionId;
	}
	public void setQuestionId(int questionId){
		this.questionId = questionId;
	}
	

}
