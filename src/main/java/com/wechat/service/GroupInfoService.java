package com.wechat.service;


import com.wechat.entity.GroupInfo;
import com.wechat.util.Page;

import java.util.HashMap;

public interface GroupInfoService {
	void saveGroupInfo(GroupInfo groupInfo);
	
	void deleteGroupInfo(Integer id);
	
	Page searchGroupInfo(HashMap map);
	
	GroupInfo getId(HashMap map);
}
