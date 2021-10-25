package com.wechat.dao.impl;

import com.wechat.dao.ClassQRInfoDao;
import com.wechat.entity.ClassQRInfo;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ClassQRInfoDaoImpl extends BaseDaoImpl<ClassQRInfo> implements
	ClassQRInfoDao {



	@Override
	public void add(ClassQRInfo o) {
		this.save(o);
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from ClassQRInfo where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public ClassQRInfo getById(int id) {
		@SuppressWarnings("rawtypes")
		List list = this.executeHQL("from ClassQRInfo where id= " + id);
		if(list.size() == 0)
			return null;
		return (ClassQRInfo)list.get(0);
	}

	@Override
	public void updates(ClassQRInfo o) {
		this.update(o);
	}

	@Override
	public Page<ClassQRInfo> getByParam(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(" from ClassQRInfo where 1=1 ");

		if (!"".equals(paramMap.get("id")) && paramMap.get("id") != null)
			sql.append(" and id = '")
					.append(paramMap.get("id").toString()).append("'");
		
		if (!"".equals(paramMap.get("status")) && paramMap.get("status") != null)
			sql.append(" and status = '")
					.append(paramMap.get("status").toString()).append("'");

		if (!"".equals(paramMap.get("sort")) && paramMap.get("sort") != null)
			sql.append(" and sort = '")
					.append(paramMap.get("sort").toString()).append("'");

		if (!"".equals(paramMap.get("type")) && paramMap.get("type") != null)
			sql.append(" and type = '")
					.append(paramMap.get("type").toString()).append("'");

		if (!"".equals(paramMap.get("partnerId")) && paramMap.get("partnerId") != null)
			sql.append(" and partner_id = '")
					.append(paramMap.get("partnerId").toString()).append("'");

		if (!"".equals(paramMap.get("classId")) && paramMap.get("classId") != null)
			sql.append(" and class_id = '")
					.append(paramMap.get("classId").toString()).append("'");

		if (!"".equals(paramMap.get("studentId")) && paramMap.get("studentId") != null)
			sql.append(" and student_id = '")
			.append(paramMap.get("studentId").toString()).append("'");
		
		if (!"".equals(paramMap.get("text")) && paramMap.get("text") != null)
			sql.append(" and text = '")
			.append(paramMap.get("text").toString()).append("'");

		//如果没传入页码page或者当前页条数rowsPerPage，则默认不分页
		if ("".equals(paramMap.get("page")) || paramMap.get("page") == null)
			paramMap.put("page", "1");
		if ("".equals(paramMap.get("rowsPerPage")) || paramMap.get("rowsPerPage") == null)
			paramMap.put("rowsPerPage", "0");;
		
		
		sql.append(" order by edit_time desc ");
		return this.pageQueryByHql(sql.toString(),
				new Integer(paramMap.get("page").toString()), new Integer(
						paramMap.get("rowsPerPage").toString()));

	}

	@Override
	public ClassQRInfo getByCode(String text) {
		@SuppressWarnings("rawtypes")
		List list = this.executeHQL("from ClassQRInfo where text = '" + text+"' AND studentId IS NULL");
		if(list.size() == 0)
			return null;
		return (ClassQRInfo)list.get(0);
	}

}
