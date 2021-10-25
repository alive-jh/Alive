package com.wechat.controller;

import com.wechat.entity.ClassCourseRankingCategory;
import com.wechat.service.DeviceService;
import com.wechat.service.RedisService;
import com.wechat.service.StatisticalService;
import com.wechat.util.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("api")
public class StatisticalApiController {

	@Resource
	private StatisticalService statisticalService;

	@Resource
	private RedisService redisServer;

	@Resource
	private DeviceService deviceService;

	/*
	 * 获取课堂下学生列表
	 */

	@RequestMapping("statistical/studentList")
	public void getStudentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String classId = ParameterFilter
					.emptyFilter("", "classId", request);
			HashMap<String, String> map = new HashMap();
			map.put("classId", classId);
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize",
					ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page result = statisticalService.getStudentList(map);
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 通过机器人设备序列号获取机器人换机后账号
	 */
	@RequestMapping("statistical/getOnlineUserList")
	public void getOnlineUserCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Jedis jedis = new Jedis("8.129.31.226", 6379, 20);
		jedis.auth("de5Vq9XdadHr8");
		jedis.select(0);
		List<String> list = new ArrayList<String>();
		Set<String> result = jedis.keys("DeviceHeartBeat_*");
		list.addAll(result);
		JSONObject data = new JSONObject();

		Integer deviceCount = deviceService.getDeviceCount();

		data.put("deviceCount", deviceCount);

