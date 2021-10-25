package com.wechat.mp3;

import java.util.ArrayList;
import java.util.List;


public class MediaInfo {
	private List<Sentence> shortSentenceList;
	private List<Sentence> longSentenceList;
	private List<int[]> pageInfo; // 记录mediaInfo中每�?页的起止下标，int[]有两位，第一位是�?始下标，第二位是结束下标
	private boolean isExpExist;
	
	/**
	 * 解析mediaInfo，获取每�?页在mediaInfo中的起止下标
	 * */
	private List<int[]> parsePageInfo(List<Sentence> sentenceList) {
		// 记录mediaInfo中每�?页的起止下标，int[]有两位，第一位是�?始下标，第二位是结束下标
		List<int[]> pageInfo = null;
		if (sentenceList != null) {
			pageInfo = new ArrayList<int[]>();
			int[] pageIndex = null;
			int lastPage = -1;
			int size = sentenceList.size();
			for (int i = 0; i < size; i++) {
				Sentence s = sentenceList.get(i);// 取一�?
				int page = s.getPage();// 句子�?属的页码

				if (pageInfo.size() == 0) {
					// �?始解析的时�?�判断是否有首页音频，没有就把首页音频的起止设为-1
					if (page != 0) {// 没有首页就设起止都为 -1
						pageIndex = new int[2];// mediaInfo中一页的起止下标
						pageIndex[0] = -1;
						pageIndex[1] = -1;
						pageInfo.add(pageIndex);// 首页
						pageIndex = null;
						lastPage = 0;
					}
				}

				if (page > lastPage) {
					if (pageIndex != null) {
						pageIndex[1] = i - 1;
						pageInfo.add(pageIndex);
					}
					pageIndex = new int[2];
					pageIndex[0] = i;
					lastPage = page;
				}

				if (i == size - 1) {// �?后一�?
					if (pageIndex != null) {
						pageIndex[1] = i;
						pageInfo.add(pageIndex);
					}
				}
			}
		}
		return pageInfo;
	}
	
	/**
	 * 解析短句组成长句，如果没有中文解释音频或文本则直接返回短句列�?
	 * @param shortSentenceList 短句列表
	 * @return 返回长句列表
	 * */
	private List<Sentence> parseLongSentenceList(List<Sentence> shortSentenceList) {
		List<Sentence> longSentenceList = null;
		if (isExpExist()) {//如果有中文解释才去组成长�?
			if (shortSentenceList != null) {
				longSentenceList = new ArrayList<Sentence>();
				
				Sentence ls = null;
				int longSentenceId = 1;
				int enStartTime = 0;
				int enLength = 0;
				String enText = "";
				
				int size = shortSentenceList.size();
				for (int i = 0; i < size; i++) {
					Sentence ss = shortSentenceList.get(i);
					//中文解释的音频信息或文本保存在一个长句的�?后一个短句上
					if (isExpExistInSentence(ss)) {//本句带有解释音频信息或文本，说明是长句的�?后一个短�?
						if (ls == null) {//第一个和�?后一个短句是同一�?
							ls = ss.clone();
							ls.setWordList(null);
							enStartTime = ss.getStartTime();
						}
						
						int endTime = ss.getStartTime() + ss.getLength();//取最后一个短句的结束时间
						if (endTime > enStartTime) {
							//�?后一个短句的结束 减去 第一个短句的�?始，获得长句的长�?
							enLength = endTime - enStartTime;
						}
						enText = enText + " " + ss.getEnText();//拼接短句文本
						
						ls.setId(longSentenceId);//长句id
						ls.setStartTime(enStartTime);//长句�?始时�?
						ls.setLength(enLength);//长句长度
						ls.setReadLength(enLength);//长句跟读长度（暂无用�?
						ls.setEnText(enText.trim());//长句文本
						ls.setExpStartTime(ss.getExpStartTime());//中文解释起点
						ls.setExpLength(ss.getExpLength());//中文解释长度
						ls.setExpText(ss.getExpText());//中文解释文本
						ls.setHomework(ss.getHomework());//作业段落
						ls.setPage(ss.getPage());//页码
						if (ss.getStruct() != null) {
							ls.setStruct(ss.getStruct().clone());//结构
						}
						if (ss.getWordList() != null) {
							ls.addWord(ss.getWordList());//单词列表
						}
						
						longSentenceList.add(ls);
						
						//重置变量，用于记录下�?个长�?
						ls = null;
						longSentenceId ++;
						enStartTime = 0;
						enLength = 0;
						enText = "";
						
					} else {//本句没有解释音频信息和文本，说明不是长句的最后一个短�?
						if (enStartTime == 0) {//长句的第�?个短�?
							ls = ss.clone();
							ls.setWordList(null);
							enStartTime = ss.getStartTime();
							
							enText = ss.getEnText();//拼接短句文本
							if (ss.getWordList() != null) {
								ls.addWord(ss.getWordList());
							}
						} else if (enStartTime > 0) {//长句的中间句
							enText = enText + " " + ss.getEnText();//拼接短句文本
							if (ss.getWordList() != null) {
								ls.addWord(ss.getWordList());
							}
						}
						
					}
				}
			}
		} else {//没有中文解释就不组成长句
			longSentenceList = shortSentenceList;
		}
		return longSentenceList;
	}
	
	/**
	 * 判断是否有解释的音频信息或解释文�?
	 * */
	private boolean isExpExist(List<Sentence> sentenceList) {
		boolean isExpExist = false;
		if (sentenceList != null) {
			for (Sentence sentence : sentenceList) {
				if (isExpExistInSentence(sentence)) {
					isExpExist = true;
					break;
				}
			}
		}
		return isExpExist;
	}
	
	/**
	 * 判断�?个句子是否有解释的音频信息或解释文本
	 * */
	private boolean isExpExistInSentence(Sentence sentence) {
		boolean isExpExist = false;
		if ((sentence.getExpStartTime() >= 0 && sentence.getExpLength() > 0)
				|| !sentence.getExpText().isEmpty()) {
			isExpExist = true;
		}
		return isExpExist;
	}
	
	/**
	 * 清空缓存
	 * */
	public void clear() {
		if (shortSentenceList != null) {
			shortSentenceList.clear();
		}
		if (longSentenceList != null) {
			longSentenceList.clear();
		}
		if (pageInfo != null) {
			pageInfo.clear();
		}
	}
	
	public MediaInfo(List<Sentence> sentenceList) {
		this.shortSentenceList = sentenceList;
		this.isExpExist = isExpExist(sentenceList);
		this.longSentenceList = parseLongSentenceList(sentenceList);
		this.pageInfo = parsePageInfo(sentenceList);
	}
	
	public List<Sentence> getShortSentenceList() {
		return shortSentenceList;
	}
	public void setShortSentenceList(List<Sentence> shortSentenceList) {
		this.shortSentenceList = shortSentenceList;
	}
	public List<Sentence> getLongSentenceList() {
		return longSentenceList;
	}

	public void setLongSentenceList(List<Sentence> longSentenceList) {
		this.longSentenceList = longSentenceList;
	}

	public List<int[]> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(List<int[]> pageInfo) {
		this.pageInfo = pageInfo;
	}
	public boolean isExpExist() {
		return isExpExist;
	}
	public void setExpExist(boolean isExpExist) {
		this.isExpExist = isExpExist;
	}
}
