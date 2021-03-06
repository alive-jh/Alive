package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCommonDict<M extends BaseCommonDict<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setText(java.lang.String text) {
		set("text", text);
		return (M)this;
	}

	public java.lang.String getText() {
		return getStr("text");
	}

	public M setPid(java.lang.Integer pid) {
		set("pid", pid);
		return (M)this;
	}

	public java.lang.Integer getPid() {
		return getInt("pid");
	}

	public M setCode(java.lang.Integer code) {
		set("code", code);
		return (M)this;
	}

	public java.lang.Integer getCode() {
		return getInt("code");
	}

}
