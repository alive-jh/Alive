package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Label;
import com.wechat.entity.Special;
import com.wechat.entity.SpecialInfo;
import com.wechat.service.BookService;
import com.wechat.service.CategoryService;
import com.wechat.service.MemberService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("special")
public class SpecialController {

	@Resource
	private BookService bookService;
	@Resource
	private MemberService memberService;

	@Resource
	private CategoryService categoryService;

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/specialManager")
	public String specialManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		if (!"".equals(request.getParameter("topicType"))
				&& request.getParameter("topicType") != null) {
			queryDto.setTopicType(request.getParameter("topicType"));
		}
		if (!"".equals(request.getParameter("status"))
				&& request.getParameter("status") != null) {
			queryDto.setStatus(request.getParameter("status"));
		}
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());
		map.put("topicType", queryDto.getTopicType());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());

		Page resultPage = this.categoryService.searchSpecial(map);
		request.setAttribute("resultPage", resultPage);
		List jsonList = new ArrayList();
		List<Object[]> infoList = resultPage.getItems();

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] bookSpecial : infoList) {
				JSONObject jobj = new JSONObject();
				jobj.put("id", bookSpecial[0]);
				jobj.put("title", bookSpecial[2]);
				jobj.put("logo", bookSpecial[1]);
				jobj.put("sort", bookSpecial[4]);
				jobj.put("status", bookSpecial[3]);
				jobj.put("topicType", bookSpecial[6]);
				jobj.put("books", bookSpecial[8]);

				if ("0".equals(bookSpecial[3].toString())) {
					jobj.put("statusName", "显示");
				} else {
					jobj.put("statusName", "隐藏");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		return "special/specialManager";
	}

	@RequestMapping("/specialMallManager")
	public String specialMallManager(HttpServletRequest request,
			QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		if (!"".equals(request.getParameter("topicType"))
				&& request.getParameter("topicType") != null) {
			queryDto.setTopicType(request.getParameter("topicType"));
		}
		if (!"".equals(request.getParameter("status"))
				&& request.getParameter("status") != null) {
			queryDto.setStatus(request.getParameter("status"));
		}
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());
		map.put("topicType", queryDto.getTopicType());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());

		Page resultPage = this.categoryService.searchSpecial(map);
		request.setAttribute("resultPage", resultPage);
		List jsonList = new ArrayList();
		List<Object[]> infoList = resultPage.getItems();

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] bookSpecial : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", bookSpecial[0]);
				jobj.put("title", bookSpecial[2]);
				jobj.put("logo", bookSpecial[1]);
				jobj.put("sort", bookSpecial[4]);
				jobj.put("status", bookSpecial[3]);
				jobj.put("topicType", bookSpecial[6]);
				jobj.put("catId", bookSpecial[5]);
				if ("0".equals(bookSpecial[3].toString())) {
					jobj.put("statusName", "显示");
				} else {
					jobj.put("statusName", "隐藏");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		return "special/specialMallManager";
	}

	@RequestMapping("/saveSpecial")
	public String saveSpecial(
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			HttpServletRequest request, Special bookSpecial) {
		// 保存圖片
		if (file1.getSize() != 0) {
			String fileName1 = "";
			fileName1 = file1.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH + "special/");
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			int random = (int) (Math.random() * 1000000);
			fileName1 = new Date().getTime()
					+ random
					+ ""
					+ fileName1.subSequence(fileName1.indexOf("."),
							fileName1.length());

			String pathName = Keys.USER_PIC_PATH + "special/";
			File targetFile = new File(pathName, fileName1);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			bookSpecial.setLogo(fileName1);

			// 保存
			try {
				file1.transferTo(targetFile);

				File deleteFile = new File(Keys.USER_PIC_PATH + "special/"
						+ request.getParameter("oldLogo"));

				if (!"".equals(request.getParameter("oldLogo"))
						&& request.getParameter("oldLogo") != null) {
					deleteFile.delete();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			if (!"".equals(request.getParameter("logo"))
					&& request.getParameter("logo") != null) {
				bookSpecial.setLogo(request.getParameter("logo"));
			} else {
				bookSpecial.setLogo("");
				File deleteFile = new File(Keys.USER_PIC_PATH + "special/"
						+ request.getParameter("oldLogo"));
				deleteFile.delete();
			}
		}

		String maxSort = this.categoryService.searchMaxSort();
		if (bookSpecial.getId() == null) {
			bookSpecial.setSort(new Integer(maxSort) + 1);
		} else {
			bookSpecial.setSort(new Integer(request.getParameter("sort")));
		}

		this.categoryService.saveSpecial(bookSpecial);
		String categoryIds = request.getParameter("tempIds");
		if (!"".equals(categoryIds) && categoryIds != null) {
			String[] tempIds = categoryIds.split(",");
			this.categoryService.deleteSpecialInfo(bookSpecial.getId()
					.toString());
			if (tempIds != null) {
				SpecialInfo bookSpecialInfo = new SpecialInfo();
				for (int i = 0; i < tempIds.length; i++) {

					bookSpecialInfo = new SpecialInfo();
					bookSpecialInfo.setCategoryId(tempIds[i]);
					bookSpecialInfo.setSpecialId(bookSpecial.getId());
					this.categoryService.saveSpecialInfo(bookSpecialInfo);
				}
			}
		}

		return "redirect:specialManager?topicType="+request.getParameter("topicType")+"";
		
	}
	
	@RequestMapping("/saveMallSpecial")
	public String saveMallSpecial(
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			HttpServletRequest request, Special bookSpecial) {
		// 保存圖片
		if (file1.getSize() != 0) {
			String fileName1 = "";
			fileName1 = file1.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH + "special/");
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			int random = (int) (Math.random() * 1000000);
			fileName1 = new Date().getTime()
					+ random
					+ ""
					+ fileName1.subSequence(fileName1.indexOf("."),
							fileName1.length());

			String pathName = Keys.USER_PIC_PATH + "special/";
			File targetFile = new File(pathName, fileName1);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			bookSpecial.setLogo(fileName1);

			// 保存
			try {
				file1.transferTo(targetFile);

				File deleteFile = new File(Keys.USER_PIC_PATH + "special/"
						+ request.getParameter("oldLogo"));

				if (!"".equals(request.getParameter("oldLogo"))
						&& request.getParameter("oldLogo") != null) {
					deleteFile.delete();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			if (!"".equals(request.getParameter("logo"))
					&& request.getParameter("logo") != null) {
				bookSpecial.setLogo(request.getParameter("logo"));
			} else {
				bookSpecial.setLogo("");
				File deleteFile = new File(Keys.USER_PIC_PATH + "special/"
						+ request.getParameter("oldLogo"));
				deleteFile.delete();
			}
		}

		String maxSort = this.categoryService.searchMaxSort();
		if (bookSpecial.getId() == null) {
			bookSpecial.setSort(new Integer(maxSort) + 1);
		} else {
			bookSpecial.setSort(new Integer(request.getParameter("sort")));
		}

		this.categoryService.saveSpecial(bookSpecial);
		String categoryIds = request.getParameter("tempIds");
		if (!"".equals(categoryIds) && categoryIds != null) {
			String[] tempIds = categoryIds.split(",");
			this.categoryService.deleteSpecialInfo(bookSpecial.getId()
					.toString());
			if (tempIds != null) {
				SpecialInfo bookSpecialInfo = new SpecialInfo();
				for (int i = 0; i < tempIds.length; i++) {

					bookSpecialInfo = new SpecialInfo();
					bookSpecialInfo.setCategoryId(tempIds[i]);
					bookSpecialInfo.setSpecialId(bookSpecial.getId());
					this.categoryService.saveSpecialInfo(bookSpecialInfo);
				}
			}
		}
		return "redirect:specialMallManager?topicType="+request.getParameter("topicType")+"";
	}

	@RequestMapping("/removeSpecial")
	public String removeSpecial(HttpServletRequest request) {

		this.categoryService.deleteSpecial(request.getParameter("specialId"));
		this.categoryService.deleteSpecialInfo(request
				.getParameter("specialId"));
		return "redirect:specialManager?topicType="+request.getParameter("topicType")+"";
	}
	
	@RequestMapping("/removeMallSpecial")
	public String removeMallSpecial(HttpServletRequest request) {
		this.categoryService.deleteSpecial(request.getParameter("specialId"));
		this.categoryService.deleteSpecialInfo(request
				.getParameter("specialId"));
		return "redirect:specialMallManager?topicType="+request.getParameter("topicType")+"";
	}


	@RequestMapping(value = "/getBook")
	public void getUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List booksList = this.bookService.searchBooks(request
				.getParameter("name"));

		JSONObject jobj = new JSONObject();

		JSONObject jsonObj = new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		if (booksList.size() > 0) {

			Object[] obj = (Object[]) booksList.get(0);
			jsonObj.put("cateId", obj[0]);
			jsonObj.put("name", obj[1]);
			jsonObj.put("author", obj[2]);
			List jsonLlist = new ArrayList();
			jsonLlist.add(jsonObj);
			jobj.put("infoList", jsonLlist);
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jobj.toString());

	}

	public static void main(String[] args) throws Exception {

		String fileName1 = "http://wechat.fandoutech.com.cn/wechat/wechatImages/book/barCode/1458200672945.png";

		//System.out.println(fileName1.subSequence(fileName1.lastIndexOf("/"),fileName1.length()));

	}

	@RequestMapping("/bookMobileView")
	public String bookMobileView(HttpServletRequest request, QueryDto queryDto) {

		Object[] category = this.bookService.searchCateGoryInfo(request
				.getParameter("cateId"));
		List jsonList = new ArrayList();
		if (category[16] != null) {
			String[] tempStr = category[16].toString().split(",");
			List commentList = new ArrayList();
			for (int i = 0; i < tempStr.length; i++) {

				Object[] comment = new Object[5];
				comment[0] = tempStr[i].toString().split(">")[0].toString();
				comment[1] = tempStr[i].toString().split(">")[1].toString();
				comment[2] = tempStr[i].toString().split(">")[2].toString();
				comment[3] = tempStr[i].toString().split(">")[3].toString()
						.subSequence(0, 10);
				commentList.add(comment);
			}

			request.setAttribute("commentList", commentList);
		}

		request.setAttribute("category", category);
		// request.setAttribute("jsonStr", jsonObj.toString());

		return "book/bookMobileView";
	}

	@RequestMapping({ "/labelManager" })
	public String labelManager(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		HashMap map = new HashMap();

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("name", queryDto.getName());

		Page resultPage = this.bookService.searchLabel(map);
		List<Label> infoList = resultPage.getItems();

		List jsonList = new ArrayList();

		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Label label : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", label.getId());
				jobj.put("name", label.getName());

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
		}

		request.setAttribute("jsonStr", jsonObj.toString());
		return "book/labelManager";
	}

	@RequestMapping({ "/saveLabel" })
	public String saveLabel(HttpServletRequest request, Label label) {

		this.bookService.saveLabel(label);
		return "redirect:labelManager";
	}

	@RequestMapping({ "/deleteLabel" })
	public String deleteLabel(HttpServletRequest request,
			HttpServletResponse response) {
		this.bookService.deleteLabel(request.getParameter("laberId"));

		return "redirect:labelManager";
	}

	@RequestMapping({ "/updateSpecialSort" })
	public String updateSpecialSort(HttpServletRequest request,
			HttpServletResponse response) {

	
		
		HashMap map = new HashMap();
		map.put("topicType", "0");
		map.put("page", "1");
		map.put("rowsPerPage", "100");

		Page resultPage = this.categoryService.searchSpecial(map);
		List list = resultPage.getItems();
		for (int i = 0; i < list.size(); i++) {
			map.put(((Object[]) list.get(i))[4].toString(),
					((Object[]) list.get(i))[0].toString());
		}

		String orderId = request.getParameter("specialId");
		String tempId = "";
		Integer sort = new Integer(request.getParameter("tempSort"));
		String type = request.getParameter("type");
		if ("0".equals(type)) {
			sort = Integer.valueOf(sort.intValue() - 1);
			if (map.get(sort.toString()) != null) {
				tempId = map.get(sort.toString()).toString();
				this.categoryService.updateSpecialSort(tempId,
						request.getParameter("tempSort"));
				this.categoryService
						.updateSpecialSort(orderId, sort.toString());
			}
		}
		if ("1".equals(type)) {
			sort = Integer.valueOf(sort.intValue() + 1);
			if (map.get(sort.toString()) != null) {
				tempId = map.get(sort.toString()).toString();
				this.categoryService.updateSpecialSort(tempId,
						request.getParameter("tempSort"));
				this.categoryService
						.updateSpecialSort(orderId, sort.toString());
			}

		}
		
		return "redirect:specialManager?topicType="+request.getParameter("topicType")+"";
	}
	
	@RequestMapping({ "/updateSpecialMallSort" })
	public String updateSpecialMallSort(HttpServletRequest request,
			HttpServletResponse response) {

		HashMap map = new HashMap();
		map.put("topicType", "1");
		map.put("page", "1");
		map.put("rowsPerPage","100");

		Page resultPage = this.categoryService.searchSpecial(map);
		List list = resultPage.getItems();
		
		for (int i = 0; i < list.size(); i++) {
			map.put(((Object[]) list.get(i))[4].toString(),
					((Object[]) list.get(i))[0].toString());
		}

		String orderId = request.getParameter("specialId");
		String tempId = "";
		Integer sort = new Integer(request.getParameter("tempSort"));
		String type = request.getParameter("type");
		if ("0".equals(type)) {
			sort = Integer.valueOf(sort.intValue() - 1);
			if (map.get(sort.toString()) != null) {
				tempId = map.get(sort.toString()).toString();
				this.categoryService.updateSpecialSort(tempId,
						request.getParameter("tempSort"));
				this.categoryService
						.updateSpecialSort(orderId, sort.toString());
			}
		}
		if ("1".equals(type)) {
			sort = Integer.valueOf(sort.intValue() + 1);
			if (map.get(sort.toString()) != null) {
				tempId = map.get(sort.toString()).toString();
				this.categoryService.updateSpecialSort(tempId,
						request.getParameter("tempSort"));
				this.categoryService
						.updateSpecialSort(orderId, sort.toString());
			}

		}
		
		return "redirect:specialMallManager?topicType="+request.getParameter("topicType")+"";
	}

	@RequestMapping({ "/updateSpecialStatus" })
	public String updateSpecialStatus(HttpServletRequest request,
			HttpServletResponse response) {

		this.categoryService.updateSpecialStatus(
				request.getParameter("specialId"),
				request.getParameter("tempStatus"));

		return "redirect:specialManager?topicType="+request.getParameter("topicType")+"";
	}
	
	@RequestMapping({ "/updateSpecialMallStatus" })
	public String updateSpecialMallStatus(HttpServletRequest request,
			HttpServletResponse response) {

		this.categoryService.updateSpecialStatus(
				request.getParameter("specialId"),
				request.getParameter("tempStatus"));

		return "redirect:specialMallManager?topicType="+request.getParameter("topicType")+"";
	}

	@RequestMapping(value = "/getSpecial")
	public void getCoupons(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("status", "0");
		map.put("memberId", request.getParameter("memberId"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Page resultPage = this.categoryService.searchSpecial(map);

		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {

			for (int i = 0; i < infoList.size(); i++) {

				jsonObj = new JSONObject();
				jsonObj.put("id", infoList.get(i)[0]);
				jsonObj.put("title", infoList.get(i)[2]);
				jsonObj.put("logo", infoList.get(i)[1]);
				jsonList.add(jsonObj);
			}
			jsonObject.accumulate("infoList", jsonList);

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObject.toString());
	}

	@RequestMapping(value = "/getSpecialInfo")
	public void getSpecialInfo(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("status", "0");
		map.put("memberId", request.getParameter("memberId"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Page resultPage = this.categoryService.searchSpecial(map);

		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {

			for (int i = 0; i < infoList.size(); i++) {

				jsonObj = new JSONObject();
				jsonObj.put("id", infoList.get(i)[0]);
				jsonObj.put("title", infoList.get(i)[2]);
				jsonObj.put("logo", infoList.get(i)[1]);
				jsonList.add(jsonObj);
			}
			jsonObject.accumulate("infoList", jsonList);

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObject.toString());
	}

}
