package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.DeviceSchedule;
import com.wechat.jfinal.model.HistorySchedules;

import java.text.ParseException;
import java.util.*;

public class DeviceScheduleService {
    private static final long INTERVAL_DAY = 86400000;
    private static final long INTERVAL_MONTH = 86400000*30;


    public List<Record> todaySchedule(int studentId) throws ParseException {
        if(studentId == 0)
            return new ArrayList<>();
        List<Record> result = new ArrayList<>();
        ClassStudent student = ClassStudent.dao.findById(studentId);
        if(xx.isEmpty(student))
            return new ArrayList<>();
        Date start = ConvetUtil.str2Date(ConvetUtil.date2Str(new Date())+" 00:00:01","yyyy-MM-dd HH:mm:ss");
        Date end = ConvetUtil.str2Date(ConvetUtil.date2Str(new Date())+" 23:59:59","yyyy-MM-dd HH:mm:ss");
        Kv cond = Kv.by("epalId",student.getEpalId()).set("start",start.getTime()).set("end",end.getTime());
        List<Record> schedules = Db.find(Db.getSqlPara("deviceSchedule.todaySchedule",cond));
        List<HistorySchedules> historySchedules = HistorySchedules.dao.find("SELECT * FROM history_schedules WHERE epalId = ? AND exe_time > ? and exe_time < ?", student.getEpalId(),start.getTime(),end.getTime());
        Map<Integer,HistorySchedules> historySchedulesMap = new HashMap<>();
        for (HistorySchedules h : historySchedules) {
            historySchedulesMap.put(Integer.parseInt(h.getScheduleId()),h);
        }
        Set<Integer> keyset = historySchedulesMap.keySet();
        Date d = new Date();
        for (Record schedule : schedules) {
            if(isToday( Long.parseLong(schedule.getStr("planTime")), Long.parseLong(schedule.getStr("period")))) {
                schedule.remove("period");
                schedule.set("planTime", planTime(schedule.getStr("planTime")));
                if(keyset.contains(schedule.getInt("scheduleId"))){
                    schedule.set("isStart",1);
                    schedule.set("startTime",planTime(historySchedulesMap.get(schedule.getInt("scheduleId")).getExeTime()));
                }
                d.setTime(Long.parseLong(schedule.getStr("creatTime")));
                schedule.set("creatTime",ConvetUtil.date2Str(d,"yyyy-MM-dd HH:mm:ss"));
                schedule.set("description",schedule.getStr("description"));
                result.add(schedule);
            }
        }
        return result;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     * */
    private boolean isToday(DeviceSchedule schedule){
        if(schedule == null)
            return false;
        long startTime = Long.parseLong(schedule.getDoTime());
        long period = Long.parseLong(schedule.getPeriod());
        return isToday(startTime,period);
    }
    /**
     * ??????????????????????????????????????????????????????????????????
     * */
    private boolean isToday( long startTime, long period ){
        boolean isToday = false;
        startTime += 28800000;//????????????8?????????
        long curTime = System.currentTimeMillis() + 28800000;//????????????8?????????
        if (period == INTERVAL_MONTH) {//????????????
            Calendar cal = Calendar.getInstance();

            //????????????
            cal.setTimeInMillis(startTime);
            int startMonth = cal.get(Calendar.MONTH) + 1;//??????????????????
            int startDay = cal.get(Calendar.DAY_OF_MONTH);//?????????????????????

            //????????????
            cal.setTimeInMillis(curTime);
            int curMonth = cal.get(Calendar.MONTH) + 1;//?????????
            int curDay = cal.get(Calendar.DAY_OF_MONTH);//?????????

            isToday = (startMonth <= curMonth && startDay == curDay);

        } else if (period > 0){//???n?????????
            int startDay = (int) (startTime / INTERVAL_DAY);
            int curDay = (int) (curTime / INTERVAL_DAY);
            int interval = curDay - startDay;
            isToday = interval >= 0 && interval % (period / INTERVAL_DAY) == 0;

        } else if (period == 0){//??????????????????
            int startDay = (int) (startTime / INTERVAL_DAY);
            int curDay = (int) (curTime / INTERVAL_DAY);
            isToday = startDay == curDay;
        }
        return isToday;
    }

    public String planTime(DeviceSchedule deviceSchedule) {
        Date d = new Date(Long.parseLong(deviceSchedule.getDoTime()));
        return d.toString();
    }
    public String planTime(String planTime) {
        Date d = new Date(Long.parseLong(planTime));
        return ConvetUtil.date2Str(d,"HH:mm:ss");
    }
    public String planTime(Long planTime) {
        Date d = new Date(planTime);
        return ConvetUtil.date2Str(d,"HH:mm:ss");
    }
}
