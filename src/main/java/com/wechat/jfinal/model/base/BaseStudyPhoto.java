package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseStudyPhoto<M extends BaseStudyPhoto<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCourseId(java.lang.Integer courseId) {
		set("course_id", courseId);
		return (M)this;
	}

	public java.lang.Integer getCourseId() {
		return getInt("course_id");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("student_id", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("student_id");
	}

	public M setPicUrl(java.lang.String picUrl) {
		set("pic_url", picUrl);
		return (M)this;
	}

	public java.lang.String getPicUrl() {
		return getStr("pic_url");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
