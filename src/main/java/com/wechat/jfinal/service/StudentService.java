package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.StdDiyStudyDay;

import java.util.*;

public class StudentService {
    private static final ClassStudent dao = new ClassStudent().dao();

    private static final ClassCourseScheduleService courseScheduleService = new ClassCourseScheduleService();
    private static final IntegralLevelInfoService integralService = new IntegralLevelInfoService();
    private AgentSchoolService agentSchoolService = new AgentSchoolService();

    public ClassStudent getById(int id) {
        return dao.findById(id);
    }

    public void setImportantGrade(int sId, int gId) {
        Db.update("UPDATE class_grades_rela set type = 0 WHERE class_student_id = ?", sId);
        Db.update("UPDATE class_grades_rela set type = 1 WHERE class_student_id = ? AND class_grades_id = ?", sId, gId);

    }

    public Date getJoinGradeDate(Integer stdId, Integer gradeId) {
        return courseScheduleService.getJoinGradeDate(stdId, gradeId);
    }

    public List<Record> getSchedule(int stdId, int gradeId, int doDayStart, int doDayEnd) {
        List<Record> courses = new ArrayList<>();//所有课程
        if (gradeId == 0)
            return courses;

        Kv cond = Kv.by("gradeId", gradeId).set("studentId", stdId).set("dodayStart", doDayStart).set("doDayEnd", doDayEnd);
        SqlPara sqlPara = Db.getSqlPara("student.getScheduleBySection", cond);

        courses = Db.find(sqlPara);
        //System.out.println(courses);
        return fillZkAndAddZkId(courses);
    }

    public Map<Integer, List<Record>> getSchedule(int stdId, List<Object> gradeIds) {

        Map<Integer, List<Record>> result = new HashMap<>();
        if (gradeIds.size() == 0)
            return result;
        Map<Integer, List<Record>> courseMap = new HashMap<>();
        List<Record> courses;//所有课程

        Kv cond = Kv.by("gradeIds", gradeIds).set("studentId", stdId);
        SqlPara sqlPara = Db.getSqlPara("student.getSchedules", cond);

        courses = Db.find(sqlPara);
        //将allCourses根据班级id分组
        courseMap = ConvetUtil.groupRecord2IntMap(courses, "class_grades_id");
        for (Integer gradeId : courseMap.keySet()) {
            result.put(gradeId, fillZkAndAddZkId(courseMap.get(gradeId)));
        }
        return result;
    }

    @Deprecated
    public List<Record> getSchedule(int stdId, int gradeIds) {
        Kv cond = Kv.by("gradeIds", gradeIds).set("studentId", stdId);
        SqlPara sqlPara = Db.getSqlPara("student.getSchedules", cond);

        return fillZkAndAddZkId(Db.find(sqlPara));
    }


    public List<Record> getSchedule2(int stdId, int gradeIds) {
        Kv cond = Kv.by("gradeIds", gradeIds).set("studentId", stdId);
        SqlPara sqlPara = Db.getSqlPara("student.getSchedules", cond);
        List<Record> courses = Db.find(sqlPara);

        Set<Integer> allDoDay = new HashSet<>();
        Map<String, List<Record>> allCourse = new HashMap<>(); //key: gradeId+doday+doSlot

        for (Record course : courses) {
            List<Record> tempList = allCourse.get(course.getInt("doDay") + course.getInt("doSlot") + "");
            if (tempList == null) {
                tempList = new ArrayList<>();
                allCourse.put("" + course.getInt("doDay") + course.getInt("doSlot"), tempList);
            }
            allDoDay.add(course.getInt("doDay"));
            tempList.add(course);
        }

        //遍历补充虚拟主课
        for (Integer doDay : allDoDay) {
            Record zk = allCourse.get("" + doDay + 300) == null ? null : allCourse.get("" + doDay + 300).get(0);
            if (zk == null) {
                Record fk = allCourse.get("" + doDay + 100) == null ? null : allCourse.get("" + doDay + 100).get(0);
                if (fk == null)
                    fk = allCourse.get("" + doDay + 500) == null ? null : allCourse.get("" + doDay + 500).get(0);
                if (fk == null)
                    continue;
                //判断是否已经学完辅课
                Object completeTime = getCompleteTime(allCourse.get("" + doDay + 100), allCourse.get("" + doDay + 500));
                Record vzk;

                vzk = new Record().set("doDay", doDay).set("doSlot", 300).set("classCourseId", -1).set("completeTime", completeTime).set("cover", fk.get("cover")).set("roomId", -1).set("score", fk.get("score")).set("roomName", "早晚听课堂");

                courses.add(vzk);
                if (allCourse.get("" + doDay + 300) == null) {
                    List<Record> tempList = new ArrayList<>();
                    allCourse.put("" + doDay + 300, tempList);
                }
                allCourse.get("" + doDay + 300).add(vzk);

            }
        }

        //遍历，为辅课加上主课ID
        for (Record record : courses) {
            if (record.getInt("doSlot") != 300) {
                Record zk = allCourse.get("" + record.getInt("doDay") + 300) == null ? null : allCourse.get("" + record.getInt("doDay") + 300).get(0);
                if (zk != null) {
                    record.set("parentCourseId", zk.get("classCourseId"));
                }
            }
        }
        return courses;
    }

