package com.wechat.service;


import com.wechat.entity.Friends;
import com.wechat.util.Page;
import org.json.JSONArray;

import java.util.HashMap;

public interface FriendsService {
	void saveFriends(Friends friends);
	
	void deleteFriends(Integer id);
	
	Page searchFriends(HashMap map);
	
	Friends getId(HashMap map);

	void batchSaveFriends(String userId, JSONArray friendIds);
}
