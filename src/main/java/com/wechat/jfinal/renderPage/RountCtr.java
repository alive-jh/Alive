package com.wechat.jfinal.renderPage;

import com.jfinal.core.Controller;

public class RountCtr   extends Controller {
    public void getLineChartPage(){
        String objectType = getPara("objectType","grade");
        int id = getParaToInt("id",0);
        if(objectType.equals("grade")){
            setAttr("objectType",objectType);
            setAttr("id",id);
            render("/statistical/LineChartPage.jsp");
        }
        if(objectType.equals("std")){
            setAttr("objectType",objectType);
            setAttr("id",id);
            render("/statistical/LineChartPage.jsp");
        }
    }
    
    public void goodVideo(){

    }
}