		data.put("data", list);
		data.put("onlineCount", result.size());
		jedis.disconnect();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(data);
	}

	/*
	 * 按小时获取在线机器数量
	 */

	@RequestMapping("statistical/getOnlineDeviceCountList")
	public void getOnlineDeviceCountList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String type = ParameterFilter.emptyFilter("", "type", request);
			JSONObject result = new JSONObject();
			String key = "";
			if ("hour".equals(type)) {
				for (int i = 0; i < 48; i++) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY,
							calendar.get(Calendar.HOUR_OF_DAY) - 1 * i);
					key = new SimpleDateFormat("yyyy-MM-dd-HH").format(calendar
							.getTime());
					String vlaue = redisServer.get(key);
					if (null != vlaue && !"".equals(vlaue)) {

					} else {
						vlaue = "[]";
					}
					key = key.split("-")[0] + "-" + key.split("-")[1] + "-"
							+ key.split("-")[2] + "(" + key.split("-")[3]
							+ "点)";
					result.put(key, vlaue);
				}

			} else if ("day".equals(type)) {
				for (int i = 0; i < 50; i++) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY,
							calendar.get(Calendar.HOUR_OF_DAY) - 24 * i);
					key = new SimpleDateFormat("yyyy-MM-dd").format(calendar
							.getTime());
					String vlaue = redisServer.get(key);
					if (null != vlaue && !"".equals(vlaue)) {

					} else {
						vlaue = "[]";
					}
					key = key.split("-")[0] + "年" + key.split("-")[1] + "月"
							+ key.split("-")[2] + "日";
					result.put(key, vlaue);
				}

			} else if ("week".equals(type)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String today = format.format(new Date());
				SimpleDateFormat frt = new SimpleDateFormat("yyyy");
				String year = frt.format(new Date());
				Calendar calendar = Calendar.getInstance();
				Date date = format.parse(today);
				calendar.setFirstDayOfWeek(Calendar.MONDAY);
				calendar.setTime(date);
				Integer currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
				for (int i = 0; i < 12; i++) {
					if (currentWeek > i) {
						key = year + Integer.toString(currentWeek - i);
					} else {
						// 暂不处理，跨年的数据
					}

					String vlaue = redisServer.get(key);
					if (null != vlaue && !"".equals(vlaue)) {

					} else {
						vlaue = "[]";
					}
					key = year + "年" + "(第" + Integer.toString(currentWeek - i)
							+ "周)";
					result.put(key, vlaue);
				}

			} else if ("month".equals(type)) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
				String strTemp = df.format(new Date());
				String currentYear = strTemp.split("-")[0];
				String currentMonth = strTemp.split("-")[1];
				for (int i = 0; i < 12; i++) {
					String year = "";
					String month = "";
					int lastMonth = Integer.parseInt(currentMonth) - 1 * i;
					if (lastMonth <= 0) {
						lastMonth = lastMonth + 12;

					}
					month = Integer.toString(lastMonth);
					if (month.length() == 1) {
						month = "0" + month;
					}
					if (Integer.parseInt(currentMonth) < lastMonth) {
						year = Integer
								.toString(Integer.parseInt(currentYear) - 1);
					} else {
						year = currentYear;
					}

					key = year + "-" + month;
					String vlaue = redisServer.get(key);
					if (null != vlaue && !"".equals(vlaue)) {

					} else {
						vlaue = "[]";
					}
					key = year + "年" + month + "月";
					result.put(key, vlaue);
				}
			} else if ("active".equals(type)) {
				HashMap map = new HashMap();
				result = deviceService.getDeviceActive(map);
			} else {

			}
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 保存榜单
	 */

	@RequestMapping("statistical/getReportDataByDay")
	public void getReportDataByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			HashMap map = new HashMap();
			map.put("page", 1);
			map.put("pageSize", 10000);
			Page result = statisticalService.getReportDataByDay(map);
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 保存榜单
	 */

	@RequestMapping("statistical/saveRankingList")
	public void saveRankingList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String name = ParameterFilter.emptyFilter("", "name", request);
			String startTime = ParameterFilter.emptyFilter("", "startTime",
					request);
			String endTime = ParameterFilter
					.emptyFilter("", "endTime", request);
			String classGradesIds = ParameterFilter.emptyFilter("all",
					"classGradesIds", request);
			ClassCourseRankingCategory classCourseRankingCategory = new ClassCourseRankingCategory();
			classCourseRankingCategory.setName(name);
			classCourseRankingCategory.setStartDate(startTime + " 00:00:00");
			classCourseRankingCategory.setEndTime(endTime + " 23:59:59");
			classCourseRankingCategory.setClassGradesIds(classGradesIds);
			statisticalService.saveRankingList(classCourseRankingCategory);
			JsonResult.JsonResultInfo(response, classCourseRankingCategory);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 通过学生ID获取榜单列表
	 */

	@RequestMapping("statistical/getRankingListByStudent")
	public void getRankingListByStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String studentId = ParameterFilter.emptyFilter("", "studentId",
					request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize",
					request);
			HashMap map = new HashMap();
			map.put("studentId", studentId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page result = statisticalService.getRankingListByStudent(map);
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * 
	 * 上传统计记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadCountRecord")
	public void uploadCountRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String countRecord = request.getParameter("countRecord");
			//待处理
//			JSONObject parameter = new JSONObject();
//			parameter.put("countRecord", countRecord);
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			parameter.put("readTime", df.format(new Date()));
//			MongoHandle mongo = new MongoHandle("fandou", "countRecord");
//			mongo.saveDocument(parameter);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * 
	 * 获取榜单排行榜
	 * 
	 * @param request
	 */
	@RequestMapping("getRanking")
	public String getRanking(HttpServletRequest request) {
		Integer categoryId = Integer.parseInt(request
				.getParameter("categoryId"));
		Integer studentId = Integer.parseInt(request.getParameter("studentId"));
		Integer currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} else {
			currentPage = 1;
		}
		try {
			Page resultPage = this.statisticalService
					.getClassCourseRankingByCId(categoryId, currentPage);
			List<Object[]> list = resultPage.getItems();
			Object[] obj = (Object[]) this.statisticalService
					.getClassCourseRankingBySId(studentId, categoryId);
			int ranking = this.statisticalService.getStudentRanking(studentId,
					categoryId);
			request.setAttribute("dataList", list);
			request.setAttribute("student", obj);
			request.setAttribute("ranking", ranking);
			request.setAttribute("categoryId", categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("message", "当前榜单为空");
		return "statistical/ranking";
	}

	/**
	 * 
	 * 设置已读
	 * 
	 * 
	 * */	
	@RequestMapping("setRead")
	@ResponseBody
	public StdResponse read(Integer sid, Integer categoryId) {
		// 新建返回对象
		StdResponse rsp = new StdResponse();

		// 校验输入参数
		if (sid == null || sid < 1)
			return rsp.setMsg(StatusCode.PARM_FAILED);
		if(categoryId==null || categoryId<1)
			return rsp.setMsg(StatusCode.PARM_FAILED);

		// 进行业务处理
		try {
			this.statisticalService.setRead(sid, categoryId);
			rsp.setMsg(StatusCode.OK);
		} catch (Exception e) {
			rsp.setMsg(StatusCode.SERVER_ERROE);
			e.printStackTrace();
		}
		return rsp;
		
	}

	/**
	 * 根据学生id获得该学生最近一期排名榜的名次
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("statistical/getRankByStudentId")
	@ResponseBody
	public Map<String, Object> getRankByStudentId(int studentId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			List<Object> list = new ArrayList<>();
			Map<String, Object> data = statisticalService
					.getRankByStudentId(studentId);
			if (data != null)
				list.add(data);
			result.put("data", list);
			result.put("code", 500);
//			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;
	}
	
	
	
	

}
