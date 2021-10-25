package com.wechat.service.impl;


import com.wechat.dao.FileCachDao;
import com.wechat.entity.FileCach;
import com.wechat.service.FileCachService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class FileCachServiceImpl implements FileCachService {
	@Resource
	private
	FileCachDao fileCachDao;
	
	/**
	 * 保存或者修改文件目录缓存入数据库
	 * @param articleLog
	 */
	
	@Override
	public void saveFileCach(FileCach fileCach){
		this.fileCachDao.saveFileCach(fileCach);
	}
	
	@Override
	public void deleteFileCach(Integer id){
		this.fileCachDao.deleteFileCach(id);
	}
	
	@Override
	public Page searchFileCach(HashMap map){
		return this.fileCachDao.searchFileCach(map);
	}
}
