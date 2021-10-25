package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.ClassGrades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GradesService {
    /**
     * 所有的 dao 对象也放在 Service 中
     */
    private static final ClassGrades dao = new ClassGrades().dao();

    /**
     * @param stdId  学生ID
     * @param types  所取班级类型  1 虚拟班 2 电教班 3 basic班 可用 - 连接
     * @param status 学生-班级状态 1 当前 -1 已退 0 全部
     * @return
     */
    public List<ClassGrades> getJoinGradesNew(int stdId, String types, int status) {

        List<String> typeList = ConvetUtil.gradeTypes2Strs(types);
        if (xx.isEmpty(typeList))
            return new ArrayList<>();
        Kv cond = Kv.by("stdId", stdId).set("types", typeList).set("status", status);
        SqlPara sqlPara = Db.getSqlPara("grade.getJoinGrades", cond);

        return dao.find(sqlPara);
    }

    /**
     * @param stdId     学生ID
     * @param types     所取班级类型  1 虚拟班 2 电教班 3 basic班 可用 - 连接
     * @param isPresent 学生-班级状态 1 当前 -1 已退 0 全部
     * @return
     */
    @Deprecated
    public static List<Record> getJoinGrades(int stdId, String types, int isPresent) {

        Kv cond = Kv.by("stdId", stdId).set("types", ConvetUtil.gradeTypes2Strs(types)).set("status", isPresent);
        SqlPara sqlPara = Db.getSqlPara("grade.getJoinGradesOld", cond);

        return Db.find(sqlPara);
    }

    public static Date getOpenDate(int gId) {
        Date openData = ConvetUtil.str2Date(dao.findById(gId).getStr("classOpenDate"));
        return openData;
    }


    public Object getById(int id) {
        return dao.findById(id);
    }


    //获得班级类型的in 条件(String)（虚拟班、电教班、basic班）
    @Deprecated
    public static String inGradesTypeStr(String types) {
        String[] typesStr = types.split("-");
        String typesSql = " (";
        boolean isFirst = true;
        for (String t : typesStr) {
            if (!isFirst) {
                typesSql += ",";
            }
            isFirst = false;
            int type = Integer.parseInt(t);
            String gType = "";
            if (type == 1)
                gType = "'virtualClass'";
            if (type == 2)
                gType = "'eleClass'";
            if (type == 3)
                gType = "'basicClass'";
            typesSql = typesSql + gType;

        }
        typesSql += ")";
        return typesSql;
    }


    public Record getGradesAndTeacherInfo(int stdId, int isPresent, int classGradesId) {
        Kv cond = Kv.by("stdId", stdId).set("status", isPresent).set("gradesId", classGradesId);
        SqlPara sqlPara = Db.getSqlPara("grade.getJoinGrades2", cond);
        return Db.findFirst(sqlPara);
    }

}
