package com.wechat.jfinal.apiAjax;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.model.ClassRoomLabel;
import com.wechat.jfinal.model.ClassRoomLabelRela;

import java.util.List;

public class AjaxClassRoomHandle  extends Controller {


    public void getLabelListByClassRoomId(){
    	int classRoomId = getParaToInt("classRoomId");
    	List<Record> result = Db.find("select a.id id,b.id lid,b.label_name labelName,b.is_show isShow,b.status status from class_room_label_rela as a,class_room_label as b where a.class_room_id=? and a.label_id=b.id",classRoomId);
    	renderJson(JsonResult.JsonResultOK(result));
    }
    public void searchLabelName(){
    	String labelName = getPara("labelName");
    	List<Record> result = Db.find("select b.id lid,b.label_name labelName,b.is_show isShow,b.status status from class_room_label as b where b.label_name like '%%"+labelName+"%%'");
    	renderJson(JsonResult.JsonResultOK(result));
    }
    public void saveClassRoomLabel(){
    	//System.out.println(getAttrNames());

    	int classRoomId = getParaToInt("classRoomId",0);
    	int labelId = getParaToInt("labelId",0);
    	String labelName = getPara("labelName");

    	if(0!=labelId){
    		
    	}else{
    		ClassRoomLabel classRoomLabel = new ClassRoomLabel();
    		classRoomLabel.setLabelName(labelName);
    		classRoomLabel.save();
    		labelId = classRoomLabel.getId();
    	}
    	ClassRoomLabelRela ClassRoomLabelRela = new ClassRoomLabelRela();
    	ClassRoomLabelRela.setClassRoomId(classRoomId);
    	ClassRoomLabelRela.setLabelId(labelId);
    	ClassRoomLabelRela.save();
    	renderJson(JsonResult.JsonResultOK("ok"));
    }
    public void deleteLabelFormClassRoom(){
    	int id = getParaToInt("id");
    	Db.deleteById("class_room_label_rela", id);
    	renderJson(JsonResult.JsonResultOK("ok"));
    }
    
    
}
