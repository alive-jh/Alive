package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRobotHourse<M extends BaseRobotHourse<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer Id) {
		set("Id", Id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("Id");
	}

	public M setStuEpalId(java.lang.String stuEpalId) {
		set("stu_epal_id", stuEpalId);
		return (M)this;
	}

	public java.lang.String getStuEpalId() {
		return getStr("stu_epal_id");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setHourseId(java.lang.Integer hourseId) {
		set("hourse_id", hourseId);
		return (M)this;
	}

	public java.lang.Integer getHourseId() {
		return getInt("hourse_id");
	}

	public M setMsg(java.lang.String msg) {
		set("msg", msg);
		return (M)this;
	}

	public java.lang.String getMsg() {
		return getStr("msg");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setColor(java.lang.String color) {
		set("color", color);
		return (M)this;
	}

	public java.lang.String getColor() {
		return getStr("color");
	}

	public M setRow(java.lang.Integer row) {
		set("row", row);
		return (M)this;
	}

	public java.lang.Integer getRow() {
		return getInt("row");
	}

	public M setCol(java.lang.Integer col) {
		set("col", col);
		return (M)this;
	}

	public java.lang.Integer getCol() {
		return getInt("col");
	}

}
