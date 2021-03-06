package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseKeyword<M extends BaseKeyword<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setKeyword(java.lang.String keyword) {
		set("keyword", keyword);
		return (M)this;
	}

	public java.lang.String getKeyword() {
		return getStr("keyword");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setContenttype(java.lang.Integer contenttype) {
		set("contenttype", contenttype);
		return (M)this;
	}

	public java.lang.Integer getContenttype() {
		return getInt("contenttype");
	}

	public M setMaterialid(java.lang.Integer materialid) {
		set("materialid", materialid);
		return (M)this;
	}

	public java.lang.Integer getMaterialid() {
		return getInt("materialid");
	}

	public M setAccountid(java.lang.Integer accountid) {
		set("accountid", accountid);
		return (M)this;
	}

	public java.lang.Integer getAccountid() {
		return getInt("accountid");
	}

	public M setMatchingrules(java.lang.Integer matchingrules) {
		set("matchingrules", matchingrules);
		return (M)this;
	}

	public java.lang.Integer getMatchingrules() {
		return getInt("matchingrules");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setCreatedate(java.util.Date createdate) {
		set("createdate", createdate);
		return (M)this;
	}

	public java.util.Date getCreatedate() {
		return get("createdate");
	}

}
