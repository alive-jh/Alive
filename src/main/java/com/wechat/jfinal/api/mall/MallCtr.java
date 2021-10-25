package com.wechat.jfinal.api.mall;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class MallCtr extends Controller {

    public void mallIndexProducts() {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT l.id AS label_id, l.`name` AS label_name , GROUP_CONCAT(c.id, '¿', c.`name`, '¿', c.logo1, '¿', c.price SEPARATOR '卐') AS");
        sql.append(" product_str FROM malllabel a JOIN productlabel b ON a.labelId = b.labelid AND a.wechat = 0 JOIN label l ON a.labelId = l.id JOIN");
        sql.append(" mallproduct c ON b.productid = c.id AND c.`status` = 0 LEFT JOIN mallspecifications d ON c.id = d.productid GROUP BY l.id");

        List<Record> results = Db.find(sql.toString());

        for (Record result : results) {

            String productStr = result.getStr("product_str");
            String[] _productStr = productStr.split("卐");

            List<Mallproduct> mallproducts = new ArrayList<>(_productStr.length);
            for (String product : _productStr) {
                String[] productFields = product.split("¿");
                Mallproduct mallproduct = new Mallproduct();
                mallproduct.setId(Integer.parseInt(productFields[0]));
                mallproduct.setName(productFields[1]);
                mallproduct.setLogo1(productFields[2]);
                mallproduct.setPrice(productFields[3]);
                mallproducts.add(mallproduct);
            }

            result.set("products", Result.makeupList(mallproducts));
            result.remove("product_str");

        }

        JSONObject json = new JSONObject();
        json.put("indexlabel", Result.makeupList(results));
        Result.ok(json, this);

    }

    public void wechatMallLabel() {

        List<Label> labels = Label.dao.find("SELECT b.* FROM malllabel a JOIN label b ON a.labelId = b.id WHERE a.wechat = 0");

        JSONObject json = new JSONObject();
        json.put("labels", Result.makeupList(labels));

        Result.ok(json, this);

    }

    public void getPoductByLabelId() {
        Integer labelId = getParaToInt("labelId");

        if (labelId == null) {
            Result.error(203, this);
            return;
        }

        List<Mallproduct> mallproducts = Mallproduct.dao.find(
                "SELECT a.id ,a.`name`,a.logo1,a.price FROM mallproduct a JOIN productlabel b ON a.id = b.productid WHERE b.labelid = ? AND a.`status` = 0"
                , labelId);

        JSONObject json = new JSONObject();
        json.put("mallproducts", Result.makeupList(mallproducts));
        Result.ok(json, this);

    }

    public void productInfo() {
        Integer productId = getParaToInt("id");

        if (productId == null) {
            Result.error(203, this);
            return;
        }

        Mallproduct mallproduct = Mallproduct.dao.findById(productId);

        JSONObject json = new JSONObject();
        json.put("mallproduct", mallproduct == null ? null : mallproduct.toJson());

        Result.ok(json, this);
    }

    @EmptyParaValidate(params = {"productId"})
    public void classRoomPackInfo() {


        Integer productId = getParaToInt("productId");

        MallProduct2 packages = MallProduct2.dao.findFirst("SELECT a.*, b.coin ,b.original_coin FROM `mall_product2` a , mall_product_specification b WHERE b.mall_product_id = a.id AND a.id =?", productId);

        List<ClassroomPackageRel> classroomPackageRels = ClassroomPackageRel.dao.find("select a.class_name from class_room a where a.id IN (select b.classroom_id from classroom_package_rel b where b.package_id=?  order by b.do_day asc)", packages.getClassRoomPackageId());

        JSONObject json = new JSONObject();

        json.put("packDetails", Result.toJson(packages));

        json.put("classRoomNameList", Result.makeupList(classroomPackageRels));

        Result.ok(json, this);
    }

    @EmptyParaValidate(params = {"account"})
    public void createInvitationCode() {
        int memberaccountId = getParaToInt("account");

        Memberaccount memberaccount = Memberaccount.dao.findById(memberaccountId);

        if (memberaccount == null) {
            Result.error(20501, "用户不存在", this);
        }

        AccountInvitationCode invitationCode = AccountInvitationCode.dao.findFirst("select * from account_invitation_code where account_id=?", memberaccountId);

        if (invitationCode == null) {
            AccountInvitationCode accountInvitationCode = new AccountInvitationCode();
            accountInvitationCode.setAccountId(memberaccountId);
            accountInvitationCode.setCode(RandomStringUtils.randomNumeric(6));
            accountInvitationCode.save();

            Result.ok(accountInvitationCode, this);
        } else {
            Result.ok(invitationCode, this);
        }

    }

    @EmptyParaValidate(params = {"account"})
    public void InvitationRecord() {

        Integer account = getParaToInt("account");

        AccountInvitationCode accountInvitation = AccountInvitationCode.dao.findFirst("select * from account_invitation_code  where  account_id=?", account);

        String code = accountInvitation.getCode();

        List<AccountInvitationRecord> accountInvitationRecords = AccountInvitationRecord.dao.find("select * from account_invitation_record where  code=?", code);

        int count = 0;

        if (accountInvitationRecords != null) {
            for (AccountInvitationRecord accountInvitationRecord : accountInvitationRecords) {
                count++;
            }
        }

        JSONObject json = new JSONObject();
        Integer coinNum = CoinGift.dao.findFirst("select * from coin_gift where id = 11").getCoinCount();
        json.put("count", count);
        json.put("coin", count * coinNum);
        json.put("invitationCode", code);

        Result.ok(json, this);
    }

    @EmptyParaValidate(params = {"name"})
    public void searchGoods() {

        String name = getPara("name");

        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 10);

        if (name == null || name == "") {
            Result.error(20502, "搜索不能为空", this);
        }

        Page<Record> page = Db.paginate(pageNumber, pageSize, "select a.*,b.original_coin  ", "from mall_product2 a , mall_product_specification b  where a.name like '%" + name + "%' and b_show=1 AND  b.mall_product_id= a.id");


        Result.ok(page, this);
    }


    @EmptyParaValidate(params = {"account"})
    public void myInvitation() {

        Integer account = getParaToInt("account");

        AccountInvitationCode accountInvitation = AccountInvitationCode.dao.findFirst("select * from account_invitation_code  where  account_id=?", account);

        String code = accountInvitation.getCode();

        JSONObject json = new JSONObject();

        List<Memberaccount> memberaccount = Memberaccount.dao.find("select a.account,DATE_FORMAT(b.create_time,'%Y-%m-%d') as invitationTime  from memberaccount a left join account_invitation_record b on b.account_id=a.id where b.code = ?  order by b.create_time desc", code);
        json.put("accountList", Result.makeupList(memberaccount));
        Integer coinNum = CoinGift.dao.findFirst("select * from coin_gift where id = 11").getCoinCount();
        json.put("count", memberaccount.size());
        json.put("coinNum", coinNum * memberaccount.size());


        Result.ok(json, this);

    }

    /**
     * 填写邀请码 补 注册豆币
     */
    @EmptyParaValidate(params = {"accountId", "invitationCode"})
    public void InvitationMemberGetCoin() {
        Integer accountId = getParaToInt("accountId");
        String invitationCode = getPara("invitationCode");
        AccountInvitationCode accountInvitationCode = AccountInvitationCode.dao.findFirst("select * from account_invitation_code where code=? and account_id != ?", invitationCode,accountId);

        if (accountInvitationCode == null) {
            Result.error(20510, "邀请码无效", this);
            return;
        }

        AccountInvitationRecord accountInvitationRecord = AccountInvitationRecord.dao.findFirst("select * from account_invitation_record where account_id =?", accountId);
        if (accountInvitationRecord == null) {
            accountInvitationRecord = new AccountInvitationRecord();
            accountInvitationRecord.setAccountId(accountId);
            accountInvitationRecord.setCode(invitationCode);

            List<CoinGift> coinGiftList = CoinGift.dao.find("select * from coin_gift where id in (9,11)");

            for (CoinGift coinGift : coinGiftList) {
                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAction(1);
                studyCoinRecord.setRemark(coinGift.getName());
                if (coinGift.getId() == 9) {
                    studyCoinRecord.setAccountId(accountInvitationCode.getAccountId());
                } else {
                    studyCoinRecord.setAccountId(accountId);
                }
                studyCoinRecord.setCount(coinGift.getCoinCount());
                studyCoinRecord.save();
            }

            if (accountInvitationRecord.save()) {
                Result.ok(this);
            } else {
                Result.error(20500, "添加失败，请联系管理员！", this);
            }
        } else {
            Result.error(20520, "不能重复获取豆币", this);
        }
    }

    @EmptyParaValidate(params = {})
    public void mallIndex() {

        //首页轮播图
        List<SnpMallBanner> snpMallBanners = SnpMallBanner.dao.find("select a.id,a.pic,a.link from snp_mall_banner a where a.status=1 order by a.create_time desc");

        //专区分类名
        List<MallProductCategory> mallProductCategories = MallProductCategory.dao.find("SELECT a.name, a.id   FROM  mall_product_category a  WHERE  a.`status`=1 and a.show_home=0");

        JSONObject json = new JSONObject();
        json.put("mallBanners", Result.makeupList(snpMallBanners));
        json.put("categoryName", Result.makeupList(mallProductCategories));


        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"categoryId"})
    public void categoryInfo() {

        Integer categoryId = getParaToInt("categoryId");

        List<Record> records = Db.find("select a.* , c.`name` AS categoryName,mps.`original_coin`  from mall_product2 a JOIN mall_product_category_rel b on b.mall_product_id= a.id join mall_product_category c on  c.id = b.mall_product_category JOIN mall_product_specification mps ON  a.id=mps.mall_product_id WHERE  c.id=?  ORDER BY  a.create_time DESC", categoryId);

        Result.ok(records, this);

    }

    @EmptyParaValidate(params = {})
    public void activityInfo() {

        List<MallProductCategory> activityZone = MallProductCategory.dao.find("SELECT mps.original_coin,mp.id,mp.class_room_package_id , mp.`name`, mp.`desc`, mp.content, mp.price_range,mp.pics,mp.video, mp.create_time, mpc.id AS categoryId, mpc.`name` AS categoryName FROM mall_product2 mp LEFT JOIN mall_product_category_rel mpcr ON mp.id = mpcr.mall_product_id LEFT JOIN mall_product_category mpc ON mpc.id = mpcr.mall_product_category LEFT JOIN mall_product_specification mps on mps.mall_product_id=mp.id WHERE mpc.show_home=1 AND mp.b_show=1 GROUP BY mp.`name`, mp.`desc`, mp.content, mp.price_range, mpc.id , mpc.`name` HAVING count(mpcr.id)<3 ORDER BY mp.create_time desc ");

        JSONObject json = new JSONObject();
        json.put("activityZone", Result.makeupList(activityZone));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"productId"})
    public void confirmOrder() {

        int memberaccountId = Integer.parseInt(getSession().getAttribute("memberaccount_id").toString());

        Integer productId = getParaToInt("productId");

        Memberaccount memberaccount = Memberaccount.dao.findById(memberaccountId);

        MallProduct2 mallProduct2 = MallProduct2.dao.findFirst("select mps.id as specId, mps.original_coin,mps.coin,mps.price,mp.id ,mp.`name`,mp.pics,mp.video,mp.`desc`,mp.class_room_package_id FROM mall_product2 mp ,mall_product_specification mps  WHERE  mp.id=? AND  mps.mall_product_id=mp.id", productId);

        List<DeviceRelation> deviceRelations = DeviceRelation.dao.find("select cs.name,cs.id as studentId from device_relation dr LEFT JOIN class_student  cs  on cs.epal_id = dr.epal_id where friend_id='" + memberaccount.getAccount() + "' and dr.isbind=1 AND cs.`status`>-1");

        StudyCoinRecord coinRecord = StudyCoinRecord.dao.findFirst("select ifNull(SUM(count),0) as coinNum ,account_id FROM `study_coin_record`  WHERE account_id= ?", memberaccount.getId());

        JSONObject json = new JSONObject();
        json.put("mallProduct", Result.toJson(mallProduct2));
        json.put("studentName", Result.makeupList(deviceRelations));
        json.put("coinInfo", Result.toJson(coinRecord));


        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"studentId", "packageId", "specId"})
    public void coinPayInfo() {

        int memberaccountId = Integer.parseInt(getSession().getAttribute("memberaccount_id").toString());

        Integer specId = getParaToInt("specId");

        String packageId = getPara("packageId");

        int studentId = getParaToInt("studentId");

        MallProductSpecification productSpecification = MallProductSpecification.dao.findById(specId);

        ClassroomPackage classroomPackage = ClassroomPackage.dao.findById(packageId);

        StudyCoinRecord coinRecord = StudyCoinRecord.dao.findFirst("select ifnull(SUM(count),0) as coinNum FROM `study_coin_record`  WHERE account_id= ?", memberaccountId);

        Integer coinNum = coinRecord.getInt("coinNum");

        if (coinNum > productSpecification.getCoin()) {

            try {
                ClassroomPackageGrades classroomPackageGrades = ClassroomPackageGrades.dao.findFirst("SELECT * from classroom_package_grades WHERE pack_id = ?", classroomPackage.getId());
                ClassGradesRela classGradesRela = new ClassGradesRela();
                classGradesRela.setClassStudentId(studentId);
                classGradesRela.setClassGradesId(classroomPackageGrades.getGradesId());
                classGradesRela.setGradesStatus(1).save();

                ClassCourse classCourse = ClassCourse.dao.findFirst("SELECT * FROM `class_course` WHERE class_grades_id = ? AND do_slot = 300 ORDER BY do_day DESC LIMIT 1", classGradesRela.getClassGradesId());

                ClassCourseSchedule classCourseSchedule = ClassCourseSchedule.dao.findFirst("SELECT * FROM `class_course_schedule` WHERE class_grades_id = ? AND student_id = ?", classGradesRela.getClassGradesId(), classGradesRela.getClassStudentId());
                if (classCourseSchedule == null) {
                    classCourseSchedule = new ClassCourseSchedule();
                }
                classCourseSchedule.setStudentId(classGradesRela.getClassStudentId());
                classCourseSchedule.setClassCourseId(classCourse.getId());
                classCourseSchedule.setClassGradesId(classGradesRela.getClassGradesId());
                classCourseSchedule.setClassRoomId(classCourse.getClassRoomId());
                classCourseSchedule.setDoDay(classCourse.getDoDay());

                if (classCourseSchedule.getId() == null) {
                    classCourseSchedule.save();
                } else {
                    classCourseSchedule.update();
                }

                StdDiyStudyDay stdDiyStudyDay = StdDiyStudyDay.dao.findFirst("SELECT * FROM `std_diy_study_day` WHERE std_id = ? AND grade_id = ?", classGradesRela.getClassStudentId(), classGradesRela.getClassGradesId());
                if (stdDiyStudyDay == null) {
                    stdDiyStudyDay = new StdDiyStudyDay();
                }
                stdDiyStudyDay.setStdId(classGradesRela.getClassStudentId());
                stdDiyStudyDay.setGradeId(classGradesRela.getClassGradesId());
                stdDiyStudyDay.setRule("[{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":0},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":1},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":2},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":3},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":4},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":5},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":6}]");
                stdDiyStudyDay.setWeek("1111111");
                stdDiyStudyDay.setIsTeacherDefault(1);
                if (stdDiyStudyDay.getId() == null) {
                    stdDiyStudyDay.save();
                } else {
                    stdDiyStudyDay.update();
                }


                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAccountId(memberaccountId);
                studyCoinRecord.setRemark("购买商品" + classroomPackage.getName() + "课程包");
                studyCoinRecord.setAction(2);
                studyCoinRecord.setCount(-productSpecification.getCoin());
                studyCoinRecord.save();

                MallProductOrder mallProductOrder = new MallProductOrder();
                mallProductOrder.setOrderNumber(RandomStringUtils.randomNumeric(16));
                mallProductOrder.setProductSpecId(specId);
                mallProductOrder.setAccountId(memberaccountId);
                mallProductOrder.setStudentId(studentId);
                mallProductOrder.save();

                Result.ok(this);
                return;
            } catch (ActiveRecordException e) {
                e.printStackTrace();
                Result.error(20501, "已加入班级", this);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Result.error(20502, "购买失败", this);
                return;
            }
        } else {
            Result.error(20509, "豆币不足，请前往任务中心获取豆币", this);
        }

    }

    public void orderList() {
        Integer account = (Integer) getSession().getAttribute("memberaccount_id");
        if (account == null) {
            account = getParaToInt("account");
        }
        redirect("/lesson/mall/html/order.html?account=" + account);
    }

    public void getOrderList() {

        List<Record> orders = Db.find("SELECT a.*, c.pics, c.`name`, c.`desc`, d.`name` AS studentName FROM mall_product_order a JOIN mall_product_specification b ON a.product_spec_id = b.id JOIN mall_product2 c ON b.mall_product_id = c.id JOIN class_student d ON d.id = a.student_id WHERE a.account_id = ?", getParaToInt("account"));
        Result.ok(orders, this);
    }

    public void getPeople() {

        Cache redis = Redis.use();


        String data = redis.get("BUY_PEOPLE_COUNT");

        JSONObject json = null;
        if (data == null) {
            json = new JSONObject();
        } else {
            json = JSONObject.fromObject(data);
        }

        List<MallProduct2> mallProduct2 = MallProduct2.dao.find("SELECT * FROM mall_product2 where 1=1");

        for (MallProduct2 product : mallProduct2) {
            int old = json.has(product.getId() + "") ? json.getInt(product.getId() + "") : 10;
            json.put(product.getId() + "", new Double(Math.ceil(old + old * (Math.random() * (0.3 - 0.1)))).intValue());
        }

        redis.set("BUY_PEOPLE_COUNT", json.toString());

        Result.ok(json, this);

    }


    public void peoPleCount() {

        getResponse().addHeader("Access-Control-Allow-Origin", "*");

        Cache redis = Redis.use();
        String data = redis.get("BUY_PEOPLE_COUNT");

        JSONObject json = JSONObject.fromObject(data);

        if (data == null) {
            getPeople();
            json = JSONObject.fromObject(data);
        }

        Result.ok(json.toString(), this);

    }

    public void moreMall() {
        Integer catId = getParaToInt("catId", 0);
        String mobile = getPara("mobile");
        int pageNumber = getParaToInt("pageNumber", 1);

        int pageSize = getParaToInt("pageSize", 10);
        Page<Record> page = Db.paginate(pageNumber, pageSize, "SELECT\n" +
                        "\ta.id,\n" +
                        "\ta.name,\n" +
                        "\ta.logo1 logo,\n" +
                        "\ta.price,\n" +
                        "\ta.mp3type,\n" +
//                        "\tifnull(a.saleCount, FLOOR(RAND() * 100))+200 saleCount, \n" +
                        "\ta.saleCount+200 saleCount, \n" +
                        "\tifnull(count(b.productid),0) courseCount\n"
                , " FROM\n" +
                        "( SELECT\n" +
                        "* \n" +
                        "FROM\n" +
                        "\tmallproduct a\n" +
                        "\tLEFT JOIN ( SELECT productid, COUNT( id ) AS saleCount FROM membermp3 GROUP BY productid ) b ON 1 = 1 \n" +
                        "\tAND a.id = b.productid \n" +
                        "\t) a\n" +
                        "\tLEFT JOIN course b ON a.id = b.productid \n" +
                        "WHERE\n" +
                        "\ta.cat_id = ? \n" +
                        "\tAND a.mp3type = 2 \n" +
                        "GROUP BY\n" +
                        "\tb.productid order by a.id desc"
                , catId);
        Result.ok(page, this);
    }

    @EmptyParaValidate(params = {"productId", "memberaccountId"})
    public void exchangeMallProduct() {

        Integer memberaccountId = getParaToInt("memberaccountId");
        int productId = getParaToInt("productId");
        Mallproduct mallproduct = Mallproduct.dao.findFirst("select * from mallproduct where id = ?", productId);
        StudyCoinRecord coinRecord = StudyCoinRecord.dao.findFirst("select ifnull(SUM(count),0) as coinNum FROM `study_coin_record`  WHERE account_id= ?", memberaccountId);
        Integer coinNum = coinRecord.getInt("coinNum");

        mallproduct.setPrice(mallproduct.getPrice() == null ? "0" : mallproduct.getPrice());

        if (coinNum > Integer.parseInt(mallproduct.getPrice())) {

            StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
            studyCoinRecord.setAccountId(memberaccountId);
            studyCoinRecord.setRemark("兑换课程教材" + mallproduct.getName());
            studyCoinRecord.setAction(2);
            studyCoinRecord.setCount(-Integer.parseInt(mallproduct.getPrice()));
            studyCoinRecord.save();
            Result.ok(this);
        } else {
            Result.error(20509, "豆币不足，请前往任务中心获取豆币", this);
        }
    }



}