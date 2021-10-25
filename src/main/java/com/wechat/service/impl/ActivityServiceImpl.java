package com.wechat.service.impl;

import com.wechat.dao.ActivityDao;
import com.wechat.entity.Activity;
import com.wechat.entity.ActivityInfo;
import com.wechat.entity.ActivityLog;
import com.wechat.entity.ActivityPrize;
import com.wechat.service.ActivityService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Resource
	private ActivityDao activityDao;

	public ActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	@Override
	public void deleteActivity(String id) {
		
		this.activityDao.deleteActivity(id);
	}

	@Override
	public void deleteActivityPrize(String id) {
		
		this.activityDao.deleteActivityPrize(id);
	}

	@Override
	public void saveActivity(Activity activity) {
		
		this.activityDao.saveActivity(activity);
	}

	@Override
	public void saveActivityInfo(ActivityInfo activityInfo) {
		
		this.activityDao.saveActivityInfo(activityInfo);
	}

	@Override
	public void saveActivityLog(ActivityLog activityLog) {
		
		this.activityDao.saveActivityLog(activityLog);
	}

	@Override
	public void saveActivityPrize(ActivityPrize activityPrize) {
		
		this.activityDao.saveActivityPrize(activityPrize);
	}

	@Override
	public Page searchActivity(HashMap map) {
		
		return this.activityDao.searchActivity(map);
	}

	@Override
	public Page searchActivityInfo(HashMap map) {
		
		return this.activityDao.searchActivityInfo(map);
	}

	@Override
	public Object[] searchActivityInfoByOpenId(String openId) {
		
		return this.activityDao.searchActivityInfoByOpenId(openId);
	}

	@Override
	public List searchActivityLog(String openId) {
		
		return this.activityDao.searchActivityLog(openId);
	}

	@Override
	public List searchActivityPrize(String id) {
		
		return this.activityDao.searchActivityPrize(id);
	}

	@Override
	public void updateActivityInfo(String id, String status) {
		
		this.activityDao.updateActivityInfo(id, status);
	}

	
	
}
