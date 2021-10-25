package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：library
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "class_course_comment")
public class ClassCourseComment {

	
	private Integer id;
	
	// 课程表ID
	
	private Integer classCourseId;
	
	// 老师录音音频
	
	private String teacherSound;
	
	// 老师评分
	
	private String teacherScore;
	
	// 学生ID
	
	private Integer studentId;
	
	
	private Integer commentTeacherId;
	
	private Integer status;
	
	
	private String insertDate;
	
	private String updateDate;
	
	
	
	private String effectiveDate;
	
	
	@Id
	@GeneratedValue(generator ="ClassCourseCommenttableGenerator")       
    @GenericGenerator(name ="ClassCourseCommenttableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	@Column(name = "class_course_id")
	public Integer getClassCourseId(){
		return this.classCourseId;
	}
	public void setClassCourseId(Integer classCourseId){
		this.classCourseId = classCourseId;
	}
	
	@Column(name = "teacher_sound")
	public String getTeacherSound(){
		return this.teacherSound;
	}
	public void setTeacherSound(String teacherSound){
		this.teacherSound = teacherSound;
	}
	
	@Column(name = "teacher_score")
	public String getTeacherScore(){
		return this.teacherScore;
	}
	public void setTeacherScore(String teacherScore){
		this.teacherScore = teacherScore;
	}
	
	@Column(name = "student_id")
	public Integer getStudentId(){
		return this.studentId;
	}
	public void setStudentId(Integer studentId){
		this.studentId = studentId;
	}

	@Column(name = "effective_date")
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@Column(name = "comment_teacher_id")
	public Integer getCommentTeacherId() {
		return commentTeacherId;
	}
	public void setCommentTeacherId(Integer commentTeacherId) {
		this.commentTeacherId = commentTeacherId;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "insert_date" , insertable =false, updatable = false)
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	
	@Column(name = "update_date", insertable =false, updatable = false)
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
