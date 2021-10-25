package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.ClassGrades;
import com.wechat.entity.ClassRoomCategory;
import com.wechat.service.LessonService;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequestMapping("/lesson")
public class LessonController {
	
	@Autowired
	LessonService lessonService;

	/**
	 * 班级管理页面跳转
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("/findClassGrades")
	public String findClassGrades(HttpServletRequest request, QueryDto queryDto) {
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
		return "lesson/classGradesManager2back";
	}
	
	/**
	 * 课堂管理页面跳转
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("/findClassRooms")
	public String findClassRooms(HttpServletRequest request, QueryDto queryDto) {
		
		HashMap map = new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("className", ParameterFilter.emptyFilter("", "name", request));
		map.put("categoryId", ParameterFilter.emptyFilter("", "categoryId", request));
		Page resultPage = lessonService.findClassRoomsByNameOrCate(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList classRoomDtos=(ArrayList) resultPage.getItems();
		JSONArray array=new JSONArray();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonTimeStampValueProcessor());
		array=JSONArray.fromObject(classRoomDtos, jsonConfig);
		request.setAttribute("jsonData", array.toString());
		return "lesson/classRoomManager";
	}
	
	
	/**
	 * 首页管理页面跳转
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("/findClassRoomsIndex")
	public String findClassRoomsIndex(HttpServletRequest request, QueryDto queryDto) {

		return "lesson/classRoomsIndexManager";
	}
	

	/**
	 * 添加年级班级数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveClassGrades")
	public String saveClassGrades(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String id = ParameterFilter.emptyFilter(null, "catId", request);
			String classGradesName = ParameterFilter.emptyFilter(null,
					"classGradesName", request);
			String parentId = ParameterFilter.emptyFilter(null, "parentId",
					request);
			String summary = ParameterFilter.emptyFilter(null, "summary",
					request);
			String cover = ParameterFilter.emptyFilter(null, "cover", request);
			String sort = ParameterFilter.emptyFilter(null, "sort", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassGrades classGrades = new ClassGrades(classGradesName,
					ParameterFilter.nullFilter(parentId), summary, cover,
					ParameterFilter.nullFilter(sort), createTime, null, sort, null,0,0,"");
			if (id != null) {
				classGrades = (ClassGrades) BeanModifyFilter.modifyFilter(
						classGrades,
						this.lessonService.getClassGrades(Integer.parseInt(id)));
			}
			this.lessonService.saveClassGrades(classGrades);
		} catch (Exception e) {
		//	JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
		return "redirect:findClassGrades";
	}
	
	/**
	 * 添加首页分类数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveClassRoomCategory")
	public String saveClassRoomCategory(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String id = ParameterFilter.emptyFilter(null, "catId", request);
			String categoryName = ParameterFilter.emptyFilter(null,
					"categoryName", request);
			String parentId = ParameterFilter.emptyFilter(null, "parentId",
					request);
			String summary = ParameterFilter.emptyFilter(null, "summary",
					request);
			String cover = ParameterFilter.emptyFilter(null, "cover", request);
			String sort = ParameterFilter.emptyFilter(null, "sort", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassRoomCategory classRoomCategory = new ClassRoomCategory(categoryName,
					ParameterFilter.nullFilter(parentId), summary, cover,
					ParameterFilter.nullFilter(sort), createTime);
			if (id != null) {
				classRoomCategory = (ClassRoomCategory) BeanModifyFilter.modifyFilter(
						classRoomCategory,
						this.lessonService.getClassRoomCategory(Integer.parseInt(id)));
			}
			this.lessonService.saveClassRoomCategory(classRoomCategory);
		//	JsonResult.JsonResultInfo(response, classGrades);
		} catch (Exception e) {
		//	JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
		return "redirect:findClassRoomsIndex";
	}

	/**
	 * 删除年级班级数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delClassGrades")
	public String delClassGrades(HttpServletRequest request,
			HttpServletResponse response) {
		String id= ParameterFilter.emptyFilter(null, "id", request);
//		if(null!=id){
//			this.lessonService.deleteTableRecord(id, "class_grades");
//		}
		return "redirect:findClassGrades";
	}
	

	
	/**
	 * 删除首页分类数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delClassRoomCategory")
	public String delClassRoomCategory(HttpServletRequest request,
			HttpServletResponse response) {
		String id= ParameterFilter.emptyFilter(null, "id", request);
		if(null!=id){
			this.lessonService.deleteTableRecord(id, "class_room_category");
		}
		return "redirect:findClassRoomsIndex";
	}
	
	/**
	 * 获取学生在线课堂评价
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getComment")
	public String getComment(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonData = new JSONObject();
		jsonData.put("teacherComment", "http://ocghbl1t3.bkt.clouddn.com/00107922cc30d4018187c44d6594ea97.mp3");
		jsonData.put("title", "五月总结");
		request.setAttribute("jsonData",jsonData);
		
		return "lesson/comment";
	}

	
	/**
	 * 获取学生在线课堂评价
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/packageManage")
	public String packageManage(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonData = new JSONObject();
		jsonData.put("teacherComment", "http://ocghbl1t3.bkt.clouddn.com/00107922cc30d4018187c44d6594ea97.mp3");
		jsonData.put("title", "五月总结");
		request.setAttribute("jsonData",jsonData);
		
		return "lesson/classRoomPackageManager";
	}
	
	
}
