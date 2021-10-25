package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceFile<M extends BaseDeviceFile<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epal_id", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epal_id");
	}

	public M setFileUrl(java.lang.String fileUrl) {
		set("file_url", fileUrl);
		return (M)this;
	}

	public java.lang.String getFileUrl() {
		return getStr("file_url");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setFilePath(java.lang.String filePath) {
		set("file_path", filePath);
		return (M)this;
	}

	public java.lang.String getFilePath() {
		return getStr("file_path");
	}

	public M setPath(java.lang.String path) {
		set("path", path);
		return (M)this;
	}

	public java.lang.String getPath() {
		return getStr("path");
	}

	public M setFileName(java.lang.String fileName) {
		set("file_name", fileName);
		return (M)this;
	}

	public java.lang.String getFileName() {
		return getStr("file_name");
	}

	public M setDuration(java.lang.String duration) {
		set("duration", duration);
		return (M)this;
	}

	public java.lang.String getDuration() {
		return getStr("duration");
	}

	public M setSource(java.lang.String source) {
		set("source", source);
		return (M)this;
	}

	public java.lang.String getSource() {
		return getStr("source");
	}

}