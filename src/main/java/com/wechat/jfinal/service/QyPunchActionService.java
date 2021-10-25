package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;

public class QyPunchActionService {

    public void delete(int actionId){
        Db.update("UPDATE qy_punch_action_type SET status = -1 WHERE id = ?",actionId);
    }
}
