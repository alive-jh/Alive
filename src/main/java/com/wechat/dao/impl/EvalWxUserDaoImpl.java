package com.wechat.dao.impl;


import com.wechat.dao.EvalWxUserDao;
import com.wechat.entity.EvalWxUser;
import org.springframework.stereotype.Repository;
@Repository
public class EvalWxUserDaoImpl extends BaseDaoImpl<EvalWxUser> implements EvalWxUserDao{


	@Override
	public EvalWxUser getByOpenId(String openId) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void add(EvalWxUser o) {
		this.save(o);		
	}

	@Override
	public void delById(int id) {
		this.delete(id);
		
	}

	@Override
	public EvalWxUser getById(int id) {
		return (EvalWxUser)this.get(id);
	}

	@Override
	public void update(EvalWxUser o) {
		this.save(o);		
	}


}
