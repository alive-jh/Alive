package com.wechat.dao.impl;

import com.wechat.dao.StatisticalDao;
import com.wechat.entity.ClassCourseRankingCategory;
import com.wechat.entity.ExhibitionSign;
import com.wechat.entity.UserSign;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class StatisticalDaoImpl extends BaseDaoImpl implements StatisticalDao {

	@Override
	public Page searchClassRooms(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select id,class_name,teacher_name,cover,summary,create_time from class_room");
		sql.append(" order by id desc");
		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> classRooms = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap classRoom = new HashMap();
				classRoom.put("id", obj[0]);
				classRoom.put("className", obj[1]);
				classRoom.put("teacherName", obj[2]);
				classRoom.put("cover", obj[3]);

				classRoom.put("summary", obj[4]);
				classRoom.put("createTime", obj[5]);
				classRooms.add(classRoom);
			}
		}
		Page resultPage = new Page<HashMap>(classRooms,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page getStudentList(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select epal_id from class_script_done where class_room_id=:classRoomId");
		sql.append(" group by epal_id");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(map.get("classRoomId"))
				&& null != map.get("classRoomId")) {
			query.setInteger("classRoomId",
					Integer.parseInt(map.get("classRoomId").toString()));
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> classRooms = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap classRoom = new HashMap();
				classRoom.put("id", obj[0]);
				classRoom.put("className", obj[1]);
				classRoom.put("teacherName", obj[2]);
				classRoom.put("cover", obj[3]);

				classRoom.put("summary", obj[4]);
				classRoom.put("createTime", obj[5]);
				classRooms.add(classRoom);
			}
		}
		Page resultPage = new Page<HashMap>(classRooms,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public JSONObject getOnlineDeviceCountList(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select epal_id,time from device_online_record where");
		String startTime = "";
		String endTime = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		if ("".equals(map.get("type")) || "".equals(map.get("hour"))) {
			endTime = df.format(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY,
					calendar.get(Calendar.HOUR_OF_DAY) - 72);
			startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(calendar.getTime());
		}
		sql.append(" time>='" + startTime + "' and time<='" + endTime + "'");
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 10000000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		JSONObject result = new JSONObject();

		for (int i = 0; i < dataList.size(); i++) {
			JSONArray epalIdList = new JSONArray();
			Object[] obj = dataList.get(i);

			String epalId = (String) obj[0];
			String time = (String) obj[1];
			String tempTime = time.split(":")[0];
			tempTime = tempTime + ":00:00";
			if (result.containsKey(tempTime)) {
				epalIdList = result.getJSONArray(tempTime);
				if (epalIdList.contains(epalId)) {

				} else {
					epalIdList.add(epalId);
				}
			} else {
				epalIdList.add(epalId);
			}
			result.put(tempTime, epalIdList);
		}

		return result;
	}

	@Override
	public Page getRankingList(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"from ClassCourseRankingCategory where 1=1");
		return this.pageQueryByHql(sql.toString(),
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public void saveRankingList(
			ClassCourseRankingCategory classCourseRankingCategory) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(classCourseRankingCategory);
	}

	@Override
	public Page getReportDataByDay(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"from ReportDayActive where 1=1 order by date desc");
		return this.pageQueryByHql(sql.toString(),
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public Page downloadRanking(String cateGoryId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select * from class_course_ranking_list where category_id="
						+ cateGoryId);
		Query query = this.getQuery(sql.toString());
		return this.pageQueryBySQL(query, 1, 10000000);

	}

	@Override
	public Page getRankingListByStudent(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select ccrc.id,ccrc.name,ccrc.start_time,ccrc.end_time,ccrl.read_status from class_course_ranking_category as ccrc,class_course_ranking_list as ccrl where ccrc.id=ccrl.category_id and ccrl.studentId="
						+ map.get("studentId").toString());
		sql.append(" order by ccrl.id desc");
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> classCourseRankingCategorys = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap classCourseRankingCategory = new HashMap();
				classCourseRankingCategory.put("id", obj[0]);
				classCourseRankingCategory.put("categoryName", obj[1]);
				classCourseRankingCategory.put("startTime", obj[2]);
				classCourseRankingCategory.put("endTime", obj[3]);
				classCourseRankingCategory.put("readStatus", obj[4]);
				classCourseRankingCategorys.add(classCourseRankingCategory);
			}
		}
		Page resultPage = new Page<HashMap>(classCourseRankingCategorys,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page getClassCourseRankingByCId(
			Integer categoryId,Integer currentPage) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.*,b.name FROM class_course_ranking_list a RIGHT JOIN class_course_ranking_category b ON a.`category_id`=" + categoryId);
		sql.append(" AND b.id="+categoryId);
		sql.append(" order by perScore desc");
		//Query query=this.getQuery(sql.toString());
		//return this.pageQueryBySQL(query, currentPage, 20);
		return this.pageQueryBySQL(sql.toString(), currentPage, 20);
	}
	
	public Page getAllRanking(Integer categoryId,Integer currentPage){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from class_course_ranking_list where category_id=" + categoryId);
		sql.append(" order by perScore desc");
		return this.pageQueryBySQL(sql.toString(), currentPage, 20);
	}
	
	public Object getClassCourseRankingBySId(Integer sid,Integer categoryId){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.name from class_course_ranking_list a JOIN class_student b ON  a.studentId=" + sid);
		sql.append(" AND a.category_id ="+categoryId);
		sql.append(" AND b.id ="+sid);
		Query query=this.getQuery(sql.toString());
		return query.list().get(0);
	}
	
	public Integer getStudentRanking(Integer sid,Integer categoryId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT rowNum FROM (SELECT (@rowNum\\:=@rowNum + 1) rowNum, t.*  FROM (SELECT @rowNum\\:=0,a.* FROM class_course_ranking_list a WHERE category_id ="+categoryId);
		sql.append(" AND perScore >=(SELECT perScore FROM class_course_ranking_list a WHERE studentId ="+sid);
		sql.append(" AND category_id ="+categoryId+")");
		sql.append(" ORDER BY perScore DESC) t) a WHERE a.studentId ="+sid);
		return new Double((double) this.executeSQL(sql.toString()).get(0)).intValue();
	}
	
	@Override
	public Integer setRead(Integer sid, Integer categoryId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE class_course_ranking_list SET read_status = 1 WHERE studentId ="+sid);
		sql.append(" AND category_id="+categoryId);
		try{
			this.executeUpdateSQL(sql.toString());
		}catch(Exception E){
			return 0;
		};
		
		return 1;
	}

	@Override
	public Map<String, Object> statisticalDao(int t) {
		Map<String, Object> result = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ROWNUM,A.category_id,A.studentId,A.studentName,found_rows() AS rowcount from ( select (@mycnt \\:= @mycnt + 1) ROWNUM , B.studentId,B.category_id,B.studentName "+
		"from ( SELECT c.studentId, c.perScore,c.category_id,c.studentName FROM class_course_ranking_list c, (SELECT l.category_id id FROM class_course_ranking_list l WHERE l.studentId = "+t+
				" ORDER BY id DESC LIMIT 1) r WHERE c.category_id = r.id) B , (Select (@mycnt \\:=0) ) c order by b.perScore DESC ) as A where studentId= "+t);
		List result1 = this.executeSQL(sql.toString());
		
		if(result1.size() ==0)
			return null;
		Object[] arry = (Object[]) result1.get(0);
		if(arry.length < 1){
			return null;
		}		
		result.put("rank", arry[0]);
		result.put("categoryId", arry[1]);
		result.put("studentId", arry[2]);
		result.put("studentName", arry[3]);
		result.put("total", arry[4]);
		return null;

	}
	
	@Override
	public Object[] getMemberInfo(Integer memberId){
		String sql = "select * from member where id = "+memberId;
		List<Object[]> list = this.executeSQL(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Transactional
	@Override
	public Integer saveSignInfo(ExhibitionSign exhibitionSign) {
		String sql = "SELECT a.mobile,b.id,b.uname FROM member a ,exhibition_sign b WHERE a.`id`= "+exhibitionSign.getMemberId()+" AND b.`member_id`=a.`id`";
		List list = this.executeSQL(sql);
		if(0!=list.size()){
			return (Integer) ((Object[])list.get(0))[1];
		}else{				
				this.save(exhibitionSign);
				Object[] obj = (Object[]) this.executeSQL(sql).get(0);
				return (Integer) obj[1];
			}
	}

	@Override
	public Integer signed(Integer memberId) {
		String sql = "SELECT a.mobile,b.id,b.uname FROM member a ,exhibition_sign b WHERE a.`id`= "+memberId+" AND b.`member_id`=a.`id`";
		List list = this.executeSQL(sql);
		if(0!=list.size()){
			return (Integer) ((Object[])list.get(0))[1];
		}else{
			return 0;
		}
	}

	@Override
	public UserSign getUserSign(Integer memberId) {
		String sql = "SELECT * FROM user_sign WHERE member_id="+memberId;
		List list = this.executeSQL(sql);
		if(0!=list.size()){
			Object [] obj = (Object[]) list.get(0);
			UserSign userSign = new UserSign();
			userSign.setId((Integer) obj[0]);
			userSign.setMemberId((Integer) obj[1]);
			userSign.setuName((String) obj[2]);
			userSign.setuGrade((String) obj[3]);
			userSign.setuAge((Integer) obj[4]);
			userSign.setMobile((String) obj[5]);
			return userSign;
		}else{
			return null;
		}
	}

	@Override
	public void saveUserSign(UserSign userSign) {
		String sql = "SELECT * FROM user_sign where member_id= "+userSign.getMemberId();
		List list = this.executeSQL(sql);
		if(0==list.size()){
			userSign.setSignTime(new Date());
			String sql1 = "UPDATE member SET mobile=? WHERE id=?";
			Query query = this.getQuery(sql1).setString(0, userSign.getMobile()).setLong(1, userSign.getMemberId());
			query.executeUpdate();
			this.save(userSign);
		}
		
	}

	@Override
	public List getSignInfo(Integer memberId) {
		String sql = "SELECT * FROM user_sign where member_id= "+memberId;
		List list = this.executeSQL(sql);
		if(list!=null){
			return list;
		}else{
		return null;
			}
	}
	
	
	
}
