package com.wechat.util.script;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ScriptToNewParser {
	
	
	/**
	 * 把旧指令数据转成新的数据结构
	 * */
	List<Command> parse(KHMissionCmd oldMissionCmd) {
		List<Command> commands = null;
		if (oldMissionCmd != null) {
			Command cmd_primary = null;
			switch (oldMissionCmd.getCommand()) {
			case KHMissionCmd.CMD_ORIGINAL://播放录音
				cmd_primary = parseOriginal(oldMissionCmd);
				break;
			case KHMissionCmd.CMD_REPEAT://跟读
				cmd_primary = parseRepeta(oldMissionCmd);
				break;
			case KHMissionCmd.CMD_SMART://播放原音音频/原音视频
				cmd_primary = parseSmart(oldMissionCmd);
				break;
			case KHMissionCmd.CMD_SENTENCE://语句
				cmd_primary = parseSentence(oldMissionCmd);
				break;
			default:
				break;
			}
			
			if (cmd_primary != null) {
				commands = new ArrayList<Command>();
				commands.add(cmd_primary);
				
				//如有图片，需要多建一条展示图片的指令
				String picUrl = oldMissionCmd.getPicUrl();
				if (picUrl != null && !picUrl.isEmpty()) {
					Command cmd_pic_show = new Command.Builder(Command.PICTURE_SHOW)
						.content(picUrl).build();
					commands.add(cmd_pic_show);
				}
			}
		}
		return commands;
	}
	
	/**
	 * 转换播放录音指令
	 * */
	private Command parseOriginal(KHMissionCmd oldMissionCmd) {
		String srcUrl = oldMissionCmd.getSrcUrl();
		int cmdPlayTime = oldMissionCmd.getCmdPlayTime();
		String name = oldMissionCmd.getName();
		return new Command.Builder(Command.AUDIO_PLAY_SELF)
			.content(srcUrl).setPrimary().instructionTime(cmdPlayTime)
			.resourcesName(name).build();
	}
	
	/**
	 * 转换跟读指令
	 * */
	private Command parseRepeta(KHMissionCmd oldMissionCmd) {
		Command command = null;
		String srcUrl = oldMissionCmd.getSrcUrl();
		if (srcUrl != null && !srcUrl.isEmpty()) {
			String resourceId = oldMissionCmd.getResourceId();
			if (resourceId == null || resourceId.isEmpty()) {
				resourceId = getResIdBySrcUrl(srcUrl);
			}
			
			if (resourceId != null && !resourceId.isEmpty()) {
				//"content":"1,2,3,5,8[10]<120>"
				String content = oldMissionCmd.getContent();//从content中解析出以下参数
				List<Integer> playList = parseToIndexList(content);//播放句子序列
				boolean isReplay = isReplay(content);//是否回放
				boolean isScore = isScore(content);//是否评分
				int speed = getSpeedSetting(content);//播放速度
				
				List<Option> option = oldMissionCmd.getOption();//获取评分分支
				if (option != null && option.isEmpty()) {
					option = null;
				}
				
				int cmdPlayTime = oldMissionCmd.getCmdPlayTime();//指令执行所需时间
				String name = oldMissionCmd.getName();
				
				command = new Command.Builder(Command.READ_AFTER).content(resourceId)
						.setPrimary().playIndex(playList).speed(speed)
						.isReplay(isReplay).isScore(isScore).option(option)
						.resourcesName(oldMissionCmd.getResourceName())
						.resourcesName(name).instructionTime(cmdPlayTime).build();
			}
		}
		return command;
	}
	
	/**
	 * 转换播放原音指令
	 * */
	private Command parseSmart(KHMissionCmd oldMissionCmd) {
		Command command = null;
		String srcUrl = oldMissionCmd.getSrcUrl();
		if (srcUrl != null && !srcUrl.isEmpty()) {
			String resourceId = oldMissionCmd.getResourceId();
			if (resourceId == null || resourceId.isEmpty()) {
				resourceId = getResIdBySrcUrl(srcUrl);
			}
			
			if (resourceId != null && !resourceId.isEmpty()) {
				//"content":"1,2,3,5,8[10]<120>"
				String content = oldMissionCmd.getContent();//从content中解析出以下参数
				List<Integer> playList = parseToIndexList(content);//播放句子序列
				int speed = getSpeedSetting(content);//播放速度
				
				int cmdPlayTime = oldMissionCmd.getCmdPlayTime();//指令执行所需时间
				String name = oldMissionCmd.getName();
				
				int cmd = Command.AUDIO_PLAY_RES;
				if (srcUrl.toLowerCase().endsWith(".mp3")) {
					cmd = Command.AUDIO_PLAY_RES;
				} else if (srcUrl.toLowerCase().endsWith(".mp4")) {
					cmd = Command.VIDEO_PLAY_RES;
				}
				
				command = new Command.Builder(cmd)
					.content(resourceId).setPrimary()
					.playIndex(playList).speed(speed)
					.resourcesName(oldMissionCmd.getResourceName())
					.resourcesName(name).instructionTime(cmdPlayTime).build();
			}
		}
		return command;
	}
	
	/**
	 * 转换语句指令
	 * */
	private Command parseSentence(KHMissionCmd oldMissionCmd) {
		//"content":"hello world[10]"
		String content = oldMissionCmd.getContent();//从content中解析出以下参数
		boolean isReplay = isReplay(content);//是否回放
		boolean isScore = isScore(content);//是否评分
		
		List<Option> option = oldMissionCmd.getOption();//获取评分分支
		if (option != null && option.isEmpty()) {
			option = null;
		}
		
		int cmdPlayTime = oldMissionCmd.getCmdPlayTime();//指令执行所需时间
		String name = oldMissionCmd.getName();
		
		Command command = new Command.Builder(Command.AUDIO_RECORD_ANS)
			.content(getContentPure(oldMissionCmd.getContent())).setPrimary()
			.isReplay(isReplay).isScore(isScore).option(option)
			.resourcesName(name).instructionTime(cmdPlayTime).build();
		return command;
	}
	
	/**
	 * 去除文本里的参数，获取纯内容
	 * */
	private String getContentPure(String content) {
		if (content != null) {
			int indexOfBracket = content.indexOf("[");
			if (indexOfBracket > 0) {//去掉中括号及后面的数据
				content = content.substring(0, indexOfBracket);
			}
		}
		return content;
	}
	
	/**
	 * 解析句子id列表
	 * */
	private List<Integer> parseToIndexList(String content) {
		//"content":"1,2,3,5,8[10]<120>"
		List<Integer> indexList = null;
		if (content != null && !content.isEmpty()) {
			String tmpContent = content;
			int indexOfBracket = tmpContent.indexOf("[");
			if (indexOfBracket < 0) {
				indexOfBracket = tmpContent.indexOf("<");
			}
			if (indexOfBracket > 0) {//去掉中括号及后面的数据
				tmpContent = tmpContent.substring(0, indexOfBracket);
			}
			String[] numStrList = tmpContent.split(",");
			if (numStrList != null && numStrList.length > 0) {
				indexList = new ArrayList<Integer>();
				for (String numStr : numStrList) {
					try {
						int num = Integer.parseInt(numStr);
						indexList.add(num);
					} catch (Exception e) {
					}
				}
			}
		}
		return indexList;
	}
	
	/**
	 * 判断用户录音后是否需要回放
	 * */
	private boolean isReplay(String content) {
		boolean isReplay = false;
		String set = getSettingInContent(content);
		if (set.startsWith("1")) {//第一位是1表示需要回放
			isReplay = true;
		}
		return isReplay;
	}
	
	/**
	 * 是否需要评分
	 * */
	private boolean isScore(String content) {
		boolean isScore = false;
		String set = getSettingInContent(content).trim();
		if (set.length() >= 2) {//第二位是1表示需要计分
			set = set.substring(1);
			if (set.startsWith("1")) {
				isScore = true;
			}
		}
		return isScore;
	}
	
	/**
	 * 从指令内容中获取配置信息
	 * */
	private String getSettingInContent(String content){
		String res = "";
		if (content != null) {
			Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=\\])");//获取中括号中的内容
			Matcher matcher = pattern.matcher(content);
			if (matcher.find())
				res = matcher.group(1);
		}
        return res;
    }
	
	/**
	 * 从指令内容中获取速度信息
	 * */
	private int getSpeedSetting(String content) {
		int res = 100;
		if (content != null) {
			Pattern pattern = Pattern.compile("(?<=\\<)(.+?)(?=\\>)");//获取尖括号中的内容
			Matcher matcher = pattern.matcher(content);
			if (matcher.find()) {
				String value = matcher.group(1);
				try {
					res = Integer.parseInt(value);
				} catch (Exception e) {
					res = 100;
				}
			}
		}
        return res;
	}
	
	/**
	 * 根据原音资源链接找到资源ID（用于兼容早期没有资源id的备课数据）
	 * */
	private String getResIdBySrcUrl(String srcUrl) {
		String audioId = null;
		Record record= Db.findFirst("SELECT audioid FROM audioinfo WHERE src = ?",srcUrl);
		if(record!=null){
			audioId = record.getStr("audioid");
		}
		return audioId;
	}
	
	//Init/////////////////////////////////////////////////////////////
	private static ScriptToNewParser instance = new ScriptToNewParser();
	public static ScriptToNewParser getInstance() {
		return instance;
	}
}
