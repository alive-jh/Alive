package com.wechat.jfinal.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;

import com.wechat.entity.UnifiedOrderModel;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jdom.JDOMException;

import com.wechat.controller.PaymentController;
import com.wechat.pay.util.MD5Util;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.Keys;

import net.sf.json.JSONArray;

public class WechatPayService {

	public String pay(HashMap<String, Object> paramets) throws JDOMException, IOException {

		/** 解析微信返回的信息，以Map形式存储便于取值 */
		Map<String, String> resultMap = unifiedOrder(paramets);

		
		String payType = (String) paramets.get("payType");
		
		if (resultMap.get("return_code").equals("FAIL")) {
			return null;
		}

		if(payType.equals("NATIVE")){
			return resultMap.get("code_url");
		}else if(payType.equals("MWEB")){
			return resultMap.get("mweb_url");
		}else{
			return null;
		}
		
	}

	@SuppressWarnings("rawtypes")
	public Map unifiedOrder(HashMap<String, Object> paramets) throws JDOMException, IOException{
		
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		/** 公众号APPID */
		parameters.put("appid", paramets.get("appId"));
		/** 商户号 */
		parameters.put("mch_id", paramets.get("mchId"));
		/** 随机字符串 */
		parameters.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		/** 商品名称 */
		parameters.put("body", paramets.get("commodityName").toString());
		/** 订单号 */
		parameters.put("out_trade_no", paramets.get("orderNumber"));
		/** 订单金额以分为单位，只能为整数 */
		Double totalPrice = new Double(paramets.get("totalPrice").toString()) * 100;
		DecimalFormat df = new DecimalFormat("####");
		String totalFee = df.format(totalPrice);
		parameters.put("total_fee", Integer.parseInt(totalFee));
		/** 客户端本地ip */
		parameters.put("spbill_create_ip", paramets.get("spbillCreateIp"));
		/** 支付回调地址 */
		parameters.put("notify_url", paramets.get("callBackUrl").toString());
		
		/** 支付方式为JSAPI支付 */
		String payType = paramets.get("payType").toString();
		parameters.put("trade_type", payType);
		
		if(payType.equals("JSAPI")){
			 parameters.put("openid", paramets.get("openid"));
		}
		
		/** MD5进行签名，必须为UTF-8编码，注意上面几个参数名称的大小写 */
		String sign = createSign("UTF-8", parameters,paramets.get("apiKey").toString());
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

		return XMLUtil.doXMLParse(result);

	}
	
	public Map officaPay(HashMap<String, Object> paramets) throws JDOMException, IOException{
		
		return unifiedOrder(paramets);
		
	}
	
	
	public Map miniAppPay(HashMap<String, Object> paramets) throws JDOMException, IOException {

		/** 总金额(单位是分的整数 ：total) */
		DecimalFormat df2 = new DecimalFormat("####");
		Double tempNum = new Double(paramets.get("totalPrice").toString());
		tempNum = tempNum * 100;
		int total = Integer.parseInt(df2.format(tempNum));

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		/** 公众号APPID */
		parameters.put("appid", paramets.get("appId"));
		/** 商户号 */
		parameters.put("mch_id", paramets.get("mchId"));
		/** 随机字符串 */
		parameters.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		/** 商品名称 */

		parameters.put("body", paramets.get("commodityName"));

		/** 订单号 */
		parameters.put("out_trade_no", paramets.get("orderNumber").toString());

		/** 订单金额以分为单位，只能为整数 */
		parameters.put("total_fee", total);
		/** 客户端本地ip */
		parameters.put("spbill_create_ip", paramets.get("spbillCreateIp"));
		/** 支付回调地址 */
		parameters.put("notify_url", paramets.get("callBackUrl"));
		/** 支付方式为JSAPI支付 */
		parameters.put("trade_type", "JSAPI");
		/** 用户微信的openid，当trade_type为JSAPI的时候，该属性字段必须设置 */
		parameters.put("openid", paramets.get("openId"));

		/** MD5进行签名，必须为UTF-8编码，注意上面几个参数名称的大小写 */
		String sign = createSign("UTF-8", parameters,paramets.get("apiKey").toString());
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

		return map;

	}

	public String createSign(String characterEncoding, SortedMap<Object, Object> parameters,final String API_KEY) {
		StringBuffer sb = new StringBuffer();
        Set<Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            /** 如果参数为key或者sign，则不参与加密签名 */
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        /** 支付密钥必须参与加密，放在字符串最后面 */
        sb.append("key=" + API_KEY);
        /** 记得最后一定要转换为大写 */
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
	}

	public Map<String, String> unifiedorder(UnifiedOrderModel model) throws JDOMException, IOException {

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		/** 公众号APPID */
		parameters.put("appid", model.getAppid());
		/** 商户号 */
		parameters.put("mch_id", model.getMchId());
		/** 随机字符串 */
		parameters.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		/** 商品名称 */
		parameters.put("body", model.getBody());
		/** 订单号 */
		parameters.put("out_trade_no", model.getOutTradeNo());
		/** 订单金额以分为单位，只能为整数 */
		Double totalPrice = new Double(model.getTotalFee()) * 100;
		DecimalFormat df = new DecimalFormat("####");
		String totalFee = df.format(totalPrice);
		parameters.put("total_fee", Integer.parseInt(totalFee));
		/** 客户端本地ip */
		parameters.put("spbill_create_ip", model.getSpbillCreateIp());
		/** 支付回调地址 */
		parameters.put("notify_url", model.getNotifyUrl());

		/** 支付方式为JSAPI支付 */
		parameters.put("trade_type", model.getTradeType());

		if (model.getTradeType().equals("JSAPI")) {
			parameters.put("openid", model.getOpenId());
		}

		/** MD5进行签名，必须为UTF-8编码，注意上面几个参数名称的大小写 */
		String sign = createSign("UTF-8", parameters, model.getApiKey());
		parameters.put("sign", sign);

		/** 生成xml结构的数据，用于统一下单接口的请求 */
		String requestXML = PaymentController.getRequestXml(parameters);

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

		return XMLUtil.doXMLParse(result);

	}
}
