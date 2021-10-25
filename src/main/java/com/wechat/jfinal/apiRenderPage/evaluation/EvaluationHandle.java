package com.wechat.jfinal.apiRenderPage.evaluation;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.model.EvalOption;
import com.wechat.jfinal.model.EvalQuestion;
import com.wechat.util.QiniuUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EvaluationHandle extends Controller{
	
	
	public void gotoEvaluation(){
		
		Integer memberId = getParaToInt("memberId");
		
		renderTemplate("/eval/newEvaluation/evaluation.html");
		
	}
	
	public void getEvaluation(){
		
		Integer level = getParaToInt("level",1);
		
		Integer all = getParaToInt("all",0);
		
		Integer pageIndex = getParaToInt("pageIndex",1);
		
		int pageSize = 10;
		
		String left = "LEFT";
		
		if(!xx.isEmpty(all)){
			left = "";
			pageSize=5;
		}
		
		StringBuffer select = new StringBuffer("SELECT a.question_id,a.question_text,IFNULL(a.question_sound,'not') as question_sound,GROUP_CONCAT(a.option_id,'#',IFNULL(IF(a.option_text='',NULL,a.option_text),'not'),'#',IFNULL(IF");
		select.append(" (a.option_pic='',NULL,a.option_pic),'not'),'#',IFNULL(IF(a.option_sound='',NULL,a.option_sound),'not'),'#',a.is_correct) AS answer_option");
		StringBuffer sql = new StringBuffer(" FROM (SELECT a.id AS question_id,a.text AS question_text,a.sound_url AS question_sound,");
		sql.append(" c.id AS option_id,c.text AS option_text,c.pic_url AS option_pic,c.sound_url AS option_sound,b.is_correct");
		sql.append(" FROM eval_question a "+left+" JOIN eval_question_option b ON a.id = b.question_id "+left+" JOIN eval_option c ON b.option_id = c.id ) a WHERE a.question_id IN (SELECT b.relation_id FROM eval_label a,eval_label_relation b WHERE a.id = b.label_id AND a.id = ?) GROUP BY a.question_id");
		sql.append(" ORDER BY a.question_id DESC");
	
		//List<Record> list = Db.find(sql.toString(),level);
		
		Page<Record> page = Db.paginate(pageIndex, pageSize, select.toString(), sql.toString(), level);
		
		List<Record> list = page.getList();
		
		JSONArray data = new JSONArray();
		
		for(int i=0;i<list.size();i++){
			
			JSONObject evaluations = new JSONObject();
			JSONObject question = new JSONObject();
			question.put("questionId", list.get(i).getInt("question_id"));
			question.put("questionText", list.get(i).getStr("question_text"));
			question.put("questionSound", list.get(i).getStr("question_sound"));
			
			JSONArray options = new JSONArray();
			
			if(list.get(i).getStr("answer_option")!=null){
				
				String [] answerArray = list.get(i).getStr("answer_option").split(",");
				
				for(int j=0;j<answerArray.length;j++){
					JSONObject option = new JSONObject();
					String [] optionsTmp = answerArray[j].split("#");
					option.put("optionId", Integer.parseInt(optionsTmp[0]));
					option.put("optionText", optionsTmp[1]);
					option.put("optionPic", optionsTmp[2]);
					option.put("optionSound", optionsTmp[3]);
					option.put("isCorrect", optionsTmp[4]);
					options.add(option);
				}
				
			}
			
			evaluations.put("question", question);
			evaluations.put("options", options);
			
			data.add(evaluations);
		}
		
		JSONObject result = new JSONObject();
		result.put("pageNumber", page.getPageNumber());//页码
		result.put("pageSize", page.getPageSize());//单页大小
		result.put("totalPage", page.getTotalPage());//总页数
		result.put("totalRow", page.getTotalRow());//数据总数
		result.put("isFirstPage", page.isFirstPage());//是否第一页
		result.put("isLastPage", page.isLastPage());//是否最后一页
		result.put("list", data);
		
		List<Record> types = Db.find("select * from sys_dict whers where pid = 185");
		JSONObject questionTypes = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<types.size();i++){
			questionTypes.put("id", types.get(i).getInt("id"));
			questionTypes.put("text", types.get(i).getStr("text"));
			jsonArray.add(questionTypes);
		}
		
		result.put("questionTypes", jsonArray);
		renderJson(JsonResult.JsonResultOKwithJson(result));
		
	}
	
	public void saveQuestion(){
		
		UploadFile uploadFile  = getFile("file");
		
		String questionText = getPara("questionText","");
		
		Integer level = getParaToInt("level",1);
		
		Integer score = getParaToInt("score",5);
		
		Integer questionWeidu = getParaToInt("questionWeidu",0);
		
		EvalQuestion evalQuestion = new EvalQuestion();
		evalQuestion.setText(questionText);
		evalQuestion.setFraction(score);
		evalQuestion.setType(questionWeidu);
		
		if(uploadFile!=null){
			File file = uploadFile.getFile();
			String fileName1=file.getName();
			int random = (int) (Math.random() * 1000000);
			String fileName = new Date().getTime()+ random	+ ""+ fileName1.subSequence(fileName1.indexOf("."),
					fileName1.length());
			String key = QiniuUtil.fileUpdate(file, fileName);
			evalQuestion.setSoundUrl("http://source.fandoutech.com.cn/"+key);
		}
		
		evalQuestion.save();
		
		Db.update("INSERT INTO eval_label_relation (label_id,relation_id) VALUES ((SELECT id FROM eval_label WHERE sort = ?),?)",level,evalQuestion.getId());
		
		renderJson(JsonResult.JsonResultOK());
		
		
	}
	
	public void delQuestion(){
		
		Integer questionId = getParaToInt("questionId",0);
		
		if(xx.isEmpty(questionId)){
			renderJson(JsonResult.JsonResultOK(203));
		}
		
		Db.update("DELETE FROM eval_question WHERE id = ?",questionId);
		
		renderJson(JsonResult.JsonResultOK());
	}
	
	public void saveOption(){
		UploadFile uploadFile = getFile();
		
		String optionText = getPara("optionText","");
		
		Integer questionId = getParaToInt("questionId");
		
		Integer isCorrect = 0;
		
		Integer optionType = getParaToInt("optionType");
		
		
		if(getPara("isCorrect")!=null){
			isCorrect = 1;
		}
		
		EvalOption evalOption = new EvalOption();
		
		if(!optionText.equals("")){
			evalOption.setText(optionText);
		}
		
		if(uploadFile!=null){
			File file = uploadFile.getFile();
			String fileName1=file.getName();
			int random = (int) (Math.random() * 1000000);
			String fileName = new Date().getTime()+ random	+ ""+ fileName1.subSequence(fileName1.indexOf("."),
					fileName1.length());
			String key = QiniuUtil.fileUpdate(file, fileName);
			
			if(optionType.equals(2)){
				evalOption.setSoundUrl("http://source.fandoutech.com.cn/"+key);
			}
			if(optionType.equals(3)){
				evalOption.setPicUrl("http://source.fandoutech.com.cn/"+key);
			}
			
		}
		
		evalOption.save();
		
		Db.update("INSERT INTO eval_question_option (question_id,option_id,is_correct) VALUES (?,?,?)"
				,questionId,evalOption.getId(),isCorrect);
		
		renderJson(JsonResult.JsonResultOK());
		
		
	}
	
	public void delOption(){
		
		Integer optionId = getParaToInt("optionId",0);
		
		if(xx.isEmpty(optionId)){
			renderJson(JsonResult.JsonResultOK(203));
		}
		
		Db.update("DELETE FROM eval_option WHERE id = ?",optionId);
		
		renderJson(JsonResult.JsonResultOK());
	}
	
	public void verificationAnswer(){
		
		String answers = getPara("answers");
		
		int num = 0;
		
		String map = getPara("map");
		
		//System.out.println(map);
		
		
		
	}
	
	
	
	
}
