package com.wechat.dao.impl;

import com.wechat.dao.CourseWordDao;
import com.wechat.entity.CourseWordLabel;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class CourseWordDaoImpl extends BaseDaoImpl implements CourseWordDao {

	@Override
	public Page getWordGroupList(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("select id,label_en,label_cn,ifnull(image,\"\"),group_name,status,ifnull(sound,\"\") from course_word_label where status=1 and group_name='" + map.get("type").toString() + "'");
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<CourseWordLabel> wordGroupList = new ArrayList<CourseWordLabel>();
		
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				CourseWordLabel courseWordLabel= new CourseWordLabel();
				courseWordLabel.setId((Integer) obj[0]);
				courseWordLabel.setLabelEN((String) obj[1]);
				courseWordLabel.setLabelCN((String) obj[2]);
				courseWordLabel.setGroup((String) obj[4]);
				courseWordLabel.setImage((String) obj[3]);
				courseWordLabel.setSound((String) obj[6]);
				wordGroupList.add(courseWordLabel);
			}

		}
		Page resultPage = new Page<CourseWordLabel>(wordGroupList, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public JSONArray getWordSourceList(HashMap map) {
		// TODO Auto-generated method stub
		JSONArray data = new JSONArray();
		JSONObject result = new JSONObject();
		JSONArray groupList = new JSONArray();
		JSONArray wordList = new JSONArray();
		JSONObject temp = new JSONObject();
		
		
		JSONObject wordsSource = new JSONObject();
		int groupId = Integer.parseInt(map.get("groupId").toString());
		StringBuffer sql=new StringBuffer("select a.id,a.word_txt," +
				"a.word_audio,a.word_pic,a.audioexplain,a.audiorepeat,a.status,a.txtexplain,a.createtime,ifnull(a.sound,c.sound) from course_word as a,course_word_to_label as b,course_word_label as c where a.id=b.word_id and b.label_id=c.id and a.status=1 and b.label_id=" + map.get("groupId"));
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				JSONArray fileList = new JSONArray(); 
				Object[] obj = dataList.get(i);
				wordsSource.put("playUrl", obj[2]);
				wordsSource.put("audioName", "原音");
				fileList.add(wordsSource);
				
				wordsSource.put("playUrl", obj[4]);
				wordsSource.put("audioName", "解释");
				fileList.add(wordsSource);
				
				wordsSource.put("playUrl", obj[5]);
				wordsSource.put("audioName", "拼写");
				fileList.add(wordsSource);
				
				wordsSource.put("playUrl", obj[9]);
				wordsSource.put("audioName", "音效");
				fileList.add(wordsSource);
				
				JSONObject words = new JSONObject();
				String wordName = (String)obj[1];
				wordName = wordName.replaceAll("\\d+","");
				words.put("word", wordName);
				
				words.put("chinese", obj[7]);
				
				int id = (Integer)obj[0];
				words.put("id",id);

				words.put("image", obj[3]);
				words.put("fileList",fileList);
				words.put("labelList", getLabelList(id));
				wordList.add(words);
			}
		}
		CourseWordLabel courseWordLabel = (CourseWordLabel) this.executeHQL("from CourseWordLabel where id= " +groupId).get(0);
		temp.put("key", courseWordLabel.getLabelCN());
		temp.put("groupId", groupId);
		temp.put("wordList", wordList);
		groupList.add(temp);
		result.put("type",courseWordLabel.getGroup());
		result.put("groupList",groupList);
		data.add(result);
		return data;
	}
	public CourseWordLabel getGroupName(int GroupId) {
		CourseWordLabel courseWordLabel = (CourseWordLabel) this.executeHQL("from CourseWordLabel where id= " +GroupId).get(0);
		return courseWordLabel;	
	}
	
	public JSONArray getLabelList(int wordId){
		JSONArray tabelList = new JSONArray();
		JSONObject tabel = new JSONObject();
		StringBuffer sql=new StringBuffer("select a.id,a.label_en,a.label_cn,a.image,a.group_name" +
				" from course_word_label as a,course_word_to_label as b where a.id=b.label_id and b.word_id=" +wordId);
//		//System.out.println(sql);
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {
			
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				tabel.put("id", obj[0]);
				tabel.put("labelEn", obj[1]);
				tabel.put("labelCn", obj[2]);
				tabel.put("image", obj[3]);
				tabel.put("groupName", obj[4]);
				tabelList.add(tabel);
				}
			}
		return tabelList;
		
	}

	@Override
	public JSONArray searchWords(HashMap map) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JSONArray data = new JSONArray();
		JSONObject result = new JSONObject();
		JSONArray groupList = new JSONArray();
		JSONArray wordList = new JSONArray();
		JSONObject temp = new JSONObject();
		JSONObject wordsSource = new JSONObject();
		
		StringBuffer sql=new StringBuffer("select a.id,a.word_txt," +
				"a.word_audio,a.word_pic,a.audioexplain,a.audiorepeat,a.status,a.txtexplain,a.createtime,ifnull(a.sound,\"\") from course_word as a where word_txt like :key");
		Query query = this.getQuery(sql.toString());
		query.setString("key", "%"+map.get("key").toString()+"%");  
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				JSONArray fileList = new JSONArray(); 
				Object[] obj = dataList.get(i);
				wordsSource.put("playUrl", obj[2]);
				wordsSource.put("audioName", "原音");
				fileList.add(wordsSource);
				
				wordsSource.put("playUrl", obj[4]);
				wordsSource.put("audioName", "解释");
				fileList.add(wordsSource);
				
				wordsSource.put("playUrl", obj[5]);
				wordsSource.put("audioName", "拼写");
				fileList.add(wordsSource);
				
				wordsSource.put("playUrl", obj[9]);
				wordsSource.put("audioName", "音效");
				fileList.add(wordsSource);
				
				JSONObject words = new JSONObject();
				String wordName = (String)obj[1];
				wordName = wordName.replaceAll("\\d+","");
				words.put("word", wordName);
				
				words.put("chinese", obj[7]);
				
				int id = (Integer)obj[0];
				words.put("id",id);

				words.put("image", obj[3]);
				words.put("fileList",fileList);
				words.put("labelList", getLabelList(id));
				wordList.add(words);
			}
		}

		temp.put("key", map.get("key"));
		temp.put("groupId", "");
		temp.put("wordList", wordList);
		groupList.add(temp);
		result.put("groupList",groupList);
		data.add(result);
		return data;
	}
}