    /**
     * 根据辅课列表确认是否已完成所有课程
     *
     * @param doSlot100
     * @param doSlot500
     * @return
     */
    private Date getCompleteTime(List<Record> doSlot100, List<Record> doSlot500) {
        List<Record> fkList = new ArrayList<>();
        if (doSlot100 != null)
            fkList.addAll(doSlot100);
        if (doSlot500 != null)
            fkList.addAll(doSlot500);
        Date lastCompleteTime = null;
        for (Record r : fkList) {
            if (r.get("completeTime") == null)
                return null;
            else if (lastCompleteTime == null) {
                lastCompleteTime = r.getDate("completeTime");
            } else if (r.getDate("completeTime").getTime() > lastCompleteTime.getTime()) {
                lastCompleteTime = r.getDate("completeTime");
            }
        }
        return lastCompleteTime;
    }


    /**
     * 补充虚拟主课（复习课堂），并为辅课加上对应主课ID
     */
    private List<Record> fillZkAndAddZkId(List<Record> courses) {
        //System.out.println(courses);
        Map<String, List<Record>> allCourse = new HashMap<>(); //key: gradeId+doday+doSlot
        Set<Integer> allDoDay = new HashSet<>();
        for (Record course : courses) {
            List<Record> tempList = allCourse.get("" + course.getInt("doDay") + course.getInt("doSlot"));
            if (tempList == null) {
                tempList = new ArrayList<>();
                allCourse.put("" + course.getInt("doDay") + course.getInt("doSlot"), tempList);
            }
            allDoDay.add(course.getInt("doDay"));
            tempList.add(course);
        }

        //-----2.遍历补充虚拟主课
        for (Integer doDay : allDoDay) {
            Record zk = allCourse.get("" + doDay + 300) == null ? null : allCourse.get("" + doDay + 300).get(0);
            if (zk == null) {
                Record fk = allCourse.get("" + doDay + 100) == null ? null : allCourse.get("" + doDay + 100).get(0);
                if (fk == null)
                    fk = allCourse.get("" + doDay + 500) == null ? null : allCourse.get("" + doDay + 500).get(0);
                if (fk == null)
                    continue;
                //判断是否已经学完辅课
                Object completeTime = getCompleteTime(allCourse.get("" + doDay + 100), allCourse.get("" + doDay + 500));
                Record vzk;

                vzk = new Record().set("doDay", doDay).set("doSlot", 300).set("classCourseId", -1).set("completeTime", completeTime).set("cover", fk.get("cover")).set("roomId", -1).set("score", fk.get("score")).set("roomName", "复习课堂");

                courses.add(vzk);
                if (allCourse.get("" + doDay + 300) == null) {
                    List<Record> tempList = new ArrayList<>();
                    allCourse.put("" + doDay + 300, tempList);
                }
                allCourse.get("" + doDay + 300).add(vzk);

            }
        }


        //遍历，为辅课加上主课ID

        for (Record c : courses) {
            if (c.getInt("doSlot") != 300) {
                Record zk = allCourse.get("" + c.getInt("doDay") + 300) == null ? null : allCourse.get("" + c.getInt("doDay") + 300).get(0);
                if (null != zk) {
                    c.set("parentCourseId", zk.get("classCourseId"));
                }
            }
        }
        return courses;
    }

