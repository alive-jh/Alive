package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseEovaUserInfo<M extends BaseEovaUserInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setRid(java.lang.Integer rid) {
		set("rid", rid);
		return (M)this;
	}

	public java.lang.Integer getRid() {
		return getInt("rid");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setNickname(java.lang.String nickname) {
		set("nickname", nickname);
		return (M)this;
	}

	public java.lang.String getNickname() {
		return getStr("nickname");
	}

	public M setMobile(java.lang.String mobile) {
		set("mobile", mobile);
		return (M)this;
	}

	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public M setProvince(java.lang.Integer province) {
		set("province", province);
		return (M)this;
	}

	public java.lang.Integer getProvince() {
		return getInt("province");
	}

	public M setCity(java.lang.Integer city) {
		set("city", city);
		return (M)this;
	}

	public java.lang.Integer getCity() {
		return getInt("city");
	}

	public M setRegion(java.lang.Integer region) {
		set("region", region);
		return (M)this;
	}

	public java.lang.Integer getRegion() {
		return getInt("region");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}

}