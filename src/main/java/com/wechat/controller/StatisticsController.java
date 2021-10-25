package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.ExcelBook;
import com.wechat.service.ChannelService;
import com.wechat.service.CourseService;
import com.wechat.service.DeviceService;
import com.wechat.service.StatisticalService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("statistical")
public class StatisticsController {

	@Resource
	private ChannelService channelService;
	
	@Resource
	private StatisticalService statisticalService;
	
	@Resource
	private CourseService courseService;	
	
	
	@Resource
	private DeviceService deviceService;	
	

	
	/*
	 * 机器人在线时间
	 * */
	


	@RequestMapping("deviceOnlineTimeManager")
	public String channelManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page channels = channelService.searchChannels(map); 
		List channel = channels.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", channels);
		return "statistical/deviceOnlineTimeManager";
	}
	

	/*
	 * 在线课堂
	 * */
	


	@RequestMapping("onlineManager")
	public String online(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page channels = statisticalService.searchClassRooms(map); 
		List channel = channels.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", channels);
		return "statistical/onlineManager";
	}
	
	/*
	 * 60天课程统计
	 * */
	


	@RequestMapping("sixtyDayCourseManager")
	public String sixtyDayCourse(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("startDate", ParameterFilter.emptyFilter("", "startDate", request));
		map.put("endDate", ParameterFilter.emptyFilter("", "endDate", request));
		map.put("systemPlan", 66);
		Page datas = courseService.searchCourseProject(map); 
		List courseProjects = datas.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(courseProjects));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", datas);
		return "statistical/sixtyDayCourseManager";
	}	
	
	
	/*
	 * 机器人在线情况统计
	 * */
	


	@RequestMapping("onlineDeviceManager")
	public String onlineDeviceManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("startDate", ParameterFilter.emptyFilter("", "startDate", request));
		map.put("endDate", ParameterFilter.emptyFilter("", "endDate", request));
		Page datas = deviceService.getOnlineDeviceRecord(map); 
		List courseProjects = datas.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(courseProjects));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", datas);
		return "statistical/onlineDeviceManager";
	}
	
	@RequestMapping("publicOnlineDeviceManager")
	public String publicOnlineDeviceManager(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("startDate", ParameterFilter.emptyFilter("", "startDate", request));
		map.put("endDate", ParameterFilter.emptyFilter("", "endDate", request));
		Page datas = deviceService.getOnlineDeviceRecord(map); 
		List courseProjects = datas.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(courseProjects));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", datas);
		return "statistical/publicOnlineDeviceManager";
	}
	
	/*
	 * 排行榜列表
	 * */
	

	@RequestMapping("rankingList")
	public String rankingList(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("startDate", ParameterFilter.emptyFilter("", "startDate", request));
		map.put("endDate", ParameterFilter.emptyFilter("", "endDate", request));
		Page datas = statisticalService.getRankingList(map); 
		List courseProjects = datas.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(courseProjects));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", datas);
		return "statistical/rankingListManager";
	}
	
	/*
	 * 下载榜单页面
	 * 
	 * */
	
	@RequestMapping("downloadRanking")
	public String downloadRanking(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		
		String cateGoryId = ParameterFilter.emptyFilter("", "cateGoryId", request);

		Page resultPage = this.statisticalService.downloadRanking(cateGoryId);
		List<Object[]> infoList = resultPage.getItems();
		response.reset();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition", "attachment; filename="+ new String("榜单.xls".getBytes("GBK"), "iso8859-1"));
		OutputStream outstream = response.getOutputStream();
		jxl.write.WritableWorkbook wwb;
		wwb = Workbook.createWorkbook(outstream);
		jxl.write.WritableSheet ws = wwb.createSheet("榜单", 1);
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat cFormat = new WritableCellFormat(font);
		// 第一行有30列
		ws.setColumnView(0, 30);
		// 第五行第一列
		ws.addCell(new jxl.write.Label(0, 0, "序号", cFormat));
		ws.addCell(new jxl.write.Label(1, 0, "学生ID", cFormat));
		ws.addCell(new jxl.write.Label(2, 0, "学生名字", cFormat));
		ws.addCell(new jxl.write.Label(3, 0, "机器人账号", cFormat));
		ws.addCell(new jxl.write.Label(4, 0, "总耗时", cFormat));
		ws.addCell(new jxl.write.Label(5, 0, "总得分", cFormat));
		ws.addCell(new jxl.write.Label(6, 0, "学习次数", cFormat));
		ws.addCell(new jxl.write.Label(7, 0, "评价得分", cFormat));
		ws.addCell(new jxl.write.Label(8, 0, "开始时间", cFormat));
		ws.addCell(new jxl.write.Label(9, 0, "结束时间", cFormat));
		ws.addCell(new jxl.write.Label(10, 0, "班级列表", cFormat));
		ws.addCell(new jxl.write.Label(11, 0, "分类ID", cFormat));
		ws.addCell(new jxl.write.Label(12, 0, "联系电话", cFormat));
		WritableCellFormat cFormat1 = new WritableCellFormat(font);
		font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD,
				false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
		cFormat1 = new WritableCellFormat(font);
		ExcelBook excelBook = new ExcelBook();	
		if(infoList.size() >0)
		{	
			int i = 1;
			for(Object[] obj : infoList){
				ws.addCell(new jxl.write.Label(0, i , obj[0].toString(), cFormat));
				ws.addCell(new jxl.write.Label(1, i ,obj[1].toString(), cFormat));
				ws.addCell(new jxl.write.Label(2, i ,obj[2].toString(), cFormat));
				ws.addCell(new jxl.write.Label(3, i ,obj[3].toString(), cFormat));
				ws.addCell(new jxl.write.Label(4, i ,obj[4].toString(), cFormat));
				ws.addCell(new jxl.write.Label(5, i ,obj[5].toString(), cFormat));
				ws.addCell(new jxl.write.Label(6, i ,obj[6].toString(), cFormat));
				ws.addCell(new jxl.write.Label(7, i ,obj[7].toString(), cFormat));
				ws.addCell(new jxl.write.Label(8, i ,obj[8].toString(), cFormat));
				ws.addCell(new jxl.write.Label(9, i ,obj[9].toString(), cFormat));
				ws.addCell(new jxl.write.Label(10, i ,obj[10].toString(), cFormat));
				ws.addCell(new jxl.write.Label(11, i ,obj[11].toString(), cFormat));
				ws.addCell(new jxl.write.Label(12, i ,obj[12].toString(), cFormat));
				i += 1;
			}

		}
		
		wwb.write();
		wwb.close();

		outstream.close();
		
		

		return null;
		
		
	}
	
	@RequestMapping("goodVideo")
	public String goodVideo(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page channels = channelService.searchChannels(map); 
		List channel = channels.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", channels);
		return "statistical/deviceOnlineTimeManager";
	}
	
}
