package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassScriptGroupToType<M extends BaseClassScriptGroupToType<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("group_id", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("group_id");
	}

	public M setTypeId(java.lang.Integer typeId) {
		set("type_id", typeId);
		return (M)this;
	}

	public java.lang.Integer getTypeId() {
		return getInt("type_id");
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
