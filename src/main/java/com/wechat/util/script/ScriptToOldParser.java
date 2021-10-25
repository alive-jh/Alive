package com.wechat.util.script;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ScriptToOldParser {
	
	
	/**
	 * 把旧指令数据转成新的数据结构
	 * */
	KHMissionCmd parse(List<Command> commands) {
		KHMissionCmd missionCmd = null;
		if (commands != null) {
			Command cmd_primary = null;
			String picUrl = "";
			for (Command command : commands) {
				if (command.isPrimary()) {//找到主指令
					cmd_primary = command;
				} else if (command.getCommand() == Command.PICTURE_SHOW) {//展示图片的指令
					picUrl = command.getContent();
				}
			}
			
			if (cmd_primary != null) {//使用主指令转换生成旧格式指令，并把图片链接加到生成的旧指令中
				String content = cmd_primary.getContent();
				int instructionTime = cmd_primary.getInstructionTime();
				String resourcesName = cmd_primary.getResourcesName();
				switch (cmd_primary.getCommand()) {
				case Command.AUDIO_PLAY_SELF://播放自定义音频--->播放录音
					missionCmd = new KHMissionCmd.Builder(KHMissionCmd.CMD_ORIGINAL)
						.srcUrl(content).picUrl(picUrl).cmdPlayTime(instructionTime)
						.name(resourcesName).build();
					break;
				case Command.AUDIO_PLAY_RES://播放原音音频--->播放原音
				case Command.VIDEO_PLAY_RES://播放原音视频--->播放原音
					if (content != null && !content.isEmpty()) {
						Resource resource = getResById(content);
						if (resource != null) {
							String oldContent = parseListToString(cmd_primary.getPlayIndex(), ",")
										+ "<" + cmd_primary.getSpeed() + ">";
							
							missionCmd = new KHMissionCmd.Builder(KHMissionCmd.CMD_SMART)
								.srcUrl(resource.getSrcUrl()).cnUrl(resource.getCnUrl())
								.expUrl(resource.getExpUrl()).mediaInfo(resource.getMediaInfo())
								.content(oldContent).picUrl(picUrl)
								.resourceId(content).resourceName(cmd_primary.getResourcesName())
								.cmdPlayTime(instructionTime).name(resourcesName).build();
						}
					}
					break;
				case Command.READ_AFTER://跟读--->跟读
					if (content != null && !content.isEmpty()) {
						Resource resource = getResById(content);
						if (resource != null) {
							String replayTag = cmd_primary.isReplay() ? "1" : "0";
							String scoreTag = cmd_primary.isScore() ? "1" : "0";
							String oldContent = parseListToString(cmd_primary.getPlayIndex(), ",")
									+ "[" + replayTag + scoreTag + "]"
									+ "<" + cmd_primary.getSpeed() + ">";
								
							List<Option> option = cmd_primary.getOption();
							
							missionCmd = new KHMissionCmd.Builder(KHMissionCmd.CMD_REPEAT)
								.srcUrl(resource.getSrcUrl()).cnUrl(resource.getCnUrl())
								.expUrl(resource.getExpUrl()).mediaInfo(resource.getMediaInfo())
								.content(oldContent).picUrl(picUrl).option(option)
								.resourceId(content).resourceName(cmd_primary.getResourcesName())
								.cmdPlayTime(instructionTime).name(resourcesName).build();
						}
					}
					break;
				case Command.AUDIO_RECORD_ANS://期待学生录音（回答）--->语句
					String replayTag = cmd_primary.isReplay() ? "1" : "0";
					String scoreTag = cmd_primary.isScore() ? "1" : "0";
					String oldContent = content + ("[" + replayTag + scoreTag + "]");
						
					List<Option> option = cmd_primary.getOption();

					missionCmd = new KHMissionCmd.Builder(KHMissionCmd.CMD_SENTENCE)
						.content(oldContent).picUrl(picUrl).option(option)
						.cmdPlayTime(instructionTime).name(resourcesName).build();
					break;
				default:
					break;
				}
			}
		}
		return missionCmd;
	}

	/**
	 * 把列表内的元素转成字符串，并以指定符号delimiter分隔
	 * */
	private String parseListToString(List<Integer> list, String delimiter) {
		String res = "";
		if (list != null) {
			StringBuilder sb = new StringBuilder();
			for (int index : list) {
				sb.append(index).append(delimiter);
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);//去掉末尾的分隔符
				res = sb.toString();
			}
		}
		return res;
	}
	
	/**
	 * 根据资源ID找到原音资源的各种链接
	 * */
	private Resource getResById(String resourceId) {
		Resource resource = null;
		Record record= Db.findFirst("SELECT src,cn,exp,mediainfo,picrecog FROM `audioinfo` WHERE audioid=?",resourceId);
		if(record!=null){
			resource = new Resource(record.getStr("src"), record.getStr("cn"), record.getStr("exp"), record.getStr("mediainfo"), record.getStr("picrecog"));
		}
		//		resource = new Resource(src, cn, exp, mediainfo, picrecog);
		return resource;
	}
		
	//Init/////////////////////////////////////////////////////////////
	private static ScriptToOldParser instance = new ScriptToOldParser();
	public static ScriptToOldParser getInstance() {
		return instance;
	}
}
