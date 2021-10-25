package com.wechat.dao;

import com.wechat.entity.Integral;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface IntegralDao {

	/**
	 * 保存积分
	 * @param integral
	 */
	void saveIntegral(Integral integral);
	
	/**
	 * 查询积分列表
	 * @param map
	 * @return
	 */
	Page searchIntegral(HashMap map);
	
	/**
	 * 查看积分总数
	 * @param memberId
	 * @return
	 */
	Integer getIntegralCount(String memberId, String memberType);
	
	/**
	 * 查询当天是否有积分
	 * @param memberId
	 * @return
	 */
	List getIntegralByMemberId(String memberId, String memberType);
	
	/**
	 * 查询积分列表
	 * @param memberId
	 * @return
	 */
	public List searchIntegralList(String memberId, String memberType);
}
