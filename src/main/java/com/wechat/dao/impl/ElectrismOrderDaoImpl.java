package com.wechat.dao.impl;

import com.wechat.dao.ElectrismOrderDao;
import com.wechat.entity.ElectrismOrder;
import com.wechat.entity.ElectrismOrderTime;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ElectrismOrderDaoImpl extends BaseDaoImpl implements ElectrismOrderDao{

	
	@Override
	public void saveElectrismOrder(ElectrismOrder electrismOrder) {
		
		this.saveEntity(electrismOrder);
	}

	
	@Override
	public void saveElectrismOrderTime(ElectrismOrderTime electrismOrderTime) {
		
		this.saveEntity(electrismOrderTime);
	}

	
	
	@Override
	public Page searchElectrismOrder(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from  ElectrismOrder where 1=1 ");
		if(!"".equals(map.get("memberId")) && map.get("memberId") != null)
		{
			sql.append(" and memberId = ").append(map.get("memberId"));
		}
		
		if(!"".equals(map.get("electrismId")) && map.get("electrismId") != null)
		{
			sql.append(" and electrismId = ").append(map.get("electrismId"));
		}
		
		sql.append(" order by createDate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}

	
	@Override
	public String searchsElectrismOrderTime(String electrismId) {
		
		String sql = " from ElectrismOrderTime where 1=1 and status = 0 and electrismId = "+electrismId+" order by id desc ";
		
		List<ElectrismOrderTime> list = this.executeHQL(sql);
		StringBuffer tempStr = new StringBuffer("");
		
		
		for (int i = 0; i < list.size(); i++) {
			
			tempStr.append("'"+list.get(i).getCreateDate()).append(" ").append(list.get(i).getTime()).append("'").append(",");
			
			
		}
		return tempStr.toString();
	}

	
	@Override
	public void updateElectrismOrderStatis(String orderId,String status) {
		
		this.executeUpdateSQL(" update electrismorder set status =  "+status+" where id = "+orderId);
	}

	
	@Override
	public void updateElectrismOrderTimeStatis(String orderId) {
		
		this.executeUpdateSQL(" update electrismordertime set status =1  where orderid = "+orderId);
	}



	@Override
	public Page searchElectrismOrderInfo(HashMap map) {
		
		
		StringBuffer sql = new StringBuffer(" select a.id,a.electrismname,b.nickname,a.contacts,a.mobile,a.orderdate,a.`status`,a.serviceitem  ,a.payment,a.ordernumber "
										    +" from electrismorder a,member b  where a.memberid = b.id ");
		if(!"".equals(map.get("memberId")) && map.get("memberId") != null)
		{
			sql.append(" and memberId = ").append(map.get("memberId"));
		}
		
		if(!"".equals(map.get("orderNumber")) && map.get("orderNumber") != null)
		{
			sql.append(" and a.ordernumber = ").append(map.get("orderNumber"));
		}
		if(!"".equals(map.get("status")) && map.get("status") != null)
		{
			sql.append(" and a.status = ").append(map.get("status"));
		}
		if(!"".equals(map.get("mobile")) && map.get("mobile") != null)
		{
			sql.append(" and a.mobile ='").append(map.get("mobile")).append("'");
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate") != null)
		{
			sql.append(" and a.orderdate >= '").append(map.get("startDate")).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate") != null)
		{
			sql.append(" and a.orderdate <= '").append(map.get("endDate")).append("'");
		}
	
		
		if(!"".equals(map.get("electrismId")) && map.get("electrismId") != null)
		{
			sql.append(" and a.electrismid =").append(map.get("electrismId"));
		}
		sql.append(" order by a.id desc  ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	@Override
	public String getMemberIdByMobile(String mobile) {
		
		String sql = "select id from member where mobile = '"+mobile+"'";
		List list = this.executeSQL(sql);
		String id = "";
		if(list.size()>0)
		{
			id = list.get(0).toString();
			this.executeUpdateSQL(" update member set type =1 where id = "+id);
		}
		return id;
	}
	
	@Override
	public HashMap getElectrismOrderCount()
	{
		
		List<Object[]> list = this.executeSQL("select electrismid,count(id) from electrismorder where status !=2 group by electrismid");
		HashMap map = new HashMap();
		
		for (int i = 0; i < list.size(); i++) {
			
			map.put(list.get(i)[0].toString(), list.get(i)[1].toString());
		}
		return map;
	}
	
	@Override
	public void addOrderPayment(String orderId,String payment,String remarks)
	{
		this.executeUpdateSQL(" update electrismorder set addpayment = "+payment+" ,remarks='"+remarks+"' , status = 3 where id = "+orderId);
	}
	
	@Override
	public void updateOrderPayment(String orderId)
	{
		this.executeHQL(" update electrismorder set status = 4 where id = "+orderId);
	}
	
	@Override
	public ElectrismOrder getElectrismOrder(String id)
	{
		ElectrismOrder electrismOrder = new ElectrismOrder();
		electrismOrder = (ElectrismOrder)this.getEntity(ElectrismOrder.class, new Integer(id));
		return electrismOrder;
	}
	
	
	@Override
	public List getElectrismNameMap()
	{
		
		List list = this.executeSQL(" select id,nickname from electrism where 1=1");
		
		return list;
	}
	
	@Override
	public Page searchOrderPaymentInfo(HashMap map)
	{
		
		StringBuffer sql = new StringBuffer("select b.nickname,b.`name`,b.card,b.bank,a.* ,b.mobile from ( select electrismid,count(id),sum(payment),group_concat(ordernumber,'>',serviceitem,'>',payment,'>',createdate) from electrismorderpayment where status = 0 ");
		

		if(!"".equals(map.get("electrismId")) && map.get("electrismId")!= null)
		{
			sql.append(" and electrismid =").append(map.get("electrismId"));
			
		}
		
		
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and createdate >='").append(map.get("startDate")).append("'");
			
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and createdate <='").append(map.get("endDate")).append("'");
			
		}
		
		sql.append(" group by  electrismid order by sum(payment) desc ) a,electrism b where a.electrismid= b.id and b.paymentstatus=1");
		
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}
}
