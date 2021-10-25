package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.recordqueue.MallProductLogDelegate;
import com.wechat.service.*;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("mall")
public class MallController {

	@Resource
	private UserService userService;
	
	@Autowired
	private AppService appService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	private MallProductService mallProductService;

	public MallProductService getMallProductService() {
		return mallProductService;
	}

	public void setMallProductService(MallProductService mallProductService) {
		this.mallProductService = mallProductService;
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
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {

		this.accountService = accountService;
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
	public BookService bookService;

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
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
	
	
	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	@RequestMapping("/mallManager")
	public String mallManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
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
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("name", queryDto.getName());
		map.put("catIds", queryDto.getCatIds());

		Page resultPage = this.mallProductService.searchMallProduct(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		map = new HashMap();
		map.put("type", "0");
		map.put("page", "1");
		map.put("rowsPerPage", "1000");

		Page tempResult = this.bookService.searchLabel(map);
		List labelList = new ArrayList();

		labelList = tempResult.getItems();

		request.getSession().setAttribute("labelList", labelList);

		return "mall/mallManager";
	}

	/**
	 * 商品分类页面跳转
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("/mallCategory")
	public String mallCategory(HttpServletRequest request, QueryDto queryDto) {
		if (!"".equals(request.getParameter("result"))
				&& request.getParameter("result") != null) {
			String result = new String(Base64Util.decode(request
					.getParameter("result")));
			queryDto.setResult(result);
		}
		request.setAttribute("queryDto", queryDto);

		return "mall/mallCategory";
	}

	/**
	 * 商品分类Json数据
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("/mallCategoryView")
	public void mallCategoryView(HttpServletRequest request,
			HttpServletResponse response) {
		List cats = null;
		if (!"".equals(request.getParameter("currentId"))
				&& request.getParameter("currentId") != null) {
			int currentId = Integer.parseInt(request.getParameter("currentId"));
			cats = productCategoryService.getProductCategoryNolevel(
					"product_category", 0, 0, currentId, new ArrayList());
		} else {
			cats = productCategoryService.getProductCategoryNolevel(
					"product_category", 0, 0, -1, new ArrayList());
		}

		// for (Iterator iterator = cats.iterator(); iterator.hasNext();) {
		// ProductCategory object = (ProductCategory) iterator.next();
		// //System.out.println(object.getMark()+"分类名称:"+object.getCat_name());
		// }

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
	 * 删除分类商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/deleteMallCate")
	public String deleteMallCate(HttpServletRequest request,
			HttpServletResponse response) {
		int catId = Integer.parseInt(request.getParameter("cat_id"));
		// 父分类下的所有子分类
		String catIdChilds = mallProductService.getCategoryChildIds(
				"product_category", catId);
		String catIds = catId + "";
		if (!catIdChilds.equals("") && null != catIdChilds) {
			catIds = catId + "," + catIdChilds;
		}
		HashMap map = new HashMap();
		map.put("catIds", catIds);
		map.put("page", "1");
		map.put("rowsPerPage", "100");
		// 父分类下面的所有商品
		Page proCats = mallProductService.searchMallProduct(map);
		String result = "";
		// 该分类下面没有子分类，且没有商品的可以删除
		if (!"".equals(catIdChilds) || proCats.getItems().size() > 0) {
			result = "该分类不是末级分类或者分类下还存在商品，您不能删除";
		} else {
			this.productCategoryService.delereProductCategory(catId);
			result = "该分类已成功删除";
		}
		result = Base64Util.encode(result.getBytes());
		
		this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
		this.redisService.del(RedisKeys.APP_CURRICULUM_INFO);
		
		return "redirect:mallCategory?result=" + result + "";
	}

	/**
	 * 添加商品
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping(value = "/saveMallCate")
	public String saveMallCate(HttpServletRequest request,
			HttpServletResponse response) {
		int catId = 0;
		String catName = "";
		String uniqueId = "";
		String keywords = "";
		String parentId = "";
		String description = "";
		String sort = "";
		if (!"".equals(request.getParameter("catId"))
				&& null != request.getParameter("catId")) {
			catId = new Integer(request.getParameter("catId"));
		}
		if (!"".equals(request.getParameter("catName"))
				&& null != request.getParameter("catName")) {
			catName = request.getParameter("catName");
		}
		if (!"".equals(request.getParameter("uniqueId"))
				&& null != request.getParameter("uniqueId")) {
			uniqueId = request.getParameter("uniqueId");
		}
		if (!"".equals(request.getParameter("keywords"))
				&& null != request.getParameter("keywords")) {
			keywords = request.getParameter("keywords");
		}
		if (!"".equals(request.getParameter("parentId"))
				&& null != request.getParameter("parentId")) {
			parentId = request.getParameter("parentId");
		}
		if (!"".equals(request.getParameter("description"))
				&& null != request.getParameter("description")) {
			description = request.getParameter("description");
		}
		if (!"".equals(request.getParameter("sort"))
				&& null != request.getParameter("sort")) {
			sort = request.getParameter("sort");
		}
		
		Integer show=Integer.parseInt(ParameterFilter.emptyFilter("0", "status", request));
		ProductCategoryShow productCategoryShow=new ProductCategoryShow();
		productCategoryShow.setCategoryId(catId);
		productCategoryShow.setShow(show);
		appService.saveProductCategoryShow(productCategoryShow);
		ProductCategory productCategory = new ProductCategory();
		if (catId == 0) {
			productCategory.setCat_id(null);
		} else {
			productCategory.setCat_id(catId);
		}

		productCategory.setCat_name(catName);
		productCategory.setDescription(description);
		productCategory.setKeywords(keywords);
		productCategory.setParent_id(Integer.parseInt(parentId));
		productCategory.setSort(Integer.parseInt(sort));
		productCategory.setUnique_id(uniqueId);
		productCategoryService.saveProductCategory(productCategory);
		
		this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
		this.redisService.del(RedisKeys.APP_CURRICULUM_INFO);
		return "redirect:mallCategory";
	}

	@RequestMapping("/mallOrderManager")
	public String mallOrderManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("number", queryDto.getNumber());
		map.put("status", queryDto.getStatus());

		Page resultPage = this.mallProductService.searchMallOrderInfo(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		List tempList = new ArrayList();
		List<MallOrderProduct> mallProductList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] mallOrder : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", mallOrder[0]);
				jobj.put("orderNumber", mallOrder[1]);
				jobj.put("totalPrice", mallOrder[5]);
				jobj.put("freight", mallOrder[8]);
				if (mallOrder[9] != null) {
					jobj.put("remarks", mallOrder[9]);
				} else {
					jobj.put("remarks", "");
				}
				if (mallOrder[10] != null) {
					jobj.put("express", mallOrder[10]);
				} else {
					jobj.put("express", "");
				}
				if (mallOrder[11] != null) {
					jobj.put("expressNumber", mallOrder[11]);
				} else {
					jobj.put("expressNumber", "");
				}
				jobj.put("address", mallOrder[12].toString() + "  "
						+ mallOrder[13].toString());

				if (mallOrder[2] != null) {
					// 订单状态,1已付款,2已发货,3已确认收货.4退款中,5已退款

					if (new Integer(mallOrder[2].toString()) == 1) {
						jobj.put("statusName", "待发货");
					}
					if (new Integer(mallOrder[2].toString()) == 2) {
						jobj.put("statusName", "已发货");
					}
					if (new Integer(mallOrder[2].toString()) == 3) {
						jobj.put("statusName", "已确认");
					}
					if (new Integer(mallOrder[2].toString()) == 4) {
						jobj.put("statusName", "退款中");
					}
					if (new Integer(mallOrder[2].toString()) == 5) {
						jobj.put("statusName", "已退款");
					}
					if (new Integer(mallOrder[2].toString()) == 7) {
						jobj.put("statusName", "未付款");
					}
					jobj.put("status", mallOrder[2]);
				}

				jobj.put("createDate", sdf.format(mallOrder[3]).toString());
				jobj.put("userName", mallOrder[6]);
				jobj.put("mobile", mallOrder[7]);
				mallProductList = this.mallProductService
						.searchMallOrderProduct(mallOrder[0].toString());
				if (mallProductList.size() > 0) {
					tempList = new ArrayList();
					for (MallOrderProduct orderProduct : mallProductList) {

						JSONObject tempJobj = new JSONObject();
						tempJobj.put("productName",
								orderProduct.getProductName());
						tempJobj.put("specifications",
								orderProduct.getSpecifications());
						tempJobj.put("price", orderProduct.getPrice());
						tempJobj.put("count", orderProduct.getCount());
						tempJobj.put("productImg", orderProduct.getProductImg());
						tempList.add(tempJobj);

					}
					jobj.put("productList", tempList);
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}

		request.setAttribute("jsonStr", jsonObj.toString());
		return "mall/mallOrderManager";
	}

	@RequestMapping("/searchMallLabelInfo")
	public String searchMallLabelInfo(HttpServletRequest request)
			throws Exception {

		HashMap map = new HashMap();
		String page = "1";
		String rowsPerPage = "50";
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			page = request.getParameter("page");
		}
		if (!"".equals(request.getParameter("rowsPerPage"))
				&& request.getParameter("rowsPerPage") != null) {
			rowsPerPage = request.getParameter("rowsPerPage");
		}

		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);

		Page resultPage = this.mallProductService.searchMallLabel(map);
		List<Object[]> infoList = resultPage.getItems();
		return null;
	}

	@RequestMapping("/mallMobileManager")
	public String mallMobileManager(HttpServletRequest request)
			throws Exception {

		request.setAttribute("memberId", request.getParameter("memberId"));

		request.getSession().removeAttribute("mallOrder");

		if (!"".equals(request.getParameter("mallProductId"))
				&& request.getParameter("mallProductId") != null) {
			Object[] productInfo = this.mallProductService
					.searchMallProductInfo(request
							.getParameter("mallProductId"));

			if ("1052".equals(request.getParameter("mallProductId"))) {

				BookShop bookShop = this.bookService
						.searchBookShopById(new Integer(request
								.getParameter("shopId")));
				DecimalFormat df2 = new DecimalFormat("####");
				productInfo[6] = df2.format(bookShop.getMemberCardPrice());
				productInfo[7] = productInfo[7].toString().split(">")[0] + ">"
						+ df2.format(bookShop.getMemberCardPrice()) + ">1000>"
						+ productInfo[7].toString().split(">")[3];
				/*System.out.println(" shopId = "
						+ request.getParameter("shopId"));
				request.getSession().setAttribute("shopId",
						request.getParameter("shopId"));*/
			}
			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "20");
			map.put("productId", request.getParameter("mallProductId"));
			Page resultPage = this.commentService.searchComment(map);
			request.setAttribute("totalCount", resultPage.getTotalCount());
			List tempList = null;
			List commentList = new ArrayList();
			Object[] tempObj = new Object[6];
			String[] tempStr = null;
			CommentInfo commentInfo = null;
			for (int i = 0; i < 3 && i < resultPage.getItems().size(); i++) {

				tempObj = (Object[]) resultPage.getItems().get(i);
				commentInfo = new CommentInfo();
				commentInfo.setContent(tempObj[2].toString());
				commentInfo.setCreateDate(tempObj[3].toString()
						.substring(0, 16));
				if (tempObj[1] != null) {
					commentInfo.setMemberName(tempObj[1].toString().substring(
							0, 1)
							+ "****"
							+ tempObj[1].toString().substring(
									tempObj[1].toString().length() - 1,
									tempObj[1].toString().length()));

				} else {
					commentInfo.setMemberName("匿名用户");
				}
				commentInfo.setProductId(new Integer(tempObj[0].toString()));
				commentInfo.setStar(new Integer(tempObj[4].toString()));
				if (tempObj[5] != null) {
					tempStr = tempObj[5].toString().split(",");
					tempList = new ArrayList();
					for (int j = 0; j < tempStr.length; j++) {

						tempList.add(Keys.STAT_NAME
								+ "/wechat/wechatImages/mallComment/"
								+ tempStr[j].toString());

					}
					commentInfo.setImgList(tempList);
					;
				}
				commentList.add(commentInfo);
			}
			request.setAttribute("commentList", commentList);
			MallProductLog mallProductLog = new MallProductLog();
			System.out
					.println("memberId = " + request.getParameter("memberId"));
			if (!"".equals(request.getParameter("memberId"))
					&& request.getParameter("memberId") != null) {
				mallProductLog.setMemberId(new Integer(request
						.getParameter("memberId")));
			} else {
				mallProductLog.setMemberId(new Integer("0"));
			}

