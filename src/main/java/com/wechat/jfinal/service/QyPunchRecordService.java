package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class QyPunchRecordService {

    public List<Record> getPunchListByGroupId(int groupId){
        List<Record> result = Db.find("SELECT record.id recordId, record.action msg, record.punch_time, record.punch_image, type.id typeId, type.name typeName, group1.id groupId, group1.name groupName FROM qy_punch_record record, qy_punch_action_type type, qy_punch_action_type_group group1 WHERE record.type = type.id AND type.group_id = group1.id AND group1.id = ? ORDER BY record.punch_time ",groupId);
        return result;
    }

    public List<Record> getListByGroupIdAndStudentId(int groupId, int studentId) {
        List<Record> result = Db.find("SELECT record.id recordId, record.action msg, record.punch_time, CONCAT('http://source.fandoutech.com.cn/epal/attendance/pic/',record.punch_image,'.jpg') punch_image, type.id typeId, type. NAME typeName, group1.id groupId, group1. NAME groupName FROM qy_punch_record record, qy_student_card s, qy_punch_action_type type, qy_punch_action_type_group group1 WHERE record.type = type.id AND type.group_id = group1.id AND group1.id = ? AND record.card_id = s.card_id AND s.student_id = ? AND s.status = 1 ORDER BY record.punch_time  ",groupId,studentId);
        return result;

    }

    public List<Record> getPunchListByTypeIdAndStudentId(int typeId, int studentId) {
        List<Record> result = Db.find("SELECT record.id recordId, record.action msg, record.punch_time, CONCAT( 'http://source.fandoutech.com.cn/epal/attendance/pic/', record.punch_image, '.jpg' ) punch_image, type.id typeId, type. NAME typeName FROM qy_punch_record record, qy_student_card s, qy_punch_action_type type WHERE record.type = type.id AND type.id = ? AND record.card_id = s.card_id AND s.student_id = ? AND s. STATUS = 1 ORDER BY record.punch_time   ",typeId,studentId);
        return result;
    }


}
