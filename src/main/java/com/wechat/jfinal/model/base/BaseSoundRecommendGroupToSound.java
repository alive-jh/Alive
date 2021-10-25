package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSoundRecommendGroupToSound<M extends BaseSoundRecommendGroupToSound<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("groupId", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("groupId");
	}

	public M setSoundId(java.lang.Integer soundId) {
		set("soundId", soundId);
		return (M)this;
	}

	public java.lang.Integer getSoundId() {
		return getInt("soundId");
	}

	public M setAlbumId(java.lang.Integer albumId) {
		set("albumId", albumId);
		return (M)this;
	}

	public java.lang.Integer getAlbumId() {
		return getInt("albumId");
	}

	public M setSortId(java.lang.Integer sortId) {
		set("sortId", sortId);
		return (M)this;
	}

	public java.lang.Integer getSortId() {
		return getInt("sortId");
	}

}
