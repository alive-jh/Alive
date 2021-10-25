package com.wechat.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jdom.JDOMException;

import com.wechat.controller.PaymentController;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.Keys;

import net.sf.json.JSONObject;

public class WechatPayService {

	public String h5Pay(String payCallback,String commodityName,String orderNumber,String ip,Integer total) throws JDOMException, IOException{

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		/** 公众号APPID */
		parameters.put("appid", Keys.APP_ID);
		/** 商户号 */
		parameters.put("mch_id", Keys.MCH_ID);
		/** 随机字符串 */
		parameters.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		/** 商品名称 */

		parameters.put("body", commodityName);

		/** 当前时间 yyyyMMddHHmmss */
		String currTime = TenpayUtil.getCurrTime();
		/** 8位日期 */
		String strTime = currTime.substring(8, currTime.length());
		/** 四位随机数 */
		String strRandom = TenpayUtil.buildRandom(4) + "";
		/** 订单号 */
		parameters.put("out_trade_no", orderNumber);

		//System.out.println("out_trade_no = " + strTime + strRandom);

		/** 订单金额以分为单位，只能为整数 */
		parameters.put("total_fee", total);
		/** 客户端本地ip */
		parameters.put("spbill_create_ip", ip);
		/** 支付回调地址 */
		parameters.put("notify_url", payCallback);
		/** 支付方式为JSAPI支付 */
		parameters.put("trade_type", "MWEB");
		/// ** 用户微信的openid，当trade_type为JSAPI的时候，该属性字段必须设置 */
		// parameters.put("openid", request.getParameter("memberOpenId"));
		// //System.out.println("openId = "+request.getParameter("memberOpenId"));

		/** MD5进行签名，必须为UTF-8编码，注意上面几个参数名称的大小写 */
		String sign = PaymentController.createSign("UTF-8", parameters);
		parameters.put("sign", sign);

		/** 生成xml结构的数据，用于统一下单接口的请求 */
		String requestXML = PaymentController.getRequestXml(parameters);
		System.err.println("requestXML：" + requestXML);
		/** 开始请求统一下单接口，获取预支付prepay_id */
		HttpClient client = new HttpClient();
		PostMethod myPost = new PostMethod("https://api.mch.weixin.qq.com/pay/unifiedorder");
		client.getParams().setSoTimeout(300 * 1000);
		String result = null;
		try {
			myPost.setRequestEntity(new StringRequestEntity(requestXML, "text/xml", "utf-8"));
			int statusCode = client.executeMethod(myPost);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
				byte[] bytes = new byte[1024];
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int count = 0;
				while ((count = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, count);
				}
				byte[] strByte = bos.toByteArray();
				result = new String(strByte, 0, strByte.length, "utf-8");
				bos.close();
				bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** 需要释放掉、关闭连接 */
		myPost.releaseConnection();
		client.getHttpConnectionManager().closeIdleConnections(0);

		System.err.println("请求统一支付接口的返回结果:");
		System.err.println(result);

		/** 解析微信返回的信息，以Map形式存储便于取值 */
		Map<String, String> map = XMLUtil.doXMLParse(result);
		
		if(map.get("return_code").equals("FAIL")){
			return null;
		}
		
		return map.get("mweb_url");
	}
}
