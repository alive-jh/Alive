package com.wechat.service.impl;


import com.wechat.dao.FriendsDao;
import com.wechat.entity.Friends;
import com.wechat.service.FriendsService;
import com.wechat.util.Page;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class FriendsServiceImpl implements FriendsService {
	@Resource
	private FriendsDao friendsDao;
	
	/**
	 * 保存app用户的好友列表到数据库
	 * @param articleLog
	 */

	@Override
	public void saveFriends(Friends friends) {
		// TODO Auto-generated method stub
		this.friendsDao.saveFirends(friends);
		
	}

	@Override
	public void deleteFriends(Integer id) {
		// TODO Auto-generated method stub
		this.friendsDao.deleteFirends(id);
		
	}

	@Override
	public Page searchFriends(HashMap map) {
		// TODO Auto-generated method stub
		return this.friendsDao.searchFirends(map);
	}
	
	@Override
	public Friends getId(HashMap map){
		return this.friendsDao.getId(map);
	}

	@Override
	public void batchSaveFriends(String userId, JSONArray friendIds) {
		// TODO Auto-generated method stub
		this.friendsDao.batchSaveFriends(userId,friendIds);
		
	}
}
