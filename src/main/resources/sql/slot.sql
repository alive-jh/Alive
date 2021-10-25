#sql("getSlots")
SELECT s.do_slot id, s.name, s.do_time time FROM class_slot s, class_student cs WHERE cs.id =  #para(0)  AND cs.epal_id = s.epal_id
#end