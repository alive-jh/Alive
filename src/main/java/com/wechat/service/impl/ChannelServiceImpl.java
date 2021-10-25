package com.wechat.service.impl;


import com.wechat.dao.ChannelDao;
import com.wechat.entity.*;
import com.wechat.service.ChannelService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelDao channelDao;

	@Override
	public JSONArray getChannelList(String userId) {
		// TODO Auto-generated method stub
		return channelDao.getChannelList(userId);
	}

	@Override
	public Page getSoundFromChannel(HashMap map) {
		// TODO Auto-generated method stub
		return channelDao.getSoundFromChannel(map);
	}

	@Override
	public void saveChannel(XMLYChannel xmlyChannel) {
		// TODO Auto-generated method stub
		this.channelDao.saveChannel(xmlyChannel);
	}

	@Override
	public void deleteChannel(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.deleteChannel(map);
		
	}

	@Override
	public Page getSoundFromAlbum(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getSoundFromAlbum(map);
	}

	@Override
	public Page searchChannels(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchChannels(map);
	}

	@Override
	public Page searchAlbums(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchAlbums(map);
	}

	@Override
	public Page searchSounds(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchSounds(map);
	}

	@Override
	public Page getAlbumsFromChannel(HashMap map) {
		return this.channelDao.getAlbumsFromChannel(map);
	}

	@Override
	public JSONObject getRecommend(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getRecommend(map);
	}

	@Override
	public void saveDeviceChannel(UserChannel userChannel) {
		// TODO Auto-generated method stub
		this.channelDao.saveDeviceChannel(userChannel);
	}

	@Override
	public void deleteDeviceChannel(String id) {
		// TODO Auto-generated method stub
		this.channelDao.deleteDeviceChannel(id);
	}

	@Override
	public Page searchSoundRecommend(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchSoundRecommend(map);
	}

	@Override
	public void deleteRecommend(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.deleteRecommend(map);
	}

	@Override
	public void switchSoundStatus(String soundId) {
		// TODO Auto-generated method stub
		this.channelDao.switchSoundStatus(soundId);
		
	}
	
	@Override
	public void switchChannelStatus(String id) {
		// TODO Auto-generated method stub
		this.channelDao.switchChannelStatus(id);
	}
	
	@Override
	public void saveRecommendAlbums(HashMap map) {
		// 保存推荐专辑
		this.channelDao.saveRecommendAlbums(map);
	}

	@Override
	public Page searchSoundSearch(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchSoundSearch(map);
	}

	@Override
	public void saveAlbumsToChannel(XMLYChannelAlbum xmlyChannelAlbum) {
		// TODO Auto-generated method stub
		this.channelDao.saveAlbumsToChannel(xmlyChannelAlbum);
	}
	
	@Override
	public void saveSoundToAlbum(XMLYAlbumSound xmlyAlbumSound) {
		// TODO Auto-generated method stub
		this.channelDao.saveSoundToAlbum(xmlyAlbumSound);
	}

	@Override
	public void deleteAlbumFromChannel(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.deleteAlbumFromChannel(map);
	}

	@Override
	public void saveAlbum(XMLYAlbum xmlyAlbum) {
		// TODO Auto-generated method stub
		this.channelDao.saveAlbum(xmlyAlbum);
		
	}

	@Override
	public void deleteSound(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.deleteSound(map);
		
	}

	@Override
	public void saveSound(XMLYSound xmlySound) {
		// TODO Auto-generated method stub
		this.channelDao.saveSound(xmlySound);
	}

	@Override
	public void deleteSoundFromAlbum(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.deleteSoundFromAlbum(map);
	}

	@Override
	public Page soundPlayManage(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.soundPlayManage(map);
	}

	@Override
	public Page getSearchDetail(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getSearchDetail(map);
	}

	@Override
	public void updateCycle(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.updateCycle(map);
	}

	@Override
	public void updateChannelInfo(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.updateChannelInfo(map);
	}

	@Override
	public Page getTagListFromGroup(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getTagListFromGroup(map);
	}

	@Override
	public Page getSoundTagGroupList(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getSoundTagGroupList(map);
	}

	@Override
	public Page searchLabel(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchLabel(map);
	}

	@Override
	public JSONObject getSourceFromTag(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getSourceFromTag(map);
	}

	@Override
	public Page getChannelFromTag(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getChannelFromTag(map);
	}

	@Override
	public Page getAlbumFromTag(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getAlbumFromTag(map);
	}

	@Override
	public Page getSoundFromTag(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getSoundFromTag(map);
	}

	@Override
	public Page searchTag(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.searchTag(map);
	}

	@Override
	public void saveTagToSource(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.saveTagToSource(map);
	}

	@Override
	public Page getAlbumTagFromAlbumId(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getAlbumTagFromAlbumId(map);
		
	}

	@Override
	public void deleteTag(String id) {
		// TODO Auto-generated method stub
		this.channelDao.deleteTag(id);
	}
	

	@Override
	public void saveAlias(HashMap map) {
		this.channelDao.saveAlias(map);
	}

	@Override
	public Page getAliasList(HashMap map) {
		// 获取别名列表
		return this.channelDao.getAliasList(map);
	}

	@Override
	public Page getSoundFromAlbumByZhuboAlbumAlias(HashMap map) {
		// 通过主播和专辑名字获取专辑下音频列表
		return this.channelDao.getSoundFromAlbumByZhuboAlbumAlias(map);
	}

	@Override
	public JSONObject getRecommendSound(HashMap map) {
		// TODO Auto-generated method stub
		return this.channelDao.getRecommendSound(map);
	}
	
	@Override
	public void deleteSourceFromTag(HashMap map) {
		// TODO Auto-generated method stub
		this.channelDao.deleteSourceFromTag(map);
	}

}
