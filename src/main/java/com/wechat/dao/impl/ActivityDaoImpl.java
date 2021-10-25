package com.wechat.dao.impl;

import com.wechat.dao.ActivityDao;
import com.wechat.entity.Activity;
import com.wechat.entity.ActivityInfo;
import com.wechat.entity.ActivityLog;
import com.wechat.entity.ActivityPrize;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl implements ActivityDao {

	@Override
	public void deleteActivity(String id) {
		
		this.executeUpdateSQL(" delete from activity where id = "+id);
	}

	@Override
	public void deleteActivityPrize(String id) {
		
		this.executeUpdateSQL(" delete from activityprize where id = "+id);
	}

	@Override
	public void saveActivity(Activity activity) {
		
		this.saveEntity(activity);
	}

	@Override
	public void saveActivityInfo(ActivityInfo activityInfo) {
		
		this.saveEntity(activityInfo);
	}

	@Override
	public void saveActivityLog(ActivityLog activityLog) {
		
		this.saveEntity(activityLog);
	}

	@Override
	public void saveActivityPrize(ActivityPrize activityPrize) {
		
		this.saveEntity(activityPrize);
	}

	@Override
	public Page searchActivity(HashMap map) {
		
		StringBuffer sql  = new StringBuffer(" select a.*,b.info from activity a "
		+" left join "
		+" ( "
		+" select activityid,GROUP_CONCAT(id,'<',name,'<',percentage,'<',count) info from activityprize group by activityid "
		+" ) b  on a.id = b.activityid");
		
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()),new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public Page searchActivityInfo(HashMap map) {
		
		return null;
	}

	@Override
	public Object[] searchActivityInfoByOpenId(String openId) {
		
		return null;
	}

	@Override
	public List searchActivityLog(String openId) {
		
		return this.executeHQL(" from ActivityLog where openId '= "+openId+"'");
	}

	@Override
	public List searchActivityPrize(String id) {
		
		return null;
	}

	@Override
	public void updateActivityInfo(String id, String status) {
		
		this.executeUpdateSQL(" update activityinfo set status = "+status+" where id = "+id);
	}

}