			mallProductLog.setProductId(new Integer(request
					.getParameter("mallProductId")));
			mallProductLog.setCreateDate(new Date());
			MallProductLogDelegate.addDataToTaskQueue(mallProductLog);// 添加商品访问记录

			request.setAttribute("tempProduct", productInfo);
			JSONObject jobj = new JSONObject();
			jobj.put("id", productInfo[0]);
			jobj.put("name", productInfo[1]);
			if(productInfo[2].toString().contains("http")){
				jobj.put("logo1", productInfo[2]);
				
			}else{
				
				jobj.put("logo1", "http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/" + productInfo[2]);
			}
			
			jobj.put("info", productInfo[7]);
			List jsonList = new ArrayList();
			jsonList.add(jobj);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("infoList", jsonList);
			request.setAttribute("jsonStr", jsonObj.toString());
			tempStr = null;
			List<Object[]> saleList = new ArrayList();
			if (productInfo[12] != null) {
				tempStr = productInfo[12].toString().split(",");
				tempObj = new Object[3];
				for (int i = 0; i < tempStr.length; i++) {

					tempObj = new Object[3];
					tempObj[0] = tempStr[i].toString().split(">")[0].toString()
							.substring(0, 1) + "***";
					tempObj[1] = tempStr[i].toString().split(">")[1];
					tempObj[2] = tempStr[i].toString().split(">")[2].toString()
							.substring(5, 16);
					saleList.add(tempObj);

				}
				request.setAttribute("saleList", saleList);
			}

