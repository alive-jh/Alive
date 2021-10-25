package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassRoomOrder<M extends BaseClassRoomOrder<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassPackId(java.lang.Integer classPackId) {
		set("class_pack_id", classPackId);
		return (M)this;
	}

	public java.lang.Integer getClassPackId() {
		return getInt("class_pack_id");
	}

	public M setClassRoomId(java.lang.Integer classRoomId) {
		set("class_room_id", classRoomId);
		return (M)this;
	}

	public java.lang.Integer getClassRoomId() {
		return getInt("class_room_id");
	}

	public M setMemberId(java.lang.Integer memberId) {
		set("member_id", memberId);
		return (M)this;
	}

	public java.lang.Integer getMemberId() {
		return getInt("member_id");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("student_id", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("student_id");
	}

	public M setOrderNumber(java.lang.String orderNumber) {
		set("order_number", orderNumber);
		return (M)this;
	}

	public java.lang.String getOrderNumber() {
		return getStr("order_number");
	}

	public M setTotalPrice(java.lang.Double totalPrice) {
		set("total_price", totalPrice);
		return (M)this;
	}

	public java.lang.Double getTotalPrice() {
		return getDouble("total_price");
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
