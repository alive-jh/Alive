package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRoomRela<M extends BaseRoomRela<M>> extends Model<M> implements IBean {

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

	public M setMainId(java.lang.Integer mainId) {
		set("main_id", mainId);
		return (M)this;
	}

	public java.lang.Integer getMainId() {
		return getInt("main_id");
	}

	public M setSubId(java.lang.Integer subId) {
		set("sub_id", subId);
		return (M)this;
	}

	public java.lang.Integer getSubId() {
		return getInt("sub_id");
	}

}