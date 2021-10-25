package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：curriculumaudio
 * 数据说明：课程和备课资源对应关系表
 * 补充说明：
 * 
 * */

@Entity
@Table(name = "curriculumaudio")
public class CurriculumAudio {

	@Id
	@GeneratedValue(generator ="curriculumaudiotableGenerator")       
    @GenericGenerator(name ="curriculumaudiotableGenerator", strategy ="identity")
	
	@Column(name = "id",length = 32)
	private Integer id;
	
	//课程ID
	@Column(name = "curriculumid")
	private Integer curriculumId;
	
	
	// 备课资源ID
	@Column(name = "audioid")
	private Integer audioId;
	

	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}

	public int getCurriculumId(){
		return this.curriculumId;
	}
	public void setCurriculumId(int curriculumId){
		this.curriculumId = curriculumId;
	}
	public int getAudioId(){
		return this.audioId;
	}
	public void setAudioId(int audioId){
		this.audioId = audioId;
	}

}
