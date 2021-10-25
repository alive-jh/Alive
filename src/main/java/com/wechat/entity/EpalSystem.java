package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "epalsystem")
public class EpalSystem {

	@Id
	@GeneratedValue(generator ="courseGenerator")       
    @GenericGenerator(name ="courseGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "epalid")
	private String epalId;
	
	@Column(name = "recommend")
	private Integer recommend = 0;//自动推荐
	
	@Column(name = "schedule")
	private Integer schedule = 0;//日程安排
	
	@Column(name = "testing")
	private Integer testing = 0;//儿童监测
	
	@Column(name = "distinguish")
	private Integer distinguish = 0;//自动识别

	@Column(name = "chat")
	private Integer chat = 0;//自动识别
	
	@Column(name = "intelligent_score")
	private Integer intelligentScore;
	
	public Integer getIntelligentScore() {
		return intelligentScore;
	}

	public void setIntelligentScore(Integer intelligentScore) {
		this.intelligentScore = intelligentScore;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getSchedule() {
		return schedule;
	}

	public void setSchedule(Integer schedule) {
		this.schedule = schedule;
	}

	public Integer getTesting() {
		return testing;
	}

	public void setTesting(Integer testing) {
		this.testing = testing;
	}

	public Integer getDistinguish() {
		return distinguish;
	}

	public void setDistinguish(Integer distinguish) {
		this.distinguish = distinguish;
	}

	public Integer getChat() {
		return chat;
	}

	public void setChat(Integer chat) {
		this.chat = chat;
	}
	
}


