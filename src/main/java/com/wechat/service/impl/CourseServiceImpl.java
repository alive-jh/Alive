package com.wechat.service.impl;

import com.wechat.dao.CourseDao;
import com.wechat.entity.*;
import com.wechat.service.CourseService;
import com.wechat.service.DeviceService;
import com.wechat.util.Page;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Resource
	private CourseDao courseDao;
	
	@Resource
	private DeviceService deviceService;


	@Override
	public Page searchCourseInfos(HashMap map) {
		return this.courseDao.searchCourseInfos(map);
	}

	@Override
	public Page searchCourseSchedules(HashMap map) {
		return this.courseDao.searchCourseSchedules(map);
	}

	@Override
	public MallProduct findMainCourseByProductId(Integer productId) {
		return this.courseDao.findMainCourseByProductId(productId);
	}

	@Override
	public void updateMallProduct(MallProduct mallProduct) {
		this.courseDao.updateMallProduct(mallProduct);
	}

	@Override
	public Course findChildCourseByCourseId(String courseId) {
		return this.courseDao.findChildCourseByCourseId(courseId);
	}

	@Override
	public void updateCourse(Course course) {
		this.courseDao.updateCourse(course);
	}
	

	/**
	 * 更新课程课时信息
	 */
	@Override
	public void updateCoursePeriodByPeriodId(CoursePeriod course) {
		this.courseDao.updateCoursePeriodByPeriodId(course);
	}
	
	@Override
	public void saveCoursePeriod(CoursePeriod coursePeriod) {
		this.courseDao.saveCoursePeriod(coursePeriod);
	}
	
	@Override
	public void saveCourseWord(CourseWord courseWord) {
		this.courseDao.saveCourseWord(courseWord);
	}

	@Override
	public Page searchCourseWords(HashMap map) {
		return this.courseDao.searchCourseWords(map);
	}

	@Override
	public CoursePeriod findCoursePeriodByPeriodId(String periodId) {
		return this.courseDao.findCoursePeriodByPeriodId(periodId);
	}

	@Override
	public Page getAllCoursesByProductId(String productId) {
		return this.courseDao.getAllCoursesByProductId(productId);
	}
	
	@Override
	public Page getAllCoursePeriodsByCourseId(String courseId) {
		return this.courseDao.getAllCoursePeriodsByCourseId(courseId);
	}

	@Override
	public Page getAllCourseSchedulesByEpalId(String epalId) {
		return this.courseDao.getAllCourseSchedulesByEpalId(epalId);
	}
	
	@Override
	public Page searchCourseInfo(HashMap map) {
		return this.courseDao.searchCourseInfo(map);
	}

	@Override
	public Page<?> getCoursePeriodsRecords(HashMap<String, String> map) {
		return this.courseDao.getCoursePeriodsRecords(map);
	}
	

	@Override
	public Page getCoursePlanSelectTree(HashMap map) {
		Page result=this.courseDao.getCoursePlanSelectTree(map);
		ArrayList<HashMap> coursePlanSelectTree= (ArrayList<HashMap>) result.getItems();
		for (int i = 0; i < coursePlanSelectTree.size(); i++) {
			HashMap courseCate=coursePlanSelectTree.get(i);
			String a1=(String) courseCate.get("mainCourses");
			String[] a2=a1.split("∮");
			ArrayList mainCourses=new ArrayList();
			for (int j = 0; j < a2.length; j++) {
				String[] a3=a2[j].split("∫");
				HashMap mainCourse=new HashMap();
				mainCourse.put("mainCourseId", a3[0]);
				mainCourse.put("mainCourseName", a3[1]);
				String b1=a3[2];
				String[] b2=b1.split("∞");
				ArrayList childCourses=new ArrayList();
				for (int k = 0; k < b2.length; k++) {
					String[] b3=b2[k].split("∝");
					HashMap childCourse=new HashMap();
					childCourse.put("childCourseId", b3[0]);
					childCourse.put("childCourseName", b3[1]);
					childCourse.put("childCoursePeriodIds", b3[2]);
					childCourses.add(childCourse);
				}
				mainCourse.put("childCourses", childCourses);
				mainCourses.add(mainCourse);
			}
			courseCate.remove("mainCourses");
			courseCate.put("mainCourses", mainCourses);
		}
		result.setItems(coursePlanSelectTree);
		return result;
	}
	

	@Override
	public CoursePlan addCoursePlan(HashMap map) {
		CoursePlan cPlan=this.courseDao.addCoursePlan(map);
		return cPlan;
	}
	
	@Override
	public ArrayList getMyCoursePlans(String epalId) {
		return this.courseDao.getMyCoursePlans(epalId);
	}

	@Override
	public void delCoursePlanInfo(String planInfoId) {
		Integer infoId=Integer.parseInt(planInfoId);
		this.courseDao.delCoursePlanInfo(infoId);
	}
	

	@Override
	public void delCoursePlanInfos(String planId) {
		this.courseDao.delCoursePlanInfos(planId);
	}
	

	@Override
	public Page getCourseStudyRecords(HashMap map) {
		
		return this.courseDao.getCourseStudyRecords(map);
	}
	

	@Override
	public Page<?> getCourseInfoByMainCourseId(HashMap<String, String> map) {
		return this.courseDao.getCourseInfoByMainCourseId(map);
	}

	@Override
	public void addCoursePlanInfo(CoursePlanInfo coursePlanInfo) {
		this.courseDao.addCoursePlanInfo(coursePlanInfo);
	}
	

	@Override
	public Page getCourseInfosTree(HashMap map) {
		return this.courseDao.getCourseInfosTree(map);
	}
	

	@Override
	public Page<?> getCourseInfoRecordByMainCourseId(HashMap<String, String> map) {
		return this.courseDao.getCourseInfoRecordByMainCourseId(map);
	}

	@Override
	public Page<?> getCourseInfoRecords(HashMap<String, String> map) {
		return this.courseDao.getCourseInfoRecords(map);
	}
	

	@Override
	public void deleteMainCourse(String productId) {
		this.courseDao.deleteMainCourse(productId);
	}

	@Override
	public void deleteChildCourse(String courseId) {
		this.courseDao.deleteChildCourse(courseId);
	}

	@Override
	public void deletePeriodCourse(String periodId) {
		this.courseDao.deletePeriodCourse(periodId);
	}

	@Override
	public void addCourse(Course course) {
		this.courseDao.addCourse(course);
	}

	@Override
	public void addCoursePeriod(CoursePeriod course) {
		this.courseDao.addCoursePeriod(course);
	}

	@Override
	public ArrayList updateCoursePlan(String planId, String coursePlanInfos) {
		return this.courseDao.updateCoursePlan(planId,coursePlanInfos);
	}

	@Override
	public void delCoursePlan(String coursePlanId) {
		this.courseDao.delCoursePlan(coursePlanId);
	}

	@Override
	public void updateCoursePlanPeriodSort(String sortedPeriodIds) {
		this.courseDao.updateCoursePlanPeriodSort(sortedPeriodIds);
	}

	
	@Override
	public void updateCourseProject(Integer planId) {
		this.courseDao.updateCourseProject(planId);
	}
	

	@Override
	public void saveCourseBookLib(CourseBookLib bookLib) {
		this.courseDao.saveCourseBookLib(bookLib);
	}
	
	
	@Override
	public void delCourseBookLibs(Integer productId) {
		this.courseDao.delCourseBookLibs(productId);
	}

	@Override
	public ArrayList<CourseBookLib> findCourseBookLib(String productId) {
		return this.courseDao.findCourseBookLib(productId);
	}
	

	@Override
	public Integer getRecordCountSum(String epalId) {
		return this.courseDao.getRecordCountSum(epalId);
	}
	

	@Override
	public CourseSchedule getCurStudyedCourse(String courseId, String epalId) {
		return this.courseDao.getCurStudyedCourse(courseId,epalId);
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@Override
	public Page searchCourseProject(HashMap map) {
		// TODO Auto-generated method stub
		return this.courseDao.searchCourseProject(map);
	}

	@Override
	public JSONObject getClassCourseRecordDetail(String classGradesId) {
		// TODO Auto-generated method stub
		return this.courseDao.getClassCourseRecordDetail(classGradesId);
	}


	
}
