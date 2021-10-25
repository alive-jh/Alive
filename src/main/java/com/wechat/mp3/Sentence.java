package com.wechat.mp3;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	private int id;//句子序号
	private int startTime;//原音频开始时间ms
	private int length;//原音频播放时长ms
	private int readLength; //�?跟读的音频时�?
	private String enText;//英文原文
	private int expStartTime;//导读音频�?始时间ms
	private int expLength;//导读音频播放时长ms
	private String expText;//导读文本内容
	private List<Word> wordList;//重点单词列表
	private int page;//页码
	private int homework; //作业�?
	private int[] struct; //结构参数，比如�?�第�?模块第二单元�?5段�?�就是[1, 2, 5]
	public boolean isSelected;
	/**
	 * 添加单词
	 * @param text 单词
	 * @param explain 单词解释
	 * */
	public void addWord(String text, String explain) {
		if (wordList == null) {
			wordList = new ArrayList<Word>();
		}
		Word word = new Word(text, explain);
		wordList.add(word);
	}
	
	/**
	 * 添加单词
	 * @param wordList 单词列表
	 * */
	public void addWord(List<Word> words) {
		if (wordList == null) {
			wordList = new ArrayList<Word>();
		}
		wordList.addAll(words);
	}
	
	/**
	 * 把单词表中的单词连同解释�?起取出组成字符串
	 * @retrun 单词和解释的字符串，比如  "tiger[p150]老虎[p300]lion[p150]狮子[p300]"
	 * */
	public String getWords() {
		String res = "";
		if (wordList != null) {
			for (Word word : wordList) {
				String text = word.getText();
				String explain = word.getExplain();
				res = res + "[word]" + text + "[p150]" + "[exp]" + explain + "[p500]";
			}
		}
		return res;
	}
	
	/**
	 * shallow clone
	 * */
	@Override
	public Sentence clone() {
		Sentence sentence = new Sentence();
		sentence.setEnText(this.getEnText());
		sentence.setExpLength(this.getExpLength());
		sentence.setExpStartTime(this.getExpStartTime());
		sentence.setExpText(this.getExpText());
		sentence.setHomework(this.getHomework());
		sentence.setId(this.getId());
		sentence.setLength(this.getLength());
		sentence.setPage(this.getPage());
		sentence.setReadLength(this.getReadLength());
		sentence.setStartTime(this.getStartTime());
		sentence.setStruct(this.getStruct());
		sentence.setWordList(this.getWordList());
		return sentence;
	}
	
	//Set & Get /////////////////////////////////////////////////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getReadLength() {
		return readLength;
	}

	public void setReadLength(int readLength) {
		this.readLength = readLength;
	}

	public String getEnText() {
		return enText;
	}
	public void setEnText(String enText) {
		this.enText = enText;
	}
	public int getExpStartTime() {
		return expStartTime;
	}
	public void setExpStartTime(int expStartTime) {
		this.expStartTime = expStartTime;
	}
	public int getExpLength() {
		return expLength;
	}
	public void setExpLength(int expLength) {
		this.expLength = expLength;
	}
	public String getExpText() {
		return expText;
	}
	public void setExpText(String expText) {
		this.expText = expText;
	}
	public List<Word> getWordList() {
		return wordList;
	}
	public void setWordList(List<Word> wordList) {
		this.wordList = wordList;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getHomework() {
		return homework;
	}
	public void setHomework(int homework) {
		this.homework = homework;
	}
	public int[] getStruct() {
		return struct;
	}
	public void setStruct(int[] struct) {
		this.struct = struct;
	}
	
	class Word {
		private String text;
		private String explain;
		
		Word(String text, String explain) {
			this.text = text;
			this.explain = explain;
		}
		
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getExplain() {
			return explain;
		}
		public void setExplain(String explain) {
			this.explain = explain;
		}
	}
}
