package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCourseWordLabel<M extends BaseCourseWordLabel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setLabelEn(java.lang.String labelEn) {
		set("label_en", labelEn);
		return (M)this;
	}

	public java.lang.String getLabelEn() {
		return getStr("label_en");
	}

	public M setLabelCn(java.lang.String labelCn) {
		set("label_cn", labelCn);
		return (M)this;
	}

	public java.lang.String getLabelCn() {
		return getStr("label_cn");
	}

	public M setImage(java.lang.String image) {
		set("image", image);
		return (M)this;
	}

	public java.lang.String getImage() {
		return getStr("image");
	}

	public M setGroupName(java.lang.String groupName) {
		set("group_name", groupName);
		return (M)this;
	}

	public java.lang.String getGroupName() {
		return getStr("group_name");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setSound(java.lang.String sound) {
		set("sound", sound);
		return (M)this;
	}

	public java.lang.String getSound() {
		return getStr("sound");
	}

}