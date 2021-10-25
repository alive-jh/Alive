package com.wechat.jfinal.api.stdI;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.CalculateUtil;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.model.ClassCourseSchedule;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.service.*;

import java.text.ParseException;
import java.util.*;

//import com.wechat.jfinal.common.utils.ConvetUtil;
//import com.wechat.jfinal.model.CourseSchedule;
//import com.wechat.jfinal.model.Grades;
//import com.wechat.jfinal.model.Slot;
//import com.wechat.jfinal.model.Student;

public class CourseCtr extends Controller {
    static GradesService gradesService = new GradesService();
    static SlotService slotService = new SlotService();
    static StudentService studentService = new StudentService();
    static ClassCourseService classCourseService = new ClassCourseService();
    static ClassCourseScheduleService courseScheduleService = new ClassCourseScheduleService();
    private static final IntegralLevelInfoService integralService = new IntegralLevelInfoService();
    private static final LevalPicInfoService levalPicInfoService = new LevalPicInfoService();

    public void getStdScheduleNew() {
        //新建返回对象
        Map<String, Object> rsp = new HashMap<>();
        try {
            int stdId = getParaToInt("stdId");
            Date index = getParaToDate("date");
            int forward = getParaToInt("forward");//往前取天数 -1 全部
            int backward = getParaToInt("backward");//往后取天数 -1 全部

            String types = getPara("type", "1-2"); //所取班级类型  1 虚拟班 2 电教班 3 basic班


            Map<Integer, Integer> starts = new HashMap<>();
            Map<Integer, Integer> ends = new HashMap<>();
            Map<Integer, List<Record>> courseMap = new HashMap<>();
            List<Integer> gradeIds = new ArrayList<>();

            //获得学生参与的班级信息
            List<ClassGrades> grades = gradesService.getJoinGradesNew(stdId, types, 1);
            //获得学生do_slot
            List<Record> slots = slotService.getSlotsNew(stdId);
            //获得学生相关信息
            ClassStudent std = studentService.getById(stdId);
            Date openDate;
            //获得每个班级的doDay区间
            for (ClassGrades grade : grades) {
                gradeIds.add(grade.getId());

                if (null == grade.getClassOpenDate() || "".equals(grade.getClassOpenDate())) {
                    openDate = grade.getCreateTime();
                    grade.setClassOpenDate("");
                } else {
                    openDate = ConvetUtil.str2Date(grade.getClassOpenDate());
                }
                Map<String, Integer> mapTemp = classCourseService.getDoDaySection(openDate, index, backward, forward);

                starts.put(grade.getId(), mapTemp.get("start"));
                ends.put(grade.getId(), mapTemp.get("end"));
            }
            //分别查出，每个班级的课程
            for (ClassGrades grade : grades) {
                int gradeId = grade.getInt("id");
                courseMap.put(gradeId, studentService.getSchedule(stdId, gradeId, starts.get(gradeId), ends.get(gradeId)));
            }
            //查出当前学习进度,组成map
            List<ClassCourseSchedule> schedules = courseScheduleService.get(stdId, gradeIds);

            Map<Integer, ClassCourseSchedule> scheduleMap = new HashMap<>();
            for (ClassCourseSchedule s : schedules) {
                scheduleMap.put(s.getClassGradesId(), s);
            }
            //组装数据返回
            for (ClassGrades grade : grades) {
                int gradeId = grade.getId();
                grade.set("classCourseList", courseMap.get(gradeId));
                grade.set("doSlotData", slots);
                ClassCourseSchedule schedule = scheduleMap.get(gradeId);
                grade.set("scheduleId", schedule.getId());
                grade.set("scheduleDoDay", schedule.getDoDay());
            }

            rsp.put("data", grades);
            rsp.put("integral", std.getIntegral() == null ? 0 : std.getIntegral());
            rsp.put("contribution", std.getInt("contribution") == null ? 0 : std.getInt("contribution"));
            rsp.put("code", 200);
        } catch (Exception e) {

            rsp.put("msg", "该服务暂不可用，如急需设置班级上课时间，请联系凡豆工作人员");
            renderJson(rsp);
            e.printStackTrace();
        }
        renderJson(rsp);
    }

