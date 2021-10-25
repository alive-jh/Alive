package com.wechat.dao.impl;

import com.wechat.dao.CourseDao;
import com.wechat.entity.*;
import com.wechat.entity.dto.CourseScheduleDto;
import com.wechat.entity.dto.MainCourseDto;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class CourseDaoImpl extends BaseDaoImpl implements CourseDao {

	@Override
	public Page searchCourseInfos(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("from MallProduct where mp3Type = 2 ");
		if(null!=map.get("searchStr")){
			sql.append(" and name like '%"+map.get("searchStr")+"%'");
		}
		sql.append(" order by createDate desc ");
		Page resultPage = this.pageQueryByHql(sql.toString(),
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
		return resultPage;
	}

	@Override
	public MallProduct findMainCourseByProductId(Integer productId) {
		List res = this.executeHQL("from MallProduct where id= " + productId);
		if (res.size() > 0) {
			return (MallProduct) res.get(0);
		}
		return new MallProduct();
	}

	@Override
	public void updateMallProduct(MallProduct mallProduct) {
		this.update(mallProduct);
	}

	@Override
	public Course findChildCourseByCourseId(String courseId) {
		return (Course) this.executeHQL("from Course where id= " + courseId)
				.get(0);
	}

	@Override
	public void updateCourse(Course course) {
		this.update(course);
	}

	@Override
	public void updateCoursePeriodByPeriodId(CoursePeriod course) {
		this.update(course);
	}

	@Override
	public CoursePeriod findCoursePeriodByPeriodId(String periodId) {
		return (CoursePeriod) this.executeHQL(
				"from CoursePeriod where id= " + periodId).get(0);
	}

	@Override
	public void saveCoursePeriod(CoursePeriod coursePeriod) {
		this.save(coursePeriod);
	}

	@Override
	public void saveCourseWord(CourseWord courseWord) {
		this.saveOrUpdate(courseWord);
	}

	@Override
	public Page searchCourseWords(HashMap map) {
		StringBuffer sql = new StringBuffer("from CourseWord where 1 = 1 ");
		if (null != map.get("searchStr")
				&& !"".equals(map.get("searchStr").toString())) {
			sql.append(" and wordTxt  like '%").append(map.get("searchStr"))
					.append("%'");
		}
		sql.append(" order by id desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));
	}

	@Override
	public Page searchCourseSchedules(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT" + "	temp_1.id," + "	temp_1.epal_id,"
				+ "	mallproduct.`name` AS productName,"
				+ "	course.`name` AS courseName," + "	temp_1.cur_class,"
				+ "	course.total_class AS totalClass," + "	temp_1.create_time"
				+ " FROM" + "	(" + "		SELECT" + "			temp.id,"
				+ "			temp.device_no," + "			temp.epal_id,"
				+ "			temp.courseid," + "			temp.productid,"
				+ "			temp. SCHEDULE," + "			temp.cur_class,"
				+ "			temp.cus_file," + "			temp.create_time" + "		FROM"
				+ "			(" + "				SELECT" + "					*" + "				FROM"
				+ "					course_schedule" + "				ORDER BY"
				+ "					course_schedule.create_time DESC" + "			) AS temp"
				+ "		WHERE" + "			1 = 1" + "		GROUP BY" + "			temp.epal_id"
				+ "		ORDER BY" + "			temp.create_time DESC" + "	) AS temp_1"
				+ " LEFT JOIN mallproduct ON mallproduct.id = temp_1.productid"
				+ " LEFT JOIN course ON course.id = temp_1.courseid");

		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> courseSchedules = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap courseSchedule = new HashMap();
				courseSchedule.put("id", obj[0]);
				courseSchedule.put("epalId", obj[1]);
				courseSchedule.put("productName", obj[2]);
				courseSchedule.put("courseName", obj[3]);
				courseSchedule.put("curClass", obj[4]);
				courseSchedule.put("totalClass", obj[5]);
				courseSchedule.put("createTime", obj[6]);
				courseSchedules.add(courseSchedule);
			}

		}

		Page resultPage = new Page<HashMap>(courseSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public Page getAllCoursePeriodsByCourseId(String courseId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from CoursePeriod where courseId = " + courseId);
		Page page = this.pageQueryByHql(sql.toString(), 1, 1000);
		return page;
	}

	@Override
	public Page getAllCoursesByProductId(String productId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT" + "	course.id AS courseId,"
				+ "	course.`name` AS courseName," 
				+ "	course.url AS courseUrl,"
				+ "	mallproduct.`name` AS productName,"
				+ "	course.total_class AS courseTotalClass" 
				+ " FROM"
				+ "	mallproduct," 
				+ "	course" 
				+ " WHERE"
				+ "	mallproduct.mp3type = 2"
				+ " AND mallproduct.id = course.productid"
				+ " AND mallproduct.id = :productId order by course.total_class ");
		Query query = this.getQuery(sql.toString());
		query.setInteger("productId", Integer.parseInt(productId));
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> courses = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap course = new HashMap();
				course.put("courseId", obj[0]);
				course.put("courseName", obj[1]);
				course.put("courseUrl", obj[2]);
				course.put("productName", obj[3]);
				course.put("totalClass", obj[4]);
				courses.add(course);
			}

		}
		Page resultPage = new Page<HashMap>(courses, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page getAllCourseSchedulesByEpalId(String epalId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT" + "	temp_1.id," + "	temp_1.epal_id,"
				+ "	mallproduct.`name` AS productName,"
				+ "	course.`name` AS courseName," + "	temp_1.cur_class,"
				+ "	course.total_class AS totalClass," + "	temp_1.create_time"
				+ " FROM" + "	(" + "		SELECT" + "			temp.id,"
				+ "			temp.device_no," + "			temp.epal_id,"
				+ "			temp.courseid," + "			temp.productid,"
				+ "			temp. SCHEDULE," + "			temp.cur_class,"
				+ "			temp.cus_file," + "			temp.create_time" + "		FROM"
				+ "			(" + "				SELECT" + "					*" + "				FROM"
				+ "					course_schedule" + "				ORDER BY"
				+ "					course_schedule.create_time DESC" + "			) AS temp"
				+ "		WHERE" + "			1 = 1" + "		AND temp.epal_id = :epalId "
				+ "		GROUP BY" + "			temp.courseid" + "		ORDER BY"
				+ "			temp.create_time DESC" + "	) AS temp_1"
				+ " LEFT JOIN mallproduct ON mallproduct.id = temp_1.productid"
				+ " LEFT JOIN course ON course.id = temp_1.courseid");
		Query query = this.getQuery(sql.toString());
		query.setString("epalId", epalId);
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> courseSchedules = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap courseSchedule = new HashMap();
				courseSchedule.put("id", obj[0]);
				courseSchedule.put("epalId", obj[1]);
				courseSchedule.put("productName", obj[2]);
				courseSchedule.put("courseName", obj[3]);
				courseSchedule.put("curClass", obj[4]);
				courseSchedule.put("totalClass", obj[5]);
				courseSchedule.put("createTime", obj[6]);
				courseSchedules.add(courseSchedule);
			}
		}
		Page resultPage = new Page<HashMap>(courseSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page<?> getCoursePeriodsRecords(HashMap<String, String> map) {
		String epalId = map.get("epalId");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"+
				"	product_category.cat_name catName,"+
				"	temp2.mainCourseId,"+
				"	mallproduct.`name` mainCourseName,"+
				"	temp2.childCourseId,"+
				"	course.`name` childCourseName,"+
				"	temp2.periodCourseId,"+
				"	course_period.courseperiod_name periodCourseName,"+
				"	temp2.recentTime,"+
				"	temp2.periodRecords"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			temp1.productid mainCourseId,"+
				"			temp1.courseid childCourseId,"+
				"			temp1.periodid periodCourseId,"+
				"			temp1.create_time recentTime,"+
				"			GROUP_CONCAT("+
				"				temp1.create_time,"+
				"				\'∫\'"+
				"			ORDER BY"+
				"				temp1.create_time DESC SEPARATOR \'∪\'"+
				"			) periodRecords"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					*"+
				"				FROM"+
				"					course_schedule"+
				"				WHERE"+
				"					course_schedule.epal_id = :epalId "+
				"				ORDER BY"+
				"					course_schedule.create_time DESC"+
				"			) temp1"+
				"		WHERE"+
				"			temp1.periodid IS NOT NULL"+
				"		GROUP BY"+
				"			temp1.periodid"+
				"		ORDER BY"+
				"			temp1.create_time DESC"+
				"	) temp2,"+
				"	course_period,"+
				"	course,"+
				"	mallproduct,"+
				"	product_category"+
				" WHERE"+
				"	temp2.mainCourseId = mallproduct.id"+
				" AND temp2.childCourseId = course.id"+
				" AND temp2.periodCourseId = course_period.id"+
				" AND mallproduct.cat_id = product_category.cat_id");

		Query query = this.getQuery(sql.toString());
		query.setString("epalId", epalId);
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 子课时记录信息数组
		ArrayList<HashMap> periodCourseRecords = new ArrayList<HashMap>();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 子课时记录信息
				HashMap periodCourseRecord = new HashMap();
				Object[] obj = dataList.get(z);
				periodCourseRecord.put("catName", obj[0]);
				periodCourseRecord.put("mainCourseId", obj[1]);
				periodCourseRecord.put("mainCourseName", obj[2]);
				periodCourseRecord.put("childCourseId", obj[3]);
				periodCourseRecord.put("childCourseName", obj[4]);
				periodCourseRecord.put("periodCourseId", obj[5]);
				periodCourseRecord.put("periodCourseName", obj[6]);
				periodCourseRecord.put("recentTime", obj[7]);
				String periodRecordStr = (String) obj[8];
				String[] periodRecordArr = periodRecordStr.split("∪");
				// 课时学习记录时间线列表
				ArrayList childPeriods = new ArrayList();
				for (int i = 0; i < periodRecordArr.length; i++) {
					String[] periodRecordPros = periodRecordArr[i].split("∫");
					HashMap periodRecord = new HashMap();
					periodRecord.put("studyTime", periodRecordPros[0]);
					childPeriods.add(periodRecord);
				}
				periodCourseRecord.put("periodRecords",childPeriods);
				// 添加到主课程
				periodCourseRecords.add(periodCourseRecord);
			}

		}
		Page resultPage = new Page<HashMap>(periodCourseRecords,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	/**
	 * 1.查询课程所有课程课时信息树型选择接口
	 * 
	 * @param request
	 * @param response
	 * 
	 */

	@Override
	public Page getCoursePlanSelectTree(HashMap map) {
		String epalId = (String) map.get("epalId");
		Integer planId = (Integer) map.get("planId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"
				+ "	temp1.cat_id catId,"
				+ "	temp1.cat_name catName,"
				+ "	GROUP_CONCAT("
				+ "		temp2.productId,"
				+ "		\'∫\',"
				+ "		temp2.productName,"
				+ "		\'∫\',"
				+ "		temp2.childCourses,"
				+ "		\'\'"
				+ "	ORDER BY"
				+ "		temp2.productId DESC SEPARATOR \'∮\'"
				+ "	) mainCourses"
				+ " FROM"
				+ "	("
				+ "		SELECT"
				+ "			product_category.cat_id,"
				+ "			product_category.cat_name"
				+ "		FROM"
				+ "			product_category"
				+ "	) AS temp1,"
				+ "	("
				+ "		SELECT"
				+ "			temp3.cat_id catId,"
				+ "			temp3.id productId,"
				+ "			temp3. NAME productName,"
				+ "			GROUP_CONCAT("
				+ "				temp4.id,"
				+ "				\'∝\',"
				+ "				temp4. NAME,"
				+ "				\'∝\',"
				+ "				temp4.coursePeriodIds,"
				+ "				\'\'"
				+ "			ORDER BY"
				+ "				temp4.id DESC SEPARATOR \'∞\'"
				+ "			) childCourses"
				+ "		FROM"
				+ "			("
				+ "				SELECT"
				+ "					mallproduct.id,"
				+ "					mallproduct.`name`,"
				+ "					mallproduct.cat_id"
				+ "				FROM"
				+ "					mallproduct"
				+ "				WHERE"
				+ "					mallproduct.mp3type = 2"
				+ "				ORDER BY"
				+ "					mallproduct.id DESC"
				+ "			) AS temp3,"
				+ "			("
				+ "				SELECT"
				+ "					course.id,"
				+ "					course.`name`,"
				+ "					course.productid,"
				+ "					GROUP_CONCAT(IFNULL(temp5.periodid ,- 1)) coursePeriodIds"
				+ "				FROM" + "					course" + "				LEFT JOIN ("
				+ "					SELECT" + "						a.id planId," + "						a.plan_name,"
				+ "						b.productid," + "						b.courseid,"
				+ "						b.periodid" + "					FROM" + "						course_plan AS a,"
				+ "						course_plan_info AS b" + "					WHERE"
				+ "						a.id = b.plan_id" + "					AND a.epalid = :epalId "
				+ "					AND a.id=:planId "
				+ "				) temp5 ON course.id = temp5.courseid" + "				GROUP BY"
				+ "					course.id" + "				ORDER BY" + "					course.id DESC"
				+ "			) AS temp4" + "		WHERE" + "			temp3.id = temp4.productid"
				+ "		GROUP BY" + "			productId" + "	) AS temp2" + " WHERE"
				+ "	temp1.cat_id = temp2.catId" + " GROUP BY" + "	catName");

		Query query = this.getQuery(sql.toString());

		query.setInteger("planId", planId);
		query.setString("epalId", epalId);

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 课程选择数组
		ArrayList<HashMap> courseCategorys = new ArrayList<HashMap>();

		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 课程类型
				HashMap courseCates = new HashMap();
				Object[] obj = dataList.get(z);
				courseCates.put("catId", obj[0]);
				courseCates.put("catName", obj[1]);
				courseCates.put("mainCourses", obj[2]);
				courseCategorys.add(courseCates);
			}

		}

		Page resultPage = new Page<HashMap>(courseCategorys,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	/**
	 * 3.添加课程计划接口
	 * 
	 * @param request
	 * @param response
	 * 
	 */

	@Override
	public CoursePlan addCoursePlan(HashMap map) {
		CoursePlan coursePlan = new CoursePlan();
		coursePlan.setEpalId((String) map.get("epalId"));
		coursePlan.setPlanName((String) map.get("planName"));
		coursePlan.setMemberId(Integer.parseInt(map.get("memberId").toString()));
		coursePlan.setSort((Integer) map.get("sort"));
		coursePlan.setSummary((String) map.get("summary"));
		coursePlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
		this.save(coursePlan);
		return coursePlan;
	}

	/**
	 * 批量删除课程计划内的执行单位接口
	 * 
	 * @param request
	 * @param response
	 * 
	 */

	@Override
	public void delCoursePlanInfos(String planId) {
		this.executeUpdateSQL("delete from course_plan_info where plan_id = "
				+ planId);
	}

	/**
	 * 更新课程计划内执行单位
	 */

	@Override
	public ArrayList updateCoursePlan(String planId, String coursePlanInfos) {
		// 添加课程计划执行单位
		JSONArray coursePlanInfosJson = JSONArray.fromObject(coursePlanInfos);
		ArrayList arrayList=new ArrayList();
		for (int i = 0; i < coursePlanInfosJson.size(); i++) {
			CoursePlanInfo coursePlanInfo = (CoursePlanInfo) JSONObject.toBean(
					coursePlanInfosJson.getJSONObject(i), CoursePlanInfo.class);
			coursePlanInfo.setPlanId(Integer.parseInt(planId));
			this.save(coursePlanInfo);
			arrayList.add(coursePlanInfo);
		}
		return arrayList;
	}

	/**
	 * 添加一条课程计划执行单位
	 */

	@Override
	public void addCoursePlanInfo(CoursePlanInfo coursePlanInfo) {
		this.save(coursePlanInfo);
	}
	
	
	/**
	 * 1.我的课程学习进度接口（学习记录模块）
	 */
	@Override
	public Page getCourseStudyRecords(HashMap map) {
		String epalId=" ";
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			epalId=map.get("epalId").toString();
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"+
				"	temp_4.catId,"+
				"	temp_4.catName,"+
				"	GROUP_CONCAT("+
				"		temp_4.parentId,"+
				"		\'∫\',"+
				"		temp_4.parentName,"+
				"		\'∫\',"+
				"		temp_4.courseLogo,"+
				"		\'∫\',"+
				"		temp_4.childCourse,"+
				"		\'\'"+
				"	ORDER BY"+
				"		parentId ASC SEPARATOR \'∮\'"+
				"	) AS mainCourse"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			temp_3.catId,"+
				"			temp_3.catName,"+
				"			temp_3.parentId,"+
				"			temp_3.parentName,"+
				"			temp_3.courseLogo,"+
				"			GROUP_CONCAT("+
				"				temp_3.courseId,"+
				"				\'∝\',"+
				"				temp_3.totalClass,"+
				"				\'∝\',"+
				"				temp_3.chapterName,"+
				"				\'∝\',"+
				"				temp_3.chapterRes,"+
				"				\'∝\',"+
				"				temp_3.curClass,"+
				"				\'∝\',"+
				"				temp_3.cusFile,"+
				"				\'\'"+
				"			ORDER BY"+
				"				courseId ASC SEPARATOR \'∞\'"+
				"			) AS childCourse"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					temp_1.catId,"+
				"					temp_1.catName,"+
				"					temp_1.product_id AS parentId,"+
				"					temp_1.parent_name AS parentName,"+
				"					temp_1.courseLogo,"+
				"					temp_1.course_id AS courseId,"+
				"					temp_1.totalClass,"+
				"					temp_1.chapter_name AS chapterName,"+
				"					temp_1.chapter_res AS chapterRes,"+
				"					IFNULL(temp_2.cur_class, 0) AS curClass,"+
				"					IFNULL(temp_2.cus_file, \'null\') AS cusFile"+
				"				FROM"+
				"					("+
				"						SELECT"+
				"							mallproduct.id AS product_id,"+
				"							mallproduct.`name` AS parent_name,"+
				"							course.id AS course_id,"+
				"							course.url AS chapter_res,"+
				"							course.`name` AS chapter_name,"+
				"							IFNULL(course.total_class, 8) AS totalClass,"+
				"							product_category.cat_name AS catName,"+
				"							product_category.cat_id AS catId,"+
				"							mallproduct.logo1 AS courseLogo"+
				"						FROM"+
				"							course,"+
				"							mallproduct,"+
				"							product_category"+
				"						WHERE"+
				"							course.productid = mallproduct.id"+
				"						AND mallproduct.cat_id = product_category.cat_id"+
				"						AND mallproduct.id IN ("+
				"							SELECT"+
				"								course_schedule.productid"+
				"							FROM"+
				"								course_schedule"+
				"							WHERE"+
				"								course_schedule.epal_id = :epalId"+
				"							GROUP BY"+
				"								course_schedule.productid"+
				"						)"+
				"					) AS temp_1"+
				"				LEFT JOIN ("+
				"					SELECT"+
				"						a.epal_id,"+
				"						a.productid,"+
				"						a.courseid,"+
				"						a.cur_class,"+
				"						a.cus_file"+
				"					FROM"+
				"						("+
				"							SELECT"+
				"								temp.id,"+
				"								temp.device_no,"+
				"								temp.epal_id,"+
				"								temp.courseid,"+
				"								temp.productid,"+
				"								temp. SCHEDULE,"+
				"								temp.cur_class,"+
				"							IF ("+
				"								temp.cus_file = \'\' || temp.cus_file = NULL,"+
				"								\'null\',"+
				"								temp.cus_file"+
				"							) AS cus_file,"+
				"							temp.create_time"+
				"						FROM"+
				"							("+
				"								SELECT"+
				"									*"+
				"								FROM"+
				"									course_schedule"+
				"								ORDER BY"+
				"									course_schedule.create_time DESC"+
				"							) AS temp"+
				"						WHERE"+
				"							1 = 1"+
				"						AND temp.epal_id = :epalId"+
				"						GROUP BY"+
				"							temp.courseid"+
				"						ORDER BY"+
				"							temp.create_time DESC"+
				"						) AS a"+
				"					GROUP BY"+
				"						a.courseid"+
				"				) AS temp_2 ON temp_1.product_id = temp_2.productid"+
				"				AND temp_1.course_id = temp_2.courseid"+
				"			) AS temp_3"+
				"		GROUP BY"+
				"			temp_3.parentId"+
				"	) AS temp_4"+
				" GROUP BY"+
				"	temp_4.catId");

		Query query = this.getQuery(sql.toString());

		query.setString("epalId", epalId);

		ArrayList<CourseScheduleDto> courseSchedules = new ArrayList<CourseScheduleDto>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int z = 0; z < dataList.size(); z++) {
				CourseScheduleDto courseSchedule = new CourseScheduleDto();
				Object[] obj = dataList.get(z);
				courseSchedule.setCatId((Integer) obj[0]);
				courseSchedule.setCatName((String) obj[1]);
				String catCourseStr=(String) obj[2];
				String[] catCourseStrArrs=catCourseStr.split("∮");
				
				ArrayList catCourseList=new ArrayList();
				
				for (int i = 0; i < catCourseStrArrs.length; i++) {
					String [] catCourseStrArr=catCourseStrArrs[i].split("∫");
					HashMap mainCourseMap=new HashMap();
					mainCourseMap.put("productId", catCourseStrArr[0]);
					mainCourseMap.put("productName", catCourseStrArr[1]);
					
					mainCourseMap.put("productLogo", "http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/"
							+catCourseStrArr[2]);
					
					//子课程列表
					ArrayList childCourseList=new ArrayList();
					
					String[] childCourseStrArrs=catCourseStrArr[3].split("∞");
					
					for (int j = 0; j < childCourseStrArrs.length; j++) {
						
						HashMap childCourseMap=new HashMap();
						
						String[] chileCourseStrArr=childCourseStrArrs[j].split("∝");
						
						childCourseMap.put("courseId", chileCourseStrArr[0]);
						
						childCourseMap.put("totalClass", chileCourseStrArr[1]);
						
						childCourseMap.put("chapterName", chileCourseStrArr[2]);
						
						childCourseMap.put("chapterRes", chileCourseStrArr[3]);
						
						childCourseMap.put("curClass", chileCourseStrArr[4]);
						
						childCourseMap.put("cusFile", chileCourseStrArr[5]);
						//添加子课程到列表
						childCourseList.add(childCourseMap);
					}
					//添加主课程的子课程列表
					mainCourseMap.put("childCourseList", childCourseList);
					//添加主课程到列表
					catCourseList.add(mainCourseMap);
					
				}
				//添加类别下的主课程列表
				courseSchedule.setCatCourse(catCourseList);
				courseSchedules.add(courseSchedule);
			}

		}
		Page resultPage = new Page<CourseScheduleDto>(courseSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}
	
	
	/**
	 * 2.1 课程学习记录详细信息接口（直接通过主课程Id查出主课程子课程课时学习记录的详细信息）
	 */

	@Override
	public Page<?> getCourseInfoRecordByMainCourseId(HashMap<String, String> map) {
		String productId = map.get("productId");
		String epalId = map.get("epalId");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"+
				"	temp3.epal_id,"+
				"	temp3.productid,"+
				"	temp4.`name`,"+
				"	temp4.logo1,"+
				"	temp3.childCourses"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			temp1.epal_id,"+
				"			temp1.productid,"+
				"			GROUP_CONCAT("+
				"				temp1.courseid,"+
				"				\'∑\',"+
				"				IFNULL(temp2. NAME, \' \'),"+
				"				\'∑\',"+
				"				IFNULL(temp1.periodRecords, \' \'),"+
				"				\'\' SEPARATOR \'∈\'"+
				"			) childCourses"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					course_schedule.epal_id,"+
				"					course_schedule.productid,"+
				"					course_schedule.courseid,"+
				"					GROUP_CONCAT("+
				"						course_schedule.periodid,"+
				"						\'∫\',"+
				"						IFNULL("+
				"							course_period.courseperiod_name,"+
				"							\' \'"+
				"						),"+
				"						\'∫\',"+
				"						IFNULL("+
				"							course_schedule.create_time,"+
				"							\' \'"+
				"						),"+
				"						\'\'"+
				"					ORDER BY"+
				"						course_schedule.create_time SEPARATOR \'∮\'"+
				"					) periodRecords"+
				"				FROM"+
				"					course_period,"+
				"					course_schedule"+
				"				WHERE"+
				"					course_period.id = course_schedule.periodid"+
				"				AND course_schedule.epal_id = :epalId "+
				"				AND course_schedule.productid = :productId "+
				"				GROUP BY"+
				"					course_schedule.periodid"+
				"			) temp1"+
				"		LEFT JOIN ("+
				"			SELECT"+
				"				course.id,"+
				"				course.`name`"+
				"			FROM"+
				"				course"+
				"		) temp2 ON temp1.courseid = temp2.id"+
				"		GROUP BY"+
				"			temp1.productid"+
				"	) temp3"+
				" LEFT JOIN ("+
				"	SELECT"+
				"		mallproduct.id,"+
				"		mallproduct.`name`,"+
				"		mallproduct.logo1"+
				"	FROM"+
				"		mallproduct"+
				" ) temp4 ON temp3.productid = temp4.id");

		Query query = this.getQuery(sql.toString());

		query.setInteger("productId", Integer.parseInt(productId));
		query.setString("epalId", epalId);
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 主课程数组
		ArrayList<HashMap> mainCourses = new ArrayList<HashMap>();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				HashMap mainCourse = new HashMap();
				Object[] obj = dataList.get(z);
				mainCourse.put("epalId", obj[0]);
				mainCourse.put("mainCourseId",obj[1]);
				mainCourse.put("mainCourseName",obj[2]);
				mainCourse.put("mainCourseLogo",obj[3]);
				String childCourseStr = obj[4].toString().trim();
				// 子课程列表
				ArrayList childCourses = new ArrayList();
				if(null!=childCourseStr&&!"".equals(childCourseStr)){
					String[] childCourseArr = childCourseStr.split("∈");
					
					for (int i = 0; i < childCourseArr.length; i++) {
						String[] childCoursePros = childCourseArr[i].split("∑");
						HashMap childCourse = new HashMap();
						childCourse.put("childCourseId", childCoursePros[0]);
						childCourse.put("childCourseName", childCoursePros[1]);
						ArrayList coursePeriods=new ArrayList();
						
						if(null!=childCoursePros[2]&&!"".equals(childCoursePros[2].trim())){
							
							String[] coursePeriodsStrArr=childCoursePros[2].split("∮");
							for (int j = 0; j < coursePeriodsStrArr.length; j++) {
								String[] coursePeriodStrArr=coursePeriodsStrArr[j].split("∫");
								HashMap coursePeriod=new HashMap();
								coursePeriod.put("periodId", coursePeriodStrArr[0]);
								coursePeriod.put("periodName", coursePeriodStrArr[1]);
								coursePeriod.put("studyTime", coursePeriodStrArr[2]);
								coursePeriods.add(coursePeriod);
							}
						}
						
						childCourse.put("childCoursePeriods",coursePeriods);
						childCourses.add(childCourse);
					}
				}
				
				mainCourse.put("childCourses", childCourses);
				// 添加到主课程
				mainCourses.add(mainCourse);
			}

		}
		Page resultPage = new Page<HashMap>(mainCourses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}
	
	
	/**
	 * 2.2 课程学习记录详细信息接口（直接通过epalId查出所有的主课程子课程课时学习记录的详细信息）
	 */

	@Override
	public Page<?> getCourseInfoRecords(HashMap<String, String> map) {
		String epalId = map.get("epalId");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"+
				"	temp3.epal_id,"+
				"	temp3.productid,"+
				"	temp4.`name`,"+
				"	temp4.logo1,"+
				"	temp3.childCourses"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			temp1.epal_id,"+
				"			temp1.productid,"+
				"			GROUP_CONCAT("+
				"				temp1.courseid,"+
				"				\'∑\',"+
				"				IFNULL(temp2. NAME, \' \'),"+
				"				\'∑\',"+
				"				IFNULL(temp1.periodRecords, \' \'),"+
				"				\'\' SEPARATOR \'∈\'"+
				"			) childCourses"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					course_schedule.epal_id,"+
				"					course_schedule.productid,"+
				"					course_schedule.courseid,"+
				"					GROUP_CONCAT("+
				"						course_schedule.periodid,"+
				"						\'∫\',"+
				"						IFNULL("+
				"							course_period.courseperiod_name,"+
				"							\' \'"+
				"						),"+
				"						\'∫\',"+
				"						IFNULL("+
				"							course_schedule.create_time,"+
				"							\' \'"+
				"						),"+
				"						\'\'"+
				"					ORDER BY"+
				"						course_schedule.create_time SEPARATOR \'∮\'"+
				"					) periodRecords"+
				"				FROM"+
				"					course_period,"+
				"					course_schedule"+
				"				WHERE"+
				"					course_period.id = course_schedule.periodid"+
				"				AND course_schedule.epal_id = :epalId "+
				"				GROUP BY"+
				"					course_schedule.periodid"+
				"			) temp1"+
				"		LEFT JOIN ("+
				"			SELECT"+
				"				course.id,"+
				"				course.`name`"+
				"			FROM"+
				"				course"+
				"		) temp2 ON temp1.courseid = temp2.id"+
				"		GROUP BY"+
				"			temp1.productid"+
				"	) temp3"+
				" LEFT JOIN ("+
				"	SELECT"+
				"		mallproduct.id,"+
				"		mallproduct.`name`,"+
				"		mallproduct.logo1"+
				"	FROM"+
				"		mallproduct"+
				" ) temp4 ON temp3.productid = temp4.id");

		Query query = this.getQuery(sql.toString());

		query.setString("epalId", epalId);
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 主课程数组
		ArrayList<HashMap> mainCourses = new ArrayList<HashMap>();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				HashMap mainCourse = new HashMap();
				Object[] obj = dataList.get(z);
				mainCourse.put("epalId", obj[0]);
				mainCourse.put("mainCourseId",obj[1]);
				mainCourse.put("mainCourseName",obj[2]);
				mainCourse.put("mainCourseLogo",obj[3]);
				String childCourseStr = obj[4].toString().trim();
				// 子课程列表
				ArrayList childCourses = new ArrayList();
				if(null!=childCourseStr&&!"".equals(childCourseStr)){
					String[] childCourseArr = childCourseStr.split("∈");
					
					for (int i = 0; i < childCourseArr.length; i++) {
						String[] childCoursePros = childCourseArr[i].split("∑");
						HashMap childCourse = new HashMap();
						childCourse.put("childCourseId", childCoursePros[0]);
						childCourse.put("childCourseName", childCoursePros[1]);
						ArrayList coursePeriods=new ArrayList();
						
						if(null!=childCoursePros[2]&&!"".equals(childCoursePros[2].trim())){
							
							String[] coursePeriodsStrArr=childCoursePros[2].split("∮");
							for (int j = 0; j < coursePeriodsStrArr.length; j++) {
								String[] coursePeriodStrArr=coursePeriodsStrArr[j].split("∫");
								HashMap coursePeriod=new HashMap();
								coursePeriod.put("periodId", coursePeriodStrArr[0]);
								coursePeriod.put("periodName", coursePeriodStrArr[1]);
								coursePeriod.put("studyTime", coursePeriodStrArr[2]);
								coursePeriods.add(coursePeriod);
							}
						}
						
						childCourse.put("childCoursePeriods",coursePeriods);
						childCourses.add(childCourse);
					}
				}
				
				mainCourse.put("childCourses", childCourses);
				// 添加到主课程
				mainCourses.add(mainCourse);
			}

		}
		Page resultPage = new Page<HashMap>(mainCourses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}
	
	

	@Override
	public Page getCourseInfosTree(HashMap map) {
		String searchStr = (String) map.get("searchStr");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"+
				"	temp1.cat_id catId,"+
				"	temp1.cat_name catName,"+
				"	temp2.productId,"+
				"	temp2.productName,"+
				"	temp2.logo1 logo,"+
				"	temp2.childCourses"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			product_category.cat_id,"+
				"			product_category.cat_name"+
				"		FROM"+
				"			product_category"+
				"	) AS temp1,"+
				"	("+
				"		SELECT"+
				"			temp3.cat_id catId,"+
				"			temp3.id productId,"+
				"			temp3.logo1,"+
				"			temp3. NAME productName,"+
				"			GROUP_CONCAT("+
				"				if(strcmp(temp4.id,''),temp4.id,\'-1\'),"+
				"				\'∫\',"+
				"				if(strcmp(temp4. NAME,''),temp4. NAME ,\' \'),"+
				"				\'∫\',"+
				"				if(strcmp(temp4.periodCourses,''),temp4.periodCourses, \' \'),"+
				"				\'∫\'"+
				"			ORDER BY"+
				"				temp4.id DESC SEPARATOR \'∪\'"+
				"			) childCourses"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					mallproduct.id,"+
				"					mallproduct.`name`,"+
				"					mallproduct.logo1 ,"+
				"					mallproduct.cat_id"+
				"				FROM"+
				"					mallproduct"+
				"				WHERE"+
				"					mallproduct.mp3type = 2"+
				"				ORDER BY"+
				"					mallproduct.id DESC"+
				"			) AS temp3,"+
				"			("+
				"				SELECT"+
				"					course.id,"+
				"					course.`name`,"+
				"					course.productid,"+
				"					GROUP_CONCAT("+
				"						if(strcmp(course_period.id,''),course_period.id,\'-1\'),"+
				"						\'∑\',"+
				"						if(strcmp(course_period.courseperiod_name,''),course_period.courseperiod_name,\' \'),"+
				"						\'∑\'"+
				"					ORDER BY"+
				"						course_period.id SEPARATOR \'∈\'"+
				"					) periodCourses"+
				"				FROM"+
				"					course"+
				"				LEFT JOIN course_period ON course.id = course_period.course_id"+
				"				GROUP BY"+
				"					course.id"+
				"			) AS temp4"+
				"		WHERE"+
				"			temp3.id = temp4.productid"+
				"		GROUP BY"+
				"			productId"+
				"	) AS temp2"+
				" WHERE"+
				"	temp1.cat_id = temp2.catId"+
				" AND temp2.productName LIKE \'%"+searchStr+"%\'"+
				" GROUP BY"+
				"	productName"+
				" ORDER BY"+
				"	catId");

		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 课程选择数组
		ArrayList<HashMap> mainCourses = new ArrayList<HashMap>();

		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 添加主课程
				HashMap mainCourse = new HashMap();
				Object[] obj = dataList.get(z);
				mainCourse.put("catId", obj[0]);
				mainCourse.put("catName", obj[1]);
				mainCourse.put("productId", obj[2]);
				mainCourse.put("productName", obj[3]);
				mainCourse.put("productLogo", obj[4]);
				ArrayList childCourses=new ArrayList();
				String[] childCourseA=obj[5].toString().split("∪");
				
				for (int i = 0; i < childCourseA.length; i++) {
					//添加子课程
					String[] childCourseB=childCourseA[i].split("∫");
					HashMap childCourse=new HashMap();
					if(!"-1".equals(childCourseB[0])){
						childCourse.put("childCourseId", childCourseB[0]);
						childCourse.put("childCourseName", childCourseB[1]);
						ArrayList periodCourses=new ArrayList();
						String[] periodCourseA=childCourseB[2].toString().split("∈");
						for (int j = 0; j < periodCourseA.length; j++) {
							//添加子课时
							HashMap periodCourse=new HashMap();
							String[] periodCourseB=periodCourseA[j].split("∑");
							if(!"-1".equals(periodCourseB[0])){
								periodCourse.put("periodCourseId",periodCourseB[0]);
								periodCourse.put("periodCourseName",periodCourseB[1]);
								periodCourses.add(periodCourse);
							}
							
						}
						childCourse.put("periodCourses", periodCourses);
						childCourses.add(childCourse);
					}
					
				}
				mainCourse.put("childCourses", childCourses);
				mainCourses.add(mainCourse);
			}

		}

		Page resultPage = new Page<HashMap>(mainCourses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page<?> getCourseInfoByMainCourseId(HashMap<String, String> map) {
		
		String productId = map.get("productId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"+
				"	mainCourse.id mainCourseId,"+
				"	mainCourse.`name` mainCourseName,"+
				"	mainCourse.logo1 mainCourseLogo,"+
				"	GROUP_CONCAT("+
				"		IF(strcmp(childCourse.id,''),childCourse.id,-1),"+
				"		\'∫\',"+
				"		IF(strcmp(childCourse.`name`,''),childCourse.`name`,' '),"+
				"		\'∫\',"+
				"		IF(strcmp(childCourse.total_class,''),childCourse.total_class,-1),"+
				"		\'∫\',"+
				"		IF(strcmp(childCourse.coursePeriods,''),childCourse.coursePeriods,' '),"+
				"		\'∫\'"+
				"	ORDER BY"+
				"		childCourse.id SEPARATOR \'∪\'"+
				"	) childCourseInfo"+
				" FROM"+
				"	mallproduct mainCourse"+
				" INNER JOIN ("+
				"	SELECT"+
				"		course.id,"+
				"		course.productid,"+
				"		course.`name`,"+
				"		course.total_class,"+
				"		GROUP_CONCAT("+
				"			IF(strcmp(course_period.id,''),course_period.id,-1),"+
				"			\'∑\',"+
				"			IF(strcmp(course_period.courseperiod_name,''),course_period.courseperiod_name,' '),"+
				"			\'∑\',"+
				"			IF(strcmp(course_period.missionCmdList,''),course_period.missionCmdList,' '),"+
				"			\'∑\'"+
				"		ORDER BY"+
				"			course_period.id DESC SEPARATOR \'∈\'"+
				"		) coursePeriods"+
				"	FROM"+
				"		( SELECT * FROM course WHERE course.productid = :productId ) AS course "+
				"	LEFT JOIN course_period ON course.id = course_period.course_id"+
				"	GROUP BY"+
				"		course.id"+
				" ) childCourse"+
				" WHERE"+
				"	mainCourse.id = childCourse.productid"+
				" AND mainCourse.id = :productId ");

		Query query = this.getQuery(sql.toString());

		query.setInteger("productId", Integer.parseInt(productId));

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 主课程数组
		ArrayList<MainCourseDto> mainCourses = new ArrayList<MainCourseDto>();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				MainCourseDto mainCourse = new MainCourseDto();
				Object[] obj = dataList.get(z);
				mainCourse.setMainCourseId((Integer) obj[0]);
				mainCourse.setMainCourseName((String) obj[1]);
				mainCourse.setMainCourseLogo((String) obj[2]);
				if(null!=obj[3]){
					String childCourseStr = obj[3].toString().trim();
					// 子课程列表
					ArrayList childCourses = new ArrayList();
					String[] childCourseArr = childCourseStr.split("∪");
					for (int i = 0; i < childCourseArr.length; i++) {
						String[] childCoursePros = childCourseArr[i].split("∫");
						//有子课程才加进去
						HashMap childCourse = new HashMap();
						childCourse.put("childCourseId", childCoursePros[0]);
						childCourse.put("childCourseName", childCoursePros[1]);
						childCourse.put("childCourseTotalClass", childCoursePros[2]);
						ArrayList coursePeriods=new ArrayList();
						String[] coursePeriodsStrArr=childCoursePros[3].split("∈");
						for (int j = 0; j < coursePeriodsStrArr.length; j++) {
							String[] coursePeriodStrArr=coursePeriodsStrArr[j].split("∑");
							//有子课时才加进去
							if(!coursePeriodStrArr[0].equals("-1")){
								HashMap coursePeriod=new HashMap();
								coursePeriod.put("periodId", coursePeriodStrArr[0]);
								coursePeriod.put("periodName", coursePeriodStrArr[1]);
								coursePeriod.put("missionCmdList", coursePeriodStrArr[2]);
								coursePeriods.add(coursePeriod);
							}
						}
						childCourse.put("childCoursePeriods",coursePeriods);
						childCourses.add(childCourse);
					}
					mainCourse.setChildrenCourses(childCourses);
				}
				// 添加到主课程
				mainCourses.add(mainCourse);
			}

		}
		Page resultPage = new Page<MainCourseDto>(mainCourses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	/**
	 * 获取我的课程计划接口
	 */
	@Override
	public ArrayList getMyCoursePlans(String epalId) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT"+
				"	course_plan.id,"+
				"	course_plan.plan_name,"+
				"	course_plan.epalid,"+
				"	course_plan.memberid,"+
				"	course_plan.createtime,"+
				"	course_plan.sort,"+
				"	course_plan.summary,"+
				"	GROUP_CONCAT("+
				"		if(strcmp(course_plan_info.id,'') , course_plan_info.id ,'-1' ) ,"+
				"		\'∫\',"+
				"		if(strcmp(course_plan_info.plan_id,'') , course_plan_info.plan_id ,' ') ,"+
				"		\'∫\',"+
				"		if(strcmp(course_plan_info.productid,'') , course_plan_info.productid ,' ') ,"+
				"		\'∫\',"+
				"		if(strcmp(course_plan_info.courseid,'') , course_plan_info.courseid ,' ') ,"+
				"		\'∫\',"+
				"		if(strcmp(course_plan_info.periodid,'') , course_plan_info.periodid ,' ') ,"+
				"		\'∫\',"+
				"		if(strcmp(course_plan_info.sort,'') , course_plan_info.sort ,' ') ,"+
				"		\'∫\',"+
				"		if(strcmp(course_plan_info.summary,'') , course_plan_info.summary ,' ') ,"+
				"		\'∫\' order by  course_plan_info.sort SEPARATOR \'∪\'"+
				"	) planInfos"+
				" FROM"+
				"	course_plan"+
				" LEFT JOIN course_plan_info ON course_plan.id = course_plan_info.plan_id"+
				" AND course_plan.epalid = \'"+epalId+"\'"+
				" GROUP BY course_plan.id ORDER BY sort DESC");
		ArrayList coursePlans=new ArrayList();
		ArrayList<Object[]> objects=(ArrayList<Object[]>) this.executeSQL(sql.toString());
		for (int i = 0; i < objects.size(); i++) {
			HashMap<String, Object> coursePlan=new HashMap<String, Object>();
			coursePlan.put("planId", objects.get(i)[0]);
			coursePlan.put("planName", objects.get(i)[1]);
			coursePlan.put("epalId", objects.get(i)[2]);
			coursePlan.put("memberId", objects.get(i)[3]);
			coursePlan.put("createTime", objects.get(i)[4]);
			coursePlan.put("sort", objects.get(i)[5]);
			coursePlan.put("summary", objects.get(i)[6]);
			ArrayList coursePlanInfos=new ArrayList();
			if(!"".equals(objects.get(i)[7].toString().trim())){
				String[] coursePlanInfoA=((String) objects.get(i)[7]).split("∪");
				for (int j = 0; j < coursePlanInfoA.length; j++) {
					HashMap<String, Object> coursePlanInfo=new HashMap<String, Object>();
					String[] coursePlanInfoB=coursePlanInfoA[j].split("∫");
					if(!"-1".equals(coursePlanInfoB[0])){
						coursePlanInfo.put("planInfoId", coursePlanInfoB[0]);
						coursePlanInfo.put("planId", coursePlanInfoB[1]);
						coursePlanInfo.put("mainCourseId", coursePlanInfoB[2]);
						coursePlanInfo.put("childCourseId", coursePlanInfoB[3]);
						coursePlanInfo.put("periodCourseId", coursePlanInfoB[4]);
						coursePlanInfo.put("sort", coursePlanInfoB[5]);
						coursePlanInfo.put("summary", coursePlanInfoB[6]);
						coursePlanInfos.add(coursePlanInfo);
					}
				}
			}
			coursePlan.put("coursePlanInfos", coursePlanInfos);
			coursePlans.add(coursePlan);
		}
		
		return coursePlans;
	}

	/**
	 * 删除课程计划内执行单位
	 */

	@Override
	public void delCoursePlanInfo(Integer infoId) {
		String delSql = "delete from course_plan_info where id = " + infoId;
		this.executeUpdateSQL(delSql);
	}

	/**
	 * 删除课程计划
	 */
	@Override
	public void delCoursePlan(String coursePlanId) {
		Integer planId = Integer.parseInt(coursePlanId);
		String sql = "select id from course_plan_info where plan_id = "
				+ planId;
		List<Integer> list = this.executeSQL(sql);
		for (int i = 0; i < list.size(); i++) {
			Integer id = list.get(i);
			String delS = "delete from course_plan_info where id = " + id;
			this.executeUpdateSQL(delS);
		}

		String delSql = "delete from course_plan where id = " + planId;
		this.executeUpdateSQL(delSql);
	}

	/**
	 * 更新课程计划内执行单位的顺序
	 */
	@Override
	public void updateCoursePlanPeriodSort(String sortedPeriodIds) {
		JSONArray array = JSONArray.fromObject(sortedPeriodIds);
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			String sql = "update course_plan_info set sort = "
					+ object.getInt("sort") + " where id = "
					+ object.getInt("id");
			this.executeUpdateSQL(sql);
		}
	}

	@Override
	public Page searchCourseInfo(HashMap map) {
		String epalId = (String) map.get("epalId");
		String productId = (String) map.get("productId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"
				+ "	temp_1.mainCourseId,"
				+ "	temp_1.mainCourseName,"
				+ "	temp_1.mainCourseLogo,"
				+ "	GROUP_CONCAT("
				+ "	temp_1.childCourseId,\'#\',"
				+ "	temp_1.childCourseName,\'#\',"
				+ "	temp_1.mainCourseTotalClass,\'#\',"
				+ "	IFNULL( temp_2.courseScheduleCurrentClass,0),\'#\',"
				+ "	IFNULL(temp_2.courseScheduleTime, NOW()) , \'\' ORDER BY temp_1.childCourseId  SEPARATOR \'@\'"
				+ "	) childCourseInfo" + " FROM" + "	(" + "		SELECT"
				+ "			mainCourse.id mainCourseId,"
				+ "			mainCourse.`name` mainCourseName,"
				+ "			mainCourse.logo1 mainCourseLogo,"
				+ "			childCourse.id childCourseId,"
				+ "			childCourse.`name` childCourseName,"
				+ "			IFNULL(childCourse.total_class, 0) mainCourseTotalClass"
				+ "		FROM" + "			mallproduct mainCourse"
				+ "		INNER JOIN course childCourse" + "		WHERE"
				+ "			mainCourse.id = childCourse.productid"
				+ "		AND mainCourse.id = :productId " + "	) temp_1"
				+ " LEFT JOIN (" + "	SELECT" + "		temp.id courseScheduleId,"
				+ "		temp.courseid childCourseId,"
				+ "		temp.cur_class courseScheduleCurrentClass,"
				+ "		temp.create_time courseScheduleTime" + "	FROM" + "		("
				+ "			SELECT" + "				*" + "			FROM" + "				course_schedule"
				+ "			ORDER BY" + "				course_schedule.create_time DESC"
				+ "		) AS temp" + "	WHERE" + "		1 = 1"
				+ "	AND temp.epal_id = :epalId "
				+ "	AND temp.productid = :productId" + "	GROUP BY"
				+ "		temp.courseid" + "	ORDER BY" + "		temp.create_time DESC"
				+ " ) temp_2 ON temp_1.childCourseId = temp_2.childCourseId");

		Query query = this.getQuery(sql.toString());

		query.setInteger("productId", Integer.parseInt(productId));
		query.setString("epalId", epalId);

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 主课程数组
		ArrayList<MainCourseDto> mainCourses = new ArrayList<MainCourseDto>();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				MainCourseDto mainCourse = new MainCourseDto();
				Object[] obj = dataList.get(z);
				mainCourse.setMainCourseId((Integer) obj[0]);
				mainCourse.setMainCourseName((String) obj[1]);
				mainCourse.setMainCourseLogo((String) obj[2]);
				String childCourseStr = (String) obj[3];
				String[] childCourseArr = childCourseStr.split("@");
				// 子课程列表
				ArrayList childCourses = new ArrayList();
				for (int i = 0; i < childCourseArr.length; i++) {
					String[] childCoursePros = childCourseArr[i].split("#");
					HashMap childCourse = new HashMap();
					childCourse.put("childCourseId", childCoursePros[0]);
					childCourse.put("childCourseName", childCoursePros[1]);
					childCourse
							.put("childCourseTotalClass", childCoursePros[2]);
					childCourse.put("childCourseCurrentClass",
							childCoursePros[3]);
					childCourse.put("courseScheduleTime", childCoursePros[4]);
					childCourses.add(childCourse);
				}
				mainCourse.setChildrenCourses(childCourses);
				// 添加到主课程
				mainCourses.add(mainCourse);
			}

		}
		Page resultPage = new Page<MainCourseDto>(mainCourses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}
	

	@Override
	public void addCoursePeriod(CoursePeriod course) {
		this.save(course);
	}

	@Override
	public void deleteMainCourse(String productId) {
		this.executeUpdateSQL("DELETE FROM course_period where course_period.course_id in (SELECT course.id from course where course.productid = "+productId+" )");
		this.executeUpdateSQL("DELETE FROM course where course.productid = "+productId);
		this.executeUpdateSQL("DELETE FROM mallproduct where mallproduct.id = "+productId);
	}
	
	@Override
	public void deleteChildCourse(String courseId) {
		this.executeUpdateSQL("DELETE FROM course_period where course_period.course_id = "+courseId);
		this.executeUpdateSQL("DELETE FROM course where course.id = "+courseId);
	}

	@Override
	public void deletePeriodCourse(String periodId) {
		this.executeUpdateSQL("DELETE FROM course_period where course_period.id = "+periodId);
	}

	@Override
	public void addCourse(Course course) {
		this.save(course);
	}

	@Override
	public void updateCourseProject(Integer planId) {
		this.executeUpdateSQL("UPDATE courseproject "+
			" SET courseproject.recordcount = ("+
			"	SELECT"+
			"		*"+
			"	FROM"+
			"		("+
			"			SELECT"+
			"			IF ("+
			"				strcmp("+
			"					courseproject.recordcount,"+
			"					\'\'"+
			"				),"+
			"				courseproject.recordcount + 1,"+
			"				1"+
			"			) recordcount"+
			"			FROM"+
			"				courseproject"+
			"			WHERE"+
			"				courseproject.id = "+planId+
			"		) AS temp"+
			" )"+
			" WHERE"+
			"	courseproject.id = "+planId);
	}

	@Override
	public void saveCourseBookLib(CourseBookLib bookLib) {
		this.save(bookLib);
	}

	@Override
	public ArrayList<CourseBookLib> findCourseBookLib(String productId) {
		return (ArrayList<CourseBookLib>) this.executeHQL("from CourseBookLib where productId = "+productId);
	}

	@Override
	public void delCourseBookLibs(Integer productId) {
		this.executeUpdateSQL("delete from course_booklib where productid = " + productId);
	}

	@Override
	public Integer getRecordCountSum(String id) {
		List list=this.executeSQL("SELECT IFNULL(sum(recordcount),0) recordcountSum from courseproject where id="+id);
		BigDecimal bigDecimal=(BigDecimal) list.get(0);
		return bigDecimal.intValue();
	}

	@Override
	public CourseSchedule getCurStudyedCourse(String courseId, String epalId) {
		List res=this.executeHQL("from CourseSchedule where courseid = "+ courseId + "and epalId = '"+ epalId +"' order by createTime Desc");
		if(res.size()>0){
			return (CourseSchedule) res.get(0);
		}
		return null;
	}

	@Override
	public Page searchCourseProject(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,epalId,recordcount,createdate from courseproject where system_plan = "+map.get("systemPlan"));
		if(!"".equals(map.get("startDate"))&&!"".equals(map.get("endDate"))){
			sql.append(" and createdate>=:startDate and createdate<=:endDate");
		}
		sql.append(" order by createdate desc");
		Query query = this.getQuery(sql.toString());
		
		
		if(!"".equals(map.get("startDate"))&&!"".equals(map.get("endDate"))){
			query.setString("startDate", map.get("startDate").toString());
			query.setString("endDate", map.get("endDate").toString());
		}
		
		
		
		
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		ArrayList<HashMap> courseProjects = new ArrayList<HashMap>();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				HashMap courseProject = new HashMap();
				Object[] obj = dataList.get(z);
			
				courseProject.put("id",obj[0]);
				courseProject.put("epalId", obj[1]);
				courseProject.put("recordcount", obj[2]);
				courseProject.put("createDate", obj[3]);
				courseProjects.add(courseProject);
			}

		}
		Page resultPage = new Page<HashMap>(courseProjects,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public JSONObject getClassCourseRecordDetail(String classGradesId) {
		// 获取课程表列表

		JSONObject record = new JSONObject();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select class_course_id,student_id,complete,score from class_course_record where class_grades_id=" + classGradesId);
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 10000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				JSONObject studeRecord = new JSONObject();
				Object[] obj = dataList.get(z);
				Integer classCourseId= (Integer)obj[0];
				Integer studentId = (Integer)obj[1];
				studeRecord.put("complete",obj[2]);
				studeRecord.put("score", obj[3]);
				String key =  studentId + "_" + classCourseId;
				record.put(key,studeRecord);
			}
		}
		
		JSONObject result = new JSONObject();
		result.put("classCourseList", getClassCourseList(classGradesId));
		result.put("record", record);
		result.put("studentList", getStudentListByClassGradesId(classGradesId));
		
		return result;
	}
	public JSONArray getClassCourseList(String classGradesId){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.class_name,b.do_day,b.id from class_room as a,class_course as b where b.do_slot=300 and a.id=b.class_room_id and b.class_grades_id=" + classGradesId);
		sql.append(" order by b.do_day asc");
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 10000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray classCourseList = new JSONArray();
		if (dataList.size() > 0) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				JSONObject classCourse = new JSONObject();
				Object[] obj = dataList.get(z);
				classCourse.put("classRoomName",obj[0]);
				classCourse.put("doDay", obj[1]);
				classCourse.put("classCourseId", obj[2]);
				classCourseList.add(classCourse);
			}
		}
		return classCourseList;
		
		
	}
	public JSONArray getStudentListByClassGradesId(String classGradesId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"
				+ "	cgr.class_student_id,"
				+ "	cs.`name` stuName,"
				+ "	cs.epal_id,"
				+ "cgr.gradesStatus"
				+ " FROM"
				+ "	class_grades_rela cgr"
				+ " INNER JOIN class_student cs ON cgr.class_grades_id = :classGradesId"
				+ " AND cgr.class_student_id = cs.id ");
		Query query = this.getQuery(sql.toString());

		query.setInteger("classGradesId",
				Integer.parseInt(classGradesId));

		Page dataPage = this.pageQueryBySQL(query, 1, 10000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		// 在线课堂数组
		JSONArray classStudentDtos = new JSONArray();
		if (dataList.size() > 0 && dataList.get(0)[0] != null) {
			for (int z = 0; z < dataList.size(); z++) {
				// 主课程
				JSONObject classStudentDto = new JSONObject();
				Object[] obj = dataList.get(z);
				classStudentDto.put("id",obj[0]);
				classStudentDto.put("studentName",obj[1]);
				classStudentDto.put("epalId",obj[2]);
				classStudentDto.put("gradesStatus",obj[3]);
				classStudentDtos.add(classStudentDto);
			}
		}
		return classStudentDtos;
	}
	

}
