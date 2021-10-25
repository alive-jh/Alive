package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.recordqueue.LogProductDelegateMember;
import com.wechat.service.*;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("member")
public class MemberController {

	@Resource
	private MemberService memberService;
	
	@Resource
	private VideoService videoService;

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Resource
	private ProductService productService;

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
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
	private IntegralService integralService;

	public IntegralService getIntegralService() {
		return integralService;
	}

	public void setIntegralService(IntegralService integralService) {
		this.integralService = integralService;
	}

	@Resource
	private AccountService accountService;

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
	private ElectrismService electrismService;
	
	
	public ElectrismService getElectrismService() {
		return electrismService;
	}

	public void setElectrismService(ElectrismService electrismService) {
		this.electrismService = electrismService;
	}
	@Resource
	private BookCardService bookCardService;

	
	public BookCardService getBookCardService() {
		return bookCardService;
	}

	public void setBookCardService(BookCardService bookCardService) {
		this.bookCardService = bookCardService;
	}

	@Resource
	private BookService bookService;
	
	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Resource
	private RedisService redisService;
	
	
	
	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	@RequestMapping(value = "/toMember")
	public String toMember(HttpServletRequest request,
			HttpServletResponse response, String memberId,String productId,String cateId,String shopId) throws Exception {

		request.setAttribute("memberId", memberId);
		request.setAttribute("productId", productId);
		request.setAttribute("cateId", cateId);
		request.setAttribute("shopId", shopId);
		return "member/toMember";
	}
	
	
	@RequestMapping(value = "/toQrCode")
	public String toQrCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	
		
