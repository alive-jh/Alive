package com.wechat.dao.impl;

import com.wechat.dao.ElectrismDao;
import com.wechat.entity.Complaint;
import com.wechat.entity.DistrictInfo;
import com.wechat.entity.Electrism;
import com.wechat.entity.ElectrismOrderPayment;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ElectrismDaoImpl extends BaseDaoImpl implements ElectrismDao {

	
	@Override
	public void deleteElectrism(String id) {
		
		Electrism electrism = new Electrism();
		electrism.setId(new Integer(id));
		this.delete(electrism);
	}

	
	@Override
	public void saveElectrism(Electrism electrism) {
		
		this.saveOrUpdate(electrism);
	}

	
	@Override
	public List searchDistrict() {
		
		String sql = " from District where 1=1";
		return this.executeHQL(sql);
	}

	
	@Override
	public Page searchElectrism(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Electrism where 1=1");
		if(!"".equals(map.get("electrismId")) && map.get("electrismId")!= null)
		{
			sql.append(" and id = ").append(map.get("electrismId").toString());
		}
		if(!"".equals(map.get("name")) && map.get("name")!= null)
		{
			sql.append(" and name like '%").append(map.get("name").toString()).append("%'");
		}
		
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and status = ").append(map.get("status").toString());
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and  createdate >= '").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and  createdate <= '").append(map.get("endDate").toString()).append("'");
		}
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	
	@Override
	public List searchDistrictByElectrism(String electrismId) {
		
		String sql = " from District where 1=1 and electrismId = "+electrismId;
		return this.executeHQL(sql);
	}
	
	@Override
	public void deleteDistricInfo(String electrismId)
	{
		this.executeUpdateSQL(" delete from districinfo where electrismid = "+electrismId);
	}
	
	
	@Override
	public 	void saveDistrictInfo(DistrictInfo districtInfo)
	{
		this.save(districtInfo);
	}
	
	@Override
	public List getMemberByAccount(String account)
	{
		return this.executeHQL(" from Member where mobile ='"+account+"'");
	}
	@Override
	public void updateElectrismStatus(String electrismId,String status)
	{
		this.executeUpdateSQL(" update  electrism set status = "+status+" where id = "+electrismId);
	}


	
	@Override
	public Electrism getElectrism(String id) {
	
		return (Electrism)this.getEntity(Electrism.class, new Integer(id));
	}


	
	@Override
	public Complaint getComplaint(Integer id) {
		

		
		return (Complaint)this.getEntity(Complaint.class, id);
	}


	
	@Override
	public void saveComplaint(Complaint complaint) {
		
		this.save(complaint);
	}


	
	@Override
	public Page searchComplaint(HashMap map) {
		
		
		StringBuffer sql = new StringBuffer(" from Complaint where 1=1");
		if(!"".equals(map.get("complaintId")) && map.get("complaintId")!= null)
		{
			sql.append(" and id = ").append(map.get("complaintId").toString());
		}
		
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and status = ").append(map.get("status").toString());
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and  createdate >= '").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and  createdate <= '").append(map.get("endDate").toString()).append("'");
		}
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	
	}


	
	@Override
	public List seartchComplaint(String orderId) {
		
		return this.executeHQL("from Complaint where 1=1 and orderId = "+orderId);
	}


	
	@Override
	public void saveElectrismOrderPayment(
			ElectrismOrderPayment electrismOrderPayment) {
		
		 this.save(electrismOrderPayment);
	}


	
	@Override
	public Page searchElectrismOrderPayment(HashMap map) {
		
		
		StringBuffer sql = new StringBuffer(" from ElectrismOrderPayment where 1=1 and status = 0 ");
		
		if(!"".equals(map.get("complaintId")) && map.get("complaintId")!= null)
		{
			sql.append(" and id = ").append(map.get("complaintId").toString());
		}
		
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and status = ").append(map.get("status").toString());
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and  createdate >= '").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and  createdate <= '").append(map.get("endDate").toString()).append("'");
		}
		sql.append(" order by createDate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	
	}


	@Override
	public String getElectrismOrderSumPayment(String electrismId)
	{
		List list = this.executeSQL("select sum(payment) from electrismorderpayment where electrismid="+electrismId);
		String sum = "0";
		if(list.size()>0)
		{
			if(list.get(0)!= null)
			{
				sum = list.get(0).toString();
			}
			
		}
		return sum;
	}
	
	@Override
	public void updateElectrismOrderPayment(String electrismId) {
		
		this.executeUpdateSQL("update electrismorderpayment set status = 1 where electrismid = "+electrismId);
	}


	
	@Override
	public void updatePaymentStatus(String id,String status) {
		
		this.executeUpdateSQL("update electrism set paymentStatus = "+status +" where id = "+id);
	}


	
	@Override
	public Electrism getElectrismByMemberId(String memberId) {
		
		Electrism electrism = new Electrism();
		
		List list = this.executeHQL(" from Electrism where memberId = "+memberId);
		if(list.size()>0)
		{
			if(list.get(0)!= null)
			{
				electrism = (Electrism)list.get(0);
			}
			
		}
		
		return electrism;
	}

}
