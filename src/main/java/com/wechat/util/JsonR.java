package com.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonR {
    public static final Integer codes[] = {200, 201, 202, 304, 404, 500, 203, 204, 205, 206};

    public static final String msg[] = {"OK", "Parameters Lack", "Parameters Type Error", "Insufficient permissions",
            "Data Not Found", "Server Error", "参数无效", "帐号或密码错误", "未登录", "该帐号已被注册"};

    public static Map<Integer, String> status;

    static {

        if (status == null) {
            status = new HashMap<Integer, String>();
            for (int i = 0; i < codes.length; i++)
                status.put(codes[i], msg[i]);
        }

    }

    /**
     * 无参数成功返回
     *
     * @param controller
     */
    public static void success(Controller controller) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", status.get(200));
        controller.renderJson(map);
    }

    /**
     * 有参数时成功返回
     *
     * @param ob
     * @param controller
     * @param <E>
     */
    public static <E> void success(E ob, Controller controller) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        if (ob instanceof Record) {
            map.put("data", fastRecord((Record) ob));
        } else if (ob instanceof List) {
            map.put("data", fastList((List) ob));
        } else if (ob instanceof Page) {
            map.put("data", fastPage((Page) ob));
        } else {
            map.put("data", ob);
        }
        map.put("msg", status.get(200));
        String obJson = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        controller.renderJson(obJson);
    }


    /**
     * 错误返回
     *
     * @param code
     * @param controller
     */
    public static void error(Integer code, Controller controller) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", status.get(code));
        controller.renderJson(result);
    }

    /**
     * 带消息错误返回
     *
     * @param code
     * @param msg
     * @param controller
     */
    public static void error(Integer code, String msg, Controller controller) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        controller.renderJson(result);
    }

    private static Map fastRecord(Record ob) {
        Map<String, Object> map = ob.getColumns();
        return map;
    }

    public static List fastList(List list) {
        List<Object> json = new ArrayList<>();
        for (Object ob : list) {
            if (ob instanceof Record) {
                Map<String, Object> map = ((Record) ob).getColumns();
                json.add(map);
            } else {
                return list;
            }
        }
        return json;
    }

    public static Map fastPage(Page page) {
        List<Object> list = new ArrayList<>();
        Map<String, Object> json = new HashMap<>();
        for (Object ob : page.getList()) {
            Map<String, Object> map = ((Record) ob).getColumns();
            list.add(map);
        }
        json.put("list", list);
        json.put("pageNumber", page.getPageNumber());//页码
        json.put("pageSize", page.getPageSize());//单页大小
        json.put("totalPage", page.getTotalPage());//总页数
        json.put("totalRow", page.getTotalRow());//数据总数
        json.put("isFirstPage", page.isFirstPage());//是否第一页
        json.put("isLastPage", page.isLastPage());//是否最后一页

        //System.out.println(json);
        return json;
    }

    public static Map RecordToJson(Record record) {
        return fastRecord(record);
    }
}
