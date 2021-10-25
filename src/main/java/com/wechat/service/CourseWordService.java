package com.wechat.service;

import com.wechat.util.Page;
import net.sf.json.JSONArray;

import java.util.HashMap;


public interface CourseWordService {

	Page getWordGroupList(HashMap map);

	JSONArray getWordSourceList(HashMap map);

	JSONArray searchWords(HashMap map);

}
