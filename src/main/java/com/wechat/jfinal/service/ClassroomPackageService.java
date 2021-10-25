package com.wechat.jfinal.service;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.ClassCourse;
import com.wechat.jfinal.model.ClassCourseSchedule;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassGradesRela;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Memberaccount;

public class ClassroomPackageService {

	public int bindStudentWithPack(Integer studentId, Integer classPackId) throws Exception {

		if (xx.isOneEmpty(studentId, classPackId)) {
			return 0;
		}

		// 获取课程包
		Record classroomPack = Db.findFirst(
				"SELECT a.*,GROUP_CONCAT(b.classroom_id) AS classroom_ids  "
						+ "FROM classroom_package a LEFT JOIN classroom_package_rel b ON a.id = b.package_id WHERE a.id = ? GROUP BY a.id",
				classPackId);

		ClassGrades classGrades = ClassGrades.dao.findFirst(
				"" + "SELECT a.* FROM class_grades a JOIN class_grades_rela b ON "
						+ "a.id = b.class_grades_id AND b.gradesStatus = 1 WHERE b.class_student_id = ? AND a.grades_type = 'basicClass' ORDER BY create_time DESC",
				studentId);

		if (classGrades == null) {
			// 创建新的basic班级
			classGrades = new ClassGrades();
			classGrades.setClassGradesName("我的basic班级");
			classGrades.setParentId(0);
			classGrades.setSummary("我的basic班级");
			classGrades.setCover("");
			classGrades.setSort(1);
			classGrades.setGradesType("basicClass");
			classGrades.setStatus(1);
			classGrades.setAuditingStatus(4);
			classGrades.save();

			// 绑定班级与学生
			ClassGradesRela classGradesRela = new ClassGradesRela();
			classGradesRela.setClassGradesId(classGrades.getId());
			classGradesRela.setClassStudentId(studentId);
			classGradesRela.setGradesStatus(1);
			classGradesRela.setType(0);
			classGradesRela.save();

			// 创建学习进度
			ClassCourseSchedule classCourseSchedule = new ClassCourseSchedule();
			classCourseSchedule.setStudentId(studentId);
			classCourseSchedule.setClassCourseId(0);
			classCourseSchedule.setClassGradesId(classGrades.getId());
			classCourseSchedule.setClassRoomId(-1);
			classCourseSchedule.setDoDay(-1);
			classCourseSchedule.setSort(0);
			classCourseSchedule.save();

		}

		// 获取班级原有的课程表
		ClassCourse classCourse1 = ClassCourse.dao.findFirst(
				"SELECT * FROM class_course WHERE class_grades_id = ? ORDER BY do_day DESC LIMIT 1",
				classGrades.getId());

		int doday = 0;
		if (classCourse1 != null) {
			doday = classCourse1.getDoDay();
		}

		// 获取课程包内所有课程
		List<ClassRoom> classRooms = ClassRoom.dao
				.find("select * from class_room where id in (" + classroomPack.getStr("classroom_ids") + ")");

		// 添加课程到课程表
		for (int i = 0; i < classRooms.size(); i++) {
			ClassCourse classCourse = new ClassCourse();
			classCourse.setDoSlot(300);
			classCourse.setDoDay(++doday);
			classCourse.setDoTitle(classRooms.get(i).getClassName());
			classCourse.setClassRoomId(classRooms.get(i).getId());
			classCourse.setClassGradesId(classGrades.getId());
			classCourse.save();
		}

		return classGrades.getId();

	}

	public int addStudent2grade(Integer studentId, Integer gradeId) throws Exception {

		if (xx.isOneEmpty(gradeId, studentId)) {
			return 0;
		}

		/*
		 * // 创建学生班级绑定对象并保存 ClassGradesRela classGradesRela = new
		 * ClassGradesRela(); classGradesRela.setClassStudentId(studentId);
		 * classGradesRela.setClassGradesId(gradeId);
		 * classGradesRela.setGradesStatus(1); classGradesRela.setType(0);
		 * classGradesRela.save();
		 */

		Db.update("insert ignore into class_grades_rela (class_grades_id,class_student_id) VALUES (?,?)", gradeId,
				studentId);

		ClassCourseSchedule classCourseSchedule = ClassCourseSchedule.dao.findFirst("SELECT * FROM class_course_schedule WHERE class_grades_id = ? and student_id = ?",gradeId,studentId);

		if(classCourseSchedule == null){

			classCourseSchedule = new ClassCourseSchedule();
			classCourseSchedule.setStudentId(studentId);
			classCourseSchedule.setClassCourseId(0);
			classCourseSchedule.setClassGradesId(gradeId);
			classCourseSchedule.setClassRoomId(-1);
			classCourseSchedule.setDoDay(-1);
			classCourseSchedule.save();

		}

		return gradeId;
	}

