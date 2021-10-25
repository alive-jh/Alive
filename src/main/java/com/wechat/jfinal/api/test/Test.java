package com.wechat.jfinal.api.test;

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.common.ElasticsearchUtils;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.SysDict;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Test extends Controller {

    public void index() throws IOException {
        renderText("Hello Jfinal");
//        List<Record> s = new GradesService().getJoinGrades(14,"1-2",1);
//        render(new RenderCamelCaseJson(s));
    }

    public void stdGet(){
        try {
            Redis.use().set("listen","test");
            Cache redis = Redis.use();
            String listen = redis.get("listen");
            renderJson(listen);
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(e);
        }
    }
    public void renderTest(){
        renderTemplate("/statistical/test.html");
    }
    public void testT(){
        setAttr("objectType","grade").setAttr("id",437);
        renderTemplate("/statistical/testTemplate.html");
    }
    public void testPostForm(){
        String jsonString = HttpKit.readData(getRequest());
        renderJson(jsonString);
    }

    public void testGetModel(){
//        SysDict model = getModel(SysDict.class,"");
        SysDict bean = getBean(SysDict.class,"");
    }

    public void testPage(){

        int pageSize = getParaToInt("pageSize");
        int currentPage = getParaToInt("currentPage");
        Page<ClassStudent> page = ClassStudent.dao.paginate(currentPage,pageSize,"SELECT *"," FROM class_student");
        renderJson(Rt.success(page));
    }

    public void testElastic(){
        ElasticsearchUtils utils= ElasticsearchUtils.getInstance();

        TransportClient client = ElasticsearchUtils.getInstance().getClient();
//        GetResponse getResponse = client.prepareGet("sound", "sound", "1417").get();
        renderJson(utils.getById("sound", "sound", "1417"));

    }

    public void testElasticFuzzy(){
        ElasticsearchUtils utils= ElasticsearchUtils.getInstance();
        int pageIndex = 1;
        int pageSize = 10;
//        TransportClient client = ElasticsearchUtils.getInstance().getClient();
//        GetResponse getResponse = client.prepareGet("sound", "sound", "1417").get();
        Page<Record> page = utils.equal("sound", "sound", "soundId","30327237",pageIndex,pageSize);
//        Page<Record> page = utils.equal("sound", "sound", "channelId","2987462",pageIndex,pageSize);

        renderJson(Rt.success(page));
    }

    public void pressureTest1(){
    	renderText(new Date().toString());
    }
    
    public void pressureTestSingle(){
    	Member member = Member.dao.findFirst("select * from member where id = 137569");
    	Result.ok(member,this);
    }
    
    
}
