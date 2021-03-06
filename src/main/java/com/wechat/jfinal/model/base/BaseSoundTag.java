package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSoundTag<M extends BaseSoundTag<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTagName(java.lang.String tagName) {
		set("tagName", tagName);
		return (M)this;
	}

	public java.lang.String getTagName() {
		return getStr("tagName");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setSortId(java.lang.Integer sortId) {
		set("sortId", sortId);
		return (M)this;
	}

	public java.lang.Integer getSortId() {
		return getInt("sortId");
	}

	public M setChannelId(java.lang.Integer channelId) {
		set("channelId", channelId);
		return (M)this;
	}

	public java.lang.Integer getChannelId() {
		return getInt("channelId");
	}

	public M setImage(java.lang.String image) {
		set("image", image);
		return (M)this;
	}

	public java.lang.String getImage() {
		return getStr("image");
	}

	public M setIntro(java.lang.String intro) {
		set("intro", intro);
		return (M)this;
	}

	public java.lang.String getIntro() {
		return getStr("intro");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("groupId", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("groupId");
	}

}