		return "member/toQrCode";
	}
	
	@RequestMapping(value = "/toMp3")
	public String toMp3(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	
		
		return "member/toMp3";
	}
	@RequestMapping(value = "/yaoqingMember")
	public String yaoqingMember(HttpServletRequest request,
			HttpServletResponse response, String memberId,String productId) throws Exception {

		request.setAttribute("memberId", memberId);
		Member member = new Member();
		member.setId(new Integer(memberId));
		member = this.getMemberService().getMember(member);
		if(!"".equals(member.getTempName()) && member.getTempName()!= null )
		{
			return "member/sendyaoqingMember";
		}
		else
		{
			return "member/yaoqingMember";
		}
		
	
	}
	
	
	
	@RequestMapping(value = "/qiandaoMember")
	public String qiandaoMember(HttpServletRequest request,
			HttpServletResponse response, String memberId,String productId) throws Exception {

		request.setAttribute("memberId", memberId);
		return "member/qiandaoMember";
	}

	@RequestMapping(value = "/memberInfo")
	public String memberInfo(HttpServletRequest request,
			HttpServletResponse response, String memberId, String mobile)
			throws Exception {
		
		
		Member member = new Member();
		if (!"".equals(mobile) && mobile != null)// 新用户注册,修改手机号码
		{
			
			member = this.memberService.getMemberByMobile(mobile);
			if(member.getId() == null)
			{
				this.memberService.updateMemberMobile(mobile, memberId);
				MemberAccount memberAccount = new MemberAccount();
				memberAccount.setAccount(mobile);
				memberAccount.setMemberId(new Integer(memberId));
				memberAccount.setPassword(MD5UTIL.encrypt(request.getParameter("pwd")));
				memberAccount.setCreateDate(new Date());
				memberAccount.setType(0);
				this.memberService.saveMemberAccount(memberAccount);
			}
			else
			{
				int newMemberId = Integer.parseInt(memberId);
				
				if(newMemberId!=member.getId()){
							
					String openId = this.memberService.getMemberById(newMemberId).getOpenId();
					member.setOpenId(openId);
					this.memberService.updateMember(member);
					this.memberService.deleteMemberByID(newMemberId);
					memberId=""+member.getId();
					
				}else{
					
					this.memberService.updateMemberMobile(mobile, memberId);
					
				}
				
				
				
				
			}
				
											
		}

		
		member.setId(new Integer(memberId));
		member = this.memberService.getMember(member);
		request.setAttribute("member", member);
		if(!"".equals(request.getParameter("productId")) && request.getParameter("productId")!= null)
		{
			response.sendRedirect(Keys.STAT_NAME+"wechat/mall/mallMobileManager?memberId="+memberId+"&mallProductId="+request.getParameter("productId")+"&shopId="+request.getParameter("shopId"));
			return null;
		}
		
		if(!"".equals(request.getParameter("toBookView")) && request.getParameter("toBookView")!= null)
		{
			response.sendRedirect(Keys.STAT_NAME+"wechat/book/bookMobileView?memberId="+memberId+"&cateId="+request.getParameter("cateId"));
			return null;
		}
		

		if(member.getType() ==1)
		{
			Electrism electrism = this.electrismService.getElectrismByMemberId(request.getParameter("memberId"));
			String sumPayment = this.electrismService.getElectrismOrderSumPayment(electrism.getId().toString());
			request.setAttribute("sumPayment", sumPayment);
		}
		
	
		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(request.getParameter("memberId"));
		List tempList = this.integralService.getIntegralByMemberId(request.getParameter("memberId"),memberAccount.getType().toString());//优惠卷列表
		request.setAttribute("tempList", tempList.size());
		Integer integralCount = this.integralService.getIntegralCount(memberId,memberAccount.getType().toString());//积分总数
		request.setAttribute("integralCount", integralCount);

		HashMap map = new HashMap();
		map.put("status", "0");
		map.put("memberId", request.getParameter("memberId"));
		tempList = this.couponsService.searchCouponsList(map);//优惠卷总数
		request.setAttribute("couponsCount", tempList.size());

		return "member/memberInfo";

	}
	
	
	@RequestMapping(value = "/sendyaoqing")
	public String sendyaoqing(HttpServletRequest request,
			HttpServletResponse response, String memberId, String mobile,String tempName)
			throws Exception {
		
		
		
		this.memberService.qiandao(memberId);
		String jsonStr = "{\"status\":\"ok\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;

	}
	
	
	@RequestMapping(value = "/qiandaoManager")
	public String qiandaoManager(HttpServletRequest request,
			HttpServletResponse response, String memberId, String mobile,String tempName)
			throws Exception {
		
		
		
		List list = this.memberService.searchMermberByqiandao(request.getParameter("name"),request.getParameter("mobile"));
		request.setAttribute("memberList", list);
		return "member/qiandaoManager";

	}
	
	@RequestMapping(value = "/yaoqing")
	public String yaoqing(HttpServletRequest request,
			HttpServletResponse response, String memberId, String mobile,String tempName)
			throws Exception {
		
		
		Member member = new Member();
		this.memberService.updateMemberInfo(mobile, tempName, memberId);
		String jsonStr = "{\"status\":\"ok\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;

	}
	
	
	@RequestMapping(value = "/qiandao")
	public String qiandao(HttpServletRequest request,
			HttpServletResponse response, String memberId, String mobile,String tempName,String age)
			throws Exception {
		
		
		Member member = new Member();
		this.memberService.updateMemberAge(mobile, tempName, age, memberId);
		String jsonStr = "{\"status\":\"ok\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;

	}

	@RequestMapping(value = "/memberPayment")
	public String memberPayment(HttpServletRequest request,
			HttpServletResponse response, String memberId, String mobile)
			throws Exception {

		return "member/WeixinJSBridge";

	}

	@RequestMapping("/addProduct")
	public String addProduct(HttpServletRequest request) throws Exception {

		this.productService.updateProductByMemberId(request
				.getParameter("productId"), request.getParameter("memberId"),
				request.getParameter("memberType"));
		HashMap map = new HashMap();

		map.put("memberId", request.getParameter("memberId"));
		map.put("type", request.getParameter("memberType"));

		map.put("page", "1");
		map.put("rowsPerPage", "30");
		Page resultPage = this.productService.searchProductByMember(map);
		request.setAttribute("resultPage", resultPage);
		return "member/memberProduct";
	}

	  public static String getSix(){  
	        Random rad=new Random();  
	          
	        String result  = rad.nextInt(1000000) +"";  
	          
	        if(result.length()!=6){  
	            return getSix();  
	        }  
	        return result;  
	    }  
	/**
	 * 
	 * 发送短信验证码
	 * 
	 * 
	 * 
	 * */
	  
	@RequestMapping(value = "/addCody")
	public String addCody(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		int random = (int) (Math.random() * 1000000);
		
		String mobiles = request.getParameter("mobile");
		
		String code = getSix() + "";
		
		TAOBAOSMS TAOBAOSMS = new TAOBAOSMS();
		com.wechat.util.TAOBAOSMS.sendCode(code, mobiles);
		SMSCode smsCode = new SMSCode();
		smsCode.setMobile(mobiles);
		smsCode.setCode(new Integer(code));
		smsCode.setCreateDate(new Date());
		this.memberService.saveSMSCode(smsCode);

		String jsonStr = "{\"data\":{\"code\":\"验证码已发送!\"}}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;
	}
	
	
	
	@RequestMapping(value = "/addVoiceCode")
	public String addVoiceCode(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		int random = (int) (Math.random() * 1000000);
		
		String mobiles = request.getParameter("mobile");
		
		String code = getSix() + "";
		
		TAOBAOSMS TAOBAOSMS = new TAOBAOSMS();
		com.wechat.util.TAOBAOSMS.sendVoiceCode(code, mobiles);
		
		SMSCode smsCode = new SMSCode();
		smsCode.setMobile(mobiles);
		smsCode.setCode(new Integer(code));
		smsCode.setCreateDate(new Date());
		this.memberService.saveSMSCode(smsCode);

		String jsonStr = "{\"data\":{\"code\":\"验证码已发送!\"}}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;
	}
	
	
	
	@RequestMapping(value = "/addPwdCode")
	public String addPwdCode(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		int random = (int) (Math.random() * 1000000);
		
		String mobiles = request.getParameter("mobile");
		
		String code = getSix() + "";
		
		TAOBAOSMS TAOBAOSMS = new TAOBAOSMS();
		com.wechat.util.TAOBAOSMS.sendPwdCode(code, mobiles);
		
		SMSCode smsCode = new SMSCode();
		smsCode.setMobile(mobiles);
		smsCode.setCode(new Integer(code));
		smsCode.setCreateDate(new Date());
		this.memberService.saveSMSCode(smsCode);

		String jsonStr = "{\"data\":{\"code\":\"验证码已发送!\"}}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;
	}
	
	
	
	

	@RequestMapping(value = "/getCody")
	public String getCody(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		String code = this.memberService.searchSMSCode(request
				.getParameter("mobile"));

		String jsonStr = "{\"data\":{\"code\":\"" + code + "\"}}";

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);

		return null;
	}

	
	@RequestMapping(value = "/toSaveOrder")
	public String toSaveOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MallOrder mallOrder = new MallOrder();

		MallOrderProduct mallOrderProduct = new MallOrderProduct();
		////System.out.println("memebrKey = "+request.getParameter("productId"));
		String productId = request.getParameter("productId");
		if ("1".equals(request.getParameter("orderType")))//购物差商品ID
		{
			productId = request.getParameter("ids");
		}
		String memberKey = RedisKeys.REDIS_MALLORDER+request.getParameter("memberId")+":"+productId;
		
		request.setAttribute("redisName", memberKey);
		
		
		//System.out.println("toSaveOrder memebrKey = "+memberKey);
		UserAddress userAddress = new UserAddress();
		if (this.getRedisService().exists(memberKey)) {

			
			//System.out.println("redis is lod  memebrKey = "+memberKey);
			mallOrder = (MallOrder)this.getRedisService().getObject(memberKey, MallOrder.class);
			if (!"".equals(request.getParameter("couponsId"))&& request.getParameter("couponsId") != null) {
				HashMap map = new HashMap();
				map.put("couponsId", request.getParameter("couponsId"));

				List list = this.couponsService.searchCouponsList(map);
				Object[] obj = new Object[5];
				if (list != null) {
					obj = (Object[]) list.get(0);
					mallOrder.setCouponsMoney(new Double(obj[3].toString()));
					mallOrder.setCouponsId(new Integer(obj[0].toString()));

				}
			}
			userAddress = this.mallProductService.getUserAddress(mallOrder.getAddressId());

		} 
		else 
		{
			if ("0".equals(request.getParameter("orderType"))) {
				
				
				if(!"".equals(request.getParameter("path")) && request.getParameter("path")!= null )
				{
					
					//System.out.println("111111redis is new  memebrKey = "+memberKey);
					////System.out.println(" specificationsId = " + request.getParameter("specificationsId"));
					MallSpecifications mallSpecifications = this.mallProductService.getMallSpecificationsById(request.getParameter("specificationsId"));
					
					MallProduct mallProduct =  new MallProduct();
					mallProduct.setId(new Integer(request.getParameter("productId")));
					mallProduct = this.mallProductService.getMallProduct(mallProduct);
					
					
					mallOrderProduct.setProductImg(mallProduct.getLogo1());
					mallOrderProduct.setProductName(mallProduct.getName());
					mallOrderProduct.setCount(new Integer(request.getParameter("count")));
					mallOrderProduct.setPrice(new Double(mallSpecifications.getPrice()));
					mallOrderProduct.setSpecifications(mallSpecifications.getName());
					mallOrderProduct.setProductId(new Integer(request.getParameter("productId")));
					if("1052".equals(request.getParameter("productId")))
					{
						
						BookShop bookShop = this.bookService.searchBookShopById(new Integer(request.getParameter("shopId")));
						DecimalFormat df2 = new DecimalFormat("####");
						String tempPrice = df2.format(bookShop.getMemberCardPrice()); 
						
						mallOrderProduct.setPrice(new Double(tempPrice));
						////System.out.println(" shopId = "+request.getParameter("shopId"));
						mallOrder.setShopId(new Integer(request.getParameter("shopId")));
						//request.getSession().setAttribute("shopId", request.getParameter("shopId"));
						
						BookCard bookCard = new BookCard();
						bookCard.setCreateDate(new Date());
						bookCard.setMemberId(mallOrder.getMemberId());
						bookCard.setCard(this.getStringRandom());
						bookCard.setYear(1);
						bookCard.setType(2);
						bookCard.setShopId(mallOrder.getShopId());
						bookCard.setPrice(new Integer(df2.format(mallOrder.getTotalPrice())));
						bookCard.setStatus(1);
						this.bookCardService.saveBookCard(bookCard);
					} 
					
					
				}
				else
				{
					
					mallOrderProduct.setProductImg(request.getParameter("productImg"));
					mallOrderProduct.setProductName(request.getParameter("productName"));
					mallOrderProduct.setCount(new Integer(request.getParameter("count")));
					mallOrderProduct.setPrice(new Double(request.getParameter("price")));
					mallOrderProduct.setSpecifications(request.getParameter("specifications"));
					mallOrderProduct.setProductId(new Integer(request.getParameter("productId")));
					
				}
				mallOrder.getProductList().add(mallOrderProduct);
				
			} 
			else 
			{
				if ("".equals(request.getParameter("ids"))
						&& request.getParameter("ids") == null) {
					
					
					response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9e904ade90942028&redirect_uri="+Keys.STAT_NAME+"/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=mall#wechat_redirect");

				}

				
				//System.out.println("333333redis is new  memebrKey = "+memberKey);
				String[] ids = request.getParameter("ids").split(",");
				
				mallOrder.setShoppingCartId(request.getParameter("ids"));
				//request.getSession().setAttribute("shoppingCartId", request.getParameter("ids"));
				String[] counts = request.getParameter("counts").split(",");
				String[] specifications = request
						.getParameter("specifications").split(",");
				String[] prices = request.getParameter("prices").split(",");
				String[] productIds = request.getParameter("productIds").split(
						",");
				request.setAttribute("ids", request.getParameter("ids"));

				if (request.getParameter("ids") != null) {

					HashMap mallProductMap = this.mallProductService.searchMallProductMap(request.getParameter("productIds").substring(0,request.getParameter("productIds").length() - 1));

					MallProduct mallProduct = new MallProduct();
					for (int i = 0; i < productIds.length; i++) {

						mallOrderProduct = new MallOrderProduct();
						if (mallProductMap.get(productIds[i].toString()) != null) {
							mallProduct = (MallProduct) mallProductMap
									.get(productIds[i].toString());

							mallOrderProduct.setProductId(mallProduct.getId());
							mallOrderProduct.setPrice(new Double(prices[i]
									.toString()));
							mallOrderProduct.setProductImg(mallProduct
									.getLogo1());
							mallOrderProduct.setProductName(mallProduct
									.getName());
							mallOrderProduct.setCount(new Integer(counts[i]
									.toString()));
							mallOrderProduct
									.setSpecifications(specifications[i]);
							mallOrder.getProductList().add(mallOrderProduct);
						}

					}
				}
				productId = request.getParameter("ids");
				memberKey = RedisKeys.REDIS_MALLORDER+request.getParameter("memberId")+":"+productId;

			}
			if("".equals(request.getParameter("memberId")) || request.getParameter("memberId")== null )
			{
				
				
				return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toProduct@"+request.getParameter("productId")+"@"+request.getParameter("specificationsId")+"@"+request.getParameter("count")+"@"+request.getParameter("shopId")+"#wechat_redirect";
				
				
			}
			userAddress = this.mallProductService.getUserAddressBystatus(new Integer(request.getParameter("memberId")));
			if (userAddress.getId() != null) {
				mallOrder.setAddressId(userAddress.getId());
			}
			
		}
		Member member = new Member();
		if(!"".equals(request.getParameter("memberId")) && request.getParameter("memberId")!= null )
		{
			member.setId(new Integer(request.getParameter("memberId")));
		}
		else
		{
			member.setId(mallOrder.getMemberId());
		}
		
		if("".equals(member.getId()) || member.getId()== null )
		{
			
			
			return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toProduct@"+request.getParameter("productId")+"@"+request.getParameter("specificationsId")+"@"+request.getParameter("count")+"@"+request.getParameter("shopId")+"#wechat_redirect";
			
			
		}
		
		member = this.memberService.getMember(member);
		request.setAttribute("member", member);

		mallOrder.setMemberId(member.getId());
		mallOrder.setTotalPrice(new Double(0));
		
		if(!"".equals(mallOrder.getProductListStr()) && mallOrder.getProductListStr()!= null )
		{
			JSONArray jsonArray = JSONArray.fromObject(mallOrder.getProductListStr());  
			List<MallOrderProduct> tempList =  (List<MallOrderProduct>) JSONArray.toCollection(jsonArray,  MallOrderProduct.class);
			mallOrder.setProductList(tempList);
		}
		
		for (int i = 0; i < mallOrder.getProductList().size(); i++) {

			mallOrderProduct = mallOrder.getProductList().get(i);
			mallOrder.setTotalPrice(mallOrder.getTotalPrice()
					+ (mallOrderProduct.getPrice() * mallOrderProduct
							.getCount()));
		}

		request.setAttribute("mallProductId", mallOrderProduct.getProductId());
	


		
		request.setAttribute("userAddress", userAddress);
		
		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(mallOrder.getMemberId().toString());
		Integer integralCount = this.integralService.getIntegralCount(mallOrder.getMemberId().toString(),memberAccount.getType().toString());
		if(integralCount>0)
		{
			request.setAttribute("integralCount", integralCount/100);
		}
		else
		{
			request.setAttribute("integralCount", 0);
		}
		
		
		if(mallOrder.getTotalPrice()<50 )
		{
			
			////System.out.println("shopId = "+request.getParameter("shopId"));
			
			if(request.getParameter("shopId") == null)//运费
			{
//				mallOrder.setFreight(new Double("10.00"));
				mallOrder.setTotalPrice(mallOrder.getTotalPrice()+mallOrder.getFreight());
			}
			
		}
		////System.out.println("totalPrice = "+mallOrder.getTotalPrice());
		mallOrder.setIntegral(integralCount/10);
		
		JSONArray jsonarray = JSONArray.fromObject(mallOrder.getProductList());  
		mallOrder.setProductListStr(jsonarray.toString());
		this.getRedisService().setObject(memberKey, mallOrder, 120);
		request.setAttribute("mallOrder", mallOrder);
		request.setAttribute("shopId", mallOrder.getShopId());
		request.setAttribute("memberId", request.getParameter("memberId"));
		request.setAttribute("productId", request.getParameter("productId"));
		
		return "mall/addOrder";
	}
	
	
	
