package com.wechat.service;

import com.wechat.entity.Coupons;
import com.wechat.entity.CouponsInfo;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface CouponsService {

	/**
	 * 创建优惠卷
	 * @param coupons
	 */
	void saveCoupons(Coupons coupons);
	
	/**
	 * 删除优惠卷
	 * @param id
	 */
	void deleteCoupons(String id);
	
	/**
	 * 查询优惠卷分页
	 * @param map
	 * @return
	 */
	Page searchCoupons(HashMap map);
	
	/**
	 * 查询会员优惠卷集合
	 * @param memberId
	 * @return
	 */
	List searchCouponsList(HashMap map);
	
	/**
	 * 会员保存优惠卷
	 * @param CouponsInfo
	 */
	void saveCouponsInfo(CouponsInfo CouponsInfo);
	
	/**
	 * 会员使用优惠卷
	 * @param id
	 */
	void updateCouponsInfoStatus(String id);
	
	/**
	 * 查询会员是否已获得优惠卷
	 * @param memberId
	 * @return
	 */
	List getCoupons(String memberId, String couponsId);
	
	/**
	 * 修改过期的优惠卷
	 */
	void updateCouponsInfoEndDateStatus();
	
	
	/**
	 * 查询单个优惠卷
	 * @param id
	 * @return
	 */
	Coupons getCoupons(String id);
	
	
}
