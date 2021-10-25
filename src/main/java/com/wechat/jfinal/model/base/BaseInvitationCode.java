package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseInvitationCode<M extends BaseInvitationCode<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassRoomId(java.lang.Integer classRoomId) {
		set("class_room_id", classRoomId);
		return (M)this;
	}

	public java.lang.Integer getClassRoomId() {
		return getInt("class_room_id");
	}

	public M setProductId(java.lang.Integer productId) {
		set("product_id", productId);
		return (M)this;
	}

	public java.lang.Integer getProductId() {
		return getInt("product_id");
	}

	public M setRandomCode(java.lang.String randomCode) {
		set("random_code", randomCode);
		return (M)this;
	}

	public java.lang.String getRandomCode() {
		return getStr("random_code");
	}

	public M setInvalidTime(java.util.Date invalidTime) {
		set("invalid_time", invalidTime);
		return (M)this;
	}

	public java.util.Date getInvalidTime() {
		return get("invalid_time");
	}

	public M setMemberId(java.lang.Integer memberId) {
		set("member_id", memberId);
		return (M)this;
	}

	public java.lang.Integer getMemberId() {
		return getInt("member_id");
	}

	public M setMobile(java.lang.String mobile) {
		set("mobile", mobile);
		return (M)this;
	}

	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public M setRecommendMemberId(java.lang.Integer recommendMemberId) {
		set("recommend_member_id", recommendMemberId);
		return (M)this;
	}

	public java.lang.Integer getRecommendMemberId() {
		return getInt("recommend_member_id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setFirstSendTime(java.util.Date firstSendTime) {
		set("first_send_time", firstSendTime);
		return (M)this;
	}

	public java.util.Date getFirstSendTime() {
		return get("first_send_time");
	}

}