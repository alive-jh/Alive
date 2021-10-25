package com.wechat.jfinal.apiAjax;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.StandardRsp;
import com.wechat.jfinal.service.StatisticalService;

import java.util.List;

public class StatisticalCtr  extends Controller {

    private StatisticalService statisticalService = new StatisticalService();

    public void getDataForLineChart(){

        StandardRsp rsp = new StandardRsp();
        try {
            //获得类型
            String dateType = getPara("dateType","day");
            String objectType = getPara("objectType","std");
            int id = getParaToInt("id",0);

            if(objectType.equals("grade")){
            	List<Record> rankData = statisticalService.studyTimeRanking(id);
                rsp.setData(statisticalService.getDataForLineChart(dateType,id,objectType));
                rsp.getContext().put("rankData",rankData);
            }else
                rsp.setData(statisticalService.getDataForLineChart(dateType,id,objectType));
            rsp.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(rsp.getContext());
        }
        renderJson(rsp.getContext());
    }
}
