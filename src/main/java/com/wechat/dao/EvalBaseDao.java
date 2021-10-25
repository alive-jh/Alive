package com.wechat.dao;

import com.wechat.util.Page;

import java.util.Map;


public interface EvalBaseDao<T> {

	void add(T t);
	void delById(int id);
	T getById(int id);
	Page<T> getByParam(Map<String, Object> paramMap);

	void updates(T t);
}
