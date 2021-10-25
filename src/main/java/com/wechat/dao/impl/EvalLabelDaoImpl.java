package com.wechat.dao.impl;

import com.wechat.dao.EvalLabelDao;
import com.wechat.entity.EvalLabel;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EvalLabelDaoImpl extends BaseDaoImpl<EvalLabel> implements
		EvalLabelDao {

	@Override
	public Page<EvalLabel> getByGroup(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(EvalLabel o) {
		this.save(o);
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from eval_label where id = " + id);
		this.executeSQL(sql.toString());
	}

	@Override
	public EvalLabel getById(int id) {
		@SuppressWarnings("rawtypes")
		List list = this.executeHQL("from EvalLabel where id= " + id);
		if(list.size() == 0)
			return null;
		return (EvalLabel)list.get(0);
	}

	@Override
	public void updates(EvalLabel o) {
		this.update(o);
	}

	@Override
	public Page<EvalLabel> getByParam(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(" from EvalLabel where 1=1 ");

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

		if (!"".equals(paramMap.get("groups")) && paramMap.get("groups") != null)
			sql.append(" and groups = '")
					.append(paramMap.get("groups").toString()).append("'");

		if (!"".equals(paramMap.get("name")) && paramMap.get("name") != null)
			sql.append(" and name = '")
					.append(paramMap.get("name").toString()).append("'");

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

}
