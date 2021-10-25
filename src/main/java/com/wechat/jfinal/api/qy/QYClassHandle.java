package com.wechat.jfinal.api.qy;

import com.jfinal.core.Controller;
import com.jfinal.i18n.Res;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.QyClass;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QYClassHandle extends Controller{
	private int defaultPageNumber = 1;
	
	private int defaultPageSize = 10;
	/**
	*  function: 保存/修改班级
	*  description:使用POST方法请求提交数据，如果提交的数据带id主键，则为修改班级信息，无ID则问添加
	*  return:返回班级对象 
	* @param:
	* 	className(班级名称)
	* 	memberId（会员ID，取凡豆伴账号ID）
	*  @author
	*	alex
	*/
    public void saveClass(){
    	QyClass bean = getBean(QyClass.class,"");
    	if(null == bean.getClassName()||"".equals(bean.getClassName())){
    		//判断班级名字是否为空
    		renderJson(JsonResult.JsonResultError(203));
    	}else{

    		if(null == bean.getId()){//判断是否有ID
    			bean.save();
    			renderJson(JsonResult.JsonResultOK(bean));
    		}else{
    			bean.update();
    			renderJson(JsonResult.JsonResultOK(QyClass.dao.findById(bean.getId())));
    		}
    	}
    	
    	
    	

    }
	/**
	*  function: 通过学员ID获取班级
	*  description:使用GET方法请求班级列表数据，必带参数为memberId（会员ID）
	*  return:返回班级列表
	* @param:
	* 	memberId（会员ID，取凡豆伴账号ID）
	*  @author
	*	alex
	*/
    public void getClassList(){
    	
    	try{
    		int memberId = getParaToInt("memberId",0);
    		
    		if(xx.isEmpty(memberId)){
    			renderJson(JsonResult.JsonResultError(203));
    		}
    		
    	    List<Record> result = Db.find("select a.id classId,a.class_name className,(SELECT COUNT(*) FROM qy_class_to_student, qy_student_card WHERE qy_class_to_student.class_id = a.id AND qy_class_to_student.STATUS = 0 AND qy_class_to_student.student_id = qy_student_card.student_id AND qy_student_card.`status` > 0) count from qy_class as a where member_id=? order by id desc",memberId);
    	    renderJson(Rt.success(result));
    		
    	}catch(Exception e){
    		
    		renderJson(JsonResult.JsonResultError(500));
    		
    	}

    }
	/**
	*  function: 通过班级ID获取班级列表
	*  description:使用GET方法请求班级列表数据。
	*  return:返回班级列表
	* @param:
	* 	classId（班级ID，必填）
	*  @author
	*	alex
	*/
    public void getStudentListByClassId(){
		int classId = getParaToInt("classId");
	    int currentPage = getParaToInt("pageNumber",defaultPageNumber);
    	int pageSize = getParaToInt("pageSize",defaultPageSize);
    	//返回对象列表，对象不属于model，所以用Record类型
    	
	    Page<Record> page = Db.paginate(currentPage,pageSize,"select a.id id,a.student_id studentId,b.name studentName,b.avatar avatar,c.card_id cardId,b.create_time createTime,c.contacts contacts","from qy_class_to_student as a,class_student as b,qy_student_card as c where a.student_id=b.id and a.student_id=c.student_id and c.status>0 and a.class_id=? ORDER BY a.id desc",classId);
    	Map<String, Object> data = new HashMap<>();
    	data.put("_list", page);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    }
    
    public void classList(){
    	
    	Integer school = getParaToInt("school",0);
    	
    	List<QyClass> qyClasses =  QyClass.dao.find("SELECT qy_class.* FROM qy_class WHERE school_id = ? UNION SELECT * FROM qy_class WHERE id IN (SELECT b.class_id FROM qy_device a JOIN qy_device_class b ON a.device_id = b.device_id WHERE a.belong = ?)",school,school);
    	JSONObject json = new JSONObject();
    	json.put("classList", Result.makeupList(qyClasses));

    	Result.ok(json,this);
    	
    }
    
}
