package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTeacherQuestion<M extends BaseTeacherQuestion<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTeacherId(java.lang.Integer teacherId) {
		set("teacher_id", teacherId);
		return (M)this;
	}

	public java.lang.Integer getTeacherId() {
		return getInt("teacher_id");
	}

	public M setQuestionId(java.lang.Integer questionId) {
		set("question_id", questionId);
		return (M)this;
	}

	public java.lang.Integer getQuestionId() {
		return getInt("question_id");
	}

	public M setBReply(java.lang.Integer bReply) {
		set("b_reply", bReply);
		return (M)this;
	}

	public java.lang.Integer getBReply() {
		return getInt("b_reply");
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