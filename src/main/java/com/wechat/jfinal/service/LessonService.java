package com.wechat.jfinal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.entity.ClassRoomWord;
import com.wechat.jfinal.model.Audioinfo;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.ClassRoomCategory;
import com.wechat.jfinal.model.ClassScriptNormal;
import com.wechat.jfinal.model.ClassScriptNormalTemp;
import com.wechat.jfinal.model.ClassScriptType;
import com.wechat.jfinal.model.ClassScriptTypeMutex;
import com.wechat.jfinal.model.ClassTeacher;
import com.wechat.util.script.ScriptTransform;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class LessonService {

	public static Object getClassScriptNormalList(int classRoomId) {
		// 获取当前课堂的所有指令列表
		
		List<Record> classScriptNormals = Db.find(
				"select a.id,a.class_room_id,a.class_script_type_id,a.class_script_content,a.sort,a.create_time,IFNULL(a.total_time,0) AS total_time,IFNULL(b.version,'0') AS version from class_script_normal a LEFT JOIN class_script_normal_version b ON a.class_room_id = b.class_room_id where a.class_room_id=? and a.sort < 10000 order by sort asc"
				,classRoomId);
		
		for(Record rc : classScriptNormals){
			
			String classScriptContent = rc.getStr("class_script_content");
			if(classScriptContent!=null){
				if (classScriptContent.startsWith("[")) {
					// 如果classScriptContent为列表不做任何操作
				}else if(classScriptContent.equals("(null)")){
					rc.set("class_script_content", new JSONArray());
				}else{
					rc.set("class_script_content", ScriptTransform.parseToNewScript(classScriptContent));
				}
			}else{
				
			}
			
		}
		
		return classScriptNormals;
		
		
		/*List<ClassScriptNormal> searchResult = ClassScriptNormal.dao.find(
				"select * from class_script_normal where class_room_id=? and sort < 10000 order by sort asc",
				classRoomId);
		for (int i = 0; i < searchResult.size(); i++) {
			String classScriptContent = searchResult.get(i).getClassScriptContent();
			//System.out.println(classScriptContent);
			if (classScriptContent != null) {
				if (classScriptContent.startsWith("[")) {
					// 如果classScriptContent为列表不做任何操作
				} else {
					searchResult.get(i).setClassScriptContent(ScriptTransform.parseToNewScript(classScriptContent));
				}
			}else if(classScriptContent.equals("(null)")){
				searchResult.get(i).setClassScriptContent("123");
			}

		}

		return searchResult;*/
	}

	public int saveClassScriptNormal(int classRoom, String classScriptNormalList) {

		try {
			String sql = "update class_script_normal set sort = 10000 where class_room_id = ?";

			Db.update(sql, classRoom);

			JSONArray lessonArray = JSONArray.fromObject(classScriptNormalList);

			String insert = "insert into class_script_normal (class_room_id,class_script_type_id,class_script_content,sort,create_time,total_time) VALUES (?,?,?,?,?,?)";

			String update = "update class_script_normal set class_room_id=?,class_script_type_id=?,class_script_content=?,sort=?,total_time=? where id=?";

			List<Object[]> updateParas = new ArrayList<Object[]>();
			List<Object[]> insertParas = new ArrayList<Object[]>();

			for (int i = 0; i < lessonArray.size(); i++) {

				Object[] obj = new Object[6];

				JSONObject json = JSONObject.fromObject(lessonArray.get(i));
				Object id = json.get("id");
				Integer sort = (Integer) json.get("sort");
				Integer classRoomId = (Integer) json.get("classRoomId");
				Integer classScriptTypeId = (Integer) json.get("classScriptTypeId");
				String classScriptContent = (String) json.get("classScriptContent");
				if (classScriptContent == null) {
					// throw new NullPointerException();
					classScriptContent = "";
				}
				Integer totalTime = (Integer) json.get("totalTime");

				if (id != null && !id.toString().equals("null") && !id.equals(0)) {
					obj[0] = classRoomId;
					obj[1] = classScriptTypeId;
					obj[2] = classScriptContent;
					obj[3] = sort;
					obj[4] = totalTime;
					obj[5] = (Integer) id;
					updateParas.add(obj);
				} else {
					obj[0] = classRoomId;
					obj[1] = classScriptTypeId;
					obj[2] = classScriptContent;
					obj[3] = sort;
					obj[4] = new Date();
					obj[5] = totalTime;
					insertParas.add(obj);
				}
			}

			Object[][] objs1 = new Object[updateParas.size()][6];

			for (int i = 0; i < updateParas.size(); i++) {
				objs1[i] = updateParas.get(i);
			}

			Object[][] objs2 = new Object[insertParas.size()][6];

			for (int i = 0; i < insertParas.size(); i++) {
				objs2[i] = insertParas.get(i);
		 	}

			Db.batch(update, objs1, updateParas.size());

			Db.batch(insert, objs2, insertParas.size());
			
			String updateMillils = System.currentTimeMillis()+"";
			Db.update("INSERT INTO class_script_normal_version (class_room_id,version) VALUES (?,?) ON DUPLICATE KEY UPDATE version=?",classRoom,updateMillils,updateMillils);

		} catch (JSONException e) {
			throw e;
		} catch (Exception e2) {
			throw e2;
		}

		return 1;
	}

	public int saveClassScriptNormalTemp(int classRoom, String classScriptNormalList) {

		try {
			String sql = "update class_script_normal_temp set sort = 10000 where class_room_id = ?";

			Db.update(sql, classRoom);

			JSONArray lessonArray = JSONArray.fromObject(classScriptNormalList);

			String insert = "insert into class_script_normal_temp (class_room_id,class_script_type_id,class_script_content,sort,create_time) VALUES (?,?,?,?,?)";

			String update = "update class_script_normal_temp set class_room_id=?,class_script_type_id=?,class_script_content=?,sort=? where id=?";

			List<Object[]> updateParas = new ArrayList<Object[]>();

			List<Object[]> insertParas = new ArrayList<Object[]>();

			for (int i = 0; i < lessonArray.size(); i++) {

				Object[] obj = new Object[5];

				JSONObject json = JSONObject.fromObject(lessonArray.get(i).toString());
				Object id = json.get("id");
				Integer sort = (Integer) json.get("sort");
				Integer classRoomId = (Integer) json.get("classRoomId");
				Integer classScriptTypeId = (Integer) json.get("classScriptTypeId");
				String classScriptContent = (String) json.get("classScriptContent");

				if (classScriptContent == null) {
					// throw new NullPointerException();
					classScriptContent = "";
				}

				if (id != null && !id.toString().equals("null") && !id.equals(0)) {

					obj[0] = classRoomId;
					obj[1] = classScriptTypeId;
					obj[2] = classScriptContent;
					obj[3] = sort;
					obj[4] = (Integer) id;
					updateParas.add(obj);

				} else {

					obj[0] = classRoomId;
					obj[1] = classScriptTypeId;
					obj[2] = classScriptContent;
					obj[3] = sort;
					obj[4] = new Date();
					insertParas.add(obj);

				}

			}

			Object[][] objs1 = new Object[updateParas.size()][6];

			for (int i = 0; i < updateParas.size(); i++) {
				objs1[i] = updateParas.get(i);
			}

			Object[][] objs2 = new Object[insertParas.size()][6];

			for (int i = 0; i < insertParas.size(); i++) {
				objs2[i] = insertParas.get(i);
			}

			Db.batch(update, objs1, updateParas.size());

			Db.batch(insert, objs2, insertParas.size());
			
			String updateMillils = System.currentTimeMillis()+"";
			Db.update("INSERT INTO class_script_normal_version (class_room_id,version) VALUES (?,?) ON DUPLICATE KEY UPDATE version=?",classRoom,updateMillils,updateMillils);

		} catch (JSONException e) {
			throw e;
		} catch (Exception e2) {
			throw e2;
		}

		return 1;
	}

	public static Object getClassRoomList(String classRoomName, int classTeacherId, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "";
		if ("".equals(classRoomName)) { // 不带name参数为取全部
			sql = "from class_room where status!=-1 and teacher_id=? order by create_time desc";

		} else { // 带name参数为搜索

			sql = "from class_room where status!=-1 and teacher_id=? and class_name like '%" + classRoomName
					+ "%' order by create_time desc";
		}
		Page<ClassRoom> searchResult = ClassRoom.dao.paginate(pageNumber, pageSize,
				"select id id,teacher_id teacherId,teacher_name teacherName,class_name className,cover cover,summary summary,status status,sort sort,category_id categoryId,book_res_id bookResId,group_id groupId,create_time createTime,class_room_type classRoomType,ifnull(video_url,'') videoUrl,type type,ifnull(book_res_ids,book_res_id) bookResIds ",
				sql, classTeacherId);
		return searchResult;
	}

	public static Object getClassGradesList(String classGradesName, int classTeacherId, int pageNumber, int pageSize) {
		// 老师等级为99的，取所有的班级
		String sql = "";
		ClassTeacher classTeacher = ClassTeacher.dao.findById(classTeacherId);
		if (classTeacher.getLevel() == 99) {

			if ("".equals(classGradesName)) { // 不带name参数为取全部

				sql = "from class_grades  order by create_time desc";

			} else { // 带name参数为搜索

				sql = "from class_grades where  class_grades_name like '%"
						+ classGradesName + "%' order by create_time desc";
			}

			Page<ClassRoom> searchResult = ClassRoom.dao.paginate(pageNumber, pageSize, "select * ", sql);
			return searchResult;
		} else {

			List<Record> otherGradesIds = Db
					.find("select class_grades_id from class_teacher_grades where class_teacher_id=?", classTeacherId);
			List<Record> myGradesIds = Db.find("select id from class_grades where teacher_id=?", classTeacherId);

			ArrayList<String> classGradesIds = new ArrayList<String>();

			for (int i = 0; i < myGradesIds.size(); i++) {

				classGradesIds.add(myGradesIds.get(i).getInt("id").toString());
			}

			for (int i = 0; i < otherGradesIds.size(); i++) {

				classGradesIds.add(otherGradesIds.get(i).getInt("class_grades_id").toString());
			}
			
			String condition = "";
			
			if(classGradesIds.size()>0){
				condition = classGradesIds.get(0);
				for (int j = 1; j < classGradesIds.size(); j++) {
					condition += "," + classGradesIds.get(j);
				}
			}else{
				JSONObject json = new JSONObject();
				json.put("list", new ArrayList<>());
				return json;
			}
			

			if ("".equals(classGradesName)) { // 不带name参数为取全部

				sql = "from class_grades where  id in (" + condition + ") order by create_time desc";

			} else { // 带name参数为搜索

				sql = "from class_grades where id in (" + condition + ") and class_grades_name like '%"
						+ classGradesName + "%' order by create_time desc";
			}

			Page<ClassRoom> searchResult = ClassRoom.dao.paginate(pageNumber, pageSize, "select * ", sql);
			return searchResult;
		}

	}

	public static Object getAudioInfo(String audioIds) {
		// 通过备课资源ID获取资源信息
		String[] temp = audioIds.split(",");
		ArrayList<Audioinfo> audioInfos = new ArrayList<Audioinfo>();
		for (int i = 0; i < temp.length; i++) {
			Audioinfo audioInfo = Audioinfo.dao.findFirst("select * from audioinfo where audioid=?", temp[i]);

			if(audioInfo == null){
				audioInfo = Audioinfo.dao.findFirst("select * from audioinfo where id=?", temp[i]);
			}

			audioInfos.add(audioInfo);
		}
		return audioInfos;
	}

	/**
	 * 
	 * 获取课堂分类标签
	 * 
	 */
	public static Object getClassRoomCategory() {
		// TODO Auto-generated method stub
		List<ClassRoomCategory> data = ClassRoomCategory.dao
				.find("select * from class_room_category where parent_id!=0 ORDER BY sort asc");
		return data;
	}

	/**
	 * 根据分类ID获取课堂列表
	 * 
	 * 
	 */
	public static Object getClassRoomByCategory(int categoryId, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Page<ClassRoom> data = ClassRoom.dao.paginate(pageNumber, pageSize,
				"select id id,teacher_id teacherId,teacher_name teacherName,class_name className,cover cover,summary summary,status status,sort sort,category_id categoryId,book_res_id bookResId,group_id groupId,create_time createTime,class_room_type classRoomType,ifnull(video_url,'') videoUrl,type type,ifnull(book_res_ids,book_res_id) bookResIds ",
				"from class_room where status!=-1 and category_id=? ORDER BY create_time desc", categoryId);
		return data;
	}

	public static Object findClassScriptTypes() {
		// 取status=1的为最新的指令分类，老的指令分裂：status=3

		List<ClassScriptType> data = ClassScriptType.dao
				.find("select * from class_script_type where status<3 order by groupId asc");
		JSONArray result = new JSONArray();

		JSONObject temp = new JSONObject();
		for (int i = 0; i < data.size(); i++) {

			ClassScriptType classScriptType = (ClassScriptType) data.get(i);
			String groupName = classScriptType.getGroupName();
			String name = classScriptType.getName();
			String des = classScriptType.getDes();
			int id = classScriptType.getId();
			//System.out.println(groupName);

			JSONObject item = new JSONObject();
			item.put("id", id);
			item.put("name", name);
			item.put("des", des);

			if (temp.containsKey(groupName)) {
				JSONArray classScriptTypeList = temp.getJSONArray(groupName);
				classScriptTypeList.add(item);
				temp.put(groupName, classScriptTypeList);

			} else {
				JSONArray classScriptTypeList = new JSONArray();
				classScriptTypeList.add(item);
				temp.put(groupName, classScriptTypeList);
			}

		}
		for (Iterator iter = temp.keys(); iter.hasNext();) { // 先遍历整个 temp 对象
			String groupName = (String) iter.next();
			JSONArray classScriptTypeList = temp.getJSONArray(groupName);
			JSONObject item = new JSONObject();
			item.put("groupName", groupName);
			item.put("classScriptTypeList", classScriptTypeList);
			result.add(item);
		}

		return result;
	}

	public static Object findClassScriptTypesMutex() {
		// TODO Auto-generated method stub
		JSONObject temp = new JSONObject();
		List<ClassScriptTypeMutex> data = ClassScriptTypeMutex.dao.find("select * from class_script_type_mutex");
		for (int i = 0; i < data.size(); i++) {
			ClassScriptTypeMutex classScriptTypeMutex = data.get(i);
			String id1 = classScriptTypeMutex.getTypeId1().toString();
			String id2 = classScriptTypeMutex.getTypeId2().toString();
			if (temp.containsKey(id1)) {
				JSONArray classScriptTypeList = temp.getJSONArray(id1);
				classScriptTypeList.add(id2);
				temp.put(id1, classScriptTypeList);

			} else {
				JSONArray classScriptTypeList = new JSONArray();
				classScriptTypeList.add(id2);
				temp.put(id1, classScriptTypeList);
			}
		}
		return temp;
	}

	public List<ClassScriptNormal> getClassScriptNormals(int classRoom) {
		return ClassScriptNormal.dao.find("select * from class_script_normal where class_room_id = ? and sort<10000",
				classRoom);
	}

	public List<ClassScriptNormalTemp> getClassScriptNormalTemp(int classRoom) {
		// TODO Auto-generated method stub
		return ClassScriptNormalTemp.dao
				.find("select * from class_script_normal_temp where class_room_id = ? and sort<10000", classRoom);
	}

	public ClassRoomWord getClassRoomWord(Integer classRoomId) {
		ClassRoomWord classRoomWord = new ClassRoomWord();
		
		List<Audioinfo> audioinfos = this.getAudioInfoByClassRoomId(classRoomId);
		
		String[] mediaInfoUrls = new String[audioinfos.size()];
		
		for(int i=0;i<audioinfos.size();i++){
			mediaInfoUrls[i] = audioinfos.get(i).getMediainfo();
		}
		
		classRoomWord.setClassRoomId(classRoomId);
		
		classRoomWord.setWords(this.getMediaInfoWords(mediaInfoUrls));
		
		return classRoomWord;
		
	}

	private String[] getMediaInfoWords(String[] mediaInfoUrls) {
		
		String[] mediaInfos = this.downloadMediaInfos(mediaInfoUrls);
		
		
		
		
		return null;
	}

	private String[] downloadMediaInfos(String[] mediaInfoUrls) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Audioinfo> getAudioInfoByClassRoomId(Integer classRoomId) {
		
		List<Audioinfo> audioinfos = new ArrayList<Audioinfo>();
		
		String sql = "SELECT a.* FROM audioinfo a JOIN class_room_audio_rel b ON a.id = b.audio_id OR a.audioid = b.audio_id WHERE b.class_room_id = ?";
		
		audioinfos.addAll(Audioinfo.dao.find(sql,classRoomId));
		
		return audioinfos;
	}
	
}
