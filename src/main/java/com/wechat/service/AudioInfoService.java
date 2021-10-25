package com.wechat.service;

import com.wechat.entity.AudioInfo;
import com.wechat.entity.CurriculumAudio;
import com.wechat.util.Page;
import net.sf.json.JSONArray;

import java.util.HashMap;

public interface AudioInfoService {
	
	
	/**
	 * 添加备课资源
	 * @param audioInfo
	 */
	void saveAudioInfo(AudioInfo audioInfo);
	
	/**
	 * 查询备课列表
	 * @param map
	 * @return
	 */
	Page searchAudioInfo(HashMap map);
	
	
	/**
	 * 删除备课资源
	 * @param id
	 */
	void deleteAudioInfo(String id);
	
	/**
	 * 查询已使用的课资源列表
	 * @return
	 */
	public HashMap getClassRoomMap() ;
	
	/**
	 * 查询备课资源信息
	 * @param id
	 * @return
	 */
	AudioInfo getAudioIdInfo(String id);

	JSONArray getAudioIdByCurriculumId(String curriculumId);

	JSONArray getAudioBindCourseList(String audioId);

	void saveCourseToAudio(CurriculumAudio curriculumAudio);

	Integer getAudioLength(String audioId);
	

}
