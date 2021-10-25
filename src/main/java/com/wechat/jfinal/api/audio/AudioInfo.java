package com.wechat.jfinal.api.audio;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.ElasticsearchUtils;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.Audioinfo;
import com.wechat.jfinal.model.ClassRoomAudioRel;
import com.wechat.jfinal.model.ClassTeacher;
import com.wechat.jfinal.model.MaterialFile;
import com.wechat.jfinal.model.MaterialPath;
import com.wechat.util.MD5UTIL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AudioInfo extends Controller{

	final public static int PUBLIC_USER_ID = 22639;
	
	public void audioManager(){
		
		String name = getPara("name","");
		Integer page = getParaToInt("page",1);
		Integer pageSize = getParaToInt("pageSize",10);
		
		if(xx.isAllEmpty(name,page,pageSize)){
			renderJson(JsonResult.JsonResultError(203,"参数异常"));
		}
		
		Page<Record> records = ElasticsearchUtils.getInstance().fuzzy("audio","audioinfo", "name",name,page,pageSize);
		renderJson(JsonResult.JsonResultOK(records));
		
	}
	
	public void search(){
		
		String audioName = getPara("audioName");
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		Integer teacherId = getParaToInt("teacherId",0);
		
		String sql = "FROM (SELECT a.*,b.type AS file_type FROM `audioinfo` a, material_file b where b.status > 0 and a.audioid = b.audioinfo_id"
					 + " AND a.user_id = ( SELECT agent_id FROM class_teacher WHERE id = "+teacherId+")"
					 + " AND a.`name` LIKE '%"+audioName+"%' UNION SELECT a.*,b.type AS file_type FROM `audioinfo` a,"
					 + " material_file b where b.status > 0 and a.audioid = b.audioinfo_id AND a.user_id = "+PUBLIC_USER_ID+" AND a.`name` LIKE '%"+audioName+"%' UNION SELECT a.*,b.type AS file_type FROM `audioinfo` a,"
					 + " material_file b where b.status > 0 and a.id = b.audioinfo_id AND a.user_id = "+PUBLIC_USER_ID+" AND a.`name` LIKE '%"+audioName+"%') a";
		
		Page<Audioinfo> page = Audioinfo.dao.paginate(pageNumber, pageSize, "SELECT a.*",sql);
		
		/*if(!xx.isEmpty(audioName)){
			sql = "FROM `audioinfo` a, material_file b where a.user_id = 1 AND a.audioid = b.audioinfo_id "
					+ "AND a.`name` LIKE '%"+audioName+"%' ORDER BY a.id DESC";
			page =  Audioinfo.dao.paginate(pageNumber, pageSize, "SELECT a.*,b.type AS file_type",sql);
		}
		
		if(!xx.isEmpty(teacherId)){
			sql = "FROM (SELECT a.*,b.type AS file_type FROM (SELECT a.* FROM `audioinfo` a WHERE a.user_id = 1 "
					+ "OR a.user_id = ( SELECT agent_id FROM class_teacher WHERE id = "+teacherId+" ) ) a,material_file"
							+ " b WHERE a.audioid = b.audioinfo_id) a ORDER BY a.id DESC";
			page =  Audioinfo.dao.paginate(pageNumber, pageSize, "SELECT a.*",sql);
		}*/
		
		
		
		
		/*String sql = "FROM `audioinfo` a, material_file b where a.user_id = 1 AND a.audioid = b.audioinfo_id";
		
		if(!xx.isEmpty(audioName)){
			sql +=  " AND a.`name` LIKE '%"+audioName+"%'";
		}
		
		if(!xx.isEmpty(teacherId)){
			sql += " OR a.user_id = (SELECT agent_id FROM class_teacher WHERE id = "+teacherId+")";
		}
		
		Page<Audioinfo> page =  Audioinfo.dao.paginate(pageNumber, pageSize, "SELECT a.*,b.type",sql+" ORDER BY a.id DESC");*/
		
		Result.ok(page,this);
	}
	
	@Deprecated
	public void addRelation(){
		Integer classRoomId = getParaToInt("classRoomId",0);
		String audioIds = getPara("audioIds");
		
		if(xx.isAllEmpty(classRoomId,audioIds)){
			Result.error(203, this);
			return;
		}
		
		String[] audioIdArray = audioIds.split(",");
		
		List<ClassRoomAudioRel> audioRels = new ArrayList<ClassRoomAudioRel>();
		
		for(String audioId:audioIdArray){
			ClassRoomAudioRel audioRel = new ClassRoomAudioRel();
			audioRel.setClassRoomId(classRoomId);
			audioRel.setAudioId(Integer.parseInt(audioId));
			audioRels.add(audioRel);
		}
		
		Db.batchSave(audioRels, 100);
		Result.ok(this);
		
	}
	
	/*
	 * 	
	 * */
	public void removeRelation(){
		Integer classRoomId = getParaToInt("classRoomId",0);
		Integer audioId = getParaToInt("audioId",0);
		int materialId = getParaToInt("material",0);
		
		if(xx.isAllEmpty(classRoomId,audioId)){
			Result.error(203, this);
			return;
		}
		
		Db.update("delete from class_room_audio_rel where class_room_id = ? and audio_id = ? and material_file_id = ?",classRoomId,audioId,materialId);
		
		Result.ok(this);
	}

	@EmptyParaValidate(params={"member"})
	public void resourceList(){
		
		Integer pathId = getParaToInt("path", 0);
		Integer memberId = getParaToInt("member");

		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		
		int agentId = getParaToInt("agent",0);
		
		List<Record> list = new ArrayList<Record>();
		

		if (pathId == 0) {
			
			/*
			 * 查出机构的的根目录
			 */
			List<Record> root = Db.find("SELECT b.id as material_file_id,IFNULL(b.`name`,a.`name`) AS `name`,IFNULL(b.path_pid,a.id) AS"
					+ " path_pid,a.user_id,a.member_id,ifnull(b.type,'folder') as folder,ifnull(b.isdir,1) as isdir,ifnull(b.create_time,a.create_time) as create_time FROM `material_path` a LEFT JOIN material_file b ON "
					+ "a.id = b.path_pid and b.`status` = 1 WHERE pid = 0 AND (user_id = "+PUBLIC_USER_ID+" or user_id = ?) ORDER BY b.create_time DESC",agentId);
			
			/*
			 * 	 用户自己的素材
			 */
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.id as material_file_id,a.id,a.name,a.url as src,a.type as file_type,a.isdir,a.belong,a.path_pid,a.status,a.create_time,a.audio_length,a.file_size,a.audioinfo_id as audioid,b.user_id,b.member_id FROM material_file a, material_path b WHERE a.belong = b.id AND b.id IN (");
			sql.append(" SELECT id FROM material_path WHERE pid = 0 AND member_id = ? ) and a.`status` = 1 order by a.isdir desc ,a.create_time desc LIMIT ?, ?");
			
			list = Db.find(sql.toString(),memberId,(pageNumber-1)*pageSize,pageSize);
			
			root.addAll(list);
			
			list = root;
		}else{
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.id as material_file_id,a.id,a.name,a.url as src,a.type as file_type,a.isdir,a.belong,a.path_pid,a.status,a.create_time,a.audio_length,a.file_size,a.audioinfo_id as audioid,b.user_id,b.member_id FROM material_file a,material_path b WHERE ");
			sql.append("a.belong = b.id AND b.id = ? AND a.`status` = 1 order by a.isdir desc , a.create_time desc LIMIT ?,?");
			
			list = Db.find(sql.toString(),pathId,(pageNumber-1)*pageSize,pageSize);
		}
		

		Result.ok(Result.makeupList(list),this);
		
	}
	
	
	@EmptyParaValidate(params={"member","name"})
	public void createFolder(){
		
		Integer path = getParaToInt("path",0);
		Integer memberId = getParaToInt("member");
		String name = getPara("name");
		
		String teacherName = getPara("teacherName");
		/*
		 * 获取根目录
		 */
		if(path==0){
			
			MaterialPath materialPath = MaterialPath.dao.findFirst("select * from material_path where member_id = ? and pid = 0",memberId);
			
			if(materialPath == null ){
				materialPath = new MaterialPath();
				materialPath.setName(teacherName+"的教学资源").setPid(0).setMemberId(memberId).save();
				
				MaterialFile root = new MaterialFile();
				root.setName(teacherName+"的教学资源").setType("folder").setIsdir(1).setBelong(0).setPathPid(materialPath.getId()).save();
			}
			
			path = materialPath.getId();
			
		}
		
		/*
		 * 创建文件夹
		 */
		
		MaterialPath materialPath2 = new MaterialPath();
		materialPath2.setName(name);
		materialPath2.setPid(path);
		materialPath2.setUserId(-1);
		materialPath2.setMemberId(memberId).save();
		
		MaterialFile materialFile = new MaterialFile();
		materialFile.setPathPid(materialPath2.getId());
		materialFile.setBelong(path);
		materialFile.setName(name);
		materialFile.setUrl("");
		materialFile.setType("folder");
		materialFile.setIsdir(1).save();
		
		Result.ok(Result.toJson(materialFile),this);
		
	}
	
	@EmptyParaValidate(params={"files","member"})
	public void copyFile2Folder(){
		
		String files = getPara("files");
		int member = getParaToInt("member");
		int path = getParaToInt("path",0);
		
		if(path==0){
			
			MaterialPath materialPath = MaterialPath.dao.findFirst("select * from material_path where member_id = ? and pid = 0",member);
			path = materialPath.getId();
			
		}
		
		List<MaterialFile> materialFiles = MaterialFile.dao.find("SELECT * FROM material_file WHERE id IN ("+files+")");
		
		for(MaterialFile materialFile : materialFiles){
			materialFile.setId(null);
			materialFile.setBelong(path);
		}
		
		Db.batchSave(materialFiles, 200);
		
		Result.ok(this);
		
	}
	
	@EmptyParaValidate(params={"name","file"})
	public void changeFileName(){
		
		MaterialFile materialFile = MaterialFile.dao.findById(getParaToInt("file"));
		
		if(materialFile.getIsdir()==1){
			Db.update("update material_path set name = ? where id = ?",getPara("name"),materialFile.getPathPid());
		}
		
		materialFile.setName(getPara("name")).update();
		
		Result.ok(this);
	}
	
	@EmptyParaValidate(params={"files","member"})
	public void deleteFile(){
		
		Integer memberId = getParaToInt("member");
		Integer path = getParaToInt("path",0);
		
		if(path==0){
			
			MaterialPath materialPath = MaterialPath.dao.findFirst("select * from material_path where member_id = ? and pid = 0",memberId);
			path = materialPath.getId();
			
		}
		
		String[] files = getPara("files").split(",");
		
		List<String> sqls = new ArrayList<String>(files.length);
		
		List<MaterialFile> materialFiles = MaterialFile.dao.find("SELECT * FROM material_file WHERE id IN ("+getPara("files")+")");
		
		for(MaterialFile materialFile : materialFiles){
			if(materialFile.getIsdir()==1){
				sqls.add("delete from material_path where id = " + materialFile.getPathPid());
			}
		}
		
		for(String file : files){
			sqls.add("delete from material_file where id = "+file+" and	belong = "+path);
		}
		
		Db.batch(sqls, 200);
		
		
		Result.ok(this);
		
	}
	
	@EmptyParaValidate(params={"name","member"})
	public void resourceSearch(){
		
		String name = getPara("name");
		int memberId = getParaToInt("member");
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		
		int path = getParaToInt("path",0);
		
		int agentId = getParaToInt("agent",0);
		
		String select = "SELECT a.id as material_file_id,a.id,a.name,a.url as src,a.type as file_type,a.isdir,a.belong,a.path_pid,a.status,a.create_time,a.audio_length,a.file_size,a.audioinfo_id as audioid,b.member_id,b.user_id ";
		
		Page<Record> page = null;
		
		if(path == 0){
			
			StringBuffer from = new StringBuffer();
			from.append("FROM `material_file` a,material_path b WHERE a.belong = b.id ");
			from.append("AND (b.member_id = ? OR b.user_id = "+PUBLIC_USER_ID+" or b.user_id = ?) AND a.`name` LIKE ? group by a.id order by a.isdir desc");
			
			page = Db.paginate(pageNumber, pageSize, select,from.toString(),memberId,agentId,"%"+name+"%");
		}else{
			
			StringBuffer from = new StringBuffer();
			from.append("FROM `material_file` a, material_path b, ( SELECT getParLst(?) AS ");
			from.append("pids ) p WHERE (a.belong = b.id AND a.`name` LIKE ? AND FIND_IN_SET(b.id, pids)) ORDER BY a.isdir DESC");
			
			page = Db.paginate(pageNumber, pageSize, select,from.toString(),path,"%"+name+"%");
			
		}
		
		Result.ok(page,this);
		
		
	}
	
	@EmptyParaValidate(params={"member"})
	public void folderList(){
		
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10000);

		int path = getParaToInt("path", 0);
		
		
		Page<MaterialPath> materialPaths = null;
		
		if (path == 0){
			path = MaterialPath.dao.findFirst("select ifnull(id,-1) as id from material_path where member_id = ? and pid = 0",getParaToInt("member")).getId();
		}

		String select = "select a.*";
		String sql = "FROM material_file a,material_path b WHERE a.path_pid = b.id and b.member_id = ? and b.pid = ?";

		materialPaths = MaterialPath.dao.paginate(pageNumber, pageSize, select, sql,getParaToInt("member"),path);
		Result.ok(materialPaths, this);
		
	}
	
	@EmptyParaValidate(params={"files","member"})
	public void move(){
		
		int memberId = getParaToInt("member");
		int path = getParaToInt("path",0);
		int originalPath = getParaToInt("originalPath",0);
		
		String[] files = getPara("files").split(",");
		
		//根目录id
		int root = MaterialPath.dao.findFirst("select ifnull(id,-1) as id from material_path where member_id = ? and pid = 0",memberId).getId();
		
		if(originalPath == 0){
			originalPath = root;
		}
		
		if(path == 0){
			path = root;
		}
		
		List<MaterialFile> materialFiles = MaterialFile.dao.find("SELECT * FROM material_file WHERE id IN ("+getPara("files")+")");
		
		
		List<String> sqls = new ArrayList<String>(files.length);
		
		for(MaterialFile materialFile : materialFiles){
			if(materialFile.getIsdir()==1){
				sqls.add("update material_path set pid = "+path+" where id = " + materialFile.getPathPid() + " and pid = "+originalPath);
			}
		}
		
		for(String file : files){
			sqls.add("update material_file set belong = "+path+" where id = "+Integer.parseInt(file)+" and belong = "+originalPath);
		}
		
		Db.batch(sqls, 200);
		
		Result.ok(this);
		
	}
	
	@EmptyParaValidate(params={"teacher"})
	public void saveTeacherMaterialFile(){
		
		MaterialFile materialFile = getBean(MaterialFile.class,"");
		String url = getPara("src");
		materialFile.setUrl(url);
		
		Integer teacherId = getParaToInt("teacher");
		
		if(materialFile.getBelong()==0){
			
			ClassTeacher classTeacher = ClassTeacher.dao.findById(teacherId);
			
			if(classTeacher == null){
				Result.error(20501, "查询不到教师",this);
				return;
			}
			
			materialFile.setBelong(getTeacherRoot(Integer.parseInt(classTeacher.getMemberId())));
			
		}
		
		materialFile.setIsdir(0);
		materialFile.setPathPid(0);
		materialFile.save();
		
		Audioinfo audioinfo = new Audioinfo();
		audioinfo.setName(materialFile.getName());
		audioinfo.setSrc(url).save();
		audioinfo.setAudioid(audioinfo.getId().toString()).update();
		
		materialFile.setAudioinfoId(audioinfo.getId()).update();
		
		JSONObject json = new JSONObject();
		json.put("materialFile", Result.toJson(audioinfo));
		Result.ok(json,this);
		
	}
	
	@EmptyParaValidate(params={"name","belong","teacher"})
	public void saveTeacherFolder(){
		
		String name = getPara("name");
		int belong = getParaToInt("belong");
		int teacherId = getParaToInt("teacher");
		
		ClassTeacher classTeacher = ClassTeacher.dao.findById(teacherId);
		
		if(classTeacher == null){
			Result.error(20501, "查询不到教师",this);
			return;
		}
		
		if(belong == 0){
		
			belong = getTeacherRoot(Integer.parseInt(classTeacher.getMemberId()));
			
		}
		
		MaterialPath materialPath = new MaterialPath();
		materialPath.setName(name);
		materialPath.setPid(belong);
		materialPath.setStatus(1);
		materialPath.setMemberId(Integer.parseInt(classTeacher.getMemberId()));
		materialPath.save();
		
		MaterialFile materialFile = new MaterialFile();
		materialFile.setName(name);
		materialFile.setType("folder");
		materialFile.setIsdir(1);
		materialFile.setBelong(belong);
		materialFile.setPathPid(materialPath.getId()).save();
		
		JSONObject json = new JSONObject();
		json.put("materialPath", Result.toJson(materialPath));
		
		Result.ok(json,this);
		
	}
	
	
	
	
	private int getTeacherRoot(int memberId){
		
		MaterialPath materialPath = MaterialPath.dao.findFirst("select * from material_path where pid = 0 and member_id = ?",memberId);
		
		if(materialPath == null){
			materialPath = new MaterialPath();
			materialPath.setName("根目录");
			materialPath.setPid(0);
			materialPath.setStatus(1);
			materialPath.setUserId(-1);
			materialPath.setMemberId(memberId).save();
		}
		
		return materialPath.getId();
	}
	
	public static void main(String[] args) {
		System.out.println("SELECT IFNULL(b.`name`,a.`name`) AS `name`,IFNULL(b.path_pid,a.id) AS"
					+ " path_pid,a.user_id,a.member_id,ifnull(b.type,'folder') as folder,ifnull(b.isdir,1) as isdir,ifnull(b.create_time,a.create_time) as create_time FROM `material_path` a LEFT JOIN material_file b ON "
					+ "a.id = b.path_pid WHERE pid = 0 AND (user_id = "+PUBLIC_USER_ID+" or user_id = ?)");
	}


	@EmptyParaValidate(params = {"data"})
	public void audioRemove() {

		JSONObject data = JSONObject.fromObject(getPara("data"));

		int classroom = data.getInt("classroom");
		JSONArray media = data.getJSONArray("media");

		List<String> sqls = new ArrayList<String>(media.size());

		for (int i = 0; i < media.size(); i++) {
			sqls.add(String.format("delete from class_room_audio_rel where class_room_id = %s and audio_id = %s and material_file_id = %s", classroom, media.getJSONObject(i).getInt("audio"), media.getJSONObject(i).getInt("material")));
		}

		Db.batch(sqls,200);

		Result.ok(this);


	}



}
