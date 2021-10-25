package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassPackageOrder<M extends BaseClassPackageOrder<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOrderId(java.lang.Integer orderId) {
		set("order_id", orderId);
		return (M)this;
	}

	public java.lang.Integer getOrderId() {
		return getInt("order_id");
	}

	public M setOrderNumber(java.lang.String orderNumber) {
		set("order_number", orderNumber);
		return (M)this;
	}

	public java.lang.String getOrderNumber() {
		return getStr("order_number");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("student_id", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("student_id");
	}

	public M setClassPackId(java.lang.Integer classPackId) {
		set("class_pack_id", classPackId);
		return (M)this;
	}

	public java.lang.Integer getClassPackId() {
		return getInt("class_pack_id");
	}

	public M setClassGradeId(java.lang.Integer classGradeId) {
		set("class_grade_id", classGradeId);
		return (M)this;
	}

	public java.lang.Integer getClassGradeId() {
		return getInt("class_grade_id");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
