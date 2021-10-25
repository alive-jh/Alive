package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.util.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BookDao {
	
	
	/**
	 * 添加书籍库存
	 * @param book
	 */
	void saveBook(Book book);
	
	
	/**
	 * 更新书籍库存状态
	 * @param id
	 * @param status
	 */
	void updateBookStatus(String id, String status);
	
	/**
	 * 查询书籍库存集合
	 * @param map
	 * @return
	 */
	List searchBookList(HashMap map);
	
	/**
	 * 查询单个书籍库存
	 * @param id
	 * @return
	 */
	Book getBook(String id);
	
	
	/**
	 * 添加书籍
	 * @param category
	 */
	void saveCategory(Category category);
	
	/**
	 * 查询书籍集合,分页
	 * @param map
	 * @return
	 */
	Page searchCategory(HashMap map);
	
	/**
	 * 查询实际集合
	 * @param map
	 * @return
	 */
	List searchCategorykList(HashMap map);

	/**
	 * 查询单个书籍
	 * @param id
	 * @return
	 */
	Category getCategory(String id);
	
	/**
	 * 查詢書籍類型-二類
	 * @return
	 */
	List getRightCategory();
	
	/**
	 * 查詢書籍類型,一類
	 * @return
	 */
	List getLeftCategory();
	
	/**
	 * 查询书籍综合信息
	 * @param map
	 * @return
	 */
	Page searchCategoryInfo(HashMap map);
	
	/**
	 * 查询书籍ID
	 * @param type
	 * @return
	 */
	Integer getCateId(String type);
	
	/**
	 * 查询书籍库存ID
	 * @param cateId
	 * @return
	 */
	public Integer getBookCateId(String cateId);
	
	/**
	 * 更新书籍信息
	 * @param cateGory
	 */
	void updateCateGory(Category cateGory);
	
	/**
	 * 删除书籍
	 * @param cateId
	 */
	void deleteCateGory(String cateId);
	
	/**
	 * 删除书籍库存
	 * @param id
	 */
	void deleteBook(String barcode);
	
	/**
	 * 查找最大库存ID
	 * @param cateId
	 * @param type
	 * @return
	 */
	String getMaxBookCateId(String cateId, String type);
	
	/**
	 * 更新库存二维码信息
	 * @param book
	 */
	void updateBookInfo(Book book);
	
	
	/**
	 * 根据特定条件查询书籍列表
	 * @param map
	 * @return
	 */
	Page searchBookBy(HashMap map);
	
	/**
	 * 查询书籍详情页
	 * @param cateId
	 * @return
	 */
	public Object[] searchCateGoryInfo(String cateId);
	
	
	/**
	 * 查询书籍标签关系表
	 * @param categoryLabel
	 */
	void saveCategoryLabel(CategoryLabel categoryLabel);
	
	/**
	 * 保存标签
	 * @param label
	 */
	void saveLabel(Label label);
	
	/**
	 * 查询书籍
	 * @param id
	 * @return
	 */
	Page searchLabel(HashMap map);
	
	/**
	 * 查询书籍标签关系
	 * @param name
	 * @return
	 */
	List searchLabelInfo(String name);
	
	/**
	 * 删除标签
	 * @param id
	 */
	void deleteLabel(String id);
	
	/**
	 * 删除标签关系
	 * @param id
	 */
	void deleteCategoryLabel(String id);
	
	/**
	 * 保存标签关系表
	 * @param categoryLabel
	 */
	void saveLabelInfo(CategoryLabel categoryLabel);
	
	/**
	 * 书籍下啦列表
	 * @return
	 */
	List searchBooks(String name);
	
	/**
	 * 查询借书车
	 * @param memberId
	 * @return
	 */
	List searchaBookVehicle(String memberId);
	
	/**
	 * 保存借书车
	 * @param bookVehicle
	 */
	void saveBookVehicle(BookVehicle bookVehicle);
	
	/**
	 * 删除借书车
	 * @param ids
	 */
	void deleteBookVehicle(String ids);
	
	/**
	 * 提交订单删除借书车
	 * @param ids
	 * @param memberId
	 */
	void deleteBookVehicleByMemberId(String ids, String memberId);
	
	
	
	 /**
	  * 保存首页展示标签
	  * @param mallLabel
	  */
	 void saveBookLabel(BookLabel bookLabel);
	 
	 /**
	  * 删除首页展示标签
	  * @param id
	  */
	 void deleteBookLabel(String id);
	 
	 /**
	  * 查询首页展示标签
	  * @param map
	  * @return
	  */
	 Page searchBookLabel(HashMap map);
	 
	 
	/**
	 * 修改首页标签设置 
	 * @param id
	 * @param ios
	 * @param wechat
	 * @param android
	 */
	void updateBookLabel(String id, String ios, String wechat, String android);
	 
	
	
	/**
	 * 根据标签查询书籍
	 * @param labeiId
	 * @return
	 */
	List searchBookByLabel(String labelId);
	
	/**
	 * 查询首页标签列表
	 * @return
	 */
	List searchBookLabel();
	
	/**
	 * 查询书籍库存
	 * @param ids
	 * @return
	 */
	List searchBookCount(String ids);
	
	
	/**
	 * 查询书籍信息
	 * @param ids
	 * @return
	 */
	List searchBooksByIds(String ids);


	/**
	 * 查询书籍加盟店
	 * @param map
	 * @return
	 */
	Page searchBookShops(HashMap map);

	
	/**
	 * 查询单个加盟店信息
	 * @param parseInt
	 * @return
	 */
	BookShop searchBookShopById(int parseInt);

	/**
	 * 保存书籍加盟店
	 * @param bookShop
	 */
	void saveBookShop(BookShop bookShop);

	/**
	 * 删除书籍加盟店
	 * @param bookShop
	 */
	void deleteBookShop(BookShop bookShop);

	/**
	 * 加盟店登录接口
	 * @param account
	 * @param password
	 * @return
	 */
	BookShop loginBookShop(String account, String password);

	/**
	 * 查询加盟店会员
	 * @param map
	 * @return
	 */
	Page searchBookShopMembers(HashMap map);
	
	
	/**
	 * 批量增加加盟店图书库存
	 * @param shopId
	 */
	void updateBookShopCategory(String shopId);
	
	/**
	 * 更新数据库存状态
	 * @param bookCode
	 */
	void updateBookCategoryStatus(String bookCode, String status);


	Page searchBookCategory(HashMap map);


	void saveBookCategory(BookCategory bookCategory);


	void deleteBookCategory(int catId);

	
	/**
	 * 查询商城和书院标签列表
	 * @param type
	 * @return
	 */
	List searchIndexLabel(String type);
	
	/**
	 * 查询书店书籍库存总数
	 * @param map
	 * @return
	 */
	Page searchBookShopCount(HashMap map);
	
	
	/**
	 * 查询书籍库存
	 * @return
	 */
	List searchBookBarCode(String cateIds);
	
	/**
	 * 批量添加书籍库存
	 */
	void addBookCode(List bookList);
	
	Page searchBooksByCatIds(HashMap map);


	List<BookCategory> getBookCategoryLevelOne();


	Page searchCategoryInfo2(HashMap map);

	/**
	 * 查询书院首页信息
	 * @return
	 */
	public List searchBooKIndexInfo();
	
	/**
	 * 查询书院分类信息
	 * @return
	 */
	public List searchBookLabelInfo();
	
	
	/**
	 * 查询书院会员信息
	 * @param memberId
	 * @return
	 */
	public Object searchMemberBookInfo(String memberId);
	
	/**
	 * 查询收藏书籍列表
	 * @param map
	 * @return
	 */
	public Page searchBookCollection(HashMap map);
	
	
	/*
	 * 删除书籍收藏记录
	 */
	public void deleteBookCollection(String id);


	Page searchCourseProjectActives(HashMap map);

	/**
	 * 删除激活二维码
	 * @param id
	 */
	void deleteCourseProjectActive(Integer id);


	CourseProjectActive findCourseProjectActive(Integer id);


	void saveCourseProjectActive(CourseProjectActive courseProjectActive);


	CourseProjectSystem findCourseProjectSystem(Integer planId);


	ArrayList findCourseProjectSystems();
	
	
	/**
	 * 查询书籍名称
	 * @return
	 * @throws Exception
	 */
	HashMap searchBookName() throws Exception;
	
	/**
	 * 批量添加书籍
	 * @param list
	 * @throws Exception
	 */
	void saveBookList(List list) throws Exception;
	
	/**
	 * 批量添加书籍库存
	 * @param list
	 * @throws Exception
	 */
	void saveBookInfoList(List list) throws Exception;


	Page searchCourseProjectSystems(HashMap map);


	void deleteCourseProjectSystem(Integer id);


	void saveCourseProjectSystem(CourseProjectSystem courseProjectSystem);
	
	/**
	 * 查询加盟书店业绩和会员数
	 * @param map
	 * @return
	 */
	Page searchBookShopSale(HashMap map);
	
	/**
	 * 查询加盟书店业绩和会员数汇总信息
	 * @param map
	 * @return
	 */
	Object[] searchBookShopSum(HashMap map);
	
	
	/**
	 * 数据查询
	 * @param map
	 * @return
	 */
	Page searchBook(HashMap map);


	Page findAnchors(HashMap map);


	void saveAnchor(Anchor anchor);


	void deleteAnchor(Integer id);
	
	
	
	
	/**
	 * 查询书店自己添加的书籍信息
	 * @param map
	 * @return
	 */
	public Page searchBookShopCategoryInfo(HashMap map);
	/**
	 * 查询书店账号
	 * @param userId
	 * @return
	 */
	public String getShopIdByUserId(String userId);
	
	/**
	 * 添加书店书籍表关系
	 * @param bookShopCategory
	 */
	public void addShopCategory(BookShopCategory bookShopCategory);
	
	/**
	 * 删除书店书籍关联关系
	 * @param cateID
	 */
	public void deleteBookShopCateId(String cateID);
	
	
	/**
	 * 查询书籍二维码列表
	 * @param cateIds
	 * @param shopId
	 * @return
	 */
	public List searchBookQRCode(String cateIds, String shopId);


	Page getBookList(HashMap map);


	Page searchBookByName(HashMap map);


	Page getBookStockList(HashMap map);
}
