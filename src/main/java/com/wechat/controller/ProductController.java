package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Product;
import com.wechat.entity.ProductInfo;
import com.wechat.service.ProductService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import com.wechat.util.TwoDimensionCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

	
	@Resource
	private ProductService productService;

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@RequestMapping("/productInfoManager")
	public String productInfoManager(HttpServletRequest request,QueryDto queryDto) {
		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		
		
		map.put("name", queryDto.getName());
		map.put("model", queryDto.getModel());
		map.put("power", queryDto.getPower());
		
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		Page resultPage = this.productService.searchProductInfo(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		return "product/productInfoManager";
	}
	
	
	@RequestMapping(value="/productInfoManagerView")
	public String productInfoManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
		HashMap map = new HashMap();
		map.put("name", queryDto.getName());
		map.put("model", queryDto.getModel());
		map.put("power", queryDto.getPower());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("productId", request.getParameter("productId"));
		
		
		Page resultPage = this.productService.searchProductInfo(map);
		List<ProductInfo> infoList  = resultPage.getItems();
		
		List jsonList = new ArrayList();
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(ProductInfo product : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", product.getId());
				jobj.put("name", product.getName());
				jobj.put("model", product.getModel());
				jobj.put("characteristic", product.getCharacteristic());
				jobj.put("colortTemperature", product.getColortTemperature());
				jobj.put("cri", product.getCri());
				jobj.put("factor", product.getFactor());
				jobj.put("description", product.getDescription());
				jobj.put("dimming", product.getDimming());
				jobj.put("power", product.getPower());
				jobj.put("luminousFlux", product.getLuminousFlux());
				jobj.put("createDate", product.getCreateDate().toString());
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
		 
		 
	}

	

	
	@RequestMapping("/productManager")
	public String productManager(HttpServletRequest request,QueryDto queryDto) throws Exception {
		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		
		
		map.put("number", queryDto.getNumber());
		map.put("productInfoId", queryDto.getProductInfoId());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		Page resultPage = this.productService.searchProductInfo(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		//map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "1000");
		resultPage = this.productService.searchProductInfo(map);
		List<ProductInfo> tempList  = resultPage.getItems();
		map = new HashMap();
		HashMap powerMap = new HashMap();
		for (int i = 0; i < tempList.size(); i++) {
			
			map.put(tempList.get(i).getId().toString(),tempList.get(i));
			powerMap.put(tempList.get(i).getId().toString(),tempList.get(i).getName());
			
		}
		request.setAttribute("tempList", tempList);
		request.getSession().setAttribute("productMap", map);
		request.getSession().setAttribute("powerMap", powerMap);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("newDate", sdf.format(new Date()));
		
		return "product/productManager";
	}
	
	
	@RequestMapping(value="/productManagerView")
	public String productManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
		HashMap map = new HashMap();
		map.put("number", queryDto.getNumber());
		
		map.put("productInfoId", queryDto.getProductInfoId());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("productId", request.getParameter("productId"));
		
		
		Page resultPage = this.productService.searchProduct(map);
		List<Product> infoList  = resultPage.getItems();
		
		HashMap powerMap = new HashMap();
		if(request.getSession().getAttribute("productMap")!= null)
		{
			map = (HashMap)request.getSession().getAttribute("productMap");
			powerMap = (HashMap)request.getSession().getAttribute("powerMap");
		}
		List jsonList = new ArrayList();
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Product product : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", product.getId());
				jobj.put("number", product.getNumber());
				jobj.put("img", product.getImg());
				jobj.put("url", product.getUrl());
				jobj.put("productInfoId", product.getProductInfoId());
				jobj.put("createDate", product.getCreateDate().toString());
				if(powerMap.get(product.getProductInfoId().toString())!= null)
				{
					jobj.put("power", powerMap.get(product.getProductInfoId().toString()).toString());
				}
				jobj.put("url", product.getUrl());
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
		 
		 
	}

	
	@RequestMapping("/saveProductInfo")
	public String saveProductInfo( HttpServletRequest request,ProductInfo productInfo)throws Exception {
		
		
		ProductInfo tempproduct  = (ProductInfo)request.getSession().getAttribute("productInfo");
		if(productInfo.getId()== null || "".equals(productInfo.getId()))
		{
			
			productInfo.setCreateDate(new Date());
			
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			productInfo.setCreateDate(sdf.parse(request.getParameter("tempDate")));
			
		}
		
		this.productService.saveProductInfo(productInfo);
		
		
		
		
		
		
		return "redirect:productManager";
	}
	
	
	@RequestMapping("/saveProduct")
	public String saveProduct( HttpServletRequest request,Product product)throws Exception {
		
		
		
		if(product.getId()== null || "".equals(product.getId()))
		{
			
			String picPath = Keys.USER_PIC_PATH+"/qrcode/";
			product.setCreateDate(new Date());
			this.productService.saveProduct(product);
			
			product.setUrl(Keys.STAT_NAME+"/wechat/product/getProduct?id="+product.getId());
			
			
			File fileDir = new File(picPath);
			if (!fileDir.exists())
			{
				
				fileDir.mkdir();
			}
			String picName = new Date().getTime()+".png";
			product.setImg(picName);
			this.productService.saveProduct(product);
			String pathName = picPath+picName;
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode(); 
			twoDimensionCode.encoderQRCode(product.getUrl(),pathName, "png");
		
		
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			product.setCreateDate(sdf.parse(request.getParameter("tempDate")));
			this.productService.saveProduct(product);
			
		}
		
		
		
		
		
		return "redirect:productManager";
	}
	
	

	@RequestMapping("/getProduct")
	public String getProduct( HttpServletRequest request)throws Exception {
		
		
		Object[] obj = new Object[15];
		if(request.getParameter("id")!= null || !"".equals(request.getParameter("id")))
		{
			
			obj  = this.productService.getProductInfoById(request.getParameter("id"));
			
			
		}
		request.setAttribute("product",obj);
		String memberId = request.getParameter("memberId");
		request.setAttribute("memberId", memberId);
		String memberType = request.getParameter("memberType");
		request.setAttribute("memberType", memberType);
		
		return "product/productView";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/deleteProductInfo")
	public String delProductInfo(HttpServletRequest request,HttpServletResponse response) {

		
		productService.delereProductInfo(new Integer(request.getParameter("productInfoId")));

		
		return "redirect:productInfoManager";
	}
	
	@RequestMapping(value = "/deleteProduct")
	public String delProduct(HttpServletRequest request,HttpServletResponse response) {

		
		productService.delereProduct(request.getParameter("productId"));

		
		return "redirect:productManager";
	}
	public static void main(String[] args) throws Exception {
		
		String tempDate = "2015-07-30";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//System.out.println(sdf.format(d));
	}

	
}
