package com.wechat.service.impl;

import com.wechat.dao.BookDao;
import com.wechat.entity.*;
import com.wechat.service.BookService;
import com.wechat.util.CategoryUtil;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("bookServiceImpl")
public class BookServiceImpl  implements BookService{

	@Resource
	private BookDao bookDao;
	
	
	public BookDao getBookDao() {
		return bookDao;
	}


	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}


	@Override
	public Book getBook(String id) { 
		
		return this.bookDao.getBook(id);
	}

	
	@Override
	public Category getCategory(String id) {
		
		return this.bookDao.getCategory(id);
	}

	
	@Override
	public void saveBook(Book book) {
		
		this.bookDao.saveBook(book);
	}

	
	@Override
	public void saveCategory(Category category) {
		
		this.bookDao.saveCategory(category);
	}

	
	@Override
	public List searchBookList(HashMap map) {
		
		return this.bookDao.searchBookList(map);
	}

	
	@Override
	public Page searchCategory(HashMap map) {
		
		return this.bookDao.searchCategory(map);
	}

	
	@Override
	public List searchCategorykList(HashMap map) {
		
		return this.bookDao.searchCategorykList(map);
	}

	
	@Override
	public void updateBookStatus(String id, String status) {
		
		this.bookDao.updateBookStatus(id, status);
	}


	
	@Override
	public List getLeftCategory() {
		
		return this.bookDao.getLeftCategory();
	}


	
	@Override
	public List getRightCategory() {
		
		return this.bookDao.getRightCategory();
	}


	
	@Override
	public Page searchCategoryInfo(HashMap map) {
		
		return this.bookDao.searchCategoryInfo(map);
	}
	
	@Override
	public Page searchCategoryInfo2(HashMap map) {
		
		return this.bookDao.searchCategoryInfo2(map);
	}




	@Override
	public Integer getCateId(String type) {
		
		
		return this.bookDao.getCateId(type);
	}


	
	@Override
	public Integer getBookCateId(String cateId) {
		
		
		return this.bookDao.getBookCateId(cateId);
	}


	
	@Override
	public void deleteBook(String barcode) {
		
		this.bookDao.deleteBook(barcode);
	}


	
	@Override
	public void deleteCateGory(String cateId) {
		
		this.bookDao.deleteCateGory(cateId);
	}


	
	@Override
	public void updateCateGory(Category cateGory) {
		
		this.bookDao.updateCateGory(cateGory);
	}

	
	@Override
	public String getMaxBookCateId(String cateId,String type)
	{
		return this.bookDao.getMaxBookCateId(cateId, type);
	}


	
	@Override
	public void updateBookInfo(Book book) {
		
		this.bookDao.updateBookInfo(book);
		
	}


	
	@Override
	public Page searchBookBy(HashMap map) {
		
		return this.bookDao.searchBookBy(map);
	}


	
	@Override
	public Object[] searchCateGoryInfo(String cateId) {
		
		return this.bookDao.searchCateGoryInfo(cateId);
	}


	
	@Override
	public void deleteCategoryLabel(String id) {
	
		this.bookDao.deleteCategoryLabel(id);
	}


	
	@Override
	public void deleteLabel(String id) {
	
		this.bookDao.deleteLabel(id);
	}


	
	@Override
	public void saveLabel(Label label) {
	
		this.bookDao.saveLabel(label);
	}


	
	@Override
	public Page searchLabel(HashMap map) {
	
		return this.bookDao.searchLabel(map);
	}


	
	@Override
	public List searchLabelInfo(String name) {
	
		return this.bookDao.searchLabelInfo(name);
	}


	
	@Override
	public void saveLabelInfo(CategoryLabel categoryLabel) {
		
		this.bookDao.saveCategoryLabel(categoryLabel);
		
	}


	
	@Override
	public List searchBooks(String name) {
		
		
		return this.bookDao.searchBooks(name);
	}


	
	@Override
	public void deleteBookVehicle(String ids) {
		
		this.bookDao.deleteBookVehicle(ids);
	}


	
	@Override
	public void deleteBookVehicleByMemberId(String ids, String memberId) {
		
		this.bookDao.deleteBookVehicleByMemberId(ids, memberId);
	}


	
	@Override
	public void saveBookVehicle(BookVehicle bookVehicle) {
		
		this.bookDao.saveBookVehicle(bookVehicle);
	}


	
	@Override
	public List searchaBookVehicle(String memberId) {
		
		return this.bookDao.searchaBookVehicle(memberId);
	}


	@Override
	public void saveBookLabel(BookLabel bookLabel) {
		
		this.bookDao.saveBookLabel(bookLabel);
	}


	@Override
	public void deleteBookLabel(String id) {
		
		this.bookDao.deleteBookLabel(id);
	}


	@Override
	public Page searchBookLabel(HashMap map) {
		
		return this.bookDao.searchBookLabel(map);
	}


	@Override
	public void updateBookLabel(String id, String ios, String wechat,
			String android) {

		this.bookDao.updateBookLabel(id, ios, wechat, android);
		
	}


	@Override
	public List searchBookByLabel(String labelId) {
		
		return this.bookDao.searchBookByLabel(labelId);
	}


	@Override
	public List searchBookLabel() {
		
		return this.bookDao.searchBookLabel();
	}


	@Override
	public List searchBookCount(String ids) {
		
		return this.bookDao.searchBookCount(ids);
	}


	@Override
	public List searchBooksByIds(String ids) {
		
		return this.bookDao.searchBooksByIds(ids);
	}


	@Override
	public Page searchBookShops(HashMap map) {
		return this.bookDao.searchBookShops(map);
	}
	
	@Override
	public Page searchCourseProjectActives(HashMap map) {
		return this.bookDao.searchCourseProjectActives(map);
	}
	
	@Override
	public void deleteCourseProjectActive(Integer id) {
		this.bookDao.deleteCourseProjectActive(id);
	}

	@Override
	public CourseProjectActive findCourseProjectActive(Integer id) {
		return this.bookDao.findCourseProjectActive(id);
	}

	@Override
	public CourseProjectSystem findCourseProjectSystem(Integer planId) {
		return this.bookDao.findCourseProjectSystem(planId);
	}

	@Override
	public ArrayList findCourseProjectSystems() {
		return this.bookDao.findCourseProjectSystems();
	}


	@Override
	public BookShop searchBookShopById(int parseInt) {
		return this.bookDao.searchBookShopById(parseInt);
	}
	

	@Override
	public void saveCourseProjectActive(CourseProjectActive courseProjectActive) {
		this.bookDao.saveCourseProjectActive(courseProjectActive);
	}


	@Override
	public void saveBookShop(BookShop bookShop) {
		this.bookDao.saveBookShop(bookShop);
	}


	@Override
	public void deleteBookShop(BookShop bookShop) {
		this.bookDao.deleteBookShop(bookShop);
	}


	@Override
	public BookShop loginBookShop(String account, String password) {
		return this.bookDao.loginBookShop(account, password);
	}


	@Override
	public Page searchBookShopMembers(HashMap map) {
		return this.bookDao.searchBookShopMembers(map);
	}


	@Override
	public void updateBookShopCategory(String shopId) {
		
		this.updateBookShopCategory(shopId);
		
	}


	@Override
	public void updateBookCategoryStatus(String bookCode, String status) {
		
		this.bookDao.updateBookCategoryStatus(bookCode, status);
		
	}


	@Override
	public List getBookCategoryNolevel(String table, int parentId, int level,
			int currentId, ArrayList cats) {
		HashMap map=new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "1000000");
		Page bData=bookDao.searchBookCategory(map);
		CategoryUtil categoryUtil = new CategoryUtil();
		return categoryUtil.getCategoryNolevelBook(table, bData, parentId, level, currentId, cats);
	}
	
	@Override
	public List getBookCategoryList(int parentId, int level,
			 ArrayList cats) {
		HashMap map=new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "1000000");
		Page bData=bookDao.searchBookCategory(map);
		CategoryUtil categoryUtil = new CategoryUtil();
		return categoryUtil.getBookCategoryList(bData, parentId, level, cats);
	}


	@Override
	public void deleteBookCategory(int catId) {
		this.bookDao.deleteBookCategory(catId);
	}


	@Override
	public String getCategoryChildIds(String table, int parentId) {
		StringBuffer ids=new StringBuffer();
		CategoryUtil categoryUtil=new CategoryUtil();
		HashMap map=new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "1000");
		Page bData=bookDao.searchBookCategory(map);
		ids=categoryUtil.getChildIdsBook(table, bData, parentId, ids);
		String childIds=ids.toString();
		if(childIds.length()>0)
		{
			childIds = childIds.substring(0,childIds.toString().length()-1);
		}
		return childIds;
	}


	@Override
	public void saveBookCategory(BookCategory bookCategory) {
		this.bookDao.saveBookCategory(bookCategory);
	}


	@Override
	public List searchIndexLabel(String type) {
		
		
		return this.bookDao.searchIndexLabel(type);
	}


	@Override
	public Page searchBookShopCount(HashMap map) {
		
		return this.bookDao.searchBookShopCount(map);
	}


	@Override
	public List searchBookBarCode(String cateIds) {
		
		return this.bookDao.searchBookBarCode(cateIds);
	}


	@Override
	public void addBookCode(List bookList) {
		
		this.bookDao.addBookCode(bookList);
	}


	@Override
	public Page searchBooksByCatIds(HashMap map) {
		
		return this.bookDao.searchBooksByCatIds(map);
	}


	@Override
	public List<BookCategory> getBookCategoryLevelOne() {
		return this.bookDao.getBookCategoryLevelOne();
	}


	@Override
	public List searchBooKIndexInfo() {
		
		return this.bookDao.searchBooKIndexInfo();
	}


	@Override
	public List searchBookLabelInfo() {
		
		return this.bookDao.searchBookLabelInfo();
	}


	@Override
	public Object searchMemberBookInfo(String memberId) {
		
		return this.bookDao.searchMemberBookInfo(memberId);
	}


	@Override
	public Page searchBookCollection(HashMap map) {
		
		return this.bookDao.searchBookCollection(map);
	}




	@Override
	public void deleteBookCollection(String id) {
		
		this.bookDao.deleteBookCollection(id);
	}


	@Override
	public HashMap searchBookName() throws Exception {
		
		return this.bookDao.searchBookName();
	}


	@Override
	public void saveBookList(List list) throws Exception {
		
		this.bookDao.saveBookList(list);
	}


	@Override
	public void saveBookInfoList(List list) throws Exception {
		
		this.bookDao.saveBookInfoList(list);
	}
	

	@Override
	public Page searchCourseProjectSystems(HashMap map) {
		return this.bookDao.searchCourseProjectSystems(map);
	}


	@Override
	public void deleteCourseProjectSystem(Integer id) {
		this.bookDao.deleteCourseProjectSystem(id);
	}


	@Override
	public void saveCourseProjectSystem(CourseProjectSystem courseProjectSystem) {
		this.bookDao.saveCourseProjectSystem(courseProjectSystem);
	}


	@Override
	public Page searchBookShopSale(HashMap map) {
		
		return this.bookDao.searchBookShopSale(map);
	}


	@Override
	public Object[] searchBookShopSum(HashMap map) {
		
		return this.bookDao.searchBookShopSum(map);
	}


	@Override
	public Page searchBook(HashMap map) {
		
		
		return this.bookDao.searchBook(map);
	}


	@Override
	public Page findAnchors(HashMap map) {
		return this.bookDao.findAnchors(map);
	}


	@Override
	public void saveAnchor(Anchor anchor) {
		this.bookDao.saveAnchor(anchor);
	}


	@Override
	public void deleteAnchor(Integer id) {
		this.bookDao.deleteAnchor(id);
	}


	@Override
	public Page searchBookShopCategoryInfo(HashMap map) {
		
		
		return this.bookDao.searchBookShopCategoryInfo(map);
	}


	@Override
	public String getShopIdByUserId(String userId) {
		
		return this.bookDao.getShopIdByUserId(userId);
	}


	@Override
	public void addShopCategory(BookShopCategory bookShopCategory) {
		
		this.bookDao.addShopCategory(bookShopCategory);
	}


	@Override
	public void deleteBookShopCateId(String cateID) {
		
		this.bookDao.deleteBookShopCateId(cateID);
		
	}


	@Override
	public List searchBookQRCode(String cateIds, String shopId) {
		
		return this.bookDao.searchBookQRCode(cateIds,shopId);
	}


	@Override
	public Page getBookList(HashMap map) {
	
		Page bookLists = this.bookDao.getBookList(map);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) bookLists
				.getItems();
		JSONArray result = new JSONArray();
		for(int i=0;i<dataList.size();i++){
			JSONObject temp = new JSONObject();
			Object[] obj = dataList.get(i);
			temp.put("cateID", obj[0]);
			temp.put("name", obj[1]);
			temp.put("author", obj[2]);
			temp.put("publish", obj[3]);
			temp.put("price", obj[4]);
			temp.put("series", obj[5]);
			temp.put("outCount", 0);//借出
			temp.put("count", obj[6]);//总库存
			temp.put("updateStatus", 1);
			temp.put("shopId", 15);
			result.add(temp);
		}
		bookLists.setItems(result);
		// 通过图书馆账号ID获取当前图书馆所有书籍列表
		return bookLists;
	}


	@Override
	public Page searchBookByName(HashMap map) {
		// TODO Auto-generated method stub
		
		Page bookLists = this.bookDao.searchBookByName(map);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) bookLists
				.getItems();
		JSONArray result = new JSONArray();
		for(int i=0;i<dataList.size();i++){
			JSONObject temp = new JSONObject();
			Object[] obj = dataList.get(i);
			temp.put("cateID", obj[0]);
			temp.put("name", obj[1]);
			temp.put("cover", obj[2]);
			temp.put("translator", obj[3]);
			temp.put("series", obj[4]);
			result.add(temp);
		}
		bookLists.setItems(result);
		// 通过图书馆账号ID获取当前图书馆所有书籍列表
		return bookLists;

	}


	@Override
	public Page getBookStockList(HashMap map) {
		// TODO Auto-generated method stub
		return this.bookDao.getBookStockList(map);
	}
	
}
