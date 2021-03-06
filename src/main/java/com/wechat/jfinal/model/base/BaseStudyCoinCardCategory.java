package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseStudyCoinCardCategory<M extends BaseStudyCoinCardCategory<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setObject(java.lang.String object) {
		set("object", object);
		return (M)this;
	}

	public java.lang.String getObject() {
		return getStr("object");
	}

	public M setPrefix(java.lang.String prefix) {
		set("prefix", prefix);
		return (M)this;
	}

	public java.lang.String getPrefix() {
		return getStr("prefix");
	}

	public M setCoinCount(java.lang.Integer coinCount) {
		set("coin_count", coinCount);
		return (M)this;
	}

	public java.lang.Integer getCoinCount() {
		return getInt("coin_count");
	}

	public M setCount(java.lang.Integer count) {
		set("count", count);
		return (M)this;
	}

	public java.lang.Integer getCount() {
		return getInt("count");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setExpireTime(java.util.Date expireTime) {
		set("expire_time", expireTime);
		return (M)this;
	}

	public java.util.Date getExpireTime() {
		return get("expire_time");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
