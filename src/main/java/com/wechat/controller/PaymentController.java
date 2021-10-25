package com.wechat.controller;

import com.wechat.entity.MallOrder;
import com.wechat.pay.util.MD5Util;
import com.wechat.pay.util.TenpayUtil;
import com.wechat.pay.util.WXUtil;
import com.wechat.pay.util.XMLUtil;
import com.wechat.service.MallProductService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


@Controller
@RequestMapping("payment")
public class PaymentController {
	
		@Resource
		private
		MallProductService mallProductService;
	
	 	@RequestMapping(value="/gopay")
	    public  String gopay(HttpServletRequest request,HttpServletResponse response) throws Exception {
	        
	        String path = request.getContextPath();
	        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	        //System.out.println("basePath=" + basePath);
	        
	        Double tempNum = new Double(request.getParameter("totalPrice"));
	        tempNum = tempNum* 100;  
	       
	        DecimalFormat df2 = new DecimalFormat("####");

	        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        //System.out.println("memberId = "+request.getParameter("memberId")+"  |   memberOpenId="+request.getParameter("memberOpenId")+"  |  totalPrice = "+request.getParameter("totalPrice")+" |  createDate ="+format.format(new Date()));
	        
	        /** 总金额(分为单位) */
	        int total = Integer.parseInt(df2.format(tempNum));
	        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
	        /** 公众号APPID */
	        parameters.put("appid", Keys.APP_ID);
	        /** 商户号 */
	        parameters.put("mch_id", Keys.MCH_ID);
	        /** 随机字符串 */
	        parameters.put("nonce_str", WXUtil.getNonceStr());
	        /** 商品名称 */
	       
	        parameters.put("body", request.getParameter("commodityName"));

	        /** 当前时间 yyyyMMddHHmmss */
	        String currTime = TenpayUtil.getCurrTime();
	        /** 8位日期 */
	        String strTime = currTime.substring(8, currTime.length());
	        /** 四位随机数 */
	        String strRandom = TenpayUtil.buildRandom(4) + "";
	        /** 订单号 */
	        parameters.put("out_trade_no", strTime + strRandom);
	        //System.out.println("out_trade_no = "+strTime + strRandom);
	        
	        /** 订单金额以分为单位，只能为整数 */
	        parameters.put("total_fee", total);
	        /** 客户端本地ip */
	        parameters.put("spbill_create_ip", request.getRemoteAddr());
	        /** 支付回调地址 */
	        parameters.put("notify_url", basePath + "weixinJSBridge/pay");
	        /** 支付方式为JSAPI支付 */
	        parameters.put("trade_type", "JSAPI");
	        /** 用户微信的openid，当trade_type为JSAPI的时候，该属性字段必须设置 */
	        parameters.put("openid", request.getParameter("memberOpenId"));
	        ////System.out.println("openId = "+request.getParameter("memberOpenId"));
	        
	        /** MD5进行签名，必须为UTF-8编码，注意上面几个参数名称的大小写 */
	        String sign = createSign("UTF-8", parameters);
	        parameters.put("sign", sign);
	        
	        /** 生成xml结构的数据，用于统一下单接口的请求 */
	        String requestXML = getRequestXml(parameters);
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
	            
	           
	            SortedMap<Object, Object> params = new TreeMap<Object, Object>();
	            params.put("appId", Keys.APP_ID);
	            params.put("timeStamp", "\"" + strTime + strRandom + "\"");
	            params.put("nonceStr", WXUtil.getNonceStr());
	            
	            
	            /** 
	             * 获取预支付单号prepay_id后，需要将它参与签名。
	             * 微信支付最新接口中，要求package的值的固定格式为prepay_id=... 
	             */
	            params.put("package", "prepay_id=" + map.get("prepay_id"));

	            /** 微信支付新版本签名算法使用MD5，不是SHA1 */
	            params.put("signType", "MD5");
	            /**
	             * 获取预支付prepay_id之后，需要再次进行签名，参与签名的参数有：appId、timeStamp、nonceStr、package、signType.
	             * 主意上面参数名称的大小写.
	             * 该签名用于前端js中WeixinJSBridge.invoke中的paySign的参数值
	             */
	            String paySign = createSign("UTF-8", params);
	            params.put("paySign", paySign);
	            
	            /** 预支付单号，前端ajax回调获取。由于js中package为关键字，所以，这里使用packageValue作为key。 */
	            params.put("packageValue", "prepay_id=" + map.get("prepay_id"));
	            
	            /** 付款成功后，微信会同步请求我们自定义的成功通知页面，通知用户支付成功 */
	            params.put("sendUrl", basePath + "member/paysuccess?totalPrice=1"); 
	            /** 获取用户的微信客户端版本号，用于前端支付之前进行版本判断，微信版本低于5.0无法使用微信支付 */
	            String userAgent = request.getHeader("user-agent");
	            char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
	            params.put("agent", new String(new char[] { agent }));

	            String json = JSONArray.fromObject(params).toString();
	            //System.out.println("json = "+json); 
	            
	            response.setContentType("application/json;charset=UTF-8");
	    		response.getWriter().println(json);
	            return null;
	        
	    }
	 	
	 	
	 	
