package com.wechat.controller;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.qiniu.QiniuUtil;
import com.wechat.service.BookCardService;
import com.wechat.service.BookOrderService;
import com.wechat.service.BookService;
import com.wechat.service.MallProductService;
import com.wechat.util.*;
import net.sf.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("bookApi")
public class BookShopApiController {
	
	
	@Resource
	private BookService bookService;
	
	@Resource
	private BookOrderService bookOrderService;
	
	@Resource
	private MallProductService mallProductService;
	
	
	public BookOrderService getBookOrderService() {
		return bookOrderService;
	}


	public void setBookOrderService(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}
	

	@Resource
	private BookCardService bookCardService;

	
	public BookCardService getBookCardService() {
		return bookCardService;
	}

	public void setBookCardService(BookCardService bookCardService) {
		this.bookCardService = bookCardService;
	}
	

	/**
	 * 根据店铺ID得到店铺所有会员信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchBookShopMembers")
	public void searchBookShopMembers(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HashMap map = new HashMap();
			String page=request.getParameter("page");
			String pageSize=request.getParameter("pageSize");
			map.put("page", null!=page&&!"".equals(page)?page:"1");
			map.put("pageSize",null!=pageSize&&!"".equals(pageSize)?pageSize:"30");
			
			if(null!=request.getParameter("id")&&!"".equals(request.getParameter("id"))){
				map.put("id", request.getParameter("id"));
			}
			if(null!=request.getParameter("memberId")&&!"".equals(request.getParameter("memberId"))){
				map.put("memberId", request.getParameter("memberId"));
			}
			if(null!=request.getParameter("memberPhone")&&!"".equals(request.getParameter("memberPhone"))){
				map.put("memberPhone", request.getParameter("memberPhone"));
			}
			
			Page resultPage = bookService.searchBookShopMembers(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response,dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 店主登录接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loginBookShop")
	public void loginBookShop(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String account = request.getParameter("account");
			String password = request.getParameter("password");

			BookShop bookShop=bookService.loginBookShop(account,MD5UTIL.encrypt(password));
			if (null!=bookShop.getAccount()) {
				JsonResult.JsonResultInfo(response, bookShop);
			} else {
				JsonResult.JsonResultError(response, 1001);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/**
	 * 添加/修改店铺信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveBookShop")
	public void saveBookShop(
			@RequestParam(value = "upFile", required = false) MultipartFile upFile,HttpServletRequest request,
			HttpServletResponse response,BookShop bookShop) {
		try {
			String epalFilePath=Config.getProperty("EPAL_FILE_PATH");
			String path = epalFilePath+"shop/";
			String logoUrl="";
			if(null!=upFile.getOriginalFilename()&&!"".equals(upFile.getOriginalFilename())){
				/** 获取文件的后缀 **/
				String suffix = upFile.getOriginalFilename().substring(
						upFile.getOriginalFilename().lastIndexOf("."));
				if (suffix.equalsIgnoreCase(".jpg")
						|| suffix.equalsIgnoreCase(".png")) {
					/** 使用UUID生成文件名称 **/
					String fileName = UUID.randomUUID().toString() + suffix;
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 保存
					upFile.transferTo(targetFile);
					logoUrl = Keys.STAT_NAME + "wechat/share/shop/" + fileName;
				}
			}
			if(null!=logoUrl&&!"".equals(logoUrl)){
				bookShop.setLogo(logoUrl);
			}
			if(null!=bookShop.getPassword()&&!"".equals(bookShop.getPassword())){
				bookShop.setPassword(MD5UTIL.encrypt(bookShop.getPassword()));
			}
			if (null!=bookShop.getId()&&!"".equals(bookShop.getId())) {
				bookShop.setCreatedate(bookService.searchBookShopById(bookShop.getId()).getCreatedate());
			} else{
				bookShop.setCreatedate(new Timestamp(System.currentTimeMillis()));
			}
			bookService.saveBookShop(bookShop);
			JsonResult.JsonResultInfo(response, bookShop);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 添加/修改系统课程计划激活二维码
	 */
	@RequestMapping("saveCourseProjectActive")
	public void saveCourseProjectActive(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		Integer planId=Integer.parseInt(ParameterFilter.emptyFilter("61", "planId", request));
		Integer produceCount=Integer.parseInt(ParameterFilter.emptyFilter("1", "produceCount", request));
		Integer activeCount=Integer.parseInt(ParameterFilter.emptyFilter("3", "activeCount", request));
		Integer status=Integer.parseInt(ParameterFilter.emptyFilter("1", "status", request));
		CourseProjectActive courseProjectActive=new CourseProjectActive();
		ArrayList<CourseProjectActive> courseProjectActives=new ArrayList<CourseProjectActive>();
		if(id!=-1){
			courseProjectActive=this.bookService.findCourseProjectActive(id);
			courseProjectActive.setPlanId(planId);
			courseProjectActive.setActiveCount(activeCount);
			courseProjectActive.setStatus(status);
			//更新激活二维码基本信息
			this.bookService.saveCourseProjectActive(courseProjectActive);
			//重新生成激活二维码
			JSONObject qrCodeContent=new JSONObject();
			qrCodeContent.put("activeId", courseProjectActive.getId());
			qrCodeContent.put("planId", courseProjectActive.getPlanId());
			courseProjectActive.setCreateTime(new Timestamp(System.currentTimeMillis()));
			CourseProjectSystem courseProjectSystem=this.bookService.findCourseProjectSystem(planId);
			courseProjectActive.setPlanName(courseProjectSystem.getProjectName());
			courseProjectActive.setQrcodeContent(qrCodeContent.toString());
			courseProjectActive.setQrcodeUrl(produceQrCodeUrl("FANDOUPLAN:"+qrCodeContent.toString()));
		    //更新激活二维码信息
			this.bookService.saveCourseProjectActive(courseProjectActive);
			courseProjectActives.add(courseProjectActive);
		}else{
			for (int i = 0; i < produceCount; i++) {
				courseProjectActive.setPlanId(planId);
				courseProjectActive.setActiveCount(activeCount);
				courseProjectActive.setStatus(status);
				//保存激活二维码基本信息
				this.bookService.saveCourseProjectActive(courseProjectActive);
				//生成激活二维码
				JSONObject qrCodeContent=new JSONObject();
				qrCodeContent.put("activeId", courseProjectActive.getId());
				qrCodeContent.put("planId", courseProjectActive.getPlanId());
				courseProjectActive.setCreateTime(new Timestamp(System.currentTimeMillis()));
				CourseProjectSystem courseProjectSystem=this.bookService.findCourseProjectSystem(planId);
				courseProjectActive.setPlanName(courseProjectSystem.getProjectName());
				courseProjectActive.setQrcodeContent(qrCodeContent.toString());
				courseProjectActive.setQrcodeUrl(produceQrCodeUrl("FANDOUPLAN:"+qrCodeContent.toString()));
			    //更新激活二维码信息
				this.bookService.saveCourseProjectActive(courseProjectActive);
				courseProjectActives.add(courseProjectActive);
				courseProjectActive=new CourseProjectActive();
			}
		}
		JsonResult.JsonResultInfo(response, courseProjectActives);
	}
	
	
	/**
	 * 添加/修改系统课程计划
	 */
	@RequestMapping("saveCourseProjectSystem")
	public void saveCourseProjectSystem(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		String projectName=ParameterFilter.emptyFilter("", "projectName", request);
		String planType=ParameterFilter.emptyFilter("1", "planType", request);
		String lessonList=ParameterFilter.emptyFilter("", "lessonList", request);
		Integer sort=Integer.parseInt(ParameterFilter.emptyFilter("0", "sort", request));
		CourseProjectSystem courseProjectSystem=new CourseProjectSystem();
		if(id!=-1){
			courseProjectSystem.setId(id);
		}
		courseProjectSystem.setLessonList(lessonList);
		courseProjectSystem.setPlanType(planType);
		courseProjectSystem.setProjectName(projectName);
		courseProjectSystem.setSort(sort);
		courseProjectSystem.setCreateDate(new Date());
		this.bookService.saveCourseProjectSystem(courseProjectSystem);
		JsonResult.JsonResultInfoDate(response, courseProjectSystem);
	}
	
	/**
	 * 添加/修改大V
	 * 
	 * 
	 */
	@RequestMapping("saveAnchor")
	public void saveAnchor(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		String anchorName=ParameterFilter.emptyFilter("", "anchorName", request);
		String logo=ParameterFilter.emptyFilter("1", "logo", request);
		String summary=ParameterFilter.emptyFilter("", "summary", request);
		Anchor anchor=new Anchor();
		if(id!=-1){
			anchor.setId(id);
		}
		anchor.setName(anchorName);
		anchor.setLogo(logo);
		anchor.setSummary(summary);
		this.bookService.saveAnchor(anchor);
		JsonResult.JsonResultInfoDate(response, anchor);
	}
	
	/**
	 * 删除大V
	 */
	@RequestMapping("deleteAnchor")
	public void deleteAnchor(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		this.bookService.deleteAnchor(id);
		JsonResult.JsonResultInfo(response, id);
	}
	
	
	/**
	 * 删除系统课程计划
	 */
	@RequestMapping("deleteCourseProjectSystem")
	public void deleteCourseProjectSystem(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		this.bookService.deleteCourseProjectSystem(id);
		JsonResult.JsonResultInfo(response, id);
	}
	
	
	public String  produceQrCodeUrl(String qrCodeContent) {
		String key=UUIDGenerator.getUUID()+".png";
		String imgPath = "/data/file/"+key; 
        TwoDimensionCode handler = new TwoDimensionCode();  
        handler.encoderQRCode(qrCodeContent, imgPath, "png");
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        String qrCodeUrl="";
        try {
            //调用put方法上传
            Response res = uploadManager.put(imgPath, key, new QiniuUtil().getUpToken(key,"image"));
            //打印返回的信息
            JSONObject qiniuRes=JSONObject.fromObject(res.bodyString());
            qrCodeUrl="http://ocvbx5hih.bkt.clouddn.com/"+qiniuRes.getString("key");
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            //System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return qrCodeUrl;
        
	}
	
	/**
	 * 删除课程计划激活二维码
	 */
	@RequestMapping("deleteCourseProjectActive")
	public void deleteCourseProjectActive(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		this.bookService.deleteCourseProjectActive(id);
		JsonResult.JsonResultInfo(response, id);
	}
	
	/**
	 * 激活课程计划激活二维码
	 */
	@RequestMapping("activeCourseProject")
	public void activeCourseProject(HttpServletRequest request,
			HttpServletResponse response){
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		Integer planId=Integer.parseInt(ParameterFilter.emptyFilter("-1", "planId", request));
		String epalId=ParameterFilter.emptyFilter("", "epalId", request);
		CourseProjectActive courseProjectActive=this.bookService.findCourseProjectActive(id);
		Integer status=courseProjectActive.getStatus();
		Integer activeCount=courseProjectActive.getActiveCount();
		if(null!=status&&status!=0){
			if(null!=activeCount&&activeCount>0){
				//更新激活码状态
				int nowActiveCount=activeCount-1;
				if(nowActiveCount==0){
					courseProjectActive.setStatus(0);
				}
				courseProjectActive.setActiveCount(nowActiveCount);
				
				this.bookService.saveCourseProjectActive(courseProjectActive);
				//添加课程计划到Epal
				CourseProjectSystem courseProjectSystem=this.bookService.findCourseProjectSystem(planId);
				CourseProject courseProject=new CourseProject();
				courseProject.setCreateDate(new Date());
				courseProject.setEpalId(epalId);
				courseProject.setMemberId(-1);
				courseProject.setPlanType(courseProjectSystem.getPlanType());
				courseProject.setProjectName(courseProjectSystem.getProjectName());
				courseProject.setRecordcount(0);
				courseProject.setSort(0);
				courseProject.setSystemPlan(courseProjectSystem.getId());
				mallProductService.saveCourseProject(courseProject);
				String[] MainCourses=courseProjectSystem.getLessonList().split(",");
				for (int i = 0; i < MainCourses.length; i++) {
					CourseProjectInfo courseProjectInfo=new CourseProjectInfo();
					courseProjectInfo.setCourseId(Integer.parseInt(MainCourses[i].trim()));
					courseProjectInfo.setProjectId(courseProject.getId());
					mallProductService.saveCourseProjectInfo(courseProjectInfo);
				}
				JsonResult.JsonResultInfo(response, "激活成功，剩余激活次数"+nowActiveCount);
			}else{
				JsonResult.JsonResultInfo(response, "二维码激活次数用完");
			}
		}else{
			JsonResult.JsonResultInfo(response, "二维码已失效");
		}
		
		
	}
	

	/**
	 * 删除店铺信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteBookShop")
	public void deleteBookShop(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String id = request.getParameter("id");
			BookShop bookShop = new BookShop();
			bookShop.setId(Integer.parseInt(id));
			bookService.deleteBookShop(bookShop);
			result.put("success", 1);
			JSONObject data = new JSONObject();
			data.put("id", bookShop.getId());
			result.put("data", data);
			writer.print(result.toString());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	
	
	
	

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}



	/**
	 * 店铺借书
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveShopOrder")
	public void saveShopOrder(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			String shopId = request.getParameter("shopId");
			String memberId = request.getParameter("memberId");
			String barCode = request.getParameter("barCode");
			String mobile = request.getParameter("mobile");
			//如果当前会员没有未完成的订单
			if(false == this.bookOrderService.checkShopOrder(shopId,memberId)){
				ShopOrder shopOrder = new ShopOrder();
				shopOrder.setCreateDate(new Date());
				shopOrder.setMemberId(new Integer(memberId));
				shopOrder.setMobile(mobile);
				shopOrder.setShopId(new Integer(shopId));
				String temp = new Date().getTime()+"";
				shopOrder.setOrderNumber(temp);
				shopOrder.setStatus(0);
				//查询是否有没有归还的订单
				
				this.bookOrderService.saveShopOrder(shopOrder);
				
				ShopOrderInfo shopOrderInfo = new ShopOrderInfo();
				if(!"".equals(barCode) && barCode != null)
				{
					String[] codes = barCode.split(",");
					
					for (int i = 0; i < codes.length; i++) {
						
						shopOrderInfo = new ShopOrderInfo();
						shopOrderInfo.setBarcode(codes[i]);
						shopOrderInfo.setCateId(codes[i].toString().substring(0,5));
						shopOrderInfo.setOrderid(shopOrder.getId());
						shopOrderInfo.setStatus(0);
						shopOrderInfo.setCreateDate(new Date());
						//保存订单详情
						this.bookOrderService.saveShopOrderInfo(shopOrderInfo);
						//修改书籍状态
						this.bookService.updateBookCategoryStatus(codes[i], "0");
						
					}
					JSONObject result = new JSONObject();
					result.put("msg", "借书成功");
					result.put("code", "200");
					JsonResult.JsonResultInfo(response,result);
				}
				else{
					JSONObject result = new JSONObject();
					result.put("msg", "借书列表为空");
					result.put("code", "201");
					JsonResult.JsonResultInfo(response,result);
				}

			}else{
				JSONObject result = new JSONObject();
				result.put("msg", "借书失败，有未完成的订单");
				result.put("code", "202");
				JsonResult.JsonResultInfo(response,result);
			}
			
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	
	
	/**
	 * 店铺还书
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateShopOrder")
	public void updateShopOrder(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			String orderId = request.getParameter("orderId");
			String barCode = request.getParameter("barCode");
			//获取待还数据列表
			List orderList = bookOrderService.searchShopOrderInfo(orderId);
			
			ShopOrderInfo shopOrderInfo = new ShopOrderInfo();
			if(!"".equals(barCode) && barCode != null){
				String[] codes = barCode.split(",");
				for (int i = 0; i < codes.length; i++) {
					//修改库存状态
					this.bookService.updateBookCategoryStatus(codes[i], "1");
					
					//修改定单详情书籍状态
					this.bookOrderService.updateShopOrderInfoStatus(codes[i]);
					
				}
			}else{
				JsonResult.JsonResultError(response, 1000);
			}
			
			
			if(orderList.size() ==barCode.split(",").length){
				this.bookOrderService.updateShopOrderStatus(orderId, "1");
			}
			
			JSONObject result = new JSONObject();
			result.put("success", 1);
			JsonResult.JsonResultInfo(response,"");
			

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	

	
	

	
	/**
	 * 根据店铺ID得到店铺所有订单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchShopOrder")
	public void searchShopOrder(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String page ="1";
			String rowsPerPage ="20";
			if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
			{
				page = request.getParameter("page");
			}
			if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
			{
				rowsPerPage = request.getParameter("pageSize");
			}
			HashMap map = new HashMap();
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			map.put("shopId", request.getParameter("shopId"));
			map.put("status", request.getParameter("status"));
			map.put("memberId", request.getParameter("memberId"));
			map.put("mobile", request.getParameter("mobile"));
			
			if(!"".equals(request.getParameter("shopId")) && request.getParameter("shopId")!= null)
			{
				Page resultPage = this.bookOrderService.searchShopOrder(map);
				List<Object[]> infoList = resultPage.getItems();
				String[] tempStr = null;
				List tempList = new ArrayList();
				//封装成JSON显示对象
				List jsonList = new ArrayList();
				JSONObject jsonObj = new JSONObject();
				JSONObject cateObj = new JSONObject();
				if (infoList!=null) {
					JSONArray AgentKeyWordInfo = new JSONArray();

					for(Object[] category : infoList) {
						JSONObject jobj = new JSONObject();
						jobj.put("orderId", category[0]);
						jobj.put("orderNumber", category[1]);
						jobj.put("orderDate", category[2].toString().substring(0,10));
						jobj.put("memberName", category[5]);
						jobj.put("mobile", category[6]);
						jobj.put("headImg", category[7]);
						jobj.put("status", category[8]);
						if(!"".equals(category[3]) && category[3]!= null )
						{
							
							tempStr = category[3].toString().split("⊙");
							tempList = new ArrayList();
							
							for (int i = 0; i < tempStr.length; i++) {
								
								cateObj = new JSONObject();
								cateObj.put("code", tempStr[i].toString().split("⊥")[0]);
								////System.out.println("lenght = "+tempStr[i].toString().split(">").length);
								
								
								////System.out.println("name = "+tempStr[i].toString().split(">")[1]);
								if(tempStr[i].toString().split("⊥").length>=2)
								{
									cateObj.put("name", tempStr[i].toString().split("⊥")[1]);
								}
								if(tempStr[i].toString().split("⊥").length>=3)
								{
									cateObj.put("cover", Keys.STAT_NAME+"/wechat/wechatImages/book/"+tempStr[i].toString().split("⊥")[2]);
								}
								else
								{
									cateObj.put("cover", "");
								}
								
								if(tempStr[i].toString().split("⊥").length>=4)
								{
									cateObj.put("author", tempStr[i].toString().split("⊥")[3]);
								}
								else
								{
									cateObj.put("author", "");
								}
								if(tempStr[i].toString().split("⊥").length>=5)
								{
									String[] tempLength = tempStr[i].toString().split("⊥");
									////System.out.println("tempLength.length = "+tempLength.length);
									if(tempLength.length ==5)
									{
										if(tempStr[i].toString().split("⊥")[4].length()>=35)
										{
											cateObj.put("content", tempStr[i].toString().split("⊥")[4].toString().substring(0,35)+"...");
										}
										else
										{
											cateObj.put("content", tempStr[i].toString().split("⊥")[4]);
										}
									}
									else
									{
										cateObj.put("content", "");
									}
								}
								else
								{
									cateObj.put("content", "");
								}
								cateObj.put("bookStatus", tempStr[i].toString().split("⊥")[5]);
								////System.out.println("name = "+tempStr[i].toString().split(">")[1]);
								tempList.add(cateObj);
								
							}
						}
						jobj.put("bookList", tempList);
						jsonList.add(jobj);
					}
					jsonObj.put("infoList", jsonList);
					jsonObj.put("pageCount", resultPage.getTotalPageCount());
					jsonObj.put("totalCount", resultPage.getTotalCount());
					JsonResult.JsonResultInfo(response,jsonObj.toString());
				}
			}else{
				JsonResult.JsonResultError(response, 1000);
			}	
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 根据店铺ID得到店铺所有书籍库存
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchShopBook")
	public void searchShopBook(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String page = ParameterFilter.emptyFilter("1","page",request);
			String pageSize = ParameterFilter.emptyFilter("30","pageSize",request);
			String shopId = ParameterFilter.emptyFilter("","shopId",request);
			String bookName = ParameterFilter.emptyFilter("","bookName",request);
			HashMap map = new HashMap();
			map.put("page", page);
			map.put("rowsPerPage", pageSize);
			map.put("shopId", shopId);
			map.put("name", bookName);
			if(!"".equals(shopId) && null != shopId){
				
				Page resultPage = this.bookOrderService.searchShopBook(map);
				List<Object[]> infoList = resultPage.getItems();
				String[] tempStr = null;
				List tempList = new ArrayList();
				//封装成JSON显示对象
				List jsonList = new ArrayList();
				JSONObject jsonObj = new JSONObject();
				JSONObject cateObj = new JSONObject();
				if (infoList!=null) {
					JSONArray AgentKeyWordInfo = new JSONArray();
					for(Object[] category : infoList) {
						JSONObject jobj = new JSONObject();
						jobj.put("cateId", category[0]);
						jobj.put("name", category[1]);
						jobj.put("cover", Keys.STAT_NAME+"/wechat/wechatImages/book/"+category[2]);
						jobj.put("author", category[3]);
						jobj.put("content", category[4]);
						
						if(category[4].toString().length()>=35)
						{
							cateObj.put("content", category[4].toString().substring(0,35)+"...");
						}
						else
						{
							cateObj.put("content",category[4]);
						}
						jobj.put("code", category[5]);
						
						
						jsonList.add(jobj);
					}
					
				}
				jsonObj.put("infoList", jsonList);
				jsonObj.put("pageCount", resultPage.getTotalPageCount());
				jsonObj.put("totalCount", resultPage.getTotalCount());
				JSONObject data = new JSONObject();
				data.put("bookList", jsonObj);
					map.put("status","0");
					resultPage = this.bookOrderService.searchOrderBook(map);
					infoList = resultPage.getItems();
					tempStr = null;
					tempList = new ArrayList();
					//封装成JSON显示对象
					jsonList = new ArrayList();
					jsonObj = new JSONObject();
					cateObj = new JSONObject();
					if (infoList.size()>0) {
						JSONArray AgentKeyWordInfo = new JSONArray();
						for(Object[] category : infoList) {
							JSONObject jobj = new JSONObject();
							jobj.put("cateId", category[0]);
							jobj.put("name", category[1]);
							jobj.put("cover", Keys.STAT_NAME+"/wechat/wechatImages/book/"+category[3]);
							jobj.put("author", category[2]);
							jobj.put("content", category[4]);
							
							if(category[4].toString().length()>=35)
							{
								cateObj.put("content", category[4].toString().substring(0,35)+"...");
							}
							else
							{
								cateObj.put("content",category[4]);
							}
							jobj.put("code", category[5]);
							
							
							jsonList.add(jobj);
						}
						
					}
					jsonObj.put("infoList", jsonList);
					jsonObj.put("pageCount", resultPage.getTotalPageCount());
					jsonObj.put("totalCount", resultPage.getTotalCount());
					data.put("outBookList", jsonObj);
					
					
					
					map.put("status","1");
					 resultPage = this.bookOrderService.searchOrderBook(map);
						infoList = resultPage.getItems();
						tempStr = null;
						tempList = new ArrayList();
						//封装成JSON显示对象
						jsonList = new ArrayList();
						jsonObj = new JSONObject();
						cateObj = new JSONObject();
						if (infoList!=null) {
							JSONArray AgentKeyWordInfo = new JSONArray();

							for(Object[] category : infoList) {
								JSONObject jobj = new JSONObject();
								
								
								jobj.put("cateId", category[0]);
								jobj.put("name", category[1]);
								jobj.put("cover", Keys.STAT_NAME+"/wechat/wechatImages/book/"+category[3]);
								jobj.put("author", category[2]);
								jobj.put("content", category[4]);
								
								if(category[4].toString().length()>=35)
								{
									cateObj.put("content", category[4].toString().substring(0,35)+"...");
								}
								else
								{
									cateObj.put("content",category[4]);
								}
								jobj.put("code", category[5]);
								
								
								jsonList.add(jobj);
							}
							
						}
						jsonObj.put("infoList", jsonList);
						jsonObj.put("pageCount", resultPage.getTotalPageCount());
						jsonObj.put("totalCount", resultPage.getTotalCount());
						data.put("inputBookList", jsonObj);
						
				
				JsonResult.JsonResultInfo(response,data);
			}
			else
			{
				JsonResult.JsonResultError(response, 1000);
			}
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}

	}
	
	
	
	
	/*
	 *修改库存状态	
	 * 
	 * */
	@RequestMapping("updateBookStockStatus")
	public void updateBookStockStatus(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String barCode = ParameterFilter.emptyFilter("", "barCode", request);
			String status = ParameterFilter.emptyFilter("", "status", request);
			String copyTimes = ParameterFilter.emptyFilter("", "copyTimes", request);
			Book book = this.bookService.getBook(barCode);
			if(!"".equals(status)&&null!=status){
				book.setStatus(Integer.parseInt(status));
				
			}
			if(!"".equals(copyTimes)&&null!=copyTimes){
				book.setCopyTimes(book.getCopyTimes() + Integer.parseInt(copyTimes));
			}			
			this.bookService.saveBook(book);
			JsonResult.JsonResultInfo(response,book);
			

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	/*
	 *	获取库存列表 
	 * 
	 * */
	@RequestMapping("getBookStockList")
	public void getBookStockList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String userId = RedisKeys.REDIS_USER
					+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
			User user =new User();
			try {
				user = RedisUtil.getUser(userId);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			String shopId = this.bookService.getShopIdByUserId(user.getId().toString());
			HashMap map = new HashMap();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			map.put("shopId", shopId);
			map.put("cateId", ParameterFilter.emptyFilter("", "cateId", request));
			Page bookLists = bookService.getBookStockList(map);
			ArrayList<Object[]> dataList = (ArrayList<Object[]>) bookLists
					.getItems();
			JSONArray result = new JSONArray();
			for(int i=0;i<dataList.size();i++){
				JSONObject temp = new JSONObject();
				Object[] obj = dataList.get(i);
				temp.put("barCode", obj[0]);
				temp.put("codeInfo", obj[1]);
				temp.put("isexist", obj[2]);
				temp.put("copy_times", obj[3]);
				temp.put("status", obj[4]);
				result.add(temp);
			}
			bookLists.setItems(result);
			JsonResult.JsonResultInfo(response,bookLists);
			

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	/*
	 * 通过书名搜索书籍
	 * 
	 * */
	@RequestMapping("searchBookByName")
	public void searchBookByName(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HashMap<String,String> map = new HashMap<>();
			map.put("bookName", ParameterFilter.emptyFilter("", "bookName", request));
			map.put("page", "1");
			map.put("pageSize", "100");
			Page<?> result = bookService.searchBookByName(map);
			JsonResult.JsonResultInfo(response,result);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}	
	

	/*
	 * 保存书籍到图书馆
	 * 
	 * */
	@RequestMapping("saveBookToLibrary")
	public void saveBookToLibrary(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String cates = ParameterFilter.emptyFilter("", "cates", request);
			String [] temp = cates.split(",");
			String userId = RedisKeys.REDIS_USER
					+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
			User user =new User();
			try {
				user = RedisUtil.getUser(userId);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			String shopId = this.bookService.getShopIdByUserId(user.getId().toString());
			
			BookShop bookShop = this.bookService.searchBookShopById(new Integer(shopId));
			JSONArray result = new JSONArray();
			for (int i=0;i<temp.length;i++){
				String[] addData = temp[i].split(":");
				String cateId = addData[0];
				
				for(int j=0;j<Integer.parseInt(addData[1]);j++){
					String maxBookId = this.bookService.getMaxBookCateId(cateId,cateId.substring(0,1));
					Book book = new Book();
					String codeValue = "";
					HashMap map = new HashMap();
					map.put("page", "1");
					map.put("rowsPerPage", "10");
					map.put("cateId", cateId);
					Object[] category =  null;
					Page resultPage = this.bookService.searchCategoryInfo(map); 
					String barCode = "";
					Integer maxId = Integer.parseInt(maxBookId);
					book.setCateId(cateId);
					book.setIsexist(1);
					barCode = cateId +(maxId+ Integer.parseInt(cateId.substring(1))*10000);
					book.setBarCode(barCode);
					book.setBelong(new Integer(shopId));
					codeValue = cateId.substring(0,5);
					category = (Object[])resultPage.getItems().get(0);
					codeValue = "["+book.getBarCode()+"]["+category[1].toString()+"]["+codeValue+"]["+bookShop.getName()+"]";
					book.setCodeInfo(codeValue);
					book.setStatus(0);
					book.setCopyTimes(0);
					this.bookService.saveBook(book);				
					
					
				}
				HashMap map2 = new HashMap();
				map2.put("page", "1");
				map2.put("pageSize", "1");
				map2.put("searchStr", "");
				map2.put("cateId", cateId);
				map2.put("userId", user.getId());
				Page bookLists = bookService.getBookList(map2); 
				result.add(bookLists.getItems().get(0));
			}
			JsonResult.JsonResultInfo(response, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}	
	
	/*
	 * 
	 * 新增库存
	 * 
	 * */
	@RequestMapping(value="/saveBook")
	public void saveBook(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		try {
			
			String userId = RedisKeys.REDIS_USER
					+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
			User user =new User();
			try {
				user = RedisUtil.getUser(userId);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			String shopId = this.bookService.getShopIdByUserId(user.getId().toString());
			
			BookShop bookShop = this.bookService.searchBookShopById(new Integer(shopId));
			
			String cateId = request.getParameter("tempCateId");
			
			String maxBookId = this.bookService.getMaxBookCateId(cateId,cateId.substring(0,1));
			Integer kucunCount = new Integer(request.getParameter("kucunCount"));
			
			
			Book book = new Book();
			String pathName = "";
			String addPathName = "";
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode(); 
			String codeValue = "";
			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "10");
			map.put("cateId", cateId);
			Object[] category =  null;
			Page resultPage = this.bookService.searchCategoryInfo(map); 
			String barCode = "";
			Integer maxId = Integer.parseInt(maxBookId);
			JSONArray result = new JSONArray();
			for (int i = 0; i < kucunCount; i++) {
				book = new Book();
				book.setCateId(cateId);
				book.setIsexist(1);
				barCode = cateId +(maxId+ Integer.parseInt(cateId.substring(1))*10000);
				book.setBarCode(barCode);
				book.setBelong(new Integer(shopId));
				codeValue = cateId.substring(0,5);
				category = (Object[])resultPage.getItems().get(0);
								
				codeValue = "["+book.getBarCode()+"]["+category[1].toString()+"]["+codeValue+"]["+bookShop.getName()+"]";
				book.setCodeInfo(codeValue);
				
				book.setStatus(0);
				book.setCopyTimes(0);
				this.bookService.saveBook(book);
				result.add(book);
				maxId ++;
				
			}
			JsonResult.JsonResultInfo(response,result);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 500);
		}
		
	}
	
	/**
	 * 店铺汇总信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchBookShopInfo")
	public void searchBookShopInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			Object[] obj  = this.bookOrderService.searchBookShopInfo(request.getParameter("shopId"),request.getParameter("bookName"));
			JSONObject result = new JSONObject();
			result.put("bookCount", obj[2]);
			result.put("outBook", obj[0]);
			result.put("inputBook", obj[1]);
			JsonResult.JsonResultInfo(response,result);
			

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	
	/**
	 * 查询会员的书院会员卡信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchBookCardByMember")
	public void searchBookCardByMember(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			List<BookCard> infoList = this.bookCardService.searchBookCardByMemberId(request.getParameter("memberId"));
			
			List jsonList = new ArrayList();
			JSONObject jsonObj = new JSONObject();
			JSONObject cateObj = new JSONObject();
			if (infoList!=null) {
				JSONArray AgentKeyWordInfo = new JSONArray();

				for(BookCard bookCard : infoList) {
					JSONObject jobj = new JSONObject();
					
					
					jobj.put("id", bookCard.getId());
					jobj.put("card", bookCard.getCard());
					jobj.put("year", bookCard.getYear());
					jobj.put("type", bookCard.getType());
					
					
					if(bookCard.getStatus()==0)
					{
						jobj.put("status", "未使用");
					}
					else
					{
						jobj.put("status", "已使用");
					}
					
					
					
					jsonList.add(jobj);
				}
				
			}
			
			JSONObject result = new JSONObject();
			result.put("infoList",jsonList.toString());
			JsonResult.JsonResultInfo(response,result);
			

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}



	public MallProductService getMallProductService() {
		return mallProductService;
	}


	public void setMallProductService(MallProductService mallProductService) {
		this.mallProductService = mallProductService;
	}
	
	
	@RequestMapping("bookShopSaleManager")
	public String bookShopSaleManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		
		
		
		try {
			
			if(!"".equals(request.getParameter("page")) && request.getParameter("page") != null)
			{
				queryDto.setPage(request.getParameter("page"));
				
			}
			if(!"".equals(request.getParameter("rowsPerPage")) && request.getParameter("rowsPerPage") != null)
			{
				queryDto.setPageSize(request.getParameter("rowsPerPage"));
				
			}
			map.put("page", queryDto.getPage());
			map.put("rowsPerPage", queryDto.getPageSize());
			map.put("shopId", request.getParameter("shopId"));
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));
			Page resultPage = bookService.searchBookShopSale(map);
			
			
			Object[] saleInfo = this.bookService.searchBookShopSum(map);
		
			ArrayList<Object[]> infoList= (ArrayList) resultPage.getItems();
			List jsonList = new ArrayList();
			JSONObject jsonObj = new JSONObject();
			if (infoList!=null) {
				JSONArray AgentKeyWordInfo = new JSONArray();
				
				
				
				for(Object[] obj : infoList) {
					JSONObject jobj = new JSONObject();
						
					jobj.put("id", obj[0]);
					jobj.put("name", obj[1]);
					jobj.put("count", obj[2]);
					jobj.put("totalPrice", obj[3]);
					jsonList.add(jobj);
				}
				jsonObj.put("infoList", jsonList);
				
			}
			JSONObject result = new JSONObject();
			result.put("infoList",jsonList.toString());
			result.put("memberCount",saleInfo[0]);
			result.put("totalPrice",saleInfo[1]);
			JsonResult.JsonResultInfo(response,result);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
				
		return null;
		
	}
	
	
	public static void main(String[] args) {
		
		String ss="1,2,3,4,5";
		//System.out.println(ss.split(",").length);
		
	}
	

	
	
	
	

}
