package com.wechat.jfinal.api.classRoom;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wechat.jfinal.service.ClassBookingService;
import org.jdom.JDOMException;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.controller.PaymentController;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassProductOrder;
import com.wechat.jfinal.model.ClassRoomAudioRel;
import com.wechat.jfinal.model.ClassRoomOrder;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.IntegralWallet;
import com.wechat.jfinal.model.IntegralWalletRecord;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Mallspecifications;
import com.wechat.jfinal.model.MaterialFile;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.service.ClassroomPackageService;
import com.wechat.jfinal.service.StudentService;
import com.wechat.jfinal.service.WechatPayService;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.CommonUtils;
import com.wechat.util.Keys;
import com.wechat.util.SecurityUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClassProductCtr extends Controller {

    public void productPay() {

        Integer studenId = getParaToInt("studentId");

        Integer mallProductId = getParaToInt("mallProductId");

        String ip = getRequest().getHeader("x-forwarded-for");

        String payType = getPara("payType", "NATIVE");

        if (xx.isOneEmpty(studenId, mallProductId, ip)) {
            Result.error(203, this);
            return;
        }

        Mallproduct mallproduct = Mallproduct.dao.findById(mallProductId);

        if (mallproduct == null) {
            Result.error(20501, "商品不存在", this);
            return;
        }

        String currTime = TenpayUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = TenpayUtil.buildRandom(8) + "";

        String orderNumber = strTime + strRandom + studenId;

        ClassProductOrder classProductOrder = new ClassProductOrder();

        classProductOrder.setProductId(mallProductId);
        classProductOrder.setProductName(mallproduct.getName());

        ClassStudent classStudent = ClassStudent.dao.findById(studenId);
        classProductOrder.setMemberId(classStudent.getMemberId());

        classProductOrder.setOrderNumber(orderNumber);
        classProductOrder.setStudentId(studenId);

        if (mallproduct.getClassRoomPackage() != 0) {

            classProductOrder.setClassPackId(mallproduct.getClassRoomPackage());

        } else if (mallproduct.getClassGradeId() != 0) {

            classProductOrder.setClassGradesId(mallproduct.getClassGradeId());

        } else if (mallproduct.getClassBooking() != 0) {  //预约课程次数商品

            classProductOrder.setClassBooking(mallproduct.getClassBooking());
        }

        classProductOrder.save();

        Double pirce = Mallspecifications.dao
                .findFirst("select * from mallspecifications where productid = ? order by id asc", mallProductId)
                .getPrice();

        HashMap<String, Object> paramets = new HashMap<String, Object>();

        paramets.put("commodityName", mallproduct.getName());
        paramets.put("totalPrice", pirce);
        paramets.put("callBackUrl", Keys.SERVER_SITE + "/wechat/v2/classproduct/payResult");
        paramets.put("spbillCreateIp", ip);
        paramets.put("appId", Keys.APP_ID);
        paramets.put("mchId", Keys.MCH_ID);
        paramets.put("orderNumber", orderNumber);
        paramets.put("payType", payType);
        paramets.put("apiKey", Keys.API_KEY);

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
            json.put("productName", mallproduct.getName());
            Result.ok(json, this);
        } catch (Exception e) {
            e.printStackTrace();
            Result.error(205, "微信支付统一下单失败 ", this);
        }

    }

    public void payResult() throws JDOMException, IOException {

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

                Db.update("UPDATE class_product_order SET `status` = 1 WHERE order_number = ?", orderNumber);

                // 获取商品
                ClassProductOrder classProductOrder = ClassProductOrder.dao
                        .findFirst("SELECT * FROM `class_product_order` where order_number = ?", orderNumber);
                ClassroomPackageService classroomPackageService = new ClassroomPackageService();

                try {

                    // 绑定班级
                    if (classProductOrder.getClassGradesId() > 0) {
                        classroomPackageService.addStudent2grade(classProductOrder.getStudentId(),
                                classProductOrder.getClassGradesId());
                    }

                    // 绑定课程包
                    if (classProductOrder.getClassPackId() > 0) {

                        classroomPackageService.bindStudentWithPack(classProductOrder.getStudentId(),
                                classProductOrder.getClassPackId());

                    }

                    if (classProductOrder.getClassBooking() > 0) {
                        ClassBookingService classBookingService = new ClassBookingService(classProductOrder.getStudentId(), classProductOrder.getClassBooking());
                        classBookingService.addAppointnum();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Result.error(500, this);
                    return;
                }

            }

        } else {
            renderText(PaymentController.setXML("FAIL", "FAIL"));
        }
    }

    public void checkOrder() {

        String orderNumber = getPara("orderNumber");

        Integer studentId = getParaToInt("studentId");
        Integer productId = getParaToInt("productId");

        int payStatus = 0;

        ClassRoomOrder classRoomOrder = null;
        ClassProductOrder classProductOrder = null;

        if (studentId != null && productId != null) {

            classRoomOrder = ClassRoomOrder.dao.findFirst(
                    "SELECT * FROM class_product_order WHERE student_id = ? and product_id = ? and `status` = 1 order by id desc",
                    studentId, productId);

        } else if (orderNumber != null) {

            classProductOrder = ClassProductOrder.dao
                    .findFirst("SELECT * FROM class_product_order WHERE order_number = ?", orderNumber);
        } else {

            Result.error(203, this);
            return;
        }

        if (classRoomOrder != null) {
            payStatus = classRoomOrder.getStatus();
        } else if (classProductOrder != null) {
            payStatus = classProductOrder.getStatus();
        } else {
            Result.error(205, "订单未支付", this);
            return;
        }

        JSONObject result = new JSONObject();
        result.put("payStatus", payStatus);
        Result.ok(result, this);
    }

    @EmptyParaValidate(params = {"product", "student", "account", "member"})
    public void integralPay() throws Exception {

        int studentId = getParaToInt("student");

        int productId = getParaToInt("product");

        IntegralWallet integralWallet = IntegralWallet.dao
                .findFirst("select * from integral_wallet where account_id = ?", getParaToInt("account"));

        Mallspecifications mallspecifications = Mallspecifications.dao
                .findFirst("SELECT * FROM `mallspecifications` WHERE productid = ?", productId);

        Mallproduct mallproduct = Mallproduct.dao.findById(productId);

        if (integralWallet != null && mallspecifications.getIntegral() != -1
                && integralWallet.getIntegral() - mallspecifications.getIntegral() > 0) {

            ClassProductOrder classProductOrder = new ClassProductOrder();
            classProductOrder.setProductId(productId);
            classProductOrder.setProductName(mallspecifications.getName());
            classProductOrder.setOrderNumber(CommonUtils.getRandomNumber(12));
            classProductOrder.setStudentId(studentId);
            classProductOrder.setMemberId(getParaToInt("member"));
            classProductOrder
                    .setClassPackId(mallproduct.getClassRoomPackage() > 0 ? mallproduct.getClassRoomPackage() : 0);
            classProductOrder.setClassGradesId(mallproduct.getClassGradeId() > 0 ? mallproduct.getClassGradeId() : 0);
            classProductOrder.setPrice(new BigDecimal(0));
            classProductOrder.setPayMemberId(0);
            classProductOrder.setStatus(1);
            classProductOrder.save();

            ClassroomPackageService classroomPackageService = new ClassroomPackageService();

            if (mallproduct.getClassGradeId() > 0) {
                classroomPackageService.addStudent2grade(getParaToInt("student"), mallproduct.getClassGradeId());
            }

            if (mallproduct.getClassRoomPackage() > 0) {
                classroomPackageService.bindStudentWithPack(studentId, mallproduct.getClassRoomPackage());
            }

            integralWallet.setIntegral((integralWallet.getIntegral() - mallspecifications.getIntegral())).update();

            IntegralWalletRecord integralWalletRecord = new IntegralWalletRecord();
            integralWalletRecord.setIntegralWalletId(integralWallet.getId());
            integralWalletRecord.setDetail(mallspecifications.getIntegral() * -1);
            integralWalletRecord.setDescription("课程购买");
            integralWalletRecord.save();

            JSONObject json = new JSONObject();
            json.put("integralWallet", Result.toJson(integralWallet));

            Result.ok(json, this);

        } else {
            Result.error(20501, "积分不足", this);
        }

    }

    @EmptyParaValidate(params = {"accessToken"})
    public void lessonStore() throws Exception {

        String str = SecurityUtil.detrypt(getPara("accessToken"), "fandou");

        String[] userInfo = str.split("#");
        getSession().setAttribute("account", userInfo[1]);

        render("/lesson/2c/store.html");

    }

    public void integralProductList() {

        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);

        StringBuffer sql = new StringBuffer();
        sql.append("FROM mallproduct a ,mallspecifications b,class_grades c");
        sql.append(" WHERE a.id = b.productid AND b.integral > 0 AND a.class_grade_id = c.id");

        Page<Record> page = Db.paginate(pageNumber, pageSize,
                "SELECT a.id,a.`name` AS product_name,b.integral, c.cover ", sql.toString());
        Result.ok(page, this);

    }

    public void studentList() {

        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?",
                getSession().getAttribute("account"));

        StudentService studentService = new StudentService();
        List<ClassStudent> students = studentService.getUnionStudentList(memberaccount.getAccount(),
                memberaccount.getMemberid());

        JSONArray studentList = new JSONArray();

        for (ClassStudent student : students) {
            JSONObject json = new JSONObject();
            json.put("title", student.getName());
            json.put("value", student.getId());
            studentList.add(json);
        }

        Result.ok(studentList, this);
    }

    public void checkIntegra() {

        IntegralWallet integralWallet = IntegralWallet.dao.findFirst(
                "SELECT a.* FROM `integral_wallet` a,memberaccount b WHERE a.account_id = b.id	 and	b.account = ?",
                getSession().getAttribute("account"));

        Result.ok(integralWallet, this);

    }
	
	/*public void updateClassRoomAudioRel(){
		
		List<ClassRoomAudioRel> audioRels = ClassRoomAudioRel.dao.find("SELECT * FROM `class_room_audio_rel` WHERE material_file_id = 0 and audio_id > 0");
		
		List<MaterialFile> materialFiles = MaterialFile.dao.find("SELECT b.* FROM material_file b WHERE b.audioinfo_id > 0");
		
		
		for (ClassRoomAudioRel classRoomAudioRel : audioRels) {
			
				
				for (MaterialFile materialFile : materialFiles) {
					
					if(classRoomAudioRel.getAudioId().equals(materialFile.getAudioinfoId())){
						classRoomAudioRel.setMaterialFileId(materialFile.getId());
						break;
					}

			}
			
		}
		
		Db.batchUpdate(audioRels, 1000);
		
		Result.ok(this);
		
	}*/

}
