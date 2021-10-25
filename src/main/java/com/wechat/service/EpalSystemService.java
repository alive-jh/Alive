package com.wechat.service;

import com.wechat.entity.EpalSystem;

public interface EpalSystemService {

	/**
	 * 保存机器人控制配置
	 * @param epalSystem
	 */
	void saveEpalSystem(EpalSystem epalSystem);
	
	/**
	 * 更新机器人控制配置
	 * @param EpalSystem
	 */
	void updateEpalSystem(EpalSystem epalSystem);
	
	
	/**
	 * 查询机器人控制配置
	 * @param epalId
	 * @return
	 */
	EpalSystem getEpalSystem(String epalId);
}
