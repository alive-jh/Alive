package com.wechat.service.impl;


import com.wechat.dao.GroupInfoDao;
import com.wechat.entity.GroupInfo;
import com.wechat.service.GroupInfoService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class GroupInfoServiceImpl implements GroupInfoService {
	@Resource
	private GroupInfoDao groupInfoDao;
	
	/**
	 * 保存app用户群备注
	 * @param articleLog
	 */

	@Override
	public void saveGroupInfo(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		this.groupInfoDao.saveGroupInfo(groupInfo);
	}

	@Override
	public void deleteGroupInfo(Integer id) {
		// TODO Auto-generated method stub
		this.groupInfoDao.deleteGroupInfo(id);
		
	}

	@Override
	public Page searchGroupInfo(HashMap map) {
		// TODO Auto-generated method stub
		return this.groupInfoDao.searchGroupInfo(map);
	}
	
	@Override
	public GroupInfo getId(HashMap map){
		return this.groupInfoDao.getId(map);
	}
}
