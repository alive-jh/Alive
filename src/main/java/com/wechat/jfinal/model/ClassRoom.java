package com.wechat.jfinal.model;

import java.util.List;

import com.wechat.jfinal.model.base.BaseClassRoom;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ClassRoom extends BaseClassRoom<ClassRoom> {
	public static final ClassRoom dao = new ClassRoom().dao();

	public List<ClassRoom> findByLessonPack(Integer classRoomPackage) {
		// TODO Auto-generated method stub
		return find("SELECT b.id,b.class_name FROM `classroom_package_rel` a,class_room b WHERE a.package_id = ? AND a.classroom_id = b.id",classRoomPackage);
	}

    public List<ClassRoom> findByGrade(Integer classGradeId) {
		return find ("SELECT b.* FROM class_course a,class_room b WHERE a.class_room_id = b.id AND a.class_grades_id = ?",classGradeId);
    }
}
