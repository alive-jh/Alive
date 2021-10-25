package com.wechat.service;

import com.wechat.entity.EvalWxUser;

public interface EvalWxUserService {
	void add(EvalWxUser o);
	void delById(int ID);
	EvalWxUser getById(int id);
	void update(EvalWxUser o);
}
