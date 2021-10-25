package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGradeCode<M extends BaseGradeCode<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCode(java.lang.String code) {
		set("code", code);
		return (M)this;
	}

	public java.lang.String getCode() {
		return getStr("code");
	}

	public M setGradeId(java.lang.Integer gradeId) {
		set("grade_id", gradeId);
		return (M)this;
	}

	public java.lang.Integer getGradeId() {
		return getInt("grade_id");
	}

}