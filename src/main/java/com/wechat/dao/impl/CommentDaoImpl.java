package com.wechat.dao.impl;

import com.wechat.dao.CommentDao;
import com.wechat.entity.Comment;
import com.wechat.entity.CommentImg;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommentDaoImpl extends BaseDaoImpl implements CommentDao {

	
	@Override
	public void saveComment(Comment comment) {
		
		this.save(comment);
	}

	
	@Override
	public void saveCommentImg(CommentImg commentImg) {
		
		this.save(commentImg);
	}

	
	@Override
	public Page searchComment(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" select * from ( "
				+" select a.productid,c.nickname,a.content,a.createDate,a.star,b.imgs from comment a left join "
				+" ( "
				+" select commentid,group_concat(img) imgs from commentimg GROUP BY commentid "
				+" ) b "
				+" on a.id = b.commentid "
				+" left join member c on a.memberid = c.id "
				+" ) a where 1=1 ");
		if(!"".equals(map.get("productId")) &&  map.get("productId")!= null)
		{
			sql.append(" and productid = ").append(map.get("productId"));
		}
		sql.append(" order by createdate desc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()) , new Integer(map.get("rowsPerPage").toString()));
	}

	
	@Override
	public List searchCommentImg(String productId) {
		
		return this.executeHQL(" from CommentImg where 1=1 and productId = "+productId);
	}
	
	  @Override
	public String getProductComment(String orderId)
	  {
	    List list = executeSQL(" select id from comment where orderid = " + orderId);
	    String count = "0";
	    if (list.size() > 0) {
	      count = "1";
	    }
	    return count;
	  }
	
	@Override
	public void updateProductCommentStatus(String id)
	{
		this.executeUpdateSQL("update mallorderproduct set commentstatus = 1 where id = "+id);
	}

}
