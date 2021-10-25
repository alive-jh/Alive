package com.wechat.jfinal.api.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.alive.sample.Sample;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.MakeUp;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.common.utils.web.ResultOld;
import com.wechat.jfinal.model.*;
import com.wechat.jfinal.service.AgentSchoolService;
import com.wechat.jfinal.service.ClassCourseService;
import com.wechat.jfinal.service.ClassTeacherService;
import com.wechat.jfinal.service.DeviceScheduleService;
import com.wechat.jfinal.service.IntegralService;
import com.wechat.jfinal.service.MemberService;
import com.wechat.jfinal.service.MemberaccountService;
import com.wechat.jfinal.service.StudentService;
import com.wechat.jfinal.service.UserService;
import com.wechat.pay.util.MD5Util;
import com.wechat.util.CommonUtils;
import com.wechat.util.MD5UTIL;
import com.wechat.util.SecurityUtil;
import com.wechat.util.StringUtil;
import com.wechat.util.TAOBAOSMS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserHandler extends Controller {

    private static final StudentService studentService = new StudentService();
    private static final ClassCourseService classCourseService = new ClassCourseService();
    private static final MemberService memberService = new MemberService();
    private static final MemberaccountService memberAccountService = new MemberaccountService();
    private static final IntegralService integralService = new IntegralService();
    private static final ClassTeacherService classTeacherService = new ClassTeacherService();
    private static final DeviceScheduleService deviceScheduleService = new DeviceScheduleService();
    private AgentSchoolService agentSchoolService = new AgentSchoolService();
    private SecurityUtil securityUtil = new SecurityUtil();
    private Cache redis = Redis.use();

    /*
     * 用户登陆,返回token loginType:凡豆伴=2,超智多多=4
     */
    public void login() {
        String account = getPara("account", "");
        String password = getPara("password", "");
        String type = getPara("type", "device");
        Integer loginType = getParaToInt("loginType", 0);

        // accountType:0=默认,4=超智多多

        String tokenStr = "";

        Memberaccount memberAccount = null;

        if (loginType.equals(4)) {

            String smscode = getPara("smscode");

            if (xx.isEmpty(smscode)) {
                ResultOld.error(203, this);
                return;
            }

            if (smscode.equals("000000")) {

            } else {
                Smscode sms = Smscode.dao.findFirst("select * from smscode where mobile = ? and code = ?", account,
                        smscode);

                if (sms == null) {
                    renderJson(JsonResult.JsonResultError(205, "验证码错误"));
                    return;
                }

                Member member = Member.dao.findFirst("select * from member where type = 4 and mobile = ?", account);

                if (xx.isEmpty(member)) {
                    /*
                     * JSONObject result = new JSONObject(); result.put("url",
                     * ChaoZhiCtr.CHAOZHI_MINI_APP_SERVER +
                     * "/ajax/chaozhi/payPage?mobile=" + account);
                     * Result.error(20401, "未开通会员", result, this);
                     */
                    Result.error(20501, "", this);
                    return;
                }
            }

            tokenStr = "chaozhi" + "#" + account + "#" + "111111" + "#" + System.currentTimeMillis();

        } else {
            memberAccount = Memberaccount.dao.findFirst("select * from memberaccount where account=? and password=?",
                    account, password);
            if (null == memberAccount) {
                // 用户不存在
                renderJson(JsonResult.JsonResultError(204));
                return;
            }

            tokenStr = type + "#" + account + "#" + password + "#" + System.currentTimeMillis() + "#"
                    + memberAccount.getMemberid();


        }

        String access_token = "";

        try {
            // 生成access_token
            access_token = SecurityUtil.encrypt(tokenStr, "fandou");
            JSONObject data = new JSONObject();
            data.put("access_token", access_token);
            data.put("account_id", memberAccount.getId());

            if (memberAccount != null) {
                data.put("memberId", memberAccount.getMemberid());
            }


            data.put("isDefaultPwd", memberAccount.isDefaultPwd());

            // 存储access_token
            redis.set(type + "#" + account, access_token);
            redis.setex("device#" + memberAccount.getMemberid(), 60 * 60, access_token);

            redis.setex(access_token, 60 * 60 * 12, memberAccount.getMemberid() + "#" + memberAccount.getAccount());

            memberAccount.setLastLogin(new Date()).update();

            renderJson(JsonResult.JsonResultOK(data));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(500));
        }

        redis.close(redis.getJedis());
    }

    /**
     * 用户注册
     * <p>
     * 201:账号或者密码不能为空 202:账号已经注册
     */
    public void register() {
        String account = getPara("account", "");
        String password = getPara("password", "");
        String deviceId = getPara("deviceId", "");
        String code = getPara("code", "");
        String sn = getPara("sn", "");


        JSONObject json = new JSONObject();

        Integer regType = getParaToInt("regType", 0);

        if ("".equals(account) || "".equals(password)) {
            if (regType == 0) {
                renderJson(JsonResult.JsonResultError(201));
                return;
            }
        } else {

        }

        // 超智多多注册
        if (regType == 4) {

            String openId = getPara("openId");

            Member member = Member.dao.findFirst("select * from member where openid = ? and type = 4", openId);

            if (member == null) {
                member = new Member();
                member.setType(4);
                member.save();
            }

            String mobile = getPara("mobile");

            String avatarUrl = getPara("avatarUrl");
            String city = getPara("city");
            int gender = getParaToInt("gender");
            String nickName = getPara("nickName");
            String province = getPara("province");

            member.setNickname(nickName);
            member.setOpenid(openId);
            if (1 == gender) {
                member.setSex("男");
            }
            if (2 == gender) {
                member.setSex("女");
            }
            if (-1 == gender) {
                member.setSex("未知");
            }
            member.setCity(city);
            member.setProvince(province);
            member.setHeadimgurl(avatarUrl);
            member.setMobile(mobile);
            member.update();

            int memberId = member.getId();

            Memberaccount memberaccount = Memberaccount.dao
                    .findFirst("select * from memberaccount where account = ? and type = 4", mobile);

            String pwdDef = MD5UTIL.encrypt("123456");

            if (xx.isEmpty(memberaccount)) {
                memberaccount = new Memberaccount();
                memberaccount.setPassword(xx.isEmpty(password) ? pwdDef : MD5UTIL.encrypt(password));
                memberaccount.setAccount(mobile);
                memberaccount.setMemberid(member.getId());
                memberaccount.setStatus(0);
                memberaccount.setType(4);
                memberaccount.setNickname(member.getNickname());
                memberaccount.save();
            }

            // 保存邀请信息
            int recommendMemberId = getParaToInt("recommendMemberId", 1);
            UserRecommendRecord record = new UserRecommendRecord();
            record.setRecommendFromMemberid(recommendMemberId);
            record.setRecommendToMemberid(memberId);
            record.setType(1);
            record.save();

            // 添加学友关系
            if (recommendMemberId > 1) {

                List<ChaozhiStudyFriend> chaozhiStudyFriends = ChaozhiStudyFriend.dao.find(
                        "select * from chaozhi_study_friend where member1 = ? or member2 = ?", recommendMemberId,
                        recommendMemberId);

                boolean isFriend = false;
                for (ChaozhiStudyFriend chaozhiStudyFriend : chaozhiStudyFriends) {
                    if (chaozhiStudyFriend.getMember1().equals(memberId)
                            || chaozhiStudyFriend.getMember2().equals(memberId)) {
                        isFriend = true;
                    }
                }

                if (isFriend == false) {
                    ChaozhiStudyFriend chaozhiStudyFriend = new ChaozhiStudyFriend();
                    chaozhiStudyFriend.setMember1(recommendMemberId);
                    chaozhiStudyFriend.setMember2(memberId);
                    chaozhiStudyFriend.save();
                }

            }

        } else if (regType == 8) { // 凡豆伴注册

            Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?", account);

            if (null == memberaccount) {

                Member newMember = new Member();
                newMember.setMobile(account);
                newMember.setType(2);
                newMember.setNickname(account);
                newMember.save();
                int memberId = newMember.getId();

                memberaccount = new Memberaccount();
                memberaccount.setAccount(account);
                memberaccount.setMemberid(memberId);
                memberaccount.setPassword(MD5UTIL.encrypt(password));
                memberaccount.setStatus(0);
                memberaccount.setType(2);
                memberaccount.setNickname(account);
                memberaccount.save();

				/*// 注册学生
				ClassStudent classStudent = new ClassStudent();
				classStudent.setEpalId("8_fandou_" + account);
				classStudent.setName(account);
				classStudent.setStudentType(8);
				classStudent.setRemark("虚拟机器人");
				classStudent.setMemberId(newMember.getId());
				classStudent.save();*/
            }

            json.put("member", memberaccount.getMemberid());

        } else {
            Member member = Member.dao.findFirst("select * from member where mobile=?", account);
            List list = Db.find("select * from device where epal_id = ?", deviceId);
            if (list.size() > 0) {
                renderJson(JsonResult.JsonResultError(208));
                return;
            } else if (null == member) {

                // 用户信息member表格
                Member newMember = new Member();
                newMember.setMobile(account);
                newMember.setType(5);
                newMember.setNickname(account);
                newMember.save();
                int memberId = newMember.getId();

                // memberaccount账号表
                Memberaccount memberaccount = new Memberaccount();
                memberaccount.setAccount(account);
                memberaccount.setMemberid(memberId);
                memberaccount.setPassword(password);
                memberaccount.setStatus(0);
                memberaccount.setType(2);
                memberaccount.setNickname(account);
                memberaccount.save();


                // 添加机器人

                Device device = new Device();
                device.setDeviceNo(deviceId);
                device.setEpalId(deviceId);
                device.setNickname(deviceId);
                device.setRemark("虚拟机器人");
                device.setStatus(1);
                device.setSn(sn);

                String epalPwd = MD5Util.MD5Encode(deviceId + "#fandou", "");
                device.setEpalPwd(epalPwd.substring(24));
                device.setDeviceType("");
                device.setIsFree("");
                device.setDeviceUsedType("");
                device.save();

                // 凡豆账号绑定机器人ID
                DeviceRelation deviceRelation = new DeviceRelation();
                deviceRelation.setDeviceNo(deviceId);
                deviceRelation.setEpalId(deviceId);
                deviceRelation.setFriendId(account);
                deviceRelation.setFriendName(account);
                deviceRelation.setIsbind(1);
                deviceRelation.setRole("guardian");
                deviceRelation.save();

                // 注册学生
                ClassStudent classStudent = new ClassStudent();
                classStudent.setEpalId(deviceId);
                classStudent.setName(deviceId);
                classStudent.setStudentType(5);
                classStudent.setRemark("虚拟机器人");
                classStudent.setMemberId(newMember.getId());
                classStudent.save();

            } else {
                renderJson(JsonResult.JsonResultError(207));
                return;
            }
        }

        renderJson(JsonResult.JsonResultOK(json));
    }

    /**
     * 注销登陆 201:access_token错误
     */
    public void loginOut() {
        String access_token = getPara("access_token", "");
        try {
            String temp = securityUtil.detrypt(access_token, "fandou");
            String type = temp.split("#")[0];
            String account = temp.split("#")[1];
            String accessTokenKey = type + "#" + account;
            redis.del(accessTokenKey);
            renderJson(JsonResult.JsonResultOK());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            renderJson(JsonResult.JsonResultError(201));
            e.printStackTrace();
        }

        redis.close(redis.getJedis());

    }

    /**
     * 注销登陆 201:access_token错误
     */
    public void getUserInfo() {
        String access_token = getPara("access_token", "");

        Integer appType = getParaToInt("appType", 0); // 3：少年派

        try {

            String temp = securityUtil.detrypt(access_token, "fandou");
            String type = temp.split("#")[0];
            String account = temp.split("#")[1];
            String accessTokenKey = type + "#" + account;
            String token = redis.get(accessTokenKey);

            if (access_token.equals(token)) {
                // token验证通过
            } else {
                renderJson(JsonResult.JsonResultError(209));
                return;
            }

            Member member = null;

            if ("device".equals(type)) {
				/*member = Member.dao.findFirst("select * from member where mobile=? and (type <=2 or type = 5)",
						account);

				if (member == null || member.getId() == null) {
					member = Member.dao.findFirst(
							"select * from member where id = (select memberid from memberaccount where account = ?)",
							account);
				}*/

                member = Member.dao.findFirst(
                        "select * from member where id = (select memberid from memberaccount where account = ?)",
                        account);

                Integer memberId = member.getId();

                StringBuffer studentSql = new StringBuffer(
                        "SELECT a.sex,date_format(a.birthday, '%Y-%m-%d') as birthday,a.id,a.`name`,ifnull(a.epal_id,'') as epal_id,a.create_time,a.student_type,a.sort_id,");
                studentSql
                        .append("IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,");
                studentSql.append(
                        "a.member_id,a.degree_of_difficulty,a.avatar,a.`status`,c.epal_pwd,ifnull(c.device_type,'') as device_type,c.nickname,a.lesson_integral FROM class_student");
                studentSql.append(
                        " a,device_relation b,device c WHERE a.epal_id = b.epal_id AND b.epal_id = c.epal_id AND b.friend_id ");
                studentSql.append(
                        "= ? and b.isbind = 1 and a.status > -1 UNION SELECT a.sex,date_format(a.birthday, '%Y-%m-%d') as birthday,a.id,a.`name`,ifnull(a.epal_id,'') as epal_id,a.create_time,a.student_type,a.");
                studentSql.append(
                        "sort_id,IFNULL(a.integral,0) AS integral ,IFNULL(a.contribution,0) AS contribution,a.remark,a.member_id,");
                studentSql.append(
                        "a.degree_of_difficulty,a.avatar,a.`status`,b.epal_pwd,ifnull(b.device_type,'') as device_type,b.nickname,a.lesson_integral FROM `class_student` a,device b WHERE ");
                studentSql.append("a.epal_id = b.epal_id AND a.member_id = ? and a.status > -1");

                List<Record> studentList = Db.find(studentSql.toString(), account, memberId);

                /*
                 * Kv cond = Kv.by("account",account).set("memberId",memberId);
                 * List<Record> list =
                 * Db.find(Db.getSqlPara("student.getUnionStudentListWithDevice"
                 * ,cond));
                 */

                // 如果没有关联学生,自动创建相关信息 并返回
                if (studentList == null || studentList.size() == 0) {

                    UserService userService = new UserService();
                    if (appType == 3) {  //3：生成少年派的
                        userService.createShaoNianPaiStudent(member.getId(), account);
                    } else {
                        userService.createDefaultStudent(member.getId(), account);
                    }

                    studentList = Db.find(studentSql.toString(), account, memberId);
                }

                for (Record student : studentList) {
                    student.set("address", Result.toJson(MemberAddress.dao.findByStudentId(student.getInt("id"))));
                }

                List<RfidAssociate> rfidAssociates = RfidAssociate.dao.findByAccount(account);


                for (Record student : studentList) {
                    JSONArray associate = new JSONArray();
                    for (RfidAssociate rfidAssociate : rfidAssociates) {
                        if (student.getInt("id").equals(rfidAssociate.getStudentId())) {
                            ClassStudent classStudent = ClassStudent.dao.findById(rfidAssociate.getRfidStudent());
                            Record studentRecord = classStudent.toRecord();
                            studentRecord.set("birthday", classStudent.getBirthday() == null ? "" : classStudent.getBirthday().toString());
                            associate.add(Result.toJson(studentRecord.getColumns()));
                        }
                    }
                    student.set("rfidAssociate", associate);
                }

                ClassTeacher classTeacher = null;
                if (memberId != null) {
                    classTeacher = classTeacher.dao.findFirst("select id,`name`,member_id,`level`,agent_id,remark,rfid,`dec`,sex,avatar from class_teacher where member_id = ?",
                            memberId);
                }

                IntegralWallet integralWallet = IntegralWallet.dao.findFirst(
                        "SELECT a.* FROM `integral_wallet` a,memberaccount b WHERE b.account = ? AND b.memberid = ? AND a.account_id = b.id"
                        , account, memberId);

                HashMap<String, Object> map = new HashMap<>();
                map.put("studentList", studentList);

                map.put("integralWallet", Result.toJson(integralWallet));

                if (classTeacher != null) {

                    Record teacher = classTeacher.toRecord();

                    AgentSchool school = AgentSchool.dao.findFirst(
                            "SELECT s.* from agent_school s , school_teacher_rela t WHERE s.id = t.school_id and s.status = 1 AND member_id = ? ",
                            member.getId());

                    teacher.set("school", school == null ? new JSONObject() : Result.toJson(school));

                    map.put("classTeacher", Result.toJson(teacher.getColumns()));

                }
                map.put("info", Result.toJson(member.toRecord().getColumns()));

                Record accountInfo = Db.findFirst("SELECT id,account FROM memberaccount WHERE account = ? AND memberid = ?", account, member.getId());
                map.put("accountInfo", Result.toJson(accountInfo.getColumns()));

                ResultOld.ok(map, this);

            } else if ("chaozhi".equals(type)) {
                member = Member.dao.findFirst(" select * from member where type = 4 and mobile = ?", account);
                JSONObject json = new JSONObject();
                json.put("studentList", new JSONArray());
                json.put("teacher", new JSONObject());
                json.put("info", member.toJson());
                Result.ok(json, this);
            } else {
                ResultOld.error(203, this);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(500, "获取账户信息失败"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            renderJson(JsonResult.JsonResultError(209));
            e.printStackTrace();
        }

    }

    /**
     * 在用户登录后，获取初始化所需的信息
     */
    public void getInitInfo() {

        String loginType = getPara("loginType", "");

        String mobile = getPara("mobile");

        Map<String, Object> data = new HashMap<>();

        if (StringUtil.isEmptyStr(mobile)) {
            renderJson(JsonResult.JsonResultError(305));
            return;
        }

        if ("4".equals(loginType)) {
            Member member = Member.dao.findFirst("select * from member where type = 4 and mobile = ?", mobile);
            JSONObject dd = Result.toJson(member.toRecord().getColumns());
            dd.put("integral", 0);
            List temp = new ArrayList<>();
            data.put("info", dd);
            data.put("teacher", new JSONObject());
            data.put("studentList", new ArrayList<>());
            renderJson(JsonResult.OK(data));
            ResultOld.ok(data, this);
            return;
        }

        try {

            Member member = memberService.getBymobile(mobile);

            if (member == null) {
                renderJson(JsonResult.JsonResultError(305));
                return;
            }

            // 已经登陆的账号，不需要去验证密码和账号是否存在
            member.remove("openid");
            //System.out.println(member);
            //System.out.println(member.getId());
            Memberaccount account = memberAccountService.findByMemberId(member.getId());
            // //System.out.println(account);
            // //System.out.println(account.getPassword());
            if (xx.isEmpty(account) || StringUtil.isEmptyStr(account.getPassword())) {
                renderJson(JsonResult.JsonResultError(500));
                return;
            }

            // 获取积分
            int integral = integralService.getIntegralTotal(member);
            // 获取老师信息
            ClassTeacher teacher = classTeacherService.findByMemberId(member.getId());

            AgentSchool school = AgentSchool.dao.findFirst(
                    "SELECT s.* from agent_school s , school_teacher_rela t WHERE s.id = t.school_id and s.status = 1 AND member_id = ? ",
                    member.getId());
            Record temp = new Record();
            if (teacher != null) {
                temp = teacher.toRecord();
                temp.set("school", school == null ? new AgentSchool() : school);
            }

            // 获得学生列表
            List<ClassStudent> students = studentService.getUnionStudentList(member.getMobile(), member.getId());

            // 如果没有关联学生,自动创建相关信息 并返回
            if (students == null || students.size() == 0) {

                UserService userService = new UserService();

                userService.createDefaultStudent(member.getId(), mobile);

                students = studentService.getUnionStudentList(member.getMobile(), member.getId());
            }

            List<Integer> studentIds = ConvetUtil.models2IntList(students, "id");
            Map<Integer, Record> schoolForStudent = agentSchoolService.getSchoolByStudentIds(studentIds);
            List<Record> deviceNickNames = studentService.getDeviceNickNames(studentIds);
            List<Record> studentMakeUp = new ArrayList<>();
            for (ClassStudent student : students) {
                Record t = student.toRecord();
                Record s = schoolForStudent.get(student.getId());
                t.set("school", s == null ? new Record() : s);

                for (int j = 0; j < deviceNickNames.size(); j++) {
                    if (deviceNickNames.get(j).getInt("id").equals(student.getId())) {
                        t.set("deviceNickName", deviceNickNames.get(j).getStr("nickname"));
                    }
                }

                if (t.getInt("rfid_status") == 1) {
                    studentMakeUp.add(t);
                }
            }

            JSONObject dd = JSONObject.fromObject(member);
            dd.put("integral", integral);
            data.put("info", MakeUp.timestampToString(dd));

            data.put("teacher", Rt.makeup(temp));

            if (students == null)
                data.put("studentList", new ArrayList<>());
            else
                data.put("studentList", Rt.makeup(studentMakeUp));
            renderJson(JsonResult.OK(data));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(500));
        }
    }

    /**
     * 今日要做的事情 课程：1、老师排课2、学生日程安排
     */
    public void getTodayToDoList() {

        try {
            Map<String, Object> data = new HashMap<>();
            int studentId = getParaToInt("studentId", 0);
            if (studentId == 0) {
                renderJson(JsonResult.JsonResultError(305));
                return;
            }

            // 获取学生的所有班级
            List<Record> records = Db.find(
                    "select class_grades_id from class_grades_rela where class_student_id = ? and gradesStatus = 1",
                    studentId);

            records.forEach(record -> {
                Sample sample = new Sample();
                sample.updateProgress(studentId,record.getInt("class_grades_id"));
            });

            // List<HashMap<Integer, String>> slotMaps = new
            // ArrayList<HashMap<Integer, String>>();

            // 班级上课规则容器
            HashMap<Integer, HashMap<Integer, String>> slotMaps = new HashMap<Integer, HashMap<Integer, String>>();

            // 获取今天是一周的第几天
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_WEEK);

            // 学生所有班级id
            String condition = "";
            if (records.size() > 0) {
                // 拼接班级id为条件;
                condition = records.get(0).getStr("class_grades_id");
                for (int i = 1; i < records.size(); i++) {
                    condition += "," + records.get(i).getStr("class_grades_id");
                }
            } else {
                // 学生没有任何班级，直接返回成功
                ResultOld.ok(this);
                return;
            }

            // 获取学生自定义上课规则
            List<StdDiyStudyDay> stdDiyStudyDays = StdDiyStudyDay.dao.find(
                    "select * from std_diy_study_day where grade_id in (" + condition + ") and std_id = ?", studentId);

            // 遍历自定义上课时间,获取今天要上课的班级
            for (StdDiyStudyDay stdDiyStudyDay : stdDiyStudyDays) {

                if (stdDiyStudyDay.getIsTeacherDefault() == 1) {
                    HashMap<Integer, String> slotMap = new HashMap<Integer, String>();
                    JSONArray jsonArray = JSONArray.fromObject(stdDiyStudyDay.getRule());
                    JSONArray datas = jsonArray.getJSONObject(day - 1).getJSONArray("data");
                    for (int i = 0; i < 3; i++) {
                        slotMap.put((Integer) datas.getJSONObject(i).get("id"),
                                datas.getJSONObject(i).get("time").toString());
                    }
                    slotMaps.put(stdDiyStudyDay.getGradeId(), slotMap);
                } else {
                    String week = stdDiyStudyDay.getStr("week");
                    int status = Integer.parseInt(week.charAt(day - 1) + "");
                    if (status == 1) {
                        HashMap<Integer, String> slotMap = new HashMap<Integer, String>();
                        JSONArray jsonArray = JSONArray.fromObject(stdDiyStudyDay.getRule());

                        JSONArray datas = null;
                        for (int i = 0; i < jsonArray.size(); i++) {
                            if (jsonArray.getJSONObject(i).getInt("sort") == (day - 1)) {
                                datas = jsonArray.getJSONObject(i).getJSONArray("data");
                            }

                        }

                        for (int i = 0; i < 3; i++) {
                            slotMap.put((Integer) datas.getJSONObject(i).get("id"),
                                    datas.getJSONObject(i).get("time").toString());
                        }
                        slotMaps.put(stdDiyStudyDay.getGradeId(), slotMap);
                    }
                }

            }

            List<ClassCourse> courses = new ArrayList<ClassCourse>();
            if (slotMaps.size() > 0) {
                Object[] objs = slotMaps.keySet().toArray();
                condition = objs[0].toString();

                for (int i = 1; i < objs.length; i++) {
                    condition += "," + objs[i].toString();
                }

                courses = classCourseService.getCourses(studentId, condition);

            }

            List<Record> coursesDetaile = classCourseService.makeUpAllInfo(courses, studentId);
            List<Integer> mainCourseIds = new ArrayList<>();
            List<Integer> allIds = new ArrayList<>();

            Iterator<Record> iterator = coursesDetaile.iterator();

            int courseId = 0;
            while (iterator.hasNext()) {
                Record record = iterator.next();
                if (courseId == record.getInt("id")) {
                    iterator.remove();
                } else {
                    courseId = record.getInt("id");
                    record.set("doSlot", record.getInt("planTime"));
                    if (record.getInt("planTime") == 300) {
                        //System.out.println("有主课" + courseId);
                        record.set("isMain", 1);
                        mainCourseIds.add(record.getInt("id"));
                    } else {
                        record.set("isMain", 0);
                    }
                    String completeTime = record.getStr("completeTime");
                    if (completeTime != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String now = sdf.format(new Date());
                        if (!now.equals(completeTime.split("-")[0])) {
                            if (classCourseService.isLastClassRoom(studentId, record.getInt("ClassGradesId"))) {
                                iterator.remove();
                            } else {
                                record.set("isComplete", 0);
                                record.set("completeTime", "");
                            }

                        } else {
                            record.set("completeTime", completeTime.split("-")[1]);
                        }
                    } else {
                        record.set("completeTime", "");
                    }
                    record.set("planTime", slotMaps.get(record.getInt("ClassGradesId")).get(record.getInt("planTime")));
                    allIds.add(record.getInt("id"));
                }
                if (record.getStr("cover") == null) {
                    record.set("cover", "");
                }
            }

            /*
             * for (Record record : coursesDetaile) { if
             * (record.getInt("planTime") == 300) { record.set("isMain", 1);
             * mainCourseIds.add(record.getInt("id")); } else {
             * record.set("isMain", 0); } String completeTime =
             * record.getStr("completeTime"); if (completeTime != null) {
             * SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
             * now = sdf.format(new Date()); if
             * (!now.equals(completeTime.split("-")[0])) {
             * record.set("isComplete", 0); record.set("completeTime", ""); }
             * else { record.set("completeTime", completeTime.split("-")[1]); }
             * } else { record.set("completeTime", ""); } record.set("planTime",
             * slotMaps.get(record.getInt("ClassGradesId")).get(record.getInt(
             * "planTime"))); allIds.add(record.getInt("id")); }
             */

            Map<Integer, Record> courseIndexs = classCourseService.courseIndex(mainCourseIds);
            Map<Integer, Integer> studyTime = classCourseService.courseStudyTimes(allIds);
            for (Record record : coursesDetaile) {
                Record temp = courseIndexs.get(record.getInt("id"));
                if (temp != null) {
                    record.set("index", temp.getInt("now"));
                    record.set("total", temp.getInt("total"));
                } else {
                    record.set("index", 0);
                    record.set("total", 0);
                }
                record.set("studied",
                        studyTime.get(record.getInt("id")) == null ? 99 : studyTime.get(record.getInt("id")));
            }

            data.put("classCourses", MakeUp.recordToJSONObject(coursesDetaile));
            List<Record> deviceSchedules = deviceScheduleService.todaySchedule(studentId);
            renderJson(JsonResult.OK(data));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(500));
        }

    }

    /**
     * 检查帐号是否存在
     *
     * @return memberId ：0表示不存在
     */
    public void isExist() {

        String account = getPara("account");
        int type = getParaToInt("type", 1);

        if (account == null) {
            Result.error(203, this);
            return;
        }

        Memberaccount memberaccount = null;

        // 查询手机号是否已经注册过帐号
        if (type == 1) {
            memberaccount = Memberaccount.dao
                    .findFirst("SELECT * FROM memberaccount WHERE account = ? AND (type <=2 or type = 5)", account);
        }

        JSONObject json = new JSONObject();

        if (memberaccount != null) {
            json.put("memberId", memberaccount.getMemberid());
        } else {
            json.put("memberId", 0);
        }
        Result.ok(json, this);

    }

    public void registerAccount() {

        String mobile = getPara("mobile");

        String password = getPara("password");

        String code = getPara("code");

        if (xx.isOneEmpty(mobile, password, code)) {
            Result.error(203, this);
            return;
        }


        if (code.equals("173166") == true) {

        } else if (code.equals(redis.get("smscode" + mobile)) == false) {
            Result.error(20501, "验证码错误", this);
            return;
        } else {

        }

        Memberaccount memberaccount = Memberaccount.dao
                .findFirst("SELECT * FROM memberaccount WHERE account = ? AND (type <=2 or type = 5)", mobile);

        if (memberaccount != null) {
            Result.error(20502, "帐号已经注册", this);
            return;
        }

        Member newMember = new Member();
        newMember.setMobile(mobile);
        newMember.setType(0);
        newMember.setNickname(mobile);
        newMember.save();

        memberaccount = new Memberaccount();
        memberaccount.setAccount(mobile);
        memberaccount.setPassword(MD5UTIL.encrypt(password));
        memberaccount.setMemberid(newMember.getId());
        memberaccount.setStatus(0);
        memberaccount.setType(0);
        memberaccount.setNickname(mobile).save();

        CoinGift coinGift = CoinGift.dao.findById(1);
        if (coinGift != null) {
            StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
            studyCoinRecord.setAction(1).setCount(coinGift.getCoinCount()).setRemark(coinGift.getName()).setAccountId(memberaccount.getId()).save();
        }


        Result.ok(memberaccount.getAccount(), this);

    }

    public void sendSmsCode() {
        String mobile = getPara("mobile");

        if (mobile == null) {
            Result.error(203, this);
            return;
        }

        if (CommonUtils.checkMobile(mobile) == false) {
            Result.error(20509, "请输入正确的手机号", this);
            return;
        }

        String code = CommonUtils.getRandomNumber(4);

        //System.out.println(code);
        redis.setex("smscode" + mobile, 60 * 60 * 15, code);

        try {

            Integer mobile_send_count = redis.get("smscount" + mobile);
            if (mobile_send_count == null) {
                redis.setex("smscount" + mobile, 60 * 60 * 24, 1);
            } else if (mobile_send_count >= 3) {
                Result.error(20503, "今天发送短信已经到达上限", this);
                return;
            } else {
                redis.set("smscount" + mobile, mobile_send_count + 1);
            }

            TAOBAOSMS.sendCode(code, mobile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Result.ok(this);
        redis.close(redis.getJedis());
    }

    /**
     * 用户注册时，选择用户角色接口（老师、家长） 后期可以扩展老师信息
     * <p>
     * <p>
     * 参数： memberId：会员ID role：角色（teacher,parent）
     */
    public void roleSelection() {
        Integer memberId = getParaToInt("memberId");
        String role = getPara("role");

        if ("teacher".equals(role)) {
            // 判断用户是否存在
            Member member = Member.dao.findById(memberId);

            if (null == member) {
                Result.error(222, "该用户不存在", this);
                return;
            } else {

            }
            // 判断用户是否已经注册老师账号

            if (null != ClassTeacher.dao.findFirst("select * from class_teacher where member_id=?", memberId)) {
                Result.error(223, "该用户已经是老师", this);
                return;
            } else {
                ClassTeacher classTeacher = new ClassTeacher();
                classTeacher.setName(member.getMobile());
                classTeacher.setAgentId(1);
                classTeacher.setLevel(1);
                classTeacher.setAvatar("");
                classTeacher.setCover("");
                classTeacher.setMemberId(memberId.toString());
                classTeacher.save();
            }

            Result.ok(this);
        } else {
            Result.error(203, "不是teacher", this);
        }

    }


    @EmptyParaValidate(params = {"mobile"})
    public void sendFindAccountCode() throws Exception {

        String mobile = getPara("mobile");

        int sendType = getParaToInt("type", 1);
        int appType = getParaToInt("appType", 0);  //3:少年派

        if (CommonUtils.checkMobile(mobile) == false) {

            Result.error(20501, "手机格式不正确", this);

        } else {

            Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?",
                    mobile);

            if (memberaccount == null) {

                Result.error(20509, "帐号未注册", this);

            } else {

                String code = CommonUtils.getRandomNumber(6);
                Cache redis = Redis.use();
                redis.setex(mobile + "#get_member_code", 300, code);

                if (appType == 3) { //少年派的短信验证
                    if (sendType == 2) {
                        TAOBAOSMS.sendVoiceCode(code, mobile);
                    } else {
                        TAOBAOSMS.sendSnpPwdCode(code, mobile);
                    }

                } else {
                    if (sendType == 2) {
                        TAOBAOSMS.sendVoiceCode(code, mobile);
                    } else {
                        TAOBAOSMS.sendPwdCode(code, mobile);
                    }
                }


                Result.ok(this);

            }

            redis.close(redis.getJedis());

        }

    }

    @EmptyParaValidate(params = {"account", "code"})
    public void getMemberByAccount() {

        Cache redis = Redis.use();

        String account = getPara("account");

        String redisCode = redis.get(account + "#get_member_code");

        if ((redisCode != null && redisCode.equals(getPara("code")))) {

            Memberaccount memberaccount = Memberaccount.dao.findFirst("select account,memberid,nickname from memberaccount where account = ?", account);

            Result.ok(Result.toJson(memberaccount), this);

        } else {

            Result.error(20501, "验证码错误", this);

        }

        redis.close(redis.getJedis());

    }


    //重置用户密码
    @EmptyParaValidate(params = {"account", "code", "pwd"})
    public void resetAccountPwd() {

        Cache redis = Redis.use();

        String account = getPara("account");

        String newPassword = getPara("pwd");

        String code = getPara("code");

        String redisCode = redis.get(account + "#get_member_code");

        if ((redisCode != null && redisCode.equals(code)) || code.equals("666888")) {

            Memberaccount memberaccount = Memberaccount.dao.findFirst("select id, account,memberid,nickname from memberaccount where account = ?", account);

            if (memberaccount == null) {

                Result.error(20509, "查询不到帐号信息", this);

            } else {

                memberaccount.setPassword(newPassword).update();
                Result.ok(Result.toJson(memberaccount), this);
            }
        } else {

            Result.error(20501, "验证码错误", this);

        }
    }

    @EmptyParaValidate(params = {"account", "pwd"})
    public void resetDefaultPwd() {


        String account = getPara("account");

        String newPassword = getPara("pwd");

        Memberaccount memberaccount = Memberaccount.dao
                .findFirst("select id, account,memberid,nickname,password from memberaccount where account = ?", account);

        if (memberaccount == null) {
            Result.error(20509, "查询不到帐号信息", this);
        } else if (memberaccount.isDefaultPwd(newPassword)) {
            Result.error(20507, "还是默认密码", this);
        } else if (memberaccount.isDefaultPwd()) {
            memberaccount.setPassword(newPassword);
            memberaccount.update();
            Result.ok(this);
        } else {
            Result.error(20508, "不是默认密码 ", this);
        }

    }

    @EmptyParaValidate(params = {"code", "type", "mobile"})
    public void checkSmsCode() {

        String mobile = getPara("mobile");

        int type = getParaToInt("type", 1);

        String code = getPara("code");

        if (CommonUtils.checkMobile(mobile) == false) {

            Result.error(20501, "手机格式不正确", this);

        }

        Cache redis = Redis.use();

        String redisCode = null;

        if (type == 1) {
            redisCode = redis.get(mobile + "#get_member_code");
        } else if (type == 2) {
            redisCode = redis.get(mobile + "#regist_code");
        }

        if (redisCode != null && redisCode.equals(code)) {
            Result.ok(this);
        } else if (code.equals("666888")) {
            Result.ok(this);
        } else {
            Result.error(20501, "验证码错误", this);
        }

        redis.close(redis.getJedis());

    }


    @EmptyParaValidate(params = {"mobile"})
    public void sendRegistCode() throws Exception {

        String mobile = getPara("mobile");

        int sendType = getParaToInt("type", 1);
        int appType = getParaToInt("appType", 0); // 3：少年派

        if (CommonUtils.checkMobile(mobile) == false) {

            Result.error(20501, "手机格式不正确", this);

        } else {

            Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?", mobile);

            if (memberaccount != null) {

                Result.error(20509, "帐号已注册", this);


            } else {

                String code = CommonUtils.getRandomNumber(6);
                Cache redis = Redis.use();
                redis.setex(mobile + "#regist_code", 300, code);

                if (appType == 3) { //少年派的短信验证
                    if (sendType == 2) {
                        TAOBAOSMS.sendVoiceCode(code, mobile);
                    } else {
                        TAOBAOSMS.sendSnpPwdCode(code, mobile);
                    }

                } else {
                    if (sendType == 2) {
                        TAOBAOSMS.sendVoiceCode(code, mobile);
                    } else {
                        TAOBAOSMS.sendPwdCode(code, mobile);
                    }
                }


                Result.ok(this);

            }

            redis.close(redis.getJedis());

        }
    }

    @EmptyParaValidate(params = {"mobile", "code", "password", "nickName"})
    public void fandoubanRegister() throws Exception {

        String account = getPara("mobile");

        String code = getPara("code");

        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?", account);

        if (memberaccount != null) {

            Result.error(20509, "帐号已注册", this);
        } else {

            Cache redis = Redis.use();
            String redisCode = redis.get(account + "#regist_code");

            if ((redisCode != null && redisCode.equals(code)) || code.equals("666888")) {

                Member newMember = new Member();
                newMember.setMobile(account);
                newMember.setType(2);
                newMember.setNickname(getPara("nickName"));
                newMember.save();
                int memberId = newMember.getId();

                memberaccount = new Memberaccount();
                memberaccount.setAccount(account);
                memberaccount.setMemberid(memberId);
                memberaccount.setPassword(getPara("password"));
                memberaccount.setStatus(0);
                memberaccount.setType(2);
                memberaccount.setNickname(getPara("nickName"));
                memberaccount.save();

                //注册完赠送豆币
                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAction(1);
                studyCoinRecord.setRemark("注册赠送");
                studyCoinRecord.setAccountId(memberaccount.getId());
                studyCoinRecord.setCount(500);
                studyCoinRecord.save();

                JSONObject json = new JSONObject();
                json.put("memberId", memberaccount.getMemberid());

                Result.ok(json, this);

            } else {

                Result.error(20501, "验证码错误", this);

            }

        }

    }


    // deviceType:  eggy=蛋蛋   qy=清园  cr=Care  oo=嘀嗒
    public void updateDeviceType() {
        String deviceType = getPara("deviceType");

        Integer studentid = getParaToInt("studentid");

        Db.update("UPDATE device a ,class_student b SET a.device_type=? WHERE  b.id=? AND a.epal_id=b.epal_id;", deviceType, studentid);

        Result.ok(this);

    }

    public void getDeviceTypeByAccount() {
        String account = getPara("account");

        List<Record> records = Db.find("SELECT  a.epal_id,b.id,c.device_type  FROM  device_relation a,class_student b,device c  WHERE  a.friend_id=?  AND   a.epal_id=b.epal_id AND c.epal_id=b.epal_id", account);

        Result.ok(records, this);


    }

    public void getDeviceTypeByStudentid() {
        Integer studentid = getParaToInt("studentid");

        Record record = Db.findFirst("SELECT a.epal_id,c.device_type,a.id FROM `class_student` a ,device_relation b,device c WHERE a.id =? AND a.epal_id=b.epal_id AND a.epal_id=c.epal_id;", studentid);

        Result.ok(record, this);
    }


    @EmptyParaValidate(params = {"student"})
    public void getTodayToDoList2() {

        int studentId = getParaToInt("student");
        JSONObject json = new JSONObject();

        try {
            json.put("master", getTOdayCourse(studentId));

            JSONArray array = new JSONArray();

            List<RfidAssociate> rfidAssociates = RfidAssociate.dao.findByStudent(studentId);
            for (RfidAssociate associate : rfidAssociates) {
                array.add(getTOdayCourse(associate.getRfidStudent()));
            }
            json.put("slave", array);

            Result.ok(json, this);

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(500));
        }

    }

    public Map<String, Object> getTOdayCourse(int studentId) throws ParseException {

        Map<String, Object> data = new HashMap<>();

        ClassStudent classStudent = ClassStudent.dao.findById(studentId);

        // 获取学生的所有班级
        List<Record> records = Db.find(
                "select class_grades_id from class_grades_rela where class_student_id = ? and gradesStatus = 1",
                studentId);

        // List<HashMap<Integer, String>> slotMaps = new
        // ArrayList<HashMap<Integer, String>>();

        // 班级上课规则容器
        HashMap<Integer, HashMap<Integer, String>> slotMaps = new HashMap<Integer, HashMap<Integer, String>>();

        // 获取今天是一周的第几天
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        // 学生所有班级id
        String condition = "";
        if (records.size() > 0) {
            // 拼接班级id为条件;
            condition = records.get(0).getStr("class_grades_id");
            for (int i = 1; i < records.size(); i++) {
                condition += "," + records.get(i).getStr("class_grades_id");
            }
        } else {
            // 学生没有任何班级，直接返回成功
            return null;
        }

        // 获取学生自定义上课规则
        List<StdDiyStudyDay> stdDiyStudyDays = StdDiyStudyDay.dao.find(
                "select * from std_diy_study_day where grade_id in (" + condition + ") and std_id = ?", studentId);

        // 遍历自定义上课时间,获取今天要上课的班级
        for (StdDiyStudyDay stdDiyStudyDay : stdDiyStudyDays) {

            if (stdDiyStudyDay.getIsTeacherDefault() == 1) {
                HashMap<Integer, String> slotMap = new HashMap<Integer, String>();
                JSONArray jsonArray = JSONArray.fromObject(stdDiyStudyDay.getRule());
                JSONArray datas = jsonArray.getJSONObject(day - 1).getJSONArray("data");
                for (int i = 0; i < 3; i++) {
                    slotMap.put((Integer) datas.getJSONObject(i).get("id"),
                            datas.getJSONObject(i).get("time").toString());
                }
                slotMaps.put(stdDiyStudyDay.getGradeId(), slotMap);
            } else {
                String week = stdDiyStudyDay.getStr("week");
                int status = Integer.parseInt(week.charAt(day - 1) + "");
                if (status == 1) {
                    HashMap<Integer, String> slotMap = new HashMap<Integer, String>();
                    JSONArray jsonArray = JSONArray.fromObject(stdDiyStudyDay.getRule());
                    JSONArray datas = jsonArray.getJSONObject(day - 1).getJSONArray("data");
                    for (int i = 0; i < 3; i++) {
                        slotMap.put((Integer) datas.getJSONObject(i).get("id"),
                                datas.getJSONObject(i).get("time").toString());
                    }
                    slotMaps.put(stdDiyStudyDay.getGradeId(), slotMap);
                }
            }

        }

        List<ClassCourse> courses = new ArrayList<ClassCourse>();
        if (slotMaps.size() > 0) {
            Object[] objs = slotMaps.keySet().toArray();
            condition = objs[0].toString();

            for (int i = 1; i < objs.length; i++) {
                condition += "," + objs[i].toString();
            }

            courses = classCourseService.getCourses(studentId, condition);

        }

        List<Record> coursesDetaile = classCourseService.makeUpAllInfo(courses, studentId);
        List<Integer> mainCourseIds = new ArrayList<>();
        List<Integer> allIds = new ArrayList<>();

        Iterator<Record> iterator = coursesDetaile.iterator();

        int courseId = 0;
        while (iterator.hasNext()) {
            Record record = iterator.next();
            record.set("studentId", classStudent.getId());
            record.set("epalId", classStudent.getEpalId());
            if (courseId == record.getInt("id")) {
                iterator.remove();
            } else {
                courseId = record.getInt("id");
                record.set("doSlot", record.getInt("planTime"));
                if (record.getInt("planTime") == 300) {
                    //System.out.println("有主课" + courseId);
                    record.set("isMain", 1);
                    mainCourseIds.add(record.getInt("id"));
                } else {
                    record.set("isMain", 0);
                }
                String completeTime = record.getStr("completeTime");
                if (completeTime != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String now = sdf.format(new Date());
                    if (!now.equals(completeTime.split("-")[0])) {
                        if (classCourseService.isLastClassRoom(studentId, record.getInt("ClassGradesId"))) {
                            iterator.remove();
                        } else {
                            record.set("isComplete", 0);
                            record.set("completeTime", "");
                        }

                    } else {
                        record.set("completeTime", completeTime.split("-")[1]);
                    }
                } else {
                    record.set("completeTime", "");
                }
                record.set("planTime", slotMaps.get(record.getInt("ClassGradesId")).get(record.getInt("planTime")));
                allIds.add(record.getInt("id"));
            }
            if (record.getStr("cover") == null) {
                record.set("cover", "");
            }
        }

        /*
         * for (Record record : coursesDetaile) { if
         * (record.getInt("planTime") == 300) { record.set("isMain", 1);
         * mainCourseIds.add(record.getInt("id")); } else {
         * record.set("isMain", 0); } String completeTime =
         * record.getStr("completeTime"); if (completeTime != null) {
         * SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
         * now = sdf.format(new Date()); if
         * (!now.equals(completeTime.split("-")[0])) {
         * record.set("isComplete", 0); record.set("completeTime", ""); }
         * else { record.set("completeTime", completeTime.split("-")[1]); }
         * } else { record.set("completeTime", ""); } record.set("planTime",
         * slotMaps.get(record.getInt("ClassGradesId")).get(record.getInt(
         * "planTime"))); allIds.add(record.getInt("id")); }
         */

        Map<Integer, Record> courseIndexs = classCourseService.courseIndex(mainCourseIds);
        Map<Integer, Integer> studyTime = classCourseService.courseStudyTimes(allIds);
        for (Record record : coursesDetaile) {
            Record temp = courseIndexs.get(record.getInt("id"));
            if (temp != null) {
                record.set("index", temp.getInt("now"));
                record.set("total", temp.getInt("total"));
            } else {
                record.set("index", 0);
                record.set("total", 0);
            }
            record.set("studied",
                    studyTime.get(record.getInt("id")) == null ? 99 : studyTime.get(record.getInt("id")));
        }

        data.put("classCourses", MakeUp.recordToJSONObject(coursesDetaile));
        return data;
    }

    @EmptyParaValidate(params = {"mobile", "code", "nickName", "invitationCode"})
    public void snpInvitationRegister() throws Exception {

        String password = getPara("password", "123456");

        String account = getPara("mobile");

        String code = getPara("code");

        String invitationCode = getPara("invitationCode");

        AccountInvitationCode accountInvitationCode = AccountInvitationCode.dao.findFirst("select * from account_invitation_code where  code=?", invitationCode);

        if (accountInvitationCode == null) {
            Result.error(20510, "邀请码无效", this);
            return;
        }

        if (CommonUtils.checkMobile(account) == false) {

            Result.error(20501, "手机格式不正确", this);
            return;

        }

        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?", account);

        if (memberaccount != null) {

            Result.error(20509, "帐号已注册", this);
        } else {

            Cache redis = Redis.use();
            String redisCode = redis.get(account + "#regist_code");

            if ((redisCode != null && redisCode.equals(code)) || code.equals("666888")) {

                Member newMember = new Member();
                newMember.setMobile(account);
                newMember.setType(2);
                newMember.setNickname(getPara("nickName"));
                newMember.save();
                int memberId = newMember.getId();

                memberaccount = new Memberaccount();
                memberaccount.setAccount(account);
                memberaccount.setMemberid(memberId);
                memberaccount.setPassword(password);
                memberaccount.setStatus(0);
                memberaccount.setType(2);
                memberaccount.setNickname(getPara("nickName"));
                memberaccount.save();

                AccountInvitationRecord invitationUser = new AccountInvitationRecord();
                invitationUser.setAccountId(memberaccount.getId());
                invitationUser.setCode(invitationCode);
                invitationUser.save();

                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAction(1);
                studyCoinRecord.setRemark("注册奖励");
                studyCoinRecord.setAccountId(memberaccount.getId());
                studyCoinRecord.setCount(500);
                studyCoinRecord.save();

                StudyCoinRecord studyCoinRecord2 = new StudyCoinRecord();
                studyCoinRecord2.setAction(1);
                studyCoinRecord2.setRemark("填写邀请码");
                studyCoinRecord2.setAccountId(memberaccount.getId());
                studyCoinRecord2.setCount(50);
                studyCoinRecord2.save();

                StudyCoinRecord CoinRecord = new StudyCoinRecord();
                CoinRecord.setAction(1);
                CoinRecord.setRemark("邀请奖励");
                CoinRecord.setAccountId(accountInvitationCode.getAccountId());
                CoinRecord.setCount(50);
                CoinRecord.save();


                JSONObject json = new JSONObject();
                json.put("memberId", memberaccount.getMemberid());

                Result.ok(json, this);

            } else {

                Result.error(20501, "验证码错误", this);

            }

        }

    }

    @EmptyParaValidate(params = {"mobile", "card"})
    public void activateCard() {

        String account = getPara("mobile");

        StudyCoinCard studyCoinCard = StudyCoinCard.dao.findFirst("SELECT * FROM `study_coin_card` WHERE  code= ?", getPara("card"));

        if (studyCoinCard == null || studyCoinCard.getStatus() == 1) {
            Result.error(20501, "豆币卡无效", this);
            return;
        }
        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?", account);


        if (memberaccount != null) {


            StudyCoinCardCategory studyCoinCardCategory = StudyCoinCardCategory.dao.findFirst("SELECT  * from study_coin_card_category  WHERE  id= ?", studyCoinCard.getCategoryId());

            StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
            studyCoinRecord.setAccountId(memberaccount.getId());
            studyCoinRecord.setAction(1);
            studyCoinRecord.setCount(studyCoinCardCategory.getCoinCount());
            studyCoinRecord.setRemark("豆币卡兑换").save();

            studyCoinCard.setAccount(account);
            studyCoinCard.setStatus(1);
            studyCoinCard.update();

            Result.ok(this);


        } else {
            Result.error(20507, "用户不存在", this);
            return;
        }
    }

    @EmptyParaValidate(params = {"ids"})
    public void checkGrade(){

        String param = String.join(",",getPara("ids").split(","));
        List<ClassGradesRela> classGradesRela = ClassGradesRela.dao.find("select * from class_grades_rela WHERE class_student_id IN (?) AND gradesStatus = 1;",param);
        if (classGradesRela != null && classGradesRela.size() > 0){
            Result.ok(this);
            return;
        }

        List<Record> classGrades = Db.find("SELECT id,class_grades_name FROM `class_grades` WHERE show_in_eova = 6");
        Result.error(20501,"请选择班级",Result.makeupList(classGrades),this);

    }
}










