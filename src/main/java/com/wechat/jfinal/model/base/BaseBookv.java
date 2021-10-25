package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBookv<M extends BaseBookv<M>> extends Model<M> implements IBean {

	public M setBookVId(java.lang.String bookVId) {
		set("bookVId", bookVId);
		return (M)this;
	}

	public java.lang.String getBookVId() {
		return getStr("bookVId");
	}

	public M setBookVName(java.lang.String bookVName) {
		set("bookVName", bookVName);
		return (M)this;
	}

	public java.lang.String getBookVName() {
		return getStr("bookVName");
	}

	public M setBookId(java.lang.String bookId) {
		set("bookId", bookId);
		return (M)this;
	}

	public java.lang.String getBookId() {
		return getStr("bookId");
	}

	public M setBookType(java.lang.String bookType) {
		set("bookType", bookType);
		return (M)this;
	}

	public java.lang.String getBookType() {
		return getStr("bookType");
	}

	public M setAlbum(java.lang.String album) {
		set("album", album);
		return (M)this;
	}

	public java.lang.String getAlbum() {
		return getStr("album");
	}

	public M setSpeaker(java.lang.String speaker) {
		set("speaker", speaker);
		return (M)this;
	}

	public java.lang.String getSpeaker() {
		return getStr("speaker");
	}

	public M setSavePath(java.lang.String savePath) {
		set("savePath", savePath);
		return (M)this;
	}

	public java.lang.String getSavePath() {
		return getStr("savePath");
	}

	public M setPicture(java.lang.String picture) {
		set("picture", picture);
		return (M)this;
	}

	public java.lang.String getPicture() {
		return getStr("picture");
	}

	public M setDownloadTimes(java.lang.Integer downloadTimes) {
		set("downloadTimes", downloadTimes);
		return (M)this;
	}

	public java.lang.Integer getDownloadTimes() {
		return getInt("downloadTimes");
	}

	public M setRecogKey(java.lang.String recogKey) {
		set("recogKey", recogKey);
		return (M)this;
	}

	public java.lang.String getRecogKey() {
		return getStr("recogKey");
	}

	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}

	public java.lang.String getDescription() {
		return getStr("description");
	}

	public M setLength(java.lang.Long length) {
		set("length", length);
		return (M)this;
	}

	public java.lang.Long getLength() {
		return getLong("length");
	}

	public M setLanguage(java.lang.Integer language) {
		set("language", language);
		return (M)this;
	}

	public java.lang.Integer getLanguage() {
		return getInt("language");
	}

	public M setShapecodeId(java.lang.String shapecodeId) {
		set("shapecode_id", shapecodeId);
		return (M)this;
	}

	public java.lang.String getShapecodeId() {
		return getStr("shapecode_id");
	}

}
