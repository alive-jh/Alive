package com.wechat.controller;


import com.wechat.common.dto.QueryDto;
import com.wechat.service.AgentService;
import com.wechat.service.LessonService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("agent")
public class AgentController {

	@Resource
	private AgentService agentService;
	
	@Resource
	private LessonService lessonService;
	/*
	 * 加盟商老师管理
	 * */

	@RequestMapping("teacherManager")
	public String teacherManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		Cookie[] enu = request.getCookies();
		String userId = "0";
		for(int i=0;i<enu.length;i++){
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("userId", userId);
		Page teachers = lessonService.getTeacherList(map); 
		
		List teacher = teachers.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(teacher));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", teachers);
		return "agent/teacherManager";
	}
	
	/*
	 * 加盟商班级管理
	 * */

	@RequestMapping("classGradesManager")
	public String classGradesManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		Cookie[] enu = request.getCookies();
		String userId = "0";
		for(int i=0;i<enu.length;i++){
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("teacherId", ParameterFilter.emptyFilter("", "teacherId", request));
		map.put("userId", userId);

		Page classGradesList = lessonService.searchClassGrades(map); 
		List classGrades = classGradesList.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(classGrades));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", classGradesList);
		return "agent/classGradesManager";
	}
	
	/*
	 * 加盟商学生管理
	 * */

	@RequestMapping("/studentManager")
	public String studentManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		Cookie[] enu = request.getCookies();
		String userId = "1";
		for(int i=0;i<enu.length;i++){
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("teacherId", ParameterFilter.emptyFilter("", "teacherId", request));
		map.put("userId", userId);

		Page classStudentList = lessonService.searchStudentList(map); 
		List classStudents = classStudentList.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(classStudents));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", classStudentList);
		return "agent/studentManager";
	}
	
	/*
	 * 加盟课程管理管理
	 * 
	 * */

	@RequestMapping("/classRoomManager")
	public String classRoomManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		Cookie[] enu = request.getCookies();
		String userId = "1";
		for(int i=0;i<enu.length;i++){
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("userId", userId);

		Page classRoomList = lessonService.searchClassRoom(map); 
		List classRooms = classRoomList.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(classRooms));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", classRoomList);
		return "agent/classRoomManager";
	}
	
	/*
	 * 加盟出货单管理
	 * 
	 * */

	@RequestMapping("/shipmentManager")
	public String shipmentManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		Cookie[] enu = request.getCookies();
		String userId = "1";
		for(int i=0;i<enu.length;i++){
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("userId", userId);

		Page agentList = agentService.getAgentList(map); 
		List agentLists = agentList.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(agentLists));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", agentList);
		return "agent/shipmentManager";
	}

}
