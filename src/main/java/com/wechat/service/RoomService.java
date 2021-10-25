package com.wechat.service;

import com.wechat.entity.ClassCourse;

import java.util.List;

public interface RoomService {


	List<ClassCourse> getCourseBySid(Integer sid);
}