//	@RequestMapping(value = "/toSaveOrderBlank")
//	public String toSaveOrder_blank(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		MallOrder mallOrder = new MallOrder();
//
//		MallOrderProduct mallOrderProduct = new MallOrderProduct();
//
//		if (request.getSession().getAttribute("mallOrder") != null) {
//
//			mallOrder = (MallOrder) request.getSession().getAttribute(
//					"mallOrder");
//			if (!"".equals(request.getParameter("couponsId"))&& request.getParameter("couponsId") != null) {
//				HashMap map = new HashMap();
//				map.put("couponsId", request.getParameter("couponsId"));
//
//				List list = this.couponsService.searchCouponsList(map);
//				Object[] obj = new Object[5];
//				if (list != null) {
//					obj = (Object[]) list.get(0);
//					mallOrder.setCouponsMoney(new Double(obj[3].toString()));
//					mallOrder.setCouponsId(new Integer(obj[0].toString()));
//
//				}
//			}
//
//		} 
//		else 
//		{
//			if ("0".equals(request.getParameter("orderType"))) {
//				
//				
//				
//				
//				
//				
//				if(!"".equals(request.getParameter("path")) && request.getParameter("path")!= null )
//				{
//					
//					
//					////System.out.println(" specificationsId = " + request.getParameter("specificationsId"));
//					MallSpecifications mallSpecifications = this.mallProductService.getMallSpecificationsById(request.getParameter("specificationsId"));
//					
//					MallProduct mallProduct =  new MallProduct();
//					mallProduct.setId(new Integer(request.getParameter("productId")));
//					mallProduct = this.mallProductService.getMallProduct(mallProduct);
//					
//					
//					mallOrderProduct.setProductImg(mallProduct.getLogo1());
//					mallOrderProduct.setProductName(mallProduct.getName());
//					mallOrderProduct.setCount(new Integer(request.getParameter("count")));
//					mallOrderProduct.setPrice(new Double(mallSpecifications.getPrice()));
//					mallOrderProduct.setSpecifications(mallSpecifications.getName());
//					mallOrderProduct.setProductId(new Integer(request.getParameter("productId")));
//					if("1052".equals(request.getParameter("productId")))
//					{
//						
//						BookShop bookShop = this.bookService.searchBookShopById(new Integer(request.getParameter("shopId")));
//						DecimalFormat df2 = new DecimalFormat("####");
//						String tempPrice = df2.format(bookShop.getMemberCardPrice()); 
//						
//						mallOrderProduct.setPrice(new Double(tempPrice));
//						//System.out.println(" shopId = "+request.getParameter("shopId"));
//						request.getSession().setAttribute("shopId", request.getParameter("shopId"));
//					}
//					
//					
//				}
//				else
//				{
//					mallOrderProduct.setProductImg(request.getParameter("productImg"));
//					mallOrderProduct.setProductName(request.getParameter("productName"));
//					mallOrderProduct.setCount(new Integer(request.getParameter("count")));
//					mallOrderProduct.setPrice(new Double(request.getParameter("price")));
//					mallOrderProduct.setSpecifications(request.getParameter("specifications"));
//					mallOrderProduct.setProductId(new Integer(request.getParameter("productId")));
//					
//				}
//				mallOrder.getProductList().add(mallOrderProduct);
//				
//			} 
//			else 
//			{
//				if ("".equals(request.getParameter("ids"))
//						&& request.getParameter("ids") == null) {
//					
//					
//					response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9e904ade90942028&redirect_uri="+Keys.STAT_NAME+"/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=mall#wechat_redirect");
//
//				}
//
//				
//				String[] ids = request.getParameter("ids").split(",");
//				request.getSession().setAttribute("shoppingCartId", request.getParameter("ids"));
//				String[] counts = request.getParameter("counts").split(",");
//				String[] specifications = request
//						.getParameter("specifications").split(",");
//				String[] prices = request.getParameter("prices").split(",");
//				String[] productIds = request.getParameter("productIds").split(
//						",");
//				request.setAttribute("ids", request.getParameter("ids"));
//
//				if (request.getParameter("ids") != null) {
//
//					HashMap mallProductMap = this.mallProductService
//							.searchMallProductMap(request.getParameter(
//									"productIds")
//									.substring(
//											0,
//											request.getParameter("productIds")
//													.length() - 1));
//
//					MallProduct mallProduct = new MallProduct();
//					for (int i = 0; i < productIds.length; i++) {
//
//						mallOrderProduct = new MallOrderProduct();
//						if (mallProductMap.get(productIds[i].toString()) != null) {
//							mallProduct = (MallProduct) mallProductMap
//									.get(productIds[i].toString());
//
//							mallOrderProduct.setProductId(mallProduct.getId());
//							mallOrderProduct.setPrice(new Double(prices[i]
//									.toString()));
//							mallOrderProduct.setProductImg(mallProduct
//									.getLogo1());
//							mallOrderProduct.setProductName(mallProduct
//									.getName());
//							mallOrderProduct.setCount(new Integer(counts[i]
//									.toString()));
//							mallOrderProduct
//									.setSpecifications(specifications[i]);
//							mallOrder.getProductList().add(mallOrderProduct);
//						}
//
//					}
//				}
//
//			}
//		}
//		Member member = new Member();
//		if(!"".equals(request.getParameter("memberId")) && request.getParameter("memberId")!= null )
//		{
//			member.setId(new Integer(request.getParameter("memberId")));
//		}
//		else
//		{
//			member.setId(mallOrder.getMemberId());
//		}
//		
//		if("".equals(member.getId()) || member.getId()== null )
//		{
//			
//			
//			return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toProduct@"+request.getParameter("productId")+"@"+request.getParameter("specificationsId")+"@"+request.getParameter("count")+"@"+request.getParameter("shopId")+"#wechat_redirect";
//			
//			
//		}
//		member = this.memberService.getMember(member);
//		request.setAttribute("member", member);
//
//		mallOrder.setMemberId(member.getId());
//		mallOrder.setTotalPrice(new Double(0));
//		for (int i = 0; i < mallOrder.getProductList().size(); i++) {
//
//			mallOrderProduct = mallOrder.getProductList().get(i);
//			mallOrder.setTotalPrice(mallOrder.getTotalPrice()
//					+ (mallOrderProduct.getPrice() * mallOrderProduct
//							.getCount()));
//		}
//
//		request.setAttribute("mallProductId", mallOrderProduct.getProductId());
//		UserAddress userAddress = new UserAddress();
//
//		userAddress = this.mallProductService.getUserAddressBystatus(mallOrder
//				.getMemberId());
//		if (userAddress.getId() != null) {
//			mallOrder.setAddressId(userAddress.getId());
//		}
//		request.setAttribute("userAddress", userAddress);
//		
//		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(mallOrder.getMemberId().toString());
//		Integer integralCount = this.integralService.getIntegralCount(mallOrder.getMemberId().toString(),memberAccount.getType().toString());
//		request.setAttribute("integralCount", integralCount/100);
//		
//		if(mallOrder.getTotalPrice()<50 )
//		{
//			
//			////System.out.println("shopId = "+request.getParameter("shopId"));
//			
//			if(request.getParameter("shopId") == null)
//			{
//				mallOrder.setFreight(new Double("10.00"));
//				mallOrder.setTotalPrice(mallOrder.getTotalPrice()+mallOrder.getFreight());
//			}
//			
//		}
//		//System.out.println("totalPrice = "+mallOrder.getTotalPrice());
//		mallOrder.setIntegral(integralCount/10);
//		request.getSession().setAttribute("mallOrder", mallOrder);
//		
//		return "mall/addOrder";
//	}

	@RequestMapping(value = "/saveOrder")
	public String saveOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String productId = request.getParameter("productId");
		if ("".equals(request.getParameter("productId")) || request.getParameter("productId") == null)//购物差商品ID
		{
			productId = request.getParameter("ids");
		}
		String memberKey = RedisKeys.REDIS_MALLORDER+request.getParameter("memberId")+":"+productId;
		//System.out.println("memebrKey = "+memberKey);
		if (this.getRedisService().exists(memberKey)) {
			
			
			//System.out.println("redis is ok  = "+memberKey);
			MallOrder mallOrder = (MallOrder) this.getRedisService().getObject(memberKey, MallOrder.class);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("infoList", mallOrder);
			////System.out.println("shoppingCartId = "+mallOrder.getShoppingCartId());
			mallOrder.setOrderNumber(request.getParameter("orderNumber"));
			mallOrder.setOrderNumber(mallOrder.getOrderNumber().replaceAll("\"", ""));
			mallOrder.setCreateDate(new Date());
			mallOrder.setRemarks(request.getParameter("remarks"));
			
			JSONArray jsonArray = JSONArray.fromObject(mallOrder.getProductListStr());  
			List<MallOrderProduct> tempList =  (List<MallOrderProduct>) JSONArray.toCollection(jsonArray,  MallOrderProduct.class);
			mallOrder.setProductList(tempList);
			MallOrderProduct mallOrderProduct = new MallOrderProduct();
			Double tempTotalPrice = new Double(0);
			for (int i = 0; i < mallOrder.getProductList().size(); i++) {

				mallOrderProduct = mallOrder.getProductList().get(i);
				tempTotalPrice = tempTotalPrice+ (mallOrderProduct.getPrice() * mallOrderProduct.getCount());
			}
			
			mallOrder.setTotalPrice(tempTotalPrice);
//			if(mallOrder.getTotalPrice()<50)
//			{
//				mallOrder.setFreight(new Double(10));
//			}
//			if("1".equals(request.getParameter("integralStatus")))
//			{
//				mallOrder.setTotalPrice(mallOrder.getTotalPrice()-mallOrder.getIntegral());
//			}
			
			
			
			
			mallOrder.setTotalPrice(new Double(request.getParameter("totalPrice")));
			
			if(!"".equals(mallOrder.getShopId()) && mallOrder.getShopId()!= null && mallOrder.getShopId()!= 0 )
			{
					DecimalFormat df2 = new DecimalFormat("####");
				
					mallOrder.setStatus(3);//修改订单状态
					mallOrder.setIntegral(0);
				
				   Calendar   calendar   =   new   GregorianCalendar(); 
				   MemberBook memberBook = new MemberBook();
				   memberBook = this.memberService.getMemberBook(mallOrder.getMemberId().toString());
				   if(memberBook.getId()!= null)//书院会员卡
				   {
					   memberBook.setType(3);
					   calendar.setTime(memberBook.getEndDate());
					   calendar.add(Calendar.YEAR,1); 
					   memberBook.setEndDate(calendar.getTime());
					  
				   }
				   else
				   {
					   memberBook.setCreateDate(new Date());
					   memberBook.setMemberid(new Integer(mallOrder.getMemberId()));
					   memberBook.setType(3);
					   Date date=new   Date();//取时间 
				     
				       calendar.setTime(date); 
				       calendar.add(Calendar.YEAR,1); 
				       memberBook.setEndDate(calendar.getTime());
				      
				   }
				   this.memberService.saveMemberBook(memberBook);
				   
				   
				BookCard bookCard = new BookCard();
				bookCard.setCreateDate(new Date());
				bookCard.setMemberId(mallOrder.getMemberId());
				bookCard.setCard(this.getStringRandom());
				bookCard.setYear(1);
				bookCard.setType(2);
				bookCard.setShopId(mallOrder.getShopId());
				bookCard.setPrice(new Integer(df2.format(mallOrder.getTotalPrice())));
				bookCard.setStatus(1);
				this.bookCardService.saveBookCard(bookCard);
				
			}
			
//			//System.out.println("productId = "+mallOrder.getProductList().get(0).getId().toString());
//			if("867".equals(mallOrder.getProductList().get(0).getProductId().toString()))
//			{
//					
//				if(mallOrder.getProductList().size() ==1)
//				{
//					
//					DecimalFormat df2 = new DecimalFormat("####");
//					
//					mallOrder.setStatus(3);//修改订单状态
//					mallOrder.setIntegral(0);
//					
//						Calendar   calendar   =   new   GregorianCalendar(); 
//					   MemberBook memberBook = new MemberBook();
//					   memberBook = this.memberService.getMemberBook(mallOrder.getMemberId().toString());
//					   BookCard bookCard = new BookCard();
//					   if(memberBook.getId()!= null)//书院会员卡
//					   {
//						  
//						   calendar.setTime(memberBook.getEndDate());
//						   calendar.add(calendar.YEAR,mallOrder.getProductList().get(0).getCount()); 
//						   memberBook.setEndDate(calendar.getTime());
//						  
//					   }
//					   else
//					   {
//						   memberBook.setCreateDate(new Date());
//						   memberBook.setMemberid(new Integer(mallOrder.getMemberId()));
//						   memberBook.setType(3);
//						   Date date=new   Date();//取时间 
//					     
//					       calendar.setTime(date); 
//					       calendar.add(calendar.YEAR,mallOrder.getProductList().get(0).getCount()); 
//					       memberBook.setEndDate(calendar.getTime());
//					      
//					   }
//					   
//					if(mallOrder.getProductList().get(0).getProductName().lastIndexOf("黄金") ==-1)
//					{
//						 memberBook.setType(1);
//						 bookCard.setType(1);
//					}
//					else
//					{
//						 memberBook.setType(0);
//						 bookCard.setType(0);
//					}
//					bookCard.setYear(mallOrder.getProductList().get(0).getCount());
//					this.memberService.saveMemberBook(memberBook);
//					
//					
//					bookCard.setCreateDate(new Date());
//					bookCard.setMemberId(mallOrder.getMemberId());
//					bookCard.setCard(this.getStringRandom());
//					
//					
//					bookCard.setShopId(new Integer((String)request.getSession().getAttribute("shopId")));
//					bookCard.setPrice(new Integer(df2.format(mallOrder.getTotalPrice())));
//					bookCard.setStatus(1);
//					this.bookCardService.saveBookCard(bookCard);
//					
//					
//				}
//
//				
//			}
			
			
			MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(mallOrder.getMemberId().toString());
			Integral integral =  new Integral();
			
			
			if("1".equals(request.getParameter("integralStatus")))
			{
				integral.setCreateDate(new Date());
				integral.setMemberId(mallOrder.getMemberId());
				integral.setStatus(1);
				integral.setFraction(-mallOrder.getIntegral()*100);
				integral.setTypeId(3);
				integral.setMemberType(memberAccount.getType());
				this.integralService.saveIntegral(integral);// 消费积分
			}
			else
				
			{
				mallOrder.setIntegral(0);
			}
			
			mallOrder.setId(null);
			this.mallProductService.saveOrder(mallOrder);
			for (int i = 0; i < mallOrder.getProductList().size(); i++) {

				mallOrder.getProductList().get(i).setOrderId(mallOrder.getId());
				mallOrder.getProductList().get(i).setCreateDate(new Date());
				this.mallProductService.saveMallOrderProduct(mallOrder.getProductList().get(i));	//保存数据
				this.mallProductService.updateProductCount(mallOrder.getProductList().get(i));		//更新库存
			}
			if (mallOrder.getCouponsId() != 0) {
				
				this.couponsService.updateCouponsInfoStatus(mallOrder.getCouponsId().toString());
			}
			this.redisService.del(RedisKeys.REDIS_MALLORDER+request.getParameter("memberId"));//删除redis属性
			
			if (!"".equals(mallOrder.getShoppingCartId())&& mallOrder.getShoppingCartId() != null) {
				
				String[] ids = mallOrder.getShoppingCartId().split(",");
				for (int i = 0; i < ids.length; i++) {

					this.mallProductService.deleteShoppingCart(ids[i].toString());
				}
			}
			
		
			DecimalFormat df = new DecimalFormat("###");
			
			
			if(!"".equals(mallOrder.getShopId()) && mallOrder.getShopId()!= null)
			{
				
			}
			else
			{
				
				Member member = new Member();
				member.setId(mallOrder.getMemberId());
				member  = this.memberService.getMember(member);
				
				NoticeInfo noticeInfo = new NoticeInfo();//发送微信通知
				String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
				noticeInfo.setAccessToKen(accessToken);
				noticeInfo.setOpenId(member.getOpenId());
				noticeInfo.setMemberId(member.getId().toString());
				noticeInfo.setFirst("您的订单已提交成功，我们将尽快为您配送。");
				noticeInfo.setKeyword1(mallOrder.getOrderNumber());
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				noticeInfo.setKeyword2(format.format(mallOrder.getCreateDate()));
				noticeInfo.setKeyword3(mallOrder.getTotalPrice().toString()+"0");
				
				noticeInfo.setRemark("若有疑问请致电客服中心:"+Keys.CUSTOMER_MOBLIE);
				noticeInfo.setUrl(Keys.STAT_NAME+"wechat/member/memberOrderManager?memberId="+member.getId());
				WeChatUtil.sendOrderNotice(noticeInfo);	
			}
			this.redisService.del(memberKey);
			
			
			
			
			return "redirect:memberOrderManager?memberId="+ mallOrder.getMemberId();
			
		} else {
			
			
			//System.out.println("redis is null ");
			
			String url = Keys.STAT_NAME + "/wechat/mall/mallMobileManager?mallProductId="+request.getParameter("productId")+"&memberId="+request.getParameter("memberId");
			if("".equals(request.getParameter("productId"))  ||  request.getParameter("productId") ==null)//购物车跳转
			{
				url = Keys.STAT_NAME + "/wechat/mall/mallMobileIndex?memberId="+request.getParameter("memberId");
			}
			response.sendRedirect(url);
			return null;

		}

	}
	@RequestMapping(value = "/mallOrderManager")
	public String mallOrderManager(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		String page = "1";
		String rowsPerPage = "20";
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			page = request.getParameter("page");
			queryDto.setPage(page);

		}
		if (!"".equals(request.getParameter("rowsPerPage"))
				&& request.getParameter("rowsPerPage") != null) {
			rowsPerPage = request.getParameter("rowsPerPage");
			queryDto.setPageSize(rowsPerPage);
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("memberId", request.getParameter("memberId"));
		Page resultPage = this.mallProductService.searchMallOrder(map);
		Integer count = new Integer(0);
		for (int i = 0; i < resultPage.getItems().size(); i++) {

			((MallOrder) resultPage.getItems().get(i))
					.setProductList(this.mallProductService
							.searchMallOrderProduct(((MallOrder) resultPage
									.getItems().get(i)).getId().toString()));
			count = new Integer(0);
			for (int j = 0; j < ((MallOrder) resultPage.getItems().get(i))
					.getProductList().size(); j++) {

				count = count
						+ ((MallOrder) resultPage.getItems().get(i))
								.getProductList().get(j).getCount();
			}
			((MallOrder) resultPage.getItems().get(i)).setProductCount(count);
		}

		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		return "member/mallOrderManager";

	}

	@RequestMapping(value = "/memberOrderManager")
	public String memberOrderManager(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();

		map.put("memberId", request.getParameter("memberId"));
		map.put("orderId", request.getParameter("orderId"));

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
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("memberList", memberList);

		if (request.getParameter("orderId") != null) 
		{
			request.setAttribute("mallOrder", mallOrder);

			UserAddress userAddress = new UserAddress();
			userAddress = this.mallProductService
					.getUserAddressBystatus(mallOrder.getMemberId());
			if (userAddress.getId() != null) {
				mallOrder.setAddressId(userAddress.getId());
			}

			request.setAttribute("userAddress", userAddress);

			return "member/memberOrderManagerView";
		} else {
			return "member/memberOrderManager";
		}
	}

	@RequestMapping(value = "/saveAddress")
	public String saveAddress(HttpServletRequest request,
			HttpServletResponse response, UserAddress userAddress)
			throws Exception {

		JSONObject jsonObject = WeChatUtil.getLngAndLat(userAddress
				.getAddress());

		if (jsonObject.toString().lastIndexOf("Error") == -1) {
			userAddress.setLat(jsonObject.getJSONObject("result")
					.getJSONObject("location").getString("lat"));
			userAddress.setLng(jsonObject.getJSONObject("result")
					.getJSONObject("location").getString("lng"));

		}

		this.mallProductService.saveUserAddress(userAddress);
		List list = this.mallProductService.searchUserAddressStatus(userAddress
				.getMemberId().toString());
		if (list.size() == 0) {
			this.mallProductService.updateAddressStatus(userAddress.getId()
					.toString(), userAddress.getMemberId().toString());// 修改默认收货地址
		}
		if (userAddress.getStatus() == 0) {
			this.mallProductService.updateAddressStatus(userAddress.getId()
					.toString(), userAddress.getMemberId().toString());// 修改默认收货地址
		}

		return "redirect:addressManager?memberId=" + userAddress.getMemberId()
				+ "&searchType=" + request.getParameter("searchType");
	}

	@RequestMapping(value = "/deleteAddress")
	public String deleteAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.mallProductService.deleteUserAddress(request
				.getParameter("userAddressId"));

		return "redirect:addressManager?memberId="
				+ request.getParameter("memberId") + "&searchType="
				+ request.getParameter("searchType");
	}

	@RequestMapping(value = "/getAddress")
	public String getAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserAddress userAddress = new UserAddress();
		if (request.getParameter("userAddressId") != null) {
			userAddress = this.mallProductService.getUserAddress(new Integer(
					request.getParameter("userAddressId")));

			//System.out.println("getAddress = "+request.getParameter("searchType"));
			
			if ("edit".equals(request.getParameter("type"))
					|| "modufy".equals(request.getParameter("searchType"))) {
				MallOrder mallOrder = new MallOrder();
				mallOrder.setMemberId(userAddress.getMemberId());
				request.setAttribute("mallOrder", mallOrder);
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("userAddress", userAddress);

				return "mall/addressModufy";
			} else if ("getAddress".equals(request.getParameter("searchType"))) {
				
				
				String memberKey = RedisKeys.REDIS_MALLORDER+request.getParameter("memberId")+":"+request.getParameter("productId");
				
				
				
				
				if (this.getRedisService().exists(memberKey))  {
					
					MallOrder mallOrder = (MallOrder) this.getRedisService().getObject(memberKey, MallOrder.class);
					
					
					mallOrder.setAddressId(userAddress.getId());
					
					
					
					request.setAttribute("userAddress", userAddress);
					Member member = new Member();
					member.setId(userAddress.getMemberId());

					member = this.memberService.getMember(member);
					request.setAttribute("member", member);
					
					JSONArray jsonArray = JSONArray.fromObject(mallOrder.getProductListStr());  
					List<MallOrderProduct> tempList =  (List<MallOrderProduct>) JSONArray.toCollection(jsonArray,  MallOrderProduct.class);
					mallOrder.setProductList(tempList);
					this.getRedisService().setObject(memberKey, mallOrder, 120);
					request.setAttribute("mallOrder", mallOrder);
					request.setAttribute("productId", request.getParameter("productId"));
					request.setAttribute("memberId", request.getParameter("memberId"));
					
					
					return "mall/addOrder";
				}

			}
			else if ("bookAddress".equals(request.getParameter("searchType"))) {
				
				if (request.getSession().getAttribute("bookOrder") != null) {
					
					BookOrder bookOrder = (BookOrder) request.getSession()
							.getAttribute("bookOrder");
					bookOrder.setAddressId(userAddress.getId());
					request.getSession().setAttribute("bookOrder", bookOrder);
					request.setAttribute("userAddress", userAddress);
					Member member = new Member();
					member.setId(userAddress.getMemberId());

					member = this.memberService.getMember(member);
					request.setAttribute("member", member);
					return "book/addBookOrder";
				}

			}

			else {
				Member member = new Member();
				member.setId(userAddress.getMemberId());
				member = this.memberService.getMember(member);
				request.setAttribute("member", member);
				request.setAttribute("userAddress", userAddress);
				return "electrism/electrismAddOrder";
			}

		}

		else {
			MallOrder mallOrder = new MallOrder();
			mallOrder
					.setMemberId(new Integer(request.getParameter("memberId")));
			request.setAttribute("mallOrder", mallOrder);
			request.setAttribute("searchType", request
					.getParameter("searchType"));

		}
		request.setAttribute("userAddress", userAddress);

		return "mall/addressModufy";
	}

	@RequestMapping(value = "/addressManager")
	public String addressManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		String memberKey = RedisKeys.REDIS_MALLORDER+request.getParameter("memberId")+":"+request.getParameter("productId");
		
		List list = this.mallProductService.searchUserAddress(request.getParameter("memberId"));
		if (this.getRedisService().exists(memberKey)) {
			
			
			request.setAttribute("tempList", list);
			request.setAttribute("memberId", request.getParameter("memberId"));
			request.setAttribute("productId", request.getParameter("productId"));
			request.setAttribute("searchType", request.getParameter("searchType"));
			
		} else {
			
			request.setAttribute("tempList", list);
			request.setAttribute("searchType", request
					.getParameter("searchType"));
			request.setAttribute("memberId", request.getParameter("memberId"));

		}

		return "mall/addressManager";
	}

	
	@RequestMapping(value = "/saveShoppingCart")
	public String saveShoppingCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setPrice(new Double(request.getParameter("price")));
		shoppingCart.setCount(new Integer(request.getParameter("count")));
		shoppingCart.setSpecifications(request.getParameter("specifications"));
		shoppingCart.setMemberId(new Integer(request.getParameter("memberId")));
		shoppingCart
				.setProductId(new Integer(request.getParameter("productId")));
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
			this.mallProductService.updateCount(map.get(
					shoppingCart.getProductId() + "-"
							+ shoppingCart.getSpecifications()).toString(),
					shoppingCart.getCount().toString());
		}

		response.setContentType("application/json;charset=utf-8");
		return null;
	}

	@RequestMapping(value = "/shoppingCartManager")
	public String shoppingCartManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List list = this.mallProductService.searchShoppingCart(request
				.getParameter("memberId"));
		request.setAttribute("tempList", list);
		request.setAttribute("memberId", request.getParameter("memberId"));
		request.getSession().removeAttribute("mallOrder");
		return "member/shoppingCartManager";
	}

	@RequestMapping(value = "/deleteShoppingCart")
	public String deleteShoppingCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.mallProductService.deleteShoppingCart(request
				.getParameter("shoppingCartId"));

		return "redirect:shoppingCartManager?memberId="
				+ request.getParameter("memberId");
	}

	@RequestMapping("/memberManager")
	public String memberManager(HttpServletRequest request, QueryDto queryDto)
			throws Exception {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("memberId", request.getParameter("memberId"));

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("nickName", queryDto.getName());
		map.put("sex", queryDto.getSex());
		map.put("mobile", queryDto.getMobile());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		Page resultPage = this.memberService.searchMemberInfo(map);
		request.setAttribute("resultPage", resultPage);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] member : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", member[0]);
				jobj.put("name", member[1]);
				jobj.put("sex", member[2]);
				jobj.put("province", member[3]);
				jobj.put("city", member[4]);
				jobj.put("mobile", member[5]);
				jobj.put("createDate", member[6].toString().substring(0, 10));
				jobj.put("headImg", member[7]);
				if (member[8] != null) {
					jobj.put("fraction", member[8]);
				} else {
					jobj.put("fraction", "0");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		return "member/memberManager";
	}

	@RequestMapping(value = "/memberManagerView")
	public String memberManagerView(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("memberId", request.getParameter("memberId"));

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("nickName", queryDto.getName());
		map.put("sex", queryDto.getSex());
		map.put("mobile", queryDto.getMobile());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		Page resultPage = this.memberService.searchMember(map);
		List<Member> infoList = resultPage.getItems();
		if (!"".equals(request.getParameter("userId"))
				&& request.getParameter("userId") != null) {
			request.setAttribute("tempUser", infoList.get(0));
		}
		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Member member : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", member.getId());
				jobj.put("name", member.getNickName());
				jobj.put("sex", member.getSex());
				jobj.put("province", member.getProvince());
				jobj.put("city", member.getCity());
				jobj.put("mobile", member.getMobile());
				jobj.put("createDate", sdf.format(member.getCreateDate())
						.toString());
				jobj.put("headImg", member.getHeadImgUrl());

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return null;

	}

	@RequestMapping(value = "/memberSign")
	public String memberSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LogProductDelegateMember.addDataToTaskQueue(request
				.getParameter("memberId"));
		Member member = new Member();
		member.setId(new Integer(request.getParameter("memberId")));
		member = this.memberService.getMember(member);
		request.setAttribute("member", member);

		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(request.getParameter("memberId"));
		
		List tempList = this.integralService.getIntegralByMemberId(request
				.getParameter("memberId"),memberAccount.getType().toString());
		request.setAttribute("tempList", tempList.size());
		String jsapitiket = this.accountService.getTicket(Keys.APP_ID,
				Keys.APP_SECRET);// 获取公众账号ticket
		String url = Keys.STAT_NAME + "/wechat/member/memberInfo?memberId="
				+ member.getId() + "&mobile=" + member.getMobile();

		Map<String, String> ret = sign(jsapitiket, url);

		String timestamp = ret.get("timestamp");
		String nonceStr = ret.get("nonceStr");
		String signature = ret.get("signature");
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("nonceStr", nonceStr);
		request.setAttribute("signature", signature);
		request.setAttribute("appId", Keys.APP_ID);

		return "member/memberInfo";

	}

	@RequestMapping(value = "/integralManager")
	public String integralManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(request.getParameter("memberId"));
		List<Object[]> list = this.integralService.searchIntegralList(request
				.getParameter("memberId"),memberAccount.getType().toString());
		
		for (int i = 0; i < list.size(); i++) {
			
			
		}
		request.setAttribute("tempList", list);
		Integer integralCount = this.integralService.getIntegralCount(request
				.getParameter("memberId"),memberAccount.getType().toString());
		request.setAttribute("integralCount", integralCount);
		return "member/integralManager";

	}

	@RequestMapping("/searchIntegral")
	public String searchIntegral(HttpServletRequest request, QueryDto queryDto)
			throws Exception {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("memberId", request.getParameter("memberId"));

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize()); 
		map.put("memberId", request.getParameter("memberId"));

		Page resultPage = this.memberService.searchIntegral(map);
		request.setAttribute("resultPage", resultPage);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] integral : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("name", integral[0]);
				jobj.put("fraction", integral[1]);
				jobj.put("createDate", integral[2].toString()
						.subSequence(0, 15));

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(request.getParameter("memberId"));
		Integer integralCount = this.integralService.getIntegralCount(request
				.getParameter("memberId"),memberAccount.getType().toString());
		request.setAttribute("integralCount", integralCount);
		return "member/searchIntegralManager";
	}

	@RequestMapping("/updateOrderStatus")
	public String updateOrderStatus(HttpServletRequest request)
			throws Exception {

		this.mallProductService.updateOrderStatus(request.getParameter("orderId"), "3");
		MallOrder mallOrder = this.mallProductService.getMallOrder(request.getParameter("orderId"));
		DecimalFormat df = new DecimalFormat("###");
		Integral integral =  new Integral();
		integral.setCreateDate(new Date());
		integral.setMemberId(mallOrder.getMemberId());
		integral.setStatus(0);
		integral.setRemark("订单编号:"+mallOrder.getOrderNumber());
		integral.setFraction(new Integer(df.format(mallOrder.getTotalPrice())));
		integral.setTypeId(2);
		MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(request.getParameter("memberId"));
		integral.setMemberType(memberAccount.getType());
		this.integralService.saveIntegral(integral);//完成订单增加积分
		
		BookCard bookCard = new BookCard();
		bookCard.setMemberId(mallOrder.getMemberId());
		bookCard.setStatus(0);
		bookCard.setShopId(1);
		bookCard.setPrice(0);
		bookCard.setCard(this.getStringRandom());
		bookCard.setCreateDate(new Date());
		List<MallOrderProduct> productList = this.mallProductService.searchMallOrderProduct(request.getParameter("orderId"));
		String productId="";
		for (int i = 0; i < productList.size(); i++) {
			
			if("864".equals(productList.get(i).getProductId().toString()))
			{
				productId = "864";
			}
			if("865".equals(productList.get(i).getProductId().toString()))
			{
				productId = "865";
			}
			if("866".equals(productList.get(i).getProductId().toString()))
			{
				productId = "866";
			}
		}
		
		if("864".equals(productId))
		{
			bookCard.setType(0);
			bookCard.setYear(3);
			this.bookCardService.saveBookCard(bookCard);
		}
		if("865".equals(productId))
		{
			bookCard.setType(0);
			bookCard.setYear(1);
			this.bookCardService.saveBookCard(bookCard);
		}
		if("866".equals(productId))
		{
			bookCard.setType(1);
			bookCard.setYear(1);
			this.bookCardService.saveBookCard(bookCard);
			
		}
		
		return "redirect:memberOrderManager?memberId="
				+ request.getParameter("memberId");

	}

	
	
	@RequestMapping("/updateOrderRefund")
	public String updateOrderRefund(HttpServletRequest request)
			throws Exception {

		MallOrder mallOrder = this.mallProductService.getMallOrder(request
				.getParameter("orderId"));

		this.mallProductService
				.updateOrderStatus(request.getParameter("orderId"), mallOrder
						.getStatusBlank().toString());

		return "redirect:memberOrderManager?memberId="
				+ request.getParameter("memberId");

	}

	@RequestMapping("/orderRefund")
	public String orderRefund(HttpServletRequest request) throws Exception {

		this.mallProductService.updateOrderRefundStatus(request
				.getParameter("orderId"), request.getParameter("status"));
		
		MallOrderService mallOrderService = new MallOrderService();
		mallOrderService.setCreateDate(new Date());
		mallOrderService.setMemberId(new Integer(request.getParameter("memberId")));
		mallOrderService.setStatus(0);
		mallOrderService.setServiceType("没发货,直接退款");
		mallOrderService.setRemarks("没发货,直接退款");
		mallOrderService.setOrderId(request.getParameter("orderId"));
		mallOrderService.setExpress("");
		mallOrderService.setExpressNumber("");
		MallOrder mallOrder = this.mallProductService.getMallOrder(request.getParameter("orderId"));
		mallOrderService.setOrderNumber(mallOrder.getOrderNumber());
		mallOrderService.setPayment(new Double(mallOrder.getTotalPrice()));
		this.mallProductService.saveMallOrderService(mallOrderService);
		
		return "redirect:memberOrderManager?memberId="
				+ request.getParameter("memberId");

	}

	  @RequestMapping({"/toComment"})
	  public String toComment(HttpServletRequest request)
	    throws Exception
	  {
		  
	    String jsapitiket = this.accountService.getTicket(Keys.APP_ID, Keys.APP_SECRET);
	    String url = "http://wechat.fandoutech.com.cn/wechat/member/toComment?orderId=" + 
	      request.getParameter("orderId") + "&memberId=" + 
	      request.getParameter("memberId");
	    
	    Map<String, String> ret = sign(jsapitiket, url);
	    
	    String timestamp = ret.get("timestamp");
	    String nonceStr = ret.get("nonceStr");
	    String signature = ret.get("signature");
	    request.setAttribute("timestamp", timestamp);
	    request.setAttribute("nonceStr", nonceStr);
	    request.setAttribute("signature", signature);
	    request.setAttribute("appId", Keys.APP_ID);
	    
	    MallOrderProduct mallOrderProduct = this.mallProductService
	      .getMallOrderProduct(request.getParameter("orderId"));
	    
	    request.setAttribute("mallOrderProduct", mallOrderProduct);
	    request.setAttribute("memberId", request.getParameter("memberId"));
	    String count = this.commentService.getProductComment(request.getParameter("orderId"));
	    if (!"0".equals(count)) {
	      return "redirect:commentManager?mallProductId=" + mallOrderProduct.getProductId();
	    }
	    return "member/mallComment";
	  }


	  @RequestMapping({"/commentManager"})
	  public String commentManager(HttpServletRequest request, QueryDto queryDto)
	    throws Exception
	  {
	    Object[] productInfo = this.mallProductService.searchMallProductInfo(request.getParameter("mallProductId"));
	    
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
	    for (int i = 0; i < resultPage.getItems().size(); i++)
	    {
	      tempObj = (Object[])resultPage.getItems().get(i);
	      commentInfo = new CommentInfo();
	      commentInfo.setContent(tempObj[2].toString());
	      commentInfo.setCreateDate(tempObj[3].toString().substring(0, 16));
	      //System.out.println(" test = "+tempObj[1]);
	      commentInfo.setMemberName(tempObj[1].toString().substring(0, 1) + "****" + tempObj[1].toString().substring(tempObj[1].toString().length() - 1, tempObj[1].toString().length()));
	      commentInfo.setProductId(new Integer(tempObj[0].toString()));
	      commentInfo.setStar(new Integer(tempObj[4].toString()));
	      if (tempObj[5] != null)
	      {
	        tempStr = tempObj[5].toString().split(",");
	        tempList = new ArrayList();
	        for (int j = 0; j < tempStr.length; j++) {
	          tempList.add("http://wechat.fandoutech.com.cn//wechat/wechatImages/mallComment/" + tempStr[j].toString());
	        }
	        commentInfo.setImgList(tempList);
	      }
	      commentList.add(commentInfo);
	    }
	    request.setAttribute("commentList", commentList);
	    
	    String jsapitiket = this.accountService.getTicket("wx84c693d387fcc49e", 
	      "f0bf562c2e634a27d181d80087a06450");
	    
	    String url = "http://wechat.fandoutech.com.cn/member/commentManager?mallProductId=" + 
	      request.getParameter("mallProductId") + "&memberId=" + 
	      request.getParameter("memberId");
	    
	    Map<String, String> ret = sign(jsapitiket, url);
	    
	    String timestamp = ret.get("timestamp");
	    String nonceStr = ret.get("nonceStr");
	    String signature = ret.get("signature");
	    request.setAttribute("timestamp", timestamp);
	    request.setAttribute("nonceStr", nonceStr);
	    request.setAttribute("signature", signature);
	    request.setAttribute("appId", "wx84c693d387fcc49e");
	    
	    return "member/mallCommentManager";
	  }

	
	  @RequestMapping({"/saveComment"})
	  public String saveComment(HttpServletRequest request)
	    throws Exception
	  {
	    Comment comment = new Comment();
	    comment.setMemberId(new Integer(request.getParameter("memberId")));
	    comment.setProductId(new Integer(request.getParameter("productId")));
	    comment.setCreateDate(new Date());
	    comment.setContent(request.getParameter("content"));
	    comment.setType(new Integer(0));
	    String star = request.getParameter("starValue");
	    comment.setStar(new Integer(star));
	    //System.out.println("orderId = " + request.getParameter("orderId"));
	    this.commentService.saveComment(comment);
	    this.commentService.updateProductCommentStatus(request.getParameter("orderId"));
	    
	    String accessToken = this.accountService.getAccessToken(Keys.APP_ID, Keys.APP_SECRET);
	    CommentImg commentImg = new CommentImg();
	    String imgs = request.getParameter("img");
	    if ((!"".equals(imgs)) && (imgs != null)) {
	      for (int i = 0; i < imgs.split(",").length; i++) {
	        if (imgs.split(",")[i] != null)
	        {
	          commentImg = new CommentImg();
	          commentImg.setImg(saveImageToDisk(imgs.split(",")[i]
	            .toString(), accessToken));
	          commentImg.setCommentId(comment.getId());
	          this.commentService.saveCommentImg(commentImg);
	        }
	      }
	    }
	    return "redirect:commentManager?mallProductId=" + request.getParameter("productId") + "&memberId=" + request.getParameter("memberId");
	  }

	  
	  
	  
	@RequestMapping(value = "/fillRefund")
	public String fillRefund(HttpServletRequest request,HttpServletResponse response) throws Exception {

			request.setAttribute("memberId", request.getParameter("memberId"));
			request.setAttribute("orderId", request.getParameter("orderId"));
			MallOrder mallOrder = this.mallProductService.getMallOrder(request.getParameter("orderId"));
			request.setAttribute("mallOrder", mallOrder);
			return "member/fillRefund";
	}
	
	
	@RequestMapping(value = "/saveFillRefund")
	public String saveFillRefund(HttpServletRequest request,HttpServletResponse response,MallOrderService mallOrderService) throws Exception {

			
		
			mallOrderService.setCreateDate(new Date());
			this.mallProductService.saveMallOrderService(mallOrderService);
			this.mallProductService.updateOrderStatus(mallOrderService.getOrderId().toString(), "4");
			return "redirect:memberOrderManager?memberId="+ request.getParameter("memberId");
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

	public InputStream getInputStream(String mediaId, String accessToken) {

		InputStream is = null;

		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="

				+ accessToken + "&media_id=" + mediaId;

		try {

			URL urlGet = new URL(url);

			HttpURLConnection http = (HttpURLConnection) urlGet

			.openConnection();

			http.setRequestMethod("GET"); // 必须是get方式请求

			http.setRequestProperty("Content-Type",

			"application/x-www-form-urlencoded");

			http.setDoOutput(true);

			http.setDoInput(true);

			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();

			// 获取文件转化为byte流

			is = http.getInputStream();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return is;

	}

	/**
	 * 
	 * 获取下载图片信息（jpg）
	 * 
	 * 
	 * 
	 * @param mediaId
	 * 
	 *            文件的id
	 * 
	 * @throws Exception
	 */

	public String saveImageToDisk(String mediaId, String accessToken)
			throws Exception {

		InputStream inputStream = getInputStream(mediaId, accessToken);

		byte[] data = new byte[1024];

		int len = 0;

		String currTime = TenpayUtil.getCurrTime();
		/** 8位日期 */
		String strTime = currTime.substring(8, currTime.length());
		/** 四位随机数 */
		String strRandom = TenpayUtil.buildRandom(4) + "";
		String picName = strTime + strRandom + ".jpg";
		FileOutputStream fileOutputStream = null;

		try {

			fileOutputStream = new FileOutputStream(Keys.USER_PIC_PATH
					+ "/mallComment/" + picName);

			while ((len = inputStream.read(data)) != -1) {

				fileOutputStream.write(data, 0, len);

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (inputStream != null) {

				try {

					inputStream.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

			if (fileOutputStream != null) {

				try {

					fileOutputStream.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}

		return picName;
	}

	public static void download(String urlString, String filename,
			String savePath) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}
	
	
	public String getStringRandom() {  
        
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < 16; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
	
	
	
	
	 public void wechatAutograph(HttpServletRequest request)
			    throws Exception
			  {
				  
			    String jsapitiket = this.accountService.getTicket(Keys.APP_ID, Keys.APP_SECRET);
			    String url = "http://wechat.fandoutech.com.cn/wechat/member/toComment?orderId=" + 
			      request.getParameter("orderId") + "&memberId=" + 
			      request.getParameter("memberId");
			    
			    Map<String, String> ret = sign(jsapitiket, url);
			    
			    String timestamp = ret.get("timestamp");
			    String nonceStr = ret.get("nonceStr");
			    String signature = ret.get("signature");
			    request.setAttribute("timestamp", timestamp);
			    request.setAttribute("nonceStr", nonceStr);
			    request.setAttribute("signature", signature);
			    request.setAttribute("appId", Keys.APP_ID);
			    
			    
			  }
	 
	 
	 
	 
		@RequestMapping(value = "/getRedisStr")
		public String getRedisStr(HttpServletRequest request,
				HttpServletResponse response, QueryDto queryDto) throws Exception {

			String str = RedisUtil.getRedisStr(request.getParameter("redisName"));
			String jsonStr = "{\"data\":{\"status\":0}}";
			if(!"".equals(str) && str!= null)
			{
				jsonStr = "{\"data\":{\"status\":1}}";
			}
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonStr);
			return null;
		}
	 

	public static void main(String[] args) throws Exception {


		String str = "{\"addressId\":36,\"couponsId\":0,\"couponsMoney\":0,\"createDate\":null,\"express\":\"\",\"expressNumber\":\"\",\"freight\":0,\"id\":0,\"integral\":4,\"memberId\":8,\"operatorId\":0,\"orderNumber\":\"\",\"productCount\":0,\"productList\":[{\"commentStatus\":0,\"count\":1,\"createDate\":null,\"id\":0,\"orderId\":0,\"price\":12,\"productId\":2,\"productImg\":\"0/1442904825201.png\",\"productName\":\"led灯泡 新5W E27螺口电灯泡\",\"specifications\":\"5W\"},{\"commentStatus\":0,\"count\":1,\"createDate\":null,\"id\":0,\"orderId\":0,\"price\":12,\"productId\":2,\"productImg\":\"0/1442904825201.png\",\"productName\":\"led灯泡 新5W E27螺口电灯泡\",\"specifications\":\"5W\"}],\"remarks\":\"\",\"status\":1,\"statusBlank\":1,\"totalPrice\":22}";
		
		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(str);

		//System.out.println(jsonObj.get("productList"));
		List<JSONObject> productList = new ArrayList();
		
		productList = jsonObj.getJSONArray("productList");
		for (int i = 0; i < productList.size(); i++) {
			
			MallOrderProduct mallOrderProduct = (MallOrderProduct)JSONObject.toBean(JSONObject.fromObject(productList.get(i).toString()),MallOrderProduct.class);
			//System.out.println("productName = "+mallOrderProduct.getProductName());
		}
	}
	
	@RequestMapping("wechatShare")
	public String wechatShare(HttpServletRequest request) throws Exception{
		Integer vid = Integer.parseInt(request.getParameter("vid"));
			Object[] videoInfo = this.videoService.getVideoInfoByVid(vid);
			if(videoInfo!=null){
				this.videoService.updateVideoAccess_num(1, videoInfo[2].toString());
				request.setAttribute("video", videoInfo);
				List OtherInfo =  this.videoService.getOtherInfo(vid);
				List<VideoComment> vclist = this.videoService.getVideoComtentById(vid);
				int learnTime = this.videoService.getLearnTime(videoInfo[7].toString(),videoInfo[10].toString());
				////System.out.println(videoInfo[10].toString());
				request.setAttribute("learnTime", "学龄：0年"+learnTime/30+"个月"+learnTime%30+"天");
				request.setAttribute("score", videoInfo[5]);
				request.setAttribute("vclist", vclist);
				if(OtherInfo!=null){
					request.setAttribute("gradeName", OtherInfo.get(0));
				}else{
					request.setAttribute("OtherInfo", null);
				}
			}
			else{
				request.setAttribute("video", null);
				return "activity/video_share";
			}
			/*for(int i=0;i<videoInfo.length;i++){
				//System.out.println(i+"-------"+videoInfo[i]);
			}*/
		String openid = request.getParameter("openid");
		String memberid = request.getParameter("memberid");
		request.setAttribute("openid", openid);
		String url=request.getRequestURL().toString()+"?vid="+videoInfo[0]+"&openid="+openid+"&memberid="+memberid;
		if("http".equals(request.getScheme()))  
			url = url.replaceFirst("http", "https");  
		Map<String, Object> map =  signature(url);
		request.setAttribute("appid", Keys.APP_ID);
		request.setAttribute("timestamp", map.get("timestamp"));
		request.setAttribute("noncestr", map.get("noncestr"));
		request.setAttribute("signature", map.get("signature"));
		return "activity/video_share";
		
	}
	
	@RequestMapping("wechatShareForward")
	public String wechatShareForward(HttpServletRequest request){
		String vid = request.getParameter("vid");
		return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=shareVideo:"+vid+"#wechat_redirect";
	}
	
	public Map<String, Object> signature(String url) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		Iterator<String> it = null;
		Map<String, String> map = new HashMap<String, String>();
		String access_token = redisService.get("wechat_access_token");
		if(null==access_token||"".equals(access_token)){
			String data = "&appid=wxbeda1313a1604ddf"
					+ "&secret=c7962d0ff8292a6b96110f1ee26a3ea2";
	        JSONObject jsonMap = WeChatUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential",data);
	        it = jsonMap.keys();  
	        while(it.hasNext()) {  
	            String key = (String) it.next();  
	            String u = jsonMap.get(key).toString();
	            map.put(key, u);  
	        }
	        redisService.set("wechat_access_token", map.get("access_token"), 6000);
	        access_token = map.get("access_token");
		}
		
        ////System.out.println("access_token=" + access_token);
        //获取ticket
        String jsapi_ticket = redisService.get("wechat_jsapi_ticket");
        if(null==jsapi_ticket||"".equals(jsapi_ticket)){
        	String data2 = "access_token="+access_token+"&type=jsapi";
            JSONObject jsonMap2 = WeChatUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket",data2); 
            map = new HashMap<String, String>();
            it = jsonMap2.keys();  
            while(it.hasNext()) {  
                String key = (String) it.next();  
                String u = jsonMap2.get(key).toString();
                map.put(key, u);  
            }
            this.redisService.set("wechat_jsapi_ticket", map.get("ticket"), 6000);
            jsapi_ticket = map.get("ticket");
        }
		
        ////System.out.println("jsapi_ticket=" + jsapi_ticket);

        //获取签名signature
        String noncestr = UUID.randomUUID().toString().replaceAll("-", "");
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = SHA1(str);
        /*//System.out.println("url=" + url);
        //System.out.println("noncestr=" + noncestr);
        //System.out.println("timestamp=" + timestamp);
        //System.out.println("signature=" + signature);*/
        //最终获得调用微信js接口验证需要的三个参数noncestr、timestamp、signature
        result.put("appid", Keys.APP_ID);
        result.put("timestamp",timestamp);
        result.put("noncestr",noncestr);
        result.put("signature",signature);
		return result;
	}

	public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
