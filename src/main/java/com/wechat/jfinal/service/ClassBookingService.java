package com.wechat.jfinal.service;

import com.wechat.jfinal.model.Appointnum;

public class ClassBookingService {

    private Integer studentId;
    private Integer num;

    public ClassBookingService() {
    }

    public ClassBookingService(Integer studentId, Integer num) {
        this.studentId = studentId;
        this.num = num;
    }


    public void addAppointnum() {
        Appointnum appointnum = this.getRestoreNumber(this.studentId);
        if (appointnum == null) {
            Appointnum newAppointnum = new Appointnum();
            newAppointnum.setSpecificNum(this.num + 1);
            newAppointnum.setStudentId(this.studentId);
            newAppointnum.save();
        } else {
            appointnum.setSpecificNum(appointnum.getSpecificNum() + this.num);
            appointnum.update();
        }

    }

    private Appointnum getRestoreNumber(Integer studentId) {
        return Appointnum.dao.findFirst("select * from appointnum where student_id = ?", studentId);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
