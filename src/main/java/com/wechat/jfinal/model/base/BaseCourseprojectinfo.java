package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCourseprojectinfo<M extends BaseCourseprojectinfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setProjectid(java.lang.Integer projectid) {
		set("projectid", projectid);
		return (M)this;
	}

	public java.lang.Integer getProjectid() {
		return getInt("projectid");
	}

	public M setCourseid(java.lang.Integer courseid) {
		set("courseid", courseid);
		return (M)this;
	}

	public java.lang.Integer getCourseid() {
		return getInt("courseid");
	}

}