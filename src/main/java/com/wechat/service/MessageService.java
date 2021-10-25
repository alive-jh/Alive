package com.wechat.service;


import com.wechat.entity.Message;
import net.sf.json.JSONArray;

import java.util.HashMap;

public interface MessageService {

	void saveMessage(Message message);

	void deleteMessage(String id);

	JSONArray getMessageList(HashMap map);

	

}
