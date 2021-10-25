package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceShipmentRecord<M extends BaseDeviceShipmentRecord<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDeviceNo(java.lang.String deviceNo) {
		set("device_no", deviceNo);
		return (M)this;
	}

	public java.lang.String getDeviceNo() {
		return getStr("device_no");
	}

	public M setAgentId(java.lang.Integer agentId) {
		set("agent_id", agentId);
		return (M)this;
	}

	public java.lang.Integer getAgentId() {
		return getInt("agent_id");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	public M setInsertTime(java.lang.String insertTime) {
		set("insert_time", insertTime);
		return (M)this;
	}

	public java.lang.String getInsertTime() {
		return getStr("insert_time");
	}

}
