package com.wechat.util.script;

public class KHWord implements Cloneable {
	private String key;	//单词英文
	private String cnKey; //单词中文
	private String audioPath = ""; //音效音频
	private String pronouncePath = ""; //英文发音音频
	
	private String localAudio;
	private String localPronounce;
	
	@Override
	protected KHWord clone() throws CloneNotSupportedException {
		KHWord khWord = (KHWord) super.clone();
		return khWord;
	}
	
	//Set & Get ///////////////////////////////////////////////
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCnKey() {
		return cnKey;
	}
	public void setCnKey(String cnKey) {
		this.cnKey = cnKey;
	}
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getPronouncePath() {
		return pronouncePath;
	}
	public void setPronouncePath(String pronouncePath) {
		this.pronouncePath = pronouncePath;
	}

	public String getLocalAudio() {
		return localAudio;
	}

	public void setLocalAudio(String localAudio) {
		this.localAudio = localAudio;
	}

	public String getLocalPronounce() {
		return localPronounce;
	}

	public void setLocalPronounce(String localPronounce) {
		this.localPronounce = localPronounce;
	}
}
