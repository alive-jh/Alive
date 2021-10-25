package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOnlineClassComment<M extends BaseOnlineClassComment<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTeacherId(java.lang.Integer teacherId) {
		set("teacherId", teacherId);
		return (M)this;
	}

	public java.lang.Integer getTeacherId() {
		return getInt("teacherId");
	}

	public M setModelId(java.lang.Integer modelId) {
		set("modelId", modelId);
		return (M)this;
	}

	public java.lang.Integer getModelId() {
		return getInt("modelId");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setScore(java.lang.Integer score) {
		set("score", score);
		return (M)this;
	}

	public java.lang.Integer getScore() {
		return getInt("score");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setStudentIds(java.lang.String studentIds) {
		set("studentIds", studentIds);
		return (M)this;
	}

	public java.lang.String getStudentIds() {
		return getStr("studentIds");
	}

	public M setCommentSound(java.lang.String commentSound) {
		set("commentSound", commentSound);
		return (M)this;
	}

	public java.lang.String getCommentSound() {
		return getStr("commentSound");
	}

	public M setGradesId(java.lang.Integer gradesId) {
		set("gradesId", gradesId);
		return (M)this;
	}

	public java.lang.Integer getGradesId() {
		return getInt("gradesId");
	}

	public M setStartDate(java.lang.String startDate) {
		set("startDate", startDate);
		return (M)this;
	}

	public java.lang.String getStartDate() {
		return getStr("startDate");
	}

	public M setEndDate(java.lang.String endDate) {
		set("endDate", endDate);
		return (M)this;
	}

	public java.lang.String getEndDate() {
		return getStr("endDate");
	}

	public M setInsertDate(java.lang.String insertDate) {
		set("insertDate", insertDate);
		return (M)this;
	}

	public java.lang.String getInsertDate() {
		return getStr("insertDate");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}