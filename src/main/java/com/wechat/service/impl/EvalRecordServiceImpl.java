package com.wechat.service.impl;

import com.wechat.dao.EvalRecordDao;
import com.wechat.entity.EvalRecord;
import com.wechat.service.EvalRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
@Service
public class EvalRecordServiceImpl implements EvalRecordService {

	@Resource
	private EvalRecordDao selDao;
	@Override
	public void add(EvalRecord o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalRecord getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalRecord o) {
		selDao.update(o);
		
	}

	@Override
	public int scoring(Map<String, Integer> map) {
		int result = 0;
		for(String qId : map.keySet()){
			if(map.get(qId) == 1)
				result ++;
		}
		return result;
	}
}
