package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：class_course_score_record
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：classCourseRecord_id对应 class_course_record主键
 * 
 * */

@Entity
@Table(name = "class_course_score_record")
public class ClassCourseScoreRecord {

	@Id
	@GeneratedValue(generator ="ClassCourseScoreRecordtableGenerator")       
    @GenericGenerator(name ="ClassCourseScoreRecordtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	
	// 课程记录ID
	@Column(name = "classCourseRecord_id")
	private Integer classCourseRecordId;
	
	
	// 评分(字符串，保留非整形评分)
	@Column(name = "score")
	private String score;
	
	// 音频地址
	@Column(name = "audioUrl")
	private String audioUrl;
	
	// 插入时间
	@Column(name = "insertDate")
	private String insertDate;
	
	// 上传时间
	@Column(name = "recordDate")
	private String recordDate;
	
	// 类型
	@Column(name = "type")
	private String type;	

	// 发音得分
	@Column(name = "accuracy")
	private String accuracy;
	
	// 完整度得分
	@Column(name = "integrity")
	private String integrity;
	
	// 流利度得分
	@Column(name = "fluency")
	private String fluency;
	
	// 花费时长
	@Column(name = "timeCost")
	private String timeCost;
	
	// 单词列表
	@Column(name = "wordList")
	private String wordList;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	
	public Integer getClassCourseRecordId(){
		return this.classCourseRecordId;
	}
	public void setClassCourseRecordId(Integer classCourseRecordId){
		this.classCourseRecordId = classCourseRecordId;
	}
	
	
	public String getScore(){
		return this.score;
	}
	public void setScore(String score){
		this.score = score;
	}
	
	
	public String getAudioUrl(){
		return this.audioUrl;
	}
	public void setAudioUrl(String audioUrl){
		this.audioUrl = audioUrl;
	}
	
	
	public String getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDate(String insertDate){
		this.insertDate = insertDate;
	}
	
	public String getRecordDate(){
		return this.recordDate;
	}
	public void seInRecordDate(String recordDate){
		this.recordDate = recordDate;
	}
	
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	
	public String getAccuracy(){
		return this.accuracy;
	}
	public void setAccuracy(String accuracy){
		this.accuracy = accuracy;
	}
	
	public String getIntegrity(){
		return this.integrity;
	}
	public void setIntegrity(String integrity){
		this.integrity = integrity;
	}
	
	public String getFluency(){
		return this.fluency;
	}
	public void setFluency(String fluency){
		this.fluency = fluency;
	}
	
	public String getTimeCost(){
		return this.timeCost;
	}
	public void setTimeCost(String timeCost){
		this.timeCost = timeCost;
	}

	
	public String getWordList(){
		return this.wordList;
	}
	public void setWordList(String wordList){
		this.wordList = wordList;
	}
	
}
