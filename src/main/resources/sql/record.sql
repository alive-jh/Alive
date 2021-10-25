#sql("studyTimeRanking")
  SELECT
	student_id stdId,
  s.name,
	ROUND(IFNULL(SUM(used_time)/60,0),0) total
FROM
	class_course_record cr LEFT JOIN class_student s ON cr.student_id = s.id
WHERE
	cr.class_grades_id = #para(gradeId)
	AND cr.complete_time > #para(start)
GROUP BY
	cr.student_id
ORDER BY
	total DESC
#end