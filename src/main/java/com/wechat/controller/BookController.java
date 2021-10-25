package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.qiniu.QiniuUtil;
import com.wechat.service.*;
import com.wechat.util.*;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
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
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("book")
public class BookController {

	@Resource
	private BookService bookService;
	@Resource
	private MemberService memberService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private MallProductService mallProductService;
	@Resource
	private IntegralService integralService;
	@Resource
	private BookOrderService bookOrderService;
	@Resource
	private AccountService accountService;
	@Resource
	private RedisService redisService;

	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public IntegralService getIntegralService() {
		return integralService;
	}

	public void setIntegralService(IntegralService integralService) {
		this.integralService = integralService;
	}

	public BookOrderService getBookOrderService() {
		return bookOrderService;
	}

	public void setBookOrderService(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}

	public MallProductService getMallProductService() {
		return mallProductService;
	}

	public void setMallProductService(MallProductService mallProductService) {
		this.mallProductService = mallProductService;
	}

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

	@RequestMapping("/bookManager")
	public String bookManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		List<RightCategory> rightList = this.bookService.getRightCategory();
		request.setAttribute("rightList", rightList);
		List<RightCategory> leftList = this.bookService.getLeftCategory();
		request.setAttribute("leftList", leftList);

		// HashMap categoryMap = new HashMap();
		// for (int i = 0; i < categoryList.size(); i++) {
		//
		// categoryMap.put(categoryList.get(i).getId().toString(),
		// categoryList.get(i).getName());
		// }

		map.put("name", queryDto.getName());
		map.put("author", queryDto.getAuthor());
		map.put("publish", queryDto.getPublish());
		map.put("rightType", queryDto.getType());
		map.put("leftType", queryDto.getStatus());

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		if (!"".equals(request.getParameter("catId"))
				&& request.getParameter("catId") != null) {
			int catId = Integer.parseInt(request.getParameter("catId"));
			// 父分类下的所有子分类
			String catIdChilds = bookService.getCategoryChildIds(
					"book_category", catId);
			String catIds = catId + "";
			if (!catIdChilds.equals("") && null != catIdChilds) {
				catIds = catId + "," + catIdChilds;
			}
			map.put("catIds", catIds);
		}

		Page resultPage = this.bookService.searchCategoryInfo(map);

		List jsonList = new ArrayList();
		List<Object[]> infoList = resultPage.getItems();
		String cateIds = "";
		for (int i = 0; i < infoList.size(); i++) {

			cateIds = cateIds + "'" + infoList.get(i)[0] + "',";
		}
		if (cateIds.length() > 0) {
			cateIds = cateIds.substring(0, cateIds.length() - 1);
		}
		map.put("cateIds", cateIds);

		List<Object[]> bookList = this.bookService.searchBookList(map);
		HashMap bookMap = new HashMap();
		for (int i = 0; i < bookList.size(); i++) {
			bookMap.put(bookList.get(i)[0].toString(), bookList.get(i));
		}

