package com.wechat.dao;

import com.wechat.entity.ClassQRInfo;

public interface ClassQRInfoDao extends EvalBaseDao<ClassQRInfo>{

	ClassQRInfo getByCode(String code);
	
}
