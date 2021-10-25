package com.wechat.service.impl;

import com.wechat.dao.EvalTemplateDao;
import com.wechat.entity.EvalTemplate;
import com.wechat.service.EvalTemplateService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvalTemplateServiceImpl implements EvalTemplateService{

	
	@Resource
	private EvalTemplateDao selDao;
	
	@Override
	public void add(EvalTemplate o) {
		selDao.add(o);
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalTemplate getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalTemplate o) {
		selDao.updates(o);
		
	}

	@Override
	public Page<EvalTemplate> getByParam(Map<String, Object> map) {
		return selDao.getByParam(map);
	}

	/**
	 * 根据分数，随机返回一个合适的模板
	 */
	@Override
	public EvalTemplate getTemplateByScore(List<EvalTemplate> templates, int score) {
		List<EvalTemplate> list = new ArrayList<>();
		for(EvalTemplate t : templates){
			if(t.getStartScore() <= score && t.getEndScore() >= score){
				list.add(t);
			}
		}
		int size = list.size(); 
		if( size == 0)
			return null;
		int index = (int)(Math.random()*size);
		return list.get(index);
	}

	@Override
	public Page<EvalTemplate> getByTIdAndGId(int teacherId, int classGradeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("teacherId", teacherId);
		map.put("gradeId", classGradeId);
		
		 Page<EvalTemplate> page = selDao.getByParam(map);
		 List<EvalTemplate> evalTemplates =  page.getItems();
		 
		 for(EvalTemplate evalTemplate : evalTemplates){
			 if(evalTemplate.getName()==null){
				 evalTemplate.setName("");
			 }
			 if(evalTemplate.getText()==null){
				 evalTemplate.setText("");
			 }
		 }
		 
		return page;
	}
	

}
