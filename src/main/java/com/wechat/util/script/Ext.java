package com.wechat.util.script;

import java.util.List;

public class Ext {
	private Integer speed;
	private List<Integer> playIndex;
	private List<Option> option;
	private Boolean bReplay;
	private Boolean bScore;
	private String resourcesName;
	
	private Ext(Builder builder) {
		this.speed = builder.speed;
		this.playIndex = builder.playIndex;
		this.option = builder.option;
		this.bReplay = builder.bReplay;
		this.bScore = builder.bScore;
		this.resourcesName = builder.resourcesName;
	}
	
	//Set & Get////////////////////////////////////////////////////////
	Integer getSpeed() {
		return speed;
	}
	void setSpeed(int speed) {
		this.speed = speed;
	}
	List<Integer> getPlayIndex() {
		return playIndex;
	}
	void setPlayIndex(List<Integer> playIndex) {
		this.playIndex = playIndex;
	}
	List<Option> getOption() {
		return option;
	}
	void setOption(List<Option> option) {
		this.option = option;
	}
	Boolean isReplay() {
		return bReplay;
	}
	void setReplay(boolean isReplay) {
		this.bReplay = isReplay;
	}
	Boolean isScore() {
		return bScore;
	}
	void setScore(boolean isScore) {
		this.bScore = isScore;
	}
	String getResourcesName() {
		return resourcesName;
	}
	void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}
	//Builder////////////////////////////////////////////////
	static class Builder {
		private Integer speed;
		private List<Integer> playIndex;
		private List<Option> option;
		private Boolean bReplay;
		private Boolean bScore;
		private String resourcesName;
		
		Builder() {
			speed = null;
			playIndex = null;
			option = null;
			bReplay = null;
			bScore = null;
			resourcesName = null;
		}
		
		Builder speed(int speed) {
			this.speed = speed;
			return this;
		}
		
		Builder playIndex(List<Integer> playIndex) {
			this.playIndex = playIndex;
			return this;
		}

		Builder option(List<Option> option) {
			this.option = option;
			return this;
		}
		
		Builder isReplay(boolean isReplay) {
			this.bReplay = isReplay;
			return this;
		}
		
		Builder isScore(boolean isScore) {
			this.bScore = isScore;
			return this;
		}
		
		Builder resourcesName(String resourcesName) {
			this.resourcesName = resourcesName;
			return this;
		}
		
		Ext build() {
			return new Ext(this);
		}
	}
}
