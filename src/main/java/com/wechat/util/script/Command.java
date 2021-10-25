package com.wechat.util.script;

import java.util.List;

public class Command {
	private int command;
	private String content;
	private int offset;
	private int priority;
	private Ext ext;
	private int instructionTime;
	
	static final int AUDIO_PLAY_SELF = 201;//201：播放自定义音频 
	static final int AUDIO_PLAY_RES = 202;//202: 播放课堂原音音频 
	static final int AUDIO_RECORD_TIME = 206;//206：期待学生录音（定时录音）
	static final int AUDIO_RECORD_ANS = 207;//207: 期待学生录音（回答-----等同于之前的“语句”）
	
	static final int VIDEO_PLAY_SELF = 211;//211：播放自定义视频  
	static final int VIDEO_PLAY_RES = 212;//212：播放课堂原音视频 
	static final int VIDEO_RECORD_TIME = 216;//216：期待学生录像（定时录像）
	
	static final int PICTURE_SHOW = 221;//221：展示图片
	static final int PICTURE_TAKE = 226;//226：期待学生拍照
	
	static final int MOTION_SHOW = 231;//231：执行动作
	
	static final int READ_AFTER = 301;//301: 跟读
	static final int ROLE_PLAY = 302;//302：配音
	
	//文件前缀，前期准备的原始资源有名字重复的现象，使用前缀进行区分
	static final String PREFIX_SRC = "src_";
	static final String PREFIX_CN = "cn_";
	static final String PREFIX_EXP = "exp_";
	
	Command(Builder builder) {
		this.command = builder.command;
		this.content = builder.content;
		this.offset = builder.offset;
		this.priority = builder.priority;
		this.ext = builder.ext;
		this.instructionTime = builder.instructionTime;
	}
	
	/**
	 * 获取指令中包含的资源ID
	 * @return 返回资源ID（正整数），没有则返回-1
	 * */
	int getResourceId() {
		int res = -1;
		if (command == Command.AUDIO_PLAY_RES || command == Command.VIDEO_PLAY_RES 
				|| command == Command.READ_AFTER || command == Command.ROLE_PLAY) {
			if (content != null) {
				try {
					res = Integer.parseInt(content);//是纯数字才是资源ID
				} catch (NumberFormatException e) {
					res = -1;
				}
			}
		}
		return res;
	}
	
	/**
	 * 是否主指令
	 * */
	boolean isPrimary() {
		return priority == 1;
	}
	
	/**
	 * 是否需要回放
	 * */
	boolean isReplay() {
		return (ext != null && ext.isReplay() != null) ? ext.isReplay() : false;
	}
	
	/**
	 * 是否需要评分
	 * */
	boolean isScore() {
		return (ext != null && ext.isScore() != null) ? ext.isScore() : false;
	}
	
	/**
	 * 播放速度配置
	 * */
	int getSpeed() {
		return (ext != null && ext.getSpeed() != null && ext.getSpeed() > 0) ? ext.getSpeed() : 100;
	}
	
	/**
	 * 获取评分分支
	 * */
	List<Option> getOption() {
		return ext != null ? ext.getOption() : null;
	}
	
	/**
	 * 获取播放句子序列
	 * */
	List<Integer> getPlayIndex() {
		return ext != null ? ext.getPlayIndex() : null;
	}
	
//	/**
//	 * 获取指令执行的预估时长（单位：秒）
//	 * */
//	int getInstructionTime() {
//		return (ext != null && ext.getInstructionTime() != null && ext.getInstructionTime() > 0) ? ext.getInstructionTime() : 0;
//	}

	/**
	 * 获取资源名称
	 * */
	String getResourcesName() {
		return (ext != null && ext.getResourcesName() != null) ? ext.getResourcesName() : "";
	}
	///////////////////////////////////////////////////////////////////
	int getCommand() {
		return command;
	}
	String getContent() {
		return content;
	}
	int getOffset() {
		return offset;
	}
	int getInstructionTime() {
		return instructionTime;
	}
	
	static class Builder {
		private int command;
		private String content;
		private int offset;
		private int priority;
		private Ext ext;
		private int instructionTime;
		
		Builder(int command) {
			this.command = command;
			this.content = "";
			this.offset = 0;
			this.priority = 0;
			this.ext = new Ext.Builder().build();
			this.instructionTime = 0;
		}
		
		Builder content(String content) {
			this.content = content;
			return this;
		}
		
		Builder offset(int offset) {
			this.offset = offset;
			return this;
		}
		
		/**
		 * 设置为主指令
		 * */
		Builder setPrimary() {
			this.priority = 1;
			return this;
		}
		
		/**
		 * 设置指令执行的预估时长（单位：秒）
		 * */
		Builder instructionTime(int instructionTime) {
			this.instructionTime = instructionTime;
			return this;
		}
		
		/**
		 * 设置是否回放
		 * */
		Builder isReplay(boolean isReplay) {
			this.ext.setReplay(isReplay);
			return this;
		}
		
		/**
		 * 设置是否评分
		 * */
		Builder isScore(boolean isScore) {
			this.ext.setScore(isScore);
			return this;
		}
		
		/**
		 * 设置播放速度
		 * */
		Builder speed(int speed) {
			this.ext.setSpeed(speed);
			return this;
		}
		
		/**
		 * 设置评分分支
		 * */
		Builder option(List<Option> option) {
			this.ext.setOption(option);
			return this;
		}
		
		/**
		 * 设置播放句子序列
		 * */
		Builder playIndex(List<Integer> playIndex) {
			this.ext.setPlayIndex(playIndex);
			return this;
		}
		
		/**
		 * 设置资源名称
		 * */
		Builder resourcesName(String resourcesName) {
			this.ext.setResourcesName(resourcesName);
			return this;
		}
		
		Command build() {
			return new Command(this);
		}
	}
}
