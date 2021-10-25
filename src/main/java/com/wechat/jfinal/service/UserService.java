package com.wechat.jfinal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.Device;
import com.wechat.jfinal.model.DeviceRelation;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.model.Member;
import com.wechat.util.MD5UTIL;

public class UserService {

    /**
     * 试听课程注册接口
     *
     * @param mobile
     * @param _memberId
     * @return
     */
    public HashMap<String, Object> registerByClassTrial(String mobile, Integer memberId) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        Memberaccount memberAccount = Memberaccount.dao
                .findFirst("select * from memberaccount where account = ? and (type <=2 or type = 5)", mobile);

        if (memberAccount == null) {

            memberAccount = new Memberaccount();
            memberAccount.setAccount(mobile);
            memberAccount.setPassword(MD5UTIL.encrypt("123456"));
            memberAccount.setMemberid(memberId);
            memberAccount.setStatus(5);
            memberAccount.setType(5);
            memberAccount.save();

            Db.update("UPDATE member SET mobile=? WHERE id = ?", mobile, memberId);

        }

        StringBuffer studentSql = new StringBuffer(
                "SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.sort_id,");
        studentSql.append("IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,");
        studentSql.append(
                "a.member_id,a.degree_of_difficulty,a.avatar,a.`status`,c.epal_pwd,c.nickname FROM class_student");
        studentSql.append(
                " a,device_relation b,device c WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id ");
        studentSql.append("= ? and b.isbind = 1 UNION SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.");
        studentSql.append(
                "sort_id,IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,a.member_id,");
        studentSql.append(
                "a.degree_of_difficulty,a.avatar,a.`status`,b.epal_pwd,b.nickname FROM `class_student` a,device b WHERE ");
        studentSql.append("a.epal_id = b.epal_id AND a.member_id = ?");

        ClassStudent classStudent = ClassStudent.dao.findFirst(studentSql.toString(), mobile, memberId);

        if (classStudent == null) {

            Device device = new Device();
            device.setDeviceNo("defaultdeviceno" + memberId);
            device.setEpalId("multi" + memberId);
            device.setSn("defaultsn" + memberId);
            device.setEpalPwd("multi" + memberId);
            device.setNickname("multi" + memberId);
            device.setDeviceType("cr");
            device.setRemark("");
            device.setDeviceUsedType("");
            device.setStatus(1);
            device.save();

            classStudent = new ClassStudent();
            classStudent.setName(mobile);
            classStudent.setEpalId(device.getEpalId());
            classStudent.setStudentType(1);
            classStudent.setMemberId(memberId);
            classStudent.save();

            DeviceRelation deviceRelation = new DeviceRelation();
            deviceRelation.setEpalId(device.getEpalId());
            deviceRelation.setFriendId(mobile);
            deviceRelation.setFriendName(mobile);
            deviceRelation.setRole("guardian");
            deviceRelation.setIsbind(1);
            deviceRelation.save();

        }

        result.put("studentId", classStudent.getId());

