package com.wechat.dao.impl;


import com.wechat.dao.EvalOptionDao;
import com.wechat.dao.EvalQuestionOptionDao;
import com.wechat.entity.EvalOption;
import com.wechat.entity.EvalQuestionOption;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
@Repository
public class EvalOptionDaoImpl extends BaseDaoImpl<EvalOption> implements EvalOptionDao{

	@Resource
	EvalQuestionOptionDao qoService;

	@Override
	public void add(EvalOption o) {
		this.save(o);		
	}

	@Override
	public void delById(int id) {
		this.delete(id);
		
	}

	@Override
	public EvalOption getById(int id) {
		return (EvalOption)this.get(id);
	}

	@Override
	public void update(EvalOption o) {
		this.save(o);		
	}

	@Override
	public Page<EvalOption> getByQuestionId(int questionId) {
		//通过questionId获得OptionId
		StringBuffer sql = new StringBuffer(
				"from EvalQuestionOption where question_id = '" + questionId + "'");
		List<EvalQuestionOption> qos = qoService.pageQuery(sql.toString(),
				1, -1).getItems();
		//返回Page<EvalOption>
		StringBuffer sql2 = new StringBuffer(
				"from EvalOption where id in (");
		int flag = 0;
		
		for(EvalQuestionOption o : qos){
			if(flag != 0)
				sql2.append(",");
			sql2.append(" '" + o.getOptionId() + "'");
			flag++;
		}
		sql2.append(")");
		Page<EvalOption> os = this.pageQueryByHql(sql2.toString(),
				1, -1);
		for(EvalOption o : os.getItems()){
			for(EvalQuestionOption qo : qos){
				if(qo.getOptionId().equals(o.getId()) )
					o.setIsCorrect(qo.getIsCorrect());
				}
			}
		return os;
		
	}


}
