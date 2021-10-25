package com.wechat.jfinal.api.bookShop;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.Bookshop;
import com.wechat.jfinal.model.BookshopBanner;
import com.wechat.jfinal.model.Shoporder;
import com.wechat.jfinal.model.Shoporderinfo;

import net.sf.json.JSONObject;

public class bookShop extends Controller {
	/**
	 * function: 检测当前会员是否有未归还的借书订单 description:使用GET方法请求提交数据，
	 * return:返回bool型对象，false为没有（可借）
	 * 
	 * @param: shopId(图书馆ID)
	 *             memberId（会员ID，取凡豆伴账号ID） status:要获取订单的状态0是未归还，1为已经归还，不带状态取全部
	 * @author alex
	 */
	public void getBookShopOrder() {
		String mobile = getPara("mobile", "0");
		int shopId = getParaToInt("shopId", 0);
		int status = getParaToInt("status", -1);
		List<Shoporder> result = null;

		if (-1 == status) {
			result = Shoporder.dao.find("select * from shoporder where mobile=? and shopid=?", mobile, shopId);
		} else {
			result = Shoporder.dao.find("select * from shoporder where mobile=? and shopid=? and status=?", mobile,
					shopId, status);
		}

		renderJson(Rt.success(result));

	}

	/**
	 * 店铺借书
	 */
	public void saveBookShopOrder() {

		Integer shopId = getParaToInt("shopId", 0);
		String cardId = getPara("studentCard", "");
		String barCodes = getPara("barCodes", "");

		if (xx.isAllEmpty(shopId, cardId, barCodes)) {
			renderJson(JsonResult.JsonResultError(203, "参数异常"));
		}
		;

		List<Record> list = Db.find("select student_id,contacts from qy_student_card where card_id = " + cardId);
		Integer studentId = list.get(0).getInt("student_id");
		String mobile = list.get(0).getStr("contacts");

		Date date = new Date();
		Shoporder shopOrder = new Shoporder();
		shopOrder.setOrdernumber(date.getTime() + "");
		shopOrder.setMemberid(studentId);
		shopOrder.setType(2);
		shopOrder.setStatus(1);
		shopOrder.setCreatedate(date);
		shopOrder.setShopid(shopId);
		shopOrder.setMobile(mobile);

		shopOrder.save();

		if (shopOrder.getId() != null) {
			String[] barCode = barCodes.split(",");
			for (int i = 0; i < barCodes.length(); i++) {
				Shoporderinfo shoporderinfo = new Shoporderinfo();
				shoporderinfo.setBarcode(barCode[i]);
				shoporderinfo.setOrderid(shopOrder.getId());
				shoporderinfo.setCateid(barCode[i].toString().substring(0, 5));
				shoporderinfo.setStatus(1);
				shoporderinfo.setCreatedate(date);
				shoporderinfo.save();
			}
		}

		renderJson(JsonResult.JsonResultOK());

	}
	
	public void bookShopInfo(){
		
		String appid = getPara("appid");
		Bookshop bookshop = Bookshop.dao.findFirst("select * from bookshop where appid = ?",appid);
		
		JSONObject json = new JSONObject();
		json.put("bookshop", bookshop.toJson());
		
		Result.ok(json,this);
		
	}
	
	public void bannerList(){
		
		Integer shopId = getParaToInt("shopId");
		
		List<BookshopBanner> banners = BookshopBanner.dao.find("select * from bookshop_banner where = ?",shopId);
		
		Result.ok(Result.makeupList(banners),this);
		
	}

}
