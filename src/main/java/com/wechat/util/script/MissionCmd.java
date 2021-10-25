package com.wechat.util.script;

import java.util.ArrayList;
import java.util.List;

public class MissionCmd {
	private int id; //任务序号
	private List<Command> commands;
	private int sort; //排序
	
	private boolean lastCmd = false;
	
	/**
	 * 添加一条指令
	 * */
	void addCommand(Command command) {
		if (commands == null) {
			commands = new ArrayList<Command>();
		}
		commands.add(command);
	}
	
	/**
	 * 获取主指令
	 * */
	Command getPrimaryCommand() {
		Command command = null;
		if (isAvailable()) {
			for (Command cmd : commands) {
				if (cmd.isPrimary()) {
					command = cmd;
					break;
				}
				
				if (command == null) {//先记住第一个指令的类型，如果没有找到主指令，就使用第一个指令作为主指令
					command = cmd;
				}
			}
		}
		return command;
	}
	
	/**
	 * 获取主指令的指令类型
	 * */
	int getPrimaryCommandType() {
		Command command = getPrimaryCommand();
		return command == null ? -1 : command.getCommand();
	}
	
	/**
	 * 获取指令内所有资源ID
	 * @return 返回资源ID（正整数）列表
	 * */
	List<Integer> getResourceIdsInMissionCmd() {
		List<Integer> ids = null;
		if (isAvailable()) {
			ids = new ArrayList<Integer>();
			for (Command command : commands) {
				int resourceId = command.getResourceId();
				if (resourceId > 0) {
					ids.add(resourceId);
				}
			}
		}
		return ids;
	}
	
	/**
	 * 是否需要评分
	 * */
	boolean isScore() {
		if (isAvailable()) {
			for (Command command : commands) {
				if (command.isScore()) {
					return true;//只要有一个指令需要评分，整个指令组就需要评分
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断指令集是否可用（不为空）
	 * */
	boolean isAvailable() {
		return (commands != null && !commands.isEmpty());
	}
	
	//Set & Get ///////////////////////////////////////////////
	int getId() {
		return id;
	}
	void setId(int id) {
		this.id = id;
	}
	List<Command> getCommands() {
		return commands;
	}
	void setCommands(List<Command> commands) {
		this.commands = commands;
	}
	int getSort() {
		return sort;
	}
	void setSort(int sort) {
		this.sort = sort;
	}
	boolean isLastCmd() {
		return lastCmd;
	}
	void setLastCmd(boolean lastCmd) {
		this.lastCmd = lastCmd;
	}
}
