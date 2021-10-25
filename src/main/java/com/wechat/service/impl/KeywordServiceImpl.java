package com.wechat.service.impl;

import com.wechat.dao.KeywordDao;
import com.wechat.entity.Keyword;
import com.wechat.service.KeywordService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("keywordService")
public class KeywordServiceImpl implements KeywordService {

	@Resource
	private KeywordDao keywordDao;
	
	
	public KeywordDao getKeywordDao() {
		return keywordDao;
	}


	public void setKeywordDao(KeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}


	@Override
	public Keyword getKeyword(Keyword keyword) throws Exception {
		
		return this.keywordDao.getKeyword(keyword);
	}

	
	@Override
	public void removeKeyword(String id) throws Exception {
		
		this.keywordDao.removeKeyword(id);
	}

	
	@Override
	public void saveKeyword(Keyword keyword) throws Exception {
		
		this.keywordDao.saveKeyword(keyword);
	}

	
	@Override
	public HashMap searchAllKeywordMap(String AccountId) throws Exception {
		
		return this.keywordDao.searchAllKeywordMap(AccountId);
	}

	
	@Override
	public Object[] searchKeyworObject(String keyword) throws Exception {
		
		return this.keywordDao.searchKeyworObject(keyword);
	}

	
	@Override
	public Page searchKeyword(HashMap map) throws Exception {
		
		return this.keywordDao.searchKeyword(map);
	}

	
	@Override
	public List<Object[]> searchKeywordList(String AccountId) throws Exception {
		
		return this.keywordDao.searchKeywordList(AccountId);
	}

	
	@Override
	public HashMap searchKeywordMap(String AccountId) throws Exception {
		
		return this.keywordDao.searchKeywordMap(AccountId);
	}



	@Override
	public HashMap searchKeywordMapByMaterialId(String accountId)
			throws Exception {
		
		return this.keywordDao.searchKeywordMapByMaterialId(accountId);
	}


	@Override
	public List searchKeywListInfo(String keyword) throws Exception {
		
		return this.keywordDao.searchKeywListInfo(keyword);
	}

}
