package com.wechat.dao;

import com.wechat.entity.Keyword;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface KeywordDao {

	
	/**
	 * 添加关键词
	 * @param weChatKeyword
	 * @throws Exception
	 */
	void saveKeyword(Keyword keyword)throws Exception;
	
	/**
	 * 查询单个关键词
	 * @param Keyword
	 * @return
	 * @throws Exception
	 */
	Keyword getKeyword(Keyword keyword)throws Exception;
	
	/**
	 * 查询关键词列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Page searchKeyword(HashMap map)throws Exception;
	
	/**
	 * 删除关键词
	 * @param id
	 * @throws Exception
	 */
	void removeKeyword(String id)throws Exception;
	
	
	/**
	 * 查询用户关键词-精准匹配
	 * @param AccountId
	 * @return
	 * @throws Exception
	 */
	public HashMap searchKeywordMap(String AccountId) throws Exception;
	
	/**
	 * 查询用户关键词-包含关键词
	 * @param AccountId
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchKeywordList(String AccountId) throws Exception;
	
	/**
	 * 查询用户所有关键词
	 * @param AccountId
	 * @return
	 * @throws Exception
	 */
	
	
	public HashMap searchAllKeywordMap(String AccountId) throws Exception;
	
	
	/**
	 * 查询关键词
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public Object[] searchKeyworObject(String keyword) throws Exception;
	
	/**
	 * 查询图文map
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	public HashMap searchKeywordMapByMaterialId(String accountId) throws Exception;
	
	
	/**
	 * 模糊查询关键词
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List searchKeywListInfo(String keyword) throws Exception;
}
