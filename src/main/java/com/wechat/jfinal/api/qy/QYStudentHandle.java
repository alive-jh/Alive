package com.wechat.jfinal.api.qy;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.QyClassToStudent;
import com.wechat.jfinal.model.QyStudentCard;

public class QYStudentHandle extends Controller{
	/**
	*  function: 保存学生
	*  description:保存学生信息
	*  return:返回学生卡信息
	* @param:
	* 	studentName:学生名字
	*  	classId:班级ID
	*  	cardId:学生卡号
	*  	avatar:学生头像
	*  @author
	*	alex
	*	@remark
	*		当前没有考虑修改学生的情况,后期需要的时候添加
	*		
	*/
    public void saveStudent(){
       	String studentName = getPara("studentName");
       	int classId = getParaToInt("classId");
       	String cardId = getPara("cardId");
       	String avatar = getPara("avatar");
       	String contacts = getPara("contacts");
       	QyStudentCard qyStudentCardTemp = QyStudentCard.dao.findFirst("select * from qy_student_card where card_id=?",cardId);
       	if(null==qyStudentCardTemp){
           	//保存学生信息到class_student数据表格
           	ClassStudent classStudent = new ClassStudent();
           	classStudent.setName(studentName);
           	classStudent.setDegreeOfDifficulty(Float.parseFloat("1"));//默认难度系数
           	classStudent.setStudentType(8);//清园项目的学生类型为8
           	classStudent.setAvatar(avatar);
           	classStudent.save();
           	
           	//保存学生卡
           	QyStudentCard qyStudentCard = new QyStudentCard();
           	qyStudentCard.setCardId(cardId);
           	qyStudentCard.setCardType(1);
           	qyStudentCard.setStatus(1);
           	qyStudentCard.setStudentId(classStudent.getId());
           	qyStudentCard.setContacts(contacts);
           	qyStudentCard.save();
           
           	//保存学生和班级的关系
           	QyClassToStudent QyClassToStudent = new QyClassToStudent();
           	QyClassToStudent.setClassId(classId);
           	QyClassToStudent.setStudentId(classStudent.getId());
           	QyClassToStudent.save();
           	
           	renderJson(JsonResult.JsonResultOK(qyStudentCard));
       	}else{
       		renderJson(JsonResult.JsonResultError(403));
       		
       	}
    }
	/**
	* function:通过memberId获取学生列表
	*  @param:
	*  	memberId:会员ID
	* 
	*
	*/
    public void updateStudent(){
    	String cardId = getPara("cardId");
    	int classId = getParaToInt("classId",0);
    	String studentName = getPara("studentName","");
    	String avatar = getPara("avatar","");
    	String contacts = getPara("contacts","");
    	//修改学生卡信息
    	QyStudentCard qyStudentCard = QyStudentCard.dao.findFirst("select * from qy_student_card where card_id=?",cardId);
    	int studentId = qyStudentCard.getStudentId();
    	if("".equals(contacts)){
    		
    	}else{
    		qyStudentCard.setContacts(contacts);
    	}
    	qyStudentCard.update();
    	
    	//修改学生信息
    	ClassStudent classStudent = ClassStudent.dao.findById(studentId);
    	if("".equals(contacts)){
    		
    	}else{
    		classStudent.setName(studentName);
    	}
    	if("".equals(avatar)){
    		
    	}else{
    		classStudent.setAvatar(avatar);
    	}
    	classStudent.update();
    	//处理学生和班级关系，每个学生只允许属于一个班级，
    	QyClassToStudent qyClassToStudent = QyClassToStudent.dao.findFirst("select * from qy_class_to_student where student_id=? and class_id=?",studentId,classId);
    	if(null!=qyClassToStudent){
    		//没有修改班级
    	}else{
    		Db.update("delete from qy_class_to_student where student_id=?",studentId);
    		QyClassToStudent qyClassToStudent2 = new QyClassToStudent();
    		qyClassToStudent2.setClassId(classId);
    		qyClassToStudent2.setStudentId(studentId);
    		qyClassToStudent2.setStatus(0);
    		qyClassToStudent2.save();
    	}
    	
    	renderJson(JsonResult.JsonResultOK("ok"));
    }
    
	/**
	* function:通过memberId获取学生列表
	*  @param:
	*  	memberId:会员ID
	* 
	*
	*/
    public void getStudentList(){
    	int memberId = getParaToInt("memberId");
    	int pageNumber = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	Page<Record> temp= Db.paginate(pageNumber, pageSize, "select a.class_name className,b.student_id as studentId,c.create_time as createTime,d.name studentName,d.avatar avatar,c.card_id as cardId,c.status status,c.contacts contacts,a.id classId", "from qy_class as a,qy_class_to_student as b,qy_student_card as c,class_student as d where a.member_id=? and a.id=b.class_id and b.student_id=c.student_id and c.student_id=d.id and c.status>0 order by d.id desc",memberId);
    	renderJson(JsonResult.JsonResultOK(temp));
    }
    
    /**
	* function:通过cardId删除学生
	*  @param:
	*  	cardId:卡ID
	* 
	*
	*/
    public void delStudentByCardId(){
    	
    	String cardId = getPara("cardId");
    	
    	String sql = "delete from qy_class_to_student where student_id = (select student_id from qy_student_card where card_id= ?)";
    	String sql2 = "delete from qy_student_card where card_id=?";
    	Db.update(sql,cardId);
    	Db.update(sql2,cardId);
    	
    	renderJson(Rt.success());
    }
    

}
