package com.wechat.controller;


import com.mysql.jdbc.PreparedStatement;
import com.wechat.common.dto.QueryDto;
import com.wechat.entity.AccessToken;
import com.wechat.entity.AgentBillOfSales;
import com.wechat.entity.Device;
import com.wechat.entity.ShipmentRecord;
import com.wechat.service.AgentService;
import com.wechat.service.DeviceService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import com.wechat.util.PropertyUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("api")
public class AgentApiController {

	@Resource
	private AgentService agentService;

	@Resource
	private DeviceService deviceService;

	static final String JDBC_DRIVER = PropertyUtil
			.getDataBaseProperty("driverClass");
	static final String DB_URL = PropertyUtil.getDataBaseProperty("jdbcUrl");

	static final String USER = PropertyUtil.getDataBaseProperty("jdbcName");
	static final String PASS = PropertyUtil.getDataBaseProperty("password");

	/**
	 * 获取加盟商列表
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/getAgentList")
	public void bookManagerView(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		String page = ParameterFilter.emptyFilter("1", "page", request);
		String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
		String searchStr = ParameterFilter.emptyFilter("", "searchStr", request);

		HashMap map = new HashMap();
		map.put("page", page);
		map.put("pageSize", pageSize);
		map.put("searchStr", searchStr);
		Page datalist  = agentService.getAgentList(map);
		JsonResult.JsonResultInfo(response, datalist);
	}



	/**
	 * 出货给加盟商
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/agentShipment")
	public void agentShipment(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		String deviceNo = ParameterFilter.emptyFilter("", "deviceNo", request);
		String agentId = ParameterFilter.emptyFilter("", "agentId", request);
		String userId = ParameterFilter.emptyFilter("", "userId", request);
		String billOfSalesId = ParameterFilter.emptyFilter("", "billOfSalesId", request);
		JSONObject result = new JSONObject();
		if("".equals(userId)){
			Cookie[] enu = request.getCookies();
			for(int i=0;i<enu.length;i++){
				Cookie cookie = enu[i];
				String value  = cookie.getValue();
				String name = cookie.getName();
				if("adminUser".equals(name)){
					userId = value;
				}
			}
		}
		String []deviceNoList = deviceNo.split("\n");
		//System.out.println(deviceNoList.length);
		if(deviceNoList.length >1){
			Connection conn = null;
			PreparedStatement pst = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			StringBuffer insertMainCourseSql = new StringBuffer(
					"insert into shipment_record (device_no,user_id,agent_id, bill_of_sales_id, create_time) values (?, ?, ?, ?, ?)");

			try {
				pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql
						.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for(int i=0;i<deviceNoList.length;i++){
				try {

					String tempDeviceNo = deviceNoList[i];
					if (tempDeviceNo!=null) {
						Pattern p = Pattern.compile("\\s*|\t|\r|\n");
						Matcher m = p.matcher(tempDeviceNo);
						tempDeviceNo = m.replaceAll("");
					}


					if("".equals(tempDeviceNo)||null==tempDeviceNo){

					}else{
						pst.setInt(3, Integer.parseInt(agentId));
						pst.setInt(4, Integer.parseInt(billOfSalesId));
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						pst.setString(5, df.format(new Date()));
						pst.setString(1, tempDeviceNo);
						pst.setInt(2, Integer.parseInt(userId));
						pst.addBatch();
					}

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			try {
				pst.executeBatch(); // 执行批量处理
				conn.commit(); // 提交
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			result.put("code", 200);
			result.put("msg", "出货成功！");
			JsonResult.JsonResultInfo(response, result);
		}else{

			if(!"".equals(deviceNo)&&!"".equals(agentId)&&!"".equals(userId)){
				Device device  = deviceService.searchDeviceByDeviceNo(deviceNo);
				if(device.getId() == null){
					result.put("code", 404);
					result.put("msg", "设备不存在！");
					JsonResult.JsonResultInfo(response, result);

				}else{
					if(device.getStatus() >= 4){
						result.put("code", 406);
						result.put("msg", "设备已经出货！");
						JsonResult.JsonResultInfo(response, result);

					}else{
						device.setStatus(4);
						ShipmentRecord shipmentRecord = new ShipmentRecord();
						shipmentRecord.setAgentId(Integer.parseInt(agentId));
						shipmentRecord.setBillOfSalesId(Integer.parseInt(billOfSalesId));
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						shipmentRecord.setCreateTime(df.format(new Date()));
						shipmentRecord.setDeviceNo(deviceNo);
						shipmentRecord.setUserId(Integer.parseInt(userId));
						agentService.saveShipmentRecord(shipmentRecord);
						deviceService.saveDevice(device);
						result.put("code", 200);
						result.put("msg", "出货成功！");
						JsonResult.JsonResultInfo(response, result);
					}


				}

			}else{
				result.put("code", 405);
				result.put("msg", "缺少参数！");
				JsonResult.JsonResultInfo(response, result);
			}



		}

	}

	/**
	 * 通过加盟商获取出货单
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/getBillOfSalesByAgentId")
	public void getBillOfSalesByAgentId(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		String agentId = ParameterFilter.emptyFilter("", "agentId", request);
		String page = ParameterFilter.emptyFilter("1", "page", request);
		String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
		JSONObject result = new JSONObject();
		if(!"".equals(agentId)){
			HashMap map = new HashMap();
			map.put("agentId", agentId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page dataList  = agentService.getBillOfSalesByAgentId(map);
			JsonResult.JsonResultInfo(response, dataList);
		}else{
			result.put("code", 405);
			result.put("msg", "缺少参数！");
			JsonResult.JsonResultInfo(response, result);
		}
	}

	/**
	 * 通过出货单获取出货详情
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/getShipmentDetailList")
	public void getShipmentDetailList(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		String shipmentId = ParameterFilter.emptyFilter("", "shipmentId", request);
		String page = ParameterFilter.emptyFilter("1", "page", request);
		String pageSize = ParameterFilter.emptyFilter("1000", "pageSize", request);
		JSONObject result = new JSONObject();
		if(!"".equals(shipmentId)){
			HashMap map = new HashMap();
			map.put("shipmentId", shipmentId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page dataList  = agentService.getShipmentDetailList(map);
			JsonResult.JsonResultInfo(response, dataList);
		}else{
			result.put("code", 405);
			result.put("msg", "缺少参数！");
			JsonResult.JsonResultInfo(response, result);
		}
	}
	/**
	 * 管理员登录
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/managerLogin")
	public void managerLogin(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		    String user = ParameterFilter.emptyFilter("", "user", request);
		    String password = ParameterFilter.emptyFilter("", "password", request);
		    String role = ParameterFilter.emptyFilter("", "role", request);
		    JSONObject result = new JSONObject();
		if(!"".equals(user)&&!"".equals(password)&&!"".equals(role)){
			AccessToken accessToken = new AccessToken();
			String access_token = accessToken.getAccessToken();
			result.put("code", 200);
			result.put("msg", "登录成功!");
			result.put("acces_token", access_token);
			JsonResult.JsonResultInfo(response, result);
		}else{
			result.put("code", 405);
			result.put("msg", "缺少参数！");
			JsonResult.JsonResultInfo(response, result);
		}
	}

	/**
	 * 创建出货单
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/createBillOfSales")
	public void createBillOfSales(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		String agentId = ParameterFilter.emptyFilter("", "agentId", request);
		String BillName = ParameterFilter.emptyFilter("", "name", request);
		Cookie[] enu = request.getCookies();
		String userId = "1";
		for(int i=0;i<enu.length;i++){
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		JSONObject result = new JSONObject();
		if(!"".equals(userId)&&!"".equals(agentId)&&!"".equals(BillName)){
			AgentBillOfSales agentBillOfSales = new AgentBillOfSales();
			agentBillOfSales.setAgentId(Integer.parseInt(agentId));
			agentBillOfSales.setName(BillName);
			agentBillOfSales.setStatus(1);
			agentService.saveAgentBillOfSales(agentBillOfSales);
			result.put("code", 200);
			result.put("msg", "创建出货单成功！");
			JsonResult.JsonResultInfo(response, result);
		}else{
			result.put("code", 405);
			result.put("msg", "出货单创建失败，缺少参数！");
			JsonResult.JsonResultInfo(response, result);
		}
	}

	/**
	 * 修改密码
	 *
	 * @param request
	 * @param response
	 * @param queryDto
	 * @throws Exception
	 */
	@RequestMapping("/managerUpdatePassword")
	public void managerUpdatePassword(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		String userId = ParameterFilter.emptyFilter("", "userId", request);
		String oldPassword = ParameterFilter.emptyFilter("", "oldPassword", request);
		String newPassword = ParameterFilter.emptyFilter("", "newPassword", request);
		JSONObject result = new JSONObject();
		if(!"".equals(userId)&&!"".equals(oldPassword)&&!"".equals(newPassword)){

			result.put("code", 200);
			result.put("msg", "修改密码成功");
			JsonResult.JsonResultInfo(response, result);
		}else{
			result.put("code", 405);
			result.put("msg", "修改密码失败，缺少参数！");
			JsonResult.JsonResultInfo(response, result);
		}
	}
}
