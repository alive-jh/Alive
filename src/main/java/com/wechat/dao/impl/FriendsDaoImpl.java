package com.wechat.dao.impl;

import com.mysql.jdbc.PreparedStatement;
import com.wechat.dao.FriendsDao;
import com.wechat.entity.Friends;
import com.wechat.util.Page;
import com.wechat.util.PropertyUtil;
import org.hibernate.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class FriendsDaoImpl extends BaseDaoImpl implements FriendsDao {
	
	
	static final String JDBC_DRIVER = PropertyUtil
			.getDataBaseProperty("driverClass");
	static final String DB_URL = PropertyUtil.getDataBaseProperty("jdbcUrl");

	static final String USER = PropertyUtil.getDataBaseProperty("jdbcName");
	static final String PASS = PropertyUtil.getDataBaseProperty("password");
	
	@Override
	public void saveFirends(Friends friends) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(friends);
		
	}
	@Override
	public void deleteFirends(Integer id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from Friends where id="
				+ id);
		this.executeHQL(sql.toString());
		
	}
	@Override
	public Page searchFirends(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select id,user_id,friend_id,friend_type,group_id,ifnull(note_name,ifnull((select nickname from (SELECT epal_id AS accountId,nickname FROM device UNION ALL SELECT mobile AS accountId,nickname FROM member where member.type=2) c where friends.friend_id=c.accountId),friend_id)) from friends where user_id=:userId");

		Query query = this.getQuery(sql.toString());
		if (null != map.get("userId")
				&& !"".equals(map.get("userId").toString())) {
			query.setString("userId", map.get("userId").toString());
		}
		

		Page dataPage = this.pageQueryBySQL2(query, 1, 1000);
		ArrayList<Friends> friends = new ArrayList<Friends>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Friends friend= new Friends();
				friend.setId((Integer) obj[0]);
				friend.setUserId((String) obj[1]);
				friend.setFriendId((String) obj[2]);
				friend.setFriendType((String) obj[3]);	
				friend.setNoteName((String) obj[5]);
				friend.setGroupId((String) obj[4]);
				friends.add(friend);
			}
		}

		Page resultPage = new Page<Friends>(friends, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		
		return resultPage;
		
	}
	
	
	@Override
	public Friends getId(HashMap map) {
		// TODO Auto-generated method stub
		Friends friends = new Friends();
		StringBuffer sql = new StringBuffer("select * from friends where user_id=:userId and friend_id=:friendId ");
		Query query = this.getQuery(sql.toString());
		if (null != map.get("userId")
				&& !"".equals(map.get("userId").toString())) {
			query.setString("userId", map.get("userId").toString());
		}

		if (null != map.get("friendId")
				&& !"".equals(map.get("friendId").toString())) {
			query.setString("friendId", map.get("friendId").toString());
		}
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {
			Object[] obj = dataList.get(0);
			friends.setId((Integer) obj[0]);
			friends.setUserId((String) obj[1]);
			friends.setFriendId((String) obj[2]);
			friends.setFriendType((String) obj[3]);
			friends.setNoteName((String) obj[4]);
			friends.setGroupId((String) obj[5]);
			return friends;
		}else{
			return friends;
		}
		
	}
	@Override
	public void batchSaveFriends(String userId, JSONArray friendIds) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuffer insertMainCourseSql = new StringBuffer(
				"insert into friends (user_id,friend_id,friend_type,note_name,group_id) values (?,?,?,?,?)");
		try {
			pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql
					.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("1");
		for (int i = 0; i < friendIds.length(); i++) {
			try {
				pst.setString(1, userId);

				try {
					pst.setString(2, friendIds.get(i).toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pst.setInt(3, 1);
				pst.setString(4, null);
				pst.setInt(5, 1);
				pst.addBatch();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("2");
		try {
			pst.executeBatch(); // 执行批量处理
			conn.commit(); // 提交
			conn.close();
			//System.out.println("3");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
