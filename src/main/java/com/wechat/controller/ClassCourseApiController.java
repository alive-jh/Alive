package com.wechat.controller;

import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.*;
import com.wechat.entity.dto.ClassCourseEvaluateDto;
import com.wechat.service.ClassCourseService;
import com.wechat.service.LessonService;
import com.wechat.util.BeanModifyFilter;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


@Controller
@RequestMapping("api")
public class ClassCourseApiController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ClassCourseService classCourseService;

    /**
     * 添加或者修改在线课堂课程表
     *
     * @param request
     * @param response
     */
    @RequestMapping("saveClassCourse")
    public void saveClassCourse(HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            String id = ParameterFilter.emptyFilter(null, "id", request);
            String doTitle = ParameterFilter.emptyFilter(null, "doTitle",
                    request);
            String cover = ParameterFilter.emptyFilter(null, "cover", request);
            String doSlot = ParameterFilter
                    .emptyFilter(null, "doSlot", request);
            String doDay = ParameterFilter.emptyFilter(null, "doDay", request);
            String classRoomId = ParameterFilter.emptyFilter(null,
                    "classRoomId", request);
            String classGradesId = ParameterFilter.emptyFilter(null,
                    "classGradesId", request);
            Timestamp createTime = new Timestamp(System.currentTimeMillis());
            ClassCourse classCourse = new ClassCourse(doTitle,
                    ParameterFilter.nullFilter(doSlot),
                    ParameterFilter.nullFilter(doDay),
                    ParameterFilter.nullFilter(classRoomId),
                    ParameterFilter.nullFilter(classGradesId), cover, createTime);
            if (id != null) {
                classCourse = (ClassCourse) BeanModifyFilter.modifyFilter(classCourse, this.lessonService.getClassCourse(Integer.parseInt(id)));

            }
            this.lessonService.saveClassCourse(classCourse);
            JsonResult.JsonResultInfo(response, classCourse);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 添加或者修改在线课堂课程表机器人学习游标
     *
     * @param request
     * @param response
     */
    @RequestMapping("saveClassCourseSchedule")
    public void saveClassCourseSchedule(HttpServletRequest request,
                                        HttpServletResponse response) {
        try {
            String id = ParameterFilter.emptyFilter(null, "id", request);
            String studentId = ParameterFilter.emptyFilter(null, "studentId",
                    request);
            String startTime = ParameterFilter.emptyFilter(null, "startTime",
                    request);
            String lastTime = ParameterFilter.emptyFilter(null, "lastTime",
                    request);
            String doDay = ParameterFilter.emptyFilter(null, "doDay", request);
            String classRoomId = ParameterFilter.emptyFilter(null,
                    "classRoomId", request);
            String classCourseId = ParameterFilter.emptyFilter(null,
                    "classCourseId", request);
            String classGradesId = ParameterFilter.emptyFilter(null,
                    "classGradesId", request);

            String sort = ParameterFilter.emptyFilter(null,
                    "sort", request);

            String currentSort = ParameterFilter.emptyFilter(null,
                    "currentSort", request);

            ClassCourseSchedule classCourseSchedule = new ClassCourseSchedule(
                    ParameterFilter.nullFilter(studentId),
                    Timestamp.valueOf(startTime), Timestamp.valueOf(lastTime),
                    ParameterFilter.nullFilter(doDay),
                    ParameterFilter.nullFilter(classRoomId),
                    ParameterFilter.nullFilter(classCourseId),
                    ParameterFilter.nullFilter(classGradesId),
                    ParameterFilter.nullFilter(sort));
            if (id != null) {
                classCourseSchedule = (ClassCourseSchedule) BeanModifyFilter.modifyFilter(classCourseSchedule, this.lessonService.getClassCourseSchedule(Integer.parseInt(id)));

            }
            ClassCourseSchedule classCourseScheduleTemp = this.lessonService.getClassCourseSchedule(Integer.parseInt(id));
            if (null != sort && !"".equals(sort)) {

                Integer nextSortId = classCourseScheduleTemp.getSort() + 1;
                classCourseSchedule.setSort(nextSortId);
                //发送cmd
                ClassStudent classStudent = this.lessonService.getStudentByStudentId(studentId);
                EasemobUtil easemobUtil = new EasemobUtil();
                String action = "lo_update_progress:{\"classId\":" + classCourseSchedule.getClassGradesId() + "}";
                String[] user = new String[1];
                user[0] = classStudent.getEpalId();
                easemobUtil.sendMessage(user, action);

            }
            if (null != currentSort && !"".equals(currentSort)) {
                if (Integer.parseInt(currentSort) == classCourseScheduleTemp.getSort()) {
                    this.lessonService.saveClassCourseSchedule(classCourseSchedule);
                    JsonResult.JsonResultInfo(response, classCourseSchedule);
                } else {
                    JsonResult.JsonResultInfo(response, classCourseScheduleTemp);

                }
            } else {
                this.lessonService.saveClassCourseSchedule(classCourseSchedule);
                JsonResult.JsonResultInfo(response, classCourseSchedule);
            }

        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 添加或者修改在线课堂学习记录
     *
     * @param request
     * @param response
     */
    @RequestMapping("saveClassCourseRecord")
    public void saveClassCourseRecord(HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            //class_course_record 表
            String id = ParameterFilter.emptyFilter(null, "id", request);
            String studentId = ParameterFilter.emptyFilter(null, "studentId", request);
            String classGradesId = ParameterFilter.emptyFilter(null, "classGradesId", request);
            String classCourseId = ParameterFilter.emptyFilter(null, "classCourseId", request);
            String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
            String complete = ParameterFilter.emptyFilter(null, "complete", request);
            String score = ParameterFilter.emptyFilter(null, "score", request);
            String doTitle = ParameterFilter.emptyFilter(null, "doTitle", request);
            String usedTime = ParameterFilter.emptyFilter(null, "usedTime", request);
            String completeTime = ParameterFilter.emptyFilter(null, "completeTime", request);
            String integral = ParameterFilter.emptyFilter(null, "integral", request); //积分

            //判断下这节课有没有学习过，学习过了做更新操作，[并且添加到学习记录附表]


            ClassCourseRecord classCourseRecord = new ClassCourseRecord(
                    ParameterFilter.nullFilter(studentId),
                    ParameterFilter.nullFilter(classGradesId),
                    ParameterFilter.nullFilter(classCourseId),
                    ParameterFilter.nullFilter(classRoomId),
                    ParameterFilter.nullFilter(complete), score, doTitle,
                    ParameterFilter.nullFilter(usedTime),
                    Timestamp.valueOf(completeTime));
            if (id != null) {
                classCourseRecord = (ClassCourseRecord) BeanModifyFilter.modifyFilter(classCourseRecord, this.classCourseService.getClassCourseRecord(Integer.parseInt(id)));
            }
            this.classCourseService.saveClassCourseRecord(classCourseRecord);

            if (integral != null) {
                //增加积分
                this.classCourseService.saveIntegralByStudent(classCourseRecord.getStudentId(), classCourseRecord.getClassCourseId(), Integer.parseInt(integral), Integer.parseInt(usedTime));
            }

            JsonResult.JsonResultInfo(response, classCourseRecord);

        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }


    /**
     * 添加或者修改在线课堂学习记录老师评语
     *
     * @param request
     * @param response
     */
    @RequestMapping("saveClassCourseReply")
    public void saveClassCourseReply(HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            String id = ParameterFilter.emptyFilter(null, "id", request);
            String gradesId = ParameterFilter.emptyFilter(null, "gradesId",
                    request);
            String recordId = ParameterFilter.emptyFilter(null,
                    "recordId", request);
            String teacherId = ParameterFilter.emptyFilter(null,
                    "teacherId", request);
            String replyText = ParameterFilter.emptyFilter(null,
                    "replyText", request);
            String replyVoice = ParameterFilter.emptyFilter(null, "replyVoice",
                    request);
            String studentVoice = ParameterFilter.emptyFilter(null, "studentVoice", request);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String replyTime = ParameterFilter.emptyFilter(df.format(new Timestamp(System.currentTimeMillis())),
                    "replyTime", request);

            ClassCourseReply classCourseReply = new ClassCourseReply(
                    ParameterFilter.nullFilter(gradesId),
                    ParameterFilter.nullFilter(recordId),
                    ParameterFilter.nullFilter(teacherId),
                    replyText, replyVoice, studentVoice,
                    Timestamp.valueOf(replyTime));
            if (id != null) {
                classCourseReply = (ClassCourseReply) BeanModifyFilter
                        .modifyFilter(classCourseReply,
                                this.classCourseService
                                        .getClassCourseReply(Integer
                                                .parseInt(id)));
            }
            this.classCourseService.saveClassCourseReply(classCourseReply);
            JsonResult.JsonResultInfo(response, classCourseReply);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 添加或者修改学生自定义自习时间
     *
     * @param request
     * @param response
     */
    @RequestMapping("saveClassSlot")
    public void saveClassSlot(HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            String id = ParameterFilter.emptyFilter(null, "id", request);
            String epalId = ParameterFilter
                    .emptyFilter(null, "epalId", request);
            String doTime = ParameterFilter
                    .emptyFilter(null, "doTime", request);
            String doSlot = ParameterFilter
                    .emptyFilter(null, "doSlot", request);
            String name = ParameterFilter.emptyFilter(null, "name", request);
            ClassSlot classSlot = new ClassSlot(epalId, doTime,
                    ParameterFilter.nullFilter(doSlot), name);
            if (id != null) {
                classSlot = (ClassSlot) BeanModifyFilter.modifyFilter(
                        classSlot,
                        this.lessonService.getClassSlot(Integer.parseInt(id)));
            }
            this.lessonService.saveClassSlot(classSlot);
            JsonResult.JsonResultInfo(response, classSlot);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID获取学生自定义自习时间列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findClassSlots")
    public void findClassSlots(HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("epalId",
                    ParameterFilter.emptyFilter("-1", "epalId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            Page<ClassSlot> classSlots = this.lessonService.findClassSlots(map);
            JsonResult.JsonResultInfo(response, classSlots);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID获取机器人上传课程表学习进度列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findClassCourseSchedulesByStudentId")
    public void findClassCourseSchedulesByStudentId(HttpServletRequest request,
                                                    HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            Page<ClassCourseSchedule> classCourseSchedules = this.lessonService
                    .findClassCourseSchedules(map);
            JsonResult.JsonResultInfo(response, classCourseSchedules);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID获取机器人今天的在线课堂学习记录列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findToDayClassCourseRecordsByStudentId")
    public void findToDayClassCourseRecordsByStudentId(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            Page<ClassCourseRecord> classCourseRecords = this.classCourseService
                    .findToDayClassCourseRecordsByStudentId(map);
            JsonResult.JsonResultInfo(response, classCourseRecords);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID和班级ID获取机器人的在线课堂学习记录列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findClassCourseRecordsByStudentIdAndGradesId")
    public void findClassCourseRecordsByStudentIdAndGradesId(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("classGradesId",
                    ParameterFilter.emptyFilter("-1", "classGradesId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            Page<ClassCourseRecord> classCourseRecords = this.classCourseService
                    .findClassCourseRecordsByStudentIdAndGradesId(map);
            JsonResult.JsonResultInfo(response, classCourseRecords);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID和班级ID获取机器人的在线课堂课程表学习总时长和总评分
     *
     * @param request
     * @param response
     */
    @RequestMapping("findClassCourseEvaluateByStudentIdAndGradesId")
    public void findClassCourseEvaluateByStudentIdAndGradesId(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("classGradesId",
                    ParameterFilter.emptyFilter("-1", "classGradesId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            ClassCourseEvaluateDto classCourseEvaluateDtos = this.classCourseService
                    .findClassCourseEvaluateByStudentIdAndGradesId(map);
            JsonResult.JsonResultInfo(response, classCourseEvaluateDtos);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据在线课堂课堂表学习记录ID（RecordID）获取机器人的在线课堂学习记录老师批复数据列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findClassCourseReplysByRecordId")
    public void findClassCourseReplysByRecordId(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("recordId",
                    ParameterFilter.emptyFilter("-1", "recordId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            Page<ClassCourseReply> classCourseReplys = this.classCourseService
                    .findClassCourseReplysByRecordId(map);
            JsonResult.JsonResultInfo(response, classCourseReplys);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 移除在线课堂课程表里的在线课堂
     * 备注：如果存在学习记录，则不运行删除。
     *
     * @param request
     * @param response
     */

    @RequestMapping("delClassRoomFromClassCourse")
    public void delClassRoomFromClassCourse(HttpServletRequest request,
                                            HttpServletResponse response) {
        try {
            String classCourseId = ParameterFilter.emptyFilter("",
                    "classCourseId", request);
            HashMap map = new HashMap();
            map.put("classCourseId", classCourseId);
            JSONObject result = new JSONObject();
            boolean isExites = this.lessonService.findClassCourseRecord(classCourseId);
            if (true == isExites) {
                result.put("code", 405);
                result.put("msg", "delete error!");

            } else {
                //从班级移除在线课堂
                result.put("code", 200);
                result.put("msg", "delete success!");
                this.lessonService.delClassRoomFromClassCourse(map);
            }


            JsonResult.JsonResultInfo(response, result);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据班级ID获取老师制作的班级在线课堂课程表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findClassCourses")
    public void findClassCourses(HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("classGradesId",
                    ParameterFilter.emptyFilter("-1", "classGradesId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            Page<ClassCourse> classCourses = this.lessonService
                    .findClassCourses(map);
            JsonResult.JsonResultInfo(response, classCourses);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID获取加入的班级在线课堂课程表
     *
     * @param request
     * @param response
     */
    @RequestMapping("findJoinedClassCoursesByStudentId")
    public void findJoinedClassCoursesByStudentId(HttpServletRequest request,
                                                  HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            HashMap<String, Object> classCourses = this.classCourseService
                    .findJoinedClassCoursesByStudentId(map);
            JsonResult.JsonResultInfo(response, classCourses);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }
/**************************通过学生ID获取学生所属班级列表（start）*************************************/

    /**
     * 根据学生ID获取加入的班级在线课堂课程表(列表形式，机器人使用)
     *
     * @param request
     * @param response joinStatus：状态控制
     *                 classOpenDate：班级开班时间
     *                 <p>
     *                 1、没有开班时间：
     *                 直接按照加入班级时间和doDay计算；
     *                 2、有开班时间：
     *                 2.1、joinStatus状态为0，课程安排按照班级开班时间和doDay计算。（晚进班补课）
     *                 2.2、joinStatus状态为1（晚进班不补课），
     *                 （1）、加入时间小于等于开班时间，使用开班时间计算课程表；
     *                 （2）、加入时间大于开班时间，按照加入班级时间计算课程表。
     */
    @RequestMapping("findJoinedClassCoursesListByStudentId")
    public void findJoinedClassCoursesListByStudentId(HttpServletRequest request,
                                                      HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            JSONArray classCourses = this.classCourseService
                    .findJoinedClassCoursesListByStudentId(map);
            JsonResult.JsonResultInfo(response, classCourses);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 根据学生ID获取加入的班级在线课堂课程表（学生首页数据集，APP和小程序使用）
     *
     * @param request
     * @param response
     */
    @RequestMapping("findJoinedClassCoursesIndexByStudentIdOptimization")
    public void findJoinedClassCoursesIndexByStudentIdOptimization(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            HashMap<String, Object> classCourses = this.classCourseService
                    .findJoinedClassCoursesIndexByStudentIdOptimization(map);
            JsonResult.JsonResultInfo(response, classCourses);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }


    /**
     * 根据学生ID获取加入的班级在线课堂课程表（小程序使用）
     *
     * @param request
     * @param response
     */
    @RequestMapping("findJoinedClassCoursesIndexInfo")
    public void findJoinedClassCoursesIndexInfo(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            HashMap<String, Object> classCourses = this.classCourseService
                    .findJoinedClassCoursesIndexInfo(map);
            JsonResult.JsonResultInfo(response, classCourses);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }


    /**************************通过学生ID获取学生所属班级列表  （end）*************************************/

    /**
     * 根据学生ID获取加入的班级在线课堂课程表（学生首页数据集）
     *
     * @param request
     * @param response
     */
    @RequestMapping("findJoinedClassCoursesIndexByStudentId")
    public void findJoinedClassCoursesIndexByStudentId(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("studentId",
                    ParameterFilter.emptyFilter("-1", "studentId", request));
            map.put("page", ParameterFilter.emptyFilter("1", "page", request));
            map.put("pageSize",
                    ParameterFilter.emptyFilter("30", "pageSize", request));
            HashMap<String, Object> classCourses = this.classCourseService
                    .findJoinedClassCoursesIndexByStudentId(map);
            JsonResult.JsonResultInfo(response, classCourses);
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }


    /**
     * 批量添加课堂到课程表(废)
     *
     * @param request
     * @param response
     */

    // @RequestMapping("saveBatchClassRoomToClassCourse")
    // public void saveBatchClassRoomToClassCourse(HttpServletRequest request,
    // HttpServletResponse response) {
    // try {
    // HashMap<String, String> map = new HashMap<String, String>();
    // String doTitles = ParameterFilter.emptyFilter(null, "doTitles",
    // request);
    // String doSlots = ParameterFilter.emptyFilter(null, "doSlots", request);
    // String doDays = ParameterFilter.emptyFilter(null, "doDays", request);
    // String classRoomIds = ParameterFilter.emptyFilter(null,
    // "classRoomIds", request);
    // String classGradesIds = ParameterFilter.emptyFilter(null,
    // "classGradesIds",
    // request);
    // map.put("doTitles", doTitles);
    // map.put("doSlots", doSlots);
    // map.put("doDays", doDays);
    // map.put("classRoomIds", classRoomIds);
    // map.put("classGradesIds", classGradesIds);
    // if(lessonService.saveBatchClassRoomToClassCourse(map)){
    // JsonResult.JsonResultInfo(response, "OK");
    // }else{
    // JsonResult.JsonResultError(response, 1000);
    // }
    // } catch (Exception e) {
    // JsonResult.JsonResultError(response, 1000);
    // e.printStackTrace();
    // }
    // }

    /**
     * 批量添加课堂到课程表
     *
     * @param request
     * @param response
     */

    @RequestMapping("saveBatchClassCourse")
    public void saveBatchClassCourse(HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            String classCoursesJson = ParameterFilter.emptyFilter(null,
                    "classCoursesJson", request);
            map.put("classCoursesJson", classCoursesJson);
//			//System.out.println(classCoursesJson);
            ArrayList<ClassCourse> classCourses = lessonService
                    .saveBatchClassCourse(map);
            if (classCourses.size() > 0) {
                JsonResult.JsonResultInfo(response, classCourses);
            } else {
                JsonResult.JsonResultError(response, 1000);
            }
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 通过学生ID获取退出的班级列表
     *
     * @param request
     * @param response
     */

    @RequestMapping("getSignOutClassGradesListBystudentId")
    public void getSignOutClassGradesListBystudentId(HttpServletRequest request,
                                                     HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            String studentId = ParameterFilter.emptyFilter(null,
                    "studentId", request);
            String page = ParameterFilter.emptyFilter("1",
                    "page", request);
            String pageSize = ParameterFilter.emptyFilter("100",
                    "pageSize", request);
            map.put("studentId", studentId);
            map.put("page", page);
            map.put("pageSize", pageSize);
            if (null != studentId && !"".equals(studentId)) {
                Page result = lessonService
                        .getSignOutClassGradesListBystudentId(map);

                JsonResult.JsonResultInfo(response, result);
            } else {
                JsonResult.JsonResultError(response, 1000);
            }

        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 退出或者删除虚拟班级
     *
     * @param request
     * @param response
     */

    @RequestMapping("quitGradesByStudentId")
    public void quitGradesByStudentId(HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            String gradesId = ParameterFilter.emptyFilter("",
                    "gradesId", request);
            String studentId = ParameterFilter.emptyFilter("",
                    "studentId", request);
            String gradesStatus = ParameterFilter.emptyFilter("",
                    "gradesStatus", request);
            map.put("gradesId", gradesId);
            map.put("studentId", studentId);
            map.put("gradesStatus", gradesStatus);
            if ("".equals(gradesId) || "".equals(studentId)) {
                JSONObject result = new JSONObject();
                result.put("code", "408");
                result.put("msg", "parameter error!");
                JsonResult.JsonResultInfo(response, result);
            } else {
                //缺少参数gradesStatus 的判断
                JSONArray gradesStatusList = new JSONArray();
                gradesStatusList.add("-1");
                gradesStatusList.add("0");
                gradesStatusList.add("1");
                if (gradesStatusList.contains(gradesStatus)) {
                    lessonService.quitGradesByStudentId(map);
                    JSONObject result = new JSONObject();
                    result.put("code", "200");
                    result.put("msg", "update gradesStatus success!");
                    JsonResult.JsonResultInfo(response, result);
                } else {
                    JSONObject result = new JSONObject();
                    result.put("code", "409");
                    result.put("msg", "gradesStatus error!");
                    JsonResult.JsonResultInfo(response, result);
                }

            }


        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }

    /**
     * 上传在线课堂课程评分
     *
     * @param request
     * @param response
     */

    @RequestMapping("saveClassCourseScoreRecord")
    public void saveClassCourseScoreRecord(HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            String classCourseRecordId = ParameterFilter.emptyFilter(null,
                    "classCourseRecordId", request);
            String score = ParameterFilter.emptyFilter(null,
                    "score", request);

            String audioUrl = ParameterFilter.emptyFilter(null,
                    "audioUrl", request);

            String recordDate = ParameterFilter.emptyFilter(null,
                    "recordDate", request);

            String type = ParameterFilter.emptyFilter("default",
                    "type", request);

            String accuracy = ParameterFilter.emptyFilter("",
                    "accuracy", request); // 发音得分

            String integrity = ParameterFilter.emptyFilter("",
                    "integrity", request);//完整度得分

            String fluency = ParameterFilter.emptyFilter("",
                    "fluency", request); // 流利度得分

            String timeCost = ParameterFilter.emptyFilter("",
                    "timeCost", request);//学习花费时长
            String wordList = ParameterFilter.emptyFilter("",
                    "wordList", request);//

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

            String insertDate = df.format(new Date()); //取当前时间

            ClassCourseScoreRecord classCourseScoreRecord = new ClassCourseScoreRecord();
            classCourseScoreRecord.setClassCourseRecordId(Integer.parseInt(classCourseRecordId));
            classCourseScoreRecord.setScore(score);
            classCourseScoreRecord.setAudioUrl(audioUrl);
            classCourseScoreRecord.seInRecordDate(recordDate);
            classCourseScoreRecord.setType(type);
            classCourseScoreRecord.setInsertDate(insertDate);
            classCourseScoreRecord.setTimeCost(timeCost);
            classCourseScoreRecord.setAccuracy(accuracy);
            classCourseScoreRecord.setFluency(fluency);
            classCourseScoreRecord.setIntegrity(integrity);
            classCourseScoreRecord.setWordList(wordList);

            lessonService.saveClassCourseScoreRecord(classCourseScoreRecord);
            JsonResult.JsonResultInfo(response, "ok");

        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
            e.printStackTrace();
        }
    }


}
