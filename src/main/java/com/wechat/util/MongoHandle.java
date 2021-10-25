package com.wechat.util;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.regex.Pattern;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.bson.*;
//import org.bson.types.ObjectId;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoClientURI;
//import com.mongodb.QueryOperators;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;

public class MongoHandle {
//	private String HOST = "183.60.136.50";
//    //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待  
// 
//	private MongoClient mongoClient = null; 
//        
//	private MongoCollection<Document> collection = null;
//	
//	public MongoHandle(String Host,String Collection,int port){  
//		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
//	    build.threadsAllowedToBlockForConnectionMultiplier(50);  
//	    build.connectTimeout(1*60*1000);  
//	    build.maxWaitTime(2*60*1000);  
//	    MongoClientOptions options = build.build();  
//	    MongoClient mongoClient = null; 
//        MongoClientURI uri = new MongoClientURI("mongodb://fandou:freedom3322@183.60.136.50:27781/?authSource=fandou",build);
//        mongoClient = new MongoClient(uri);  
//        MongoDatabase mongoDatabase = mongoClient.getDatabase("fandou");  
//        this.collection = mongoDatabase.getCollection(Collection);
//
//	}  
//	
//	public MongoHandle(String DBName,String Collection){  
//		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
//	    build.threadsAllowedToBlockForConnectionMultiplier(50);  
//	    build.connectTimeout(1*60*1000);  
//	    build.maxWaitTime(2*60*1000);  
//	    MongoClientOptions options = build.build();  
//	    MongoClient mongoClient = null; 
//        MongoClientURI uri = new MongoClientURI("mongodb://fandou:freedom3322@183.60.136.50:27781/?authSource=fandou",build);
//        mongoClient = new MongoClient(uri);  
//        MongoDatabase mongoDatabase = mongoClient.getDatabase(DBName);  
//        this.collection = mongoDatabase.getCollection(Collection);
//		
//	}
//
//
//	// 指定 服务器地址；数据库名称； 文档名称
//	public MongoHandle(String Host,String DBName,String Collection){  
//		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
//	    build.threadsAllowedToBlockForConnectionMultiplier(50);  
//	    build.connectTimeout(1*60*1000);  
//	    build.maxWaitTime(2*60*1000);  
//	    MongoClientOptions options = build.build();  
//	    MongoClient mongoClient = null; 
//        MongoClientURI uri = new MongoClientURI("mongodb://fandou:freedom3322@183.60.136.50:27781/?authSource=fandou",build);
//        mongoClient = new MongoClient(uri);  
//        MongoDatabase mongoDatabase = mongoClient.getDatabase(DBName);  
//        this.collection = mongoDatabase.getCollection(Collection);
//	}  
//
//	 // 指定 服务器地址；数据库名称； 文档名称
//	public MongoHandle(){  
//		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
//	    build.threadsAllowedToBlockForConnectionMultiplier(50);  
//	    build.connectTimeout(1*60*1000);  
//	    build.maxWaitTime(2*60*1000);  
//	    MongoClientOptions options = build.build();  
//	    MongoClient mongoClient = null; 
//       MongoClientURI uri = new MongoClientURI("mongodb://fandou:freedom3322@183.60.136.50:27781/?authSource=fandou",build);
//       mongoClient = new MongoClient(uri);  
//       MongoDatabase mongoDatabase = mongoClient.getDatabase("fandou");  
//       this.collection = mongoDatabase.getCollection("data");
//	} 
//	
//	public JSONArray getHistorySchedules(JSONObject parameter){
//		JSONArray data = new JSONArray();
//
//		long startTime = Long.parseLong(parameter.get("startTime").toString());
//		long endTime = Long.parseLong(parameter.get("endTime").toString());
//		String epalId = parameter.get("epalId").toString();
//		BasicDBObject queryObject = new BasicDBObject()
//			.append("exe_time",  new BasicDBObject().append(QueryOperators.GTE, startTime))
//			.append("exe_time",  new BasicDBObject().append(QueryOperators.LT, endTime))
//			.append("epalId",  epalId); 
//		FindIterable<Document> findIterable = this.collection.find();  
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		info.put("id",info.get("_id").toString());
//   	 		info.remove("_id");
//   	 		data.add(info);
//   	 	}  
//
//		return data;
//		
//	}
//	public JSONArray getUploadFileList(JSONObject parameter) {
//		JSONArray data = new JSONArray();
//		
//		String userId = parameter.get("userId").toString();
//		String type = parameter.get("type").toString();
//
//		BasicDBObject queryObject = new BasicDBObject()
//			.append("userId",  userId)
//			.append("type",  type); 
//   	 	FindIterable<Document> findIterable = this.collection.find(queryObject);  
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		info.put("id",info.get("_id").toString());
//   	 		info.remove("_id");
//   	 		data.add(info);
//   	 	}  
//
//		return data;
//	}
//	
//	
//	/*
//	 * 
//	 *  保存文档
//	 * 
//	 * 
//	 * */
//	public void saveDocument(JSONObject parameter) {
//		// TODO Auto-generated method stub
//	   	Document document = Document.parse(parameter.toString());
//		List<Document> documents = new ArrayList<Document>();  
//		documents.add(document);  
//		this.collection.insertMany(documents);
//	}
//	
//	
//	/*
//	 * 
//	 *  删除文档，参数为id（字符串）
//	 * 
//	 * 
//	 * */
//	public void deleteDocument(JSONObject parameter) {
//		BasicDBObject queryObject = new BasicDBObject()
//		.append("_id",  new ObjectId(parameter.get("id").toString()));
//		this.collection.deleteOne(queryObject);
//		this.mongoClient.close();
//		
//	}
//
//	/*
//	 * 
//	 *  修改文档：
//	 *  参数：id 必须
//	 *  其他传修改的字段
//	 * 
//	 * 
//	 * */
//	public void updateDocument(JSONObject parameter) {
//		BasicDBObject queryObject = new BasicDBObject()
//		.append("_id",  new ObjectId(parameter.get("id").toString()));
//
//		BasicDBObject doc = new BasicDBObject();
//		doc.append("$set",  parameter);
//		this.collection.updateOne(queryObject,doc);
//		this.mongoClient.close();
//		
//	}
//	
//	
//	/*
//	 * 
//	 *  查询文档：
//	 * 
//	 *  其他传修改的字段
//	 * 
//	 * 
//	 * */
//	public JSONArray getDocument(JSONObject parameter) {
//		JSONArray data = new JSONArray();
//		BasicDBObject queryObject = new BasicDBObject(parameter);
//   	 	FindIterable<Document> findIterable = this.collection.find(queryObject);  
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		info.put("id",info.get("_id").toString());
//   	 		info.remove("_id");
//   	 		data.add(info);
//   	 	}  
//
//		return data;
//	}
//	
//	public static void main( String args[] ){
//	     try{   
//	       // 连接到 mongodb 服务
//	    	 MongoHandle MongoHandle_obj = new MongoHandle("fandou","test");
//
//	    	 
//	    //插入文档
//	    	 JSONObject data = new JSONObject();
//	    	 data.put("exe_time", 1348569923);
//	    	 data.put("picture", "");
//	    	 data.put("title", "测试3");
//	    	
//	    	MongoHandle_obj.saveDocument(data);
//	     }catch(Exception e){
//	    	 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//	     }
//	   }
//
//	public JSONArray getDocument(JSONObject parameter, JSONObject sortParameter) {
//		// 带排序参数，获取所有文档
//		JSONArray data = new JSONArray();
//		BasicDBObject queryObject = new BasicDBObject(parameter);
//		
//		BasicDBObject sortObject = new BasicDBObject(sortParameter);
//		
//   	 	FindIterable<Document> findIterable = this.collection.find(queryObject).sort(sortObject);  
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		info.put("id",info.get("_id").toString());
//   	 		info.remove("_id"); //结果中去除主键ID
//   	 		data.add(info);
//   	 	}  
//
//		return data;
//	}
//
//	public long getDocumentCount(JSONObject parameter){
//		BasicDBObject queryObject = new BasicDBObject(parameter);
//		long count = this.collection.count(queryObject);
//		return count;
//	}
//	public JSONObject getPageDocument(JSONObject parameter,
//			JSONObject sortParameter) {
//		// 分页带排序
//		
//		JSONObject result = new JSONObject();
//		JSONArray sourceList = new JSONArray();
//		String page = "1";
//		String pageSize = "20";
//		if(!parameter.containsKey("page")){
//			
//		}else{
//			page = parameter.getString("page");
//			parameter.remove("page");
//		}
//
//		if(!parameter.containsKey("pageSize")){
//			
//		}else{
//			pageSize = parameter.getString("pageSize");
//			parameter.remove("pageSize");
//		}	
//		
//		BasicDBObject queryObject = new BasicDBObject(parameter);
//		
//		BasicDBObject sortObject = new BasicDBObject(sortParameter);
//   	 	FindIterable<Document> findIterable = this.collection.find(queryObject).sort(sortObject).skip((Integer.parseInt(page)-1)*Integer.parseInt(pageSize)).limit(Integer.parseInt(pageSize));  
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		try{
//	   	 		JSONObject sourceDetail = new JSONObject();
//	   	 		sourceDetail.put("id",info.get("id").toString());
//	   	 		sourceDetail.put("intro", info.get("intro").toString());
//	   	 		sourceDetail.put("duration", info.get("duration").toString());
//	   	 		sourceDetail.put("SoundName", info.get("title").toString());
//	   	 		sourceDetail.put("coverUrl", info.get("cover_url").toString());
//	   	 		sourceDetail.put("playCount", info.get("play_count").toString());
//	   	 		sourceDetail.put("palyPath", info.get("play_path").toString());
//	   	 		sourceDetail.put("albumTitle", info.get("album_title").toString());
//	   	 		sourceDetail.put("favoritesCount", info.get("favorites_count").toString());
//	   	 		sourceList.add(sourceDetail);
//   			} catch (Exception e) {
//   				e.printStackTrace();
//   			}
//   	 	}  
//   	 	result.put("sourceList", sourceList);
//   	 	result.put("page", page);
//   	 	result.put("pageSize", pageSize);
//   	 	long total = getDocumentCount(parameter);
//   	 	result.put("total", total);
//   	 	if((Integer.parseInt(page)*Integer.parseInt(pageSize))>total){
//   	 		result.put("nextPage", false);
//   	 	}else{
//   	 		result.put("nextPage", true);
//   	 	}
//
//		return result;
//	}
//
//	public JSONObject getPageLikeDocument(JSONObject parameter,
//			JSONObject sortParameter) {
//		//分页带排序，模糊查找文档
//		JSONObject result = new JSONObject();
//		JSONArray sourceList = new JSONArray();
//		String page = "1";
//		String pageSize = "20";
//		if(!parameter.containsKey("page")){
//			
//		}else{
//			page = parameter.getString("page");
//			parameter.remove("page");
//		}
//
//		if(!parameter.containsKey("pageSize")){
//			
//		}else{
//			pageSize = parameter.getString("pageSize");
//			parameter.remove("pageSize");
//		}	
//		
//		String channelName = parameter.get("name").toString();
//		
//		Pattern pattern = Pattern.compile("^.*"+channelName+".*$", Pattern.CASE_INSENSITIVE);
//	    BasicDBObject query = new BasicDBObject();
//	    query.put("name",pattern);
//	    query.put("status",1);
//	    
//
//		BasicDBObject sortObject = new BasicDBObject(sortParameter);
//   	 	FindIterable<Document> findIterable = this.collection.find(query).sort(sortObject).skip((Integer.parseInt(page)-1)*Integer.parseInt(pageSize)).limit(Integer.parseInt(pageSize));
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		info.remove("_id");
//   	 		info.put("type", this.collection.getNamespace().getCollectionName());
//   	 		sourceList.add(info);
//   	 	}  
//   	 	result.put("sourceList", sourceList);
//   	 	result.put("page", page);
//   	 	result.put("pageSize", pageSize);
//		long count = this.collection.count(query);
//   	 	result.put("total", count);
//   	 	if((Integer.parseInt(page)*Integer.parseInt(pageSize))>count){
//   	 		result.put("nextPage", false);
//   	 	}else{
//   	 		result.put("nextPage", true);
//   	 	}
//
//		return result;
//	}
//	
//	
//	public JSONObject getAlbumsFromChannel(JSONObject parameter,
//			JSONObject sortParameter) {
//		JSONObject result = new JSONObject();
//		JSONArray sourceList = new JSONArray();
//		String page = "1";
//		String pageSize = "20";
//		if(!parameter.containsKey("page")){
//			
//		}else{
//			page = parameter.getString("page");
//			parameter.remove("page");
//		}
//
//		if(!parameter.containsKey("pageSize")){
//			
//		}else{
//			pageSize = parameter.getString("pageSize");
//			parameter.remove("pageSize");
//		}	
//		
//		String channelId = parameter.get("channelId").toString();
//
//	    BasicDBObject query = new BasicDBObject();
//	    query.put("zhubo_id",Integer.parseInt(channelId));
//	    
//
//		BasicDBObject sortObject = new BasicDBObject(sortParameter);
//   	 	FindIterable<Document> findIterable = this.collection.find(query).sort(sortObject).skip((Integer.parseInt(page)-1)*Integer.parseInt(pageSize)).limit(Integer.parseInt(pageSize));
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		info.remove("_id");
//   	 		info.put("type", this.collection.getNamespace().getCollectionName());
//   	 		sourceList.add(info);
//   	 	}  
//   	 	result.put("sourceList", sourceList);
//   	 	result.put("page", page);
//   	 	result.put("pageSize", pageSize);
//		long count = this.collection.count(query);
//   	 	result.put("total", count);
//   	 	if((Integer.parseInt(page)*Integer.parseInt(pageSize))>count){
//   	 		result.put("nextPage", false);
//   	 	}else{
//   	 		result.put("nextPage", true);
//   	 	}
//
//		return result;
//	}
//
//	public JSONObject getRandomDocument(JSONObject parameter,
//			JSONObject sortParameter) {
//		
//		JSONObject result = new JSONObject();
//		JSONArray sourceList = new JSONArray();
//		
//		BasicDBObject queryObject = new BasicDBObject(parameter);
//		
//		BasicDBObject sortObject = new BasicDBObject(sortParameter);
//		long total = getDocumentCount(parameter);
//
//		 int maxNumber = 1;
//		 if((int)total < 20){
//			 maxNumber = 100;
//		 }else{
//			 maxNumber = (int)total - 20;
//		 }
//		
//		Random ra = new Random();
//		int skipNumber = ra.nextInt(maxNumber);
//
//   	 	FindIterable<Document> findIterable = this.collection.find(queryObject).sort(sortObject).skip(skipNumber).limit(20);  
//   	 	MongoCursor<Document> mongoCursor = findIterable.iterator();  
//   	 	while(mongoCursor.hasNext()){
//   	 		Document info = mongoCursor.next();
//   	 		try{
//	   	 		JSONObject sourceDetail = new JSONObject();
//	   	 		sourceDetail.put("id",info.get("_id").toString());
//	   	 		sourceDetail.put("intro", info.get("intro").toString());
//	   	 		sourceDetail.put("duration", info.get("duration").toString());
//	   	 		sourceDetail.put("SoundName", info.get("title").toString());
//	   	 		sourceDetail.put("coverUrl", info.get("cover_url").toString());
//	   	 		sourceDetail.put("playCount", info.get("play_count").toString());
//	   	 		sourceDetail.put("palyPath", info.get("play_path").toString());
//	   	 		sourceDetail.put("albumTitle", info.get("album_title").toString());
//	   	 		sourceDetail.put("favoritesCount", info.get("favorites_count").toString());
//	   	 		sourceList.add(sourceDetail);
//   			} catch (Exception e) {
//   				e.printStackTrace();
//   			}
//   	 	}  
//   	 	result.put("sourceList", sourceList);
//   	 	result.put("total", total);
//   	 	result.put("nextPage", false);
//		return result;
//	}
//	
	
}