        return result;
        /*
         * HashMap<String, Object> result = new HashMap<String, Object>();
         *
         * Integer memberId = _memberId;
         *
         * Member member =
         * Member.dao.findFirst("SELECT * FROM member where id = ?",memberId);
         *
         * //设置用户类型为5表示是care用户 member.setType(5).update();
         *
         *
         * if(memberAccount==null){ memberAccount = new Memberaccount();
         * memberAccount.setAccount(mobile);
         * memberAccount.setPassword(MD5UTIL.encrypt("123456"));
         * memberAccount.setMemberid(memberId); memberAccount.setStatus(0);
         * memberAccount.setType(5); memberAccount.save(); }else{
         * //如果手机号已经注册，则使用已注册帐号的memberId memberId = memberAccount.getMemberid();
         * }
         *
         * StringBuffer studentSql =new
         * StringBuffer("SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.sort_id,"
         * ); studentSql.
         * append("IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,"
         * ); studentSql.
         * append("a.member_id,a.degree_of_difficulty,a.avatar,a.`status`,c.epal_pwd,c.nickname FROM class_student"
         * ); studentSql.
         * append(" a,device_relation b,device c WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id "
         * ); studentSql.
         * append("= ? and b.isbind = 1 UNION SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a."
         * ); studentSql.
         * append("sort_id,IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,a.member_id,"
         * ); studentSql.
         * append("a.degree_of_difficulty,a.avatar,a.`status`,b.epal_pwd,b.nickname FROM `class_student` a,device b WHERE "
         * ); studentSql.append("a.epal_id = b.epal_id AND a.member_id = ?");
         *
         * ClassStudent classStudent =
         * ClassStudent.dao.findFirst(studentSql.toString(),mobile,memberId);
         *
         * if(classStudent==null){
         *
         * Device device = new Device();
         * device.setDeviceNo("defaultdeviceno"+memberId);
         * device.setEpalId("multi"+memberId);
         * device.setSn("defaultsn"+memberId);
         * device.setEpalPwd("multi"+memberId);
         * device.setNickname("defaultdeviceno"+memberId);
         * device.setDeviceType("cr"); device.setRemark("");
         * device.setDeviceUsedType(""); device.setStatus(1); device.save();
         *
         * classStudent = new ClassStudent();
         * classStudent.setName(member.getNickname());
         * classStudent.setEpalId(device.getEpalId());
         * classStudent.setStudentType(1); classStudent.setMemberId(memberId);
         * classStudent.save();
         *
         *
         * }
         *
         * result.put("memberId", memberId); result.put("studentId",
         * classStudent.getId());
         *
         * return result;
         */
    }

    public int createDefaultStudent(Integer memberId, String account) {

        ClassStudent classStudent = new ClassStudent();
        classStudent.setName("嘀嗒迷你");
        classStudent.setStudentType(1);
        classStudent.setSortId(2);
        classStudent.setIntegral(0);
        classStudent.setContribution(0);
        classStudent.setRemark("");
        classStudent.setMemberId(memberId);
        classStudent.save();

        int studentId = classStudent.getId();

        classStudent.setEpalId("multi" + studentId).update();

        Device device = new Device();
        device.setDeviceNo("defaultdeviceno" + studentId);
        device.setEpalId("multi" + studentId);
        device.setSn("defaultsn" + studentId);
        device.setEpalPwd("multi" + studentId);
        device.setNickname("multi" + studentId);
        device.setDeviceType("oo");
        device.setRemark("");
        device.setDeviceUsedType("");
        device.setStatus(1);
        device.save();

        DeviceRelation deviceRelation = new DeviceRelation();
        deviceRelation.setEpalId(device.getEpalId());
        deviceRelation.setFriendId(account);
        deviceRelation.setFriendName(account);
        deviceRelation.setRole("guardian");
        deviceRelation.setIsbind(1);
        deviceRelation.save();

        return 1;
    }

    public int createShaoNianPaiStudent(Integer memberId, String account) {

        Member member = Member.dao.findById(memberId);

        ClassStudent classStudent = new ClassStudent();
        classStudent.setName(member.getNickname());
        classStudent.setStudentType(1);
        classStudent.setSortId(2);
        classStudent.setContribution(0);
        classStudent.setIntegral(0);
        classStudent.setMemberId(memberId);
        classStudent.setRemark("");

        classStudent.save();

        int studentId = classStudent.getId();

        classStudent.setEpalId("multi" + studentId).update();

        Device device = new Device();
        device.setEpalId("multi" + studentId);
        device.setDeviceNo("defaultdeviceno" + studentId);
        device.setEpalPwd("multi" + studentId);
        device.setSn("defaultsn" + studentId);
        device.setNickname("multi" + studentId);
        device.setDeviceType("oo");
        device.setDeviceUsedType("");
        device.setStatus(1);
        device.setRemark("");
        device.save();

        DeviceRelation deviceRelation = new DeviceRelation();
        deviceRelation.setFriendName(account);
        deviceRelation.setEpalId(device.getEpalId());
        deviceRelation.setRole("guardian");
        deviceRelation.setIsbind(1);
        deviceRelation.setFriendId(account);
        deviceRelation.save();

        return 1;
    }

    public HashMap<String, Object> registerByClassTrial2(String mobile) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        Memberaccount memberAccount = Memberaccount.dao
                .findFirst("select * from memberaccount where account = ? and (type <=2 or type = 5)", mobile);

        Member member = Member.dao.findFirst("select * from member where mobile = ?", mobile);

        if (memberAccount == null) {

            if (member == null) {
                member = new Member();
                member.setType(2);
                member.setMobile(mobile);
                member.save();
            }

            memberAccount = new Memberaccount();
            memberAccount.setAccount(mobile);
            memberAccount.setPassword(MD5UTIL.encrypt("123456"));
            memberAccount.setMemberid(member.getId());
            memberAccount.setStatus(5);
            memberAccount.setType(5);
            memberAccount.save();

        }

        int memberId = member.getId();

        StringBuffer studentSql = new StringBuffer(
                "SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.sort_id,");
        studentSql.append("IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,");
        studentSql.append(
                "a.member_id,a.degree_of_difficulty,a.avatar,a.`status`,c.epal_pwd,c.nickname FROM class_student");
        studentSql.append(
                " a,device_relation b,device c WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id ");
        studentSql.append("= ? and b.isbind = 1 UNION SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.");
        studentSql.append(
                "sort_id,IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,a.member_id,");
        studentSql.append(
                "a.degree_of_difficulty,a.avatar,a.`status`,b.epal_pwd,b.nickname FROM `class_student` a,device b WHERE ");
        studentSql.append("a.epal_id = b.epal_id AND a.member_id = ?");

        ClassStudent classStudent = ClassStudent.dao.findFirst(studentSql.toString(), mobile, memberId);

        if (classStudent == null) {

            classStudent = new ClassStudent();
            classStudent.setName(mobile);
            classStudent.setStudentType(1);
            classStudent.setMemberId(memberId);
            classStudent.save();

            int studentId = classStudent.getId();

            Device device = new Device();
            device.setDeviceNo("defaultdeviceno" + studentId);
            device.setEpalId("multi" + studentId);
            device.setSn("defaultsn" + studentId);
            device.setEpalPwd("multi" + studentId);
            device.setNickname("multi" + studentId);
            device.setDeviceType("cr");
            device.setRemark("");
            device.setDeviceUsedType("");
            device.setStatus(1);
            device.save();

            classStudent.setEpalId(device.getEpalId()).update();

            DeviceRelation deviceRelation = new DeviceRelation();
            deviceRelation.setEpalId(device.getEpalId());
            deviceRelation.setFriendId(mobile);
            deviceRelation.setFriendName(mobile);
            deviceRelation.setRole("guardian");
            deviceRelation.setIsbind(1);
            deviceRelation.save();

        }

        result.put("studentId", classStudent.getId());
        result.put("memberId", memberId);

        return result;
    }

    public Memberaccount registerByClassAtrial(String mobile, Integer memberId) {

        Memberaccount memberAccount = Memberaccount.dao
                .findFirst("select * from memberaccount where account = ? and (type <=2 or type = 5)", mobile);

        if (memberAccount == null) {

            Member member = new Member();
            member.setNickname(mobile);
            member.setType(5);
            member.setMobile(mobile).save();

            memberAccount = new Memberaccount();
            memberAccount.setAccount(mobile);
            memberAccount.setPassword(MD5UTIL.encrypt("123456"));
            memberAccount.setMemberid(member.getId());
            memberAccount.setStatus(1);
            memberAccount.setType(5);
            memberAccount.save();

        } else if (memberAccount.getMemberid().equals(memberId) == false) {

        }

        return memberAccount;
    }

    public ClassStudent createDefaultStudent(String mobile, Integer memberId) {

        StringBuffer studentSql = new StringBuffer(
                "SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.sort_id,");
        studentSql.append("IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,");
        studentSql.append(
                "a.member_id,a.degree_of_difficulty,a.avatar,a.`status`,c.epal_pwd,c.nickname FROM class_student");
        studentSql.append(
                " a,device_relation b,device c WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id ");
        studentSql.append("= ? and b.isbind = 1 UNION SELECT a.id,a.`name`,a.epal_id,a.create_time,a.student_type,a.");
        studentSql.append(
                "sort_id,IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,a.member_id,");
        studentSql.append(
                "a.degree_of_difficulty,a.avatar,a.`status`,b.epal_pwd,b.nickname FROM `class_student` a,device b WHERE ");
        studentSql.append("a.epal_id = b.epal_id AND a.member_id = ?");

        ClassStudent classStudent = ClassStudent.dao.findFirst(studentSql.toString(), mobile, memberId);

        if (classStudent == null) {

            Device device = new Device();
            device.setDeviceNo("defaultdeviceno" + memberId);
            device.setEpalId("multi" + memberId);
            device.setSn("defaultsn" + memberId);
            device.setEpalPwd("multi" + memberId);
            device.setNickname("multi" + memberId);
            device.setDeviceType("cr");
            device.setRemark("");
            device.setDeviceUsedType("");
            device.setStatus(1);
            device.save();

            classStudent = new ClassStudent();
            classStudent.setName(mobile);
            classStudent.setEpalId(device.getEpalId());
            classStudent.setStudentType(1);
            classStudent.setMemberId(memberId);
            classStudent.save();

            DeviceRelation deviceRelation = new DeviceRelation();
            deviceRelation.setEpalId(device.getEpalId());
            deviceRelation.setFriendId(mobile);
            deviceRelation.setFriendName(mobile);
            deviceRelation.setRole("guardian");
            deviceRelation.setIsbind(1);
            deviceRelation.save();

        }

        return classStudent;

    }

}
