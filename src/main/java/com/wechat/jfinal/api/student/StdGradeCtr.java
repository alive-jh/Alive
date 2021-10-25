package com.wechat.jfinal.api.student;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.*;

import com.wechat.jfinal.service.StudentService;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

public class StdGradeCtr extends Controller {
    private static final StudentService studentService = new StudentService();


    public void setImportantGrade(){
        Map<String, Object> rsp = new HashMap<>();
        try{
            int sId = getParaToInt("studentId",0);
            int gId = getParaToInt("gradeId",0);
            if(xx.isOneEmpty(sId,gId)){
                rsp.put("code",403);
                rsp.put("msg","参数不合法");
                renderJson(rsp);
                return;
            }
            studentService.setImportantGrade(sId,gId);
            rsp.put("code",200);
        }catch (Exception e){
            rsp.put("code",500);
        }
        renderJson(rsp);
    }
    public void cancelImportantGrade(){
        Map<String, Object> rsp = new HashMap<>();
        try{
            int sId = getParaToInt("studentId",0);
            int gId = getParaToInt("gradeId",0);
            if(xx.isOneEmpty(sId,gId)){
                rsp.put("code",403);
                rsp.put("msg","参数不合法");
                renderJson(rsp);
                return;
            }
            studentService.cancelImportantGrade(sId,gId);
            rsp.put("code",200);
        }catch (Exception e){
            rsp.put("code",500);
        }
        renderJson(rsp);
    }

    //0:代表所有卡圈
   // @EmptyParaValidate(params = {"studentId","my","type"})
    public  void  getCard(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer studentId = getParaToInt("studentId");

        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 10);

        Integer my = getParaToInt("my");

        Integer type = getParaToInt("type",1);

