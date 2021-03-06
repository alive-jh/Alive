package com.wechat.jfinal.model;

import java.util.List;

import com.wechat.jfinal.model.base.BaseLessonScriptReply;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class LessonScriptReply extends BaseLessonScriptReply<LessonScriptReply> {
	public static final LessonScriptReply dao = new LessonScriptReply().dao();

	public List<LessonScriptReply> findByStudentWithCourse(int student,int course) {
		return find("select * from lesson_script_reply where student_id = ? and class_course_id = ? and time_sn is not null order by id desc", student,course);
	}

	public List<LessonScriptReply> findByTimeSn(String timeSN) {
		// TODO Auto-generated method stub
		return find("select * from lesson_script_reply where time_sn = ?",timeSN);
	}
}
