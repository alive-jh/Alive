package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.*;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClassCourseService {
    private static final StudentService studentService = new StudentService();
    private final GradesService gradesService = new GradesService();
    private final ClassCourseScheduleService classCourseScheduleService = new ClassCourseScheduleService();
    private final StudentStudyDayService studentStudyDayService = new StudentStudyDayService();

    //获得当前时间前后区间内的doday
    public Map<String, Integer> getDoDaySection(Date classOpenDate, Date index, int backward, int forward) throws ParseException {
        Map<String, Integer> result = new HashMap<>();

        Long betweenDays = (long) ((index.getTime() - classOpenDate.getTime()) / (1000 * 60 * 60 * 24) + 0.5);
        int nowDoDay = betweenDays.intValue() + 1;
        Integer doDayStart;
        Integer doDayEnd;
        doDayStart = nowDoDay - (backward == -1 ? 0 : backward);
        doDayEnd = nowDoDay + (forward == -1 ? 99999 : forward);

        result.put("start", doDayStart);
        result.put("end", doDayEnd);
        return result;
    }

    public ClassCourse getById(int id) {
        return ClassCourse.dao.findById(id);
    }

    /**
     * function: 根据学生ID，获取当天学生要上的所有课程
     * description: 根据学生学习规则判断当天是否要上课，不需要上课就取前一天学习的辅课复习（根据游标获得前一天的courseId），需要上课就取当天的课程（通过游标的doday和classCourseId判断）
     *
     * @param studentId 学生ID
     * @return 课程列表
     * @author zlisten
     */
    public List<ClassCourse> todayCourses(int studentId) {
        //获得所有班级
        List<ClassGrades> grades = gradesService.getJoinGradesNew(studentId, "1-2-3", 1);
        if (grades == null || grades.size() == 0) {
            return new ArrayList<>();
        }
        List<ClassCourse> coursesShouldBe = new ArrayList<>();
        List<ClassCourse> result = new ArrayList<>();
        Map<Integer, StdDiyStudyDay> gradeId_rule = new HashMap<>();
        Map<Integer, ClassCourseSchedule> gradeId_schedule = new HashMap<>();

        //获得所有规则
        List<StdDiyStudyDay> rules = studentService.getAllRule(studentId);
        for (StdDiyStudyDay rule : rules) {
            gradeId_rule.put(rule.getGradeId(), rule);
        }
        //获得所有游标
        List<ClassCourseSchedule> schedules = classCourseScheduleService.GetAllSchedule(studentId);
        for (ClassCourseSchedule schedule : schedules) {
            gradeId_schedule.put(schedule.getClassGradesId(), schedule);
        }

//        Map<Integer, Integer> lastDoDays = classCourseScheduleService.convetDoDay2TodayShouldBe(schedules);
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        for (ClassGrades grade : grades) {
            //返回游标的正确doday
            int doday = classCourseScheduleService.dodayShouldBe(gradeId_schedule.get(grade.getId()));
            //判断是否使用自定义规则
            StdDiyStudyDay rule = gradeId_rule.get(grade.getId());
            int flag = studentStudyDayService.isStudyDay(rule, new Date(), doday, gradeId_schedule.get(grade.getId()));
            switch (flag) {
                case 0:
                    break;
                case 1:
                    result.addAll(getAllCourse(grade.getId(), doday));
                    break;
                case 2:
                    result.addAll(getSubCourse(grade.getId(), doday));
                    break;
                case 3:
                    result.addAll(getNextDodayAllCourse(grade.getId(), doday));
                    break;
            }
        }
        return result;
    }

    private List<ClassCourse> getNextDodayAllCourse(Integer gradeId, int doday) {
        Kv cond = Kv.by("gradeId", gradeId).set("doday", doday);
        return ClassCourse.dao.find(Db.getSqlPara("course.getNextDodayAllCourse", cond));
    }

    private List<ClassCourse> getSubCourse(Integer gradeId, int doday) {
        return ClassCourse.dao.find("SELECT c.*, r.class_name name FROM class_course c, class_room r WHERE c.class_grades_id = ? AND c.do_day = ? AND r.id = c.class_room_id and c.do_slot != 300", gradeId, doday);
    }

    private List<ClassCourse> getAllCourse(Integer gradeId, int doday) {
        return ClassCourse.dao.find("SELECT c.*, r.class_name name FROM class_course c, class_room r WHERE c.class_grades_id = ? AND c.do_day = ? AND r.id = c.class_room_id", gradeId, doday);
    }

    @Deprecated
    //TODO 待优化
    private List<ClassCourse> getListByCourse(ClassCourse course) {
        if (course.getDoSlot() == null)//只取辅课
            return ClassCourse.dao.find("SELECT * FROM class_course WHERE class_grades_id = ? AND do_day = ? AND do_slot != 300", course.getClassGradesId(), course.getDoDay());
//        else if ()course.getDoSlot() == 300)//主辅都取
        else //主辅都取
            return ClassCourse.dao.find("SELECT * FROM class_course WHERE class_grades_id = ? AND do_day = ? ", course.getClassGradesId(), course.getDoDay());
    }

    public List<ClassCourse> getMainByGradeIdsAndDodayIds(List<Integer> gradeIds, List<Integer> dodays) {
        Kv cond = Kv.by("gradeIds", gradeIds).set("dodays", dodays);
        SqlPara sql = Db.use("oss").getSqlPara("course.getByGradeIdsAndDodayIds", cond);
        return ClassCourse.dao.find(sql);
    }

    public ClassCourse filterClassCourse(List<ClassCourse> courses, int gradeId, int doday) {
        for (ClassCourse course : courses) {
            if (course.getClassGradesId() == gradeId && course.getDoDay() == doday) {
                return course;
            }
        }
        return null;
    }

    public List<ClassCourse> getDodayAndNextDodayCourse(int gradeId, int doday) {
        return ClassCourse.dao.find("SELECT * FROM class_course WHERE class_grades_id = ? AND do_day >= ? AND do_slot = 300 LIMIT 2", gradeId, doday);
    }

    public List<Record> makeUpAllInfo(List<ClassCourse> courses, int studentId) {
        if (xx.isEmpty(courses))
            return new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (ClassCourse cours : courses) {
            ids.add(cours.getId());
        }
        Kv cond = Kv.by("ids", ids).set("studentId", studentId);
        return Db.find(Db.getSqlPara("course.getCourseDetailed", cond));

    }

    public Map<Integer, Record> courseIndex(List<Integer> mainCourseIds) {
        Map<Integer, Record> result = new HashMap<>();
        if (xx.isEmpty(mainCourseIds))
            return result;
        for (Integer mainCourseId : mainCourseIds) {
            result.put(mainCourseId, courseIndex(mainCourseId));
        }
        return result;
    }

    public Record courseIndex(int courseId) {
        Kv cond = Kv.by("courseId", courseId);
        SqlPara sql = Db.getSqlPara("course.courseIndex", cond);
        return Db.findFirst(sql);

    }

    public Map<Integer, Integer> courseStudyTimes(List<Integer> ids) {
        Map<Integer, Integer> result = new HashMap<>();
        if (xx.isEmpty(ids))
            return result;
        List<Record> data = Db.find(Db.getSqlPara("course.courseStudyTimes", Kv.by("ids", ids)));
        for (Record record : data) {
            result.put(record.getInt("id"), record.getInt("studied"));
        }
        return result;
    }

    public List<ClassCourse> getCourses(int studentId, String gradesIds) throws ParseException {

        /*
         * 获取学习进度，如果班级是basicClass并且doday为-1则设置doday为1
         */
        StringBuffer sql = new StringBuffer();
        sql.append("select a.class_grades_id,IF(b.grades_type='basicClass' AND a.do_day=-1,1,a.do_day) AS do_day,b.classOpenDate");
        sql.append(" from class_course_schedule a JOIN class_grades b ON a.class_grades_id = b.id where");
        sql.append(" a.class_grades_id in (" + gradesIds + ") and a.student_id = ?");
        //List<ClassCourseSchedule> classCourseSchedules = ClassCourseSchedule.dao.find(sql.toString(),studentId);
        List<Record> classCourseSchedules = Db.find(sql.toString(), studentId);

        List<ClassCourse> result = new ArrayList<ClassCourse>();

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Record classCourseSchedule : classCourseSchedules) {

            String classOpenDate = classCourseSchedule.getStr("classOpenDate");

            //如果班级不是basicClass并且classOpenDate为空并且doDay为-1则设置doday为1
            int doDay = classCourseSchedule.getInt("do_day");
            if (classOpenDate == null && doDay == -1) {
                doDay = 1;
            }
            ;

            if (!StringUtils.isEmpty(classOpenDate) && sdf.parse(classOpenDate).after(today)) {
                break;
            }

            List<ClassCourse> classCourses = ClassCourse.dao.find("select * from class_course where class_grades_id = ? and do_day = ?", classCourseSchedule.getInt("class_grades_id"), doDay);
            if (classCourses.size() > 0) {
                result.addAll(classCourses);
            }
        }

        return result;
    }

    public static void main(String[] args) throws ParseException {
        String str = "2018-08-07";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf(String.valueOf(sdf.parse(str).after(new Date())));
    }

    /**
     * @param studentId
     * @param classGradeId
     * @return
     */
    public boolean isLastClassRoom(int studentId, int classGradeId) {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT IF(a.do_day = IFNULL(b.do_day, 0), 1, 0) AS is_last_class FROM class_course a LEFT JOIN class_course_schedule b ON ");
        sql.append("a.class_grades_id = b.class_grades_id AND b.student_id = ? WHERE a.class_grades_id = ? ORDER BY a.do_day DESC LIMIT 1");

        Record record = Db.findFirst(sql.toString(), studentId, classGradeId);

        if (record != null && record.getInt("is_last_class").equals(1)) {
            return true;
        }

        return false;
    }

    public void saveIntegralByStudent(int studentId, int classCourseId, Integer integral, Integer usedTime) {

        if (integral <= 0 || integral == null) {
            return;
        }

        ClassCourseRecordIntegral classCourseRecordIntegral = new ClassCourseRecordIntegral();
        classCourseRecordIntegral.setIntegral(integral);
        classCourseRecordIntegral.setStudentId(studentId);
        classCourseRecordIntegral.setClassCourseId(classCourseId);
        classCourseRecordIntegral.setUsedTime(usedTime);
        classCourseRecordIntegral.save();

        ClassCourseRecordAllIntegral classCourseRecordAllIntegral = ClassCourseRecordAllIntegral.dao.findFirst("select * from class_course_record_all_integral where student_id = ?", studentId);
        if (classCourseRecordAllIntegral == null) {
            classCourseRecordAllIntegral = new ClassCourseRecordAllIntegral();
            classCourseRecordAllIntegral.setStudentId(studentId);
            classCourseRecordAllIntegral.setAllIntegral(integral);
            classCourseRecordAllIntegral.save();
        } else {
            classCourseRecordAllIntegral.setAllIntegral(classCourseRecordAllIntegral.getAllIntegral() + integral);
            classCourseRecordAllIntegral.update();
        }
    }

}
