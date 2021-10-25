package com.wechat.jfinal.api.classRoom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.i18n.Res;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassGradesRela;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.ClassroomPackage;
import com.wechat.jfinal.model.ClassroomPackageRel;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Mallspecifications;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.model.Productlabel;
import com.wechat.jfinal.service.ClassroomPackageService;
import com.wechat.util.CommonUtils;
import com.wechat.util.SecurityUtil;

import net.sf.json.JSONObject;

public class ClassRoomPackageCtr extends Controller {

	private final String FANDOUMALL_SERVER = "https://wechat.fandoutech.com.cn/fandoumall";

	private Cache redis = Redis.use();

	/**
	 * 根据老师id获取所有课堂
	 * @param teacherId 老师id 非空
	 * @param keyWord 搜索条件
	 */
	public void classList() {
		Integer teacherId = getParaToInt("teacherId", 0);
		
		String keyWord = getPara("keyWord");
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",1000);
		
		if (xx.isOneEmpty(teacherId)) {
			Result.error(203, this);
			return;
		}

		String sql = "from class_room where teacher_id = ? and status > -1";
		
		if (xx.isEmpty(keyWord) == false){
			sql += " and class_name like '%"+keyWord+"%'";
		}
		
		Result.ok(ClassRoom.dao.paginate(pageNumber, pageSize, "select *",sql+" order by id desc",teacherId), this);
	}
	
	/**
	 * 根据老师id获取课程包商品列表
	 * @param teacherId 老师id 必填
	 * @param keyWord 搜索条件
	 */
	public void classProductList(){
		
		Integer teacherId = getParaToInt("teacherId", 0);
		String keyWord = getPara("keyWord");
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		
		if (xx.isOneEmpty(teacherId)) {
			Result.error(203, this);
			return;
		}
		
		String sql = "FROM classroom_package a LEFT JOIN classroom_package_rel b ON a.id = b.package_id "
				+ "LEFT JOIN class_room c ON b.classroom_id = c.id WHERE a.teacher_id = ? AND a.STATUS > 0";
		
		if (xx.isEmpty(keyWord) == false) { // 是否是模糊搜索
			sql += " AND a.NAME LIKE '%"+keyWord+"%'";
		}
		
		Page<Record> page = Db.paginate(pageNumber, pageSize
				, "SELECT a.*, GROUP_CONCAT(c.id,',',c.class_name,',',c.cover separator '@') AS classRoomStr"
				,sql+" GROUP BY a.id order by a.id desc",teacherId);
		
		//组装数据格式
		for(Record classroomPack : page.getList()){
			
			String classRoomStr = classroomPack.getStr("classRoomStr");
			
			if(classRoomStr!=null){
				String[] classRooms = classRoomStr.split("@");
				
				List<Record> classRommList = new ArrayList<Record>(classRooms.length);
						
				for(String classRoom : classRooms){
					String[] _classRoom = classRoom.split(",");
					Record classRoom_ = new Record();
					classRoom_.set("id", Integer.parseInt(_classRoom[0]));
					classRoom_.set("class_name", _classRoom[1]);
					classRoom_.set("cover", _classRoom[2]);
					classRommList.add(classRoom_);
				}
				classroomPack.remove("classRoomStr");
				classroomPack.set("classrooms", Result.makeupList(classRommList));
			}
			
		}
		
		Result.ok(page,this);
		
	}

