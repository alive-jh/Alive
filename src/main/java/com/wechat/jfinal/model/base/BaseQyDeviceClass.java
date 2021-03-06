package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQyDeviceClass<M extends BaseQyDeviceClass<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDeviceId(java.lang.String deviceId) {
		set("device_id", deviceId);
		return (M)this;
	}

	public java.lang.String getDeviceId() {
		return getStr("device_id");
	}

	public M setClassId(java.lang.Integer classId) {
		set("class_id", classId);
		return (M)this;
	}

	public java.lang.Integer getClassId() {
		return getInt("class_id");
	}

}
