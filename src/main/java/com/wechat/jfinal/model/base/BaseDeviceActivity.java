package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceActivity<M extends BaseDeviceActivity<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDeviceNo(java.lang.String deviceNo) {
		set("deviceNo", deviceNo);
		return (M)this;
	}

	public java.lang.String getDeviceNo() {
		return getStr("deviceNo");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epalId", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epalId");
	}

	public M setActivityTime(java.lang.String activityTime) {
		set("activityTime", activityTime);
		return (M)this;
	}

	public java.lang.String getActivityTime() {
		return getStr("activityTime");
	}

}
