package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.entity.dto.ChildrenCourseDto;
import com.wechat.entity.dto.CourseScheduleInfoDto;
import com.wechat.service.CourseService;
import com.wechat.service.DeviceService;
import com.wechat.service.MallProductService;
import com.wechat.service.RedisService;
import com.wechat.util.JsonResult;
import com.wechat.util.Keys;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class CourseApiController {

	@Autowired
	private CourseService courseService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private MallProductService mallProductService;

	@Resource
	private RedisService redisService;
	
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

	// 删除整个课程

	@RequestMapping("deleteMainCourse")
	public void deleteMainCourse(HttpServletRequest request,
			HttpServletResponse response) {
		String productId = ParameterFilter.emptyFilter("-1", "productId",
				request);
		this.courseService.deleteMainCourse(productId);

	}
	
	
	// 模糊搜索主课程

	@RequestMapping("searchCourse")
	public void searchCourse(HttpServletRequest request,
			HttpServletResponse response) {
		String courseStr = ParameterFilter.emptyFilter("", "courseStr",
				request);
		HashMap map = new HashMap();
		map.put("searchStr", courseStr);
		map.put("page", "1");
		map.put("pageSize", "100");
		Page result = this.courseService.searchCourseInfos(map);
		JsonResult.JsonResultInfo(response, result);
	}

	// 删除子课程

	@RequestMapping("deleteChildCourse")
	public void deleteChildCourse(HttpServletRequest request,
			HttpServletResponse response) {
		String courseId = ParameterFilter
				.emptyFilter("-1", "courseId", request);
		this.courseService.deleteChildCourse(courseId);
	}

	// 删除子课时
	@RequestMapping("deletePeriodCourse")
	public void deletePeriodCourse(HttpServletRequest request,
			HttpServletResponse response) {
		String periodId = ParameterFilter
				.emptyFilter("-1", "periodId", request);
		this.courseService.deletePeriodCourse(periodId);
	}

	/**
	 * 1.添加修改单词
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveCourseWord")
	public void saveCourseWord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String wordTxt = ParameterFilter
					.emptyFilter("", "wordTxt", request);
			String txtexplain = ParameterFilter.emptyFilter("", "txtexplain",
					request);
			String wordPic = ParameterFilter
					.emptyFilter("", "wordPic", request);
			String wordAudio = ParameterFilter.emptyFilter("", "wordAudio",
					request);
			String audioexplain = ParameterFilter.emptyFilter("",
					"audioexplain", request);
			String status = ParameterFilter.emptyFilter("0", "status", request);
			String id = ParameterFilter.emptyFilter("", "id", request);
			CourseWord courseWord = new CourseWord();
			if (!"".equals(id)) {
				courseWord.setId(Integer.parseInt(id));
			}
			courseWord.setAudioexplain(audioexplain);
			courseWord.setCreatetime(new Timestamp(System.currentTimeMillis()));
			courseWord.setTxtexplain(txtexplain);
			courseWord.setWordAudio(wordAudio);
			courseWord.setWordPic(wordPic);
			courseWord.setWordTxt(wordTxt);
			courseWord.setStatus(Integer.parseInt(status));
			courseService.saveCourseWord(courseWord);
			JsonResult.JsonResultInfo(response, courseWord);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 2.创建课程计划
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveCourseProduct")
	public void saveCourseProduct(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String memberId = ParameterFilter.emptyFilter("-1", "memberId",
					request);
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String planType = ParameterFilter.emptyFilter("4", "planType",
					request);
			String systemPlan = ParameterFilter.emptyFilter("-1", "systemPlan",
					request);
			String courseId = request.getParameter("courseId");
			String projectName = request.getParameter("projectName");
			String projectId = request.getParameter("projectId");
			if (!"".equals(courseId) && courseId != null) {
				String[] courseIds = courseId.split(",");

				CourseProject courseProject = new CourseProject();
				
				courseProject.setCreateDate(new Date());
				courseProject.setEpalId(epalId);
				courseProject.setPlanType(planType);
				courseProject.setSystemPlan(Integer.parseInt(systemPlan));
				courseProject.setMemberId(new Integer(memberId));
				courseProject.setProjectName(projectName);
				if (!"".equals(projectId) && projectId != null) {
					courseProject.setId(new Integer(projectId));
				}
				String sort = this.mallProductService
						.searchCourseProjectMaxSortByEpalId(epalId);
				courseProject.setSort(new Integer(sort) + 1);
				this.mallProductService.saveCourseProject(courseProject);
				
				projectId=courseProject.getId()+"";

				for (int i = 0; i < courseIds.length; i++) {

					if (!"".equals(courseIds[i]) && courseIds != null) {
						CourseProjectInfo courseProjectInfo = new CourseProjectInfo();
						courseProjectInfo.setProjectId(courseProject.getId());
						courseProjectInfo.setCourseId(new Integer(courseIds[i]
								.toString()));
						this.mallProductService
								.saveCourseProjectInfo(courseProjectInfo);
					}
				}

			} else {
				JsonResult.JsonResultError(response, 1000);
			}

			JsonResult.JsonResultInfo(response, "projectId:"+projectId);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 3.删除课程计划
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteCourseProduct")
	public void deleteCourseProduct(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String projectId = request.getParameter("projectId");

			if (!"".equals(projectId) && projectId != null) {

				this.mallProductService.deleteCourseProject(projectId);
				JSONObject result = new JSONObject();
				result.put("success", 1);
				JsonResult.JsonResultInfo(response, "");
			} else {
				JsonResult.JsonResultError(response, 1000);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 4.查询机器人账号的课程计划
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchCourseProject")
	public void searchCourseProject(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String page = "1";
			String rowsPerPage = "20";
			if (!"".equals(request.getParameter("page"))
					&& request.getParameter("page") != null) {
				page = request.getParameter("page");
			}
			if (!"".equals(request.getParameter("pageSize"))
					&& request.getParameter("pageSize") != null) {
				rowsPerPage = request.getParameter("pageSize");
			}
			HashMap map = new HashMap();
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			map.put("memberId",
					ParameterFilter.emptyFilter("", "memberId", request));
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = this.mallProductService.searchCourseProject(map);

			List<CourseProject> infoList = resultPage.getItems();

			JSONObject result = new JSONObject();
			List jsonList = new ArrayList();
			JSONObject jobj = new JSONObject();
			if (infoList != null) {
				for (CourseProject courseProject : infoList) {
					jobj = new JSONObject();
					jobj.put("id", courseProject.getId());
					jobj.put("projectName", courseProject.getProjectName());
					jobj.put("createDate", courseProject.getCreateDate()
							.toString().substring(0, 10));
					jobj.put("sort", courseProject.getSort());
					jobj.put("recordCount",
							courseProject.getRecordcount() == null ? 0
									: courseProject.getRecordcount());
					jobj.put("planType", courseProject.getPlanType());
					jobj.put("systemPlan", courseProject.getSystemPlan());
					jsonList.add(jobj);
				}
			}

			result.put("infoList", jsonList);
			result.put("pageCount", resultPage.getTotalPageCount());
			result.put("totalCount", resultPage.getTotalCount());
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 5.查询更新系统内置的课程计划
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateCourseProjectSystem")
	public void updateCourseProjectSystem(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String page = "1";
			String rowsPerPage = "20";
			if (!"".equals(request.getParameter("page"))
					&& request.getParameter("page") != null) {
				page = request.getParameter("page");
			}
			if (!"".equals(request.getParameter("pageSize"))
					&& request.getParameter("pageSize") != null) {
				rowsPerPage = request.getParameter("pageSize");
			}
			HashMap map = new HashMap();
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = this.mallProductService
					.searchCourseProjectSystem(map);
			List<CourseProjectSystem> infoList = resultPage.getItems();
			JSONObject result = new JSONObject();
			result.put("infoList", infoList);
			result.put("pageCount", resultPage.getTotalPageCount());
			result.put("totalCount", resultPage.getTotalCount());
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 6.查询机器人课程计划详情
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchCourseProjectInfo")
	public void searchCourseProjectInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Object[]> infoList = this.mallProductService
					.searchCourseProjectInfo(request.getParameter("projectId"));
			String projectName = "";
			String projectId = "";
			List jsonList = new ArrayList();
			JSONObject jobj = new JSONObject();
			if (infoList != null) {
				if (infoList.size() > 0) {
					projectId = request.getParameter("projectId");
				}

				for (Object[] courseProject : infoList) {

					jobj = new JSONObject();

					projectName = courseProject[1].toString();
					jobj.put("courseId", courseProject[3]);
					jobj.put("courseName", courseProject[2]);
					jobj.put("logo", Keys.STAT_NAME
							+ "/wechat/wechatImages/mall/" + courseProject[5]);

					jsonList.add(jobj);
				}
			}

			JSONObject result = new JSONObject();
			result.put("projectId", projectId);
			result.put("projectName", projectName);
			result.put("infoList", jsonList.toString());
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 7.更新课程计划排序
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateCourseProjectSort")
	public void updateCourseProjectSort(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String projectInfo = request.getParameter("projectInfo");
			if (!"".equals(projectInfo) && projectInfo != null) {
				String[] tempInfo = projectInfo.split(",");
				String[] tempStr = null;
				String projectId = "";
				String sort = "";
				for (int i = 0; i < tempInfo.length; i++) {

					tempStr = tempInfo[i].split("@");
					if (!"".equals(tempStr) && tempStr != null) {
						projectId = tempStr[0];
						sort = tempStr[1];
						this.mallProductService.updateCeourseProjectSort(
								projectId, sort);
					}

				}
				JSONObject result = new JSONObject();
				result.put("success", 1);
				JsonResult.JsonResultInfo(response, "");

			} else {
				JsonResult.JsonResultError(response, 1000);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 8.修改主课程
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateMainCourseByProductId")
	public void updateMainCourseByProductId(HttpServletRequest request,
			HttpServletResponse response) {
		Integer productId = Integer.parseInt(ParameterFilter.emptyFilter("-1",
				"productId", request));
		String name = ParameterFilter.emptyFilter("", "name", request);
		String courseBooksJson = ParameterFilter.emptyFilter("",
				"courseBooksJson", request);
		String logo="";
		if (productId != -1) {

			if (!"".equals(courseBooksJson)) {
				// 删除之前添加的书籍
				courseService.delCourseBookLibs(productId);
				// 添加新书籍
				JSONArray courseBooks = JSONArray.fromObject(courseBooksJson);
				for (int i = 0; i < courseBooks.size(); i++) {
					CourseBookLib bookLib = (CourseBookLib) JSONObject.toBean(
							courseBooks.getJSONObject(i), CourseBookLib.class);
					bookLib.setProductId(productId);
					this.courseService.saveCourseBookLib(bookLib);
				}
			}
			MallProduct mallProduct = this.courseService
					.findMainCourseByProductId(productId);
			mallProduct.setCreateDate(new Date());
			mallProduct.setName(name);
			this.courseService.updateMallProduct(mallProduct);
			logo=mallProduct.getLogo1();
		}

		// 返回结果
		HashMap couMap = new HashMap();
		couMap.put("id", productId);
		couMap.put("name", name);
		couMap.put("createDate", new Date());
		couMap.put("logo", "http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/"+logo);
		couMap.put("courseBooksJson", courseBooksJson);
		JsonResult.JsonResultInfoDate(response, couMap);

	}

	/**
	 * 获取课程下面所有书籍
	 */
	@RequestMapping("/findCourseBookLib")
	public void findCourseBookLib(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String productId = ParameterFilter.emptyFilter("-1", "productId",
				request);
		ArrayList<CourseBookLib> bookLibs = this.courseService
				.findCourseBookLib(productId);
		JsonResult.JsonResultInfo(response, bookLibs);
	}

	/**
	 * 添加书籍到MangoDB
	 * 
	 * 
	 */
	@RequestMapping("/saveBookToMyLib")
	public void saveBookToMyLib(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String epalId = ParameterFilter.emptyFilter("", "epalId", request);
		String productId = ParameterFilter.emptyFilter("-1", "productId",
				request);
		ArrayList<CourseBookLib> bookLibs = this.courseService
				.findCourseBookLib(productId);

		for (int i = 0; i < bookLibs.size(); i++) {
			DeviceLiber deviceLiber = new DeviceLiber();
			deviceLiber.setUserId(epalId);
			deviceLiber.setFileName(bookLibs.get(i).getBookName());
			deviceLiber.setImageUrl(bookLibs.get(i).getBookCover());
			deviceLiber.setType("bookStack");
			deviceLiber.setMusicUrl("");
			deviceLiber.setCreateDate( new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.format(new Date()));
			deviceService.saveBookToMyLib(deviceLiber);
		}

		JsonResult.JsonResultInfo(response, "成功添加到书库");

	}

	/**
	 * 9.修改/添加子课程
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateChildCourseByCourseId")
	public void updateChildCourseByCourseId(HttpServletRequest request,
			HttpServletResponse response) {
		String mainCourseId = ParameterFilter.emptyFilter("", "mainCourseId",
				request);
		String courseId = ParameterFilter.emptyFilter("", "courseId", request);
		String courseName = ParameterFilter.emptyFilter("", "courseName",
				request);
		String totalClass = ParameterFilter.emptyFilter("", "totalClass",
				request);
		Course course = new Course();
		if (!"".equals(courseId)) {
			course = this.courseService.findChildCourseByCourseId(courseId);
			course.setTotalClass(Integer.parseInt(totalClass));
			course.setName(courseName);
			this.courseService.updateCourse(course);
		} else {
			course.setTotalClass(Integer.parseInt(totalClass));
			course.setName(courseName);
			course.setProductId(Integer.parseInt(mainCourseId));
			this.courseService.addCourse(course);
		}
		JsonResult.JsonResultInfo(response, course);

	}

	/**
	 * 10.修改子课程课时
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateCoursePeriodByPeriodId")
	public void updateCoursePeriodByPeriodId(HttpServletRequest request,
			HttpServletResponse response) {
		String periodId = ParameterFilter
				.emptyFilter("-1", "periodId", request);
		String childCourseId = ParameterFilter.emptyFilter("-1",
				"childCourseId", request);
		String courseperiodName = ParameterFilter.emptyFilter("",
				"courseperiodName", request);
		String missionCmdList = ParameterFilter.emptyFilter("",
				"missionCmdList", request);
		CoursePeriod course = new CoursePeriod();
		if (!"-1".equals(periodId)) {
			course = this.courseService.findCoursePeriodByPeriodId(periodId);
			course.setCourseperiodName(courseperiodName);
			course.setMissionCmdList(missionCmdList);
			this.courseService.updateCoursePeriodByPeriodId(course);
		} else {
			course.setCourseperiodName(courseperiodName);
			course.setCourseId(Integer.parseInt(childCourseId));
			course.setMissionCmdList(missionCmdList);
			this.courseService.addCoursePeriod(course);
		}
		JsonResult.JsonResultInfo(response, course);
	}

	/**
	 * 11.查看主课程下所有子课程(新)
	 * 
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("getAllCoursesByProductId")
	public void getAllCoursesByProductId(HttpServletRequest request,
			HttpServletResponse response) {
		String productId = ParameterFilter
				.emptyFilter("", "productId", request);
		Page courses = this.courseService.getAllCoursesByProductId(productId);
		JsonResult.JsonResultInfo(response, courses.getItems());
	}

	/**
	 * 12.查看机器人下所有最新课程记录信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getAllCourseSchedulesByEpalId")
	public void getAllCourseSchedulesByEpalId(HttpServletRequest request,
			HttpServletResponse response) {
		String epalId = ParameterFilter.emptyFilter("", "epalId", request);
		Page courseSchedules = this.courseService
				.getAllCourseSchedulesByEpalId(epalId);
		JsonResult.JsonResultInfo(response, courseSchedules.getItems());
	}

	/**
	 * 13.机器人添加课程记录信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveCourseSchedule")
	public void saveCourseSchedule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Integer productid = Integer.parseInt(request
					.getParameter("productid"));
			Integer courseid = Integer.parseInt(request
					.getParameter("courseid"));
			String deviceNo = request.getParameter("deviceNo");
			String epalId = request.getParameter("epalId");
			String cusFile = request.getParameter("cusFile");
			String createTime = request.getParameter("createTime");
			Integer curClass = Integer.parseInt(request
					.getParameter("curClass"));

			String schedule = request.getParameter("schedule");
			Integer periodId = Integer.parseInt(ParameterFilter.emptyFilter(
					"-1", "periodId", request));
			Integer planId = Integer.parseInt(ParameterFilter.emptyFilter("-1",
					"planId", request));
			// 对相应的计划更新学习计数
			this.courseService.updateCourseProject(planId);

			CourseSchedule courseSchedule = new CourseSchedule();
			courseSchedule.setProductid(productid);
			courseSchedule.setCourseid(courseid);
			courseSchedule.setPeriodId(periodId);
			courseSchedule.setDeviceNo(deviceNo);
			courseSchedule.setEpalId(epalId);
			courseSchedule.setSchedule(schedule);
			courseSchedule.setCusFile(cusFile);
			courseSchedule.setCurClass(curClass);
			courseSchedule.setCreateTime(Timestamp.valueOf(createTime));
			this.getDeviceService().saveCourseSchedule(courseSchedule);
			//更新记录 清除缓存
			String key = "searchCourseSchedulesByEpalId_" + ParameterFilter.emptyFilter("", "epalId", request);
			redisService.del(key);
			JsonResult.JsonResultInfo(response, courseSchedule);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 14.查询机器人所有当前课程计划执行游标
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCurrentCourseSchedule")
	public void getCurrentCourseSchedule(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			HashMap map = new HashMap();
			map.put("epalId", epalId);
			List<CourseScheduleNow> courseScheduleNows = this
					.getDeviceService().getCurrentCourseSchedule(map);
			JsonResult.JsonResultInfo(response, courseScheduleNows);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * 15.添加/修改当前课程计划执行游标
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("modifyCurrentCourseSchedule")
	public void modifyCurrentCourseSchedule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Integer projectId = Integer.parseInt(request
					.getParameter("projectId"));
			Integer productid = Integer.parseInt(request
					.getParameter("productid"));
			Integer courseid = Integer.parseInt(request
					.getParameter("courseid"));
			String epalId = request.getParameter("epalId");
			String cusFile = ParameterFilter
					.emptyFilter("", "cusFile", request);
			// String createTime = request.getParameter("createTime");
			Integer curClass = Integer.parseInt(request
					.getParameter("curClass"));
			String schedule = ParameterFilter.emptyFilter("", "schedule",
					request);
			CourseScheduleNow courseSchedule = new CourseScheduleNow();
			courseSchedule.setProductid(productid);
			courseSchedule.setCourseid(courseid);
			courseSchedule.setProjectId(projectId);
			courseSchedule.setEpalId(epalId);
			courseSchedule.setSchedule(schedule);
			courseSchedule.setCusFile(cusFile);
			courseSchedule.setCurClass(curClass);
			courseSchedule.setCreateTime(new Timestamp(System
					.currentTimeMillis()));
			Integer id = this.deviceService.findCourseScheduleNow(epalId,
					projectId);
			if (id != null) {
				courseSchedule.setId(id);
			}
			this.getDeviceService().saveCourseScheduleNow(courseSchedule);
			JsonResult.JsonResultInfo(response, courseSchedule);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 16.查看主课程下所有子课程(旧)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchCoursesByProductId")
	public void searchCoursesByProductId(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("productId", request.getParameter("productId"));
			Page resultPage = getDeviceService().searchCoursesByProductId(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * 17.我的课程学习进度接口(新)
	 * 
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchCourseSchedulesByEpalId")
	public void searchCourseSchedulesByEpalId(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		map.put("page", null != page && !"".equals(page) ? page : "1");
		map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
				: "1000");
		String noCach = ParameterFilter.emptyFilter("false", "noCach", request);
		
		// 获取机器人所有学习的课程的相关信息
		
		String key = "searchCourseSchedulesByEpalId_" + ParameterFilter.emptyFilter("", "epalId", request);
		
		String temp = this.redisService.get(key);
		List courseScheduleInfos;
		if("true".equals(noCach)){
			temp = null;
		}
		if("".equals(temp)||null==temp){
			JSONObject data = new JSONObject();
			Page courseSchedules = getDeviceService()
					.searchCourseSchedulesByEpalId2(map);
			courseScheduleInfos = courseSchedules.getItems();
			data.put("courseScheduleInfos", courseScheduleInfos);
			this.redisService.set(key, data.toString(),86400);
			JsonResult.JsonResultInfo(response, courseScheduleInfos);
			
		}else{
			JSONObject result =  JSONObject.fromObject(temp);
			JsonResult.JsonResultInfo(response, result.get("courseScheduleInfos"));
		}

		
		

	}

	/**
	 * 18.我的课程学习进度接口(旧)
	 * 
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchCourseSchedulesByEpalIdOld")
	public void searchCourseSchedulesByEpalIdOld(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		map.put("epalId", request.getParameter("epalId"));
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		map.put("page", null != page && !"".equals(page) ? page : "1");
		map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
				: "1000");
		ArrayList<Object> courses = new ArrayList<Object>();

		// 给课程的对应子课程加上对应设备的进度属性
		Page courseSchedules = getDeviceService()
				.searchCourseSchedulesByEpalId(map);
		List courseScheduleInfos = courseSchedules.getItems();

		// 筛选出购买的商品中是课程的
		int count = 1;
		for (int i = 0; i < courseScheduleInfos.size(); i++) {
			CourseScheduleInfoDto courseScheduleInfoDto = (CourseScheduleInfoDto) courseScheduleInfos
					.get(i);
			map.put("productId", courseScheduleInfoDto.getProductId());
			Page resultPage = getDeviceService()
					.searchCourseScheduleByProductId(map);
			Map<String, Object> course = new HashMap<String, Object>();
			List dataList = resultPage.getItems();
			if (dataList.size() > 0) {
				ChildrenCourseDto childrenCourseDto = (ChildrenCourseDto) dataList
						.get(0);
				course.put("courseName", childrenCourseDto.getParentName());
				course.put("productId", childrenCourseDto.getProductId());
				course.put("courseLogo", childrenCourseDto.getCourseLogo());
				course.put("childCourseList", dataList);
				course.put("courseScheduleInfo", courseScheduleInfoDto);
				courses.add(course);
			}
		}
		JsonResult.JsonResultInfo(response, courses);
	}

	/**
	 * 退机条件判断
	 */
	@RequestMapping("backCondition")
	public void backCondition(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = ParameterFilter.emptyFilter("", "id", request);
		Integer recordCountSum = courseService.getRecordCountSum(id);
		JSONObject object = new JSONObject();
		if (recordCountSum > 192) {
			object.put("success", "符合退机条件，申请成功");
			object.put("recordCountSum", recordCountSum);
		} else {
			object.put("success", "暂不可退机，课时未学完");
			object.put("recordCountSum", recordCountSum);
		}
		JsonResult.JsonResultInfo(response, object);
	}
	
	/**
	 * 获取子课程最新学习过的课时
	 */
	@RequestMapping("getCurStudyedCourse")
	public void getCurStudyedCourse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String courseId = ParameterFilter.emptyFilter("", "courseId", request);
		String epalId = ParameterFilter.emptyFilter("", "epalId", request);
		CourseSchedule courseSchedule = courseService.getCurStudyedCourse(courseId,epalId);
		JsonResult.JsonResultInfo(response, courseSchedule);
	}
	/**
	 * 获取子课程最新学习过的课时
	 */
	@RequestMapping("getClassCourseRecordDetail")
	public void getClassCourseRecordDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
		JSONObject result = courseService.getClassCourseRecordDetail(classGradesId);
		JsonResult.JsonResultInfo(response, result);
	}	
	
	

}
