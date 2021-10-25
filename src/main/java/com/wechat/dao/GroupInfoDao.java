package com.wechat.dao;

import com.wechat.entity.GroupInfo;
import com.wechat.util.Page;

import java.util.HashMap;

public interface GroupInfoDao {
	
	void saveGroupInfo(GroupInfo groupInfo);
	
	void deleteGroupInfo(Integer id);
	
	Page searchGroupInfo(HashMap map);
	
	GroupInfo getId(HashMap map);
}
