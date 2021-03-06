package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQyPunchActionType<M extends BaseQyPunchActionType<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setCreatTime(java.util.Date creatTime) {
		set("creat_time", creatTime);
		return (M)this;
	}

	public java.util.Date getCreatTime() {
		return get("creat_time");
	}

	public M setEditTime(java.util.Date editTime) {
		set("edit_time", editTime);
		return (M)this;
	}

	public java.util.Date getEditTime() {
		return get("edit_time");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setMsg(java.lang.String msg) {
		set("msg", msg);
		return (M)this;
	}

	public java.lang.String getMsg() {
		return getStr("msg");
	}

	public M setGroup(java.lang.Integer group) {
		set("group", group);
		return (M)this;
	}

	public java.lang.Integer getGroup() {
		return getInt("group");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("group_id", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("group_id");
	}

	public M setSchoolId(java.lang.Integer schoolId) {
		set("school_id", schoolId);
		return (M)this;
	}

	public java.lang.Integer getSchoolId() {
		return getInt("school_id");
	}

}
