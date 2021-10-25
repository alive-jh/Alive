package com.wechat.jfinal.api.push;


import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.MakeUp;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.PlayHistory;
import com.wechat.jfinal.model.PushHistory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SourcePush extends Controller{
	
	//保存推送历史，推送列表存缓存中，推送记录存数据库中
    public void savePushSourceList(){
    	String userId = getPara("userId","");
    	String epalId = getPara("epalId","");
    	String sourceList = getPara("sourceList","");
    	JSONArray temp = new JSONArray();
    	try{
    		temp = JSONArray.fromObject(sourceList); //资源列表转json
    	}catch(Exception e){
    		e.printStackTrace();
        	JSONObject data = new JSONObject();
        	data.put("_list", sourceList);
        	data.put("code", 201);
        	data.put("msg", "paramter formater error!");
        	renderJson(data);
        	return;
    		
    	}
    	//后加字段status，没有指定默认值
    	List<PushHistory> pushHistorys = new ArrayList<>();
    	for(int i=0;i<temp.size();i++){
    		JSONObject pushObj = temp.getJSONObject(i);
    		String sourceName = pushObj.getString("name");
    		String sourceId = pushObj.getString("id");
    		if("".equals(sourceId)||null==sourceId){
    			sourceId = "0";
    		}
    		String sourceUrl = pushObj.getString("url");
    		String source = pushObj.toString();
    		PushHistory pushHistory = new PushHistory();
    		
    		try {
    			pushHistory.setSourceId(Integer.parseInt(sourceId));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
    		
    		pushHistory.setSource(source);
    		pushHistory.setSourceName(sourceName);
    		pushHistory.setSourceUrl(sourceUrl);
    		pushHistory.setTimes(1);
    		pushHistory.setEpalId(epalId);
    		pushHistory.setUserId(userId);
    		pushHistorys.add(pushHistory);
    	}
    	Db.batchSave(pushHistorys, pushHistorys.size());
    	String key = userId + "_" + epalId;
    	Cache redis = Redis.use();
    	redis.set(key, sourceList);
    	JSONObject data = new JSONObject();
    	data.put("_list", sourceList);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    	return;
    }

	//保存推送历史，推送列表存缓存中，推送记录存数据库中
	@EmptyParaValidate(params = {"userId","epalId","sourceList"})
	public void savePushSourceListNew(){
		String userId = getPara("userId");
		String epalId = getPara("epalId");
		String sourceList = getPara("sourceList");

		JSONObject pushData = null;

		try {
			pushData = JSONObject.fromObject(getPara("sourceList"));
		}catch (Exception e){
			e.printStackTrace();
			Result.error(20501,"结构体解析异常",this);
			return;
		}

		JSONArray pushList = pushData.getJSONArray("list");
		List<PushHistory> pushHistorys = new ArrayList<PushHistory>(pushList.size());

		for (int i = 0; i < pushList.size(); i++) {
			JSONObject pushObj = pushList.getJSONObject(i);
			String sourceName = pushObj.getString("name");
			String sourceId = pushObj.getString("id");
			if(null==sourceId||"".equals(sourceId)){
				sourceId = "0";
			}
			PushHistory pushHistory = new PushHistory();
			pushHistory.setSourceId(Integer.parseInt(sourceId));
			pushHistory.setSource(pushObj.toString());
			pushHistory.setSourceName(pushObj.getString("name"));
			pushHistory.setSourceUrl(pushObj.getString("url"));
			pushHistory.setTimes(1);
			pushHistory.setEpalId(epalId);
			pushHistory.setUserId(userId);
			pushHistorys.add(pushHistory);
		}

		Db.batchSave(pushHistorys, 200);
		String key = userId + "_" + epalId;
		Cache redis = Redis.use();
		redis.set(key, sourceList);

		Result.ok(this);

	}
    
    //获取推送列表，数据存在缓存中
    public void getPushSourceList(){
    	String userId = getPara("userId","");
    	String epalId = getPara("epalId","");
    	String key = userId + "_" + epalId;
    	Cache redis = Redis.use();
    	String sourceList = redis.get(key);
    	JSONObject data = new JSONObject();
    	data.put("_list", sourceList);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    }
    
    //获取推送记录（状态为1的）
    public void getPushHistoryList(){
    	String userId = getPara("userId","");
    	String epalId = getPara("epalId","");
    	List<Record> temp= Db.find("select id id,source_name sourceName,source_id sourceId,source_url sourceUrl,source source,times times,date_format(push_time,'%Y-%c-%d %H:%i:%S') pushTime from push_history where user_id=? and epal_id=? and status=1 order by push_time desc",userId,epalId);
    	Map<String, Object> data = new HashMap();
    	data.put("_list", temp);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    }
    
    //清除推送记录 （修改推送记录的状态）
    public void wipePushHistory(){
    	Map<String, Object> data = new HashMap();
    	String userId = getPara("userId","");
    	String epalId = getPara("epalId","");
    	//执行update语句
    	Integer result = Db.update("update push_history set status=0 where user_id=? and epal_id=?",userId,epalId);
    	//System.out.println(result);
    	data.put("code", 200);
    	data.put("msg", "清除成功！");
    	renderJson(data);
    }
    
    
    //删除推送记录 (单条，直接删除数据库数据)
    public void deletePushHistory(){
    	Map<String, Object> data = new HashMap();
    	int id = getParaToInt("id",0);
    	PushHistory pushHistory = PushHistory.dao.findById(id);//查找数据
    	if(null == pushHistory){ //数据不存在
        	data.put("code", 402);
        	data.put("msg", "数据不存在");
    	}else{
        	boolean result = pushHistory.delete(); //删除数据，并且返回状态
        	if(result == true){
            	data.put("code", 200);
            	data.put("msg", "删除成功");
        	}else{
            	data.put("code", 209);
            	data.put("msg", "系统错误，删除失败");
        	}
    	}

    	renderJson(data);
    }
    
    //机器播放记录
    public void savePlayHistory() throws ParseException{
		PlayHistory bean = getBean(PlayHistory.class,"");
		if(null == bean.getId()){
			bean.save();
			renderJson(JsonResult.JsonResultOK(bean));
		}else{
			bean.update();
			renderJson(JsonResult.JsonResultOK(PlayHistory.dao.findById(bean.getId())));
		}
    	
    }
    
    //获取机器人播放记录
    public void getPlayHistoryList() throws Exception {

			String epalId = getPara("epalId", "");
			int studentId = getParaToInt("studentId",0);
			String date = getPara("date", "1997-09-01");

			List<PlayHistory> temp = PlayHistory.dao.find("SELECT id, source_name, source_id, source_url, source, play_time, stop_time, complete_time FROM play_history WHERE student_id = ? AND play_time > '" + date + " 00:00:01' AND play_time <'" + date + " 23:59:59' ORDER BY play_time DESC", studentId);
			if(temp == null)
				temp = new ArrayList<>();
			renderJson(JsonResult.OK(MakeUp.timestampToString(temp)));

    }

}
