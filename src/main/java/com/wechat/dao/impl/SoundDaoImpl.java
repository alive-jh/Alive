package com.wechat.dao.impl;


import com.wechat.dao.SoundDao;
import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Repository
public class SoundDaoImpl extends BaseDaoImpl implements SoundDao {

	@Override
	public void saveCollection(SoundCollection soundCollection) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(soundCollection);

	}

	@Override
	public void saveSoundTop(SoundBest soundBest) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(soundBest);
	}

	@Override
	public JSONArray searchSound(HashMap map) {
		// TODO Auto-generated method stub

		JSONArray result = new JSONArray();

		ArrayList ids = new ArrayList();
		if(null != map.get("ISBN")
				&& !"".equals(map.get("ISBN").toString())){
			StringBuffer sql=new StringBuffer("select * from sound_best where 1=1 and isbn=:ISBN order by id DESC");
			Query query = this.getQuery(sql.toString());
			query.setString("ISBN", map.get("ISBN").toString());
			Page dataPage = this.pageQueryBySQL(query, 1, 1000);

			ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					JSONObject temp = new JSONObject();
					Object[] obj = dataList.get(i);
					temp.put("play_count", 10000);
					temp.put("id", obj[0]);
					temp.put("sound_id", obj[1]);
					temp.put("image", obj[2]);
					temp.put("soundUrl", obj[3]);
					if(ids.contains(obj[3])){
						continue;
					}
					temp.put("soundName", obj[4]);
					temp.put("serierName", obj[5]);
					int collectionNumber = getCollectionsFromId(map);
					temp.put("collectionNumber",collectionNumber);
					temp.put("soundId", obj[1]);
					temp.put("ISBN", obj[6]);
					temp.put("source", "best");
					temp.put("site", "网络资源");
					ids.add(obj[3]);
					result.add(temp);

				}
			}

		}else{

		}
		if(null != map.get("ISBN")
				&& !"".equals(map.get("ISBN").toString())){
			StringBuffer sql=new StringBuffer("select * from sound_collection where 1=1 and isbn=:ISBN");
			Query query = this.getQuery(sql.toString());
			query.setString("ISBN", map.get("ISBN").toString());
			Page dataPage = this.pageQueryBySQL(query, 1, 1000);

			ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					Object[] obj = dataList.get(i);
					JSONObject temp = new JSONObject();
					temp.put("play_count", 10000);
					temp.put("sound_id", obj[1]);
					temp.put("image", obj[2]);
					temp.put("soundUrl", obj[3]);
					if(ids.contains(obj[3])){
						continue;
					}
					temp.put("soundName", obj[4]);
					temp.put("serierName", obj[5]);
					temp.put("soundId", obj[1]);
					temp.put("source", "collection");
					int collectionNumber = getCollectionsFromId(map);
					temp.put("collectionNumber",collectionNumber);
					temp.put("ISBN", obj[6]);
					temp.put("site", "网络资源");
					ids.add(obj[3]);
					result.add(temp);

				}
			}else{

			}
		}
		return result;
	}

	public int getCollectionsFromId(HashMap map){
		StringBuffer sql=new StringBuffer("select * from sound_collection where 1=1");
		if (null != map.get("soundId")

				&& !"".equals(map.get("soundId").toString())) {
			sql.append(" and sound_id=:soundId");
			return 0;
		}
		Query query = this.getQuery(sql.toString());
		if (null != map.get("soundId")
				&& !"".equals(map.get("soundId").toString())) {
			query.setInteger("soundId", Integer.parseInt(map.get("soundId").toString()));
		}
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		return dataPage.getTotalCount();
	}

	@Override
	public Page searchCollection(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("select * from sound_collection where 1=1 and user_id=:userId");
		Query query = this.getQuery(sql.toString());
		if (null != map.get("userId")
				&& !"".equals(map.get("userId").toString())) {
			query.setString("userId", map.get("userId").toString());
		}
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<SoundCollection> soundCollections = new ArrayList<SoundCollection>();

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);
				SoundCollection soundCollection= new SoundCollection();
				soundCollection.setId((Integer) obj[0]);
				soundCollection.setSoundId((String) obj[1]);
				soundCollection.setImage((String) obj[2]);
				soundCollection.setSoundUrl((String) obj[3]);
				soundCollection.setSoundName((String) obj[4]);
				soundCollection.setSerierName((String) obj[5]);
				soundCollection.setISBN((String) obj[6]);
				soundCollection.setUserId((String) obj[7]);
				soundCollection.setInsertDate((Date) obj[8]);
				soundCollections.add(soundCollection);
			}

		}

		Page resultPage = new Page<SoundCollection>(soundCollections, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void deleteCollection(HashMap map) {

		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from SoundCollection where userId='" + map.get("userId") + "' and soundId='" + map.get("soundId") + "'");
		this.executeHQL(sql.toString());

	}

	@Override
	public void CancelTop(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from SoundBest where id=" + id);
		this.executeHQL(sql.toString());

	}

	@Override
	public Page searchDailyRecommentDateList(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("select * from daily_recommend_date order by dateName desc");
		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

		ArrayList<HashMap> dates = new  ArrayList<HashMap>();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);
				HashMap date=new HashMap();
				date.put("id", obj[0]);
				date.put("dateName", obj[1]);
				date.put("intro", obj[2]);
				date.put("createTime", obj[3]);
				dates.add(date);
			}
		}

		Page resultPage = new Page<HashMap>(dates, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public Page getTagListFromDateId(HashMap map) {
		StringBuffer sql=new StringBuffer("select id,tagName,intro,dateId,createTime from daily_recommend_tag where dateId=:dateId order by sortId asc");
		Query query = this.getQuery(sql.toString());
		if(!"".equals(map.get("dateId"))&& null!=map.get("dateId")){
			query.setInteger("dateId", Integer.parseInt(map.get("dateId").toString()));
		}

		Page dataPage = this.pageQueryBySQL(query, 1,1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

		ArrayList<HashMap> dates = new  ArrayList<HashMap>();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);
				HashMap date=new HashMap();
				date.put("id", obj[0]);
				date.put("tagName", obj[1]);
				date.put("intro", obj[2]);
				date.put("dateId", obj[3]);
				date.put("createTime", obj[4]);
				dates.add(date);
			}
		}

		Page resultPage = new Page<HashMap>(dates, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDailyRecommendDate(DailyRecommendDate dailyRecommendDate) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(dailyRecommendDate);
	}

	@Override
	public void deleteDailyDate(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from DailyRecommendDate where id = "
				+ id);
		this.executeHQL(sql.toString());
	}

	@Override
	public void saveDailyRecommendTag(DailyRecommendTag dailyRecommendTag) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(dailyRecommendTag);
	}

	@Override
	public void deleteTagFromDate(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from DailyRecommendTag where id = "
				+ id);
		this.executeHQL(sql.toString());
	}

	//通过分类ID获取资源
	@Override
	public Page getSourceFromTagId(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT" +
				" b.id,"+
				"b.name soundName," +
				"b.albumName," +
				"e.name channelName," +
				"b.image," +
				"b.intro," +
				"b.playUrl," +
				"c.albumId," +
				"a.id indexId," +
				"c.soundId," +
				"d.channelId," +
				"IFNULL((select aliasName from sound_alias where sound_alias.soundId=c.soundId),b.name) soundAliasName," +
				"IFNULL((select aliasName from album_alias where album_alias.albumId=c.albumId),b.albumName) albumAliasName," +
				"IFNULL((select aliasName from zhubo_alias where zhubo_alias.zhuboId=d.channelId),e.name) zhuboAliasName" +
				" FROM" +
				" daily_recommend_source AS a," +
				"xmly_sound AS b," +
				"xmly_album_sound AS c," +
				"xmly_channel_album AS d," +
				"xmly_channel AS e" +
				" WHERE" +
				" b.`status` = 1 and a.sourceId = b.id" +
				" AND a.sourceId = c.soundId" +
				" AND c.albumId = d.albumId" +
				" AND d.channelId = e.id" +
				" AND e.channel_id > 1" +
				" AND a.tagId =:tagId" +
				" ORDER BY" +
				" a.sortId DESC");
		Query query = this.getQuery(sql.toString());
		if(!"".equals(map.get("tagId"))&& null!=map.get("tagId")){
			query.setInteger("tagId", Integer.parseInt(map.get("tagId").toString()));
		}

		Page dataPage = this.pageQueryBySQL(query, 1,1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

		ArrayList<HashMap> dates = new  ArrayList<HashMap>();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);
				HashMap date=new HashMap();
				date.put("soundId", obj[0]);
				date.put("soundName", obj[1]);
				date.put("albumName", obj[2]);
				date.put("channelName", obj[3]);
				date.put("image", obj[4]);
				date.put("intro", obj[5]);
				date.put("playUrl", obj[6]);
				date.put("albumId", obj[7]);
				date.put("id", obj[8]);
				date.put("soundId", obj[9]);
				date.put("channelId", obj[10]);

				date.put("soundAliasName", obj[11]);
				date.put("albumAliasName", obj[12]);
				date.put("zhuboAliasName",obj[13]);
				dates.add(date);
			}
		}

		Page resultPage = new Page<HashMap>(dates, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveSoundToTag(DailyRecommendSource dailyRecommendSource) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(dailyRecommendSource);
	}

	@Override
	public void deleteSoundFromTag(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from DailyRecommendSource where id = "
				+ id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page getDailyRecommendSoundList(String dateId) {
		StringBuffer sql=new StringBuffer("select " +
				"a.intro as dateIntro," +
				"b.tagName," +
				"b.intro as tagIntro," +
				"d.id," +
				"d.name," +
				"d.image," +
				"d.intro soundIntro," +
				"d.playUrl," +
				"d.albumName," +
				"e.albumId," +
				"f.channelId," +
				"IFNULL((select aliasName from sound_alias where sound_alias.soundId=e.soundId),'') soundAliasName," +
				"IFNULL((select aliasName from album_alias where album_alias.albumId=e.albumId),'') albumAliasName," +
				"IFNULL((select aliasName from zhubo_alias where zhubo_alias.zhuboId=f.channelId),'') zhuboAliasName " +
				"from " +
				"daily_recommend_date as a," +
				"daily_recommend_tag as b," +
				"daily_recommend_source as c," +
				"xmly_sound as d," +
				"xmly_album_sound as e," +
				"xmly_channel_album as f " +
				"where d.status = 1 and " +
				"a.id=b.dateId " +
				"and b.id=c.tagId " +
				"and c.sourceId=d.id " +
				"and d.id=e.soundId " +
				"and e.soundId=d.id " +
				"and e.albumId=f.albumId " +
				"and a.dateName=:dateId");
		sql.append(" order by b.sortId asc");
		Query query = this.getQuery(sql.toString());

		query.setString("dateId", dateId);

		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<HashMap> sourceList = new  ArrayList<HashMap>();

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);
				HashMap source=new HashMap();
				source.put("dateIntro", obj[0]);
				source.put("tagName", obj[1]);
				source.put("tagIntro", obj[2]);
				source.put("soundId", obj[3]);
				source.put("soundName", obj[4]);
				source.put("soundImage", obj[5]);
				source.put("soundIntro", obj[6]);
				source.put("playUrl", obj[7]);
				source.put("albumName", obj[8]);
				source.put("albumId", obj[9]);
				source.put("channelId", obj[10]);
				source.put("soundAlias", obj[11]);
				source.put("albumAlias", obj[12]);
				source.put("channelAlias", obj[13]);
				sourceList.add(source);
			}

		}else{//如果当天没有数据，随机取一天的数据
			sql=new StringBuffer("select " +
					"a.intro as dateIntro," +
					"b.tagName," +
					"b.intro as tagIntro," +
					"d.id," +
					"d.name," +
					"d.image," +
					"d.intro soundIntro," +
					"d.playUrl," +
					"d.albumName," +
					"e.albumId," +
					"f.channelId," +
					"IFNULL((select aliasName from sound_alias where sound_alias.soundId=e.soundId),'') soundAliasName," +
					"IFNULL((select aliasName from album_alias where album_alias.albumId=e.albumId),'') albumAliasName," +
					"IFNULL((select aliasName from zhubo_alias where zhubo_alias.zhuboId=f.channelId),'') zhuboAliasName " +
					"from " +
					"daily_recommend_date as a," +
					"daily_recommend_tag as b," +
					"daily_recommend_source as c," +
					"xmly_sound as d," +
					"xmly_album_sound as e," +
					"xmly_channel_album as f " +
					"where " +
					"d.`status` = 1 and a.id=b.dateId " +
					"and b.id=c.tagId " +
					"and c.sourceId=d.id " +
					"and d.id=e.soundId " +
					"and e.soundId=d.id " +
					"and e.albumId=f.albumId " +
					"and a.dateName=(SELECT dateName FROM daily_recommend_date ORDER BY RAND() LIMIT 1)");
			sql.append(" order by b.sortId asc");
			query = this.getQuery(sql.toString());
			dataPage = this.pageQueryBySQL(query, 1, 1000);
			sourceList = new  ArrayList<HashMap>();

			dataList = (ArrayList<Object[]>) dataPage.getItems();
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);
				HashMap<String,Object> source=new HashMap<>();
				source.put("dateIntro", obj[0]);
				source.put("tagName", obj[1]);
				source.put("tagIntro", obj[2]);
				source.put("soundId", obj[3]);
				source.put("soundName", obj[4]);
				source.put("soundImage", obj[5]);
				source.put("soundIntro", obj[6]);
				source.put("playUrl", obj[7]);
				source.put("albumName", obj[8]);
				source.put("albumId", obj[9]);
				source.put("channelId", obj[10]);
				source.put("soundAlias", obj[11]);
				source.put("albumAlias", obj[12]);
				source.put("channelAlias", obj[13]);
				sourceList.add(source);
			}
			
		}

		Page resultPage = new Page<HashMap>(sourceList, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void updateDailyRecommendTagSort(String id, String sortId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update daily_recommend_tag set sortId=" + sortId +" where id=" + id);
		this.executeUpdateSQL(sql.toString());
	}

	@Override
	public void deleteDailyRecommendSoundFromTag(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from daily_recommend_source where id=" + id);
		this.executeSQL(sql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Object[]> fuzzySearchByName(String soundName, int page, int perPage) {

		String sql = "SELECT s.playTimes, s.soundId, s.image, s.name, s.albumName, s.playUrl FROM xmly_sound s WHERE s.`status` = 1 and  id < 700000 and s.name LIKE '%"
				+soundName+"%' ";
		return this.pageQueryBySQL(sql,page,20);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<XMLYSound> SearchByAlbumId(Integer albumId, Integer pageSize,
										   Integer page) {
		StringBuffer hql = new StringBuffer("from XMLYSound s where s.albumId ="+albumId  + " and status > 0");

		return (Page<XMLYSound>)this.pageQueryByHql(hql.toString(),
				page, pageSize);
	}



}
