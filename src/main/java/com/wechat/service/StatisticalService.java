package com.wechat.service;


import com.wechat.entity.ClassCourseRankingCategory;
import com.wechat.entity.ExhibitionSign;
import com.wechat.entity.UserSign;
import com.wechat.util.Page;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StatisticalService {
	Page searchClassRooms(HashMap map);

	Page getStudentList(HashMap map);

	JSONObject getOnlineDeviceCountList(HashMap<String, String> map);

	Page getRankingList(HashMap map);

	void saveRankingList(ClassCourseRankingCategory classCourseRankingCategory);

	Page getReportDataByDay(HashMap map);

	Page downloadRanking(String cateGoryId);

	Page getRankingListByStudent(HashMap map);
	
	Page getClassCourseRankingByCId(Integer categoryId, Integer currentPage);
	
	Page getAllRanking(Integer categoryId, Integer currentPage);
	
	Object getClassCourseRankingBySId(Integer sid, Integer categoryId);
	
	Integer getStudentRanking(Integer sid, Integer categoryId);

	Map<String, Object> getRankByStudentId(int t);

	Integer setRead(Integer sid, Integer categoryId);

	Object[] getMemberInfo(Integer memberId);

	Integer saveSignInfo(ExhibitionSign exhibitionSign);

	Integer signed(Integer memberId);

	UserSign getUserSign(Integer memberId);

	void saveUserSign(UserSign userSign);

	List getSignInfo(Integer memberId);


}
