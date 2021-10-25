package com.wechat.dao.impl;

import com.wechat.dao.UserDao;
import com.wechat.entity.MemberCard;
import com.wechat.entity.User;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {


	@Override
	public void saveUser(User user) {
		
		this.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String id) {
		User user  = new User();
		user.setId(new Integer(id));
		this.removeEntity(user);
		
	}

	@Override
	public Page searchUser(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from User where 1=1 ");
		
		if(!"".equals(map.get("userId")) && map.get("userId")!= null)
		{
			sql.append(" and id =  ").append(map.get("userId").toString());
		}
		
		if(!"".equals(map.get("name")) && map.get("name")!= null)
		{
			sql.append(" and name like '%").append(map.get("name").toString()).append("%'");
		}
		if(!"".equals(map.get("account")) && map.get("name")!= null)
		{
			sql.append(" and account like '%").append(map.get("account").toString()).append("%'");
		}
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and status = ").append(map.get("status").toString());
		}
		if(!"".equals(map.get("roleId")) && map.get("roleId")!= null)
		{
			sql.append(" and roleid = ").append(map.get("roleId").toString());
		}
		if(!"".equals(map.get("email")) && map.get("email")!= null)
		{
			sql.append(" and email = '").append(map.get("email").toString()).append("'");
		}
		if(!"".equals(map.get("mobile")) && map.get("mobile")!= null)
		{
			sql.append(" and mobile = '").append(map.get("mobile").toString()).append("'");
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and createdate >= '").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and createdate >= '").append(map.get("endDate").toString()).append("'");
		}
		
		sql.append(" order by createdate desc ");
		
		
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public void updateUser(User user) {

		this.saveOrUpdate(user);
		
	}

	@Override
	public User getUser(Integer id) {
		
		return (User) this.getEntity(User.class,id);
	}


	@Override
	public User getUser(HashMap map) throws Exception {
		
		StringBuffer sql = new StringBuffer(" from User where 1=1 ");
		if(map.get("account")!= null && !"".equals(map.get("account")))
		{
			sql.append(" and account ='").append(map.get("account")).append("' ");
		}
		
		if(map.get("email")!= null && !"".equals(map.get("email")))
		{
			sql.append(" and email ='").append(map.get("email")).append("' ");
		}
		if(map.get("userId")!= null && !"".equals(map.get("userId")))
		{
			sql.append(" and id ='").append(map.get("userId")).append("' ");
		}
		User user = new User();
		List list = this.executeHQL(sql.toString());
		if(list.size()>0)
		{
			user = (User)list.get(0);
		}
		return user;
	}


	
	@Override
	public void updateUserStatus(String userId,String status)throws Exception
	{
		this.executeUpdateSQL("update  user set status = "+status +" where id = "+userId);
		
	}
	@Override
	public void updateUserPwd(String userId,String password)throws Exception
	{
		this.executeUpdateSQL("update  user set password = '"+password +"' where id = "+userId);
		
	}

	
	@Override
	public User login(String account, String password) throws Exception {

		String sql = "from User where account ='"+account+"' and password='"+password+"'";
		User user = new User();
		List<User> list = this.executeHQL(sql);
		if(list.size()>0)
		{
			user = list.get(0);
		}
		return user;
	}

	
	@Override
	public HashMap getOperatorNameMap()
	{
		HashMap map = new HashMap();
		
		List list = this.executeSQL(" select id,name from user where 1=1");
		for (int i = 0; i < list.size(); i++)
		{
			map.put(((Object[])list.get(i))[0].toString(), ((Object[])list.get(i))[1].toString());
		}
		return map;
	}

	@Override
	public void saveMemberCard(MemberCard memberCard) {
		
		
		this.save(memberCard);
		
	}
	

	
}
