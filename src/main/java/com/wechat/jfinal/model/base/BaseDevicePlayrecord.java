package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDevicePlayrecord<M extends BaseDevicePlayrecord<M>> extends Model<M> implements IBean {

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

	public M setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
		return (M)this;
	}

	public java.util.Date getStartTime() {
		return get("start_time");
	}

	public M setFrom(java.lang.String from) {
		set("from", from);
		return (M)this;
	}

	public java.lang.String getFrom() {
		return getStr("from");
	}

	public M setLastid(java.lang.String lastid) {
		set("lastid", lastid);
		return (M)this;
	}

	public java.lang.String getLastid() {
		return getStr("lastid");
	}

	public M setNextid(java.lang.String nextid) {
		set("nextid", nextid);
		return (M)this;
	}

	public java.lang.String getNextid() {
		return getStr("nextid");
	}

}