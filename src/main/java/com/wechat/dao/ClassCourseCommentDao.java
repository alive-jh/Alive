package com.wechat.dao;

import com.wechat.entity.ClassCourseComment;

import java.util.HashMap;


public interface ClassCourseCommentDao {

	void saveClassCourseComment(ClassCourseComment classCourseComment);
	
	void deleteClassCourseComment(HashMap map);

	ClassCourseComment getById(Integer id);
	

}
