package com.wechat.service.impl;

import com.wechat.dao.ModuleDao;
import com.wechat.entity.Module;
import com.wechat.service.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

	@Resource
	private ModuleDao moduleDao;
	
	@Override
	public void deleteModule(String id) {
		
		this.moduleDao.deleteModule(id);
	}

	
	@Override
	public Module getModule(Integer id) {
		
		return this.moduleDao.getModule(id);
	}

	
	@Override
	public void saveModule(Module module) {
		
		this.moduleDao.saveModule(module);
	}

	
	@Override
	public List searchModule(HashMap map) {
		
		return this.moduleDao.searchModule(map);
	}

}
