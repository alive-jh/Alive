package com.wechat.jfinal.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.wechat.jfinal.model.base.BaseClassRoomPracticePic;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ClassRoomPracticePic extends BaseClassRoomPracticePic<ClassRoomPracticePic> {
	public static final ClassRoomPracticePic dao = new ClassRoomPracticePic().dao();

	public List<ClassRoomPracticePic> findPracticePic(int student, int classroom) {
		// TODO Auto-generated method stub
		return find(
				"SELECT b.* FROM `class_room_practice` a,class_room_practice_pic b WHERE  a.student_id = ? AND a.class_room_id = ? AND a.id = b.class_room_practice_id",
				student, classroom);
	}
}
