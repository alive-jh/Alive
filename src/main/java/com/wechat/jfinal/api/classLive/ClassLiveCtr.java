package com.wechat.jfinal.api.classLive;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.Appointnum;
import com.wechat.jfinal.model.ClassLiveBooking;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassLiveCtr extends Controller {

    public void toBookLivePage() {

        Integer scheduleId = getParaToInt("scheduleId");
        Integer studentId = getParaToInt("studentId");
        String mobile = getPara("mobile", "");

        Appointnum appointnum = this.getRestoreNumber(studentId);

        if (appointnum == null) {
            appointnum = new Appointnum();
            appointnum.setStudentId(studentId);
            appointnum.setSpecificNum(1);
            appointnum.setMobile(mobile);
            appointnum.save();
        }
        
        this.setAttr("specificNum", appointnum.getSpecificNum());
        setAttr("student", studentId);
        
        ClassLiveBooking classLiveBooking = ClassLiveBooking.dao.findFirst("select * from class_live_booking where schedule_id = ? and student_id = ? and status != 0 order by id desc", scheduleId, studentId);

        if (appointnum.getSpecificNum() < 1 && classLiveBooking == null) {
            renderTemplate("/live/booking_not_specific.html");
            return;
        }

        if (classLiveBooking == null) {
            classLiveBooking = new ClassLiveBooking();
            classLiveBooking.setMobile(mobile);
            classLiveBooking.setScheduleId(scheduleId);
            classLiveBooking.setStudentId(studentId);
            classLiveBooking.setStatus(0);
        }

        //时间判断，如果超时则跳回原来页面
        if (classLiveBooking.getClassTime() != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            if (appointnum.getSpecificNum() < 1 && StringUtil.compare_date(df.format(new Date()), classLiveBooking.getClassTime()) == 1) {
            	renderTemplate("/live/booking_not_specific.html");
                return;
            } else if (StringUtil.compare_date(df.format(new Date()), classLiveBooking.getClassTime()) == 1) {
                classLiveBooking.setStatus(0);
            }

        }

        this.toView(classLiveBooking);

    }

    public void saveBooking() {
        ClassLiveBooking classLiveBooking = getBean(ClassLiveBooking.class, "", false);
        System.out.println(classLiveBooking.toString());

        Integer schoolId = ClassStudent.dao.findFirst("select agent_id from class_student where id = ?", classLiveBooking.getStudentId()).getAgentId();
        classLiveBooking.setSchoolId(schoolId);
        classLiveBooking.save();
        Db.update("update appointnum set specific_num = specific_num-1 where student_id = ?", classLiveBooking.getStudentId());
        redirect("/v2/live/toBookLivePage?mobile=" + classLiveBooking.getMobile() + "&studentId=" + classLiveBooking.getStudentId() + "&scheduleId=" + classLiveBooking.getScheduleId() + "&popup=1");
    }

    /**
     * 重新预约
     */
    public void againBooking() {
        Integer bookingId = getParaToInt("bookingId");
        ClassLiveBooking.dao.findById(bookingId).set("status", 0).update();
        ClassLiveBooking classLiveBooking = ClassLiveBooking.dao.findById(bookingId);
        System.out.println(classLiveBooking);
        Appointnum appointnum = this.getRestoreNumber(classLiveBooking.getStudentId());
        
        setAttr("student", appointnum.getStudentId());
        
        if (appointnum.getSpecificNum() < 1) {
            renderTemplate("/live/booking_not_specific.html");
            return;
        }
        this.toView(classLiveBooking);
//        redirect("/v2/live/toBookLivePage?mobile=" + classLiveBooking.getMobile() + "&studentId=" + classLiveBooking.getStudentId() + "&scheduleId=" + classLiveBooking.getScheduleId());
    }

    /**
     * 转换时间
     *
     * @param date
     * @return
     */
    private String dateFomat(String date) {
        if (date == null) {
            return "";
        }
        return date.replace(" ", "T");
    }


    public void bookingInfo() {
        Integer studentId = getParaToInt("studentId");
        Appointnum appointnum = this.getRestoreNumber(studentId);
        if (appointnum == null) {
            Appointnum newAppointnum = new Appointnum();
            newAppointnum.setStudentId(studentId);
            newAppointnum.save();
            appointnum = new Appointnum();
            appointnum.setSpecificNum(1);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("specificNum", appointnum.getSpecificNum());
        List<Record> result = Db.find("select a.appoint_time,a.spare_time,a.spare_time2,a.class_time,a.create_time,a.status,b.do_title from class_live_booking a,class_course b where a.student_id = ? and b.id = a.schedule_id", studentId);

        for (Record classLive : result) {
            if (classLive.get("appoint_time") == null) {
                classLive.set("appoint_time", "");
            }
            if (classLive.get("class_time") == null) {
                classLive.set("class_time", "");
            }
            if (classLive.get("spare_time") == null) {
                classLive.set("spare_time", "");
            }
            if (classLive.get("spare_time2") == null) {
                classLive.set("spare_time2", "");
            }
        }

        data.put("booking", result);
        Result.ok(data, this);
    }

    public void bookPay(){
        renderTemplate("/live/booking_pay.html");
    }

    /**
     * 返回页面
     *
     * @param classLiveBooking
     */
    private void toView(ClassLiveBooking classLiveBooking) {

        this.setAttr("liveBooking", classLiveBooking);


        if (classLiveBooking.getStatus() == -1) {
            renderTemplate("/live/booking_fail.html");
            return;
        }

        if (classLiveBooking.getId() == null || classLiveBooking.getStatus() == 0) {
            this.setAttr("mobile", classLiveBooking.getMobile());
            this.setAttr("scheduleId", classLiveBooking.getScheduleId());
            this.setAttr("studentId", classLiveBooking.getStudentId());
            renderTemplate("/live/booking.html");
            return;
        }

        if (classLiveBooking.getClassPassword() == null || classLiveBooking.getClassAccount() == null || classLiveBooking.getClassTime() == null) {
            this.setAttr("appointTime", this.dateFomat(classLiveBooking.getAppointTime()));
            this.setAttr("spareTime", this.dateFomat(classLiveBooking.getSpareTime()));
            this.setAttr("spareTime2", this.dateFomat(classLiveBooking.getSpareTime2()));
            renderTemplate("/live/booking_now.html");
            return;
        }

        this.setAttr("class_time", this.dateFomat(classLiveBooking.getClassTime()));
        renderTemplate("/live/booking_complete.html");
    }

    /**
     * 返还次数
     *
     * @param studentId
     */
    private void restoreNumber(Integer studentId) {
        Db.update("update appointnum set specific_num = specific_num-1 where student_id = ?", studentId);
    }

    private Appointnum getRestoreNumber(Integer studentId) {
        return Appointnum.dao.findFirst("select * from appointnum where student_id = ?", studentId);
    }

}
