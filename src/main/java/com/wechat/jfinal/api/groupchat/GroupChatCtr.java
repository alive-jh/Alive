package com.wechat.jfinal.api.groupchat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.EasemobGroupChat;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.FandouGroup;
import com.wechat.jfinal.model.GroupChat;

import net.sf.json.JSONObject;

public class GroupChatCtr extends Controller {

	@EmptyParaValidate(params = { "grade" })
	public void groupMembers() {

		Integer gradeId = getParaToInt("grade");

		GroupChat groupChat = GroupChat.dao.findFirst("select * from group_chat where class_grade_id = ?", gradeId);

		JSONObject json = new JSONObject();

		try {
			String[] members = EasemobUtil.getGroupChatMembers(groupChat.getEasemobGroupId());
			json.put("members", members);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Result.error(20501, "获取失败", this);
		}

		Result.ok(json, this);
	}

	@EmptyParaValidate(params = { "grade", "groupName", "desc", "apublic", "membersOnly", "allowinvites", "owner" })
	public void create() {
		Integer gradeId = getParaToInt("grade");

		EasemobGroupChat easemobGroupChat = getBean(EasemobGroupChat.class, "");

		JSONObject json = new JSONObject();

		try {
			String groupId = EasemobUtil.createGroupChat(easemobGroupChat);

			if (groupId != null) {
				GroupChat groupChat = new GroupChat();
				groupChat.setClassGradeId(gradeId);
				groupChat.setEasemobGroupId(groupId).save();
			}

			json.put("groupId", groupId);
			json.put("easemobGroup", easemobGroupChat);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Result.error(20501, "创建群聊失败", this);
			return;
		}

		Result.ok(json, this);

	}

	@EmptyParaValidate(params = { "group", "member" })
	public void addMember() {

		String groupId = getPara("group");
		String[] members = getPara("member").split(",");

		try {
			EasemobUtil.addMember2Group(groupId, members);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Result.error(20501, e.getMessage(), this);
			return;
		}

		Result.ok(this);

	}

	public void groupChatInfo() {

		Integer gradeId = getParaToInt("grade", 0);

		String groupids = getPara("groups");

		String group = null;

		if (groupids != null && groupids.trim() != "") {

			group = groupids;

		} else {
			GroupChat groupChat = GroupChat.dao.findFirst("select * from group_chat where class_grade_id = ?", gradeId);

			if (groupChat == null) {
				Result.error(20501, "没有找到相关班级的群聊", this);
				return;
			}

			group = groupChat.getEasemobGroupId();
		}

		JSONObject json = new JSONObject();
		try {
			List<EasemobGroupChat> easemobGroupChats = EasemobUtil.getGroupChat(group);
			json.put("easemobGroupChats", easemobGroupChats);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Result.ok(json, this);

	}

	@EmptyParaValidate(params = { "groupName", "teacherId" })
	public void createGroup() {

		FandouGroup fandouGroup = getBean(FandouGroup.class, "");

		if (fandouGroup.getId() == null) {
			fandouGroup.save();
		} else {
			fandouGroup.update();
		}

		Result.ok(fandouGroup, this);
	}

	@EmptyParaValidate(params = { "teacher" })
	public void glist() {

		List<Record> fandouGroups = Db.find("select * from fandou_group where teacher_id = ?",getParaToInt("teacher"));
		
		List<Record> groupMembers = Db.find("SELECT f.id AS group_id, a.id AS group_member_id, b.id AS student_id, b.`name`, b.avatar , "
				+ "b.epal_id FROM `fandou_group_member` a JOIN fandou_group f ON a.group_id = f.id AND f.teacher_id = ?, class_student b "
				+ "WHERE a.student_id = b.id",getParaToInt("teacher"));
		
		/*
		 * 数据按照 group_id 分组
		 */
        Map<Integer, List<Record>> groupMembersMap = new HashMap<Integer, List<Record>>();
        
        for(Record groupMember : groupMembers){
        	
        	 List<Record> tempList = groupMembersMap.get(groupMember.getInt("group_id"));
        	 
        	 if (tempList == null) {
                 tempList = new ArrayList<>();
                 tempList.add(groupMember);
                 groupMembersMap.put(groupMember.getInt("group_id"), tempList);
             }
             else {
                 tempList.add(groupMember);
             }
        	 
        }
        
        /*
         * 
         */
		
		for(Record fandouGroup :fandouGroups){
			fandouGroup.set("groupMembers", Result.makeupList(groupMembersMap.get(fandouGroup.getInt("id"))));
		}
		
		JSONObject json = new JSONObject();
		json.put("groups", Result.makeupList(fandouGroups));
		
		Result.ok(json,this);

	}

	@EmptyParaValidate(params = { "group", "student" })
	public void addMember2Group() {

		Integer groupId = getParaToInt("group");
		String[] students = getPara("student").split(",");

		List<String> sqls = new ArrayList<String>(students.length);

		for (String student : students) {
			sqls.add("insert ignore into fandou_group_member (group_id,student_id) values (" + groupId + "," + student
					+ ")");
		}

		Db.batch(sqls, 500);

		Result.ok(this);

	}
	
	@EmptyParaValidate(params = { "group" })
	public void fandouGroupMembers(){
		
		List<Record> groupMembers = Db.find(
				"SELECT a.id AS group_member_id,b.id AS student_id ,b.`name`,b.avatar,b.epal_id FROM `fandou_group_member` a,class_student b WHERE a.student_id = b.id AND a.group_id = ?",getParaToInt("group"));
	
		FandouGroup fandouGroup = FandouGroup.dao.findById(getParaToInt("group"));
		
		JSONObject json = new JSONObject();
		json.put("groupMembers", Result.makeupList(groupMembers));
		json.put("groupInfo", fandouGroup);
		
		Result.ok(json,this);
		
	}

	@EmptyParaValidate(params = { "teacher" })
	public void getStudentsByTeahcer() {

		String sql = "SELECT a.* FROM class_student a,class_grades b JOIN class_grades_rela c ON b.teacher_id = ? AND b.id = c.class_grades_id WHERE a.id = c.class_student_id";

		List<ClassStudent> students = ClassStudent.dao.find(sql,getParaToInt("teacher"));
		
		JSONObject json = new JSONObject();
		json.put("students", Result.makeupList(students));
		
		Result.ok(json,this);
		
	}

}
