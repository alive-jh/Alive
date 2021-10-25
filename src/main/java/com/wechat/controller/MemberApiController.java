package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.service.IntegralService;
import com.wechat.service.LessonService;
import com.wechat.service.MallProductService;
import com.wechat.service.MemberService;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@RequestMapping("/memberApi")
public class MemberApiController {

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

	@Resource
	private IntegralService integralService;

	@Resource
	private LessonService lessonService;

	public LessonService getLessonService() {
		return lessonService;
	}

	public void setLessonService(LessonService lessonService) {
		this.lessonService = lessonService;
	}

	public IntegralService getIntegralService() {
		return integralService;
	}

	public void setIntegralService(IntegralService integralService) {
		this.integralService = integralService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/memberManager")
	public void memberManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		String page = "1";
		String rowsPerPage = "20";
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			page = request.getParameter("page");
		}
		if (!"".equals(request.getParameter("rowsPerPage"))
				&& request.getParameter("rowsPerPage") != null) {
			rowsPerPage = request.getParameter("rowsPerPage");
		}

		map.put("type", 2);
		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);
		Page resultPage = this.memberService.searchMember(map);
		List<Member> infoList = resultPage.getItems();
		request.setAttribute("infoList", infoList);
		List jsonList = new ArrayList();
		Object[] tempProduct = null;
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			for (Member member : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", member.getId());
				jobj.put("sex", member.getSex());
				jobj.put("province", member.getProvince());
				jobj.put("city", member.getCity());
				jobj.put("nickName", member.getNickName());
				jobj.put("openId", member.getOpenId());
				jobj.put("headimgurl", member.getHeadImgUrl());
				jobj.put("createDate", member.getCreateDate().toString());
				jobj.put("type", member.getType());

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}

	@RequestMapping("/mallManagerView")
	public void mallManagerView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Object[] productInfo = this.mallProductService
				.searchMallProductInfo(request.getParameter("mallProductId"));
		request.setAttribute("tempProduct", productInfo);
		JSONObject jobj = new JSONObject();
		jobj.put("id", productInfo[0]);
		jobj.put("name", productInfo[1]);
		jobj.put("logo1", productInfo[2]);
		jobj.put("logo2", productInfo[3]);
		jobj.put("logo3", productInfo[4]);

		jobj.put("content", productInfo[5]);
		jobj.put("price", productInfo[6]);

		jobj.put("info", productInfo[7].toString().replaceAll(">", ":"));
		if (productInfo[8] != null) {
			jobj.put("saleCount", productInfo[8]);
		} else {
			jobj.put("saleCount", "0");
		}
		if (productInfo[9] != null) {
			jobj.put("viewCount", productInfo[9]);
		} else {
			jobj.put("viewCount", "0");
		}

		SaleProductLog saleProductLog = new SaleProductLog();
		String[] tempStr = null;
		List<SaleProductLog> saleList = new ArrayList();
		if (productInfo[10] != null) {

			tempStr = productInfo[10].toString().split(",");
			for (int i = 0; i < tempStr.length; i++) {

				saleProductLog = new SaleProductLog();
				saleProductLog
						.setMemberName(tempStr[i].toString().split(">")[0]
								.toString().substring(0, 1) + "***");
				saleProductLog
						.setCreateDate(tempStr[i].toString().split(">")[2]
								.toString().substring(5, 16));
				saleProductLog.setCount(tempStr[i].toString().split(">")[1]);

				saleList.add(saleProductLog);

			}
			jobj.put("saleProductList", saleList);

		} else {
			jobj.put("saleProductList", "");
		}

		List jsonList = new ArrayList();
		jsonList.add(jobj);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("infoList", jsonList);

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}

	@RequestMapping(value = "/shoppingCartManager")
	public void shoppingCartManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<ShoppingCart> infoList = this.mallProductService
				.searchShoppingCart(request.getParameter("memberId"));

		for (int i = 0; i < infoList.size(); i++) {

			infoList.get(i).setProductImg(
					Keys.STAT_NAME + "/wechat/wechatImages/mall/"
							+ infoList.get(i).getProductImg());
		}

		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {

			jsonObj.put("infoList", infoList);
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}

	@RequestMapping(value = "/saveShoppingCart")
	public String saveShoppingCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";
		try {
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setPrice(new Double(request.getParameter("price")));
			shoppingCart.setCount(new Integer(request.getParameter("count")));
			shoppingCart.setSpecifications(request
					.getParameter("specifications"));
			shoppingCart.setMemberId(new Integer(request
					.getParameter("memberId")));
			shoppingCart.setProductId(new Integer(request
					.getParameter("productId")));
			shoppingCart.setProductImg(request.getParameter("productImg"));
			shoppingCart.setProductName(request.getParameter("productName"));

			List<ShoppingCart> list = this.mallProductService
					.searchShoppingCart(request.getParameter("memberId"));
			HashMap map = new HashMap();
			for (int i = 0; i < list.size(); i++) {

				map.put(list.get(i).getProductId() + "-"
						+ list.get(i).getSpecifications(), list.get(i).getId());
			}

			if (map.get(shoppingCart.getProductId() + "-"
					+ shoppingCart.getSpecifications()) == null) {
				this.mallProductService.saveShoppingCart(shoppingCart);
			} else {
				this.mallProductService.updateCount(
						map.get(shoppingCart.getProductId() + "-"
								+ shoppingCart.getSpecifications()).toString(),
						shoppingCart.getCount().toString());
			}
		} catch (Exception e) {

			jsonStr = "{\"status\":\"error\"}";
		}

		response.setContentType("application/json;charset=utf-8");
		response.getWriter().println(jsonStr);
		return null;
	}

	@RequestMapping(value = "/addressManager")
	public void addressManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List infoList = this.mallProductService.searchUserAddress(request
				.getParameter("memberId"));

		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {

			jsonObj.put("infoList", infoList);
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

	}

	@RequestMapping(value = "/getMemberId")
	public void getMemberId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();
		map.put("memberId", request.getParameter("memberId"));
		map.put("mobile", request.getParameter("mobile"));

		String memberId = this.memberService.getMemberId(map);

		String jsonStr = "{\"memberId\":\"" + memberId + "\"}";

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr.toString());

	}

	@RequestMapping(value = "/saveAddress")
	public void saveAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";
		try {

			UserAddress userAddress = new UserAddress();
			JSONObject jsonObject = WeChatUtil.getLngAndLat(userAddress
					.getAddress());

			if (!"".equals(request.getParameter("userAddressId"))
					&& request.getParameter("userAddressId") != null) {
				userAddress.setId(new Integer(request
						.getParameter("userAddressId")));
			}

			userAddress.setUserName(request.getParameter("userName"));
			userAddress.setAddress(request.getParameter("address"));
			userAddress.setArea(request.getParameter("area"));
			userAddress.setMemberId(new Integer(request
					.getParameter("memberId")));
			userAddress.setMobile(request.getParameter("mobile"));
			userAddress.setStatus(new Integer(request.getParameter("status")));
			if (jsonObject.toString().lastIndexOf("Error") == -1) {
				userAddress.setLat(jsonObject.getJSONObject("result")
						.getJSONObject("location").getString("lat"));
				userAddress.setLng(jsonObject.getJSONObject("result")
						.getJSONObject("location").getString("lng"));

			}
			this.mallProductService.saveUserAddress(userAddress);
			List list = this.mallProductService
					.searchUserAddressStatus(userAddress.getMemberId()
							.toString());
			if (list.size() == 1) {
				this.mallProductService.updateAddressStatus(userAddress.getId()
						.toString(), userAddress.getMemberId().toString());// 修改默认收货地址
			}
			if (userAddress.getStatus() == 0) {
				this.mallProductService.updateAddressStatus(userAddress.getId()
						.toString(), userAddress.getMemberId().toString());// 修改默认收货地址
			}
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);

	}

	@RequestMapping(value = "/saveMemberAccount")
	public void saveMemberAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";
		if (!"".equals(request.getParameter("mobile"))
				&& request.getParameter("mobile") != null
				&& !"".equals(request.getParameter("pwd"))
				&& request.getParameter("pwd") != null) {
			Member member = new Member();
			member = this.memberService.getMemberByMobile(request
					.getParameter("mobile"));
			if (member.getId() == null) {
				try {
					member.setNickName(request.getParameter("mobile"));
					member.setMobile(request.getParameter("mobile"));
					member.setCreateDate(new Date());
					member.setType(2);
					this.memberService.saveMember(member);
					MemberAccount memberAccount = new MemberAccount();
					memberAccount.setMemberId(member.getId());
					memberAccount.setAccount(request.getParameter("mobile"));
					memberAccount.setPassword(MD5UTIL.encrypt(request
							.getParameter("pwd")));
					memberAccount.setStatus(0);
					memberAccount.setNickName(request.getParameter("nickName"));
					if (!"".equals(request.getParameter("type"))
							&& request.getParameter("type") != null) {
						memberAccount.setType(new Integer(request
								.getParameter("type")));
					}

					memberAccount.setCreateDate(new Date());
					this.memberService.saveMemberAccount(memberAccount);
				} catch (Exception e) {
					jsonStr = "{\"status\":\"error\"}";
				}
			} else {
				boolean isExitesPasswd = this.memberService
						.checkPasswdIsExites(request
								.getParameter("mobile"));
				if (false == isExitesPasswd) {
					MemberAccount memberAccount = new MemberAccount();
					memberAccount.setMemberId(member.getId());
					memberAccount.setAccount(request.getParameter("mobile"));
					memberAccount.setPassword(MD5UTIL.encrypt(request
							.getParameter("pwd")));
					memberAccount.setStatus(0);
					memberAccount.setNickName(request.getParameter("nickName"));
					if (!"".equals(request.getParameter("type"))
							&& request.getParameter("type") != null) {
						memberAccount.setType(new Integer(request
								.getParameter("type")));
					}

					memberAccount.setCreateDate(new Date());
					this.memberService.saveMemberAccount(memberAccount);

				} else {

					jsonStr = "{\"status\":\"error\",\"message\":\"该手机号码已被注册,请重新输入!\"}";
				}
			}
		} else {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/getMember")
	public void getMember(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "";

		Member member = new Member();
		member = this.memberService.getMemberByMobile(request
				.getParameter("mobile"));

		boolean isExitesPasswd = this.memberService.checkPasswdIsExites(request
				.getParameter("mobile"));
		if (member.getId() != null && true == isExitesPasswd) {
			JSONObject jsonObj = new JSONObject();
			if (null != member.getCity()) {
				jsonObj.put("city", member.getCity());
			} else {
				jsonObj.put("city", "");
			}
			jsonObj.put(
					"createDate",
					member.getCreateDate()
							.toString()
							.substring(
									0,
									member.getCreateDate().toString().length() - 2));

			if (null != member.getHeadImgUrl()) {
				jsonObj.put("headImgUrl", member.getHeadImgUrl());
			} else {
				jsonObj.put("headImgUrl", "");
			}

			jsonObj.put("id", member.getId());

			jsonObj.put("mobile", member.getMobile());

			if (null != member.getNickName()) {
				jsonObj.put("nickName", member.getNickName());
			} else {
				jsonObj.put("nickName", "");
			}

			if (null != member.getOpenId()) {
				jsonObj.put("openId", member.getOpenId());
			} else {
				jsonObj.put("openId", "");
			}
			if (null != member.getProvince()) {
				jsonObj.put("province", member.getProvince());
			} else {
				jsonObj.put("province", "");
			}

			if (null != member.getSex()) {
				jsonObj.put("sex", member.getSex());
			} else {
				jsonObj.put("sex", "");
			}

			if (null != member.getType()) {
				jsonObj.put("type", member.getType());
			} else {
				jsonObj.put("type", "");
			}

			MemberAccount memberAccount = this.memberService
					.searchMemberAccountByMemberId(member.getId().toString());
			Integer integralCount = this.integralService.getIntegralCount(
					member.getId().toString(), memberAccount.getType()
							.toString());// 积分总数

			jsonObj.put("integralCount", integralCount);

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("memberId", member.getId().toString());
			ArrayList<ClassTeacher> classTeachers = this.lessonService
					.findClassTeachers(map);
			if (classTeachers.size() > 0) {
				jsonObj.put("teacherId", classTeachers.get(0).getId());
				jsonObj.put("teacherName", classTeachers.get(0).getName());
				jsonObj.put("teacherLevel", classTeachers.get(0).getLevel());
			} else {
				jsonObj.put("teacherId", 0);
				jsonObj.put("teacherName", "");
				jsonObj.put("teacherLevel", 1);
			}

			List jsonLlist = new ArrayList();
			jsonLlist.add(jsonObj);
			JSONObject jobj = new JSONObject();
			jobj.put("infoList", jsonLlist);
			jsonStr = jobj.toString();
		} else {

			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/saveMember")
	public void saveMember(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		Member member = new Member();
		try {

			member.setId(new Integer(request.getParameter("memberId")));

			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			member.setCity(request.getParameter("city"));
			member.setMobile(request.getParameter("mobile"));
			
			String createDate = request.getParameter("createDate");
			
			if(createDate.indexOf("-")>-1){
				member.setCreateDate(sdf.parse(createDate));
			}else{
				member.setCreateDate(new Date(Long.parseLong(createDate)));
			}
				
			member.setHeadImgUrl(request.getParameter("headImgUrl"));
			member.setSex(request.getParameter("sex"));
			member.setNickName(request.getParameter("nickName"));
			member.setOpenId(request.getParameter("openId"));
			member.setProvince(request.getParameter("province"));
			member.setType(2);
			this.memberService.saveMember(member);

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/updateMemberPwd")
	public void updateMemberPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		try {
			if (!"".equals(request.getParameter("memberId"))
					&& request.getParameter("memberId") != null
					&& !"".equals(request.getParameter("pwd"))
					&& request.getParameter("pwd") != null) {
				this.memberService.updateMemberAccountPwd(
						request.getParameter("memberId"),
						request.getParameter("pwd"));
			} else {
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
			}

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/saveMemberChildren")
	public void saveMemberChildren(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		MemberChildren memberChildren = new MemberChildren();

		if (!"".equals(request.getParameter("memberId"))
				&& request.getParameter("memberId") != null
				&& !"".equals(request.getParameter("age"))
				&& request.getParameter("age") != null
				&& !"".equals(request.getParameter("sex"))
				&& request.getParameter("sex") != null)

		{
			try {

				if (!"".equals(request.getParameter("id"))
						&& request.getParameter("id") != null) {
					memberChildren
							.setId(new Integer(request.getParameter("id")));
				}
				memberChildren.setMemberId(new Integer(request
						.getParameter("memberId")));
				memberChildren.setAge(request.getParameter("age"));
				//System.out.println(" age = " + memberChildren.getAge());
				memberChildren.setSex(new Integer(request.getParameter("sex")));
				memberChildren.setInterest(request.getParameter("interest"));
				this.memberService.saveMemberChildren(memberChildren);
			} catch (Exception e) {
				jsonStr = "{\"status\":\"error\"}";
			}
		} else {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/getMemberChildren")
	public void getMemberChildren(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "";

		MemberChildren memberChildren = new MemberChildren();
		memberChildren = this.memberService.getMemberChildren(request
				.getParameter("memberId"));
		if (memberChildren.getId() != null) {

			JSONObject jsonObj = new JSONObject();

			jsonObj.put("infoList", memberChildren);
			jsonStr = jsonObj.toString();
		} else {

			jsonStr = "{\"status\":\"error\",\"message\":\"暂无记录!\"}";

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	/*
	 * 
	 * 用户登录 001
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response, User user) throws Exception {
		String account = ParameterFilter.emptyFilter("", "account", request);
		String pwd = ParameterFilter.emptyFilter("", "pwd", request);
		JSONObject result = new JSONObject();
		if (!"".equals(account) && !"".equals(pwd) && null != account
				&& null != pwd) {
			if(pwd.length() == 32){
				
			}else{
				pwd = MD5UTIL.encrypt(pwd);
			}
			MemberAccount memberAccount = this.memberService.login(account,
					pwd);
			if (memberAccount.getId() != null) {

				result.put("status", "ok");
			} else {
				result.put("status", "error");
				result.put("message", "账号或密码不正确,请重新输入!");
			}

		} else {
			result.put("status", "error");
			result.put("message", "缺少参数!");
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(result);
		return null;
	}

	/*
	 * 通过手机号码判断用户是否注册(安卓使用)
	 */
	@RequestMapping(value = "/searchMemberByMobile")
	public void searchMemberByMobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonStr = new JSONObject();
		String mobile = ParameterFilter.emptyFilter("", "mobile", request);
		if (!"".equals(mobile) && null != mobile) {
			Member member = new Member();
			member = this.memberService.getMemberByMobile(mobile);
			if (member.getId() != null) {
				boolean isExitesPasswd = this.memberService
						.checkPasswdIsExites(mobile);
				if (false == isExitesPasswd) {
					jsonStr.put("status", "0");
				} else {
					jsonStr.put("status", "1");
				}

			} else {
				jsonStr.put("status", "0");
			}
		} else {
			jsonStr.put("status", "error");
			jsonStr.put("message", "缺少参数!");
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/saveMemberPayment")
	public void saveMemberPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		try {

			if (!"".equals(request.getParameter("memberId"))
					&& request.getParameter("memberId") != null
					&& !"".equals(request.getParameter("payment"))
					&& request.getParameter("payment") != null
					&& !"".equals(request.getParameter("orderNumber"))
					&& request.getParameter("orderNumber") != null) {
				MemberPayment memberPayment = new MemberPayment();
				memberPayment.setCreateDate(new Date());
				memberPayment.setPayment(new Double(request
						.getParameter("payment")));
				memberPayment.setOrderNumber(request
						.getParameter("orderNumber"));
				memberPayment.setStatus(0);
				memberPayment.setMemberId(new Integer(request
						.getParameter("memberId")));
				MemberAccount memberAccount = this.memberService
						.searchMemberAccountByMemberId(request
								.getParameter("memberId"));
				memberPayment.setType(memberAccount.getType());
				this.memberService.saveMemberPayment(memberPayment);

				Integral integral = new Integral();
				integral.setCreateDate(new Date());
				integral.setMemberId(new Integer(request
						.getParameter("memberId")));
				integral.setFraction(new Integer(request
						.getParameter("payment")) * 100);
				integral.setTypeId(1);
				integral.setStatus(0);
				integral.setMemberType(memberAccount.getType());
				integral.setRemark("订单编号:" + memberPayment.getOrderNumber()
						+ ",充值金额:" + memberPayment.getPayment() + "元!");
				this.memberService.saveMemberIntegar(integral);

			} else {
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
			}

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/consumptionMemberPayment")
	public void consumptionMemberPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		try {

			if (!"".equals(request.getParameter("memberId"))
					&& request.getParameter("memberId") != null
					&& !"".equals(request.getParameter("payment"))
					&& request.getParameter("payment") != null
					&& !"".equals(request.getParameter("productId"))
					&& request.getParameter("productId") != null) {

				MemberAccount memberAccount = this.memberService
						.searchMemberAccountByMemberId(request
								.getParameter("memberId"));

				MallProduct mallProduct = this.mallProductService
						.getMallProductById(request.getParameter("productId"));
				Integral integral = new Integral();
				integral.setCreateDate(new Date());
				integral.setMemberId(new Integer(request
						.getParameter("memberId")));
				integral.setFraction(-new Integer(request
						.getParameter("payment")));
				integral.setTypeId(4);
				integral.setStatus(0);
				integral.setMemberType(memberAccount.getType());
				integral.setRemark("购买音频:" + mallProduct.getName());
				this.memberService.saveMemberIntegar(integral);

			} else {
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
			}

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/integralManager")
	public String integralManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MemberAccount memberAccount = this.memberService
				.searchMemberAccountByMemberId(request.getParameter("memberId"));

		List<Object[]> list = this.integralService.searchIntegralList(request
				.getParameter("memberId"), memberAccount.getType().toString());

		request.setAttribute("tempList", list);
		Integer integralCount = this.integralService.getIntegralCount(request
				.getParameter("memberId"), memberAccount.getType().toString());
		request.setAttribute("integralCount", integralCount);
		return "member/integralManager";

	}

	@RequestMapping("/searchMemberIntegral")
	public void seaechMall(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

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
			rowsPerPage = request.getParameter("rowsPerPage");
		}
		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);
		map.put("memberId", request.getParameter("memberId"));
		map.put("type", request.getParameter("type"));

		MemberAccount memberAccount = this.memberService
				.searchMemberAccountByMemberId(request.getParameter("memberId"));
		Integer integralCount = this.integralService.getIntegralCount(request
				.getParameter("memberId"), memberAccount.getType().toString());

		map.put("type", memberAccount.getType().toString());
		;
		Page resultPage = this.memberService.searchMemberIntegral(map);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jobj = new JSONObject();
		if (infoList != null) {

			for (int i = 0; i < infoList.size(); i++) {

				jobj = new JSONObject();
				if ("1".equals(infoList.get(i)[3].toString())) {
					jobj.put("fraction", "-" + infoList.get(i)[3]);
				} else {
					jobj.put("fraction", infoList.get(i)[3]);
				}

				jobj.put("createDate",
						infoList.get(i)[4].toString().substring(0, 10));
				jobj.put("typeName", infoList.get(i)[5]);
				jobj.put("remark", infoList.get(i)[6]);

				jsonList.add(jobj);
			}
			jsonObject.accumulate("infoList", jsonList);
			jsonObject.put("integralCount", integralCount);
			jsonObject.put("pageCount", resultPage.getTotalPageCount());
			jsonObject.put("totalCount", resultPage.getTotalCount());

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObject.toString());

	}

	@RequestMapping(value = "/saveAccountInfo")
	public void saveAccountInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		AccountInfo accountInfo = new AccountInfo();
		try {

			accountInfo.setId(new Integer(request.getParameter("memberId")));
			accountInfo.setImg(request.getParameter("accountInfo"));
			accountInfo.setNickName(request.getParameter("nickName"));
			accountInfo.setIntroduce(request.getParameter("introduce"));
			accountInfo.setSex(request.getParameter("sex"));
			this.memberService.saveAccountInfo(accountInfo);

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/getAccountInfo")
	public void getAccountInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "";

		AccountInfo accountInfo = this.memberService.getAccountInfo(request
				.getParameter("memberId"));

		if (accountInfo.getId() != null) {

			JSONObject jsonObj = new JSONObject();

			jsonObj.put("infoList", accountInfo);
			jsonStr = jsonObj.toString();
		} else {
			jsonStr = "{\"status\":\"error\",\"message\":\"暂无记录!\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/appletLogin")
	public String appletLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonStr = "";
		try {

			Object[] obj = this.memberService.searchaMemberEpalIdByMobile(
					request.getParameter("account"),
					request.getParameter("memberId"));

			jsonStr = "{\"memberId\":\"" + obj[1] + "\",\"openId\":\"" + obj[1]
					+ "\",\"epalId\":\"" + obj[0] + "\",\"status\":\"ok\"}";
			if ("1".equals(request.getParameter("type"))) {
				this.memberService.updateAppletMember(request
						.getParameter("memberId"));
			}

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;
	}

}