	/**
	 * 创建课堂包商品
	 * 
	 * @param classRoomIds 课程id拼接字符串 非空
	 * @param classroomPackage 课程包对象  非空
	 * 
	 */
	public void createPkg() {

		String classRoomIds = getPara("classRoomIds");

		ClassroomPackage classroomPackage = getBean(ClassroomPackage.class,"");

		if (xx.isOneEmpty(classroomPackage,classRoomIds)) {
			Result.error(203, this);
			return;
		}
		
		if(classroomPackage.getId()==null){
			classroomPackage.save();
		}else{
			classroomPackage.update();
		}

		//删除课程包内所有课程
		Db.update("delete from classroom_package_rel where package_id = ?",classroomPackage.getId());
		
		//添加课程到课程包
		String[] _classRoomIds = classRoomIds.split(",");
		
		for (String classRoomId : _classRoomIds) {
			ClassroomPackageRel classroomPackageRel = new ClassroomPackageRel();
			classroomPackageRel.setClassroomId(Integer.parseInt(classRoomId));
			classroomPackageRel.setPackageId(classroomPackage.getId());
			classroomPackageRel.save();
		}

		Mallproduct mallproduct = Mallproduct.dao.findFirst("select * from mallproduct where class_room_package = ?",classroomPackage.getId());
		Mallspecifications mallspecifications = null;
		
		if(mallproduct == null){
			mallproduct = new Mallproduct();
			mallproduct.save();
			
			//绑定商品标签 labelid=103=课程包商品标签
			Productlabel productlabel = new Productlabel();
			productlabel.setLabelid(103);
			productlabel.setProductid(mallproduct.getId());
			productlabel.save();
			
			mallspecifications = new Mallspecifications();
			mallspecifications.setName(classroomPackage.getName());
			mallspecifications.setCount(999);
			mallspecifications.setPrice(classroomPackage.getPrice());
			mallspecifications.setProductid(mallproduct.getId());
			mallspecifications.save();
			
		}else{
			mallspecifications = Mallspecifications.dao.findFirst("select * from mallspecifications where productid = ?",mallproduct.getId());
		}
		
		mallproduct.setName(classroomPackage.getName());
		mallproduct.setCreatedate(new Date());
		mallproduct.setContent(classroomPackage.getSummary());
		mallproduct.setPrice(classroomPackage.getPrice().toString());
		mallproduct.setLogo1(classroomPackage.getCover());
		mallproduct.setStatus(0);
		mallproduct.setClassRoomPackage(classroomPackage.getId());
		mallproduct.update();
		
		mallspecifications.setName(classroomPackage.getName());
		mallspecifications.setPrice(classroomPackage.getPrice());
		mallspecifications.update();
			
		JSONObject json = new JSONObject();
		json.put("classroomPackageId", classroomPackage.getId());
		json.put("productId", mallproduct.getId());
		Result.ok(json,this);
		

	}
	
	/**
	 * 删除课堂包
	 */
	public void delPack (){
		Integer classRoomPackId = getParaToInt("pack",0);
		
		if(xx.isEmpty(classRoomPackId)){
			Result.error(203, this);
			return;
		}
		
		//设置课程包的id为0,伪删除
		Db.update("update classroom_package set status = 0 where id = ?",classRoomPackId);
		//设置商品状态为下架
		Db.update("UPDATE mallproduct SET `status` = 1 WHERE class_room_package = ?",classRoomPackId);
		Result.ok(this);
		
	}
	

	/**
	 * 创建班级商品
	 */
	public void createGrade() {

		Integer gradeId = getParaToInt("gradeId", 0);
		String price = getPara("price");

		if (xx.isOneEmpty(gradeId, price)) {
			Result.error(203, this);
			return;
		}

		ClassGrades classGrades = ClassGrades.dao.findById(gradeId);

		if (xx.isEmpty(classGrades)) {
			Result.error(205, "查询不到班级信息", this);
			return;
		}

		Mallproduct mallproduct = new Mallproduct();
		mallproduct.setName(classGrades.getClassGradesName());
		mallproduct.setCreatedate(new Date());
		mallproduct.setContent(classGrades.getSummary());
		mallproduct.setLogo1(classGrades.getCover());
		mallproduct.setStatus(1);
		mallproduct.setClassGradeId(gradeId);
		mallproduct.setPrice(price);
		mallproduct.save();

		//绑定商品标签 labelid=105=班级商品标签
		Productlabel productlabel = new Productlabel();
		productlabel.setLabelid(105);
		productlabel.setProductid(mallproduct.getId());
		productlabel.save();
		
		
		Mallspecifications mallspecifications = new Mallspecifications();
		mallspecifications.setCount(999);
		mallspecifications.setPrice(Double.parseDouble(price));
		mallspecifications.setProductid(mallproduct.getId());
		mallspecifications.save();

	}

