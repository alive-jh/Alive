package com.wechat.service;

import com.wechat.entity.*;
import com.wechat.entity.dto.ClassCourseEvaluateDto;
import com.wechat.util.Page;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ClassCourseService {

    ClassQRInfo getQRInfo(String code);

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

    Page<ClassCourseReply> findClassCourseReplysByRecordId(
            HashMap<String, String> map);

    ClassCourseReply getClassCourseReply(int id);

    void saveClassCourseReply(ClassCourseReply classCourseReply);

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

    void updateQRInfo(ClassQRInfo info);

    List<ClassQRInfo> getQRInfoByParm(Map<String, Object> paramMap);

    List<ClassGradesRela> getGradesRelas(int studentId, int intGradeId);

    public void saveIntegralByStudent(int studentId, int classCourseId, Integer integral, Integer usedTime);

    public void getIntegralByStudentId(int studentId, int classCourseId);
}
