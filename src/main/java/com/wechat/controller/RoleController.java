package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Modular;
import com.wechat.entity.Role;
import com.wechat.entity.RoleModular;
import com.wechat.entity.User;
import com.wechat.service.RedisService;
import com.wechat.service.RoleService;
import com.wechat.util.MyCookie;
import com.wechat.util.Page;
import com.wechat.util.RedisKeys;
import com.wechat.util.RedisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Controller
@RequestMapping("role")
public class RoleController {

	@Resource
	private RoleService roleService;
	
	

	public RoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Resource
	private RedisService redisService;
	
	

	public RedisService getRedisService() {
		return redisService;
	}


	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}


	@RequestMapping("/roleManager")
	public String roleManager(HttpServletRequest request,QueryDto queryDto) {
		
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
		map.put("roleId", request.getParameter("roleId"));
		map.put("name", queryDto.getName());
	
		
		
		Page resultPage = this.roleService.searchRole(map);
		request.setAttribute("resultPage", resultPage);
		List modularList = this.roleService.searchModular();
		request.setAttribute("tempModularList", modularList);
		
		
		
		List<Role> infoList  = resultPage.getItems();
		
		List jsonList = new ArrayList();
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Role role : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", role.getId());
				jobj.put("name", role.getName());
				jobj.put("modularIds", role.getModularIds());
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("jsonStr", jsonObj.toString());
		
		request.setAttribute("queryDto", queryDto);
		
		return "role/roleManager";
	}
	
	


	


	
	@RequestMapping("/saveRole")
	public String saveRole( HttpServletRequest request,Role role) throws Exception{
		

		this.roleService.saveRole(role);
		this.roleService.deleteRoleModular(role.getId().toString());
		if(!"".equals(request.getParameter("modularIds")) && request.getParameter("modularIds")!= null)
		{
			String[] modulaIds = request.getParameter("modularIds").split(",");
			RoleModular roleModular = new RoleModular();
			for (int i = 0; i < modulaIds.length; i++) {
				
				 roleModular = new RoleModular();
				 roleModular.setModularId(new Integer(modulaIds[i]));
				 roleModular.setRoleId(role.getId());
				 this.roleService.saveRoleModular(roleModular);
			}
		}
		

		String userId = RedisKeys.REDIS_USER+MyCookie.getCookie(RedisKeys.ADMIN_USER, request);

		User user = RedisUtil.getUser(userId);
		
		this.redisService.del(RedisKeys.REDIS_MODULA+user.getId());
		
		ArrayList modularData = new ArrayList();
		
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
		return "redirect:roleManager";
	}
	
	
	
	@RequestMapping(value = "/deleteRole")
	public String delRole(HttpServletRequest request,HttpServletResponse response) {

		
		this.roleService.deleteRole(request.getParameter("roleId"));
		this.roleService.deleteRoleModular(request.getParameter("roleId"));
		
		return "redirect:roleManager";
	}


	

	

	
	
}
