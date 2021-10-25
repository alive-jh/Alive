package com.wechat.service.impl;

import com.wechat.dao.ClassCourseDao;
import com.wechat.dao.ClassCourseRecordIntegralDao;
import com.wechat.dao.ClassQRInfoDao;
import com.wechat.dao.GradeDao;
import com.wechat.entity.*;
import com.wechat.entity.dto.ClassCourseEvaluateDto;
import com.wechat.service.ClassCourseService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassCourseServiceImpl implements ClassCourseService {

    @Autowired
    ClassCourseDao classCourseDao;

    @Autowired
    ClassQRInfoDao classQRInfoDao;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    ClassCourseRecordIntegralDao classCourseRecordIntegralDao;

    @Override
    public List<ClassGradesRela> getGradesRelas(int sid, int gid) {

        return gradeDao.getBySidAndGId(sid, gid).getItems();
    }


    @Override
    public List<ClassQRInfo> getQRInfoByParm(Map<String, Object> paramMap) {

        return classQRInfoDao.getByParam(paramMap).getItems();
    }

    @Override
    public void updateQRInfo(ClassQRInfo info) {
        classQRInfoDao.updates(info);

    }

    @Override
    public ClassQRInfo getQRInfo(String code) {
        return classQRInfoDao.getByCode(code);
    }


    @Override
    public ClassCourseRecord getClassCourseRecord(int id) {
        return this.classCourseDao.getClassCourseRecord(id);
    }

    @Override
    public void saveClassCourseRecord(ClassCourseRecord classCourseRecord) {
        this.classCourseDao.saveClassCourseRecord(classCourseRecord);
    }

    @Override
    public Page<ClassCourseRecord> findToDayClassCourseRecordsByStudentId(
            HashMap<String, String> map) {
        return this.classCourseDao.findToDayClassCourseRecordsByStudentId(map);
    }

    @Override
    public HashMap<String, Object> findJoinedClassCoursesByStudentId(
            HashMap<String, String> map) {
        return this.classCourseDao.findJoinedClassCoursesByStudentId(map);
    }

    @Override
    public HashMap<String, Object> findJoinedClassCoursesIndexByStudentId(
            HashMap<String, String> map) {
        return this.classCourseDao.findJoinedClassCoursesIndexByStudentId(map);
    }

    @Override
    public Page<ClassCourseRecord> findClassCourseRecordsByStudentIdAndGradesId(
            HashMap<String, String> map) {
        return this.classCourseDao.findClassCourseRecordsByStudentIdAndGradesId(map);
    }

    @Override
    public Page<ClassCourse> findClassCoursesByRoomId(
            HashMap<String, String> map) {
        return this.classCourseDao.findClassCoursesByRoomId(map);
    }

    @Override
    public Page<ClassCourseReply> findClassCourseReplysByRecordId(
            HashMap<String, String> map) {
        return this.classCourseDao.findClassCourseReplysByRecordId(map);
    }

    @Override
    public ClassCourseReply getClassCourseReply(int id) {
        return this.classCourseDao.getClassCourseReply(id);
    }

    @Override
    public void saveClassCourseReply(ClassCourseReply classCourseReply) {
        this.classCourseDao.saveClassCourseReply(classCourseReply);
    }

    @Override
    public ClassCourseEvaluateDto findClassCourseEvaluateByStudentIdAndGradesId(
            HashMap<String, String> map) {
        return this.classCourseDao.findClassCourseEvaluateByStudentIdAndGradesId(map);
    }

    @Override
    public JSONArray findJoinedClassCoursesListByStudentId(
            HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return this.classCourseDao.findJoinedClassCoursesListByStudentId(map);
    }

    @Override
    public HashMap<String, Object> findJoinedClassCoursesIndexByStudentIdOptimization(
            HashMap<String, String> map) {
        return this.classCourseDao.findJoinedClassCoursesIndexByStudentIdOptimization(map);
    }

    @Override
    public HashMap<String, Object> findJoinedClassCoursesIndexInfo(
            HashMap<String, String> map) {

        return this.classCourseDao.findJoinedClassCoursesIndexInfo(map);
    }


    @Override
    public void saveIntegralByStudent(int studentId, int classCourseId, Integer integral, Integer usedTime) {

        if (integral <= 0 || integral == null) {
            return;
        }

        ClassCourseRecordIntegral classCourseRecordIntegral = new ClassCourseRecordIntegral(studentId, classCourseId, integral, usedTime);
        this.classCourseRecordIntegralDao.saveIntegral(classCourseRecordIntegral);

        ClassCourseRecordAllIntegral classCourseRecordAllIntegral = this.classCourseRecordIntegralDao.getIntegralByStudentId(studentId, classCourseId);
        if (classCourseRecordAllIntegral == null) {
            classCourseRecordAllIntegral = new ClassCourseRecordAllIntegral(studentId, integral);
        } else {
            classCourseRecordAllIntegral.setAllIntegral(classCourseRecordAllIntegral.getAllIntegral() + integral);
        }
        this.classCourseRecordIntegralDao.saveAllIntegral(classCourseRecordAllIntegral);
    }

    @Override
    public void getIntegralByStudentId(int studentId, int classCourseId) {
        classCourseRecordIntegralDao.getIntegralByStudentId(studentId, classCourseId);
    }
}
