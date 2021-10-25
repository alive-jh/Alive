package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.xx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentSchoolService {

    public Map<Integer,Record> getSchoolByStudentIds(List<Integer> studentIds){
        Map<Integer,Record> result = new HashMap<>();
        if(xx.isEmpty(studentIds))
            return result;
        Kv comd = Kv.by("studentIds",studentIds);
        List<Record> schools = Db.find(Db.getSqlPara("agentSchool.getSchoolByStudentIds",comd));
        for (Record school : schools) {
            result.put(school.getInt("student_id"),school);
        }
        return result;
    }
}
