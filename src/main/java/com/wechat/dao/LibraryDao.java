package com.wechat.dao;

import com.wechat.entity.Library;
import net.sf.json.JSONArray;

public interface LibraryDao {
	
	void saveLibrary(Library library);
	
	String getBookName(String ISBN);

	JSONArray getbookNameList(String iSBN);

}
