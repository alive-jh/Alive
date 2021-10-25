package com.wechat.service;


import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public interface SoundService {
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

	Map<String, Object> fuzzySearchByName(String soundName, int page, int perPage);

	Map<String, Object>  searchByISBN(String ISBN,String userId,String name,int page,int size);

	Map<String, Object> fuzzySearchChannels(int page, int pageSize, String name);

	Map<String, Object> fuzzySearchAlbums(Integer page, Integer pageSize, String name);

	Map<String, Object> SearchByAlbumId(Integer page, Integer pageSize, Integer albumId);

	Map<String, Object> getAlbumsByCollectionId(Integer page, Integer pageSize,
												Integer channelId);
}