    @Deprecated
    public void getStdSchedule() {
        //新建返回对象
        Map<String, Object> rsp = new HashMap<>();

        boolean isSection = true;
        try {
            int forward = -1;//往前取天数
            int backward = -1;//往后取天数

            int stdId = getParaToInt("stdId", 0);
            Date now = getParaToDate("date", null);

            if (now != null) {
                forward = getParaToInt("forward");
                backward = getParaToInt("backward");
            }
            String type = getPara("type", "1-2");

            Map<Integer, Integer> starts = new HashMap<>();
            Map<Integer, Integer> ends = new HashMap<>();
            Map<Integer, List<Record>> courseMap = new HashMap<>();

            //获得学生参与的班级信息
//            List<Record> grades = new ArrayList<>();
            List<Record> grades = gradesService.getJoinGrades(stdId, type, 1);
            //获得学生do_slot
            List<Record> slots = slotService.getSlots(stdId);
            //获得学生相关信息
            Record std = Db.findFirst("SELECT * FROM class_student WHERE id = ?", stdId);

            if (now == null || backward == -1 || forward == -1)
                isSection = false;

            //获得每个班级的doDay区间
            if (isSection) {
                for (Record grade : grades) {
                    //获得当前doDay
                    if (null == grade.getStr("classOpenDate") || "".equals(grade.getStr("classOpenDate"))) {
                        starts.put(grade.getInt("classGradesId"), 0);
                        ends.put(grade.getInt("classGradesId"), 9999);
                        continue;
                    }
                    Date openDate = ConvetUtil.str2Date(grade.getStr("classOpenDate"));
                    Long betweenDays = (long) ((now.getTime() - openDate.getTime()) / (1000 * 60 * 60 * 24) + 0.5);
                    int nowDoDay = betweenDays.intValue() + 1;
                    Integer doDayStart;
                    Integer doDayEnd;
                    doDayStart = nowDoDay - (backward == -1 ? 0 : backward);
                    doDayEnd = nowDoDay + (forward == -1 ? 99999 : forward);

                    starts.put(grade.getInt("classGradesId"), doDayStart);
                    ends.put(grade.getInt("classGradesId"), doDayEnd);
                }
                //分别查出，每个班级的课程
                for (Record grade : grades) {

                    int gradeId = grade.getInt("classGradesId");
                    courseMap.put(gradeId, studentService.getSchedule(stdId, gradeId, starts.get(gradeId), ends.get(gradeId)));
                }
            } else {
                //一次查出所有班级的所有课程，并组成map
                courseMap = studentService.getSchedule(stdId, ConvetUtil.records2List(grades, "classGradesId"));

            }

            //查出当前学习进度,组成map
            String sql = "SELECT * FROM class_course_schedule  WHERE student_id = ? AND class_grades_id in (";
            sql = CalculateUtil.sqlInRecordsColumn(sql, grades, "classGradesId") + ")";
            List<Record> schedules = Db.find(sql, stdId);
            Map<Integer, Record> scheduleMap = new HashMap<>();
            for (Record s : schedules) {
                scheduleMap.put(s.getInt("class_grades_id"), s);
            }
            //组装数据返回
            for (Record grade : grades) {
                int gradeId = grade.getInt("classGradesId");
                grade.set("classCourseList", courseMap.get(gradeId) == null ? new ArrayList<>() : courseMap.get(gradeId));
                grade.set("doSlotData", slots);
                Record schedule = scheduleMap.get(gradeId);
                grade.set("isComplete", false);
                if (schedule == null) {
                    grade.set("scheduleId", null);
                    grade.set("scheduleDoDay", null);
                } else {
                    grade.set("isComplete", true);
                    grade.set("scheduleId", schedule.getInt("id"));
                    grade.set("scheduleDoDay", schedule.getInt("do_day"));
                }

            }

            int integral = std.getInt("integral") == null ? 0 : std.getInt("integral");

            rsp.put("data", grades);
            rsp.put("integral", integral);
            rsp.put("levelPic", integralService.getByIntegral(integral));
            rsp.put("contribution", std.getInt("contribution") == null ? 0 : std.getInt("contribution"));
            rsp.put("code", 200);
        } catch (Exception e) {

            rsp.put("code", "500");
            renderJson(rsp);
            e.printStackTrace();
        }
        renderJson(rsp);
    }

