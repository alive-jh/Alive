package com.wechat.dao.impl;

import com.wechat.dao.AudioInfoDao;
import com.wechat.entity.AudioInfo;
import com.wechat.entity.CurriculumAudio;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AudioInfoDaoImpl extends BaseDaoImpl implements AudioInfoDao{

	@Override
	public void saveAudioInfo(AudioInfo audioInfo) {
		if(audioInfo.getId()!= null)
		{
			this.saveOrUpdate(audioInfo);
		}
		else
		{
			this.saveOrUpdate(audioInfo);
			audioInfo.setAudioId(audioInfo.getId() + "");
			this.saveOrUpdate(audioInfo);
			
		}
		
	}

	@Override
	public Page searchAudioInfo(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from AudioInfo where 1=1");
		if(!"".equals(map.get("name")) && map.get("name") !=  null)
		{
			sql.append(" and name like '%").append(map.get("name")).append("%'");
		}
		
		if(!"".equals(map.get("audioId")) && map.get("audioId") !=  null)
		{
			sql.append(" and audioId = '").append(map.get("audioId")).append("'");
		}

		if(!"".equals(map.get("userId")) && map.get("userId") !=  null)
		{
			sql.append(" and userId =").append(map.get("userId"));
		}
		
		sql.append(" order by id desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public void deleteAudioInfo(String id) {
		
		this.executeUpdateSQL("delete from audioinfo where id = "+id);
	}

	@Override
	public HashMap getClassRoomMap() {
		
		List list = this.executeSQL(" select book_res_id from class_room  where 1=1  and book_res_id  is not null group by book_res_id");
		HashMap map = new HashMap();
		if(list.size()>0)
		{
			for (int j = 0; j < list.size(); j++) {
				map.put(list.get(j).toString(), list.get(j).toString());
			}
			
		}
		return map;
	}

	@Override
	public AudioInfo getAudioIdInfo(String id) {
		List list = this.executeHQL(" from AudioInfo where audioId ="+id);
		
		AudioInfo audioInfo = new AudioInfo();
		if(list.size()>0)
		{
			audioInfo =(AudioInfo)list.get(0);
		}
		return audioInfo;
	}

	@Override
	public JSONArray getAudioIdByCurriculumId(String curriculumId) {
		// TODO Auto-generated method stub

		
		StringBuffer sql = new StringBuffer();
		sql.append("select b.audioid,b.name,b.mediainfo,b.src,b.cover from curriculumaudio as a,audioinfo as b where a.audioid=b.id and a.curriculumid=:curriculumId");
		
		Query query = this.getQuery(sql.toString());
		query.setString("curriculumId",curriculumId);
		
		Page dataPage = this.pageQueryBySQL(query, 1, 10000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		JSONObject temp = new JSONObject();
		if(dataList.size()>0)
		{
			for(int i=0;i<dataList.size();i++){
				temp.put("id", dataList.get(i)[0]);
				temp.put("name", dataList.get(i)[1]);
				temp.put("mediainfo", dataList.get(i)[2]);
				temp.put("src", dataList.get(i)[3]);
				temp.put("cover", dataList.get(i)[4]);
				result.add(temp);
			}

		}
		return result;
	}

	@Override
	public JSONArray getAudioBindCourseList(String audioId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.id,b.name,b.logo1,b.logo2,b.logo3 from curriculumaudio as a,mallproduct as b where a.curriculumid=b.id and a.audioId=:audioId");
		
		Query query = this.getQuery(sql.toString());
		query.setString("audioId",audioId);
		
		Page dataPage = this.pageQueryBySQL(query, 1, 100);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		JSONObject temp = new JSONObject();
		if(dataList.size()>0)
		{
			for(int i=0;i<dataList.size();i++){
				temp.put("id", dataList.get(i)[0]);
				temp.put("name", dataList.get(i)[1]);
				temp.put("logo1", dataList.get(i)[2]);
				temp.put("logo2", dataList.get(i)[3]);
				temp.put("logo3", dataList.get(i)[3]);
				result.add(temp);
			}

		}
		return result;
	}

	@Override
	public void saveCourseToAudio(CurriculumAudio curriculumAudio) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(curriculumAudio);
	}

	@Override
	public Integer getAudioLength(String audioId) {
		// TODO Auto-generated method stub
		List<Object> objs = this.executeSQL("select audio_length from material_file where audioinfo_id = "+audioId);
		
		Integer result = null;
		if(objs!=null && objs.size()!=0){
			result = (Integer) objs.get(0);
		}
		
		return result;
	}

}
