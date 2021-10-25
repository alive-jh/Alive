package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.service.*;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import com.wechat.util.RedisKeys;
import com.wechat.util.WeChatUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@RequestMapping("/api")
public class MallApiController {
	
	@Resource
	private MallProductService mallProductService;
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private CategoryService categoryService;
	
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public MallProductService getMallProductService() {
		return mallProductService;
	}

	public void setMallProductService(MallProductService mallProductService) {
		this.mallProductService = mallProductService;
	}
	
	@Resource
	private CouponsService couponsService;

	
	public CouponsService getCouponsService() {
		return couponsService;
	}

	public void setCouponsService(CouponsService couponsService) {
		this.couponsService = couponsService;
	}
	@Resource
	private CommentService commentService;
	
	
	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Resource
	private IntegralService integralService;

	
	
	public IntegralService getIntegralService() {
		return integralService;
	}

	public void setIntegralService(IntegralService integralService) {
		this.integralService = integralService;
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
	public ProductCategoryService productCategoryService;


	public ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}


	public void setProductCategoryService(
			ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}
	
	@Resource
	private RedisService redisService;
	
	/**
	 * 获取所有分类数据接口
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	@RequestMapping("/mallCategoryView")
	public void mallCategoryView(HttpServletRequest request,HttpServletResponse response) {
		List cats=null;
		int currentId=-1;
		int parentId=0;
		if(!"".equals(request.getParameter("currentId"))  && request.getParameter("currentId")!= null){
			currentId=Integer.parseInt(request.getParameter("currentId"));
		}
		if(!"".equals(request.getParameter("parentId"))  && request.getParameter("parentId")!= null){
			parentId=Integer.parseInt(request.getParameter("parentId"));
		}
		cats=productCategoryService.getProductCategoryNolevel("product_category", parentId, 0, currentId, new ArrayList());
//		for (Iterator iterator = cats.iterator(); iterator.hasNext();) {
//			ProductCategory object = (ProductCategory) iterator.next();
//			//System.out.println(object.getMark()+"分类名称:"+object.getCat_name());
//		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("infoList", cats);
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * 根据分类Id查询商品信息集合
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping(value="/searchMallManagerView")
	public void searchMallManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{

		HashMap map = new HashMap();
		String page = "1"; 
		String rowsPerPage = "50";
		if(!"".equals(request.getParameter("page")) && request.getParameter("page")!= null)
		{
			page = request.getParameter("page");
		}
		if(!"".equals(request.getParameter("rowsPerPage")) && request.getParameter("rowsPerPage")!= null)
		{
			page = request.getParameter("rowsPerPage");
		}
		String sortType = "saleCount";
		if(!"".equals(request.getParameter("sortType")) && request.getParameter("sortType")!= null)
		{
			sortType = request.getParameter("sortType");
		}
		if (!"".equals(request.getParameter("catId"))
				&& request.getParameter("catId") != null) {
			// 父分类下的所有子分类
			int catId = Integer.parseInt(request.getParameter("catId"));
			String catIdChilds = mallProductService.getCategoryChildIds(
					"product_category", catId);
			String catIds = catId + "";
			if (!catIdChilds.equals("") && null != catIdChilds) {
				catIds = catId + "," + catIdChilds;
			}
			queryDto.setCatIds(catIds);
		}
		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);
		//String name = new String(queryDto.getName().getBytes(),"UTF-8");
		map.put("name", queryDto.getName());
		map.put("catIds", queryDto.getCatIds());
		request.getSession().removeAttribute("mallOrder");
		Page resultPage = this.mallProductService.searchMallProductList(map);
		List<Object[]> infoList= new ArrayList<Object[]>();
		if(null!=resultPage){
			infoList  = resultPage.getItems();	
		}
	
		String productIds = "";
		for (int i = 0; i < infoList.size(); i++)
		{
			productIds = productIds+ infoList.get(i)[0].toString()+",";
		}
		if(productIds.length()>0)
		{
			productIds = productIds.substring(0,productIds.length()-1);
			
		}
		List jsonList = new ArrayList();
		List<Object[]> labelList=new ArrayList<Object[]>();
		HashMap labelMap = new HashMap();
		if(!productIds.equals("")&&null!=productIds){
			labelList = this.mallProductService.searchProductLabel(productIds);
			for (int i = 0; i < labelList.size(); i++) {
				if(labelList.get(i)[0]!= null)
				{
					labelMap.put(labelList.get(i)[0].toString(), labelList.get(i));
				}
			}
		}
		
		Object[] tempObj = new Object[3];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			for(Object[] mallProduct : infoList) {
				JSONObject jobj = new JSONObject();
				
				jobj.put("id", mallProduct[0]);
				jobj.put("name", mallProduct[1]);
				jobj.put("createDate",sdf.format(mallProduct[2]));
				jobj.put("content", mallProduct[3]);
				jobj.put("logo1", mallProduct[5]);
				jobj.put("logo2", mallProduct[6]);
				jobj.put("logo3", mallProduct[7]);
				jobj.put("status", mallProduct[8]);
				jobj.put("mp3", mallProduct[10]);
				jobj.put("mp3Type", mallProduct[11]);
				jobj.put("info", mallProduct[13]);
				jobj.put("catId", mallProduct[12]);
				if(null==mallProduct[14]||mallProduct[14].equals("")){
					jobj.put("catName", "无");
				}else{
					jobj.put("catName", mallProduct[14]);
				}
				
				
				
				if(labelMap!= null )
				{
					
					if(labelMap.get(mallProduct[0].toString())!= null)
					{
						
						tempObj = (Object[])labelMap.get(mallProduct[0].toString());
						if(tempObj[1]!= null)
						{
							jobj.put("labelInfo", tempObj[1]);
						}
						else
						{
							jobj.put("labelInfo", "");
						}
						
						if(tempObj[2]!= null)
						{
							jobj.put("keyword", tempObj[2]);
						}
						else
						{
							jobj.put("keyword", "");
						}
						
					}
				}
				else
				{
					jobj.put("labelInfo", "");
					jobj.put("keyword", "");
				}
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			jsonObj.put("count", jsonList.size());
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
	}
	
	
	@RequestMapping("/mallMobileSpecialView")
	public void mallMobileSpecialView(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		{
			HashMap map = new HashMap();
			if (!"".equals(request.getParameter("page"))
					&& request.getParameter("page") != null) {
				queryDto.setPage(request.getParameter("page"));
			}
			if (!"".equals(request.getParameter("pageSize"))
					&& request.getParameter("pageSize") != null) {
				queryDto.setPageSize(request.getParameter("pageSize"));
			}
			if (!"".equals(request.getParameter("topicType"))
					&& request.getParameter("topicType") != null) {
				queryDto.setTopicType(request.getParameter("topicType"));
			}
			if (!"".equals(request.getParameter("status"))
					&& request.getParameter("status") != null) {
				queryDto.setStatus(request.getParameter("status"));
			}
			map.put("name", queryDto.getName());
			map.put("status", queryDto.getStatus());
			map.put("topicType", queryDto.getTopicType());
			map.put("page", queryDto.getPage());
			map.put("rowsPerPage", queryDto.getPageSize());

			Page resultPage = this.categoryService.searchSpecial(map);
			request.getSession().setAttribute("resultPage", resultPage);
			List jsonList = new ArrayList();
			List<Object[]> infoList = resultPage.getItems();

			// 封装成JSON显示对象
			JSONObject jsonObj = new JSONObject();
			if (infoList != null) {
				JSONArray AgentKeyWordInfo = new JSONArray();

				for (Object[] bookSpecial : infoList) {
					JSONObject jobj = new JSONObject();
					jobj.put("id", bookSpecial[0]);
					jobj.put("title", bookSpecial[2]);
					jobj.put("logo", bookSpecial[1]);
					jobj.put("sort", bookSpecial[4]);
					jobj.put("status", bookSpecial[3]);
					jobj.put("topicType", bookSpecial[6]);
					jobj.put("books", bookSpecial[8]);

					if ("0".equals(bookSpecial[3].toString())) {
						jobj.put("statusName", "显示");
					} else {
						jobj.put("statusName", "隐藏");
					}

					jsonList.add(jobj);
				}
				jsonObj.put("infoList", jsonList);

			}
			try {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().println(jsonObj.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	@RequestMapping("/mallManager")
	public void  mallManager(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
			HashMap map = new HashMap();
			String page = "1";
			String rowsPerPage = "20";
			if(!"".equals(request.getParameter("page")) && request.getParameter("page")!= null)
			{
				page = request.getParameter("page");
			}
			if(!"".equals(request.getParameter("rowsPerPage")) && request.getParameter("rowsPerPage")!= null)
			{
				rowsPerPage = request.getParameter("rowsPerPage");
			}
			String sortType = "saleCount";
			if(!"".equals(request.getParameter("sortType")) && request.getParameter("sortType")!= null)
			{
				sortType = request.getParameter("sortType");
			}
			map.put("sortType", sortType);
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			Page resultPage = this.mallProductService.searchMobileMallProduct(map);
			List<Object[]> infoList  = resultPage.getItems();
			request.setAttribute("infoList", infoList);
			List jsonList = new ArrayList();
			Object[] tempProduct = null;
			//封装成JSON显示对象
			JSONObject jsonObj = new JSONObject();
			if (infoList!=null) {
				JSONArray AgentKeyWordInfo = new JSONArray();
				for(Object[] mallProduct : infoList) {
					JSONObject jobj = new JSONObject();
					tempProduct = mallProduct;
					jobj.put("id", mallProduct[0]);
					jobj.put("name", mallProduct[1]);
					jobj.put("logo", Keys.STAT_NAME + "/wechat/wechatImages/mall/" + mallProduct[2]);
					jobj.put("info", mallProduct[4].toString().replaceAll(">", ":"));
					jobj.put("price", mallProduct[3]);
					if(mallProduct[5]!= null)
					{
						jobj.put("saleCount", mallProduct[5]);
					}
					else
					{
						jobj.put("saleCount", "0");
					}
					if(mallProduct[6]!= null)
					{
						jobj.put("viewCount", mallProduct[6]);
					}
					else
					{
						jobj.put("viewCount", "0");
					}
					
					
					
					jsonList.add(jobj);
				}
				jsonObj.put("infoList", jsonList);
				
			}
			request.setAttribute("jsonStr", jsonObj.toString());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonObj.toString());
			
	}
	
	
	
	@RequestMapping("/mallManagerView")
	public void  mallManagerView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
			Object[] productInfo = this.mallProductService.searchMallProductInfo(request.getParameter("mallProductId"));
			request.setAttribute("tempProduct", productInfo);
			JSONObject jobj = new JSONObject();
			jobj.put("id", productInfo[0]);
			jobj.put("name", productInfo[1]);
			jobj.put("logo1", productInfo[2]);
			jobj.put("logo2", productInfo[3]);
			jobj.put("logo3", productInfo[4]);
			
			jobj.put("content", productInfo[5]);
			jobj.put("price", productInfo[6]);
			if(productInfo[7] != null)
			{
				jobj.put("info", productInfo[7].toString().replaceAll(">", ":"));
			}
		
			if(!"".equals(productInfo[9]) && productInfo[9]!= null)
			{
				//System.out.println(productInfo[9]);
				jobj.put("mp3", Keys.STAT_NAME+"wechat/wechatImages/book/mp3/"+productInfo[9].toString().substring(0,productInfo[9].toString().length()-4));
			}
			else
			{
				jobj.put("mp3","");
			}
			
			jobj.put("mp3Type", productInfo[10]);
			//音频类型为课程时
			if("2".equals(productInfo[10].toString())){
				HashMap map = new HashMap();
				String page=request.getParameter("page");
				String pageSize=request.getParameter("pageSize");
				map.put("page", null!=page&&!"".equals(page)?page:"1");
				map.put("pageSize",null!=pageSize&&!"".equals(pageSize)?page:"10000");
				map.put("productId", request.getParameter("mallProductId"));
				Page resultPage = getDeviceService().searchCoursesByProductId(map);
				//子课程数据集合
				List dataList = resultPage.getItems();
				jobj.put("courses", dataList);
			}
			
			if(productInfo[8]!= null)
			{
				jobj.put("saleCount", productInfo[8]);
			}
			else
			{
				jobj.put("saleCount", "0");
			}
			if(productInfo[11]!= null)
			{
				jobj.put("viewCount", productInfo[11]);
			}
			else
			{
				jobj.put("viewCount", "0");
			}
			
			SaleProductLog saleProductLog= new SaleProductLog();
			String[] tempStr =  null;
			List<SaleProductLog> saleList = new ArrayList();
			if(productInfo[12]!= null)
			{
				
				tempStr = productInfo[12].toString().split(",");
				for (int i = 0; i < tempStr.length; i++) {
					
					saleProductLog = new SaleProductLog();
					saleProductLog.setMemberName( tempStr[i].toString().split(">")[0].toString().substring(0,1)+"***");
					saleProductLog.setCreateDate(tempStr[i].toString().split(">")[2].toString().substring(5,16));
					saleProductLog.setCount(tempStr[i].toString().split(">")[1]);
					
					saleList.add(saleProductLog);
					
				}
				jobj.put("saleProductList", saleList);
				
			}
			else
			{
				jobj.put("saleProductList", "");
			}
			
			
			List<Object[]> labelList = this.mallProductService.searchProductLabel(productInfo[0].toString());
			if(labelList.size()>0)
			{
				if(labelList.get(0)[2]!= null)
				{
					jobj.put("keyword", labelList.get(0)[2].toString());
				}
				
			}
			else
			{
				jobj.put("keyword", "");
			}
			
			List jsonList = new ArrayList();
			jsonList.add(jobj);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("infoList", jsonList);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonObj.toString());
		
		
			
	}
	
	
	@RequestMapping(value="/shoppingCartManager")
	public void shoppingCartManager(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		
		
		List<ShoppingCart> infoList  = this.mallProductService.searchShoppingCart(request.getParameter("memberId"));
		
		for (int i = 0; i < infoList.size(); i++) {
			
			infoList.get(i).setProductImg(Keys.STAT_NAME + "/wechat/wechatImages/mall/" + infoList.get(i).getProductImg());
		}
		
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {

			jsonObj.put("infoList", infoList);
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		
	}
	
	
	@RequestMapping(value="/saveShoppingCart")
	public String saveShoppingCart(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		String jsonStr = "{\"status\":\"ok\"}";
		try {
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setPrice(new Double(request.getParameter("price")));
			shoppingCart.setCount(new Integer(request.getParameter("count")));
			shoppingCart.setSpecifications(request.getParameter("specifications"));
			shoppingCart.setMemberId(new Integer(request.getParameter("memberId")));
			shoppingCart.setProductId(new Integer(request.getParameter("productId")));
			shoppingCart.setProductImg(request.getParameter("productImg"));
			shoppingCart.setProductName(request.getParameter("productName"));
			
			List<ShoppingCart> list = this.mallProductService.searchShoppingCart(request.getParameter("memberId"));
			HashMap map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				
				map.put(list.get(i).getProductId()+"-"+list.get(i).getSpecifications(),list.get(i).getId());
			}
			
			if(map.get(shoppingCart.getProductId()+"-"+shoppingCart.getSpecifications())== null)
			{
				this.mallProductService.saveShoppingCart(shoppingCart);
			}
			else
			{
				this.mallProductService.updateCount(map.get(shoppingCart.getProductId()+"-"+shoppingCart.getSpecifications()).toString(), shoppingCart.getCount().toString());
			}
			} catch (Exception e) {
				
				jsonStr = "{\"status\":\"error\"}";
			}
		
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().println(jsonStr);
		return null;
	}
	
	
	@RequestMapping(value="/deleteShoppingCart")
	public void deleteShoppingCart(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		String jsonStr = "{\"status\":\"ok\"}";
		
		try {
			
			this.mallProductService.deleteShoppingCart(request.getParameter("shoppingCartId"));
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}
	
	@RequestMapping(value="/addressManager")
	public void addressManager(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		List infoList = this.mallProductService.searchUserAddress(request.getParameter("memberId"));
		
		
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {

			jsonObj.put("infoList", infoList);
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		
	}
	/*
	 * 
	 * 
	 * 获取用户地址列表
	 * 
	 * */
	@RequestMapping(value="/getUserAddress")
	public void getUserAddress(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		UserAddress userAddress = new UserAddress();
		userAddress = this.mallProductService.getUserAddress(new Integer(request.getParameter("userAddressId")));
		
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("infoList", userAddress);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		
	}
	
