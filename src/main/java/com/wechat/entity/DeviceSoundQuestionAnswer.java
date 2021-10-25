package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/*
 * 表名：device_sound_question_answer
 * 数据说明：机器人音频答案表
 * 补充说明：
 * 
 * */

@Entity
@Table(name = "device_sound_question_answer")
public class DeviceSoundQuestionAnswer {

	@Id
	@GeneratedValue(generator ="DeviceSoundQuestionAnswertableGenerator")       
    @GenericGenerator(name ="DeviceSoundQuestionAnswertableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 问题音频地址
	@Column(name = "sound_url")
	private String soundUrl;
	
	
	// 机器人账号
	@Column(name = "epal_id")
	private String epalId;
	
	// 状态
	@Column(name = "status")
	private Integer status;

	
	// 问题ID
	@Column(name = "question_id")
	private Integer questionId;
	
	
	// 创建时间
	@Column(name = "create_time")
	private Date createTime;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getSoundUrl(){
		return this.soundUrl;
	}
	public void setSoundUrl(String soundUrl){
		this.soundUrl =soundUrl;
	}

	public String getEpalId(){
		return this.epalId;
	}
	public void setEpalId(String epalId){
		this.epalId =epalId;
	}

	
	
	
	public Integer getQuestionId(){
		return this.questionId;
	}
	
	public void setQuestionId(Integer questionId){
		this.questionId =questionId;
	}
	
	public Integer getStatus(){
		return this.status;
	}
	
	public void setStatus(Integer status){
		this.status =status;
	}
	
	public Date getInsertDate(){
		return this.createTime;
	}
	public void setInsertDate(Date insertDate){
		this.createTime = insertDate;
	}
	

}
