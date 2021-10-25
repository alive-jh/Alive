package com.wechat.dao.impl;

import com.wechat.dao.GroupInfoDao;
import com.wechat.entity.GroupInfo;
import com.wechat.util.Page;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class GroupInfoDaoImpl extends BaseDaoImpl implements GroupInfoDao {
	@Override
	public void saveGroupInfo(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(groupInfo);
		
	}
	@Override
	public void deleteGroupInfo(Integer id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from GroupInfo where id = "
				+ id);
		this.executeHQL(sql.toString());
		
	}
	@Override
	public Page searchGroupInfo(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select * from group_info");

		if (null != map.get("groupId")
				&& !"".equals(map.get("groupId").toString())) {
			sql.append(" where group_id=").append(" :groupId ");
		}

		Query query = this.getQuery(sql.toString());
		if (null != map.get("groupId")
				&& !"".equals(map.get("groupId").toString())) {
			query.setString("groupId", map.get("groupId").toString());
		}
		ArrayList<GroupInfo> groupInfos = new ArrayList<GroupInfo>();
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				GroupInfo groupInfo= new GroupInfo();
				groupInfo.setId((Integer) obj[0]);
				groupInfo.setUserId((String) obj[1]);
				groupInfo.setGroupId((String) obj[2]);
				groupInfo.setNoteName((String) obj[3]);
				groupInfos.add(groupInfo);
			}

		}

		Page resultPage = new Page<GroupInfo>(groupInfos, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());


		return resultPage;
	}
	@Override
	public GroupInfo getId(HashMap map) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = new GroupInfo();
		StringBuffer sql = new StringBuffer("select * from group_info where user_id=:userId and group_id=:groupId ");
		Query query = this.getQuery(sql.toString());
		if (null != map.get("userId")
				&& !"".equals(map.get("userId").toString())) {
			query.setString("userId", map.get("userId").toString());
		}

		if (null != map.get("groupId")
				&& !"".equals(map.get("groupId").toString())) {
			query.setString("groupId", map.get("groupId").toString());
		}
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {
			Object[] obj = dataList.get(0);
			groupInfo.setId((Integer) obj[0]);
			groupInfo.setUserId((String) obj[1]);
			groupInfo.setGroupId((String) obj[2]);
			groupInfo.setNoteName((String) obj[3]);
			return groupInfo;
		}else{
			return groupInfo;
		}
	}
}
