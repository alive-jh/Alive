#sql("getScheduleBySection")
SELECT
    ifnull(c.live_dodate,"") as live_dodate,
    c.take_picture,
    c.live_mode,
    c.b_live,
    c.take_time,
    ifnull((SELECT easemob_group_id FROM `group_chat` WHERE class_grade_id = c.class_grades_id limit 1),"") as easemob_group_id,
    (select account from v_teacher_account where id =  r.teacher_id limit 1) as teacher_account,
	c.id classCourseId,
	r.class_name roomName,
	c.cover,
	c.do_slot doSlot,
	c.class_room_id roomId,
	cc.complete_time completeTime,
	cc.score,
	IFNULL(
		(
			SELECT
				count
			FROM
				v_course_study_count
			WHERE
				classCourseRecord_id = c.id
		),
		1
	) studyCount,
	c.do_day doDay,
	c.class_grades_id,
	if(SUM(csn.total_time)=0,300,SUM(csn.total_time)) courseTotalTime,
	IFNULL((SELECT id FROM lesson_script_reply WHERE class_course_id = c.id AND student_id = #para(studentId) LIMIT 1),0) AS hasReply
FROM
	class_course c
LEFT JOIN (
	SELECT
		a.id classCourseId,
		b.score,
		b.complete_time
	FROM
		class_course a
	LEFT JOIN class_course_record b ON a.id = b.class_course_id
	WHERE
		a.class_grades_id = #para(gradeId)
	AND a.do_day >= #para(doDayStart)
	AND a.do_day < #para(doDayEnd)
	AND b.student_id = #para(studentId)
) cc ON cc.classCourseId = c.id,
 class_room r,
	class_script_normal csn
WHERE
	c.class_grades_id = #para(gradeId)
AND c.do_day >= #para(doDayStart)
AND c.do_day < #para(doDayEnd)
AND r.id = c.class_room_id
AND r.id = csn.class_room_id
GROUP BY
c.id,
r.id
#end



#sql("getSchedules")
SELECT
    ifnull(c.live_dodate,"") as live_dodate,
    c.take_picture,
    c.live_mode,
    c.b_live,
    c.take_time,
    ifnull((SELECT easemob_group_id FROM `group_chat` WHERE class_grade_id = c.class_grades_id limit 1),"") as easemob_group_id,
    (select account from v_teacher_account where id =  r.teacher_id limit 1) as teacher_account,
	c.id classCourseId,
	r.class_name roomName,
	r.cover,
	c.do_slot doSlot,
	c.class_room_id roomId,
	cc.complete_time completeTime,
	ifnull(cc.complete,0) isComplete,
	cc.score,
	IFNULL(
		(
			SELECT
				count
			FROM
				v_course_study_count
			WHERE
				classCourseRecord_id = c.id
		),
		1
	) studyCount,
	c.do_day doDay,
	c.class_grades_id,
	if(SUM(csn.total_time)=0,300,SUM(csn.total_time)) courseTotalTime,
	IFNULL((SELECT id FROM lesson_script_reply WHERE class_course_id = c.id AND student_id = #para(studentId) LIMIT 1),0) AS hasReply
FROM
	class_course c
LEFT JOIN (
	SELECT
		a.id classCourseId,
		b.score,
		b.complete_time,
		b.complete
	FROM
		class_course a
	LEFT JOIN class_course_record b ON a.id = b.class_course_id
	WHERE
		a.class_grades_id in (
		#for(t : gradeIds)
  #(for.index > 0?",":"")
  #(t)
#end
		)
	AND b.student_id = #para(studentId)
) cc ON cc.classCourseId = c.id,
 class_room r,
	class_script_normal csn
WHERE
	c.class_grades_id in  (
	#for(t : gradeIds)
  #(for.index > 0?",":"")
  #(t)
#end
	)
AND r.id = c.class_room_id
AND r.id = csn.class_room_id
GROUP BY
c.id,
r.id
#end

#sql("getUnionStudentList")
SELECT a.*,IFNULL(b.`status`,1) AS rfid_status FROM (
SELECT
	s.*
FROM
	class_student s
LEFT JOIN device_relation dr ON dr.epal_id = s.epal_id
WHERE
	dr.friend_id = #para(account)
AND dr.isbind = 1
AND dr.id IS NOT NULL AND s.status = 1 
UNION
	SELECT
		s.*
	FROM
		class_student s,
		member_student_rela rela
	WHERE
		rela.member_id = #para(memberId)
	AND rela.student_id = s.id AND s.status = 1 
	UNION
	SELECT
s.*
FROM
class_student s,
qy_student_card c
WHERE
c.contacts = #para(account) AND s.status = 1 
AND
s.id = c.student_id
UNION
	SELECT * FROM class_student where member_id = #para(memberId) AND status = 1
) a LEFT JOIN public_room_fid_to_student b ON a.id = b.student_id

#end

#sql("getUnionStudentListWithDevice")
SELECT
	s.*,d.epal_pwd
FROM
	class_student s
LEFT JOIN device_relation dr ON dr.epal_id = s.epal_id
LEFT JOIN device d on dr.epal_id = d.epal_id
WHERE
	dr.friend_id = #para(account)
AND dr.isbind = 1
AND dr.id IS NOT NULL
UNION
	SELECT
		s.*,d.epal_pwd
	FROM
		class_student s,
		member_student_rela rela,
		device d
	WHERE
		rela.member_id = #para(memberId)
	AND rela.student_id = s.id AND s.epal_id = d.epal_id
	UNION
	SELECT
s.*,d.epal_pwd
FROM
class_student s,
qy_student_card c,
device d
WHERE
c.contacts = #para(account)
AND
s.id = c.student_id AND s.epal_id = d.epal_id
#end