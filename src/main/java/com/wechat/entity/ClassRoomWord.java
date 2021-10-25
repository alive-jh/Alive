package com.wechat.entity;

import java.util.Arrays;

public class ClassRoomWord {

	private Integer classRoomId;
	
	private String[] words;

	public Integer getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	@Override
	public String toString() {
		return "ClassRoomWord [classRoomId=" + classRoomId + ", words=" + Arrays.toString(words) + "]";
	}
	
	
}
