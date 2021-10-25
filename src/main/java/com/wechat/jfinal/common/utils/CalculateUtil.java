package com.wechat.jfinal.common.utils;
//package com.wechat.jfinal.common.utils; com.wechat.jfinal.common.utils

import com.jfinal.plugin.activerecord.Record;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 生成随机字符串
 * @author zlisten
 */
public class CalculateUtil {

    //前面加上时间戳，去重
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        Long timeMillis = System.currentTimeMillis();
        String timeStr = String.valueOf(timeMillis);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - timeStr.length(); i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return timeStr + sb.toString();
    }

    //inputStream转outputStream
    public static ByteArrayOutputStream parse(InputStream in) throws Exception
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    //outputStream转inputStream
    public static ByteArrayInputStream parse(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos;
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }





    /**
     * 获取当前时间的前一天时间
     * @param cl
     * @return
     */
    public static Calendar getBeforeDay(Calendar cl){
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day-1);
        return cl;
    }

    /**
     * 获取当前时间的后一天时间
     * @param cl
     * @return
     */
    public static Calendar getAfterDay(Calendar cl , int days){
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day+days);
        return cl;
    }
    /**
     * 获取当前时间的后一天时间
     * @param date
     * @return
     */
    public static Date getAfterDay(Date date , int days){

        if(date == null || days == 0)
            return date;
        //使用set方法直接设置时间值
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        Calendar after = getAfterDay(cl,days);
        return after.getTime();
    }

    public static String sqlInRecordsColumn(String sqlHead, List<Record> records, String column){
        if(records.size() == 0){
            sqlHead += ("-9199");
            return sqlHead;
        }
        int flag = 0;
        for (Record c : records) {
            if(flag == 0 ) {
                sqlHead += c.getInt(column);
                flag++;
            }
            else {
                sqlHead += " , " +  c.getInt(column);
            }
        }
        if(null == records || records.size() == 0)
            sqlHead += 535462548;
        return sqlHead;
    }

    public static String sqlInRecordsColumnStr(String sqlHead, List<Record> records, String column){
        int flag = 0;
        if(records.size() == 0){
            sqlHead += ("'——__+='");
            return sqlHead;
        }
        for (Record c : records) {
            if(flag == 0 ) {
                sqlHead += ("'" +c.getStr(column)+"'");
                flag++;
            }
            else {
                sqlHead +=( " , " + "'" +c.getStr(column)+"'");
            }
        }
        if(null == records || records.size() == 0)
            sqlHead += ("'" +"_=mz8wqs"+"'");
        return sqlHead;
    }

    public static int howDaysBetween(Date start, Date end){
        return  (int)((end.getTime() - start.getTime()) / ((1000 * 60 * 60 *24) + 0.5));
    }
}