			String str = productInfo[7].toString();
			tempStr = str.split(",");
			str = "";
			List list = new ArrayList();
			Object obj = new Object();
			for (int i = 0; i < tempStr.length; i++) {

				obj = new Object();
				obj = tempStr[i].split(">")[0].toString();
				list.add(obj);
				str = str + tempStr[i].split(">")[0].toString();
				if (i + 1 != tempStr.length) {
					str = str + "、";
				}

			}
			request.setAttribute("tempStr", str);
			request.setAttribute("tempList", list);

			String jsapitiket = this.accountService.getTicket(Keys.APP_ID,
					Keys.APP_SECRET);// 获取公众账号ticket

			String url = Keys.STAT_NAME
					+ "mall/mallMobileManager??mallProductId="
					+ request.getParameter("mallProductId") + "&memberId="
					+ request.getParameter("memberId");
			//System.out.println("aaaurl = " + url);
			Map<String, String> ret = sign(jsapitiket, url);

			String timestamp = ret.get("timestamp");
			String nonceStr = ret.get("nonceStr");
			String signature = ret.get("signature");
			request.setAttribute("timestamp", timestamp);
			request.setAttribute("nonceStr", nonceStr);
			request.setAttribute("signature", signature);
			request.setAttribute("appId", Keys.APP_ID);

