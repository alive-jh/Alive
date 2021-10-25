package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceOnlineRecordCount<M extends BaseDeviceOnlineRecordCount<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDeviceCount(java.lang.Integer deviceCount) {
		set("device_count", deviceCount);
		return (M)this;
	}

	public java.lang.Integer getDeviceCount() {
		return getInt("device_count");
	}

	public M setOnlineCount(java.lang.Integer onlineCount) {
		set("online_count", onlineCount);
		return (M)this;
	}

	public java.lang.Integer getOnlineCount() {
		return getInt("online_count");
	}

	public M setInsertDate(java.lang.String insertDate) {
		set("insert_date", insertDate);
		return (M)this;
	}

	public java.lang.String getInsertDate() {
		return getStr("insert_date");
	}

}