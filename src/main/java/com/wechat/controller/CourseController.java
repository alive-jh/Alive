package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.MallProduct;
import com.wechat.service.CourseService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	
	@RequestMapping("courseManager")
	public String courseManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page courseInfos=courseService.searchCourseInfos(map);
		List courses=courseInfos.getItems();
		ArrayList couArr=new ArrayList();
		for (int i = 0; i < courses.size(); i++) {
			MallProduct mallProduct=(MallProduct) courses.get(i);
			HashMap couMap=new HashMap();
			couMap.put("id", mallProduct.getId());
			couMap.put("name", mallProduct.getName());
			couMap.put("logo", "http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/"+mallProduct.getLogo1());
			couMap.put("createDate", mallProduct.getCreateDate());
			couArr.add(couMap);
		}
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(couArr));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", courseInfos);
		return "course/courseManager";
	}
	
	
	@RequestMapping("courseSchedule")
	public String courseSchedule(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page courseSchedules=courseService.searchCourseSchedules(map);
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(courseSchedules.getItems()));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", courseSchedules);
		return "course/courseScheduleManager";
	}
	
	
	@RequestMapping("courseWordManager")
	public String courseWordManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page courseWords=courseService.searchCourseWords(map);
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(courseWords.getItems()));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", courseWords);
		return "course/courseWordManager";
	}
	


	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
}