			return "mall/mallMobileView";
		} else {
			HashMap map = new HashMap();
			String page = "1";
			String rowsPerPage = "20";
			if (!"".equals(request.getParameter("page"))
					&& request.getParameter("page") != null) {
				page = request.getParameter("page");
			}
			if (!"".equals(request.getParameter("rowsPerPage"))
					&& request.getParameter("rowsPerPage") != null) {
				page = request.getParameter("rowsPerPage");
			}
			String sortType = "saleCount";
			if (!"".equals(request.getParameter("sortType"))
					&& request.getParameter("sortType") != null) {
				sortType = request.getParameter("sortType");
			}
			map.put("page", page);
			map.put("rowsPerPage", rowsPerPage);
			Page resultPage = this.mallProductService
					.searchMobileMallProduct(map);
			List<Object[]> infoList = resultPage.getItems();
			request.setAttribute("infoList", infoList);
			List jsonList = new ArrayList();
			Object[] tempProduct = null;
			// 封装成JSON显示对象
			JSONObject jsonObj = new JSONObject();
			if (infoList != null) {
				JSONArray AgentKeyWordInfo = new JSONArray();
				for (Object[] mallProduct : infoList) {
					JSONObject jobj = new JSONObject();
					tempProduct = mallProduct;
					jobj.put("id", mallProduct[0]);
					jobj.put("name", mallProduct[1]);
					jobj.put("logo1", mallProduct[2]);
					jobj.put("info", mallProduct[4]);
					jobj.put("price", mallProduct[3]);
					jsonList.add(jobj);
				}
				jsonObj.put("infoList", jsonList);

			}
			request.setAttribute("jsonStr", jsonObj.toString());

			List keywordList = this.memberService.searchMemberKerword(request
					.getParameter("memberId"));
			request.setAttribute("keywordList", keywordList);
			map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "20");

			map.put("status", "0");
			map.put("type", "1");
			resultPage = this.mallProductService.searchMallBanner(map);

			List<MallBanner> list = resultPage.getItems();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getBanner().contains("http")){
					//System.out.println(list.get(i).getBanner());
				}else{
					list.get(i).setBanner(Keys.STAT_NAME + "wechat/wechatImages/mall/"
									+ list.get(i).getBanner());					
					
				}

				if (list.get(i).getUrlType() != 0) {
					if (!"".equals(request.getParameter("memberId"))
							&& request.getParameter("memberId") != null) {
						list.get(i)
								.setUrl(Keys.STAT_NAME
										+ "wechat/mall/mallMobileManager?mallProductId="
										+ list.get(i).getUrl() + "&memberId="
										+ request.getParameter("memberId"));
					} else {
						list.get(i)
								.setUrl(Keys.STAT_NAME
										+ "wechat/mall/mallMobileManager?mallProductId="
										+ list.get(i).getUrl());
					}
					//System.out.println("URL = " + list.get(i).getUrl());

				}
			}

			map = new HashMap();
			request.setAttribute("bannerList", list);
			return "mall/mallMobileManager";
		}

	}

	/*
	 *  微商城首页
	 * 
	 * */
	@RequestMapping("/mallMobileIndex")
	public String mallMobileManagerByLabel(HttpServletRequest request)
			throws Exception {
		request.setAttribute("memberId", request.getParameter("memberId"));
		request.getSession().removeAttribute("mallOrder");
		HashMap map = new HashMap();
		map.put("wechat", "0");
		List<Object[]> indexList = this.mallProductService
				.searchMallLabelByIndex(map);// 查询首页标签集合
		String labelIds = "";
		for (int i = 0; i < indexList.size(); i++) {// 组装标签ids

			labelIds = labelIds + indexList.get(i)[2];
			if (i + 1 != indexList.size()) {
				labelIds = labelIds + ",";
			}
		}

		List<Object[]> labelInfoList = this.mallProductService
				.searchMallProdyctByLabel(labelIds, "6");// 查询标签集合数据

		HashMap infoMap = new HashMap();
		for (int i = 0; i < labelInfoList.size(); i++) {

			infoMap.put(labelInfoList.get(i)[0].toString(),
					labelInfoList.get(i)[1]);

		}
		List mallList = new ArrayList();
		MallInfoList mallInfoList = new MallInfoList();
		List tempList = new ArrayList();

		JSONObject jsonObj = new JSONObject();
		Object[] tempProduct = new Object[10];
		List jsonList = new ArrayList();
		List<Object[]> objList = new ArrayList();

		String[] tempStr = null;
		for (int i = 0; i < indexList.size(); i++) {
			mallInfoList = new MallInfoList();
			mallInfoList.setTitle(indexList.get(i)[1].toString());
			if (infoMap.get(indexList.get(i)[2].toString()) != null) {

				tempStr = infoMap.get(indexList.get(i)[2].toString())
						.toString().split("@");// 分隔商品

				JSONArray AgentKeyWordInfo = new JSONArray();
				objList = new ArrayList();
				for (int j = 0; j < tempStr.length; j++) {

					tempProduct = new Object[10];
					tempProduct = tempStr[j].toString().split("#");// 分隔商品属性
					objList.add(tempProduct);
					JSONObject jobj = new JSONObject();

					jobj.put("id", tempProduct[0]);
					jobj.put("name", tempProduct[1]);
					if(null!=tempProduct[2].toString()&&!"".equals(tempProduct[2].toString())){
						if(tempProduct[2].toString().contains("http")){
							jobj.put("logo1", tempProduct[2]);
					
						}else{
							jobj.put("logo1","http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/" + tempProduct[2]);
						}
						
					}else{
						jobj.put("logo1", "");
					}

					jobj.put("info", tempProduct[4]);
					jobj.put("price", tempProduct[3]);

					jsonList.add(jobj);
				}
				mallInfoList.setMallProductList(objList);
			}
			mallList.add(mallInfoList);
		}
		jsonObj.put("infoList", jsonList);
		request.setAttribute("jsonStr", jsonObj.toString());
		List keywordList = this.memberService.searchMemberKerword(request
				.getParameter("memberId"));
		request.setAttribute("keywordList", keywordList);

		request.setAttribute("mallList", mallList);

		HashMap bannerMap = new HashMap();
		bannerMap.put("type", "1");
		bannerMap.put("page", "1");
		bannerMap.put("rowsPerPage", "20");
		//轮播数据获取
		Page bannerPage = this.mallProductService.searchMallBanner(bannerMap);
		List<MallBanner> list = bannerPage.getItems();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getBanner().contains("http")){
				
				
			}else{
				list.get(i).setBanner(
						Keys.STAT_NAME + "wechat/wechatImages/mall/"
								+ list.get(i).getBanner());		
			}

			if (list.get(i).getUrlType() != 0) {
				if (!"".equals(request.getParameter("memberId"))
						&& request.getParameter("memberId") != null) {

					list.get(i)
							.setUrl(Keys.STAT_NAME
									+ "wechat/mall/mallMobileManager?mallProductId="
									+ list.get(i).getUrl() + "&memberId="
									+ request.getParameter("memberId"));
				} else {
					list.get(i)
							.setUrl(Keys.STAT_NAME
									+ "wechat/mall/mallMobileManager?mallProductId="
									+ list.get(i).getUrl());
				}

			}
		}
		map = new HashMap();
		request.setAttribute("bannerList", list);
		return "mall/mallMobileIndex";

	}

	@RequestMapping("/showMallView")
	public String showMallView(HttpServletRequest request) throws Exception {

		request.getSession().removeAttribute("mallOrder");

		if (!"".equals(request.getParameter("mallProductId"))
				&& request.getParameter("mallProductId") != null) {
			Object[] productInfo = this.mallProductService
					.searchMallProductInfo(request
							.getParameter("mallProductId"));

			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "20");
			map.put("productId", request.getParameter("mallProductId"));
			Page resultPage = this.commentService.searchComment(map);
			request.setAttribute("totalCount", resultPage.getTotalCount());
			List tempList = null;
			List commentList = new ArrayList();
			Object[] tempObj = new Object[6];
			String[] tempStr = null;
			CommentInfo commentInfo = null;
			for (int i = 0; i < 3 && i < resultPage.getItems().size(); i++) {

				tempObj = (Object[]) resultPage.getItems().get(i);
				commentInfo = new CommentInfo();
				commentInfo.setContent(tempObj[2].toString());
				commentInfo.setCreateDate(tempObj[3].toString()
						.substring(0, 16));
				commentInfo.setMemberName(tempObj[1].toString().substring(0, 1)
						+ "****"
						+ tempObj[1].toString().substring(
								tempObj[1].toString().length() - 1,
								tempObj[1].toString().length()));
				commentInfo.setProductId(new Integer(tempObj[0].toString()));
				commentInfo.setStar(new Integer(tempObj[4].toString()));
				if (tempObj[5] != null) {
					tempStr = tempObj[5].toString().split(",");
					tempList = new ArrayList();
					for (int j = 0; j < tempStr.length; j++) {

						tempList.add(Keys.STAT_NAME
								+ "/wechat/wechatImages/mallComment/"
								+ tempStr[j].toString());

					}
					commentInfo.setImgList(tempList);
					;
				}
				commentList.add(commentInfo);
			}
			request.setAttribute("commentList", commentList);
			MallProductLog mallProductLog = new MallProductLog();
			if (!"".equals(request.getParameter("memberId"))
					&& request.getParameter("memberId") != null) {
				mallProductLog.setMemberId(new Integer(request
						.getParameter("memberId")));
			} else {
				mallProductLog.setMemberId(new Integer("0"));
			}

			mallProductLog.setProductId(new Integer(request
					.getParameter("mallProductId")));
			mallProductLog.setCreateDate(new Date());
			MallProductLogDelegate.addDataToTaskQueue(mallProductLog);// 添加商品访问记录
			request.setAttribute("tempProduct", productInfo);
			JSONObject jobj = new JSONObject();
			jobj.put("id", productInfo[0]);
			jobj.put("name", productInfo[1]);
			jobj.put("logo1", productInfo[2]);
			jobj.put("info", productInfo[7]);
			List jsonList = new ArrayList();
			jsonList.add(jobj);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("infoList", jsonList);
			request.setAttribute("jsonStr", jsonObj.toString());
			tempStr = null;
			List<Object[]> saleList = new ArrayList();
			if (productInfo[12] != null) {
				tempStr = productInfo[12].toString().split(",");
				tempObj = new Object[3];
				for (int i = 0; i < tempStr.length; i++) {

					tempObj = new Object[3];
					tempObj[0] = tempStr[i].toString().split(">")[0].toString()
							.substring(0, 1) + "***";
					tempObj[1] = tempStr[i].toString().split(">")[1];
					tempObj[2] = tempStr[i].toString().split(">")[2].toString()
							.substring(5, 16);
					saleList.add(tempObj);

				}
				request.setAttribute("saleList", saleList);
			}

			String str = productInfo[7].toString();
			tempStr = str.split(",");
			str = "";
			List list = new ArrayList();
			Object obj = new Object();
			for (int i = 0; i < tempStr.length; i++) {

				obj = new Object();
				obj = tempStr[i].split(">")[0].toString();
				list.add(obj);
				str = str + tempStr[i].split(">")[0].toString();
				if (i + 1 != tempStr.length) {
					str = str + "、";
				}

			}
			request.setAttribute("tempStr", str);
			request.setAttribute("tempList", list);

			String jsapitiket = this.accountService.getTicket(Keys.APP_ID,
					Keys.APP_SECRET);// 获取公众账号ticket

			String url = Keys.STAT_NAME
					+ "mall/mallMobileManager??mallProductId="
					+ request.getParameter("mallProductId") + "&memberId="
					+ request.getParameter("memberId");
			//System.out.println("aaaurl = " + url);
			Map<String, String> ret = sign(jsapitiket, url);

			String timestamp = ret.get("timestamp");
			String nonceStr = ret.get("nonceStr");
			String signature = ret.get("signature");
			request.setAttribute("timestamp", timestamp);
			request.setAttribute("nonceStr", nonceStr);
			request.setAttribute("signature", signature);
			request.setAttribute("appId", Keys.APP_ID);

			return "mall/showMallView";
		}

		return null;

	}

	@RequestMapping("/seaechMall")
	public String seaechMall(HttpServletRequest request) throws Exception {

		request.setAttribute("memberId", request.getParameter("memberId"));

		HashMap map = new HashMap();
		String page = "1";
		String rowsPerPage = "50";
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			page = request.getParameter("page");
		}
		if (!"".equals(request.getParameter("rowsPerPage"))
				&& request.getParameter("rowsPerPage") != null) {
			page = request.getParameter("rowsPerPage");
		}
		String sortType = "saleCount";
		if (!"".equals(request.getParameter("sortType"))
				&& request.getParameter("sortType") != null) {
			sortType = request.getParameter("sortType");
		}
		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);
		map.put("labelId", request.getParameter("labelId"));
		map.put("name", request.getParameter("name"));
		request.setAttribute("name", request.getParameter("name"));

		if (!"".equals(request.getParameter("name"))
				&& request.getParameter("name") != null) {

			if (!"".equals(request.getParameter("memberId"))
					&& request.getParameter("memberId") != null) {
				MemberKeyword memberKeyword = new MemberKeyword();
				memberKeyword.setKeyword(request.getParameter("name"));

				memberKeyword.setMemberId(new Integer(request
						.getParameter("memberId")));
				this.memberService.saveMemberKeyword(memberKeyword);
			}

		}
		Page resultPage = this.mallProductService.searchMobileMallProduct(map);
		List<Object[]> infoList = resultPage.getItems();
		request.setAttribute("infoList", infoList);

		List jsonList = new ArrayList();
		Object[] tempProduct = null;
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			for (Object[] mallProduct : infoList) {
				JSONObject jobj = new JSONObject();
				tempProduct = mallProduct;
				jobj.put("id", mallProduct[0]);
				jobj.put("name", mallProduct[1]);
				jobj.put("logo1", mallProduct[2]);
				jobj.put("info", mallProduct[4]);
				jobj.put("price", mallProduct[3]);
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		List keywordList = this.memberService.searchMemberKerword(request
				.getParameter("memberId"));
		request.setAttribute("keywordList", keywordList);

		return "mall/searchMall";

	}

	@RequestMapping(value = "/searchMallManagerView")
	public void searchMallManagerView(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		String page = "1";
		String rowsPerPage = "50";
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			page = request.getParameter("page");
		}
		if (!"".equals(request.getParameter("rowsPerPage"))
				&& request.getParameter("rowsPerPage") != null) {
			page = request.getParameter("rowsPerPage");
		}
		String sortType = "saleCount";
		if (!"".equals(request.getParameter("sortType"))
				&& request.getParameter("sortType") != null) {
			sortType = request.getParameter("sortType");
		}
		if (!"".equals(request.getParameter("catIds"))
				&& request.getParameter("catIds") != null) {
			queryDto.setCatIds(request.getParameter("catIds"));
		}
		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);
		// String name = new String(queryDto.getName().getBytes(),"UTF-8");
		map.put("name", queryDto.getName());
		map.put("catIds", queryDto.getCatIds());
		request.getSession().removeAttribute("mallOrder");
		Page resultPage = this.mallProductService.searchMallProductList(map);
		List<Object[]> infoList = new ArrayList<Object[]>();
		if (null != resultPage) {
			infoList = resultPage.getItems();
		}

		String productIds = "";
		for (int i = 0; i < infoList.size(); i++) {
			productIds = productIds + infoList.get(i)[0].toString() + ",";
		}
		if (productIds.length() > 0) {
			productIds = productIds.substring(0, productIds.length() - 1);

		}
		List jsonList = new ArrayList();
		List<Object[]> labelList = new ArrayList<Object[]>();
		HashMap labelMap = new HashMap();
		if (!productIds.equals("") && null != productIds) {
			labelList = this.mallProductService.searchProductLabel(productIds);
			for (int i = 0; i < labelList.size(); i++) {
				if (labelList.get(i)[0] != null) {
					labelMap.put(labelList.get(i)[0].toString(),
							labelList.get(i));
				}
			}
		}

		Object[] tempObj = new Object[3];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] mallProduct : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", mallProduct[0]);
				jobj.put("name", mallProduct[1]);
				jobj.put("createDate", sdf.format(mallProduct[2]));
				jobj.put("content", mallProduct[3]);
				jobj.put("logo1", mallProduct[5]);
				jobj.put("logo2", mallProduct[6]);
				jobj.put("logo3", mallProduct[7]);
				jobj.put("status", mallProduct[8]);
				jobj.put("mp3", mallProduct[10]);
				jobj.put("mp3Type", mallProduct[11]);
				jobj.put("info", mallProduct[13]);
				jobj.put("catId", mallProduct[12]);
				jobj.put("files", mallProduct[15]);
				if (null == mallProduct[14] || mallProduct[14].equals("")) {
					jobj.put("catName", "无");
				} else {
					jobj.put("catName", mallProduct[14]);
				}

				if (labelMap != null) {

					if (labelMap.get(mallProduct[0].toString()) != null) {

						tempObj = (Object[]) labelMap.get(mallProduct[0]
								.toString());
						if (tempObj[1] != null) {
							jobj.put("labelInfo", tempObj[1]);
						} else {
							jobj.put("labelInfo", "");
						}

						if (tempObj[2] != null) {
							jobj.put("keyword", tempObj[2]);
						} else {
							jobj.put("keyword", "");
						}

					}
				} else {
					jobj.put("labelInfo", "");
					jobj.put("keyword", "");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}

	@RequestMapping(value = "/mallManagerView")
	public void mallManagerView(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		request.getSession().removeAttribute("mallOrder");
		List<Object[]> infoList = this.mallProductService
				.searchMallProductList(request.getParameter("mallProductId"));
		List jsonList = new ArrayList();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] mallProduct : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", mallProduct[0]);
				jobj.put("name", mallProduct[1]);
				jobj.put("logo1", mallProduct[5]);
				jobj.put("content", mallProduct[3]);
				jobj.put("createDate", sdf.format(mallProduct[2]));
				jobj.put("logo1", mallProduct[5]);
				jobj.put("logo2", mallProduct[6]);
				jobj.put("logo3", mallProduct[7]);
				jobj.put("status", mallProduct[8]);
				jobj.put("info", mallProduct[13]);
				jobj.put("mp3", mallProduct[10]);
				jobj.put("mp3Type", mallProduct[11]);
				jobj.put("catId", mallProduct[12]);
				jobj.put("catName", mallProduct[14]);
				List labellist = this.mallProductService
						.searchProductLabel(mallProduct[0].toString());

				if (labellist != null) {
					if (labellist.size() > 0) {
						jobj.put("labelInfo",
								((Object[]) labellist.get(0))[1].toString());
					}
				} else {
					jobj.put("labelInfo", "");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}

	@RequestMapping(value = "/saveMall")
	public String saveMall(
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2,
			@RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4,
			HttpServletRequest request, HttpServletResponse response,
			MallProduct mallProduct) throws Exception {

		if (!"".equals(mallProduct.getId()) && mallProduct.getId() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mallProduct.setCreateDate(sdf.parse(request
					.getParameter("tempDate")));
		} else {
			mallProduct.setCreateDate(new Date());
		}

		if (file1.getSize() != 0) {
			String fileName=file1.getOriginalFilename();
			int random = (int) (Math.random() * 1000000);
			fileName = new Date().getTime()+ random	+ ""+ fileName.subSequence(fileName.indexOf("."), fileName.length());
			String key = QiniuUtil.fileUpdate(file1, fileName);
			fileName = "http://source.fandoutech.com.cn/"+key;
			mallProduct.setLogo1(fileName);
			
			
			
		}

		if (file2.getSize() != 0) {
			String fileName=file2.getOriginalFilename();
			int random = (int) (Math.random() * 1000000);
			fileName = new Date().getTime()+ random	+ ""+ fileName.subSequence(fileName.indexOf("."), fileName.length());
			String key = QiniuUtil.fileUpdate(file2, fileName);
			fileName = "http://source.fandoutech.com.cn/"+key;
			mallProduct.setLogo2(fileName);
		}

		if (file3.getSize() != 0) {
			String fileName=file3.getOriginalFilename();
			int random = (int) (Math.random() * 1000000);
			fileName = new Date().getTime()+ random	+ ""+ fileName.subSequence(fileName.indexOf("."), fileName.length());
			String key = QiniuUtil.fileUpdate(file3, fileName);
			fileName = "http://source.fandoutech.com.cn/"+key;
			mallProduct.setLogo3(fileName);
		}

		if (file4.getSize() != 0) {
			String fileName=file4.getOriginalFilename();
			int random = (int) (Math.random() * 1000000);
			fileName = new Date().getTime()+ random	+ ""+ fileName;
			String key = QiniuUtil.fileUpdate(file4, fileName);
			fileName = "http://source.fandoutech.com.cn/"+key;
			mallProduct.setMp3(fileName);
		}

		String[] names = request.getParameterValues("names");
		String[] prices = request.getParameterValues("prices");
		String[] counts = request.getParameterValues("counts");
	
		////System.out.println(prices.length);
		if (prices != null) {
			if(prices.length ==1)
			{
				mallProduct.setPrice(prices[0]);
			}
			else
			{
				
				
				int[] tempPrice = new int[prices.length];
				for (int i = 0; i < prices.length; i++) {
					
					tempPrice[i] = new Integer(prices[i]);
				}
				
				Arrays.sort(tempPrice);
				mallProduct.setPrice(tempPrice[0] + "-" + tempPrice[tempPrice.length - 1]);
			}


		}

		if (!"".equals(request.getParameter("catId"))
				&& request.getParameter("catId") != null) {
			mallProduct
					.setCatId(Integer.parseInt(request.getParameter("catId")));
		}

		if (!"".equals(request.getParameter("classRoomPackageId"))
				&& request.getParameter("classRoomPackageId") != null) {
			mallProduct.setClassRoomPackage(Integer.parseInt(request.getParameter("classRoomPackageId")));
		}
		
		this.mallProductService.saveMailProduct(mallProduct);

		this.mallProductService.deleteMallSpecifications(mallProduct.getId()
				.toString());
		if (names != null) {
			MallSpecifications mallSpecifications = new MallSpecifications();
			for (int i = 0; i < names.length; i++) {

				mallSpecifications = new MallSpecifications();
				mallSpecifications.setProductId(mallProduct.getId());
				mallSpecifications.setName(names[i]);
				mallSpecifications.setPrice(new Double(prices[i]));
				mallSpecifications.setCount(new Integer(counts[i]));
				this.mallProductService
						.saveMallSpecifications(mallSpecifications);

			}
		}

		String labelIds = request.getParameter("labelId");
		String[] tempIds = labelIds.split(",");
		this.mallProductService.deleteProductLabel(mallProduct.getId()
				.toString());
		if (!"".equals(labelIds) && labelIds != null) {
			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "20");
			map.put("type", "0");

			List<Label> labelList = new ArrayList();

			
			Page tempResult = this.bookService.searchLabel(map);
			labelList = tempResult.getItems();
			
			HashMap labelMap = new HashMap();

			for (int i = 0; i < labelList.size(); i++) {
				labelMap.put(labelList.get(i).getName().toString(), labelList
						.get(i).getId().toString());
			}

			ProductLabel productLabel = new ProductLabel();
			
			for (int i = 0; i < tempIds.length; i++) {

				if (labelMap.get(tempIds[i].toString()) != null) {
					productLabel = new ProductLabel();
					productLabel.setProductId(mallProduct.getId());
					productLabel.setLabelId(new Integer(labelMap.get(
							tempIds[i].toString()).toString()));
					
					this.mallProductService.saveProductLabel(productLabel);
					
				}

			}
		}

		String tempKeyword = request.getParameter("tempName");
		if (!"".equals(tempKeyword) && tempKeyword != null) {
			this.mallProductService.deleteMallProductKeyword(mallProduct
					.getId().toString());
			String[] keyword = tempKeyword.split(",");
			MallProductKeyword mallProductKeyword = new MallProductKeyword();
			for (int i = 0; i < keyword.length; i++) {

				if (keyword[i] != null) {
					mallProductKeyword = new MallProductKeyword();
					mallProductKeyword.setProductId(mallProduct.getId()
							.toString());
					mallProductKeyword.setName(keyword[i]);
					this.mallProductService
							.saveMallProductKeyword(mallProductKeyword);
				}
			}

		}
		// 存储课程信息到课程表
		String fileNames = request.getParameter("fileNames");
		if (!"".equals(fileNames) && fileNames != null) {
			String[] fileName = fileNames.split(",");
			Course course = new Course();
			String tempFileName = "";
			int random = 0;
			for (int i = 0; i < fileName.length; i++) {

				if (fileName[i] != null) {
					tempFileName = fileName[i].subSequence(0,
							fileName[i].indexOf("."))
							+ "";

					course = new Course();
					course.setProductId(mallProduct.getId());
					course.setName(tempFileName);
					random = (int) (Math.random() * 1000000);

					// course.setUrl(Keys.STAT_NAME+"/wechat/wechatImages/book/mp3/"+tempFileName.replaceAll(" ",""));
					course.setUrl(Keys.STAT_NAME
							+ "/wechat/wechatImages/book/mp3/" + tempFileName);
					this.mallProductService.saveCourse(course);

				}
			}
		}

		this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
		return "redirect:mallManager";

	}

	@RequestMapping(value = "/deleteMall")
	public String deleteMall(HttpServletRequest request,
			HttpServletResponse response) {

		MallProduct mallProduct = new MallProduct();
		mallProduct.setId(new Integer(request.getParameter("mallId")));
		mallProduct = this.mallProductService.getMallProduct(mallProduct);
		File deleteFile = null;
		if (mallProduct.getLogo1() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH + "mall/"
					+ mallProduct.getLogo1());
			deleteFile.delete();
		}
		if (mallProduct.getLogo2() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH + "mall/"
					+ mallProduct.getLogo2());
			deleteFile.delete();
		}
		if (mallProduct.getLogo3() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH + "mall/"
					+ mallProduct.getLogo3());
			deleteFile.delete();
		}

		this.mallProductService.deleteMallProduct(mallProduct);

		this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
		return "redirect:mallManager";
	}

	@RequestMapping(value = "/updateStatus")
	public String updateStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userId = RedisKeys.REDIS_USER+MyCookie.getCookie(RedisKeys.ADMIN_USER, request);

		User user = RedisUtil.getUser(userId);
		
		this.mallProductService.updateMallOrderStatus(
				request.getParameter("orderId"), user.getId(),
				request.getParameter("express"));

		String kuaidi = request.getParameter("kuaidi");
		String danhao = request.getParameter("danhao");
		MallOrder mallOrder = this.mallProductService.getMallOrder(request
				.getParameter("orderId"));
		Member member = new Member();
		member.setId(mallOrder.getMemberId());
		member = this.memberService.getMember(member);

		NoticeInfo noticeInfo = new NoticeInfo();
		String accessToken = this.accountService.getAccessToken(Keys.APP_ID,
				Keys.APP_SECRET);
		noticeInfo.setAccessToKen(accessToken);
		noticeInfo.setOpenId(member.getOpenId());
		noticeInfo.setMemberId(member.getId().toString());
		noticeInfo.setFirst("亲爱滴会员，您的宝贝已委托快递大哥护送出门：");
		noticeInfo.setKeyword1(request.getParameter("userName"));
		noticeInfo.setKeyword2(request.getParameter("address"));
		noticeInfo.setKeyword3(mallOrder.getOrderNumber());
		noticeInfo.setKeyword4(request.getParameter("kuaidi"));
		noticeInfo.setKeyword5(request.getParameter("danhao"));
		noticeInfo.setRemark("若有疑问请致电客服中心:" + Keys.CUSTOMER_MOBLIE);
		noticeInfo.setUrl("http://m.kuaidi100.com/index_all.html?type="
				+ request.getParameter("kuaidi") + "&postid="
				+ request.getParameter("danhao") + "#result");
		WeChatUtil.sendOrderExpress(noticeInfo); // 接受返回的json格式数据

		return "redirect:mallOrderManager";
	}

	@RequestMapping("/orderServiceManager")
	public String orderServiceManager(HttpServletRequest request,
			QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("number", queryDto.getNumber());
		map.put("status", queryDto.getStatus());

		Page resultPage = this.mallProductService.searchMallOrderService(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		List<MallOrderService> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		List tempList = new ArrayList();
		List<MallOrderProduct> mallProductList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		HashMap operatorMap = this.userService.getOperatorNameMap();
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (MallOrderService orderService : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", orderService.getId());
				jobj.put("orderId", orderService.getOrderId());
				jobj.put("orderNumber", orderService.getOrderNumber());
				jobj.put("express", orderService.getExpress());
				jobj.put("expressNumber", orderService.getExpressNumber());
				jobj.put("remarks", orderService.getRemarks());
				jobj.put("memberId", orderService.getMemberId());
				jobj.put("createDate", orderService.getCreateDate().toString()
						.substring(0, 12));
				jobj.put("payment", orderService.getPayment());
				jobj.put("type", orderService.getType());
				jobj.put("status", orderService.getStatus());
				if (orderService.getOperatorId() != 0) {

					if (operatorMap
							.get(orderService.getOperatorId().toString()) != null) {
						jobj.put(
								"operatorName",
								operatorMap
										.get(orderService.getOperatorId()
												.toString()).toString());
					} else {
						jobj.put("operatorName", "");
					}

				} else {
					jobj.put("operatorName", "");
				}

				if (orderService.getStatus() == 0) {
					jobj.put("statusName", "待处理");
				} else {
					jobj.put("statusName", "已处理");
				}
				if (orderService.getType() == 0) {
					jobj.put("typeName", "退款");
				} else {
					jobj.put("typeName", "退货");
				}

				mallProductList = this.mallProductService
						.searchMallOrderProduct(orderService.getOrderId()
								.toString());
				if (mallProductList.size() > 0) {
					tempList = new ArrayList();
					for (MallOrderProduct orderProduct : mallProductList) {

						JSONObject tempJobj = new JSONObject();
						tempJobj.put("productName",
								orderProduct.getProductName());
						tempJobj.put("specifications",
								orderProduct.getSpecifications());
						tempJobj.put("price", orderProduct.getPrice());
						tempJobj.put("count", orderProduct.getCount());
						tempJobj.put("productImg", orderProduct.getProductImg());
						tempList.add(tempJobj);

					}
					jobj.put("productList", tempList);
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		return "mall/orderServiceManager";
	}

	@RequestMapping("/deleteMemberKeyword")
	public void deleteMemberKeyword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		try {
			this.memberService.deleteMemberKeyword(request
					.getParameter("memberId"));

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);

	}

	@RequestMapping(value = "/updateOrderServiceStatus")
	public String updateOrderServiceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//System.out.println("id = " + request.getParameter("id"));
		//System.out.println("orderId = " + request.getParameter("orderId"));
		
		this.mallProductService.updateOrderStatus(
				request.getParameter("orderId"), "5");
		
		String userId = RedisKeys.REDIS_USER
				+ MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User user =new User();
		try {
			user = RedisUtil.getUser(userId);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		
		this.mallProductService.updateMallOrderService(
				request.getParameter("id"), "1", user.getId().toString());

		return "redirect:orderServiceManager";
	}

	@RequestMapping("/mallBannerManager")
	public String mallBannerManager(HttpServletRequest request,
			QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");

		map.put("name", queryDto.getName());

		Page resultPage = this.mallProductService.searchMallBanner(map);

		map = new HashMap();
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("infoList", resultPage.getItems());

		request.setAttribute("jsonStr", jsonObj.toString());
		return "mall/mallBannerManager";
	}

	@RequestMapping(value = "/saveMallBanner")
	public String saveMallBanner(HttpServletRequest request,
			HttpServletResponse response, MallBanner mallBanner,
			@RequestParam(value = "file1", required = false) MultipartFile file1)
			throws Exception {

		if (file1.getSize() != 0) {
			String fileName1 = "";
			fileName1 = file1.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH + "mall/");
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			int random = (int) (Math.random() * 1000000);
			fileName1 = new Date().getTime()
					+ random
					+ ""
					+ fileName1.subSequence(fileName1.indexOf("."),
							fileName1.length());

			String pathName = Keys.USER_PIC_PATH + "mall/";
			File targetFile = new File(pathName, fileName1);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			mallBanner.setBanner(fileName1);

			// 保存
			try {
				file1.transferTo(targetFile);
				File deleteFile = new File(Keys.USER_PIC_PATH + "mall/"
						+ request.getParameter("oldLogo"));

				if (!"".equals(request.getParameter("oldLogo"))
						&& request.getParameter("oldLogo") != null) {
					deleteFile.delete();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			if (!"".equals(request.getParameter("logo"))
					&& request.getParameter("logo") != null) {
				mallBanner.setBanner(request.getParameter("logo"));
			} else {
				mallBanner.setBanner("");
				File deleteFile = new File(Keys.USER_PIC_PATH + "mall/"
						+ request.getParameter("oldLogo"));
				deleteFile.delete();
			}
		}

		this.mallProductService.saveMallBanner(mallBanner);

		return "redirect:mallBannerManager";
	}

	@RequestMapping(value = "/deleteMallBanner")
	public String deleteMallBanner(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.mallProductService.deleteMallBanner(request
				.getParameter("bannerId"));

		return "redirect:mallBannerManager";
	}

	@RequestMapping(value = "/saveMallLabel")
	public String saveMallLabel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String labelIds = (String) request.getSession()
				.getAttribute("labelIds");
		if (request.getSession().getAttribute("labelIds") != null) {
			labelIds = (String) request.getSession().getAttribute("labelIds");
		}
		HashMap map = new HashMap();
		String[] tempLabelIds = labelIds.split(",");
		for (int i = 0; i < tempLabelIds.length; i++) {

			map.put(tempLabelIds[i].toString(), "");
		}
		MallLabel mallLabel = new MallLabel();
		String ids = request.getParameter("labelId");
		if (!"".equals(ids) && ids != null) {
			String[] tempIds = ids.split(",");

			for (int i = 0; i < tempIds.length; i++) {

				mallLabel = new MallLabel();
				mallLabel.setLabelId(new Integer(tempIds[i]));
				if (map.get(tempIds[i].toString()) == null) {
					this.mallProductService.saveMallLabel(mallLabel);
				}

			}
		}

		return "redirect:mallLabelManager";
	}

	@RequestMapping("/mallLabelManager")
	public String mallLabelManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");

		map.put("name", queryDto.getName());

		Page resultPage = this.mallProductService.searchMallLabel(map);

		map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "100");
		Page labelPage = this.bookService.searchLabel(map);
		request.setAttribute("labelPage", labelPage);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		List tempList = new ArrayList();

		String labelIds = "";

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] orderService : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", orderService[0]);
				jobj.put("name", orderService[1]);
				jobj.put("ios", orderService[3]);
				jobj.put("android", orderService[4]);
				jobj.put("wechat", orderService[5]);
				if ("0".equals(orderService[3].toString())) {
					jobj.put("iosName", "<font color='green'>显示</font>");
				} else {
					jobj.put("iosName", "<font color='red'>不显示</font>");
				}

				if ("0".equals(orderService[4].toString())) {
					jobj.put("androidName", "<font color='green'>显示</font>");
				} else {
					jobj.put("androidName", "<font color='red'>不显示</font>");
				}

				if ("0".equals(orderService[5].toString())) {
					jobj.put("wechatName", "<font color='green'>显示</font>");
				} else {
					jobj.put("wechatName", "<font color='red'>不显示</font>");
				}

				labelIds = labelIds + orderService[2] + ",";
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.getSession().setAttribute("labelIds", labelIds);
		return "mall/mallLabelManager";
	}

	@RequestMapping(value = "/updateMallLabel")
	public String updateMallLabel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String ios = "1";
		if (!"".equals(request.getParameter("ios"))
				&& request.getParameter("ios") != null) {
			ios = "0";
		}
		String wechat = "1";
		if (!"".equals(request.getParameter("wechat"))
				&& request.getParameter("wechat") != null) {
			wechat = "0";
		}
		String android = "1";
		if (!"".equals(request.getParameter("android"))
				&& request.getParameter("android") != null) {
			android = "0";
		}
		this.mallProductService.updateMallabel(request.getParameter("id"), ios,
				wechat, android);

		return "redirect:mallLabelManager";
	}

	@RequestMapping(value = "/deleteMallLabel")
	public String deleteMallLabel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.mallProductService.deleteMallLabel(request.getParameter("id"));

		return "redirect:mallLabelManager";
	}

	@RequestMapping({ "/updateMallBannerStatus" })
	public String updateMallBannerStatus(HttpServletRequest request,
			HttpServletResponse response) {

		this.mallProductService.updateMallBannerStatus(
				request.getParameter("bannerId"),
				request.getParameter("tempStatus"));
		return "redirect:mallBannerManager";

	}

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		// //System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	
	
	@RequestMapping({ "/searchMallProductByCatId" })
	public String searchMallProductByCatId(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
			
		jsonObj.put("infoList", this.mallProductService.searchMallProductByCatId(request.getParameter("catId")));
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return null;
	}
	
	
	
	@RequestMapping({ "/updateMallProductCatSort" })
	public String updateMallProductCatSort(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		
		String[] tempIds =request.getParameterValues("tempIds"); 
		String[] tempSort =request.getParameterValues("tempSort");
		MallProduct mallProduct = new MallProduct();
		List list = new ArrayList();
		for (int i = 0; i < tempIds.length; i++) {
			
			mallProduct = new MallProduct();
			mallProduct.setId(new Integer(tempIds[i].toString()));
			mallProduct.setAccountId(new Integer(tempSort[i].toString()));
			list.add(mallProduct);
			
		}
		this.mallProductService.updateMallProductCatSort(list);
		return "redirect:mallCategory";
	}
	
	public static void main(String[] args) {
		
		
	  try {
          InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址
          //System.out.println(address);//PC-20140317PXKX/192.168.0.121
          //System.out.println(address.getHostAddress());//192.168.0.121
          //System.out.println("===============");
          InetAddress address1=InetAddress.getByName("wechat.fandoutech.com.cn");//获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地址
          //System.out.println(address1);//www.wodexiangce.cn/124.237.121.122
          //System.out.println(address1.getHostAddress());//124.237.121.122
          //System.out.println("===============");
          InetAddress[] addresses=InetAddress.getAllByName("www.baidu.com");//根据主机名返回其可能的所有InetAddress对象
          for(InetAddress addr:addresses){
              //System.out.println(addr);//www.baidu.com/14.215.177.38
                                      //www.baidu.com/14.215.177.37
          }
      } catch (UnknownHostException e) {
          e.printStackTrace();
      }
	  
	  
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

}