    public void cancelImportantGrade(int sId, int gId) {
        Db.update("UPDATE class_grades_rela SET type = 0 WHERE class_student_id = ? AND class_grades_id = ?", sId, gId);
    }

    public List<ClassStudent> getListByMemberId(String account) {
        //TODO 1账户直接申请成为凡豆伴的学生 2账户申请绑定RFID卡的学生
        return ClassStudent.dao.find("SELECT s.* FROM class_student s LEFT JOIN device_relation dr ON dr.epal_id = s.epal_id WHERE dr.friend_id = ? AND dr.isbind = 1  AND dr.id IS NOT NULL", account);
    }


    public List<ClassStudent> getUnionStudentList(String account, int memberId) {
        // 1账户直接申请成为凡豆伴的学生 2账户申请绑定RFID卡的学生 3 机器人绑定的学生

        Kv cond = Kv.by("account", account).set("memberId", memberId);

        return ClassStudent.dao.find(Db.getSqlPara("student.getUnionStudentList", cond));
    }

    public List<Record> getUnionStudentListWithSchool(String account, int memberId) {
        // 1账户直接申请成为凡豆伴的学生 2账户申请绑定RFID卡的学生 3 机器人绑定的学生

        List<ClassStudent> students = getUnionStudentList(account, memberId);
        List<Integer> studentIds = ConvetUtil.models2IntList(students, "id");
        Map<Integer, Record> schoolForStudent = agentSchoolService.getSchoolByStudentIds(studentIds);
        List<Record> studentMakeUp = new ArrayList<>();
        for (ClassStudent student : students) {
            Record t = student.toRecord();
            Record s = schoolForStudent.get(student.getId());
            t.set("school", s == null ? new Record() : s);
            studentMakeUp.add(t);
        }
        return studentMakeUp;
    }


    public List<StdDiyStudyDay> getAllRule(int studentId) {
        return StdDiyStudyDay.dao.find("SELECT * FROM std_diy_study_day WHERE std_id = ?", studentId);
    }

    public List<Record> getDeviceNickNames(List<Integer> studentIds) {

        if (xx.isAllEmpty(studentIds)) {
            return new ArrayList<Record>();
        }

        String stuIds = studentIds.get(0).toString();
        for (int i = 1; i < studentIds.size(); i++) {
            stuIds += "," + studentIds.get(i);
        }

        String sql = "SELECT a.id,b.nickname,ifnull(b.device_type,'') as device_type,ifnull(b.epal_pwd,'') as epal_pwd FROM class_student a,device b WHERE a.epal_id = b.epal_id AND a.id in (" + stuIds + ")";

        return Db.find(sql);
    }

    public List<ClassStudent> getFandouList(String account, Integer memberId) {

        List<ClassStudent> classStudents = new ArrayList<ClassStudent>();

        StringBuffer studentSql = new StringBuffer(
                "SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.sort_id,");
        studentSql.append("IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,");
        studentSql.append(
                "a.member_id,a.degree_of_difficulty,a.avatar,a.`status`,c.epal_pwd,c.nickname FROM class_student");
        studentSql.append(
                " a,device_relation b,device c WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id ");
        studentSql.append("= ? and b.isbind = 1 UNION SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.");
        studentSql.append(
                "sort_id,IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,a.member_id,");
        studentSql.append(
                "a.degree_of_difficulty,a.avatar,a.`status`,b.epal_pwd,b.nickname FROM `class_student` a,device b WHERE ");
        studentSql.append("a.epal_id = b.epal_id AND a.member_id = ?");

        classStudents = ClassStudent.dao.find(studentSql.toString(), account, memberId);

        return classStudents;

    }
}
