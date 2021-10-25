package com.wechat.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "train_question_item",catalog="wechat")
public class TrainQuestionItem {

	@Id
	@GeneratedValue(generator ="train_question_itemtableGenerator")       
    @GenericGenerator(name ="train_question_itemtableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	Integer id;
	@Column(name = "text")
	String text;
	
	
	public TrainQuestionItem(){}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	@Override
	public String toString() {
		return "TrainQuestionItem [id=" + id + ", text=" + text + "]";
	}
	
	
	
	
}
