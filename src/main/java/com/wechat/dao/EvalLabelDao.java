package com.wechat.dao;

import com.wechat.entity.EvalLabel;
import com.wechat.util.Page;

import java.util.HashMap;

public interface EvalLabelDao extends EvalBaseDao<EvalLabel>{

	
	Page<EvalLabel> getByGroup(HashMap<String, String> map);

}
