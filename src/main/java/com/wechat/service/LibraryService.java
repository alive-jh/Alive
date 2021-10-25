package com.wechat.service;

import com.wechat.entity.Library;
import net.sf.json.JSONArray;

public interface LibraryService {
	void saveLibrary(Library library);
	
	String getBookName(String ISBN);

	JSONArray getbookNameList(String iSBN);
	
	
}
