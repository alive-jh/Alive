package com.wechat.jfinal.service;

import com.wechat.jfinal.model.Memberaccount;

public class MemberaccountService {
    public Memberaccount findByMemberId(int memberId){
        return Memberaccount.dao.findFirst("SELECT * FROM memberaccount WHERE memberid = ?", memberId);
    }

    public Memberaccount getByMobile(String mobile) {
        return Memberaccount.dao.findFirst("SELECT * FROM memberaccount WHERE account = ?", mobile);
    }
}
