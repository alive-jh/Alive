package com.wechat.jfinal.api.qy;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.QyPunchActionType;
import com.wechat.jfinal.model.QyPunchActionTypeGroup;
import com.wechat.jfinal.service.QyPunchActionService;
import com.wechat.jfinal.service.QyPunchActionTypeGroupService;
import net.sf.json.JSONObject;

import java.util.List;

public class QYPunchActionHandle extends Controller {
    QyPunchActionService qyPunchActionService = new QyPunchActionService();
    QyPunchActionTypeGroupService actionTypeGroupService = new QyPunchActionTypeGroupService();

    /**
     * function:获取打卡类别列表
     * 传groupId 只取该group的type
     * 传schoolId 取该校所有group对应的所有type
     * @param: 无
     */
    public void getPunchActionTypeList() {
        int groupId = getParaToInt("groupId", 0);
        int schoolId = getParaToInt("schoolId", 0);
        if (xx.isAllEmpty(groupId,schoolId)) {
            renderJson(Rt.paraError());
            return;
        }
        List<Record> actionList;
        if(groupId != 0) {
            actionList = Db.find("SELECT g.id groupId,g.name groupName,t.id typeId, t.name typeName, t.msg typeMsg FROM qy_punch_action_type_group g,qy_punch_action_type t WHERE t.group_id = g.id AND g.status = 1 AND t.status = 1 AND g.id = ?", groupId);
        }else
            actionList = Db.find("SELECT g.id groupId,g.name groupName,t.id typeId, t.name typeName, t.msg typeMsg FROM qy_punch_action_type_group g,qy_punch_action_type t WHERE t.group_id = g.id AND g.status = 1 AND t.status = 1 AND g.school_id =  ?",schoolId);
        renderJson(Rt.success(actionList));
    }

    /*
     * 保存或者修改打卡行为类型
     * 参数说明：
     * 	id：主键
     * 	status：状态
     * 	create_time：创建时间
     * 	edit_time：编辑时间
     * 	name：行为名称
     * 	msg：消息说明
     * 	group：分组名称
     * 	group_id：分组ID
     * 	school_id：所属学校
     * 
     * */
    public void updateOrSavePunchActionType() {
        QyPunchActionType action = getBean(QyPunchActionType.class, "");
        if (xx.isEmpty(action)) {//如果不传参数，返回错误信息
            renderJson(Rt.paraError());
            return;
        }
        if (action.getId() != null){//如果ID不为空，为修改
        	action.update();
        }else{
        	action.save();//如果ID为空，为新增
        }
        renderJson(Rt.success(action));
    }

    /**
     * 功能：删除打卡类型
     * 参数：
     * 	id：打卡类型ID
     * 
     * */
    public void deletePunchActionType() {
        int actionId = getParaToInt("id", 0);
        if (xx.isEmpty(actionId)) {
            renderJson(Rt.paraError());
            return;
        }
        qyPunchActionService.delete(actionId);
        renderJson(Rt.success());
    }

    public void updateOrSavePunchActionTypeGroup() {
        QyPunchActionTypeGroup group = getBean(QyPunchActionTypeGroup.class, "");
        if (group.getId() != null) {
            group.update();
        } else
            group.save();
        renderJson(Rt.success(group));
    }

    public void getPunchActionTypeGroupList(){
        int schoolId = getParaToInt("schoolId");
        if (xx.isEmpty(schoolId)) {
            renderJson(Rt.paraError());
            return;
        }
        List<QyPunchActionTypeGroup> groups = actionTypeGroupService.getListBySchoolId(schoolId);
        renderJson(Rt.success(groups));
    }

    public void getPunchActionTypeAndGroupList(){
        int schoolId = getParaToInt("schoolId");
        if (xx.isEmpty(schoolId)) {
            renderJson(Rt.paraError());
            return;
        }
        List<JSONObject> groups = actionTypeGroupService.getTypeBySchoolId(schoolId);
        renderJson(Rt.success(groups));
    }

    public void deletePunchActionTypeGroup(){
        int punchActionTypeGroupId = getParaToInt("punchActionTypeGroupId");
        actionTypeGroupService.delete(punchActionTypeGroupId);
        renderJson(Rt.success());
    }




}
