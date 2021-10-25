package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCourseScheduleNow<M extends BaseCourseScheduleNow<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setProjectId(java.lang.Integer projectId) {
		set("project_id", projectId);
		return (M)this;
	}

	public java.lang.Integer getProjectId() {
		return getInt("project_id");
	}

	public M setCourseid(java.lang.Integer courseid) {
		set("courseid", courseid);
		return (M)this;
	}

	public java.lang.Integer getCourseid() {
		return getInt("courseid");
	}

	public M setProductid(java.lang.Integer productid) {
		set("productid", productid);
		return (M)this;
	}

	public java.lang.Integer getProductid() {
		return getInt("productid");
	}

	public M setSchedule(java.lang.String schedule) {
		set("schedule", schedule);
		return (M)this;
	}

	public java.lang.String getSchedule() {
		return getStr("schedule");
	}

	public M setCurClass(java.lang.Integer curClass) {
		set("cur_class", curClass);
		return (M)this;
	}

	public java.lang.Integer getCurClass() {
		return getInt("cur_class");
	}

	public M setCusFile(java.lang.String cusFile) {
		set("cus_file", cusFile);
		return (M)this;
	}

	public java.lang.String getCusFile() {
		return getStr("cus_file");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
