package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public interface CourseDao {

	Page searchCourseInfos(HashMap map);

	Page searchCourseSchedules(HashMap map);

	MallProduct findMainCourseByProductId(Integer productId);

	void updateMallProduct(MallProduct mallProduct);

	Course findChildCourseByCourseId(String courseId);

	void updateCourse(Course course);

	Page getAllCoursesByProductId(String productId);

	Page getAllCourseSchedulesByEpalId(String epalId);

	Page getAllCoursePeriodsByCourseId(String courseId);

	void updateCoursePeriodByPeriodId(CoursePeriod course);

	CoursePeriod findCoursePeriodByPeriodId(String periodId);

	void saveCoursePeriod(CoursePeriod coursePeriod);

	Page searchCourseWords(HashMap map);

	void saveCourseWord(CourseWord courseWord);

	Page searchCourseInfo(HashMap map);

	Page<?> getCoursePeriodsRecords(HashMap<String, String> map);

	Page getCoursePlanSelectTree(HashMap map);

	CoursePlan addCoursePlan(HashMap map);

	void updateCoursePlanPeriodSort(String sortedPeriodIds);

	void delCoursePlan(String coursePlanId);

	void delCoursePlanInfo(Integer infoId);

	ArrayList getMyCoursePlans(String epalId);

	void delCoursePlanInfos(String planId);

	ArrayList updateCoursePlan(String planId, String coursePlanInfos);

	void addCoursePlanInfo(CoursePlanInfo coursePlanInfo);

	Page getCourseStudyRecords(HashMap map);

	Page<?> getCourseInfoByMainCourseId(HashMap<String, String> map);

	Page getCourseInfosTree(HashMap map);

	Page<?> getCourseInfoRecordByMainCourseId(HashMap<String, String> map);

	Page<?> getCourseInfoRecords(HashMap<String, String> map);

	void addCoursePeriod(CoursePeriod course);

	void deleteMainCourse(String productId);

	void deleteChildCourse(String courseId);

	void deletePeriodCourse(String periodId);

	void addCourse(Course course);

	void updateCourseProject(Integer planId);

	void saveCourseBookLib(CourseBookLib bookLib);

	ArrayList<CourseBookLib> findCourseBookLib(String productId);

	void delCourseBookLibs(Integer productId);

	Integer getRecordCountSum(String epalId);

	CourseSchedule getCurStudyedCourse(String courseId, String epalId);

	Page searchCourseProject(HashMap map);

	JSONObject getClassCourseRecordDetail(String classGradesId);



}
