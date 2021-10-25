package com.wechat.service.impl;

import com.jfinal.plugin.activerecord.Record;
import com.wechat.dao.ChannelDao;
import com.wechat.dao.SoundAlbumsDao;
import com.wechat.dao.SoundCollectionDao;
import com.wechat.dao.SoundDao;
import com.wechat.entity.*;
import com.wechat.jfinal.common.ElasticsearchUtils;
import com.wechat.jfinal.model.XmlySound;
import com.wechat.service.SoundService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class SoundServiceImpl implements SoundService {

	@Resource
	private SoundDao soundDao;

	@Resource
	private SoundCollectionDao soundCollectionDao;

	@Resource
	private ChannelDao channelDao;

	@Resource
	private SoundAlbumsDao albumsDao;

	@Override
	public JSONArray searchSound(HashMap map) {
		// TODO Auto-generated method stub

		return soundDao.searchSound(map);
	}

	@Override
	public void saveCollection(SoundCollection soundCollection) {
		// TODO Auto-generated method stub
		this.soundDao.saveCollection(soundCollection);

	}

	@Override
	public void saveSoundTop(SoundBest soundBest) {
		// TODO Auto-generated method stub
		this.soundDao.saveSoundTop(soundBest);
	}

	@Override
	public Page searchCollection(HashMap map) {
		// TODO Auto-generated method stub
		return soundDao.searchCollection(map);
	}

	@Override
	public void deleteCollection(HashMap map) {
		// TODO Auto-generated method stub
		this.soundDao.deleteCollection(map);

	}

	@Override
	public void CancelTop(String id) {
		// TODO Auto-generated method stub
		this.soundDao.CancelTop(id);
	}

	@Override
	public Page searchDailyRecommentDateList(HashMap map) {
		// TODO Auto-generated method stub
		return this.soundDao.searchDailyRecommentDateList(map);
	}

	@Override
	public Page getTagListFromDateId(HashMap map) {
		// TODO Auto-generated method stub
		return this.soundDao.getTagListFromDateId(map);
	}

	@Override
	public void saveDailyRecommendDate(DailyRecommendDate dailyRecommendDate) {
		// TODO Auto-generated method stub
		this.soundDao.saveDailyRecommendDate(dailyRecommendDate);

	}

	@Override
	public void deleteDailyDate(String id) {
		// TODO Auto-generated method stub
		this.soundDao.deleteDailyDate(id);

	}

	@Override
	public void saveDailyRecommendTag(DailyRecommendTag dailyRecommendTag) {
		// TODO Auto-generated method stub
		this.soundDao.saveDailyRecommendTag(dailyRecommendTag);
	}

	@Override
	public void deleteTagFromDate(String id) {
		// TODO Auto-generated method stub
		this.soundDao.deleteTagFromDate(id);
	}

	@Override
	public Page getSourceFromTagId(HashMap map) {
		// TODO Auto-generated method stub
		return this.soundDao.getSourceFromTagId(map);
	}

	@Override
	public void saveSoundToTag(DailyRecommendSource dailyRecommendSource) {
		// TODO Auto-generated method stub
		this.soundDao.saveSoundToTag(dailyRecommendSource);

	}

	@Override
	public void deleteSoundFromTag(String id) {
		// TODO Auto-generated method stub
		this.soundDao.deleteSoundFromTag(id);
	}

	@Override
	public Page getDailyRecommendSoundList(String dateId) {
		// TODO Auto-generated method stub
		return this.soundDao.getDailyRecommendSoundList(dateId);
	}

	@Override
	public void updateDailyRecommendTagSort(String id, String sortId) {
		// TODO Auto-generated method stub
		this.soundDao.updateDailyRecommendTagSort(id,sortId);

	}

	@Override
	public void deleteDailyRecommendSoundFromTag(String id) {
		// TODO Auto-generated method stub
		this.soundDao.deleteDailyRecommendSoundFromTag(id);
	}

	@Override
	public Map<String, Object> fuzzySearchByName(String soundName, int page, int pageSize) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();

		com.jfinal.plugin.activerecord.Page<Record> esPage = ElasticsearchUtils.getInstance().fuzzy("sound","sound","name",soundName,page,pageSize);
		List<Record> data = esPage.getList();
		Set<String> ids = new HashSet();
		for (Record record : data) {
	/*		Map<String, Object> temp = new HashMap<>();
			if(record.getInt("playTimes") == 0){
				Random random = new Random();
				int r = random.nextInt(9999)%(9000) + 1000;
				temp.put("play_count", r );
			}else{
				temp.put("play_count", record.getInt("playTimes") );
			}

			//temp.put("id", record.getInt("id"));
			temp.put("soundId", record.getInt("soundId"));
			temp.put("image", record.getStr("image"));
			temp.put("soundUrl", record.getStr("playUrl"));
			temp.put("soundName", record.getStr("name"));

			temp.put("serierName",  record.getStr("albumName"));
			temp.put("collectionNumber", 0);
			temp.put("ISBN", "");
			temp.put("source", "best");
			temp.put("site", "网络资源");
			list.add(temp);*/
			ids.add(record.getInt("id").toString());
		}

		List<XmlySound> xmlySounds = XmlySound.dao.findStatusById(ids);

		for (XmlySound xmlySound : xmlySounds) {
			Map<String, Object> temp = new HashMap<>();
			if(xmlySound.getPlayTimes() == 0){
				Random random = new Random();
				int r = random.nextInt(9999)%(9000) + 1000;
				temp.put("play_count", r );
			}else{
				temp.put("play_count", xmlySound.getPlayTimes() );
			}
			temp.put("id", xmlySound.getId());
			temp.put("soundId", xmlySound.getSoundId());
			temp.put("image", xmlySound.getImage());
			temp.put("soundUrl", xmlySound.getPlayUrl());
			temp.put("soundName", xmlySound.getName());

			temp.put("serierName",  xmlySound.getAlbumName());
			temp.put("collectionNumber", 0);
			temp.put("ISBN", "");
			temp.put("source", "best");
			temp.put("site", "网络资源");
			list.add(temp);
		}


		result.put("data", list);
		result.put("currentPage",page);
		result.put("totalPage",esPage.getTotalPage());
		result.put("nextPage", page<esPage.getTotalPage());

		return result;
	}

	@Override
	public Map<String, Object>  searchByISBN(String ISBN,String userId,String name,int page,int size) {

		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();

		Page<Object[] > data = this.soundCollectionDao.getByISBN(ISBN, userId,name,page,size);

		for(Object[] s : data.getItems() ){
			Map<String, Object> temp = new HashMap<>();
			Random random = new Random();
			int r = random.nextInt(9999)%(9000) + 1000;
			temp.put("play_count", r );
			temp.put("id", s[1]);
			temp.put("soundId", s[1]);
			temp.put("image", s[2]);
			temp.put("soundUrl", s[3]);
			temp.put("soundName", s[4]);

			temp.put("serierName", s[5]);
			temp.put("collectionNumber", 0);
			temp.put("ISBN", s[6]);
			temp.put("source", "best");
			temp.put("site", "网络资源");
			list.add(temp);
		}
		result.put("data", list);
		result.put("currentPage",data.getStartIndex());
		result.put("totalPage",data.getTotalCount());
		result.put("nextPage", page >= data.getTotalPageCount() ? 0 :1);


		return result;
	}

	private SoundCollection getCollectionsFromId(int soundId){
		if (soundId > 0) {
			return soundCollectionDao.getBySoundId(soundId);
		}
		return null;
	}

	@Override
	public Map<String, Object> fuzzySearchChannels(int page, int pageSize,
												   String name) {
		List<XMLYChannel> list = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
//		Page<XMLYChannel> data = channelDao.fuzzySearchByName(page,pageSize,name);
		com.jfinal.plugin.activerecord.Page<Record> esPage = ElasticsearchUtils.getInstance().fuzzy("channel","channel","name",name,page,pageSize);
		List<Record> data = esPage.getList();
		for (Record record : data) {
			XMLYChannel temp = new XMLYChannel();
			temp.setId(record.getInt("channel_id"));
			temp.setChannelId(record.getInt("channel_id"));
			temp.setFans(record.getInt("fans"));
			temp.setImage(record.getStr("image"));
			temp.setIntro(record.getStr("intro"));
			temp.setLastUpdateTime(record.getStr("lastUpdateTime"));
			temp.setLevel(record.getInt("level"));
			temp.setName(record.getStr("name"));
			temp.setNextUpdateTime(record.getStr("nextUpdateTime"));
			temp.setStatus(record.getInt("status"));
			temp.setUpdateCycle(record.getInt("updateCycle"));
			list.add(temp);
		}
		result.put("sourceList", list);
		result.put("page", page);
		result.put("pageSize", pageSize);
		result.put("total", esPage.getTotalRow());
		result.put("nextPage", page >= esPage.getTotalPage() ? 0 :1);
		return result;
	}

	@Override
	public Map<String, Object> fuzzySearchAlbums(Integer page,
												 Integer pageSize, String name) {
		Map<String, Object> result = new HashMap<>();
		List<XMLYAlbum> list = new ArrayList<>();

		com.jfinal.plugin.activerecord.Page<Record> esPage = ElasticsearchUtils.getInstance().fuzzy("album","album","name",name,page,pageSize);
		List<Record> data = esPage.getList();

		for (Record record : data) {
			XMLYAlbum t = new XMLYAlbum();
			t.setId(record.getInt("album_id"));
			t.setAlbumId(record.getInt("album_id"));
			t.setChannelId(record.getInt("channel_id"));
			t.setImage(record.getStr("image"));
			t.setIntro(record.getStr("intro"));
			t.setName(record.getStr("name"));
			t.setSortId(record.getInt("sort_id"));
			t.setStatus(record.getInt("status"));
			list.add(t);
		}

		result.put("success", 1);
		result.put("sourceList", list);
		result.put("page", page);
		result.put("pageSize", pageSize);
		result.put("total", esPage.getTotalRow());
		result.put("nextPage", page >= esPage.getTotalPage() ? 0 :1);
		return result;
	}

	@Override
	public Map<String, Object> SearchByAlbumId(Integer page, Integer pageSize,
											   Integer albumId) {
		Map<String, Object> result = new HashMap<>();

		com.jfinal.plugin.activerecord.Page<Record> esPage = ElasticsearchUtils.getInstance().equal("sound","sound", "albumId",albumId.toString(),page,pageSize);
		List<Record> data = esPage.getList();


//		Page<XMLYSound> data = soundDao.SearchByAlbumId(albumId,pageSize,page);
		List<HashMap<String, Object>> list = new ArrayList<>();

		for (Record record : data) {


			HashMap<String, Object> temp = new HashMap<>();
			temp.put("id", record.getStr("soundId"));
			temp.put("coverUrl", record.getStr("image"));
			temp.put("palyPath", record.getStr("playUrl"));
			temp.put("SoundName", record.getStr("name"));

			if(record.getInt("playTimes") == 0){
				Random random = new Random();
				int r = random.nextInt(9999)%(9000) + 1000;
				temp.put("playCount", r);
			}else{
				temp.put("playCount", record.getInt("playTimes"));
			}
			list.add(temp);
		}
		result.put("success", 1);
		result.put("sourceList", list);
		result.put("page", page);
		result.put("pageSize", pageSize);
		result.put("total", esPage.getTotalPage());
		result.put("nextPage", page<esPage.getTotalPage() ? 0 :1);
		return result;
	}

	@Override
	public Map<String, Object> getAlbumsByCollectionId(Integer page,
													   Integer pageSize, Integer channelId) {
		Map<String, Object> result = new HashMap<>();
		List<HashMap<String, Object>> list = new ArrayList<>();
		com.jfinal.plugin.activerecord.Page<Record> esPage = ElasticsearchUtils.getInstance().equal("album","album","channel_id",channelId.toString(),page,pageSize);
		List<Record> data = esPage.getList();
		for (Record record : data) {
			HashMap<String, Object> t = new HashMap<>();
			t.put("id", record.getInt("album_id"));
			t.put("image", record.getStr("image"));
			t.put("intro", record.getStr("intro"));
			t.put("name", record.getStr("name"));
			t.put("type", 0);
			list.add(t);
		}

		result.put("success", 1);
		result.put("sourceList", list);
		result.put("page", page);
		result.put("pageSize", pageSize);
		result.put("total", esPage.getTotalPage());
		result.put("nextPage", page >= esPage.getTotalPage()? 0 :1);
		return result;
	}

}
