package com.wechat.dao.impl;

import com.wechat.dao.EvalLabelRelationDao;
import com.wechat.entity.EvalLabelRelation;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class EvalLabelRelationDaoImpl extends BaseDaoImpl<EvalLabelRelation> implements EvalLabelRelationDao{
	

	@Override
	public void add(EvalLabelRelation o) {
		this.save(o);		
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from eval_label_relation where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public EvalLabelRelation getById(int id) {
		return (EvalLabelRelation) this.executeHQL("from eval_label_relation where id= " + id)
				.get(0);
	}

	@Override
	public void update(EvalLabelRelation o) {
		this.save(o);		
	}

	@Override
	public Page<EvalLabelRelation> getByParmMap(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(" from EvalLabelRelation where 1=1 ");

		if (!"".equals(map.get("id")) && map.get("id") != null)
			sql.append(" and id = '")
					.append(map.get("id").toString()).append("'");
		
		if (!"".equals(map.get("status")) && map.get("status") != null)
			sql.append(" and status = '")
					.append(map.get("status").toString()).append("'");

		if (!"".equals(map.get("sort")) && map.get("sort") != null)
			sql.append(" and sort = '")
					.append(map.get("sort").toString()).append("'");

		if (!"".equals(map.get("labelId")) && map.get("labelId") != null)
			sql.append(" and label_id = '")
					.append(map.get("labelId").toString()).append("'");

		if (!"".equals(map.get("relationId")) && map.get("relationId") != null)
			sql.append(" and relation_id = '")
					.append(map.get("relationId").toString()).append("'");

		//如果没传入页码page或者当前页条数rowsPerPage，则默认不分页
		if ("".equals(map.get("page")) || map.get("page") == null)
			map.put("page", "1");
		if ("".equals(map.get("rowsPerPage")) || map.get("rowsPerPage") == null)
			map.put("rowsPerPage", "0");;
		
		
		sql.append(" order by edit_time desc ");
		return this.pageQueryByHql(sql.toString(),
				new Integer(map.get("page").toString()), new Integer(
						map.get("rowsPerPage").toString()));

	}


}
