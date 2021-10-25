package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceSoundPlayHistory<M extends BaseDeviceSoundPlayHistory<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setSoundId(java.lang.Integer soundId) {
		set("sound_id", soundId);
		return (M)this;
	}

	public java.lang.Integer getSoundId() {
		return getInt("sound_id");
	}

	public M setInsertDate(java.util.Date insertDate) {
		set("insert_date", insertDate);
		return (M)this;
	}

	public java.util.Date getInsertDate() {
		return get("insert_date");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setPlayType(java.lang.String playType) {
		set("playType", playType);
		return (M)this;
	}

	public java.lang.String getPlayType() {
		return getStr("playType");
	}

}