	 	@RequestMapping(value="/goPayResult")
	    public  void goPayResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 		String result = null;
	        try {
	                BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
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
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        /** 解析微信返回的信息，以Map形式存储便于取值 */
            Map<String, String> map = XMLUtil.doXMLParse(result);
            String return_code=map.get("return_code");
            
            if(return_code!=null&&return_code.equals("SUCCESS")){
            	 response.getWriter().write(setXML("SUCCESS", "OK"));
	             //System.out.println("-------------" + setXML("SUCCESS", "OK"));
	             
	             //商户订单号
	             String ourTradeNo=map.get("out_trade_no");
	             //支付结果
	             String resultCode=map.get("result_code");
	             
	             MallOrder mallOrder=new MallOrder();
	             HashMap map2=new HashMap();
	             map2.put("outTradeNo", ourTradeNo);
	             Page mallOrders=mallProductService.searchMallOrder(map2);
	             ArrayList list=(ArrayList) mallOrders.getItems();
	             if("SUCCESS".equals(resultCode)){
	            	 if(list.size()>0){
	            		 mallOrder=(MallOrder) list.get(0);
	            		 //付款成功，已支付
	            		 mallOrder.setStatus(1);
	            		 mallProductService.saveOrder(mallOrder);
	            		 //System.out.println("付款成功");
	            	 }
	             }else{
	            	 if(list.size()>0){
	            		 mallOrder=(MallOrder) list.get(0);
	            		 //付款失败，未支付
	            		 mallOrder.setStatus(7);
	            		 mallProductService.saveOrder(mallOrder);
	            		 //System.out.println("付款失败");
	            	 }
	             }
	             
            }else{
            	 response.getWriter().write(setXML("FAIL", "FAIL"));
	             //System.out.println("-------------" + setXML("FAIL", "FAIL"));
            }
	        
		        
	 	}
	 	/**
	 	 * 必传参数
	 	 * 总金额：totalPrice
	 	 * 商品名称：commodityName
	 	 * 
	 	 * 
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception
	 	 */
	 	
