package com.wechat.service.impl;


import com.wechat.dao.LibraryDao;
import com.wechat.entity.Library;
import com.wechat.service.LibraryService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LibraryServiceImpl implements LibraryService {
	@Resource
	private LibraryDao libraryDao;
	
	/**
	 * 保存app用户的好友列表到数据库
	 * @param articleLog
	 */

	@Override
	public void saveLibrary(Library library) {
		// TODO Auto-generated method stub
		this.libraryDao.saveLibrary(library);
		
	}

	@Override
	public String getBookName(String ISBN){
		return this.libraryDao.getBookName(ISBN);
	}

	@Override
	public JSONArray getbookNameList(String iSBN) {
		// TODO Auto-generated method stub
		return this.libraryDao.getbookNameList(iSBN);
	}
}
