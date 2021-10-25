package com.wechat.controller;

import com.wechat.easemob.*;
import com.wechat.entity.Member;
import com.wechat.entity.MemberAccount;
import com.wechat.entity.User;
import com.wechat.jmessage.JMessage;
import com.wechat.jmessage.MessageExample;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.service.AccountService;
import com.wechat.service.MemberService;
import com.wechat.util.JsonResult;
import com.wechat.util.Keys;
import com.wechat.util.MD5UTIL;
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
import java.util.*;

@Controller
@RequestMapping("/applet")
public class AppletController {

	@Resource
	private AccountService accountService;
	
	@Resource
	private MemberService memberService;
	
	
	
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
	
	
	
	

	
	@RequestMapping(value = "/toRegister")
	public String toRegister(HttpServletRequest request,
			HttpServletResponse response,String memberId) throws Exception {
		
		
		
		request.setAttribute("memberId", request.getParameter("memberId"));
		return "applet/register";
	}
	
	
	@RequestMapping(value="/register")
	public void register(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		String jsonStr = "{\"status\":\"ok\"}";
		
		
		
			Member member = new Member();
			member = this.memberService.getMemberByMobile(request.getParameter("mobile"));
			if(member.getId()!= null)
			{
				jsonStr = "{\"status\":\"error\",\"message\":\"该手机号码已被注册,请重新输入!\"}";
			}
			else
			{
				try {
					
					member.setNickName(request.getParameter("mobile"));
					member.setMobile(request.getParameter("mobile"));
					member.setCreateDate(new Date());
					member.setType(2);
					this.memberService.saveMember(member);
					MemberAccount memberAccount = new MemberAccount();
					memberAccount.setMemberId(member.getId());
					memberAccount.setAccount(request.getParameter("mobile"));
					memberAccount.setPassword(MD5UTIL.encrypt(request.getParameter("pwd")));
					memberAccount.setStatus(0);
					memberAccount.setNickName(request.getParameter("nickName"));
					if(!"".equals(request.getParameter("type")) && request.getParameter("type")!= null)
					{
						memberAccount.setType(new Integer(request.getParameter("type")));
					}
					
					memberAccount.setCreateDate(new Date());
					this.memberService.saveMemberAccount(memberAccount);
				} catch (Exception e) {
					jsonStr = "{\"status\":\"error\"}";
				}
			}
		
		
		
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}
	
	
	
	@RequestMapping(value="/isResigster")
	public void isResigster(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
			String jsonStr = "{\"status\":\"0\"}";
		
		
			JSONObject jsonObj = new JSONObject();
			Member member = new Member();
			member = this.memberService.getMemberByMobile(request.getParameter("mobile"));
			if(member.getId()!= null)
			{
				if(!"".equals(member.getOpenId()) && member.getOpenId() != null )
				{
					
					jsonObj.put("status", "1");//凡豆伴注册,未绑定微信,直接绑定
				}
				else
				{
					jsonObj.put("status", "2");//已绑定微信,不能注册
				}
				
			}
			else
			{
				try {
					
					member.setNickName(request.getParameter("mobile"));
					member.setMobile(request.getParameter("mobile"));
					member.setCreateDate(new Date());
					member.setType(2);
					this.memberService.saveMember(member);
					MemberAccount memberAccount = new MemberAccount();
					memberAccount.setMemberId(member.getId());
					memberAccount.setAccount(request.getParameter("mobile"));
					memberAccount.setPassword(MD5UTIL.encrypt(request.getParameter("pwd")));
					memberAccount.setStatus(0);
					memberAccount.setNickName(request.getParameter("nickName"));
					if(!"".equals(request.getParameter("type")) && request.getParameter("type")!= null)
					{
						memberAccount.setType(new Integer(request.getParameter("type")));
					}
					
					memberAccount.setCreateDate(new Date());
					this.memberService.saveMemberAccount(memberAccount);
				} catch (Exception e) {
					jsonStr = "{\"status\":\"error\"}";
				}
			}
		
			response.setContentType("application/json;charset=UTF-8");
			
			JSONObject json = new JSONObject();
			json.put("data", jsonObj);
			response.getWriter().println(jsonObj.toString());
		
		
		

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}
	


	
	@RequestMapping(value = "/index")
	public String home(HttpServletRequest request,
			HttpServletResponse response,String memberId) throws Exception {
		
		
		wechatAutograph(request);
		request.setAttribute("memberId", request.getParameter("memberId"));
		return "applet/appletIndex";
	}
	
	

