package com.wechat.jfinal.api.classRoom;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.controller.PaymentController;
import com.wechat.entity.MallAccessToken;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.*;
import com.wechat.jfinal.service.ClassRoomSvc;
import com.wechat.jfinal.service.WechatPayService;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.CommonUtils;
import com.wechat.util.HttpRequest;
import com.wechat.util.Keys;
import com.wechat.util.SecurityUtil;

import net.sf.json.JSONObject;

//机器人上传操作
public class ClassRoomHandle extends Controller {

    private Cache redis = Redis.use();

    /*
     *
     * 保存在线课堂（机器人） 表格：class_room 备注：
     *
     *
     */
    public void saveClassRoom() {
        ClassRoom classRoom = getBean(ClassRoom.class, "");

        Integer classRoomId = classRoom.getId();

        if (classRoomId != null) {
            classRoom.update();
        } else {
            classRoom.save();
        }

        String tempId = getPara("tempId", "");
        if (null != tempId) {
            HashMap<String, String> map = new HashMap<String, String>();

            List<ClassScriptNormal> ClassScriptNormals = ClassScriptNormal.dao
                    .find("select * from class_script_normal where class_room_id=" + tempId);

            for (int i = 0; i < ClassScriptNormals.size(); i++) {
                ClassScriptNormal classScriptNormal = ClassScriptNormals.get(i);
                classScriptNormal.setId(null);
                classScriptNormal.setClassRoomId(classRoomId);
                classScriptNormal.save();
            }

        }

    }

    // 保存标签
    public void saveClassRoomLabel() {
        int classRoomId = getParaToInt("classRoomId", 0);
        int labelId = getParaToInt("labelId", 0);
        String labelName = getPara("labelName");
        int memberId = getParaToInt("memberId", 0);
        if (0 != labelId) {

        } else {
            ClassRoomLabel classRoomLabel = new ClassRoomLabel();
            classRoomLabel.setLabelName(labelName);
            classRoomLabel.save();
            labelId = classRoomLabel.getId();
        }
        ClassRoomLabelRela ClassRoomLabelRela = new ClassRoomLabelRela();
        ClassRoomLabelRela.setClassRoomId(classRoomId);
        ClassRoomLabelRela.setLabelId(labelId);
        ClassRoomLabelRela.save();
        if (0 == memberId) {

        } else {
            ClassRoomLabelUser ClassRoomLabelUser = new ClassRoomLabelUser();
            ClassRoomLabelUser.setLabelId(labelId);
            ClassRoomLabelUser.setMemberId(memberId);
            ClassRoomLabelUser.save();
        }
        renderJson(JsonResult.JsonResultOK("ok"));
    }

    // 根据用户获取标签列表
    public void getClassRoomLabel() {
        int memberId = getParaToInt("memberId", 0);
        List<ClassRoomLabel> result = new ArrayList();
        if (0 == memberId) {

        } else {
            result = ClassRoomLabel.dao.find(
                    "SELECT b.* from class_room_label_user as a,class_room_label as b where a.label_id=b.id and a.member_id=?",
                    memberId);

        }
        renderJson(JsonResult.JsonResultOK(result));
    }

    /*
     * 用户删除标签
     *
     *
     *
     */
    public void deleteClassRoomLabel() {
        int memberId = getParaToInt("memberId", 0);
        int labelId = getParaToInt("labelId", 0);
        if (0 == memberId || 0 == labelId) {

        } else {
            ClassRoomLabelUser classRoomLabelUser = ClassRoomLabelUser.dao.findFirst(
                    "select * from class_room_label_user where member_id=? and label_id=?", memberId, labelId);
            if (null == classRoomLabelUser) {

            } else {
                ClassRoomLabelUser.dao.deleteById(classRoomLabelUser.getId());
            }

        }
        renderJson(JsonResult.JsonResultOK());
    }

    // 根据系统默认标签
    public void getDefaultClassRoomLabel() {
        List<ClassRoomLabel> result = ClassRoomLabel.dao.find("SELECT b.* from class_room_label as b where is_show=1");
        renderJson(JsonResult.JsonResultOK(result));
    }

