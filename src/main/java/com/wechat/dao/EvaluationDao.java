package com.wechat.dao;

import java.util.List;

public interface EvaluationDao {

	List<Object[]> getQuestionLabels();

	List<Object[]> getQuestionTypes();

}