	public void wechatAutograph(HttpServletRequest request)
			    throws Exception
			  {
				  
			    String jsapitiket = this.accountService.getTicket(Keys.APP_ID, Keys.APP_SECRET);
			    String url = Keys.STAT_NAME+"/wechat/applet/index?memberId=" + 
			      request.getParameter("memberId");
			    //System.out.println("url = "+url);
			    Map<String, String> ret = sign(jsapitiket, url);
			    
			    String timestamp = ret.get("timestamp");
			    String nonceStr = ret.get("nonceStr");
			    String signature = ret.get("signature");
			    request.setAttribute("timestamp", timestamp);
			    request.setAttribute("nonceStr", nonceStr);
			    request.setAttribute("signature", signature);
			    request.setAttribute("appId", Keys.APP_ID);
			    
			    
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
		
		
		
		
		
		
		
		
		
		
		@RequestMapping(value="/epalRelation")
		public void epalRelation(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
			
			try {
				
			
				String memberId =  request.getParameter("memberId");
				String epalId = request.getParameter("epalId");
				
				
				Member member = new Member();
				member.setId(new Integer(memberId));
				member = this.memberService.getMember(member);
				
				JSONObject jsonObj = new JSONObject();
				
				if(!"".equals(member.getMobile()) && member.getMobile()!= null)
				{
					MessageExample messageExample = new MessageExample();
					JMessage jMessage = new JMessage();
			    	
			    	jMessage.setAccount(member.getMobile());
			    	//jMessage.setEpalId("o821212oo12");
			    	jMessage.setEpalId(epalId);
			    	jMessage.setCreadaDate(new Date().getTime());
			    	jMessage.setType(0);
			    	MessageExample.testSendSingleTextByAdmin(jMessage);//发送消息
			    	jsonObj.put("status", 0);
			    	jsonObj.put("mobile", member.getMobile());
				}
				else
				{
					jsonObj.put("status", 1);
				}
				
				
				response.setContentType("application/json;charset=UTF-8");
				
				JSONObject json = new JSONObject();
				json.put("data", jsonObj);
				response.getWriter().println(jsonObj.toString());
				//System.out.println("jsonStr  "+json.toString());
			} catch (Exception e) {
				JsonResult.JsonResultError(response, 1000);
			}
			
		}
		
		
		
		@RequestMapping(value="/login")
		public String login(HttpServletRequest request,HttpServletResponse response,User user)throws Exception{
			
			String jsonStr = "";
			JSONObject json = new JSONObject();
			MemberAccount memberAccount = this.memberService.login(request.getParameter("account"),MD5UTIL.encrypt(request.getParameter("pwd")));
			if(!"".equals(request.getParameter("account")) && request.getParameter("account")!= null
					&& !"".equals(request.getParameter("pwd")) && request.getParameter("pwd")!= null)
			{
				if (memberAccount.getId()!= null)
				 {
					
					 Member member = new Member();
					 
					 jsonStr = "{\"status\":\"ok\",\"type\":\"1\",\"memberId\":\""+memberAccount.getMemberId()+"\"}";
					 
					 
					 Object[] obj = this.memberService.searchaMemberEpalIdByMobile(request.getParameter("account"), memberAccount.getMemberId().toString());
					
					 List epalList = this.memberService.searchEapLIdList(request.getParameter("account"));
					 JSONObject epalListJobj = new JSONObject();
					 JSONObject epalJobj = new JSONObject();
					 List infoList = new ArrayList();
					 for (int i = 0; i < epalList.size(); i++) {
						 
						 
						 
						 epalJobj = new JSONObject();
						 epalJobj.put("id", ((Object[])epalList.get(i))[0]);
						 epalJobj.put("epalId", ((Object[])epalList.get(i))[1]);
						 epalJobj.put("friendId", ((Object[])epalList.get(i))[2]);
						 epalJobj.put("role", ((Object[])epalList.get(i))[3]);
						 epalJobj.put("friendName", ((Object[])epalList.get(i))[4]);
						 infoList.add(epalJobj);
						
					}
					 
					 json.put("epalIdList", infoList);
					 json.put("status", "ok");
					 json.put("type", "1");
					 json.put("memberId", memberAccount.getMemberId());
					 if(obj!= null)
					 {
						 jsonStr = "{\"type\":\"2\",\"memberId\":\""+obj[1]+"\",\"openId\":\""+obj[2]+"\",\"epalId\":\""+obj[0]+"\",\"status\":\"ok\",\"epalIdList\":\""+epalList.toString()+"\"}";
						 json.put("openId", obj[2]);
						 json.put("epalId", obj[0]);
						 json.put("type", "2");
						 json.put("epalName", obj[3]);
						 
					 }
						
					 
					 
					 EasemobUtil easemobUtil = new EasemobUtil();
					 TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
					 EasemobToKen accessToken = EasemobUtil.createAccessToKen();
					 
					 Authentic.Token TEST_TOKEN = new Authentic.Token(accessToken.getToken(),accessToken.getExpire());
					 
					 service.setToken(TEST_TOKEN);
						
						//注册
						////System.out.println("注册="+JsonTool.write(service.userSave(TEST_USERNAME,TEST_USERNAME,"米茶乐乐"))+"\n");
						
						//判断环信是否注册
					
						String result = JsonTool.write(service.userLine(request.getParameter("account")));
						if(result.indexOf("statusCode")!= -1)
						{
							JsonTool.write(service.userSave(request.getParameter("account"),"fandou",request.getParameter("account")));
							//System.out.println(" register huan xin user ");
						}
						else
						{
							//System.out.println(" user is register ");
						}
					 
					 
					 
					 
					
						
					
				 }
				 else
				 {
					 json.put("status", "error");
					 jsonStr = "{\"status\":\"error\",\"message\":\"账号或密码不正确,请重新输入!\"}";	 
					
				 }
			}
			else
			{
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";	
				json.put("status", "error");
			}
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(json);
			return null;
		}
		
		
		
		
		@RequestMapping(value="/sendMessage")
		public String appletLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
			
			String jsonStr = "";
				
			
			try {
				//o821212oo12,13612767902
				String account = request.getParameter("account");
				String epalId = request.getParameter("epalId");
				EasemobUtil easemobUtil = new EasemobUtil();
				String[] users = new String []{epalId};
				easemobUtil.sendMessage(account,users, "su_set:"+account);
				jsonStr = "{\"status\":\"ok\"}";
				
			} catch (Exception e) {
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
			}
			
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonStr);
			return null;
		}
		
		

		@RequestMapping(value="/clearEpal")
		public String clearEpal(HttpServletRequest request,HttpServletResponse response)throws Exception{
			
			String jsonStr = "";
				
			
			try {
				//o821212oo12,13612767902
				String account = request.getParameter("account");
				String epalId = request.getParameter("epalId");
				EasemobUtil easemobUtil = new EasemobUtil();
				String[] users = new String []{epalId};
				easemobUtil.sendMessage(account,users, "su_unset");
				jsonStr = "{\"status\":\"ok\"}";
				
			} catch (Exception e) {
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
			}
			
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonStr);
			return null;
		}
		
		
		@RequestMapping(value="/searchMemberByMobile")
		public void searchMemberByMobile(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
			String jsonStr = "{\"status\":\"1\"}";
			
			
			if(!"".equals(request.getParameter("mobile"))  && request.getParameter("mobile")!= null)
			{
				Member member = new Member();
				member = this.memberService.getMemberByMobile(request.getParameter("mobile"));
				if(member.getId()!= null)
				{
					 jsonStr = "{\"status\":\"1\",\"memberId\":\""+member.getId()+"\"}";
				}
				else
				{
					 jsonStr = "{\"status\":\"0\"}";
				}
			}
			else
			{
				jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
			}
			
			
			

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonStr);
		}
		
		public static void main(String[] args)throws Exception {
			
			
		}
		
		
		
		
		
}
