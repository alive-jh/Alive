package com.wechat.mp3;


import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Map;

public class ProxyManager {
	private static final String proxyCfg = "config.ini";
	
	private final String IS_PROXY_ENABLE = "isProxyEnable";
	private final String PROXY_HOST = "proxyHost";
	private final String PROXY_PORT = "proxyPort";
	private final String PROXY_USR = "username";
	private final String PROXY_PWD = "password";
	
	private int isProxyEnable = 0;
	private String proxyHost = "";
	private String proxyPort = "";
	private String username = "";
	private String password = "";
	private Map<String, String> proxyMap = new HashMap<String, String>();
	private void setProxy(String proxyHost, String proxyPort){
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);
	}
	
	private String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	public void initProxy(){
		//////isProxyEnable///////////////////////////////
		String tmp = proxyMap.get(IS_PROXY_ENABLE);
		if (tmp == null) {
			tmp = "0";
		}
		try {
			isProxyEnable = Integer.parseInt(tmp);
		} catch (NumberFormatException e) {
			isProxyEnable = 0;
		}
		//////proxyHost///////////////////////////////
		proxyHost = proxyMap.get(PROXY_HOST);
		if (proxyHost == null) {
			proxyHost = "";
		}
		//////proxyHost///////////////////////////////
		proxyPort = proxyMap.get(PROXY_PORT);
		if (proxyPort == null) {
			proxyPort = "";
		}
		//////username///////////////////////////////
		username = proxyMap.get(PROXY_USR);
		if (username == null) {
			username = "";
		}
		//////proxyHost///////////////////////////////
		password = proxyMap.get(PROXY_PWD);
		if (password == null) {
			password = "";
		}
		
		////////////////////////////////////////////////
		if (isProxyEnable > 0) {
			setProxy(proxyHost, proxyPort);
		}
		
		saveProxyToCfg();
	}
	
	public void setProxy(int isEnable, String host, String port, String usr, String pwd){
		setIsProxyEnable(isEnable);
		setProxyHost(host);
		setProxyPort(port);
		setUsername(usr);
		setPassword(pwd);
		
		if (isProxyEnable > 0) {
			setProxy(proxyHost, proxyPort);
		}else {
			setProxy("", "");
		}
		saveProxyToCfg();
	}
	
	private void saveProxyToCfg(){
		if (proxyMap == null) {
			proxyMap = new HashMap<String, String>();
		}
		String cfgStr = "";
		String[] keySet = {IS_PROXY_ENABLE,PROXY_HOST,PROXY_PORT,PROXY_USR,PROXY_PWD};
		for(String key:keySet){
			String value = proxyMap.get(key);
			if (value == null) {
				if(key.equals(IS_PROXY_ENABLE))
					value = "0";
				else
					value = "";
			}
			cfgStr = cfgStr + key + ":" + value + "\r\n";
		}
	}

	public String getEncode(){
		if(username.trim().isEmpty()){
			return "";
		}
		String encoded = new String(base64Encode(new String(username+":"+password).getBytes()));
		return encoded;
	}
	public int getIsProxyEnable() {
		return isProxyEnable;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setIsProxyEnable(int isProxyEnable) {
		this.isProxyEnable = isProxyEnable;
		proxyMap.put(IS_PROXY_ENABLE, String.valueOf(isProxyEnable));
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
		proxyMap.put(PROXY_HOST, proxyHost);
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
		proxyMap.put(PROXY_PORT, proxyPort);
	}

	public void setUsername(String username) {
		this.username = username;
		proxyMap.put(PROXY_USR, username);
	}

	public void setPassword(String password) {
		this.password = password;
		proxyMap.put(PROXY_PWD, password);
	}
	
	//Init//////////////////////////////////////////////////////
	private static ProxyManager instance;
	public static ProxyManager getInstance(){
		if (instance == null) {
			instance = new ProxyManager();
		}
		return instance;
	}
}
