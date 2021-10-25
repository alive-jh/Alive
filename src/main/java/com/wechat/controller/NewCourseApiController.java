package com.wechat.controller;

import com.sun.jmx.snmp.Timestamp;
import com.wechat.common.dto.QueryDto;
import com.wechat.entity.CoursePlan;
import com.wechat.entity.CoursePlanInfo;
import com.wechat.entity.DeviceSchedule;
import com.wechat.service.CourseService;
import com.wechat.service.DeviceService;
import com.wechat.service.MallProductService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("api")
public class NewCourseApiController {

	@Autowired
	private CourseService courseService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private MallProductService mallProductService;

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public MallProductService getMallProductService() {
		return mallProductService;
	}

	public void setMallProductService(MallProductService mallProductService) {
		this.mallProductService = mallProductService;
	}

	/**
	 * 1.我的课程学习进度接口（学习记录模块）
	 * 
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCourseStudyRecords")
	public void getCourseStudyRecords(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		map.put("page", null != page && !"".equals(page) ? page : "1");
		map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
				: "1000");
		// 获取机器人所有学习的课程的相关信息
		Page courseStudyRecords = courseService
				.getCourseStudyRecords(map);
		List courseStudyRecordInfos = courseStudyRecords.getItems();	
		JsonResult.JsonResultInfo(response, courseStudyRecordInfos);

	}

	/**
	 * 2.0 查询机器人所有课时学习记录详细信息接口（新UI数据结构）（学习记录模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCoursePeriodRecords")
	public void getCourseRecords(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize",
				ParameterFilter.emptyFilter("1000", "pageSize", request));
		// 获取机器人所有学习的课程的相关信息
		Page<?> coursePeriodRecords = courseService
				.getCoursePeriodsRecords(map);
		List<?> coursePeriodRecordsJson = coursePeriodRecords.getItems();
		JsonResult.JsonResultInfo(response, coursePeriodRecordsJson);
	}
	
	/**
	 * 2.1 课程学习记录详细信息接口（直接通过主课程Id查出主课程子课程课时学习记录的详细信息）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCourseInfoRecordByMainCourseId")
	public void getCourseInfoRecordByMainCourseId(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
		map.put("productId", ParameterFilter.emptyFilter("-1", "productId", request));
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
		// 获取机器人所有学习的课程的相关信息
		Page<?> courseInfoRecords = courseService.getCourseInfoRecordByMainCourseId(map);
		List<?> courseInfoRecordsJson = courseInfoRecords.getItems();
		JsonResult.JsonResultInfo(response, courseInfoRecordsJson);
	}
	
	/**
	 * 2.2 课程学习记录详细信息接口（直接通过epalId查出所有的主课程子课程课时学习记录的详细信息）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCourseInfoRecords")
	public void getCourseInfoRecords(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
		// 获取机器人所有学习的课程的相关信息
		Page<?> courseInfoRecords = courseService.getCourseInfoRecords(map);
		List<?> courseInfoRecordsJson = courseInfoRecords.getItems();
		JsonResult.JsonResultInfo(response, courseInfoRecordsJson);
	}
	

	/**
	 * 3.课程详细信息接口[附加课程进度]（APP）（课程详情模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCourseInfo")
	public void getCourseInfo(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
		map.put("productId",
				ParameterFilter.emptyFilter("-1", "productId", request));
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize",
				ParameterFilter.emptyFilter("1000", "pageSize", request));
		// 获取机器人所有学习的课程的相关信息
		Page<?> courseInfo = courseService.searchCourseInfo(map);
		List<?> courseInfoJson = courseInfo.getItems();
		JsonResult.JsonResultInfo(response, courseInfoJson);
	}
	
	
	/**
	 * 3.1 课程详细信息接口（机器人/APP）（课程详情模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCourseInfoByMainCourseId")
	public void getCourseInfoByMainCourseId(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("productId",
				ParameterFilter.emptyFilter("-1", "productId", request));
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize",
				ParameterFilter.emptyFilter("1000", "pageSize", request));
		// 获取机器人所有学习的课程的相关信息
		Page<?> courseInfo = courseService.getCourseInfoByMainCourseId(map);
		
		List<?> courseInfoJson = courseInfo.getItems();
		JsonResult.JsonResultInfo(response, courseInfoJson);
	}

	/**
	 * 4.机器人主课程下子课程的所有课时详细信息（APP/机器人）（课程详情模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getAllCoursePeriodsByCourseId")
	public void getAllCoursePeriodsByCourseId(HttpServletRequest request,
			HttpServletResponse response) {
		String courseId = ParameterFilter.emptyFilter("", "courseId", request);
		Page courses = this.courseService
				.getAllCoursePeriodsByCourseId(courseId);
		JsonResult.JsonResultInfo(response, courses.getItems());

	}

	/**
	 * 5.查询课程所有课程课时信息树型选择接口（课程计划模块）
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("getCoursePlanSelectTree")
	public void getCoursePlanSelectTree(HttpServletRequest request,
			HttpServletResponse response) {
		String epalId = ParameterFilter.emptyFilter("-1", "epalId", request);
		Integer planId = Integer.parseInt(ParameterFilter.emptyFilter("-1",
				"planId", request));
		HashMap map = new HashMap();
		map.put("epalId", epalId);
		map.put("planId", planId);
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize",
				ParameterFilter.emptyFilter("1000", "pageSize", request));
		Page coursePlanSelectTree = this.courseService
				.getCoursePlanSelectTree(map);
		JsonResult.JsonResultInfo(response, coursePlanSelectTree.getItems());

	}
	
	/**
	 * 5.1 查询课程所有课程课时信息树型选择接口(带分页查询和模糊匹配)（课程计划模块）
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("getCourseInfosTree")
	public void getCourseInfosTree(HttpServletRequest request,
			HttpServletResponse response) {
		String searchStr = ParameterFilter.emptyFilter("", "searchStr", request);
		HashMap map = new HashMap();
		map.put("searchStr", searchStr);
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize",
				ParameterFilter.emptyFilter("1000", "pageSize", request));
		Page coursePlanSelectTree = this.courseService
				.getCourseInfosTree(map);
		JsonResult.JsonResultInfo(response, coursePlanSelectTree.getItems());

	}

	/**
	 * 6.修改课程计划组内课时执行排列顺序（课程计划模块）(废)
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("updateCoursePlanPeriodSort")
	public void updateCoursePlanPeriodSort(HttpServletRequest request,
			HttpServletResponse response) {
		String sortedPeriodIds = ParameterFilter.emptyFilter("",
				"sortedPeriodIds", request);
		if (!"".equals(sortedPeriodIds)) {
			this.courseService.updateCoursePlanPeriodSort(sortedPeriodIds);
			JsonResult.JsonResultInfo(response, "修改排序成功");
		} else {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 7.添加课程计划接口（课程计划模块）
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("addCoursePlan")
	public void addCoursePlan(HttpServletRequest request,
			HttpServletResponse response) {
		String planName = ParameterFilter.emptyFilter("", "planName", request);
		String epalId = ParameterFilter.emptyFilter("-1", "epalId", request);
		String memberId = ParameterFilter
				.emptyFilter("-1", "memberId", request);
		String sort = ParameterFilter.emptyFilter("0", "sort", request);
		String summary = ParameterFilter.emptyFilter("0", "summary", request);
		String coursePlanInfos = ParameterFilter.emptyFilter("",
				"coursePlanInfos", request);
		String doTime = ParameterFilter.emptyFilter(
				new Timestamp().getDateTime() + "", "doTime", request);
		String doStyle = ParameterFilter.emptyFilter("0", "doStyle", request);
		HashMap map = new HashMap();
		map.put("planName", planName);
		map.put("epalId", epalId);
		map.put("memberId", memberId);
		map.put("sort", Integer.parseInt(sort));
		map.put("summary", summary);
		CoursePlan coursePlan = courseService.addCoursePlan(map);
		// 添加日程
		DeviceSchedule deviceSchedule = new DeviceSchedule();
		deviceSchedule.setCatalogFile("");
		deviceSchedule.setContent("lesson:" + coursePlan.getId() + "");
		deviceSchedule.setDescription(coursePlan.getSummary());
		deviceSchedule.setDoTime(doTime);
		deviceSchedule.setEpalId(coursePlan.getEpalId());
		deviceSchedule.setEvent(coursePlan.getPlanName());
		deviceSchedule.setEventCN(coursePlan.getPlanName());
		deviceSchedule.setIsDef(0);
		deviceSchedule.setNote("");
		deviceSchedule.setPeriod(doStyle);
		deviceSchedule.setSid("-1");
		deviceSchedule.setState(1);
		deviceSchedule.setTitle("init");
		deviceSchedule.setType("schedule");
		deviceService.saveDeviceSchedule(deviceSchedule);
		// 添加课程计划执行单位
		JSONArray coursePlanInfosJson = JSONArray.fromObject(coursePlanInfos);
		for (int i = 0; i < coursePlanInfosJson.size(); i++) {
			CoursePlanInfo coursePlanInfo = (CoursePlanInfo) JSONObject.toBean(
					coursePlanInfosJson.getJSONObject(i), CoursePlanInfo.class);
			coursePlanInfo.setPlanId(coursePlan.getId());
			if("".equals(coursePlan.getSummary())){
				coursePlan.setSummary("课程简介");
			}
			this.courseService.addCoursePlanInfo(coursePlanInfo);
		}
		JsonResult.JsonResultInfo(response, coursePlan);
	}

	/**
	 * 8.删除课程计划接口（课程计划模块）
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("delCoursePlan")
	public void delCoursePlan(HttpServletRequest request,
			HttpServletResponse response) {
		String planId = ParameterFilter.emptyFilter("-1", "planId", request);
		this.courseService.delCoursePlan(planId);
		JsonResult.JsonResultInfo(response, "删除成功");
	}

	/**
	 * 9.删除课程计划内的执行单位接口（课程计划模块）(废)
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("delCoursePlanInfo")
	public void delCoursePlanInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String planInfoId = ParameterFilter.emptyFilter("-1", "planInfoId",
				request);
		this.courseService.delCoursePlanInfo(planInfoId);
		JsonResult.JsonResultInfo(response, "删除成功");

	}

	/**
	 * 10.修改课程计划详情接口（课程计划模块）
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	
	@RequestMapping("updateCoursePlan")
	public void updateCoursePlan(HttpServletRequest request,
			HttpServletResponse response) {
		String planId = ParameterFilter.emptyFilter("-1", "planId", request);
		String coursePlanInfos = ParameterFilter.emptyFilter("",
				"coursePlanInfos", request);
		this.courseService.delCoursePlanInfos(planId);
		ArrayList coursePlanInfosArr= this.courseService.updateCoursePlan(planId, coursePlanInfos);
		JsonResult.JsonResultInfo(response, coursePlanInfosArr);

	}

	/**
	 * 11.查询我的课程计划列表接口（课程计划模块）
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("getMyCoursePlans")
	public void getMyCoursePlans(HttpServletRequest request,
			HttpServletResponse response) {
		String epalId = ParameterFilter.emptyFilter("-1", "epalId", request);
		ArrayList coursePlans = this.courseService
				.getMyCoursePlans(epalId);
		JsonResult.JsonResultInfo(response, coursePlans);
	}

}
