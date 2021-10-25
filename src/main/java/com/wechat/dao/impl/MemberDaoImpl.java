package com.wechat.dao.impl;

import com.wechat.dao.MemberDao;
import com.wechat.entity.*;
import com.wechat.util.MD5UTIL;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public class MemberDaoImpl extends BaseDaoImpl implements MemberDao  {

	
	@Override
	public void saveMember(Member member) {
		
		this.saveOrUpdate(member);
	}

	
	@Override
	public void saveSMSCode(SMSCode smsCode) {
		
		this.saveEntity(smsCode);
	}

	
	@Override
	public Page searchMember(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Member where 1=1 ");
		if(!"".equals(map.get("memberId")) && map.get("memberId")!= null)
		{
			sql.append(" and id =").append(map.get("memberId").toString());
		}
		if(!"".equals(map.get("sex")) && map.get("sex")!= null)
		{
			sql.append(" and sex ='").append(map.get("sex").toString()).append("'");
		}
		if(!"".equals(map.get("nickName")) && map.get("nickName")!= null)
		{
			sql.append(" and nickName like '%").append(map.get("nickName").toString()).append("%'");
		}
		if(!"".equals(map.get("mobile")) && map.get("mobile")!= null)
		{
			sql.append(" and mobile ='").append(map.get("mobile").toString()).append("'");
		}
		if(!"".equals(map.get("type")) && map.get("type")!= null)
		{
			sql.append(" and type =").append(map.get("type").toString());
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and createDate >='").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and createDate <='").append(map.get("endDate").toString()).append("'");
		}
		sql.append(" order by id desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}	

	
	@Override
	public String searchSMSCode(String mobile) {
		
		String sql = "select code from smscode where mobile ='"+mobile+"' order by id desc  ";
		List list = this.executeSQL(sql);
		String code = "";
		if(list.size()>0)
		{
			code = list.get(0).toString();
		}
		
		return code;
	}

	
	@Override
	public void updateMemberMobile(String mobile,String memberId) {
		
		String sql = "update member set mobile ='"+mobile+"' where id="+memberId+"";
		this.executeUpdateSQL(sql);
	}


	
	@Override
	public Member getMember(Member member) {
		
		return (Member)this.getEntity(Member.class, member.getId());
	}


	
	@Override
	public Member getMemberByOpenId(String openId) {
		
		String sql = "from Member where openId='"+openId+"'";
		List<Member> list = this.executeHQL(sql);
		Member member = new Member();
		if(list.size()>0)
		{
			member = list.get(0);
		}
		return member;
	}
	@Override
	public Page searchMemberInfo(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" select a.id,a.nickname,a.sex,a.province,a.city,a.mobile,a.createdate,a.headimgurl,b.fraction "
					 +" from member a  left join  (select memberid, sum(fraction) fraction from integral group by memberid) b on a.id = b.memberid ");
		
		if(!"".equals(map.get("memberId")) && map.get("memberId")!= null)
		{
			sql.append(" and a.id =").append(map.get("memberId").toString());
		}
		if(!"".equals(map.get("sex")) && map.get("sex")!= null)
		{
			sql.append(" and a.sex ='").append(map.get("sex").toString()).append("'");
		}
		if(!"".equals(map.get("nickName")) && map.get("nickName")!= null)
		{
			sql.append(" and a.nickName like '%").append(map.get("nickName").toString()).append("%'");
		}
		if(!"".equals(map.get("mobile")) && map.get("mobile")!= null)
		{
			sql.append(" and a.mobile ='").append(map.get("mobile").toString()).append("'");
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and a.createDate >='").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and a.createDate <='").append(map.get("endDate").toString()).append("'");
		}
		sql.append(" order by a.id desc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}	
	
	
	@Override
	public Page searchIntegral(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" select b.name,a.fraction,a.createdate from integral a ,integraltype b where a.typeid = b.id and a.memberid = "+map.get("memberId")+"  order by a.id desc  ");
		
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}


	
	@Override
	public Member getMemberByMobile(String mobile) {
		
		List list = this.executeHQL(" from Member where mobile = '"+mobile+"'");
		Member member = new Member();
		if(list.size()>0)
		{
			member = (Member)list.get(0);
			
		}
		return member;
	}


	
	@Override
	public String getMemberId(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" select memberid from member_account where 1=1 ");
		if(!"".equals(map.get("mobile")) && map.get("mobile")!= null )
		{
			sql.append(" and mobile = '").append(map.get("mobile")).append("'");
		}
		if(!"".equals(map.get("memberId")) && map.get("memberId")!= null )
		{
			sql.append(" and memberid = '").append(map.get("memberId")).append("'");
		}
		List list = this.executeSQL(sql.toString());
		String memberId = "";
		if(list.size()>0)
		{
			memberId = list.get(0).toString();
		}
		return memberId;
	}


	
	@Override
	public void saveMemberAccount(MemberAccount memberAccount) {
		
		this.saveEntity(memberAccount);
	}


	
	@Override
	public void updateMemberAccountPwd(String id, String pwd) {
		
		pwd = MD5UTIL.MD5(pwd);
		this.executeUpdateSQL(" update memberaccount set password = '"+pwd+"' where memberid ="+id);
		
	}


	
	@Override
	public MemberChildren getMemberChildren(String memberId) {
		
		List<MemberChildren> list = this.executeHQL(" from MemberChildren where 1=1 and memberId = "+memberId);
		MemberChildren memberChildren = new MemberChildren();
		if(list.size()>0)
		{
			memberChildren = list.get(0);
		}
		return memberChildren;
	}


	
	@Override
	public void saveMemberChildren(MemberChildren memberChildren) {
		
		this.saveEntity(memberChildren);
	}


	
	@Override
	public MemberAccount login(String account, String pwd) {
		
		String sql = " from MemberAccount where account = '"+account+"' and password = '"+pwd+"'";
		MemberAccount memberAccount = new MemberAccount();
		List<MemberAccount> list = this.executeHQL(sql);
		if(list.size()>0)
		{
			memberAccount = list.get(0);
		}
		return memberAccount;
	}


	
	@Override
	public void deleteMemberKeyword(String id) {
		
		this.executeUpdateSQL(" delete from memberkeyword where memberid = "+id);
	}


	
	@Override
	public void saveMemberKeyword(MemberKeyword memberKeyword) {
		
		List list = this.executeHQL(" from MemberKeyword where keyword  ='"+memberKeyword.getKeyword()+"'");
		if(list.size() == 0)
		{
			this.saveEntity(memberKeyword);
		}
		
	}


	
	@Override
	public List searchMemberKerword(String memberId) {
		
		
		return this.executeHQL(" from MemberKeyword where memberId="+memberId);
		
	}


	
	@Override
	public void deleteMemberCollection(String id) {
		
		this.executeUpdateSQL("delete from membercollection where id in (  "+id+" )");
	}


	
	@Override
	public void deleteMemberMp3(String id) {
		
		this.executeUpdateSQL("delete from membermp3 where id in ( "+id+" )");
	}


	
	@Override
	public void saveMemberCollection(MemberCollection memberCollection) {
		
		List list = this.executeHQL(" from MemberCollection where productId  ="+memberCollection.getProductId()+" and memberId="+memberCollection.getMemberId());
		if(list.size()==0)
		{
			this.saveEntity(memberCollection);
		}
		
	}


	
	@Override
	public void saveMemberMp3(MemberMp3 memberMp3) {
		
		List list = this.executeHQL(" from MemberMp3 where productId  ="+memberMp3.getProductId()+" and memberId="+memberMp3.getMemberId());
		if(list.size()==0)
		{
			this.saveEntity(memberMp3);
		}
	}


	
	@Override
	public Page searchMemberCollection(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" select b.id,a.id productid,a.name,a.logo1,a.mp3,a.mp3type from mallproduct a,membercollection b where a.id = b.productid and b.memberid="+map.get("memberId"));

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}


	
	@Override
	public Page searchMemberMp3(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" select b.id,a.id productid,a.name,a.logo1,a.mp3,a.mp3type from mallproduct a,membermp3 b where a.id = b.productid and b.memberid="+map.get("memberId"));
		if(!"".equals(map.get("mp3Type")) && map.get("mp3Type")!= null)
		{
			
			sql.append(" and a.mp3type=").append(map.get("mp3Type"));
			
		}
		sql.append(" order by id desc ");

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}


	@Override
	public void saveMemberPayment(MemberPayment memberPayment) {
	
		this.saveEntity(memberPayment);
		
	}


	@Override
	public void saveMemberIntegar(Integral integral) {
		
		this.saveEntity(integral);
	}


	@Override
	public Page searchMemberIntegral(HashMap map) {
	
		StringBuffer sql  = new StringBuffer("select a.id,a.memberid,a.status,a.fraction,a.createdate,b.name,a.remark " +
					"from integral a,integraltype b where a.typeid = b.id and membertype="+map.get("type")+" and a.memberid="+map.get("memberId") +" order by a.createdate desc");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}


	@Override
	public MemberAccount searchMemberAccountByMemberId(String memberId) {
		
		MemberAccount memberAccount = new MemberAccount();
		List list = this.executeHQL(" from MemberAccount where memberId = "+memberId);
		if(list.size()>0)
		{
			memberAccount = (MemberAccount)list.get(0);
		}
		
		return memberAccount;
	}


	@Override
	public List searchMemberMp3ByMemberId(String memberId,String productId) {
		
		return this.executeHQL(" from MemberMp3 where productId="+productId+" and memberId="+memberId);
	}


	@Override
	public void saveMemberBook(MemberBook memberBook) {
		
		this.saveOrUpdate(memberBook);
	}


	@Override
	public MemberBook getMemberBook(String memberId) {
		
		MemberBook memberBook = new MemberBook();
		
		List<MemberBook> list = this.executeHQL(" from MemberBook where memberId = "+memberId);
		if(list.size() > 0)
		{
			memberBook = list.get(0);
		}
		return memberBook;
	}


	@Override
	public void saveAccountInfo(AccountInfo accountInfo) {
		
		this.saveOrUpdate(accountInfo);
	}


	@Override
	public AccountInfo getAccountInfo(String memberId) {
		
		AccountInfo accountInfo = new AccountInfo();
		List<AccountInfo> list = this.executeHQL("from AccountInfo where memberId = "+memberId);
		if(list.size()>0)
		{
			accountInfo = list.get(0);
		}
		return accountInfo;
	}
	
	
	@Override
	public void updateMemberInfo(String mobile,String name,String memberId)
	{
		this.executeUpdateSQL(" update member set mobile ='"+mobile+"' ,tempname='"+name+"' where id = "+memberId);
	}


	@Override
	public void updateMemberAge(String mobile, String name, String age,
			String memberId) {
		
		this.executeUpdateSQL(" update member set mobile ='"+mobile+"' ,tempname='"+name+"',age="+age+" where id = "+memberId);
		
	}

	@Override
	public void qiandao(String memberId)
	{
		this.executeUpdateSQL(" update member set age='1' where id = "+memberId);
	}
	
	@Override
	public List searchMermberByqiandao(String name,String mobile)
	{
		String sql = " select tempname,mobile,age from member where 1=1 and tempname is not null ";
		if(!"".equals(name) && name!= null)
		{
			sql = sql+" and tempname like '%"+name+"%'";
		}
		if(!"".equals(mobile) && mobile!= null)
		{
			sql = sql+" and mobile like '%"+mobile+"%'";
		}
		return this.executeSQL(sql);
	}


	@Override
	public Object[] searchaMemberEpalIdByMobile(String mobile, String memberId) {
		
		String sql = "select a.epal_id,b.id,b.openid ,c.nickname from device_relation a,member b ,device c where a.epal_id = c.epal_id and a.friend_id = b.mobile and b.id = "+memberId+"  and friend_id = '"+mobile+"' and isbind = 1";
		List list = this.executeSQL(sql);
		Object[] obj  =	null;
		if(list.size()>0)
		{
			obj = (Object[])list.get(0);
		}
		return obj;
	}


	@Override
	public void updateAppletMember(String memberId) throws Exception {
		
		String sql = " update member set accountid = 1 where id = "+memberId;
		this.executeUpdateSQL(sql);
		
	}
	
	
	@Override
	public List searchEapLIdList(String mobile)
	{
		
		//return this.executeHQL(" from DeviceRelation where 1=1 and friendId = '"+mobile+"' and  isbind=1 and role ='guardian'");
		String sql = " select a.id,a.epal_id,a.friend_id,a.role,b.nickname from device_relation a ,device b where  a.isbind = 1 and a.epal_id = b.epal_id and a.friend_id ='"+mobile+"'";
		return this.executeSQL(sql);
	}


	@Override
	public boolean checkPasswdIsExites(String account) {
		// TODO Auto-generated method stub
		AccountInfo accountInfo = new AccountInfo();
		List<MemberAccount> list = this.executeHQL("from MemberAccount where account='"+account + "'");
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Member getMemberById(int memberId) {
		// TODO Auto-generated method stub
		return (Member)this.getEntity(Member.class, memberId);
	}


	@Override
	public void deleteMemberByID(int memberId) {
		// TODO Auto-generated method stub
		this.executeSQL("delete from member where id = "+memberId);
	}


	@Override
	public Member getMemberByOpenIdWithType(String openId, int type) {
		String hql = "from Member where openId='"+openId+"' and (type<=2 or type=5)	and status > 0";
		List<Member> list = this.executeHQL(hql);
		Member member = new Member();
		if(list.size()>0)
		{
			member = list.get(0);
		}
		return member;
	}

}
