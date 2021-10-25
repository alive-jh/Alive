package com.wechat.dao;

import com.wechat.entity.Message;
import net.sf.json.JSONArray;

import java.util.HashMap;

public interface MessageDao {

	void saveMessage(Message message);

	void deleteMessage(String id);

	JSONArray getMessageList(HashMap map);


	
	
		
}
