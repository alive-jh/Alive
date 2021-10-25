package com.wechat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "train_knowledge",catalog="wechat")
public class TrainKnowledge {

	@Id
	@GeneratedValue(generator = "train_knowledgetableGenerator")
	@GenericGenerator(name = "train_knowledgetableGenerator", strategy = "identity")
	@Column(name = "id", length = 32)
	Integer id;
	
	@Column(name="status")
	Integer status;
	
	@Column(name="member_id")
	Integer memberId;
	
	@Column(name="epal_id")
	String epalId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	
	
	
	
}
