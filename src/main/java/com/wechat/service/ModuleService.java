package com.wechat.service;

import com.wechat.entity.Module;

import java.util.HashMap;
import java.util.List;

public interface ModuleService {

	/**
	 * 保存模块
	 * @param module
	 */
	void saveModule(Module module);
	
	/**
	 * 查询列表
	 * @param map
	 * @return
	 */
	List searchModule(HashMap map);
	
	/**
	 * 删除模块
	 * @param id
	 */
	void deleteModule(String id);
	/**
	 * 查询单个模块 
	 * @param id
	 * @return
	 */
	Module getModule(Integer id);

}
