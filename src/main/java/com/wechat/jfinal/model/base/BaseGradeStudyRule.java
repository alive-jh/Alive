package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGradeStudyRule<M extends BaseGradeStudyRule<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGradeId(java.lang.Integer gradeId) {
		set("grade_id", gradeId);
		return (M)this;
	}

	public java.lang.Integer getGradeId() {
		return getInt("grade_id");
	}

	public M setRule(java.lang.String rule) {
		set("rule", rule);
		return (M)this;
	}

	public java.lang.String getRule() {
		return getStr("rule");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
