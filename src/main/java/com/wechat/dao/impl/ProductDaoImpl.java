package com.wechat.dao.impl;

import com.wechat.dao.ProductDao;
import com.wechat.entity.Product;
import com.wechat.entity.ProductInfo;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao{

	
	@Override
	public void delereProduct(String id) {
		

		
		this.executeUpdateSQL("delete from product where id in( "+id+" ) ");
	}

	
	@Override
	public void delereProductInfo(Integer id) {
		
		ProductInfo productInfo  = new ProductInfo();
		productInfo.setId(new Integer(id));
		this.removeEntity(productInfo);
	}

	
	@Override
	public Product getProduct(Integer id) {
		
		 return (Product) this.getEntity(Product.class,id);
	}

	
	@Override
	public ProductInfo getProductInfo(Integer id) {
		
		 return (ProductInfo) this.getEntity(ProductInfo.class,id);
	}

	
	@Override
	public void saveProduct(Product product) {
		
		this.saveOrUpdate(product);
	}

	
	@Override
	public void saveProductInfo(ProductInfo productInfo) {
		
		this.saveOrUpdate(productInfo);
	}

	
	@Override
	public Page searchProduct(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Product where 1=1 ");
		if(!"".equals(map.get("productId")) && map.get("productId")!= null)
		{
			sql.append(" and id = ").append(map.get("productId").toString());
		}
		if(!"".equals(map.get("number")) && map.get("number")!= null)
		{
			sql.append(" and number like '%").append(map.get("number").toString()).append("%'");
		}
		if(!"".equals(map.get("productInfoId")) && map.get("productInfoId")!= null)
		{
			sql.append(" and productinfoid = ").append(map.get("productInfoId"));
		}
		
		if(!"".equals(map.get("startDate")) && map.get("startDate")!= null)
		{
			sql.append(" and  createdate >= '").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate")) && map.get("endDate")!= null)
		{
			sql.append(" and  createdate <= '").append(map.get("endDate").toString()).append("'");
		}
		sql.append(" order by createdate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	
	@Override
	public Page searchProductInfo(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from ProductInfo where 1=1 ");
		if(!"".equals(map.get("productId")) && map.get("productId")!= null)
		{
			sql.append(" and id = ").append(map.get("productId").toString());
		}
		if(!"".equals(map.get("name")) && map.get("name")!= null)
		{
			sql.append(" and name like '%").append(map.get("name").toString()).append("%'");
		}
		if(!"".equals(map.get("model")) && map.get("model")!= null)
		{
			sql.append(" and model like '%").append(map.get("model").toString()).append("%'");
		}
		if(!"".equals(map.get("power")) && map.get("power")!= null)
		{
			sql.append(" and power = '").append(map.get("power").toString()).append("'");
		}
		sql.append(" order by createdate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	@Override
	public Object[] getProductInfoById(String id)throws Exception {
		
		StringBuffer sql = new StringBuffer(" select a.id,a.number,date_format(a.createdate,'%Y-%m-%d'),b.name,b.model,b.power,b.factor,b.colorttemperature,b.cri," +
				"b.dimming,b.luminousflux,b.description,b.characteristic ,a.memberid,a.electricianid from product a,product_info b where a.productInfoid = b.id  ");
		if(!"".equals(id) && id != null)
		{
			sql.append(" and a.id = ").append(id);
		}
		List list = this.executeSQL(sql.toString());
		Product product = new Product();
		ProductInfo productInfo = new ProductInfo();
		Object[] obj = new Object[15];
		if(list.size()>0)
		{
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			obj = (Object[])list.get(0);
//			product.setId(new Integer(obj[0].toString()));
//			product.setNumber(obj[1].toString());
//			product.setCreateDate(sdf.parse(obj[2].toString()));
//			productInfo.setName(obj[3].toString());
//			productInfo.setModel(obj[3].toString());
//			productInfo.setPower(obj[4].toString());
//			productInfo.setFactor(obj[5].toString());
//			productInfo.setColortTemperature(obj[6].toString());
//			productInfo.setCri(obj[7].toString());
//			productInfo.setDimming(obj[8].toString());
//			productInfo.setLuminousFlux(obj[9].toString());
//			productInfo.setDescription(obj[10].toString());
//			productInfo.setCharacteristic(obj[11].toString());
//			//product.setInfo(productInfo);
//			
		}
		return obj;
	}


	
	@Override
	public void updateProductByMemberId(String productId, String memberId,
			String type) throws Exception {
		
		String sql = "";
		if("0".equals(type))
		{
			sql = "update product set memberid="+memberId+" where id ="+productId;
		}
		else
		{
			sql = "update product set electricianid="+memberId+" where id ="+productId;
		}
		this.executeUpdateSQL(sql);
	}
	
	@Override
	public Page searchProductByMember(HashMap map ) throws Exception {
		
		StringBuffer sql =  new StringBuffer(" select a.id,a.number,date_format(a.createdate,'%Y-%m-%d'),b.name,b.model,b.power  from product a,product_info b where a.productInfoid = b.id ");
		if("0".equals(map.get("type").toString()))
		{
			sql.append(" and  a.memberid="+map.get("memberId").toString());
		}
		else
		{
			sql.append( " and a.electricianid="+map.get("memberId").toString());
		}
		sql.append(" order by a.id desc ");

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	
	
	
	
	
	
	
	
	
}
