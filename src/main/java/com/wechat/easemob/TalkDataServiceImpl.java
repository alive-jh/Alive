package com.wechat.easemob;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TalkDataServiceImpl implements TalkDataService {
	private TalkHttpService service;
	private Authentic auth;
	//TODO @Autowired
	public TalkDataServiceImpl(TalkHttpService service) {
		this.service = service;
		this.auth = new Authentic(service);
	}
	@Override
	public void setToken(Authentic.Token token) {
		this.auth = new Authentic(service,token);
	}
	@Override
	public TalkNode login(String username, String password) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("grant_type","password");
		param.put("username",username);
		param.put("password",password);
		return service.request(EasemobKey.URR_TOKEN,EasemobKey.HTTP_METHOD_POST,param,null,null);
	}
	@Override
	public TalkNode logout(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/disconnect",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_GROUP_FRIEND);
	}
	@Override
	public TalkNode userSave(String username, String password, String nickname) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("username",username);
		param.put("password",password);
		param.put("nickname",nickname);
		return service.request(EasemobKey.URL_USER,EasemobKey.HTTP_METHOD_POST,param,auth,null);
	}
	@Override
	public TalkNode userSave(String[]username, String[]password, String[]nickname) throws Exception {
		if(username==null||password==null||username.length!=password.length||(nickname!=null&&username.length!=nickname.length)){
			return null;
		}
		if(nickname==null){
			nickname = new String[username.length];
		}
		List<Map<String,Object>>param = new ArrayList<Map<String,Object>>();
		for(int i=0;i<username.length;i++){
			Map<String,Object>map = new HashMap<String,Object>();
			map.put("username",username[i]);
			map.put("password",password[i]);
			map.put("nickname",nickname[i]);
			param.add(map);
		}
		return service.request(EasemobKey.URL_USER,EasemobKey.HTTP_METHOD_POST,param,auth,null);
	}
	@Override
	public TalkNode userDrop(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username,EasemobKey.HTTP_METHOD_DELETE,null,auth,null);
	}
	@Override
	public TalkNode userDrop(Long size) throws Exception {
		return service.request(EasemobKey.URL_USER+"?limit="+(size!=null?size:EasemobKey.APP_PAGE_SIZE),EasemobKey.HTTP_METHOD_DELETE,null,auth,null);
	}
	@Override
	public TalkNode userModifyAccess(String username, Boolean access) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/"+(access!=null&&access?"activate":"deactivate"),EasemobKey.HTTP_METHOD_POST,null,auth,null);
	}
	@Override
	public TalkNode userModifyPassword(String username, String password) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("newpassword",password);
		return service.request(EasemobKey.URL_USER+"/"+username+"/password",EasemobKey.HTTP_METHOD_PUT,param,auth,null);
	}
	@Override
	public TalkNode userModifyNickname(String username, String nickname) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("nickname",nickname);
		return service.request(EasemobKey.URL_USER+"/"+username,EasemobKey.HTTP_METHOD_PUT,param,auth,null);
	}
	@Override
	public TalkNode userGet(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username,EasemobKey.HTTP_METHOD_GET,null,auth,null);
	}
	@Override
	public TalkNode userList(Long size, String cursor, Long start) throws Exception {
		return service.request(EasemobKey.URL_USER+"?"+this.page(size,cursor,start),EasemobKey.HTTP_METHOD_GET,null,auth,null);
	}
	@Override
	public TalkNode userLine(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/status",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_MAP);
	}
	@Override
	public TalkNode userGroupList(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/joined_chatgroups",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_GROUP_LIST);
	}
	@Override
	public TalkNode userRoomList(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/joined_chatrooms",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_ROOM_LIST);
	}
	@Override
	public TalkNode userMessageCount(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/offline_msg_count",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_MAP);
	}
	@Override
	public TalkNode userMessageLine(String username, String id) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/offline_msg_status/"+id,EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_MAP);
	}
	@Override
	public TalkNode friendSave(String username, String friend) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/contacts/users/"+friend,EasemobKey.HTTP_METHOD_POST,null,auth,null);
	}
	@Override
	public TalkNode friendDrop(String username, String friend) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/contacts/users/"+friend,EasemobKey.HTTP_METHOD_DELETE,null,auth,null);
	}
	@Override
	public TalkNode friendList(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/contacts/users",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_ARRAY);
	}
	@Override
	public TalkNode blockSave(String username, String[]friend) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("usernames",friend);
		return service.request(EasemobKey.URL_USER+"/"+username+"/blocks/users",EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA_ARRAY);
	}
	@Override
	public TalkNode blockDrop(String username, String friend) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/blocks/users/"+friend,EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_ARRAY);
	}
	@Override
	public TalkNode blockList(String username) throws Exception {
		return service.request(EasemobKey.URL_USER+"/"+username+"/blocks/users",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_ARRAY);
	}
	@Override
	public TalkNode groupSave(String owner, String name, String desc, Integer limit, String[]friend) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("groupname",name);
		param.put("desc",desc);
		param.put("public",true);
		param.put("maxusers",limit);
		param.put("approval",true);
		param.put("owner",owner);
		param.put("members",friend);
		return service.request(EasemobKey.URL_GROUP,EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA_GROUP);
	}
	@Override
	public TalkNode groupDrop(String id) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id,EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_GROUP);
	}
	@Override
	public TalkNode groupModify(String id, String name, String desc, Integer limit) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("groupname",name);
		param.put("description",desc);
		param.put("maxusers",limit);
		return service.request(EasemobKey.URL_GROUP+"/"+id,EasemobKey.HTTP_METHOD_PUT,param,auth,TalkNode.DATA_GROUP_UPDATE);
	}
	@Override
	public TalkNode groupModifyOwner(String id, String owner) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("newowner",owner);
		return service.request(EasemobKey.URL_GROUP+"/"+id,EasemobKey.HTTP_METHOD_PUT,param,auth,TalkNode.DATA_GROUP_OWNER);
	}
	@Override
	public TalkNode groupGet(String[] id) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+this.param(id),EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_GROUP_LIST_NEW);
	}
	@Override
	public TalkNode groupList(Long size, String cursor, Long start) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"?"+this.page(size,cursor,start),EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_GROUP_LIST);
	}
	@Override
	public TalkNode groupFriendSave(String id, String friend) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/users/"+friend,EasemobKey.HTTP_METHOD_POST,null,auth,TalkNode.DATA_GROUP_FRIEND);
	}
	@Override
	public TalkNode groupFriendSave(String id, String[]friend) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("usernames",friend);
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/users",EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA_GROUP_FRIEND);
	}
	@Override
	public TalkNode groupFriendDrop(String id, String friend) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/users/"+friend,EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_GROUP_FRIEND);
	}
	@Override
	public TalkNode groupFriendDrop(String id, String[]friend) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/users/"+this.param(friend),EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_GROUP_FRIEND_LIST);
	}
	@Override
	public TalkNode groupFriendList(String id) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/users",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_GROUP_LIST_MEMBER);
	}
	@Override
	public TalkNode groupBlackSave(String id, String friend) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/blocks/users/"+friend,EasemobKey.HTTP_METHOD_POST,null,auth,TalkNode.DATA_GROUP_FRIEND);
	}
	@Override
	public TalkNode groupBlackSave(String id, String[]friend) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("usernames",friend);
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/blocks/users",EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA_GROUP_FRIEND_LIST);
	}
	@Override
	public TalkNode groupBlackDrop(String id, String friend) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/blocks/users/"+friend,EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_GROUP_FRIEND);
	}
	@Override
	public TalkNode groupBlackDrop(String id, String[]friend) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/blocks/users/"+this.param(friend),EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_GROUP_FRIEND_LIST);
	}
	@Override
	public TalkNode groupBlackList(String id) throws Exception {
		return service.request(EasemobKey.URL_GROUP+"/"+id+"/blocks/users",EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_ARRAY);
	}
	@Override
	public TalkNode fileUpload(File file) throws Exception {
		return service.upload(EasemobKey.URL_FILE,file,auth,TalkNode.DATA_ENTITIES);
	}
	@Override
	public void fileDown(String id, String share, Boolean thumb, File file) throws Exception {
		Map<String,String>header = new HashMap<String,String>();
		header.put("Accept","application/octet-stream");
		if(share!=null){
			header.put("share-secret",share);
		}
		if(thumb!=null&&thumb){
			header.put("thumbnail",String.valueOf(thumb));
		}
		service.downLoad(EasemobKey.URL_FILE+"/"+id,file,auth,header);
	}
	@Override
	public TalkNode messageSave(TalkMsg data) throws Exception {
		String type = data.getMessageType();
		if(data.getTargetType()==null||type==null||data.getFrom()==null||data.getTarget()==null){
			return null;
		}
		Map<String,Object>msg = new HashMap<String,Object>();
		switch(data.getType()){
			case 1:
				msg.put("type",type);
				msg.put("msg",data.getMsg());
				break;
			case 2:
				msg.put("type",type);
//				msg.put("url","https://a1.easemob.com/993006816/chanew/chatfiles/56adabb0-68f4-11e6-96ec-0bd6f1b5c14d");
//				msg.put("url",EasemobKey.URL_FILE+"/"+data.getFile_id());
				msg.put("url",data.getUrl());
				msg.put("filename",data.getFilename());
				msg.put("secret",data.getSecret());
				Map<String,Integer>temp = new HashMap<String,Integer>();
				temp.put("width",data.getWidth());
				temp.put("height",data.getHeight());
				msg.put("size",temp);
				break;
			case 3:
				msg.put("type",type);
				/*msg.put("url",EasemobKey.URL_FILE+"/"+data.getFile_id());*/
				msg.put("url",data.getUrl());
				msg.put("filename",data.getFilename());
				msg.put("secret",data.getSecret());
				msg.put("length",data.getLength());
				break;
			case 4:
				msg.put("type",type);
				msg.put("filename",data.getFilename());
				msg.put("thumb",EasemobKey.URL_FILE+"/"+data.getThumb());
				msg.put("length",data.getLength());
				msg.put("secret",data.getSecret());
				msg.put("file_length",data.getFile_length());
				msg.put("thumb_secret",data.getThumb_secret());
				/*msg.put("url",EasemobKey.URL_FILE+"/"+data.getFile_id());*/
				msg.put("url",data.getUrl());
				break;
			case 5:
				msg.put("type",type);
				msg.put("action",data.getAction());
				break;
		}
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("target_type",data.getTargetType());
		param.put("from",data.getFrom());
		param.put("target",data.getTarget());
		param.put("msg",msg);
		param.put("ext",data.getExt());
		return service.request(EasemobKey.URL_MESSAGES,EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA);//2016-8-23
	}
	@Override
	public TalkNode messageList(Long size, String cursor, Long start) throws Exception {
		return service.request(EasemobKey.URL_MESSAGES+"?"+this.page(size,cursor,start),EasemobKey.HTTP_METHOD_GET,null,auth,null);
	}
	@Override
	public TalkNode chatList(Long size, String cursor, Long start) throws Exception {
		return service.request(EasemobKey.URL_CHAT+"?"+this.page(size,cursor,start),EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_CHAT_LIST);
	}
	@Override
	public TalkNode roomSave(String owner, String name, String desc, Integer limit, String[]friend) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("name",name);
		param.put("description",desc);
		param.put("maxusers",limit);
		param.put("owner",owner);
		param.put("members",friend);
		return service.request(EasemobKey.URL_ROOM,EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA_ROOM);
	}
	@Override
	public TalkNode roomModify(String id, String name, String desc, Integer limit) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("name",name);
		param.put("description",desc);
		param.put("maxusers",limit);
		return service.request(EasemobKey.URL_ROOM+"/"+id,EasemobKey.HTTP_METHOD_PUT,param,auth,TalkNode.DATA_ROOM_REDO);
	}
	@Override
	public TalkNode roomDrop(String id) throws Exception {
		return service.request(EasemobKey.URL_ROOM+"/"+id,EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_ROOM_REDO);
	}
	@Override
	public TalkNode roomGet(String id) throws Exception {
		return service.request(EasemobKey.URL_ROOM+"/"+id,EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_ROOM_LIST);
	}
	@Override
	public TalkNode roomList() throws Exception {
		return service.request(EasemobKey.URL_ROOM,EasemobKey.HTTP_METHOD_GET,null,auth,TalkNode.DATA_ROOM_LIST);
	}
	@Override
	public TalkNode roomFriendSave(String id, String friend) throws Exception {
		return service.request(EasemobKey.URL_ROOM+"/"+id+"/users/"+friend,EasemobKey.HTTP_METHOD_POST,null,auth,TalkNode.DATA_ROOM_REDO);
	}
	@Override
	public TalkNode roomFriendSave(String id, String[] friend) throws Exception {
		Map<String,Object>param = new HashMap<String,Object>();
		param.put("usernames",friend);
		return service.request(EasemobKey.URL_ROOM+"/"+id+"/users",EasemobKey.HTTP_METHOD_POST,param,auth,TalkNode.DATA_ROOM_REDO);
	}
	@Override
	public TalkNode roomFriendDrop(String id, String friend) throws Exception {
		return service.request(EasemobKey.URL_ROOM+"/"+id+"/users/"+friend,EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_ROOM_REDO);
	}
	@Override
	public TalkNode roomFriendDrop(String id, String[] friend) throws Exception {
		return service.request(EasemobKey.URL_ROOM+"/"+id+"/users/"+this.param(friend),EasemobKey.HTTP_METHOD_DELETE,null,auth,TalkNode.DATA_ROOM_REDO_LIST);
	}
	private String param(String[]data) {
		StringBuffer param = new StringBuffer();
		for(String temp:data){
			param.append(temp+",");
		}
		return param.deleteCharAt(param.length()-1).toString();
	}
	private String page(Long size, String cursor, Long start) throws Exception {
		StringBuffer res = new StringBuffer("limit="+(size!=null?size:EasemobKey.APP_PAGE_SIZE));
		if(cursor!=null){
			res.append("&cursor="+cursor);
		}
		if(start!=null){
			Long now = System.currentTimeMillis();
			res.append("&ql=select * where timestamp>"+(now-start)+" and timestamp<"+now);
		}
		return URLEncoder.encode(res.toString(),"UTF-8");
	}
}