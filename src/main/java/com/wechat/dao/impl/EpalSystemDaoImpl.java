package com.wechat.dao.impl;

import com.wechat.dao.EpalSystemDao;
import com.wechat.entity.EpalSystem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EpalSystemDaoImpl extends BaseDaoImpl implements EpalSystemDao {

	@Override
	public void saveEpalSystem(EpalSystem epalSystem) {

		this.saveOrUpdate(epalSystem);
		
	}

	@Override
	public void updateEpalSystem(EpalSystem epalSystem) {
		this.saveOrUpdate(epalSystem);
		
	}

	@Override
	public EpalSystem getEpalSystem(String epalId) {

		List list = this.executeHQL(" from EpalSystem where epalId ='"+epalId+"'");
		EpalSystem epalSystem = new EpalSystem();
		if(list.size()>0)
		{
			epalSystem = (EpalSystem)list.get(0);
		}
//		//System.out.println(epalSystem.getRecommend());
		return epalSystem;
	}

}
