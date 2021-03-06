package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMallproduct<M extends BaseMallproduct<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setCreatedate(java.util.Date createdate) {
		set("createdate", createdate);
		return (M)this;
	}

	public java.util.Date getCreatedate() {
		return get("createdate");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setAccountid(java.lang.Integer accountid) {
		set("accountid", accountid);
		return (M)this;
	}

	public java.lang.Integer getAccountid() {
		return getInt("accountid");
	}

	public M setLogo1(java.lang.String logo1) {
		set("logo1", logo1);
		return (M)this;
	}

	public java.lang.String getLogo1() {
		return getStr("logo1");
	}

	public M setLogo2(java.lang.String logo2) {
		set("logo2", logo2);
		return (M)this;
	}

	public java.lang.String getLogo2() {
		return getStr("logo2");
	}

	public M setLogo3(java.lang.String logo3) {
		set("logo3", logo3);
		return (M)this;
	}

	public java.lang.String getLogo3() {
		return getStr("logo3");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setPrice(java.lang.String price) {
		set("price", price);
		return (M)this;
	}

	public java.lang.String getPrice() {
		return getStr("price");
	}

	public M setMp3(java.lang.String mp3) {
		set("mp3", mp3);
		return (M)this;
	}

	public java.lang.String getMp3() {
		return getStr("mp3");
	}

	public M setMp3type(java.lang.Integer mp3type) {
		set("mp3type", mp3type);
		return (M)this;
	}

	public java.lang.Integer getMp3type() {
		return getInt("mp3type");
	}

	public M setCatId(java.lang.Integer catId) {
		set("cat_id", catId);
		return (M)this;
	}

	public java.lang.Integer getCatId() {
		return getInt("cat_id");
	}

	public M setClassRoomPackage(java.lang.Integer classRoomPackage) {
		set("class_room_package", classRoomPackage);
		return (M)this;
	}

	public java.lang.Integer getClassRoomPackage() {
		return getInt("class_room_package");
	}

	public M setClassGradeId(java.lang.Integer classGradeId) {
		set("class_grade_id", classGradeId);
		return (M)this;
	}

	public java.lang.Integer getClassGradeId() {
		return getInt("class_grade_id");
	}

	public M setClassBooking(java.lang.Integer classBooking) {
		set("class_booking", classBooking);
		return (M)this;
	}

	public java.lang.Integer getClassBooking() {
		return getInt("class_booking");
	}

}
