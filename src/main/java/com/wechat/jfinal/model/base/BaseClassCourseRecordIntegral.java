package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassCourseRecordIntegral<M extends BaseClassCourseRecordIntegral<M>> extends Model<M> implements IBean {

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

	public M setClassCourseId(java.lang.Integer classCourseId) {
		set("class_course_id", classCourseId);
		return (M)this;
	}

	public java.lang.Integer getClassCourseId() {
		return getInt("class_course_id");
	}

	public M setIntegral(java.lang.Integer integral) {
		set("integral", integral);
		return (M)this;
	}

	public java.lang.Integer getIntegral() {
		return getInt("integral");
	}

	public M setUsedTime(java.lang.Integer usedTime) {
		set("used_time", usedTime);
		return (M)this;
	}

	public java.lang.Integer getUsedTime() {
		return getInt("used_time");
	}

	public M setCareteTime(java.util.Date careteTime) {
		set("carete_time", careteTime);
		return (M)this;
	}

	public java.util.Date getCareteTime() {
		return get("carete_time");
	}

}