    /**
     * 2019/5/21
     */
    @EmptyParaValidate(params = {"classGradesId"})
    public void getStdSchedule2() {
        int stdId = getParaToInt("stdId", 0);
        Date now = getParaToDate("date", null);
        int forward = getParaToInt("forward", -1);//往前取天数 -1 全部
        int backward = getParaToInt("backward", -1);//往后取天数 -1 全部
        int classGradesId = getParaToInt("classGradesId");

        //新建返回对象
        Map<String, Object> rsp = new HashMap<>();

        String type = getPara("type", "1-2");
        //获取班级信息
        Record grade = gradesService.getGradesAndTeacherInfo(stdId, 1, classGradesId);
        //获得学生do_slot
        List<Record> slots = slotService.getSlotsNew(stdId);
        //获得学生相关信息
        ClassStudent std = studentService.getById(stdId);

        //查出当前学习进度,组成map
        List<Integer> gradeIds = new ArrayList<>();
        gradeIds.add(classGradesId);
        List<ClassCourseSchedule> schedules = courseScheduleService.get(stdId, gradeIds);

        Date openDate;


        //获取班级的doday区间
        if (null == grade.get("classOpenDate") || "".equals(grade.get("classOpenDate"))) {
            openDate = grade.getDate("create_time");
            grade.set("classOpenDate", "");
        } else {
            openDate = ConvetUtil.str2Date(grade.getStr("classOpenDate"));
        }
        Map<String, Integer> mapTemp = null;
        Integer starts;
        Integer ends;
        List<Record> CourseList;


        if (now == null || forward == -1 || backward == -1) {
            CourseList = studentService.getSchedule2(stdId, grade.getInt("id"));
        } else {
            try {
                mapTemp = classCourseService.getDoDaySection(openDate, now, backward, forward);
                starts = mapTemp.get("start");
                ends = mapTemp.get("end");
                CourseList = studentService.getSchedule(stdId, classGradesId, starts, ends);
            } catch (ParseException e) {
                e.printStackTrace();
                renderJson(Rt.sysError());
            }
            return;
        }
        ClassCourseSchedule schedule = null;
        if (null != schedules && 0 != schedules.size()){
            schedule = schedules.get(0);
        }else {
            schedule = new  ClassCourseSchedule();
            schedule.setStudentId(stdId);
            schedule.setClassCourseId(0);
            schedule.setClassGradesId(classGradesId);
            schedule.setClassRoomId(-1);
            schedule.setDoDay(-1);
            schedule.save();
        }
        grade.set("scheduleId", schedule.getId());
        grade.set("scheduleDoDay", schedule.getDoDay());

        grade.set("classGradesId", grade.getInt("id"));
        grade.set("classCourseList", CourseList);
        grade.set("doSlotData", slots);

        rsp.put("data", grade);
        rsp.put("contribution", std.getInt("contribution") == null ? 0 : std.getInt("contribution"));
        rsp.put("integral", std.getIntegral() == null ? 0 : std.getIntegral());
        rsp.put("msg", "success");
        rsp.put("code", 200);
        renderJson(rsp);
    }


    public void isBasicStd() {

        Map<String, Object> rsp = new HashMap<>();
        try {
            int sId = getParaToInt("stdId");

            rsp.put("isBasicStd", gradesService.getJoinGrades(sId, "3", 1).size() == 0 ? 0 : 1);
            rsp.put("code", 200);
        } catch (Exception e) {
            rsp.put("code", 500);
        }
        renderJson(rsp);
    }

}
