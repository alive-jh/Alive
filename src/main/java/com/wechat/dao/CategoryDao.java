package com.wechat.dao;

import com.wechat.entity.BookKeyword;
import com.wechat.entity.Special;
import com.wechat.entity.SpecialInfo;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface CategoryDao {

	
	/**
	 * 添加专题
	 * @param bookSpecial
	 */
	void saveSpecial(Special special);
	/**
	 * 删除专题
	 * @param id
	 */
	void deleteSpecial(String id);
	/**
	 * 查询首页专题
	 * @return
	 */
	List searchSpecialIndex();
	/**
	 * 查询专题列表
	 * @param Special
	 * @return
	 */
	Page searchSpecial(HashMap map);
	
	
	/**
	 * 添加专题关系
	 * @param SpecialInfo
	 */
	void saveSpecialInfo(SpecialInfo specialInfo);
	/**
	 * 删除专题关系表
	 * @param SpecialId
	 */
	void deleteSpecialInfo(String specialId);
	/**
	 * 查询专题关系
	 * @param SpecialId
	 * @return
	 */
	List searchSpecialInfo(String specialId);
	
	
	/**
	 * 更新专题排序
	 * @param id
	 * @param sort
	 */
	 void updateSpecialSort(String id, String sort);
	
	 /**
	  * 更新专题状态
	  */
	 void updateSpecialStatus(String id, String status);
	 
	 /**
	  * 查询专题最大排序
	  * @return
	  */
	 String searchMaxSort();
	 
	 /**
	  * 查询专题书籍列表
	  * @param id
	  * @return
	  */
	 public List searchBooksBySpecial(String id);
	 
	 
	 /**
	  * 根据标签查询书籍列表
	  * @param map
	  * @return
	  */
	 public Page searchBooksByLabel(HashMap map);
	
	 /**
	  * 保存书籍关键词
	  * @param bookKeyword
	  */
	 void saveBookKeyword(BookKeyword bookKeyword);
	 
	 /**
	  * 删除书籍关键词
	  * @param cateId
	  */
	 void deleteBookKeyword(String cateId);
	 
	 /**
	  * 查询书籍关键词
	  * @param cateId
	  * @return
	  */
	 List searchBookKeyword(String cateId);
	 
	 /**
	  * 根据条形码查询书籍
	  * @param code
	  * @return
	  */
	 List searchBookByCode(String code);
	 
	 
	 /**
	  * 按标签和关键词查询书籍
	  * @param map
	  * @return
	  */
	 Page searchBooksByLabelIdAndKeyWord(HashMap map);
	
}
