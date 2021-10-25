package com.wechat.jfinal.api.agent;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wechat.jfinal.api.interception.AgentInterception;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.*;
import com.wechat.util.MD5UTIL;

import net.sf.json.JSONObject;

public class AgentCtr extends Controller {

    @Clear
    public void login() {

        String account = getPara("account");
        String password = getPara("password");

        if (xx.isOneEmpty(account, password)) {
            Result.error(203, this);
            return;
        }
        System.out.println(MD5UTIL.encrypt(password));
        EovaUser eovaUser = EovaUser.dao.findFirst("select * from eova_user where login_id = ? and login_pwd = ?", account, MD5UTIL.getSM32(password));

        if (eovaUser == null) {
            Result.error(20501, "登录失败，用户名或密码错误", this);
            return;
        }


        HttpSession session = getSession();
        session.setAttribute("miniapp_user", eovaUser);

        System.out.println(session.getId());

        JSONObject json = new JSONObject();
        json.put("sessionId", session.getId());
        json.put("user", eovaUser.toJson());

        Result.ok(json, this);

    }

    public void getEleClass() {

        EovaUser eovaUser = (EovaUser) getSession().getAttribute("miniapp_user");

        OssAgentSchool ossAgentSchool = OssAgentSchool.dao.findFirst("select id from oss_agent_school where oss_user_id = ? ", eovaUser.getId());
        String sql = "SELECT * FROM `class_hourse` WHERE user_id = ?";

        List<ClassHourse> classHourses = ClassHourse.dao.find(sql, ossAgentSchool.getId());

        if (classHourses.size() <= 0) {
            Result.error(20502, "你没有电教室，赶快去创建吧！", this);
            return;
        }

        JSONObject json = new JSONObject();
        json.put("classHourses", Result.makeupList(classHourses));

        Result.ok(json, this);

    }

    public void getDevicesByGradesId() {

        Integer gradesId = getParaToInt("gradesId");

        if (gradesId == null) {
            Result.error(203, this);
            return;
        }

        List<RobotHourse> robotHourses = RobotHourse.dao.find("SELECT Id,stu_epal_id,epal_id,hourse_id,IFNULL(msg,'') AS msg,create_time,color,row,col FROM robot_hourse WHERE hourse_id = ?", gradesId);

        JSONObject json = new JSONObject();
        json.put("robotHourses", Result.makeupList(robotHourses));

        Result.ok(json, this);

    }

    public void saveDeviceSeat() {

        String epal = getPara("epalId");
        Integer row = getParaToInt("row");
        Integer col = getParaToInt("col");
        Integer gradesId = getParaToInt("gradesId");

        if (xx.isOneEmpty(epal, row, col, gradesId)) {
            Result.error(203, this);
            return;
        }

        String sql = "INSERT INTO robot_hourse (epal_id,hourse_id,row,col) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE row=?,col=?";

        JSONObject json = new JSONObject();
        json.put("AffectedRows", Db.update(sql, epal, gradesId, row, col, row, col));

        Result.ok(json, this);

    }

}
