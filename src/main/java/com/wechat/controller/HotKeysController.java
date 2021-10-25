package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.HotKeysGroup;
import com.wechat.service.HotKeysService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("HotKeys")
public class HotKeysController {

	@Resource
	private HotKeysService hotKeysService;
	

	/**
	 * 根据分类获取热词列表
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping("HotKeysManage")
	public String getHotKeysList(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page hotKeysTypeList=hotKeysService.getHotKeysTypeList(map); 
		List hotKeytypeList = hotKeysTypeList.getItems();
//		
		ArrayList queArr = new ArrayList();
		for (int i = 0; i < hotKeytypeList.size(); i++) {
			HotKeysGroup hotKeysGroup=(HotKeysGroup) hotKeytypeList.get(i);
			HashMap hotKyesGroupMap = new HashMap();
			hotKyesGroupMap.put("id", hotKeysGroup.getId());
			hotKyesGroupMap.put("groupName", hotKeysGroup.getGroupName());
			hotKyesGroupMap.put("groupCode", hotKeysGroup.getGroupCode());		
			hotKyesGroupMap.put("status", hotKeysGroup.getStatus());		
//			hotKyesGroupMap.put("insertDate", hotKeysGroup.getInsertDate());		
//			hotKyesGroupMap.put("updateDate", hotKeysGroup.getUpdateDate());		
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insertDate =sdf.format(hotKeysGroup.getInsertDate());
			hotKyesGroupMap.put("insertDate", insertDate);
			
			
			String updateDate =sdf.format(hotKeysGroup.getUpdateDate());
			hotKyesGroupMap.put("updateDate", updateDate);
			queArr.add(hotKyesGroupMap);
		}
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(queArr));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", hotKeysTypeList);
		return "hotkeys/hotkeysManager";

	}
	
	
}
