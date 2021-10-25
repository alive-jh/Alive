package com.wechat.jfinal.service;

import com.wechat.jfinal.model.AgentSchool;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.model.QyDevice;

import java.util.HashMap;
import java.util.Map;

public class QyDeviceService {

    public Map<String,Object> getInfo(String deviceId){
        QyDevice device = QyDevice.dao.findFirst("SELECT * FROM qy_device WHERE device_id = ?",deviceId);
        Map<String, Object> result = new HashMap<>();
        AgentSchool school = AgentSchool.dao.findById(device.getBelong());
        Memberaccount account = Memberaccount.dao.findFirst("SELECT * FROM memberaccount where memberid = ? ",device.getMemberId());
        result.put("deviceId",device.getId());
        result.put("deviceName",device.getDeviceName());
        result.put("schoolId",device.getBelong());
        result.put("schoolName",school == null ? "":school.getName());
        result.put("memberId",device.getMemberId());
        result.put("memberAccount",account == null ? "" : account.getAccount());

        return result;
    }
}
