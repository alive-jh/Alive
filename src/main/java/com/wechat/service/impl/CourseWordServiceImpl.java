package com.wechat.service.impl;

import com.wechat.dao.CourseWordDao;
import com.wechat.service.CourseWordService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("CourseWordServiceImpl")
public class CourseWordServiceImpl  implements CourseWordService{

	@Resource
	private CourseWordDao courseWordDao;


	@Override
	public Page getWordGroupList(HashMap map) {
		return courseWordDao.getWordGroupList(map);
	}


	@Override
	public JSONArray getWordSourceList(HashMap map) {
		// TODO Auto-generated method stub
		return courseWordDao.getWordSourceList(map);
	}


	@Override
	public JSONArray searchWords(HashMap map) {
		// TODO Auto-generated method stub
		return courseWordDao.searchWords(map);
	}
}
