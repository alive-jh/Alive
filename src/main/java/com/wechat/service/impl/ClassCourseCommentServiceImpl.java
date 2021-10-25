package com.wechat.service.impl;

import com.wechat.dao.ClassCourseCommentDao;
import com.wechat.entity.ClassCourseComment;
import com.wechat.service.ClassCourseCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class ClassCourseCommentServiceImpl implements ClassCourseCommentService{

	
	@Resource
	private ClassCourseCommentDao classCourseCommentDao;

	@Override
	public void saveClassCourseComment(ClassCourseComment classCourseComment) {
		// TODO Auto-generated method stub
		this.classCourseCommentDao.saveClassCourseComment(classCourseComment);
	}

	@Override
	public void deleteClassCourseComment(HashMap map) {
		// TODO Auto-generated method stub
		this.classCourseCommentDao.deleteClassCourseComment(map);
	}
	


}
