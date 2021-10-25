package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassScriptType<M extends BaseClassScriptType<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setGroupName(java.lang.String groupName) {
		set("groupName", groupName);
		return (M)this;
	}

	public java.lang.String getGroupName() {
		return getStr("groupName");
	}

	public M setSort(java.lang.Integer sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Integer getSort() {
		return getInt("sort");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("groupId", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("groupId");
	}

	public M setDes(java.lang.String des) {
		set("des", des);
		return (M)this;
	}

	public java.lang.String getDes() {
		return getStr("des");
	}

}