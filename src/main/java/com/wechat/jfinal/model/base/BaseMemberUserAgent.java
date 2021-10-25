package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMemberUserAgent<M extends BaseMemberUserAgent<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer Id) {
		set("Id", Id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("Id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setMemberId(java.lang.Integer memberId) {
		set("member_id", memberId);
		return (M)this;
	}

	public java.lang.Integer getMemberId() {
		return getInt("member_id");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

}