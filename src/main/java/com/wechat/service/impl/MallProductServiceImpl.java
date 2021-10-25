package com.wechat.service.impl;

import com.wechat.dao.MallProductDao;
import com.wechat.dao.ProductCategoryDao;
import com.wechat.entity.*;
import com.wechat.service.MallProductService;
import com.wechat.util.CategoryUtil;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("mallProductService")
public class MallProductServiceImpl implements MallProductService{

	@Resource
	private  MallProductDao  mallProductDao;
	
	@Resource
	private ProductCategoryDao productCategoryDao;
	
	
	public ProductCategoryDao getProductCategoryDao() {
		return productCategoryDao;
	}

	public void setProductCategoryDao(ProductCategoryDao productCategoryDao) {
		this.productCategoryDao = productCategoryDao;
	}

	public MallProductDao getMallProductDao() {
		return mallProductDao;
	}

	public void setMallProductDao(MallProductDao mallProductDao) {
		this.mallProductDao = mallProductDao;
	}
	
	@Override
	public void deleteMallProduct(MallProduct mallProduct) {
		
		this.mallProductDao.deleteMallProduct(mallProduct);
	}

	
	@Override
	public MallProduct getMallProduct(MallProduct mallProduct) {
		
		return this.mallProductDao.getMallProduct(mallProduct);
	}

	
	@Override
	public void saveMailProduct(MallProduct mallProduct) {
		
		this.mallProductDao.saveMailProduct(mallProduct);
	}

	
	@Override
	public Page searchMallProduct(HashMap map) {
		
		return this.mallProductDao.searchMallProduct(map);
	}

	
	@Override
	public void deleteMallSpecifications(String productId) {
		
		this.mallProductDao.deleteMallSpecifications(productId);
	}

	
	@Override
	public List getMallSpecifications(String productId) {
		
		return this.mallProductDao.getMallSpecifications(productId);
	}

	
	@Override
	public void saveMallSpecifications(MallSpecifications mallSpecifications) {
		
		this.mallProductDao.saveMallSpecifications(mallSpecifications);
	}

	
	@Override
	public void saveOrder(MallOrder mallOrder) {
		
		this.mallProductDao.saveOrder(mallOrder);
	}

	
	@Override
	public Page searchMallOrder(HashMap map) {
		
		return this.mallProductDao.searchMallOrder(map);
	}

	
	@Override
	public void updateOrderStatus(String orderId, String status) {
		
		this.mallProductDao.updateOrderStatus(orderId, status);
	}

	
	@Override
	public void deleteUserAddress(String id) {
	
		this.mallProductDao.deleteUserAddress(id);
	}

	
	@Override
	public UserAddress getUserAddress(Integer id) {
	
		return this.mallProductDao.getUserAddress(id);
	}

	
	@Override
	public void saveUserAddress(UserAddress userAddress) {
	
		this.mallProductDao.saveUserAddress(userAddress);
	}

	
	@Override
	public List searchCity(String provinceId) {
	
		return this.mallProductDao.searchCity(provinceId);
	}

	
	@Override
	public List searchProvince() {
	
		return this.mallProductDao.searchProvince();
	}

	
	@Override
	public List searchUserAddress(String memberId) {
	
		return this.mallProductDao.searchUserAddress(memberId);
	}

	
	@Override
	public UserAddress getUserAddressBystatus(Integer memberId) {
		
		return this.mallProductDao.getUserAddressBystatus(memberId);
	}

	
	@Override
	public void updateAddressStatus(String id, String memberId) {
		
		this.mallProductDao.updateAddressStatus(id, memberId);
	}

	
	@Override
	public void saveMallOrderProduct(MallOrderProduct mallOrderProduct) {
		
		this.mallProductDao.saveMallOrderProduct(mallOrderProduct);
	}

	
	@Override
	public List searchMallOrderProduct(String orderId) {
		
		return this.mallProductDao.searchMallOrderProduct(orderId);
	}

	
	@Override
	public void deleteShoppingCart(String id) {
		
		this.mallProductDao.deleteShoppingCart(id);
	}

	
	@Override
	public void saveShoppingCart(ShoppingCart shoppingCart) {
		
		this.mallProductDao.saveShoppingCart(shoppingCart);
	}

	
	@Override
	public List searchShoppingCart(String memberId) {
		
		return this.mallProductDao.searchShoppingCart(memberId);
	}

	
	@Override
	public void updateCount(String id, String count) {
		
		this.mallProductDao.updateCount(id, count);
	}

	
	@Override
	public HashMap searchMallProductMap(String ids) {
		
		return this.mallProductDao.searchMallProductMap(ids);
	}

	
	@Override
	public List searchMallOrderByMemberId(String memberId) {
		
		return this.mallProductDao.searchMallOrderByMemberId(memberId);
	}

	
	@Override
	public List searchMallOrderProductByMemberId(String orderId) {
		
		return this.mallProductDao.searchMallOrderProductByMemberId(orderId);
	}

	
	@Override
	public List searchMallProductList(String productId) {
		
		return this.mallProductDao.searchMallProductList(productId);
	}

	
	@Override
	public List searchMemberOrder(HashMap map) {
		
		return this.mallProductDao.searchMemberOrder(map);
	}

	
	@Override
	public Page searchMallOrderInfo(HashMap map) {
		
		return this.mallProductDao.searchMallOrderInfo(map);
	}

	
	@Override
	public void updateMallOrderStatus(String id, Integer operatorId,
			String express) {
		
		this.mallProductDao.updateMallOrderStatus(id, operatorId, express);
		
	}

	
	@Override
	public List seartchMallList(String productId) {
		
		return this.mallProductDao.seartchMallList(productId);
	
	}

	
	@Override
	public void saveMallProductLog(MallProductLog mallProductLog) {
		
		this.mallProductDao.saveMallProductLog(mallProductLog);
	}

	
	@Override
	public Page searchMobileMallProduct(HashMap map) {
		
		return this.mallProductDao.searchMobileMallProduct(map);
	}

	
	@Override
	public Object[] searchMallProductInfo(String productId) {
		
		return this.mallProductDao.searchMallProductInfo(productId);
	}

	
	@Override
	public List searchUserAddressStatus(String memberId) {
		
		return this.mallProductDao.searchUserAddressStatus(memberId);
	}

	
	@Override
	public MallOrder getMallOrder(String orderId) {
		
		return this.mallProductDao.getMallOrder(orderId);
	}
	
	
	@Override
	public  void updateOrderRefundStatus(String orderId, String status)
	{
		 this.mallProductDao.updateOrderRefundStatus(orderId,status);
	}

	
	@Override
	public MallOrderProduct getMallOrderProduct(String id) {
		
		return this.mallProductDao.getMallOrderProduct(id);
	}

	
	@Override
	public Integer getMallProductLogCount(String memberId, String tempDate) {
		
		return this.mallProductDao.getMallProductLogCount(memberId, tempDate);
	}

	
	@Override
	public void updateProductCount(MallOrderProduct mallOrderProduct) {
		
		this.mallProductDao.updateProductCount(mallOrderProduct);
		
	}

	
	@Override
	public void saveMallOrderService(MallOrderService mallOrderService) {
		
		this.mallProductDao.saveMallOrderService(mallOrderService);
	}

	
	@Override
	public Page searchMallOrderService(HashMap map) {
		
		return this.mallProductDao.searchMallOrderService(map);
	}

	
	@Override
	public void updateMallOrderService(String id, String status,String operatorId) {
		
		this.mallProductDao.updateMallOrderService(id, status,operatorId);
	}

	
	@Override
	public void deleteProductLabel(String id) {
		
		this.mallProductDao.deleteProductLabel(id);
	}

	
	@Override
	public void saveProductLabel(ProductLabel productLabel) {
		
		this.mallProductDao.saveProductLabel(productLabel);
	}

	
	@Override
	public List searchProductLabel(String ids) {
		
		return this.mallProductDao.searchProductLabel(ids);
	}

	
	@Override
	public Page searchMallProductList(HashMap map) {
		
		return this.mallProductDao.searchMallProductList(map);
	}

	
	@Override
	public void deleteMallProductKeyword(String productIds) {
		
		this.mallProductDao.deleteMallProductKeyword(productIds);
	}

	
	@Override
	public void saveMallProductKeyword(MallProductKeyword mallProductKeyword) {
		
		this.mallProductDao.saveMallProductKeyword(mallProductKeyword);
	}

	
	@Override
	public List searchMallProductKeyword(String productId) {
		
		return this.mallProductDao.searchMallProductKeyword(productId);
	}

