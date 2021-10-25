package com.wechat.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Amazon {
	public data getinfo(String ISBN) throws Exception{
		HttpRequestor httpget = new HttpRequestor();
		data result = new data();
		String url = "https://www.amazon.cn/s/ref=nb_sb_noss?field-keywords=9787115228567";
		String html = httpget.doGet(url);
		//System.out.println(html);
		Document doc = Jsoup.parse(html);
		Elements eles = doc.getElementsByClass("a-link-normal a-text-normal");
		if(eles.size() > 0){
			Element ele = eles.get(0);
			//System.out.println(ele);
		}
//		URL targerurl = new URL(url);
//		Document doc = Jsoup.parse(targerurl, 30000);
//		Element ele = doc.getElementById("result_0");
//		//System.out.println(ele.attr("data-asin"));
		
		return result;
	}
	public void main(String[] args) throws Exception{
		getinfo("9787532498437");
	}
}
