package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Modular;
import com.wechat.entity.Role;
import com.wechat.entity.User;
import com.wechat.service.*;
import com.wechat.util.*;
import io.goeasy.GoEasy;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private ModuleService moduleService;
	@Resource
	private RoleService roleService;
	
	@Resource
	private RedisService redisService;
	
	@Resource
	private AgentService agentService;
	
	public RedisService getRedisService() {
		return redisService;
	}


	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public ModuleService getModuleService() {
		return moduleService;
	}


	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}


	public RoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}


	@RequestMapping("/userManager")
	public String userManager(HttpServletRequest request,QueryDto queryDto) {
		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		User tempUser  = (User)request.getSession().getAttribute("user");
		if(tempUser!= null)
		{
			map.put("operatorId", ((User)request.getSession().getAttribute("user")).getId());
		}
		
		
		
		map.put("name", queryDto.getName());
		map.put("account", queryDto.getAccount());
		map.put("roleId", queryDto.getRoleId());
		map.put("status", queryDto.getStatus());
		map.put("mobile", queryDto.getMobile());
		map.put("email", queryDto.getEmail());
		map.put("endDate", queryDto.getEndDate());
		map.put("startDate", queryDto.getStartDate());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		
		List<Role> roleList  = new ArrayList();
		if(request.getSession().getAttribute("roleList")!= null)
		{
			roleList = (ArrayList)request.getSession().getAttribute("roleList");
		}
		else
		{
			roleList = this.roleService.searchRoleList();
		}
		HashMap roleMap = new HashMap();
		
		if(request.getSession().getAttribute("roleMap")!= null)
		{
			roleMap = (HashMap)request.getSession().getAttribute("roleMap");
		}
		else
		{
			for (int i = 0; i < roleList.size(); i++) {
				
				roleMap.put(roleList.get(i).getId().toString(), roleList.get(i).getName());
			}
		}
		
		
		request.getSession().setAttribute("roleMap", roleMap);
		request.getSession().setAttribute("roleList", roleList);
		
		Page resultPage = this.userService.searchUser(map);
		
		List jsonList = new ArrayList();
		List<User> infoList = resultPage.getItems();
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			for(User user : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", user.getId());
				jobj.put("account", user.getAccount());
				jobj.put("name", user.getName());
				jobj.put("sex", user.getSex());
				jobj.put("mobile", user.getMobile());
				jobj.put("email", user.getEmail());
				jobj.put("createdate", user.getCreatedate().toString());
				jobj.put("password", user.getPassword());
				jobj.put("roleId", user.getRoleId());
				if(roleMap.get(user.getRoleId().toString()) != null)
				{
					jobj.put("roleName", roleMap.get(user.getRoleId().toString()).toString());
				}
				else
				{
					jobj.put("roleName", "");
				}
				if(user.getStatus() ==0)
				{
					jobj.put("status", "正常");
				}
				if(user.getStatus() ==1)
				{
					jobj.put("status", "锁定");
				}
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);	
		
		
		return "user/userManager";
	}
	
	
	
	


	
	@RequestMapping("/saveUser")
	public String saveUser( HttpServletRequest request,User user)throws Exception {
		
		
		
		String userId = RedisKeys.REDIS_USER + MyCookie.getCookie(RedisKeys.ADMIN_USER, request);
		User tempUser = RedisUtil.getUser(userId);
		
		
		if(user.getId()== null || "".equals(user.getId()))
		{
			user.setOperatorid(tempUser.getId());
			user.setCreatedate(new Date());
			user.setPassword(MD5UTIL.encrypt(user.getPassword()));
		}
		else
		{
			tempUser = this.userService.getUser(user.getId());
			user.setCreatedate(tempUser.getCreatedate());
		}
		
		
		
		this.userService.saveUser(user);
		
		return "redirect:userManager";
	}
	
	
	
	@RequestMapping(value = "/deleteUser")
	public String delUser(HttpServletRequest request,HttpServletResponse response) {

		
		userService.deleteUser(request.getParameter("userId"));

		
		return "redirect:userManager";
	}


	public String getStringRandom() {  
        
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < 12; i++) {  
              
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
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request,HttpServletResponse response,User user)throws Exception{
		
		
		
		User tempUser = new User();
		user.setPassword(MD5UTIL.encrypt(user.getPassword()));
		tempUser = this.userService.login(user.getAccount(),user.getPassword());
		
		 if (tempUser.getId()!= null)
		 {
			 request.getSession().setAttribute("user", tempUser); 
			 if (tempUser.getStatus() ==1)
			 {
				 request.setAttribute("message", "您的账号已锁定,请联系管理员!");    
				 request.getRequestDispatcher("../login.jsp").forward(request,response);
				 return null;
			 }
			 else
			 {
				 request.getSession().removeAttribute("user");
				 tempUser.setLastdate(new Date());
				 request.getSession().removeAttribute("accessToken");
				
				 this.userService.saveUser(tempUser);
				 ////System.out.println("userName  = "+tempUser.getName());
				 
				 
//				 this.redisService.set("freedom", "林老虎");
//				 //System.out.println("name = "+this.redisService.get("freedom"));
				 MyCookie.addCookie(RedisKeys.ADMIN_USER,tempUser.getId().toString(), response);
				 
				 String userId = RedisKeys.REDIS_USER+tempUser.getId().toString();
				 tempUser.setCreatedate(null);
				 tempUser.setLastdate(null);
				 this.redisService.setObject(userId,tempUser,RedisKeys.ADMIN_TIME);
				 				 
				 this.redisService.set("loginUser1", tempUser.getAccount(),RedisKeys.ADMIN_TIME);
				 return "redirect:toMain";
			 }
		 }
		 else
		 {
			 if(tempUser.getAccount() == null)
			 {
				 request.setAttribute("message", "账号或密码不正确!");    
			 }
			  
			
			 request.getRequestDispatcher("../login.jsp").forward(request,response);
			 return null;
		 }
		 
		
		 
	}
	
	
	
	@RequestMapping(value="/toMain")
	public String toMain(HttpServletRequest request,HttpServletResponse response)throws Exception{
	

		String userId = RedisKeys.REDIS_USER+MyCookie.getCookie(RedisKeys.ADMIN_USER, request);

		User user = RedisUtil.getUser(userId);

		ArrayList modularData = new ArrayList();
		

		////System.out.println("jsonStr = "+this.redisService.exists(RedisKeys.REDIS_MODULA+user.getId()));
		if(this.redisService.exists(RedisKeys.REDIS_MODULA+user.getId()))
		{
			modularData = (ArrayList)RedisUtil.getModulaByCookie(request);
		}
		else
		{
			
			List modularList = this.roleService.searchRoleModular(user.getRoleId().toString());
			for (int i = 0; i < modularList.size(); i++) {
				Object[] obj=(Object[]) modularList.get(i);
				Modular modular=new Modular();
				modular.setName((String) obj[0]);
				modular.setUrl((String) obj[1]);
				modular.setSort((Integer) obj[2]);
				modular.setParentId((Integer) obj[3]);
				modular.setId((Integer) obj[4]);
				modularData.add(modular);
			}
			
			this.redisService.setList(RedisKeys.REDIS_MODULA+user.getId(), modularData, RedisKeys.ADMIN_TIME);
			
		}
		
	
        
		return "main";
	}
	

	@RequestMapping(value="/out")
	public void out(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
				
		request.getSession().removeAttribute("user");
	
		
//		EasemobUtil easemobUtil = new EasemobUtil();
//			
//		String[] users = new String []{"o82t2o2oon3","o8212w2oow3","o5n52o2oo11"};
//		String action = "epal_play:{\"id\":\"\",\"name\":\"白雪公主\",\"url\":\"http://image.fandoutech.com.cn/322c09755d9e4e73a69b562d9708500a.mp3\",\"action\":1,\"times\":1,\"start\":1}";
//
//		easemobUtil.sendMessage(users,action);
		

		
		response.sendRedirect("../login.jsp");
		 
		 
		 
	}
	
	
	
	@RequestMapping(value="/apiTest")
	public void apiTest(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
				
		//todo :确认是否已弃用
		GoEasy goEasy = new GoEasy(request.getRequestURL().toString(),"BC-82bffd0bf6bd4219a509ed45eb162f78");
		goEasy.publish("freedom","Hello, GoEasy!");
		
		response.sendRedirect("../login.jsp");
		 
		 
		 
	}
	
	@RequestMapping(value="/apiResult")
	public void apiResult(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
				
		
		
		
		response.sendRedirect("../test.jsp");
		 
		 
		 
	}
	

	@RequestMapping(value="/changePwd")
	public String changePwd(HttpServletRequest request,HttpServletResponse response)throws Exception{

	
		return "user/changePwd";
 
	}
	
	@RequestMapping(value="/updateUserPwd")
	public String updateUserPwd(HttpServletRequest request,HttpServletResponse response)throws Exception{

		User user = (User)request.getSession().getAttribute("user");
		
		String pwd = request.getParameter("password");
		pwd  = MD5UTIL.encrypt(pwd);
		this.userService.updateUserPwd(user.getId().toString(), pwd);
		return "redirect:userManager";
 
	}
	
	@RequestMapping(value="/getUserPwd")
	public String getUserPwd(HttpServletRequest request,HttpServletResponse response)throws Exception{

		String userId = request.getParameter("userId");
		String oldPwd = request.getParameter("oldPwd");
		oldPwd  = MD5UTIL.encrypt(oldPwd);
		
		User user  =  RedisUtil.getUserByCookie(request);
		String jsonObj = "";
		if(oldPwd.equals(user.getPassword()))
		{
			
			jsonObj="{\"data\":{\"ok\":\"密码正确!\"}}";
		}
		else
		{
			jsonObj="{\"data\":{\"error\":\"原始密码错误,请重新输入!\"}}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
 
	}
	
	@RequestMapping(value="/getUser")
	public String getUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		
		
		HashMap map = new HashMap();
		map.put("account", request.getParameter("account"));
		map.put("email", request.getParameter("email"));
		
		String str ="";
		if(!"".equals(request.getParameter("account")) && request.getParameter("account")!= null)
		{
			str = "账号";
		}
		if(!"".equals(request.getParameter("email")) && request.getParameter("email")!= null)
		{
			str = "email";
		}
		HashMap tempMap = new HashMap();
		String tempId = "000";
		if(request.getParameter("userId")!= null &&!"".equals(request.getParameter("userId")))
		{
			tempId = request.getParameter("userId");
		}
		tempMap.put("userId", tempId);
		User tempUser = this.userService.getUser(tempMap);
		String jsonObj = "";
		response.setContentType("text/html;charset=UTF-8");
		if(tempUser.getEmail()!= null && tempUser.getEmail().equals(request.getParameter("email")))
		{
			jsonObj="{\"data\":{\"ok\":\""+str+"可以使用!\"}}";
		}
		else if(tempUser.getAccount()!= null && tempUser.getAccount().equals(request.getParameter("account")))
		{
			jsonObj="{\"data\":{\"ok\":\""+str+"可以使用!\"}}";
		}
		else
		{
			User user = this.userService.getUser(map);
			
			
			if(user.getId() != null)
			{
				//jsonObj="{error:'账号已存在,请重新输入!'}";
				jsonObj="{\"data\":{\"error\":\""+str+"已存在,请重新输入!\"}}";
			}
			else
			{
				jsonObj="{\"data\":{\"ok\":\""+str+"可以使用!\"}}";
			}
		}
		
		
		response.getWriter().println(jsonObj);
		return "result";
		
		 
		 
		 
	}
	
	
	

	@RequestMapping(value="/updateUserStatus")
	public String updateUserStatus(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		
		
		this.userService.updateUserStatus(request.getParameter("userId"),request.getParameter("status"));
		return "redirect:userManager";
		 
		 
		 
	}
	
	/*
	 * 账号管理
	 * */

//	@RequestMapping("/userManager")
//	public String channelManage(HttpServletRequest request,
//			HttpServletResponse response,QueryDto queryDto) throws Exception {
//		HashMap map=new HashMap();
//		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
//		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
//		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
//		Page users = agentService.getAgentList(map); 
//		
//		List user = users.getItems();
//		request.setAttribute("jsonData",JsonResult.JsonResultToStr(user));
//		request.setAttribute("queryDto", queryDto);
//		request.setAttribute("resultPage", users);
//		return "agent/agentManager";
//	}
	
}
