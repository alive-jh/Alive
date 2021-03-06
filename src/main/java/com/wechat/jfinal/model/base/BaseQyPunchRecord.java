package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQyPunchRecord<M extends BaseQyPunchRecord<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCardId(java.lang.String cardId) {
		set("card_id", cardId);
		return (M)this;
	}

	public java.lang.String getCardId() {
		return getStr("card_id");
	}

	public M setDeviceId(java.lang.String deviceId) {
		set("device_id", deviceId);
		return (M)this;
	}

	public java.lang.String getDeviceId() {
		return getStr("device_id");
	}

	public M setPunchTime(java.util.Date punchTime) {
		set("punch_time", punchTime);
		return (M)this;
	}

	public java.util.Date getPunchTime() {
		return get("punch_time");
	}

	public M setPunchImage(java.lang.String punchImage) {
		set("punch_image", punchImage);
		return (M)this;
	}

	public java.lang.String getPunchImage() {
		return getStr("punch_image");
	}

	public M setSendStatus(java.lang.Integer sendStatus) {
		set("send_status", sendStatus);
		return (M)this;
	}

	public java.lang.Integer getSendStatus() {
		return getInt("send_status");
	}

	public M setAction(java.lang.String action) {
		set("action", action);
		return (M)this;
	}

	public java.lang.String getAction() {
		return getStr("action");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setTemperature(java.lang.Float temperature) {
		set("temperature", temperature);
		return (M)this;
	}

	public java.lang.Float getTemperature() {
		return getFloat("temperature");
	}

}
