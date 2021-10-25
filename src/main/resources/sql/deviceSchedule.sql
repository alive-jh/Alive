#sql("todaySchedule")
SELECT
ds.period,
ds.id scheduleId,
ds.event_cn scheduleName,
ds.do_time  planTime,
if(ifnull(tt.id,0) > 0, 1,0) isStart,
ds.do_time creatTime,
ds.description,
ds.content
FROM
device_schedule ds LEFT JOIN history_schedules tt
ON ds.id = tt.scheduleId AND tt.exe_time > #para(start) AND tt.exe_time < #para(epalId)
WHERE
ds.epal_id = #para(epalId)
AND
ds.state = 1
#end