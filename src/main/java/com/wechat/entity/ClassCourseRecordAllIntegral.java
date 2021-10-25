package com.wechat.entity;


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "class_course_record_all_integral", catalog = "wechat")
public class ClassCourseRecordAllIntegral implements java.io.Serializable {
    private Integer id;
    private Integer studentId;
    private Integer allIntegral;

    public ClassCourseRecordAllIntegral() {
    }

    public ClassCourseRecordAllIntegral(Integer studentId, Integer allIntegral) {
        this.studentId = studentId;
        this.allIntegral = allIntegral;
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

    @Column(name = "all_integral")
    public Integer getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(Integer allIntegral) {
        this.allIntegral = allIntegral;
    }
}
