package com.wechat.service;

import com.wechat.entity.ClassCourseComment;

import java.util.HashMap;

public interface ClassCourseCommentService {

	void saveClassCourseComment(ClassCourseComment classCourseComment);
	void deleteClassCourseComment(HashMap map);
}
