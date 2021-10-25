package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseHistorySchedules<M extends BaseHistorySchedules<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setEvent(java.lang.String event) {
		set("event", event);
		return (M)this;
	}

	public java.lang.String getEvent() {
		return getStr("event");
	}

	public M setSid(java.lang.String sid) {
		set("sid", sid);
		return (M)this;
	}

	public java.lang.String getSid() {
		return getStr("sid");
	}

	public M setDoTime(java.lang.String doTime) {
		set("do_time", doTime);
		return (M)this;
	}

	public java.lang.String getDoTime() {
		return getStr("do_time");
	}

	public M setPicture(java.lang.String picture) {
		set("picture", picture);
		return (M)this;
	}

	public java.lang.String getPicture() {
		return getStr("picture");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setNote(java.lang.String note) {
		set("note", note);
		return (M)this;
	}

	public java.lang.String getNote() {
		return getStr("note");
	}

	public M setPeriod(java.lang.String period) {
		set("period", period);
		return (M)this;
	}

	public java.lang.String getPeriod() {
		return getStr("period");
	}

	public M setType(java.lang.String type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.String getType() {
		return getStr("type");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epalId", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epalId");
	}

	public M setScheduleId(java.lang.String scheduleId) {
		set("scheduleId", scheduleId);
		return (M)this;
	}

	public java.lang.String getScheduleId() {
		return getStr("scheduleId");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setExeTime(java.lang.Long exeTime) {
		set("exe_time", exeTime);
		return (M)this;
	}

	public java.lang.Long getExeTime() {
		return getLong("exe_time");
	}

	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}

	public java.lang.String getDescription() {
		return getStr("description");
	}

}