package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassCourseSchedule<M extends BaseClassCourseSchedule<M>> extends Model<M> implements IBean {

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

	public M setClassGradesId(java.lang.Integer classGradesId) {
		set("class_grades_id", classGradesId);
		return (M)this;
	}

	public java.lang.Integer getClassGradesId() {
		return getInt("class_grades_id");
	}

	public M setClassRoomId(java.lang.Integer classRoomId) {
		set("class_room_id", classRoomId);
		return (M)this;
	}

	public java.lang.Integer getClassRoomId() {
		return getInt("class_room_id");
	}

	public M setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
		return (M)this;
	}

	public java.util.Date getStartTime() {
		return get("start_time");
	}

	public M setLastTime(java.util.Date lastTime) {
		set("last_time", lastTime);
		return (M)this;
	}

	public java.util.Date getLastTime() {
		return get("last_time");
	}

	public M setDoDay(java.lang.Integer doDay) {
		set("do_day", doDay);
		return (M)this;
	}

	public java.lang.Integer getDoDay() {
		return getInt("do_day");
	}

	public M setSort(java.lang.Integer sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Integer getSort() {
		return getInt("sort");
	}

}