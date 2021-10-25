package com.wechat.dao.impl;

import com.wechat.dao.CategoryDao;
import com.wechat.entity.BookKeyword;
import com.wechat.entity.Special;
import com.wechat.entity.SpecialInfo;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {

	@Override
	public void deleteSpecial(String id) {

		this.executeUpdateSQL(" delete from special where id = " + id);
	}

	@Override
	public void deleteSpecialInfo(String SpecialId) {

		this.executeUpdateSQL(" delete from specialinfo where specialid = "
				+ SpecialId);
	}

	@Override
	public void saveSpecial(Special special) {

		this.saveOrUpdate(special);
	}

	@Override
	public void saveSpecialInfo(SpecialInfo specialInfo) {

		this.saveOrUpdate(specialInfo);
	}

	@Override
	public Page searchSpecial(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"  select * from  special a LEFT JOIN ( "
						+ " select a.id as tempId,group_concat(c.cateID,'>',c.bname,'>',c.author) from special a,specialinfo b,category c  where a.id = b.specialid and b.categoryid= c.cateID "
						+ " group by a.id) b on a.id = b.tempId  where 1=1 ");
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and title like '%").append(map.get("name").toString())
					.append("%'");
		}

		if (!"".equals(map.get("topicType")) && map.get("topicType") != null) {
			sql.append(" and topic_type =").append(
					map.get("topicType").toString());
		}
		if (!"".equals(map.get("status")) && map.get("status") != null) {
			sql.append(" and status =").append(map.get("status").toString());
		}
		sql.append("  order by sort asc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public List searchSpecialIndex() {

		return this.executeHQL(" from Special where 1=1 ");
	}

	@Override
	public List searchSpecialInfo(String SpecialId) {

		String sql = "select b.cateId from specialinfo a,category b where a.categoryid = b.cateId";
		return this.executeSQL(sql);
	}

	@Override
	public void updateSpecialSort(String id, String sort) {
		executeUpdateSQL(" update special set sort = " + sort + "  where id = "
				+ id);
	}

	@Override
	public void updateSpecialStatus(String id, String status) {
		executeUpdateSQL(" update special set status = " + status
				+ "  where id = " + id);
	}

	@Override
	public String searchMaxSort() {
		List list = executeSQL("select max(sort) from special where 1=1 ");
		if (list.get(0) != null) {
			return list.get(0).toString();
		}

		return "0";
	}

	@Override
	public List searchBooksBySpecial(String id) {
		StringBuffer sql = new StringBuffer(
				" select * from ( "
						+ " select b.cateID,b.bname,b.author,b.cover,b.content ,a.fraction,a.star,a.countid from (  "
						+ "  select * from specialinfo a LEFT JOIN   "
						+ " ( select cateid as cateids ,sum(fraction) fraction ,sum(star) star,count(id) countid from comment   where 1=1  "
						+ " 	group by cateid  "
						+ " )  b on a.categoryid = b.cateids ) a LEFT JOIN category b  on    a.categoryid = b.cateID and a.specialid =  "
						+ id + " ) a where a.cateID is not null");

		return this.executeSQL(sql.toString());
	}

	@Override
	public Page searchBooksByLabel(HashMap map) {
		StringBuffer sql = new StringBuffer(
				" select a.* from ( "
						+ " 	select * from ( "
						+ "select a.cateID,a.bname,a.author,a.cover,a.content,b.* from category a , "
						+ "( "
						+ "select  a.id as leftid,a.name,b.id as rightid,b.name as testname from leftcategory a ,rightcategory b where b.left_id = a.id "
						+ ") b where a.right_id = b.rightid )a LEFT JOIN ( "
						+ " select cateid as cateids ,sum(fraction) fraction ,sum(star) star,count(id) countid from comment   where 1=1  "
						+ "	group by cateid ) b on a.cateID = b.cateids "
						+ ") a ");

		if (!"".equals(map.get("labelId")) && map.get("labelId") != null) {
			sql.append(" ,(select b.categoryid,a.* from label a,categorylabel b where a.id = b.labelid");
			sql.append(" and b.labelid =  " + map.get("labelId").toString());
			sql.append(") b where a.cateID = b.categoryid");
		} else {
			sql.append(" where 1=1 ");
		}

		if (!"".equals(map.get("leftId")) && map.get("leftId") != null) {
			sql.append(" and a.leftid  =  " + map.get("leftId").toString());
		}
		if (!"".equals(map.get("rightId")) && map.get("rightId") != null) {
			sql.append(" and a.rightid  =  " + map.get("rightId").toString());
		}

		if (!"".equals(map.get("bookName")) && map.get("bookName") != null) {
			sql.append("  and a.bname like '%" + map.get("bookName").toString())
					.append("%'");
		}

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public void deleteBookKeyword(String cateId) {

		this.executeUpdateSQL("delete from bookkeyword where cateId='" + cateId
				+ "'");
	}

	@Override
	public void saveBookKeyword(BookKeyword bookKeyword) {

		this.saveEntity(bookKeyword);
	}

	@Override
	public List searchBookKeyword(String cateId) {

		return this.executeHQL(" from BookKeyword where cateId='" + cateId
				+ "'");
	}

	@Override
	public List searchBookByCode(String code) {
		return this.executeHQL(" from Category where code like '%" + code
				+ "%'");
	}

	@Override
	public Page searchBooksByLabelIdAndKeyWord(HashMap map) {
		StringBuffer sql = new StringBuffer(
						"SELECT"+
						"	a.*"+
						" FROM"+
						"	("+
						"		SELECT"+
						"			*"+
						"		FROM"+
						"			("+
						"				SELECT"+
						"					a.cateID,"+
						"					a.bname,"+
						"					a.author,"+
						"					a.cover,"+
						"					a.content,"+
						"					a.book_cate_id,"+
						"					b.*"+
						"				FROM"+
						"					category a,"+
						"					("+
						"						SELECT"+
						"							a.id AS leftid,"+
						"							a. NAME,"+
						"							b.id AS rightid,"+
						"							b. NAME AS testname"+
						"						FROM"+
						"							leftcategory a,"+
						"							rightcategory b"+
						"						WHERE"+
						"							b.left_id = a.id"+
						"					) b"+
						"				WHERE"+
						"					a.right_id = b.rightid"+
						"			) a"+
						"		LEFT JOIN ("+
						"			SELECT"+
						"				cateid AS cateids,"+
						"				sum(fraction) fraction,"+
						"				sum(star) star,"+
						"				count(id) countid"+
						"			FROM"+
						"				wechat.`comment`"+
						"			WHERE"+
						"				1 = 1"+
						"			GROUP BY"+
						"				cateid"+
						"		) b ON a.cateID = b.cateids"+
						"	) a"+
						"  WHERE"+
						"	1 = 1");
		if (!"".equals(map.get("labelId")) && map.get("labelId") != null) {
			sql.append( "  AND a.cateID IN ("+
						"	SELECT"+
						"		b.categoryid"+
						"	FROM"+
						"		label a,"+
						"		categorylabel b"+
						"	WHERE"+
						"		a.id = b.labelid"+
						"	AND b.labelid = "+ map.get("labelId").toString()+
						")");
		}
		
		if (!"".equals(map.get("bookName")) && map.get("bookName") != null) {
			sql.append("  AND a.bname like '%" + map.get("bookName").toString()+"%' OR a.book_cate_id IN ("+
							"	SELECT"+
							"		cat_id"+
							"	FROM"+
							"		book_category"+
							"	WHERE"+
							"		1 = 1"+
							"	AND cat_name LIKE '%"+map.get("bookName").toString()+"%'"+
							")");
		}
		
		if (!"".equals(map.get("leftId")) && map.get("leftId") != null) {
			sql.append(" AND a.leftid  =  " + map.get("leftId").toString());
		}
		
		if (!"".equals(map.get("rightId")) && map.get("rightId") != null) {
			sql.append(" AND a.rightid  =  " + map.get("rightId").toString());
		}

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

}
