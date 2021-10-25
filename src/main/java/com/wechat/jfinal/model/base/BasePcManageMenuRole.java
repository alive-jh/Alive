package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePcManageMenuRole<M extends BasePcManageMenuRole<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setRoleid(java.lang.Integer roleid) {
		set("roleid", roleid);
		return (M)this;
	}

	public java.lang.Integer getRoleid() {
		return getInt("roleid");
	}

	public M setMenuid(java.lang.Integer menuid) {
		set("menuid", menuid);
		return (M)this;
	}

	public java.lang.Integer getMenuid() {
		return getInt("menuid");
	}

}
