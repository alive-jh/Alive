package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGroupChat<M extends BaseGroupChat<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassGradeId(java.lang.Integer classGradeId) {
		set("class_grade_id", classGradeId);
		return (M)this;
	}

	public java.lang.Integer getClassGradeId() {
		return getInt("class_grade_id");
	}

	public M setEasemobGroupId(java.lang.String easemobGroupId) {
		set("easemob_group_id", easemobGroupId);
		return (M)this;
	}

	public java.lang.String getEasemobGroupId() {
		return getStr("easemob_group_id");
	}

	public M setTeacherId(java.lang.Integer teacherId) {
		set("teacher_id", teacherId);
		return (M)this;
	}

	public java.lang.Integer getTeacherId() {
		return getInt("teacher_id");
	}

}
