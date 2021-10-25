package com.wechat.dao;


import com.wechat.entity.ClassCourseRecordAllIntegral;
import com.wechat.entity.ClassCourseRecordIntegral;

public interface ClassCourseRecordIntegralDao {

    public void saveIntegral(ClassCourseRecordIntegral classCourseRecordIntegral);

    public void saveAllIntegral(ClassCourseRecordAllIntegral classCourseRecordAllIntegral);

    public ClassCourseRecordAllIntegral getIntegralByStudentId(int studentId, int classCourseId);

    public ClassCourseRecordIntegral getIntegralByStudentIdAndCourseId(int studentId, int classCourseId);

}
