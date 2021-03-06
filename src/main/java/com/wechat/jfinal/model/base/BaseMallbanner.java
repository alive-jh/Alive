package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMallbanner<M extends BaseMallbanner<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setBanner(java.lang.String banner) {
		set("banner", banner);
		return (M)this;
	}

	public java.lang.String getBanner() {
		return getStr("banner");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setUrl(java.lang.String url) {
		set("url", url);
		return (M)this;
	}

	public java.lang.String getUrl() {
		return getStr("url");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setUrltype(java.lang.Integer urltype) {
		set("urltype", urltype);
		return (M)this;
	}

	public java.lang.Integer getUrltype() {
		return getInt("urltype");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setBanner1(java.lang.String banner1) {
		set("banner1", banner1);
		return (M)this;
	}

	public java.lang.String getBanner1() {
		return getStr("banner1");
	}

}
