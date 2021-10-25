package com.wechat.dao.impl;

import com.wechat.dao.ClassCourseCommentDao;
import com.wechat.entity.ClassCourseComment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;


@Repository
public class ClassCourseCommentDaoImpl  extends BaseDaoImpl implements ClassCourseCommentDao {

	@Override
	public void saveClassCourseComment(ClassCourseComment classCourseComment) {
		this.saveOrUpdate(classCourseComment);
	}

	@Override
	public void deleteClassCourseComment(HashMap map) {
		String id = map.get("id").toString();
		StringBuffer sql = new StringBuffer(
				"delete from class_course_comment where id = " + id);
		this.executeSQL(sql.toString());
	}

	@Override
	public ClassCourseComment getById(Integer id) {
		return (ClassCourseComment) this.executeHQL("from ClassCourseComment where id= " + id)
				.get(0);
		
	}

}
