package com.wechat.dao.impl;

import com.wechat.dao.MallProductDao;
import com.wechat.entity.*;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MallProductDaoImpl extends BaseDaoImpl implements MallProductDao {

	@Override
	public void deleteMallProduct(MallProduct mallProduct) {

		this.delete(mallProduct);
	}

	@Override
	public MallProduct getMallProduct(MallProduct mallProduct) {

		return (MallProduct) this.getEntity(MallProduct.class,
				mallProduct.getId());
	}

	@Override
	public void saveMailProduct(MallProduct mallProduct) {

		this.saveOrUpdate(mallProduct);
	}

	@Override
	public Page searchMallProduct(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"select x.* , y.cat_name from ( select * from (select a.*,b.info from mallproduct a left JOIN ( "
						+ " select productid, group_concat(name,'>',price,'>',count,'>',id order by price asc ) as info from   mallspecifications  "
						+ " group by productid order by id desc )b on a.id = b.productid");

		sql.append(" ) a where 1=1 ");
		if (!"".equals(map.get("mallProductId"))
				&& map.get("mallProductId") != null) {
			sql.append(" and a.id =").append(
					map.get("mallProductId").toString());
		}
		if (!"".equals(map.get("status")) && map.get("status") != null) {
			sql.append(" and a.status =").append(map.get("status").toString());
		}
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.name like '%")
					.append(map.get("name").toString()).append("%'");
		}
		if (!"".equals(map.get("startDate")) && map.get("startDate") != null) {
			sql.append(" and  a.createdate > '")
					.append(map.get("startDate").toString()).append("'");
		}
		if (!"".equals(map.get("endDate")) && map.get("endDate") != null) {
			sql.append(" and a. a.createdate < '")
					.append(map.get("endDate").toString()).append("'");
		}
		if (!"".equals(map.get("catIds")) && map.get("catIds") != null) {
			sql.append(" and cat_id in ( ")
					.append(map.get("catIds").toString()).append(" )");
		}
		sql.append(" ) x  LEFT JOIN product_category y on x.cat_id = y.cat_id ");
		sql.append(" order by createdate desc ");
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	/*
	 * 根据单个id查询商品(non-Javadoc)
	 * 
	 * @see
	 * com.wechat.dao.MallProductDao#searchMallProductList(java.lang.String)
	 */
	@Override
	public List searchMallProductList(String productId) {

		StringBuffer sql = new StringBuffer(
				"select a.*,b.courseinfo from ( select x.* , y.cat_name from ( select * from (select a.*,b.info from mallproduct a left JOIN ( "
						+ " select productid, group_concat(name,'>',price,'>',count,'>',id order by price asc ) as info from   mallspecifications  "
						+ " group by productid order by id desc )b on a.id = b.productid ) a where 1=1 and a.status = 0 ");

		if (!"".equals(productId) && productId != null) {
			sql.append(" and a.id =").append(productId);
		}
		sql.append(" ) x  LEFT JOIN product_category y on x.cat_id = y.cat_id ");
		sql.append("  order by createdate desc ");
		sql.append(" ) a  left JOIN (select productid,GROUP_CONCAT(id,'>',name,'>',url,'>',productid) from course where 1=1 GROUP BY productid) b on a.id = b.productid  ");
		return this.executeSQL(sql.toString());

	}

	@Override
	public void deleteMallSpecifications(String productId) {

		String sql = " delete from mallspecifications where productid="
				+ productId;
		this.executeUpdateSQL(sql);
	}

	@Override
	public List getMallSpecifications(String productId) {

		String sql = " from MallSpecifications where productId=" + productId;
		return this.executeHQL(sql);
	}

	@Override
	public void saveMallSpecifications(MallSpecifications mallSpecifications) {

		this.save(mallSpecifications);
	}

	@Override
	public void saveOrder(MallOrder mallOrder) {

		this.saveOrUpdate(mallOrder);
	}

	@Override
	public Page searchMallOrder(HashMap map) {

		StringBuffer sql = new StringBuffer(" from MallOrder where 1=1");

		if (!"".equals(map.get("orderId")) && map.get("orderId") != null) {
			sql.append(" and id =").append(map.get("orderId").toString());
		}

		if (!"".equals(map.get("memberId")) && map.get("memberId") != null) {
			sql.append(" and memberId =")
					.append(map.get("memberId").toString());
		}
		if (!"".equals(map.get("status")) && map.get("status") != null) {
			sql.append(" and status =").append(map.get("status").toString());
		}

		sql.append(" order by createdate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public List searchMemberOrder(HashMap map) {

		StringBuffer sql = new StringBuffer(" from MallOrder where 1=1");

		if (!"".equals(map.get("orderId")) && map.get("orderId") != null) {
			sql.append(" and id =").append(map.get("orderId").toString());
		}

		if (!"".equals(map.get("memberId")) && map.get("memberId") != null) {
			sql.append(" and memberId =")
					.append(map.get("memberId").toString());
		}

		sql.append(" order by createdate desc ");
		return this.executeHQL(sql.toString());
	}

	@Override
	public void updateOrderStatus(String orderId, String status) {

		this.executeUpdateSQL(" update mallorder set status = " + status
				+ ",statusblank=" + status + "  where id = " + orderId);
	}

	@Override
	public void updateOrderRefundStatus(String orderId, String status) {

		this.executeUpdateSQL(" update mallorder set status = " + status
				+ " where id = " + orderId);
	}

	@Override
	public List searchProvince() {
		return this.executeHQL(" from Province where 1=1");
	}

	@Override
	public List searchCity(String provinceId) {
		return this.executeHQL(" from City where 1=1 and provinceId="
				+ provinceId);
	}

	@Override
	public List searchUserAddress(String memberId) {
		return this
				.executeHQL(" from UserAddress where 1=1 and type=0 and memberId="
						+ memberId);
	}

	@Override
	public void saveUserAddress(UserAddress userAddress) {
		this.saveOrUpdate(userAddress);
	}

	@Override
	public void deleteUserAddress(String id) {
		// UserAddress userAddress = new UserAddress();
		// userAddress.setId(new Integer(id));
		// this.removeEntity(userAddress);
		this.executeUpdateSQL(" update useraddress set type=1 where id in ( "
				+ id + " ) ");
	}

	@Override
	public UserAddress getUserAddress(Integer id) {
		return (UserAddress) this.getEntity(UserAddress.class, id);
	}

	@Override
	public void updateAddressStatus(String id, String memberId) {
		this.executeUpdateSQL("update useraddress set status = 1 where 1=1 and memberId="
				+ memberId);
		this.executeUpdateSQL(" update useraddress set status = 0 where id = "
				+ id);
	}

	@Override
	public UserAddress getUserAddressBystatus(Integer memberId) {
		List list = this
				.executeHQL(" from UserAddress where 1=1 and status = 0 and memberId= "
						+ memberId);
		UserAddress userAddress = new UserAddress();
		if (list.size() > 0) {
			userAddress = (UserAddress) list.get(0);
		}
		return userAddress;
	}

	@Override
	public void saveMallOrderProduct(MallOrderProduct mallOrderProduct) {
		this.save(mallOrderProduct);
	}

	@Override
	public List searchMallOrderProduct(String orderId) {
		return this
				.executeHQL(" from MallOrderProduct where 1=1 and orderId = "
						+ orderId);

	}

	@Override
	public void deleteShoppingCart(String id) {

		// ShoppingCart shoppingCart = new ShoppingCart();
		// shoppingCart.setId( new Integer(id));
		// this.delete(shoppingCart);
		this.executeUpdateSQL(" delete from shoppingcart where id = " + id);
	}

	@Override
	public void saveShoppingCart(ShoppingCart shoppingCart) {

		this.saveEntity(shoppingCart);
	}

	@Override
	public List searchShoppingCart(String memberId) {

		String sql = " from ShoppingCart where 1=1 and memberId = " + memberId;
		return this.executeHQL(sql);
	}

	@Override
	public void updateCount(String id, String count) {
		this.executeUpdateSQL(" update shoppingcart set count = count + "
				+ count + " where id = " + id);

	}

	@Override
	public HashMap searchMallProductMap(String ids) {
		String sql = "from MallProduct where 1=1 and id in ( " + ids + ")";
		HashMap map = new HashMap();
		List<MallProduct> list = this.executeHQL(sql);
		for (int i = 0; i < list.size(); i++) {

			map.put(list.get(i).getId().toString(), list.get(i));
		}
		return map;
	}

	@Override
	public List searchMallOrderByMemberId(String memberId) {
		return this.executeHQL(" from MallOrder where 1=1 and memberId="
				+ memberId + "order by id desc ");
	}

	@Override
	public List searchMallOrderProductByMemberId(String orderId) {
		return this
				.executeHQL(" from MallOrderProduct where 1=1 and orderId = "
						+ orderId + "order by id desc ");
	}

	@Override
	public Page searchMallOrderInfo(HashMap map) {
		StringBuffer sql = new StringBuffer(
				"select a.id,a.ordernumber,a.status,a.createdate,a.addressid,a.totalprice,ifnull((select username from useraddress where useraddress.id=a.addressid),'无收货人'),ifnull((select mobile from useraddress where useraddress.id=a.addressid),'无收货人号码'),a.freight,a.remarks,a.express "
						+ " ,a.expressnumber,ifnull((select area from useraddress where useraddress.id=a.addressid),'无地区'),ifnull((select address from useraddress where useraddress.id=a.addressid),'无地址')  from mallorder a where 1=1");
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and b.username like '%").append(map.get("name"))
					.append("%'");

		}
		if (!"".equals(map.get("mobile")) && map.get("mobile") != null) {
			sql.append(" and b.mobile like %").append(map.get("mobile"))
					.append("%");

		}
		if (!"".equals(map.get("status")) && map.get("status") != null) {
			sql.append(" and a.status =").append(map.get("status"));

		}

		if (!"".equals(map.get("number")) && map.get("number") != null) {
			sql.append(" and a.ordernumber ='").append(map.get("number"))
					.append("'");

		}

		if (!"".equals(map.get("startDate")) && map.get("startDate") != null) {
			sql.append(" and a.createdate >='").append(map.get("startDate"))
					.append("'");

		}
		if (!"".equals(map.get("endDate")) && map.get("endDate") != null) {
			sql.append(" and a.createdate <='").append(map.get("endDate"))
					.append("'");

		}
		sql.append(" order by id desc ");
		
		//System.out.println(sql.toString());
		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public void updateMallOrderStatus(String id, Integer operatorId,
			String express) {
		this.executeUpdateSQL(" update mallorder set status =2,statusblank = 2 ,express='"
				+ express.split(":")[0]
				+ "',expressnumber='"
				+ express.split(":")[1]
				+ "' ,operatorid="
				+ operatorId
				+ "  where id= " + id);
	}

	@Override
	public List seartchMallList(String productId) {

		String sql = "select  c.nickname,a.count,a.createdate from mallorderproduct a,mallorder b ,member c "
				+ " where a.orderid = b.id and c.id = b.memberid and a.productid  = "
				+ productId;
		return this.executeSQL(sql);
	}

	@Override
	public Page searchMobileMallProduct(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"  select * from ( select * from (  select a.*,b.viewCount from ( "
						+ " select id,name,logo1,price,info,saleCount from (select a.id,a.name,a.logo1,a.price,b.info,a.`status` from ( "
						+ " select a.id,a.name,a.logo1,a.price,a.`status`  from mallproduct a where status = 0 "
						+ " ) a left JOIN ( "
						+ " select productid, group_concat(name,'>',price  ,'>',count,'>',id ORDER BY price asc )  as info from   mallspecifications  "
						+ " group by productid order by id desc )b on a.id = b.productid ) a LEFT  JOIN (select productid,count(id) saleCount from mallorderproduct group by productid) b "
						+ " on 1=1 and a.status = 0 "
						+ " and a.id = b.productid ) a LEFT  JOIN (select productid,count(id) viewCount  from mallproductlog group by productid) b on  a.id = b.productid ");

		sql.append("   ) a LEFT JOIN (select productid,GROUP_CONCAT(b.id) as labelId from productlabel a,label b where a.labelid = b.id");

		sql.append(" group by productid) b on a.id = b.productid ) a where 1=1 ");
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.name like '%").append(map.get("name"))
					.append("%'");
		}

		if (!"".equals(map.get("labelId")) && map.get("labelId") != null) {
			sql.append(" and a.labelId in ( ").append(map.get("labelId"))
					.append("  )  ");
		}
		if (!"".equals(map.get("sortType")) && map.get("sortType") != null) {
			sql.append(" order by ").append(map.get("sortType"))
					.append("  desc  ");
		} else {
			sql.append(" order by saleCount  desc ");
		}

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public Page searchMobileMallProductByApp(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"  select a.*,b.mp3type from ( select * from ( select * from (  select a.*,b.viewCount from ( "
						+ " select id,name,logo1,price,info,saleCount from (select a.id,a.name,a.logo1,a.price,b.info,a.`status` from ( "
						+ " select a.id,a.name,a.logo1,a.price,a.`status`  from mallproduct a where 1=1 "
						+ " ) a left JOIN ( "
						+ " select productid, group_concat(name,'>',price  ,'>',count,'>',id ORDER BY price asc )  as info from   mallspecifications  "
						+ " group by productid order by id desc )b on a.id = b.productid ) a LEFT  JOIN (select productid,count(id) saleCount from mallorderproduct group by productid) b "
						+ " on 1=1   "
						+ " and a.id = b.productid ) a LEFT  JOIN (select productid,count(id) viewCount  from mallproductlog group by productid) b on  a.id = b.productid ");

		sql.append("   ) a LEFT JOIN (select productid,GROUP_CONCAT(b.id) as labelId from productlabel a,label b where a.labelid = b.id");

		sql.append(" group by productid) b on a.id = b.productid ) a where 1=1");
		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and a.name like '%").append(map.get("name"))
					.append("%'");
		}

		if (!"".equals(map.get("labelId")) && map.get("labelId") != null) {
			sql.append(" and a.labelId in ( ").append(map.get("labelId"))
					.append("  )  ");
		}
		if (!"".equals(map.get("sortType")) && map.get("sortType") != null) {
			sql.append(" order by ").append(map.get("sortType"))
					.append("  desc  ");
		} else {
			sql.append(" order by saleCount  desc ) a ,mallproduct b where a.id = b.id and b.mp3type=2 ");
		}
		if (!"".equals(map.get("catId")) && map.get("catId") != null) {
			sql.append(" and b.cat_id  =  ").append(map.get("catId"));
		}

		sql.append(" order by saleCount desc  ");

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public void saveMallProductLog(MallProductLog mallProductLog) {
		this.saveEntity(mallProductLog);
	}

	@Override
	public Object[] searchMallProductInfo(String productId) {
		String sql = " select * from ( select a.*,b.viewCount,c.purchaserecords from ( "
				+ " select id,name,logo1,logo2,logo3,content,price,info,count ,mp3,mp3type from (select a.id,a.name,a.logo1,a.logo2,a.logo3,a.price,a.content,b.info,a.`status` ,a.mp3,a.mp3type from mallproduct a left JOIN (  "
				+ " select productid, group_concat(name,'>',price,'>',count,'>',id order by price asc ) as info from   mallspecifications   "
				+ " group by productid order by id desc )b on a.id = b.productid ) a LEFT JOIN (select productid,sum(count) count from mallorderproduct group by productid) b  "
				+ " on 1=1 and a.status = 0  "
				+ " and a.id = b.productid ) a  "
				+ " LEFT  JOIN (select productid,count(id) viewCount  from mallproductlog group by productid) b on  a.id = b.productid "
				+ " LEFT JOIN (select productid,group_concat(nickname,'>',count,'>',createdate order by createdate desc) purchaserecords  from ( "
				+ " select  a.productid,c.nickname,a.count,a.createdate from mallorderproduct a,mallorder b ,member c  "
				+ " where a.orderid = b.id and c.id = b.memberid   order by a.createdate desc ) a where 1=1 "
				+ " group by productid "
				+ " ) c on   a.id = c.productid ) a where a.id= " + productId;
		List list = this.executeSQL(sql);
		Object[] obj = new Object[10];
		if (list.size() > 0) {
			obj = (Object[]) list.get(0);
		}
		return obj;
	}

	@Override
	public MallOrder getMallOrder(String orderId) {
		return (MallOrder) this
				.getEntity(MallOrder.class, new Integer(orderId));
	}

	@Override
	public List searchUserAddressStatus(String memberId) {

		return this.executeHQL(" from UserAddress where memberId=" + memberId);
	}

	@Override
	public MallOrderProduct getMallOrderProduct(String id) {

		return (MallOrderProduct) this.getEntity(MallOrderProduct.class,
				new Integer(id));
	}

	@Override
	public Integer getMallProductLogCount(String memberId, String tempDate) {
		Integer count = 0;
		List list = this
				.executeSQL(" select count(id) from mallproductlog where memberid="
						+ memberId
						+ " and date_format(createdate,'%Y-%m-%d% %H:%i') = '"
						+ tempDate + "'");
		if (list.get(0) != null) {

			count = new Integer(list.get(0).toString());
		}

		return count;
	}

	@Override
	public void updateProductCount(MallOrderProduct mallOrderProduct) {

		this.executeUpdateSQL("update mallspecifications set count = count - 1 where name ='"
				+ mallOrderProduct.getSpecifications()
				+ "' and productid = "
				+ mallOrderProduct.getProductId());

	}

	@Override
	public void saveMallOrderService(MallOrderService mallOrderService) {

		this.saveEntity(mallOrderService);
	}

	@Override
	public Page searchMallOrderService(HashMap map) {

		StringBuffer sql = new StringBuffer(" from MallOrderService where 1=1 ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public void updateMallOrderService(String id, String status,
			String operatorId) {

		this.executeUpdateSQL(" update mallorderservice set status = " + status
				+ ",operatorid = " + operatorId + " where id = " + id);
	}

	@Override
	public void deleteProductLabel(String id) {

		this.executeUpdateSQL(" delete from productlabel where productid = "
				+ id);
	}

	@Override
	public void saveProductLabel(ProductLabel productLabel) {

		//System.out.println("===============");
		this.save(productLabel);
	}

	@Override
	public List searchProductLabel(String ids) {

		return this
				.executeSQL("select * from ( select a.*,b.keyword from ( "
						+ " select productid,GROUP_CONCAT(a.name) from label  a LEFT JOIN  productlabel b  "
						+ " on b.labelid = a.id  and b.productid  in ("
						+ ids
						+ ") group  by productid "
						+ " ) a left JOIN (select productid,GROUP_CONCAT(name)  keyword from mallproductkeyword group by productid ) b on a.productid = b.productid ) a where 1=1 and productid is not null");
	}

	@Override
	public Page searchMallProductList(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"select x.* , y.cat_name ,z.files from ( select * from (select a.id,a.`name`,a.createdate,a.content,a.accountid,a.logo1,a.logo2,a.logo3,a.`status`,a.price,a.mp3,a.mp3type,a.cat_id,b.info from mallproduct a left JOIN ( "
						+ " select productid, group_concat(name,'>',price,'>',count,'>',id order by price asc ) as info from   mallspecifications  "
						+ " group by productid order by id desc )b on a.id = b.productid ) a where 1=1  ");

		if (!"".equals(map.get("name")) && map.get("name") != null) {
			sql.append(" and name  like '%").append(map.get("name"))
					.append("%'");
		}
		if (!"".equals(map.get("catIds")) && map.get("catIds") != null) {
			sql.append(" and cat_id in ( ").append(map.get("catIds"))
					.append(" )");
		}
		sql.append(" ) x  LEFT JOIN product_category y on x.cat_id = y.cat_id ");
		sql.append(" left JOIN ( select productid,group_concat(id,'>',name) as files from course GROUP BY productid ) z  on x.id = z.productid  ");
		sql.append(" order by createdate desc ");

		return this.pageQueryBySQL(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public void deleteMallProductKeyword(String productId) {

		this.executeUpdateSQL("delete from mallproductkeyword where productid  = "
				+ productId);
	}

	@Override
	public void saveMallProductKeyword(MallProductKeyword mallProductKeyword) {

		this.saveEntity(mallProductKeyword);
	}

	@Override
	public List searchMallProductKeyword(String productId) {

		return this
				.executeHQL(" from MallProductKeyword where 1=1 and productId="
						+ productId);
	}

	@Override
	public void saveMallLabel(MallLabel mallLabel) {

		this.saveEntity(mallLabel);

	}

	@Override
	public void deleteMallLabel(String id) {

		this.executeUpdateSQL("delete from malllabel where id = " + id);

	}

	@Override
	public Page searchMallLabel(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select a.id,b.name ,b.id as bid,a.ios,a.android,a.wechat  from malllabel a,label b where a.labelid = b.id  ");

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
	public List searchMallLabelByIndex(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" select a.id,b.name ,b.id as bid,a.ios,a.android,a.wechat  from malllabel a,label b where a.labelid = b.id  ");

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

		return this.executeSQL(sql.toString());

	}

	@Override
	public List searchProductCollection(String memberId, String productId) {

		return this.executeHQL(" from MemberCollection where memberId="
				+ memberId + " and productId=" + productId);
	}

	@Override
	public void saveMallBanner(MallBanner mallBanner) {

		this.saveEntity(mallBanner);

	}

	@Override
	public void deleteMallBanner(String id) {

		this.executeUpdateSQL(" delete from mallbanner where id = " + id);
	}

	@Override
	public Page searchMallBanner(HashMap map) {

		StringBuffer sql = new StringBuffer(" from MallBanner where 1=1");
		if (!"".equals(map.get("mame")) && map.get("mame") != null) {
			sql.append(" and title like '%").append(map.get("mame"))
					.append("%'");
		}
		if (!"".equals(map.get("status")) && map.get("status") != null) {
			sql.append(" and status =").append(map.get("status"));
		}
		if (!"".equals(map.get("type")) && map.get("type") != null) {

			sql.append(" and type in( 0,").append(map.get("type"))
					.append(" ) ");

		}
		sql.append(" order by id asc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));

	}

	@Override
	public List searchMallBanner() {
		return this.executeHQL(" from MallBanner where 1=1  and status = 0");
	}

	@Override
	public void deletreProductCollectionByMemberId(String memberId,
			String productId) {

		this.executeUpdateSQL(" delete from membercollection where memberId="
				+ memberId + " and productId =" + productId);

	}

	@Override
	public void deletreProductMp3ByMemberId(String memberId, String productId) {

		this.executeUpdateSQL(" delete from membermp3 where memberId="
				+ memberId + " and productId =" + productId);

	}

	@Override
	public void updateMallBannerStatus(String id, String status) {

		this.executeUpdateSQL(" update mallbanner set status= " + status
				+ "  where id = " + id);

	}

	@Override
	public Object[] searchMemberInfo(String memberId) {

		List list = this
				.executeSQL(" select * from (select a.*,b.count ,b.memberid from ( select a.*,b.type,b.enddate from ( select * from (   "
						+ " select a.id,b.mp3Count,c.collectionCount from member a  left join (select b.memberid,count(a.name) as mp3Count from mallproduct a,membermp3 b   "
						+ " where a.id = b.productid and a.mp3type=1  "
						+ " group by b.memberid) b on a.id = b.memberid  left join (select  memberid,count(id) collectionCount   "
						+ " from membercollection where 1=1 group by memberid ) c on a.id = c.memberid  ) a where  a.id ="
						+ memberId
						+ " ) a left join   "
						+ " memberbook b on a.id = b.memberid   ) a left join (  select memberid,count(a.id) count from bookorderinfo a, (    "
						+ " select id,memberid from bookorder where memberid =  "
						+ memberId
						+ " ) b where a.orderid = b.id  and a.status = 0 group by memberid  ) b on  a.id = b.memberid ) a  "
						+ " left JOIN (select b.memberid memberids ,count(a.name) as coursCount from mallproduct a,membermp3 b   "
						+ " where a.id = b.productid and a.mp3type=2  "
						+ " group by b.memberid) b on a.id = b.memberids");

		Object[] obj = new Object[5];
		if (list.size() > 0) {
			obj = (Object[]) list.get(0);
		}
		return obj;
	}

	@Override
	public void updateMallabel(String id, String ios, String wechat,
			String android) {
		this.executeUpdateSQL(" update malllabel set ios=" + ios + ",wechat="
				+ wechat + ",android=" + android + " where id = " + id);
	}

	@Override
	public List searchMallProdyctByLabel(String labelId, String limit) {
		String sql = " select a.*,b.name from ( select labelid,GROUP_CONCAT(id,'#',name,'#',logo1,'#',price,'#',info separator '@') from ( "
				+ " select a.*,b.labelid from (  select id,name,logo1,price,info from (  select a.id,a.name,a.logo1,a.price,b.info,a.`status`  "
				+ " from (  select a.id,a.name,a.logo1,a.price,a.`status`    from mallproduct a where 1=1   ) a  "
				+ " left JOIN (  select productid, group_concat(name,'>',price,'>',count,'>',id) as info from   mallspecifications    group by productid order by id desc )b on a.id = b.productid ) a ) a , "
				+ " (select b.productid,b.labelid from malllabel a,productlabel b    "
				+ " where  a.labelId = b.labelid and a.labelid in ("
				+ labelId
				+ ") ) b  where a.id = b.productid "
				+ " ) a where 1=1 GROUP BY labelid limit "
				+ limit
				+ " ) a left JOIN label b on a.labelid = b.id ";

		return this.executeSQL(sql);

	}

	@Override
	public MallSpecifications getMallSpecificationsById(String id) {

		MallSpecifications mallSpecifications = new MallSpecifications();
		List<MallSpecifications> list = this
				.executeHQL(" from MallSpecifications where id = " + id);
		if (list.size() > 0) {
			mallSpecifications = list.get(0);
		}
		return mallSpecifications;
	}

	@Override
	public MallProduct getMallProductById(String id) {
		MallProduct mallProduct = new MallProduct();
		List<MallProduct> list = this.executeHQL("from MallProduct where id ="
				+ id);
		if (list.size() > 0) {
			mallProduct = list.get(0);
		}
		return mallProduct;
	}

	@Override
	public List searchMallProdyctByCateGory(String cateIds) {
		String sql = " select cat_id,GROUP_CONCAT(id,'#',name,'#',logo1,'#',IFNULL(price,''),'#',IFNULL(info,''),'#',saleCount,'#',viewCount,'#' ORDER BY id ASC separator '@' ) from ( "
				+ " select a.*,ifnull(b.viewCount,0) viewCount from (   "
				+ " select id,name,logo1,price,info,cat_id,ifnull(saleCount,0) saleCount from (select a.id,a.name,a.logo1,a.price,b.info,a.`status` ,a.cat_id "
				+ " from (  select a.id,a.name,a.logo1,a.price,a.`status`,a.cat_id  from mallproduct a where 1=1 and cat_id >0 ) a  "
				+ " left JOIN (  select productid, group_concat(name,'>',price  ,'>',count,'>',id ORDER BY price asc )  as info from    "
				+ " mallspecifications   group by productid order by id desc )b on a.id = b.productid ) a  "
				+ " LEFT  JOIN (select productid,count(id) saleCount from mallorderproduct group by productid) b  on 1=1    and a.id = b.productid ) a  "
				+ " LEFT  JOIN (select productid,count(id) viewCount  from mallproductlog group by productid) b on  a.id = b.productid order by  saleCount,viewCount desc  ) a  "
				+ " where  a.cat_id  in( "
				+ " select cat_id from product_category where parent_id  in ( "
				+ cateIds
				+ ") order by  parent_id,sort  asc ) "
				+ " group by cat_id order by cat_id";

		return this.executeSQL(sql);

	}

	@Override
	public List searchAppIndex() {

		return this
				.executeHQL(" from AppIndex where 1=1 and status =0  order by sort ,id asc ");
	}

	@Override
	public Page searchAppIndex(HashMap map) {

		StringBuffer sql = new StringBuffer("  from AppIndex where 1=1 ");
		if (!"".equals(map.get("title")) && map.get("title") != null) {
			sql.append(" and title like '%" + map.get("title").toString()
					+ "%'");
		}
		if (!"".equals(map.get("type")) && map.get("type") != null) {
			sql.append(" and type = ").append(map.get("type"));
		}
		if (!"".equals(map.get("status")) && map.get("status") != null) {
			sql.append(" and status = ").append(map.get("status"));
		}
		sql.append(" order by sort  asc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public void saveAppIndex(AppIndex appIndex) {

		this.saveEntity(appIndex);
	}

	@Override
	public void deleteAppIndex(String id) {

		this.executeUpdateSQL(" delete from appindex where id = " + id);
	}

	@Override
	public List searchLabeiList() {

		return this
				.executeSQL(" select b.id,b.name from malllabel a,label b where a.labelid = b.id  and ios = 0 ");
	}

	@Override
	public List searchCateGory() {

		return this
				.executeHQL(" from ProductCategory where 1=1 and parent_id = 0 ");
	}

	@Override
	public List searchCateGoryByParentId(String id) {

		return this
				.executeHQL(" from ProductCategory where 1=1 and parent_id =  "
						+ id +" order by sort asc");
	}

	@Override
	public void saveCourse(Course course) {

		this.saveOrUpdate(course);
	}

	@Override
	public void deleteCourse(String id) {

		this.executeUpdateSQL(" delete from course where id =" + id);
	}

	@Override
	public List searchCourse(String mallProductId) {

		StringBuffer sql = new StringBuffer(
				" from Course where 1=1 and productId=").append(mallProductId);

		return this.executeHQL(sql.toString());
	}

	@Override
	public void saveCourseProject(CourseProject courseProject) {

		this.saveEntity(courseProject);
		this.executeUpdateSQL(" delete from courseprojectinfo where projectid = "
				+ courseProject.getId());
	}

	@Override
	public void deleteCourseProject(String projectId) {

		this.executeUpdateSQL(" delete from courseproject where id = "
				+ projectId);
		this.executeUpdateSQL(" delete from courseprojectinfo where projectid = "
				+ projectId);

	}

	@Override
	public Page searchCourseProject(HashMap map) {

		StringBuffer sql = new StringBuffer(
				" from CourseProject where 1=1  and epalId = '"
						+ map.get("epalId") + "'")
				.append(" order by sort asc ");

		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public List searchCourseProjectInfo(String projectId) {
		return this
				.executeSQL(""
						+ " select a.id,a.projectname,c.name,c.id courseid ,b.id as productinfoid ,c.logo1 from courseproject a, "
						+ " courseprojectinfo b,mallproduct c  where a.id = b.projectid and b.courseid = c.id and a.id =  "
						+ projectId + " order by b.id asc");
	}

	@Override
	public void saveCourseProjectInfo(CourseProjectInfo courseProjectInfo) {

		this.saveEntity(courseProjectInfo);

	}

	@Override
	public String searchCourseProjectMaxSort(String memberId) {

		String maxCount = "0";
		List list = this
				.executeSQL("select max(sort) from courseproject where memberid = "
						+ memberId);
		if (list.size() > 0) {
			maxCount = list.get(0).toString();
		}

		return maxCount;
	}

	@Override
	public void updateCeourseProjectSort(String id, String sort) {

		this.executeUpdateSQL("update courseproject set sort = " + sort
				+ " where id = " + id);

	}

	@Override
	public String searchCourseProjectMaxSortByEpalId(String epalId) {
		String maxCount = "0";
		List list = this
				.executeSQL("select max(sort) from courseproject where epalId = '"
						+ epalId + "'");
		if (list.get(0) != null) {
			maxCount = list.get(0).toString();
		}
		return maxCount;
	}

	@Override
	public Page searchCourseProjectSystem(HashMap map) {
		StringBuffer sql = new StringBuffer(
				" from CourseProjectSystem where 1=1  order by sort asc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	
	@Override
	public List searchCurriculumIndex()
	{
		
		//分隔符://⊥ ∪∩∫⊙
		//结果集:一级分类id,一级分类名称,排序,GROUP_CONCAT(二级分类ID ∩ 二级分类名称 ∩ 课程集合 以⊙分隔)
		//课程集合:GROUP_CONCAT(id⊥名称⊥封面⊥价格 以∪分隔)
		String sql = "SELECT"+
				"	*"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			a.infoid,"+
				"			max(a.title) title,"+
				"			max(a.sort) sort,"+
				"			GROUP_CONCAT("+
				"				b.cat_id,"+
				"				\'∩\',"+
				"				b.unique_id,"+
				"				\'∩\',"+
				"				info"+
				"			ORDER BY"+
				"				b.sort ASC SEPARATOR \'⊙\'"+
				"			) infoList"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					infoid,"+
				"					title,"+
				"					sort"+
				"				FROM"+
				"					appindex"+
				"				WHERE"+
				"					type = 1"+
				"				AND STATUS = 0"+
				"			) a"+
				"		LEFT JOIN ("+
				"			SELECT"+
				"				a.cat_id,"+
				"				max(a.unique_id) AS unique_id,"+
				"				max(a.sort) sort,"+
				"				max(a.parent_id) AS parent_id,"+
				"				GROUP_CONCAT("+
				"					b.id,"+
				"					\'⊥\',"+
				"					b. NAME,"+
				"					\'⊥\',"+
				"					b.logo1,"+
				"					\'⊥\',"+
				"					IFNULL(b.price, \'0\')"+
				"				ORDER BY"+
				"					b.accountid asc ,b.id DESC SEPARATOR \'∪\'"+
				"				) info"+
				"			FROM"+
				"				("+
				"					SELECT"+
				"						cat_id,"+
				"						unique_id,"+
				"						parent_id,"+
				"						sort"+
				"					FROM"+
				"						product_category"+
				"					WHERE"+
				"						parent_id != 0"+
				"					ORDER BY"+
				"						sort ASC"+
				"				) a"+
				"			LEFT JOIN ("+
				"				SELECT"+
				"					id,"+
				"					NAME,"+
				"					logo1,"+
				"					price,"+
				"					cat_id,"+
				"					accountid"+
				"				FROM"+
				"					mallproduct"+
				"				WHERE"+
				"					mp3type = 2"+
				"				AND STATUS = 0"+
				"			) b ON a.cat_id = b.cat_id"+
				"			GROUP BY"+
				"				a.cat_id"+
				"		) b ON a.infoid = b.parent_id"+
				"		GROUP BY"+
				"			a.infoid"+
				"	) a"+
				" WHERE"+
				"	1 = 1 "+
				" ORDER BY"+
				"	a.sort ASC";
		
		return this.executeSQL(sql);
	}
		
	
	
	@Override
	public List searchCategoryInfo()
	{
		
		//id,一级分类,GROUP_CONCAT(id∪2级分类∪分类简介∪课程信息(id,⊥课程名称∩分隔))⊙分隔
		String sql = "SELECT"+
				"	max(a.cat_id),"+
				"	a.unique_id AS parentName,"+
				"	GROUP_CONCAT("+
				"		b.cat_id,"+
				"		\'∪\',"+
				"		b.unique_id,"+
				"		\'∪\',"+
				"		b.description,"+
				"		\'∪\',"+
				"		b.info"+
				"	ORDER BY"+
				"		b.sort ASC SEPARATOR \'⊙\'"+
				"	)"+
				" FROM"+
				"	product_category a,"+
				"	("+
				"		SELECT"+
				"			a.cat_id,"+
				"			max(a.unique_id) unique_id,"+
				"			max(a.parent_id) parent_id,"+
				"			max(a.sort) sort,"+
				"			max(a.description) description,"+
				"			GROUP_CONCAT("+
				"				b.id,"+
				"				\'⊥\',"+
				"				b. NAME order by b.id desc SEPARATOR \'∩\'"+
				"			) info"+
				"		FROM"+
				"			product_category a,"+
				"			("+
				"				SELECT"+
				"					id,"+
				"					NAME,"+
				"					cat_id"+
				"				FROM"+
				"					mallproduct"+
				"				WHERE"+
				"					STATUS = 0"+
				"				AND mp3type = 2"+
				"			) b"+
				"		WHERE"+
				"			a.cat_id = b.cat_id"+
				"		AND a.parent_id != 0"+
				"		GROUP BY"+
				"			a.cat_id"+
				"	) b"+
				" WHERE"+
				"	a.cat_id = b.parent_id"+
				" GROUP BY"+
				"	a.unique_id";
		
		return this.executeSQL(sql);
	}
	
	
	
	public List searchMemberCourseProduct(String memberId)
	{
		String sql = "select b.productid ,count(*),GROUP_CONCAT(b.courseid) from course_plan a ,course_plan_info b where a.id = b.plan_id and a.memberid = "+memberId+"group by  b.productid";
		return this.executeSQL(sql);
	}

	@Override
	public void updateMallProductCatSort(List list) {
		
		MallProduct mallProduct = null;
		for (int i = 0; i < list.size(); i++) {
			
			mallProduct = (MallProduct)list.get(i);
			this.executeUpdateSQL(" update mallproduct set accountId = "+mallProduct.getAccountId()+" where id = "+mallProduct.getId());
		}
		
	}
	
	
	@Override
	public List searchMallProductByCatId(String catId)throws Exception
	{
		return this.executeHQL(" from MallProduct where catId = "+catId+" order by id asc ");
	}

}
