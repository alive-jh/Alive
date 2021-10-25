package com.wechat.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "train_answer_item",catalog="wechat")
public class TrainAnswerItem {

	@Id
	@GeneratedValue(generator = "train_answer_itemtableGenerator")
	@GenericGenerator(name = "train_answer_itemtableGenerator", strategy = "identity")
	@Column(name = "id", length = 32)
	Integer id;
	@Column(name = "text")
	String text;
	@Column(name = "type")
	Integer type;
	@Column(name = "content")
	String content;
	@Column(name = "priority")
	Integer priority = 2;
	@Column(name = "loop_count")
	Integer loop_count = 0;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getLoop_count() {
		return loop_count;
	}
	public void setLoop_count(Integer loop_count) {
		this.loop_count = loop_count;
	}
	
	
	
	
}
