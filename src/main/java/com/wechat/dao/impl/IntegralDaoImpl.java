package com.wechat.dao.impl;

import com.wechat.dao.IntegralDao;
import com.wechat.entity.Integral;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class IntegralDaoImpl extends BaseDaoImpl implements IntegralDao{

	@Override
	public Integer getIntegralCount(String memberId,String memberType) {
		
		String sql = "select sum(fraction) from integral where memberid ="+memberId+" and membertype="+memberType;
		List list = this.executeSQL(sql);
		Integer sumIntegral = new Integer(0);
		if(list.get(0) != null)
		{
			sumIntegral = new Integer(list.get(0).toString());
		}
		return sumIntegral;
	}

	@Override
	public void saveIntegral(Integral integral) {
		
		this.save(integral);
	}

	@Override
	public Page searchIntegral(HashMap map) {
		
		StringBuffer sql  = new StringBuffer("select a.id,a.memberid,a.status,a.fraction,a.createdate,b.name from integral a,integraltype b where a.typeid = b.id ");
		if(!"".equals(map.get("memberId")) && map.get("memberId")!= null)
		{
			sql.append(" and a.memberid = ").append(map.get("memberId"));
		}
		sql.append(" and a.membertype=").append(map.get("memberType"));
		
		if(!"".equals(map.get("typeId")) && map.get("typeId")!= null)
		{
			sql.append(" and a.typeid = ").append(map.get("typeId"));
		}
		sql.append("  order by a.createdate desc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	@Override
	public List getIntegralByMemberId(String memberId,String memberType)
	{
		String sql = "select * from integral where date_format(createdate,'%Y-%m-%d') = date_format(sysdate(),'%Y-%m-%d') and memberid ="+memberId+" and membertype="+memberType;
		
		return this.executeSQL(sql);
	}
	
	@Override
	public List searchIntegralList(String memberId,String memberType)
	{
		StringBuffer sql  = new StringBuffer("select a.id,a.memberid,a.status,a.fraction,a.createdate,b.name,a.remark " +
				"from integral a,integraltype b where a.typeid = b.id and a.memberid="+memberId +" and a.membertype = "+memberType+" order by a.createdate desc");
		return this.executeSQL(sql.toString());
	}

	
}
