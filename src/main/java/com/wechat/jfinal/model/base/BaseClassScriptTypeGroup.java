package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassScriptTypeGroup<M extends BaseClassScriptTypeGroup<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGroupName(java.lang.String groupName) {
		set("group_name", groupName);
		return (M)this;
	}

	public java.lang.String getGroupName() {
		return getStr("group_name");
	}

	public M setGroupCode(java.lang.String groupCode) {
		set("group_code", groupCode);
		return (M)this;
	}

	public java.lang.String getGroupCode() {
		return getStr("group_code");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setSortId(java.lang.Integer sortId) {
		set("sort_id", sortId);
		return (M)this;
	}

	public java.lang.Integer getSortId() {
		return getInt("sort_id");
	}

	public M setInsertTime(java.util.Date insertTime) {
		set("insert_time", insertTime);
		return (M)this;
	}

	public java.util.Date getInsertTime() {
		return get("insert_time");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
