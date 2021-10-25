#sql("getByGradeIds")
SELECT * FROM class_course_schedule  WHERE student_id = #para(stdId) AND class_grades_id in (
#for(t : gradeIds)
  #(for.index > 0?",":"")
  #(t)
#end
)
#end