package com.wechat.dao.impl;

import com.wechat.dao.AgentDao;
import com.wechat.entity.AgentBillOfSales;
import com.wechat.entity.ShipmentRecord;
import com.wechat.entity.User;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Repository
public class AgentDaoImpl extends BaseDaoImpl implements AgentDao
{
	public Page getAgentList(HashMap map) {
		//获取所有代理商的账号 账号ID》10000
		StringBuffer sql=new StringBuffer("select * from user where roleId=7");
		String searchStr = map.get("searchStr").toString();
		if(!"".equals(searchStr)&&null!=searchStr){
			sql.append(" and name like  '%" + map.get("searchStr")).append("%'");
		}
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				User user= new User();
				user.setId((Integer) obj[0]);
				user.setAccount((String) obj[1]);
				user.setPassword((String) obj[2]);
				user.setSex((Integer) obj[3]);
				user.setName((String) obj[4]);
				user.setEmail((String) obj[5]);
				user.setMobile((String) obj[6]);
				user.setStatus((Integer) obj[7]);
				user.setRoleId((Integer) obj[8]);
				users.add(user);
			}

		}

		Page resultPage = new Page<User>(users, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
		
	}

	@Override
	public Page getBillOfSalesByAgentId(HashMap map) {
		// 通过加盟商ID获取出货单列表
		String agentId = map.get("agentId").toString();
		StringBuffer sql=new StringBuffer("from AgentBillOfSales where agentId=" + agentId);
		return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public void saveShipmentRecord(ShipmentRecord shipmentRecord) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(shipmentRecord);
		
	}

	@Override
	public void saveAgentBillOfSales(AgentBillOfSales agentBillOfSales) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(agentBillOfSales);
		
	}

	@Override
	public Page getShipmentDetailList(HashMap map) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String startTime = df.format(new Date());
	    Calendar calendar=Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 168);
		String endTime = df.format(calendar.getTime());
		StringBuffer sql=new StringBuffer("select sr.device_no,sr.id,sr.create_time,sr.user_id,d.epal_id,d.nickname,ifnull((SELECT friend_id FROM device_relation WHERE epal_id = d.epal_id AND isbind = 1 ORDER BY id DESC LIMIT 1),'没有绑定') friend_id,(SELECT time FROM device_online_record WHERE epal_id = d.epal_id ORDER BY id DESC LIMIT 1) active_time,(SELECT count(epal_id) FROM device_online_record WHERE epal_id = d.epal_id and time<'"+startTime+"' and time>'"+endTime+"') countTime,ifnull((select name from class_student where class_student.epal_id=d.epal_id limit 1),'没有成为学生') studentName,ifnull((select GROUP_CONCAT(a.class_grades_name) from class_grades as a,class_grades_rela as b,class_student as c where a.id=b.class_grades_id and b.class_student_id=c.id and c.epal_id=d.epal_id),'没有加入班级') classGradesName,ifnull((select activityTime from device_activity where device_activity.epalId=d.epal_id order by id desc limit 1),'没有绑定信息') bindTime from shipment_record sr LEFT JOIN device d on sr.device_no=d.device_no where sr.bill_of_sales_id=:BillOfSalesId");
		Query query = this.getQuery(sql.toString());
		query.setInteger("BillOfSalesId", Integer.parseInt(map.get("shipmentId").toString()));
		Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		JSONArray result = new JSONArray();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject temp= new JSONObject();
				temp.put("deviceNo",obj[0]);
				temp.put("id",obj[1]);
				temp.put("createTime",obj[2]);
				temp.put("userId",obj[3]);
				temp.put("epalId",obj[4]);
				temp.put("nickName",obj[5]);
				temp.put("friendId",obj[6]);
				temp.put("activeTime",obj[7]);
				temp.put("countTime",obj[8]);
				temp.put("studentName",obj[9]);
				temp.put("classGradesName",obj[10]);
				temp.put("bindTime",obj[11]);
				
				
				result.add(temp);
			}

		}
		Page resultPage = new Page<User>(result, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	} 

	
}
