package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseXmlyChannel<M extends BaseXmlyChannel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setFans(java.lang.Integer fans) {
		set("fans", fans);
		return (M)this;
	}

	public java.lang.Integer getFans() {
		return getInt("fans");
	}

	public M setIntro(java.lang.String intro) {
		set("intro", intro);
		return (M)this;
	}

	public java.lang.String getIntro() {
		return getStr("intro");
	}

	public M setImage(java.lang.String image) {
		set("image", image);
		return (M)this;
	}

	public java.lang.String getImage() {
		return getStr("image");
	}

	public M setChannelId(java.lang.Integer channelId) {
		set("channel_id", channelId);
		return (M)this;
	}

	public java.lang.Integer getChannelId() {
		return getInt("channel_id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setLevel(java.lang.Integer level) {
		set("level", level);
		return (M)this;
	}

	public java.lang.Integer getLevel() {
		return getInt("level");
	}

	public M setLastUpdateTime(java.lang.String lastUpdateTime) {
		set("lastUpdateTime", lastUpdateTime);
		return (M)this;
	}

	public java.lang.String getLastUpdateTime() {
		return getStr("lastUpdateTime");
	}

	public M setNextUpdateTime(java.lang.String nextUpdateTime) {
		set("nextUpdateTime", nextUpdateTime);
		return (M)this;
	}

	public java.lang.String getNextUpdateTime() {
		return getStr("nextUpdateTime");
	}

	public M setUpdateCycle(java.lang.Integer updateCycle) {
		set("updateCycle", updateCycle);
		return (M)this;
	}

	public java.lang.Integer getUpdateCycle() {
		return getInt("updateCycle");
	}

}
