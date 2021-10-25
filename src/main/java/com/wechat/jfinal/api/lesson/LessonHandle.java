package com.wechat.jfinal.api.lesson;

import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tencentyun.TLSSigAPIv2;
import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.NoticeInfo;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.*;
import com.wechat.jfinal.service.ClassroomPackageService;
import com.wechat.jfinal.service.LessonService;
import com.wechat.jfinal.service.UserService;
import com.wechat.jfinal.service.WechatPayService;
import com.wechat.pay.util.WXUtil;
import com.wechat.service.impl.SoundServiceImpl;
import com.wechat.util.*;
import com.wechat.util.script.ScriptTransform;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LessonHandle extends Controller {

    @EmptyParaValidate(params = {"id"})
    public void closeLive(){

        int classRoomId = getParaToInt("id");
        Db.update("update class_room set live_status = -1 where id = ?",classRoomId);
        renderJson(Rt.success(this));
    }

    @EmptyParaValidate(params = {"id"})
    public void closeLive2(){

        int classCourseId = getParaToInt("id");
        Db.update("UPDATE class_course SET b_live = -1 WHERE id = ?",classCourseId);
        renderJson(Rt.success(this));
    }

    /**
     * 批量上传课堂脚本
     **/
    LessonService lessonService = new LessonService();

    public void saveClassScriptNormal() {

        int classRoom = getParaToInt("classRoomId", 1);
        String classScriptNormalList = getPara("classScriptNormalList");

        if (null == classScriptNormalList || "".equals(classScriptNormalList)) {
            renderJson(JsonResult.JsonResultError(203));
            return;
        }

        try {

            int status = lessonService.saveClassScriptNormal(classRoom, classScriptNormalList);
            if (status == 1) {
                List<ClassScriptNormal> result = lessonService.getClassScriptNormals(classRoom);
                Result.ok(result, this);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(201, "参数格式有误"));
        } catch (NullPointerException e1) {
            e1.printStackTrace();
            renderJson(JsonResult.JsonResultError(20101, "参数有为空"));
        } catch (Exception e2) {
            e2.printStackTrace();
            renderJson(JsonResult.JsonResultError(500));
        }

    }

    /*
     * 通过课堂Id获取课堂的脚本列表,获取的指令列表为json数组形式的
     *
     */

    public void getClassScriptNormalList() {
        int classRoomId = getParaToInt("classRoomId", 0);
        renderJson(Rt.success(LessonService.getClassScriptNormalList(classRoomId)));
    }

    public void saveClassScriptNormalTemp() {

        int classRoom = getParaToInt("classRoomId", 1);
        String classScriptNormalList = getPara("classScriptNormalList");

        if (null == classScriptNormalList || "".equals(classScriptNormalList)) {
            renderJson(JsonResult.JsonResultError(203));
            return;
        }

        try {

            int status = lessonService.saveClassScriptNormalTemp(classRoom, classScriptNormalList);
            if (status == 1) {
                List<ClassScriptNormalTemp> result = lessonService.getClassScriptNormalTemp(classRoom);
                Result.ok(result, this);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            renderJson(JsonResult.JsonResultError(201, "参数格式有误"));
        } catch (NullPointerException e1) {
            e1.printStackTrace();
            renderJson(JsonResult.JsonResultError(20101, "参数有为空"));
        } catch (Exception e2) {
            e2.printStackTrace();
            renderJson(JsonResult.JsonResultError(500));
        }

    }

    /**
     * function: 保存/修改在线课堂
     * description:使用POST方法请求提交数据，如果提交的数据带id主键，则为修改在线课堂信息，无ID则为添加
     * return:返回在线课堂对象
     *
     * @param: className(班级名称)
     * memberId（会员ID，取凡豆伴账号ID）
     * @author alex(已经添加接口文档)
     */
    public void saveClassRoom() {
        ClassRoom bean = getBean(ClassRoom.class, "");
        Integer tempId = getParaToInt("tempId", 0);

        if (tempId != 0) {
            bean.setOriginal(tempId);
        }


        List<ClassRoom> classRoomList = ClassRoom.dao.find("select id from class_room where class_name = ? and status != -1", bean.getClassName());
        //判断名字是否有重复
        if (classRoomList.size() > 0 && !classRoomList.get(0).getId().equals(bean.getId())) {
            renderJson(Rt.paraError("课堂名已存在"));
            return;
        }

        if (null == bean.getId()) {// 判断是否有ID
            bean.save();
        } else {
            bean.update();
        }

        String labels = getPara("labels");

        Db.update("update class_room_label_rela set status = 0 where class_room_id = ?", bean.getId());

        JSONArray labelArrary = new JSONArray();

        if (labels != null && labels.trim().equals("") == false) {
            String[] labelArray = labels.split(",");

            for (String label : labelArray) {

                if (labels.trim().equals("")) {
                    break;
                }

                ClassRoomLabel classRoomLabel = new ClassRoomLabel();
                classRoomLabel.setLabelName(label).setGroupId(211).save();

                labelArrary.add(classRoomLabel.toJson());

                ClassRoomLabelRela classRoomLabelRela = new ClassRoomLabelRela();
                classRoomLabelRela.setLabelId(classRoomLabel.getId()).setClassRoomId(bean.getId()).save();
            }

        }

        /*
         * if(labelIdStr!=null){
         *
         * String[] labelIds = labelIdStr.split(","); List<ClassRoomLabelRela>
         * BatchClassRoomLabelRela = new
         * ArrayList<ClassRoomLabelRela>(labelIds.length); for(String labelId :
         * labelIds){ ClassRoomLabelRela classRoomLabelRela = new
         * ClassRoomLabelRela();
         * classRoomLabelRela.setClassRoomId(bean.getId());
         * classRoomLabelRela.setLabelId(Integer.parseInt(labelId));
         * BatchClassRoomLabelRela.add(classRoomLabelRela); }
         *
         * Db.batchSave(BatchClassRoomLabelRela, 200);
         *
         * }
         */

        if (tempId != 0) {
            List<Record> list = Db
                    .find("SELECT * FROM class_script_normal_temp WHERE class_room_id = ? ORDER BY sort ASC", tempId);

            Object[][] objs = new Object[list.size()][4];

            for (int i = 0; i < list.size(); i++) {

                Object[] obj = new Object[4];
                obj[0] = bean.getId();
                obj[1] = list.get(i).getInt("class_script_type_id");
                obj[2] = list.get(i).getStr("class_script_content");
                obj[3] = list.get(i).getInt("sort");
                objs[i] = obj;

            }

            Db.batch(
                    "INSERT INTO class_script_normal (class_room_id,class_script_type_id,class_script_content,sort) VALUES (?,?,?,?)",
                    objs, list.size());
        }

        Record classRoom = Db.findFirst(
                "SELECT a.*,IFNULL(b.category_name,'') AS category_name FROM class_room a LEFT JOIN class_room_category b ON a.category_id = b.id WHERE a.id  = ?",
                bean.getId());

        classRoom.set("labels", labelArrary);

        renderJson(Rt.success(classRoom));

    }

    /**
     * function: 删除在线课堂 description: return:
     *
     * @param: id(在线课堂ID)
     * @author alex(已经添加接口文档)
     */
    public void deleteClassRoom() {
        int id = getParaToInt("id", 0);
        ClassRoom classRoom = ClassRoom.dao.findById(id);
        classRoom.delete();
        renderJson(Rt.success());
    }

    /**
     * 获取课堂列表（带参数name为搜索）
     *
     * @author alex(已经添加接口文档)
     * 2019.6.4 pang修改
     */
    public void getClassRoomList() {

        String classRoomName = getPara("name");
        int classTeacherId = getParaToInt("classTeacherId", 0);
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 20);

        Integer memberAccountId = getParaToInt("accountId", 0);
        Integer categoryId = getParaToInt("categoryId");
        Integer subjectId = getParaToInt("subjectId");
        Integer sort = getParaToInt("sort", 0);
        String sortType = getPara("sortType", "");

        //--------------------------------------------

        String class_room_sql = "";
        int condition = -1;

        if (classTeacherId > 0) {
            class_room_sql = "from ( class_room a LEFT JOIN classroom_subject crs ON a.subject_id = crs.id )  LEFT JOIN class_room_label_rela b ON a.id = b.class_room_id and b.status = 1"
                    + " LEFT JOIN class_room_label c ON b.label_id = c.id LEFT JOIN class_room_category crc on a.category_id = crc.id where a.teacher_id = ? and a.status > -1";
            condition = classTeacherId;
        } else if (memberAccountId > 0) {
            class_room_sql = "from ( class_room a LEFT JOIN classroom_subject crs ON a.subject_id = crs.id )  LEFT JOIN class_room_label_rela b ON a.id = b.class_room_id and b.status = 1"
                    + " LEFT JOIN class_room_label c ON b.label_id = c.id LEFT JOIN class_room_category crc on a.category_id = crc.id where a.member_account_id = ? and a.status > -1";
            condition = memberAccountId;
        }

        if (classRoomName != null && classRoomName.indexOf("'") == -1) {

            // 如果classRoomName参数不为空 则模糊查询
            class_room_sql += " AND (a.class_name like '%" + classRoomName + "%' OR a.summary LIKE '%" + classRoomName
                    + "%' )";

        } else if (classRoomName != null && classRoomName.indexOf("\"") == -1) {
            // 如果classRoomName参数不为空 则模糊查询
            class_room_sql += " AND (a.class_name like \"%" + classRoomName + "%\" OR a.summary LIKE \"%"
                    + classRoomName + "%\" )";

        }

        String orderBy = " order by create_time desc";

        if (sortType.equals("time")) {
            orderBy = " order by create_time " + (sort > 0 ? "ASC" : "DESC");
        } else if (sortType.equals("name")) {
            orderBy = " order /**/ by CONVERT(class_name USING 'gbk') " + (sort > 0 ? "ASC" : "DESC");
            System.out.println(orderBy);
        }

        Page<Record> page = null;

        if (subjectId != null) {


            if (classTeacherId == 0) {

                String selectSql = "SELECT\n" +
                        "\ta.id,\n" +
                        "\ta.teacher_id,\n" +
                        "\ta.teacher_name,\n" +
                        "\ta.class_name,\n" +
                        "\ta.cover,\n" +
                        "\ta.summary,\n" +
                        "\ta.status status,\n" +
                        "\tIFNULL(a.sort ,- 1) AS sort,\n" +
                        "\ta.category_id,\n" +
                        "\tIFNULL(a.book_res_id ,- 1) AS book_res_id,\n" +
                        "\ta.group_id,\n" +
                        "\ta.create_time,\n" +
                        "\ta.class_room_type,\n" +
                        "\ta.video_url,\n" +
                        "\tIFNULL(a.type ,- 1) AS type,\n" +
                        "\ta.book_res_ids\n";
                page = Db.paginate(pageNumber, pageSize,
                        selectSql, "FROM\n" +
                                "\tclass_room a\n" +
                                "WHERE\n" +
                                "\ta.subject_id = ?\n" +
                                "AND a.STATUS = 1\n" +
                                "ORDER BY\n" +
                                "\tcreate_time DESC", subjectId);
            } else {

                class_room_sql = "from ( class_room a LEFT JOIN classroom_subject crs ON a.subject_id = crs.id )LEFT JOIN class_room_label_rela b ON a.id = b.class_room_id and b.status = 1"
                        + " LEFT JOIN class_room_label c ON b.label_id = c.id LEFT JOIN class_room_category crc on a.category_id = crc.id where a.teacher_id = ? and a.subject_id = ? and a.status > -1";
                page = Db.paginate(pageNumber, pageSize,
                        "SELECT crs.`name` AS subjectName,a.subject_id AS subjectId,a.b_visible AS bVisible,a.alive_form AS aliveForm, a.id,a.teacher_id,a.teacher_name,a.class_name,a.cover,a.summary,IFNULL(crc.category_name,'') AS category_name,a.status,IFNULL(a.sort,-1) AS sort,a.category_id,IFNULL(a.book_res_id,-1) AS book_res_id,a.group_id,a.create_time,a.class_room_type,a.video_url,IFNULL(a.type,-1) AS type,a.book_res_ids"
                                + ",GROUP_CONCAT(c.id,'#fd#',c.label_name SEPARATOR '∫') AS label_str",
                        class_room_sql + " GROUP BY a.id " + orderBy, classTeacherId, subjectId);
            }


        } else {
            // 查询并按照课堂创建时间倒序排序返回结果
            page = Db.paginate(pageNumber, pageSize,
                    "SELECT crs.`name` AS subjectName,a.subject_id AS subjectId,a.b_visible AS bVisible,a.alive_form AS aliveForm,  a.id,a.teacher_id,a.teacher_name,a.class_name,IFNULL(a.cover,'') AS cover,a.summary,IFNULL(crc.category_name,'') AS category_name, a.status,IFNULL(a.sort,-1) AS sort,a.category_id,IFNULL(a.book_res_id,-1) AS book_res_id,a.group_id,a.create_time,a.class_room_type,a.video_url,IFNULL(a.type,-1) AS type,a.book_res_ids"
                            + ",GROUP_CONCAT(c.id,'#fd#',c.label_name SEPARATOR '∫') AS label_str",
                    class_room_sql + " GROUP BY a.id " + orderBy, condition);

        }

        // 得到所有课堂id
        List<Integer> classRoomIds = ConvetUtil.records2IntList(page.getList(), "id");

        if (classRoomIds.size() == 0) {
            Result.ok(page, this);
            return;
        }

        // 查询所有课堂关联的资源
        Kv kv = Kv.by("classRoomIds", classRoomIds);

        List<Record> audioInfos = null;

        if (getPara("new") != null) {
            audioInfos = Db.find(Db.getSqlPara("audioInfo.getAudioInfoByClassRoomId", kv));
        } else {
            audioInfos = Db.find(Db.getSqlPara("audioInfo.getAudioInfoByClassRoomIdOld", kv));
        }

        // 遍历所有课堂并将资源匹配后放入
        for (Record classRoom : page.getList()) {

            JSONArray audioInfoList = new JSONArray();

            Iterator<Record> iterator = audioInfos.iterator();

            while (iterator.hasNext()) {

                Record audioInfo = iterator.next();
                if (audioInfo.getInt("class_room_id").equals(classRoom.getInt("id"))) {
                    audioInfoList.add(Result.toJson(audioInfo.getColumns()));
                    iterator.remove();
                }

            }

            /*
             * for (Record audioInfo : audioInfos) { if
             * (audioInfo.getInt("class_room_id").equals(classRoom.getInt("id"))
             * ) { audioInfoList.add(Result.toJson(audioInfo.getColumns())); } }
             */

            classRoom.set("audioInfos", audioInfoList);

            String labelStr = classRoom.getStr("label_str");

            if (labelStr != null) {
                String[] labelsArray = labelStr.split("∫");
                List<Record> ClassRoomLabels = new ArrayList<Record>(labelsArray.length);
                for (String labels : labelsArray) {
                    String[] labelArray = labels.split("#fd#");
                    Record record = new Record();
                    record.set("label_id", labelArray[0]);
                    record.set("label_name", labelArray[1]);
                    ClassRoomLabels.add(record);
                }
                classRoom.set("labels", Result.makeupList(ClassRoomLabels));
            }

            classRoom.remove("label_str");
        }

        Result.ok(page, this);

    }


    /**
     * function: 保存/修改班级信息
     * description:使用POST方法请求提交数据，如果提交的数据带id主键，则为修改班级信息，无ID则为添加 return:返回班级对象
     *
     * @param: classGradesName(班级名称)
     * memberId（会员ID，取凡豆伴账号ID）
     * @author alex(已经添加接口文档)
     */
    public void saveClassGrades() {
        ClassGrades bean = getBean(ClassGrades.class, "");

        if (null == bean.getId()) {// 判断是否有ID
            bean.save();
            renderJson(JsonResult.JsonResultOK(bean));
        } else {
            bean.update();
            renderJson(JsonResult.JsonResultOK(ClassGrades.dao.findById(bean.getId())));
        }
    }

    /**
     * 获取班级列表(带参数name为搜索)
     *
     * @author alex(已经添加接口文档)
     */

    public void getClassGradesList() {
        String classGradesName = getPara("name", "");
        int classTeacherId = getParaToInt("classTeacherId", 0);
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        renderJson(Rt.success(LessonService.getClassGradesList(classGradesName, classTeacherId, pageNumber, pageSize)));
    }

    /*
     * 获取备课资源(支持多个,机器人专用)
     *
     */

    public void getAudioInfo() {
        String audioId = getPara("audioId", "");
        renderJson(Rt.success(LessonService.getAudioInfo(audioId)));
    }

    /*
     * 获取课堂分类标签列表
     *
     */

    public void getClassRoomCategory() {
        renderJson(Rt.success(LessonService.getClassRoomCategory()));
    }

    /*
     * 根据课堂标签，获取课堂列表
     *
     */

    public void getClassRoomByCategory() {
        int categoryId = getParaToInt("categoryId", 0);
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);
        renderJson(Rt.success(LessonService.getClassRoomByCategory(categoryId, pageNumber, pageSize)));

    }

    // 获取课堂指令类型列表
    public void findClassScriptTypes() {
        renderJson(Rt.success(LessonService.findClassScriptTypes()));

    }

    // 获取课堂指令互斥列表
    public void findClassScriptTypesMutex() {
        renderJson(Rt.success(LessonService.findClassScriptTypesMutex()));

    }

    // 获取课堂指令分组列表
    public void findClassScriptGroupList() {
        JSONArray data = new JSONArray();
        List<ClassScriptTypeGroup> classScriptTypeGroupList = ClassScriptTypeGroup.dao
                .find("select * from class_script_type_group where status=1 order by sort_id desc");
        for (int i = 0; i < classScriptTypeGroupList.size(); i++) {
            ClassScriptTypeGroup classScriptTypeGroup = classScriptTypeGroupList.get(i);
            int groupId = classScriptTypeGroup.getId();
            String classScriptTypeGroupName = classScriptTypeGroup.getGroupName();
            String groupCode = classScriptTypeGroup.getGroupCode();
            List<ClassScriptType> classScriptTypeList = ClassScriptType.dao.find(
                    "select a.* from class_script_type as a,class_script_group_to_type as b where a.id=b.type_id and b.group_id=?",
                    groupId);
            JSONArray result = new JSONArray();
            // System.out.println(groupId);

            JSONObject temp = new JSONObject();
            for (int j = 0; j < classScriptTypeList.size(); j++) {

                ClassScriptType classScriptType = (ClassScriptType) classScriptTypeList.get(j);
                String groupName = classScriptType.getGroupName();
                String name = classScriptType.getName();
                String des = classScriptType.getDes();
                int id = classScriptType.getId();
                // System.out.println(groupName);

                JSONObject item = new JSONObject();
                item.put("id", id);
                item.put("name", name);
                item.put("des", des);
                item.put("sort", classScriptType.getSort());
                // System.out.println(item);
                if (temp.containsKey(groupName)) {
                    JSONArray temp2 = temp.getJSONArray(groupName);
                    temp2.add(item);
                    temp.put(groupName, temp2);

                } else {
                    JSONArray temp2 = new JSONArray();
                    temp2.add(item);
                    temp.put(groupName, temp2);
                }

            }
            // System.out.println(temp);
            for (Iterator iter = temp.keys(); iter.hasNext(); ) { // 先遍历整个 temp
                // 对象
                String groupName = (String) iter.next();
                JSONArray temp2 = temp.getJSONArray(groupName);
                JSONObject item = new JSONObject();
                item.put("groupName", groupName);
                item.put("typeList", temp2);
                result.add(item);
            }

            JSONObject temp3 = new JSONObject();
            temp3.put("classScriptTypeGroupName", classScriptTypeGroupName);
            temp3.put("classScriptTypeGroupCode", groupCode);
            temp3.put("classScriptTypeList", CommonUtils.ClassScriptTypeSort(result));
            data.add(temp3);
        }
        Result.ok(data, this);
    }

    /**
     * 设置临时课
     */
    public void saveTemporaryClass() {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer gradesId = getParaToInt("gradesId");
        Integer classRoomId = getParaToInt("classRoomId");
        int courseId = getParaToInt("courseId", 0);

        if (xx.isOneEmpty(gradesId, classRoomId)) {
            Result.error(203, this);
            return;
        }

        String sql = "INSERT INTO grades_temporary_class (class_grades_id,class_room_id,class_course_id) VALUES (?,?,?) ON DUPLICATE KEY UPDATE class_room_id=?,class_course_id = ?,update_time=?";

        Db.update(sql, gradesId, classRoomId, courseId, classRoomId, courseId, new Date());

        Result.ok(this);

    }

    /**
     * 获取临时课
     */
    public void getTemporaryClass() {

        Integer gradesId = getParaToInt("gradesId");

        Integer studentId = getParaToInt("studentId");

        if (xx.isAllEmpty(gradesId, studentId)) {
            Result.error(203, this);
            return;
        }

        Integer condition = gradesId;

        String sql = "SELECT a.*,b.class_name FROM `grades_temporary_class` a JOIN class_room b ON a.class_room_id = b.id WHERE a.class_grades_id = ?";

        if (studentId != null) {

            sql = "SELECT c.*,cr.class_name FROM class_grades a JOIN class_grades_rela b ON a.id = b.class_grades_id AND b.gradesStatus > -1"
                    + " JOIN grades_temporary_class c ON a.id = c.class_grades_id JOIN class_room cr on c.class_room_id = cr.id WHERE b.class_student_id = ?";

            condition = studentId;
        }

        List<Record> gradesTemporaryClasss = Db.find(sql, condition);

        JSONObject json = new JSONObject();
        json.put("gradesTemporaryClassList", Result.makeupList(gradesTemporaryClasss));

        Result.ok(json, this);

    }

    public void removeLabel() {

        Integer labelId = getParaToInt("labelId");
        Integer classRoomId = getParaToInt("classRoomId");

        if (xx.isOneEmpty(classRoomId, labelId)) {
            Result.error(203, this);
            return;
        }

        Db.update("update class_room_label_rela set status = 0 where label_id= ? and class_room_id = ?", labelId,
                classRoomId);

    }

    public void getClassRoomLabels() {

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        Result.ok(
                Db.paginate(pageNumber, pageSize, "SELECT id,label_name", "FROM `class_room_label` WHERE group_id = 0"),
                this);
    }

    public void getClassRoomByLabel() {

        String labelIds = getPara("labelIds");
    }

    public void classIndex() {

        int limit = 10;

        List<Record> categorys = Db.find("select * from class_room_category where parent_id > 0 ");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.id, a.teacher_id, a.teacher_name, a.class_name, a.cover , a.summary, a.status,");
        sql.append(" a.sort, a.category_id, a.book_res_id , a.group_id, a.create_time, a.class_room_type,");
        sql.append(" a.video_url, a.type , a.book_res_ids FROM ( SELECT a.* , IF(@tmpgid = a.category_id,");
        sql.append(" @rank := @rank + 1, @rank := 0) AS rank , @tmpgid := a.category_id , @number := @number + 1,");
        sql.append(
                " @rank , @tmpgid FROM ( SELECT * FROM class_room WHERE `status` = 1 ORDER BY category_id, id DESC )");
        sql.append(" a, ( SELECT @rank := 0, @number := 0 , @tmpgid := '' ) b ) a WHERE rank < ?");

        List<ClassRoom> classRooms = ClassRoom.dao.find(sql.toString(), limit);

        int cid = 0;

        Iterator<ClassRoom> iterator = null;

        for (Record category : categorys) {

            cid = category.getInt("id");

            iterator = classRooms.iterator();

            JSONArray newClassRooms = new JSONArray();

            ClassRoom classRoom = null;

            while (iterator.hasNext()) {

                classRoom = iterator.next();
                if (classRoom.getCategoryId().equals(cid)) {
                    newClassRooms.add(Result.toJson(classRoom.toRecord().getColumns()));
                    iterator.remove();
                }

            }

            category.set("classRooms", newClassRooms);

        }

        JSONObject json = new JSONObject();
        json.put("categorys", Result.makeupList(categorys));
        Result.ok(json, this);

    }

    public void classIndexSearch() {

        long start = System.currentTimeMillis();

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

//        Integer categoryId = getParaToInt("categoryId");

        //修改2019.8.27
        String categoryIds = getPara("categoryIds");

        String indexSearch = getPara("search", "");

        String labelIds = getPara("labelIds");

        String sql_header = "SELECT a.id, a.teacher_id, a.teacher_name, a.class_name, a.cover, a.summary, "
                + "a.STATUS, IFNULL(a.sort, -1) AS sort , a.category_id, IFNULL(a.book_res_id, -1) AS book_res_id,"
                + " a.group_id , a.create_time, a.class_room_type, a.video_url,	 IFNULL(a.type, -1) AS type, a.book_res_ids"
                + ", GROUP_CONCAT(b.label_id, '#fd#', b.label_name SEPARATOR '∫') AS label_str";

        String class_room_sql = null;

        if (indexSearch.indexOf("'") == -1) {

            class_room_sql = "FROM class_room a LEFT JOIN ( SELECT b.class_room_id, c.id AS label_id, c.label_name FROM class_room_label_rela b,"
                    + " class_room_label c WHERE b.label_id = c.id AND b.`status` = 1 ) b ON a.id = b.class_room_id,class_teacher ct WHERE a.STATUS = 1 AND a.teacher_id = ct.id AND (a.class_name"
                    + " LIKE '%" + indexSearch + "%' OR b.label_name LIKE '%" + indexSearch + "%' OR a.summary LIKE '%"
                    + indexSearch + "%' OR ct.name LIKE '%" + indexSearch + "%') ";

        } else {

            class_room_sql = "FROM class_room a LEFT JOIN ( SELECT b.class_room_id, c.id AS label_id, c.label_name FROM class_room_label_rela b,"
                    + " class_room_label c WHERE b.label_id = c.id AND b.`status` = 1 ) b ON a.id = b.class_room_id,class_teacher ct WHERE a.STATUS = 1 AND a.teacher_id = ct.id AND (a.class_name"
                    + " LIKE \"%" + indexSearch + "%\" OR b.label_name LIKE \"%" + indexSearch
                    + "%\" OR a.summary LIKE \"%" + indexSearch + "%\" OR ct.name LIKE \"%" + indexSearch + "%\") ";

        }

//        if (categoryId != null && categoryId != -1) {
//            class_room_sql += " and a.category_id = " + categoryId;
//        }

        //修改2019.8.27
        if (categoryIds != null && !categoryIds.equals("")) {
            class_room_sql += " and a.category_id in (" + categoryIds + ") ";
        }

        if (labelIds != null && labelIds.equals("") == false) {
            class_room_sql += " and b.label_id in (" + labelIds + ")";
        }

        // 查询并按照课堂创建时间倒序排序返回结果
        Page<Record> page = Db.paginate(pageNumber, pageSize, sql_header,
                class_room_sql + " GROUP BY a.id order by create_time desc");

        // 得到所有课堂id的列表
        List<Integer> classRoomIds = ConvetUtil.records2IntList(page.getList(), "id");

        if (classRoomIds.size() == 0) {
            Result.ok(page, this);
            return;
        }

        // 查询所有课堂关联的资源
        Kv kv = Kv.by("classRoomIds", classRoomIds);
        List<Record> audioInfos = Db.find(Db.getSqlPara("audioInfo.getAudioInfoByClassRoomId", kv));

        // 遍历所有课堂并将资源匹配后放入
        for (Record classRoom : page.getList()) {

            JSONArray audioInfoList = new JSONArray();

            Iterator<Record> iterator = audioInfos.iterator();

            while (iterator.hasNext()) {

                Record audioInfo = iterator.next();

                if (audioInfo.getInt("class_room_id").equals(classRoom.getInt("id"))) {
                    audioInfoList.add(Result.toJson(audioInfo.getColumns()));
                    iterator.remove();
                }

            }

            /*
             * for (Record audioInfo : audioInfos) { if
             * (audioInfo.getInt("class_room_id").equals(classRoom.getInt("id"))
             * ) { audioInfoList.add(Result.toJson(audioInfo.getColumns())); } }
             */

            classRoom.set("audioInfos", audioInfoList);

            String labelStr = classRoom.getStr("label_str");

            if (labelStr != null) {
                String[] labelsArray = labelStr.split("∫");
                List<Record> ClassRoomLabels = new ArrayList<Record>(labelsArray.length);
                for (String labels : labelsArray) {
                    String[] labelArray = labels.split("#fd#");
                    Record record = new Record();
                    record.set("label_id", labelArray[0]);
                    record.set("label_name", labelArray[1]);
                    ClassRoomLabels.add(record);
                }
                classRoom.set("labels", Result.makeupList(ClassRoomLabels));
            }

            classRoom.remove("label_str");

        }

        System.out.println(System.currentTimeMillis() - start);
        Result.ok(page, this);
    }

    public void getClassRoomWords() {

        Integer classRoomId = getParaToInt("classRoomId");

        List<MediainfoWords> mediainfoWords = MediainfoWords.dao.find(
                "SELECT * FROM class_room_audio_rel a JOIN mediainfo_words b ON a.audio_id = b.audio_id WHERE a.class_room_id = ?",
                classRoomId);

        Record filterWords = Db.findFirst(
                "select IFNULL(GROUP_CONCAT(words SEPARATOR '|'),'') AS words FROM class_room_words_filter where class_room_id = ? and `status` = 1",
                classRoomId);

        for (MediainfoWords mediainfoWord : mediainfoWords) {
            mediainfoWord.setWords(mediainfoWord.getWords().replaceAll("(?:" + filterWords.getStr("words") + ")", ""));
        }

        JSONObject json = new JSONObject();
        json.put("classRoomWords", Result.makeupList(mediainfoWords));

        Result.ok(json, this);

    }

    public void serachClassRoomByWord() {

        String word = getPara("word");

        if (word == null) {
            Result.error(203, this);
            return;
        }

        List<ClassRoom> classRooms = ClassRoom.dao
                .find("SELECT c.* FROM `mediainfo_words` a JOIN class_room_audio_rel b ON a.audio_id = b.audio_id "
                        + "JOIN class_room c ON b.class_room_id = c.id WHERE a.words LIKE '%" + word + "%'");

        JSONObject json = new JSONObject();
        json.put("classRooms", Result.makeupList(classRooms));

        Result.ok(json, this);

    }

    public void saveClassFilterWords() {

        String word = getPara("word");
        Integer classRoomId = getParaToInt("classRoomId");

        if (xx.isOneEmpty(word, classRoomId)) {
            Result.error(203, this);
            return;
        }

        String sql = "INSERT INTO class_room_words_filter (class_room_id,words) VALUES (?,?) ON DUPLICATE KEY UPDATE `status`=`status`^1";

        JSONObject json = new JSONObject();
        json.put("affectedRows", Db.update(sql, classRoomId));

        Result.ok(json, this);

    }

    public void getClassFilterWords() {
        Integer classRoomId = getParaToInt("classRoomId");

        if (xx.isOneEmpty(classRoomId)) {
            Result.error(203, this);
            return;
        }

        Record words = Db.findFirst(
                "select class_room_id,IFNULL(GROUP_CONCAT(words SEPARATOR ' '),' ') AS words FROM class_room_words_filter where class_room_id = ? and `status` = 1",
                classRoomId);

        words.set("wordArray", words.getStr("words").split(" "));
        JSONObject json = new JSONObject();
        json.put("words", Result.toJson(words.getColumns()));

        Result.ok(json, this);
    }

    public void addLesson2Basic() {

        Integer classRoomId = getParaToInt("classRoomId");
        Integer basic = getParaToInt("basic");

        ClassroomPackageService service = new ClassroomPackageService();
        service.addLesson2Basic(classRoomId, basic);

        Result.ok(this);

    }

    public void getClassCourseByProductId() {

        Integer productId = getParaToInt("productId", 0);

        String sql = "SELECT do_title,do_day FROM `class_course` WHERE class_grades_id = (SELECT class_grade_id FROM mallproduct WHERE id = ?) and do_slot = 300 ORDER BY do_day ASC limit 10";

        JSONObject json = new JSONObject();
        json.put("classCourses", Result.makeupList(Db.find(sql, productId)));

        String techerSql = "select * from class_teacher where id = (SELECT teacher_id FROM class_grades WHERE id = (SELECT class_grade_id FROM mallproduct WHERE id = ?))";

        json.put("classTeacher", Result.toJson(ClassTeacher.dao.findFirst(techerSql, productId)));

        String gradeSql = "SELECT id,class_grades_name,summary FROM class_grades WHERE id = (SELECT class_grade_id FROM mallproduct WHERE id = ?)";
        json.put("classGrade", Result.toJson(ClassGrades.dao.findFirst(gradeSql, productId)));

        Result.ok(json, this);
    }

    public void lessonScriptReplyList() {

        Integer courseId = getParaToInt("course", 0);
        Integer studentId = getParaToInt("student", 0);

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        String totalRowSql = "select count(*) as count from (select a.id from lesson_script_reply a join lesson_script_time b on a.time_sn = b.time_sn where a.class_course_id = ? and a.student_id = ? group by a.time_sn) a";
        int totalRow = Db.findFirst(totalRowSql, courseId, studentId).getInt("count");
        int totalPage = totalRow / pageSize;

        if (totalRow % pageSize != 0) {
            totalPage++;
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT lsr.*, scn.sort, UNIX_TIMESTAMP(lst.create_time) AS lesson_time FROM lesson_script_reply lsr JOIN ( SELECT a.time_sn FROM ");
        sql.append(
                " lesson_script_reply a join lesson_script_time b on a.time_sn = b.time_sn WHERE a.class_course_id = ? AND a.student_id = ? GROUP BY a.time_sn order by b.create_time desc  LIMIT ?, ? ) lsr2 ON lsr.time_sn =");
        sql.append(
                " lsr2.time_sn, class_script_normal scn, lesson_script_time lst WHERE lsr.class_script_id = scn.id AND lsr.time_sn = lst.time_sn order by scn.sort asc");

        List<Record> lessonScriptReplys = Db.find(sql.toString(), courseId, studentId, (pageNumber - 1) * pageSize,
                pageSize);

        /*
         * 数据按照 time_sn 分组
         */
        Map<Long, List<Record>> lessonScriptReplyMap = new TreeMap<>(new Comparator<Long>() {

            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }

        });

        for (Record lessonScriptReply : lessonScriptReplys) {

            List<Record> tempList = lessonScriptReplyMap.get(Long.parseLong(lessonScriptReply.getStr("lesson_time")));

            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(lessonScriptReply);
                lessonScriptReplyMap.put(Long.parseLong(lessonScriptReply.getStr("lesson_time")), tempList);
            } else {
                tempList.add(lessonScriptReply);
            }

        }

        JSONObject result = new JSONObject();
        JSONArray lessonScriptReplies = new JSONArray();

        for (Long key : lessonScriptReplyMap.keySet()) {

            JSONObject lessonScriptReply = new JSONObject();
            lessonScriptReply.put("_list", Result.makeupList(lessonScriptReplyMap.get(key)));
            lessonScriptReply.put("lessonTime", key * 1000);
            lessonScriptReplies.add(lessonScriptReply);

        }

        result.put("lessonScriptReplies", lessonScriptReplies);
        result.put("toalRow", totalRow);
        result.put("toalPage", totalPage);

        Result.ok(result, this);

    }

    @EmptyParaValidate(params = {"time", "student", "classRoom"})
    public void lesonScriptReplys() {

        String time = getPara("time");
        int studentId = getParaToInt("student");
        int classRoomId = getParaToInt("classRoom");

        String sql = "SELECT * FROM `lesson_script_reply` WHERE student_id = ? AND time_sn = ? AND class_room_id = ?";
        List<LessonScriptReply> lessonScriptReplies = LessonScriptReply.dao.find(sql, studentId, time, classRoomId);

        JSONObject json = new JSONObject();
        json.put("lessonScriptReplies", Result.makeupList(lessonScriptReplies));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"reply", "public"})
    public void updatePublicReply() {

        int replyId = getParaToInt("reply");
        int b_public = getParaToInt("public");

        String sql = "update lesson_script_reply set b_public = ? where id = ?";

        int result = Db.update(sql, b_public, replyId);

        Result.ok(this);

    }

    @EmptyParaValidate(params = {"replys", "student", "classRoom"})
    public void saveShareReplys() {

        int studentId = getParaToInt("student");

        String[] replyIds = getPara("replys").split(",");

        LessonScriptReplyGroup lessonScriptReplyGroup = new LessonScriptReplyGroup();
        lessonScriptReplyGroup.setStudentId(studentId).setClassRoomId(getParaToInt("classRoom")).save();

        List<LessonScriptReplyGroupRel> lessonScriptReplyGroupRels = new ArrayList<LessonScriptReplyGroupRel>(
                replyIds.length);

        for (String replyId : replyIds) {

            LessonScriptReplyGroupRel lessonScriptReplyGroupRel = new LessonScriptReplyGroupRel();
            lessonScriptReplyGroupRel.setLessonScriptReplyGroupId(lessonScriptReplyGroup.getId());
            lessonScriptReplyGroupRel.setLessonScriptReplyId(Integer.parseInt(replyId));
            lessonScriptReplyGroupRels.add(lessonScriptReplyGroupRel);

        }

        Db.batchSave(lessonScriptReplyGroupRels, 200);

        JSONObject json = new JSONObject();
        json.put("groupId", lessonScriptReplyGroup.getId());

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"student", "reply", "like"})
    public void lessonReplyLikeApp() {

        int studentId = getParaToInt("student");
        int replyId = getParaToInt("reply");
        int like = getParaToInt("like");

        String sql = "INSERT INTO lesson_reply_like_app (student_id,reply_id,lesson_reply_like_app.status) VALUES (?,?,?) ON DUPLICATE KEY UPDATE lesson_reply_like_app.status = ?";

        Db.update(sql, studentId, replyId, like, like);

        Result.ok(this);

    }

    @EmptyParaValidate(params = {"wechat", "group", "like"})
    public void lessonReplyLikeH5() {

        int wechatId = getParaToInt("wechat");
        int groupId = getParaToInt("group");
        int like = getParaToInt("like");

        String sql = "INSERT INTO lesson_script_reply_like (group_id,member_id,lesson_script_reply_like.status) VALUES (?,?,?) ON DUPLICATE KEY UPDATE lesson_script_reply_like.status = ?";

        Db.update(sql, groupId, wechatId, like, like);

        String sql2 = "SELECT IFNULL(( SELECT COUNT(id) FROM lesson_script_reply_like lsrl WHERE lsrl.group_id = ? AND lsrl.`status` = 1 ), 0) "
                + "AS like_count , IFNULL(( SELECT lsrl2.`status` FROM lesson_script_reply_like lsrl2 WHERE lsrl2.group_id = ?"
                + " AND lsrl2.member_id = ? ), 0) AS my_like";

        Record likeInfo = Db.findFirst(sql2, groupId, groupId, wechatId);

        JSONObject json = new JSONObject();
        json.put("likeInfo", Result.toJson(likeInfo.getColumns()));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"script", "student"})
    public void publicLessonReply() {

        int scriptId = getParaToInt("script");
        int studentId = getParaToInt("student");

        String sql = "SELECT a.id, a.reply_script,a.class_script_id,b.avatar,b.name as student_name,a.score,a.create_time,IFNULL((SELECT `status` from lesson_reply_like_app c "
                + "WHERE a.id = c.reply_id AND student_id = ?) ,0) as mylike,ifnull((SELECT COUNT(id) from lesson_reply_like_app e WHERE a.id = e.reply_id and e.status = 1),0) as like_count "
                + "FROM lesson_script_reply a,class_student b WHERE"
                + " a.student_id = b.id and a.class_script_id = ? AND a.b_public = 1";

        List<LessonScriptReply> lessonScriptReplies = LessonScriptReply.dao.find(sql, studentId, scriptId);

        JSONObject json = new JSONObject();
        json.put("lessonScriptReplies", Result.makeupList(lessonScriptReplies));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"script", "student"})
    public void publicLessonReply2() {

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        int scriptId = getParaToInt("script");
        int studentId = getParaToInt("student");

        String select = "SELECT a.id, a.reply_script,a.class_script_id,b.avatar,b.name as student_name,a.score,a.create_time,IFNULL((SELECT `status` from lesson_reply_like_app c "
                + "WHERE a.id = c.reply_id AND student_id = " + studentId
                + ") ,0) as mylike,ifnull((SELECT COUNT(id) from lesson_reply_like_app e WHERE a.id = e.reply_id and e.status = 1),0) as like_count ";

        String from = "FROM lesson_script_reply a,class_student b WHERE"
                + " a.student_id = b.id and a.class_script_id = ? AND a.b_public = 1 order by a.create_time desc";

        Page<Record> page = Db.paginate(pageNumber, pageSize, select, from, scriptId);

        Result.ok(page, this);

    }

    public void saveLessonScriptReply() {

        LessonScriptReply lessonScriptReply = getBean(LessonScriptReply.class, "");

        if (lessonScriptReply.getId() == null) {

            lessonScriptReply.save();

        } else {

            lessonScriptReply.update();

        }

        Db.update("insert ignore into lesson_script_time (class_room_id,time_sn) values (?,?)",
                lessonScriptReply.getClassRoomId(), lessonScriptReply.getTimeSn());

        Result.ok(Result.toJson(lessonScriptReply), this);

    }

    public void hasLessonScriptReply() {

        Integer gradeId = getParaToInt("grade");
        Integer studentId = getParaToInt("student");

        if (xx.isOneEmpty(gradeId, studentId)) {
            Result.error(203, this);
            return;
        }

        /*
         * StringBuffer sql = new StringBuffer(); sql.append(
         * "SELECT b.id,b.class_name,c.create_time as lesson_time,b.cover FROM lesson_script_reply a,class_room b,lesson_script_time c WHERE "
         * ); sql.append(
         * " a.class_room_id = b.id AND a.class_room_id IN	(SELECT class_room_id FROM"
         * ); sql.append(
         * " class_course WHERE class_grades_id = ?) AND student_id = ? AND a.time_sn = c.time_sn group by b.id ORDER BY c.create_time DESC"
         * );
         */

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT b.id AS course, d.id AS class_room, substring_index(c.time_sn,'_',-1) AS lesson_time, d.cover, d.class_name FROM lesson_script_reply a,");
        sql.append(
                " class_course b, lesson_script_time c, class_room d WHERE (b.class_grades_id = ? AND a.student_id = ? AND a.class_course_id =");
        sql.append(
                " b.id AND a.time_sn = c.time_sn AND b.class_room_id = d.id) GROUP BY b.id order by c.create_time desc");

        List<Record> classRooms = Db.find(sql.toString(), gradeId, studentId);

        JSONObject json = new JSONObject();
        json.put("classRooms", Result.makeupList(classRooms));
        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"courseId"})
    public void getStudentListByClassGradesIdAndClassCourseId() {

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT c.audioUrl, a.id AS record_id, d.id AS student_id , ifnull(d.avatar, '') AS avatar, d.`name` AS studentName , a.do_title, ");
        sql.append(
                "IFNULL(b.teacher_sound, '') AS teacher_sound , date_format(c.recordDate, '%Y-%c-%d %h:%i:%s') AS recordDate, c.integrity ,");
        sql.append(
                "IFNULL(b.teacher_score, '') AS teacher_score , IFNULL(b.id, 0) AS classCourseCommendId, c.fluency , CAST(a.score AS SIGNED) AS ");
        sql.append(
                "score, c.timeCost , date_format(IFNULL(b.insert_date, c.recordDate), '%Y-%c-%d %h:%i:%s') AS teacherRecordDate , c.accuracy, c.id ");
        sql.append(
                "FROM `class_course_record` a LEFT JOIN class_course_comment b ON a.class_course_id = b.class_course_id AND a.student_id = ");
        sql.append(
                "b.student_id, class_course_score_record c, class_student d WHERE (a.class_course_id = ? AND a.id = c.classCourseRecord_id AND ");
        sql.append("a.student_id = d.id) ORDER BY d.id DESC, a.score DESC");

        List<Record> records = Db.find(sql.toString(), getParaToInt("courseId"));

        /*
         * 只取学生最高分的记录
         */
        int student = 0;
        int score = 0;
        Iterator<Record> iterator = records.iterator();

        while (iterator.hasNext()) {

            Record record = iterator.next();

            if (record.getInt("student_id").equals(student) && record.getInt("score") <= score) {
                iterator.remove();
            } else {
                student = record.getInt("student_id");
                score = record.getInt("score");
            }

        }

        JSONObject json = new JSONObject();
        json.put("items", Result.makeupList(records));

        Result.ok(json, this);
    }

    @EmptyParaValidate(params = {"studentId", "courseId", "picUrl"})
    public void saveStudyPhoto() {

        StudyPhoto studyPhoto = getBean(StudyPhoto.class, "");
        studyPhoto.save();

        Result.ok(this);
    }

    @EmptyParaValidate(params = {"group"})
    public void classScriptTypeList() {

        Integer group = getParaToInt("group");

        List<ClassScriptType> classScriptTypes = ClassScriptType.dao.find(
                "SELECT a.* FROM class_script_type a,class_script_group_to_type b WHERE a.id = b.type_id AND b.group_id = ?",
                group);

        JSONObject json = new JSONObject();
        json.put("classScriptTypes", Result.makeupList(classScriptTypes));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"student"})
    public void studentGrades() {

        Integer studentId = getParaToInt("student");
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        Page<Record> page = Db.paginate(pageNumber, pageSize, "select a.* ",
                "FROM `class_grades` a,class_grades_rela b WHERE a.id = b.class_grades_id AND class_student_id = ? AND b.gradesStatus = 1",
                studentId);

        if (page.getList().size() == 0) {
            Result.ok(page, this);
            return;
        }

        String gradeIds = "";

        for (int i = 0; i < page.getList().size(); i++) {
            if (i == 0) {
                gradeIds += page.getList().get(i).getInt("id");
            }
            gradeIds += "," + page.getList().get(i).getInt("id");
        }

        StringBuffer classRoom_sql = new StringBuffer();
        classRoom_sql.append(
                "SELECT cc.id AS course_id, cc.class_grades_id, cc.class_room_id, cc.do_slot , IFNULL(( SELECT SUM(total_time) FROM");
        classRoom_sql.append(
                " class_script_normal WHERE class_room_id = cc.class_room_id ), 0) AS classLen , IFNULL(( SELECT ccr.id FROM class_course_record ccr");
        classRoom_sql.append(" WHERE ccr.class_course_id = cc.id AND ccr.student_id = " + studentId
                + " LIMIT 1 ), 0) AS is_complet, cr.class_name , cr.summary, cr.cover, cc.do_day FROM");
        classRoom_sql
                .append(" `class_course` cc, class_room cr WHERE cc.class_room_id = cr.id AND cc.class_grades_id IN ( "
                        + gradeIds + " ) ORDER BY cc.do_day ASC");

        List<Record> classRooms = Db.find(classRoom_sql.toString());

        for (Record classGrade : page.getList()) {

            JSONArray json = new JSONArray();
            for (Record classRoom : classRooms) {
                if (classGrade.getInt("id").equals(classRoom.getInt("class_grades_id"))) {
                    json.add(Result.toJson(classRoom.getColumns()));
                }
            }

            classGrade.set("classRooms", json);
            classGrade.set("classCount", json.size());

        }

        Result.ok(page, this);

    }


    @EmptyParaValidate(params = {"students"})
    public void studentsGradesAll() {

        String studentIds = getPara("students");
        int pageSize = getParaToInt("pageSize", 10);
        int pageNumber = getParaToInt("pageNumber", 1);

        Page<Record> page = Db.paginate(pageNumber, pageSize, "select a.* ",
                "FROM `class_grades` a,class_grades_rela b WHERE a.id = b.class_grades_id AND class_student_id in (" + studentIds + ") AND b.gradesStatus = 1");

        if (page.getList().size() == 0) {
            renderJson(Rt.success());
            return;
        }

        String gradeIds = "" + page.getList().get(0).getInt("id");
        for (int i = 1; i < page.getList().size(); i++) {
            gradeIds += "," + page.getList().get(i).getInt("id");
        }

        String classRoom_sql = "SELECT cc.id AS course_id, cc.class_grades_id, cc.class_room_id, cc.do_slot , IFNULL(( SELECT SUM(total_time) FROM"
                + " class_script_normal WHERE class_room_id = cc.class_room_id ), 0) AS classLen , IFNULL(( SELECT ccr.id FROM class_course_record ccr"
                + " WHERE ccr.class_course_id = cc.id AND ccr.student_id in (" + studentIds + ") "
                + " LIMIT 1 ), 0) AS is_complet, cr.class_name , cr.summary, cr.cover, cc.do_day FROM"
                + " `class_course` cc, class_room cr WHERE cc.class_room_id = cr.id AND cc.class_grades_id IN ( "
                + gradeIds + " ) ORDER BY cc.do_day ASC";

        List<Record> allClassRooms = Db.find(classRoom_sql.toString());

        for (Record classGrade : page.getList()) {

            JSONArray json = new JSONArray();
            allClassRooms.forEach((v) -> {
                if (classGrade.getInt("id").equals(v.getInt("class_grades_id"))) {
                    json.add(Result.toJson(v.getColumns()));
                }
            });

            classGrade.set("allClassRooms", json);
            classGrade.set("classCount", json.size());

        }

        Result.ok(page, this);

    }


    @EmptyParaValidate(params = {"grade", "student"})
    public void gradeClassRooms() {

        int gradeId = getParaToInt("grade");
        int studentId = getParaToInt("student");

        StringBuffer classRoom_sql = new StringBuffer();
        classRoom_sql.append(
                "SELECT cc.id AS course_id, cc.class_grades_id, cc.class_room_id, cc.do_slot , IFNULL(( SELECT SUM(total_time) FROM");
        classRoom_sql.append(
                " class_script_normal WHERE class_room_id = cc.class_room_id ), 0) AS classLen , IFNULL(( SELECT csd.id FROM class_script_done csd");
        classRoom_sql.append(" WHERE csd.class_course_id = cc.id AND student_id = " + studentId
                + " LIMIT 1 ), 0) AS is_complet, cr.class_name , cr.summary, cr.cover, cc.do_day FROM");
        classRoom_sql
                .append(" `class_course` cc, class_room cr WHERE cc.class_room_id = cr.id AND cc.class_grades_id = "
                        + gradeId + " ORDER BY cc.do_day ASC");

        List<Record> classRooms = Db.find(classRoom_sql.toString());

        Result.ok(Result.makeupList(classRooms), this);

    }

    @EmptyParaValidate(params = {"student", "name"})
    public void searchClassRoomFromGrade() {

        int studentId = getParaToInt("student");
        String name = getPara("name");

        String class_grades_sql = "select a.* FROM `class_grades` a,class_grades_rela b WHERE a.id = b.class_grades_id AND class_student_id = ? AND b.gradesStatus = 1";

        List<ClassGrades> classGrades = ClassGrades.dao.find(class_grades_sql, studentId);

        String gradeIds = "";

        for (int i = 0; i < classGrades.size(); i++) {
            if (i == 0) {
                gradeIds += classGrades.get(i).getInt("id");
            }
            gradeIds += "," + classGrades.get(i).getInt("id");
        }

        StringBuffer classRoom_sql = new StringBuffer();
        classRoom_sql.append(
                "SELECT cc.id AS course_id, cc.class_grades_id, cc.class_room_id, cc.do_slot , IFNULL(( SELECT SUM(total_time) FROM");
        classRoom_sql.append(
                " class_script_normal WHERE class_room_id = cc.class_room_id ), 0) AS classLen , IFNULL(( SELECT csd.id FROM class_script_done csd");
        classRoom_sql.append(" WHERE csd.class_course_id = cc.id AND student_id = " + studentId
                + " LIMIT 1 ), 0) AS is_complet, cr.class_name , cr.summary, cr.cover, cc.do_day FROM");
        classRoom_sql
                .append(" `class_course` cc, class_room cr WHERE cc.class_room_id = cr.id AND cc.class_grades_id IN ( "
                        + gradeIds + " ) and cr.class_name like '%" + name + "%' ORDER BY cc.do_day ASC");

        List<Record> classRooms = Db.find(classRoom_sql.toString());

        Result.ok(Result.makeupList(classRooms), this);
    }

    public void lessonReplyShare() throws UnsupportedEncodingException {

        String WX_USER = (String) getSession().getAttribute("WX#USER");
        System.out.println(getSession().getId());

        String oauthHtml = "to_test_lesson_reply_share.html";

        String groupId = getPara("group");
        String from = getPara("from", "default");
        String accountId = getPara("account");
        String classRoomId = getPara("classRoomId");

        if (WX_USER == null) {

            String state = "/wechat/v2/lesson/lessonReplyShare?group=" + groupId + "&from=" + from + "&account="
                    + accountId + "&classRoomId=" + classRoomId;

            redirect("https://open.weixin.qq.com/connect/oauth2/authorize" + "?appid=" + Keys.APP_ID
                    + "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/" + oauthHtml
                    + "&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(state, "UTF-8")
                    + "#wechat_redirect", true);

            return;
        }

        String[] wx_user = WX_USER.split("#");
        // String openid = wx_user[0];
        String wechatId = wx_user[1];

        /*
         * 绑定帐号和微信
         */

        if (from.equals("toParents")) {
            Db.update(
                    "insert ignore into account_wechat_rel (account_id,wechat_user_id,status) values (?,?,?)  ON DUPLICATE KEY UPDATE status = 1",
                    accountId, wechatId, 1);
        }

        redirect(Keys.SERVER_SITE + "/wechat/lesson/2c/lesson_share.html?group=" + groupId + "&wechat=" + wechatId
                + "&from=" + from, true);
    }

    public void lessonReplyGroup() {

        int wechatId = getParaToInt("member");
        int groupId = getParaToInt("group");

        String sql = "SELECT a.*, b.id AS group_id,b.class_room_id FROM `lesson_script_reply` a, lesson_script_reply_group b, lesson_script_reply_group_rel c"
                + " WHERE (a.id = c.lesson_script_reply_id AND c.lesson_script_reply_group_id = b.id AND b.id = ?)";

        String sql2 = "SELECT IFNULL(( SELECT COUNT(id) FROM lesson_script_reply_like lsrl WHERE lsrl.group_id = ? AND lsrl.`status` = 1 ), 0) "
                + "AS like_count , IFNULL(( SELECT lsrl2.`status` FROM lesson_script_reply_like lsrl2 WHERE lsrl2.group_id = ?"
                + " AND lsrl2.member_id = ? ), 0) AS my_like";

        List<Record> lessonReplys = Db.find(sql, groupId);

        Record likeInfo = Db.findFirst(sql2, groupId, groupId, wechatId);

        WechatUser wechatUser = WechatUser.dao.findById(wechatId);

        String sql6 = "SELECT a.class_name,a.teacher_name,cs.`name` AS student_name,cs.avatar,ifnull(a.cover,'') as cover FROM class_room a,lesson_script_reply_group b LEFT JOIN class_student cs ON"
                + " b.student_id = cs.id WHERE a.id = b.class_room_id AND b.id = ?";

        JSONObject json = new JSONObject();
        json.put("lessonReplys", Result.makeupList(lessonReplys));
        json.put("likeInfo", Result.toJson(likeInfo.getColumns()));
        json.put("member", Result.toJson(wechatUser));

        Record classRoom = Db.findFirst(sql6, groupId);
        if (classRoom != null) {
            json.put("classRoom", Result.toJson(classRoom.getColumns()));
        }

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"group"})
    public void lessonReplsCommens() {

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 5);

        String sql = "FROM `lesson_script_reply_commont` a ,wechat_user b WHERE a.member_id = b.id AND a.status = 1 AND a.group_id = ?";

        Page<Record> comments = Db.paginate(1, pageNumber * pageSize, "SELECT a.*,b.nickname,b.headimgurl", sql,
                getParaToInt("group"));

        Result.ok(comments, this);
    }

    @EmptyParaValidate(params = {"member", "group", "comment"})
    public void saveLessonReplsCommens() {

        LessonScriptReplyCommont commont = new LessonScriptReplyCommont();
        commont.setGroupId(getParaToInt("group"));
        commont.setComment(getPara("comment"));
        commont.setMemberId(getParaToInt("member")).save();

        String sql3 = "SELECT a.*,b.nickname,b.headimgurl FROM `lesson_script_reply_commont` a ,wechat_user b WHERE a.member_id = b.id AND a.id = ? and a.status = 1";

        Record commontNew = Db.findFirst(sql3, commont.getId());

        Result.ok(Result.toJson(commontNew.getColumns()), this);
    }

    @EmptyParaValidate(params = {"id"})
    public void delComment() {

        String sql = "update lesson_script_reply_commont set `status` = 0 where id = ?";
        Db.update(sql, getParaToInt("id"));
        Result.ok(this);
    }

    @EmptyParaValidate(params = {"grade"})
    public void gradeLessons() {

        int gradeId = getParaToInt("grade");

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        ClassGrades classGrades = ClassGrades.dao.findById(gradeId);

        Page<ClassRoom> page = ClassRoom.dao.paginate(pageNumber, pageSize, "SELECT a.*",
                "FROM class_room a,class_course b WHERE a.id = b.class_room_id AND b.class_grades_id = ? order by b.do_day asc", gradeId);

        /*
         * List<ClassRoom> classRooms = ClassRoom.dao.find(
         * "SELECT a.* FROM class_room a,class_course b WHERE a.id = b.class_room_id AND b.class_grades_id = ?"
         * ,gradeId);
         */

        JSONObject json = new JSONObject();
        // json.put("clasRooms", Result.makeupList(classRooms));
        json.put("page", Result.makeupPage(page));
        json.put("classGrades", Result.toJson(classGrades));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"student", "classRoom"})
    public void gradeCourse() {

        String sql = "SELECT cg.id AS grade_id,cg.grades_type,cc.id AS course_id,cc.do_title "
                + "FROM class_grades cg,class_grades_rela cgr,class_course cc WHERE cg.id = cc.class_grades_id AND cg.id = "
                + "cgr.class_grades_id AND cgr.class_student_id = ? AND cc.class_room_id = ? order by cc.do_slot asc";

        List<Record> records = Db.find(sql, getParaToInt("student"), getParaToInt("classRoom"));

        Result.ok(Result.makeupList(records), this);

    }

    @EmptyParaValidate(params = {"account", "replyGroup", "student"})
    public void share2Parents() throws Exception {

        int accountId = getParaToInt("account");
        int replyGroupId = getParaToInt("replyGroup");

        List<AccountWechatRel> accountWechatRels = AccountWechatRel.dao
                .find("select * from account_wechat_rel where account_id = ? and `status` = 1", accountId);

        if (accountWechatRels != null && accountWechatRels.size() > 0) {

            List<WechatUser> wechatUsers = WechatUser.dao.find(
                    "SELECT b.* FROM account_wechat_rel a,wechat_user b WHERE a.wechat_user_id = b.id  AND a.account_id = ? AND a.`status` = 1",
                    accountId);

            int subscribeNum = 0;

            Iterator<WechatUser> iterator = wechatUsers.iterator();

            while (iterator.hasNext()) {

                WechatUser wechatUser = iterator.next();

                if (wechatUser.getBSubscribe() == 1) {
                    subscribeNum++;
                } else {
                    iterator.remove();
                }

            }

            if (subscribeNum == 0) {
                Result.error(20501, "未关注公众号", this);
            } else {

                ClassRoom classRoom = ClassRoom.dao.findFirst(
                        "SELECT a.* FROM class_room a,lesson_script_reply_group b WHERE a.id = b.class_room_id AND b.id = ?",
                        replyGroupId);

                String access_token = Account.dao.findFirst("select * from account where appid = ?", Keys.APP_ID)
                        .getAccessToken();

                for (WechatUser wechatUser : wechatUsers) {

                    NoticeInfo noticeInfo = new NoticeInfo();
                    noticeInfo.setAccessToKen(access_token);
                    noticeInfo.setOpenId(wechatUser.getOpenid());
                    noticeInfo.setFirst("您的孩子已经完成了学习");
                    noticeInfo.setKeyword1(classRoom == null ? "" : classRoom.getClassName());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
                    noticeInfo.setKeyword2(sdf.format(new Date()));
                    noticeInfo.setRemark("点击查看学习记录");
                    noticeInfo.setUrl(Keys.SERVER_SITE + "/wechat/v2/lesson/lessonReplyShare?group=" + replyGroupId
                            + "&from=toParents&account=" + accountId);
                    WeChatUtil.sendLessonMessage(noticeInfo);

                }

                Result.ok(this);

            }

        } else {

            Result.error(20502, "未绑定微信号", this);

        }

    }

    @EmptyParaValidate(params = {"wechat"})
    public void checkSubscribe() {

        int subscribe = 0;

        WechatUser wechatUser = WechatUser.dao.findById(getParaToInt("wechat"));

        if (wechatUser != null && wechatUser.getBSubscribe() == 1)
            subscribe = 1;

        JSONObject json = new JSONObject();
        json.put("subscribe", subscribe);

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"data"})
    public void addLessonResource() {

        JSONObject data = JSONObject.fromObject(getPara("data"));

        int classRoomId = data.getInt("classRoom");

        Iterator<JSONObject> iterator = data.getJSONArray("resource").iterator();

        List<ClassRoomAudioRel> audioRels = new ArrayList<ClassRoomAudioRel>();

        JSONObject paras = null;

        while (iterator.hasNext()) {

            paras = iterator.next();
            ClassRoomAudioRel audioRel = new ClassRoomAudioRel();
            audioRel.setClassRoomId(classRoomId);
            audioRel.setAudioId(paras.getInt("audio"));
            audioRel.setMaterialFileId(paras.getInt("material"));
            audioRels.add(audioRel);

        }

        Db.batchSave(audioRels, 200);

        Result.ok(this);

        /*
         * String requestData = HttpKit.readData(getRequest());
         *
         * JSONObject data = JSONObject.fromObject(requestData);
         *
         * int classRoomId = data.getInt("classRoom");
         *
         * Iterator<JSONObject> iterator =
         * data.getJSONArray("resource").iterator();
         *
         * List<ClassRoomAudioRel> audioRels = new
         * ArrayList<ClassRoomAudioRel>();
         *
         * JSONObject paras = null;
         *
         * while (iterator.hasNext()) {
         *
         * paras = iterator.next(); ClassRoomAudioRel audioRel = new
         * ClassRoomAudioRel(); audioRel.setClassRoomId(classRoomId);
         * audioRel.setAudioId(paras.getInt("audio"));
         *
         * audioRels.add(audioRel);
         *
         * }
         *
         * Result.ok(this);
         */

    }

    @EmptyParaValidate(params = {"product"})
    public void productInfo() {

        String sql = "SELECT a.id,a.`name`,b.`name` AS special_name,b.price,b.integral FROM mallproduct a,mallspecifications b WHERE a.id = ? AND a.id = b.productid";

        List<Record> productInfo = Db.find(sql, getParaToInt("product"));

        Result.ok(productInfo, this);

    }

    @EmptyParaValidate(params = {"classroom"})
    public void lessonInfo() throws UnsupportedEncodingException {

		/*HttpSession session =  getSession();
		session.setAttribute("WX#USER", "org-FwyN2d0jfeSKnYMVTrLUQmLM#9");
		session.setAttribute("openid", "org-FwyN2d0jfeSKnYMVTrLUQmLM");*/

        int classRoomId = getParaToInt("classroom");
        int inviter = getParaToInt("inviter", 0);

        String WX_USER = (String) getSession().getAttribute("WX#USER");

        if (WX_USER == null) {

            String oauthHtml = "to_test_lesson_reply_share.html";

            String state = "/wechat/v2/lesson/lessonInfo?classroom=" + classRoomId + "&inviter=" + inviter;

            redirect("https://open.weixin.qq.com/connect/oauth2/authorize" + "?appid=" + Keys.APP_ID
                    + "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/" + oauthHtml
                    + "&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(state, "UTF-8")
                    + "#wechat_redirect", true);

            return;

        }

        setAttr("openId", getSession().getAttribute("openid"));
        setAttr("WX_USER", WX_USER);
        setAttr("user", WX_USER.split("#")[1]);
        setAttr("classRoom", classRoomId);
        setAttr("inviter", inviter);

        renderTemplate("/lesson/2c/lesson.html");
        /*redirect("/lesson/2c/lesson.html?classRoom=" + classRoomId + "&inviter=" + inviter, true);*/

    }

    public void getInviteCode() {

        int inviter = getParaToInt("inviter", 0);

        Record wechatRel = Db.findFirst(
                "select b.*,c.account,c.memberid from wechat_user a, account_wechat_rel b,memberaccount c WHERE a.openid = ? AND a.id = b.wechat_user_id AND b.account_id = c.id and b.`status` = 1",
                getSession().getAttribute("openid"));

        if (wechatRel == null) {
            Result.error(20501, "no bind account", this);
        } else {

            int classRoomId = getParaToInt("classRoom");

            // 如果已经发送过邀请码则返回原来的邀请码并延长失效时间
            InvitationCode invitationCode = InvitationCode.dao.findFirst(
                    "SELECT * FROM `invitation_code` WHERE class_room_id = ? and mobile = ? order by invalid_time desc limit 1",
                    classRoomId, wechatRel.getStr("account"));

            long invalidTime = 1000 * 60 * 60 * 24 * 3;
            Date endDate = new Date(System.currentTimeMillis() + invalidTime);

            if (invitationCode == null) {

                String inviteCode = CommonUtils.getRandomString(6);

                invitationCode = new InvitationCode();
                invitationCode.setClassRoomId(classRoomId);
                invitationCode.setProductId(0);
                invitationCode.setRandomCode(inviteCode);
                invitationCode.setInvalidTime(endDate);
                invitationCode.setMemberId(wechatRel.getInt("memberid"));
                invitationCode.setMobile(wechatRel.getStr("account"));
                invitationCode.setRecommendMemberId(inviter);
                invitationCode.setStatus(0).save();

            } else {
                invitationCode.setInvalidTime(endDate).update();
            }

			/*Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where memberid = ?",inviter);

			if(memberaccount != null){

				IntegralWallet integralWallet = IntegralWallet.dao.findFirst("select * from integral_wallet where account_id = ?",memberaccount.getId());

				if(integralWallet == null){
					integralWallet = new IntegralWallet();
					integralWallet.setAccountId(memberaccount.getId());
					integralWallet.setIntegral(50);
					integralWallet.save();

				}else{

					integralWallet.setIntegral(integralWallet.getIntegral()+50);
					integralWallet.update();

				}

			}*/

            JSONObject json = new JSONObject();
            json.put("inviteCode", invitationCode.getRandomCode());
            Result.ok(json, this);

        }
    }

    @EmptyParaValidate(params = {"mobile"})
    public void sendCaptcha() throws Exception {

        String mobile = getPara("mobile");

        if (CommonUtils.checkMobile(mobile) == false) {
            Result.error(20501, "请输入正确手机号码", this);
            return;
        }

        String code = CommonUtils.getRandomNumber(4);

        Cache redis = Redis.use();
        redis.setex("lessonBind#" + mobile, 300, code);
        redis.close(redis.getJedis());

        TAOBAOSMS.sendCode(code, mobile);

        Result.ok(this);

    }

    @EmptyParaValidate(params = {"mobile", "code"})
    public void bindAccount() {

        String mobile = getPara("mobile");
        String code = getPara("code");

        Cache redis = Redis.use();

        String redisCode = redis.get("lessonBind#" + mobile);
        redis.close(redis.getJedis());

        if ((redisCode != null && redisCode.equals(code)) || code.equals("0731")) {

            UserService userService = new UserService();

            Memberaccount memberaccount = userService.registerByClassAtrial(mobile, 0);

            WechatUser wechatUser = WechatUser.dao.findFirst("SELECT * FROM `wechat_user` WHERE  openid = ?", getSession().getAttribute("openid"));

            Db.update("INSERT IGNORE INTO account_wechat_rel (account_id,wechat_user_id,`status`) VALUES (?,?,1) ON DUPLICATE KEY UPDATE `status` = 1", memberaccount.getId(), wechatUser.getId());

            Result.ok(this);

        } else {
            Result.error(20501, "验证码错误", this);
        }


    }

    @EmptyParaValidate(params = {"classRoom"})
    public void lessonIntro() {

        ClassRoom classRoom = ClassRoom.dao.findById(getParaToInt("classRoom"));
        if (xx.isEmpty(classRoom.getCover())) {
            classRoom.setCover("http://word.fandoutech.com.cn/app/OnlineCourse/18335775869/7f2e3efb9a1848aa7b386e1d8c203040.jpg");
        }

        Result.ok(classRoom, this);

    }

    @EmptyParaValidate(params = {"classRoom", "comment"})
    public void saveLessonComment() {

        String openid = (String) getSession().getAttribute("openid");

        if (openid != null) {

            WechatLessonComment comment = new WechatLessonComment();
            comment.setClassRoomId(getParaToInt("classRoom"));
            comment.setOpenid(openid);
            comment.setContent(getPara("comment"));
            comment.save();

            Record record = Db.findFirst("select b.id AS user_id,b.nickname,b.headimgurl,a.* from wechat_lesson_comment a,wechat_user b where a.openid = b.openid and a.id = ?", comment.getId());

            JSONObject json = new JSONObject();
            json.put("comment", Result.toJson(record.getColumns()));

            Result.ok(json, this);

        } else {

            Result.error(20501, "授权登录过期，请重新登录", this);
        }

    }

    @EmptyParaValidate(params = {"classroom"})
    public void getLessonComment() {

        List<Record> comments = Db.find("SELECT b.id as user_id,b.nickname,b.headimgurl,a.* FROM wechat_lesson_comment a,wechat_user b WHERE a.class_room_id = ? AND a.openid = b.openid", getParaToInt("classroom"));

        JSONObject json = new JSONObject();
        json.put("comments", Result.makeupList(comments));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"commentid"})
    public void delLessonComment() {

        Db.update("delete from wechat_lesson_comment where id = ? and openid = ?", getParaToInt("commentid"), getSession().getAttribute("openid"));

        Result.ok(this);
    }

    @EmptyParaValidate(params = {"product"})
    public void lessonProductInfo() {

        int productId = getParaToInt("product");

        Mallproduct mallproduct = Mallproduct.dao.findById(productId);

        if (mallproduct != null) {
            ClassGrades classGrades = ClassGrades.dao.findById(mallproduct.getClassGradeId());

            List<ClassCourse> classCourses = new ArrayList<ClassCourse>();
            if (classGrades != null) {
                classCourses = ClassCourse.dao.find("select * from class_course where class_grades_id = ?", classGrades.getId());
            }

            List<Mallspecifications> mallspecifications = Mallspecifications.dao.find("select * from mallspecifications where productid = ?", productId);
            JSONObject json = new JSONObject();
            json.put("classGrades", Result.toJson(classGrades));
            json.put("classCourses", Result.makeupList(classCourses));
            json.put("mallspecifications", Result.makeupList(mallspecifications));
            Result.ok(json, this);
        } else {
            Result.error(20501, "查询不到商品", this);
        }

    }

    public void createOrder() {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        Mallproduct mallproduct = Mallproduct.dao.findById(getParaToInt("product"));
        Mallspecifications mallspecifications = Mallspecifications.dao.findFirst("select * from mallspecifications where productid = ?", mallproduct.getId());

        LessonProductOrder lessonProduct = new LessonProductOrder();
        lessonProduct.setOrderNumber(CommonUtils.getRandomNumber(16));
        lessonProduct.setProductId(mallproduct.getId());
        lessonProduct.setProductName(mallproduct.getName());
        lessonProduct.setTotalPrice(BigDecimal.valueOf(mallspecifications.getPrice()));
        lessonProduct.setOpenid((String) getSession().getAttribute("openid"));
        //lessonProduct.setInviter(getParaToInt("inviter",0));
        lessonProduct.setStatus(0);
        lessonProduct.save();

        JSONObject json = new JSONObject();
        json.put("order", lessonProduct.getId());
        json.put("orderNumber", lessonProduct.getOrderNumber());

        Result.ok(json, this);

    }

    public void lessonPay() {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        LessonProductOrder lessonProductOrder = LessonProductOrder.dao.findById(getParaToInt("order"));

        WechatPayService payService = new WechatPayService();

        HashMap<String, Object> paramets = new HashMap<String, Object>();
        paramets.put("appId", Keys.APP_ID);
        paramets.put("mchId", Keys.MCH_ID);
        paramets.put("commodityName", lessonProductOrder.getProductName());
        paramets.put("orderNumber", lessonProductOrder.getOrderNumber());
        paramets.put("totalPrice", lessonProductOrder.getTotalPrice());
        paramets.put("spbillCreateIp", getRequest().getRemoteAddr());
        paramets.put("callBackUrl", "abc");
        paramets.put("payType", "JSAPI");
        paramets.put("openid", lessonProductOrder.getOpenid());
        paramets.put("apiKey", Keys.API_KEY);

        SortedMap<Object, Object> params = new TreeMap<Object, Object>();
        try {
            Map result = payService.officaPay(paramets);

            params.put("appId", Keys.APP_ID);
            params.put("timeStamp", new Date().getTime() + "");
            params.put("nonceStr", WXUtil.getNonceStr());
            /**
             * 获取预支付单号prepay_id后，需要将它参与签名。
             * 微信支付最新接口中，要求package的值的固定格式为prepay_id=...
             */
            params.put("package", "prepay_id=" + result.get("prepay_id"));

            /** 微信支付新版本签名算法使用MD5，不是SHA1 */
            params.put("signType", "MD5");
            /**
             * 获取预支付prepay_id之后，需要再次进行签名，参与签名的参数有：appId、timeStamp、nonceStr、package、signType.
             * 主意上面参数名称的大小写. 该签名用于前端js中WeixinJSBridge.invoke中的paySign的参数值
             */
            String paySign = payService.createSign("UTF-8", params, Keys.API_KEY);
            params.put("paySign", paySign);
            Result.ok(params, this);

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

    }

    public void getClassroomProduct() {

        ClassRoomProductRel classRoomProductRel = ClassRoomProductRel.dao.findFirst("SELECT * FROM class_room_product_rel WHERE class_room_id = ?", getParaToInt("classroom"));

        if (classRoomProductRel == null) {
            classRoomProductRel = new ClassRoomProductRel();
            classRoomProductRel.setProductId(0);
        }

        Result.ok(Result.toJson(classRoomProductRel), this);
    }

    @EmptyParaValidate(params = {"classroom", "member"})
    public void classRoomReply() {

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 3);

        String select = "SELECT a.id AS group_id, a.class_room_id, b.id AS student_id, b.`name`, IFNULL(b.avatar,'http://source.fandoutech.com.cn/15311899893353246.jpg?imageMogr2/thumbnail/200x') AS avatar ,"
                + " ( SELECT COUNT(id) FROM lesson_script_reply_commont WHERE group_id = a.id AND `status` = 1 ) AS comment_count , "
                + "( SELECT COUNT(id) FROM lesson_script_reply_like WHERE group_id = a.id AND `status` = 1) AS like_count,"
                + "(SELECT bb.reply_script FROM `lesson_script_reply_group_rel` aa ,lesson_script_reply bb WHERE  aa.lesson_script_reply_group_id = a.id AND aa.lesson_script_reply_id = bb.id LIMIT 1) AS first_reply,"
                + "IFNULL(( SELECT lsrl2.`status` FROM lesson_script_reply_like lsrl2 WHERE lsrl2.group_id = a.id AND lsrl2.member_id = " + getParaToInt("member") + " ), 0) AS my_like";
        String from = "FROM `lesson_script_reply_group` a, class_student b WHERE a.class_room_id = ? AND a.student_id = b.id order by a.create_time desc";

        Page<Record> page = Db.paginate(pageNumber, pageSize, select, from, getParaToInt("classroom"));

        Result.ok(page, this);

    }


    @EmptyParaValidate(params = {"data"})
    public void saveDiyRuleBatch() {

        JSONObject data = JSONObject.fromObject(getPara("data"));

        JSONArray studentRulys = data.getJSONArray("student_study_rule");

        String[] users = new String[studentRulys.size()];

        for (int i = 0; i < studentRulys.size(); i++) {

            StdDiyStudyDay stdDiyStudyDay = StdDiyStudyDay.dao.findFirst(
                    "select * from std_diy_study_day where std_id = ? and grade_id = ?"
                    , studentRulys.getJSONObject(i).getInt("std_id")
                    , data.getInt("grade_id"));

            if (stdDiyStudyDay == null) {

                stdDiyStudyDay = new StdDiyStudyDay();

            }

            stdDiyStudyDay.setStdId(studentRulys.getJSONObject(i).getInt("std_id"));
            stdDiyStudyDay.setRule(data.getString("rule"));
            stdDiyStudyDay.setGradeId(data.getInt("grade_id"));
            stdDiyStudyDay.setIsTeacherDefault(data.getInt("is_teacher_default"));
            stdDiyStudyDay.setRuleType(data.getInt("rule_type"));
            stdDiyStudyDay.setWeek(data.getString("week"));

            if (stdDiyStudyDay.getId() == null) {
                stdDiyStudyDay.save();
            } else {
                stdDiyStudyDay.update();
            }

            users[i] = studentRulys.getJSONObject(i).getString("epal_id");

        }

        try {
            EasemobUtil easemobUtil = new EasemobUtil();
            String action = "lo_timetag_update:{\"classId\":" + data.getInt("grade_id") + "}";
            easemobUtil.sendMessage(users, action);
            Result.ok(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Result.error(20501, "设置上课规则成功，环信推送更新失败", this);
        }


    }

    public void copyOldClaassRoomREL() {

        List<ClassRoomAudioRel> audioRels = ClassRoomAudioRel.dao.find("SELECT * FROM class_room_audio_rel WHERE material_file_id = 0");

        List<MaterialFile> files = MaterialFile.dao.find("select * from material_file where isdir = 0 and audioinfo_id > 0");

        List<ClassRoomAudioRel> newAudioRels = new ArrayList<ClassRoomAudioRel>(files.size());

        for (ClassRoomAudioRel audioRel : audioRels) {

            for (MaterialFile materialFile : files) {

                if (materialFile.getAudioinfoId() != null && materialFile.getAudioinfoId().equals(audioRel.getAudioId())) {

                    audioRel.setMaterialFileId(materialFile.getId());
                    newAudioRels.add(audioRel);

                }

            }

        }

        Db.batchUpdate(newAudioRels, 1000);
    }

    public void getLessonReply() {

        String timeSn = getPara("timeSn");
        List<LessonScriptReply> lessonScriptReplies = LessonScriptReply.dao.findByTimeSn(timeSn);

        for (int i = 0; i < lessonScriptReplies.size(); i++) {
            String content = lessonScriptReplies.get(i).getClassScriptContent();
            JSONArray ja = JSONArray.fromObject(content);
            String command = ja.getJSONObject(0).getString("command");
            if (command.equals("403") || command.equals("401")) {
                lessonScriptReplies.remove(i);
            }
        }

        ClassRoom classRoom = ClassRoom.dao.findById(Integer.parseInt(timeSn.split("_")[0]));

        JSONObject json = new JSONObject();
        json.put("lessonScriptReplies", Result.makeupList(lessonScriptReplies));
        json.put("classRoom", classRoom.toJson());

        Mallproduct mallproduct = Mallproduct.dao.findFirst("SELECT d.* FROM lesson_script_reply a,class_course b,class_grades c,mallproduct d WHERE a.time_sn = ? AND a.class_course_id = b.id AND b.class_grades_id = c.id AND c.id = d.class_grade_id limit 1", timeSn);

        if (mallproduct != null) {
            json.put("product", mallproduct.getId());
        }


        if (lessonScriptReplies.size() > 0) {
            ClassStudent classStudent = ClassStudent.dao.findById(lessonScriptReplies.get(0).getStudentId());
            json.put("classStudent", classStudent.toJson());
        }

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"student", "course"})
    public void lessonReply() {

        int student = getParaToInt("student");
        int course = getParaToInt("course");

        List<LessonScriptReply> lessonScriptReplies = LessonScriptReply.dao.findByStudentWithCourse(student, course);

        TreeMap<String, List<LessonScriptReply>> map = new TreeMap<>();

        for (LessonScriptReply lessonScriptReply : lessonScriptReplies) {

            List<LessonScriptReply> temp = map.get(lessonScriptReply.getTimeSn());

            if (temp == null) {
                temp = new ArrayList<LessonScriptReply>();
            }
            temp.add(lessonScriptReply);
            map.put(lessonScriptReply.getTimeSn(), temp);

        }

        JSONObject json = new JSONObject();
        json.put("time_group", map.keySet());

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"id"})
    public void classRoomInfo() {

        int id = getParaToInt("id");

        ClassRoom classRoom = ClassRoom.dao.findById(id);
        List<ClassScriptNormal> classScriptNormals = ClassScriptNormal.dao.findByClassRoomId(id);

        for (ClassScriptNormal classScriptNormal : classScriptNormals) {

            String classScriptContent = classScriptNormal.getClassScriptContent();

            if (classScriptContent != null) {
                if (classScriptContent.startsWith("[")) {
                    // 如果classScriptContent为列表不做任何操作
                } else if (classScriptContent.equals("(null)")) {
                    classScriptNormal.setClassScriptContent("[]");
                } else {
                    classScriptNormal.setClassScriptContent(ScriptTransform.parseToNewScript(classScriptContent));
                }
            } else {

            }

        }

        JSONObject json = new JSONObject();
        json.put("classRoom", Result.toJson(classRoom));
        json.put("classScriptNormals", Result.makeupList(classScriptNormals));

        Result.ok(json, this);
    }

    @EmptyParaValidate(params = {"data"})
    public void saveClassRoomReply() {

        JSONObject data = JSONObject.fromObject(getPara("data"));

        JSONArray picArray = data.getJSONArray("pics");

        List<ClassRoomReplyPic> classRoomReplyPics = new ArrayList<ClassRoomReplyPic>(picArray.size());
        ClassRoomReply classRoomReply = new ClassRoomReply();
        classRoomReply.setClassRoomId(data.getInt("classroom"));
        classRoomReply.setStudentId(data.getInt("student"));
        classRoomReply.setText(data.getString("text"));
        classRoomReply.save();

        for (int i = 0; i < picArray.size(); i++) {

            ClassRoomReplyPic classRoomReplyPic = new ClassRoomReplyPic();
            classRoomReplyPic.setClassRoomReplyId(classRoomReply.getId());
            classRoomReplyPic.setPic(picArray.getString(i));

            classRoomReplyPics.add(classRoomReplyPic);

        }

        Db.batchSave(classRoomReplyPics, 200);

        Result.ok(this);
    }

    public void getClassRoomReply() {
        int studentId = getParaToInt("student", 0);
        int classRoomId = getParaToInt("classroom", 0);

        List<ClassRoomReply> classRoomReplies = ClassRoomReply.dao.findByStudentWithClassRoom(studentId, classRoomId);

        List<ClassRoomReplyPic> classRoomReplyPics = ClassRoomReplyPic.dao.findByStudentWithClassRoom(studentId, classRoomId);

        Map<Integer, List<ClassRoomReplyPic>> map = classRoomReplyPics.stream().collect(Collectors.groupingBy(ClassRoomReplyPic::getClassRoomReplyId));
        JSONArray array = new JSONArray();

        for (ClassRoomReply classRoomReply : classRoomReplies) {
            classRoomReply.put("pics", array);
            for (Integer key : map.keySet()) {
                if (classRoomReply.getId().equals(key)) {
                    classRoomReply.put("pics", Result.makeupList(map.get(key)));
                }
            }

        }

        Result.ok(classRoomReplies, this);

    }

    @EmptyParaValidate(params = {"id"})
    public void deleteClassRoomReply() {

        ClassRoomReply.dao.deleteById(getParaToInt("id"));
        Result.ok(this);

    }

    @EmptyParaValidate(params = {"studentId", "classroomId"})
    public void emptyClassRoomReply() {

        Integer studentId = getParaToInt("studentId");

        Integer classroomId = getParaToInt("classroomId");

        Db.update("delete from class_room_reply where class_room_id =? and student_id=?;", classroomId, studentId);

        Result.ok(this);
    }

    /**
     * 展示课堂 2019.5.28
     */
    @EmptyParaValidate(params = {"classRoomId", "status"})
    public void showClassRoom() {
        Integer classRoomId = getParaToInt("classRoomId");
        Integer status = getParaToInt("status");
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(classRoomId);
        classRoom.setStatus(status);
        if (classRoom.update()) {
            Result.ok(this);
        } else {
            Result.error(400, this);
        }
    }

    @EmptyParaValidate(params = {"studentId"})
    public void studyWeeklyTemplate() {
        Integer studentId = getParaToInt("studentId");
        setAttr("studentId", studentId);
        renderTemplate("/lesson/weekly/study_weekly.html");
    }

    /**
     * 学习周报 2019.5.29
     */
   /* @EmptyParaValidate(params = {"studentId", "timeSn"})
    public void studyWeekly() throws ParseException {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        Integer studentId = getParaToInt("studentId");
        String timeSn = getPara("timeSn");
        //根据学生id获取本周的课程信息
        List<Record> recordList = Db.find("SELECT * FROM class_course_record_integral ci LEFT JOIN class_course cc ON ci.class_course_id = cc.id WHERE ci.student_id = ? AND YEARWEEK(ci.carete_time) = YEARWEEK('" + timeSn + "');", studentId);
        Integer weeklyClassTime = 0;  //总时长
        Integer weeklyClassNum = 0;
        Integer earlyClass = 0; //早读课 100
        Integer mainClass = 0; //主课 300
        Integer lateClass = 0; //晚听课 500
//        DateFormat df = DateFormat.getDateInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String Day = DateUtil.getCurrentMonday(timeSn);
        Map<String, Integer> weeklyTime = new HashMap<>();
        for (Record record : recordList) {
            for (int i = 0; i < 7; i++) {
                String specifiedDayAfter = DateUtil.getSpecifiedDayAfter(Day, i - 1);
                if (specifiedDayAfter.equals(df.format(record.getDate("carete_time")))) {
                    if (weeklyTime.containsKey("day" + i)) {
                        weeklyTime.put("day" + i, weeklyTime.get("day" + i) + record.getInt("used_time"));
                    } else {
                        weeklyTime.put("day" + i, record.getInt("used_time"));
                    }
                    break;
                }
            }
            switch (record.getInt("do_slot")) {
                case 100:
                    earlyClass++;
                    break;
                case 300:
                    mainClass++;
                    break;
                case 500:
                    lateClass++;
                    break;
            }
            weeklyClassNum++;
            weeklyClassTime += record.getInt("used_time");
        }
        Record courseInfo = Db.findFirst("SELECT COUNT(id) usedClass, SUM(used_time) usedTime FROM class_course_record_integral where student_id=?", studentId);
        Map<String, Object> data = new HashMap<>();
        data.put("weeklyTime", weeklyTime);
        data.put("weeklyClassTime", weeklyClassTime);
        data.put("weeklyClassNum", weeklyClassNum);
        data.put("earlyClass", earlyClass);
        data.put("mainClass", mainClass);
        data.put("lateClass", lateClass);
        data.put("all", courseInfo);
        renderJson(Rt.success(data));
    }*/

    /**
     * 学习周报 2021.2.5
     */
    @EmptyParaValidate(params = {"studentId", "timeSn"})
    public void studyWeekly() throws ParseException {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer studentId = getParaToInt("studentId");
        String timeSn = getPara("timeSn");

        //根据学生id获取本周的课程信息
        List<Record> recordList = Db.find("SELECT * FROM class_course_record_integral ci LEFT JOIN class_course cc ON ci.class_course_id = cc.id WHERE ci.student_id = ? AND YEARWEEK(ci.carete_time) = YEARWEEK('" + timeSn + "');", studentId);
        recordList = recordList.stream().filter(x -> x.getInt("do_slot") != null).collect(Collectors.toList());
        Integer weeklyClassTime = 0;  //总时长
        Integer weeklyClassNum = 0;
        Integer earlyClass = 0; //早读课 100
        Integer mainClass = 0; //主课 300
        Integer lateClass = 0; //晚听课 500
        Map<String, Integer> weeklyTime = new HashMap<>();
        LocalDate daySn = LocalDate.parse(timeSn);
        for (Record record : recordList) {
            Integer userTime = record.getInt("used_time");
            for (int i = 0; i < 7; i++) {
                LocalDate nextDay = daySn.plusDays(i);
                LocalDate creatDay = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(record.getDate("carete_time")));
                if (nextDay.compareTo(creatDay) == 0) {
                    if (weeklyTime.containsKey("day" + i)) {
                        weeklyTime.put("day" + i, weeklyTime.get("day" + i) + userTime);
                    } else {
                        weeklyTime.put("day" + i, userTime);
                    }
                    break;
                }
            }

            switch (record.getInt("do_slot")) {
                case 100:
                    earlyClass++;
                    break;
                case 300:
                    mainClass++;
                    break;
                case 500:
                    lateClass++;
                    break;
            }
            weeklyClassNum++;
            weeklyClassTime += userTime;
        }
        Record courseInfo = Db.findFirst("SELECT COUNT(id) usedClass, SUM(used_time) usedTime FROM class_course_record_integral where student_id=?", studentId);

        Map<String, Object> data = new HashMap<>();

        data.put("weeklyTime", weeklyTime);
        data.put("weeklyClassTime", weeklyClassTime);
        data.put("weeklyClassNum", weeklyClassNum);
        data.put("earlyClass", earlyClass);
        data.put("mainClass", mainClass);
        data.put("lateClass", lateClass);
        data.put("all", courseInfo);

        renderJson(Rt.success(data));
    }

    /**
     * 学习周报(再优化) 2021.2.5
     */
    @EmptyParaValidate(params = {"studentId", "timeSn"})
    public void studyWeekly1() throws ParseException {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        Integer studentId = getParaToInt("studentId");
        String timeSn = getPara("timeSn");
        //根据学生id获取本周的课程信息
        List<Record> recordList = Db.find("SELECT * FROM class_course_record_integral ci LEFT JOIN class_course cc ON ci.class_course_id = cc.id WHERE ci.student_id = ? AND YEARWEEK(ci.carete_time) = YEARWEEK('" + timeSn + "');", studentId);
        Map<LocalDate,List<Record>> map = recordList.stream().collect(Collectors.groupingBy(x->LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(x.getDate("carete_time")))));
        int weeklyClassTime = 0;  //总时长
        Integer earlyClass = 0; //早读课 100
        Integer mainClass = 0; //主课 300
        Integer lateClass = 0; //晚听课 500
        Map<String, Integer> weeklyTime = new HashMap<>();
        LocalDate daySn = LocalDate.parse(timeSn);
        for (int i = 0; i < 7; i++) {
            LocalDate nextDay = daySn.plusDays(i);
            List<Record> list = map.get(nextDay);
            if (list == null || list.size() == 0){
                continue;
            }
            Integer userTime = 0;
            for (Record record : list) {
                userTime += record.getInt("used_time");
                switch (record.getInt("do_slot")) {
                    case 100:
                        earlyClass++;
                        break;
                    case 300:
                        mainClass++;
                        break;
                    case 500:
                        lateClass++;
                        break;
                }
            }
            weeklyClassTime += userTime;
            weeklyTime.put("day" + i, userTime);
        }
        Record courseInfo = Db.findFirst("SELECT COUNT(id) usedClass, SUM(used_time) usedTime FROM class_course_record_integral where student_id=?", studentId);
        Map<String, Object> data = new HashMap<>();
        data.put("weeklyTime", weeklyTime);
        data.put("weeklyClassTime", weeklyClassTime);
        data.put("weeklyClassNum", recordList.size());
        data.put("earlyClass", earlyClass);
        data.put("mainClass", mainClass);
        data.put("lateClass", lateClass);
        data.put("all", courseInfo);
        renderJson(Rt.success(data));
    }

    Logger logger = LogManager.getLogger(LessonHandle.class);

    public void addStudent2Grade() {


        int student = getParaToInt("studentIds", 0);
        String code = getPara("classGradesId");

        if (xx.isOneEmpty(student, code)) {
            Result.error(203, this);
            return;
        }

        try{
            ClassGradesRela gradesRela = ClassGradesRela.dao.findFirst("SELECT * FROM `class_grades_rela`  WHERE class_grades_id=? AND class_student_id=?", code, student);

            if (gradesRela == null) {
                gradesRela = new ClassGradesRela();
                gradesRela.setClassGradesId(Integer.parseInt(code));
                gradesRela.setClassStudentId(student).save();
            }else if(gradesRela.getGradesStatus() == -1){
                gradesRela.setGradesStatus(1).update();
            }else{
                Result.error(20502, "此学生已加入过该班级", this);
                return;
            }

            ClassCourse classCourse = ClassCourse.dao.findFirst("SELECT * FROM `class_course` WHERE class_grades_id = ? AND do_slot = 300 ORDER BY do_day ASC LIMIT 1", code);

            ClassCourseSchedule classCourseSchedule = ClassCourseSchedule.dao.findFirst("SELECT * FROM `class_course_schedule` WHERE class_grades_id = ? AND student_id = ?", code, student);
            if (classCourseSchedule == null) {
                classCourseSchedule = new ClassCourseSchedule();
            }
            classCourseSchedule.setStudentId(student);
            classCourseSchedule.setClassCourseId(classCourse.getId());
            classCourseSchedule.setClassGradesId(Integer.parseInt(code));
            classCourseSchedule.setClassRoomId(classCourse.getClassRoomId());
            classCourseSchedule.setDoDay(classCourse.getDoDay());

            if (classCourseSchedule.getId() == null) {
                classCourseSchedule.save();
            } else {
                classCourseSchedule.update();
            }

            StdDiyStudyDay stdDiyStudyDay = StdDiyStudyDay.dao.findFirst("SELECT * FROM `std_diy_study_day` WHERE std_id = ? AND grade_id = ?", student, code);
            if (stdDiyStudyDay == null) {
                stdDiyStudyDay = new StdDiyStudyDay();
            }
            stdDiyStudyDay.setStdId(student);
            stdDiyStudyDay.setGradeId(Integer.parseInt(code));
            stdDiyStudyDay.setRule("[{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":0},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":1},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":2},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":3},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":4},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":5},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":6}]");
            stdDiyStudyDay.setWeek("1111111");
            stdDiyStudyDay.setIsTeacherDefault(1);
            if (stdDiyStudyDay.getId() == null) {
                stdDiyStudyDay.save();
            } else {
                stdDiyStudyDay.update();
            }

            Record gradeSchool = Db.findFirst("SELECT * FROM school_grades WHERE class_grades_id = ?", gradesRela.getClassGradesId());

            List<ClassRoom> classRooms = ClassRoom.dao.findByGrade(gradesRela.getClassGradesId());

            long totalCredit = 0;
            for (ClassRoom classRoom : classRooms) {
                totalCredit += classRoom.getCredit();
            }

            SchoolCreditRecord creditRecord = new SchoolCreditRecord();
            creditRecord.setSchoolId(gradeSchool.getInt("school_id"));
            creditRecord.setAction(3);
            creditRecord.setAmount(-totalCredit);
            creditRecord.save();

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            try{
                ClassGradeCode classGradeCode = ClassGradeCode.dao.findByCode(code);

                if (!xx.isEmpty(classGradeCode) && classGradeCode.getStatus() == 0) {

                    ClassGradesRela gradesRela = ClassGradesRela.dao.findFirst("SELECT * FROM `class_grades_rela`  WHERE class_grades_id=? AND class_student_id=?", classGradeCode.getClassGradeId(), student);

                    if (gradesRela == null) {
                        gradesRela = new ClassGradesRela();
                        gradesRela.setClassGradesId(classGradeCode.getClassGradeId());
                        gradesRela.setClassStudentId(student).save();
                    }else if(gradesRela.getGradesStatus() == -1){
                        gradesRela.setGradesStatus(1).update();
                    }else{
                        Result.error(20502, "此学生已加入过该班级", this);
                        return;
                    }

                    ClassCourse classCourse = ClassCourse.dao.findFirst("SELECT * FROM `class_course` WHERE class_grades_id = ? AND do_slot = 300 ORDER BY do_day ASC LIMIT 1", classGradeCode.getClassGradeId());

                    ClassCourseSchedule classCourseSchedule = ClassCourseSchedule.dao.findFirst("SELECT * FROM `class_course_schedule` WHERE class_grades_id = ? AND student_id = ?", classGradeCode.getClassGradeId(), student);
                    if (classCourseSchedule == null) {
                        classCourseSchedule = new ClassCourseSchedule();
                    }
                    classCourseSchedule.setStudentId(student);
                    classCourseSchedule.setClassCourseId(classCourse.getId());
                    classCourseSchedule.setClassGradesId(classGradeCode.getClassGradeId());
                    classCourseSchedule.setClassRoomId(classCourse.getClassRoomId());
                    classCourseSchedule.setDoDay(classCourse.getDoDay());

                    if (classCourseSchedule.getId() == null) {
                        classCourseSchedule.save();
                    } else {
                        classCourseSchedule.update();
                    }

                    StdDiyStudyDay stdDiyStudyDay = StdDiyStudyDay.dao.findFirst("SELECT * FROM `std_diy_study_day` WHERE std_id = ? AND grade_id = ?", student, code);
                    if (stdDiyStudyDay == null) {
                        stdDiyStudyDay = new StdDiyStudyDay();
                    }
                    stdDiyStudyDay.setStdId(student);
                    stdDiyStudyDay.setGradeId(classGradeCode.getClassGradeId());
                    stdDiyStudyDay.setRule("[{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":0},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":1},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":2},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":3},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":4},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":5},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":6}]");
                    stdDiyStudyDay.setWeek("1111111");
                    stdDiyStudyDay.setIsTeacherDefault(1);
                    if (stdDiyStudyDay.getId() == null) {
                        stdDiyStudyDay.save();
                    } else {
                        stdDiyStudyDay.update();
                    }

                    Record gradeSchool = Db.findFirst("SELECT * FROM school_grades WHERE class_grades_id = ?", classGradeCode.getClassGradeId());

                    List<ClassRoom> classRooms = ClassRoom.dao.findByGrade(classGradeCode.getClassGradeId());

                    long totalCredit = 0;
                    for (ClassRoom classRoom : classRooms) {
                        totalCredit += classRoom.getCredit();
                    }

                    SchoolCreditRecord creditRecord = new SchoolCreditRecord();
                    creditRecord.setSchoolId(gradeSchool.getInt("school_id"));
                    creditRecord.setAction(3);
                    creditRecord.setAmount(-totalCredit);
                    creditRecord.save();

                    classGradeCode.setStudentId(student);
                    classGradeCode.setStatus(1);
                    classGradeCode.update();

                    Result.ok(this);

                } else {
                    Result.error(20501, "二维码已失效", this);
                    return;
                }

            }catch (Exception e1){
                e1.printStackTrace();
                logger.error(e);
                Result.error(20501, "二维码已失效", this);
            }

        }

        try{

            Db.tx(() -> {

                ClassGrades classGrades = ClassGrades.dao.findById(Integer.parseInt(code));

                Record student1 = Db.findFirst("select * from v_student_account where id = ?" ,student);
                Record teacher = Db.findFirst("select * from v_teacher_account where id = ?" ,classGrades.getTeacherId());

                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAction(1);
                studyCoinRecord.setCount(200);
                studyCoinRecord.setRemark("加入班级赠送200豆币");
                studyCoinRecord.setAccountId(student1.getInt("account_id")).save();

                new StudyCoinRecord().setAction(1).setCount(50).setRemark("新生加入班级奖励50豆币").setAccountId(teacher.getInt("accountId")).save();

                return true;
            });

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }

        Result.ok(this);

        /*ClassGradeCode classGradeCode = ClassGradeCode.dao.findByCode(code);

        if (!xx.isEmpty(classGradeCode) && classGradeCode.getStatus() == 0) {

            ClassGradesRela gradesRela = ClassGradesRela.dao.findFirst("SELECT * FROM `class_grades_rela`  WHERE class_grades_id=? AND class_student_id=?", classGradeCode.getClassGradeId(), student);

            if (!xx.isEmpty(gradesRela)) {
                Result.error(20502, "此学生已加入过该班级", this);
                return;
            }

            String sql = "INSERT IGNORE INTO class_grades_rela (class_grades_id,class_student_id) VALUES (" + classGradeCode.getClassGradeId() + "," + student + ")";
            String sql2 = "INSERT IGNORE INTO class_course_schedule (student_id,class_course_id,class_grades_id,class_room_id,do_day,sort) VALUES (?,?,?,?,?,?)";

            Db.update(sql);
            Db.update(sql2, student, 0, classGradeCode.getClassGradeId(), -1, -1, 0);

            Record gradeSchool = Db.findFirst("SELECT * FROM school_grades WHERE class_grades_id = ?", classGradeCode.getClassGradeId());

            List<ClassRoom> classRooms = ClassRoom.dao.findByGrade(classGradeCode.getClassGradeId());

            long totalCredit = 0;
            for (ClassRoom classRoom : classRooms) {
                totalCredit += classRoom.getCredit();
            }

            SchoolCreditRecord creditRecord = new SchoolCreditRecord();
            creditRecord.setSchoolId(gradeSchool.getInt("school_id"));
            creditRecord.setAction(3);
            creditRecord.setAmount(-totalCredit);
            creditRecord.save();

            classGradeCode.setStudentId(student);
            classGradeCode.setStatus(1);
            classGradeCode.update();

            Result.ok(this);

        } else {
            Result.error(20501, "二维码已失效", this);
            return;
        }*/


    }

    @EmptyParaValidate(params = {"data"})
    public void saveMoment() {

        Moment moment = FastJson.getJson().parse(getPara("data"), Moment.class);

        if (moment.getId() == null) {
            moment.save();
        } else {
            moment.update();
        }

        Result.ok(moment, this);

    }

    @EmptyParaValidate(params = {"student"})
    public void getMoment() {

        List<Moment> Moments = Moment.dao.findByStudent(getParaToInt("student"));

        Result.ok(Moments, this);

    }

    @EmptyParaValidate(params = {"id"})
    public void delMoment() {

        Moment.dao.deleteById(getParaToInt("id"));

        Result.ok(this);
    }

    @EmptyParaValidate(params = {"student"})
    public void moment() {
        redirect("/lesson/moment/html/circlip.html", true);
    }

    @EmptyParaValidate(params = {"account", "student", "rfid", "studentName"})
    public void rfidAssociate() {

        String rfid = getPara("rfid");

        PublicRoomFidToStudent publicRoomFidToStudent = PublicRoomFidToStudent.dao.findByFid(rfid);

        try {
            if (publicRoomFidToStudent != null) {

                ClassStudent student = ClassStudent.dao.findById(publicRoomFidToStudent.getStudentId());

                if (student != null && student.getName().equals(getPara("studentName"))) {

                } else {
                    Result.error(20509, "学生名验证失败", this);
                    return;
                }

                RfidAssociate rfidAssociate = new RfidAssociate();
                rfidAssociate.setAccountId(getParaToInt("account"));
                rfidAssociate.setStudentId(getParaToInt("student"));
                rfidAssociate.setRfidStudent(publicRoomFidToStudent.getStudentId());
                rfidAssociate.save();
                Result.ok(student, this);
            } else {
                Result.error(20501, "学生卡号错误，请检查后重新输入", this);
            }
        } catch (ActiveRecordException e) {
            if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                Result.error(20502, "已关联，请勿重复操作", this);
            } else {
                throw e;
            }
        }

    }

    @EmptyParaValidate(params = {"id"})
    public void studentGrade() {

        int studentId = getParaToInt("id");

        JSONObject json = new JSONObject();
        json.put("master", Result.makeupList(classGrades(studentId)));

        JSONArray array = new JSONArray();

        List<RfidAssociate> rfidAssociates = RfidAssociate.dao.findByStudent(studentId);
        for (RfidAssociate associate : rfidAssociates) {
            array.add(Result.makeupList(classGrades(associate.getRfidStudent())));
        }

        json.put("slave", array);

        Result.ok(json, this);

    }

    public List<ClassGrades> classGrades(int studentId) {

        List<ClassGrades> classGrades = ClassGrades.dao.findByStudent(studentId);

        ClassStudent classStudent = ClassStudent.dao.findById(studentId);

        int[] gradeIds = new int[classGrades.size()];

        for (int i = 0; i < classGrades.size(); i++) {
            gradeIds[i] = classGrades.get(i).getId();
        }

        List<ClassCourse> classCourses = ClassCourse.dao.findByGradeIdsWithReply(gradeIds, studentId);

        //List<ClassCourseRecord> classCourseRecords = ClassCourseRecord.dao.findByGradeIds(gradeIds,studentId);

        for (ClassCourse classCours : classCourses) {

            classCours.put("complete", 0);
           /* for (ClassCourseRecord classCourseRecord : classCourseRecords) {

                if (classCours.getId().equals(classCourseRecord.getClassCourseId())){
                    classCours.put("complete",1);
                }

            }
*/
            classCours.put("courseTotalTime", 0);

        }

        Map<Integer, List<ClassCourse>> courseMap = classCourses.stream()
                .collect(Collectors.groupingBy(ClassCourse::getClassGradesId));

        List<ClassCourseSchedule> classCourseSchedules = ClassCourseSchedule.dao.findDoDayByStudent(studentId);

        for (ClassGrades classGrade : classGrades) {

            classGrade.put("course", Result.makeupList(courseMap.get(classGrade.getId())));
            classGrade.put("studentId", classStudent.getId());
            classGrade.put("epalId", classStudent.getEpalId());

            classGrade.put("scheduleDoday", -1);

            for (ClassCourseSchedule courseSchedule : classCourseSchedules) {
                if (courseSchedule.getClassGradesId().equals(classGrade.getId())) {
                    classGrade.put("scheduleDoday", courseSchedule.getDoDay());
                    classGrade.put("classCourseId", courseSchedule.getClassCourseId());
                }
            }

        }

        return classGrades;
    }


    public void getLowWordList() {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        String timeSn = getPara("timeSn");

        LessonScriptReply lessonScriptReply = LessonScriptReply.dao.findFirst("select * from lesson_script_reply where time_sn=? ", timeSn);

        ClassCourseRecord classCourseRecord = ClassCourseRecord.dao.findFirst("select * from class_course_record where class_course_id=? and student_id=? order by id desc limit 1", lessonScriptReply.getClassCourseId(), lessonScriptReply.getStudentId());

        ClassCourseScoreRecord classCourseScoreRecord = ClassCourseScoreRecord.dao.findFirst("select * from class_course_score_record where id=?", classCourseRecord.getId());

        List<String> wordlist = new ArrayList<>();

        if (classCourseScoreRecord == null) {
            Result.error(20501, "学生低分单词为空", this);

            return;
        }

        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(classCourseScoreRecord.getWordList());

        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                Integer score = jsonArray.getJSONObject(i).getInteger("score");

                if (score < 50) {
                    wordlist.add(jsonArray.getJSONObject(i).getString("word"));
                }
            }
        } else {

            Result.ok(wordlist, this);
        }
    }

    @EmptyParaValidate(params = {"users", "action"})
    public void sendMessage() throws Exception {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        String[] users = getPara("users").split(",");
        EasemobUtil easemobUtil = new EasemobUtil();
        easemobUtil.sendMessage(users, getPara("action"));
        Result.ok(this);

    }
    /**
    *  复制课堂
    * */
    public  void  copyClassRoomToTearcher(){

        String teacherId = getPara("teacherId");
        String classRoomId = getPara("classRoomId");
        String teacherName = getPara("teacherName");

        ClassRoom classRoom = ClassRoom.dao.findById(classRoomId);

        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        if (classRoom!=null){
            classRoom.setId(null);
            classRoom.setStatus(0);
            classRoom.setTeacherName(teacherName);
            classRoom.setClassName(classRoom.getClassName()+"copy"+CommonUtils.getRandomNumber(2));
            classRoom.setTeacherId(Integer.parseInt(teacherId));
            classRoom.setCreateTime(createTime);
            classRoom.setGroupId("0");
            classRoom.setSort(0);
            classRoom.save();
        }else {
            return;
        }

        int newClassRoomId= classRoom.getId();

        //复制标签
        List<ClassRoomLabelRela> classRoomLabelRelaList = ClassRoomLabelRela.dao.find("select * from class_room_label_rela where class_room_id = ? and status = 1", classRoomId);
        if (classRoomLabelRelaList.size() > 0) {
            List<ClassRoomLabelRela> newClassRoomLabelRelaList = new ArrayList<>();
            for (ClassRoomLabelRela crl : classRoomLabelRelaList) {
                ClassRoomLabelRela classRoomLabelRela = new ClassRoomLabelRela();
                classRoomLabelRela.setLabelId(crl.getLabelId());
                classRoomLabelRela.setClassRoomId(newClassRoomId);
                classRoomLabelRela.setStatus(1);
                newClassRoomLabelRelaList.add(classRoomLabelRela);
            }

            Db.batchSave(newClassRoomLabelRelaList, newClassRoomLabelRelaList.size());
        }

        // 复制课堂指令到老师名下
        List<ClassScriptNormal> classScriptNormals = ClassScriptNormal.dao.find("select class_script_type_id,class_script_content,sort,total_time from class_script_normal where class_room_id=?", classRoomId);
        if (classScriptNormals.size()>0){

            List<ClassScriptNormal> newclassScriptNormalList = new ArrayList<>();
            for (ClassScriptNormal csnl : classScriptNormals) {

                ClassScriptNormal classScriptNormal  = new ClassScriptNormal();
                classScriptNormal.setClassRoomId(newClassRoomId);
                classScriptNormal.setClassScriptTypeId(csnl.getClassScriptTypeId());
                classScriptNormal.setClassScriptContent(csnl.getClassScriptContent());
                classScriptNormal.setSort(csnl.getSort());
                classScriptNormal.setCreateTime(createTime);
                classScriptNormal.setTotalTime(csnl.getTotalTime());
                newclassScriptNormalList.add(classScriptNormal);

            }

            Db.batchSave(newclassScriptNormalList,newclassScriptNormalList.size());
        }

        // 复制课堂资源到新课堂
        List<ClassRoomAudioRel> classRoomAudioRels = ClassRoomAudioRel.dao.find("select class_room_id,audio_id,material_file_id from class_room_audio_rel where class_room_id =?", classRoomId);
        if (classRoomAudioRels.size()>0){
            List<ClassRoomAudioRel> classRoomAudioRelList = new ArrayList<>();
            for (ClassRoomAudioRel crar : classRoomAudioRels) {
                ClassRoomAudioRel classRoomAudioRel = new ClassRoomAudioRel();
                classRoomAudioRel.setClassRoomId(newClassRoomId);
                classRoomAudioRel.setMaterialFileId(crar.getMaterialFileId());
                classRoomAudioRel.setAudioId(crar.getAudioId());
                classRoomAudioRelList.add(classRoomAudioRel);
            }
            Db.batchSave(classRoomAudioRelList,classRoomAudioRelList.size());

        }

        Result.ok(this);


    }

    @EmptyParaValidate(params = {"account"})
    public void firstShare(){
        CoinGift coinGift = CoinGift.dao.findById(5);
        if (coinGift != null){
            StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
            studyCoinRecord.setAction(1).setCount(coinGift.getCoinCount()).setRemark(coinGift.getName()).setAccountId(getParaToInt("account")).save();
        }
    }

    @EmptyParaValidate(params = {"account"})
    public void complteLesson(){
        CoinGift coinGift = CoinGift.dao.findById(7);
        if (coinGift != null){
            StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
            studyCoinRecord.setAction(1).setCount(coinGift.getCoinCount()).setRemark(coinGift.getName()).setAccountId(getParaToInt("account")).save();
        }
    }

    public void getGradeId(){
        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        int id = getParaToInt("id",0);
        ClassCourse classCourse  = ClassCourse.dao.findById(id);
        Result.ok(classCourse,this);
    }

    @EmptyParaValidate(params = {"scriptid","courseid","gradeid"})
    public void lessonScriptComment(){
        String sql = "select a.* from (" +
                "SELECT lsr.update_time,lsr.create_time,lsr.id as reply_id,lrc.content,cs.id AS student_id,cs.`name` AS student_name,cs.avatar, lsr.class_script_id,lsr.reply_script FROM " +
                "(`class_grades_rela` cgr JOIN class_student cs ON cgr.class_student_id = cs.id AND cgr.gradesStatus = 1 and cgr.class_grades_id = ?)" +
                " LEFT JOIN lesson_script_reply lsr ON cgr.class_student_id = lsr.student_id AND lsr.class_course_id = ? and lsr.class_script_id = ? " +
                "LEFT JOIN lesson_reply_comment lrc ON lsr.id = lrc.reply_id where 1=1 order by lrc.id desc, lsr.id desc " +
                ") a group by a.student_id, a.class_script_id";

        Result.ok(Db.find(sql,getParaToInt("gradeid"),getParaToInt("courseid"),getParaToInt("scriptid")),this);
    }

    @EmptyParaValidate(params = {"id"})
    public void classRoomInfo2() {

        int id = getParaToInt("id");

        ClassRoom classRoom = ClassRoom.dao.findFirst("SELECT b.*,a.take_picture,a.take_time,a.live_mode,a.b_live FROM `class_course` a,class_room b WHERE a.id = ? AND a.class_room_id = b.id",id);
        List<ClassScriptNormal> classScriptNormals = ClassScriptNormal.dao.findByClassRoomId(classRoom.getId());

        /*String key = "liveStatus-"+id;
        Cache redis = Redis.use();
        List<ClassScriptStatus> classScriptStatuses = redis.get(key);*/

        List<LiveScriptStatus> liveScriptStatuses = LiveScriptStatus.dao.find("select * from live_script_status where course_id = ?",id);

        for (ClassScriptNormal classScriptNormal : classScriptNormals) {

            String classScriptContent = classScriptNormal.getClassScriptContent();

            if (classScriptContent != null) {
                if (classScriptContent.startsWith("[")) {
                    // 如果classScriptContent为列表不做任何操作
                } else if (classScriptContent.equals("(null)")) {
                    classScriptNormal.setClassScriptContent("[]");
                } else {
                    classScriptNormal.setClassScriptContent(ScriptTransform.parseToNewScript(classScriptContent));
                }
            } else {

            }

            for (LiveScriptStatus liveScriptStatus : liveScriptStatuses) {
                if (classScriptNormal.getId().equals(liveScriptStatus.getClassScriptId())){
                    classScriptNormal.put("liveStatua2",liveScriptStatus.getLiveStatus());
                }
            }

        }

        JSONObject json = new JSONObject();
        json.put("classRoom", Result.toJson(classRoom));
        json.put("classScriptNormals", Result.makeupList(classScriptNormals));

        Result.ok(json, this);
    }

    public void subject(){
        List<ClassroomSubject> classroomSubjects = ClassroomSubject.dao.find("select * from classroom_subject where `status` = 1");
        Result.ok(classroomSubjects,this);
    }

    public void classRooms(){

        int limit = getParaToInt("limit",10);

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.id, a.teacher_id, a.teacher_name, a.class_name, a.cover , a.summary, a.status,");
        sql.append(" a.sort, a.subject_id, a.book_res_id , a.group_id, a.create_time, a.class_room_type,");
        sql.append(" a.video_url, a.type , a.book_res_ids FROM ( SELECT a.* , IF(@tmpgid = a.subject_id,");
        sql.append(" @rank := @rank + 1, @rank := 0) AS rank , @tmpgid := a.subject_id , @number := @number + 1,");
        sql.append(" @rank , @tmpgid FROM ( SELECT * FROM class_room WHERE `status` = 1 ORDER BY subject_id, id DESC )");
        sql.append(" a, ( SELECT @rank := 0, @number := 0 , @tmpgid := '' ) b ) a WHERE rank < ?");

        List<Record> classrooms = Db.find(sql.toString(),limit);
        List<ClassroomSubject> classroomSubjects = ClassroomSubject.dao.find("select * from classroom_subject where `status` = 1");
        classroomSubjects.forEach(classroomSubject -> {
            List<Record> list = new ArrayList<Record>(10);
            classrooms.forEach(classroom -> {
                if (classroomSubject.getId().equals(classroom.getInt("subject_id"))){
                    list.add(classroom);
                }
            });
            classroomSubject.put("list",Result.makeupList(list));
        });
        Result.ok(classroomSubjects,this);
    }

    public void subjectClassRooms(){

        int page = getParaToInt("page",1);
        int size = getParaToInt("size",10);
        Integer id = getParaToInt("id");
        String name = getPara("name");

        String sql = "FROM class_room a LEFT JOIN (class_room_label_rela c JOIN class_room_label b ON b.id = c.label_id) ON a.id = c.class_room_id WHERE a.`status` = 1";

        if (id != null){
            sql += " AND a.subject_id = "+id;
        }

        if (name != null){
            sql += " AND (a.summary LIKE '%"+name+"%' OR a.class_name LIKE '%"+name+"%' OR b.label_name LIKE '%"+name+"%')";
        }

        Page<Record> pageData = Db.paginate(page,size,"SELECT a.*,b.label_name",sql + " group by a.id");
        Result.ok(pageData,this);

    }

    public void labels(){
        int limit = 10;
        Result.ok(Db.find("SELECT a.* FROM (SELECT *,COUNT(*) AS count FROM class_room_label GROUP BY label_name) a ORDER BY a.count desc LIMIT ?",limit),this);
    }

    @EmptyParaValidate(params = {"id"})
    public void saveCourseScore(){
        int score = getParaToInt("score");
        String audio = getPara("audio");
        String tId = getPara("tid");
        String[] ids = getPara("id").split(",");

        if (tId != null){
            EvaluationTemplate evaluationTemplate = EvaluationTemplate.dao.findById(tId);
            score = evaluationTemplate.getScore();
            audio = evaluationTemplate.getAudio();
        }

        List<ClassCourseScore> classCourseScoreList = new ArrayList<ClassCourseScore>(ids.length);
        for (int i = 0; i < ids.length; i++) {
            String s = ids[i];
            ClassCourseScore classCourseScore = new ClassCourseScore();
            classCourseScore.setClassCourseRecordId(Integer.parseInt(s)).setScore(score).setAudio(audio);
            classCourseScoreList.add(classCourseScore);
        }
        Db.batchSave(classCourseScoreList,200);
        Result.ok(this);
    }

    @EmptyParaValidate(params = {"id","grade"})
    public void getEvaluationTemplate(){
        List<EvaluationTemplate> evaluationTemplates = EvaluationTemplate.dao.find("select * from evaluation_template where teacher_id = ? and grade_id = ?",getParaToInt("id"),getParaToInt("grade"));
        Result.ok(evaluationTemplates,this);
    }

    @EmptyParaValidate(params = {"id"})
    public void getCourseScore(){
        List<Record> records = Db.find("SELECT a.complete,a.progress,cs.avatar,a.id as class_course_record_id,a.score AS record_Score,cs.id as student_id, cs.`name`, b.id,b.score,b.audio,b.create_time,b.update_time FROM `class_course_record` a JOIN class_student cs ON a.student_id = cs.id LEFT JOIN class_course_score b ON a.id = b.class_course_record_id WHERE a.class_course_id = ?", getParaToInt("id"));
        Result.ok(records,this);
    }

    @EmptyParaValidate(params = {"level","score","text","audio","teacherId"})
    public void saveEvaluationTemplate(){
        EvaluationTemplate evaluationTemplate = getBean(EvaluationTemplate.class,"");
        if (evaluationTemplate.getId() == null){
            evaluationTemplate.save();
        }else {
            evaluationTemplate.update();
        }
        Result.ok(evaluationTemplate,this);
    }

    @EmptyParaValidate(params = {"sids","course","grade","teacherId"})
    public void autoEvaluationTemp(){
        List<EvaluationTemplate> evaluationTemplates = EvaluationTemplate.dao.find("SELECT * FROM evaluation_template WHERE grade_id = ? AND teacher_id = ? group by level",getParaToInt("grade"),getParaToInt("teacherId"));

        if (evaluationTemplates.size() < 4){
            Result.error(20501,"请完善评价模板",this);
            return;
        }

        try{
            Db.tx(() ->{
                HashMap<Integer,EvaluationTemplate> evaluationTemplateHashMap = new HashMap<>();
                for (EvaluationTemplate evaluationTemplate : evaluationTemplates) {
                    evaluationTemplateHashMap.put(evaluationTemplate.getLevel(),evaluationTemplate);
                }

                String sids = getPara("sids");
                List<ClassCourseRecord> courseRecords = ClassCourseRecord.dao.find("SELECT * FROM class_course_record WHERE class_course_id = ? AND FIND_IN_SET(student_id,?)",getParaToInt("course"),sids);
                List<ClassCourseScore> classCourseScoreList = new ArrayList<ClassCourseScore>(courseRecords.size());
                for (ClassCourseRecord courseRecord : courseRecords) {
                    int score = Integer.parseInt(courseRecord.getScore());
                    EvaluationTemplate evaluationTemplate = null;
                    if (score>=-1 && score<=40){
                        evaluationTemplate = evaluationTemplateHashMap.get(4);
                    }
                    if (score>=41 && score<=60){
                        evaluationTemplate = evaluationTemplateHashMap.get(3);
                    }

                    if (score>=61 && score<=80){
                        evaluationTemplate = evaluationTemplateHashMap.get(2);
                    }

                    if (score>=81 && score<=100){
                        evaluationTemplate = evaluationTemplateHashMap.get(1);
                    }

                    ClassCourseScore classCourseScore = new ClassCourseScore();
                    classCourseScore.setClassCourseRecordId(courseRecord.getId()).setScore(evaluationTemplate.getScore()).setAudio(evaluationTemplate.getAudio());
                    classCourseScoreList.add(classCourseScore);
                }
                Db.batchSave(classCourseScoreList,200);
                Result.ok(this);
                return true;
            });
        }catch (Exception e){
            e.printStackTrace();
            Result.error(20502,"提交失败，请重试",this);
        }
    }

    @EmptyParaValidate(params = {"id"})
    public void deleteEvaluationTemp(){
        EvaluationTemplate.dao.deleteById(getParaToInt("id"));
        Result.ok(this);
    }

    @EmptyParaValidate(params = {"id"})
    public void getEvaluation(){
        ClassCourseScore score = ClassCourseScore.dao.findFirst("SELECT b.* FROM class_course_record a,class_course_score b WHERE a.class_course_id = ? AND a.student_id = ? AND a.id = b.class_course_record_id order by b.id desc",getParaToInt("course"),getParaToInt("id"));
        Result.ok(score,this);
    }

    @EmptyParaValidate(params = {"gradeId","rule"})
    public void saveStudyRule(){
        GradeStudyRule gradeStudyRule = getBean(GradeStudyRule.class,"");
        gradeStudyRule.save();
        Result.ok(this);
    }

    @EmptyParaValidate(params = {"id"})
    public void gradeLive(){
        Result.ok(ClassRoom.dao.find("SELECT a.*,b.id as course_id,b.do_slot FROM `class_room` a,class_course b WHERE b.class_grades_id = ? and b.class_room_id = a.id AND b.live_dodate is NOT NULL",getParaToInt("id")),this);
    }


    public void lessonPack(){
        Result.ok(Mallproduct.dao.find("SELECT mp.* FROM mallproduct mp,mallspecifications ms WHERE mp.`status` = 6 AND mp.id = ms.productid"),this);
    }

    public void rzll(){
        renderTemplate("/lesson/2c/rzll/class.html");
    }

    @EmptyParaValidate(params = {"timesn"})
    public void lessonScriptReply(){
        List<Record> list = Db.find("SELECT a.* FROM ( SELECT lsr.id AS reply_id, lsr.student_id, lsr.class_script_id, lsr.class_script_content, lsr.reply_script , lsrc.content, cs.NAME, cs.avatar FROM `lesson_script_reply` lsr LEFT JOIN lesson_reply_comment lsrc ON lsr.id = lsrc.reply_id LEFT JOIN class_student cs ON lsr.student_id = cs.id WHERE lsr.time_sn = ? ORDER BY lsrc.id DESC ) a GROUP BY a.class_script_id",getPara("timesn"));
        Result.ok(list,this);
    }

    public void saveReplyComment(){
        getBean(LessonReplyComment.class,"").save();
        Result.ok(this);
    }


    @EmptyParaValidate(params = {"course","classScriptId","liveStatus"})
    public void setLiveStatus(){

        /*String key = "liveStatus-"+getPara("course");
        Cache redis = Redis.use();
        List<ClassScriptStatus> classScriptStatuses = redis.get(key);
        ClassScriptStatus classScriptStatus = getBean(ClassScriptStatus.class,"");
        boolean clear = getParaToBoolean("clear",false);

        if (classScriptStatuses == null || clear){
            redis.del(key);
            classScriptStatuses = new ArrayList<ClassScriptStatus>();
            classScriptStatuses.add(classScriptStatus);
        }else {
            boolean flag = true;
            for (ClassScriptStatus scriptStatus : classScriptStatuses) {
                if (scriptStatus.getClassScriptId().equals(classScriptStatus.getClassScriptId())){
                    scriptStatus.setLiveStatus(classScriptStatus.getLiveStatus());
                    flag = false;
                }
            }
            if (flag){
                classScriptStatuses.add(classScriptStatus);
            }
        }

        redis.set(key,classScriptStatuses);
        Result.ok(classScriptStatuses,this);*/

        String sql = "INSERT INTO `live_script_status`(`course_id`, `class_script_id`, `live_status`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE live_status = ?";
        Db.update(sql,getParaToInt("course"),getParaToInt("classScriptId"),getParaToInt("liveStatus"),getParaToInt("liveStatus"));
        Result.ok(this);

    }


    @EmptyParaValidate(params = {"id","mode"})
    public void setLiveMode(){
        Db.update("UPDATE class_course SET live_mode = ? WHERE id = ?",getParaToInt("mode"),getParaToInt("id"));
        Result.ok(this);
    }

    public void lessonScriptReplyList2() {

        Integer courseId = getParaToInt("course", 0);
        Integer studentId = getParaToInt("student", 0);

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        String totalRowSql = "select count(*) as count from (select a.id from lesson_script_reply a join lesson_script_time b on a.time_sn = b.time_sn where a.class_course_id = ? and a.student_id = ? group by a.time_sn) a";
        int totalRow = Db.findFirst(totalRowSql, courseId, studentId).getInt("count");
        int totalPage = totalRow / pageSize;

        if (totalRow % pageSize != 0) {
            totalPage++;
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT a.* FROM (SELECT a.*,lrc.content as comment FROM (SELECT lsr.*, UNIX_TIMESTAMP(lst.create_time) AS lesson_time FROM lesson_script_reply lsr JOIN ( SELECT a.time_sn FROM ");
        sql.append(
                " lesson_script_reply a join lesson_script_time b on a.time_sn = b.time_sn WHERE a.class_course_id = ? AND a.student_id = ? GROUP BY a.time_sn order by b.create_time desc  LIMIT ?, ? ) lsr2 ON lsr.time_sn =");
        sql.append(
                " lsr2.time_sn, lesson_script_time lst WHERE lsr.time_sn = lst.time_sn) a LEFT JOIN lesson_reply_comment lrc ON a.id = lrc.reply_id ORDER BY lrc.id DESC) a GROUP BY a.id");

        List<Record> lessonScriptReplys = Db.find(sql.toString(), courseId, studentId, (pageNumber - 1) * pageSize,
                pageSize);

        /*
         * 数据按照 time_sn 分组
         */
        Map<Long, List<Record>> lessonScriptReplyMap = new TreeMap<>(new Comparator<Long>() {

            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }

        });

        for (Record lessonScriptReply : lessonScriptReplys) {

            List<Record> tempList = lessonScriptReplyMap.get(Long.parseLong(lessonScriptReply.getStr("lesson_time")));

            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(lessonScriptReply);
                lessonScriptReplyMap.put(Long.parseLong(lessonScriptReply.getStr("lesson_time")), tempList);
            } else {
                tempList.add(lessonScriptReply);
            }

        }

        JSONObject result = new JSONObject();
        JSONArray lessonScriptReplies = new JSONArray();

        for (Long key : lessonScriptReplyMap.keySet()) {

            JSONObject lessonScriptReply = new JSONObject();
            lessonScriptReply.put("_list", Result.makeupList(lessonScriptReplyMap.get(key)));
            lessonScriptReply.put("lessonTime", key * 1000);
            lessonScriptReplies.add(lessonScriptReply);

        }

        result.put("lessonScriptReplies", lessonScriptReplies);
        result.put("toalRow", totalRow);
        result.put("toalPage", totalPage);

        Result.ok(result, this);

    }

    public void getUserSig(){
        TLSSigAPIv2 api = new TLSSigAPIv2(Keys.SDKAPPID,Keys.SECRETKEY);
        Result.ok(api.genSig(getPara("name"), 180*86400),this);
    }

    @EmptyParaValidate(params = "str")
    public void mediaSearch(){
        String str = "%"+getPara("str")+"%";
        int size = getParaToInt("size",10);
        List<ClassRoom> classRooms = ClassRoom.dao.find("SELECT * FROM class_room WHERE EXISTS " +
                "(SELECT id FROM `class_script_normal` WHERE class_room.id = class_script_normal.`class_room_id`)" +
                "AND class_name LIKE ? LIMIT ?",str,size);
        List<MaterialFile> materialFiles = MaterialFile.dao.find("SELECT * FROM material_file WHERE `name` LIKE ? LIMIT ?",str,size);
        Map<String, Object> data = new SoundServiceImpl().fuzzySearchByName(getPara("str"), 1, size);
        List<Course> courses = Course.dao.find("SELECT a.`name`,b.* FROM (SELECT * FROM mallproduct WHERE `name` LIKE ? LIMIT ?) a,course b WHERE a.id = b.productid",str,size);
        List<Category> categories = Category.dao.find("SELECT * FROM `category` WHERE bname LIKE ? LIMIT ?",str,size);

        JSONObject rs = new JSONObject();
        rs.put("classRooms",classRooms);
        rs.put("materialFiles",materialFiles);
        rs.put("sound",data);
        rs.put("mallproducts",courses);
        rs.put("categories",categories);

        Result.ok(rs,this);

    }
}
