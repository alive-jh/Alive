package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassScriptDone<M extends BaseClassScriptDone<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassRoomId(java.lang.Integer classRoomId) {
		set("class_room_id", classRoomId);
		return (M)this;
	}

	public java.lang.Integer getClassRoomId() {
		return getInt("class_room_id");
	}

	public M setClassScriptTypeId(java.lang.Integer classScriptTypeId) {
		set("class_script_type_id", classScriptTypeId);
		return (M)this;
	}

	public java.lang.Integer getClassScriptTypeId() {
		return getInt("class_script_type_id");
	}

	public M setClassScriptContent(java.lang.String classScriptContent) {
		set("class_script_content", classScriptContent);
		return (M)this;
	}

	public java.lang.String getClassScriptContent() {
		return getStr("class_script_content");
	}

	public M setReply(java.lang.String reply) {
		set("reply", reply);
		return (M)this;
	}

	public java.lang.String getReply() {
		return getStr("reply");
	}

	public M setFeedback(java.lang.String feedback) {
		set("feedback", feedback);
		return (M)this;
	}

	public java.lang.String getFeedback() {
		return getStr("feedback");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setSort(java.lang.Integer sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Integer getSort() {
		return getInt("sort");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
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

}