	/**
	 * 商城access_token获取
	 * @param fdb_access_token 原appaccess_token
	 */
	public void mallLogin() {

		String fdb_access_token = getPara("fdb_access_token");

		if (xx.isEmpty(fdb_access_token)) {
			Result.error(203, this);
			return;
		}

		String[] userInfo = null;

		try {
			//token解密获得用户信息数组
			userInfo = SecurityUtil.detrypt(fdb_access_token, "fandou").split("#");
		} catch (Exception e1) {
			e1.printStackTrace();
			Result.error(205, "access_token解析失败", this);
		}

		String account = userInfo[1];
		String password = userInfo[2];

		//数据库查询是否存在
		Memberaccount memberAccount = Memberaccount.dao
				.findFirst("select * from memberaccount where account = ? and password = ?", account, password);

		if (xx.isEmpty(memberAccount)) {
			Result.error(205, "用户不存在", this);
			return;
		}

		JSONObject result = new JSONObject();

		try {

			//重新组装成商城token并放入redis
			String str = memberAccount.getMemberid() + "#notopendid#"+ new Date().getTime() + "#fandoumall#"+memberAccount.getAccount();
			//System.out.println(str);
			String access_token = SecurityUtil.encrypt(str, "fandoumall");
			String key = "token"+CommonUtils.getRandomString(6);
			redis.setex(key, 6 * 60 * 60, access_token);
			result.put("access_token", key);

		} catch (Exception e) {
			e.printStackTrace();
			Result.error(500, this);
		}

		Result.ok(result, this);

	}

	public void showProduct() throws ClassNotFoundException {
		
		Integer teacherId = getParaToInt("teacherId",0);

		if(xx.isEmpty(teacherId)){
			Result.error(203, this);
			return;
		}
		
		redirect(FANDOUMALL_SERVER + "/search/" + teacherId + "/3" + "?mallType=app");
	}
	
	/**
	 * 添加学生到班级
	 * @param graderId 班级id
	 * @param studentId 学生id
	 * @param 订单号
	 */
	public void addStudent2grade(){
		
		Integer gradeId = getParaToInt("gradeId");
		Integer studentId = getParaToInt("studentId");
		
		String orderNumber = getPara("orderNumber");
		
		if(xx.isOneEmpty(gradeId,studentId)){
			Result.error(203, this);
			return;
		}
		
		//创建学生班级绑定不过对象并保存
		ClassGradesRela classGradesRela = new ClassGradesRela();
		classGradesRela.setClassStudentId(studentId);
		classGradesRela.setClassGradesId(gradeId);
		classGradesRela.setGradesStatus(1);
		classGradesRela.setType(0);
		classGradesRela.save();
		
		//更新订单状态 2=已完成
		Db.update("update mallorder set status = 2 where ordernumber = ?",orderNumber);
		
		Result.ok(this);
		
		
	}
	
	/**
	 * 根据teacherId 获取所有商品
	 * @param teacherId
	 * 
	 */
	public void productList(){
		
		Integer teacherId = getParaToInt("teacherId",0);
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		
		String keyWord = getPara("keyWord");
		
		String type = getPara("type","");
		
		if(xx.isOneEmpty(teacherId)){
			Result.error(203, this);
			return;
		}
		
		String sql = "from v_teacher_product where teacher_id = ? and status = 0";
		
		if(xx.isEmpty(keyWord) == false){
			sql += " and `name` like '%"+keyWord+"%'";
		}
		
		if(type.equals("classGrade")){
			sql += " and class_grade_id > 0";
		}else if(type.equals("course")){
			sql += " and class_room_package > 0";
		}
		
		Result.ok(Db.paginate(pageNumber, pageSize, "select *",sql+" order by id desc",teacherId),this);
		
	}
	
