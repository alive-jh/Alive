package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGameintegar<M extends BaseGameintegar<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMemberid(java.lang.Integer memberid) {
		set("memberid", memberid);
		return (M)this;
	}

	public java.lang.Integer getMemberid() {
		return getInt("memberid");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setIntegar(java.lang.Integer integar) {
		set("integar", integar);
		return (M)this;
	}

	public java.lang.Integer getIntegar() {
		return getInt("integar");
	}

	public M setCreatedate(java.util.Date createdate) {
		set("createdate", createdate);
		return (M)this;
	}

	public java.util.Date getCreatedate() {
		return get("createdate");
	}

}