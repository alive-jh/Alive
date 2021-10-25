package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseVideoAccess<M extends BaseVideoAccess<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setStuId(java.lang.Integer stuId) {
		set("stu_id", stuId);
		return (M)this;
	}

	public java.lang.Integer getStuId() {
		return getInt("stu_id");
	}

	public M setWeek(java.lang.Integer week) {
		set("week", week);
		return (M)this;
	}

	public java.lang.Integer getWeek() {
		return getInt("week");
	}

	public M setAccessNum(java.lang.Integer accessNum) {
		set("access_num", accessNum);
		return (M)this;
	}

	public java.lang.Integer getAccessNum() {
		return getInt("access_num");
	}

	public M setEditTime(java.util.Date editTime) {
		set("edit_time", editTime);
		return (M)this;
	}

	public java.util.Date getEditTime() {
		return get("edit_time");
	}

	public M setCreatTime(java.util.Date creatTime) {
		set("creat_time", creatTime);
		return (M)this;
	}

	public java.util.Date getCreatTime() {
		return get("creat_time");
	}

}
