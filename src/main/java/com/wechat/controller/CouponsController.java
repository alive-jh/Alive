package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Coupons;
import com.wechat.entity.CouponsInfo;
import com.wechat.entity.MallOrder;
import com.wechat.service.CouponsService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("coupons")
public class CouponsController {

	@Resource
	private CouponsService couponsService;
	
	
	
	public CouponsService getCouponsService() {
		return couponsService;
	}



	public void setCouponsService(CouponsService couponsService) {
		this.couponsService = couponsService;
	}



	@RequestMapping(value="/couponsManager")
	public String couponsManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
	
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("title", queryDto.getTitle());
		map.put("dateType", queryDto.getDateType());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("type", queryDto.getType());

		
		
		Page resultPage = this.couponsService.searchCoupons(map);
		request.setAttribute("resultPage", resultPage);
		
		
		List<Coupons> infoList  = resultPage.getItems();
		
		List jsonList = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Coupons coupons : infoList) {
				
				JSONObject jobj = new JSONObject();
				jobj.put("id", coupons.getId());	
				jobj.put("title", coupons.getTitle());	
				jobj.put("content", coupons.getContent());	
				jobj.put("price", coupons.getPrice());	
				jobj.put("money", coupons.getMoney());	
				jobj.put("createDate", coupons.getCreateDate().toString().substring(0,10));	
				jobj.put("endDate", coupons.getEndDate().toString().substring(0,10));	
				jobj.put("url", coupons.getUrl());	
				if(coupons.getType() ==0)
				{
					jobj.put("typeName", "无使用门槛");
				}
				else
				{
					jobj.put("typeName", "满"+coupons.getPrice()+"元可用");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("jsonStr", jsonObj.toString());
		
		request.setAttribute("queryDto", queryDto);
		
		
		return "coupons/couponsManager";
	}
	
	@RequestMapping(value="/saveCoupons")
	public String saveCoupons(HttpServletRequest request,HttpServletResponse response,Coupons coupons)throws Exception{
	
		coupons.setCreateDate(new Date());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		coupons.setEndDate(sdf.parse(request.getParameter("tempDate")));
		
		
		this.couponsService.saveCoupons(coupons);
		coupons.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Keys.APP_ID+"&redirect_uri="+Keys.STAT_NAME+"/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=coupons@"+coupons.getId()+"#wechat_redirect");
		this.couponsService.saveCoupons(coupons);
		return "redirect:couponsManager";
	}
	
	
	
	@RequestMapping(value="/couponsView")
	public String couponsView(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		//System.out.println(request.getParameter("couponsId"));
		Coupons coupons = this.couponsService.getCoupons(request.getParameter("couponsId"));
		
		request.setAttribute("coupons", coupons);
		request.setAttribute("memberId", request.getParameter("memberId"));
		request.setAttribute("memberImg", request.getParameter("memberImg"));
		List list = this.couponsService.getCoupons(request.getParameter("memberId"),request.getParameter("couponsId"));
		if(list.size() == 0)
		{
			CouponsInfo couponsInfo = new CouponsInfo();
			couponsInfo.setMemberId(new Integer(request.getParameter("memberId")));
			couponsInfo.setCouponsId(new Integer(request.getParameter("couponsId")));
			couponsInfo.setStatus(new Integer(0));
			this.couponsService.saveCouponsInfo(couponsInfo);
		}
		
		return "coupons/couponsMobileView";
	}
	
	@RequestMapping(value="/saveCouponsInfo")
	public String saveCouponsInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		
		return "redirect:couponsMobileManager?memberId="+request.getParameter("memberId");
	}
	@RequestMapping(value="/couponsMobileManager")
	public String couponsMobileManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
	
		
		this.couponsService.updateCouponsInfoEndDateStatus();
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		
		map.put("memberId",request.getParameter("memberId"));
		String price = request.getParameter("price");
		
		if(price.indexOf(".") > 0){  
			price = price.replaceAll("0+?$", "");//去掉多余的0  
			price = price.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
		map.put("price", price);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Object[]> tempList = this.couponsService.searchCouponsList(map);
		

		
		request.setAttribute("memberId", request.getParameter("memberId"));
		request.setAttribute("tempList", tempList);
		request.setAttribute("type", request.getParameter("type"));
		MallOrder mallOrder = (MallOrder)request.getSession().getAttribute("mallOrder");
		request.getSession().setAttribute("mallOrder", request.getSession().getAttribute("mallOrder"));
		request.setAttribute("productId", request.getParameter("productId"));
		return "coupons/couponsMobileManager";
	}
	
	  public static String compare_date(String DATE1, String DATE2) throws Exception{
	        
	        
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	       
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	              ////System.out.println(">>>>>>>>>>");
	                return ">";
	            } else if (dt1.getTime() < dt2.getTime()) {
	            	 ////System.out.println("<<<<<<<<");
	                return "<";
	            } else {
	            	// //System.out.println("==========");
	            	 return "=";
	            }
	        
	    }
	
	
	@RequestMapping(value="/deleteCoupons")
	public String deleteCoupons(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
	
		this.couponsService.deleteCoupons(request.getParameter("couponsId"));
		return "redirect:couponsManager";
	}
	
}
