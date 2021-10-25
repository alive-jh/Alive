package com.wechat.service;

import com.wechat.entity.AppFucShow;
import com.wechat.entity.AppTime;
import com.wechat.entity.ProductCategoryShow;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface AppService {

	
	
	/**
	 * 添加APP使用时间记录
	 * @param AppTime
	 */
	void saveAppTime(AppTime appTime);
	
	/**
	 * 查询APP使用时间列表
	 * @param map
	 * @return
	 */
	Page searchAppTime(HashMap map);

	Page searchDeviceOnLineTotalTime(HashMap map);

	void saveAppFucShow(AppFucShow appFucShow);

	void saveProductCategoryShow(ProductCategoryShow productCategoryShow);

	List<AppFucShow> findAppFucShow(AppFucShow appFucShow);

	
}