	public int bindStudentWithClassRoom(Integer studentId, Integer classRoomId) throws Exception {

		if (xx.isOneEmpty(studentId, classRoomId)) {

			System.out.println("参数为空");
			return 0;
		}

		ClassRoom classRoom = ClassRoom.dao.findById(classRoomId);

		ClassGrades classGrades = ClassGrades.dao.findFirst(
				"" + "SELECT a.* FROM class_grades a JOIN class_grades_rela b ON "
						+ "a.id = b.class_grades_id AND b.gradesStatus = 1 WHERE b.class_student_id = ? AND a.grades_type = 'basicClass' ORDER BY create_time DESC",
				studentId);

		if (classGrades == null) {
			// 创建新的basic班级
			classGrades = new ClassGrades();
			classGrades.setClassGradesName("凡豆体验班");
			classGrades.setParentId(0);
			classGrades.setSummary("凡豆体验班");
			classGrades.setCover("");
			classGrades.setSort(1);
			classGrades.setGradesType("basicClass");
			classGrades.setStatus(1);
			classGrades.setAuditingStatus(4);
			classGrades.save();

			// 绑定班级与学生
			ClassGradesRela classGradesRela = new ClassGradesRela();
			classGradesRela.setClassGradesId(classGrades.getId());
			classGradesRela.setClassStudentId(studentId);
			classGradesRela.setGradesStatus(1);
			classGradesRela.setType(0);
			classGradesRela.save();

			// 创建学习进度
			ClassCourseSchedule classCourseSchedule = new ClassCourseSchedule();
			classCourseSchedule.setStudentId(studentId);
			classCourseSchedule.setClassCourseId(0);
			classCourseSchedule.setClassGradesId(classGrades.getId());
			classCourseSchedule.setClassRoomId(-1);
			classCourseSchedule.setDoDay(-1);
			classCourseSchedule.setSort(0);
			classCourseSchedule.save();

		}
		
		

		ClassCourse classCourse = ClassCourse.dao.findFirst(
				"SELECT * FROM class_course WHERE class_grades_id = ? ORDER BY do_day DESC LIMIT 1",
				classGrades.getId());
		
		ClassCourse newClassCourse = ClassCourse.dao.findFirst
				("SELECT * FROM class_course WHERE class_grades_id = ? AND class_room_id = ?",classGrades.getId(),classRoomId);
		
		if(newClassCourse == null){
			newClassCourse = new ClassCourse();
			newClassCourse.setDoTitle(classRoom.getClassName());
			newClassCourse.setDoSlot(300);
			newClassCourse.setDoDay(classCourse == null ? 1 : (classCourse.getDoDay() + 1));
			newClassCourse.setClassRoomId(classRoomId);
			newClassCourse.setClassGradesId(classGrades.getId());
			newClassCourse.setCover(classRoom.getCover());
			newClassCourse.save();
		}

		return 1;

	}

	public int addLesson2Basic(int classRoomId, int basic) {

		ClassRoom classRoom = ClassRoom.dao.findById(classRoomId);

		ClassCourse classCourse = ClassCourse.dao
				.findFirst("SELECT * FROM class_course WHERE class_grades_id = ? ORDER BY do_day DESC LIMIT 1", basic);

		ClassCourse newClassCourse = new ClassCourse();
		newClassCourse.setDoTitle(classRoom.getClassName());
		newClassCourse.setDoSlot(300);
		newClassCourse.setDoDay(classCourse == null ? 1 : (classCourse.getDoDay() + 1));
		newClassCourse.setClassRoomId(classRoomId);
		newClassCourse.setClassGradesId(basic);
		newClassCourse.setCover(classRoom.getCover());
		newClassCourse.save();


		return 1;

	}

	public void addFreeLesson2Member(Integer studentId, int productId) {

		Mallproduct mallproduct = Mallproduct.dao.findById(productId);

		try {

			if (mallproduct.getClassGradeId() > 0) {
				addStudent2grade(studentId, mallproduct.getClassGradeId());
			}

			if (mallproduct.getClassRoomPackage() > 0) {
				bindStudentWithPack(studentId, mallproduct.getClassRoomPackage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addFreeLesson(int inviter, int productId) {

		Mallproduct mallproduct = Mallproduct.dao.findById(productId);
		Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where memberid = ?",inviter);
		
		StudentService service = new StudentService();
		
		List<ClassStudent> students = null;
		
		if(memberaccount!=null){
			students = service.getFandouList(memberaccount.getAccount(), inviter);
		}else{
			students = service.getFandouList("", inviter);
		}
		
		if(students != null && students.size() > 0){
			
			Integer studentId = students.get(0).getId();
			
			try {

				if (mallproduct.getClassGradeId() > 0) {
					addStudent2grade(studentId, mallproduct.getClassGradeId());
				}

				if (mallproduct.getClassRoomPackage() > 0) {
					bindStudentWithPack(studentId, mallproduct.getClassRoomPackage());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		

	}
}
