package com.wechat.service.impl;


import com.wechat.dao.HotKeysDao;
import com.wechat.entity.HotKeys;
import com.wechat.service.HotKeysService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class HotKeysServiceImpl implements HotKeysService {
	@Resource
	private HotKeysDao hotKeysDao;
	

	@Override
	public Page getHotKeysList(HashMap map) {
		// TODO Auto-generated method stub
		return hotKeysDao.getHotKeysList(map);
	}
	
	@Override
	public void save(HotKeys hotKeys){
		this.hotKeysDao.saveHotKeys(hotKeys);
	}

	@Override
	public Page getHotKeysTypeList(HashMap map) {
		// TODO Auto-generated method stub
		return this.hotKeysDao.getHotKeysTypeList(map);
	}
}
