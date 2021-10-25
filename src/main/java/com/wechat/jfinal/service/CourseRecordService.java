package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wechat.jfinal.common.utils.CalculateUtil;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.model.ClassCourseRecord;

import java.util.*;

public class CourseRecordService {
    private static final ClassCourseRecord dao = new ClassCourseRecord().dao();

    public List<Record> getStudyTime(int stdId, Date start, Date end) {
        Kv cond = Kv.by("stdId", stdId).set("start", start).set("end", end);
        SqlPara sqlPara = Db.getSqlPara("course.getStudyTime", cond);
        return Db.find(sqlPara);
    }

    public List<Record> getStudyTime(int stdId, Date start) {
        return getStudyTime(stdId, start, null);
    }

    public List<Record> getStdStudyCount(int gradeId, Date start, Date end) {
        Kv cond = Kv.by("gradeId", gradeId).set("start", start).set("end", end);
        SqlPara sqlPara = Db.getSqlPara("course.getStdStudyCount", cond);
        return Db.find(sqlPara);
    }

    public List<Record> getStdStudyCount(int gradeId, Date start) {
        return getStdStudyCount(gradeId, start, null);
    }

    /**
     * @param data
     * @param type day、 week、 month
     */
    private Map<Object,Integer> groupByType(List<Record> data, String type, String objectType) {
        String dataCol = "";
        switch (objectType){
            case "std":
                dataCol = "used";
                break;
            case "grade":
                dataCol = "count";
                break;
        }

        if(data == null || data.size() == 0)
            return null;
        Map<Object,Integer> result = new HashMap<>();
        if ("day".equals(type)) {
            for (Record d : data) {
                result.put(ConvetUtil.date2Str(d.getDate("rawDate"),"MM-dd"), d.getInt(dataCol) == null ? 0 : d.getInt(dataCol));
            }
        } else {
            int used = 0;
            if ("week".equals(type)) {
                int temp = -1;
                for (int i = 0; i < data.size(); i++) {
                    Record d = data.get(i);
                    int weekOfYear = ConvetUtil.normalWeekOfYear(d.getDate("rawDate"));
                    if(temp == -1){
                        used +=  d.getInt(dataCol) == null ? 0 : d.getInt(dataCol);
                        temp = weekOfYear;
                    }else {
                        if (weekOfYear == temp) {
                            used =  d.getInt(dataCol) == null ? 0 : d.getInt(dataCol)  + used;
                        }else {
                            result.put(temp,used);
                            temp = weekOfYear;
                            used =  d.getInt(dataCol) == null ? 0 : d.getInt(dataCol);
                        }
                    }
                    if( i+1 == data.size()){
                        result.put(temp,used);
                    }
                }
            } else if ("month".equals(type)) {
                int temp = -1;
                for (int i = 0; i < data.size(); i++) {
                    Record d = data.get(i);
                    int monthOfYear = ConvetUtil.monthOfYear(d.getDate("rawDate")); //一月：0，二月 1，。。。。
                    if(temp == -1){
                        used +=  d.getInt(dataCol) == null ? 0 : d.getInt(dataCol);
                        temp = monthOfYear;
                    }else {
                        if (monthOfYear == temp) {
                            used =  d.getInt(dataCol) == null ? 0 : d.getInt(dataCol) + used;
                        }else {
                            result.put(temp,used);
                            temp = monthOfYear;
                            used =  d.getInt(dataCol) == null ? 0 : d.getInt(dataCol);
                        }
                    }
                    if( i+1 == data.size()){
                        result.put(temp,used);
                    }
                }
            }
        }
        return result;
    }

    /**
     *
     * @param end
     * @param length
     * @return
     */
    public void getDataForLineChart(List<String> xAxis,List<Integer> xData,int id,Date end, int length,String type,String objectType){
        Date start = CalculateUtil.getAfterDay(end,-length);
        List<Record> data = new ArrayList<>();
        Map<Object,Integer> raw = new HashMap<>();
        if(objectType.equals("std"))   {
            data = getStudyTime(id,start,end);
        }
        if(objectType.equals("grade")) {
            data = getStdStudyCount(id,start,end);
        }
        raw = groupByType(data,type,objectType);
        if(raw == null)raw = new HashMap<>();
        fillX(raw,end,length,type,xAxis,xData);
    }

