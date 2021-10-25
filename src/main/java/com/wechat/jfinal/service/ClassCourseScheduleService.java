package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.ClassCourse;
import com.wechat.jfinal.model.ClassCourseSchedule;

import java.util.*;

public class ClassCourseScheduleService {

    private static final ClassCourseSchedule dao = new ClassCourseSchedule().dao();
    private static final ClassCourseService classCourseService = new ClassCourseService();

    public Object getById(int id) {
        return dao.findById(id);
    }

    public Date getJoinGradeDate(Integer stdId, Integer gradeId) {
        return dao.findFirst("SELECT * FROM class_course_schedule WHERE student_id = ? AND class_grades_id = ?", stdId, gradeId).getStartTime();
    }

    public List<ClassCourseSchedule> get(int stdId, List<Integer> gradeIds) {
        if (xx.isEmpty(gradeIds))
            return new ArrayList<>();
        Kv cond = Kv.by("stdId", stdId).set("gradeIds", gradeIds);
        SqlPara sqlPara = Db.getSqlPara("courseSchedule.getByGradeIds", cond);

        return dao.find(sqlPara);

    }


    public List<ClassCourseSchedule> GetAllSchedule(int studentId) {
        return ClassCourseSchedule.dao.find("SELECT * FROM class_course_schedule WHERE student_id = ?", studentId);
    }

    public Map<Integer, Integer> convetDoDay2TodayShouldBe(List<ClassCourseSchedule> schedules, boolean isMacthNextCourseDoDay) {
        Map<Integer, Integer> result = new HashMap<>();

        Map<Integer, List<ClassCourse>> gradeId_courses = new HashMap<>();
        if (schedules == null || schedules.size() == 0)
            return result;
        for (ClassCourseSchedule schedule : schedules) {
            int gradeId = schedule.getClassGradesId();
            gradeId_courses.put(gradeId, classCourseService.getDodayAndNextDodayCourse(gradeId, schedule.getDoDay()));
        }
        //判断游标的doday是否已经更新，没更新的doday挪至下一节course
        for (ClassCourseSchedule schedule : schedules) {
            int gradeId = schedule.getClassGradesId();
            List<ClassCourse> courses = gradeId_courses.get(gradeId);
            if (courses == null || courses.size() < 2)//没课了，不用改
                result.put(gradeId, schedule.getDoDay());
            else {
                if (courses.get(0).getId() != schedule.getClassCourseId())//不相等，doday未更新，+1
                    result.put(gradeId, courses.get(1).getDoDay());
            }
        }
        return result;
    }

    public int dodayShouldBe(ClassCourseSchedule schedule) {
        int gradeId = schedule.getClassGradesId();
        List<ClassCourse> courses = classCourseService.getDodayAndNextDodayCourse(gradeId, schedule.getDoDay());
        if (courses == null || courses.size() < 2)//没课了，不用改
            return schedule.getDoDay();
        else {
            if (!courses.get(0).getId().equals(schedule.getClassCourseId()) )//不相等，doday未更新，+1
                return courses.get(1).getDoDay();
        }
        return 1;
    }
}
