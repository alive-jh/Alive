package com.wechat.alive;

import java.util.List;

public interface DataHelper {
	/**
	 * ��ȡѧ�����Ͽ�ʱ�䰲��<br/>
	 * select * from std_diy_study_day where std_id=studentId and grade_id=classGradeId;
	 * */
	StudyDay getStudyDay(int studentId, int classGradeId);
	
	/**
	 * ��ȡ��ǰ��¼�Ľ���<br/>
	 * select * from class_course_schedule where class_grades_id=classGradeId and student_id=studentId;
	 * */
	ClassCourseSchedule getClassCourseSchedule(int studentId, int classGradeId);
	
	/**
	 * ���½���<br/>
	 * update class_course_schedule set class_course_id=classCourseId, class_room_id=classRoomId, do_day=doDay where id=id; 
	 * */
	void updateClassCourseSchedule(int id, int classCourseId, int classRoomId, int doDay);
	
	/**
	 * ��ȡ��startDoDay֮��Ŀγ̱�����startDoDay���죩<br/>
	 * select * from class_course where class_grades_id=classGradeId and do_day>=startDoDay;
	 * */
	ClassCourse getClassCourse(int classGradeId, int startDoDay);
	
	/**
	 * ��ȡѧ����ĳ���ָ࣬��ʱ�䣨startTime��֮����ɵ����пγ�<br/>
	 * select class_course_id from class_course_record where student_id=studentId
	 *  and class_grades_id=classGradesId and complete=1 and complete_time>=startTime and complete_time<endTime;
	 * */
	List<Integer> getRecordsAfterTime(int studentId, int classGradesId, String startTime, String endTime);
	
	void noChange(String log);
}
