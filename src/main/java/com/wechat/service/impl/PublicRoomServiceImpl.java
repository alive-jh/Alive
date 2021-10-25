package com.wechat.service.impl;


import com.wechat.dao.PublicRoomDao;
import com.wechat.service.PublicRoomService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class PublicRoomServiceImpl implements PublicRoomService {
	@Resource
	private PublicRoomDao publicRoomDao;
	
	/**
	 * 通过Fid卡号，获取学生ID
	 * @param
	 */

	@Override
	public JSONObject getStudentIdByFid(HashMap map) {
		// TODO Auto-generated method stub
		return publicRoomDao.getStudentIdByFid(map);
	}
}
