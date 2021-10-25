package com.wechat.service.impl;

import com.wechat.dao.MessageDao;
import com.wechat.entity.Message;
import com.wechat.service.MessageService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


@Service
public class MessageServiceImpl implements MessageService {
	
	@Resource
	private MessageDao messageDao;

	@Override
	public void saveMessage(Message message) {
		// TODO Auto-generated method stub
		this.messageDao.saveMessage(message);
	}

	@Override
	public void deleteMessage(String id) {
		// TODO Auto-generated method stub
		this.messageDao.deleteMessage(id);
	}

	@Override
	public JSONArray getMessageList(HashMap map) {
		// TODO Auto-generated method stub
		return this.messageDao.getMessageList(map);
	}

	

}
