package com.wechat.jfinal.common.utils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import org.apache.poi.ss.formula.functions.T;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConvetUtil {

    public static List<String>  gradeTypes2Strs(String types){
        String[] intType = types.split("-");
        List<Integer> ints = new ArrayList<>();
        for (String i : intType) {
            ints.add(Integer.parseInt(i));
        }
        return gradeTypes2Strs(ints);
    }

    public static List<String> gradeTypes2Strs(List<Integer> types){
        List<String> result = new ArrayList<>();
        for (Integer type : types) {
            result.add(gradeType2Str(type));
        }
        return result;
    }

    public static String gradeType2Str(int type){
        String result;
        switch (type){
            case 1:
                result = "virtualClass";
                break;
            case 2:
                result = "eleClass";
                break;
            case 3:
                result = "basicClass";
                break;
            default:
                result = "";
        }
        return result;
    }

    //String 转 date
    //格式 yyyy-MM-dd
    public static Date str2Date(String dateStr)  {
        return str2Date(dateStr,"yyyy-MM-dd");
    }

    public static String date2Str(Date date, String format){
        if(null == date || "".equals(date))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        return str;
    }

    public static String dayOfWeekStr(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return dayOfWeek(cl.get(Calendar.DAY_OF_WEEK));
    }

    public static int dayOfWeek(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.DAY_OF_WEEK);
    }
    public static int weekOfYear(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.WEEK_OF_YEAR);
    }

    public static int monthOfYear(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.MONTH);
    }
    public static int dayOfMonth(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.DAY_OF_MONTH);
    }

    public static String dayOfWeek(int index){
        String name = "null";
        switch (index) {
            case 1:
                name = "星期日";
                break;
            case 2:
                name = "星期一";
                break;
            case 3:
                name = "星期二";
                break;
            case 4:
                name = "星期三";
                break;
            case 5:
                name = "星期四";
                break;
            case 6:
                name = "星期五";
                break;
            case 7:
                name = "星期六";
                break;
        }
        return  name;
    }

    public static int normalWeekOfYear(Date d){
        int result = weekOfYear(d);
        if(dayOfWeek(d) == 1)
            return result-1;
        return result;
    }

    public static Date getFirstDayOfMonth(int year,int monthIndex){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, monthIndex-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);

        return cal.getTime();
    }

    public static int getYear(Date d){
        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        return cl.get(Calendar.YEAR);
    }

    //格式 yyyy-MM-dd
    public static String date2Str(Date date) throws ParseException {
        if(null == date || "".equals(date))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String str = sdf.format(date);

        return str;
    }
    public static List<Object> records2List(List<Record> records,String columsName){
        List<Object> result = new ArrayList<>();
        for (Record record : records) {
            result.add(record.get(columsName));
        }
        return result;
    }
    public static List<Integer> records2IntList(List<Record> records,String columsName){
    	
    	if(records==null){
    		return null;
    	}
        List<Integer> result = new ArrayList<>();
        for (Record record : records) {
            result.add(record.getInt(columsName));
        }
        return result;
    }
    public static List<Integer> models2IntList(List<? extends Model> models, String columsName){
        List<Integer> result = new ArrayList<>();
        for (Model model: models) {
            result.add(model.getInt(columsName));
        }
        return result;
    }

    public static Map<Integer,List<Record>> groupRecord2IntMap(List<Record> records, String columsName) {
        Map<Integer,List<Record>> result = new HashMap<>();
        for (Record record : records) {
            List<Record> temp = result.get(record.get(columsName));
            if(temp == null){
                temp = new ArrayList<>();
                result.put(record.getInt(columsName),temp);
            }
            temp.add(record);
        }
        return result;
    }

    public static Map<Integer,Record> listRecordToIntMap(List<Record> records,String columsName){
        Map<Integer,Record> result = new HashMap<>();
        for (Record record : records) {
            result.put(record.getInt(columsName),record);
        }
        return result;
    }

    public static int DayOfWeek2ChinaDayOfWeek(int dayOfWeek){
        int i;
        switch (dayOfWeek){
            case 0:
                i = 7;
                break;
            case 6:
                i=7;
                break;
            default:
                i=dayOfWeek+1;
        }
        return i;
    }

    public static Date str2Date(String dateStr, String dateFormat) {
        if(null == dateStr || "".equals(dateStr))
            return null;
        DateFormat fmt =new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
