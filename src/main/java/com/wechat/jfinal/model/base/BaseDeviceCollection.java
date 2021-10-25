package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceCollection<M extends BaseDeviceCollection<M>> extends Model<M> implements IBean {

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

	public M setAudioId(java.lang.String audioId) {
		set("audio_id", audioId);
		return (M)this;
	}

	public java.lang.String getAudioId() {
		return getStr("audio_id");
	}

	public M setAudioName(java.lang.String audioName) {
		set("audio_name", audioName);
		return (M)this;
	}

	public java.lang.String getAudioName() {
		return getStr("audio_name");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setResFrom(java.lang.String resFrom) {
		set("res_from", resFrom);
		return (M)this;
	}

	public java.lang.String getResFrom() {
		return getStr("res_from");
	}

	public M setUrl(java.lang.String url) {
		set("url", url);
		return (M)this;
	}

	public java.lang.String getUrl() {
		return getStr("url");
	}

}