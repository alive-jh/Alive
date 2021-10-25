package com.wechat.dao;

import com.wechat.entity.Friends;
import com.wechat.util.Page;
import org.json.JSONArray;

import java.util.HashMap;

public interface FriendsDao {
	
	void saveFirends(Friends friends);
	
	void deleteFirends(Integer id);
	
	Page searchFirends(HashMap map);
	
	Friends getId(HashMap map);

	void batchSaveFriends(String userId, JSONArray friendIds);
}
