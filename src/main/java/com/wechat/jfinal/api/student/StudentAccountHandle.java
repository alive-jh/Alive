package com.wechat.jfinal.api.student;

import java.text.SimpleDateFormat;
import java.util.*;

import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import com.jfinal.json.JsonManager;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.*;
import com.wechat.jfinal.service.AgentSchoolService;
import com.wechat.jfinal.service.GradesService;
import com.wechat.jfinal.service.MemberService;
import com.wechat.jfinal.service.MemberaccountService;
import com.wechat.jfinal.service.StudentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StudentAccountHandle extends Controller {
    static GradesService gradesService = new GradesService();
    static StudentService studentService = new StudentService();
    private static final MemberService memberService = new MemberService();
    private static final MemberaccountService memberAccountService = new MemberaccountService();
    private AgentSchoolService agentSchoolService = new AgentSchoolService();

    // 新增学生和修改学生信息
    public void saveStudent() {

        ClassStudent classStudent = getBean(ClassStudent.class, "");
        String account = getPara("account");

        int success = 0;

        if (classStudent.getId() != null) {
            classStudent.update();
        } else {
            classStudent.save();

            if (classStudent.getEpalId() == null || classStudent.getEpalId().equals("") == true) {
                classStudent.setEpalId("multi" + classStudent.getId()).update();
            }

            Device device = Device.dao.findFirst("select * from device where epal_id = ?", classStudent.getEpalId());

            if (device == null) {
                device = new Device();
                device.setDeviceNo("defaultdeviceno" + classStudent.getId());
                device.setEpalId(classStudent.getEpalId());
                device.setSn("defaultsn" + classStudent.getId());
                device.setEpalPwd(classStudent.getEpalId());
                device.setNickname(classStudent.getEpalId());
                device.setDeviceType("oo");
                device.setRemark("");
                device.setDeviceUsedType("");
                device.setStatus(1);
                device.save();

            }

            Memberaccount memberaccount = Memberaccount.dao.findFirst("SELECT * FROM memberaccount WHERE memberid = ?", classStudent.getMemberId());

            if (memberaccount != null) {
                DeviceRelation deviceRelation = new DeviceRelation();
                deviceRelation.setEpalId(device.getEpalId());
                deviceRelation.setFriendId(memberaccount.getAccount());
                deviceRelation.setFriendName(memberaccount.getAccount());
                deviceRelation.setRole("guardian");
                deviceRelation.setIsbind(1);
                deviceRelation.save();
            }

        }

        String cardFid = getPara("cardFid");

        if (cardFid != null) {
            PublicRoomFidToStudent prfts = new PublicRoomFidToStudent();
            prfts.setStudentId(classStudent.getId());
            prfts.setCardFid(cardFid);
            prfts.save();
        }

        MemberAddress memberAddress = getBean(MemberAddress.class,"address");
        memberAddress.setStudentId(classStudent.getId());

        if (memberAddress.getId()==null){
            memberAddress.save();
        }else {
            memberAddress.update();
        }



        Record student = Db.findFirst("SELECT a.*,b.device_type,b.epal_pwd FROM class_student a,device b WHERE a.epal_id = b.epal_id AND a.id = ?", classStudent.getId());
        Date birthday = student.getDate("birthday");

        student.set("birthday",birthday==null?null:birthday.toString());
        success = 1;

        JSONObject result = new JSONObject();
        result.put("success", success);
        result.put("data", Result.toJson(student.getColumns()));
        result.put("address", Result.toJson(memberAddress));
        renderJson(result);

    }

    public void getStudentListByMemberId() {
        // 新建返回对象
        renderJson();
    }

    @Deprecated
    public void applyStudent() {
        // 新建返回对象

        String memberId = getPara("memberId", "");
        String name = getPara("name");
        String epalId = getPara("epalId", "");
        ClassStudent classStudent = new ClassStudent();
        classStudent.setName(name);
        classStudent.setEpalId(epalId);
        classStudent.setContribution(0);
        classStudent.setCreateTime(new Date());
        classStudent.setIntegral(0);
        classStudent.setRemark("");
        classStudent.setSortId(1);
        if (!"".equals(memberId) && "".equals(epalId)) {
            classStudent.setStudentType(4);
        } else if ("".equals(memberId) && !"".equals(epalId)) {
            classStudent.setStudentType(1);
        } else {
            classStudent.setStudentType(0);
        }
        classStudent.save();
        renderJson(classStudent);
    }

    public void isBasicStd() {

        Map<String, Object> rsp = new HashMap<>();
        try {
            int sId = getParaToInt("stdId");

            rsp.put("isBasicStd", gradesService.getJoinGrades(sId, "3", 1).size() == 0 ? 0 : 1);
            rsp.put("code", 200);
        } catch (Exception e) {
            rsp.put("code", 500);
        }
        renderJson(rsp);
    }

    // 获取学生信息
    public void findClassStudentByEpalId() {
        String epalId = getPara("epalId");


        List<ClassStudent> classStudent = ClassStudent.dao.find("SELECT id, name, epal_id, create_time, student_type , sort_id, integral, contribution, remark, member_id , degree_of_difficulty, avatar, status, lesson_integral, agent_id , sex, DATE_FORMAT(birthday, '%Y-%m-%d') FROM class_student a WHERE epal_id = ?", epalId);



        if (classStudent.size() > 0) {
            renderJson(JsonResult.JsonResultOK(classStudent.get(0)));
        } else {
            renderJson(JsonResult.JsonResultError(404));
        }

    }

    public void getListByMobile() {
        long start = System.currentTimeMillis();
        String mobile = getPara("mobile");
        if (xx.isEmpty(mobile)) {
            renderJson(Rt.paraError());
            return;
        }
        Member  member = memberService.getBymobile(mobile);
        if (member == null) {
            renderJson(Rt.paraError());
            return;
        }
        member.remove("openid");
        Memberaccount account = memberAccountService.findByMemberId(member.getId());
        // 获得学生列表
        List<ClassStudent> students = studentService.getUnionStudentList(account.getAccount(), member.getId());
        List<Integer> studentIds = ConvetUtil.models2IntList(students, "id");
        List<Record> deviceNickNames = studentService.getDeviceNickNames(studentIds);
        Map<Integer, Record> schoolForStudent = agentSchoolService.getSchoolByStudentIds(studentIds);
        List<Record> studentMakeUp = new ArrayList<>();

        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getEpalId() == null) {
                students.get(i).setEpalId("");
            }

            Record t = students.get(i).toRecord();
            Record s = schoolForStudent.get(students.get(i).getId());
            t.set("school", s == null ? new Record() : s);

            MemberAddress memberAddress = MemberAddress.dao.findByStudentId(students.get(i).getId());
            t.set("address",Result.toJson(memberAddress));
            for (int j = 0; j < deviceNickNames.size(); j++) {
                if (deviceNickNames.get(j).getInt("id").equals(students.get(i).getId())) {
                    t.set("deviceNickName", deviceNickNames.get(j).getStr("nickname"));
                    t.set("deviceType", deviceNickNames.get(j).getStr("device_type") == null ? "" : deviceNickNames.get(j).getStr("device_type"));
                    t.set("epalPwd", deviceNickNames.get(j).getStr("epal_pwd") == null ? "" : deviceNickNames.get(j).getStr("epal_pwd"));
                }
            }

            if (t.getInt("rfid_status") == 1) {
                studentMakeUp.add(t);
            }

        }

        if (xx.isEmpty(students))
            students = new ArrayList<>();

        // 临时办法，解决返回数据结构统一
        JSONObject json = makeUpJson(Rt.success(studentMakeUp));

        renderJson(json);
        //System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 更新学生信息
     */
    public void updateStudent() {
        ClassStudent classStudent = getBean(ClassStudent.class, "");

        if (classStudent == null) {
            Result.error(203, this);
            return;
        }

        classStudent.update();
        Result.ok(classStudent, this);
    }

    public void delStudent() {

        int studentId = getParaToInt("studentId", 0);

        int delType = getParaToInt("delType", 0);

        if (xx.isEmpty(studentId)) {
            Result.error(203, this);
            return;
        }

        String delStudent = "update class_student set status = -1 where id = ?";
        String delRfid = "update public_room_fid_to_student set status = -1 where student_id = ?";
        String sql = "";

        // delType为1 删除整个学生
        if (delType == 1) {
            sql = delStudent;
        }

        // delType为1 删除绑定rfid卡
        if (delType == 2) {
            sql = delRfid;
        }

        Db.update(sql, studentId);

        Result.ok(this);
    }

    public void JsonTimeConvertWhitTimeStamp(JSONObject json, String key) {

        long time = (long) json.getJSONObject(key).get("time");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        json.put(key, sdf.format(date));

    }

    public JSONObject makeUpJson(JSONObject json) {

        JSONArray jsonArray = json.getJSONArray("data");
        JSONArray jsonArrayNew = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject temp = JSONObject.fromObject(jsonArray.get(i));
            if (temp.get("avatar") != null) {
                if (temp.get("avatar").toString().indexOf("{") != -1) {
                    temp.put("avatar", "");
                }
            }
            jsonArrayNew.add(temp);
        }
        json.put("data", jsonArrayNew);
        return json;
    }

    /**
     * 根据学生id设置epalId
     */
    public void setEpal() {

        String epalId = getPara("epalId");
        Integer studentId = getParaToInt("studentId", 0);

        if (xx.isOneEmpty(studentId)) {
            Result.error(203, this);
            return;
        }

        ClassStudent classStudent = ClassStudent.dao.findFirst("select * from class_student where id = ?", studentId);

        if (classStudent == null) {
            Result.error(205, "查找不到学生", this);
            return;
        }

        classStudent.setEpalId(epalId).update();

        Result.ok(this);

    }

    /**
     * 根据学生id获取学生的所有老师
     *
     * @param studentId 学生id 必填
     */
    public void teacherList() {

        Integer studentId = getParaToInt("studentId");

        if (xx.isOneEmpty(studentId)) {
            Result.error(203, this);
            return;
        }

        String sql = "SELECT a.* FROM class_teacher a,class_grades b,class_grades_rela c WHERE b.id "
                + "= c.class_grades_id AND b.teacher_id = a.id AND c.class_student_id = ? AND c.gradesStatus = 1 GROUP BY a.id";

        List<ClassTeacher> classTeachers = ClassTeacher.dao.find(sql, studentId);

        JSONObject json = new JSONObject();
        json.put("classTeachers", Result.makeupList(classTeachers));
        Result.ok(json, this);

    }

    /**
     * 根据rfid卡号获取学生信息
     *
     * @param rfid rfid卡号 非空
     */
    public void getStudentByRfid() {

        String rfid = getPara("rfid");

        if (rfid == null) {
            Result.error(203, this);
            return;
        }

        // 获取学生信息
        ClassStudent classStudent = ClassStudent.dao.findFirst(
                "select * from class_student where id = (select student_id from public_room_fid_to_student where card_fid = ? limit 1)",
                rfid);

        if (classStudent == null) {
            Result.error(205, "查询不到学生信息", this);
            return;
        }


        // 如果学生没有epalid则生成默认的
        if (classStudent.getEpalId() == null || "".equals(classStudent.getEpalId())) {

            classStudent.setEpalId("fd_2_" + classStudent.getId()).update();

        }

        Device device = Device.dao.findFirst("SELECT * FROM device WHERE epal_id = ?", classStudent.getEpalId());

        if (device == null) {
            device = new Device();
            device.setDeviceNo("defaultdeviceno" + classStudent.getId());
            device.setEpalId("fd_2_" + classStudent.getId());
            device.setSn("defaultsn" + classStudent.getId());
            device.setEpalPwd("fd_2_" + classStudent.getId());
            device.setNickname(classStudent.getEpalId());
            device.setDeviceType("");
            device.setRemark("");
            device.setDeviceUsedType("");
            device.setStatus(1);
            device.save();
        }

        // 转换学生对象为record，后期方便添加新的返回数据
        Record _classStudent = classStudent.toRecord();
        // 添加epal_pwd 机器人密码
        _classStudent.set("epal_pwd", device.getEpalPwd());

        // 查找学生绑定的家长帐号并返回给客户端
        DeviceRelation deviceRelation = DeviceRelation.dao.findFirst("select * from device_relation where epal_id = ?",
                device.getEpalId());
        _classStudent.set("friend_id", deviceRelation == null ? "" : deviceRelation.getFriendId());

        JSONObject json = new JSONObject();
        json.put("student", Result.toJson(_classStudent.getColumns()));

        Result.ok(json, this);

    }

    /**
     * 通过rfid卡绑定学生与家长关系
     */
    public void addRfidRealtion() {

        String friendId = getPara("friendId");
        String rfid = getPara("rfid");

        if (xx.isOneEmpty(friendId, rfid)) {
            Result.error(203, this);
            return;
        }

        // 获取学生信息
        ClassStudent classStudent = ClassStudent.dao.findFirst(
                "select * from class_student where id = (select student_id from public_room_fid_to_student where card_fid = ? limit 1)"
                , rfid);

        if (classStudent == null) {
            Result.error(20501, "查询不到学生信息", this);
            return;
        }

        //获取账户信息
        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ? and type !=4 order by type asc limit 1", friendId);

        if (memberaccount == null) {
            Result.error(20502, "查询不到账户信息", this);
            return;
        }

        //获取绑定信息
        DeviceRelation deviceRelation = DeviceRelation.dao.findFirst("SELECT * FROM `device_relation` WHERE epal_id = ?", classStudent.getEpalId());

        if (deviceRelation != null) {
            Result.error(20503, "该rfid卡已被别人绑定", this);
            return;
        }


        // 如果学生没有epalid则生成默认的
        if (classStudent.getEpalId() == null || "".equals(classStudent.getEpalId())) {

            classStudent.setEpalId("fd_2_" + classStudent.getId()).update();

        }

        Device device = Device.dao.findFirst("SELECT * FROM `device` WHERE epal_id = ?", classStudent.getEpalId());


        if (device == null) {
            device = new Device();
            device.setDeviceNo("defaultdeviceno" + classStudent.getId());
            device.setEpalId(classStudent.getEpalId());
            device.setSn("defaultsn" + classStudent.getId());
            device.setEpalPwd(classStudent.getEpalId());
            device.setNickname(classStudent.getEpalId());
            device.setDeviceType("");
            device.setRemark("");
            device.setDeviceUsedType("");
            device.setStatus(1);
            device.save();
        }


        //绑定关系
        deviceRelation = new DeviceRelation();
        deviceRelation.setDeviceNo(device.getDeviceNo());
        deviceRelation.setEpalId(device.getEpalId());
        deviceRelation.setFriendId(friendId);
        deviceRelation.setFriendName(memberaccount.getNickname());
        deviceRelation.setRole("guardian");
        deviceRelation.setIsbind(1);
        deviceRelation.save();


        Result.ok(classStudent, this);


    }

    public void saveIntergal() {

    }

    /**
     * 查询中积分
     */
    public void rankingOfStudents() {
        Integer studentId = getParaToInt("studentId");
        Integer pageNumber = getParaToInt("pageNumber", 1);
        Integer pageSize = getParaToInt("pageSize", 10);
        Page<Record> page = Db.paginate(pageNumber, pageSize, "select a.*,b.all_integral ", "from class_student a, class_course_record_all_integral b where a.id = b.student_id ORDER BY b.all_integral desc");
        if (studentId == null) {
            Result.ok(page, this);
            return;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("rankList", page);
        Record record = Db.findFirst("SELECT b.* FROM (SELECT t.*, @rownum := @rownum + 1 AS rownum FROM (SELECT @rownum := 0) r,(SELECT * FROM class_course_record_all_integral ORDER BY all_integral DESC) AS t) AS b WHERE b.student_id = ?;", studentId);
        if (record == null) {
            ClassCourseRecordAllIntegral classCourseRecordAllIntegral = new ClassCourseRecordAllIntegral();
            classCourseRecordAllIntegral.setAllIntegral(0);
            classCourseRecordAllIntegral.setStudentId(studentId);
            data.put("student", classCourseRecordAllIntegral);
        } else {
            data.put("student", record);
        }
        renderJson(Rt.success(data));
    }
}
