package com.wechat.alive.test;

import com.wechat.alive.ClassCourse;
import com.wechat.alive.ClassCourse.Course;
import com.wechat.alive.ClassCourseSchedule;
import com.wechat.alive.test.Test.AbstractData;

import java.util.ArrayList;
import java.util.List;

public class TestData14 extends AbstractData {
	
	@Override
	public ClassCourseSchedule getClassCourseSchedule(int studentId, int classGradeId) {
		ClassCourseSchedule ccs = super.getClassCourseSchedule(studentId, classGradeId);
		ccs.setClassCourseId(5);//TODO
		ccs.setClassRoomId(123);//TODO
		ccs.setDoDay(2);//TODO
		return ccs;
	}
	
	/**
	 * ��ȡѧ����ĳ���ָ࣬��ʱ�䣨startTime��֮����ɵ����пγ�
	 * */
	@Override
	public List<Integer> getRecordsAfterTime(int studentId, int classGradesId, String startTime, String endTime) {
		List<Integer> records = new ArrayList<Integer>();
		records.add(5);//TODO
		return records;
	}
	
	private void isMatch(int id, int classCourseId, int classRoomId, int doDay) {
		String log = "";
//		if (id != ccs.getId()) {
//			log += " id ";
//		}
//		if (other.getStudentId() != ccs.getStudentId()) {
//			log += " studentId ";
//		}
//		if (other.getClassGradesId() != ccs.getClassGradesId()) {
//			log += " classGradesId ";
//		}
		if (classCourseId != ClassCourseSchedule.TAG_NOT_FINISH_BUT_NO_MORE_PRIMARY_COURSE) {//TODO
			log += " classCourseId ";
		}
		if (classRoomId != -1) {//TODO
			log += " classRoomId ";
		}
		if (doDay != 3) {//TODO
			log += " doDay ";
		}
//		if (other.getStartTime() != ccs.getStartTime()) {
//			log += " startTime ";
//		}
//		if (other.getLastTime() != ccs.getLastTime()) {
//			log += " lastTime ";
//		}
		if (!log.isEmpty()) {
			showLog("Not Match:" + log);
		} else {
			showLog("Match");
		}
	}
	
	@Override
	public void updateClassCourseSchedule(int id, int classCourseId, int classRoomId, int doDay) {
		isMatch(id, classCourseId, classRoomId, doDay);
	}
	
	@Override
	public ClassCourse getClassCourse(int classGradeId, int startDoDay) {
		return new ClassCourse(getCourseList(classGradeId, startDoDay));
	}
	
	private List<Course> getCourseList(int classGradeId, int startDoDay) {
		List<Course> courseList = new ArrayList<Course>();
		courseList.add(getCourse(1, "��-01-��", 100, 1, 111, classGradeId));
		courseList.add(getCourse(2, "��-01",    300, 1, 113, classGradeId));
		courseList.add(getCourse(3, "��-01-��", 500, 1, 115, classGradeId));
		
		courseList.add(getCourse(4, "��-02-��", 100, 2, 121, classGradeId));
		courseList.add(getCourse(5, "��-02",    300, 2, 123, classGradeId));
		courseList.add(getCourse(6, "��-02-��", 500, 2, 125, classGradeId));
		
		courseList.add(getCourse(7, "��-03-��", 100, 3, 131, classGradeId));
//		courseList.add(getCourse(8, "��-03",    300, 3, 133, classGradeId));
		courseList.add(getCourse(9, "��-03-��", 500, 3, 135, classGradeId));
		
		courseList.add(getCourse(10, "��-04-��", 100, 4, 141, classGradeId));
//		courseList.add(getCourse(11, "��-04",    300, 4, 143, classGradeId));
		courseList.add(getCourse(12, "��-04-��", 500, 4, 145, classGradeId));
		
//		courseList.add(getCourse(13, "��-05-��", 100, 5, 151, classGradeId));
//		courseList.add(getCourse(14, "��-05",    300, 5, 153, classGradeId));
//		courseList.add(getCourse(15, "��-05-��", 500, 5, 155, classGradeId));
//		
//		courseList.add(getCourse(16, "��-06-��", 100, 6, 161, classGradeId));
//		courseList.add(getCourse(17, "��-06",    300, 6, 163, classGradeId));
//		courseList.add(getCourse(18, "��-06-��", 500, 6, 165, classGradeId));
//		
//		courseList.add(getCourse(19, "��-07-��", 100, 7, 171, classGradeId));
//		courseList.add(getCourse(20, "��-07",    300, 7, 173, classGradeId));
//		courseList.add(getCourse(21, "��-07-��", 500, 7, 175, classGradeId));
//		
//		courseList.add(getCourse(22, "��-08-��", 100, 8, 181, classGradeId));
//		courseList.add(getCourse(23, "��-08",    300, 8, 183, classGradeId));
//		courseList.add(getCourse(24, "��-08-��", 500, 8, 185, classGradeId));
//		
//		courseList.add(getCourse(25, "��-09-��", 100, 9, 191, classGradeId));
//		courseList.add(getCourse(26, "��-09",    300, 9, 193, classGradeId));
//		courseList.add(getCourse(27, "��-09-��", 500, 9, 195, classGradeId));
		return courseList;
	}
	
	private Course getCourse(int id, String doTitle, int doSlot, int doDay,
                             int classRoomId, int classGradesId) {
		return new Course(id, doTitle, doSlot, doDay, classRoomId, classGradesId);
	}
	
	@Override
	public void noChange(String log) {
		showLog(log);
	}
	
	private void showLog(String log) {
		System.out.println(getClass().getSimpleName() + " " + log);
	}

	
}
