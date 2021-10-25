package com.wechat.dao;

import com.wechat.entity.Activity;
import com.wechat.entity.ActivityInfo;
import com.wechat.entity.ActivityLog;
import com.wechat.entity.ActivityPrize;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface ActivityDao {

	/**
	 * 保存活动
	 * @param activity
	 */
	void saveActivity(Activity activity);
	
	/**
	 * 删除活动
	 * @param id
	 */
	void deleteActivity(String id);
	
	/**
	 * 查询活动
	 * @param map
	 * @return
	 */
	Page searchActivity(HashMap map);
	
	/**
	 * 保存中奖信息
	 * @param activityInfo
	 */
	void saveActivityInfo(ActivityInfo activityInfo);
	
	/**
	 * 更新中奖信息状态
	 * @param id
	 * @param status
	 */
	void updateActivityInfo(String id, String status);
	
	/**
	 * 查询中奖列表
	 * @param map
	 * @return
	 */
	Page searchActivityInfo(HashMap map);
	
	/**
	 * 查询用户奖品信息
	 * @param openId
	 * @return
	 */
	Object[] searchActivityInfoByOpenId(String openId);
	
	/**
	 * 保存活动奖品
	 * @param activityPrize
	 */
	void saveActivityPrize(ActivityPrize activityPrize);
	
	/**
	 * 删除活动奖品
	 * @param id
	 */
	void deleteActivityPrize(String id);
	
	
	/**
	 * 查询奖品列表
	 * @param id
	 * @return
	 */
	List searchActivityPrize(String id);
	
	/**
	 * 保存活动记录
	 * @param activityLog
	 */
	void saveActivityLog(ActivityLog activityLog);
	
	/**
	 * 查询活动记录
	 * @param openId
	 * @return
	 */
	List searchActivityLog(String openId);
	
	
}
