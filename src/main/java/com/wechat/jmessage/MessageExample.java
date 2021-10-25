package com.wechat.jmessage;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.message.MessageResult;
import cn.jmessage.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import cn.jmessage.api.common.model.MessageBody;
//import cn.jmessage.api.common.model.MessagePayload;

public class MessageExample {

    protected static final Logger LOG = LoggerFactory.getLogger(MessageExample.class);

    private static final String appkey = "cb86ac84bf6bda5618bec75c";
    private static final String masterSecret = "328620e1ae4ec217328e39e3";

    public static void main(String[] args) throws Exception {
//
//    	//FD_EPAL_ACCOUNT:o8212w2oow3
//    	JMessage jMessage = new JMessage();
//
//    	jMessage.setAccount("13612767902");
//    	jMessage.setCreadaDate(new Date().getTime());
//    	jMessage.setType(0);
//    	testSendSingleTextByAdmin(jMessage);//发送消息
//    	//Thread.sleep(5000);
//    	//testGetUserMessageList();//接受消息
    }

    /**
     * Send single text message by admin, this method will invoke sendMessage() in JMessageClient eventually, whose 
     * parameters are as list:
     */
    public static void testSendSingleTextByAdmin(JMessage jMessage) {
//        JMessageClient client = new JMessageClient(appkey, masterSecret);
//
//        try {
//
//
//
//        	JSONObject jsonObject = new JSONObject();
//        	jsonObject.put("data", jMessage);
//
//        	//System.out.println("jsonStr = "+jsonObject.toString());
////            MessageBody body = MessageBody.text(jsonObject.toString());
//
////            SendMessageResult result = client.sendSingleTextByAdmin(jMessage.getEpalId(), "admin", body);
//
////            //System.out.println(" === "+String.valueOf(result.getMsg_id()));
//        } catch (APIConnectionException e) {
//            //System.out.println("Connection error. Should retry later. ");
//        } catch (APIRequestException e) {
//            //System.out.println("Error response from JPush server. Should review and fix it. ");
//            //System.out.println("HTTP Status: " + e.getStatus());
//            //System.out.println("Error Message: " + e.getMessage());
//        }
    }
    
    /**
     * Send group text message by admin
     */
//    public static void testSendGroupTextByAdmin() {
//    	JMessageClient client = new JMessageClient(appkey, masterSecret);
//
//    	try {
//    		MessageBody body = MessageBody.text("Hello World!");
//    		SendMessageResult result = client.sendGroupTextByAdmin("targetUserName", "fromUserName", body);
//    		//System.out.println(String.valueOf(result.getMsg_id()));
//    	} catch (APIConnectionException e) {
//            //System.out.println("Connection error. Should retry later. ");
//        } catch (APIRequestException e) {
//            //System.out.println("Error response from JPush server. Should review and fix it. ");
//            //System.out.println("HTTP Status: " + e.getStatus());
//            //System.out.println("Error Message: " + e.getMessage());
//        }
//    }

//    public static void testSendImageMessage() {
//        JMessageClient client = new JMessageClient(appkey, masterSecret);
//        MessageBody messageBody = new MessageBody.Builder()
//                .setMediaId("qiniu/image/r/A92D550D57464CDF5ADC0D79FBD46210")
//                .setMediaCrc32(4258069839L)
//                .setWidth(43)
//                .setHeight(44)
//                .setFormat("png")
//                .setFsize(2670)
//                .build();
//
//        MessagePayload payload = MessagePayload.newBuilder()
//                .setVersion(1)
//                .setTargetType("single")
//                .setTargetId("test_user1")
//                .setFromType("admin")
//                .setFromId("junit_admin")
//                .setMessageType(MessageType.IMAGE)
//                .setMessageBody(messageBody)
//                .build();
//
//        try {
//            SendMessageResult res = client.sendMessage(payload);
//            //System.out.println(res.getMsg_id());
//        } catch (APIConnectionException e) {
//            //System.out.println("Connection error. Should retry later. ");
//        } catch (APIRequestException e) {
//            //System.out.println("Error response from JPush server. Should review and fix it. ");
//            //System.out.println("HTTP Status: " + e.getStatus());
//            //System.out.println("Error Message: " + e.getMessage());
//        }
//
//    }

    /**
     * Get message list without cursor(first time), will return cursor, the later request will
     * use cursor to get messages.
     */
    public static void testGetMessageList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            MessageListResult result = client.getMessageList(10, "2016-09-08 10:10:10", "2016-09-15 10:10:10");
            String cursor = result.getCursor();
            if (null != cursor && StringUtils.isNotEmpty(cursor)) {
                MessageResult[] messages = result.getMessages();
                MessageListResult secondResult = client.getMessageListByCursor(cursor);
                MessageResult[] secondMessages = secondResult.getMessages();
            }
        } catch (APIConnectionException e) {
            //System.out.println("Connection error. Should retry later. ");
        } catch (APIRequestException e) {
            //System.out.println("Error response from JPush server. Should review and fix it. ");
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserMessageList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            MessageListResult result = client.getUserMessages("admin", 100, "2017-04-26 10:00:10", "2017-04-26 23:49:59");
            //System.out.println(" count = "+result.getCount());
            //System.out.println(" Total = "+result.getTotal());
            
            for (int i = 0; i < result.getMessages().length; i++) {
            	
            	 //System.out.println(" Messages "+i+" = "+result.getMessages()[i].getMsgBody().getText());
			}
           
           
           
    		
//    		JSONObject jobj =  JSONObject.fromObject(result.getMessages()[result.getMessages().length-1].getMsgBody().getText());
//    		//System.out.println("data = "+jobj.toString());
//    		JSONObject jsonObject = new JSONObject();
//    		jsonObject = (JSONObject) jobj.get("data");	
//    		
//    		
//    		
//    		//System.out.println("data = "+jsonObject.toString());
//    		//System.out.println("data = "+jsonObject.get("account"));
    		
            String cursor = result.getCursor();
            MessageListResult secondResult = client.getUserMessagesByCursor("admin", cursor);
            
            
        } catch (APIConnectionException e) {
            //System.out.println("Connection error. Should retry later. ");
        } catch (APIRequestException e) {
            //System.out.println("Error response from JPush server. Should review and fix it. ");
            //System.out.println("HTTP Status: " + e.getStatus());
            //System.out.println("Error Message: " + e.getMessage());
        }
    }
}
