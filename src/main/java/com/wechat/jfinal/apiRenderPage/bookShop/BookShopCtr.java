package com.wechat.jfinal.apiRenderPage.bookShop;

import com.jfinal.core.Controller;
import com.wechat.jfinal.model.Bookshop;
import com.wechat.jfinal.service.BookShopService;

import java.util.UUID;


public class BookShopCtr extends Controller {
	static BookShopService bookShopService = new BookShopService();
	
    public void applyShopCard(){
        String shopId = getPara("shopId","1");
        Bookshop bookshop = bookShopService.getBookShop(shopId);
        setAttr("bookshop",bookshop);
        render("/bookShop/applyShopCard.jsp");
    }
    
    public void insertShopCard(){
        String memeberId = getPara("memberid");
        if(null==memeberId||"".equals(memeberId)){
        	setAttr("message","申请失败，未授权登录");
        	setAttr("pic","dachatubiao.jpg");
        	render("/bookShop/pageResult.jsp");
        	return;
        }
        int shopId = Integer.parseInt(getPara("shopid"));
        String telephone = getPara("tel");

        boolean hasMobile = bookShopService.checkMobile(memeberId);
        
        String card = UUID.randomUUID().toString().replace("-", "");
        
        boolean repeat =  bookShopService.isRepeat(memeberId,shopId);
        
        if(repeat){
        	bookShopService.insertShopCard(memeberId,shopId,card,telephone,hasMobile);
        	setAttr("message","申请成功");
        	setAttr("pic","dagoutubiao.jpg");
        }else{
        	setAttr("message","申请失败，重复申请");
        	setAttr("pic","dachatubiao.jpg");
        }
        
        render("/bookShop/pageResult.jsp");
    }
    
    
    public void wechatLogin(){
    	String shopId = getPara("shopId");
    	String telephone = getPara("telephone");
    	StringBuffer wxUrl = new StringBuffer();
		wxUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=");
		wxUrl.append("http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&");
		wxUrl.append("state=addBookCard:"+shopId+"P"+telephone+"#wechat_redirect");
		redirect(wxUrl.toString());
    }
    
   
}
