package com.wechat.util.script;

import java.util.ArrayList;
import java.util.List;

public class KHMissionCmd implements Cloneable {
	private int command; //任务指令
	private int times; //重复次数
	private String srcUrl = ""; //原音音频路径
	private String cnUrl = ""; //翻译音频路径
	private String expUrl = ""; //导读音频路径
	private String mediaInfo = ""; //分句配置文件路径
	private String picUrl = "";//图片路径
	private String content = ""; //自定义脚本内容
	private List<Option> option; //条件列表
	private List<KHWord> wordList; //单词列表
	
	private String name;
	private String recordTimeStr;
	
	//Android独有
	private int id; //任务序号
	private String resId;
	private String resName;
	private int conumeTime;//记录当前脚本使用的时间
	
	//IOS独有
	private String readTimeLength;
	private String audioId;
	private String resourcesName;
	
	static final int CMD_SCRIPT = 101; //脚本
	static final int CMD_ORIGINAL = 102; //听原音
	static final int CMD_GUIDE = 103; //听导读
	static final int CMD_REPEAT = 104; //跟读
	static final int CMD_WORD = 105; //学单词
	static final int CMD_SMART = 106; //中英导读
	static final int CMD_RETELL = 107; //复述
	static final int CMD_SENTENCE = 108; //句子
	
	KHMissionCmd(Builder builder) {
		this.command = builder.command;
		this.times = builder.times;
		this.srcUrl = builder.srcUrl;
		this.cnUrl = builder.cnUrl;
		this.expUrl = builder.expUrl;
		this.mediaInfo = builder.mediaInfo;
		this.picUrl = builder.picUrl;
		this.content = builder.content;
		this.option = builder.option;
		this.wordList = builder.wordList;
		
		this.name = builder.name;
		
		setResourceId(builder.resourceId);
		setResourceName(builder.resourceName);
		setCmdPlayTime(builder.cmdPlayTime);
	}
	
	
	
	/**
	 * 获取资源ID
	 * */
	String getResourceId() {
		String audioId = getAudioId();//IOS独有
		if (audioId != null && !audioId.isEmpty()) {
			return audioId;
		}
		
		String resId = getResId();//Android独有
		if (resId != null && !resId.isEmpty()) {
			return resId;
		}
		return null;
	}
	
	/**
	 * 设置资源id
	 * */
	void setResourceId(String resourceId) {
		setResId(resourceId);//Android独有
		setAudioId(resourceId);//IOS独有
	}
	
	/**
	 * 获取资源名称
	 * */
	String getResourceName() {
		String resourcesName = getResourcesName();//IOS独有
		if (resourcesName != null && !resourcesName.isEmpty()) {
			return resourcesName;
		}
		
		String resName = getResName();//Android独有
		if (resName != null && !resName.isEmpty()) {
			return resName;
		}
		return "";
	}
	
	/**
	 * 获取资源名称
	 * */
	void setResourceName(String resourcesName) {
		setResName(resourcesName);//Android独有
		setResourcesName(resourcesName);//IOS独有
	}
	
	/**
	 * 获取指令执行的预估时间(秒)
	 * */
	int getCmdPlayTime() {
		int cmdPlayTime = 0;
		
		//IOS独有
		String readTimeLength = this.getReadTimeLength();
		try {
			cmdPlayTime = Integer.parseInt(readTimeLength);
		} catch (NumberFormatException e) {
		}
		
		//Android独有
		if (cmdPlayTime <= 0) {
			cmdPlayTime = this.getConumeTime();
		}
		
		//Android和IOS共有
		if (cmdPlayTime <= 0) {
			cmdPlayTime = parseTimeToSecond(this.getRecordTimeStr());
		}
		return cmdPlayTime;
	}
	
	/**
	 * 设置播放所需时间
	 * */
	void setCmdPlayTime(int cmdPlayTime) {
		this.setReadTimeLength(Integer.toString(cmdPlayTime));
		this.setConumeTime(cmdPlayTime);
		this.setRecordTimeStr(parseTimeToClock(cmdPlayTime));
	}
	