	@Override
	public void saveMallLabel(MallLabel mallLabel) {
		
		this.mallProductDao.saveMallLabel(mallLabel);
	}

	@Override
	public void deleteMallLabel(String id) {
		
		this.mallProductDao.deleteMallLabel(id);
	}

	@Override
	public Page searchMallLabel(HashMap map) {
		
		return this.mallProductDao.searchMallLabel(map);
	}

	@Override
	public List searchProductCollection(String memberId, String productId) {
	
		return this.mallProductDao.searchProductCollection(memberId, productId);
	}

	@Override
	public void deletreProductCollectionByMemberId(String memberId,
			String productId) {
		
		this.mallProductDao.deletreProductCollectionByMemberId(memberId, productId);
	}

	@Override
	public void saveMallBanner(MallBanner mallBanner) {
		
		this.mallProductDao.saveMallBanner(mallBanner);
	}

	@Override
	public void deleteMallBanner(String id) {
		
		this.mallProductDao.deleteMallBanner(id);
	}

	@Override
	public Page searchMallBanner(HashMap map) {
		
		return this.mallProductDao.searchMallBanner(map);
	}

	@Override
	public void updateMallBannerStatus(String id, String status) {
		
		this.mallProductDao.updateMallBannerStatus(id, status);
	}

	@Override
	public Object[] searchMemberInfo(String memberId) {
		
		return this.mallProductDao.searchMemberInfo(memberId);
	}

