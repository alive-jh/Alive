package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.recordqueue.ElectrismDelegate;
import com.wechat.service.*;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import com.wechat.util.SMSTest;
import com.wechat.util.WeChatUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("electrism")
public class ElectrismController {

	@Resource
	private ElectrismService electrismService;

	public ElectrismService getElectrismService() {
		return electrismService;
	}

	public void setElectrismService(ElectrismService electrismService) {
		this.electrismService = electrismService;
	}
	
	@Resource
	private AccountService accountService;
	
	
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Resource
	private MemberService memberService;

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	@Resource
	private MallProductService mallProductService ;
	
	

	public MallProductService getMallProductService() {
		return mallProductService;
	}

	public void setMallProductService(MallProductService mallProductService) {
		this.mallProductService = mallProductService;
	}

	@Resource
	private ElectrismOrderService electrismOrderService;
	
	
	public ElectrismOrderService getElectrismOrderService() {
		return electrismOrderService;
	}

	public void setElectrismOrderService(ElectrismOrderService electrismOrderService) {
		this.electrismOrderService = electrismOrderService;
	}
	@Resource
	private CommentService commentService;
	
	
	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@RequestMapping("/electrismManager")
	public String electrismManager(HttpServletRequest request,QueryDto queryDto) {
		

		
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
		map.put("rowsPerPage", "20");
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());;
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		Page resultPage = this.electrismService.searchElectrism(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		List tempList = this.electrismService.searchDistrict();
		request.getSession().setAttribute("tempList", tempList);
		return "electrism/electrismManager";
		
	}
	
	
	
