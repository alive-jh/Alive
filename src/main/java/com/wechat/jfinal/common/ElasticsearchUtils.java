package com.wechat.jfinal.common;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticsearchUtils {

    //设立静态变量
    private static ElasticsearchUtils utils = new ElasticsearchUtils();

    private TransportClient client;

    private ElasticsearchUtils() {
        //私有化构造函数
        try {
            before();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //开放一个公有方法，判断是否已经存在实例，有返回，没有新建一个在返回
    public static ElasticsearchUtils getInstance() {
        return utils;
    }

    public void before() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("cluster.name", "my-application");
        Settings.Builder settings = Settings.builder().put(map);
//        TransportAddress transportAddress = new InetSocketTransportAddress("192.168.79.131", 9300);
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("8.129.49.214"), 9300));
    }

    public GetResponse getById(String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id)
                .setOperationThreaded(false)    // 线程安全
                .get();
        return response;
    }

//    public GetResponse


    public Page<Record> fuzzy(String index, String type, String fileName, String contentText, int courrentPage, int pageSize) {
        int from = (courrentPage - 1) * pageSize;
        MatchQueryBuilder queryBuilders = QueryBuilders.matchQuery(fileName, contentText);
        SearchRequestBuilder s = client.prepareSearch(index).setTypes(type);
        s.setQuery(queryBuilders);
        s.setFrom(from).setSize(pageSize);//分页
        SearchResponse searchResponse = s.get();
        SearchHits hits = searchResponse.getHits();

        List<Record> list = new ArrayList<>();
        SearchHit[] result = hits.getHits();
        for (SearchHit hit : result) {
            Record record = new Record();
            Map<String, Object> map = hit.getSource();
            for (String str : map.keySet()) {
                record.set(str, map.get(str));
            }
            list.add(record);
        }
        int total = Integer.parseInt(Long.toString(hits.getTotalHits()));
        Page<Record> page = new Page<>(list, courrentPage, pageSize, total / pageSize, total);
        return page;
    }


    public Page<Record> equal(String index, String type, String fileName, String contentText, int courrentPage, int pageSize) {
        int from = (courrentPage - 1) * pageSize;
        TermQueryBuilder queryBuilders = QueryBuilders.termQuery(fileName, contentText);
//        RegexpQueryBuilder queryBuilders = QueryBuilders.regexpQuery(fileName,contentText);
        SearchRequestBuilder s = client.prepareSearch(index).setTypes(type);
//        s.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        s.setQuery(queryBuilders);
        s.setFrom(from).setSize(pageSize);//分页
        SearchResponse searchResponse = s.get();
        SearchHits hits = searchResponse.getHits();

        List<Record> list = new ArrayList<>();
        SearchHit[] result = hits.getHits();
        for (SearchHit hit : result) {
            Record record = new Record();
            Map<String, Object> map = hit.getSource();
            for (String str : map.keySet()) {
                record.set(str, map.get(str));
            }
            list.add(record);
        }

        int total = Integer.parseInt(Long.toString(hits.getTotalHits()));
        Page<Record> page = new Page<>(list, courrentPage, pageSize, total / pageSize, total);
        return page;
    }


    /**
     * 执行搜索
     *
     * @param queryBuilder
     * @param indexname
     * @param type
     * @return
     */
    public List<Record> searcher(QueryBuilder queryBuilder, String indexname, String type) {
        List<Record> list = new ArrayList<>();
        SearchResponse searchResponse = client.prepareSearch(indexname).setTypes(type)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                SearchHit h = hit;
            }
        }
        return list;
    }

    public TransportClient getClient() {
        return this.client;
    }

}
