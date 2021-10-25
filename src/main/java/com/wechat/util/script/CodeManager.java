package com.wechat.util.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeManager {
	/**
	 * description：把十六进制Unicode编码字符串转换为中文字符串(assic码字符不转换)
	 * @param str
	 * @return
	 */
	public String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}
	
	/**
	 * description：把中文字符串转换为十六进制Unicode编码字符串(assic码字符不转换)
	 * @param s
	 * @return
	 */
	public String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			if (ch > 255) {
				str += "\\u" + Integer.toHexString(ch);
			} else {
				str += s.substring(i, i + 1);
			}
		}
		return str;
	}
	
	//Init//////////////////////////////////////////////////////////
	private static CodeManager instance;
	public static CodeManager getInstance(){
		if (instance == null) {
			instance = new CodeManager();
		}
		return instance;
	}
	
	private CodeManager(){
		
	}
}
