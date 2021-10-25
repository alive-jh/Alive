package com.wechat.util;

import com.wechat.edtity.message.req.TextMessage;
import com.wechat.edtity.message.resp.Article;
import com.wechat.edtity.message.resp.NewsMessage;
import com.wechat.entity.Account;
import com.wechat.entity.Keyword;
import com.wechat.entity.Material;
import com.wechat.service.AccountService;
import com.wechat.service.KeywordService;
import com.wechat.service.MaterialService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CoreService {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		MaterialService materialService = (MaterialService) appContext.getBean("materialService");
		AccountService accountService = (AccountService) appContext.getBean("accountService");
		KeywordService keywordService = (KeywordService) appContext.getBean("keywordService");

		String respMessage = null;
		try {

			// 默认返回的文本消息内容
			String respContent = "";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// textMessage.setFuncFlag(0);
			String status = "0";
			String repStatus = "0";
			String tempRespContent = requestMap.get("Content");
			String sendName = Keys.STAT_NAME;
			// 文本消息
			Account account = accountService.getAccount("0");
			//// System.out.println("account = "+account.getAccount());
			//// System.out.println("msgType = "+msgType);
			List tempList = new ArrayList();
			String str = "";
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "";

				if (account.getId() != null) {

					// System.out.println("=====jingzhun=== "+status);
					int seatchType = 0;
					HashMap keywordMap = keywordService.searchKeywordMap(account.getId().toString());// 关键词回复--精准匹配
					Object[] keywordObj = new Object[3];

					if (keywordMap.get(requestMap.get("Content")) != null)// 精准匹配
					{
						keywordObj = (Object[]) keywordMap.get(requestMap.get("Content"));
						respContent = keywordObj[1].toString();
						seatchType++;

					}
					if (seatchType == 0)// 模糊匹配
					{
						// System.out.println("=====mohu===");
						// 模糊匹配单个结果代码
						// keywordObj = new Object[3];
						// keywordObj[0] = tempRespContent ;
						// keywordObj[1] = tempRespContent ;
						// keywordObj[2] = "0" ;
						// List<Object[]> tempList =
						// keywordService.searchKeywordList(account.getId().toString());//关键词回复--包含关键词
						// List<String> keywordList = new ArrayList();
						//
						// //System.out.println("tempList is size =
						// "+tempList.size());
						//
						// Iterator<Object[]> iter =
						// keywordMap.keySet().iterator();
						// keywordMap = new HashMap();
						// for (int i = 0; i < tempList.size(); i++) {
						//
						// keywordList.add(tempList.get(i)[0].toString());
						// keywordMap.put(tempList.get(i)[0].toString(),
						// tempList.get(i));
						//
						// }
						// KeywordFilter filter = new KeywordFilter();
						// filter.addKeywords(keywordList);
						//
						// Set set =
						// filter.getTxtKeyWords(requestMap.get("Content"));
						// //System.out.println("set1 = "+set.size());
						//
						// if(set.size()>0)
						// {
						// str = set.toString();
						// str = str.substring(1,str.length()-1);
						// String[] tempStr = str.split(",");
						// if(tempStr.length>1)
						// {
						// str = tempStr[0];
						// }
						// //System.out.println(" str = "+str);
						// keywordObj = keywordService.searchKeyworObject(str);
						// }
						// else
						// {
						// keywordObj = new Object[3];
						// }

						tempList = keywordService.searchKeywListInfo(tempRespContent);
						// System.out.println("tempList size is
						// "+tempList.size());
						if (tempList.size() == 1) {
							keywordObj = (Object[]) tempList.get(0);
						} else {
							// System.out.println("==============");
							keywordObj = null;
						}

					}

					if (keywordObj != null) {
						// System.out.println(" keywordObj is null ");
						if (keywordObj.length >= 2) {

							if (keywordObj[2] != null) {

								if (!"0".equals(keywordObj[2].toString()))// 单图文回复
								{

									NewsMessage newsMessage = new NewsMessage();
									newsMessage.setToUserName(fromUserName);
									newsMessage.setFromUserName(toUserName);
									newsMessage.setCreateTime(new Date().getTime());
									newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
									newsMessage.setFuncFlag(0);
									List<Article> articleList = new ArrayList<Article>();

									Material material = new Material();
									// System.out.println("materialId =
									// "+keywordObj[3].toString());
									material.setId(new Integer(keywordObj[3].toString()));
									//// System.out.println("keywordObj[3].toString()
									//// = "+keywordObj[3].toString());
									material = materialService.getMaterial(material);
									//// System.out.println("weChatMaterial.getType()
									//// = "+weChatMaterial.getType());
									// System.out.println("materialType =
									//// "+keywordObj[3].toString());
									if (material.getType() == 0)// 单图文消息
									{

										// contentType = 0;
										// //内容类型:0微官网页面,1显示正文,2显示跳转网址
										Article article = new Article();
										article.setTitle(material.getTitle());
										article.setDescription(material.getSummary());
										article.setPicUrl(sendName + "/wechat/wechatImages/" + material.getLogo());
										article.setUrl(material.getUrl());

										articleList.add(article);
										newsMessage = new NewsMessage();
										newsMessage.setToUserName(fromUserName);
										newsMessage.setFromUserName(toUserName);
										newsMessage.setCreateTime(new Date().getTime());
										newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
										newsMessage.setFuncFlag(0);
										newsMessage.setArticleCount(articleList.size());
										newsMessage.setArticles(articleList);
										respMessage = MessageUtil.newsMessageToXml(newsMessage);
										// System.out.println("dantuwen 0 =
										// "+respMessage);

										status = "2";

									}

									if (material.getType() == 1)// 多图文消息
									{

										Article article1 = new Article();
										article1.setTitle(material.getTitle());
										article1.setDescription("");
										article1.setPicUrl(sendName + "/wechat/wechatImages/" + material.getLogo());
										if (material.getContentType() == 2) {
											article1.setUrl(material.getUrl());
										} else if (material.getContentType() == 0) {
											article1.setUrl(material.getUrl());
										} else {
											article1.setUrl(sendName + material.getUrl() + material.getId());
										}

										// System.out.println("url =
										// "+article1.getUrl());
										articleList.add(article1);
										List<Material> list = materialService
												.searchMaterialByParentId(material.getId().toString());

										Article tempArticle = new Article();
										Material tempMaterial = new Material();
										for (int i = 0; i < list.size(); i++) {
											tempMaterial = list.get(i);
											tempArticle = new Article();
											tempArticle.setTitle(tempMaterial.getTitle());
											tempArticle.setDescription("");
											tempArticle.setPicUrl(
													sendName + "/wechat/wechatImages/" + tempMaterial.getLogo());
											tempArticle.setUrl(tempMaterial.getUrl());

											articleList.add(tempArticle);
										}

										newsMessage = new NewsMessage();
										newsMessage.setToUserName(fromUserName);
										newsMessage.setFromUserName(toUserName);
										newsMessage.setCreateTime(new Date().getTime());
										newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
										newsMessage.setFuncFlag(0);
										newsMessage.setArticleCount(articleList.size());
										newsMessage.setArticles(articleList);
										respMessage = MessageUtil.newsMessageToXml(newsMessage);
										// System.out.println("duotuwen 1 =
										// "+respMessage);
										status = "2";

									}
								} else {

									if ("0".equals(repStatus)) {
										status = "1";
										respContent = keywordObj[1].toString();
									}
								}

							}

						}
					} else {
						if (tempList.size() > 1) {

							status = "1";
							respContent = "";
							for (int i = 0; i < tempList.size(); i++) {

								respContent = respContent + ((Object[]) tempList.get(i))[1];
								if (i + 1 != tempList.size()) {
									respContent = respContent + "\n";
								}
							}
						}
					}

				}

			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				// respContent = "您发送的是图片消息！";

			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				// respContent = "您发送的是地理位置消息！";

			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				// respContent = "您发送的是链接消息！";

			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				// respContent = "您发送的是音频消息！";

			}

			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// System.out.println("eventType = "+eventType);

				if (account.getId() != null) {
					// 订阅
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						// respContent = "谢谢您的关注！";
						if (account != null) {
							// 关注推送
							if (!"".equals(account.getKeywordId()) && account.getKeywordId() != null) {
								Keyword keyword = new Keyword();
								keyword.setId(account.getKeywordId());
								keyword = keywordService.getKeyword(keyword);
								if (keyword.getId() != null) {
									if (keyword.getContentType() == 0) {
										respContent = keyword.getContent();
										status = "1";
										;
									} else {
										NewsMessage newsMessage = new NewsMessage();
										newsMessage.setToUserName(fromUserName);
										newsMessage.setFromUserName(toUserName);
										newsMessage.setCreateTime(new Date().getTime());
										newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
										newsMessage.setFuncFlag(0);
										List<Article> articleList = new ArrayList<Article>();

										Material material = new Material();
										material.setId(keyword.getMaterialId());

										material = materialService.getMaterial(material);

										if (material.getType() == 0)// 单图文消息
										{

											// contentType = 0;
											// //内容类型:0微官网页面,1显示正文,2显示跳转网址
											Article article = new Article();
											article.setTitle(material.getTitle());
											article.setDescription(material.getSummary());
											article.setPicUrl(sendName + "/wechat/wechatImages/" + material.getLogo());

											article.setUrl(material.getUrl());

											articleList.add(article);
											newsMessage = new NewsMessage();
											newsMessage.setToUserName(fromUserName);
											newsMessage.setFromUserName(toUserName);
											newsMessage.setCreateTime(new Date().getTime());
											newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
											newsMessage.setFuncFlag(0);
											newsMessage.setArticleCount(articleList.size());
											newsMessage.setArticles(articleList);
											respMessage = MessageUtil.newsMessageToXml(newsMessage);
											//// System.out.println("img 0 =
											//// "+respMessage);
											status = "2";

										}
										if (material.getType() == 1)// 多图文消息
										{

											Article article1 = new Article();
											article1.setTitle(material.getTitle());
											article1.setDescription("");
											article1.setPicUrl(sendName + "/wechat/wechatImages/" + material.getLogo());

											article1.setUrl(material.getUrl());

											// System.out.println("url1 =
											// "+article1.getUrl());
											articleList.add(article1);
											List<Material> list = materialService
													.searchMaterialByParentId(material.getId().toString());

											Article tempArticle = new Article();
											Material tempMaterial = new Material();
											for (int i = 0; i < list.size(); i++) {

												tempMaterial = list.get(i);
												tempArticle = new Article();
												tempArticle.setTitle(tempMaterial.getTitle());
												tempArticle.setDescription("");
												tempArticle.setPicUrl(
														sendName + "/wechat/wechatImages/" + tempMaterial.getLogo());

												tempArticle.setUrl(tempMaterial.getUrl());

												articleList.add(tempArticle);
											}

											newsMessage = new NewsMessage();
											newsMessage.setToUserName(fromUserName);
											newsMessage.setFromUserName(toUserName);
											newsMessage.setCreateTime(new Date().getTime());
											newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
											newsMessage.setFuncFlag(0);
											newsMessage.setArticleCount(articleList.size());
											newsMessage.setArticles(articleList);
											respMessage = MessageUtil.newsMessageToXml(newsMessage);

											status = "2";

										}
									}
								}

							}
						}
						
						accountService.createDefualtWechatUser(fromUserName);
						accountService.updateSubscribeStatus(fromUserName, 1);

					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
						accountService.updateSubscribeStatus(fromUserName, 0);
						
					}
					// 自定义菜单点击事件
					else if (eventType.equals("VIEW")) {
						// String eventKey = requestMap.get("EventKey");
						// Integer account_id=account.getId();
						// weChatContentService.saveBrowse(eventKey,account_id
						// ); //记录点击事件
					} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

						// Integer account_id=account.getId();
						// wechatContentService.saveBrowse(eventKey,account_id
						// );//记录点击事件

						String eventKey = requestMap.get("EventKey");
						// System.out.println(" eventKey == "+eventKey);
						if (eventKey.lastIndexOf("materialId") != -1) {

							eventKey = eventKey.substring(11, eventKey.length());
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setFuncFlag(0);
							List<Article> articleList = new ArrayList<Article>();

							Material material = new Material();
							material.setId(new Integer(eventKey.toString()));

							material = materialService.getMaterial(material);

							if (material.getType() == 0)// 单图文消息
							{

								// contentType = 0; //内容类型:0微官网页面,1显示正文,2显示跳转网址
								Article article = new Article();
								article.setTitle(material.getTitle());
								article.setDescription(material.getSummary());
								article.setPicUrl(sendName + "/wechat/wechatImages/" + material.getLogo());
								article.setUrl(material.getUrl());

								articleList.add(article);
								newsMessage = new NewsMessage();
								newsMessage.setToUserName(fromUserName);
								newsMessage.setFromUserName(toUserName);
								newsMessage.setCreateTime(new Date().getTime());
								newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
								newsMessage.setFuncFlag(0);
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								//// System.out.println("img 0 = "+respMessage);
								status = "2";

							}
							if (material.getType() == 1)// 多图文消息
							{

								Article article1 = new Article();
								article1.setTitle(material.getTitle());
								article1.setDescription("");
								article1.setPicUrl(sendName + "/wechat/wechatImages/" + material.getLogo());

								article1.setUrl(material.getUrl());

								articleList.add(article1);
								List<Material> list = materialService
										.searchMaterialByParentId(material.getId().toString());

								Article tempArticle = new Article();
								Material tempMaterial = new Material();
								for (int i = 0; i < list.size(); i++) {
									tempMaterial = list.get(i);
									tempArticle = new Article();
									tempArticle.setTitle(tempMaterial.getTitle());
									tempArticle.setDescription("");
									tempArticle.setPicUrl(sendName + "/wechat/wechatImages/" + tempMaterial.getLogo());

									tempArticle.setUrl(tempMaterial.getUrl());

									articleList.add(tempArticle);
								}

								newsMessage = new NewsMessage();
								newsMessage.setToUserName(fromUserName);
								newsMessage.setFromUserName(toUserName);
								newsMessage.setCreateTime(new Date().getTime());
								newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
								newsMessage.setFuncFlag(0);
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								//// System.out.println("img 1 = "+respMessage);
								status = "2";

							}

							// System.out.println(" aaaa status = "+status);

						} else if (eventKey.lastIndexOf("pic/") == -1) {
							textMessage.setContent(requestMap.get("EventKey"));
							// System.out.println(requestMap.get("EventKey"));
							respMessage = MessageUtil.textMessageToXml(textMessage);
							status = "2";
						}
					}
					// 如果二维码是微信生成的，则参数含有sense_id 和ticket
					else if (!"".equals(requestMap.get("Ticket")) && requestMap.get("Ticket") != null) {
						String eventKey = requestMap.get("EventKey");
						status = "4";
						// System.out.println("eventKey = "+eventKey);
						// System.out.println("ticket =
						// "+requestMap.get("Ticket"));
					}
					// 微信自带扫码工具
					else if (eventType.equals(MessageUtil.EVENT_TYPE_SCANCODE)) {
						status = "1";
					}
					// 自定义菜单扫描事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_PUSH)) {
						status = "1";
					}
				}

			}

			// if(account.getId()!= null)//保存消息记录
			// {
			// weChatMemberMessage.setWeChatAccountId(weChatAccount.getId());
			// //System.out.println("save message");
			// weChatMemberMessgeService.saveWeChatMemberMessge(weChatMemberMessage);
			// }
			// System.out.println("status type = "+status);
			// System.out.println("respContent = "+respContent);
			if ("0".equals(status)) {

				textMessage.setContent("/微笑");
				respMessage = MessageUtil.textMessageToXml(textMessage);
				String tempStr = requestMap.get("Content");
				if (!"".equals(tempStr) && tempStr != null) {

					// if(tempStr.lastIndexOf(account.getCustomerKeyword())!=
					// -1)//聊天内容转接到多客服
					// {
					// //System.out.println(" customer server start ");
					// textMessage.setMsgType(MessageUtil.TRANSFER_CUSOMER_SERVICE);
					// }
					// respMessage = MessageUtil.textMessageToXml(textMessage);
				}

			}
			if ("1".equals(status)) {

				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);

			}
			if ("4".equals(status))// 菜单推送
			{
				String key = requestMap.get("EventKey");
				textMessage.setContent(key);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			if ("10".equals(status)) {
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			if (requestMap.get("EventKey") != null && !"".equals(requestMap.get("EventKey")))// 菜单推送图文
			{
				respContent = requestMap.get("EventKey");
			}
			//// System.out.println(" respMessage == "+respMessage);
			if ("".equals(respContent) || respContent == null)// 不做任何回复
			{

				respMessage = "success";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	/**
	 * 计算采用utf-8编码方式时字符串所占字节数
	 * 
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	public static void main(String[] args) {
		String str = "1,234";

		String[] tempStr = str.split(",");

		// System.out.println("关键词="+tempStr.toString());
	}
}
