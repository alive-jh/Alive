package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQyPunchActionRule<M extends BaseQyPunchActionRule<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setActionId(java.lang.Integer actionId) {
		set("action_id", actionId);
		return (M)this;
	}

	public java.lang.Integer getActionId() {
		return getInt("action_id");
	}

	public M setClassId(java.lang.Integer classId) {
		set("class_id", classId);
		return (M)this;
	}

	public java.lang.Integer getClassId() {
		return getInt("class_id");
	}

	public M setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
		return (M)this;
	}

	public java.util.Date getStartTime() {
		return get("start_time");
	}

	public M setEndTime(java.util.Date endTime) {
		set("end_time", endTime);
		return (M)this;
	}

	public java.util.Date getEndTime() {
		return get("end_time");
	}

}
