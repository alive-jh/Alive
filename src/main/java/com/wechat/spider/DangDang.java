package com.wechat.spider;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DangDang {
	private String html = "";
	public data getinfo(String ISBN){
		
		String url =  "http://search.dangdang.com/?key=" + ISBN;
		HttpRequestor httpget = new HttpRequestor();
		data result = new data();
		
		try {
			html = httpget.doGet(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(html,"UTF-8");
		Element content = doc.getElementById("component_0__0__6612"); 
		if(null!=content && !"".equals(content)){
			Elements links = content.getElementsByTag("a"); 
			 for (Element link : links) { 
				 String detailurl = link.attr("href"); 
				 try {
					getdetailinfo(detailurl,result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
				}
				 break;
			 }
		}
		 return result;
	}
	
	private void getdetailinfo(String url,data info) throws Exception{
		String html = "";
		JSONObject result = new JSONObject();
		Document doc = Jsoup.connect(url).get(); 
		Elements elem = doc.getElementsByTag("script"); 
		String mix = elem.toString().replaceAll("\\s*|\t|\r|\n","");
		String[] temp = mix.split("varprd_info=");
		if (temp.length > 1){
			temp = temp[1].split("}]};");
			html = temp[0];
			html = html + "}]}";
		}
		JSONObject data = new JSONObject(html);
//		//System.out.println(data);

		String uPrice = data.getJSONObject("product_info_new").getString("original_price");
		result.put("uPrice", uPrice);
		info.setuPrice(uPrice);
		JSONArray product_desc_sorted = data.getJSONArray("product_desc_sorted");
		
		
		String uContent = data.getJSONObject("product_desc").getString("content");
		uContent = uContent.replace("\n", "");
		result.put("uContent", uContent);
		info.setuContent(uContent);
		
		String uEditRecommend = data.getJSONObject("product_desc").getString("abstract");
		uEditRecommend = uEditRecommend.replace("\n","");
		result.put("uEditRecommend", uEditRecommend);
		info.setuEditRecommend(uEditRecommend);
		
		String uAuthorDetail = data.getJSONObject("product_desc").getString("authorintro");
		uAuthorDetail = uAuthorDetail.replace("\n", "");
		result.put("uAuthorDetail", uAuthorDetail);
		info.setuAuthorDetail(uAuthorDetail);
		
		for(int i=0 ; i < product_desc_sorted.length();i++){
			JSONObject temp2 = product_desc_sorted.getJSONObject(i);
			String name = temp2.getString("name");
			if(name.equals("????????????")){
				JSONArray content = temp2.getJSONArray("content");
				for(int j = 0 ;j < content.length();j++){
					String name2 = content.getJSONObject(j).getString("name");
					if(name2.equals("??????")){
						String uBname = content.getJSONObject(j).getString("content");
						result.put("uBname",uBname );
						info.setuBname(uBname);
						
					}else if(name2.equals("ISBN")){
						String ISBN = content.getJSONObject(j).getString("content");
						result.put("ISBN", ISBN);
						
					}else if(name2.equals("??????")){
						String uAuthor = content.getJSONObject(j).getString("content");
						String uTranslator = "";
						String [] temp6 = uAuthor.split("???");
						if(temp6.length > 1){
							for(int k = 0;k < temp6.length;k++ ){
								if (temp6[k].contains("???")){
									uAuthor = temp6[k];
									uAuthor = uAuthor.replace("???","");
								}else if(temp6[k].contains("???")){
									uTranslator = temp6[k];
									uTranslator = uTranslator.replace("???","");								
								}else{
									
								}
								
								result.put("uAuthor",uAuthor );
								info.setuAuthor(uAuthor);
								
								result.put("uTranslator",uTranslator );
								info.setuTranslator(uTranslator);
							
							}
							
						}else{
		
							result.put("uAuthor",uAuthor );
							info.setuAuthor(uAuthor);
						}
						
					}else if(name2.equals("?????????")){
						String uPublish = content.getJSONObject(j).getString("content");
						result.put("uPublish",uPublish );
						info.setuPublish(uPublish);
						
					}else if(name2.equals("??????")){
						String uFolio = content.getJSONObject(j).getString("content");
						result.put("uFolio",uFolio);
						info.setuFolio(uFolio);
						
					}else if(name2.equals("??????")){
						String uCataLog = content.getJSONObject(j).getString("content");
						result.put("uCataLog", uCataLog);
						info.setuCataLog(uCataLog);
						
					}else if(name2.equals("??????")){
						String uPage = content.getJSONObject(j).getString("content");
						if(isInteger(uPage)){
							result.put("uPage", uPage);
							info.setuPage(uPage);
						}else{
						    Pattern pattern =Pattern.compile("(\\d+)");
						    Matcher matcher = pattern.matcher(uPage);
							if(matcher.find()){
								uPage = matcher.group(0);
								result.put("uPage", uPage);
								info.setuPage(uPage);	
							}else{
								uPage = "0";
								result.put("uPage", uPage);
								info.setuPage(uPage);	
	
							}
						}
						
					}else if(name2.equals("?????????")){
						String uSeries = content.getJSONObject(j).getString("content");

						result.put("uSeries", uSeries);
						info.setuSeries(uSeries);
					}else{
						
					}
					
				}
			}
			
		}
		 return;
	}
	/*
	  * ????????????????????? 
	  * @param str ?????????????????? 
	  * @return ???????????????true,????????????false 
	*/


	  public static boolean isInteger(String str) {  
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	    return pattern.matcher(str).matches();  
	  }

	public void main(String[] args) throws Exception{
		//System.out.println(getinfo("9787532498437"));
	}
}
