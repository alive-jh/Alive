package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassRoomAudioRel<M extends BaseClassRoomAudioRel<M>> extends Model<M> implements IBean {

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

	public M setAudioId(java.lang.Integer audioId) {
		set("audio_id", audioId);
		return (M)this;
	}

	public java.lang.Integer getAudioId() {
		return getInt("audio_id");
	}

	public M setMaterialFileId(java.lang.Integer materialFileId) {
		set("material_file_id", materialFileId);
		return (M)this;
	}

	public java.lang.Integer getMaterialFileId() {
		return getInt("material_file_id");
	}

}
