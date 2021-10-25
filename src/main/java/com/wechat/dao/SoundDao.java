package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;

import java.util.HashMap;

public interface SoundDao {

	JSONArray searchSound(HashMap map);

	void saveCollection(SoundCollection soundCollection);

	void saveSoundTop(SoundBest soundBest);

	Page searchCollection(HashMap map);

	void deleteCollection(HashMap map);

	void CancelTop(String id);

	Page searchDailyRecommentDateList(HashMap map);

	Page getTagListFromDateId(HashMap map);

	void saveDailyRecommendDate(DailyRecommendDate dailyRecommendDate);

	void deleteDailyDate(String id);

	void saveDailyRecommendTag(DailyRecommendTag dailyRecommendTag);

	void deleteTagFromDate(String id);

	Page getSourceFromTagId(HashMap map);

	void saveSoundToTag(DailyRecommendSource dailyRecommendSource);

	void deleteSoundFromTag(String id);

	Page getDailyRecommendSoundList(String dateId);

	void updateDailyRecommendTagSort(String id, String sortId);

	void deleteDailyRecommendSoundFromTag(String id);

	Page<Object[]> fuzzySearchByName(String soundName, int page, int perPage);

	Page<XMLYSound> SearchByAlbumId(Integer albumId, Integer pageSize,
									Integer page);



}
