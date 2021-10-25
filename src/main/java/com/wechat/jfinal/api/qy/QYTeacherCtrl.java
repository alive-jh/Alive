package com.wechat.jfinal.api.qy;

import com.jfinal.core.Controller;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.AgentSchool;

public class QYTeacherCtrl  extends Controller {

	/**
	 * 通过memberId获取绑定的学校
	 * 
	 */
    public void getBelong(){
    	
        int memberId = getParaToInt("memberId");
        if(xx.isEmpty(memberId)){
            renderJson(Rt.paraError());
            return;
        }
        AgentSchool school = AgentSchool.dao.findFirst("SELECT * FROM school_teacher_rela WHERE member_id = ? ", memberId);
        renderJson(Rt.success(school));
    }
}
