package com.wechat.service;


import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;

public interface ChannelService {
	JSONArray getChannelList(String userId);

	Page getSoundFromChannel(HashMap map);

	//保存
	void saveChannel(XMLYChannel xmlyChannel);

	void saveAlbum(XMLYAlbum xmlyAlbum);

	void saveSound(XMLYSound xmlySound);
	
	//删除
	void deleteChannel(HashMap map);

	Page getSoundFromAlbum(HashMap map);

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


	Page searchSoundSearch(HashMap map);

	void switchChannelStatus(String id);

	//对应关系操作
	void saveAlbumsToChannel(XMLYChannelAlbum xmlyChannelAlbum);

	void saveSoundToAlbum(XMLYAlbumSound xmlyAlbumSound);
	
	void deleteAlbumFromChannel(HashMap map);


	void deleteSound(HashMap map);

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

	Page getSoundFromAlbumByZhuboAlbumAlias(HashMap map);

	JSONObject getRecommendSound(HashMap map);

	void deleteSourceFromTag(HashMap map);

}
