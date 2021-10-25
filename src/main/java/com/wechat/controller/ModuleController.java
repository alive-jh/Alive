package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Module;
import com.wechat.service.ModuleService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("module")
public class ModuleController {

	@Resource
	private ModuleService moduleService;
	
	@RequestMapping("/moduleManager")
	public String moduleManager(HttpServletRequest request,QueryDto queryDto) {
		
		HashMap map = new HashMap();
	
		List<Module> moduleList = this.moduleService.searchModule(map);
		request.setAttribute("moduleList", moduleList);
		request.setAttribute("queryDto", queryDto);
		StringBuffer str = new StringBuffer("");

		
		for (int i = 0; i < moduleList.size(); i++) {
			
			str.append("{id:"+moduleList.get(i).getId().toString()+",pId:"+moduleList.get(i).getParentId()+",name:'"+moduleList.get(i).getName()+"',open:true,moduleUrl:'"+moduleList.get(i).getUrl()+"'}");
			if(i+1!=moduleList.size())
			{
				str.append(",");
			}
		}
		request.setAttribute("jsonStr", str.toString());
		//System.out.println("jsonStr = "+str.toString());
		return "module/moduleManager";
	}
	
	
	@RequestMapping(value="/moduleManagerView")
	public String moduleManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
		Module module = this.moduleService.getModule(new Integer(request.getParameter("moduleId")));
		JSONObject jobj = new JSONObject();
		jobj.put("id", module.getId());
		jobj.put("name", module.getName());
		jobj.put("parentId", module.getParentId());
		jobj.put("sort", module.getSort());
		
		List jsonList = new ArrayList();
		jsonList.add(jobj);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("infoList", jsonList);
		
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
		 
		 
	}

	


	
	@RequestMapping("/saveModule")
	public String saveModule( HttpServletRequest request,Module module) {
		
		module.setCreateDate(new Date());
		this.moduleService.saveModule(module);
		
		return "redirect:moduleManager";
	}
	
	
	
	@RequestMapping(value = "/deleteModule")
	public String delModule(HttpServletRequest request,HttpServletResponse response) {

		
		moduleService.deleteModule(request.getParameter("userId"));

		
		return "redirect:moduleManager";
	}
}
