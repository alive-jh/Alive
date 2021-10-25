-- 对应的表包含：class_course、class_course_record
#sql("getStudyTime")
SELECT round(sum(used_time)/60) used, date_format(complete_time, '%Y-%m-%d') date ,complete_time rawDate FROM class_course_record WHERE student_id =  #para(stdId) AND complete_time > #para(start)
#if(end != null)
    AND complete_time < #para(end)
#end
 GROUP BY date ORDER BY complete_time ASC
#end


#sql("getStdStudyCount")
SELECT
	date_format(complete_time, '%Y-%m-%d') date,
	COUNT(student_id) count,
	complete_time rawDate
FROM
	class_course_record
WHERE
	class_grades_id = #para(gradeId)
AND complete_time > #para(start)
#if(end != null)
    AND complete_time < #para(end)
#end
GROUP BY
	date
ORDER BY
	complete_time ASC
#end

#sql("getByGradeIdsAndDodayIds")
SELECT
	*
FROM
	class_course
WHERE
	class_grades_id IN (
#for(t : gradeIds)
  #(for.index > 0?",":"")
  #(t)
#end
)
AND do_day IN (
#for(t : dadays)
  #(for.index > 0?",":"")
  #(t)
#end
)
AND
do_slot = 300
#end

#sql("getNextDodayAllCourse")
SELECT
c.*,r.class_name name
FROM
class_course c ,
(
SELECT
c.do_day,
c.class_grades_id
FROM
class_course c
WHERE
c.class_grades_id = #para(gradeId)
AND
c.do_day > #para(doday)
ORDER BY
c.do_day ASC
LIMIT 1
) a,
class_room r
WHERE c.do_day = a.do_day
AND
c.class_grades_id = a.class_grades_id
AND r.id = c.class_room_id
#end

#sql("getCourseDetailed")
SELECT
    ifnull(c.live_dodate,"") as live_dodate,
    c.take_picture,
    c.live_mode,
    c.b_live,
    c.take_time,
    ifnull((SELECT easemob_group_id FROM `group_chat` WHERE class_grade_id = c.class_grades_id limit 1),"") as easemob_group_id,
    (select account from v_teacher_account where id =  g.teacher_id limit 1) as teacher_account,
	c.class_grades_id ClassGradesId,
	g.class_grades_name classGradesName,
	c.id,
	c.cover,  --  2019.5.22
	r.class_name className,
	c.do_slot planTime,
	c.class_room_id roomId,
IFNULL(cr.complete,0) isComplete,
DATE_FORMAT(cr.complete_time,'%Y%m%d-%H:%i:%S') completeTime,
IFNULL((SELECT SUM(total_time) FROM class_script_normal WHERE class_room_id = r.id ),0) AS classLen,
IFNULL((SELECT id FROM lesson_script_reply WHERE class_course_id = c.id AND student_id = #para(studentId) LIMIT 1),0) AS hasReply
FROM
	class_course c LEFT JOIN class_course_record cr ON cr.class_grades_id = c.class_grades_id AND cr.class_course_id = c.id AND
cr.student_id = #para(studentId),
	class_room r,
	class_grades g
WHERE
	c.id IN (
#for(t : ids)
  #(for.index > 0?",":"")
  #(t)
#end
)
AND r.id = c.class_room_id
AND g.id = c.class_grades_id ORDER BY c.id DESC,cr.complete_time DESC

#end

#sql("courseIndex")
SELECT
COUNT(b.id) now,
(SELECT count(c.id) FROM class_course c WHERE c.class_grades_id = b.class_grades_id AND c.do_slot = 300) total
FROM
class_course a,
class_course b
WHERE
a.id  = #para(courseId)
and
b.class_grades_id = a.class_grades_id
and
b.do_day <= a.do_day
AND
b.do_slot = 300
#end

#sql("courseStudyTimes")
SELECT cr.class_course_id id, COUNT(cr.id) studied FROM class_course_record cr WHERE cr.class_room_id in(
#for(t : ids)
  #(for.index > 0?",":"")
  #(t)
#end
)
#end