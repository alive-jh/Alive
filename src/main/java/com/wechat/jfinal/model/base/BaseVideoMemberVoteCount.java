package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseVideoMemberVoteCount<M extends BaseVideoMemberVoteCount<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMemberId(java.lang.Integer memberId) {
		set("member_id", memberId);
		return (M)this;
	}

	public java.lang.Integer getMemberId() {
		return getInt("member_id");
	}

	public M setCount(java.lang.Integer count) {
		set("count", count);
		return (M)this;
	}

	public java.lang.Integer getCount() {
		return getInt("count");
	}

	public M setDay(java.lang.Integer day) {
		set("day", day);
		return (M)this;
	}

	public java.lang.Integer getDay() {
		return getInt("day");
	}

}
