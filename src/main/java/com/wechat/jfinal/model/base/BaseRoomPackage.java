package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRoomPackage<M extends BaseRoomPackage<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setCreatTime(java.util.Date creatTime) {
		set("creat_time", creatTime);
		return (M)this;
	}

	public java.util.Date getCreatTime() {
		return get("creat_time");
	}

	public M setEditTime(java.util.Date editTime) {
		set("edit_time", editTime);
		return (M)this;
	}

	public java.util.Date getEditTime() {
		return get("edit_time");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setCover(java.lang.String cover) {
		set("cover", cover);
		return (M)this;
	}

	public java.lang.String getCover() {
		return getStr("cover");
	}

	public M setSummary(java.lang.String summary) {
		set("summary", summary);
		return (M)this;
	}

	public java.lang.String getSummary() {
		return getStr("summary");
	}

	public M setFlag(java.lang.Integer flag) {
		set("flag", flag);
		return (M)this;
	}

	public java.lang.Integer getFlag() {
		return getInt("flag");
	}

}