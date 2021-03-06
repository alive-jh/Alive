package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGradesTemporaryClass<M extends BaseGradesTemporaryClass<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
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

	public M setClassCourseId(java.lang.Integer classCourseId) {
		set("class_course_id", classCourseId);
		return (M)this;
	}

	public java.lang.Integer getClassCourseId() {
		return getInt("class_course_id");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
