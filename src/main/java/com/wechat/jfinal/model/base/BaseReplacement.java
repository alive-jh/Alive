package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseReplacement<M extends BaseReplacement<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setLastEpalId(java.lang.String lastEpalId) {
		set("last_epal_id", lastEpalId);
		return (M)this;
	}

	public java.lang.String getLastEpalId() {
		return getStr("last_epal_id");
	}

	public M setCurrentEpalId(java.lang.String currentEpalId) {
		set("current_epal_id", currentEpalId);
		return (M)this;
	}

	public java.lang.String getCurrentEpalId() {
		return getStr("current_epal_id");
	}

	public M setCreateTime(java.lang.String createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.lang.String getCreateTime() {
		return getStr("create_time");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
