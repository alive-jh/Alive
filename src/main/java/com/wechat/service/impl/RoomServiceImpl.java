package com.wechat.service.impl;

import com.wechat.dao.ClassCourseDao;
import com.wechat.dao.RoomPackageDao;
import com.wechat.entity.ClassCourse;
import com.wechat.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

	
	@Resource
	private RoomPackageDao roomPackageDao;

	@Resource
	private ClassCourseDao classCourseDao;

	@Override
	public List<ClassCourse> getCourseBySid(Integer sid) {
		//获得学生参与的课程
		List<ClassCourse>  list = classCourseDao.findJoinedCoursesByStudentId(sid);
		//根据主课程获得辅课

		//组装辅课
		//返回数据
		return list;
	}
}
