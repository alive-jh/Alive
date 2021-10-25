package com.wechat.jmessage;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.NoDisturbPayload;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;
import cn.jmessage.api.user.UserStateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

//import cn.jmessage.api.common.model.FriendNote;

public class UserExample {

    protected static final Logger LOG = LoggerFactory.getLogger(UserExample.class);

    private static final String appkey = "cb86ac84bf6bda5618bec75c";
    private static final String masterSecret = "328620e1ae4ec217328e39e3";
    private static final long test_gid = 10004809;
    
    public static void main(String[] args) {
    	testUpdateUserInfo();
    	testGetUserInfo("test_user");
    	testGetUserState();
    	testGetUsers();
    	//System.out.println("bbbbbbbbb");
    }



    public static void testRegisterUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {

            List<RegisterInfo> users = new ArrayList<RegisterInfo>();

            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("test_user2")
                    .setPassword("test_pass2")
                    .build();

            RegisterInfo user1 = RegisterInfo.newBuilder()
                    .setUsername("test_user3")
                    .setPassword("test_pass3")
                    .build();

            users.add(user);
            users.add(user1);

            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            String res = client.registerUsers(users.toArray(regUsers));
            //System.out.println(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserInfo(String account) {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
        	
            UserInfoResult res = client.getUserInfo(account);
            //System.out.println(res.getUsername());
            //System.out.println(res.getNickname());
            //System.out.println(res.getBirthday());
            
           
           
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserState() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            UserStateResult result = client.getUserState("test_user");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdatePassword() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateUserPassword("test_user", "test_new_pass");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdateUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateUserInfo("test_user", "test_nick", "2000-01-12", "help me!", 1, "shenzhen", "nanshan");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            UserListResult res = client.getUserList(0, 30);
            //System.out.println(res.getOriginalContent());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testDeleteUser() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.deleteUser("test_user_119");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }
    
    /**
     * Get admins by appkey
     */
    public static void testGetAdminListByAppkey() {
    	JMessageClient client = new JMessageClient(appkey, masterSecret);
    	try {
			UserListResult res = client.getAdminListByAppkey(0, 1);
			//System.out.println(res.getOriginalContent());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
		}
    }

    public static void testGetBlackList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            UserInfoResult[] result = client.getBlackList("username");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testRemoveBlacklist() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.removeBlacklist("test_user", "test_user1", "test_user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testAddBlackList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.addBlackList("username", "user1", "user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testSetNoDisturb() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            NoDisturbPayload payload = new NoDisturbPayload.Builder()
                    .setAddSingleUsers("test_user1", "test_user2")
                    .setAddGroupIds(test_gid)
                    .build();
            ResponseWrapper response = client.setNoDisturb("test_user", payload);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testAddFriends() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.addFriends("test_user", "test_user1", "test_user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testDeleteFriends() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.deleteFriends("test_user", "test_user1", "test_user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

//    public static void testUpdateFriendsNote() {
//        JMessageClient client = new JMessageClient(appkey, masterSecret);
//        try {
//            List<FriendNote> friendNotes = new ArrayList<FriendNote>();
//            FriendNote friendNote1 = new FriendNote.Builder()
//                    .setNoteName("note name 1")
//                    .setOthers("test")
//                    .setUsername("test_user1")
//                    .builder();
//            FriendNote friendNote2 = new FriendNote.Builder()
//                    .setNoteName("note name 2")
//                    .setOthers("test")
//                    .setUsername("test_user2")
//                    .builder();
//            friendNotes.add(friendNote1);
//            friendNotes.add(friendNote2);
//            FriendNote[] array = new FriendNote[friendNotes.size()];
//            ResponseWrapper result = client.updateFriendsNote("test_user", friendNotes.toArray(array));
//        } catch (APIConnectionException e) {
//            LOG.error("Connection error. Should retry later. ", e);
//        } catch (APIRequestException e) {
//            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//            //System.out.println("HTTP Status: " + e.getStatus());
//            //System.out.println("Error Message: " + e.getMessage());
//        }
//    }

    public static void testGetFriends() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            UserInfoResult[] userInfoArray = client.getFriendsInfo("test_user");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

}

