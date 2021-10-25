package com.wechat.dao;

import com.wechat.entity.SoundCollection;
import com.wechat.util.Page;


public interface SoundCollectionDao{
	
	SoundCollection getBySoundId(int soundId);
	Page<Object[]> getByISBN(String sound, String userId, String name, int currentPage, int pageSize);
	
}
