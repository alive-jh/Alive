package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCourseprojectSystem<M extends BaseCourseprojectSystem<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setLessonList(java.lang.String lessonList) {
		set("lessonList", lessonList);
		return (M)this;
	}

	public java.lang.String getLessonList() {
		return getStr("lessonList");
	}

	public M setPlanType(java.lang.String planType) {
		set("plan_type", planType);
		return (M)this;
	}

	public java.lang.String getPlanType() {
		return getStr("plan_type");
	}

	public M setProjectname(java.lang.String projectname) {
		set("projectname", projectname);
		return (M)this;
	}

	public java.lang.String getProjectname() {
		return getStr("projectname");
	}

	public M setCreatedate(java.util.Date createdate) {
		set("createdate", createdate);
		return (M)this;
	}

	public java.util.Date getCreatedate() {
		return get("createdate");
	}

	public M setSort(java.lang.Integer sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Integer getSort() {
		return getInt("sort");
	}

	public M setActive(java.lang.Integer active) {
		set("active", active);
		return (M)this;
	}

	public java.lang.Integer getActive() {
		return getInt("active");
	}

}