	 	@RequestMapping(value="/goPayApp")
	    public void goPayApp(HttpServletRequest request,HttpServletResponse response) {
	        
	 		PrintWriter writer = null;
			try {
				JSONObject resultJ = new JSONObject();
				writer = response.getWriter();
				//商户订单号
				String outTradeNo = request.getParameter("outTradeNo");
				String path = request.getContextPath();
		        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		        Double tempNum = new Double(request.getParameter("totalPrice"));
		        DecimalFormat df2 = new DecimalFormat("####");
		        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        String nonceStr=WXUtil.getNonceStr();
		        /** 总金额(分为单位) */
		        int total = Integer.parseInt(df2.format(tempNum));
		        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		        /** 公众号APPID */
		        parameters.put("appid", "wxca15ec4e2ad1acca");
		        /** 商户号 */
		        parameters.put("mch_id", "1247457201");
		        /** 随机字符串 */
		        parameters.put("nonce_str", nonceStr);
		        /** 商品名称 */
		        parameters.put("body", request.getParameter("commodityName"));
		        /** 当前时间 yyyyMMddHHmmss */
		        String currTime = TenpayUtil.getCurrTime();
		        /** 8位日期 */
		        String strTime = currTime.substring(8, currTime.length());
		        /** 四位随机数 */
		        String strRandom = TenpayUtil.buildRandom(4) + "";
		        /** 订单号 */
		        parameters.put("out_trade_no", outTradeNo);
		        /** 订单金额以分为单位，只能为整数 */
		        parameters.put("total_fee", total);
		        /** 客户端本地ip */
		        parameters.put("spbill_create_ip", request.getRemoteAddr());
		        /** 支付回调地址 */
		        parameters.put("notify_url", basePath + "payment/goPayResult");
		        /** 支付方式为APP支付 */
		        parameters.put("trade_type", "APP");
		        
		        /** MD5进行签名，必须为UTF-8编码，注意上面几个参数名称的大小写   APP签名  */
		        String sign = createSignA("UTF-8", parameters);
		        parameters.put("sign", sign);
		        
		        /** 生成xml结构的数据，用于统一下单接口的请求 */
		        String requestXML = getRequestXml(parameters);
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
		            SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		            params.put("appid", "wxca15ec4e2ad1acca");
		            params.put("partnerid", "f39a6ae0aa0144bf0dbfd6642ff96081");
		            params.put("prepayid", map.get("prepay_id"));
		            params.put("package", "Sign=WXPay");
		            params.put("noncestr", nonceStr);
		            params.put("timestamp", System.currentTimeMillis());
		            String paySign = createSignA("UTF-8", params);
		            params.put("sign", paySign);
		            params.put("out_trade_no", outTradeNo);
		            /** 获取用户的微信客户端版本号，用于前端支付之前进行版本判断，微信版本低于5.0无法使用微信支付 */
		            String userAgent = request.getHeader("user-agent");
		            char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
		            params.put("agent", new String(new char[] { agent }));
		            //构造的json
		            String json = JSONArray.fromObject(params).toString();
		    		resultJ.put("success", 1);
					JSONObject data = new JSONObject();
					data.put("result", json);
					resultJ.put("data", data);
					writer.print(resultJ.toString());

			}catch(Exception e) {
					JSONObject result = new JSONObject();
					result.put("success", 0);
					JSONObject error = new JSONObject();
					error.put("code", 1000);
					result.put("error", error);
					writer.print(result.toString());
					e.printStackTrace();
			}
	        
	    }
	 	

	    
	    @RequestMapping(value = "pay")
	    public  void notify_success(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
	    	//System.out.println("微信支付成功调用回调URL");
	        
	        InputStream inStream = request.getInputStream();
	        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = inStream.read(buffer)) != -1) {
	            outSteam.write(buffer, 0, len);
	        }
	        //System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
	        outSteam.close();
	        inStream.close();

	        /** 支付成功后，微信回调返回的信息 */
	        String result = new String(outSteam.toByteArray(), "utf-8");
	        //System.out.println("微信返回的订单支付信息:" + result);
	        Map<Object, Object> map = XMLUtil.doXMLParse(result);

	        // 用于验签
	        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
	        for (Object keyValue : map.keySet()) {
	            /** 输出返回的订单支付信息 */
	            //System.out.println(keyValue + "=" + map.get(keyValue));
	            if (!"sign".equals(keyValue)) {
	                parameters.put(keyValue, map.get(keyValue));
	            }
	        }
	        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
	            // 先进行校验，是否是微信服务器返回的信息
	            String checkSign = createSign("UTF-8", parameters);
	            //System.out.println("对服务器返回的结果进行签名：" + checkSign);
	            //System.out.println("服务器返回的结果签名：" + map.get("sign"));
	            if (checkSign.equals(map.get("sign"))) {// 如果签名和服务器返回的签名一致，说明数据没有被篡改过
	                //System.out.println("签名校验成功，信息合法，未被篡改过");
	                
	                /** 告诉微信服务器，我收到信息了，不要再调用回调方法了 */
	                /**如果不返回SUCCESS的信息给微信服务器，则微信服务器会在一定时间内，多次调用该回调方法，如果最终还未收到回馈，微信默认该订单支付失败*/
	                /** 微信默认会调用8次该回调地址 */
	                response.getWriter().write(setXML("SUCCESS", ""));
	                //System.out.println("-------------" + setXML("SUCCESS", ""));
	                
	                ////System.out.println("我去掉了发送SUCCESS给微信服务器");
	                
	            }
	        }
	    }
	    
	    
	    /**
	     * 支付成功请求的地址URL，告知用户已经支付成功
	     * 
	     * 作者: zhoubang 日期：2015年6月10日 上午10:37:35
	     * 
	     * @param request
	     * @param response
	     * @param totalPrice
	     *            金额单位为元
	     * @return
	     */
	    @RequestMapping("paysuccess")
	    public ModelAndView paysuccess(HttpServletRequest request,
	            HttpServletResponse response, double totalPrice) {
	        ModelAndView mav = new ModelAndView("forward:" + "/result.jsp");
	        mav.addObject("money", totalPrice);
	        return mav;
	    }
	    
	    /**
	     * 发送xml格式数据到微信服务器 告知微信服务器回调信息已经收到。
	     * 
	     * 作者: zhoubang 日期：2015年6月10日 上午9:27:33
	     * 
	     * @param return_code
	     * @param return_msg
	     * @return
	     */
	    public static String setXML(String return_code, String return_msg) {
	        return "<xml><return_code><![CDATA[" + return_code
	                + "]]></return_code><return_msg><![CDATA[" + return_msg
	                + "]]></return_msg></xml>";
	    }
	    /**
	     * sign签名
	     * 
	     * 作者: zhoubang 日期：2015年6月10日 上午9:31:24
	     * 
	     * @param characterEncoding
	     * @param parameters
	     * @return
	     */
	    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
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
	        sb.append("key=" + Keys.API_KEY);
	        /** 记得最后一定要转换为大写 */
	        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	        return sign;
	    }
	    
	    /**
	     * android 加密
	     * @param characterEncoding
	     * @param parameters
	     * @return
	     */
	    
	    public static String createSignA(String characterEncoding, SortedMap<Object, Object> parameters) {
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
	        sb.append("key=" + "f39a6ae0aa0144bf0dbfd6642ff96081");
	        /** 记得最后一定要转换为大写 */
	        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	        return sign;
	    }
	    

	    /**
	     * 将请求参数转换为xml格式的string
	     * 
	     * 作者: zhoubang 日期：2015年6月10日 上午9:25:51
	     * 
	     * @param parameters
	     * @return
	     */
	    public static String getRequestXml(SortedMap<Object, Object> parameters) {
	        StringBuffer sb = new StringBuffer();
	        sb.append("<xml>");
	        Set<Entry<Object, Object>> es = parameters.entrySet();
	        Iterator<Entry<Object, Object>> it = es.iterator();
	        while (it.hasNext()) {
	            Entry<Object, Object> entry = it.next();
	            String k = (String) entry.getKey();
	            String v = entry.getValue() + "";
	            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
	                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
	            } else {
	                sb.append("<" + k + ">" + v + "</" + k + ">");
	            }
	        }
	        sb.append("</xml>");
	        return sb.toString();
	    }



		public MallProductService getMallProductService() {
			return mallProductService;
		}



		public void setMallProductService(MallProductService mallProductService) {
			this.mallProductService = mallProductService;
		}

}
