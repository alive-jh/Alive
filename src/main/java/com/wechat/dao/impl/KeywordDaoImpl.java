package com.wechat.dao.impl;

import com.wechat.dao.KeywordDao;
import com.wechat.entity.Keyword;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class KeywordDaoImpl extends BaseDaoImpl implements KeywordDao{

	@Override
	public Keyword getKeyword(Keyword keyword)
			throws Exception {
	
		return (Keyword)this.getEntity(Keyword.class,keyword.getId());
	}

	

	@Override
	public void removeKeyword(String id) throws Exception {

		this.executeUpdateSQL(" delete from keyword where id  in ( "+id+" ) ");
		
	}

	@Override
	public void saveKeyword(Keyword keyword) throws Exception {

		this.saveOrUpdate(keyword);
		
	}

	@Override
	public Page searchKeyword(HashMap map) throws Exception {
		
		StringBuffer sql  = new StringBuffer("select id,keyword,content,date_format(createdate,'%Y-%m-%d'),matchingrules,contenttype,status  from keyword where 1=1");
		if(!"".equals(map.get("type")) && map.get("type")!= null)
		{
			sql.append(" and contenttype = ").append(map.get("type"));
		}
		
		if(!"".equals(map.get("matchingrules")) && map.get("matchingrules")!= null)
		{
			sql.append(" and matchingrules = ").append(map.get("matchingrules"));
		}
		
		if(!"".equals(map.get("keyword")) && map.get("keyword")!= null)
		{
			sql.append(" and keyword like '%").append(map.get("keyword").toString().trim()).append("%'");
		}
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and status = ").append(map.get("status"));
		}
		if(!"".equals(map.get("accountId")) && map.get("accountId")!= null)
		{
			sql.append(" and accountid = ").append(map.get("accountId"));
		}
		if(!"".equals(map.get("keywordId")) && map.get("keywordId")!= null)
		{
			sql.append(" and id = ").append(map.get("keywordId"));
		}
		sql.append(" order by createdate desc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}
	
	@Override
	public HashMap searchKeywordMap(String accountId) throws Exception
	{
		HashMap map = new HashMap();
		String sql = "select keyword,content,contenttype,materialid ,matchingrules from keyword where 1=1 and matchingrules=0 and status = 0 and accountid = "+accountId;
		List list = this.executeSQL(sql);
		Object[] obj = new Object[2];
		for (int i = 0; i < list.size(); i++) {
			
			obj = (Object[])list.get(i);
			map.put(obj[0].toString(), obj);
		}
		return map;
	}
	
	
	
	@Override
	public List<Object[]> searchKeywordList(String AccountId) throws Exception
	{
		HashMap map = new HashMap();
		String sql = "select keyword ,content,contenttype,materialid,matchingrules from keyword where 1=1 and matchingrules=1 and status = 0  and accountid = "+AccountId;
		return this.executeSQL(sql);
		
	}
	
	@Override
	public HashMap searchAllKeywordMap(String AccountId) throws Exception
	{
		HashMap map = new HashMap();
		String sql = "from Keyword where 1=1  and accountId = "+AccountId;
		List list = this.executeHQL(sql);
		Keyword obj = new Keyword();
		for (int i = 0; i < list.size(); i++) {
			
			obj = (Keyword)list.get(i);
			map.put(obj.getMaterialId().toString(), obj);
		}
		return map;
	}
	
	@Override
	public Object[] searchKeyworObject(String keyword) throws Exception
	{
		HashMap map = new HashMap();
		String sql = "select keyword,content,contenttype,materialid,matchingrules,id from keyword where 1=1 and keyword ='"+keyword+"'";
		List list = this.executeSQL(sql);
		Object[] obj =  null;
		for (int i = 0; i < list.size(); i++) {
			obj = new Object[4];
			obj = (Object[])list.get(i);
			
		}
		return obj;
	}
	
	
	
	@Override
	public List searchKeywListInfo(String keyword) throws Exception
	{
		HashMap map = new HashMap();
		String sql = "select keyword,content,contenttype,materialid,matchingrules,id from keyword where 1=1 and keyword like '%"+keyword+"%'";
		
		return this.executeSQL(sql);
	}
	
	@Override
	public HashMap searchKeywordMapByMaterialId(String accountId) throws Exception
	{
		HashMap map = new HashMap();
		String sql = "select materialid,keyword,status,matchingrules from keyword where 1=1  and accountid = "+accountId;
		List list = this.executeSQL(sql);
		Object[] obj = new Object[2];
		for (int i = 0; i < list.size(); i++) {
			
			obj = (Object[])list.get(i);
			if(obj[1]!= null)
			{
				map.put(obj[0].toString(), obj);
			}
			
		}
		return map;
	}
	
}
