package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Keyword;
import com.wechat.entity.Material;
import com.wechat.service.ArticleService;
import com.wechat.service.KeywordService;
import com.wechat.service.MaterialService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/material")
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private KeywordService keywordService;
	
	
	
	public KeywordService getKeywordService() {
		return keywordService;
	}

	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	
	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}


	@Autowired
	private KeywordService weChatKeywordService;
	
	
	

	public KeywordService getWeChatKeywordService() {
		return weChatKeywordService;
	}

	public void setWeChatKeywordService(KeywordService weChatKeywordService) {
		this.weChatKeywordService = weChatKeywordService;
	}

	@RequestMapping(value="/materialManager")
	public String materialManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		HashMap map = new HashMap();
		
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "10");
		map.put("weChatAccountId", Keys.ACCOUNT_ID);
		
		
		Page resultPage = this.materialService.searchMaterial(map);
		HashMap keywordMap = this.keywordService.searchKeywordMapByMaterialId(Keys.ACCOUNT_ID);
		HashMap materialMap = this.materialService.searchMaterialMap();
		Iterator entries = materialMap.entrySet().iterator(); 
		Material material = new Material();
		Object[] obj = new Object[5];
		for (int i = 0; i < resultPage.getItems().size(); i++) {
			
			if(keywordMap.get(((Material)resultPage.getItems().get(i)).getId().toString())!= null)
			{
				
				 obj = (Object[])keywordMap.get(((Material)resultPage.getItems().get(i)).getId().toString());
				((Material)resultPage.getItems().get(i)).setKeywordName(obj[1].toString());
				((Material)resultPage.getItems().get(i)).setKeywordStatus(new Integer(obj[2].toString()));
				((Material)resultPage.getItems().get(i)).setKeywordType(1);
				((Material)resultPage.getItems().get(i)).setKeywordMatchingRules(new Integer(obj[3].toString()));
			}
			
			 if(((Material)resultPage.getItems().get(i)).getType() ==1)
			    {
				 	while (entries.hasNext()) {  
					    Map.Entry entry = (Map.Entry) entries.next();  
					    material = (Material)entry.getValue(); 
					    if(material.getParentId() == ((Material)resultPage.getItems().get(i)).getId())
					    {
					    	((Material)resultPage.getItems().get(i)).getMaterialList().add(material);
					    }
					    
					  
					}  
			    }
			
		}
		//HashMap keywordMap = this.weChatKeywordService.searchAllKeywordMap(Keys.ACCOUNT_ID);
		Material Material = new Material();

		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("contentType", request.getParameter("contentType"));
		return "material/materialManager";
		 
	}

	@RequestMapping(value="/toMaterial")
	public String toMaterial(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		
				
		
		
		HashMap map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "100");
		Page resultPage = this.articleService.searchArticle(map);
		request.setAttribute("resultPage", resultPage);
		
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("materialId", request.getParameter("materialId"));
		String materialId = request.getParameter("materialId");
		Material material = new Material();
		if(materialId!= null && !"".equals(materialId))
		{
			
			material.setId(new Integer(materialId));
			material = this.materialService.getMaterial(material);
			List materialList = this.materialService.searchMaterialByParentId(material.getId().toString());
			request.setAttribute("materialList", materialList);
			request.setAttribute("material", material);
			request.setAttribute("showMaterial", material);
			request.setAttribute("userType", "edit");
			request.setAttribute("type", material.getType());
			
		}
		else if(request.getParameter("showMaterialId")!= null && !"".equals(request.getParameter("showMaterialId")))
		{
			List materialList = this.materialService.searchMaterialByParentId(request.getParameter("showMaterialId"));
			request.setAttribute("materialList", materialList);
			if("add".equals(request.getParameter("userType")))
			{
				material = new Material();
				material.setType(1);
				material.setParentId(new Integer(request.getParameter("showMaterialId")));
				request.setAttribute("material", material);
				request.setAttribute("userType", "add");
				
			}
			else
			{
				
				material.setId(new Integer(request.getParameter("oldMaterialId")));
				material = this.materialService.getMaterial(material);
				request.setAttribute("material", material);
				
				Material showMaterial = new Material();
				showMaterial.setId(new Integer(request.getParameter("showMaterialId")));
				showMaterial = this.materialService.getMaterial(showMaterial);
				request.setAttribute("showMaterial", showMaterial);
				request.setAttribute("userType", "edit");
			}
			
		}
		else
		{
			request.setAttribute("showMaterial", material);
			if("add".equals(request.getParameter("userType")) || "1".equals(request.getParameter("type")))
			{
				material.setType(1);
			}
			else
			{
				material.setType(0);
			}
			
			
			request.setAttribute("material", material);
		}
		
		
		return "material/materialModify"; 
	}
	
	@RequestMapping(value="/toMaterialUrl")
	public String toMaterialUrl(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String MaterialId = request.getParameter("materialId");
		if(MaterialId!= null && !"".equals(MaterialId))
		{
			
			Material Material = new Material();
			Material.setId(new Integer(MaterialId));
			Material = this.materialService.getMaterial(Material);
			request.setAttribute("Material", Material);
			
		}		
		return "material/materialView";
	}
		
	@RequestMapping(value="/toMaterialView")
	public String toMaterialView(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
//		String keywordId = request.getParameter("MaterialId");
//		if(keywordId!= null && !"".equals(keywordId))
//		{
//			Material Material = new Material();
//			Material.setId(new Integer(keywordId));
//			Material = this.materialService.getMaterial(Material);
//			request.setAttribute("Material", Material);
//		}
		return "material/materialView";
		
		
		 
		 
		 
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
	
	@RequestMapping(value="/saveMaterial")
	public String saveMaterial(@RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2,@RequestParam(value = "file3", required = false) MultipartFile file3,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		request.setAttribute("type", request.getParameter("type"));
		Material material = new Material();
		material.setAccountId(new Integer(Keys.ACCOUNT_ID));
		MultipartFile file = null;
		if("0".equals(request.getParameter("typeId")))
		{
			material.setTitle(request.getParameter("title0"));
			material.setSummary(request.getParameter("summary0"));
			file = file1;
			material.setUrl(Keys.STAT_NAME+"/wechat/article/toArticle?articleId="+request.getParameter("url"));
		}
		if("1".equals(request.getParameter("typeId")))
		{
			material.setTitle(request.getParameter("title1"));
			material.setSummary(request.getParameter("summary1"));
			file = file2;
			material.setUrl(Keys.STAT_NAME+"/wechat/article/toArticle?materialId=");
			
		}
		if("2".equals(request.getParameter("typeId")))
		{
			material.setTitle(request.getParameter("title2"));
			material.setSummary(request.getParameter("summary2"));
			file = file3;
			material.setUrl(request.getParameter("url"));
			
		}
		material.setType(new Integer(request.getParameter("type")));
		
		material.setContent(request.getParameter("content"));
		material.setContentType(new Integer(request.getParameter("typeId")));
		material.setLogoStatus(new Integer(request.getParameter("logoStatus")));
		material.setParentId(new Integer(request.getParameter("parentId")));
		String fileName = "";
		
		if(file.getSize()!=0)
		{
			fileName = file.getOriginalFilename();
			material.setLogoStatus(0);
		}
		
		if(request.getParameter("id")!= null && !"".equals(request.getParameter("id")))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			material.setId(new Integer(request.getParameter("id")));
			
			material.setCreateDate(sdf.parse(request.getParameter("tempDate")));
			
			
			if(file.getSize()!=0)
			{
				
				File fileDir = new File(Keys.USER_PIC_PATH+ material.getAccountId());
				if (!fileDir.exists())
				{
					fileDir.mkdir();
				}
				fileName = new Date().getTime() + "" + fileName.subSequence(fileName.indexOf("."), fileName.length());
				
				String pathName = Keys.USER_PIC_PATH + material.getAccountId();
		        File targetFile = new File(pathName, fileName);
		        if(!targetFile.exists()){
		            targetFile.mkdirs();
		        }

		        //保存
		        try {
		            file.transferTo(targetFile);
		            File deleteFile = new File(Keys.USER_PIC_PATH+request.getParameter("logo"));

		            if(!"".equals(request.getParameter("logo")) && request.getParameter("logo")!= null)
		            {
		            	 deleteFile.delete();
		            }
		           
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        material.setLogo(material.getAccountId()+"/"+fileName);
		        
			}
			else
			{
				material.setLogo(request.getParameter("logo"));
				File deleteFile = new File(Keys.USER_PIC_PATH+request.getParameter("logo"));
	            
	            if(material.getLogoStatus() ==1)
	            {
	            	material.setLogo("");
	            	deleteFile.delete();
	            	
	            }
			}
//			if(!"".equals(request.getParameter("showMaterialId")) && request.getParameter("showMaterialId")!= null )
//			{
//
//				if("add".equals(request.getParameter("userType")))
//				{
//					material.setParentId(new Integer(request.getParameter("showMaterialId")));
//				}
//				else
//				{
//					material.setParentId(tempMaterial.getParentId());
//				}
//				
//			}
			if(material.getContentType() ==0)
			{
			
				material.setUrl(Keys.STAT_NAME+"/wechat/article/toArticle?articleId="+request.getParameter("url"));
			
				
			}
			if(material.getContentType() ==1)
			{
				
				material.setUrl(Keys.STAT_NAME+"/wechat/article/toArticle?materialId="+material.getId().toString());
				
			}
			this.materialService.saveMaterial(material);
			
			
			
		}
		else
		{	
			if(file.getSize()!=0)
			{
				File fileDir = new File(Keys.USER_PIC_PATH + material.getAccountId());
				if (!fileDir.exists())
				{
					fileDir.mkdir();
				}
				fileName = new Date().getTime() + "" + fileName.subSequence(fileName.indexOf("."), fileName.length());
				String pathName = Keys.USER_PIC_PATH + material.getAccountId();
		        File targetFile = new File(pathName, fileName);
		        if(!targetFile.exists()){
		            targetFile.mkdirs();
		        }
	
		        //保存
		        try {
		            file.transferTo(targetFile);
		            material.setLogo(material.getAccountId()+"/"+fileName);
		        
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			else
			{
				material.setLogo(request.getParameter("logo"));
				File deleteFile = new File(Keys.USER_PIC_PATH+request.getParameter("logo"));
	            
	            if(material.getLogoStatus() ==1)
	            {
	            	material.setLogo("");
	            	deleteFile.delete();
	            	
	            }
			}
//			if(!"".equals(request.getParameter("showMaterialId")) && request.getParameter("showMaterialId")!= null )
//			{
//				if("add".equals(request.getParameter("userType")))
//				{
//					material.setParentId(new Integer(request.getParameter("showMaterialId")));
//				}
//				
//				
//				
//			}
			
			
			
			material.setCreateDate(new Date());
		
			this.materialService.saveMaterial(material);
			if(material.getContentType() ==1)
			{
				material.setUrl(material.getUrl() + "" + material.getId().toString());
				this.materialService.saveMaterial(material);
			}
	    	
		}
		
		
		if(material.getType() ==0)
		{
			return "redirect:materialManager?type="+request.getParameter("type");
		}
		else
		{
			if(material.getParentId()==0)
			{
				return "redirect:toMaterial?type=1&materialId="+material.getId();
			}
			else
			{
				return "redirect:toMaterial?showMaterialId="+material.getParentId()+"&userType=edit&oldMaterialId="+material.getId()+"&type="+request.getParameter("type");
			}
			
		}
		
		
		 
		 
	}
	
	
	

	@RequestMapping(value="/materialManagerView")
	public String materialManagerView(HttpServletRequest request,HttpServletResponse response)throws Exception{
		

		

		
		
		List jsonList = new ArrayList();
		Material material = new Material();
		material.setId(new Integer(request.getParameter("materialId")));
		material = this.materialService.getMaterial(material);
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		JSONArray AgentKeyWordInfo = new JSONArray();
		JSONObject jobj = new JSONObject();		
		jobj.put("id", material.getId());
		jobj.put("content", material.getContent());		
		jsonList.add(jobj);	
		jsonObj.put("infoList", jsonList);
			
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
		 
		 
	}
	
	
	
	
	
	@RequestMapping(value="/removeMaterial")
	public String removeMaterial(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String MaterialId = request.getParameter("materialId");
		
		Material Material = new Material();
		Material.setId(new Integer(MaterialId));
		
		 Material tempMaterial = this.materialService.getMaterial(Material);
		 if(tempMaterial.getLogoStatus()==0)
		 {
			 File deleteFile = new File(Keys.USER_PIC_PATH+tempMaterial.getLogo());
			 deleteFile.delete(); 
		 }
		 this.materialService.removeMaterial(MaterialId);
		
		 
		 List<Material> materialList = this.materialService.searchMaterialByParentId(tempMaterial.getId().toString());
		 
		 for (int i = 0; i < materialList.size(); i++) {
			
			 if(materialList.get(i).getLogoStatus()==0)
			 {
				 File deleteFile = new File(Keys.USER_PIC_PATH+materialList.get(i).getLogo());
				 deleteFile.delete(); 
			 }
			 this.materialService.removeMaterial(materialList.get(i).getId().toString());
		}
		 
		 if("add".equals(request.getParameter("userType")))
		 {
			 return "redirect:toMaterial?userType=add&showMaterialId="+tempMaterial.getParentId()+"&type=1";
		 }
		return "redirect:materialManager";
		
	 
		 
	}
	
	
	@RequestMapping(value="/saveKeywordByMaterialId")
	public String saveKeywordByMaterialId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		
		
		Keyword keyword = new Keyword();
		if(request.getParameter("keywordId")!= null && !"".equals(request.getParameter("keywordId")))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			keyword.setId(new Integer(request.getParameter("keywordId")));
			Keyword tempKeyword = this.keywordService.getKeyword(keyword);
			keyword.setCreateDate(sdf.parse(tempKeyword.getCreateDate().toString()));
			keyword.setAccountId(new Integer(Keys.ACCOUNT_ID));
			if(keyword.getStatus() ==null)
			{
				keyword.setStatus(1);
				
			}
		}
		keyword.setKeyword(request.getParameter("keywordName"));
		keyword.setCreateDate(new Date());
		keyword.setAccountId(new Integer(Keys.ACCOUNT_ID));
		if("0".equals(request.getParameter("contentType")))
		{
			keyword.setContent("单图文");
		}
		else
		{
			keyword.setContent("多图文");
		}
		
		keyword.setMatchingRules(new Integer(request.getParameter("matchingRules")));
		keyword.setContentType(new Integer(request.getParameter("contentType"))+1);
		
		keyword.setMaterialId(new Integer(request.getParameter("materialId")));
		
		//System.out.println("status = "+request.getParameter("status"));
		
		keyword.setStatus(new Integer(request.getParameter("status")));
		
		
		
		
		this.keywordService.saveKeyword(keyword);
		return "redirect:materialManager?type=1";
		 
	}
	
	
}
