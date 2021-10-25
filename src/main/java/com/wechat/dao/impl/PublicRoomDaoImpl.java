package com.wechat.dao.impl;


import com.wechat.dao.PublicRoomDao;
import com.wechat.util.Page;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class PublicRoomDaoImpl extends BaseDaoImpl implements PublicRoomDao {
	
	
	@Override
	public JSONObject getStudentIdByFid(HashMap map) {
		StringBuffer sql=new StringBuffer("select a.id,a.card_fid,a.student_id,b.name from public_room_fid_to_student as a,class_student as b  where a.student_id=b.id and a.card_fid=:fid order by a.id desc");
		Query query = this.getQuery(sql.toString());
		query.setString("fid", map.get("fid").toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 1);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		JSONObject result = new JSONObject();
		if (dataList.size() > 0) {
			Object[] obj = dataList.get(0);
			result.put("id", obj[0]);
			result.put("fid", obj[1]);
			result.put("studentId", obj[2]);
			result.put("studentName", obj[3]);
		}
		return result;
	}


}
