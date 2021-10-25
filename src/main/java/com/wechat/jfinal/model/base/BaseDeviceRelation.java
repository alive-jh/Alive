package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceRelation<M extends BaseDeviceRelation<M>> extends Model<M> implements IBean {

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

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setFriendId(java.lang.String friendId) {
		set("friend_id", friendId);
		return (M)this;
	}

	public java.lang.String getFriendId() {
		return getStr("friend_id");
	}

	public M setFriendName(java.lang.String friendName) {
		set("friend_name", friendName);
		return (M)this;
	}

	public java.lang.String getFriendName() {
		return getStr("friend_name");
	}

	public M setRole(java.lang.String role) {
		set("role", role);
		return (M)this;
	}

	public java.lang.String getRole() {
		return getStr("role");
	}

	public M setIsbind(java.lang.Integer isbind) {
		set("isbind", isbind);
		return (M)this;
	}

	public java.lang.Integer getIsbind() {
		return getInt("isbind");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
