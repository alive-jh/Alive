#sql("getJoinGrades")
SELECT
	g.id,
	g.class_grades_name,
	g.cover,
	g.summary 'desc',
	date_format(cs.start_time, '%Y-%m-%d') create_time, -- 用这个字段存储加班时间
	g.joinStatus,
	g.classOpenDate,
	g.grades_type
FROM
	class_grades g,
	class_course_schedule cs,
	class_grades_rela gr
WHERE
	cs.student_id = #para(stdId)
AND gr.class_student_id = cs.student_id
AND gr.class_grades_id = g.id
AND g.id = cs.class_grades_id
AND g.grades_type IN (
#for(t : types)
  #(for.index > 0?",":"")
  '#(t)'
#end
)
#(status == 0 ? "" : "AND gr.gradesStatus = ") #para(status)
#end


#sql("getJoinGrades2")
SELECT
  cg.id,
	cg.class_grades_name,
	cg.cover,
	cg.summary 'desc',
	cg.joinStatus,
	cg.classOpenDate,
	cg.grades_type,
	cg.grades_type classGradesType,
	ct.`name` teacherName,
	ct.avatar teacherAvatar,
	gr.type relaType,
	date_format(gr.create_time, '%Y-%m-%d') addClassGradesDate

FROM
	class_grades cg
LEFT JOIN class_teacher ct ON cg.teacher_id = ct.id
LEFT JOIN (SELECT * from class_grades_rela where class_student_id=#para(stdId) and class_grades_id = #para(gradesId)) gr ON gr.class_grades_id = cg.id
WHERE
	cg.id = #para(gradesId)

#end

#sql("getJoinGradesOld")
SELECT
	g.id classGradesId,
	g.class_grades_name classGradesName,
	g.cover,
	g.summary 'desc',
	IFNULL((select date_format(cs.start_time, '%Y-%m-%d') addClassGradesDate from class_course_schedule as cs where gr.class_student_id=cs.student_id and gr.class_grades_id=cs.class_grades_id order by id asc limit 1),date_format(gr.create_time, '%Y-%m-%d'))
 addClassGradesDate,
	g.joinStatus,
	g.classOpenDate,
	g.grades_type classGradesType,
	gr.type relaType
FROM
	class_grades_rela gr
LEFT JOIN class_grades g ON gr.class_grades_id = g.id
WHERE
	gr.class_student_id =  #para(stdId)

AND g.grades_type IN (
#for(t : types)
  #(for.index > 0?",":"")
  '#(t)'
#end
)
#(status == 0 ? "" : "AND gr.gradesStatus = ") #para(status)
#end

