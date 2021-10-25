package com.wechat.jfinal.api.resource;

import com.jfinal.core.Controller;
import com.wechat.jfinal.common.StandardRsp;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.Category;

public class CategoryCtrl extends Controller {

    public void getById() {
        StandardRsp rsp = new StandardRsp();//构建返回体
        try {//处理模块
            String cateId = getPara("cateId");//取值
            if (xx.isEmpty(cateId)) {//参数合理性检测
                renderJson(rsp.setCode(305));
                return;
            }
            Category c = Category.dao.findById(cateId);
            if (c == null)//空值检测，防止出现data为null
                rsp.setCode(405);
            else{
                String cover = c.getCover();
                if(null != cover && !"".equals(cover))
                    c.setCover("https://source.fandoutech.com.cn/wechat/wechatImages/book/"+cover);
                else
                    c.setCover("");
                String mp3 = c.getMp3();
                if(null != mp3 && !"".equals(mp3)&&mp3.endsWith(".rar")) {
                    mp3 = mp3.replace(".rar","");
                    c.setMp3("https://source.fandoutech.com.cn/wechat/wechatImages/book/mp3/" + mp3);
                }else
                    c.setMp3("");
                String name = c.getBname();
                if(name == null && "".equals(name))
                    c.setBname("");
                rsp.setData(c).setCode(200);//设置返回值、和code
            }
        } catch (Exception e) {//捕捉错误
            renderJson(rsp.getContext());//默认不设值返回就是500
            e.printStackTrace();
        }
        renderJson(rsp.getContext());//返回结果
    }
}