	@Override
	public void deletreProductMp3ByMemberId(String memberId, String productId) {
		
		this.mallProductDao.deletreProductMp3ByMemberId(memberId,productId);
		
	}

	@Override
	public void updateMallabel(String id, String ios, String wechat,
			String android) {
		
		this.mallProductDao.updateMallabel(id, ios, wechat, android);
		
	}

	@Override
	public List searchMallProdyctByLabel(String labelId,String pageSize) {
		
		return this.mallProductDao.searchMallProdyctByLabel(labelId,pageSize);
	}

	@Override
	public MallSpecifications getMallSpecificationsById(String id) {
		
		return this.mallProductDao.getMallSpecificationsById(id);
	}

	@Override
	public Page searchMobileMallProductByApp(HashMap map) {
		
		return this.mallProductDao.searchMobileMallProductByApp(map);
	}

	@Override
	public MallProduct getMallProductById(String id) {
		
		return this.mallProductDao.getMallProductById(id);
	}
	
	
	@Override
	public String getCategoryChildIds(String table, int parentId){
		StringBuffer ids=new StringBuffer();
		CategoryUtil categoryUtil=new CategoryUtil();
		HashMap map=new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "1000");
		Page bData=getProductCategoryDao().searchProductCategory(map);
		ids=categoryUtil.getChildIds(table, bData, parentId, ids);
		String childIds=ids.toString();
		if(childIds.length()>0)
		{
			childIds = childIds.substring(0,childIds.toString().length()-1);
		}
		return childIds;
	}

	@Override
	public List searchMallLabelByIndex(HashMap map) {
		
		return this.mallProductDao.searchMallLabelByIndex(map);
	}

	@Override
	public List searchMallBanner() {
		
		return this.mallProductDao.searchMallBanner();
	}

	@Override
	public List searchAppIndex() {
		
		return this.mallProductDao.searchAppIndex();
	}

	@Override
	public Page searchAppIndex(HashMap map) {
		
		return this.mallProductDao.searchAppIndex(map);
	}

	@Override
	public void saveAppIndex(AppIndex appIndex) {
		
		this.mallProductDao.saveAppIndex(appIndex);
	}

	@Override
	public void deleteAppIndex(String id) {
		
		this.mallProductDao.deleteAppIndex(id);
	}

	@Override
	public List searchLabeiList() {
		
		return this.mallProductDao.searchLabeiList();
	}

	@Override
	public List searchCateGory() {
		
		return this.mallProductDao.searchCateGory();
	}

	@Override
	public List searchMallProdyctByCateGory(String cateIds) {
		
		
		return this.mallProductDao.searchMallProdyctByCateGory(cateIds);
	}

	@Override
	public List searchCateGoryByParentId(String id) {

		return this.mallProductDao.searchCateGoryByParentId(id);
	}

	@Override
	public void saveCourse(Course course) {
		
		this.mallProductDao.saveCourse(course);
	}

	@Override
	public void deleteCourse(String id) {
		
		this.mallProductDao.deleteCourse(id);
	}

	@Override
	public List searchCourse(String mallProductId) {
		
		return this.mallProductDao.searchCourse(mallProductId);
	}

	@Override
	public void saveCourseProject(CourseProject courseProject) {
		
		this.mallProductDao.saveCourseProject(courseProject);
	}

	@Override
	public void deleteCourseProject(String projectId) {
		
		this.mallProductDao.deleteCourseProject(projectId);
	}

	@Override
	public Page searchCourseProject(HashMap map) {
		
		return this.mallProductDao.searchCourseProject(map);
	}

	@Override
	public List searchCourseProjectInfo(String projectId) {
		
		return this.mallProductDao.searchCourseProjectInfo(projectId);
	}

	@Override
	public void saveCourseProjectInfo(CourseProjectInfo courseProjectInfo) {

		this.mallProductDao.saveCourseProjectInfo(courseProjectInfo);
		
	}

	@Override
	public String searchCourseProjectMaxSort(String memberId) {
		
		return this.mallProductDao.searchCourseProjectMaxSort(memberId);
	}

	@Override
	public void updateCeourseProjectSort(String id, String sort) {
		
		this.mallProductDao.updateCeourseProjectSort(id, sort);
	}

	@Override
	public String searchCourseProjectMaxSortByEpalId(String epalId) {
		return this.mallProductDao.searchCourseProjectMaxSortByEpalId(epalId);
	}

	@Override
	public Page searchCourseProjectSystem(HashMap map) {
		
		return this.mallProductDao.searchCourseProjectSystem(map);
	}

	@Override
	public List searchCurriculumIndex() {
		
		return this.mallProductDao.searchCurriculumIndex();
	
	}

	@Override
	public List searchCategoryInfo() {
		
		
		return this.mallProductDao.searchCategoryInfo();
	}
	
	
	@Override
	public void updateMallProductCatSort(List list)
	{
		this.mallProductDao.updateMallProductCatSort(list);
	}
	
	@Override
	public List searchMallProductByCatId(String catId)throws Exception
	{
		return this.mallProductDao.searchMallProductByCatId(catId);
	}


	
	
}
