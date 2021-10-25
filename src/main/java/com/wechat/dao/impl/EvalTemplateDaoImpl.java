package com.wechat.dao.impl;

import com.wechat.dao.EvalTemplateDao;
import com.wechat.entity.EvalTemplate;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class EvalTemplateDaoImpl extends BaseDaoImpl<EvalTemplate> implements EvalTemplateDao {

	@Override
	public void add(EvalTemplate o) {
		this.saveOrUpdate(o);
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from EvalTemplate where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public EvalTemplate getById(int id) {
		return (EvalTemplate) this.executeHQL("from EvalTemplate where id= " + id)
				.get(0);
	}

	@Override
	public void updates(EvalTemplate o) {
		if("".equals(o.getId()) || null == o.getId())
			return;
		boolean hasChange = false;
		boolean isFirst = true;
		String sql = " UPDATE eval_template SET ";
		
		if (!"".equals(o.getStatus()) && null != o.getStatus()){
			sql = sql +" status = '"+o.getStatus()+"'";
			hasChange = true;
			isFirst = false;
		}
			
		if (!"".equals(o.getSort()) && null != o.getSort()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" sort = '"+o.getSort()+"'";
			hasChange = true;
			isFirst = false;
		}			
		
		if (!"".equals(o.getType()) && null != o.getType()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" type = '"+o.getType()+"'";
			hasChange = true;
			isFirst = false;
		}			
		if (!"".equals(o.getTeacherId()) && null != o.getTeacherId()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" teacher_id = '"+o.getTeacherId()+"'";
			hasChange = true;
			isFirst = false;
		}			

		if (!"".equals(o.getGradeId()) && null != o.getGradeId()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" grade_id = '"+o.getGradeId()+"'";
			hasChange = true;
			isFirst = false;
		}			
		if (!"".equals(o.getSound()) && null != o.getSound()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" sound = '"+o.getSound()+"'";
			hasChange = true;
			isFirst = false;
		}			
		if (!"".equals(o.getText()) && null != o.getText()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" text = '"+o.getText()+"'";
			hasChange = true;
			isFirst = false;
		}			
		if (!"".equals(o.getName()) && null != o.getName()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" name = '"+o.getName()+"'";
			hasChange = true;
			isFirst = false;
		}
		if (!"".equals(o.getStartScore()) && null != o.getStartScore()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" start_score  = '"+o.getStartScore()+"'";
			hasChange = true;
			isFirst = false;
		}
		if (!"".equals(o.getEndScore()) && null != o.getEndScore()){
			if(!isFirst)
				sql = sql + " , ";
			sql = sql +" end_score  = '"+o.getEndScore()+"'";
			hasChange = true;
			isFirst = false;
		}
		if(hasChange){
			sql = sql + " WHERE id = '" + o.getId() +"'";
			this.executeUpdateSQL(sql);
		}
		
	}

	@Override
	public Page<EvalTemplate> getByParam(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(" from EvalTemplate where 1=1 ");

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

		if (!"".equals(paramMap.get("teacherId")) && paramMap.get("teacherId") != null)
			sql.append(" and teacher_id = '")
					.append(paramMap.get("teacherId").toString()).append("'");

		if (!"".equals(paramMap.get("gradeId")) && paramMap.get("gradeId") != null)
			sql.append(" and grade_id = '")
					.append(paramMap.get("gradeId").toString()).append("'");
		if (!"".equals(paramMap.get("sound")) && paramMap.get("sound") != null)
			sql.append(" and sound = '")
			.append(paramMap.get("sound").toString()).append("'");
		if (!"".equals(paramMap.get("text")) && paramMap.get("text") != null)
			sql.append(" and text = '")
			.append(paramMap.get("text").toString()).append("'");
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
