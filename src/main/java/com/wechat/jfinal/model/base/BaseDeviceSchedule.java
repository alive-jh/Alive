package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceSchedule<M extends BaseDeviceSchedule<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCreatTime(java.util.Date creatTime) {
		set("creat_time", creatTime);
		return (M)this;
	}

	public java.util.Date getCreatTime() {
		return get("creat_time");
	}

	public M setEditTime(java.util.Date editTime) {
		set("edit_time", editTime);
		return (M)this;
	}

	public java.util.Date getEditTime() {
		return get("edit_time");
	}

	public M setDeviceNo(java.lang.String deviceNo) {
		set("device_no", deviceNo);
		return (M)this;
	}

	public java.lang.String getDeviceNo() {
		return getStr("device_no");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setEventCn(java.lang.String eventCn) {
		set("event_cn", eventCn);
		return (M)this;
	}

	public java.lang.String getEventCn() {
		return getStr("event_cn");
	}

	public M setEvent(java.lang.String event) {
		set("event", event);
		return (M)this;
	}

	public java.lang.String getEvent() {
		return getStr("event");
	}

	public M setNote(java.lang.String note) {
		set("note", note);
		return (M)this;
	}

	public java.lang.String getNote() {
		return getStr("note");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return getStr("content");
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

	public M setDoTime(java.lang.String doTime) {
		set("do_time", doTime);
		return (M)this;
	}

	public java.lang.String getDoTime() {
		return getStr("do_time");
	}

	public M setState(java.lang.Integer state) {
		set("state", state);
		return (M)this;
	}

	public java.lang.Integer getState() {
		return getInt("state");
	}

	public M setIsDef(java.lang.Integer isDef) {
		set("is_def", isDef);
		return (M)this;
	}

	public java.lang.Integer getIsDef() {
		return getInt("is_def");
	}

	public M setCatalogFile(java.lang.String catalogFile) {
		set("catalog_file", catalogFile);
		return (M)this;
	}

	public java.lang.String getCatalogFile() {
		return getStr("catalog_file");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}

	public java.lang.String getDescription() {
		return getStr("description");
	}

	public M setSid(java.lang.String sid) {
		set("sid", sid);
		return (M)this;
	}

	public java.lang.String getSid() {
		return getStr("sid");
	}

	public M setGroupId(java.lang.Integer groupId) {
		set("group_id", groupId);
		return (M)this;
	}

	public java.lang.Integer getGroupId() {
		return getInt("group_id");
	}

}