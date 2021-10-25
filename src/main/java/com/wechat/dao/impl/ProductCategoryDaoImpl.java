package com.wechat.dao.impl;

import com.wechat.dao.ProductCategoryDao;
import com.wechat.entity.ProductCategory;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public class ProductCategoryDaoImpl extends BaseDaoImpl implements ProductCategoryDao{

	

	
	@Override
	public void delereProductCategory(Integer id) {
		
		ProductCategory productCategory  = new ProductCategory();
		productCategory.setCat_id(new Integer(id));
		this.removeEntity(productCategory);
	}

	
	
	
	@Override
	public ProductCategory getProductCategory(Integer id) {
		
		 return (ProductCategory) this.getEntity(ProductCategory.class,id);
		 
		 
	}

	
	
	@Override
	public void saveProductCategory(ProductCategory productCategory) {
		
		this.saveOrUpdate(productCategory);
	}

	
	
	
	@Override
	public Page searchProductCategory(HashMap map) {
		
		
		StringBuffer sql = new StringBuffer(" from ProductCategory where 1=1 ");
		
		
		if(!"".equals(map.get("catId")) && map.get("catId")!= null)
		{
			sql.append(" and cat_id = " + map.get("catId"));
			
		}else{
//			List<ProductCategoryShow> categoryShows=this.executeHQL("from ProductCategoryShow where show = 0");
//			String removeCatId="";
//			for (int i = 0; i < categoryShows.size(); i++) {
//				removeCatId=removeCatId+categoryShows.get(i).getCategoryId()+",";
//			}
//			//有要隐藏的分类ID
//			if(!"".equals(removeCatId)){
//				removeCatId=removeCatId.substring(0, removeCatId.length()-1);
//				sql.append(" and cat_id not in("+removeCatId+") ");
//			}
		}
		sql.append(" order by sort ASC ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	
	}
	
	
	public Object[] getProductCategoryById(String id)throws Exception {
		
		StringBuffer sql = new StringBuffer(" select * from product_category where 1=1 ");
		if(!"".equals(id) && id != null)
		{
			sql.append(" and id = ").append(id);
		}
		List list = this.executeSQL(sql.toString());
		ProductCategory productCategory = new ProductCategory();
		Object[] obj = new Object[15];
		if(list.size()>0)
		{
			obj = (Object[])list.get(0);
		}
		return obj;
	}


	
	
	
	
	
}
