package com.wechat.jfinal.service;

import com.wechat.jfinal.model.Member;

public class MemberService {

	/**
	 * 根据手机号码获取用户信息
	 * @param mobile
	 * @return Member 会员对象
	 */
    public Member getBymobile(String mobile){
    	
    	//type:0~2=凡豆伴用户,5=care用户
        return Member.dao.findFirst("SELECT * FROM member WHERE id = "
        		+ "(SELECT memberid FROM memberaccount WHERE account = ? and (type <=2 or type=5) order by createdate ASC LIMIT 1)", mobile);
        
    }
}
