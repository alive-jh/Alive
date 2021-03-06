package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassCourseErrorWord<M extends BaseClassCourseErrorWord<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("student_id", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("student_id");
	}

	public M setClassCourseRecordId(java.lang.Integer classCourseRecordId) {
		set("class_course_record_id", classCourseRecordId);
		return (M)this;
	}

	public java.lang.Integer getClassCourseRecordId() {
		return getInt("class_course_record_id");
	}

	public M setWord(java.lang.String word) {
		set("word", word);
		return (M)this;
	}

	public java.lang.String getWord() {
		return getStr("word");
	}

	public M setScore(java.lang.Integer score) {
		set("score", score);
		return (M)this;
	}

	public java.lang.Integer getScore() {
		return getInt("score");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
