package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.QyPunchActionTypeGroup;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QyPunchActionTypeGroupService {

    public List<QyPunchActionTypeGroup> getListBySchoolId(int schoolId){
        return QyPunchActionTypeGroup.dao.find("SELECT * FROM qy_punch_action_type_group WHERE school_id = ?",schoolId);
    }

    public void delete(int punchActionTypeGroupId) {
        Db.update("UPDATE qy_punch_action_type_group SET status = -1 WHERE id = ?",punchActionTypeGroupId);
    }

    public List<JSONObject> getTypeBySchoolId(int schoolId) {
        List<JSONObject> result = new ArrayList<>();
        Map<Integer, JSONObject> map = new HashMap<>();
        List<Record> data = Db.find("SELECT g.id groupId, g.name groupName, t.id typeId, t.msg typeMsg, t.name typeName FROM qy_punch_action_type t, agent_school s, qy_punch_action_type_group g WHERE s.id = ? AND g.school_id = s.id AND t.group_id = g.id ",schoolId);

        for (Record record : data) {
            JSONObject groupTemp =map.get(record.getInt("groupId"));
            if(groupTemp  == null){
                groupTemp = new JSONObject();
                map.put(record.getInt("groupId"),groupTemp);
                result.add(groupTemp);
            }
            groupTemp.put("groupId",record.getInt("groupId"));
            groupTemp.put("groupName",record.getStr("groupName"));
            Object isNull = groupTemp.get("typeList");
            JSONArray typeList ;
            if(isNull == null){
                typeList = new JSONArray();
                JSONObject type = new JSONObject();
                type.put("typeId",record.getInt("typeId"));
                type.put("typeMsg",record.getStr("typeMsg"));
                type.put("typeName",record.getStr("typeName"));
                typeList.add(type);
                groupTemp.put("typeList",typeList);
            }else {
                typeList = groupTemp.getJSONArray("typeList");
                JSONObject type = new JSONObject();
                type.put("typeId",record.getInt("typeId"));
                type.put("typeMsg",record.getStr("typeMsg"));
                type.put("typeName",record.getStr("typeName"));
                typeList.add(type);
                groupTemp.put("typeList",typeList);
            }

        }
        return result;

    }
}
