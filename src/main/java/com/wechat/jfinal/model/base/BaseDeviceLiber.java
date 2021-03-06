package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceLiber<M extends BaseDeviceLiber<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUserId(java.lang.String userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.String getUserId() {
		return getStr("user_id");
	}

	public M setFileName(java.lang.String fileName) {
		set("file_name", fileName);
		return (M)this;
	}

	public java.lang.String getFileName() {
		return getStr("file_name");
	}

	public M setImageUrl(java.lang.String imageUrl) {
		set("image_url", imageUrl);
		return (M)this;
	}

	public java.lang.String getImageUrl() {
		return getStr("image_url");
	}

	public M setType(java.lang.String type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.String getType() {
		return getStr("type");
	}

	public M setMusicUrl(java.lang.String musicUrl) {
		set("music_url", musicUrl);
		return (M)this;
	}

	public java.lang.String getMusicUrl() {
		return getStr("music_url");
	}

	public M setCreateDate(java.lang.String createDate) {
		set("create_date", createDate);
		return (M)this;
	}

	public java.lang.String getCreateDate() {
		return getStr("create_date");
	}

}
