package com.wechat.jfinal.model;

import java.util.List;

import com.wechat.jfinal.model.base.BaseClassScriptNormal;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ClassScriptNormal extends BaseClassScriptNormal<ClassScriptNormal> {
	public static final ClassScriptNormal dao = new ClassScriptNormal().dao();

	public List<ClassScriptNormal> findByClassRoomId(int id) {
		// TODO Auto-generated method stub
		return find("select * from class_script_normal where class_room_id = ?",id);
	}
}