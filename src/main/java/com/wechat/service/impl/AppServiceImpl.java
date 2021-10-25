package com.wechat.service.impl;

import com.wechat.dao.AppDao;
import com.wechat.entity.AppFucShow;
import com.wechat.entity.AppTime;
import com.wechat.entity.ProductCategoryShow;
import com.wechat.service.AppService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class AppServiceImpl implements AppService{

	@Resource
	private AppDao appDao;
	
	@Override
	public void saveAppTime(AppTime appTime) {
		
		this.appDao.saveAppTime(appTime);
	}

	@Override
	public Page searchAppTime(HashMap map) {
		
		return this.appDao.searchAppTime(map);
	}

	@Override
	public Page searchDeviceOnLineTotalTime(HashMap map) {
		return this.appDao.searchDeviceOnLineTotalTime(map);
	}

	@Override
	public void saveAppFucShow(AppFucShow appFucShow) {
		this.appDao.saveAppFucShow(appFucShow);
	}

	@Override
	public void saveProductCategoryShow(ProductCategoryShow productCategoryShow) {
		this.appDao.saveProductCategoryShow(productCategoryShow);
	}

	@Override
	public List<AppFucShow> findAppFucShow(AppFucShow appFucShow) {
		return this.appDao.findAppFucShow(appFucShow);
	}
	
	
	
	

}