		// List tempLaberList = this.bookService.searchLabelInfo(cateIds);
		// HashMap LaberMap = new HashMap();
		// for (int i = 0; i < tempLaberList.size(); i++) {
		//
		// LaberMap.put(tempLaberList.get(i)[0].toString(),tempLaberList.get(i)[1]);
		// }
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] category : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("cateID", category[0]);
				jobj.put("name", category[1]);
				jobj.put("author", category[4]);
				jobj.put("publish", category[3]);
				jobj.put("price", category[2]);

				jobj.put("translator", category[5]);
				jobj.put("page", category[6]);
				jobj.put("remark", category[7]);
				jobj.put("cover", category[8]);
				jobj.put("content", category[9]);
				jobj.put("series", category[13]);

				jobj.put("status", category[19]);
				jobj.put("book_cate_id", category[20]);

				jobj.put("rightName", category[24]);
				jobj.put("leftName", category[22]);
				jobj.put("cataLog", category[16]);
				jobj.put("leftId", category[21]);
				jobj.put("rightId", category[12]);
				jobj.put("mp3", category[14]);
				jobj.put("testMp3", category[15]);
				jobj.put("label", category[25]);
				jobj.put("code", category[17]);
				jobj.put("mp3Type", category[18]);

				if (category[26] != null) {
					jobj.put("keyword", category[26]);
				} else {
					jobj.put("keyword", "");
				}

				if (bookMap.get(category[0].toString()) != null) {

					jobj.put("bookInfo",
							((Object[]) bookMap.get(category[0].toString()))[1]);

					if (((Object[]) bookMap.get(category[0].toString()))[2] == null) {
						jobj.put("count", "0");
					} else {
						jobj.put(
								"count",
								((Object[]) bookMap.get(category[0].toString()))[2]);
					}
				} else {
					jobj.put("bookInfo", "");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		List labelList = getLabelList();
		request.setAttribute("labelList", labelList);
		return "book/bookManager";
	}

	public List getLabelList() {
		List labelList = new ArrayList();
		if (this.redisService.exists(RedisKeys.REDIS_LABEL)) {
			labelList = this.redisService.getList(RedisKeys.REDIS_LABEL,
					Label.class);
		} else {
			HashMap map = new HashMap();
			map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "1000");
			map.put("type", "1");
			Page tempResult = this.bookService.searchLabel(map);

			labelList = tempResult.getItems();

			this.redisService.setList(RedisKeys.REDIS_LABEL, labelList,
					RedisKeys.ADMIN_TIME);
		}
		return labelList;
	}

	/**
	 * 书籍分类页面跳转
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping("/bookCategory")
	public String bookCategory(HttpServletRequest request, QueryDto queryDto) {
		if (!"".equals(request.getParameter("result"))
				&& request.getParameter("result") != null) {
			String result = new String(Base64Util.decode(request
					.getParameter("result")));
			queryDto.setResult(result);
		}
		request.setAttribute("queryDto", queryDto);

		return "book/bookCategory";
	}

	/**
	 * 书籍分类Json数据
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("/bookCategoryView")
	public void bookCategoryView(HttpServletRequest request,
			HttpServletResponse response) {
		List cats = null;
		if (!"".equals(request.getParameter("currentId"))
				&& request.getParameter("currentId") != null) {
			int currentId = Integer.parseInt(request.getParameter("currentId"));
			cats = bookService.getBookCategoryNolevel("book_category", 0, 0,
					currentId, new ArrayList());
		} else {
			cats = bookService.getBookCategoryNolevel("book_category", 0, 0,
					-1, new ArrayList());
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("infoList", cats);
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(jsonObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除书籍商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/deleteBookCate")
	public String deleteBookCate(HttpServletRequest request,
			HttpServletResponse response) {
		int catId = Integer.parseInt(request.getParameter("cat_id"));
		// 父分类下的所有子分类
		String catIdChilds = bookService.getCategoryChildIds("book_category",
				catId);
		String catIds = catId + "";
		if (!catIdChilds.equals("") && null != catIdChilds) {
			catIds = catId + "," + catIdChilds;
		}
		HashMap map = new HashMap();
		map.put("catIds", catIds);
		map.put("page", "1");
		map.put("rowsPerPage", "1000");
		// 父分类下面的所有商品
		Page proCats = bookService.searchCategoryInfo(map);
		String result = "";
		// 该分类下面没有子分类，且没有商品的可以删除
		if (!"".equals(catIdChilds) || proCats.getItems().size() > 0) {
			result = "该分类不是末级分类或者分类下还存在商品，您不能删除";
		} else {
			this.bookService.deleteBookCategory(catId);
			result = "该分类已成功删除";
		}
		result = Base64Util.encode(result.getBytes());

		this.redisService.del(RedisKeys.APP_BOOK_INDEX);
		return "redirect:bookCategory?result=" + result + "";
	}

	/**
	 * 添加商品
	 * 
	 * @param request
	 * @param queryDto
	 * @return
	 */
	@RequestMapping(value = "/saveBookCate")
	public String saveBookCate(HttpServletRequest request,
			HttpServletResponse response) {
		int catId = 0;
		String catName = "";
		String uniqueId = "";
		String keywords = "";
		String parentId = "";
		String description = "";
		String sort = "";
		if (!"".equals(request.getParameter("catId"))
				&& null != request.getParameter("catId")) {
			catId = new Integer(request.getParameter("catId"));
		}
		if (!"".equals(request.getParameter("catName"))
				&& null != request.getParameter("catName")) {
			catName = request.getParameter("catName");
		}
		if (!"".equals(request.getParameter("uniqueId"))
				&& null != request.getParameter("uniqueId")) {
			uniqueId = request.getParameter("uniqueId");
		}
		if (!"".equals(request.getParameter("keywords"))
				&& null != request.getParameter("keywords")) {
			keywords = request.getParameter("keywords");
		}
		if (!"".equals(request.getParameter("parentId"))
				&& null != request.getParameter("parentId")) {
			parentId = request.getParameter("parentId");
		}
		if (!"".equals(request.getParameter("description"))
				&& null != request.getParameter("description")) {
			description = request.getParameter("description");
		}
		if (!"".equals(request.getParameter("sort"))
				&& null != request.getParameter("sort")) {
			sort = request.getParameter("sort");
		}

		BookCategory bookCategory = new BookCategory();
		if (catId == 0) {
			bookCategory.setCat_id(null);
		} else {
			bookCategory.setCat_id(catId);
		}

		bookCategory.setCat_name(catName);
		bookCategory.setDescription(description);
		bookCategory.setKeywords(keywords);
		bookCategory.setParent_id(Integer.parseInt(parentId));
		bookCategory.setSort(Integer.parseInt(sort));
		bookCategory.setUnique_id(uniqueId);
		bookService.saveBookCategory(bookCategory);
		this.redisService.del(RedisKeys.APP_BOOK_INDEX);
		return "redirect:bookCategory";
	}

	@RequestMapping("/saveCategory")
	public String saveCategory(
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2,
			@RequestParam(value = "file3", required = false) MultipartFile file3,
			HttpServletRequest request, Category category) {

		String bookType = "";
		String bookTypeNumber = "";
		if (category.getRightId() <= 3) {
			bookType = "1";
			bookTypeNumber = "C";
		} else {
			bookType = "2";
			bookTypeNumber = "E";
		}
		if (file1.getSize() != 0) {
			String fileName1 = "";
			fileName1 = file1.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH + "book/");
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			int random = (int) (Math.random() * 1000000);
			fileName1 = new Date().getTime()
					+ random
					+ ""
					+ fileName1.subSequence(fileName1.indexOf("."),
							fileName1.length());

			String pathName = Keys.USER_PIC_PATH + "book/";
			File targetFile = new File(pathName, fileName1);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// category.setCover(fileName1);

			// 保存
			try {
				file1.transferTo(targetFile);
				String oldPic = request.getParameter("oldLogo1");
				File deleteFile = new File(Keys.USER_PIC_PATH + "book/"
						+ request.getParameter("oldLogo1"));

				if (!"".equals(request.getParameter("oldLogo1"))
						&& request.getParameter("oldLogo1") != null) {
					deleteFile.delete();
					QiniuUtil.delFile(Keys.QINIU_IMAGE,
							oldPic.substring(oldPic.lastIndexOf("/") + 1));// 删除七牛旧文件
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			category.setCover(QiniuUtil.addFile(pathName, fileName1,
					Keys.QINIU_IMAGE));

		} else {

			if (!"".equals(request.getParameter("cover1"))
					&& request.getParameter("cover1") != null) {
				category.setCover(request.getParameter("cover1"));
			} else {
				category.setCover("");
				File deleteFile = new File(Keys.USER_PIC_PATH + "book/"
						+ request.getParameter("oldLogo1"));
				deleteFile.delete();
				QiniuUtil.delFile(
						Keys.QINIU_IMAGE,
						request.getParameter("cover1")
								.substring(
										request.getParameter("cover1")
												.lastIndexOf("/") + 1));// 删除七牛旧文件
			}
		}

		// if(file2.getSize()!=0)
		// {
		// String fileName2 = "";
		// fileName2 = file2.getOriginalFilename();
		// File fileDir = new File(Keys.USER_PIC_PATH +"book/mp3");
		// if (!fileDir.exists())
		// {
		// fileDir.mkdir();
		// }
		// int random=(int)(Math.random()*1000000);
		// //fileName2 = new Date().getTime() + random + "" +
		// fileName2.subSequence(fileName2.indexOf("."), fileName2.length());
		//
		// String pathName = Keys.USER_PIC_PATH +"book/mp3/";
		// File targetFile = new File(pathName, fileName2);
		// if(!targetFile.exists()){
		// targetFile.mkdirs();
		// }
		// category.setTestMp3(fileName2);
		//
		//
		// //保存
		// try {
		// file2.transferTo(targetFile);
		//
		// File deleteFile = new File(Keys.USER_PIC_PATH + "book/mp3/" +
		// request.getParameter("testMp3"));
		//
		// if(!"".equals(request.getParameter("testMp3")) &&
		// request.getParameter("testMp3")!= null)
		// {
		// deleteFile.delete();
		// }
		//
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		//
		// }

		if (file3.getSize() != 0) {
			String fileName3 = "";
			fileName3 = file3.getOriginalFilename();
			File fileDir = new File(Keys.USER_PIC_PATH + "book/mp3");
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			int random = (int) (Math.random() * 1000000);
			// fileName3 = new Date().getTime() + random + "" +
			// fileName3.subSequence(fileName3.indexOf("."),
			// fileName3.length());

			String pathName = Keys.USER_PIC_PATH + "book/mp3/";
			File targetFile = new File(pathName, fileName3);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// category.setMp3(fileName3);

			// 保存
			try {
				file3.transferTo(targetFile);

				File deleteFile = new File(Keys.USER_PIC_PATH + "book/mp3/"
						+ request.getParameter("oldMp3"));

				if (!"".equals(request.getParameter("oldMp3"))
						&& request.getParameter("oldMp3") != null) {
					deleteFile.delete();
					QiniuUtil.delFile(
							Keys.QINIU_IMAGE,
							request.getParameter("oldMp3").substring(
									request.getParameter("oldMp3").lastIndexOf(
											"/") + 1));// 删除七牛旧文件
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			category.setMp3(QiniuUtil.addFile(pathName, fileName3,
					Keys.QINIU_IMAGE));

		}
		Integer cateId = this.bookService.getCateId(bookTypeNumber);
		cateId++;
		DecimalFormat df = new DecimalFormat("0000");
		String tempCateId = df.format(cateId);
		if (!"".equals(category.getCateID()) && category.getCateID() != null) {

			this.bookService.updateCateGory(category);

		} else {
			if (!"".equals(request.getParameter("tempCateId"))
					&& request.getParameter("tempCateId") != null) {
				category.setCateID(request.getParameter("tempCateId"));
			} else {
				category.setCateID(bookTypeNumber + tempCateId);
			}

			this.bookService.saveCategory(category);

			Book book = new Book();
			Integer bookCateId = this.bookService.getBookCateId(category
					.getCateID());
			String tempBookCateId = "";

			String picPath = Keys.USER_PIC_PATH + "/book/barCode/";
			int random = 0;
			String pathName = "";
			String addPatName = "";
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode();

			for (int i = 0; i < category.getCount(); i++) {

				book = new Book();
				bookCateId++;
				tempBookCateId = df.format(bookCateId);
				book.setCateId(category.getCateID());
				book.setBarCode(category.getCateID() + tempBookCateId);
				this.bookService.saveBook(book);

				random = (int) (Math.random() * 1000000);
				pathName = new Date().getTime() + random + ".png";
				addPatName = picPath + pathName;

				String codeValue = "";
				if ("1".equals(bookType)) {
					codeValue = codeValue + "0";
				}
				if ("2".equals(bookType)) {
					codeValue = codeValue + "1";
				}
				if ("3".equals(bookType)) {
					codeValue = codeValue + "1";
				}

				// codeValue =
				// "["+book.getBarCode()+"]["+category.getbName()+"]["+codeValue+"][凡豆书院]";
				codeValue = "[" + book.getBarCode() + "]["
						+ category.getbName() + "]";
				book.setCodeInfo(codeValue);
				book.setUrl(Keys.STAT_NAME
						+ "/wechat/wechatImages/book/barCode" + pathName);
				this.bookService.updateBookInfo(book);
				twoDimensionCode.encoderQRCode(codeValue, addPatName, "png");
			}
		}

		String tempKeyword = request.getParameter("tempName");
		if (!"".equals(tempKeyword) && tempKeyword != null) {
			this.categoryService.deleteBookKeyword(category.getCateID());
			String[] keyword = tempKeyword.split(",");
			BookKeyword bookKeyword = new BookKeyword();
			for (int i = 0; i < keyword.length; i++) {

				if (keyword[i] != null) {
					bookKeyword = new BookKeyword();
					bookKeyword.setCateId(category.getCateID());
					bookKeyword.setName(keyword[i]);
					this.categoryService.saveBookKeyword(bookKeyword);
				}
			}
		} else {
			this.categoryService.deleteBookKeyword(category.getCateID());
		}

		String labelIds = request.getParameter("labelId");
		String[] tempIds = labelIds.split(",");
		this.bookService.deleteCategoryLabel(category.getCateID().toString());
		if (!"".equals(labelIds) && labelIds != null) {
			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "20");

			List<Label> labelList = this.getLabelList();

			HashMap labelMap = new HashMap();

			for (int i = 0; i < labelList.size(); i++) {
				labelMap.put(labelList.get(i).getName().toString(), labelList
						.get(i).getId().toString());
			}

			CategoryLabel categoryLabel = new CategoryLabel();
			//System.out.println(tempIds.length);
			for (int i = 0; i < tempIds.length; i++) {

				categoryLabel = new CategoryLabel();
				categoryLabel.setCategoryId(category.getCateID());
				if (tempIds[i] != null) {
					if (labelMap.get(tempIds[i].toString()) != null) {
						categoryLabel.setLabelId(new Integer(labelMap.get(
								tempIds[i].toString()).toString()));
						this.bookService.saveLabelInfo(categoryLabel);

					}
				}

			}
		}

		this.redisService.del(RedisKeys.APP_BOOK_INDEX);
		this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookManager";
	}

	@RequestMapping("/removeCategory")
	public String removeCategory(HttpServletRequest request) {

		this.bookService.deleteCateGory(request.getParameter("cateId"));
		File deleteFile = new File(Keys.USER_PIC_PATH + "book/"
				+ request.getParameter("cover"));
		deleteFile.delete();
		this.redisService.del(RedisKeys.APP_BOOK_INDEX);
		this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookManager";
	}

	@RequestMapping("/removeBook")
	public String removeBook(HttpServletRequest request) {

		this.bookService.deleteBook(request.getParameter("barcode"));

		return "redirect:bookManager";
	}

	@RequestMapping(value = "/removeBookByAjax")
	public void removeBookByAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		try {

			Book book = this.bookService.getBook(request
					.getParameter("barcode"));

			String url = book.getUrl();
			if (!"".equals(url) && url != null) {
				File deleteFile = new File(Keys.USER_PIC_PATH + "book/barCode/"
						+ url.subSequence(url.lastIndexOf("/"), url.length()));
				// //System.out.println(Keys.USER_PIC_PATH + "book/barCode/" +
				// url.subSequence(url.lastIndexOf("/"), url.length()));
				deleteFile.delete();
			}

			this.bookService.deleteBook(request.getParameter("barcode"));
		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(value = "/saveBookByAjax")
	public void saveBookByAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		try {
			String cateId = request.getParameter("cateId");

			String maxBookId = this.bookService.getMaxBookCateId(cateId,
					cateId.substring(0, 1));
			DecimalFormat df = new DecimalFormat("0000");
			Integer maxId = 0;
			if ("0".equals(maxBookId)) {
				maxId = 1;
			} else {
				maxId = new Integer(maxBookId.substring(1, maxBookId.length())) + 1;
			}

			Book book = new Book();
			book.setCateId(cateId);
			book.setIsexist(1);
			book.setBarCode(cateId + df.format(maxId));
			this.bookService.saveBook(book);

			String picPath = Keys.USER_PIC_PATH + "/book/barCode/";
			int random = (int) (Math.random() * 1000000);
			String pathName = new Date().getTime() + random + ".png";
			String addPathName = picPath + pathName;
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode();

			String codeValue = cateId.substring(0, 5);
			HashMap map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "10");
			map.put("cateId", cateId);
			Object[] category = null;
			Page resultPage = this.bookService.searchCategoryInfo(map);
			category = (Object[]) resultPage.getItems().get(0);

			codeValue = "[" + book.getBarCode() + "][" + category[1].toString()
					+ "][" + codeValue + "][凡豆书院]";
			twoDimensionCode.encoderQRCode(codeValue, addPathName, "png");
			book.setCodeInfo(codeValue);
			book.setUrl(Keys.STAT_NAME + "wechat/wechatImages/book/barCode/"
					+ pathName);
			this.bookService.updateBookInfo(book);
			JSONObject jobj = new JSONObject();
			jobj.put("infoList", book);
			jsonStr = jobj.toString();

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
	}

	public static void main(String[] args) throws Exception {

		String fileName1 = "winnie's new ,computer";
		//System.out.println(fileName1.replaceAll("'", " ").replaceAll(",", " "));

		// //System.out.println(fileName1.subSequence(fileName1.lastIndexOf("/"),
		// fileName1.length()));

	}

	@RequestMapping("/bookMobileManager")
	public String bookMobileManager(HttpServletRequest request,
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

		request.getSession().setAttribute("memberId",
				request.getParameter("memberId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "15");
		map.put("labelId", "1");
		Page resultPage = this.categoryService.searchBooksByLabel(map);
		List tempList = resultPage.getItems();
		List fengmianList1 = new ArrayList();
		List fengmianList2 = new ArrayList();
		List fengmianList3 = new ArrayList();
		List fengmianList4 = new ArrayList();
		List fengmianList5 = new ArrayList();
		for (int i = 0; i < tempList.size(); i++) {

			if (i <= 2) {
				fengmianList1.add(tempList.get(i));
			}
			if (i >= 3 && i <= 5) {
				fengmianList2.add(tempList.get(i));
			}
			if (i >= 6 && i <= 8) {
				fengmianList3.add(tempList.get(i));
			}
			if (i >= 9 && i <= 11) {
				fengmianList4.add(tempList.get(i));
			}
			if (i >= 12 && i <= 14) {
				fengmianList5.add(tempList.get(i));
			}

		}
		request.setAttribute("fengmianList1", fengmianList1);
		request.setAttribute("fengmianList2", fengmianList2);
		request.setAttribute("fengmianList3", fengmianList3);
		request.setAttribute("fengmianList4", fengmianList4);
		request.setAttribute("fengmianList5", fengmianList5);

		List<Object[]> labelList = this.bookService.searchBookLabel();
		StringBuffer labelIds = new StringBuffer("");
		List<MallInfoList> bookInfoList = new ArrayList();

		if (labelList.size() > 0) {
			MallInfoList mallInfoList = new MallInfoList();

			for (int i = 0; i < labelList.size(); i++) {

				mallInfoList = new MallInfoList();
				mallInfoList.setId(labelList.get(i)[0].toString());
				mallInfoList.setTitle(labelList.get(i)[1].toString());
				mallInfoList.setMallProductList(this.bookService
						.searchBookByLabel(labelList.get(i)[0].toString()));

				bookInfoList.add(mallInfoList);
			}

		}
		request.setAttribute("bookInfoList", bookInfoList);

		return "book/bookMobileManager";
	}

	@RequestMapping("/toBookMobileByLabel")
	public String toBookMobileByLabel(HttpServletRequest request,
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

		request.setAttribute("title", request.getParameter("title"));
		map.put("labelId", request.getParameter("labelId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		DecimalFormat df = new DecimalFormat("###.0");
		Page resultPage = this.categoryService.searchBooksByLabel(map);
		List<Object[]> infoList = resultPage.getItems();

		for (int i = 0; i < infoList.size(); i++) {

			infoList.get(i)[3] = Keys.STAT_NAME + "wechat/wechatImages/book/"
					+ infoList.get(i)[3];

			if (infoList.get(i)[10] != null && !"".equals(infoList.get(i)[10])) {

				infoList.get(i)[5] = df.format(new Double(infoList.get(i)[10]
						.toString())
						/ new Double(infoList.get(i)[12].toString()));
				infoList.get(i)[6] = df.format(new Double(infoList.get(i)[11]
						.toString())
						/ new Double(infoList.get(i)[12].toString()));
				infoList.get(i)[7] = "";

				if ("5".equals(infoList.get(i)[6].toString())) {
					infoList.get(i)[7] = "<li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li>";
				}
				if ("4".equals(infoList.get(i)[6].toString())) {
					infoList.get(i)[7] = "<li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li><li></li>";
				}

				if ("3".equals(infoList.get(i)[6].toString())) {
					infoList.get(i)[7] = "<li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li><li class='active'></li><li></li><li></li>";
				}

				if ("2".equals(infoList.get(i)[6].toString())) {
					infoList.get(i)[7] = "<li class='active'></li><li class='active'></li><li></li><li></li><li></li>";
				}

				if ("1".equals(infoList.get(i)[6].toString())) {
					infoList.get(i)[7] = "<li class='active'></li><li></li><li></li><li></li><li></li>";
				}

			} else {
				infoList.get(i)[5] = "0";
				infoList.get(i)[7] = "<li></li><li></li><li></li><li></li><li></li>";
			}

		}
		request.setAttribute("infoList", infoList);
		return "book/bookMobileByLabel";
	}

	@RequestMapping("/bookMobileByLabelAjax")
	public String bookMobileByLabel(HttpServletRequest request,
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

		map.put("labelId", request.getParameter("labelId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		Page resultPage = this.categoryService.searchBooksByLabel(map);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		DecimalFormat df = new DecimalFormat("###.0");

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] category : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("cateId", category[0]);
				jobj.put("name", category[1]);
				jobj.put("author", category[2]);
				jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/"
						+ category[3]);
				jobj.put("content", category[4]);

				if (category[10] != null && !"".equals(category[10])) {

					jobj.put(
							"fraction",
							df.format(new Double(category[10].toString())
									/ new Double(category[12].toString())));
					jobj.put(
							"star",
							df.format(new Double(category[11].toString())
									/ new Double(category[12].toString())));

				} else {
					jobj.put("fraction", "");
					jobj.put("star", "");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());

		return null;
	}

	@RequestMapping("/bookMobileView")
	public String bookMobileView(HttpServletRequest request, QueryDto queryDto) {

		request.setAttribute("memberId", request.getParameter("memberId"));

		Object[] category = this.bookService.searchCateGoryInfo(request
				.getParameter("cateId"));
		List jsonList = new ArrayList();
		if (category[18] != null) {
			String[] tempStr = category[18].toString().split(",");
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
		if (category[20] != null) {

			String[] tempLabel = category[20].toString().split(",");
			List list = new ArrayList();
			Object obj = new Object();
			for (int i = 0; i < tempLabel.length; i++) {

				obj = new Object();
				obj = tempLabel[i];
				list.add(obj);
			}
			request.setAttribute("labelList", list);
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
		map.put("type", queryDto.getType());

		Page resultPage = this.bookService.searchLabel(map);
		request.setAttribute("resultPage", resultPage);
		List<Label> infoList = resultPage.getItems();
		request.setAttribute("labelList", infoList);
		List jsonList = new ArrayList();

		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Label label : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", label.getId());
				jobj.put("name", label.getName());
				jobj.put("type", label.getType());

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

	@RequestMapping(value = "/saveBookVehicle")
	public String saveBookVehicle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BookVehicle bookVehicle = new BookVehicle();
		bookVehicle.setCateId(request.getParameter("cateId"));
		bookVehicle.setMemberId(new Integer(request.getParameter("memberId")));

		List<Object[]> list = this.bookService.searchaBookVehicle(request
				.getParameter("memberId"));
		HashMap map = new HashMap();
		for (int i = 0; i < list.size(); i++) {

			map.put(list.get(i)[0].toString(), list.get(i));
		}

		if (map.get(bookVehicle.getCateId().toString()) == null) {

			this.bookService.saveBookVehicle(bookVehicle);
		}

		response.setContentType("application/json;charset=utf-8");
		return null;
	}

	@RequestMapping(value = "/bookVehicleManager")
	public String bookVehicleManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if ("".equals(request.getParameter("memberId"))
				|| request.getParameter("memberId") == null) {
			return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toBookVehicle#wechat_redirect";

		}
		List<Object[]> list = this.bookService.searchaBookVehicle(request
				.getParameter("memberId"));
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i)[3] != null) {
				if (list.get(i)[3].toString().length() >= 35) {
					list.get(i)[3] = list.get(i)[3].toString().substring(0, 35)
							+ "...";
				}
			}

		}
		request.setAttribute("tempList", list);
		request.setAttribute("memberId", request.getParameter("memberId"));

		return "book/bookVehicle";
	}

	@RequestMapping(value = "/deleteShoppingCart")
	public String deleteShoppingCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!"".equals(request.getParameter("ids"))
				&& request.getParameter("ids") != null) {
			this.bookService.deleteBookVehicle(request.getParameter("ids"));

		}

		return "redirect:bookVehicleManager?memberId="
				+ request.getParameter("memberId");
	}

	@RequestMapping(value = "/saveBookLabel")
	public String saveBookLabel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String labelIds = request.getParameter("tempLabelIds");
		HashMap map = new HashMap();
		String[] tempLabelIds = labelIds.split(",");
		for (int i = 0; i < tempLabelIds.length; i++) {

			map.put(tempLabelIds[i].toString(), "");
		}
		BookLabel bookLabel = new BookLabel();
		String ids = request.getParameter("labelId");
		if (!"".equals(ids) && ids != null) {
			String[] tempIds = ids.split(",");

			for (int i = 0; i < tempIds.length; i++) {

				bookLabel = new BookLabel();
				bookLabel.setLabelId(new Integer(tempIds[i]));
				if (map.get(tempIds[i].toString()) == null) {
					this.bookService.saveBookLabel(bookLabel);
				}

			}
		}

		this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookLabelManager";
	}

	@RequestMapping("/bookLabelManager")
	public String bookLabelManager(HttpServletRequest request, QueryDto queryDto) {

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
		map.put("rowsPerPage", "20");

		map.put("name", queryDto.getName());

		Page resultPage = this.bookService.searchBookLabel(map);

		map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "100");
		Page labelPage = this.bookService.searchLabel(map);
		request.setAttribute("labelPage", labelPage);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		List tempList = new ArrayList();

		String labelIds = "";

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] orderService : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("id", orderService[0]);
				jobj.put("name", orderService[1]);
				jobj.put("ios", orderService[3]);
				jobj.put("android", orderService[4]);
				jobj.put("wechat", orderService[5]);
				if ("0".equals(orderService[3].toString())) {
					jobj.put("iosName", "<font color='green'>显示</font>");
				} else {
					jobj.put("iosName", "<font color='red'>不显示</font>");
				}

				if ("0".equals(orderService[4].toString())) {
					jobj.put("androidName", "<font color='green'>显示</font>");
				} else {
					jobj.put("androidName", "<font color='red'>不显示</font>");
				}

				if ("0".equals(orderService[5].toString())) {
					jobj.put("wechatName", "<font color='green'>显示</font>");
				} else {
					jobj.put("wechatName", "<font color='red'>不显示</font>");
				}

				labelIds = labelIds + orderService[2] + ",";
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("labelIds", labelIds);
		return "book/bookLabelManager";
	}

	@RequestMapping(value = "/updateBookLabel")
	public String updateBookLabel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String ios = "1";
		if (!"".equals(request.getParameter("ios"))
				&& request.getParameter("ios") != null) {
			ios = "0";
		}
		String wechat = "1";
		if (!"".equals(request.getParameter("wechat"))
				&& request.getParameter("wechat") != null) {
			wechat = "0";
		}
		String android = "1";
		if (!"".equals(request.getParameter("android"))
				&& request.getParameter("android") != null) {
			android = "0";
		}
		this.bookService.updateBookLabel(request.getParameter("id"), ios,
				wechat, android);

		this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookLabelManager";
	}

	@RequestMapping(value = "/deleteBookLabel")
	public String deleteMallLabel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.bookService.deleteBookLabel(request.getParameter("id"));
		this.redisService.del(RedisKeys.APP_BOOK_LABEL);
		return "redirect:bookLabelManager";
	}

	@RequestMapping(value = "/toSaveBookOrder")
	public String toSaveBookOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] ids = request.getParameter("ids").split(",");
		String[] bookCodes = request.getParameter("bookCodes").split(",");
		request.setAttribute("memberId", request.getParameter("memberId"));

		BookOrderInfo bookOrderInfo = new BookOrderInfo();
		List bookList = new ArrayList();
		for (int i = 0; i < ids.length; i++) {

			bookOrderInfo = new BookOrderInfo();
			bookOrderInfo.setBarCode(bookCodes[i]);
			bookOrderInfo.setCateId(ids[i]);
			bookOrderInfo.setCreateDate(new Date());
			bookList.add(bookOrderInfo);
		}

		request.getSession().setAttribute("bookList", bookList);

		List<Object[]> showBookList = this.bookService.searchBooksByIds(request
				.getParameter("cateIds"));

		BookOrder bookOrder = new BookOrder();
		for (int i = 0; i < showBookList.size(); i++) {

			bookOrder.setBookPrice(bookOrder.getBookPrice() + 4);
			bookOrder.setDeposit(bookOrder.getDeposit()
					+ new Double(showBookList.get(i)[4].toString()));

		}
		Object[] obj = this.mallProductService.searchMemberInfo(request
				.getParameter("memberId"));

		if (obj[3] != null) {
			bookOrder.setBookPrice(0);
			bookOrder.setDeposit(new Double(0));
			if ("0".equals(obj[3].toString())) {
				// jsonStr =
				// "{\"mp3Count\":\""+obj[1]+"\",\"collectionCount\":\""+obj[2]+"\",\"integralCount\":\""+integralCount+"\",\"memberType\":\"金卡会员\",\"endDate\":\""+obj[4].toString().substring(0,10)+"\"}";
				if (showBookList.size() < 20) {
					bookOrder.setFreight(10);
				} else if (showBookList.size() < 25) {
					bookOrder.setFreight(15);
				} else if (showBookList.size() < 30) {
					bookOrder.setFreight(20);
				}

				bookOrder
						.setTotalPrice(new Double(bookOrder.getFreight() + ""));

			} else {
				// jsonStr =
				// "{\"mp3Count\":\""+obj[1]+"\",\"collectionCount\":\""+obj[2]+"\",\"integralCount\":\""+integralCount+"\",\"memberType\":\"白金会员\",\"endDate\":\""+obj[4].toString().substring(0,10)+"\"}";

				bookOrder.setTotalPrice(new Double(0));

			}
		} else {
			if (showBookList.size() < 20) {
				bookOrder.setFreight(10);
			} else if (showBookList.size() < 25) {
				bookOrder.setFreight(15);
			} else if (showBookList.size() < 30) {
				bookOrder.setFreight(20);
			}

			bookOrder.setTotalPrice(bookOrder.getBookPrice()
					+ bookOrder.getDeposit()
					+ new Double(bookOrder.getFreight() + ""));

		}

		Member member = new Member();
		member.setId(new Integer(request.getParameter("memberId")));
		bookOrder.setMemberId(new Integer(request.getParameter("memberId")));
		member = this.memberService.getMember(member);
		request.setAttribute("member", member);
		UserAddress userAddress = new UserAddress();

		userAddress = this.mallProductService
				.getUserAddressBystatus(new Integer(request
						.getParameter("memberId")));
		if (userAddress.getId() != null) {
			bookOrder.setAddressId(userAddress.getId());
		}
		request.setAttribute("userAddress", userAddress);

		MemberAccount memberAccount = this.memberService
				.searchMemberAccountByMemberId(request.getParameter("memberId"));
		Integer integralCount = this.integralService.getIntegralCount(request
				.getParameter("memberId"), memberAccount.getType().toString());

		integralCount = integralCount / 100;
		if (integralCount > bookOrder.getTotalPrice()) {
			DecimalFormat df2 = new DecimalFormat("####");

			integralCount = new Integer(df2.format(bookOrder.getTotalPrice()));
		}
		bookOrder.setIntegral(integralCount);
		request.setAttribute("integralCount", integralCount);
		request.getSession().setAttribute("member", member);
		request.getSession().setAttribute("bookOrder", bookOrder);
		request.getSession().setAttribute("showBookList", showBookList);

		return "book/addBookOrder";
	}

	@RequestMapping(value = "/saveBookOrder")
	public String saveBookOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getSession().getAttribute("bookOrder") != null
				&& request.getSession().getAttribute("bookList") != null) {
			BookOrder bookOrder = (BookOrder) request.getSession()
					.getAttribute("bookOrder");
			bookOrder.setOrderNumber(request.getParameter("orderNumber"));
			bookOrder.setCreateDate(new Date());
			List<BookOrderInfo> bookList = (ArrayList) request.getSession()
					.getAttribute("bookList");

			MemberAccount memberAccount = this.memberService
					.searchMemberAccountByMemberId(bookOrder.getMemberId()
							.toString());
			Integral integral = new Integral();

			if ("1".equals(request.getParameter("integralStatus"))) {
				integral.setCreateDate(new Date());
				integral.setMemberId(bookOrder.getMemberId());
				integral.setStatus(1);
				integral.setFraction(-bookOrder.getIntegral() * 100);
				integral.setTypeId(3);
				integral.setMemberType(memberAccount.getType());
				integral.setRemark("书籍消费");
				this.integralService.saveIntegral(integral);// 消费积分
			}

			this.bookOrderService.saveBookOrder(bookOrder);

			for (int i = 0; i < bookList.size(); i++) {

				bookList.get(i).setOrderId(bookOrder.getId());
				this.bookOrderService.saveBookOrderInfo(bookList.get(i));
				this.bookService.updateBookStatus(bookList.get(i).getBarCode(),
						"0");
				this.bookOrderService.deleteBookvehicle(bookList.get(i)
						.getCateId(), bookOrder.getMemberId().toString());

			}

			return "redirect:memberBookInfo?memberId="
					+ bookOrder.getMemberId();

		} else {
			return "redirect:bookMobileManager";
		}

	}

	@RequestMapping("/searchBookCount")
	public String searchBookCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Object[]> infoList = this.bookService.searchBookCount(request
				.getParameter("ids"));
		List jsonList = new ArrayList();

		String labelIds = "";
		int count = 0;
		String ids = request.getParameter("ids");
		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] orderService : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("cateId", orderService[0]);
				jobj.put("bookName", orderService[1]);
				jobj.put("bookCode", orderService[2]);

				jsonList.add(jobj);

			}
			jsonObj.put("infoList", jsonList);

		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return null;
	}

	@RequestMapping(value = "/memberBookOrderManager")
	public String memberBookOrderManager(HttpServletRequest request,
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

		map.put("memberId", request.getParameter("memberId"));
		map.put("orderId", request.getParameter("orderId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		MallOrder mallOrder = new MallOrder();

		Page resultPage = this.bookOrderService.searchOrderInfo(map);

		BookOrderList bookOrderList = new BookOrderList();
		Object[] obj = new Object[9];
		String[] tempCate = null;
		String[] tempBook = null;
		Category category = new Category();
		List tempList = new ArrayList();
		List<BookOrderList> bookList = new ArrayList();
		for (int i = 0; i < resultPage.getItems().size(); i++) {

			bookOrderList = new BookOrderList();
			bookOrderList.setMemberId(new Integer(request
					.getParameter("memberId")));
			obj = (Object[]) resultPage.getItems().get(i);
			bookOrderList.setOrderNumber(obj[0].toString());
			bookOrderList.setTotalPrice(new Double(obj[3].toString()));
			bookOrderList.setStatus(new Integer(obj[2].toString()));
			if (obj[4] != null) {
				bookOrderList.setExpress(obj[4].toString());
			}
			if (obj[5] != null) {
				bookOrderList.setExpressNumber(obj[5].toString());
			}
			bookOrderList.setId(new Integer(obj[6].toString()));

			if (obj[7] != null) {
				tempCate = obj[7].toString().split(",");
				category = new Category();
				tempList = new ArrayList();
				for (int j = 0; j < tempCate.length; j++) {

					tempBook = tempCate[j].split("<");
					if (tempBook != null) {
						category = new Category();
						category.setAuthor(tempBook[3]);
						category.setbName(tempBook[1]);
						category.setCateID(tempBook[0]);
						category.setCover(Keys.STAT_NAME
								+ "/wechat/wechatImages/book/" + tempBook[2]);
						category.setPrice(new Double(tempBook[4]));
						category.setCode(tempBook[5]);
						category.setStatus(new Integer(tempBook[6]));
						tempList.add(category);

					}
					bookOrderList.setBookList(tempList);

				}

				bookOrderList.setCount(bookOrderList.getBookList().size());

			}
			bookList.add(bookOrderList);

		}
		request.setAttribute("bookList", bookList);

		return "book/memberBookOrderManager";

	}

	@RequestMapping({ "/memberBookInfo" })
	public String memberBookInfo(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		if ("".equals(request.getParameter("memberId"))
				|| request.getParameter("memberId") == null) {

			return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toBookInfo#wechat_redirect";

		}
		Member member = new Member();
		member.setId(new Integer(request.getParameter("memberId")));

		member = this.memberService.getMember(member);
		request.setAttribute("member", member);
		Object[] obj = this.mallProductService.searchMemberInfo(request
				.getParameter("memberId"));
		if (obj[3] != null) {
			if ("0".equals(obj[3].toString())) {
				request.setAttribute("memberType", "金卡会员");
			} else {
				request.setAttribute("memberType", "白金会员");

			}
		} else {
			request.setAttribute("memberType", "普通会员");
		}

		if (obj[5] != null) {
			request.setAttribute("bookCount", obj[5]);
		} else {
			request.setAttribute("bookCount", 0);
		}
		MemberAccount memberAccount = this.memberService
				.searchMemberAccountByMemberId(request.getParameter("memberId"));
		Integer integralCount = this.integralService.getIntegralCount(request
				.getParameter("memberId"), memberAccount.getType().toString());
		request.setAttribute("integralCount", integralCount);

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("memberId", request.getParameter("memberId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("status", "0");

		Page resultPage = this.bookOrderService.searchMemberBookInfo(map);

		request.setAttribute("bookList", resultPage.getItems());

		return "book/memberBookInfo";
	}

	@RequestMapping({ "/bookInfoManager" })
	public String bookInfoManager(HttpServletRequest request,
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

		map.put("memberId", request.getParameter("memberId"));
		map.put("orderId", request.getParameter("orderId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		MallOrder mallOrder = new MallOrder();

		Page resultPage = this.bookOrderService.searchOrderInfo(map);

		BookOrderList bookOrderList = new BookOrderList();
		Object[] obj = new Object[9];
		String[] tempCate = null;
		String[] tempBook = null;
		Category category = new Category();
		List tempList = new ArrayList();
		List<BookOrderList> bookList = new ArrayList();
		for (int i = 0; i < resultPage.getItems().size(); i++) {

			bookOrderList = new BookOrderList();
			bookOrderList.setMemberId(new Integer(request
					.getParameter("memberId")));
			obj = (Object[]) resultPage.getItems().get(i);
			bookOrderList.setOrderNumber(obj[0].toString());
			bookOrderList.setTotalPrice(new Double(obj[3].toString()));
			bookOrderList.setStatus(new Integer(obj[2].toString()));
			if (obj[4] != null) {
				bookOrderList.setExpress(obj[4].toString());
			}
			if (obj[5] != null) {
				bookOrderList.setExpressNumber(obj[5].toString());
			}
			bookOrderList.setId(new Integer(obj[6].toString()));

			if (obj[7] != null) {
				tempCate = obj[7].toString().split(",");
				category = new Category();

				for (int j = 0; j < tempCate.length; j++) {

					tempBook = tempCate[j].split("<");
					if (tempBook != null) {
						category = new Category();
						category.setAuthor(tempBook[3]);
						category.setbName(tempBook[1]);
						category.setCateID(tempBook[0]);
						category.setCover(Keys.STAT_NAME
								+ "/wechat/wechatImages/book/" + tempBook[2]);
						category.setPrice(new Double(tempBook[4]));
						category.setCode(tempBook[5]);
						category.setStatus(new Integer(tempBook[6]));
						tempList.add(category);

					}

				}

			}

		}
		request.setAttribute("bookList", tempList);

		return "book/bookInfoManager";
	}

	@RequestMapping("/toBookPayment")
	public String toBookPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("integralCount",
				request.getParameter("integralCount"));
		request.setAttribute("memberId", request.getParameter("memberId"));

		return "book/bookPayment";
	}

	@RequestMapping("/toRecharge")
	public String toRecharge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("memberId", request.getParameter("memberId"));
		Member member = new Member();
		member.setId(new Integer(request.getParameter("memberId")));

		member = this.memberService.getMember(member);
		request.setAttribute("member", member);

		return "book/recharge";
	}

	@RequestMapping(value = "/saveMemberPayment")
	public String saveMemberPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";

		MemberPayment memberPayment = new MemberPayment();
		memberPayment.setCreateDate(new Date());
		memberPayment.setPayment(new Double(request.getParameter("payment")));
		memberPayment.setOrderNumber(request.getParameter("orderNumber"));
		memberPayment.setStatus(0);
		memberPayment
				.setMemberId(new Integer(request.getParameter("memberId")));
		MemberAccount memberAccount = this.memberService
				.searchMemberAccountByMemberId(request.getParameter("memberId"));
		memberPayment.setType(memberAccount.getType());
		this.memberService.saveMemberPayment(memberPayment);

		Integral integral = new Integral();
		integral.setCreateDate(new Date());
		integral.setMemberId(new Integer(request.getParameter("memberId")));
		integral.setFraction(new Integer(request.getParameter("payment")) * 100);
		integral.setTypeId(1);
		integral.setStatus(0);
		integral.setMemberType(memberAccount.getType());
		integral.setRemark("订单编号:" + memberPayment.getOrderNumber() + ",充值金额:"
				+ memberPayment.getPayment() + "元!");
		this.memberService.saveMemberIntegar(integral);

		return "redirect:memberBookInfo?memberId="
				+ request.getParameter("memberId");

	}

	@RequestMapping(value = "/saveMemberBook")
	public String saveMemberBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonStr = "{\"status\":\"ok\"}";
		try {

			Calendar calendar = new GregorianCalendar();
			MemberBook memberBook = new MemberBook();
			memberBook = this.memberService.getMemberBook(request
					.getParameter("memberId"));
			if (memberBook.getId() != null) {
				memberBook.setType(new Integer(request.getParameter("type")));
				calendar.setTime(memberBook.getEndDate());
				calendar.add(Calendar.YEAR, 1);
				memberBook.setEndDate(calendar.getTime());

			} else {
				memberBook.setCreateDate(new Date());
				memberBook.setMemberid(new Integer(request
						.getParameter("memberId")));
				memberBook.setType(new Integer(request.getParameter("type")));
				Date date = new Date();// 取时间

				calendar.setTime(date);
				calendar.add(Calendar.YEAR, 1);
				memberBook.setEndDate(calendar.getTime());

			}
			this.memberService.saveMemberBook(memberBook);

		} catch (Exception e) {
			jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		return null;
	}

	@RequestMapping("/integralManager")
	public String integralManager(HttpServletRequest request,
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

		MemberAccount memberAccount = this.memberService
				.searchMemberAccountByMemberId(request.getParameter("memberId"));
		map.put("memberId", request.getParameter("memberId"));
		map.put("memberType", memberAccount.getType().toString());
		map.put("typeId", request.getParameter("typeId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());

		if ("1".equals(request.getParameter("typeId"))) {
			request.setAttribute("title", "充值记录");
		}
		if ("3".equals(request.getParameter("typeId"))) {
			request.setAttribute("title", "消费记录");
		}
		Page resultPage = this.integralService.searchIntegral(map);
		request.setAttribute("integralList", resultPage.getItems());

		return "book/integralManager";
	}

	@RequestMapping(value = "/getAddress")
	public String getAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserAddress userAddress = new UserAddress();
		if (request.getParameter("userAddressId") != null) {
			userAddress = this.mallProductService.getUserAddress(new Integer(
					request.getParameter("userAddressId")));

			if ("edit".equals(request.getParameter("type"))
					|| "modufy".equals(request.getParameter("searchType"))) {
				MallOrder mallOrder = new MallOrder();
				mallOrder.setMemberId(userAddress.getMemberId());
				request.setAttribute("mallOrder", mallOrder);
				request.setAttribute("searchType",
						request.getParameter("searchType"));
				request.setAttribute("userAddress", userAddress);

				return "mall/addressModufy";
			} else if ("getAddress".equals(request.getParameter("searchType"))) {
				if (request.getSession().getAttribute("mallOrder") != null) {
					MallOrder mallOrder = (MallOrder) request.getSession()
							.getAttribute("mallOrder");
					mallOrder.setAddressId(userAddress.getId());
					request.getSession().setAttribute("mallOrder", mallOrder);
					request.setAttribute("userAddress", userAddress);
					Member member = new Member();
					member.setId(userAddress.getMemberId());

					member = this.memberService.getMember(member);
					request.setAttribute("member", member);
					return "mall/addOrder";
				}

			}

			else {
				Member member = new Member();
				member.setId(userAddress.getMemberId());
				member = this.memberService.getMember(member);
				request.setAttribute("member", member);
				request.setAttribute("userAddress", userAddress);
				return "electrism/electrismAddOrder";
			}

		}

		else {
			MallOrder mallOrder = new MallOrder();
			mallOrder
					.setMemberId(new Integer(request.getParameter("memberId")));
			request.setAttribute("mallOrder", mallOrder);
			request.setAttribute("searchType",
					request.getParameter("searchType"));

		}
		request.setAttribute("userAddress", userAddress);

		return "mall/addressModufy";
	}

	@RequestMapping(value = "/toBookStill")
	public String toBookStill(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		request.setAttribute("memberId", request.getParameter("memberId"));
		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("memberId", request.getParameter("memberId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("status", "0");

		Page resultPage = this.bookOrderService.searchMemberBookInfo(map);

		request.setAttribute("bookList", resultPage.getItems());

		return "book/bookStill";
	}

	@RequestMapping(value = "/bookOrderManager")
	public String bookOrderManager(HttpServletRequest request,
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

		// map.put("memberId", request.getParameter("memberId"));
		map.put("orderNumber", queryDto.getNumber());
		map.put("status", queryDto.getStatus());
		map.put("startDate", queryDto.getStartDate());
		map.put("endDate", queryDto.getEndDate());

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		MallOrder mallOrder = new MallOrder();

		Page resultPage = this.bookOrderService.searchOrderInfo(map);

		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jobj = new JSONObject();
		String[] tempCate = null;
		String[] tempBook = null;
		Category category = new Category();
		List tempList = new ArrayList();
		if (infoList != null) {

			for (int i = 0; i < infoList.size(); i++) {

				jobj = new JSONObject();
				jobj.put("id", infoList.get(i)[6]);
				jobj.put("orderNumber", infoList.get(i)[0]);
				jobj.put("totalPrice", infoList.get(i)[3]);
				jobj.put("status", infoList.get(i)[2]);
				if ("1".equals(infoList.get(i)[2].toString()))// 订单状态:1已付款,待发货,2已发货,3退款中,4已退款
				{
					jobj.put("statusName", "待发货");
					jobj.put("express", "");
					jobj.put("expressNumber", "");
				} else if ("2".equals(infoList.get(i)[2].toString())) {
					jobj.put("statusName", "已发货");
					jobj.put("express", infoList.get(i)[4]);
					jobj.put("expressNumber", infoList.get(i)[5]);
				} else if ("3".equals(infoList.get(i)[2].toString())) {
					jobj.put("statusName", "退款中");
					jobj.put("express", "");
					jobj.put("expressNumber", "");
				} else if ("4".equals(infoList.get(i)[2].toString())) {
					jobj.put("statusName", "已退款");
					jobj.put("express", "");
					jobj.put("expressNumber", "");
				} else if ("5".equals(infoList.get(i)[2].toString())) {
					jobj.put("statusName", "已评论");
					jobj.put("express", "");
					jobj.put("expressNumber", "");
				}

				jobj.put("userName", infoList.get(i)[8]);
				jobj.put("mobile", infoList.get(i)[9]);
				jobj.put("createDate", infoList.get(i)[10].toString()
						.substring(0, 10));
				jobj.put("address",
						infoList.get(i)[11].toString() + infoList.get(i)[12]);
				if (infoList.get(i)[7] != null) {
					tempCate = infoList.get(i)[7].toString().split(",");
					category = new Category();
					tempList = new ArrayList();
					for (int j = 0; j < tempCate.length; j++) {

						tempBook = tempCate[j].split("<");
						if (tempBook != null) {
							category = new Category();
							category.setAuthor(tempBook[3]);
							category.setbName(tempBook[1]);
							category.setCateID(tempBook[0]);
							category.setCover(Keys.STAT_NAME
									+ "/wechat/wechatImages/book/"
									+ tempBook[2]);
							category.setPrice(new Double(tempBook[4]));
							category.setCode(tempBook[5]);
							category.setStatus(new Integer(tempBook[6]));
							tempList.add(category);

						}
						jobj.put("bookList", tempList);

					}
					jobj.put("count", tempList.size());

				} else {
					jobj.put("count", "0");
				}
				jsonList.add(jobj);
			}
			jsonObject.accumulate("infoList", jsonList);

		}

		request.setAttribute("resultPage", resultPage);

		request.setAttribute("jsonStr", jsonObject.toString());

		return "book/bookOrderManager";

	}

	@RequestMapping(value = "/updateBookOrderStatus")
	public String updateBookOrderStatus(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		String express = request.getParameter("express");
		this.bookOrderService.updateBookOrderExpess(
				request.getParameter("orderId"), "2", express.split(":")[0],
				express.split(":")[1]);

		BookOrder bookOrder = this.bookOrderService.getBookOrder(request
				.getParameter("orderId"));
		Member member = new Member();
		member.setId(bookOrder.getMemberId());
		member = this.memberService.getMember(member);

		NoticeInfo noticeInfo = new NoticeInfo();
		String accessToken = this.accountService.getAccessToken(Keys.APP_ID,
				Keys.APP_SECRET);
		noticeInfo.setAccessToKen(accessToken);
		noticeInfo.setOpenId(member.getOpenId());
		noticeInfo.setMemberId(member.getId().toString());
		noticeInfo.setFirst("亲爱滴会员，您的宝贝已委托快递大哥护送出门：");
		noticeInfo.setKeyword1(request.getParameter("userName"));
		noticeInfo.setKeyword2(request.getParameter("address"));
		noticeInfo.setKeyword3(bookOrder.getOrderNumber());
		noticeInfo.setKeyword4(request.getParameter("kuaidi"));
		noticeInfo.setKeyword5(request.getParameter("danhao"));
		noticeInfo.setRemark("若有疑问请致电客服中心:" + Keys.CUSTOMER_MOBLIE);
		noticeInfo.setUrl("http://m.kuaidi100.com/index_all.html?type="
				+ request.getParameter("kuaidi") + "&postid="
				+ request.getParameter("danhao") + "#result");
		WeChatUtil.sendOrderExpress(noticeInfo);

		return "redirect:bookOrderManager";
	}

	@RequestMapping(value = "/bookExpressManager")
	public String bookExpressManager(HttpServletRequest request,
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

		// map.put("memberId", request.getParameter("memberId"));
		map.put("nickName", queryDto.getName());
		map.put("status", queryDto.getStatus());
		map.put("mobile", queryDto.getMobile());

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		MallOrder mallOrder = new MallOrder();

		Page resultPage = this.bookOrderService.searchMemberExpress(map);

		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jobj = new JSONObject();
		String[] tempCate = null;
		String[] tempBook = null;
		Category category = new Category();
		List tempList = new ArrayList();
		if (infoList != null) {

			for (int i = 0; i < infoList.size(); i++) {

				jobj = new JSONObject();
				jobj.put("id", infoList.get(i)[0]);
				jobj.put("express", infoList.get(i)[1]);
				jobj.put("expressNumber", infoList.get(i)[2]);
				jobj.put("status", infoList.get(i)[2]);
				if ("0".equals(infoList.get(i)[3].toString()))// 订单状态:0待收货,1已收货
				{
					jobj.put("statusName", "待收货");

				} else if ("1".equals(infoList.get(i)[2].toString())) {
					jobj.put("statusName", "已收货");

				}

				jobj.put("userName", infoList.get(i)[6]);
				jobj.put("mobile", infoList.get(i)[7]);
				jobj.put("createDate",
						infoList.get(i)[4].toString().substring(0, 10));

				if (infoList.get(i)[5] != null) {

					jobj.put("info", infoList.get(i)[5]);
				}

				jsonList.add(jobj);
			}
			jsonObject.accumulate("infoList", jsonList);

		}

		request.setAttribute("resultPage", resultPage);

		request.setAttribute("jsonStr", jsonObject.toString());

		return "book/bookExpressManager";

	}

	@RequestMapping(value = "/updatekExpressStatus")
	public String updateBookExpressStatus(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		String express = request.getParameter("express");
		this.bookOrderService
				.updateBookExpress(request.getParameter("id"), "1");
		this.bookOrderService
				.updateBooksStatus(request.getParameter("codeIds"));

		// BookOrder bookOrder =
		// this.bookOrderService.getBookOrder(request.getParameter("orderId"));
		// Member member = new Member();
		// member.setId(bookOrder.getMemberId());
		// member = this.memberService.getMember(member);
		//
		// NoticeInfo noticeInfo = new NoticeInfo();
		// String accessToken = this.accountService.getAccessToken(Keys.APP_ID,
		// Keys.APP_SECRET);
		// noticeInfo.setAccessToKen(accessToken);
		// noticeInfo.setOpenId(member.getOpenId());
		// noticeInfo.setMemberId(member.getId().toString());
		// noticeInfo.setFirst("亲爱滴会员，您的宝贝已委托快递大哥护送出门：");
		// noticeInfo.setKeyword1(request.getParameter("userName"));
		// noticeInfo.setKeyword2(request.getParameter("address"));
		// noticeInfo.setKeyword3(bookOrder.getOrderNumber());
		// noticeInfo.setKeyword4(request.getParameter("kuaidi"));
		// noticeInfo.setKeyword5(request.getParameter("danhao"));
		// noticeInfo.setRemark("若有疑问请致电客服中心:"+Keys.CUSTOMER_MOBLIE);
		// noticeInfo.setUrl("http://m.kuaidi100.com/index_all.html?type="+request.getParameter("kuaidi")+"&postid="+request.getParameter("danhao")+"#result");
		// WeChatUtil.sendOrderExpress(noticeInfo);

		return "redirect:bookExpressManager";
	}

	@RequestMapping(value = "/memberShopOrder")
	public String memberShopOrder(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {

		String page = "1";
		String rowsPerPage = "20";
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			page = request.getParameter("page");
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			rowsPerPage = request.getParameter("pageSize");
		}
		HashMap map = new HashMap();
		map.put("page", page);
		map.put("rowsPerPage", rowsPerPage);
		map.put("shopId", request.getParameter("shopId"));

		map.put("memberId", request.getParameter("memberId"));
		map.put("mobile", request.getParameter("mobile"));

		Page resultPage = this.bookOrderService.searchShopOrder(map);
		List<Object[]> infoList = resultPage.getItems();
		String[] tempStr = null;
		List tempList = new ArrayList();
		// 封装成JSON显示对象
		List jsonList = new ArrayList();
		JSONObject jsonObj = new JSONObject();
		JSONObject cateObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] category : infoList) {
				JSONObject jobj = new JSONObject();

				jobj.put("orderId", category[0]);
				jobj.put("orderNumber", category[1]);
				jobj.put("orderDate", category[2].toString().substring(0, 10));

				jobj.put("memberName", category[5]);
				jobj.put("mobile", category[6]);
				jobj.put("headImg", category[7]);
				jobj.put("status", category[8]);

				if (!"".equals(category[3]) && category[3] != null) {

					tempStr = category[3].toString().split(",");
					tempList = new ArrayList();

					for (int i = 0; i < tempStr.length; i++) {

						String[] tempLength = tempStr[i].toString().split(">");
						cateObj = new JSONObject();
						cateObj.put("code", tempStr[i].toString().split(">")[0]);
						cateObj.put("name", tempStr[i].toString().split(">")[1]);

						// //System.out.println("tempLength.length = "+tempLength.length);
						if (tempLength.length >= 3) {
							cateObj.put("cover", Keys.STAT_NAME
									+ "/wechat/wechatImages/book/"
									+ tempStr[i].toString().split(">")[2]);
						} else {
							cateObj.put("cover", "");
						}

						if (tempLength.length >= 4) {
							cateObj.put("author",
									tempStr[i].toString().split(">")[3]);
						} else {
							cateObj.put("author", "");
						}

						// //System.out.println("tempLength.length = "+tempLength.length);
						// if(tempLength.length ==5)
						// {
						// if(tempStr[i].toString().split(">")[4].length()>=35)
						// {
						// cateObj.put("content",
						// tempStr[i].toString().split(">")[4].toString().substring(0,35)+"...");
						// }
						// else
						// {
						// cateObj.put("content",
						// tempStr[i].toString().split(">")[4]);
						// }
						// }
						// else
						// {
						// cateObj.put("content", "");
						// }

						tempList.add(cateObj);

					}
				}
				jobj.put("bookList", tempList);

				jsonList.add(jobj);
			}

		}

		request.setAttribute("bookList", jsonList);

		return "book/memberShopOrder";

	}

	@RequestMapping(value = "/stockManager")
	public String stockManager(HttpServletRequest request,
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

		// map.put("memberId", request.getParameter("memberId"));
		map.put("nickName", queryDto.getName());
		map.put("status", queryDto.getStatus());
		map.put("mobile", queryDto.getMobile());

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		MallOrder mallOrder = new MallOrder();

		Page resultPage = this.bookService.searchBookShopCount(map);

		List<Object[]> infoList = resultPage.getItems();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		JSONObject jobj = new JSONObject();
		String[] tempCate = null;
		String[] tempBook = null;
		Category category = new Category();
		List tempList = new ArrayList();
		if (infoList != null) {

			for (int i = 0; i < infoList.size(); i++) {

				jobj = new JSONObject();
				jobj.put("id", infoList.get(i)[1]);
				jobj.put("name", infoList.get(i)[0]);
				jobj.put("count", infoList.get(i)[2]);

				jsonList.add(jobj);
			}
			jsonObject.accumulate("infoList", jsonList);

		}

		request.setAttribute("resultPage", resultPage);

		request.setAttribute("jsonStr", jsonObject.toString());

		return "book/stockManager";

	}

	@RequestMapping(value = "/upExcelBook")
	public String upExcelBook(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "excelFile", required = false) MultipartFile excelFile)
			throws Exception {
		jxl.Workbook wb = Workbook.getWorkbook(excelFile.getInputStream()); // 得到工作薄
		jxl.Sheet st = wb.getSheet(0);// 得到工作薄中的第一个工作表
		int rsRows = st.getRows(); // 得到excel的总行数
		// excel导入的数据
		// 总的格式正确数据集合
		ArrayList memberInfos = new ArrayList();
		// 总的格式错误数据集合
		ArrayList memberInfosErrors = new ArrayList();

		// 总的错误记录条数
		int errortotal = 0;

		ArrayList<String> memberCards = new ArrayList<String>();

		ArrayList<String> memberNames = new ArrayList<String>();

		ArrayList<String> memberMobiles = new ArrayList<String>();

		StringBuffer cateIds = new StringBuffer();
		String barcode = "";
		Category category = new Category();
		int errorCount = 0;
		int saveCount = 0;
		List excelList = new ArrayList();
		List AddList = new ArrayList();

		HashMap codeMap = new HashMap();
		HashMap bookNameMap = this.bookService.searchBookName();

		Book book = new Book();

		ExcelBook excelBook = new ExcelBook();
		int errorBuffer = 0;

		String codeValue = "";

		String picPath = Keys.USER_PIC_PATH + "/book/barCode/";
		int random = 0;
		String pathName = "";
		String addPatName = "";
		TwoDimensionCode twoDimensionCode = new TwoDimensionCode();
		Integer bookCount = new Integer(0);

		Integer C_CateId = this.bookService.getCateId("C");
		Integer E_CateId = this.bookService.getCateId("E");

		DecimalFormat df = new DecimalFormat("0000");

		for (int i = 1; i < rsRows; i++) {

			// 每次添加时错误的字段数

			// 每次添加时错误的具体信息
			// StringBuffer errorMsg = new StringBuffer();
			category = new Category();

			excelBook = new ExcelBook();

			if (st.getCell(1, i).getContents() != null) {
				category.setCateID(st.getCell(1, i).getContents().toString()
						.trim());

			} else {

				if (st.getCell(0, i).getContents() != null) {
					if ("1".equals(st.getCell(0, i).getContents().toString())) {
						C_CateId++;
						category.setCateID("C" + df.format(C_CateId));
					}
					if ("2".equals(st.getCell(0, i).getContents().toString())) {
						E_CateId++;
						category.setCateID("E" + df.format(E_CateId));
					}

				}

			}

			if (st.getCell(2, i).getContents() != null) {
				category.setbName(st.getCell(2, i).getContents().toString()
						.trim());
			}

			if (st.getCell(3, i).getContents() != null) {
				category.setCode(st.getCell(3, i).getContents().toString()
						.trim());
			}

			if (st.getCell(4, i).getContents() != null) {
				category.setAuthor(st.getCell(4, i).getContents().toString()
						.trim());
			}

			if (st.getCell(5, i).getContents() != null) {
				category.setTranslator(st.getCell(5, i).getContents()
						.toString().trim());
			}

			if (st.getCell(6, i).getContents() != null) {
				category.setPublish(st.getCell(6, i).getContents().toString()
						.trim());
			}

			if (st.getCell(7, i).getContents() != null) {
				category.setSeries(st.getCell(7, i).getContents().toString()
						.trim());
			}
			if (st.getCell(8, i).getContents() != null) {
				category.setContent(st.getCell(8, i).getContents().toString()
						.trim());
			}
			category.setRightId(11);
			category.setPrice(new Double(10));
			category.setPage(1);

			errorBuffer = 0;

			if (st.getCell(0, i).getContents() == null) {
				category.setExcelMessage("添加失败,数据不完整");
				errorBuffer = 1;
			}

			if ("".equals(category.getbName()) && category.getbName() == null) {
				category.setExcelMessage("添加失败,数据不完整");
				errorBuffer = 1;
			}

			else {

				if (bookNameMap.get(category.getCateID().toString()) != null) {

					excelBook.setMessage("添加失败,书籍已存在!");
					errorBuffer = 1;
				} else {
					AddList.add(category);

					if (st.getCell(9, i).getContents() != null) {
						bookCount = new Integer(st.getCell(9, i).getContents()
								.toString().trim());
					} else {
						bookCount = 1;
					}

					int codeNum = 1;

					for (int j = 0; j < bookCount && j < 10; j++) {// 最多生成9个库存

						book = new Book();
						book.setCateId(category.getCateID());
						book.setBarCode(category.getCateID() + "00" + codeNum);
						book.setBelong(1);
						this.bookService.saveBook(book);

						// random=(int)(Math.random()*1000000);
						// pathName = new Date().getTime() + random +".png";
						// addPatName = picPath + pathName;

						// codeValue =
						// "["+book.getBarCode()+"]["+category.getbName()+"]["+codeValue+"][凡豆书院]";
						codeValue = "["
								+ book.getBarCode()
								+ "]["
								+ category.getbName().replaceAll("'", " ")
										.replaceAll(",", " ") + "]";
						book.setCodeInfo(codeValue);
						// book.setUrl(Keys.STAT_NAME+"/wechat/wechatImages/book/barCode/"+pathName);
						this.bookService.updateBookInfo(book);
						// twoDimensionCode.encoderQRCode(codeValue,addPatName,
						// "png");
						codeNum++;
					}

					excelBook.setMessage("添加成功!");
				}

			}
			excelBook.setStatus(errorBuffer);

			if (errorBuffer == 0) {
				saveCount++;
			} else {
				errorCount++;
				errortotal = 1;
			}
			excelList.add(excelBook);
		}

		// sqlServerMemberInfoDao.saveMemberInfoBatch(memberInfos);
		// response.setCharacterEncoding("UTF-8");
		// response.setContentType("application/json;charset=UTF-8");
		this.bookService.saveBookList(AddList);
		String jsonStr = "";
		if (errortotal == 0) {

			jsonStr = "成功导入数据:" + saveCount + "条!";
		} else {

			Long dateTime = new Date().getTime();
			this.redisService.setList("EXCEL:" + dateTime, excelList, 180);
			request.setAttribute("errorCount", "1");
			request.setAttribute("excelNumber", "EXCEL:" + dateTime);
			jsonStr = "成功导入数据:" + saveCount + "条!导入失败数据:" + errorCount + "条!";
		}

		request.setAttribute("message", jsonStr);

		return this.bookManager(request, new QueryDto());

	}

	@RequestMapping(value = "/upExcel")
	public String getExlce(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "excelFile", required = false) MultipartFile excelFile)
			throws Exception {

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		// MultipartFile excelFile = multipartRequest.getFile("excelFile");

		//System.out.println("shopId=" + request.getParameter("shopId"));
		jxl.Workbook wb = Workbook.getWorkbook(excelFile.getInputStream()); // 得到工作薄
		jxl.Sheet st = wb.getSheet(0);// 得到工作薄中的第一个工作表
		int rsRows = st.getRows(); // 得到excel的总行数
		// excel导入的数据
		// 总的格式正确数据集合
		ArrayList memberInfos = new ArrayList();
		// 总的格式错误数据集合
		ArrayList memberInfosErrors = new ArrayList();

		// 总的错误记录条数
		int errortotal = 0;

		ArrayList<String> memberCards = new ArrayList<String>();

		ArrayList<String> memberNames = new ArrayList<String>();

		ArrayList<String> memberMobiles = new ArrayList<String>();

		StringBuffer cateIds = new StringBuffer();
		String barcode = "";
		for (int i = 0; i < rsRows; i++) {

			barcode = st.getCell(0, i).getContents().toString().trim();
			if (!"".equals(barcode) && barcode != null) {
				cateIds.append("'" + barcode + "'");
				cateIds.append(",");

			}

		}
		if (cateIds.length() > 0) {
			cateIds = new StringBuffer(cateIds.substring(0,
					cateIds.length() - 1));

		}
		// //System.out.println("cateIds = "+cateIds);
		int errorCount = 0;
		int saveCount = 0;
		List excelList = new ArrayList();
		if (cateIds != null && cateIds.length() > 0) {
			List barCodeList = this.bookService.searchBookBarCode(cateIds
					.toString());
			HashMap codeMap = new HashMap();
			HashMap cateIdMap = new HashMap();

			Book book = new Book();

			ExcelBook excelBook = new ExcelBook();
			int errorBuffer = 0;

			String codeValue = "";

			String picPath = Keys.USER_PIC_PATH + "/book/barCode/";
			int random = 0;
			String pathName = "";
			String addPatName = "";
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode();
			for (int i = 0; i < rsRows; i++) {

				// 每次添加时错误的字段数

				// 每次添加时错误的具体信息
				// StringBuffer errorMsg = new StringBuffer();
				errorBuffer = 0;
				excelBook = new ExcelBook();
				excelBook.setCateId(st.getCell(0, i).getContents().toString()
						.trim());
				excelBook.setBarCode(st.getCell(0, i).getContents().toString()
						.trim()
						+ st.getCell(1, i).getContents().toString().trim());
				excelBook.setName(st.getCell(2, i).getContents().toString()
						.trim());

				if ("".equals(excelBook.getCateId())
						&& excelBook.getCateId() == null) {
					excelBook.setMessage("添加失败,数据不完整");
					errorBuffer = 1;
				}
				if ("".equals(excelBook.getBarCode())
						&& excelBook.getBarCode() == null && errorBuffer == 0) {
					excelBook.setMessage("添加失败,数据不完整");
					errorBuffer = 1;
				} else {

					book = new Book();
					book.setCateId(excelBook.getCateId());
					book.setBarCode(excelBook.getBarCode());
					book.setBelong(new Integer(request.getParameter("shopId")));
					book.setCodeInfo(st.getCell(3, i).getContents().toString()
							.trim());
					this.bookService.saveBook(book);

					random = (int) (Math.random() * 1000000);
					pathName = new Date().getTime() + random + ".png";
					addPatName = picPath + pathName;

					// codeValue =
					// "["+book.getBarCode()+"]["+category.getbName()+"]["+codeValue+"][凡豆书院]";
					codeValue = book.getCodeInfo();
					book.setCodeInfo(codeValue);
					book.setUrl(Keys.STAT_NAME
							+ "/wechat/wechatImages/book/barCode/" + pathName);
					this.bookService.updateBookInfo(book);
					twoDimensionCode
							.encoderQRCode(codeValue, addPatName, "png");

					excelBook.setMessage("添加成功!");

				}
				excelBook.setStatus(errorBuffer);

				if (errorBuffer == 0) {
					saveCount++;
				} else {
					if (!"".equals(excelBook.getCateId())
							&& excelBook.getBarCode() != null) {
						errorCount++;
					}

					errortotal++;
				}
				excelList.add(excelBook);

			}

			if (errortotal > 0) {
				request.getSession().setAttribute("memberInfosErrors",
						memberInfosErrors);
			}
		}

		Long dateTime = new Date().getTime();
		this.redisService.setList("EXCEL:" + dateTime, excelList, 180);

		String jsonStr = "";
		if (errortotal == 0) {
			// response.getWriter().println("ok");
			jsonStr = "成功导入数据:" + saveCount + "条!";
		} else {
			// response.getWriter().println("导入数据失败！失败条数：" + saveCount + "条！");
			request.setAttribute("errorCount", "1");
			jsonStr = "成功导入数据:" + saveCount + "条!导入失败数据:" + errorCount + "条!";
		}
		// response.setContentType("application/json;charset=UTF-8");
		// response.getWriter().println(jsonStr);
		request.getSession().setAttribute("excelBookList", excelList);
		request.setAttribute("message", jsonStr);
		request.setAttribute("exceNumber", "EXCEL:" + dateTime);
		return this.stockManager(request, response, new QueryDto());

	}

	@RequestMapping(value = "/upExcelBlank")
	public String getExlce_blank(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "excelFile", required = false) MultipartFile excelFile)
			throws Exception {

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		// MultipartFile excelFile = multipartRequest.getFile("excelFile");

		//System.out.println("shopId=" + request.getParameter("shopId"));
		jxl.Workbook wb = Workbook.getWorkbook(excelFile.getInputStream()); // 得到工作薄
		jxl.Sheet st = wb.getSheet(0);// 得到工作薄中的第一个工作表
		int rsRows = st.getRows(); // 得到excel的总行数
		// excel导入的数据
		// 总的格式正确数据集合
		ArrayList memberInfos = new ArrayList();
		// 总的格式错误数据集合
		ArrayList memberInfosErrors = new ArrayList();

		// 总的错误记录条数
		int errortotal = 0;

		ArrayList<String> memberCards = new ArrayList<String>();

		ArrayList<String> memberNames = new ArrayList<String>();

		ArrayList<String> memberMobiles = new ArrayList<String>();

		StringBuffer cateIds = new StringBuffer();
		String barcode = "";
		for (int i = 0; i < rsRows; i++) {

			barcode = st.getCell(0, i).getContents().toString().trim();
			if (!"".equals(barcode) && barcode != null) {
				cateIds.append("'" + barcode + "'");
				cateIds.append(",");

			}

		}
		if (cateIds.length() > 0) {
			cateIds = new StringBuffer(cateIds.substring(0,
					cateIds.length() - 1));

		}
		// //System.out.println("cateIds = "+cateIds);
		int errorCount = 0;
		int saveCount = 0;
		List excelList = new ArrayList();
		if (cateIds != null && cateIds.length() > 0) {
			List barCodeList = this.bookService.searchBookBarCode(cateIds
					.toString());
			HashMap codeMap = new HashMap();
			HashMap cateIdMap = new HashMap();
			for (int i = 0; i < barCodeList.size(); i++) {

				codeMap.put(barCodeList.get(i).toString(), barCodeList.get(i)
						.toString());
				cateIdMap.put(barCodeList.get(i).toString().substring(0, 5),
						barCodeList.get(i).toString().substring(0, 5));
			}
			Book book = new Book();

			ExcelBook excelBook = new ExcelBook();
			int errorBuffer = 0;

			String codeValue = "";

			String picPath = Keys.USER_PIC_PATH + "/book/barCode/";
			int random = 0;
			String pathName = "";
			String addPatName = "";
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode();
			for (int i = 0; i < rsRows; i++) {

				// 每次添加时错误的字段数

				// 每次添加时错误的具体信息
				// StringBuffer errorMsg = new StringBuffer();
				errorBuffer = 0;
				excelBook = new ExcelBook();
				excelBook.setCateId(st.getCell(0, i).getContents().toString()
						.trim());
				excelBook.setBarCode(st.getCell(0, i).getContents().toString()
						.trim()
						+ st.getCell(1, i).getContents().toString().trim());
				excelBook.setName(st.getCell(2, i).getContents().toString()
						.trim());

				if ("".equals(excelBook.getCateId())
						&& excelBook.getCateId() == null) {
					excelBook.setMessage("添加失败,数据不完整");
					errorBuffer = 1;
				}
				if ("".equals(excelBook.getBarCode())
						&& excelBook.getBarCode() == null && errorBuffer == 0) {
					excelBook.setMessage("添加失败,数据不完整");
					errorBuffer = 1;
				} else {
					if (codeMap.get(excelBook.getBarCode().toString()) == null) {

						if (cateIdMap.get(excelBook.getCateId().toString()) != null) {

							book = new Book();
							book.setCateId(excelBook.getCateId());
							book.setBarCode(excelBook.getBarCode());
							book.setBelong(new Integer(request
									.getParameter("shopId")));
							this.bookService.saveBook(book);

							random = (int) (Math.random() * 1000000);
							pathName = new Date().getTime() + random + ".png";
							addPatName = picPath + pathName;

							// codeValue =
							// "["+book.getBarCode()+"]["+category.getbName()+"]["+codeValue+"][凡豆书院]";
							codeValue = "["
									+ book.getBarCode()
									+ "]["
									+ excelBook.getName().replaceAll("'", " ")
											.replaceAll(",", " ") + "]";
							book.setCodeInfo(codeValue);
							book.setUrl(Keys.STAT_NAME
									+ "/wechat/wechatImages/book/barCode"
									+ pathName);
							this.bookService.updateBookInfo(book);
							twoDimensionCode.encoderQRCode(codeValue,
									addPatName, "png");

							excelBook.setMessage("添加成功!");
						} else {
							excelBook.setMessage("添加失败,书籍不存在!");
							errorBuffer = 1;
						}

					} else {
						excelBook.setMessage("添加失败,编码已存在");
						errorBuffer = 1;
						// errorcount++;
						// errorMsg.append("会员卡号不能为空，");
						// memberInfo.setMemCard("");
					}

				}
				excelBook.setStatus(errorBuffer);

				if (errorBuffer == 0) {
					saveCount++;
				} else {
					if (!"".equals(excelBook.getCateId())
							&& excelBook.getBarCode() != null) {
						errorCount++;
					}

					errortotal++;
				}
				excelList.add(excelBook);

			}

			if (errortotal > 0) {
				request.getSession().setAttribute("memberInfosErrors",
						memberInfosErrors);
			}
		}

		Long dateTime = new Date().getTime();
		this.redisService.setList("EXCEL:" + dateTime, excelList, 180);

		String jsonStr = "";
		if (errortotal == 0) {
			// response.getWriter().println("ok");
			jsonStr = "成功导入数据:" + saveCount + "条!";
		} else {
			// response.getWriter().println("导入数据失败！失败条数：" + saveCount + "条！");
			request.setAttribute("errorCount", "1");
			jsonStr = "成功导入数据:" + saveCount + "条!导入失败数据:" + errorCount + "条!";
		}
		// response.setContentType("application/json;charset=UTF-8");
		// response.getWriter().println(jsonStr);
		request.getSession().setAttribute("excelBookList", excelList);
		request.setAttribute("message", jsonStr);
		request.setAttribute("exceNumber", "EXCEL:" + dateTime);
		return this.stockManager(request, response, new QueryDto());

	}

	@RequestMapping(value = "/showExcelMessage")
	public String showExcelMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList memberInfosErrors = new ArrayList();
		;
		String key = request.getParameter("excelNumber");
		//System.out.println("key =" + key);
		if (this.redisService.exists(key)) {

			memberInfosErrors = (ArrayList) this.redisService.getList(key,
					ExcelBook.class);
			//System.out.println("size = " + memberInfosErrors.size());
			request.getSession().removeAttribute("memberInfosErrors");
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String("批量导入错误信息.xls".getBytes("GBK"), "iso8859-1"));
			OutputStream outstream = response.getOutputStream();
			jxl.write.WritableWorkbook wwb;
			wwb = Workbook.createWorkbook(outstream);

			jxl.write.WritableSheet ws = wwb.createSheet("批量导入错误信息", 1);
			WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat cFormat = new WritableCellFormat(font);
			// 第一行有30列
			ws.setColumnView(0, 30);
			// 第五行第一列
			ws.addCell(new jxl.write.Label(0, 0, "书籍编码", cFormat));
			ws.addCell(new jxl.write.Label(1, 0, "库存编码", cFormat));
			ws.addCell(new jxl.write.Label(2, 0, "书籍名称", cFormat));
			ws.addCell(new jxl.write.Label(3, 0, "导入结果", cFormat));

			WritableCellFormat cFormat1 = new WritableCellFormat(font);
			font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.RED);
			cFormat1 = new WritableCellFormat(font);
			ExcelBook excelBook = new ExcelBook();

			if (memberInfosErrors != null) {
				if (memberInfosErrors.size() > 0) {
					for (int i = 0; i < memberInfosErrors.size(); i++) {
						excelBook = (ExcelBook) memberInfosErrors.get(i);

						ws.addCell(new jxl.write.Label(0, i + 1, excelBook
								.getName(), cFormat));
						ws.addCell(new jxl.write.Label(3, i + 1, excelBook
								.getMessage(), cFormat));

					}
				}
			}

			wwb.write();
			wwb.close();

			outstream.close();
		}

		return null;
	}

}
