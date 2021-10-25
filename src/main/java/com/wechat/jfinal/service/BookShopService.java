package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.Bookshop;

import java.util.Date;
import java.util.List;

public class BookShopService {
    private static final Bookshop dao = new Bookshop().dao();

	public Bookshop getBookShop(String shopId) {
		// TODO Auto-generated method stub
		return dao.findById(Integer.parseInt(shopId));
	}
	
	public boolean checkMobile(String memeberId) {
		// TODO Auto-generated method stub
		String sql = "select mobile from member where id="+memeberId;
		String mobile = Db.find(sql).get(0).getStr("mobile");
		if(null!=mobile&&!"".equals(mobile)){
			return true;
		}
		return false;
	}

	public int insertShopCard(String memeberId, int shopId, String card,String telephone,boolean hasMobile) {
		// TODO Auto-generated method stub
		String sql = "insert into bookcard (memberid,card,shopid,status,price,createdate,year,type) values(?,?,?,?,?,?,?,?)";
		Db.update(sql,memeberId,card,shopId,1,1,new Date(),1,2);
		if(hasMobile==false){
		Db.update("update member set mobile=? where id = ?",telephone,memeberId);
		}
		return 1;
	}

	public boolean isRepeat(String memeberId,int shopid) {
		// TODO Auto-generated method stub
		String sql = "select * from bookcard where memberid="+memeberId+" and shopid="+shopid;
		List<Record> list  = Db.find(sql);
		if(list.size()!=0){
			return false;
		}
		return true;
	}

	
}
