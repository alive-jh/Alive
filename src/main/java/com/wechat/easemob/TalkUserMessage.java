package com.wechat.easemob;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TalkUserMessage {
	private Boolean activated;
	private Long created;
	private Long modified;
	private String nickname;
	private String share_secret;
	private String type;
	private String username;
	private String uuid;

	
	public Boolean getActivated() {
		return activated;
	}
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public Long getModified() {
		return modified;
	}
	public void setModified(Long modified) {
		this.modified = modified;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getShare_secret() {
		return share_secret;
	}
	public void setShare_secret(String share_secret) {
		this.share_secret = share_secret;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public static void main(String[] args) {
		
		String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(2885092);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        //System.out.println(res);
	}

}
