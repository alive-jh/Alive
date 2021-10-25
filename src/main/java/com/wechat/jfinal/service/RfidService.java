package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.xx;

import java.util.List;

public class RfidService {

    public int isLegal(String rfid){
        List<Record> list = Db.find("SELECT * FROM v_rfid_all WHERE card_id = ?",rfid);
        if(xx.isEmpty(list))
            return 0;//没有这张卡
        if(list.size() == 1 )
            if(list.get(0).getStr("student_id").equals(""))
                return 1;//新卡，未被使用，可使用
            else
                return 2;// 该卡已有学生ID，已被使用（历史遗留问题）
        if(list.size() > 1)//该卡已被使用
            return 2;
        return 0;
    }
}
