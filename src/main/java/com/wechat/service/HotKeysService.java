package com.wechat.service;


import com.wechat.entity.HotKeys;
import com.wechat.util.Page;

import java.util.HashMap;

public interface HotKeysService {
	
	 void save(HotKeys hotKeys);
	
	 Page getHotKeysList(HashMap map);
	 
	 Page getHotKeysTypeList(HashMap map);

}
