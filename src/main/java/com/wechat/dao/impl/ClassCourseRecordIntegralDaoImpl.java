package com.wechat.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.wechat.dao.ClassCourseRecordIntegralDao;
import com.wechat.entity.ClassCourseRecordAllIntegral;
import com.wechat.entity.ClassCourseRecordIntegral;
import org.springframework.stereotype.Repository;

@Repository
public class ClassCourseRecordIntegralDaoImpl extends BaseDaoImpl implements ClassCourseRecordIntegralDao {

    @Override
    public void saveIntegral(ClassCourseRecordIntegral classCourseRecordIntegral) {
        this.save(classCourseRecordIntegral);
    }

    @Override
    public ClassCourseRecordAllIntegral getIntegralByStudentId(int studentId, int classCourseId) {
        String hql = "select rai from ClassCourseRecordAllIntegral rai where rai.studentId = ?";
        return (ClassCourseRecordAllIntegral) createQuery(hql, studentId).uniqueResult();
    }

    @Override
    public void saveAllIntegral(ClassCourseRecordAllIntegral classCourseRecordAllIntegral) {
        this.saveOrUpdateEntity(classCourseRecordAllIntegral);
    }

    @Override
    public ClassCourseRecordIntegral getIntegralByStudentIdAndCourseId(int studentId, int classCourseId) {
        String hql = "select ri from ClassCourseRecordIntegral ri where ri.studentId = ? and classCourseId = ?";
        return (ClassCourseRecordIntegral) createQuery(hql, studentId, classCourseId).uniqueResult();
    }
}
