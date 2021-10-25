package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCollection<M extends BaseCollection<M>> extends Model<M> implements IBean {

	public M setMemId(java.lang.String memId) {
		set("mem_id", memId);
		return (M)this;
	}

	public java.lang.String getMemId() {
		return getStr("mem_id");
	}

	public M setCateId(java.lang.String cateId) {
		set("cate_id", cateId);
		return (M)this;
	}

	public java.lang.String getCateId() {
		return getStr("cate_id");
	}

}
