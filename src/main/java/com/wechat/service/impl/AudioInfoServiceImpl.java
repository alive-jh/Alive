package com.wechat.service.impl;

import com.wechat.dao.AudioInfoDao;
import com.wechat.entity.AudioInfo;
import com.wechat.entity.CurriculumAudio;
import com.wechat.service.AudioInfoService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("audioInfoService")
public class AudioInfoServiceImpl implements AudioInfoService {

	@Resource
	private AudioInfoDao audioInfoDao;
	
	
	public AudioInfoDao getAudioInfoDao() {
		return audioInfoDao;
	}

	public void setAudioInfoDao(AudioInfoDao audioInfoDao) {
		this.audioInfoDao = audioInfoDao;
	}

	@Override
	public void saveAudioInfo(AudioInfo audioInfo) {
		
		this.audioInfoDao.saveAudioInfo(audioInfo);
	}

	@Override
	public Page searchAudioInfo(HashMap map) {
		
		return this.audioInfoDao.searchAudioInfo(map);
	}

	@Override
	public void deleteAudioInfo(String id) {
		
		this.audioInfoDao.deleteAudioInfo(id);
	}

	@Override
	public HashMap getClassRoomMap()  {
		
		return this.audioInfoDao.getClassRoomMap();
	}

	@Override
	public AudioInfo getAudioIdInfo(String id) {
		
		return this.audioInfoDao.getAudioIdInfo(id);
	}

	@Override
	public JSONArray getAudioIdByCurriculumId(String curriculumId) {
		// TODO Auto-generated method stub
		return this.audioInfoDao.getAudioIdByCurriculumId(curriculumId);
	}

	@Override
	public JSONArray getAudioBindCourseList(String audioId) {
		// TODO Auto-generated method stub
		return this.audioInfoDao.getAudioBindCourseList(audioId);
	}

	@Override
	public void saveCourseToAudio(CurriculumAudio curriculumAudio) {
		// TODO Auto-generated method stub
		this.audioInfoDao.saveCourseToAudio(curriculumAudio);
		
	}

	@Override
	public Integer getAudioLength(String audioId) {
		// TODO Auto-generated method stub
		return audioInfoDao.getAudioLength(audioId);
	}


}