	/*
	 * 
	 * 
	 * 保存用户地址
	 * 
	 * */
	
	@RequestMapping(value="/saveAddress")
	public void saveAddress(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		String jsonStr = "{\"status\":\"ok\"}";
		try {
			
			UserAddress userAddress = new UserAddress();
			JSONObject jsonObject = WeChatUtil.getLngAndLat(userAddress.getAddress());
			
			if(!"".equals(request.getParameter("userAddressId")) && request.getParameter("userAddressId")!= null)
			{
				userAddress.setId(new Integer(request.getParameter("userAddressId")));
			}
			
			userAddress.setUserName(request.getParameter("userName"));
			userAddress.setAddress(request.getParameter("address"));
			userAddress.setArea(request.getParameter("area"));
			userAddress.setMemberId(new Integer(request.getParameter("memberId")));
			userAddress.setMobile(request.getParameter("mobile"));
			userAddress.setStatus(new Integer(request.getParameter("status")));
			if(jsonObject.toString().lastIndexOf("Error") ==-1)
			{
				userAddress.setLat(jsonObject.getJSONObject("result").getJSONObject("location").getString("lat"));
				userAddress.setLng(jsonObject.getJSONObject("result").getJSONObject("location").getString("lng"));
				
			}
			this.mallProductService.saveUserAddress(userAddress);
			List list = this.mallProductService.searchUserAddressStatus(userAddress.getMemberId().toString());
			if(list.size() ==1)
			{
				this.mallProductService.updateAddressStatus(userAddress.getId().toString(), userAddress.getMemberId().toString());//修改默认收货地址
			}
			if(userAddress.getStatus() ==0)
			{
				this.mallProductService.updateAddressStatus(userAddress.getId().toString(), userAddress.getMemberId().toString());//修改默认收货地址
			}
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		
		
	}
	
	@RequestMapping(value="/deleteAddress")
	public void deleteAddress(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		String jsonStr = "{\"status\":\"ok\"}";
		
		try {
			
			this.mallProductService.deleteUserAddress(request.getParameter("userAddressId"));
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}
	
	
	
	
	
	@RequestMapping(value = "/saveOrder")
	public String saveOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		 JSONObject jsonObject  = WeChatUtil.httpRequest(url, data);
//		 List<WeChatmemberGroup> groupList = new ArrayList();
//		 groupList = jsonObject.getJSONArray("groups");
		String jsonStr = "{\"status\":\"error\"}";
		if (!"".equals(request.getParameter("data")) && request.getParameter("data")!= null){
			try {
				List tempList = new ArrayList();
				//String dataStr = "{\"addressId\":36,\"couponsId\":0,\"couponsMoney\":0,\"createDate\":null,\"express\":\"\",\"expressNumber\":\"\",\"freight\":10,\"id\":0,\"integral\":4,\"memberId\":8,\"operatorId\":0,\"orderNumber\":\"20160306020\",\"productCount\":0,\"productList\":[{\"commentStatus\":0,\"count\":1,\"createDate\":null,\"id\":0,\"orderId\":0,\"price\":12,\"productId\":2,\"productImg\":\"0/1442904825201.png\",\"productName\":\"led灯泡 新5W E27螺口电灯泡\",\"specifications\":\"5W\"},{\"commentStatus\":0,\"count\":1,\"createDate\":null,\"id\":0,\"orderId\":0,\"price\":12,\"productId\":2,\"productImg\":\"0/1442904825201.png\",\"productName\":\"led灯泡 新5W E27螺口电灯泡\",\"specifications\":\"5W\"}],\"remarks\":\"\",\"status\":1,\"statusBlank\":1,\"totalPrice\":22}";
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				//System.out.println("createDate = "+format.format(new Date())+" data = "+request.getParameter("data"));
				
				String dataStr = request.getParameter("data");
				JSONObject jsonObject =  JSONObject.fromObject(dataStr);
				tempList = jsonObject.getJSONArray("productList");
				String memberId = jsonObject.getString("memberId");
				String orderNumber = jsonObject.getString("orderNumber");
				String addressId = jsonObject.getString("addressId");
				String shoppingCartId = jsonObject.getString("shoppingCartId");
				List<MallOrderProduct> productList = new ArrayList();
				MallOrderProduct mallOrderProduct = new MallOrderProduct();
				
				for (int i = 0; i < tempList.size(); i++) {
					
					mallOrderProduct = (MallOrderProduct)JSONObject.toBean(JSONObject.fromObject(tempList.get(i).toString()),MallOrderProduct.class);
					
					productList.add(mallOrderProduct);
				}
				
				MallOrder mallOrder = new MallOrder();
				mallOrder.setOrderNumber(orderNumber);
				mallOrder.setMemberId(new Integer(memberId));
				mallOrder.setAddressId(new Integer(addressId));
				mallOrder.setCreateDate(new Date());
				mallOrder.setRemarks(request.getParameter("remarks"));
				mallOrderProduct = new MallOrderProduct();
				Double tempTotalPrice = new Double(0);
				for (int i = 0; i < productList.size(); i++) {
	
					mallOrderProduct = productList.get(i);
					tempTotalPrice = tempTotalPrice+ (mallOrderProduct.getPrice() * mallOrderProduct.getCount());
				}
				
				mallOrder.setTotalPrice(tempTotalPrice);
				
				mallOrder.setFreight(new Double(10));
					
				
				if(mallOrder.getIntegral()>0)
				{
					mallOrder.setTotalPrice(mallOrder.getTotalPrice()-mallOrder.getIntegral());
				}
				
				if(jsonObject.getString("couponsId")!= null )
				{
					mallOrder.setCouponsId(new Integer(jsonObject.getString("couponsId")));
				}
				this.mallProductService.saveOrder(mallOrder);
				for (int i = 0; i < productList.size(); i++) {
	
					productList.get(i).setOrderId(mallOrder.getId());
					productList.get(i).setCreateDate(new Date());
					this.mallProductService.saveMallOrderProduct(productList.get(i));	//保存数据
					this.mallProductService.updateProductCount(productList.get(i));		//更新库存
				}
				if (mallOrder.getCouponsId() != 0) {
					
					this.couponsService.updateCouponsInfoStatus(mallOrder.getCouponsId().toString());
				}
				request.getSession().removeAttribute("mallOrder");
				
				if (!"".equals(shoppingCartId)&&!"0".equals(shoppingCartId)&& shoppingCartId != null) {
					
					String[] ids = shoppingCartId.split(",");
					
					if(ids.length ==1)
					{
						this.mallProductService.deleteShoppingCart(shoppingCartId);
					}
					else
					{
						for (int i = 0; i < ids.length; i++) {
							
							this.mallProductService.deleteShoppingCart(ids[i].toString());
						}
					}
					
				}
				
				Integral integral =  new Integral();
				
				
				if("1".equals(request.getParameter("integralStatus")))
				{
					integral.setCreateDate(new Date());
					integral.setMemberId(mallOrder.getMemberId());
					integral.setStatus(1);
					integral.setFraction(-mallOrder.getIntegral()*10);
					integral.setTypeId(4);
					this.integralService.saveIntegral(integral);// 消费积分
				}
				
				
				jsonStr = "{\"status\":\"ok\"}";
			} catch (Exception e) {
				
				jsonStr = "{\"status\":\"error\"}";
			}
//			Member member = new Member();
//			member.setId(mallOrder.getMemberId());
//			member  = this.memberService.getMember(member);
			
//			NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
//			String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
//			noticeInfo.setAccessToKen(accessToken);
//			noticeInfo.setOpenId(member.getOpenId());
//			noticeInfo.setMemberId(member.getId().toString());
//			noticeInfo.setFirst("您的订单已提交成功，我们将尽快为您配送。");
//			noticeInfo.setKeyword1(mallOrder.getOrderNumber());
//			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			noticeInfo.setKeyword2(format.format(mallOrder.getCreateDate()));
//			noticeInfo.setKeyword3(mallOrder.getTotalPrice().toString()+"0");
//			
//			noticeInfo.setRemark("若有疑问请致电客服中心:"+Keys.CUSTOMER_MOBLIE);
//			noticeInfo.setUrl("http://m.kuaidi100.com/index_all.html?type="+request.getParameter("danhao")+"&postid="+request.getParameter("danhao")+"#result");
//			WeChatUtil.sendOrderNotice(noticeInfo);	//接受返回的json格式数据		
			
		}
		else
		{
			
			jsonStr = "{\"status\":\"error\"}";
			

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr.toString());
		return null;

	}
	
	
	@RequestMapping(value="/getCoupons")
	public void getCoupons(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
		
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
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Object[]> infoList = this.couponsService.searchCouponsList(map);
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			
			for (int i = 0; i < infoList.size(); i++) {
				
				jsonObj = new JSONObject();
				jsonObj.put("id", infoList.get(i)[0]);
				jsonObj.put("title", infoList.get(i)[1]);
				jsonObj.put("price", infoList.get(i)[2]);
				jsonObj.put("money", infoList.get(i)[3]);
				jsonObj.put("type", infoList.get(i)[4]);
				jsonObj.put("endDate", infoList.get(i)[5].toString().substring(0, 10));
				jsonObj.put("status", infoList.get(i)[6]);
				jsonList.add(jsonObj);
			}
			jsonObject.accumulate("infoList", jsonList);
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObject.toString());
	}
	
	
	
	
	@RequestMapping("/seaechMall")
	public void seaechMall(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		
			request.setAttribute("memberId", request.getParameter("memberId"));
			HashMap map = new HashMap();
			String page = "1"; 
			String rowsPerPage = "50";
			if(!"".equals(request.getParameter("page")) && request.getParameter("page")!= null)
			{
				page = request.getParameter("page");
			}
			if(!"".equals(request.getParameter("rowsPerPage")) && request.getParameter("rowsPerPage")!= null)
			{
				rowsPerPage = request.getParameter("rowsPerPage");
			}
			String sortType = "saleCount";
			if(!"".equals(request.getParameter("sortType")) && request.getParameter("sortType")!= null)
			{
				sortType = request.getParameter("sortType");
			}
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			map.put("labelId", request.getParameter("labelId"));
			map.put("name", request.getParameter("name"));
			map.put("catId", request.getParameter("catId"));
			
			Page resultPage = this.mallProductService.searchMobileMallProductByApp(map);
			List<Object[]> infoList  = resultPage.getItems();
			List jsonList = new ArrayList();
			JSONObject jsonObject = new JSONObject();
			JSONObject jobj = new JSONObject();
			if (infoList!=null) {
				
				for (int i = 0; i < infoList.size(); i++) {
					
					jobj = new JSONObject();
					jobj.put("id", infoList.get(i)[0]);
					jobj.put("name", infoList.get(i)[1]);
					if (infoList.get(i)[2].toString().startsWith("http")){
						jobj.put("logo", infoList.get(i)[2]);
					}else {
						jobj.put("logo", Keys.STAT_NAME + "/wechat/wechatImages/mall/" + infoList.get(i)[2]);
					}
					if(infoList.get(i)[4]!= null)
					{
						jobj.put("info", infoList.get(i)[4].toString().replaceAll(">", ":"));
					}
					else
					{
						jobj.put("info", "");
					}
					
					jobj.put("price", infoList.get(i)[3]);
					jobj.put("mp3Type", infoList.get(i)[9]);
					if(infoList.get(i)[5]!= null)
					{
						jobj.put("saleCount", infoList.get(i)[5]);
					}
					else
					{
						jobj.put("saleCount", "0");
					}
					if(infoList.get(i)[6]!= null)
					{
						jobj.put("viewCount", infoList.get(i)[6]);
					}
					else
					{
						jobj.put("viewCount", "0");
					}
					jsonList.add(jobj);
				}
				jsonObject.accumulate("infoList", jsonList);
				jsonObject.put("pageCount", resultPage.getTotalPageCount());
				jsonObject.put("totalCount", resultPage.getTotalCount());
				
			}
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonObject.toString());
		
			
	}
	
	
	
	@RequestMapping(value="/saveComment")
	public void saveComment(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
		
		String jsonStr = "{\"status\":\"ok\"}";
		
			try {
					
		 	Comment comment = new Comment();
		    comment.setMemberId(new Integer(request.getParameter("memberId")));	
		    comment.setProductId(new Integer(request.getParameter("productId")));
		    comment.setCreateDate(new Date());
		    comment.setContent(request.getParameter("content"));
		    comment.setType(new Integer(0));
		    String star = request.getParameter("starValue");
		    comment.setStar(new Integer(star));
		   
		    this.commentService.saveComment(comment);
		    this.commentService.updateProductCommentStatus(request.getParameter("orderId"));
		    CommentImg commentImg = new CommentImg();
		    String imgs = request.getParameter("imgs");
		    if(!"".equals(imgs) && imgs!= null )
		    {
		    	String[] tempStr = imgs.split(",");
		    	
		    	for (int i = 0; i < tempStr.length; i++) {
		    		
		    		  commentImg = new CommentImg();
		    		  if(!"".equals(tempStr[i]) && tempStr[i]!= null )
		    		  {
		    			  commentImg.setImg(tempStr[i]);
				          commentImg.setCommentId(comment.getId());
				          this.commentService.saveCommentImg(commentImg);
		    		  }
			         
				}
		    }
		    
			} catch (Exception e) {
				
				jsonStr = "{\"status\":\"error\"}";
			}
		    
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr.toString());
	}
	
	
	
	
	@RequestMapping(value="/getComment")
	public void getComment(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		
		String jsonStr = "{\"status\":\"ok\"}";
		JSONObject jsonObject = new JSONObject();
			try {
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
				map.put("productId", request.getParameter("productId"));
				Page resultPage = this.commentService.searchComment(map);
				CommentInfo commentInfo  = null;
				List tempList = new ArrayList();
				String[] tempStr = null;
				Object[] tempObj = null;
				List commentList = new ArrayList();
				for (int i = 0; i< resultPage.getItems().size(); i++) {
					
					tempObj = (Object[])resultPage.getItems().get(i);
					commentInfo = new CommentInfo();
					commentInfo.setContent(tempObj[2].toString());
					commentInfo.setCreateDate(tempObj[3].toString().substring(0,16));
					commentInfo.setMemberName(tempObj[1].toString().substring(0,1)+"****"+tempObj[1].toString().substring(tempObj[1].toString().length()-1,tempObj[1].toString().length()));
					commentInfo.setProductId(new Integer(tempObj[0].toString()));
					commentInfo.setStar(new Integer(tempObj[4].toString()));
					if(tempObj[5]!= null)
					{
						tempStr = tempObj[5].toString().split(",");
						tempList = new ArrayList();
						for (int j = 0; j < tempStr.length; j++) {
							
							tempList.add(Keys.STAT_NAME+"/wechat/wechatImages/mallComment/"+tempStr[j].toString());
							
						}
						commentInfo.setImgList(tempList);;
					}
					commentList.add(commentInfo);
				}
				
				jsonObject.put("infoList", commentList);
				jsonStr = jsonObject.toString();
		    
			} catch (Exception e) {
				
				jsonStr = "{\"status\":\"error\"}";
			}
		    
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr.toString());
	}
	

	@RequestMapping(value = "/getMallOrderInfo")
	public String getMallOrderInfo(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();

		map.put("memberId", request.getParameter("memberId"));

		MallOrder mallOrder = new MallOrder();

		List<MallOrder> memberList = this.mallProductService
				.searchMemberOrder(map);
		Integer count = new Integer(0);
		Double totalPrice = new Double(0);
		for (int i = 0; i < memberList.size(); i++) {

			mallOrder = memberList.get(i);

			memberList.get(i).setProductList(
					this.mallProductService.searchMallOrderProduct(memberList
							.get(i).getId().toString()));
			count = new Integer(0);
			for (int j = 0; j < memberList.get(i).getProductList().size(); j++) {

				count = count+ memberList.get(i).getProductList().get(j).getCount();
				totalPrice = totalPrice +memberList.get(i).getProductList().get(j).getCount()*memberList.get(i).getProductList().get(j).getPrice();
			}

			memberList.get(i).setProductCount(count);

		}
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		List jsonList = new ArrayList();
		for (int i = 0; i < memberList.size(); i++) {
			
			jsonObject = new JSONObject();
			jsonObject.put("orderId", memberList.get(i).getId());
			jsonObject.put("orderNumber", memberList.get(i).getOrderNumber());
			jsonObject.put("express", memberList.get(i).getExpress());
			jsonObject.put("expressNumber", memberList.get(i).getExpressNumber());
			jsonObject.put("totalPrice", memberList.get(i).getTotalPrice());
			jsonObject.put("productCount", memberList.get(i).getProductCount());
			jsonObject.put("status",  memberList.get(i).getStatus());
			//订单状态,1已付款,2已发货,3已确认收货.4已评价,5退款中,6已退款
			if( memberList.get(i).getStatus() ==1)
			{
				jsonObject.put("statusName",  "已付款");
			}
			if( memberList.get(i).getStatus() ==2)
			{
				jsonObject.put("statusName",  "已发货");
			}
			if( memberList.get(i).getStatus() ==3)
			{
				jsonObject.put("statusName",  "已确认收货");
			}
			if( memberList.get(i).getStatus() ==4)
			{
				jsonObject.put("statusName",  "已评价");
			}
			if( memberList.get(i).getStatus() ==5)
			{
				jsonObject.put("statusName",  "退款中");
			}
			if( memberList.get(i).getStatus() ==6)
			{
				jsonObject.put("statusName",  "已退款");
			}
			for (int j = 0; j < memberList.get(i).getProductList().size(); j++) {
				
				memberList.get(i).getProductList().get(j).setCreateDate(null);
			}
			jsonObject.put("productList",  memberList.get(i).getProductList());
			
			jsonList.add(jsonObject);
		}
		
		jsonObj.put("infoList", jsonList);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return null;
	}
	
	@RequestMapping("/updateOrderStatus")
	public void updateOrderStatus(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		
		
		String jsonStr = "{\"status\":\"ok\"}";
		
		try {
			this.mallProductService.updateOrderStatus(request.getParameter("orderId"), "3");
			MallOrder mallOrder = this.mallProductService.getMallOrder(request.getParameter("orderId"));
			DecimalFormat df = new DecimalFormat("###");
			Integral integral =  new Integral();
			integral.setCreateDate(new Date());
			integral.setMemberId(mallOrder.getMemberId());
			integral.setStatus(0);
			integral.setFraction(new Integer(df.format(mallOrder.getTotalPrice())));
			integral.setTypeId(3);
			this.integralService.saveIntegral(integral);//完成订单增加积分
			
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		
	}
	
	
	
	
	@RequestMapping("/saveMemberKeyword")
	public void saveMemberKeyword(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		
		
		String jsonStr = "{\"status\":\"ok\"}";
		
		try {
			MemberKeyword memberKeyword = new MemberKeyword();
			memberKeyword.setKeyword(request.getParameter("keyword"));
			memberKeyword.setMemberId(new Integer(request.getParameter("memberId")));
			this.memberService.saveMemberKeyword(memberKeyword);
			
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		
	}
	
	@RequestMapping("/deleteMemberKeyword")
	public void deleteMemberKeyword(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		
		
		String jsonStr = "{\"status\":\"ok\"}";
		
		try {
			this.memberService.deleteMemberKeyword(request.getParameter("memberId"));
			
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		
	}
	
	
	@RequestMapping("/searchMemberKeyword")
	public void searchMemberKeyword(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		
		
		
		List infoList = this.memberService.searchMemberKerword(request.getParameter("memberId"));
			

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("infoList", infoList);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
	}
	
	
	
	@RequestMapping(value="/searchProductCollection")
	public void searchProductCollection(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		List list = this.mallProductService.searchProductCollection(request.getParameter("memberId"), request.getParameter("productId"));
		
		
		String jsonStr = "{\"status\":\"0\"}";
		
		if(list.size()>0)
		{
			jsonStr = "{\"status\":\"1\"}";
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr.toString());
		
		
	}
	
	
	
	@RequestMapping(value="/searchProductMp3")
	public void searchProductMp3(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		
		List list = this.memberService.searchMemberMp3ByMemberId(request.getParameter("memberId"), request.getParameter("productId"));
		
		
		String jsonStr = "{\"status\":\"0\"}";
		
		if(list.size()>0)
		{
			jsonStr = "{\"status\":\"1\"}";
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr.toString());
		
		
	}
	

	@RequestMapping("/searchMallLabel")
	public void searchMallLabel(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
			
			HashMap map = new HashMap();
			String page = "1"; 
			String rowsPerPage = "50";
			if(!"".equals(request.getParameter("page")) && request.getParameter("page")!= null)
			{
				page = request.getParameter("page");
			}
			if(!"".equals(request.getParameter("rowsPerPage")) && request.getParameter("rowsPerPage")!= null)
			{
				rowsPerPage = request.getParameter("rowsPerPage");
			}
			
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			
			
			Page resultPage = this.mallProductService.searchMallLabel(map);
			List<Object[]> infoList  = resultPage.getItems();
			List jsonList = new ArrayList();
			JSONObject jsonObject = new JSONObject();
			JSONObject jobj = new JSONObject();
			if (infoList!=null) {
				
				for (int i = 0; i < infoList.size(); i++) {
					
					jobj = new JSONObject();
					jobj.put("id", infoList.get(i)[2]);
					jobj.put("name", infoList.get(i)[1]);
					
					jsonList.add(jobj);
				}
				jsonObject.accumulate("infoList", jsonList);
				jsonObject.put("pageCount", resultPage.getTotalPageCount());
				jsonObject.put("totalCount", resultPage.getTotalCount());
				
			}
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonObject.toString());
		
	}
	
	
	
	
	@RequestMapping("/mallBannerManager")
	public void mallBannerManager(HttpServletRequest request,QueryDto queryDto,HttpServletResponse response) throws Exception{
		

		
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
	
		map.put("status", "0");
		Page resultPage = this.mallProductService.searchMallBanner(map);
		
		List<MallBanner> list =  resultPage.getItems();
		for (int i = 0; i < list.size(); i++) {
			
			list.get(i).setBanner(Keys.STAT_NAME+"wechat/wechatImages/mall/"+list.get(i).getBanner());
		}
		map = new HashMap();
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
	
		jsonObj.put("infoList", list);
			
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
	}
	
	
	
	
	

//	@RequestMapping("/mallMobileIndex1")
//	public void mallMobileIndex1(HttpServletRequest request,HttpServletResponse response)
//			throws Exception {
//
//		request.setAttribute("memberId", request.getParameter("memberId"));
//
//		request.getSession().removeAttribute("mallOrder");
//
//		HashMap map = new HashMap();
//		map.put("page", "1");
//		
//		map.put("android", request.getParameter("android"));
//		map.put("ios", request.getParameter("ios"));
//		map.put("wechat", request.getParameter("wechat"));
//
//		
//		List<Object[]> indexList = this.mallProductService.searchMallLabelByIndex(map);//查询首页标签集合
//		String labelIds = "";
//		for (int i = 0; i < indexList.size(); i++) {//组装标签ids
//			
//			
//			labelIds = labelIds+indexList.get(i)[2];
//			if(i +1 != indexList.size())
//			{
//				labelIds = labelIds + ",";
//			}
//		}
//
//		List<Object[]> labelInfoList = this.mallProductService.searchMallProdyctByLabel(labelIds, "8");//查询标签集合数据
//		
//		HashMap infoMap = new HashMap();
//		for (int i = 0; i < labelInfoList.size(); i++) {
//			
//			infoMap.put(labelInfoList.get(i)[0].toString(), labelInfoList.get(i)[1]);
//			
//			
//		}
//		List mallList = new ArrayList();
//		MallInfoList mallInfoList = new MallInfoList();
//		List tempList = new ArrayList();
//
//		JSONObject jsonObj = new JSONObject();
//		JSONObject labelObj = new JSONObject();
//		Object[] tempProduct = new Object[10];
//		List jsonList = new ArrayList();
//		List<Object[]> objList = new ArrayList();
//		
//		String[] tempStr = null;
//		
//		for (int i = 0; i < indexList.size(); i++) {
//			
//			labelObj = new JSONObject();
//			labelObj.put("labelName", indexList.get(i)[1].toString());
//			labelObj.put("labelId", indexList.get(i)[2].toString());
//			if(infoMap.get(indexList.get(i)[2].toString()) != null)
//			{
//				
//				
//				tempStr = infoMap.get(indexList.get(i)[2].toString()).toString().split("@");//分隔商品
//				
//				JSONArray AgentKeyWordInfo = new JSONArray();
//				jsonList = new ArrayList();
//				for (int j = 0; j < tempStr.length; j++) {
//					
//					 tempProduct = new Object[10];
//					 tempProduct = tempStr[j].toString().split("#");//分隔商品属性
//					
//					 if(tempProduct!= null)
//					 {
//						 JSONObject jobj = new JSONObject();
//							
//							jobj.put("id", tempProduct[0]);
//							jobj.put("name", tempProduct[1]);
//							jobj.put("logo", tempProduct[2]);
//							
//							
//							if(tempProduct[4]!= null)
//							{
//								jobj.put("info", tempProduct[4].toString().replaceAll(">", ":"));
//							}
//							else
//							{
//								jobj.put("info", "");
//							}
//							
//							jobj.put("price", tempProduct[3]);
//							jsonList.add(jobj);
//					 }
//					
//					 
//				}
//				
//				
//			}
//
//			labelObj.put("productList", jsonList);
//			tempList.add(labelObj);
//			
//		}
//		
//		jsonObj.put("labelList", tempList);
//
////		List keywordList = this.memberService.searchMemberKerword(request
////				.getParameter("memberId"));
////		jsonObj.put("keywordList", keywordList);
//
//	
//
//		List<MallBanner> list = this.mallProductService.searchMallBanner();
//		for (int i = 0; i < list.size(); i++) {
//
//			list.get(i).setBanner(
//					Keys.STAT_NAME + "wechat/wechatImages/mall/"
//							+ list.get(i).getBanner());
//			if (list.get(i).getUrlType() != 0) {
//				if (!"".equals(request.getParameter("memberId")) && request.getParameter("memberId") != null) {
//					
//					list.get(i).setUrl(Keys.STAT_NAME+ "wechat/mall/mallMobileManager?mallProductId="+ list.get(i).getUrl() + "&memberId="+ request.getParameter("memberId"));
//				} else 
//				{
//					list.get(i).setUrl(Keys.STAT_NAME+ "wechat/mall/mallMobileManager?mallProductId="+ list.get(i).getUrl());
//				}
//				
//
//			}
//		}
//
//		jsonObj.put("bannerList", list);
//		response.setContentType("application/json;charset=UTF-8");
//		response.getWriter().println(jsonObj.toString());
//
//	}
//	
	
	
	
	
	@RequestMapping("changeMallOrderStatus")
	public void changeMallOrderStatus(HttpServletRequest request, HttpServletResponse response){
		PrintWriter printWriter=null;
		try {
			JSONObject result=new JSONObject();
			printWriter=response.getWriter();
			int status =Integer.parseInt(request.getParameter("status"));
			String orderId=request.getParameter("orderId");
			MallOrder mallOrder=new MallOrder();
			mallOrder=mallProductService.getMallOrder(orderId);
			mallOrder.setStatus(status);
			result.put("success", 1);
			JSONObject data=new JSONObject();
			data.put("mallOrder", mallOrder);
			result.put("data", data);
			printWriter.print(result.toString());
			
			
		} catch (Exception e) {
			JSONObject result=new JSONObject();
			result.put("success", 0);
			JSONObject error=new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			printWriter.print(result.toString());
		}
		
	}
	
	
	
	/**
	 * 课程,专题,标签集合版首页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/mallMobileIndex")
	public void mallMobileIndex(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		request.setAttribute("memberId", request.getParameter("memberId"));

		request.getSession().removeAttribute("mallOrder");

		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject result=new JSONObject();
		try {
			
		HashMap map = new HashMap();
		
		List<AppIndex> appIndexList = this.mallProductService.searchAppIndex();
		
		String categoryIds = "";
		String labelIds = "";
		
		for (int i = 0; i < appIndexList.size(); i++) {
			
			if(appIndexList.get(i).getType() ==1)
			{
				categoryIds = categoryIds  + appIndexList.get(i).getInfoId().toString()+ ",";
			}
			
			if(appIndexList.get(i).getType() ==0)
			{
				labelIds = labelIds  + appIndexList.get(i).getInfoId().toString()+ ",";
			}
			
		}
		HashMap categoryMap = new HashMap();
		List<Object[]> categoryList = new ArrayList();
		if(!"".equals(categoryIds) && categoryIds != null )
		{
			categoryIds = categoryIds.substring(0,categoryIds.length()-1);
			categoryList = this.mallProductService.searchMallProdyctByCateGory(categoryIds);
			for (int i = 0; i < categoryList.size(); i++) {
				categoryMap.put(categoryList.get(i)[0].toString(),categoryList.get(i));
			}
		}
		
		HashMap labelMap = new HashMap();
		List<Object[]> labelInfoList = new ArrayList();
		if(!"".equals(labelIds) && labelIds != null)
		{
			labelIds = labelIds.substring(0,labelIds.length()-1);
			labelInfoList = this.mallProductService.searchMallProdyctByLabel(labelIds, "8");//查询标签集合数据
			for (int i = 0; i < labelInfoList.size(); i++) {
				
				labelMap.put(labelInfoList.get(i)[0].toString(),labelInfoList.get(i));
			}
		}
		
		
		Object[] tempLabelObj =  new Object[10];
		
		Object[] tempCategoryObj =  new Object[10];
		
		
		JSONObject labelObj = new JSONObject();
		Object[] tempProduct = new Object[10];
		List jsonList = new ArrayList();
		
		List<Object[]> objList = new ArrayList();
		
		String[] tempStr = null;
		List infoList = new ArrayList();
		AppIndexList tempAppIndexList = new AppIndexList();
		JSONObject categoryJson = new JSONObject();
		
		JSONObject appIndexObj = new JSONObject();
		
		List<ProductCategory> tempIndexList = new ArrayList();
		
		List tempList = new ArrayList();
		//分隔符://⊥ ∪∩∫⊙
		for (int i = 0; i < appIndexList.size(); i++) {
			
			appIndexObj = new JSONObject();
			tempAppIndexList = new AppIndexList();
			tempAppIndexList.setTitle(appIndexList.get(i).getTitle());
			tempAppIndexList.setType(appIndexList.get(i).getType());
			categoryJson = new JSONObject();
			infoList = new ArrayList();
			if(appIndexList.get(i).getType() == 0)
			{
				if(labelMap.get(appIndexList.get(i).getInfoId().toString()) != null)
				{
					tempLabelObj = (Object[])labelMap.get(appIndexList.get(i).getInfoId().toString());
					tempStr = tempLabelObj[1].toString().split("@");//分隔集合
					jsonList = new ArrayList();
					infoList = new ArrayList();
					categoryJson = new JSONObject();
					categoryJson.put("listName", tempLabelObj[2].toString());
					categoryJson.put("id", tempLabelObj[0].toString());
					for (int j = 0; j < tempStr.length && j<8; j++) {
						
						 tempProduct = new Object[10];
						 tempProduct = tempStr[j].toString().split("#");//分隔商品属性
						
						 if(tempProduct!= null)
						 {
							 	JSONObject jobj = new JSONObject();
								
								jobj.put("id", tempProduct[0]);
								jobj.put("name", tempProduct[1]);
								jobj.put("logo", Keys.STAT_NAME+"/wechat/wechatImages/mall/"+tempProduct[2]);
								
								if(tempProduct[4]!= null)
								{
									jobj.put("info", tempProduct[4].toString().replaceAll(">", ":"));
								}
								else
								{
									jobj.put("info", "");
								}
								
								jobj.put("price", tempProduct[3]);
								jsonList.add(jobj);
						 }
						
						 
					}
					categoryJson.put("productList", jsonList);
					
				}
				infoList.add(categoryJson);
				
			}
			//*********tempIndexList***********************************
		
			if(appIndexList.get(i).getType() ==1)
			{
				//
				tempIndexList = this.mallProductService.searchCateGoryByParentId(appIndexList.get(i).getInfoId().toString());
				infoList = new ArrayList();
				
				for (int k = 0; k < tempIndexList.size(); k++) {//子课程集合
					
					categoryJson = new JSONObject();
					categoryJson.put("listName", tempIndexList.get(k).getUnique_id());
					categoryJson.put("id", tempIndexList.get(k).getCat_id());
					if(categoryMap.get(tempIndexList.get(k).getCat_id().toString()) != null)
					{
						tempCategoryObj = (Object[])categoryMap.get(tempIndexList.get(k).getCat_id().toString());
						
						if(tempCategoryObj[1]!= null)
						{
							tempStr = tempCategoryObj[1].toString().split("@");//分隔课程
							
							
							jsonList = new ArrayList();
							for (int j = 0; j < tempStr.length; j++) {
								
								 tempProduct = new Object[10];
								 tempProduct = tempStr[j].toString().split("#");//分隔商品属性
								
								 if(tempProduct!= null)
								 {
									 JSONObject jobj = new JSONObject();
										
										jobj.put("id", tempProduct[0]);
										jobj.put("name", tempProduct[1]);
										jobj.put("logo", Keys.STAT_NAME+"/wechat/wechatImages/mall/"+tempProduct[2]);
										
										if(tempProduct[4]!= null)
										{
											jobj.put("info", tempProduct[4].toString().replaceAll(">", ":"));
										}
										else
										{
											jobj.put("info", "");
										}
										
										jobj.put("price", tempProduct[3]);
										jsonList.add(jobj);
								 }
								
								 
							}
							
						}
						categoryJson.put("productList", jsonList);
					}
					
					infoList.add(categoryJson);
					
					
				}
				
			}
				
				
				tempAppIndexList.setItems(infoList);
				tempAppIndexList.setItemsCount(infoList.size());
				appIndexObj.put("infoList", tempAppIndexList);
				tempList.add(appIndexObj);
			}

	

		List<MallBanner> list = this.mallProductService.searchMallBanner();
		for (int i = 0; i < list.size(); i++) {

			list.get(i).setBanner(
					Keys.STAT_NAME + "wechat/wechatImages/mall/"
							+ list.get(i).getBanner());
			if (list.get(i).getUrlType() != 0) {
				if (!"".equals(request.getParameter("memberId")) && request.getParameter("memberId") != null) {
					
					list.get(i).setUrl(Keys.STAT_NAME+ "wechat/mall/mallMobileManager?mallProductId="+ list.get(i).getUrl() + "&memberId="+ request.getParameter("memberId"));
				} else 
				{
					list.get(i).setUrl(Keys.STAT_NAME+ "wechat/mall/mallMobileManager?mallProductId="+ list.get(i).getUrl());
				}
				

			}
		}

		
		
		data.put("success", 1);
		data.put("bannerList", list);
		if(!"".equals(request.getParameter("memberId")) && request.getParameter("memberId")!= null)
		{
			Integer memberId = new Integer(request
					.getParameter("memberId"));
			List keywordList = this.memberService.searchMemberKerword(memberId.toString());
			data.put("keywordList", keywordList);
			
		}

		data.put("dataList", tempList);
		
		jsonObj.put("data", data);
		
		} catch (Exception e) {
			
			jsonObj=new JSONObject();
			data=new JSONObject();
			JSONObject error=new JSONObject();
			data.put("success", 0);
			error.put("code", 1000);
			data.put("error", error);
			jsonObj.put("data", data);
			e.printStackTrace();
			
		}
		
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}
	
	
	
	@RequestMapping(value = "/saveOrderByAndroid")
	public void saveOrderByAndroid(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter printWriter=null;
		try {
			JSONObject result=new JSONObject();
			printWriter=response.getWriter();
			List tempList = new ArrayList();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			////System.out.println("createDate = "+format.format(new Date())+" data = "+request.getParameter("data"));
			
			String dataStr = request.getParameter("data");
			//System.out.println(dataStr);
			JSONObject jsonObject =  JSONObject.fromObject(dataStr);
			tempList = jsonObject.getJSONArray("productList");
			String memberId = jsonObject.getString("memberId");
			String orderNumber = jsonObject.getString("orderNumber");
			String orderStr=orderNumber.substring(0,1);
			if (orderStr.equalsIgnoreCase("A")||orderStr.equalsIgnoreCase("I")) {
				orderNumber=orderNumber.substring(1, orderNumber.length());
			}
			String addressId = jsonObject.getString("addressId");
			String status= jsonObject.getString("status");
			String shoppingCartId = jsonObject.getString("shoppingCartId");
			List<MallOrderProduct> productList = new ArrayList<MallOrderProduct>();
			MallOrderProduct mallOrderProduct = new MallOrderProduct();
			
			for (int i = 0; i < tempList.size(); i++) {
				mallOrderProduct = (MallOrderProduct)JSONObject.toBean(JSONObject.fromObject(tempList.get(i).toString()),MallOrderProduct.class);
				productList.add(mallOrderProduct);
			}
			MallOrder mallOrder = new MallOrder();
			mallOrder.setOrderNumber(orderNumber);
			mallOrder.setMemberId(new Integer(memberId));
			mallOrder.setAddressId(new Integer(addressId));
			mallOrder.setStatus(Integer.parseInt(status));
			mallOrder.setCreateDate(new Date());
			mallOrder.setRemarks(request.getParameter("remarks"));
			mallOrderProduct = new MallOrderProduct();
			Double tempTotalPrice = new Double(0);
			for (int i = 0; i < productList.size(); i++) {

				mallOrderProduct = productList.get(i);
				tempTotalPrice = tempTotalPrice+ (mallOrderProduct.getPrice() * mallOrderProduct.getCount());
			}
			
			mallOrder.setTotalPrice(tempTotalPrice);
			
			mallOrder.setFreight(new Double(10));
				
			
			if(mallOrder.getIntegral()>0)
			{
				mallOrder.setTotalPrice(mallOrder.getTotalPrice()-mallOrder.getIntegral());
			}
			if(jsonObject.getString("couponsId")!= null )
			{
				mallOrder.setCouponsId(new Integer(jsonObject.getString("couponsId")));
			}
			this.mallProductService.saveOrder(mallOrder);
			for (int i = 0; i < productList.size(); i++) {

				productList.get(i).setOrderId(mallOrder.getId());
				productList.get(i).setCreateDate(new Date());
				this.mallProductService.saveMallOrderProduct(productList.get(i));	//保存数据
				this.mallProductService.updateProductCount(productList.get(i));		//更新库存
			}
			if (mallOrder.getCouponsId() != 0) {
				
				this.couponsService.updateCouponsInfoStatus(mallOrder.getCouponsId().toString());
			}
			request.getSession().removeAttribute("mallOrder");
			
			if (!"".equals(shoppingCartId)&&!"0".equals(shoppingCartId)&& shoppingCartId != null) {
				
				String[] ids = shoppingCartId.split(",");
				
				if(ids.length ==1)
				{
					this.mallProductService.deleteShoppingCart(shoppingCartId);
				}
				else
				{
					for (int i = 0; i < ids.length; i++) {
						
						this.mallProductService.deleteShoppingCart(ids[i].toString());
					}
				}
				
			}
			
			Integral integral =  new Integral();
			
			
			if("1".equals(request.getParameter("integralStatus")))
			{
				integral.setCreateDate(new Date());
				integral.setMemberId(mallOrder.getMemberId());
				integral.setStatus(1);
				integral.setFraction(-mallOrder.getIntegral()*10);
				integral.setTypeId(4);
				this.integralService.saveIntegral(integral);// 消费积分
			}
			
			result.put("success", 1);
			JSONObject data=new JSONObject();
			data.put("outTradeNo", orderNumber);
			data.put("orderId", mallOrder.getId());
			result.put("data", data);
			printWriter.print(result.toString());
			
			
		} catch (Exception e) {
			JSONObject result=new JSONObject();
			result.put("success", 0);
			JSONObject error=new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			printWriter.print(result.toString());
			e.printStackTrace();
		}
	}

	
	@RequestMapping("/curriculumIndex")
	public void curriculumIndex(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		
		
		long startTime = System.currentTimeMillis();   //获取开始时间  
		
		
		Integer pagesize = 12;//分类显示记录数
		if(!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null )
		{
			pagesize = new Integer(request.getParameter("pageSize"));
		}
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		
		
		CurriculumIndex curriculumIndex = new CurriculumIndex();//一级分类
		
		CurriculumInfo curriculumInfo = new CurriculumInfo();//二级分类
		Object [] tempObject = new Object[3];//一级分类集合对象
		
		
		Object [] tempCurriculumInfo = new Object[3];//二级分类集合对象
		
		Object [] tempCurriculumList = new Object[4];//课程集合对象
		
		TempCurriculum tempCurriculum = new TempCurriculum();	//课程实体
		Object[] tempCurriculumObject = new Object[3];//课程属性对象
		
		try {
			if("true".equals(request.getParameter("refresh"))){//删除缓存
				this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
			}
		
			if(this.redisService.exists(RedisKeys.APP_CURRICULUM_INDEX))
			{
				List<T> dataList = this.redisService.getList(RedisKeys.APP_CURRICULUM_INDEX, CurriculumIndex.class);
				data.put("dataList", dataList);
				
			}
			else
			{
				List<Object[]> curriculumList   = this.mallProductService.searchCurriculumIndex();
				List<CurriculumIndex> dataList = new ArrayList();
				//分隔符://⊥ ∪⊙
				for (int i = 0; i < curriculumList.size(); i++) {//一级分类
					
					curriculumIndex = new CurriculumIndex();
					
					curriculumIndex.setTitle(curriculumList.get(i)[1].toString());
					////System.out.println(" === "+curriculumList.get(i).length);
					if(curriculumList.get(i).length>=4)
					{
						
						if(curriculumList.get(i)[3]!= null)
						{
							////System.out.println(" === "+curriculumList.get(i)[3].toString());
							tempObject = curriculumList.get(i)[3].toString().split("⊙");
							
							
							for (int j = 0; j < tempObject.length; j++) {//二级分类集合
								
								tempCurriculumInfo = tempObject[j].toString().split("∩");
								curriculumInfo = new CurriculumInfo();
								curriculumInfo.setCatId(new Integer(tempCurriculumInfo[0].toString()));
								curriculumInfo.setTitle(tempCurriculumInfo[1].toString());
								
								
								tempCurriculumObject = tempCurriculumInfo[2].toString().split("∪");//课程集合
								
								for (int k = 0; k < tempCurriculumObject.length; k++) {
									
									tempCurriculumList = tempCurriculumObject[k].toString().split("⊥");//课程属性
									tempCurriculum = new TempCurriculum();
									
								//	for (int h = 0; h < tempCurriculumList.length; h++) {
										
										
										
										tempCurriculum.setId(new Integer(tempCurriculumList[0].toString()));
										
										if(tempCurriculumList.length>=2)
										{
											tempCurriculum.setName(tempCurriculumList[1].toString());
										}
										else
										{
											tempCurriculum.setName("");
										}
										
										
										if(tempCurriculumList.length>=3)
										{
											if(tempCurriculumList[2].toString().contains("http")){
												tempCurriculum.setCover(tempCurriculumList[2].toString());
											}else{
												tempCurriculum.setCover(Keys.STAT_NAME+"wechat/wechatImages/mall/"+tempCurriculumList[2].toString());
											}
											
										}
										else
										{
											tempCurriculum.setCover("");
										}
										
										if(tempCurriculumList.length>=4)
										{
											tempCurriculum.setPrice(tempCurriculumList[3].toString());
										}
										else
										{
											tempCurriculum.setPrice("0");
										}
										
										curriculumInfo.getInfoList().add(tempCurriculum);
									//}
									
									
									
								}
								
								
		
							
		
								curriculumIndex.getCurriculumList().add(curriculumInfo);
							}
							
							dataList.add(curriculumIndex);
						}
						
						
					}
					
		
				}
				
				data.put("dataList", dataList);
				this.redisService.setList(RedisKeys.APP_CURRICULUM_INDEX, dataList);
			}
			
			long endTime=System.currentTimeMillis(); //获取结束时间 
			//System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
			data.put("success", 1);
			
			
			
			List<MallBanner> list = this.mallProductService.searchMallBanner();
			for (int i = 0; i < list.size(); i++) {

				list.get(i).setBanner(
						Keys.STAT_NAME + "wechat/wechatImages/mall/"
								+ list.get(i).getBanner());
				if (list.get(i).getUrlType() != 0) {
					if (!"".equals(request.getParameter("memberId")) && request.getParameter("memberId") != null) {
						
						list.get(i).setUrl(Keys.STAT_NAME+ "wechat/mall/mallMobileManager?mallProductId="+ list.get(i).getUrl() + "&memberId="+ request.getParameter("memberId"));
					} else 
					{
						list.get(i).setUrl(Keys.STAT_NAME+ "wechat/mall/mallMobileManager?mallProductId="+ list.get(i).getUrl());
					}
					

				}
			}

			
			
			data.put("success", 1);
			data.put("bannerList", list);
			if(!"".equals(request.getParameter("memberId")) && request.getParameter("memberId")!= null)
			{
				Integer memberId = new Integer(request
						.getParameter("memberId"));
				List keywordList = this.memberService.searchMemberKerword(memberId.toString());
				data.put("keywordList", keywordList);
				
			}
			
			
			jsonObj.put("data", data);
		
	
		} catch (Exception e) {
			jsonObj=new JSONObject();
			data=new JSONObject();
			JSONObject error=new JSONObject();
			data.put("success", 0);
			error.put("code", 1000);
			data.put("error", error);
			jsonObj.put("data", data);
			e.printStackTrace();
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
	}
	
	
	
	@RequestMapping("/curriculumType")
	public void curriculumType(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		
		
		long startTime = System.currentTimeMillis();   //获取开始时间  
		
		
		Integer pagesize = 12;//分类显示记录数
		if(!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null )
		{
			pagesize = new Integer(request.getParameter("pageSize"));
		}
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		
		
		CategoryInfo categoryInfo = new CategoryInfo();;//一级分类
		
		TempCategory tempCategory = new TempCategory();;//二级分类
		
		Object [] tempObject = new Object[3];//一级分类集合对象
		
		
		Object [] tempCurriculumInfo = new Object[3];//二级分类集合对象
		
		Object [] tempCurriculumList = new Object[4];//课程集合对象
		
		TempCurriculum tempCurriculum = new TempCurriculum();	//课程实体
		Object[] tempCurriculumObject = new Object[3];//课程属性对象
		
		try {
			
		
			if(this.redisService.exists(RedisKeys.APP_CURRICULUM_INFO))
			{
				List<T> dataList = this.redisService.getList(RedisKeys.APP_CURRICULUM_INFO, CategoryInfo.class);
				data.put("dataList", dataList);
				
			}
			else
			{
				List<Object[]> curriculumList   = this.mallProductService.searchCategoryInfo();
				List<CategoryInfo> dataList = new ArrayList();
				//分隔符://⊥ ∪⊙
				for (int i = 0; i < curriculumList.size(); i++) {//一级分类
					
					categoryInfo = new CategoryInfo();
					
					categoryInfo.setTitle(curriculumList.get(i)[1].toString());
					if(curriculumList.get(i).length>=3)
					{
						
						tempObject = curriculumList.get(i)[2].toString().split("⊙");
						
						
						for (int j = 0; j < tempObject.length; j++) {//二级分类集合
							
							tempCurriculumInfo = tempObject[j].toString().split("∪");
							
							tempCategory = new TempCategory();
							tempCategory.setCatId(new Integer(tempCurriculumInfo[0].toString()));
							tempCategory.setTitle(tempCurriculumInfo[1].toString());
							if(tempCurriculumInfo.length>=3)
							{
								
								tempCategory.setDescription(tempCurriculumInfo[2].toString());
							}
							else
							{
								tempCategory.setDescription("");
							}
							
							
//							tempCurriculumObject = tempCurriculumInfo[3].toString().split("∩");//课程集合
//							
//							for (int k = 0; k < tempCurriculumObject.length&& k < 2; k++) {
//								
//								tempCurriculumList = tempCurriculumObject[k].toString().split("⊥");//课程属性
//
//								
//
//								
//							}
							
							categoryInfo.getCategoryList().add(tempCategory);
						}
						
						dataList.add(categoryInfo);
						
					}
					
		
				}
				
				data.put("dataList", dataList);
				this.redisService.setList(RedisKeys.APP_CURRICULUM_INFO, dataList);
			}
			
			long endTime=System.currentTimeMillis(); //获取结束时间 
			//System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
	
			
			data.put("success", 1);

			
			jsonObj.put("data", data);
		
	
		} catch (Exception e) {
			jsonObj=new JSONObject();
			data=new JSONObject();
			JSONObject error=new JSONObject();
			data.put("success", 0);
			error.put("code", 1000);
			data.put("error", error);
			jsonObj.put("data", data);
			e.printStackTrace();
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
	}
	
	
	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	
	 
}
