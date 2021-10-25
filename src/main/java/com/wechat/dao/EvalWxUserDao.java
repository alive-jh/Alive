package com.wechat.dao;


import com.wechat.entity.EvalWxUser;

public interface EvalWxUserDao {

	
	EvalWxUser getByOpenId(String openId);
	void add(EvalWxUser o);
	void delById(int ID);
	EvalWxUser getById(int id);
	void update(EvalWxUser o);
}
