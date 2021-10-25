#sql("getAudioInfoByClassRoomId")
SELECT a.class_room_id,a.material_file_id, b.id,b.audioid,b.cover,b.cn,b.exp,b.picrecog,b.mediainfo,b.src,b.user_id,b.src2,b.type, c.type as fileType,c.name FROM
 class_room_audio_rel a,audioinfo b,material_file c WHERE a.audio_id = b.id AND a.material_file_id = c.id
 AND a.class_room_id in (
 #for(x : classRoomIds)
 	#(for.index > 0 ? ",":"") #(x)
 #end
 ) GROUP BY a.id
#end

#sql("getAudioInfoByClassRoomIdOld")
SELECT a.class_room_id, b.*, c.type as fileType FROM
 class_room_audio_rel a,audioinfo b,material_file c WHERE a.audio_id = b.audioid and a.audio_id = c.audioinfo_id
 AND a.class_room_id in (
 #for(x : classRoomIds)
 	#(for.index > 0 ? ",":"") #(x)
 #end
 ) GROUP BY a.class_room_id,b.id
#end