package com.wechat.util.script;

import java.util.ArrayList;
import java.util.List;

public class Option  implements Comparable<Option>{
	private String condition;
	private String action = "";
	
	private String localAction;

	@Override
	public int compareTo(Option another) {
		int res = 0;
		try {
			int condition = Integer.parseInt(this.getCondition());
			int anotherCondition = Integer.parseInt(another.getCondition());
			if (condition < anotherCondition) {
				res = -1;
			} else if (condition > anotherCondition) {
				res = 1;
			}
		} catch (Exception e) {
		}
		return res;
	}

	private static List<Integer> findNumber(String str) {
		List<Integer> resList = new ArrayList<Integer>();
		List<String> strList = findNumberStr(str);
		for (String s : strList) {
			int i = str2Int(s);
			if (i >= 0) {
				resList.add(i);
			}
		}
		return resList;
	}

	private static int str2Int(String numStr) {
		int res = -1;
		try {
			res = Integer.parseInt(numStr);
			return res;
		} catch (NumberFormatException e) {
		} catch (NullPointerException e) {
			return res;
		}
		return res;
	}

	private static List<String> findNumberStr(String str) {
		String numList = "0123456789";
		List<String> resList = new ArrayList<String>();
		resList.addAll(findNumberStr(str, numList));
		return resList;
	}

	private static List<String> findNumberStr(String str, String numList) {
		List<String> resList = new ArrayList<String>();
		String resStr = "";
		int length = str.length();
		for (int i = 0; i < length; i++) {
			String ss = str.substring(i, i + 1);
			if (numList.indexOf(ss) >= 0) {
				resStr += ss;
			} else {
				if (!resStr.isEmpty()) {
					resList.add(resStr);
					resStr = "";
				}
			}
		}
		if (!resStr.isEmpty()) {
			resList.add(resStr);
			resStr = "";
		}
		return resList;
	}
	
	//Set & Get ///////////////////////////////////////////////	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getLocalAction() {
		return localAction;
	}

	public void setLocalAction(String localAction) {
		this.localAction = localAction;
	}
}
