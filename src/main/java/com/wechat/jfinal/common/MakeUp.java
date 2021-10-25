package com.wechat.jfinal.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MakeUp {

    /**
     * 将对象里面的时间戳转换成标准字符串
     *
     * @param o
     * @return
     * @throws Exception
     */
    public static Object timestampToString(Object o) throws Exception {
        if (o == null)
            return null;
        if (o instanceof List) {
            JSONArray jsonArray = new JSONArray();
            Iterator iter = ((List) o).iterator();
            while (iter.hasNext()) {
                jsonArray.add(timestampToString(iter.next()));
            }
            return jsonArray;
        } else if (o instanceof Map) {
            JSONObject jsonObject = new JSONObject();
            if (((Map) o).keySet().size() == 10 && ((Map) o).keySet().contains("nanos")) {
                return replaceTimestamp(o);
            } else {
                for (Object key : ((Map) o).keySet()) {
                    Object temp = ((Map) o).get(key);
                    jsonObject.put(key.toString(), timestampToString(temp));
                }
            }

            return jsonObject;
        } else if (o instanceof Model){
            return replaceTimestampFormModel(o);
        }
        else {
            return replaceTimestamp(o);
        }
    }

    private static Object replaceTimestamp(Object o) {
        if (o instanceof String || o instanceof Integer || o instanceof Float || o instanceof Long || o instanceof Boolean)
            return o.toString();
        JSONObject j = JSONObject.fromObject(o);
        if (!j.isNullObject() && j.size() == 10 && j.get("nanos") != null) {
            Timestamp t = new Timestamp(j.getInt("year"), j.getInt("month"), j.getInt("date"), j.getInt("hours"), j.getInt("minutes"), j.getInt("seconds"), j.getInt("nanos"));
            return t.toString().substring(0, 16);
//            j.put(key, t.toString().substring(0, 16));
        }else
            return j;

//        for (Object key : j.keySet()) {
//            if (j.get(key).getClass().toString().equals("class net.sf.json.JSONObject")) {
//                JSONObject temp = (JSONObject) j.get(key);
//                if (!temp.isNullObject() && temp.size() == 10 && temp.get("nanos") != null) {
//                    Timestamp t = new Timestamp(temp.getInt("year"), temp.getInt("month"), temp.getInt("date"), temp.getInt("hours"), temp.getInt("minutes"), temp.getInt("seconds"), temp.getInt("nanos"));
//                    j.put(key, t.toString().substring(0, 16));
//                }
//            }
//        }
//        return j;
    }

    public static Object replaceTimestampFormModel(Object o) {
        JSONObject j = JSONObject.fromObject(o);
        for (Object key : j.keySet()) {
            if (j.get(key).getClass().toString().equals("class net.sf.json.JSONObject")) {
                JSONObject temp = (JSONObject) j.get(key);
                if (!temp.isNullObject() && temp.size() == 10 && temp.get("nanos") != null) {
                    Timestamp t = new Timestamp(temp.getInt("year"), temp.getInt("month"), temp.getInt("date"), temp.getInt("hours"), temp.getInt("minutes"), temp.getInt("seconds"), temp.getInt("nanos"));
                    j.put(key, t.toString().substring(0, 16));
                }else{
                    j.put(key, "");
                }
            }
        }
        return j;
    }

    public static JSONArray recordToJSONObject(List<Record> records) {
        JSONArray jsonArray = new JSONArray();
        for (Record rc : records) {
            jsonArray.add(rc.toJson());
        }
        return jsonArray;
    }


}
