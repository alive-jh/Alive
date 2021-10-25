package com.wechat.jfinal.api.sell;

import com.jfinal.core.Controller;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Mallspecifications;
import com.wechat.util.Keys;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class SellCtr extends Controller {

    public void productInfo() throws UnsupportedEncodingException {

        HttpSession session = getSession();
        session.setAttribute("WX#USER", "org-FwyN2d0jfeSKnYMVTrLUQmLM#9");
        session.setAttribute("openid", "org-FwyN2d0jfeSKnYMVTrLUQmLM");

        int product = getParaToInt("product");
        int inviter = getParaToInt("inviter", 0);
        String[] WX_USER = null;

        try {

            WX_USER = ((String) getSession().getAttribute("WX#USER")).split("#");

        } catch (NullPointerException e) {
            String oauthHtml = "to_test_wechatLogin.html";

            String state = "/wechat/member/sell/productInfo?product=" + product + "&inviter=" + inviter;

            redirect("https://open.weixin.qq.com/connect/oauth2/authorize" + "?appid=" + Keys.APP_ID
                    + "&redirect_uri=https://wechat.fandoutech.com.cn/wechat/wechat-oauth2.0/" + oauthHtml
                    + "&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(state, "UTF-8")
                    + "#wechat_redirect", true);

            return;
        }

        setAttr("product", product);

        renderTemplate("/lesson/2c/lessonProduct.html");

    }


    public void productDetail() {
        int product = getParaToInt("product");
        setAttr("product", product);
        renderTemplate("/lesson/payClass/productDetail.html");
    }

    public void productPayComplete() {
        int product = getParaToInt("product");
        setAttr("product", product);
        renderTemplate("/lesson/payClass/productPayComplete.html");
    }
}
