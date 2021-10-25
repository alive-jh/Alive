package com.wechat.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wechat.dao.EvalQuestionDao;
import com.wechat.entity.EvalLabelRelation;
import com.wechat.entity.EvalQuestion;
import com.wechat.util.Page;
@Repository
public class EvalQuestionDaoImpl extends BaseDaoImpl<EvalQuestion> implements EvalQuestionDao{

	@Override
	public Page<EvalQuestion> getByLabelorGroup(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void add(EvalQuestion o) {
		this.save(o);		
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from eval_question where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public EvalQuestion getById(int id) {
		return (EvalQuestion) this.executeHQL("from eval_question where id= " + id)
				.get(0);
	}
	@Override
	public void updates(EvalQuestion o) {
		this.update(o);		
	}


	@Override
	public Page<EvalQuestion> getByParmMap(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(" from EvalQuestion where 1=1 ");

		if (!"".equals(map.get("id")) && map.get("id") != null)
			sql.append(" and id = '")
					.append(map.get("id").toString()).append("'");
		
		if (!"".equals(map.get("status")) && map.get("status") != null)
			sql.append(" and status = '")
					.append(map.get("status").toString()).append("'");

		if (!"".equals(map.get("sort")) && map.get("sort") != null)
			sql.append(" and sort = '")
					.append(map.get("sort").toString()).append("'");

		if (!"".equals(map.get("type")) && map.get("type") != null)
			sql.append(" and type = '")
					.append(map.get("type").toString()).append("'");

		if (!"".equals(map.get("text")) && map.get("text") != null)
			sql.append(" and text = '")
					.append(map.get("text").toString()).append("'");

		if (!"".equals(map.get("picUrl")) && map.get("picUrl") != null)
			sql.append(" and pic_url = '")
					.append(map.get("picUrl").toString()).append("'");

		if (!"".equals(map.get("soundUrl")) && map.get("soundUrl") != null)
			sql.append(" and sound_url = '")
					.append(map.get("soundUrl").toString()).append("'");

		if (!"".equals(map.get("explains")) && map.get("explains") != null)
			sql.append(" and explains = '")
					.append(map.get("explains").toString()).append("'");

		if (!"".equals(map.get("explainSoundUrl")) && map.get("explainSoundUrl") != null)
			sql.append(" and explain_sound_url = '")
					.append(map.get("explainSoundUrl").toString()).append("'");

		//如果没传入页码page或者当前页条数rowsPerPage，则默认不分页
		if (!"".equals(map.get("page")) && map.get("page") != null)
			map.put("page", "1");
		if (!"".equals(map.get("rowsPerPage")) && map.get("rowsPerPage") != null)
			map.put("rowsPerPage", "0");;
		
		
		sql.append(" order by edit_time desc ");
		return this.pageQueryByHql(sql.toString(),
				new Integer(map.get("page").toString()), new Integer(
						map.get("rowsPerPage").toString()));

	}


	@Override
	public Page<EvalQuestion> getbyIds(List<EvalLabelRelation> items) {
		if(items.size() == 0)
			return null;
		StringBuffer sql = new StringBuffer(
				"from EvalQuestion where id in (");
		int flag = 0;
		
		for(EvalLabelRelation lr : items){
			if(flag != 0)
				sql.append(",");
			sql.append(" '" + lr.getRelationId() + "'");
			flag++;
		}
		sql.append(")");
		return this.pageQueryByHql(sql.toString(),
				1, -1);
	}


	@Override
	public int saveQuestion(Integer level, String questionText, String file) {
		// TODO Auto-generated method stub
		com.wechat.jfinal.model.EvalQuestion evalQuestion = new com.wechat.jfinal.model.EvalQuestion();
		evalQuestion.setText(questionText);
		evalQuestion.setSoundUrl(file);
		evalQuestion.save();
		this.createSQLQuery("insert into eval_label_relation (label_id,relation_id) values (43,?)").setInteger(0, evalQuestion.getId()).executeUpdate();
		return 1;
	}
	


}
