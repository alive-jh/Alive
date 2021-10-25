package com.wechat.util.script;

public class Resource {
	private String srcUrl;
	private String cnUrl;
	private String expUrl;
	private String mediaInfo; //分句配置文件路径
	private String picLib; //分页识别文件路径
	
	public Resource(String src, String cn, String exp, String mediainfo, String picrecog) {
		this.srcUrl = src;
		this.cnUrl = cn;
		this.expUrl = exp;
		this.mediaInfo = mediainfo;
		this.picLib = picrecog;
	}
	
//	private Resource(Builder builder) {
//		this.id = builder.id;
//		this.srcUrl = builder.srcUrl;
//		this.cnUrl = builder.cnUrl;
//		this.expUrl = builder.expUrl;
//		this.mediaInfo = builder.mediaInfo;
//		this.picLib = builder.picLib;
//	}
	
	String getSrcUrl() {
		return srcUrl;
	}
	void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}
	String getCnUrl() {
		return cnUrl;
	}
	void setCnUrl(String cnUrl) {
		this.cnUrl = cnUrl;
	}
	String getExpUrl() {
		return expUrl;
	}
	void setExpUrl(String expUrl) {
		this.expUrl = expUrl;
	}
	String getMediaInfo() {
		return mediaInfo;
	}
	void setMediaInfo(String mediaInfo) {
		this.mediaInfo = mediaInfo;
	}
	String getPicLib() {
		return picLib;
	}
	void setPicLib(String picLib) {
		this.picLib = picLib;
	}
	
//	static class Builder {
//		private String id;
//		private String srcUrl;
//		private String cnUrl;
//		private String expUrl;
//		private String mediaInfo; //分句配置文件路径
//		private String picLib; //分页识别文件路径
//		
//		/**
//		 * @throws IllegalArgumentException id是null或者""的时候抛出
//		 * */
//		Builder(String id) {
//			if (id == null || id.isEmpty()) {
//				throw new IllegalArgumentException("Resource Id can not be " + (id == null ? "Null" : "Empty"));
//			}
//			this.id = id;
////			this.srcUrl = "";
////			this.cnUrl = "";
////			this.expUrl = "";
////			this.mediaInfo = "";
////			this.picLib = "";
//		}
//		
//		Builder srcUrl(String srcUrl) {
//			this.srcUrl = srcUrl;
//			return this;
//		}
//		
//		Builder cnUrl(String cnUrl) {
//			this.cnUrl = cnUrl;
//			return this;
//		}
//		
//		Builder expUrl(String expUrl) {
//			this.expUrl = expUrl;
//			return this;
//		}
//		
//		Builder mediaInfo(String mediaInfo) {
//			this.mediaInfo = mediaInfo;
//			return this;
//		}
//		
//		Builder picLib(String picLib) {
//			this.picLib = picLib;
//			return this;
//		}
//		
//		Resource build() {
//			return new Resource(this);
//		}
//	}
}