    public void saveClassPackStudentRel() {
        Integer studentId = getParaToInt("studentId", 0);
        Integer classPackId = getParaToInt("classPackId", 0);

        if (xx.isAllEmpty(studentId, classPackId)) {
            Result.error(203, this);
            return;
        }

        RoomPackage roomPackage = RoomPackage.dao.findById(classPackId);
        ClassGrades classGrades = new ClassGrades();
        classGrades.setClassGradesName(roomPackage.getName());
        classGrades.setParentId(0);
        classGrades.setSummary(roomPackage.getName());
        classGrades.setCover(roomPackage.getCover());
        classGrades.setGradesType("virtualClass");
        classGrades.setTeacherId(6);
        classGrades.setRemark("商城购买课程包创建的班级");
        classGrades.save();
        GradeStudyDayByDay gradeStudyDayByDay = new GradeStudyDayByDay();
        gradeStudyDayByDay.setGradeId(classGrades.getId());
        gradeStudyDayByDay.setDayStr("1");
        gradeStudyDayByDay.save();
        ClassGradesRela classGradesRela = new ClassGradesRela();
        classGradesRela.setClassGradesId(classGrades.getId());
        classGradesRela.setClassStudentId(studentId);
        classGradesRela.setType(0);
        classGradesRela.save();

        try {
            HttpRequest.doPost("http://agent.fandoutech.com.cn/course/addPkg",
                    "?pkgId=" + classPackId + "&isLast=on&gradeId=" + classGrades.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createPay() {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Integer classRoomId = getParaToInt("classRoomId", 0);
        Integer memberId = getParaToInt("memberId", 0);
        String payType = getPara("payType");
        String ip = getPara("ip");
        Integer classPackId = getParaToInt("classPackId");

        String remoteIp = getRequest().getRemoteAddr();
        if (xx.isOneEmpty(classRoomId, memberId, payType, ip)) {
            Result.error(203, this);
            return;
        }

        ClassroomPackage classroomPackage = ClassroomPackage.dao.findById(classPackId);
        ClassStudent classStudent = ClassStudent.dao.findFirst("select * from class_student where member_id = ?",
                memberId);

        String currTime = TenpayUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = TenpayUtil.buildRandom(4) + "";

        String orderNumber = strTime + strRandom + memberId;

        HashMap<String, Object> paramets = new HashMap<String, Object>();

        paramets.put("classRoomId", classRoomId);
        paramets.put("classStudentId", classStudent.getId());
        paramets.put("classPackId", classPackId);
        paramets.put("commodityName", classroomPackage.getName());
        paramets.put("totalPrice", 0.01);
        paramets.put("callBackUrl", Keys.SERVER_SITE + "/wechat/v2/classRoom/ClassRoomHandle/classRoomPayResult");
        paramets.put("spbillCreateIp", ip);
        paramets.put("appId", Keys.APP_ID);
        paramets.put("mchId", Keys.MCH_ID);
        paramets.put("memberId", memberId);
        paramets.put("orderNumber", orderNumber);
        paramets.put("payType", payType);
        paramets.put("apiKey", Keys.API_KEY);

        ClassRoomSvc classRoomSvc = new ClassRoomSvc();

        boolean success = classRoomSvc.createOrder(paramets);

        WechatPayService wechatPayService = new WechatPayService();

        try {
            JSONObject json = new JSONObject();
            String url = wechatPayService.pay(paramets);
            if (payType.equals("NATIVE")) {
                json.put("codeUrl", url);
            } else {
                json.put("mwebUrl", url);
            }
            json.put("orderNumber", orderNumber);
            Result.ok(json, this);
        } catch (Exception e) {
            e.printStackTrace();
            Result.error(205, "微信支付统一下单失败 ", this);
        }

    }

    /**
     * 查询订单支付情况
     */
    public void checkOrder() {

        String orderNumber = getPara("orderNumber");

        int payStatus = 0;

        if (xx.isEmpty(orderNumber)) {
            Result.error(203, this);
            return;
        }

        ClassRoomOrder classRoomOrder = ClassRoomOrder.dao
                .findFirst("SELECT * FROM class_room_order WHERE order_number = ?", orderNumber);

        if (classRoomOrder != null) {
            payStatus = classRoomOrder.getStatus();
        } else {
            Result.error(205, "查询不到订单信息", this);
            return;
        }

        JSONObject result = new JSONObject();
        result.put("payStatus", payStatus);
        Result.ok(result, this);
    }

    public void classRoomPayResult() throws Exception {
        HttpServletRequest request = getRequest();
        String result = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int count = 0;
            while ((count = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, count);
            }
            byte[] strByte = bos.toByteArray();
            result = new String(strByte, 0, strByte.length, "utf-8");
            bos.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /** 解析微信返回的信息，以Map形式存储便于取值 */
        Map<String, String> map = XMLUtil.doXMLParse(result);
        String return_code = map.get("return_code");

        if (return_code != null && return_code.equals("SUCCESS")) {
            renderText(PaymentController.setXML("SUCCESS", "OK"));

            // 商户订单号
            String orderNumber = map.get("out_trade_no");
            // 支付结果
            String resultCode = map.get("result_code");

            if ("SUCCESS".equals(resultCode)) {

                Db.update("UPDATE class_room_order SET `status` = 1 WHERE order_number = ?", orderNumber);

            }

        } else {
            renderText(PaymentController.setXML("FAIL", "FAIL"));
        }
    }

    /*
     * public void copytonewtable() { List<ClassRoom> classRooms = ClassRoom.dao
     * .find("SELECT id,book_res_ids,book_res_id FROM `class_room` WHERE teacher_id = 103"
     * );
     *
     * List<ClassRoomAudioRel> classRoomAudioRels = new
     * ArrayList<ClassRoomAudioRel>(); for (ClassRoom classRoom : classRooms) {
     *
     * if (classRoom.getBookResIds() != null) {
     *
     * String[] temp = classRoom.getBookResIds().split(",");
     *
     * for (int i = 0; i < temp.length; i++) {
     *
     * ClassRoomAudioRel classRoomAudioRel = new ClassRoomAudioRel();
     * classRoomAudioRel.setClassRoomId(classRoom.getId());
     * classRoomAudioRel.setAudioId(Integer.parseInt(temp[i]));
     * classRoomAudioRels.add(classRoomAudioRel);
     *
     * }
     *
     * } else {
     *
     * ClassRoomAudioRel classRoomAudioRel = new ClassRoomAudioRel();
     * classRoomAudioRel.setClassRoomId(classRoom.getId());
     * classRoomAudioRel.setAudioId(classRoom.getBookResId());
     * classRoomAudioRels.add(classRoomAudioRel);
     *
     * } }
     *
     * //System.out.println(classRoomAudioRels.size());
     * //System.out.println(Db.batchSave(classRoomAudioRels, 1000)); }
     */

    public void showProducts() throws UnsupportedEncodingException, ClassNotFoundException, IllegalArgumentException,
            IllegalAccessException {

        Integer type = getParaToInt("type", 2);

        String access_token = getPara("access_token");

        Integer classRoomId = getParaToInt("classRoomId", 0);

        if (xx.isOneEmpty(classRoomId, access_token)) {
            Result.error(203, this);
            return;
        }

        ClassRoom classRoom = ClassRoom.dao.findFirst("select * from class_room where id = ?", classRoomId);

        Integer teacherId = classRoom.getTeacherId();

        redirect(Keys.SERVER_SITE + "/fandoumall/search/" + teacherId + "/" + type + "?accessTokenKey=" + access_token
                + "&mallType=care&teacherId=" + teacherId, true);

    }

    /**
     * 通过课堂ID获取课堂信息
     */
    public void getClassRoom() {
        int classRoomId = getParaToInt("classRoomId", 0);
        ClassRoom classRoom = ClassRoom.dao.findById(classRoomId);
        if (null == classRoom) {
            Result.error(404, this);
            return;
        } else {
            //System.out.println(Result.toJson(classRoom.toRecord().getColumns()));
            Result.ok(classRoom, this);
            return;
        }
    }

    /**
     * 跳转到商城获取商品信息
     *
     * @throws UnsupportedEncodingException
     */
    public void productInfo() throws UnsupportedEncodingException {

        Integer productId = getParaToInt("productId", 0);

        Integer rebate = getParaToInt("rebate");

        String access_token = getPara("access_token");

        if (xx.isEmpty(productId)) {
            Result.error(203, this);
            return;
        }

        Integer techerId = Db.findFirst("select teacher_id from v_teacher_product where id = ?", productId)
                .getInt("teacher_id");

        String redirectUrl = Keys.SERVER_SITE + "/fandoumall/search/product/" + productId + "?accessTokenKey=" + access_token
                + "&mallType=care&teacherId=" + techerId;

        if (rebate != null) {

            if (rebate == 100) {
                Result.error(205, "折扣信息错误", this);
                return;
            }

            redirectUrl += "&rebate" + rebate;
        }

        redirect(redirectUrl, true);

    }

    public void saveLessonQuality() {
        StudentLessonQuality lessonQuality = getBean(StudentLessonQuality.class, "");
        lessonQuality.save();
        Result.ok(this);
    }

    public void getLessonQuality() {

        Integer studentId = getParaToInt("studentId", 0);

        Integer classRoomId = getParaToInt("classRoomId");

        String sql = "select * from student_lesson_quality where student_id = ?";

        if (classRoomId != null) {
            sql += " and class_room_id = " + classRoomId;
        }
        List<StudentLessonQuality> lessonQualitys = StudentLessonQuality.dao
                .find(sql, studentId);

        Result.ok(lessonQualitys, this);
    }


    /**
     * 课堂分类（带分类课堂数量）
     * 2019.8.24
     *
     * @author pang
     */
    public void findClassRoomCategorys() {

        int classTeacherId = getParaToInt("classTeacherId", 0);

        List<ClassRoomCategory> classRoomCategoryList = ClassRoomCategory.dao.find("select * from class_room_category where parent_id = 1");
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id) as categoryType1");
        for (ClassRoomCategory category : classRoomCategoryList) {
            sql.append(", count(case when category_id=" + category.getId() + " then 1 end) as categoryType" + category.getId());
        }

        Record record;
        //资源请求还是老师请求
        if (classTeacherId != 0) {
            sql.append(" from class_room where teacher_id = ? and status >= 0");
            record = Db.findFirst(sql.toString(), classTeacherId);
        } else {
            sql.append(" from class_room where status >= 0");
            record = Db.findFirst(sql.toString());
        }

        for (int i = 0; i < classRoomCategoryList.size(); i++) {
            if (record.getInt("categoryType" + classRoomCategoryList.get(i).getId()) == 0) {
                classRoomCategoryList.remove(i);
                i--;
            } else {
                classRoomCategoryList.get(i).put("number", record.getInt("categoryType" + classRoomCategoryList.get(i).getId()));
            }
        }
        ClassRoomCategory category = new ClassRoomCategory();
        category.setCategoryName("全部分类");
        category.put("number", record.getInt("categoryType1"));
        classRoomCategoryList.add(0, category);

        renderJson(Rt.success(classRoomCategoryList));
    }

    /**
     * 获取标签
     * 2019.8.28
     *
     * @author pang
     */
    @EmptyParaValidate(params = {"classRoomId"})
    public void findClassRoomLabel() {
        int classRoomId = getParaToInt("classRoomId", 0);

        List<ClassRoomLabel> classRoomLabelList = ClassRoomLabel.dao.find("select * from class_room_label where id in " +
                "(select label_id from class_room_label_rela where class_room_id = ? and status = 1)", classRoomId);

        renderJson(Rt.success(classRoomLabelList));
    }
}
