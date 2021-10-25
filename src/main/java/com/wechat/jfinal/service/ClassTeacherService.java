package com.wechat.jfinal.service;

import com.wechat.jfinal.model.ClassTeacher;

public class ClassTeacherService {

    public ClassTeacher findByMemberId(int memberId){
        return ClassTeacher.dao.findFirst("SELECT * FROM class_teacher WHERE member_id = ?", memberId);
    }
}
