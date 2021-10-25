package com.wechat.entity;


import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "class_course_record_integral", catalog = "wechat")
public class ClassCourseRecordIntegral implements java.io.Serializable {
    private Integer id;
    private Integer studentId;
    private Integer classCourseId;
    private Integer integral;
    private Integer usedTime;

    public ClassCourseRecordIntegral() {
    }

    public ClassCourseRecordIntegral(Integer id, Integer studentId, Integer classCourseId, Integer integral, Integer usedTime) {
        this.id = id;
        this.studentId = studentId;
        this.classCourseId = classCourseId;
        this.integral = integral;
        this.usedTime = usedTime;
    }

    public ClassCourseRecordIntegral(Integer studentId, Integer classCourseId, Integer integral, Integer usedTime) {
        this.studentId = studentId;
        this.classCourseId = classCourseId;
        this.integral = integral;
        this.usedTime = usedTime;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "student_id")
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Column(name = "class_course_id")
    public Integer getClassCourseId() {
        return classCourseId;
    }

    public void setClassCourseId(Integer classCourseId) {
        this.classCourseId = classCourseId;
    }

    @Column(name = "integral")
    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    @Column(name = "used_time")
    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    @Override
    public String toString() {
        return "ClassCourseRecordIntegral{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", classCourseId=" + classCourseId +
                ", integral=" + integral +
                ", usedTime=" + usedTime +
                '}';
    }
}
