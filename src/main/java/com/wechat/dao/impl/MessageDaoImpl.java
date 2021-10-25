package com.wechat.dao.impl;

import com.wechat.dao.MessageDao;
import com.wechat.entity.Message;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao{

	@Override
	public void saveMessage(Message message) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(message);
	}

	@Override
	public void deleteMessage(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from Message where id="
				+ id);
		this.executeHQL(sql.toString());
	}

	@Override
	public JSONArray getMessageList(HashMap map) {
		// TODO Auto-generated method stub
		String to = map.get("to").toString();
		String id = map.get("id").toString();
		StringBuffer sql=new StringBuffer("select id,to_user,from_user,type,content,create_date,update_date from message where 1=1");
		if(!"".equals(id)){
			sql.append(" and id=:id");
		}
		if(!"".equals(to)){
			sql.append(" and to_user=:to");
		}
		sql.append(" order by id desc");
		Query query = this.getQuery(sql.toString());
		if(!"".equals(id)){
			query.setInteger("id",Integer.parseInt(id));
		}
		if(!"".equals(to)){
			query.setString("to",to);
		}
		
		Page dataPage = this.pageQueryBySQL(query, 1, 10);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject temp = new JSONObject();
				temp.put("id", (Integer) obj[0]);
				temp.put("to", (String) obj[1]);
				temp.put("from", (String) obj[2]);
				temp.put("type", (String) obj[3]);
				temp.put("message", (String) obj[4]);
				temp.put("createDate", (String) obj[5]);
				temp.put("updateDate", (String) obj[6]);
				result.add(temp);
			}
		}
		return result;
	}

	
}
