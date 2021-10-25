package com.wechat.service;

import com.wechat.entity.*;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface MemberService {
	/**
	 * 保存微信会员
	 * @param member
	 */
	void saveMember(Member member);
	
	/**
	 * 修改会员电话
	 * @param mobile
	 */
	void updateMemberMobile(String mobile, String memberId);
	
	/**
	 * 查询会员信息
	 * @param map
	 * @return
	 */
	Page searchMember(HashMap map);
	
	/**
	 * 保存手机验证码
	 * @param smsCode
	 */
	void saveSMSCode(SMSCode smsCode);
	
	/**
	 * 查询手机验证码
	 * @param mobile
	 * @return
	 */
	String searchSMSCode(String mobile);
	
	/**
	 * 根据openId查询会员
	 * @param openId
	 * @return
	 */
	Member getMemberByOpenId(String openId);
	
	/**
	 * 查询会员
	 * @param member
	 * @return
	 */
	Member getMember(Member member);
	
	/**
	 * 查询会员信息列表
	 * @param map
	 * @return
	 */
	Page searchMemberInfo(HashMap map);
	
	
	/**
	 * 查询会员积分列表
	 * @param map
	 * @return
	 */
	public Page searchIntegral(HashMap map);
	
	/**
	 * APP注册用户
	 * @param memberAccount
	 */
	public void saveMemberAccount(MemberAccount memberAccount);
	
	/**
	 * 根据条件获取app会员id
	 * @param map
	 * @return
	 */
	public String getMemberId(HashMap map);
	
	/**
	 * 修改会员账号密码
	 * @param id
	 * @param pwd
	 */
	public void updateMemberAccountPwd(String id, String pwd);
	
	/**
	 * 查询手机号码是否被注册
	 * @param mobile
	 * @return
	 */
	public Member getMemberByMobile(String mobile);
	
	
	/**
	 * 保存会员儿童信息
	 * @param memberChildren
	 */
	public void saveMemberChildren(MemberChildren memberChildren);
	
	
	/**
	 * 查询会员儿童信息
	 * @param memberId
	 * @return
	 */
	public MemberChildren getMemberChildren(String memberId);
	
	/**
	 * 会员账号登录
	 * @param account
	 * @param pwd
	 * @return
	 */
	public MemberAccount login(String account, String pwd);
	
	
	/**
	 * 保存会员搜索记录
	 * @param memberKeyword
	 */
	public void saveMemberKeyword(MemberKeyword memberKeyword);
	
	/**
	 * 删除会员搜索记录
	 * @param id
	 */
	public void deleteMemberKeyword(String id);
	
	/**
	 * 查询会员搜索记录
	 * @param memberId
	 * @return
	 */
	public List searchMemberKerword(String memberId);
	
	/**
	 * 保存会员音频表
	 * @param memberMp3
	 */
	void saveMemberMp3(MemberMp3 memberMp3);
	
	/**
	 * 删除音频表
	 * @param id
	 */
	void deleteMemberMp3(String id);
	
	/**
	 * 查询音频表
	 * @param map
	 * @return
	 */
	Page searchMemberMp3(HashMap map);
	
	
	
	/**
	 * 保存会员收藏
	 * @param memberCollection
	 */
	void saveMemberCollection(MemberCollection memberCollection);
	/**
	 * 删除会员收藏
	 * @param id
	 */
	void deleteMemberCollection(String id);
	
	/**
	 * 查询会员收藏
	 * @param map
	 * @return
	 */
	Page searchMemberCollection(HashMap map);
	
	
	/**
	 * 保存会员充值记录
	 * @param memberPayment
	 */
	void saveMemberPayment(MemberPayment memberPayment);
	
	/**
	 * 保存会员积分
	 * @param integral
	 */
	void saveMemberIntegar(Integral integral);
	
	
	/**
	 * 查询会员积分列表
	 * @param map
	 * @return
	 */
	Page searchMemberIntegral(HashMap map);
	
	
	/**
	 * 查询会员账号
	 * @param memberId
	 * @return
	 */
	MemberAccount searchMemberAccountByMemberId(String memberId);
	
	
	/**
	 * 查询音频是否被购买
	 * @return
	 */
	List searchMemberMp3ByMemberId(String memberId, String productId);
	
	/*
	 * 保存会员卡
	 */
	void saveMemberBook(MemberBook memberBook);
	
	
	/**
	 * 查询会员卡
	 * @param memberId
	 * @return
	 */
	MemberBook getMemberBook(String memberId);
	
	
	/*
	 * 保存账号信息
	 */
	void saveAccountInfo(AccountInfo accountInfo);
	
	
	/**
	 * 查询账号信息
	 * @param memberId
	 * @return
	 */
	AccountInfo getAccountInfo(String memberId);
	
	/*
	 *保存邀请函信息
	 */
	void updateMemberInfo(String mobile, String name, String memberId);
	
	
	/**
	 * 保存签到信息
	 * @param mobile
	 * @param name
	 * @param memberId
	 */
	void updateMemberAge(String mobile, String name, String age, String memberId);
	
	/**
	 * 会场签到
	 * @param memberId
	 */
	public void qiandao(String memberId);
	
	/**
	 * 查询天道数据
	 * @param name
	 * @return
	 */
	public List searchMermberByqiandao(String name, String mobile);
	
	
	/**
	 * 根据手机号码,查询机器人id
	 *
	 * @param mobile
	 * @param memberId
	 * @return
	 */
	public Object[] searchaMemberEpalIdByMobile(String mobile, String memberId);
	
	
	/**
	 * 小程序登录,绑定账号
	 * @throws Exception
	 */
	public void updateAppletMember(String memberId)throws Exception;
	
	
	/**
	 * 查询绑定的机器人列表
	 * @param mobile
	 * @return
	 */
	public List searchEapLIdList(String mobile);

	boolean checkPasswdIsExites(String mobile);

	
	Member getMemberById(int memberId);

	void updateMember(Member member);

	void deleteMemberByID(int newMemberId);

	Member getMemberByOpenIdWithType(String openId, int type);

}
