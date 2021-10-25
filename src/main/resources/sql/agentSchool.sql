#sql("getSchoolByStudentIds")
SELECT
s.*,
std.student_id student_id
FROM
agent_school s,
school_teacher_rela rela,
qy_class_to_student std,
qy_class c
WHERE
std.student_id in(
#for(t : studentIds)
  #(for.index > 0?",":"")
  #(t)
#end
)
AND
std.class_id = c.id
AND
c.member_id = rela.member_id
AND
rela.school_id = s.id
#end