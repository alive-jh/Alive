package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.qiniu.QiniuUtil;
import com.wechat.service.BookService;
import com.wechat.service.CategoryService;
import com.wechat.util.*;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("bookShop")
@Controller
public class BookShopController {
	
	@Resource
	private BookService bookService;
	
	public BookService getBookService() {
		return bookService;
	}
	
	@Resource
	private CategoryService categoryService;

	
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	@RequestMapping("bookShopManager")
	public String bookShopManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = bookService.searchBookShops(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList bookShops=(ArrayList) resultPage.getItems();
		JSONArray array=new JSONArray();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonTimeStampValueProcessor());
		array=JSONArray.fromObject(bookShops, jsonConfig);
		request.setAttribute("jsonData", array.toString());
		
		return "bookShop/bookShopManager";
	}
	
	/**
	 * 系统课程计划激活
	 * @param request
	 * @param response
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("coursePlanActive")
	public String coursePlanActive(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = bookService.searchCourseProjectActives(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList courseProjectActives=(ArrayList) resultPage.getItems();
		JSONArray jsonData=new JSONArray();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonTimeStampValueProcessor());
		jsonData=JSONArray.fromObject(courseProjectActives, jsonConfig);
		request.setAttribute("jsonData", jsonData.toString());
		ArrayList systemProjects=this.bookService.findCourseProjectSystems();
		JSONArray jsonSystem=new JSONArray();
		jsonSystem=JSONArray.fromObject(systemProjects);
		request.setAttribute("systemProjects", jsonSystem.toString());
		return "bookShop/coursePlanActive";
	}
	
	/**
	 * 系统课程计划管理
	 * @param request
	 * @param response
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("coursePlanManager")
	public String coursePlanManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = bookService.searchCourseProjectSystems(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList courseProjectSystems=(ArrayList) resultPage.getItems();
		JSONArray jsonData=new JSONArray();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		jsonData=JSONArray.fromObject(courseProjectSystems, jsonConfig);
		request.setAttribute("jsonData", jsonData.toString());
		return "bookShop/coursePlanManager";
	}
	
	/**
	 * 大V管理
	 * @param request
	 * @param response
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("anchorManager")
	public String anchorManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = bookService.findAnchors(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList courseProjectSystems=(ArrayList) resultPage.getItems();
		JSONArray jsonData=new JSONArray();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		jsonData=JSONArray.fromObject(courseProjectSystems, jsonConfig);
		request.setAttribute("jsonData", jsonData.toString());
		return "bookShop/anchorManager";
	}
	
	
	@RequestMapping("bookMemberManager")
	public String bookMemberManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = bookService.searchBookShopMembers(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList bookShops=(ArrayList) resultPage.getItems();
		JSONArray array=new JSONArray();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonTimeStampValueProcessor());
		array=JSONArray.fromObject(bookShops, jsonConfig);
		request.setAttribute("jsonData", array.toString());
		
		return "bookShop/bookMemberManager";
	}


	
	
	@RequestMapping("bookShopSaleManager")
	public String bookShopSaleManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		HashMap map = new HashMap();
		
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
		map.put("name", queryDto.getName());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		Page resultPage = bookService.searchBookShopSale(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		request.setAttribute("saleInfo", this.bookService.searchBookShopSum(map));
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
		
		
		request.setAttribute("jsonStr", jsonObj.toString());
		
		return "bookShop/bookShopSaleManager";
	}
	
	
	
	@RequestMapping("/bookManager")
	public String bookManager(HttpServletRequest request,QueryDto queryDto) {
		String userId = RedisKeys.REDIS_USER
				+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User user =new User();
		try {
			user = RedisUtil.getUser(userId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		HashMap map = new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("userId", user.getId());
		Page bookLists = bookService.getBookList(map); 
		
		List bookList = bookLists.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(bookList));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", bookLists);
		return "bookShop/newBookManager";
	}
	
	
	
	@RequestMapping("/saveCategory")
	public String saveCategory(@RequestParam(value = "file1", required = false) MultipartFile file1,@RequestParam(value = "file2", required = false) MultipartFile file2, 
			@RequestParam(value = "file3", required = false) MultipartFile file3, HttpServletRequest request,Category category) {
		
		
		category.setStatus(0);
		String userId = RedisKeys.REDIS_USER
				+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User user =new User();
		try {
			user = RedisUtil.getUser(userId);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		String shopId = this.bookService.getShopIdByUserId(user.getId().toString());
		
		
		String bookType = "";
		String bookTypeNumber = "";
		if(category.getRightId() <=3)
		{
			bookType = "1";
			bookTypeNumber = "C";
		}
		else  
		{
			bookType = "2";
			bookTypeNumber = "E";
		}
		if(file1.getSize()!=0)
		{
			String fileName1 = "";
			fileName1 = file1.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH +"book/");
			if (!fileDir.exists())
			{
				fileDir.mkdir();
			}
			int random=(int)(Math.random()*1000000); 
			fileName1 = new Date().getTime() + random  + "" + fileName1.subSequence(fileName1.indexOf("."), fileName1.length());
			
			String pathName = Keys.USER_PIC_PATH +"book/";
	        File targetFile = new File(pathName, fileName1);
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }
	        
	       
	        
	        //保存
	        try {
	            file1.transferTo(targetFile);
	           
	            File deleteFile = new File(Keys.USER_PIC_PATH + "book/" + request.getParameter("oldLogo1"));
	            String oldPic = request.getParameter("oldLogo1");
	            if(!"".equals(request.getParameter("oldLogo1")) && request.getParameter("oldLogo1")!= null)
	            {
	            	 deleteFile.delete();
	            	 
	            	
						QiniuUtil.delFile(Keys.QINIU_IMAGE, oldPic.substring(oldPic.lastIndexOf("/")+1));//删除七牛旧文件
						
	            }
	           
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        category.setCover(QiniuUtil.addFile(pathName, fileName1,Keys.QINIU_IMAGE));
	        
	        
		}
		else
		{
			
			
			if(!"".equals(request.getParameter("cover1")) && request.getParameter("cover1")!= null )
            {
				  category.setCover(request.getParameter("cover1"));	
            }
            else
            {
            	category.setCover("");	
            	if(!"".equals(request.getParameter("oldLogo1")) && request.getParameter("oldLogo1")!= null)
            	{
            		File deleteFile = new File(Keys.USER_PIC_PATH + "book/" + request.getParameter("oldLogo1"));
                	deleteFile.delete();
                	
                	QiniuUtil.delFile(Keys.QINIU_IMAGE, request.getParameter("oldLogo1").substring(request.getParameter("oldLogo1").lastIndexOf("/")+1));//删除七牛旧文件
            	}
            	
            }
		}
		

		if(file3.getSize()!=0)
		{
			String fileName3 = "";
			fileName3 = file3.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH +"book/mp3");
			if (!fileDir.exists())
			{
				fileDir.mkdir();
			}
			int random=(int)(Math.random()*1000000); 
			//fileName3 = new Date().getTime() + random  + "" + fileName3.subSequence(fileName3.indexOf("."), fileName3.length());
			
			String pathName = Keys.USER_PIC_PATH +"book/mp3/";
	        File targetFile = new File(pathName, fileName3);
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }
	        
	        
	        
	        //保存
	        try {
	            file3.transferTo(targetFile);
	           
	            File deleteFile = new File(Keys.USER_PIC_PATH + "book/mp3/" + request.getParameter("oldMp3"));

	            if(!"".equals(request.getParameter("oldMp3")) && request.getParameter("oldMp3")!= null)
	            {
	            	 deleteFile.delete();
	            	 QiniuUtil.delFile(Keys.QINIU_IMAGE, request.getParameter("oldMp3").substring(request.getParameter("oldMp3").lastIndexOf("/")+1));//删除七牛旧文件
	                    
	            }
	           
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        category.setMp3(QiniuUtil.addFile(pathName, fileName3,Keys.QINIU_IMAGE));
	        
	        
		}
		Integer cateId = this.bookService.getCateId(bookTypeNumber);
		cateId++;
		DecimalFormat df = new DecimalFormat("0000");
	    String tempCateId = df.format(cateId);
	    if(!"".equals(category.getCateID()) && category.getCateID()!= null)
	    {
	    	
	    	this.bookService.updateCateGory(category);
	    	
	    	
	    }
	    else
	    {
	    	if(!"".equals(request.getParameter("tempCateId")) && request.getParameter("tempCateId") != null)
	    	{
	    		category.setCateID(request.getParameter("tempCateId"));
	    	}
	    	else
	    	{
	    		category.setCateID(bookTypeNumber + tempCateId);
	    	}
	    	
	    	this.bookService.saveCategory(category);
	    	
	    	
	    	BookShopCategory bookShopCategory = new BookShopCategory();
	    	
	    	bookShopCategory.setCateId(category.getCateID());
	    	bookShopCategory.setShopId(new Integer(shopId));
	    	this.bookService.addShopCategory(bookShopCategory);//新增书店书籍
	    	
	    	Book book = new Book();
			Integer bookCateId = this.bookService.getBookCateId(category.getCateID());
			String tempBookCateId = "";
			
			String picPath = Keys.USER_PIC_PATH+"/book/barCode/";
			int random = 0; 
			String pathName = "";
			String addPatName = "";
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode(); 
			
			
			BookShop bookShop = this.bookService.searchBookShopById(new Integer(shopId));
			for (int i = 0; i < category.getCount(); i++) {
				
				
				book = new Book();
				book.setBelong(new Integer(shopId));
				bookCateId ++;
				tempBookCateId = df.format(bookCateId);
				book.setCateId(category.getCateID());
				book.setBarCode(category.getCateID()+tempBookCateId);
				this.bookService.saveBook(book);
				
				random=(int)(Math.random()*1000000); 
				pathName = new Date().getTime() + random  +".png";
				addPatName = picPath + pathName;
			
				
				String codeValue = "";
				codeValue = "["+book.getBarCode()+"]["+category.getbName()+"]["+category.getCateID()+"]["+bookShop.getName()+"]";
				book.setCodeInfo(codeValue);
				twoDimensionCode.encoderQRCode(codeValue,addPatName, "png");
				book.setUrl(QiniuUtil.addFile(picPath, pathName,Keys.QINIU_IMAGE));
				
				this.bookService.updateBookInfo(book);
				
			}
	    }
	   
	    
	    
		
	   
		
		//this.redisService.del(RedisKeys.APP_BOOK_INDEX);
		//this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookManager";
	}
	
	
	@RequestMapping(value="/saveBookByAjax")
	public void saveBookByAjax(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		String jsonStr = "{\"status\":\"ok\"}";
		
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
			
			String cateId = request.getParameter("cateId");
			
			String maxBookId = this.bookService.getMaxBookCateId(cateId,cateId.substring(0,1));
			DecimalFormat df = new DecimalFormat("0000");
			Integer maxId =0;
			if("0".equals(maxBookId))
			{
				maxId =1;
			}
			else
			{
				maxId = new Integer(maxBookId.substring(1,maxBookId.length())) + 1;
			}
			
			
			
			
			Book book = new Book();
			book.setCateId(cateId);
			book.setIsexist(1);
			book.setBarCode(cateId + df.format(maxId));
			book.setBelong(new Integer(shopId));
			this.bookService.saveBook(book);
			
			String picPath = Keys.USER_PIC_PATH+"/book/barCode/";
			int random=(int)(Math.random()*1000000); 
			String pathName =   new Date().getTime() + random  +".png";
			String addPathName = picPath + pathName;
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode(); 
			
			String codeValue = cateId.substring(0,5);
			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "10");
			map.put("cateId", cateId);
			Object[] category =  null;
			Page resultPage = this.bookService.searchCategoryInfo(map); 
			category = (Object[])resultPage.getItems().get(0);
			
		
			
			
		
			
			codeValue = "["+book.getBarCode()+"]["+category[1].toString()+"]["+codeValue+"]["+bookShop.getName()+"]";
			twoDimensionCode.encoderQRCode(codeValue,addPathName, "png");
			book.setCodeInfo(codeValue);
			
			//book.setUrl(Keys.STAT_NAME+"wechat/wechatImages/book/barCode/"+pathName);
			book.setUrl(QiniuUtil.addFile(picPath, pathName,Keys.QINIU_IMAGE));
			this.bookService.updateBookInfo(book);
			
			
			
			JSONObject jobj = new JSONObject();
			jobj.put("infoList", book);
			jsonStr = jobj.toString();
			
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}
	
	
	
	
	@RequestMapping(value="/saveBook")
	public String saveBook(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
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
//			String picPath = Keys.USER_PIC_PATH+"/book/barCode/";
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
			for (int i = 0; i < kucunCount; i++) {
				book = new Book();
				book.setCateId(cateId);
				book.setIsexist(1);
				barCode = cateId.substring(0, 1) +(maxId+ Integer.parseInt(cateId.substring(1))*10000);
				book.setBarCode(barCode);
				book.setBelong(new Integer(shopId));
//				
//				int random=(int)(Math.random()*1000000); 
//				pathName =   new Date().getTime() + random  +".png";
//				addPathName = picPath + pathName;
				codeValue = cateId.substring(0,5);
				category = (Object[])resultPage.getItems().get(0);
								
				codeValue = "["+book.getBarCode()+"]["+category[1].toString()+"]["+codeValue+"]["+bookShop.getName()+"]";
				//取消生成二维码
				//				twoDimensionCode.encoderQRCode(codeValue,addPathName, "png");
				book.setCodeInfo(codeValue);
				
				//book.setUrl(QiniuUtil.addFile(picPath, pathName,Keys.QINIU_IMAGE));
				this.bookService.saveBook(book);
				maxId ++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:bookManager?page="+queryDto.getPage();
	}
	
	
	
	@RequestMapping("/removeCategory")
	public String removeCategory( HttpServletRequest request) 
	{
		
		
		this.bookService.deleteCateGory(request.getParameter("cateId"));
		this.bookService.deleteBookShopCateId(request.getParameter("cateId"));
		File deleteFile = new File(Keys.USER_PIC_PATH + "book/" + request.getParameter("cover"));
    	deleteFile.delete();
    	//this.redisService.del(RedisKeys.APP_BOOK_INDEX);
    	//this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookManager";
	}
	

	
	@RequestMapping("searchBookQRCode")
	public String searchBookQRCode(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto) throws Exception{
		
		
		String userId = RedisKeys.REDIS_USER
				+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User user =new User();
		try {
			user = RedisUtil.getUser(userId);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		String shopId = this.bookService.getShopIdByUserId(user.getId().toString());
		
		
		List<String> tempList = null;
		List<Book> list = this.bookService.searchBookQRCode(request.getParameter("cateIds"), shopId);
		
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="+ new String("书籍二维码.xls".getBytes("GBK"), "iso8859-1"));
			OutputStream outstream = response.getOutputStream();
			jxl.write.WritableWorkbook wwb;
			wwb = Workbook.createWorkbook(outstream);

			jxl.write.WritableSheet ws = wwb.createSheet("书籍二维码", 1);
			WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat cFormat = new WritableCellFormat(font);
			// 第一行有30列
			ws.setColumnView(0, 30);
			// 第五行第一列
			ws.addCell(new jxl.write.Label(0, 0, "书籍库存编码", cFormat));


			WritableCellFormat cFormat1 = new WritableCellFormat(font);
			font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
			cFormat1 = new WritableCellFormat(font);
			ExcelBook excelBook = new ExcelBook();
			
			if(list != null)
			{
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						//System.out.println(list.get(i).getCodeInfo());
						ws.addCell(new jxl.write.Label(0, i+1 , list.get(i).getCodeInfo(), cFormat));
					}
				}
			}
			
			wwb.write();
			wwb.close();

			outstream.close();
		return null;
	}
	
	
	
//	@RequestMapping("searchBookQRCode")
//	public String searchBookQRCode(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
//		
//		
//		String userId = RedisKeys.REDIS_USER
//				+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
//		User user =new User();
//		try {
//			user = RedisUtil.getUser(userId);
//		} catch (Exception e1) {
//			
//			e1.printStackTrace();
//		}
//		String shopId = this.bookService.getShopIdByUserId(user.getId().toString());
//		
//		
//		List<String> tempList = null;
//		List<Book> list = this.bookService.searchBookQRCode(request.getParameter("cateIds"), shopId);
//		
//		
//		for (int i = 0; i < list.size(); i++) {
//			
//			tempList = extractMessageByRegular(list.get(i).getCodeInfo());
//			
//			list.get(i).setCodeInfo(list.get(1)+":"+list.get(0));
//		}
//		
//		
//		
//		request.setAttribute("infoList", list);
//		
//		return "bookShop/QRcodeManager";
//	}
	
	
	
	/** 
     * 使用正则表达式提取中括号中的内容 
     * @param msg 
     * @return  
     */
	  public static List<String> extractMessageByRegular(String msg){  
          
	        List<String> list=new ArrayList<String>();  
	        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");  
	        Matcher m = p.matcher(msg);  
	        while(m.find()){  
	            list.add(m.group().substring(1, m.group().length()-1));  
	        }  
	        return list;  
	    }  
	  
	
	public static void main(String[] args) {
		
		String s= "[D00060001][Brave 勇敢传说][D0006][测试书店]";
		
		//BookShopController bookShopController  = new BookShopController();
		List<String> list = extractMessageByRegular(s);  
        for (int i = 0; i < list.size(); i++) {  
            //System.out.println(i+"-->"+list.get(i));  
        }
        //System.out.println(list.get(1)+":"+list.get(0));  
	}

}