	/**
	 * 把 00:00 或 00:00:00 格式的时间转换成秒
	 * */
	private int parseTimeToSecond(String timeString) {
		if (timeString != null && !timeString.isEmpty()) {
			String[] hms = timeString.split(":");
			if (hms.length == 2) {
				try {
					int s = Integer.parseInt(hms[1]);
					int m = Integer.parseInt(hms[0]);
					return m * 60 + s;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (hms.length == 3) {
				try {
					int s = Integer.parseInt(hms[2]);
					int m = Integer.parseInt(hms[1]);
					int h = Integer.parseInt(hms[0]);
					return h * 3600 + m * 60 + s;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	/**
	 * 把 秒数 转换成00:00 或 00:00:00 格式的时间字符串
	 * */
	private String parseTimeToClock(int time) {
		int s = time % 60;
		int m = (time / 60) % 60;
		int h = time / 3600;
		
		StringBuilder sb = new StringBuilder();
		if (h > 0) {
			if (h < 10) {
				sb.append("0");
			}
			sb.append(h).append(":");
		}
		
		if (m < 10) {
			sb.append("0");
		}
		sb.append(m).append(":");
		
		if (s < 10) {
			sb.append("0");
		}
		sb.append(s);
		return sb.toString();
	}
	
	protected KHMissionCmd clone() throws CloneNotSupportedException {
		KHMissionCmd missionCmd = (KHMissionCmd)super.clone();
//		KHMissionCmd missionCmd = new KHMissionCmd();
//		missionCmd.setId(this.getId());
//		missionCmd.setCommand(this.getCommand());
//		missionCmd.setTimes(this.getTimes());
//		missionCmd.setSrcUrl(this.getSrcUrl());
//		missionCmd.setCnUrl(this.getCnUrl());
//		missionCmd.setExpUrl(this.getExpUrl());
//		missionCmd.setMediaInfo(this.getMediaInfo());
//		missionCmd.setPicLib(this.getPicLib());
//		missionCmd.setContent(this.getContent());
		List<KHWord> wordList = null;
		if (this.getWordList() != null) {
			wordList = new ArrayList<KHWord>();
			for (KHWord khw : this.getWordList()) {
				wordList.add(khw.clone());
			}
		}
		missionCmd.setWordList(wordList);
		return missionCmd;
	}
	
	//Set & Get ///////////////////////////////////////////////
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getSrcUrl() {
		return srcUrl;
	}
	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}
	public String getCnUrl() {
		return cnUrl;
	}
	public void setCnUrl(String cnUrl) {
		this.cnUrl = cnUrl;
	}
	public String getExpUrl() {
		return expUrl;
	}
	public void setExpUrl(String expUrl) {
		this.expUrl = expUrl;
	}
	public String getMediaInfo() {
		return mediaInfo;
	}
	public void setMediaInfo(String mediaInfo) {
		this.mediaInfo = mediaInfo;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<KHWord> getWordList() {
		return wordList;
	}
	public void setWordList(List<KHWord> wordList) {
		this.wordList = wordList;
	}
	public List<Option> getOption() {
		return option;
	}
	public void setOption(List<Option> option) {
		this.option = option;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	String getRecordTimeStr() {
		return recordTimeStr;
	}
	void setRecordTimeStr(String recordTimeStr) {
		this.recordTimeStr = recordTimeStr;
	}

	//Android 独有/////////////////////////////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	private String getResId() {
		return resId;
	}
	private void setResId(String resId) {
		this.resId = resId;
	}

	private String getResName() {
		return resName;
	}
	private void setResName(String resName) {
		this.resName = resName;
	}

	private int getConumeTime() {
		return conumeTime;
	}
	private void setConumeTime(int conumeTime) {
		this.conumeTime = conumeTime;
	}

	//IOS 独有////////////////////////////////////////////
	private String getReadTimeLength() {
		return readTimeLength;
	}
	private void setReadTimeLength(String readTimeLength) {
		this.readTimeLength = readTimeLength;
	}

	private String getAudioId() {
		return audioId;
	}
	private void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	private String getResourcesName() {
		return resourcesName;
	}
	private void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}
	
	/////////////////////////////////////////////////////////////
	
	static class Builder {
		private int command; //任务指令
		private int times; //重复次数
		private String srcUrl; //原音音频路径
		private String cnUrl; //翻译音频路径
		private String expUrl; //导读音频路径
		private String mediaInfo; //分句配置文件路径
		private String picUrl;//图片路径
		private String content; //自定义脚本内容
		private List<Option> option; //条件列表
		private List<KHWord> wordList; //单词列表
		private String name;
		
		private String resourceId;
		private String resourceName;
		private int cmdPlayTime;
		
		Builder(int command) {
			this.command = command;
			this.times = 1;
			this.srcUrl = "";
			this.cnUrl = "";
			this.expUrl = "";
			this.mediaInfo = "";
			this.picUrl = "";
			this.content = "";
			this.option = null;
			this.wordList = null;
			
			this.name = "";
			
			this.resourceId = "";
			this.resourceName = "";
			this.cmdPlayTime = 0;
		}
		
		Builder srcUrl(String srcUrl) {
			this.srcUrl = srcUrl;
			return this;
		}
		
		Builder cnUrl(String cnUrl) {
			this.cnUrl = cnUrl;
			return this;
		}
		
		Builder expUrl(String expUrl) {
			this.expUrl = expUrl;
			return this;
		}
		
		Builder mediaInfo(String mediaInfo) {
			this.mediaInfo = mediaInfo;
			return this;
		}
		
		Builder picUrl(String picUrl) {
			this.picUrl = picUrl;
			return this;
		}
		
		Builder content(String content) {
			this.content = content;
			return this;
		}
		
		Builder option(List<Option> option) {
			this.option = option;
			return this;
		}
		
		Builder name(String name) {
			this.name = name;
			return this;
		}
		
		Builder resourceId(String resourceId) {
			this.resourceId = resourceId;
			return this;
		}
		
		Builder resourceName(String resourceName) {
			this.resourceName = resourceName;
			return this;
		}
		
		Builder cmdPlayTime(int cmdPlayTime) {
			this.cmdPlayTime = cmdPlayTime;
			return this;
		}
		
		KHMissionCmd build() {
			return new KHMissionCmd(this);
		}
	}
}
