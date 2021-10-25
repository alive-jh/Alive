package com.wechat.dao;

import com.wechat.entity.FileCach;
import com.wechat.util.Page;

import java.util.HashMap;

public interface FileCachDao {
	
	void saveFileCach(FileCach fileCach);
	
	void deleteFileCach(Integer id);
	
	Page searchFileCach(HashMap map);
}
