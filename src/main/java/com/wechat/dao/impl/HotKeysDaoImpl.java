package com.wechat.dao.impl;

import com.wechat.dao.HotKeysDao;
import com.wechat.entity.HotKeys;
import com.wechat.entity.HotKeysGroup;
import com.wechat.util.Page;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Repository
public class HotKeysDaoImpl extends BaseDaoImpl implements HotKeysDao {
	@Override
	public void saveHotKeys(HotKeys HotKeys) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(HotKeys);
		
	}

	@Override
	public Page getHotKeysList(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from hot_keys");

		if (null != map.get("keyType")
				&& !"".equals(map.get("keyType").toString())) {
			sql.append(" where key_type=").append(" :keyType ");
		}
		sql.append(" order by score DESC");
		
		Query query = this.getQuery(sql.toString());
		if (null != map.get("keyType")
				&& !"".equals(map.get("keyType").toString())) {
			query.setString("keyType", map.get("keyType").toString());
		}
		

		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<HotKeys> HotKeysList = new ArrayList<HotKeys>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HotKeys hotKeys= new HotKeys();
				hotKeys.setId((Integer) obj[0]);
				hotKeys.setKeys((String) obj[1]);
				hotKeys.setKeyType((String) obj[2]);
				hotKeys.setScore((Integer) obj[3]);
				HotKeysList.add(hotKeys);
			}

		}
		Page resultPage = new Page<HotKeys>(HotKeysList, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public Page getHotKeysTypeList(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from hotkey_group");
		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<HotKeysGroup> hotKeysGroupList = new ArrayList<HotKeysGroup>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HotKeysGroup hotKeysGroup= new HotKeysGroup();
				hotKeysGroup.setId((Integer) obj[0]);
				hotKeysGroup.setGroupName((String) obj[1]);
				hotKeysGroup.setGroupCode((String) obj[2]);
				hotKeysGroup.setStatus((Integer) obj[3]);
				hotKeysGroup.setUpdateDate( (Date) obj[4]);
				hotKeysGroup.setInsertDate((Date) obj[5]);
				hotKeysGroupList.add(hotKeysGroup);
			}

		}
		Page resultPage = new Page<HotKeysGroup>(hotKeysGroupList, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}
}
