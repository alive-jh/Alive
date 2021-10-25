package com.wechat.dao.impl;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.wechat.dao.BookDao;
import com.wechat.entity.*;
import com.wechat.entity.dto.MemberCardDto;
import com.wechat.util.JsonTimeStampValueProcessor;
import com.wechat.util.Page;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class BookDaoImpl extends BaseDaoImpl implements BookDao {

	@Override
	public Book getBook(String id) {

		List list = this.executeHQL(" from Book where barcode = '" + id + "'");
		Book book = new Book();
		if (list.size() > 0) {
			book = (Book) list.get(0);
		}
		return book;
	}

	@Override
	public Category getCategory(String id) {

		List list = this.executeHQL(" from Category where cateID = '" + id
				+ "'");
		Category category = new Category();
		if (list.size() > 0) {
			category = (Category) list.get(0);
		}
		return category;
	}

	@Override
	public void saveBook(Book book) {

		this.saveOrUpdate(book);
	}

	@Override
	public void saveCategory(Category category) {

		this.saveOrUpdate(category);
	}


	@Override
	public List searchBookList(HashMap map) {

		StringBuffer sql = new StringBuffer("");
		
		
		
		if (!"".equals(map.get("shopId")) && map.get("shopId") != null) {//如果是书院加盟店查询,属性值修改
			
			sql.append("  select a.*,b.outCount from ( ");
		}


		sql.append(" select  a.*,b.count from ( select cate_id,group_concat(a.barcode,'>',a.isexist,'>',b.name,'>',IFNULL(a.cate_id,''),'>',IFNULL(a.url,'') order by a.barcode desc )  from book a ,bookshop b where 1=1 and a.belong = b.id  ");

		
		
		if (!"".equals(map.get("shopId")) && map.get("shopId") != null) {//如果是书院加盟店查询,属性值修改
			
			sql.append(" and a.belong = ").append(map.get("shopId"));
			map.put("cateIds", "select cate_id from book where belong = "+map.get("shopId")+" group by cate_id");
		}
		
		
		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and cate_id = " + map.get("cateId"));
		}
		if (!"".equals(map.get("cateIds")) && map.get("cateIds") != null) {
			sql.append(" and cate_id in( " + map.get("cateIds")).append(" ) ");
		}
		sql.append("  group by cate_id ) a  left join (select cate_id ,count(cate_id) count  from book where isexist = 1  ");
		
		if (!"".equals(map.get("shopId")) && map.get("shopId") != null) {
			sql.append(" and belong = " + map.get("shopId"));
		}
		sql.append("  group by cate_id )  b on a.cate_id = b.cate_id  ");
		
		if (!"".equals(map.get("cateIds")) && map.get("cateIds") != null) {
			sql.append(" and a.cate_id in( " + map.get("cateIds"))
					.append(" ) ");
		}
		
		if (!"".equals(map.get("shopId")) && map.get("shopId") != null) {//如果是书院加盟店查询,属性值修改
			
			sql.append(" )a LEFT JOIN "
					+" (select cate_id,count(isexist) outCount  from book where  belong = "+map.get("shopId")+" and isexist=0 GROUP BY cate_id) b on a.cate_id = b.cate_id ");
		}


		
		
		
		return this.executeSQL(sql.toString());
	}

	@Override
	public Page searchCategory(HashMap map) {

		StringBuffer sql = new StringBuffer(" from Category where 1=1 ");
		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and cateId = " + map.get("cateId"));
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and bname like  %'" + map.get("cateId")).append("'%");
		}
		if (!"".equals(map.get("author")) && map.get("author") != null) {
			sql.append(" and author ='" + map.get("author")).append("'");
		}
		if (!"".equals(map.get("publish")) && map.get("publish") != null) {
			sql.append(" and publish ='" + map.get("publish")).append("'");
		}
		sql.append(" order by cateId desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public Page searchCategoryInfo(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select a.*,b.info,c.keyword from ( select a.*,b.* from category a,(select a.id,a.name,b.id as typeid,b.name as typename  from leftcategory a ,rightcategory b where a.id = b.left_id) b where a.right_id = b.typeid ");

		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and a.cateID ='" + map.get("cateId")).append("'");
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.bname like  '%" + map.get("name")).append("%'");
		}
		if (!"".equals(map.get("author")) && map.get("author") != null) {
			sql.append(" and a.author ='" + map.get("author")).append("'");
		}
		if (!"".equals(map.get("publish")) && map.get("publish") != null) {
			sql.append(" and a.publish ='" + map.get("publish")).append("'");
		}
		if (!"".equals(map.get("rightType")) && map.get("rightType") != null) {
			sql.append(" and a.right_id =" + map.get("rightType"));
		}
		if (!"".equals(map.get("leftType")) && map.get("leftType") != null) {
			sql.append(" and b.id =" + map.get("leftType"));
		}
		if (!"".equals(map.get("catIds")) && map.get("catIds") != null) {
			sql.append(" and a.book_cate_id in ( ")
					.append(map.get("catIds").toString()).append(" )");
		}
		sql.append("  order by  replace(replace(a.cateID,'C',''),'E','') desc ");
		sql.append("  ) a LEFT JOIN (select a.categoryid,group_concat(b .name) info from categorylabel a,label b where a.labelid = b.id group by a.categoryid) b on a.cateID = b.categoryid");
		sql.append("  LEFT JOIN  (select cateid,group_concat(name) keyword  from bookkeyword GROUP BY cateid)  c on a.cateID = c.cateid");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	@Override
	public Page searchCategoryInfo2(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select "+
						"   a.cateID,"+
						"	a.bname,"+
						"	a.price,"+
						"	a.publish,"+
						"	a.author,"+
						"	a.translator,"+
						"	a.page,"+
						"	a.remark,"+
						"	a.cover,"+
						"	a.count,"+
						"	a.bquantity,"+
						"	a.right_id,"+
						"	a.series,"+
						"	a.mp3,"+
						"	a.testmp3,"+
						"	a. CODE,"+
						"	a.mp3type,"+
						"	a. STATUS,"+
						"	a.book_cate_id,"+
						"	a. NAME,"+
						"	a.typeid,"+
						"	a.typename,"+
						"	b.info,"+
						"	c.keyword"
			+" from ( select a.*,b.* from category a,(select a.id,a.name,b.id as typeid,b.name as typename  from leftcategory a ,rightcategory b where a.id = b.left_id) b where a.right_id = b.typeid ");

		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and a.cateID ='" + map.get("cateId")).append("'");
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.bname like  '%" + map.get("name")).append("%'");
		}
		if (!"".equals(map.get("author")) && map.get("author") != null) {
			sql.append(" and a.author ='" + map.get("author")).append("'");
		}
		if (!"".equals(map.get("publish")) && map.get("publish") != null) {
			sql.append(" and a.publish ='" + map.get("publish")).append("'");
		}
		if (!"".equals(map.get("rightType")) && map.get("rightType") != null) {
			sql.append(" and a.right_id =" + map.get("rightType"));
		}
		if (!"".equals(map.get("leftType")) && map.get("leftType") != null) {
			sql.append(" and b.id =" + map.get("leftType"));
		}
		if (!"".equals(map.get("catIds")) && map.get("catIds") != null) {
			sql.append(" and a.book_cate_id in ( ")
					.append(map.get("catIds").toString()).append(" )");
		}
		sql.append("  order by  replace(replace(a.cateID,'C',''),'E','') desc ");
		sql.append("  ) a LEFT JOIN (select a.categoryid,group_concat(b .name) info from categorylabel a,label b where a.labelid = b.id group by a.categoryid) b on a.cateID = b.categoryid");
		sql.append("  LEFT JOIN  (select cateid,group_concat(name) keyword  from bookkeyword GROUP BY cateid)  c on a.cateID = c.cateid");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}


	@Override
	public List searchCategorykList(HashMap map) {

		StringBuffer sql = new StringBuffer(" from Category where 1=1 ");
		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and cateId = " + map.get("cateId"));
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and bname like  %'" + map.get("cateId")).append("'%");
		}
		if (!"".equals(map.get("author")) && map.get("author") != null) {
			sql.append(" and author ='" + map.get("author")).append("'");
		}
		if (!"".equals(map.get("publish")) && map.get("publish") != null) {
			sql.append(" and publish ='" + map.get("publish")).append("'");
		}
		sql.append(" order by cateId desc ");
		return this.executeHQL(sql.toString());

	}

	@Override
	public void updateBookStatus(String id, String status) {

		this.executeUpdateSQL(" update book set isexist = " + status
				+ " where barcode= '" + id + "'");
	}

	@Override
	public List getLeftCategory() {

		return this.executeHQL(" from LeftCategory where 1=1 ");
	}

	@Override
	public List getRightCategory() {

		return this.executeHQL(" from RightCategory where 1=1 ");
	}

	@Override
	public Integer getCateId(String type) {
		Integer cateId = 0;

		List cList = this.executeSQL("select  max(replace(cateID,'" + type
				+ "','')) from category where cateID like '%" + type
				+ "%' order by cateID desc");
		if (cList.size() > 0) {
			if (cList.get(0) != null) {
				cateId = new Integer(cList.get(0).toString());
			}
		}

		return cateId;
	}

	@Override
	public Integer getBookCateId(String cateId) {

		Integer maxCateId = 0;
		List cList = this.executeSQL("select max(replace(barcode,'" + cateId
				+ "',''))  from book where cate_id = '" + cateId + "'");
		if (cList.size() > 0) {
			if (cList.get(0) != null) {
				maxCateId = new Integer(cList.get(0).toString());
			}
		}

		return maxCateId;
	}

	@Override
	public void updateCateGory(Category cateGory) {
		this.executeUpdateSQL(" update category set code='"
				+ cateGory.getCode() + "' , status=" + cateGory.getStatus()
				+ ", mp3type=" + cateGory.getMp3Type() + " ,catalog='"
				+ cateGory.getCataLog() + "',testmp3='" + cateGory.getTestMp3()
				+ "',mp3='" + cateGory.getMp3() + "',bname='"
				+ cateGory.getbName() + "',price =" + cateGory.getPrice()
				+ ",publish='" + cateGory.getPublish() + "',author='"
				+ cateGory.getAuthor() + "',translator='"
				+ cateGory.getTranslator() + "',page=" + cateGory.getPage()
				+ ",cover='" + cateGory.getCover() + "',content='"
				+ cateGory.getContent() + "',right_id=" + cateGory.getRightId()
				+ ",series='" + cateGory.getSeries() + "',book_cate_id= "
				+ cateGory.getBook_cate_id() + "  where cateID='"
				+ cateGory.getCateID() + "'");

	}

	@Override
	public void deleteCateGory(String cateId) {
		this.executeUpdateSQL("delete from book where cate_id ='" + cateId
				+ "'");

		this.executeUpdateSQL("delete from categoryamount where cate_id ='"
				+ cateId + "'");

		this.executeUpdateSQL("delete from category where cateID ='" + cateId
				+ "'");

	}

	@Override
	public void deleteBook(String barcode) {

		this.executeUpdateSQL(" delete from book where barcode='" + barcode
				+ "'");

	}

	@Override
	public String getMaxBookCateId(String cateId, String type) {

		Integer maxCateId = 0;
		List cList = this.executeSQL("select barcode from book where cate_id ='" + cateId + "'");
		if (cList.size() > 0) {
			for(int i=0;i<cList.size();i++){
				String tempCateId = cList.get(i).toString();
				
				if (Integer.parseInt(tempCateId.substring(tempCateId.length()-4)) > maxCateId) {
					maxCateId = Integer.parseInt(tempCateId.substring(tempCateId.length()-4));
				}
				
			}
		}
		Integer resultMaxCateid = maxCateId + 1;
		return resultMaxCateid.toString();
	}

	@Override
	public void updateBookInfo(Book book) {
		this.executeUpdateSQL(" update book set url  ='" + book.getUrl()
				+ "',codeinfo='" + book.getCodeInfo() + "' where barcode='"
				+ book.getBarCode() + "'");
	}

	@Override
	public Page searchBookBy(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select a.cateID,a.bname,a.author,a.publish,a.price,a.cover,a.content,a.catalog , b.* from category a,(select a.id,a.name,b.id as typeid,b.name as typename  from leftcategory a ,rightcategory b where a.id = b.left_id) b where a.right_id = b.typeid ");

		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and a.cateID ='" + map.get("cateId")).append("'");
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.bname like  '%" + map.get("name")).append("%'");
		}
		if (!"".equals(map.get("author")) && map.get("author") != null) {
			sql.append(" and a.author ='" + map.get("author")).append("'");
		}
		if (!"".equals(map.get("publish")) && map.get("publish") != null) {
			sql.append(" and a.publish ='" + map.get("publish")).append("'");
		}
		if (!"".equals(map.get("rightType")) && map.get("rightType") != null) {
			sql.append(" and a.right_id =" + map.get("rightType"));
		}
		if (!"".equals(map.get("leftType")) && map.get("leftType") != null) {
			sql.append(" and b.id =" + map.get("leftType"));
		}
		sql.append(" order by  replace(replace(a.cateID,'C',''),'E','') desc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public Object[] searchCateGoryInfo(String cateId)// 忽略库存
	{
		StringBuffer sql = new StringBuffer(
				" select * from ( select a.*,c.* from ( "
						+ " select a.cateID,a.bname,a.price,a.publish,a.author,a.translator,a.page,a.cover,a.content,a.mp3,a.testmp3,'1',c.name,c.name1,a.catalog,a.code,a.mp3type from category a, "

						+ " (select a.name,b.name as name1,b.id from leftcategory a,rightcategory b where b.left_id = a.id) c "
						+ " where a.cateID = '"
						+ cateId
						+ "' and a.right_id = c.id ) a left  JOIN "
						+ " (select cateid as cateids,group_concat(b.nickname,'>',a.content,'>',a.star,'>',a.createdate) from comment a,member b where a.memberid = b.id  "
						+ " group by cateid ) c  on a.cateid = c.cateids"
						+ " )a left join (select a.categoryid,group_concat(b.name) info from categorylabel a,label b where a.labelid = b.id group by a.categoryid ) b on a.cateID = b.categoryid");
		Object[] obj = null;
		List list = this.executeSQL(sql.toString());
		if (list.size() > 0) {
			obj = (Object[]) list.get(0);
		}
		return obj;
	}

	// public Object[] searchCateGoryInfo(String cateId) //计算库存
	// {
	// StringBuffer sql = new StringBuffer(
	// " select * from ( select a.*,c.* from ( "
	// +" select a.cateID,a.bname,a.price,a.publish,a.author,a.translator,a.page,a.cover,a.content,a.mp3,a.testmp3,b.kucun,c.name,c.name1,a.catalog,a.code,a.mp3type from category a, "
	// +" (select cate_id, count(*) kucun from book where 1=1 and cate_id = '"+cateId+"' group by cate_id) b, "
	// +" (select a.name,b.name as name1,b.id from leftcategory a,rightcategory b where b.left_id = a.id) c "
	// +" where a.cateID = b.cate_id and a.right_id = c.id ) a left  JOIN "
	// +" (select cateid as cateids,group_concat(b.nickname,'>',a.content,'>',a.star,'>',a.createdate) from comment a,member b where a.memberid = b.id  "
	// +" group by cateid ) c  on a.cateid = c.cateids"
	// +" )a left join (select a.categoryid,group_concat(b.name) info from categorylabel a,label b where a.labelid = b.id group by a.categoryid ) b on a.cateID = b.categoryid");
	// Object[] obj = null;
	// List list = this.executeSQL(sql.toString());
	// if(list.size()>0)
	// {
	// obj = (Object[])list.get(0);
	// }
	// return obj;
	// }

	@Override
	public void deleteCategoryLabel(String id) {

		this.executeUpdateSQL("delete from categorylabel where categoryid ='"
				+ id + "'");
	}

	@Override
	public void deleteLabel(String id) {

		this.executeUpdateSQL("delete from label where id = " + id);

	}

	@Override
	public void saveCategoryLabel(CategoryLabel categoryLabel) {

		this.saveOrUpdate(categoryLabel);
	}

	@Override
	public void saveLabel(Label label) {

		this.saveOrUpdate(label);
	}

	@Override
	public Page searchLabel(HashMap map) {

		StringBuffer sql = new StringBuffer(" from Label where 1=1 ");
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and name = '" + map.get("name") + "'");
		}
		if (!"".equals(map.get("type")) && map.get("type") != null) {
			sql.append(" and type = " + map.get("type"));
		}
		sql.append(" order by id desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public List searchLabelInfo(String name) {

		return this
				.executeSQL("select a.id,b.categoryid,a.name from label a ,categorylabel b where a.id = b.labelid and b,categiryid in ( "
						+ name + " )");
	}

	@Override
	public void saveLabelInfo(CategoryLabel categoryLabel) {

		this.saveOrUpdate(categoryLabel);

	}

	@Override
	public List searchBooks(String name) {

		String sql = "select cateID,bname,author from category where  bname='"
				+ name + "'";
		return this.executeSQL(sql);

	}

	@Override
	public void deleteBookVehicle(String ids) {

		this.executeUpdateSQL("delete from bookvehicle where id in( " + ids
				+ " )");
	}

	@Override
	public void deleteBookVehicleByMemberId(String ids, String memberId) {

		this.executeUpdateSQL(" delete from bookvehicle where cateid in ( "
				+ ids + " ) and memberid =  " + memberId);
	}

	@Override
	public void saveBookVehicle(BookVehicle bookVehicle) {

		this.saveEntity(bookVehicle);
	}

	@Override
	public List searchaBookVehicle(String memberId) {

		StringBuffer sql = new StringBuffer(
				" select a.cateID,bname,cover,content,price,author,b.id from category a,bookvehicle b where a.cateID = b.cateId and b.memberid = "
						+ memberId + " order by b.id desc");
		return this.executeSQL(sql.toString());

	}

	@Override
	public void saveBookLabel(BookLabel bookLabel) {

		this.saveEntity(bookLabel);

	}

	@Override
	public void deleteBookLabel(String id) {

		this.executeUpdateSQL("delete from booklabel where id =" + id);

	}

	@Override
	public Page searchBookLabel(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select a.id,b.name ,b.id as bid,a.ios,a.android,a.wechat  from booklabel a,label b where a.labelid = b.id  ");

		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and b.name  like '%").append(map.get("name"))
					.append("%'");

		}

		if (!"".equals(map.get("wechat")) && map.get("wechat") != null) {
			sql.append(" and a.wechat = 0");

		}
		if (!"".equals(map.get("ios")) && map.get("ios") != null) {
			sql.append(" and a.ios = 0");

		}
		if (!"".equals(map.get("android")) && map.get("android") != null) {
			sql.append(" and a.android = 0");

		}

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public void updateBookLabel(String id, String ios, String wechat,
			String android) {

		this.executeUpdateSQL(" update booklabel set ios=" + ios + ",wechat="
				+ wechat + ",android=" + android + " where id = " + id);

	}

	@Override
	public List searchBookByLabel(String labelId) {

		String sql = "  select a.cateId,b.bname,b.author,b.cover,a.labelid from ( "
				+ " select a.cateid,b.labelid from category a,categorylabel b "
				+ " where a.cateId = b.categoryId "
				+ " and b.labelid in ("
				+ labelId
				+ ") "
				+ " ) a ,category b where a.cateid = b.cateid limit 3";

		return this.executeSQL(sql);
	}

	@Override
	public List searchBookLabel() {

		return this
				.executeSQL(" select b.id ,b.name  from booklabel a,label b where a.labelid = b.id ");
	}

	@Override
	public List searchBookCount(String ids) {
		return this
				.executeSQL(" select a.cateID,a.bname,b.count from ( select * from category a where   cateID in ("
						+ ids
						+ ") ) a  "
						+ " LEFT JOIN (select cate_id,min(barcode) count  from book where cate_id in("
						+ ids
						+ ") and isexist = 1 "
						+ " group by cate_id ) b on  a.cateID = b.cate_id");

	}

	@Override
	public List searchBooksByIds(String ids) {

		String sql = " select cateID,bname,author,cover,price from category  where  cateID in ("
				+ ids + ")";
		return this.executeSQL(sql);

	}

	@Override
	public Page searchBookShops(HashMap map) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT " + "bookshop.id," + // 0
				"bookshop.account," + // 1
				"bookshop.`status`," + // 2
				"bookshop.`password`," + // 3
				"bookshop.`name`," + // 4
				"bookshop.email," + // 5
				"bookshop.telephone," + // 6
				"bookshop.contacts," + // 7
				"bookshop.createdate ," + // 8
				"bookshop.memberCardPrice ," + // 9
				"bookshop.logo ," + // 10
				"bookshop.type " + // 11
				"FROM `bookshop` where 1 = 1 ");
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			sql.append(" and name like ").append(" :name ");
		}
		sql.append(" ORDER BY " + "id DESC");
		Query query = this.getQuery(sql.toString());

		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			query.setString("name", "%" + map.get("name").toString() + "%");
		}

		ArrayList<BookShop> bookShops = new ArrayList<BookShop>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				BookShop bookShop = new BookShop();
				bookShop.setId((Integer) obj[0]);
				bookShop.setAccount((String) obj[1]);
				bookShop.setStatus((Integer) obj[2]);
				bookShop.setPassword((String) obj[3]);
				bookShop.setName((String) obj[4]);
				bookShop.setEmail((String) obj[5]);
				bookShop.setTelephone((String) obj[6]);
				bookShop.setContacts((String) obj[7]);
				bookShop.setCreatedate((Timestamp) obj[8]);
				bookShop.setMemberCardPrice((Float) obj[9]);
				bookShop.setLogo((String) obj[10]);
				bookShop.setType((Integer) obj[11]);
				bookShops.add(bookShop);
			}

		}

		Page resultPage = new Page<BookShop>(bookShops,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}
	
	@Override
	public CourseProjectActive findCourseProjectActive(Integer id) {
		CourseProjectActive courseProjectActive=new CourseProjectActive();
		List list=this.executeHQL("from CourseProjectActive where id = "+id);
		if(list.size()>0){
			courseProjectActive=(CourseProjectActive) list.get(0);
		}
		return courseProjectActive;
	}
	

	@Override
	public CourseProjectSystem findCourseProjectSystem(Integer planId) {
		CourseProjectSystem courseProjectSystem=new CourseProjectSystem();
		List list=this.executeHQL("from CourseProjectSystem where id = "+planId);
		if(list.size()>0){
			courseProjectSystem=(CourseProjectSystem) list.get(0);
		}
		return courseProjectSystem;
	}

	@Override
	public ArrayList findCourseProjectSystems() {
		ArrayList list=new ArrayList();
		list=(ArrayList) this.executeHQL("from CourseProjectSystem");
		return list;
	}

	@Override
	public void saveCourseProjectActive(CourseProjectActive courseProjectActive) {
		this.saveOrUpdate(courseProjectActive);
	}

	@Override
	public void deleteCourseProjectActive(Integer id) {
		this.executeUpdateSQL("delete from courseproject_active where id="+id);
	}

	@Override
	public Page searchCourseProjectActives(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT " + "courseproject_active.id," + // 0
				"courseproject_active.qrcode_url," + // 1
				"courseproject_active.plan_id ," + // 2
				"courseproject_active.plan_name," + // 3
				"courseproject_active.active_count," + // 4
				"courseproject_active.status," + // 5
				"courseproject_active.create_time " + // 6
				"FROM courseproject_active where 1 = 1 ");
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			sql.append(" and plan_name like ").append(" :plan_name ");
		}
		sql.append(" ORDER BY " + "id DESC");
		Query query = this.getQuery(sql.toString());

		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			query.setString("plan_name", "%" + map.get("name").toString() + "%");
		}

		ArrayList<CourseProjectActive> courseProjectActives = new ArrayList<CourseProjectActive>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				CourseProjectActive courseProjectActive = new CourseProjectActive();
				courseProjectActive.setId((Integer) obj[0]);
				courseProjectActive.setQrcodeUrl((String) obj[1]);
				courseProjectActive.setPlanId((Integer) obj[2]);
				courseProjectActive.setPlanName((String) obj[3]);
				courseProjectActive.setActiveCount((Integer) obj[4]);
				courseProjectActive.setStatus((Integer) obj[5]);
				courseProjectActive.setCreateTime((Timestamp) obj[6]);
				courseProjectActives.add(courseProjectActive);
			}

		}

		Page resultPage = new Page<CourseProjectActive>(courseProjectActives,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public BookShop searchBookShopById(int parseInt) {
		StringBuffer sql = new StringBuffer("from BookShop where id = "
				+ parseInt);
		ArrayList result = (ArrayList) this.executeHQL(sql.toString());
		BookShop bookShop = new BookShop();
		if (result.size() > 0) {
			bookShop = (BookShop) result.get(0);
		}
		return bookShop;
	}

	@Override
	public void saveBookShop(BookShop bookShop) {
		this.saveOrUpdate(bookShop);
	}

	@Override
	public void deleteBookShop(BookShop bookShop) {
		StringBuffer sql = new StringBuffer("delete from BookShop where id = "
				+ bookShop.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public BookShop loginBookShop(String account, String password) {
		StringBuffer sql = new StringBuffer("from BookShop where account = '"
				+ account + "' and password = '" + password + "'");
		ArrayList result = (ArrayList) this.executeHQL(sql.toString());
		BookShop bookShop = new BookShop();
		if (result.size() > 0) {
			bookShop = (BookShop) result.get(0);
		}
		return bookShop;
	}

	@Override
	public Page searchBookShopMembers(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"
				+ "	bookshop.id,"
				+ // 0
				"	bookshop.account,"
				+ // 1
				"	bookshop.`name`,"
				+ // 2
				"	member.nickname,"
				+ // 3
				"	bookcard.card,"
				+ // 4
				"	bookcard.`status`,"
				+ // 5
				"	bookcard.price,"
				+ // 6
				"	bookcard.createdate,"
				+ // 7
				"	member.headimgurl,"
				+ // 8
				"	bookcard.memberid,"
				+ // 9
				"	member.mobile"
				+ // 10
				"	FROM" + "	bookshop ," + "	member ," + "	bookcard" + "	WHERE"
				+ "	bookshop.id = bookcard.shopid AND"
				+ "	member.id = bookcard.memberid ");
		if (null != map.get("id") && !"".equals(map.get("id").toString())) {
			sql.append(" AND bookshop.id = ").append(" :id ");
		}
		if (null != map.get("memberId")
				&& !"".equals(map.get("memberId").toString())) {
			sql.append(" AND bookcard.memberid = ").append(" :memberId ");
		}
		if (null != map.get("memberPhone")
				&& !"".equals(map.get("memberPhone").toString())) {
			sql.append(" AND member.mobile = ").append(" :memberPhone ");
		}
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			sql.append(" AND bookshop.`name` like ").append(" :name ");
		}
		sql.append(" ORDER BY " + "bookshop.id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("id") && !"".equals(map.get("id").toString())) {
			query.setString("id", map.get("id").toString());
		}
		if (null != map.get("memberId")
				&& !"".equals(map.get("memberId").toString())) {
			query.setString("memberId", map.get("memberId").toString());
		}
		if (null != map.get("memberPhone")
				&& !"".equals(map.get("memberPhone").toString())) {
			query.setString("memberPhone", map.get("memberPhone").toString());
		}
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			query.setString("name", "%" + map.get("name").toString() + "%");
		}

		ArrayList<MemberCardDto> memberCardDtos = new ArrayList<MemberCardDto>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				MemberCardDto memberCardDto = new MemberCardDto();
				memberCardDto.setId((Integer) obj[0]);
				memberCardDto.setAccount((String) obj[1]);
				memberCardDto.setName((String) obj[2]);
				memberCardDto.setNickname((String) obj[3]);
				memberCardDto.setCard((String) obj[4]);
				memberCardDto.setStatus((Integer) obj[5]);
				memberCardDto.setPrice((Integer) obj[6]);
				memberCardDto.setCreatedate((Timestamp) obj[7]);
				memberCardDto.setHeadimgurl((String) obj[8]);
				memberCardDto.setMemberId((Integer) obj[9]);
				memberCardDto.setMemberPhone((String) obj[10]);
				memberCardDtos.add(memberCardDto);
			}
		}

		Page resultPage = new Page<MemberCardDto>(memberCardDtos,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void updateBookShopCategory(String shopId) {

		List list = this
				.executeSQL(" select a.cateId,max(a.bname), max(replace(replace(b.barcode,'C',''),'E','')) from category a ,book b "
						+ " where a.cateID = b.cate_id " + " group by a.cateId");

	}

	@Override
	public void updateBookCategoryStatus(String bookCode, String status) {

		this.executeUpdateSQL(" update book set isexist = " + status
				+ " where barcode='" + bookCode + "'");

	}

	@Override
	public Page searchBookCategory(HashMap map) {
		StringBuffer sql = new StringBuffer(" from BookCategory where 1=1 ");
		if (!"".equals(map.get("catId")) && map.get("catId") != null) {
			sql.append(" and cat_id = " + map.get("catId"));
		}
		sql.append(" order by sort ASC ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public void saveBookCategory(BookCategory bookCategory) {
		this.saveOrUpdate(bookCategory);
	}

	@Override
	public void deleteBookCategory(int catId) {
		BookCategory bookCategory = new BookCategory();
		bookCategory.setCat_id(new Integer(catId));
		this.removeEntity(bookCategory);
	}

	@Override
	public List searchIndexLabel(String type) {

		StringBuffer sql = new StringBuffer("");
		if ("0".equals(type)) {
			sql = new StringBuffer(
					" select b.id,b.name,a.* from ( "
							+ "  select labelid,count(* ) productcount from productlabel group by labelid "
							+ "  ) a ,label b where a.labelid = b.id "
							+ "  order by productcount desc ");
		} else {
			sql = new StringBuffer(
					" select b.id,b.name,a.* from (  "
							+ " select labelid,count(*) bookcount from categorylabel GROUP BY  labelid "
							+ " ) a ,label b where a.labelid = b.id "
							+ " order by bookcount desc ");
		}

		return this.executeSQL(sql.toString());
	}

	@Override
	public Page searchBookShopCount(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select b.name,a.* from ( select belong,count(barcode) from book where 1=1 GROUP BY belong ) a ,bookshop b where a.belong = b.id");

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public List searchBookBarCode(String cateIds) {

		return this
				.executeSQL(" select barcode from book where 1=1 and cate_id in ( "
						+ cateIds + " ) ");
	}

	@Override
	public void addBookCode(List bookList) {

	}

	@Override
	public List<BookCategory> getBookCategoryLevelOne() {
		return this.executeHQL("from BookCategory where parent_id = 0 ");
	}

	@Override
	public Page searchBooksByCatIds(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"
				+ "	temp_1.cat_id,"
				+ "	temp_1.parent_id,"
				+ "	temp_1.cat_name,"
				+ "	temp_1.sort,"
				+ "	GROUP_CONCAT("
				+ "		temp_2.cateID,"
				+ // 0
				"		\'け\',"
				+ "		temp_2.bname,"
				+ // 1
				"		\'け\',"
				+ "		temp_2.author,"
				+ // 2
				"		\'け\',"
				+ "		temp_2.publish,"
				+ // 3
				"		\'け\',"
				+ "		temp_2.price,"
				+ // 4
				"		\'け\',"
				+ "		temp_2.translator,"
				+ // 5
				"		\'け\',"
				+ "		temp_2.page,"
				+ // 6
				"		\'け\',"
				+ "		temp_2.remark,"
				+ // 7
				"		\'け\',"
				+ "		temp_2.cover,"
				+ // 8
				"		\'け\',"
				+ "		temp_2.series,"
				+ // 9
				"		\'け\',"
				+ "		temp_2.`status`,"
				+ // 10
				"		\'け\',"
				+ "		temp_2.mp3,"
				+ // 11
				"		\'け\',"
				+ "		temp_2.testmp3,"
				+ // 12
				"		\'け\',"
				+ "		temp_2.`code`,"
				+ // 13
				"		\'け\',"
				+ "		temp_2.mp3type,"
				+ // 14
				"		\'\' SEPARATOR \'ぃ\'" + "	) AS books" + " FROM" + "	("
				+ "		SELECT" + "			book_category.sort,"
				+ "			book_category.parent_id," + "			book_category.cat_name,"
				+ "			book_category.cat_id" + "		FROM" + "			book_category"
				+ "		WHERE" + "			book_category.cat_id IN ("
				+ map.get("catIds").toString()
				+ "			)"
				+ "	) AS temp_1"
				+ " LEFT JOIN ("
				+ "	SELECT"
				+ "		category.cateID,"
				+ "		category.bname,"
				+ "		category.price,"
				+ "		category.publish,"
				+ "		category.author,"
				+ "		category.translator,"
				+ "		category.page,"
				+ "		category.remark,"
				+ "		category.cover,"
				+ "		category.count,"
				+ "		category.bquantity,"
				+ "		category.right_id,"
				+ "		category.series,"
				+ "		category.mp3,"
				+ "		category.testmp3,"
				+ "		category.`code`,"
				+ "		category.mp3type,"
				+ "		category.`status`,"
				+ "		category.book_cate_id"
				+ "	FROM"
				+ "		category "
				+ " ) AS temp_2 ON temp_2.book_cate_id = temp_1.cat_id"
				+ " GROUP BY" + "	temp_1.cat_id");

		Query query = this.getQuery(sql.toString());

		Page resultPage = null;

		HashMap dataRecord = null;

		ArrayList<HashMap> dataRecords = new ArrayList<HashMap>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				dataRecord = new HashMap();
				dataRecord.put("id", String.valueOf(obj[0]));
				dataRecord.put("parentId", String.valueOf(obj[1]));
				dataRecord.put("parentName", String.valueOf(obj[2]));
				dataRecord.put("sort", String.valueOf(obj[3]));
				String text = (String) obj[4];
				ArrayList books = new ArrayList();
				if (!"".equals(text) && null != text) {
					String[] booksStrArr = text.split("ぃ");
					int booksStrArrLength = booksStrArr.length;
					// 限制每个分类下的数据长度
					// if(booksStrArrLength>=3){
					// booksStrArrLength=3;
					// }
					for (int j = 0; j < booksStrArrLength; j++) {
						HashMap book = new HashMap();
						if (!"".equals(booksStrArr[j])
								&& null != booksStrArr[j]) {
							String[] bookStrArr = booksStrArr[j].split("け");
							book.put("cateID", bookStrArr[0]);
							book.put("bname", bookStrArr[1]);
							book.put("author", bookStrArr[2]);
							book.put("publish", bookStrArr[3]);
							book.put("price", bookStrArr[4]);
							book.put("translator", bookStrArr[5]);
							book.put("page", bookStrArr[6]);
							book.put("remark", bookStrArr[7]);
							book.put("cover", bookStrArr[8]);
							book.put("series", bookStrArr[9]);
							book.put("status", bookStrArr[10]);
							book.put("mp3", bookStrArr[11]);
							book.put("testmp3", bookStrArr[12]);
							book.put("code", bookStrArr[13]);
							book.put("mp3type", bookStrArr[14]);
						}
						books.add(book);
					}
				}
				JSONObject result = new JSONObject();
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Timestamp.class,
						new JsonTimeStampValueProcessor());
				result.accumulate("books", books, jsonConfig);
				dataRecord.put("data", result.toString());
				dataRecords.add(dataRecord);

			}

		}

		resultPage = new Page<HashMap>(dataRecords, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}
	
	
	@Override
	public List searchBookLabelInfo()
	{
		
		//分隔符://⊥ ∪∩∫⊙
		StringBuffer sql = new StringBuffer( //labelId分组,GROUP_CONCAT(cateId,书籍名称,作者,封面,以⊥ 分隔,记录集以∪分隔)
				" SELECT"+
				"	a.id,"+
				"	max(a. NAME),"+
				"	GROUP_CONCAT("+
				"		b.cateID,"+
				"		\'⊥\',"+
				"		b.bname,"+
				"		\'⊥\',"+
				"		IFNULL(b.author,''),"+
				"		\'⊥\',"+
				"		IFNULL(b.cover,'')"+
				"	ORDER BY"+
				"		substring("+
				"			b.cateID,"+
				"			1,"+
				"			length(b.cateID)"+
				"		) DESC SEPARATOR \'∪\'"+
				"	)"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			b.id,"+
				"			b. NAME"+
				"		FROM"+
				"			booklabel a,"+
				"			label b"+
				"		WHERE"+
				"			a.labelid = b.id"+
				"		AND a.ios = 0"+
				"	) a"+
				" LEFT JOIN ("+
				"	SELECT"+
				"		a.bname,"+
				"		a.cateId,"+
				"		a.cover,"+
				"		a.author,"+
				"		b.labelid"+
				"	FROM"+
				"		category a,"+
				"		categorylabel b"+
				"	WHERE"+
				"		a.cateID = b.categoryid"+
				" ) b ON a.id = b.labelid"+
				" GROUP BY"+
				"	a.id");
		return this.executeSQL(sql.toString());
	}
	
	
	
	@Override
	public List searchBooKIndexInfo()
	{
		
		//分隔符://⊥ ∪∩∫⊙
		//书籍类型(1级)cat_id 分组,GROUP_CONCAT(书籍类型ID⊥书籍类型(2级)⊥书籍集合),以∪号分隔
		//书籍集合GROUP_CONCAT(cateId⊙书籍名称⊙作者⊙封面),以'∩'号分隔
		String sql = "SELECT"+		
				"	b.unique_id,"+
				"	GROUP_CONCAT("+
				"		a.cat_id,"+
				"		\'⊥\',"+
				"		a.categoryname,"+
				"		\'⊥\',"+
				"		a.info"+
				"	ORDER BY"+
				"		a.sort ASC SEPARATOR \'∪\'"+
				"	)"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			b.cat_id,"+
				"			max(b.sort) sort,"+
				"			max(b.unique_id) categoryname,"+
				"			max(b.parent_id) parent_id,"+
				"			GROUP_CONCAT("+
				"				a.cateID,"+
				"				\'⊙\',"+
				"				a.bname,"+
				"				\'⊙\',"+
				"				IFNULL(a.author,''),"+
				"				\'⊙\',"+
				"				IFNULL(a.cover,'')"+
				"			ORDER BY"+
				"				substring("+
				"					a.cateID,"+
				"					1,"+
				"					length(a.cateID)"+
				"				) DESC SEPARATOR \'∩\'"+
				"			) info"+
				"		FROM"+
				"			category a,"+
				"			book_category b"+
				"		WHERE"+
				"			a.book_cate_id = b.cat_id"+
				"		GROUP BY"+
				"			b.cat_id"+
				"	) a,"+
				"	book_category b"+
				" WHERE"+
				"	a.parent_id = b.cat_id"+
				" GROUP BY"+
				"	b.unique_id"+
				" order by b.sort asc";
				return this.executeSQL(sql);
	}
	
