package com.wechat.entity;

import java.util.Arrays;

public class EasemobGroupChat {

	private String groupName;
	private String desc;
	private boolean apublic;
	private int maxusers = 200;
	private boolean membersOnly;
	private boolean allowinvites;
	private String owner;
	
	private String id;
	private int affiliationsCount;
	
	
	private String[] members;

	public EasemobGroupChat() {

	}

	public EasemobGroupChat(String groupName, String desc, boolean apublic, int maxusers, boolean membersOnly,
			boolean allowinvites, String owner, String id, int affiliationsCount, String[] members) {
		super();
		this.groupName = groupName;
		this.desc = desc;
		this.apublic = apublic;
		this.maxusers = maxusers;
		this.membersOnly = membersOnly;
		this.allowinvites = allowinvites;
		this.owner = owner;
		this.id = id;
		this.affiliationsCount = affiliationsCount;
		this.members = members;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isApublic() {
		return apublic;
	}

	public void setApublic(boolean apublic) {
		this.apublic = apublic;
	}

	public int getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public boolean isMembersOnly() {
		return membersOnly;
	}

	public void setMembersOnly(boolean membersOnly) {
		this.membersOnly = membersOnly;
	}

	public boolean isAllowinvites() {
		return allowinvites;
	}

	public void setAllowinvites(boolean allowinvites) {
		this.allowinvites = allowinvites;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAffiliationsCount() {
		return affiliationsCount;
	}

	public void setAffiliationsCount(int affiliationsCount) {
		this.affiliationsCount = affiliationsCount;
	}

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "EasemobGroupChat [groupName=" + groupName + ", desc=" + desc + ", apublic=" + apublic + ", maxusers="
				+ maxusers + ", membersOnly=" + membersOnly + ", allowinvites=" + allowinvites + ", owner=" + owner
				+ ", id=" + id + ", affiliationsCount=" + affiliationsCount + ", members=" + Arrays.toString(members)
				+ "]";
	}

	

	
	
	

}
