package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBulletinQuestionAnswer<M extends BaseBulletinQuestionAnswer<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setQuestionId(java.lang.Integer questionId) {
		set("question_id", questionId);
		return (M)this;
	}

	public java.lang.Integer getQuestionId() {
		return getInt("question_id");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("student_id", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("student_id");
	}

	public M setTeacherId(java.lang.Integer teacherId) {
		set("teacher_id", teacherId);
		return (M)this;
	}

	public java.lang.Integer getTeacherId() {
		return getInt("teacher_id");
	}

	public M setMemberId(java.lang.Integer memberId) {
		set("member_id", memberId);
		return (M)this;
	}

	public java.lang.Integer getMemberId() {
		return getInt("member_id");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setAnswerScript(java.lang.String answerScript) {
		set("answer_script", answerScript);
		return (M)this;
	}

	public java.lang.String getAnswerScript() {
		return getStr("answer_script");
	}

	public M setIsAdoption(java.lang.Integer isAdoption) {
		set("is_adoption", isAdoption);
		return (M)this;
	}

	public java.lang.Integer getIsAdoption() {
		return getInt("is_adoption");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
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
