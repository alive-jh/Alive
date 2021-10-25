package com.wechat.jfinal.renderPage.bookShop;

import com.jfinal.core.Controller;
import com.wechat.jfinal.model.Bookshop;
import com.wechat.jfinal.service.BookShopService;


public class bookShop   extends Controller {
	static BookShopService bookShopService = new BookShopService();
	
    public void applyShopCard(){
        String shopId = getPara("shopId","1");
        Bookshop bookshop = bookShopService.getBookShop(shopId);
        setAttr("memberId","706");
        setAttr("memberMobile","18666412281");
        setAttr("bookshop",bookshop);
        render("/bookShop/applyShopCard.jsp");
    }
   
}
