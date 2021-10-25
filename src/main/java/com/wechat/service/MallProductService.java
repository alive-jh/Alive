package com.wechat.service;

import com.wechat.entity.*;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface MallProductService {

	
	/**
	 * 保存商品
	 * @param mallProduct
	 */
	void saveMailProduct(MallProduct mallProduct);
	
	/**
	 * 删除商品
	 * @param mallProduct
	 */
	void deleteMallProduct(MallProduct mallProduct);
	
	/**
	 * 查询单个商品
	 * @param mallProduct
	 * @return
	 */
	MallProduct getMallProduct(MallProduct mallProduct);
	
	/**
	 * 查询商品列表
	 * @param map
	 * @return
	 */
	Page searchMallProduct(HashMap map);
	
	/**
	 * 添加商品规格
	 * @param mallSpecifications
	 */
	void saveMallSpecifications(MallSpecifications mallSpecifications);
	/**
	 * 查询规格表
	 * @param productId
	 * @return
	 */
	List getMallSpecifications(String productId);
	
	/**
	 * 删除商品规格表
	 * @param productId
	 */
	void deleteMallSpecifications(String productId);
	
	
	
	/**
	 * 保存订单信息
	 * @param mallOrder
	 */
	void saveOrder(MallOrder mallOrder);
	/**
	 * 查询订单列表
	 * @param map
	 * @return
	 */
	Page searchMallOrder(HashMap map);
	/**
	 * 修改订单状态
	 * @param orderId
	 * @param status
	 */
	void updateOrderStatus(String orderId, String status);
	
	/**
	 * 查询省份列表
	 * @return
	 */
	public List searchProvince();
	/**
	 * 根据省份查询城市列表
	 * @param provinceId
	 * @return
	 */
	public List searchCity(String provinceId);
	/**
	 * 查询收货地址列表
	 * @param memberId
	 * @return
	 */
	public List searchUserAddress(String memberId);
	/**
	 * 保存会员收货地址
	 * @param userAddress
	 */
	public void saveUserAddress(UserAddress userAddress);
	
	/**
	 * 删除收货地址
	 * @param id
	 */
	public void deleteUserAddress(String id);
	
	/**
	 * 查询单个收货地址
	 * @param id
	 * @return
	 */
	public UserAddress getUserAddress(Integer id);
	
	
	
	/**
	 * 修改默认收货地址
	 * @param id
	 * @param memberId
	 */
	public void updateAddressStatus(String id, String memberId);
	
	/**
	 * 查询用户默认收货地址
	 * @param memberId
	 * @return
	 */
	public UserAddress getUserAddressBystatus(Integer memberId);
	
	/**
	 * 保存订单产品
	 * @param mallOrderProduct
	 */
	public void saveMallOrderProduct(MallOrderProduct mallOrderProduct);
	
	
	/**
	 * 查询订单产品
	 * @param orderId
	 * @return
	 */
	public List searchMallOrderProduct(String orderId);
	
	
	/**
	 * 保存购物车
	 * @param shoppingCart
	 */
	public void saveShoppingCart(ShoppingCart shoppingCart);
	
	/**
	 * 删除购物车产品
	 * @param id
	 */
	public void deleteShoppingCart(String id);
	
	/**
	 * 查询购物车
	 * @param memberId
	 * @return
	 */
	public List searchShoppingCart(String memberId);
	
	/**
	 * 修改购物车产品数量
	 * @param id
	 * @param count
	 */
	public void updateCount(String id, String count);
	
	/**
	 * 查询产品信息
	 * @param ids
	 * @return
	 */
	public HashMap searchMallProductMap(String ids);
	
	
	
	/**
	 * 查询订单列表
	 * @param memberId
	 * @return
	 */
	public List searchMallOrderByMemberId(String memberId);
	
	/**
	 * 查询订单详情
	 * @param orderId
	 * @return
	 */
	public List searchMallOrderProductByMemberId(String orderId);
	
	/**
	 * 查询商品列表
	 */
	public List searchMallProductList(String productId);
	
	
	
	/**
	 * 查询会员订单列表
	 * @param map
	 * @return
	 */
	public List searchMemberOrder(HashMap map);
	
	/**
	 * 查询会员订单列表
	 * @param map
	 * @return
	 */
	public Page searchMallOrderInfo(HashMap map);

	
	
	/**
	 * 修改订单状态
	 * @param id
	 * @param operatorid
	 * @param express
	 */
	public void updateMallOrderStatus(String id, Integer operatorId, String express);
	
	/**
	 * 查询商品销售记录
	 * @param productId
	 * @return
	 */
	List seartchMallList(String productId);
	
	 /**
	  * 查看商城商品列表-分页
	  * @param map
	  * @return
	  */
	 Page searchMobileMallProduct(HashMap map);
	 
	 /**
	  * 添加商品访问日志
	  * @param mallProductLog
	  */
	 void saveMallProductLog(MallProductLog mallProductLog);
	 
	 /**
	  * 查询商品详情
	  * @param productId
	  * @return
	  */
	 Object[] searchMallProductInfo(String productId);
	 
	 
	 /**
	  * 查询会员 地址
	  * @param memberId
	  * @return
	  */
	 List searchUserAddressStatus(String memberId);
	 
	 
	 /**
	  * 查询单个订单
	  * @param orderId
	  * @return
	  */
	 MallOrder getMallOrder(String orderId);
	 
	 /**
	  * 修改订单退款状态
	  * @param orderId
	  * @param status
	  */
	 void updateOrderRefundStatus(String orderId, String status);
	 
	 /**
	  * 查询单个订单产品信息
	  * @param id
	  * @return
	  */
	 MallOrderProduct getMallOrderProduct(String id);
	 
	 /**
	  * 查询商品访问记录
	  * @param memberId
	  * @param tempDate
	  * @return
	  */
	 Integer getMallProductLogCount(String memberId, String tempDate);
	 
	 /**
	  * 更新产品库存
	  * @param productId
	  */
	 void updateProductCount(MallOrderProduct mallOrderProduct);
	 
	 
	 /**
	  * 保存退货记录
	  * @param mallOrderService
	  */
	 void saveMallOrderService(MallOrderService mallOrderService);
	 
	 /**
	  * 更新退货记录状态
	  * @param id
	  * @param status
	  */
	 void updateMallOrderService(String id, String status, String operatorId);
	 
	 /**
	  * 查询退货列表
	  * @param map
	  * @return
	  */
	 Page searchMallOrderService(HashMap map);
	 
	 /**
	  * 保存商品标签
	  * @param productLabel
	  */
	 void saveProductLabel(ProductLabel productLabel);
	 
	 /**
	  * 删除商品标签
	  * @param id
	  */
	 void deleteProductLabel(String id);
	 
	 /**
	  * 查询商品标签
	  * @param ids
	  * @return
	  */
	 List searchProductLabel(String ids);
	 
	 /**
	  * 查询商品列表
	  * @param map
	  * @return
	  */
	 Page searchMallProductList(HashMap map);
	 
	 /**
	  * 保存商品关键字
	  * @param mallProductKeyword
	  */
	 void saveMallProductKeyword(MallProductKeyword mallProductKeyword);
	 
	 /**
	  * 删除商品关键字
	  */
	 void deleteMallProductKeyword(String productIds);
	 
	 /**
	  * 查询商品关键词
	  * @return
	  */
	 List searchMallProductKeyword(String productId);
	 
	 
	 /**
	  * 保存首页展示标签
	  * @param mallLabel
	  */
	 void saveMallLabel(MallLabel mallLabel);
	 
	 /**
	  * 删除首页展示标签
	  * @param id
	  */
	 void deleteMallLabel(String id);
	 
	 /**
	  * 查询首页展示标签
	  * @param map
	  * @return
	  */
	 Page searchMallLabel(HashMap map);
	 
	 
	 /**
	  * 查询商品用户是否购买
	  * @param memberId
	  * @param productId
	  * @return
	  */
	 List searchProductCollection(String memberId, String productId);
	 
	 
	 /**
	  * 删除商品收藏
	  * @param memberId
	  * @param productId
	  */
	 void deletreProductCollectionByMemberId(String memberId, String productId);

	 /**
	  * 保存首页banner
	  * @param mallBanner
	  */
	 void saveMallBanner(MallBanner mallBanner);
	 
	 /**
	  * 删除首页banner
	  * @param id
	  */
	 void deleteMallBanner(String id);
	 
	 /**
	  * 查询首页benner
	  * @param map
	  * @return
	  */
	 Page searchMallBanner(HashMap map);
	 
	 
	 /**
	  * 修改封面状态
	  * @param map
	  * @return
	  */
	 void updateMallBannerStatus(String id, String status);
	 
	 
	 /**
	  * 查询会员收藏和音频汇总集合
	  * @param memberId
	  * @return
	  */
	 Object[] searchMemberInfo(String memberId);
	 
	 
	 /**
	  * 删除商品音频
	  * @param memberId
	  * @param productId
	  */
	 void deletreProductMp3ByMemberId(String memberId, String productId);
	 
		/**
		 * 修改首页标签设置 
		 * @param id
		 * @param ios
		 * @param wechat
		 * @param android
		 */
	void updateMallabel(String id, String ios, String wechat, String android);
	
	
	/**
	 * 根据标签查询商品
	 * @param labelId
	 * @return
	 */
	List searchMallProdyctByLabel(String labelId, String pageSize);
	
	
	/**
	 * 查询商品规格
	 * @param id
	 * @return
	 */
	MallSpecifications getMallSpecificationsById(String id);
	
	
	
	/**
	 * APP查询商品接口
	 * @param map
	 * @return
	 */
	public Page searchMobileMallProductByApp(HashMap map);
	 
	
	/**
	 * 查询单个产品信息
	 * @param id
	 * @return
	 */
	public MallProduct getMallProductById(String id);
	
	/**
	 * 获取父分类的所有子Id
	 * @param table
	 * @param parentId
	 * @return
	 */
	public String getCategoryChildIds(String table, int parentId);
	
	
	/**
	 * 首页查询标签列表
	 * @param map
	 * @return
	 */
	List searchMallLabelByIndex(HashMap map);
	
	/**
	 * 商城首页查询封面广告
	 * @return
	 */
	public List searchMallBanner();
	
	

	
	
	/**
	 * APP首页查询设置
	 * @return
	 */
	public List searchAppIndex();
	
	/**
	 * 管理后台查询APP设置
	 * @param map
	 * @return
	 */
	public Page searchAppIndex(HashMap map);
	
	/**
	 * 保存app设置
	 * @param appIndex
	 */
	public void saveAppIndex(AppIndex appIndex);
	
	/**
	 * 删除app设置
	 * @param id
	 */
	public void deleteAppIndex(String id);

	/**
	 * 查询首页标签
	 * @return
	 */
	public List searchLabeiList();
	
	/*
	 * 查询课程列表
	 */
	public List searchCateGory();
	
	
	
	/**
	 * 查询课程所属的音频
	 * @param cateIds
	 * @return
	 */
	public  List searchMallProdyctByCateGory(String cateIds);
	
	
	/**
	 * 根据上级查询课程分类
	 * @param id
	 * @return
	 */
	public List searchCateGoryByParentId(String id); 
	
	
	/**
	 * 添加子课程
	 * @param course
	 */
	public void saveCourse(Course course);
	
	/**
	 * 删除子课程
	 * @param id
	 */
	public void deleteCourse(String id);
	
	/**
	 * 根据课程,查询子课程列表
	 * @param MallproductId
	 * @return
	 */
	List searchCourse(String mallProductId);
	
	
	
	/**
	 * 保存课程计划
	 * @param courseProject
	 */
	void saveCourseProject(CourseProject courseProject);
	
	/**
	 * 删除课程计划
	 * @param projectId
	 */
	void deleteCourseProject(String projectId);
	
	/**
	 * 查询课程计划
	 * @param map
	 * @return
	 */
	Page searchCourseProject(HashMap map);
	
	/**
	 * 查询课程计划详情
	 * @return
	 */
	List searchCourseProjectInfo(String projectId);
	
	/**
	 * 保存课程计划详情
	 * @param courseProject
	 */
	void saveCourseProjectInfo(CourseProjectInfo courseProjectInfo);
	
	
	/**
	 * 查询最大排序数
	 * @param memberId
	 * @return
	 */
	String searchCourseProjectMaxSort(String memberId);
	
	
	/**
	 * 修改课程计划排序
	 * @param id
	 * @param sort
	 */
	void updateCeourseProjectSort(String id, String sort);

	/**
	 * 自动给课程计划排序
	 * @param epalId
	 * @return
	 */
	String searchCourseProjectMaxSortByEpalId(String epalId);

	/**
	 * 系统默认课程计划
	 * @param map
	 * @return
	 */
	Page searchCourseProjectSystem(HashMap map); 
	
	
	/**
	 * 查询课程首页数据
	 * @return
	 */
	public List searchCurriculumIndex();
	
	
	/**
	 * 课程首页分类查询数据
	 * @return
	 */
	public List searchCategoryInfo();
	
	
	/**
	 * 修改分类商品排序
	 * @param list
	 */
	public void updateMallProductCatSort(List list);
	
	/**
	 * 查询分类下面的课程
	 * @param catId
	 * @return
	 * @throws Exception
	 */
	public List searchMallProductByCatId(String catId)throws Exception;
}
