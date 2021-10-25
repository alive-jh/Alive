package com.wechat.dao.impl;

import com.wechat.dao.AppDao;
import com.wechat.entity.AppFucShow;
import com.wechat.entity.AppTime;
import com.wechat.entity.ProductCategoryShow;
import com.wechat.entity.dto.EpalOnlineTime;
import com.wechat.util.Page;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AppDaoImpl extends BaseDaoImpl implements AppDao {

	@Override
	public void saveAppTime(AppTime appTime) {
		this.saveOrUpdate(appTime);
	}

	@Override
	public Page searchAppTime(HashMap map) {
		StringBuffer sql = new StringBuffer(" from AppTime where 1=1 ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public Page searchDeviceOnLineTotalTime(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT" + " apptime.device_no," + // 0
				" Sum(apptime.time) AS totalTime," + // 1
				" date_format(apptime.createdate,\'%Y-%m-%d\') loginDate ," + // 2
				" apptime.epal_id" + // 3
				" FROM" + " apptime" + " WHERE 1 = 1");
		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" and apptime.device_no = ").append(" :deviceNo ");
		}
		sql.append(" GROUP BY" + " apptime.device_no,"
				+ " date_format(apptime.createdate,\'%Y-%m-%d\')"
				+ " ORDER BY " + " apptime.device_no DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", map.get("deviceNo").toString());
		}

		ArrayList<EpalOnlineTime> epalOnlineTimes = new ArrayList<EpalOnlineTime>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				EpalOnlineTime epalOnlineTime = new EpalOnlineTime();
				epalOnlineTime.setDeviceNo((String) obj[0]);
				epalOnlineTime.setTotalTime(((BigDecimal) obj[1]).intValue());
				epalOnlineTime.setLoginDate((String) obj[2]);
				epalOnlineTime.setEpalId((String) obj[3]);
				epalOnlineTimes.add(epalOnlineTime);
			}

		}

		Page resultPage = new Page<EpalOnlineTime>(epalOnlineTimes,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveAppFucShow(AppFucShow appFucShow) {
		this.saveOrUpdate(appFucShow);
	}

	@Override
	public void saveProductCategoryShow(ProductCategoryShow productCategoryShow) {
		List<ProductCategoryShow> res=this.executeHQL("from ProductCategoryShow where categoryId = "+productCategoryShow.getCategoryId());
		if(res.size()>0){
			ProductCategoryShow categoryShow=res.get(0);
			categoryShow.setShow(productCategoryShow.getShow());
			this.update(categoryShow);
		}else{
			this.save(productCategoryShow);
		}
	}

	@Override
	public List<AppFucShow> findAppFucShow(AppFucShow appFucShow) {
		return this.executeHQL("from AppFucShow where epalId = '" + appFucShow.getEpalId()+"'");
	}
	
	
	

}
