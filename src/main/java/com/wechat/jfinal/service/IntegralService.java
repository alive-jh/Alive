package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.Member;

public class IntegralService {
    public int getIntegralTotal(Member member){
        int integral = 0;
        Record integralTotal = Db.findFirst("select IFNULL(sum(fraction),0) total from integral where memberid =? and membertype= ?", member.getId(), member.getType());
        if (integralTotal != null)
            integral = integralTotal.getInt("total");
        return integral;
    }
}
