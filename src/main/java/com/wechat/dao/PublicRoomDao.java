package com.wechat.dao;

import net.sf.json.JSONObject;

import java.util.HashMap;

//import com.alibaba.fastjson.JSONObject;

public interface PublicRoomDao {

	JSONObject getStudentIdByFid(HashMap map);
	
}
