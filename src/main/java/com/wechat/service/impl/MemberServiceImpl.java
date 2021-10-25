package com.wechat.service.impl;

import com.wechat.dao.MemberDao;
import com.wechat.entity.*;
import com.wechat.service.MemberService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Resource
	private MemberDao memberDao;

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	
	@Override
	public void saveMember(Member member) {
		
		this.memberDao.saveMember(member);
	}

	
	@Override
	public void saveSMSCode(SMSCode smsCode) {
		
		this.memberDao.saveSMSCode(smsCode);
	}

	
	@Override
	public Page searchMember(HashMap map) {
		
		return this.memberDao.searchMember(map);
	}

	
	@Override
	public String searchSMSCode(String mobile) {
		
		return this.memberDao.searchSMSCode(mobile);
	}

	
	@Override
	public void updateMemberMobile(String mobile, String memberId) {
		
		this.memberDao.updateMemberMobile(mobile, memberId);
	}

	
	@Override
	public Member getMember(Member member) {
		
		return this.memberDao.getMember(member);
	}

	
	@Override
	public Member getMemberByOpenId(String openId) {
		
		return this.memberDao.getMemberByOpenId(openId);
	}

	
	@Override
	public Page searchMemberInfo(HashMap map) {
		
		
		return this.memberDao.searchMemberInfo(map);
	}

	
	@Override
	public Page searchIntegral(HashMap map) {
		
		
		return this.memberDao.searchIntegral(map);
	}

	
	@Override
	public String getMemberId(HashMap map) {
		
		return this.memberDao.getMemberId(map);
	}

	
	@Override
	public void saveMemberAccount(MemberAccount memberAccount) {
		
		this.memberDao.saveMemberAccount(memberAccount);
	}

	
	@Override
	public void updateMemberAccountPwd(String id, String pwd) {
		
		this.memberDao.updateMemberAccountPwd(id, pwd);
	}

	
	@Override
	public Member getMemberByMobile(String mobile) {
		
		
		return this.memberDao.getMemberByMobile(mobile);
	}

	
	@Override
	public MemberChildren getMemberChildren(String memberId) {
		
		return this.memberDao.getMemberChildren(memberId);
	}

	
	@Override
	public void saveMemberChildren(MemberChildren memberChildren) {
		
		this.memberDao.saveMemberChildren(memberChildren);
	}

	
	@Override
	public MemberAccount login(String account, String pwd) {
		
		return this.memberDao.login(account, pwd);
	}

	
	@Override
	public void deleteMemberKeyword(String id) {
		
		this.memberDao.deleteMemberKeyword(id);
		
	}


	@Override
	public void saveMemberKeyword(MemberKeyword memberKeyword) {
		
		this.memberDao.saveMemberKeyword(memberKeyword);
	}

	
	@Override
	public List searchMemberKerword(String memberId) {
		
		return this.memberDao.searchMemberKerword(memberId);
	}

	
	@Override
	public void deleteMemberCollection(String id) {
		
		this.memberDao.deleteMemberCollection(id);
	}

	
	@Override
	public void deleteMemberMp3(String id) {
		
		this.memberDao.deleteMemberMp3(id);
	}

	
	@Override
	public void saveMemberCollection(MemberCollection memberCollection) {
		
		this.memberDao.saveMemberCollection(memberCollection);
	}

	
	@Override
	public void saveMemberMp3(MemberMp3 memberMp3) {
		
		this.memberDao.saveMemberMp3(memberMp3);
	}

	
	@Override
	public Page searchMemberCollection(HashMap map) {
		
		return this.memberDao.searchMemberCollection(map);
	}

	
	@Override
	public Page searchMemberMp3(HashMap map) {
		
		return this.memberDao.searchMemberMp3(map);
	}

	@Override
	public void saveMemberPayment(MemberPayment memberPayment) {
		
		this.memberDao.saveMemberPayment(memberPayment);
		
	}

	@Override
	public void saveMemberIntegar(Integral integral) {
		
		this.memberDao.saveMemberIntegar(integral);
		
	}

	@Override
	public Page searchMemberIntegral(HashMap map) {
		
		return this.memberDao.searchMemberIntegral(map);
	}

	@Override
	public MemberAccount searchMemberAccountByMemberId(String memberId) {
		
		return this.memberDao.searchMemberAccountByMemberId(memberId);
	}

	@Override
	public List searchMemberMp3ByMemberId(String memberId, String productId) {
		
		return this.memberDao.searchMemberMp3ByMemberId(memberId, productId);
	}

	@Override
	public void saveMemberBook(MemberBook memberBook) {
		
		this.memberDao.saveMemberBook(memberBook);
	}

	@Override
	public MemberBook getMemberBook(String memberId) {
		
		
		return this.memberDao.getMemberBook(memberId);
	}

	@Override
	public void saveAccountInfo(AccountInfo accountInfo) {
		
		this.memberDao.saveAccountInfo(accountInfo);
	}

	@Override
	public AccountInfo getAccountInfo(String memberId) {
		
		return this.memberDao.getAccountInfo(memberId);
	}

	@Override
	public void updateMemberInfo(String mobile, String name, String memberId) {
		
		this.memberDao.updateMemberInfo(mobile, name, memberId);
	}

	@Override
	public void updateMemberAge(String mobile, String name, String age,
			String memberId) {
		
		this.memberDao.updateMemberAge(mobile, name, age, memberId);
	}

	@Override
	public void qiandao(String memberId) {
		
		this.memberDao.qiandao(memberId);
	}

	@Override
	public List searchMermberByqiandao(String name,String mobile) {
		
		return this.memberDao.searchMermberByqiandao(name,mobile);
	}

	@Override
	public Object[] searchaMemberEpalIdByMobile(String mobile, String memberId) {
		
		return this.memberDao.searchaMemberEpalIdByMobile(mobile,memberId);
	}

	@Override
	public void updateAppletMember(String memberId) throws Exception {
		
		this.memberDao.updateAppletMember(memberId);
		
	}

	@Override
	public List searchEapLIdList(String mobile) {
		
		return this.memberDao.searchEapLIdList(mobile);
	}

	@Override
	public boolean checkPasswdIsExites(String mobile) {
		// TODO Auto-generated method stub
		return this.memberDao.checkPasswdIsExites(mobile);
	}

	@Override
	public Member getMemberById(int memberId) {
		// TODO Auto-generated method stub
		return this.memberDao.getMemberById(memberId);
	}

	@Override
	public void updateMember(Member member) {
		// TODO Auto-generated method stub
		this.memberDao.saveMember(member);
	}

	@Override
	public void deleteMemberByID(int newMemberId) {
		// TODO Auto-generated method stub
		this.memberDao.deleteMemberByID(newMemberId);
	}

	@Override
	public Member getMemberByOpenIdWithType(String openId, int type) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByOpenIdWithType(openId,type);
	}
	
}
