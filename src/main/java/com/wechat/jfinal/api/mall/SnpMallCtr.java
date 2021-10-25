package com.wechat.jfinal.api.mall;

import com.jfinal.core.Controller;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.util.SecurityUtil;

public class SnpMallCtr extends Controller {

    @EmptyParaValidate(params = {"access_token"})
    public void index() throws Exception {

        String accessToken = getPara("access_token");
        String accessTokenDetrypt = SecurityUtil.detrypt(accessToken, "fandou");
        String account = accessTokenDetrypt.split("#")[1];
        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?",account);
        getSession().setAttribute("memberaccount_id",memberaccount.getId());
        getSession().setAttribute("memberaccount",memberaccount.getAccount());

        redirect("/lesson/mall/html/index.html?session="+ getSession().getId());

    }
}
