package com.wechat.dao.impl;


import com.wechat.dao.LibraryDao;
import com.wechat.entity.Library;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public class LibraryDaoImpl extends BaseDaoImpl implements LibraryDao {

	@Override
	public void saveLibrary(Library library) {
		String ISBN = library.getISBN();
		String bookName = library.getBookName();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from library where isbn=:ISBN and book_name=:bookName").append("  ") ;
		Query query = this.getQuery(sql.toString());
		query.setString("ISBN",ISBN);
		query.setString("bookName",bookName);
		Page dataPage = this.pageQueryBySQL(query,1, 1);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {
			
		}else{
			this.saveOrUpdate(library);
		}
		
		
	}

	@Override
	public String getBookName(String ISBN){
		String bookName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select * from library where isbn =").append(" :ISBN ");
		Query query = this.getQuery(sql.toString());
		if (null != ISBN && !"".equals(ISBN)) {
			query.setString("ISBN",ISBN);
		}
		Page dataPage = this.pageQueryBySQL(query,1, 1);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {
			Object[] obj = dataList.get(0);
			bookName = (String)obj[2];
		}else{
			bookName = "";
		}
		return bookName;

	}

	@Override
	public JSONArray getbookNameList(String iSBN) {
		// TODO Auto-generated method stub
		JSONArray result = new JSONArray();
		String bookName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select * from library where isbn =").append(" :ISBN ");
		Query query = this.getQuery(sql.toString());
		if (null != iSBN && !"".equals(iSBN)) {
			query.setString("ISBN",iSBN);
		}
		Page dataPage = this.pageQueryBySQL(query,1, 1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONObject temp = new JSONObject();
			temp.put("bookName", obj[2]);
			result.add(temp);
		}
		return result;
	}
}
