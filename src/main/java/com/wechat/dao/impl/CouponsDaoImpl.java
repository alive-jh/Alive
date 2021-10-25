package com.wechat.dao.impl;

import com.wechat.dao.CouponsDao;
import com.wechat.entity.Coupons;
import com.wechat.entity.CouponsInfo;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CouponsDaoImpl extends BaseDaoImpl implements CouponsDao{

	
	@Override
	public void deleteCoupons(String id) {
		
		this.executeUpdateSQL(" delete from coupons where id = "+id);
		
	}

	
	@Override
	public void saveCoupons(Coupons coupons) {
		
		this.saveOrUpdate(coupons);
	}

	
	@Override
	public void saveCouponsInfo(CouponsInfo CouponsInfo) {
		
		this.saveOrUpdate(CouponsInfo);
	}

	
	@Override
	public Page searchCoupons(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Coupons where 1=1 ");
		if(!"".equals(map.get("title")) && map.get("title") != null)
		{
			sql.append(" and title like '%").append(map.get("title").toString()).append("%'");
			
		}
		if(!"".equals(map.get("type")) && map.get("type") != null)
		{
			sql.append(" and type =").append(map.get("type").toString());
			
		}
		if("0".equals(map.get("dateType")))
		{

			if(!"".equals(map.get("startDate")) && map.get("startDate")!= null )
			{
				sql.append(" and createDate >='").append(map.get("startDate")).append("'");
				
			}
			if(!"".equals(map.get("endDate")) && map.get("endDate")!= null )
			{
				sql.append(" and createDate <='").append(map.get("endDate")).append("'");
				
			}
			
		}
		if("1".equals(map.get("dateType")))
		{

			if(!"".equals(map.get("startDate")) && map.get("startDate")!= null )
			{
				sql.append(" and endDate >='").append(map.get("startDate")).append("'");
				
			}
			if(!"".equals(map.get("endDate")) && map.get("endDate")!= null )
			{
				sql.append(" and endDate <='").append(map.get("endDate")).append("'");
				
			}
			
		}
		
		sql.append(" order by createDate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}

	
	@Override
	public List searchCouponsList(HashMap map ) {
		
		StringBuffer sql = new StringBuffer(" select b.id,a.title,a.price,a.money,a.type,a.endDate,b.`status` from coupons a ,couponsinfo b "
				+" where a.id = b.couponsid ");
		
		if(!"".equals(map.get("memberId")) && map.get("memberId")!= null)			
		{
			sql.append("  and b.memberid = ").append(map.get("memberId").toString());
		}
		if(!"".equals(map.get("status")) && map.get("status")!= null)			
		{
			sql.append(" and b.status = ").append(map.get("status").toString());
		}

		if(!"".equals(map.get("couponsId")) && map.get("couponsId")!= null)			
		{
			sql.append(" and b.id = ").append(map.get("couponsId").toString());
		}
		
		if(!"".equals(map.get("price")) && map.get("price")!= null)			
		{
			sql.append(" and a.price <= ").append(map.get("price").toString());
		}
		
		sql.append(" order by b.status asc  ");
		
		return this.executeSQL(sql.toString());
		
		
	}

	
	@Override
	public void updateCouponsInfoStatus(String id) {
		
		this.executeUpdateSQL(" update couponsinfo set status = 1 where id = "+id);
	}
	
	
	@Override
	public List getCoupons(String memberId,String couponsId )
	{
		
		return this.executeHQL(" from CouponsInfo where 1=1 and memberId = "+memberId+" and couponsId = "+couponsId);
			
	}
	
	
	@Override
	public void updateCouponsInfoEndDateStatus() {
		
		this.executeUpdateSQL(" update couponsinfo set status = 2 where couponsid in (select id from coupons where 1=1 and enddate<sysdate())");
	}


	
	@Override
	public Coupons getCoupons(String id) {
		
		return (Coupons)this.getEntity(Coupons.class, new Integer(id));
	}

}
