package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLessonScriptReplyCommont<M extends BaseLessonScriptReplyCommont<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("group_id", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("group_id");
	}

	public M setComment(java.lang.String comment) {
		set("comment", comment);
		return (M)this;
	}

	public java.lang.String getComment() {
		return getStr("comment");
	}

	public M setMemberId(java.lang.Integer memberId) {
		set("member_id", memberId);
		return (M)this;
	}

	public java.lang.Integer getMemberId() {
		return getInt("member_id");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}