    /**
     * 填充数据，使X轴坐标满上
     */
    public void fillX(Map<Object,Integer> raw, Date end, int length, String type, List<String> xAxis, List<Integer> xData){
        if(null != type && !"".equals(type)){
            if(type.equals("day")){
                //获得X坐标
                for (int i = length - 1; i >=0; i--) {
                    xAxis.add(ConvetUtil.date2Str(CalculateUtil.getAfterDay(end,-i),"MM-dd"));
                }
                //填充值
                for (String x : xAxis) {
                    if(raw.get(x)== null)
                        raw.put(x,0);
                }
                for (String x : xAxis) {
                    xData.add(raw.get(x));
                }
            }else if(type.equals("week")){
                //获得X坐标
                int temp = -1;
                String index = "";
                String pre= "";
                Map<String, Integer> indexToWeekOfYear = new HashMap<>();

                for (int i = length - 1; i >=0; i--) {
                    Date d = CalculateUtil.getAfterDay(end,-i);

                    int weekOfYear = ConvetUtil.normalWeekOfYear(d);
                    if(temp == -1){
                        pre =ConvetUtil.date2Str(d,"MM-dd");
                        index = pre+" ~ ";
                        temp = weekOfYear;
                    }else {
                        if (weekOfYear != temp) {
                            String x = index + pre;
                            xAxis.add(x);
                            indexToWeekOfYear.put(x,temp);
                            pre =ConvetUtil.date2Str(d,"MM-dd");
                            index = ConvetUtil.date2Str(d,"MM-dd")+" ~ ";
                            temp = weekOfYear;
                        }else {
                            pre = ConvetUtil.date2Str(d,"MM-dd");
                        }
                    }
                    if( i == 0){
                        String x = index + pre;
                        xAxis.add(x);
                        indexToWeekOfYear.put(x,temp);
                    }
                }
                //填充值
                Map<Object,Integer> result = new HashMap<>();
                for (String x : xAxis) {
                    if(!raw.containsKey(indexToWeekOfYear.get(x)))
                        result.put(x,0);
                    else
                        result.put(x,raw.get(indexToWeekOfYear.get(x)));
                }
                for (String x : xAxis) {
                    xData.add(result.get(x));
                }
            }else if(type.equals("month")){
                //获得X坐标
                int temp = -1;
                String index = "";
                String pre= "";
                Map<String, Integer> indexToMonthOfYear = new HashMap<>();

                for (int i = length - 1; i >=0; i--) {
                    Date d = CalculateUtil.getAfterDay(end,-i);

                    int monthOfYear = ConvetUtil.monthOfYear(d);
                    if(temp == -1){
                        pre = (monthOfYear+1)+"月";
                        temp = monthOfYear;
                    }else {
                        if (monthOfYear != temp) {
                            String x = pre;
                            xAxis.add(x);
                            indexToMonthOfYear.put(x,temp);
                            pre = (monthOfYear+1)+"月";
                            temp = monthOfYear;
                        }else {
                            pre =(monthOfYear+1)+"月";
                        }
                    }
                    if( i == 0){
                        String x = pre;
                        xAxis.add(x);
                        indexToMonthOfYear.put(x,temp);
                    }
                }
                //填充值
                Map<Object,Integer> result = new HashMap<>();
                for (String x : xAxis) {
                    if(!raw.containsKey(indexToMonthOfYear.get(x)))
                        result.put(x,0);
                    else
                        result.put(x,raw.get(indexToMonthOfYear.get(x)));
                }
                for (String x : xAxis) {
                    xData.add(result.get(x));
                }
            }
        }
    }
}
