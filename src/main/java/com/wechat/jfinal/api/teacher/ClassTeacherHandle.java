package com.wechat.jfinal.api.teacher;

import java.util.ArrayList;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassTeacher;
import com.wechat.jfinal.model.Memberaccount;


public class ClassTeacherHandle extends Controller{
	/**
	 *  通过老师ID获取老师信息
	 * 
	 * */
	public void getTeacherInfo(){
		int teacherId = getParaToInt("teacherId",0);
		ClassTeacher classTeacher = ClassTeacher.dao.findById(teacherId);
		if(null == classTeacher){
			Result.error(404,this);
			return;
		}else{
			
			if(classTeacher.getCover()==null||classTeacher.getCover().equals("")){
				Record _classTeacher = classTeacher.toRecord();
				_classTeacher.set("cover", new ArrayList<>());
				Result.ok(_classTeacher,this);
			}else{
				Result.ok(classTeacher,this);
			}
		}
		
	}
	
	/**
	 *  修改老师信息
	 * 
	 * */
	public void updateTeacherInfo(){
		ClassTeacher bean = getBean(ClassTeacher.class,"");
		if(null == bean.getId()){
			
//			bean.save();
			Result.error(403,this);
			return;
		}else{
			if(null!=bean.getMemberId()){//不允许修改老师对应会员ID
				Result.error(203,this);
				return;
			}
			if(null!=bean.getAgentId()){//不允许修改老师所属代理商ID
				Result.error(203,this);
				return;
			}
			if(null!=bean.getCreateTime()){//不允许修改老师创建时间
				Result.error(203,this);
				return;
			}
			
			if(null!=bean.getLevel()){//不允许修改老师等级权限
				Result.error(203,this);
				return;
			}
			bean.update();
			Result.ok(bean,this);
			return;
		}
		
	}
	/**
	 * 通过手机号码搜索老师
	 * 参数：
	 *  mobile（手机号码）
	 * 
	 * */
	
	public void searchClassTeacherByMobile(){
		String mobile = getPara("mobile");
		if(xx.isEmpty(mobile)){//手机号码判空
			Result.error(202 ,this);
			return;
		}else{
			Memberaccount memberAccount = Memberaccount.dao.findFirst("select * from memberaccount where account=?",mobile);
			if(null == memberAccount){
				Result.error(202,"用户不存在" ,this);
				return;
			}else{
				Integer memberId = memberAccount.getMemberid();
				ClassTeacher classTeacher = ClassTeacher.dao.findFirst("select * from class_teacher where member_id=?",memberId);
				if(null == classTeacher){
					Result.error(203,"该用户不是老师" ,this);
					return;
				}else{
					
				}
				Result.ok(classTeacher,this);
				return;
			}
				
		}
		
	}
}
