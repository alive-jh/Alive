package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.model.ClassSlot;

import java.util.List;

public class SlotService  {
    private static final ClassSlot dao = new ClassSlot().dao();

    public List<Record> getSlotsNew(int stdId) {
        SqlPara sqlPara = Db.getSqlPara("slot.getSlots",stdId);
        return Db.find(sqlPara);
    }
    @Deprecated
    public static List<Record> getSlots(int stdId){
        return Db.find("SELECT s.do_slot id, s.name, s.do_time time FROM class_slot s, class_student cs WHERE cs.id =  "+stdId+"  AND cs.epal_id = s.epal_id");
    }


    public Object getById(int id) {
        return dao.findById(id);
    }
}
