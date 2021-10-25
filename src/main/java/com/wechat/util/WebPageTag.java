package com.wechat.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebPageTag extends SimpleTagSupport {
	public Page page = null;
	public Map params = null;
	public String url = null;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void doTag() throws JspException, IOException {
		
		JspWriter out = getJspContext().getOut();
		if (null != page && page.getItems()!= null) {
			StringBuffer sb = new StringBuffer();
			int startIndex = page.getStartIndex().intValue();
			int pageSize = page.getPageSize().intValue();
			
			int totalCount = page.getTotalCount().intValue();
			//int nextIndex = page.getNextIndex().intValue();
			//int previousIndex = page.getPreviousIndex().intValue();
			int totalPage = page.getIndexes();
			startIndex  = startIndex -1;
			// 0,1,2,3...
			//int currentPage = startIndex / pageSize;
			int currentPage = startIndex ;
			int start = 0;
			int end = totalPage;
			//sb.append("<div class=\"default-page-container\">");
			sb.append("<div class=\"col-xs-12 no-padding\" style=\"text-align: left;font-family:'微软雅黑';line-height: 40px;\">");
			
			sb.append("<div class=\"col-xs-4 no-padding\"><label>共查询到<font id=\"fPageTotal\" color='red'>"+totalCount+"</font>条记录</label></div>");
			
			sb.append("<div class=\"col-xs-8 no-padding\"><ul class=\"pagination pull-right no-padding\" style=\"text-align:right;margin:1px;\">");
			
			
				if (currentPage + 5 < totalPage) {
					end = currentPage + 5;
					if(currentPage < 5)
					{
						if((currentPage+10)<totalPage)
						{
							end = currentPage + 10 - currentPage;
						}
					}
					
				}
			
			
			
			if (currentPage - 5 >= 0) {
				start = currentPage - 5;
				if(currentPage + 5>totalPage)
				{
					if((currentPage+10)>totalPage)
					{
							start = totalPage -10;
					}
				}
				
				
			}
			
			//sb.append("每页<font color='red'>").append(pageSize).append("</font>条记录，共<font color='red'>").append(page.getTotalCount()).append("</font>条记录                        ");
			
			int tempIndex = startIndex+1;//临时的当前页参数
			
			if (currentPage > 0 && tempIndex !=1) {
				
				sb.append("<li><a href=\"" + this.recombineUrl((0) * pageSize, pageSize)
						+ "\" class=\"yellowgreen\">首页</a></li>");
			}

			
			if (currentPage > 0) {
				
				sb.append("<li><a href=\"" + this.recombineUrl((currentPage - 1) * pageSize, pageSize)
						+ "\" class=\"yellowgreen\">上一页</a></li>");
			}
			
			if(totalCount>pageSize)
			{
				for (int i = start; i < end; i++) {
					String url = "";
					if (i == startIndex) {
						// focus-bg
						sb.append("<li class=\"active\"><a href=\"#>");
						// url += this.recombineUrl(i * pageSize, pageSize);
						// sb.append(url);
						sb.append("\" class=\"blue\">");
						sb.append(i + 1);
						sb.append("</a></li>");
						
						
	
					} else {
						if(i>=0)
						{
						sb.append("<li><a href=\"");
						url += this.recombineUrl(i * pageSize, pageSize);
						sb.append(url);
						sb.append("\" class=\"blue\">");
						sb.append(i + 1);
						sb.append("</a></li>");
						}
					}
	
				}
			}
			if (currentPage < totalPage - 1) {
				
				sb.append("<li><a href=\"" + this.recombineUrl((currentPage+1 ) * pageSize, pageSize)
						+ "\" class=\"yellowgreen\">下一页</a></li>");
			}
			if ( tempIndex <= totalPage-1) {
				
				sb.append("<li><a href=\"" + this.recombineUrl((totalPage) * pageSize, pageSize)
						+ "\" class=\"yellowgreen\">末页</a></li>           ");
			}

			sb.append("</ul></div>");
			
			//if(totalCount>pageSize)
			//{
				out.print(sb.toString());
			//}
			
		}

	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	
	private String recombineUrl(int index, int pageSize) {
		//Map map = CollectionUtil.getInstance().transferRequstParameterMap(params);
		String resultUrl = this.getUrl();
		JspContext jspContext = getJspContext();
		PageContext pageContext = (PageContext) jspContext;
		 
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		Map map =  request.getParameterMap();
		
		map = this.transferRequstParameterMap(map);
		String contextPath = request.getContextPath();
		if (null == map) {
			map = new HashMap();
		}
		map.put("startIndex", null);
		map.put("pageSize", null);
        Integer pageNum=page.getPageNum(index, pageSize);
        map.put("page", pageNum);
		if (map!= null) {

			if(resultUrl.lastIndexOf("?") == -1)
			{
				resultUrl = resultUrl+"?";
			}
			else {
				resultUrl = resultUrl+"&";
			}
			
			Iterator it = map.keySet().iterator();
			
			

			
		
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = MapUtils.getString(map, key);
				if (!"".equals(key)&&key!= null && !"".equals(value)&&value!= null) {
					resultUrl += key + "=" + value;
					resultUrl += "&";
				}
			}
			if (resultUrl.endsWith("&")) {
				resultUrl = resultUrl.substring(0, resultUrl.lastIndexOf("&"));
			}
		}
		if (resultUrl.startsWith("/")) {
			resultUrl = contextPath + resultUrl;
		}
		return resultUrl;
	}
	
	
	
	public Map transferRequstParameterMap(Map map)
	{
		if (map == null)
		{
			return null;
		}
		Map result = new HashMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext())
		{
			String key = (String) it.next();
			String[] keyValues = (String[]) map.get(key);
			String keyValue = keyValues[0];
			
			result.put(key, keyValue);
			
		}
		return result;
	}

	public static String stringArray2String(String[] array) {
		if (ArrayUtils.isEmpty(array)) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			result.append(array[i]);
		}
		return result.toString();
	}
}
