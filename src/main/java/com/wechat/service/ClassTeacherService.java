package com.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wechat.entity.ClassTeacher;

public interface ClassTeacherService {

	List<ClassTeacher> getTeachersByStudent(Integer studentId);

}
