package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassScriptTypeMutex<M extends BaseClassScriptTypeMutex<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTypeId1(java.lang.Integer typeId1) {
		set("type_id1", typeId1);
		return (M)this;
	}

	public java.lang.Integer getTypeId1() {
		return getInt("type_id1");
	}

	public M setTypeId2(java.lang.Integer typeId2) {
		set("type_id2", typeId2);
		return (M)this;
	}

	public java.lang.Integer getTypeId2() {
		return getInt("type_id2");
	}

}
