package com.wechat.dao.impl;

import com.wechat.dao.FileCachDao;
import com.wechat.entity.FileCach;
import com.wechat.util.Page;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class FileCachDaoImpl extends BaseDaoImpl implements FileCachDao {

	@Override
	public void saveFileCach(FileCach fileCach) {
		this.saveOrUpdate(fileCach);
	}
	@Override
	public void deleteFileCach(Integer id){
		StringBuffer sql = new StringBuffer("delete from filecach where id = "
				+ id);
		this.executeHQL(sql.toString());
	}
	@Override
	public Page searchFileCach(HashMap map){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from filecach where epal_id=");
		sql.append("\"");
		sql.append(map.get("epalId").toString());
		sql.append("\"");
		sql.append(" and path = ");
		sql.append("\"");
		sql.append(map.get("path").toString());
		sql.append("\"");
		sql.append(" ORDER BY id DESC");
		
		Query query = this.getQuery(sql.toString());
		Page resultPage = null;
		FileCach filecach = null;
		ArrayList<FileCach> filecachs = new ArrayList<FileCach>();
		Page dataPage = this.pageQueryBySQL(query, 1, 10000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				filecach = new FileCach();
				filecach.setId((Integer) obj[0]);
				filecach.setDeviceNo((String) obj[2]);
				filecach.setEpalId((String) obj[1]);
				filecach.setPath((String) obj[3]);
				filecach.setCachData((String) obj[4]);
				filecach.setInsertdate((String) obj[5]);
				filecach.setUpdatedate((String) obj[6]);
				filecachs.add(filecach);
			}

		}

		resultPage = new Page<FileCach>(filecachs, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}
}
