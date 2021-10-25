package com.wechat.dao.impl;

import com.wechat.dao.BookOrderDao;
import com.wechat.entity.*;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BookOrderDaoImpl extends BaseDaoImpl implements BookOrderDao {

	
	public void saveBookOrder(BookOrder bookOrder) {
		
		this.saveEntity(bookOrder);
	}

	
	public Page searchBookOrder(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from BookOrder where 1=1 ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
		
	}

	
	public void updateBookOrderStatus(String orderId, String status) {
		
		this.executeUpdateSQL(" update bookorder set status = "+status+" where id = "+orderId);
	}

	
	public BookOrder getBookOrder(String id) {
		
		List<BookOrder> list = this.executeHQL(" from BookOrder where id = "+id);
		BookOrder bookOrder = new BookOrder();
		
		if(list.size()>0)
		{
			bookOrder = list.get(0);
		}
		return bookOrder;
	}

	
	public void saveBookOrderInfo(BookOrderInfo bookOrderInfo) {
		
		this.saveEntity(bookOrderInfo);
	}

	
	public List<BookOrder> searchBookOrderInfo(String orderId) {
		
		
		return this.executeHQL(" from BookOrderInfo where 1=1 and status = 0 and orderId="+orderId);
	}

	
	public void updateBookOrderInfoStatus(String id, String status) {
		
		this.executeUpdateSQL(" update bookorderinfo set status = "+status+" where id in ( "+id+" ) ");
	}

	
	public void saveBookExpress(BookExpress BookExpress) {
		
		this.saveEntity(BookExpress);
	}

	
	public void updateBookExpress(String id, String status) {
		
		this.executeUpdateSQL(" update bookexpress set status = "+status+" where id = "+id);
	
	}
	
	
	
	public Page searchOrderInfo(HashMap map)
	{
		
		StringBuffer sql = new StringBuffer(

			  "   select b.ordernumber,b.memberid,b.status,b.totalprice,b.express,b.expressnumber,a.* ,c.username,c.mobile ,b.createdate ,c.area, c.address from ( "
			+ "  select orderid,GROUP_CONCAT(a.cateid,'<',b.bname,'<',IFNULL(b.cover,''),'<',IFNULL(b.author,''),'<',IFNULL(b.price,''),'<',a.barcode,'<',a.status ) from bookorderinfo a ,category b where a.cateid = b.cateID " 
			+ "  group by orderid ) a,bookorder b ,useraddress c where a.orderid = b.id and b.addressid = c.id  ");
		
		if(!"".equals(map.get("orderId")) && map.get("orderId") != null)
		{
			sql.append(" and a.orderid = ").append(map.get("orderId"));
		}
		if(!"".equals(map.get("memberId")) && map.get("memberId") != null)
		{
			sql.append(" and b.memberid = ").append(map.get("memberId"));
		}
		if(!"".equals(map.get("status")) && map.get("status") != null)
		{
			sql.append(" and b.status = ").append(map.get("status"));
		}
		if(!"".equals(map.get("orderNumber")) && map.get("orderNumber") != null)
		{
			sql.append(" and b.ordernumber ='").append(map.get("orderNumber")).append("'");
		}
		if(!"".equals(map.get("startDate")) && map.get("startDate") != null)
		{
			sql.append(" and b.createdate >='").append(map.get("startDate")).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate") != null)
		{
			sql.append(" and b.createdate <='").append(map.get("endDate")).append("'");
		}
		sql.append("   order by b.createdate desc  ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}

	
	public void updateBookOrderInfo(String orderId, String express,
			String expressNumber, String operatorId) {
		
		this.executeUpdateSQL(" update bookorder set express ='"+express+"' ,expressnumber='"+expressNumber+"', operatorid ="+operatorId+" where id = "+orderId);
				
	}

	
	public Page searchMemberBook(HashMap map) {
		
		StringBuffer sql = new StringBuffer(
			"  select a.*,b.bname,b.cover,b.author from ( "
			+" select a.* from bookorderinfo a, ( "
			+" select id from bookorder where memberid =  "+map.get("memberId").toString()
			+" ) b where a.orderid = b.id "
			+" ) a,category b where a.cateid = b.cateID ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	
	public void updateBookStatus(String bookCode, String status) {
		
		this.executeUpdateSQL(" update bookorderinfo set status="+status +" where barcode='"+bookCode+"'");
	}

	
	public void saveMemberBookInfo(MemberBookInfo memberBookInfo) {
		
		this.saveEntity(memberBookInfo);
	}

	
	public void updateMemberBookInfoStatus(String id) {
		
		this.executeUpdateSQL(" update memberbookinfo set status = 1 where id = "+id);
	}

	
	public Page searchBookExpress(HashMap map) {
		
		StringBuffer sql = new StringBuffer("select * from bookexpress where 1=1");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	
	
	public Page searchMemberBookInfo(HashMap map)
	{
		
		StringBuffer sql = new StringBuffer(
				
			 " select b.cateID,b.bname,b.author,b.cover,a.* from ( "
			+" select b.id,b.cateid cate_id ,b.barcode,b.status from bookorder  a,bookorderinfo b  where a.id = b.orderid "
			+" and a.memberid = "+map.get("memberId")+" ) a,category b where a.cate_id = b.cateID ");
		
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and a.status = ").append(map.get("status"));
		}
		sql.append(" order by  a.id desc ");
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}
	
	
	
	public void deleteBookvehicle(String cateId, String memberId) {
		
		this.executeUpdateSQL(" delete from bookvehicle where memberid="+memberId +" and cateid='"+cateId+"'");
	}
	
	
	
	
	public void updateBookOrderExpess(String id, String status,String express,String expressNumber) {
		
		this.executeUpdateSQL(" update bookorder set status = "+status+" ,express ='"+express+"' , expressnumber = '"+expressNumber+"' where id in ( "+id+" ) ");
	}
	
	
	
	
	public Page searchMemberExpress(HashMap map)
	{
		StringBuffer sql = new StringBuffer(
		" select b.id,b.express,b.expressnumber,b.status,b.createdate,a.info,c.nickname,c.mobile  from ( "
		+" select a.memberid,GROUP_CONCAT(a.cateid,'>',a.barcode,'>',a.status,'>',b.bname,'>',IFNULL(b.cover,''),'>',IFNULL(b.author,'')) info "
		+" from memberbookinfo a ,category b where a.cateid = b.cateID GROUP BY memberid "
		+" ) a, bookexpress b ,member c  where a.memberid = b.memberid and a.memberid = c.id ");
		
		
		if(!"".equals(map.get("status")) && map.get("status")!= null)
		{
			sql.append(" and a.status = ").append(map.get("status"));
		}
		
		
		
		if(!"".equals(map.get("nickName")) && map.get("nickName")!= null)
		{
			sql.append(" and c.nickName ='").append(map.get("nickName")).append("'");
		}
		if(!"".equals(map.get("mobile")) && map.get("mobile")!= null)
		{
			sql.append(" and c.mobile ='").append(map.get("mobile")).append("'");
		}
		
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}
	
	
	
	public void updateMemberExpress(String id,String status)
	{
		this.executeUpdateSQL(" update bookexpress set status = "+status + " where id = "+id);
	}
	
	
	
	public void updateBooksStatus(String bookCode) {
		
		this.executeUpdateSQL(" update book set isexist=1  where barcode in ( " + bookCode + " )");
	}

	
	public void saveShopOrder(ShopOrder shopOrder) {
		
		this.saveEntity(shopOrder);
		
	}

	
	public void saveShopOrderInfo(ShopOrderInfo shopOrderInfo) {
		
		this.saveEntity(shopOrderInfo);
		
	}

	
	public Page searchShopOrder(HashMap map) {
		
		StringBuffer sql = new StringBuffer(
				 " select a.id,a.ordernumber,a.createdate,a.info ,a.memberid,b.nickname,b.mobile,b.headimgurl ,a.status from (  "
				+"   select a.id,max(a.ordernumber) ordernumber,max(a.createdate) createdate, GROUP_CONCAT(barcode,'⊥',bname,'⊥',IFNULL(cover,''),'⊥',IFNULL(author,''),'⊥',IFNULL(content,''),'⊥',bookstatus SEPARATOR '⊙') info ,max(a.memberid) as memberid,a.status "
				+" from (  select a.id,a.ordernumber,a.createdate,b.barcode ,c.bname,c.cover,c.author,c.content,a.memberid,a.status ,b.status as bookstatus from shoporder a ,shoporderinfo b ,category c "   
				+" where a.id = b.orderid and a.shopid = "+map.get("shopId"));
				
				if(!"".equals(map.get("status")) && map.get("status")!= null)
				{
					sql.append("  and a.status in ( ").append(map.get("status")).append(")");
				}
		
				sql.append(" and b.cateid = c.cateID ) a where 1=1    group by  a.id ) a,member b  where a.memberid = b.id ");
				
				if(!"".equals(map.get("mobile")) && map.get("mobile")!= null)
				{
					sql.append(" and b.mobile = '").append(map.get("mobile")).append("'");
				}
				if(!"".equals(map.get("memberId")) && map.get("memberId")!= null)
				{
					sql.append(" and a.memberid = ").append(map.get("memberId"));
				}
				
				sql.append(" order by a.id desc  ");
				
				
			
				
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}
	
	
	public void updateShopOrderStatus(String id,String status) {
		
		this.executeUpdateSQL(" update shoporder set status = "+status+" where id = "+id);
		this.executeUpdateSQL(" update shoporderinfo set status= 1 where orderid = "+id);
		
		
	}
	
	
	
	public void updateShopOrderInfoStatus(String code) {
		
		
		this.executeUpdateSQL(" update shoporderinfo set status= 1 where barcode = '"+code+"'");
		
		
	}

	
	public Page searchShopBook(HashMap map) {
		
		StringBuffer sql = new StringBuffer("select a.cateId,max(a.bname), max(a.cover),max(author),max(a.content),max(b.barcode) from category a ,book b "
			 +" where a.cateID = b.cate_id   and b.belong = "+map.get("shopId").toString());
		
		if(!"".equals(map.get("name")) && map.get("name") != null)
		{
			sql.append(" and a.bname like '%").append(map.get("name")).append("%'");
		}
		sql.append("  group by a.cateId  ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
		
	}
	
	
	
	public Object[] searchBookShopInfo(String shopId,String bookName)
	{
		
		String tempName = bookName;
		if(!"".equals(bookName) && bookName!= null)
		{
			bookName = "and cateid in (select cateID from category where bname like '%"+bookName+"%')";
		}
		else
		{
			bookName = "";
		}
		String sql  =" "
					+" select sum(outbook) from ( "
					+" select  orderid,count(id) outbook from shoporderinfo where orderid in (select id from shoporder where shopid = "+shopId+") "
					+" and status =0 "+bookName
					+" group by orderid "
					+" ) a ";
		Object[] obj = new Object[3];
		List<Object> list = this.executeSQL(sql);
		Object resultObj = list.get(0);
		
		if(resultObj ==null)
		{
			resultObj ="0";
		}
		
		
		obj[0] = resultObj;
		
		
		
		
		
		sql  =" "
				+" select sum(inputbook) from ( "
				+" select  orderid,count(id) inputbook from shoporderinfo where orderid in (select id from shoporder where shopid = "+shopId+") "
				+" and status =1 "+bookName
				+" group by orderid "
				+" ) a ";
		
		list = this.executeSQL(sql);
		
		resultObj = list.get(0);
		if(resultObj ==null)
		{
			resultObj ="0";
		}
		
		
		obj[1] = resultObj;
		
		
		
		
		if(!"".equals(tempName) && tempName!= null)
		{
			tempName = "and a.bname like '%"+tempName+"%'";
		}
		else
		{
			tempName = "";
		}
		sql = " select count(a.cateID) from category a ,book b ,  bookshop c "
			  +" where a.cateID = b.cate_id "+tempName
			  +" and b.belong  = c.id and c.id = "+shopId;
		List<Object> tempList = this.executeSQL(sql);
		Object testObj = tempList.get(0);
		if(testObj!= null)
		{
			
			obj[2] = testObj.toString();
		}
		else
		{
			obj[2] = "0";
		}
		return obj;
		
	}

	
	
	public Page searchOrderBook(HashMap map) {
		
		StringBuffer sql = new StringBuffer(

		" select a.cateID,a.bname,a.author,a.cover,a.content,b.barcode from category a ,shoporderinfo b ,shoporder c "
		+" where a.cateID = b.cateid and c.id = b.orderid ");
		
		if(!"".equals(map.get("shopId")) && map.get("shopId") != null)
		{
			sql.append(" and c.shopid =").append(map.get("shopId"));
		}
		if(!"".equals(map.get("name")) && map.get("name") != null)
		{
			sql.append(" and a.bname like '%").append(map.get("name")).append("%'");
		}
		
		if(!"".equals(map.get("status")) && map.get("status") != null)
		{
			sql.append(" and b.status = ").append(map.get("status"));
		}
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
		
	}
	
	
	
	
	public List<BookOrder> searchShopOrderInfo(String orderId) {
		
		
		return this.executeHQL(" from ShopOrderInfo where 1=1 and status = 0 and orderId="+orderId);
	}


	@Override
	public boolean checkShopOrder(String shopId, String memberId) {
		// 检测是否还有借书未归还
		StringBuffer sql = new StringBuffer("select * from shoporder where status=0 and memberId="+memberId +" and shopid=" +shopId );
		List<Object> list = this.executeSQL(sql.toString());
		if(list.size() == 0){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	
}
