package com.wechat.dao;

import com.wechat.entity.ClassCourse;
import com.wechat.entity.ClassCourseRecord;
import com.wechat.entity.ClassCourseReply;
import com.wechat.entity.dto.ClassCourseEvaluateDto;
import com.wechat.util.Page;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.List;


public interface ClassCourseDao {

	ClassCourseRecord getClassCourseRecord(int id);

	void saveClassCourseRecord(ClassCourseRecord classCourseRecord);

	Page<ClassCourseRecord> findToDayClassCourseRecordsByStudentId(
            HashMap<String, String> map);

	HashMap<String, Object> findJoinedClassCoursesByStudentId(
            HashMap<String, String> map);

	HashMap<String, Object> findJoinedClassCoursesIndexByStudentId(
            HashMap<String, String> map);

	Page<ClassCourseRecord> findClassCourseRecordsByStudentIdAndGradesId(
            HashMap<String, String> map);

	Page<ClassCourse> findClassCoursesByRoomId(HashMap<String, String> map);

	void saveClassCourseReply(ClassCourseReply classCourseReply);

	ClassCourseReply getClassCourseReply(int id);

	Page<ClassCourseReply> findClassCourseReplysByRecordId(
            HashMap<String, String> map);

	ClassCourseEvaluateDto findClassCourseEvaluateByStudentIdAndGradesId(
            HashMap<String, String> map);

	JSONArray findJoinedClassCoursesListByStudentId(
            HashMap<String, String> map);
	
	
	
	/**
	 * 根据学生ID获取加入的班级在线课堂课程表（学生首页数据集）[APP用]
	 */
	HashMap<String, Object> findJoinedClassCoursesIndexByStudentIdOptimization(
            HashMap<String, String> map);
	
	/**
	 * 根据学生ID获取加入的班级在线课堂课程表（学生首页数据集）[微信小程序用]
	 */
	public HashMap<String, Object> findJoinedClassCoursesIndexInfo(
            HashMap<String, String> map);


	List<ClassCourse> findJoinedCoursesByStudentId(Integer sid);



}
