package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseApiLog<M extends BaseApiLog<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setApiUrl(java.lang.String apiUrl) {
		set("api_url", apiUrl);
		return (M)this;
	}

	public java.lang.String getApiUrl() {
		return getStr("api_url");
	}

	public M setAccessTime(java.util.Date accessTime) {
		set("access_time", accessTime);
		return (M)this;
	}

	public java.util.Date getAccessTime() {
		return get("access_time");
	}

}