	@RequestMapping(value="/electrismManagerView")
	public void electrismManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());;
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("electrismId", request.getParameter("electrismId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		Page resultPage = this.electrismService.searchElectrism(map);
		List<Electrism> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Electrism electrism : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", electrism.getId());
				jobj.put("name", electrism.getName());
				jobj.put("headImg", electrism.getHeadImg());
				jobj.put("price", electrism.getPrice());
				jobj.put("createDate", sdf.format(electrism.getCreateDate()).toString());
				jobj.put("area", electrism.getArea());
				jobj.put("content", electrism.getContent());
				jobj.put("mobile", electrism.getMobile());
				jobj.put("hobbies", electrism.getHobbies());
				jobj.put("address", electrism.getAddress());
				jobj.put("memberId", electrism.getMemberId());
				jobj.put("status", electrism.getStatus());
				jobj.put("didstrict", electrism.getDidstrict());
				jobj.put("item", electrism.getItem());
				jobj.put("nickName", electrism.getNickName());
				jobj.put("card", electrism.getCard());
				jobj.put("bank", electrism.getBank());
				jobj.put("idNumber", electrism.getIdNumber());
				
				if(electrism.getStatus() ==0)
				{
					jobj.put("statusName", "正常");
				}
				if(electrism.getStatus() ==1)
				{
					jobj.put("statusName", "锁定");
				}
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		 
		 
	}

	@RequestMapping(value="/saveElectrism")
	public String saveElectrism(HttpServletRequest request,HttpServletResponse response,Electrism electrism,@RequestParam(value = "file1", required = false) MultipartFile file)throws Exception{
		
		if(!"".equals(electrism.getId())  &&  electrism.getId()!= null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			electrism.setCreateDate(sdf.parse(request.getParameter("tempDate")));
			//this.electrismService.deleteDistricInfo(electrism.getId().toString());
		}
		else
		{
			electrism.setCreateDate(new Date());
		}
		
		String fileName = "";
		if(file.getSize()!=0)
		{
			fileName = file.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH + "electrism/");
			if (!fileDir.exists())
			{
				fileDir.mkdir();
			}
			fileName = new Date().getTime() + "" + fileName.subSequence(fileName.indexOf("."), fileName.length());
			
			String pathName = Keys.USER_PIC_PATH +  "electrism/";
	        File targetFile = new File(pathName, fileName);
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }

	        //保存
	        try {
	            file.transferTo(targetFile);
	            File deleteFile = new File(Keys.USER_PIC_PATH+"electrism/"+request.getParameter("oldLogo"));

	            if(!"".equals(request.getParameter("headImg")) && request.getParameter("headImg")!= null)
	            {
	            	 deleteFile.delete();
	            }
	           
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        electrism.setHeadImg("/"+fileName);
	        
		}
		else
		{
			electrism.setHeadImg(request.getParameter("headImg"));
			File deleteFile = new File(Keys.USER_PIC_PATH + "electrism/" + request.getParameter("oldLogo"));
            
			
            if(request.getParameter("headImg") == null || "".equals(request.getParameter("headImg")))
            {
            	
            	electrism.setHeadImg("");
            	deleteFile.delete();
            	
            }
		}
		
		JSONObject jsonObject = WeChatUtil.getLngAndLat(electrism.getAddress());
		
		electrism.setLat(jsonObject.getJSONObject("result").getJSONObject("location").getString("lat"));
		electrism.setLng(jsonObject.getJSONObject("result").getJSONObject("location").getString("lng"));
		String memberId = this.electrismOrderService.getMemberIdByMobile(electrism.getMobile());
		if(!"".equals(memberId) && memberId != null)
		{
			electrism.setMemberId(new Integer(memberId));
		}
		
		this.electrismService.saveElectrism(electrism);
		
		return "redirect:electrismManager";
		 
		 
	}
	
	
	@RequestMapping(value = "/deleteElectrism")
	public String deleteElectrism(HttpServletRequest request,HttpServletResponse response) {

		
		this.electrismService.deleteElectrism(request.getParameter("electrismId"));
		
		return "redirect:electrismManager";
	}
	
	@RequestMapping(value = "/updateElectrismStatus")
	public String updateElectrismStatus(HttpServletRequest request,HttpServletResponse response) {

		
		this.electrismService.updateElectrismStatus(request.getParameter("electrismId"),request.getParameter("status"));
		
		return "redirect:electrismManager";
	}
	
	@RequestMapping(value="/getMember")
	public String getMember(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		
		
		List list = this.electrismService.getMemberByAccount(request.getParameter("mobile"));
		String jsonObj = "";
	
		response.setContentType("text/html;charset=UTF-8");
		if(list.size()!=0)
		{
			jsonObj="{\"data\":{\"ok\":\"手机号码可以使用!\"}}";
		}
		
		else
		{
			
			jsonObj="{\"data\":{\"error\":\"手机号码没有匹配到对应的微信会员,请重新输入!\"}}";
			
		}
		
		
		response.getWriter().println(jsonObj);
		return "result";
		
		 
		 
		 
	}
	
	
	@RequestMapping(value="/searchAddress")
	public String searchAddress(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		
		request.setAttribute("memberId", request.getParameter("memberId"));
		
		return "electrism/searchAddress";
		
		 
		 
		 
	}
	
	@RequestMapping(value="/setAddress")
	public String setAddress(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		
		
		
		JSONObject jsonObject = WeChatUtil.getLngAndLat(request.getParameter("tempAddress"));
		String lngandlat = jsonObject.getJSONObject("result").getJSONObject("location").getString("lng")+","+jsonObject.getJSONObject("result").getJSONObject("location").getString("lat");
		
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
		map.put("rowsPerPage", "20");
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());;
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		Page resultPage = this.electrismService.searchElectrism(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		List tempList = this.electrismService.searchDistrict();
		request.getSession().setAttribute("tempList", tempList);
		
		List<Electrism> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Electrism electrism : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", electrism.getId());
				jobj.put("name", electrism.getName());
				jobj.put("headImg", electrism.getHeadImg());
				jobj.put("price", electrism.getPrice());
				
				
				jobj.put("address", electrism.getAddress());
				jobj.put("memberId", electrism.getMemberId());
				jobj.put("didstrict", electrism.getDidstrict());
				jobj.put("item", electrism.getItem());
				jobj.put("lat", electrism.getLat());
				jobj.put("lng", electrism.getLng());
				
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("lngandlat", lngandlat);
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("address", request.getParameter("tempAddress"));
		request.setAttribute("memberId", request.getParameter("memberId"));
		return "electrism/electrismMobileManager"; 
		 
		 
	}
	
	
	

	@RequestMapping("/electrismMobileManager")
	public String electrismMobileManager(HttpServletRequest request,QueryDto queryDto) throws Exception {
		

		
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
		map.put("rowsPerPage", "20");
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());;
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		Page resultPage = this.electrismService.searchElectrism(map);
		
		HashMap countMap = this.electrismOrderService.getElectrismOrderCount();
		
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		List tempList = this.electrismService.searchDistrict();
		request.getSession().setAttribute("tempList", tempList);
		
		List<Electrism> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Electrism electrism : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", electrism.getId());
				jobj.put("name", electrism.getName());
				jobj.put("headImg", electrism.getHeadImg());
				jobj.put("price", electrism.getPrice());
				
				
				jobj.put("address", electrism.getAddress());
				jobj.put("memberId", electrism.getMemberId());
				jobj.put("didstrict", electrism.getDidstrict());
				jobj.put("item", electrism.getItem());
				jobj.put("lat", electrism.getLat());
				jobj.put("lng", electrism.getLng());
				if(countMap.get(electrism.getId().toString())!= null)
				{
					jobj.put("orderCount", countMap.get(electrism.getId().toString()).toString());
				}
				else
				{
					jobj.put("orderCount", "0");
				}
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
	
		UserAddress userAddress = new UserAddress();
		userAddress = this.mallProductService.getUserAddressBystatus(new Integer(request.getParameter("memberId")));

		JSONObject jsonObject = WeChatUtil.getLngAndLat(userAddress.getAddress());
			
		request.setAttribute("lngandlat", jsonObject.getJSONObject("result").getJSONObject("location").getString("lng")+","+jsonObject.getJSONObject("result").getJSONObject("location").getString("lat"));
		
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("address", userAddress.getAddress());
		request.getSession().setAttribute("memberId", request.getParameter("memberId"));
		return "electrism/electrismMobileManager";
		
	}
	
	
	@RequestMapping("/electrismMobileManagerView")
	public String electrismMobileManagerView(HttpServletRequest request,QueryDto queryDto) throws Exception {
		

		
		Electrism electrism = new Electrism();
		electrism = this.electrismService.getElectrism(request.getParameter("electrismId"));
		request.setAttribute("electrism", electrism);
		request.setAttribute("memberId", request.getParameter("memberId"));
		////System.out.println("memberId = "+request.getSession().getAttribute("memberId"));
		if(electrism.getItem()!= null)
		{
			String[] str = electrism.getItem().split(",");
			List list = new ArrayList();
			for (int i = 0; i < str.length; i++) {
				
				list.add(str[i]);
			}
			request.setAttribute("itemList", list);
			
		}
		return "electrism/electrismManagerView";
		
	}
	
	@RequestMapping("/electrismOrderManager")
	public String electrismOrderManager(HttpServletRequest request,QueryDto queryDto) throws Exception {
		

		HashMap map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "20");
		
		map.put("orderNumber", queryDto.getNumber());
		map.put("mobile", queryDto.getMobile());
		map.put("status", queryDto.getStatus()	);
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("electrismId", queryDto.getAccount());
		Page resultPage = this.electrismOrderService.searchElectrismOrderInfo(map);
		List electrismList = this.electrismOrderService.getElectrismNameMap();
		request.setAttribute("electrismList", electrismList);
		List<Object[]> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Object[] electrismOrder : infoList) {
				JSONObject jobj = new JSONObject();
				
				jobj.put("id", electrismOrder[0]);
				jobj.put("electrismName", electrismOrder[1]);
				jobj.put("memberName", electrismOrder[2]);
				
				jobj.put("payment", electrismOrder[9]);
				jobj.put("contacts", electrismOrder[3]);
				jobj.put("mobile", electrismOrder[4]);
				jobj.put("orderNumber", electrismOrder[9]);
				jobj.put("payment", electrismOrder[8]);
				jobj.put("status", electrismOrder[6]);

				
				if("0".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "待处理");
				}
				if("1".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "进行中");
				}
				if("2".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "已取消");
				}
				if("3".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "待付款");
				}
				if("4".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "进行中");
				}
				if("5".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "已完成");
				}
				if("6".equals(electrismOrder[6].toString()))
				{
					jobj.put("statusName", "已评论");
				}
				
				
				
				if("1".equals(electrismOrder[7].toString()))
				{
					jobj.put("serviceName", "上门服务");
				}
				if("2".equals(electrismOrder[7].toString()))
				{
					jobj.put("serviceName", "电器维护");
				}
				if("3".equals(electrismOrder[7].toString()))
				{
					jobj.put("serviceName", "线路维护");
				}
				if("4".equals(electrismOrder[7].toString()))
				{
					jobj.put("serviceName", "其他服务");
				}
				
				jobj.put("serviceItem", electrismOrder[8]);
				jobj.put("orderDate", electrismOrder[5]);

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		return "electrism/electrismOrderManager";
		
	}
	
	
	@RequestMapping("/addOrderPayment")
	public String addOrderPayment(HttpServletRequest request) throws Exception {
		

		
		this.electrismOrderService.addOrderPayment(request.getParameter("orderId"), request.getParameter("payment"),request.getParameter("remarks"));
		
		NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
		String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
		noticeInfo.setAccessToKen(accessToken);
		
		ElectrismOrder electrismOrder = this.electrismOrderService.getElectrismOrder(request.getParameter("orderId"));
		
		Member member = new Member();
		member.setId(electrismOrder.getMemberId());
		member = this.memberService.getMember(member);
		Electrism electrism  = new Electrism();
		electrism = this.electrismService.getElectrism(electrismOrder.getElectrismId().toString());
		
		
		noticeInfo.setOpenId(member.getOpenId());
		noticeInfo.setMemberId(member.getId().toString());
		
		
		
		noticeInfo.setFirst(electrism.getNickName()+"修改了订单金额!");
		
		
		
		noticeInfo.setKeyword1(electrismOrder.getOrderNumber());
		noticeInfo.setKeyword2(electrism.getAddress());
		noticeInfo.setKeyword3(electrismOrder.getAddress());
		noticeInfo.setKeyword4(electrism.getMobile());
		noticeInfo.setKeyword5(electrismOrder.getPayment()+"0元");
		
		
		noticeInfo.setRemark("备注:"+request.getParameter("remarks")+","+electrismOrder.getAddPayment()+"0元");
		

		noticeInfo.setUrl(Keys.STAT_NAME+"wechat/electrism/electrismOrder?memberId="+member.getId());
		WeChatUtil.sendOrderResult(noticeInfo);	//发送通知到会员微信
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");
	}
	
	@RequestMapping("/updateOrderPayment")
	public String updateOrderPayment(HttpServletRequest request) throws Exception {
		

		
		this.electrismOrderService.addOrderPayment(request.getParameter("orderId"), "0","");
		this.electrismOrderService.updateElectrismOrderStatis(request.getParameter("orderId"), "1");
		
		
		
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");
	}
	
	@RequestMapping("/updateOrderPaymentStatus")
	public String updateOrderPaymentStatus(HttpServletRequest request) throws Exception {
		

		this.electrismOrderService.updateElectrismOrderStatis(request.getParameter("orderId"), "4");
		
		NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
		String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
		noticeInfo.setAccessToKen(accessToken);
		ElectrismOrder electrismOrder = this.electrismOrderService.getElectrismOrder(request.getParameter("orderId"));
		
		Electrism electrism = this.electrismService.getElectrism(electrismOrder.getElectrismId().toString());
	
		Member member = new Member();
		member.setId(electrism.getMemberId());
		member = this.memberService.getMember(member);
	
		
		noticeInfo.setOpenId(member.getOpenId());
		noticeInfo.setMemberId(member.getId().toString());

		noticeInfo.setFirst("客户已完成附加费付款!");
		noticeInfo.setKeyword1(electrismOrder.getOrderNumber());
		noticeInfo.setKeyword2(electrism.getAddress());
		noticeInfo.setKeyword3(electrismOrder.getAddress());
		noticeInfo.setKeyword4(electrism.getMobile());
		noticeInfo.setKeyword5(electrismOrder.getPayment()+"0元");
		noticeInfo.setRemark("请及时服务!");
		
		
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");
	}
	
	
	
	@RequestMapping("/electrismOrder")
	public String electrismOrder(HttpServletRequest request) throws Exception {
		

		
		Member member = new Member();
		member.setId(new Integer(request.getParameter("memberId")));
		member  = this.memberService.getMember(member);
		request.setAttribute("member", member);
		HashMap map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "20");
		
		if(member.getType()==0)
		{
			map.put("memberId", request.getParameter("memberId"));
		}
		else
		{
			Electrism electrism  = this.electrismService.getElectrismByMemberId(member.getId().toString());
			map.put("electrismId", electrism.getId());
		}
		Page resultPage = this.electrismOrderService.searchElectrismOrder(map);
		request.setAttribute("resultPage", resultPage);
		
		
		return "electrism/electrismOrder";
		
	}
	
	
	@RequestMapping("/updateElectrismOrder")
	public String updateElectrismOrder(HttpServletRequest request) throws Exception {
		

		//修改订单状态
		this.electrismOrderService.updateElectrismOrderStatis(request.getParameter("orderId"), request.getParameter("status"));
		
		
		NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
		String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
		noticeInfo.setAccessToKen(accessToken);
		
		ElectrismOrder electrismOrder = this.electrismOrderService.getElectrismOrder(request.getParameter("orderId"));
		
		Member member = new Member();
		member.setId(electrismOrder.getMemberId());
		member = this.memberService.getMember(member);
		Electrism electrism  = new Electrism();
		electrism = this.electrismService.getElectrism(electrismOrder.getElectrismId().toString());
		
		
		noticeInfo.setOpenId(member.getOpenId());
		noticeInfo.setMemberId(member.getId().toString());
		
		
		if("1".equals(request.getParameter("status")))
		{
			noticeInfo.setFirst(electrism.getNickName()+"接了您的订单!");
		}
		if("2".equals(request.getParameter("status")))
		{
			noticeInfo.setFirst("尊敬的用户,对不起!"+electrism.getNickName()+"拒绝您的订单!");
		}
		
		noticeInfo.setKeyword1(electrismOrder.getOrderNumber());
		noticeInfo.setKeyword2(electrism.getAddress());
		noticeInfo.setKeyword3(electrismOrder.getAddress());
		noticeInfo.setKeyword4(electrism.getMobile());
		noticeInfo.setKeyword5(electrismOrder.getPayment()+"0元");
		
		
		
		if("1".equals(request.getParameter("status")))
		{
			noticeInfo.setRemark("师傅将会在约定时间内上门服务!");
		}
		if("2".equals(request.getParameter("status")))
		{
			noticeInfo.setRemark("您的退款请求已提交,退款将于3个工作日内原路退回!");
		}
		if("5".equals(request.getParameter("status")))
		{
			ElectrismOrderPayment electrismOrderPayment = new ElectrismOrderPayment();
			electrismOrderPayment.setCreateDate(new Date());
			electrismOrderPayment.setElectrismId(electrismOrder.getElectrismId());
			electrismOrderPayment.setPayment(electrismOrder.getPayment()+electrismOrder.getAddPayment());
			electrismOrderPayment.setOrderId(electrismOrder.getId());
			electrismOrderPayment.setOrderNumber(electrismOrder.getOrderNumber());
			
			ElectrismDelegate.addDataToTaskQueue(electrismOrderPayment);
		}
		noticeInfo.setUrl(Keys.STAT_NAME+"/wechat/electrism/electrismOrder?memberId="+member.getId());
		if(!"5".equals(request.getParameter("status")))
		{
			WeChatUtil.sendOrderResult(noticeInfo);	//发送通知到电工微信
		}
		
		
		
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");

		
	}
	
	
	@RequestMapping("/electrismAddOrder")
	public String electrismAddOrder(HttpServletRequest request) throws Exception {
		


		
		Electrism electrism = new Electrism();
		electrism = this.electrismService.getElectrism(request.getParameter("electrismId"));
		request.getSession().setAttribute("electrism", electrism);
		String memberId = request.getParameter("memberId");
		Member member = new Member();
		member.setId(new Integer(memberId));
		member = this.memberService.getMember(member);
		request.getSession().setAttribute("member", member);
		request.setAttribute("memberId", request.getParameter("memberId"));
		String timeStr = this.electrismOrderService.searchsElectrismOrderTime(electrism.getId().toString());
		
		request.getSession().setAttribute("timeStr", timeStr);
		
		request.getSession().setAttribute("itemType", request.getParameter("itemType"));
		UserAddress userAddress  = this.mallProductService.getUserAddressBystatus(new Integer(memberId));
		request.getSession().setAttribute("userAddress", userAddress);
		return "electrism/electrismAddOrder";
		
	}
	
	@RequestMapping("/electrismSaveOrder")
	public String electrismSaveOrder(HttpServletRequest request,ElectrismOrder electrismOrder) throws Exception {
		

		////System.out.println("memberId = " + request.getParameter("memberId"));
		
		electrismOrder.setCreateDate(new Date());
		electrismOrder.setMemberId(new Integer(request.getParameter("memberId")));
		electrismOrder.setOrderDate(request.getParameter("orderDate"));
		electrismOrder.setOrderNumber(request.getParameter("orderNumber"));
		electrismOrder.setOrderNumber(electrismOrder.getOrderNumber().replaceAll("\"", ""));
		this.electrismOrderService.saveElectrismOrder(electrismOrder);
		
		
		
		ElectrismOrderTime electrismOrderTime = new ElectrismOrderTime();
		
		electrismOrderTime.setCreateDate(request.getParameter("orderDate"));
		electrismOrderTime.setTime(request.getParameter("orderTime"));
		electrismOrderTime.setElectrismId(electrismOrder.getElectrismId());
		electrismOrderTime.setOrderId(electrismOrder.getId());
		this.electrismOrderService.saveElectrismOrderTime(electrismOrderTime);//提交预约时间
		
		String tempTime = electrismOrderTime.getTime();
		//设置前后预约时间
		electrismOrderTime.setTime(new Integer(tempTime.replaceAll(":00", "")) -1 + ":00");
		electrismOrderTime.setId(null);
		this.electrismOrderService.saveElectrismOrderTime(electrismOrderTime);//提交预约时间
		
		
		electrismOrderTime.setTime(new Integer(tempTime.replaceAll(":00", "")) +1 + ":00");
		electrismOrderTime.setId(null);
		this.electrismOrderService.saveElectrismOrderTime(electrismOrderTime);//提交预约时间
		
		
		
		
		Electrism electrism = this.electrismService.getElectrism(electrismOrder.getElectrismId().toString());
		
		NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
		String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
		noticeInfo.setAccessToKen(accessToken);
		Member member = new Member();
		member.setId(electrism.getMemberId());
		member = this.memberService.getMember(member);
		
		noticeInfo.setOpenId(member.getOpenId());
		noticeInfo.setMemberId(member.getId().toString());
		noticeInfo.setFirst("您有一个新的订单,请及时处理!");
		noticeInfo.setKeyword1("待处理");
		DateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
		
		if("1".equals(electrismOrder.getServiceItem()))
		{
			noticeInfo.setKeyword2("上门服务");
		}
		if("2".equals(electrismOrder.getServiceItem()))
		{
			noticeInfo.setKeyword2("电器维修");
		}
		if("3".equals(electrismOrder.getServiceItem()))
		{
			noticeInfo.setKeyword2("电路维修");
		}
		if("4".equals(electrismOrder.getServiceItem()))
		{
			noticeInfo.setKeyword2("咨询服务");
		}
		noticeInfo.setKeyword3(format.format(electrismOrder.getCreateDate()));
		if(new Integer(electrismOrderTime.getTime().replaceAll(":00", "")) <= 12  )
		{
			noticeInfo.setKeyword4("上午:"+electrismOrderTime.getTime());
		}
		else if(new Integer(electrismOrderTime.getTime().replaceAll(":00", "")) > 12 && new Integer(electrismOrderTime.getTime().replaceAll(":00", "")) <= 18  )
		{
			noticeInfo.setKeyword4("下午:"+electrismOrderTime.getTime());
		}
		else
		{
			noticeInfo.setKeyword4("晚上:"+electrismOrderTime.getTime());
		}
		
		noticeInfo.setKeyword5(electrismOrder.getAddress());
		
		noticeInfo.setRemark("若有疑问请致电客服中心:"+Keys.CUSTOMER_MOBLIE);
		noticeInfo.setUrl(Keys.STAT_NAME+"/wechat/electrism/electrismOrder?memberId="+member.getId());
		WeChatUtil.sendServiceOrder(noticeInfo);	//发送通知到电工微信
		
		request.getSession().removeAttribute("electrism");
		request.getSession().removeAttribute("timeMap");
		request.getSession().removeAttribute("itemType");
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");
	}
	
	@RequestMapping("/updateOrderStatus")
	public String updateOrderStatus(HttpServletRequest request) throws Exception {
		

		
		
		this.electrismOrderService.updateElectrismOrderStatis(request.getParameter("orderId"),"2");
		this.electrismOrderService.updateElectrismOrderTimeStatis(request.getParameter("orderId"));
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");
		
	}
	@RequestMapping("/toComment")
	public String toComment(HttpServletRequest request) throws Exception {
		
		request.setAttribute("orderId", request.getParameter("orderId"));
		request.setAttribute("memberId", request.getParameter("memberId"));
		Electrism electrism = this.electrismService.getElectrism(request.getParameter("electrismId"));
		request.setAttribute("electrism", electrism);
		return "electrism/electrismComment";
		
	}
	
	@RequestMapping("/saveComment")
	public String saveComment(HttpServletRequest request,Comment comment) throws Exception {
		
		
		comment.setCreateDate(new Date());
		comment.setType(1);
		//System.out.println("star = "+request.getParameter("star"));
		this.commentService.saveComment(comment);
		this.electrismOrderService.updateElectrismOrderStatis(request.getParameter("orderId"), "6");
		return "redirect:electrismOrder?memberId="+request.getParameter("memberId");
		
	}
	
	@RequestMapping("/toAddPayment")
	public String toAddPayment(HttpServletRequest request) throws Exception {
		
		request.setAttribute("orderId", request.getParameter("orderId"));
		request.setAttribute("memberId", request.getParameter("memberId"));
		return "electrism/addPayment";
		
	}
	
	
	@RequestMapping("/electrismPaymentOrder")
	public String electrismPaymentOrder(HttpServletRequest request) throws Exception {
		

	
		request.setAttribute("memberId", request.getParameter("memberId"));
		Electrism electrism = this.electrismService.getElectrismByMemberId(request.getParameter("memberId"));
		request.setAttribute("electrism", electrism);
		String sumPayment = this.electrismService.getElectrismOrderSumPayment(electrism.getId().toString());
		request.setAttribute("sumPayment", sumPayment);
		HashMap map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "20");
		map.put("electrismId", electrism.getId().toString());
		Page resultPage = this.electrismService.searchElectrismOrderPayment(map);
		request.setAttribute("resultPage", resultPage);
		
	
		return "electrism/electrismPaymentOrder";
		
	}
	
	@RequestMapping("/updatePaymentStatus")
	public String updatePaymentStatus(HttpServletRequest request) throws Exception {
		
		
		this.electrismService.updatePaymentStatus(request.getParameter("electrismId"), request.getParameter("status"));
		
		return "redirect:electrismPaymentOrder?memberId="+request.getParameter("memberId");
		
	}
	
	
	@RequestMapping("/orderPaymentManager")
	public String orderPaymentManager(HttpServletRequest request,QueryDto queryDto) throws Exception {
		

		HashMap map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "20");
		
		map.put("orderNumber", queryDto.getNumber());
		map.put("mobile", queryDto.getMobile());
		map.put("status", queryDto.getStatus()	);
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("electrismId", queryDto.getAccount());
		Page resultPage = this.electrismOrderService.searchOrderPaymentInfo(map);
		List electrismList = this.electrismOrderService.getElectrismNameMap();
		request.setAttribute("electrismList", electrismList);
		List<Object[]> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			for(Object[] electrismOrder : infoList) {
				JSONObject jobj = new JSONObject();
				
				jobj.put("id", electrismOrder[4]);
				jobj.put("electrismName", electrismOrder[0]);
				jobj.put("nickName", electrismOrder[1]);
				jobj.put("card", electrismOrder[2]);
				jobj.put("bank", electrismOrder[3]);
				jobj.put("orderCount", electrismOrder[5]);
				jobj.put("payment", electrismOrder[6]);
				jobj.put("info", electrismOrder[7]);
				jobj.put("mobile", electrismOrder[8]);
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		return "electrism/OrderPaymentManager";
		
	}
	
	
	@RequestMapping("/updateOrderPaymentManager")
	public String updateOrderPaymentManager(HttpServletRequest request) throws Exception {
		
		
		this.electrismService.updateElectrismOrderPayment(request.getParameter("id"));
		
		String str = "尊敬的"+request.getParameter("nickName")+"先生,你的社区好电工提现已处理,具体到账时间以银行为准!";

		//System.out.println();
		
		//SMSTest.sendMessage(str, request.getParameter("mobile"));
		return "redirect:orderPaymentManager";
		
	}
	
	
	@RequestMapping("/toElectrismOrder")
	public String toElectrismOrder(HttpServletRequest request) throws Exception {
		


		
		Electrism electrism = new Electrism();
		electrism = this.electrismService.getElectrism(request.getParameter("electrismId"));
		request.getSession().setAttribute("electrism", electrism);
		String memberId = request.getParameter("memberId");
		Member member = new Member();
		member.setId(new Integer(memberId));
		member = this.memberService.getMember(member);
		request.getSession().setAttribute("member", member);
		request.setAttribute("memberId", request.getParameter("memberId"));
		String timeStr = this.electrismOrderService.searchsElectrismOrderTime(electrism.getId().toString());
		
		request.getSession().setAttribute("timeStr", timeStr);
		request.getSession().setAttribute("orderDate", request.getParameter("orderDate"));
		request.getSession().setAttribute("itemType", request.getParameter("itemType"));
		UserAddress userAddress  = this.mallProductService.getUserAddressBystatus(new Integer(memberId));
		request.getSession().setAttribute("userAddress", userAddress);
		return "electrism/electrismOrderInfo";
		
	}
	
	public static void main(String[] args) throws Exception{
		
		String s = "15:00";
		//System.out.println(s.replaceAll(":00", ""));
		
		SMSTest.sendMessage("尊敬的林子京先生,你的社区好电工提现已处理,具体到账时间以银行为准!", "15919429553");
	}
	
	
}
