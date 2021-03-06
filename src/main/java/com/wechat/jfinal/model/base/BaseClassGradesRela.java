package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassGradesRela<M extends BaseClassGradesRela<M>> extends Model<M> implements IBean {

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

	public M setClassStudentId(java.lang.Integer classStudentId) {
		set("class_student_id", classStudentId);
		return (M)this;
	}

	public java.lang.Integer getClassStudentId() {
		return getInt("class_student_id");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setGradesStatus(java.lang.Integer gradesStatus) {
		set("gradesStatus", gradesStatus);
		return (M)this;
	}

	public java.lang.Integer getGradesStatus() {
		return getInt("gradesStatus");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