//			select * from (
//			select memberid,count(*) from bookorder where memberid = 278 group by memberid
//			) a 
//
//			(
//			select b.id,sum(if(b.status=0,1,0)) outbook ,sum(if(b.status=1,1,0)) inputbook   
//			from bookorder a ,bookorderinfo b where a.id = b.orderid and a.memberid = 278 group by memberid
//			) b 
	
	
	@Override
	public Object searchMemberBookInfo(String memberId)
	{
		String sql =  "SELECT"+
				"	a.*, b.outbook,"+
				"	b.inputbook,"+
				"	c.enddate,"+
				"	c.type"+
				"FROM"+
				"	("+
				"		SELECT"+
				"			memberid,"+
				"			count(*)"+
				"		FROM"+
				"			bookorder"+
				"		WHERE"+
				"			1 = 1"+
				"		GROUP BY"+
				"			memberid"+
				"	) a,"+
				"	("+
				"		SELECT"+
				"			a.memberid,"+
				"			sum(IF(b. STATUS = 0, 1, 0)) outbook,"+
				"			sum(IF(b. STATUS = 1, 1, 0)) inputbook"+
				"		FROM"+
				"			bookorder a,"+
				"			bookorderinfo b"+
				"		WHERE"+
				"			a.id = b.orderid"+
				"		GROUP BY"+
				"			memberid"+
				"	) b,"+
				"	memberbook c"+
				" WHERE"+
				"	a.memberid = b.memberid"+
				" AND a.memberid = c.memberid and a.memberid  ="+memberId;
		return this.executeSQL(sql);
	}
	
	
	@Override
	public Page searchBookCollection(HashMap map)
	{
		String sql = " select b.cateID,b.bname,b.author,b.cover from bookcollection a ,category b where a.cateid = b.cateID and a.mem_id = "+map.get("memberId");
		return this.pageQueryBySQL(sql, new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	@Override
	public void deleteBookCollection(String id )
	{
		this.executeUpdateSQL(" delete from bookcollection where id = "+id);
	}

	@Override
	public HashMap searchBookName() throws Exception {
		
		List list = this.executeSQL("select cateID from category where 1=1 ");
		HashMap map = new HashMap();
		
		
		for (int i = 0; i < list.size(); i++) {
			
			
			
			map.put(list.get(i).toString(), list.get(i).toString());
		}
		return map;
	}

	@Override
	public void saveBookList(List list) throws Exception {
		
//		String sql = " insert into category(cateId,bname,author,publish,translator,page,price,content,count,status,remark) values(?,?,?,?,?,1,1,?,1,1,'test');";
//		Connection connection = this.getConnection();
//		PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
		final int batchSize = 1000;
		int count = 0;
		Session session = getOpenSession();
		Category cateGory = new Category();
		Transaction tx = session.beginTransaction();
		 //tx.begin();
		try {
			
			
			for (int i = 0; i < list.size(); i++) 
			{
				cateGory = (Category) list.get(i);
				cateGory.setRemark("testadd");
				session.saveOrUpdate(cateGory);
			    
			    if(++count % batchSize == 0) {
			    	session.flush();
		            session.clear();
		            tx.commit(); // 提交事务
		            tx = session.beginTransaction();
			    }
			    
			}
			tx.commit(); // 提交事务
		} catch (Exception e) {
			tx.rollback();
		}
		finally
		{
			session.close();
		}
		
		
		
//		ps.executeBatch(); // insert remaining records
//		//System.out.println("====add is "+count+" info ====");
//		ps.close();
//		connection.close();
	}

	@Override
	public void saveBookInfoList(List list) throws Exception {
		
		
		
		String sql = " insert into book values(?,1,?,?,?,?);  ";
		Connection connection = this.getConnection();
		PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
		final int batchSize = 1000;
		int count = 0;
		
		Book book = new Book();
		for (int i = 0; i < list.size(); i++) 
		{
			book = (Book) list.get(i);
		    ps.setString(1, book.getBarCode());
		    ps.setString(2, book.getCateId());
		    ps.setInt(3, book.getBelong());
		    ps.setString(4, book.getUrl());
		    ps.setString(5, book.getCodeInfo());
		    
		    
		    ps.addBatch();
		    
		    if(++count % batchSize == 0) {
		        ps.executeBatch();
		    }
		}
		ps.executeBatch(); // insert remaining records
		ps.close();
		connection.close();
	}

	@Override
	public Page searchCourseProjectSystems(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT " + "courseproject_system.id," + // 0
				"courseproject_system.lessonList ," + // 1
				"courseproject_system.plan_type," + // 2
				"courseproject_system.projectname," + // 3
				"courseproject_system.createdate," + // 4
				"courseproject_system.sort " + // 5
				"FROM courseproject_system where 1 = 1 ");
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			sql.append(" and projectname like ").append(" :projectname ");
		}
		sql.append(" ORDER BY " + "id DESC");
		Query query = this.getQuery(sql.toString());

		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			query.setString("projectname", "%" + map.get("name").toString() + "%");
		}

		ArrayList<CourseProjectSystem> courseProjectSystems = new ArrayList<CourseProjectSystem>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				CourseProjectSystem courseProjectSystem = new CourseProjectSystem();
				courseProjectSystem.setId((Integer) obj[0]);
				courseProjectSystem.setLessonList((String) obj[1]);
				courseProjectSystem.setPlanType((String) obj[2]);
				courseProjectSystem.setProjectName((String) obj[3]);
				courseProjectSystem.setCreateDate((Date) obj[4]);
				courseProjectSystem.setSort((Integer) obj[5]);
				courseProjectSystems.add(courseProjectSystem);
			}

		}

		Page resultPage = new Page<CourseProjectSystem>(courseProjectSystems,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void deleteCourseProjectSystem(Integer id) {
		this.executeUpdateSQL("delete from courseproject_system where id = "+ id);
	}

	@Override
	public void saveCourseProjectSystem(CourseProjectSystem courseProjectSystem) {
		this.saveOrUpdate(courseProjectSystem);
	}
	
	
	
	@Override
	public Page searchBookShopSale(HashMap map)
	{
		
		StringBuffer sql = new StringBuffer("select a.shopid,max(b.name) , count(a.id),sum(a.price) from bookcard a, bookshop b  where  a.shopid = b.id  ");
		
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			
			sql.append(" and b.name like '%").append(map.get("name").toString()).append("%'");
		}
		if (null != map.get("shopId") && !"".equals(map.get("shopId").toString())) {
			
			sql.append(" and b.id = ").append(map.get("shopId").toString());
		}
		
		if (null != map.get("startDate") && !"".equals(map.get("startDate").toString())) {
			
			sql.append(" and a.createdate >='").append(map.get("startDate").toString()).append("'");
		}
	
		if (null != map.get("endDate") && !"".equals(map.get("endDate").toString())) {
		
			sql.append(" and a.createdate <='").append(map.get("endDate").toString()).append("'");
		}
		
		sql.append(" group by a.shopid ");
		
		
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
		
	
	}

	@Override
	public Object[] searchBookShopSum(HashMap map) {
		
		
		StringBuffer sql = new StringBuffer(" select  count(a.id),sum(a.price) from bookcard a, bookshop b  where  a.shopid = b.id   ");
		
		if (null != map.get("shopId") && !"".equals(map.get("shopId").toString())) {
			
			sql.append(" and b.id = ").append(map.get("shopId").toString());
		}

		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			
			sql.append(" and b.name like '%").append(map.get("name").toString()).append("%'");
		}
		
		if (null != map.get("startDate") && !"".equals(map.get("startDate").toString())) {
			
			sql.append(" and a.createdate >='").append(map.get("startDate").toString()).append("'");
		}
	
		if (null != map.get("endDate") && !"".equals(map.get("endDate").toString())) {
		
			sql.append(" and a.createdate <='").append(map.get("endDate").toString()).append("'");
		}
		
		List list =this.executeSQL(sql.toString());
		Object[] obj  = new Object[1];
		
		if(list.size()>0)
		{
			obj   = (Object[])list.get(0);
			
		}
		else
		{
			obj[0] ="0";
			obj[1] ="0";
		}
		return obj;
	}
	
	
	@Override
	public Page searchBook(HashMap map)
	{
		
		StringBuffer sql = new StringBuffer("");
		
		if("0".equals(map.get("searchType")))
		{
			sql = new StringBuffer("select a.cateId,a.bname,a.publish,a.author,a.cover from category a where 1=1 ");
		}
		else
		{
			sql  = new StringBuffer(" select a.cateId,a.bname,a.publish,a.author,a.cover from category a " 
					+" ,  categorylabel b   where a.cateID = b.categoryid  ");
			
			if(!"".equals(map.get("labelId")) && map.get("labelId")!= null)
			{
				sql.append("  and b.labelid in ( ").append(map.get("labelId")).append(" )");
			}
			

		}
		if(!"".equals(map.get("name")) && map.get("name")!= null)
		{
			sql.append("  and a.bname like '%").append(map.get("name")).append("%'");
		}
		
		
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public Page findAnchors(HashMap map) {
		return this.pageQueryByHql("from Anchor", new Integer(map.get("page").toString()), new Integer(map.get("pageSize").toString()));
	}

	@Override
	public void saveAnchor(Anchor anchor) {
		this.saveOrUpdate(anchor);
	}

	@Override
	public void deleteAnchor(Integer id) {
		StringBuffer sql = new StringBuffer("delete from Anchor where id = "
				+ id);
		this.executeHQL(sql.toString());
	}
	
	
	@Override
	public Page searchBookShopCategoryInfo(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"  select a.*,b.categoryid from (  select a.*,b.info,c.keyword from ( select a.*,b.* from category a,(select a.id,a.name,b.id as typeid,b.name as typename  from leftcategory a ,rightcategory b where a.id = b.left_id) b where a.right_id = b.typeid ");

		if (!"".equals(map.get("cateId")) && map.get("cateId") != null) {
			sql.append(" and a.cateID ='" + map.get("cateId")).append("'");
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.bname like  '%" + map.get("name")).append("%'");
		}
		if (!"".equals(map.get("author")) && map.get("author") != null) {
			sql.append(" and a.author ='" + map.get("author")).append("'");
		}
		if (!"".equals(map.get("publish")) && map.get("publish") != null) {
			sql.append(" and a.publish ='" + map.get("publish")).append("'");
		}
		if (!"".equals(map.get("rightType")) && map.get("rightType") != null) {
			sql.append(" and a.right_id =" + map.get("rightType"));
		}
		if (!"".equals(map.get("leftType")) && map.get("leftType") != null) {
			sql.append(" and b.id =" + map.get("leftType"));
		}
		if (!"".equals(map.get("catIds")) && map.get("catIds") != null) {
			sql.append(" and a.book_cate_id in ( ")
					.append(map.get("catIds").toString()).append(" )");
		}
		sql.append("  order by  replace(replace(a.cateID,'C',''),'E','') desc ");
		sql.append("  ) a LEFT JOIN (select a.categoryid,group_concat(b .name) info from categorylabel a,label b where a.labelid = b.id group by a.categoryid) b on a.cateID = b.categoryid");
		sql.append("  LEFT JOIN  (select cateid,group_concat(name) keyword  from bookkeyword GROUP BY cateid)  c on a.cateID = c.cateid ) a LEFT JOIN book_shop_category b on a.cateID = b.categoryid ");
		sql.append(" and b.shopid in ( select shopid from book_shop_user where userid =   ").append(map.get("userId")).append(" ) ").append(" order by b.categoryid desc  ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	@Override
	public void addShopCategory(BookShopCategory bookShopCategory)
	{
		this.saveEntity(bookShopCategory);
	}
	
	@Override
	public String getShopIdByUserId(String userId)
	{
		String shopId = "";
		
		List list = this.executeSQL(" select shopid from book_shop_user where userid = "+userId);
		if(list.size()>0)
		{
			shopId = list.get(0).toString();
		}
		return shopId;
	}
	
	
	@Override
	public void deleteBookShopCateId(String cateID)
	{
		this.executeUpdateSQL("delete from book_shop_category where categoryid ='"+cateID+"'");
	}
	
	@Override
	public List searchBookQRCode(String cateIds,String shopId)
	{
		
		return this.executeHQL(" from Book where belong = "+shopId+" and cateId in ( "+cateIds+" ) ");
	}

	@Override
	public Page getBookList(HashMap map) {
		// TODO Auto-generated method stub
		String shopId = getShopIdByUserId(map.get("userId").toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select a.cate_id,b.bname,b.author,b.publish,b.price,b.series,count(cate_id) from book as a,category as b where a.cate_id=b.cateID and belong="+shopId);
		if(null!=map.get("searchStr") && !"".equals("searchStr")){
			
			sql.append(" and b.bname like '%"+map.get("searchStr").toString()+"%'");
		}
		if(null!=map.get("cateId") && !"".equals("cateId")){
			
			sql.append(" and a.cate_id='"+map.get("cateId").toString()+"'");
		}
		sql.append(" group by cate_id");
		return this.pageQueryBySQL(sql.toString(), Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	
	@Override
	public Page searchBookByName(HashMap map) {
		// TODO Auto-generated method stub
		String bookName = map.get("bookName").toString();
		StringBuffer sql = new StringBuffer();
		sql.append("select cateID,bname,ifnull(cover,''),translator,series from category where CONCAT(IFNULL(bname,''),IFNULL(translator,''),IFNULL(series,'')) like '%"+bookName+"%'");
		return this.pageQueryBySQL(sql.toString(), Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}

	@Override
	public Page getBookStockList(HashMap map) {
		// TODO Auto-generated method stub
		String shopId = map.get("shopId").toString();
		String cateId = map.get("cateId").toString();
		StringBuffer sql = new StringBuffer();
		sql.append("select barcode,codeInfo,isexist,copy_times,status from book where belong='"+shopId+"' and cate_id='" + cateId +"'");
		return this.pageQueryBySQL(sql.toString(), Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
	}
	
}
