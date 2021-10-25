package com.wechat.jfinal.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rt {

    public static JSONObject success(Object o) {

//        if(xx.isEmpty(o))
//            return success();
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", makeup(o));
        return result;
    }
    public static JSONObject success(Object o,String msg) {

//        if(xx.isEmpty(o))
//            return success();
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("msg", msg);
        result.put("data", makeup(o));
        return result;
    }

    public static JSONObject showMsg(String msg){
        JSONObject result = new JSONObject();
        result.put("code", 210);
        result.put("msg", msg);
        return result;
    }


    public static JSONObject success() {
        JSONObject result = new JSONObject();
        result.put("code", 204);
        result.put("msg", "success");
        return result;
    }
    public static JSONObject success(String msg) {
        JSONObject result = new JSONObject();
        result.put("code", 204);
        result.put("msg", msg);
        return result;
    }

    public static JSONObject unauthorized() {
        JSONObject result = new JSONObject();
        result.put("code", 401);
        result.put("msg", "unauthorized");
        return result;
    }

    public static JSONObject paraError() {
        JSONObject result = new JSONObject();
        result.put("code", 402);
        result.put("msg", "parameter error");
        return result;
    }
    public static JSONObject paraError(String msg) {
        JSONObject result = new JSONObject();
        result.put("code", 402);
        result.put("msg", msg);
        return result;
    }

    public static JSONObject forbidden() {
        JSONObject result = new JSONObject();
        result.put("code", 403);
        result.put("msg", "forbidden");
        return result;
    }

    public static JSONObject notFound() {
        JSONObject result = new JSONObject();
        result.put("code", 404);
        result.put("msg", "not found");
        return result;
    }

    public static JSONObject timeout() {
        JSONObject result = new JSONObject();
        result.put("code", 408);
        result.put("msg", "timeout");
        return result;
    }

    public static JSONObject sysError() {
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("msg", "服务器异常");
        return result;
    }

    public static Object makeup(Object o) {
        if (o == null)
            return null;
        if (o instanceof List) {
            JSONArray jsonArray = new JSONArray();
            Iterator iter = ((List) o).iterator();
            while (iter.hasNext()) {
                jsonArray.add(makeup(iter.next()));
            }
            return jsonArray;
        } else if (o instanceof Map) {
            JSONObject jsonObject = new JSONObject();
            for (Object key : ((Map) o).keySet()) {
                Object temp = ((Map) o).get(key);
                jsonObject.put(underlineToCamel(key.toString()), makeup(temp));
            }
            return jsonObject;
        } else if (o instanceof Model) {
            return makeupModel(((Model) o));
        } else if (o instanceof Record) {
            return makeupRecord(((Record) o));
        } else if (o instanceof Page) {
            return makeupPage(((Page) o));
        } else if (o instanceof java.util.Date) {
            return ((Date) o).getTime();
        } else if (o instanceof java.util.Calendar) {
            return ((Calendar) o).getTimeInMillis();
        } else if(o instanceof Float){
        	return o.toString();
        }else if (o instanceof Number) {
            return o;
        } else if (o instanceof String ) {
            return o.toString();
        } else {
            return o.toString();
        }
    }

    private static Object makeupPage(Page o) {
        JSONObject s = new JSONObject();
        s.put("isLastPage", o.isLastPage());
        s.put("isFirst", o.isFirstPage());
        s.put("pageSize", o.getPageSize());
        s.put("pageNumber",o.getPageNumber());
        s.put("totalRow", o.getTotalRow());
        s.put("totalPage", o.getTotalPage());
        List list = o.getList();
        Object e = makeup(list);
        s.put("list", e);
        return s;
    }


    private static Object makeupModel(Model o) {
        JSONObject jsonObject = new JSONObject();
        Set<Map.Entry<String, Object>> entrys = o._getAttrsEntrySet();
        for (Map.Entry<String, Object> entry : entrys) {
            jsonObject.put(underlineToCamel(entry.getKey()), makeup(entry.getValue()));
        }
        return jsonObject;
    }

    private static Object makeupRecord(Record o) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> kvs = o.getColumns();
        for (String s : kvs.keySet()) {
            jsonObject.put(underlineToCamel(s), makeup(kvs.get(s)));
        }
        return jsonObject;
    }

    /**
     * 将下划线去除，并将下划线后第一个字母大写
     */
    private static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }
    
}
