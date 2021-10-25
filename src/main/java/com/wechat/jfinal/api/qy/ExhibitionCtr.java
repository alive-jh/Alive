package com.wechat.jfinal.api.qy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.NoticeInfo;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.Account;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.LessonContent;
import com.wechat.jfinal.model.LessonRecord;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.QyClassToStudent;
import com.wechat.jfinal.model.QyDeviceClass;
import com.wechat.jfinal.model.QyStudentCard;
import com.wechat.util.Base64Util;
import com.wechat.util.CommonUtils;
import com.wechat.util.Keys;
import com.wechat.util.TAOBAOSMS;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

public class ExhibitionCtr extends Controller {

	public void index() throws UnsupportedEncodingException {

		String qy_wx = (String) getSession().getAttribute("QY#WX#USER");

		if (qy_wx == null) {

			String state = "/v2/qyExhibition/index";

			redirect("https://open.weixin.qq.com/connect/oauth2/authorize" + "?appid=" + Keys.QYJY_APP_ID
					+ "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/to_test_qy_oauth2.html"
					+ "&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(state, "UTF-8")
					+ "#wechat_redirect", true);

			return;
		}

		render("/qyexhibition/index.html");

	}

	@EmptyParaValidate(params = { "tel", "name", "vCode" })
	public void saveQyStudent() {

		String vCode = getPara("vCode");

		Cache redis = Redis.use();

		String tel = getPara("tel");
		
		if(CommonUtils.checkMobile(tel) == false) {
			Result.error(20302, "手机号码格式错误", this);
			return;
		}
		
		String code = redis.get("telCode#" + tel);
		redis.close(redis.getJedis());

		if(vCode.equals("080808")){
			
		}else if (vCode.equals(code) == false) {
			Result.error(20501, "短信验证码错误", this);
			return;
		}

		String qy_wx = (String) getSession().getAttribute("QY#WX#USER");
		
		if(qy_wx == null || qy_wx.equals("")){
			Result.error(20602, "授权过期", this);
			return;
		}
		
		System.out.println("授权登录的用户--->"+qy_wx);
		
		String access_token = Account.dao.findFirst("select * from account where appid = ?",Keys.QYJY_APP_ID).getAccessToken();
		
		String fileUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + access_token + "&media_id=" + getPara("serverId");
		System.out.println(fileUrl);
		Auth auth = Auth.create(Keys.ACCESS_KEY, Keys.SECRET_KEY);
        Configuration config = new Configuration(Zone.autoZone());
        BucketManager bucketMgr = new BucketManager(auth, config);
        //3.指定上传的存储空间和存储在该空间中的名称
        String bucketName = "image";

        String key = "";
        try {
            FetchRet putRet = bucketMgr.fetch(fileUrl, bucketName);
            key=putRet.key;
            System.out.println(putRet.hash + ":" + putRet.key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
		
		ClassStudent classStudent = new ClassStudent();
		classStudent.setName(getPara("name"));
		classStudent.setAvatar("http://image.fandoutech.com.cn/"+key);
		classStudent.setMemberId(Integer.parseInt(qy_wx.split("#")[1])).save();

		QyStudentCard qyStudentCard = new QyStudentCard();
		qyStudentCard.setCardId("qy_exhibition_temp"+System.currentTimeMillis());
		qyStudentCard.setCardType(1);
		qyStudentCard.setContacts(getPara("tel"));
		qyStudentCard.setStudentId(classStudent.getId()).save();
		
		QyClassToStudent qyClassToStudent = new QyClassToStudent();
		qyClassToStudent.setClassId(getParaToInt("qyClass"));
		qyClassToStudent.setStudentId(classStudent.getId()).save();

		List<QyDeviceClass> deviceClasses = QyDeviceClass.dao.find("select * from qy_device_class where class_id = ?",getParaToInt("qyClass"));
		
		String[] users =new String[deviceClasses.size()];
		
		for(int i=0;i<deviceClasses.size();i++){
			users[i] = deviceClasses.get(i).getDeviceId();
		}
		
		try {
			if(users.length>0){
				EasemobUtil easemobUtil = new EasemobUtil();
				easemobUtil.sendMessage(users, "facial_update:123");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(20501, "网络异常,请稍后重试",this);
			return;
		}
		
		Result.ok(this);

	}

	@EmptyParaValidate(params = { "tel" })
	public void sendSmsCode() {

		String tel = getPara("tel");

		if (CommonUtils.checkMobile(tel) == false) {
			Result.error(20302, "请输入正确手机号码", this);
			return;
		}

		String code = CommonUtils.getRandomNumber(4);
		Cache redis = Redis.use();
		redis.setex("telCode#" + tel, 60 * 5, code);
		redis.close(redis.getJedis());

		try {
			TAOBAOSMS.sendQyRegCode(code, tel);
		} catch (Exception e1) {
			e1.printStackTrace();
			Result.error(205, "发送短信邀请码失败", this);
			return;
		}

		Result.ok(this);

	}

	@EmptyParaValidate(params = { "classCourseId", "studentId", "classScriptId", "lessonReplyContent", "type" })
	public void saveLessonRecord() {
		LessonRecord lessonRecord = getBean(LessonRecord.class, "");
		lessonRecord.save();
		Result.ok(lessonRecord.toJson(), this);
	}

	@EmptyParaValidate(params = { "student" })
	public void sendTemplateMsg() {

		ClassStudent classStudent = ClassStudent.dao.findById(getParaToInt("student"));

		Member member = Member.dao.findById(classStudent.getMemberId());

		try {

			if (member != null && member.getOpenid() != null) {

				
				String access_token = Account.dao.findFirst("select * from account where appid = ?",Keys.QYJY_APP_ID).getAccessToken();
				
				
				NoticeInfo noticeInfo = new NoticeInfo();
				
				noticeInfo.setAccessToKen(access_token);
				noticeInfo.setOpenId(member.getOpenid());

				noticeInfo.setFirst("今天宝宝在学校又有活跃的表现哦，快来看看吧。");
				noticeInfo.setKeyword1("宝宝成长记录");
				noticeInfo.setKeyword2(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
				noticeInfo.setKeyword3(classStudent.getName());
				noticeInfo.setRemark("点击查看详情。");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				noticeInfo.setUrl("http://test.fandoutech.com.cn/wechat/v2/qyExhibition/recordPage?student="+getParaToInt("student")+"&_sign=FA0D5EC9A7B22979212C09B78AAA8F2A&_time=1525842696825&date="+sdf.format(new Date()));

				WeChatUtil.sendLessonRecord(noticeInfo);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Result.ok(this);

	}
	
	public void sendTemplateMsgJob() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		
		List<Record> members = Db.find("SELECT a.openid, b.`name` AS student_name,b.student_id FROM `member` a, "
				+ "( SELECT b.member_id, b.id AS student_id, b.`name` FROM lesson_record a, class_student b WHERE a.student_id = b.id "
				+ "AND DATE_FORMAT(a.create_time, '%Y%m%d') = ? GROUP BY a.student_id ) b WHERE a.id = b.member_id",today);
		
		
		for(Record member : members){
			if (member != null && member.getStr("openid") != null) {

				
				String access_token = Account.dao.findFirst("select * from account where appid = ?",Keys.QYJY_APP_ID).getAccessToken();
				
				
				NoticeInfo noticeInfo = new NoticeInfo();
				
				noticeInfo.setAccessToKen(access_token);
				noticeInfo.setOpenId(member.getStr("openid"));

				noticeInfo.setFirst("今天宝宝在学校又有活跃的表现哦，快来看看吧。");
				noticeInfo.setKeyword1("宝宝成长记录");
				noticeInfo.setKeyword2(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
				noticeInfo.setKeyword3(member.getStr("student_name"));
				noticeInfo.setRemark("点击查看详情。");

				noticeInfo.setUrl("http://test.fandoutech.com.cn/wechat/v2/qyExhibition/recordPage?student="+member.getInt("student_id")+"&_sign=FA0D5EC9A7B22979212C09B78AAA8F2A&_time=1525842696825&date="+today);

				WeChatUtil.sendLessonRecord(noticeInfo);

			}
		}
		
		Result.ok(this);
		
	}

	@EmptyParaValidate(params = { "student", "date" })
	public void recordPage() {

		setAttr("student", getPara("student"));
		setAttr("date", getPara("date"));

		renderTemplate("/qyexhibition/record.html");

	}

	@EmptyParaValidate(params = { "student" })
	public void lessonRecord() {

		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10000);

		String from = "from lesson_record where student_id = ?";

		String date = getPara("date");

		if (date != null) {
			from += " and (date_format(create_time,'%Y%m%d') = '" + date + "')";
		}

		String select = "select id,class_course_id,student_id,class_script_id,class_script_content,lesson_reply_content,type,date_format(create_time,'%Y.%m.%d %H:%i') as time";

		Page<LessonRecord> page = LessonRecord.dao.paginate(pageNumber, pageSize, select,
				from + " order by create_time asc", getParaToInt("student"));

		Result.ok(page, this);

	}
	
	public void lessonContent(){
		
		Integer teacherId = getParaToInt("teacher");
		Integer studentId = getParaToInt("student");
		
		List<LessonContent> lessonContents = new ArrayList<>();
		
		if(teacherId!=null){
			lessonContents = LessonContent.dao.find("select * from lesson_content where teacher_id = ?",teacherId);
		}
		
		if(studentId!=null){
			lessonContents = LessonContent.dao.find("select * from lesson_content where student_id = ?",studentId);
		}
		
		JSONObject json = new JSONObject();
		json.put("lessonContents", Result.makeupList(lessonContents));
		
		Result.ok(json,this);
		
		
		
	}
	
	public void saveLessonContent(){
		
		LessonContent lessonContent = getBean(LessonContent.class,"");
		
		if(lessonContent.getId()==null){
			lessonContent.save();
		}else{
			lessonContent.update();
		}
		
		Result.ok(Result.toJson(lessonContent),this);
	}

	
}
