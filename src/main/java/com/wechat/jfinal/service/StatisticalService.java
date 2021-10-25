package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.common.utils.CalculateUtil;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;

import java.util.*;

public class StatisticalService {

    static CourseRecordService courseRecordService = new CourseRecordService();

    public  Map<String, Object> getDataForLineChart(String dateType,int id, String objectType){

        Map<String, Object> result = new HashMap<>();

            List<String> xAxis = new ArrayList<>();
            List<Integer> xData = new ArrayList<>();
            Date now = new Date();
            int length;
            if(id == 0){
                return null;
            }
            if("day".equals(dateType)){
                //获得最近60天学习情况
                length = 30;
                courseRecordService.getDataForLineChart(xAxis,xData,id,now,length,dateType,objectType);
            }else if("week".equals(dateType)){
                //包含今周，再往前取11周
                int dayOfWeek = ConvetUtil.dayOfWeek(now)-1 ;
                length = 7*11+(dayOfWeek == 0 ? 7:dayOfWeek);
                courseRecordService.getDataForLineChart(xAxis,xData,id,now,length,dateType,objectType);
            }else if("month".equals(dateType)){
                //包含今月，再往前取n个月 先写死11
                int monthOfYear = ConvetUtil.monthOfYear(now)+1;
                int year = ConvetUtil.getYear(now);
                if(monthOfYear < 12){
                    year--;
                }
                int monthIndex = monthOfYear%12 + 1;

                Date firstDay = ConvetUtil.getFirstDayOfMonth(year,monthIndex);

                length = CalculateUtil.howDaysBetween(firstDay,now);
                courseRecordService.getDataForLineChart(xAxis,xData,id,now,length,dateType,objectType);
            }

            result.put("_xAxis",xAxis);
            result.put("_xData",xData);
       return result;
    }

    public List<Record> studyTimeRanking(int gradeId){

        if(xx.isEmpty(gradeId))
            return new ArrayList<>();
        Kv cond = Kv.by("gradeId", gradeId).set("start",ConvetUtil.date2Str(CalculateUtil.getAfterDay(new Date(),-7),"yyyy-MM-dd")+" 00:00:01");
        SqlPara sqlPara = Db.getSqlPara("record.studyTimeRanking", cond);

        List<Record> data = Db.find(sqlPara);
        return data;
    }
}