	/**
	 * 绑定课程包与学生
	 * @param studentId 学生id
	 * @param classPackId 课程包id
	 * @param orderNumber 订单号
	 */
	public void bindStudentWithPack(){
		
		Integer studentId = getParaToInt("studentId",0);
		Integer classPackId = getParaToInt("classPackId",0);
		
		String orderNumber = getPara("orderNumber");
		
		if(xx.isOneEmpty(studentId,classPackId)){
			Result.error(203, this);
			return;
		}
		
		
		ClassroomPackageService classroomPackageService = new ClassroomPackageService();
		
		int result = 0;
		
		try {
			result = classroomPackageService.bindStudentWithPack(studentId, classPackId);
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(205,"绑定课程包失败",this);
			return;
		}
		
		if(result==1){
			//更新订单状态 2=已完成
			Db.update("update mallorder set status = 2 where ordernumber = ?",orderNumber);
		}else{
			Result.error(205,"绑定课程包失败",this);
			return;
		}
		
		Result.ok(this);
		
	}
	
	/**
	 * 添加免费商品到学生名下
	 * @param productId 商品id 非空
	 * @param studentId 学生id 非空
	 * 
	 */
	public void addFreeClass2Student(){
		
		Integer productId = getParaToInt("productId");
		Integer studentId = getParaToInt("studentId");
		
		if(xx.isOneEmpty(productId,studentId)){
			Result.error(203, this);
			return;
		}
		
		//获取商品
		Mallproduct mallproduct = Mallproduct.dao.findFirst("SELECT * FROM `mallproduct` where id = ?",productId);
		
		ClassroomPackageService classroomPackageService = new ClassroomPackageService();
		
		if(mallproduct==null){
			Result.error(205, "没有该商品",this);
			return;
		}
		
		int gradeId = 0;
		
		try {
			
			//绑定班级
			if(mallproduct.getClassGradeId()>0){
				gradeId = classroomPackageService.addStudent2grade(studentId,mallproduct.getClassGradeId());
			}
			
			//绑定课程包
			if(mallproduct.getClassRoomPackage()>0){
				
				gradeId = classroomPackageService.bindStudentWithPack(studentId, mallproduct.getClassRoomPackage());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(500,this);
			return;
		}
		
		ClassGrades classGrades = ClassGrades.dao.findById(gradeId);
		
		JSONObject json = new JSONObject();
		json.put("gradeId", gradeId);
		json.put("graderName", classGrades.getClassGradesName());
		Result.ok(json,this);
		
	}
	

	public void gradeProductList(){
		
		Integer teacherId = getParaToInt("teacherId", 0);

		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		
		String keyWord = getPara("keyWord");

		if (xx.isOneEmpty(teacherId)) {
			Result.error(203, this);
			return;
		}
		
		String sql = "from v_teacher_product where teacher_id = ? and class_grade_id > 0";

		if (xx.isEmpty(keyWord) == false) {
			sql += " and `name` like '%" + keyWord + "%'";
		}

		Result.ok(Db.paginate(pageNumber, pageSize, "select *", sql + " order by id desc", teacherId), this);
		
	}
	
	
	public void gradesList(){
		
		Integer teacherId = getParaToInt("teacherId", 0);

		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 20);

		String keyWord = getPara("keyWord");

		Page<ClassGrades> page = null;

		if (keyWord != null) {

			page = ClassGrades.dao.paginate(pageNumber, pageSize,
					"select id,class_grades_name,summary,cover,grades_type,auditing_status,create_time",
					"from class_grades where teacher_id = ? and class_grades_name like ?", teacherId,
					"%" + keyWord + "%");

		} else {
			page = ClassGrades.dao.paginate(pageNumber, pageSize,
					"select id,class_grades_name,summary,cover,grades_type,auditing_status,create_time",
					"from class_grades where teacher_id = ? ", teacherId);
		}

		Result.ok(page, this);
		
	}
	
	public void saveGradesProduct(){
		
		Integer productId = getParaToInt("productId");
		BigDecimal price = new BigDecimal(getPara("price"));
		Integer gradeId = getParaToInt("gradeId",0);
		
		/*List<String> sqls = new ArrayList<String>();
		Db.update("update class_grades set price = ? where id = ?",price,gradeId);*/
		
		Db.update("update mallspecifications set price = ? where productid = ?",price,productId);
		Db.update("update mallproduct set price = ? where id = ?",price.toString(),productId);
		
		Result.ok(this);
		 
	}
	
	public static void main(String[] args) {
		
		String price = "0.10";
		
	}
	

}
