package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMiniappTemplateForm<M extends BaseMiniappTemplateForm<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMiniappOpenid(java.lang.String miniappOpenid) {
		set("miniapp_openid", miniappOpenid);
		return (M)this;
	}

	public java.lang.String getMiniappOpenid() {
		return getStr("miniapp_openid");
	}

	public M setFormId(java.lang.String formId) {
		set("form_id", formId);
		return (M)this;
	}

	public java.lang.String getFormId() {
		return getStr("form_id");
	}

}
