package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceProperty<M extends BaseDeviceProperty<M>> extends Model<M> implements IBean {

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

	public M setPropertyKey(java.lang.String propertyKey) {
		set("property_key", propertyKey);
		return (M)this;
	}

	public java.lang.String getPropertyKey() {
		return getStr("property_key");
	}

	public M setPropertyValue(java.lang.String propertyValue) {
		set("property_value", propertyValue);
		return (M)this;
	}

	public java.lang.String getPropertyValue() {
		return getStr("property_value");
	}

}
