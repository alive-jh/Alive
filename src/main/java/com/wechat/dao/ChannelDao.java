package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;

public interface ChannelDao {

	JSONArray getChannelList(String userId);

	Page getSoundFromChannel(HashMap map);

	Page getSoundFromAlbum(HashMap map);
	
	void saveChannel(XMLYChannel xmlyChannel);
	
	void saveAlbum(XMLYAlbum xmlyAlbum);
	
	
	void saveSound(XMLYSound xmlySound);

	
	void deleteChannel(HashMap map);

	void deleteAlbum(HashMap map);
	
	void deleteSound(HashMap map);	
	

	Page searchChannels(HashMap map);

	Page searchAlbums(HashMap map);

	Page searchSounds(HashMap map);

	Page getAlbumsFromChannel(HashMap map);

	JSONObject getRecommend(HashMap map);

	void saveDeviceChannel(UserChannel userChannel);

	void deleteDeviceChannel(String id);

	Page searchSoundRecommend(HashMap map);

	void deleteRecommend(HashMap map);

	void switchSoundStatus(String soundId);

	void saveRecommendAlbums(HashMap map);

	void updateAlbumById(XMLYAlbum xmlyAlbum);

	Page searchSoundSearch(HashMap map);

	void switchChannelStatus(String id);

	void saveAlbumsToChannel(XMLYChannelAlbum xmlyChannelAlbum);

	void deleteAlbumFromChannel(HashMap map);

	void saveSoundToAlbum(XMLYAlbumSound xmlyAlbumSound);

	void deleteSoundFromAlbum(HashMap map);

	Page soundPlayManage(HashMap map);

	Page getSearchDetail(HashMap map);

	void updateCycle(HashMap map);

	void updateChannelInfo(HashMap map);

	Page getTagListFromGroup(HashMap map);

	Page getSoundTagGroupList(HashMap map);

	Page searchLabel(HashMap map);

	JSONObject getSourceFromTag(HashMap map);

	Page getChannelFromTag(HashMap map);

	Page getAlbumFromTag(HashMap map);

	Page getSoundFromTag(HashMap map);

	Page searchTag(HashMap map);

	void saveTagToSource(HashMap map);

	Page getAlbumTagFromAlbumId(HashMap map);

	void deleteTag(String id);

	void saveAlias(HashMap map);

	Page getAliasList(HashMap map);

	// 通过主播和专辑名字获取专辑下音频列表
	Page getSoundFromAlbumByZhuboAlbumAlias(HashMap map);

	JSONObject getRecommendSound(HashMap map);

	Page<XMLYChannel> fuzzySearchByName(int page, int pageSize, String name);

	void deleteSourceFromTag(HashMap map);

}
