package com.wechat.dao;

import com.wechat.entity.HotKeys;
import com.wechat.util.Page;

import java.util.HashMap;

public interface HotKeysDao {
	void saveHotKeys(HotKeys HotKeys);
	
	Page getHotKeysList(HashMap map);
	
	Page getHotKeysTypeList(HashMap map);
}
