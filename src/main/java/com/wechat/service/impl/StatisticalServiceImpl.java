package com.wechat.service.impl;


import com.wechat.dao.StatisticalDao;
import com.wechat.entity.ClassCourseRankingCategory;
import com.wechat.entity.ExhibitionSign;
import com.wechat.entity.UserSign;
import com.wechat.service.StatisticalService;
import com.wechat.util.Page;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticalServiceImpl implements StatisticalService {
	@Resource
	private StatisticalDao statisticalDao;

	@Override
	public Page searchClassRooms(HashMap map) {
		// TODO Auto-generated method stub
		return this.statisticalDao.searchClassRooms(map);
	}

	@Override
	public Page getStudentList(HashMap map) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getStudentList(map);
	}

	@Override
	public JSONObject getOnlineDeviceCountList(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getOnlineDeviceCountList(map);
	}

	@Override
	public Page getRankingList(HashMap map) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getRankingList(map);
	}

	@Override
	public void saveRankingList(
			ClassCourseRankingCategory classCourseRankingCategory) {
		// TODO Auto-generated method stub
		this.statisticalDao.saveRankingList(classCourseRankingCategory);
		
	}

	@Override
	public Page getReportDataByDay(HashMap map) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getReportDataByDay(map);
	}

	@Override
	public Page downloadRanking(String cateGoryId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.downloadRanking(cateGoryId);
	}

	@Override
	public Page getRankingListByStudent(HashMap map) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getRankingListByStudent(map);
	}

	@Override
	public Page getClassCourseRankingByCId(Integer categoryId,
			Integer currentPage) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getClassCourseRankingByCId(categoryId, currentPage);
	}

	@Override
	public Page getAllRanking(Integer categoryId, Integer currentPage) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getAllRanking(categoryId, currentPage);
	}

	@Override
	public Object getClassCourseRankingBySId(Integer sid,Integer categoryId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getClassCourseRankingBySId(sid,categoryId);
	}

	@Override
	public Integer getStudentRanking(Integer sid, Integer categoryId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getStudentRanking(sid, categoryId);
	}

	@Override
	public Map<String, Object> getRankByStudentId(int t) {
		return statisticalDao.statisticalDao(t);
		
	}

	@Override
	public Integer setRead(Integer sid,Integer categoryId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.setRead(sid,categoryId);
	}

	@Override
	public Object[] getMemberInfo(Integer memberId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getMemberInfo(memberId);
	}

	@Override
	public Integer saveSignInfo(ExhibitionSign exhibitionSign) {
		// TODO Auto-generated method stub
		return this.statisticalDao.saveSignInfo(exhibitionSign);
	}

	@Override
	public Integer signed(Integer memberId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.signed(memberId);
	}

	@Override
	public UserSign getUserSign(Integer memberId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getUserSign(memberId);
	}

	@Override
	public void saveUserSign(UserSign userSign) {
		// TODO Auto-generated method stub
		this.statisticalDao.saveUserSign(userSign);
	}

	@Override
	public List getSignInfo(Integer memberId) {
		// TODO Auto-generated method stub
		return this.statisticalDao.getSignInfo(memberId);
		
	}
}