        if (my==0){
            Page<Record> paginate = Db.paginate
                    (pageNumber, pageSize, "SELECT a.*,b.name,b.avatar,c.class_name,c.cover ", "FROM `moment` a, class_student b, class_room c WHERE  a.student_id = b.id AND a.class_room_id = c.id and a.b_public=1 and m_type=? order by a.create_time desc",type);

            Page<Record> recordPage = CardData(paginate, studentId);

            Result.ok(recordPage,this);
        }else if (my==1){
            Page<Record> paginate = Db.paginate
                    (pageNumber, pageSize, "SELECT a.*,b.name,b.avatar,c.class_name,c.cover ", "FROM `moment` a, class_student b, class_room c WHERE a.student_id =? AND a.student_id = b.id AND a.class_room_id = c.id and m_type=? order by a.create_time desc", studentId,type);

            Page<Record> recordPage = CardData(paginate, studentId);

            Result.ok(recordPage,this);

        }

    }

    public void deleteCard(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer momentId = getParaToInt("momentId");

        Moment.dao.deleteById(momentId);

        Result.ok(this);
    }

    //2019.12.27 修改 @pang
   @EmptyParaValidate(params = {"userId","momentId"})
    public  void  isNotOrLike(){

       getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer momentId = getParaToInt("momentId");
        //1是卡圈  0是分享出去
       Integer status = getParaToInt("status");

       Integer userId = getParaToInt("userId");

        JSONObject json = new JSONObject();

        if (userId!=null && userId!=0 && status==1){
            ThumbUp thumbUp = ThumbUp.dao.findRecord(momentId, userId);  //查询是否点过赞

            if (thumbUp==null ){  //没点赞
                thumbUp = new ThumbUp();
                thumbUp.setUserId(userId);
                thumbUp.setMomentId(momentId);
                thumbUp.setStatus(1);
                thumbUp.save();

                List<ThumbUp> thumbUps = ThumbUp.dao.find("SELECT * FROM `thumb_up` WHERE user_id = ? and TO_DAYS(create_time) = TO_DAYS(NOW())",userId);
                CoinGift coinGift = CoinGift.dao.findById(3);
                if (coinGift != null && thumbUps.size() >= 3){
                    Record record = Db.findFirst("SELECT * FROM v_student_account WHERE id = ?",userId);
                    StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                    studyCoinRecord.setAction(1).setCount(coinGift.getCoinCount()).setRemark(coinGift.getName()).setAccountId(record.getInt("account_id")).save();
                }

                List<ThumbUp> thumbUps2 = ThumbUp.dao.find("SELECT * FROM `thumb_up` WHERE moment_id = ? AND `status` = 1",momentId);
                List<CoinGift> coinGifts = CoinGift.dao.find("select * from coin_gift where id in (13,15,17)");
                for (CoinGift gift : coinGifts) {
                    if (thumbUps2.size() == gift.getTimes()){
                        Record record = Db.findFirst("SELECT a.student_id,b.account_id FROM `moment` a,v_student_account b WHERE a.student_id = b.id AND a.id = ? LIMIT 1",momentId);
                        StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                        studyCoinRecord.setAction(1).setCount(gift.getCoinCount()).setRemark(gift.getName()).setAccountId(record.getInt("account_id")).save();
                    }
                }

                Moment moment = Moment.dao.findById(momentId);
                moment.setLikes(moment.getLikes()+1);

                moment.update();
                json.put("likeCount",moment.getLikes());
                json.put("status",1);
            }else {
                if (thumbUp.getStatus()==1) {
                    thumbUp.setStatus(0).update();

                    Moment moment = Moment.dao.findById(momentId);
                    moment.setLikes(moment.getLikes() - 1);
                    moment.update();
                    json.put("likeCount", moment.getLikes());
                    json.put("status", 0);
                }else if (thumbUp.getStatus()==0){
                    thumbUp.setStatus(1).update();

                    Moment moment = Moment.dao.findById(momentId);
                    moment.setLikes(moment.getLikes() + 1);
                    moment.update();
                    json.put("likeCount", moment.getLikes());
                    json.put("status", 1);
                }
            }
        }else if (userId==0 && momentId!=null && status==0){
                Moment  moment =  Moment.dao.findById(momentId);
                moment.setLikes(moment.getLikes()+1);
                moment.update();
        }

        Result.ok(json,this);
    }

    //@EmptyParaValidate(params = {"studentId","content","momentId"})
    public void  saveRandComment(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer studentId = getParaToInt("studentId");
        Integer teacherId = getParaToInt("teacherId");
        String content = getPara("content");
        Integer momentId = getParaToInt("momentId");
        String audioUrl = getPara("audioUrl");
        String audioTime = getPara("audioTime");


        JSONObject json = new JSONObject();

        Calendar calendar =Calendar.getInstance();
        RandComment randComment =  getBean(RandComment.class);

        if (studentId==0 && teacherId==0){
            Result.ok("参数错误",this);
        }

        if (teacherId!=0){
            randComment.setContent(content);
            randComment.setTeacherId(teacherId);
            randComment.setStudentId(0);
            randComment.setMomentId(momentId);
            randComment.setCreateDate(calendar.getTime());
            randComment.setAudioUrl(audioUrl);
            randComment.setAudioTime(audioTime);
            randComment.save();

            ClassTeacher teacherInfo = ClassTeacher.dao.findTeacherInfo(teacherId);

            json.put("Info",teacherInfo);
        }else if (studentId!=0){
            randComment.setContent(content);
            randComment.setStudentId(studentId);
            randComment.setTeacherId(0);
            randComment.setMomentId(momentId);
            randComment.setAudioUrl(audioUrl);
            randComment.setCreateDate(calendar.getTime());
            randComment.setAudioTime(audioTime);
            randComment.save();

            ClassStudent studentInfo = ClassStudent.dao.findStudentInfo(studentId);
            json.put("Info",studentInfo);
        }

        json.put("randComment",randComment);
        Result.ok(json,this);

    }

    @EmptyParaValidate(params = {"randCommentId"})
    public void deleteRandComment(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        RandComment.dao.deleteById(getParaToInt("randCommentId"));

        Result.ok(this);

    }

    public void getRandComment(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 10);

        Page<Record> paginate = Db.paginate(pageNumber, pageSize, "SELECT a.*",
                "FROM (SELECT cs.`name` AS studentName, cs.avatar AS studentAvatar, ct.avatar AS teacherAvatar, ct.`name` AS teacherName, rc.*, IF(rc.teacher_id > 0,1,0) AS flag FROM rand_comment rc LEFT JOIN class_student cs ON cs.id = rc.student_id LEFT JOIN class_teacher ct ON ct.id = rc.teacher_id WHERE rc.moment_id = ? ) a ORDER BY a.flag DESC,a.createDate DESC", getParaToInt("momentId"));

        Result.ok(paginate,this);
    }


    @EmptyParaValidate(params = {"momentId","b_public"})
    public void  MomentIsNotOrPublic(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer b_public = getParaToInt("b_public");

        Integer momentId = getParaToInt("momentId");

        if (b_public==1){
             Db.update("UPDATE moment SET b_public=1 WHERE id=?;", momentId);
            Result.ok(true,this);
        }else if (b_public==0){
            Db.update("UPDATE moment SET b_public=0 WHERE id=?;",momentId);
            Result.ok(false,this);
        }

    }

    public  void  getMomentInfo(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer momentId = getParaToInt("momentId");

        Moment momentInfo = Moment.dao.findMomentInfo( momentId);

        Mallproduct mallproduct = Mallproduct.dao.findFirst("SELECT d.* FROM moment a,class_course b,mallproduct d WHERE a.id = ? AND a.class_course_id = b.id AND b.class_grades_id = d.class_grade_id",momentId);

        if (mallproduct != null){
            momentInfo.put("product",mallproduct.getId());
        }



        Result.ok(momentInfo,this);
    }
    /**
     * 根据学生名字模糊查询得到该学生所有的卡圈
     * @param : name , studentId
     * */
    public void serarchStudendCard(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        String name = getPara("name");

        Integer studentId = getParaToInt("studentId");

        Integer classRoomId = getParaToInt("classRoomId");

        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 100);

        if (name==null || name==""){
            Result.error(20502,"搜索不能为空",this);
        }

        if (classRoomId == 0){
            List<ClassStudent> classStudent = ClassStudent.dao.find("select * from class_student where name like '%"+name+"%'");

            if (classStudent!=null){
                List<String> ids = new ArrayList<>();

                for (ClassStudent student : classStudent) {
                    ids.add(student.getId().toString());
                }

                String id = Pattern.compile("\\b([\\w\\W])\\b").matcher(ids.toString().substring(1,ids.toString().length()-1)).replaceAll("'$1'");

                Page<Record> paginate = Db.paginate(pageNumber, pageSize,"select a.*,b.name,b.avatar,c.class_name,c.cover","from moment a, class_student b, class_room c where a.student_id = b.id AND a.class_room_id = c.id and a.b_public=1 and a.student_id in("+id+") ORDER  by create_time desc");

                Page<Record> recordPage = CardData(paginate, studentId);

                Result.ok(recordPage,this);
            }

        }else {

            Page<Record> paginate = Db.paginate(pageNumber, pageSize,"select a.*,b.name,b.avatar,c.class_name,c.cover","from class_student b,moment a,class_room c  where b.id=a.student_id AND a.class_room_id=c.id AND a.class_room_id=?  and name like '%"+name+"%'",classRoomId);
            if (paginate !=null){

                Page<Record> recordPage = CardData(paginate, studentId);
                Result.ok(recordPage,this);
            }else {
                Result.error(20501,"无法搜索该学生",this);
            }

        }

    }

    /**
     *  根据课堂id索引当前课堂下所有学生的打卡
     *  @param:  classRoomId
     * */

    public void getCardByClassRoom(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 10);

        Integer type = getParaToInt("type",1);

        Integer classRoomId = getParaToInt("classRoomId");

        Integer studentId = getParaToInt("studentId");

        Page<Record> paginate = Db.paginate(pageNumber, pageSize, "select a.*,b.name,b.avatar,c.class_name,c.cover"," from moment a, class_student b, class_room c where a.student_id = b.id AND a.class_room_id = c.id and class_room_id=?  and m_type=? AND a.b_public=1  order by create_time desc ", classRoomId,type);

        Page<Record> recordPage = CardData(paginate, studentId);

        Result.ok(recordPage,this);


    }

    private Page<Record> CardData(Page<Record> page,Integer studentId){

        for (Record record : page.getList()) {
            int mommentCount = RandComment.dao.findMommentCount(record.getInt("id"));
            record.set("mommentCount", mommentCount);
            int likeStatus = ThumbUp.dao.findGoodsStatus(record.get("id"), studentId);
            record.set("likesStatus",likeStatus);

            Integer classCourseId = record.getInt("class_course_id");

            ClassCourse classCourse = ClassCourse.dao.findById(classCourseId);

            ClassGrades classGrades = ClassGrades.dao.findById(classCourse.getClassGradesId());

            record.set("classGradesName",classGrades.getClassGradesName());

        }

        return page;
    }


    public void  studentData() throws ParseException {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer studentId = getParaToInt("studentId");

        MemberAddress memberAddress = MemberAddress.dao.findFirst("select * from member_address a where student_id=?", studentId);

        ClassStudent classStudent = ClassStudent.dao.findFirst("select * from class_student where id =? ", studentId);

        net.sf.json.JSONObject data = new net.sf.json.JSONObject();

        if (memberAddress!=null){
            data.put("moblie",memberAddress.getMobile());
            data.put("Provice",memberAddress.getProvince());
            data.put("City",memberAddress.getCity());
            data.put("Area",memberAddress.getArea());
            data.put("Address",memberAddress.getAddress());

        }
        if (classStudent.getId()!=null){
            if (classStudent.getBirthday() !=null){
                int age = getAgeByBirth(classStudent.getBirthday());
                data.put("age",age);
                data.put("birthday",classStudent.getBirthday().toString());
            }
            data.put("studentName",classStudent.getName());
            data.put("studentAvatar",classStudent.getAvatar());
            data.put("sex",classStudent.getSex());
        }

        DeviceRelation deviceRelation = DeviceRelation.dao.findFirst("SELECT b.* FROM class_student a,device_relation b WHERE a.epal_id = b.epal_id AND a.id = ? and  b.isbind = 1",studentId);

        if (deviceRelation != null){
            data.put("phone",deviceRelation.getFriendId());
        }

        Result.ok(data,this);
    }

    /**
     * 计算年龄
     * @param birthDay
     * */
    public static int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }


    /**
     *  根据班级id查询学生列表
     * @Param  classGradesId
     * */
    public  void  findClassStudentsByClassGradesId(){



        String classGradesId = getPara("classGradesId");

        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 1000);

        Page<Record> paginate = Db.paginate(pageNumber, pageSize,"SELECT cs.id, cs.`name` studentName, cs.epal_id, cgr.gradesStatus, ccs.do_day, cs.avatar, cs.sex , cgr.update_time","from class_grades_rela cgr, `class_course_schedule` ccs, class_student cs WHERE ccs.student_id = cs.id AND cgr.class_grades_id = ccs.class_grades_id AND ccs.student_id = cgr.class_student_id AND ccs.class_grades_id = ? ORDER BY cgr.gradesStatus DESC",classGradesId);

        Result.ok(paginate,this);
    }

    public  void  getContentByScriptId(){

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer classScriptId = getParaToInt("classScriptId");

        Integer pageNumber = getParaToInt("pageNumber", 1);

        Integer pageSize = getParaToInt("pageSize", 20);

        Page<Record> paginate = Db.paginate(pageNumber, pageSize,"SELECT a.class_script_content , a.reply_script, s.name,IFNULL(s.avatar,'') avatar","FROM lesson_script_reply a, class_student s where a.student_id = s.id AND a.class_script_id =?  order by a.create_time",classScriptId);

        Result.ok(paginate,this);


    }



}
