package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCoinGift<M extends BaseCoinGift<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setDesc(java.lang.String desc) {
		set("desc", desc);
		return (M)this;
	}

	public java.lang.String getDesc() {
		return getStr("desc");
	}

	public M setTimes(java.lang.Integer times) {
		set("times", times);
		return (M)this;
	}

	public java.lang.Integer getTimes() {
		return getInt("times");
	}

	public M setCoinCount(java.lang.Integer coinCount) {
		set("coin_count", coinCount);
		return (M)this;
	}

	public java.lang.Integer getCoinCount() {
		return getInt("coin_count");
	}

}
