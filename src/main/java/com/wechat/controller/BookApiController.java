package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.*;
import com.wechat.service.*;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("bookApi")
public class BookApiController {

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
    private BookCardService bookCardService;

    @Resource
    private RedisService redisService;

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public BookCardService getBookCardService() {
        return bookCardService;
    }

    public void setBookCardService(BookCardService bookCardService) {
        this.bookCardService = bookCardService;
    }

    public BookOrderService getBookOrderService() {
        return bookOrderService;
    }

    public void setBookOrderService(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    public IntegralService getIntegralService() {
        return integralService;
    }

    public void setIntegralService(IntegralService integralService) {
        this.integralService = integralService;
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

    public MallProductService getMallProductService() {
        return mallProductService;
    }

    public void setMallProductService(MallProductService mallProductService) {
        this.mallProductService = mallProductService;
    }

    /**
     * 书院首页接口
     *
     * @param request
     * @param response
     * @param queryDto
     * @throws Exception
     */
    @RequestMapping("/bookManagerView")
    public void bookManagerView(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        long startTime = System.currentTimeMillis();
        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        List<RightCategory> rightList = this.bookService.getRightCategory();
        request.setAttribute("rightList", rightList);
        List<RightCategory> leftList = this.bookService.getLeftCategory();
        request.setAttribute("leftList", leftList);

        map.put("name", queryDto.getName());
        map.put("author", queryDto.getAuthor());
        map.put("publish", queryDto.getPublish());
        map.put("rightType", queryDto.getType());
        map.put("leftType", queryDto.getStatus());

        map.put("page", queryDto.getPage());
        map.put("rowsPerPage", queryDto.getPageSize());
        map.put("rowsPerPage", "10000");
        int catId = 0;
        if (!"".equals(request.getParameter("catId")) && request.getParameter("catId") != null) {
            catId = Integer.parseInt(request.getParameter("catId"));
            // 父分类下的所有子分类
            String catIdChilds = bookService.getCategoryChildIds("book_category", catId);
            String catIds = catId + "";
            if (!catIdChilds.equals("") && null != catIdChilds) {
                catIds = catId + "," + catIdChilds;
            }
            map.put("catIds", catIds);
        }

        Page resultPage = this.bookService.searchCategoryInfo2(map);

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

        List cats = bookService.getBookCategoryList(catId, 0, new ArrayList());
        String a = "";
        String b = "";
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
                jobj.put("rightId", category[11]);
                jobj.put("series", category[12]);
                jobj.put("mp3", category[13]);
                jobj.put("testMp3", category[14]);
                jobj.put("code", category[15]);
                jobj.put("mp3Type", category[16]);
                jobj.put("status", category[17]);
                jobj.put("book_cate_id", category[18]);
                jobj.put("leftName", category[19]);
                jobj.put("leftId", category[20]);
                jobj.put("rightName", category[21]);
                jobj.put("label", category[22]);

                if (category[23] != null) {
                    jobj.put("keyword", category[23]);
                } else {
                    jobj.put("keyword", "");
                }

                if (bookMap.get(category[0].toString()) != null) {

                    jobj.put("bookInfo", ((Object[]) bookMap.get(category[0].toString()))[1]);

                    if (((Object[]) bookMap.get(category[0].toString()))[2] == null) {
                        jobj.put("count", "0");
                    } else {
                        jobj.put("count", ((Object[]) bookMap.get(category[0].toString()))[2]);
                    }
                } else {
                    jobj.put("bookInfo", "");
                }
                jsonList.add(jobj);
                // 遍历分类数组，属于哪个集合就添加到哪个分类下面
                for (int i = 0; i < cats.size(); i++) {
                    BookCategory bookCategory = (BookCategory) cats.get(i);

                    a = category[18].toString();
                    b = bookCategory.getCat_id().toString();
                    if (a.equals(b)) {
                        bookCategory.getBookList().add(jobj);
                        cats.set(i, bookCategory);

                    }
                }
            }
            jsonObj.put("infoList", jsonList);
        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        //System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
        request.setAttribute("jsonStr", jsonObj.toString());
        request.setAttribute("resultPage", resultPage);
        request.setAttribute("queryDto", queryDto);
        map = new HashMap();
        map.put("page", "1");
        map.put("rowsPerPage", "10000");
        map.put("type", "1");
        Page tempResult = this.bookService.searchLabel(map);
        List labelList = new ArrayList();
        labelList = tempResult.getItems();
        JSONObject dataList = new JSONObject();
        dataList.put("jsonStr", cats);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonResult.JsonResultToStr(dataList));

    }

    @RequestMapping("/bookManagerViewLevelOne")
    public void bookManagerViewLevelOne(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto) {
        List<BookCategory> bookCategorys = bookService.getBookCategoryLevelOne();
        JsonResult.JsonResultInfo(response, bookCategorys);
    }

    /**
     * 书院首页接口(优化版)
     *
     * @param request
     * @param response
     * @param queryDto
     * @throws Exception
     */
    @RequestMapping("/bookManagerView2")
    public void bookManagerView2(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        map.put("page", queryDto.getPage());
        map.put("pageSize", "10000");
        int catId = 0;
        if (!"".equals(request.getParameter("catId")) && request.getParameter("catId") != null) {
            catId = Integer.parseInt(request.getParameter("catId"));
            // 父分类下的所有子分类
            String catIdChilds = bookService.getCategoryChildIds("book_category", catId);
            String catIds = catId + "";
            if (!catIdChilds.equals("") && null != catIdChilds) {
                catIds = catId + "," + catIdChilds;
            }
            map.put("catIds", catIds);
            // //System.out.println(catIds);
        }

        Page resultPage = this.bookService.searchBooksByCatIds(map);

        List infoList = resultPage.getItems();
        MultipleTree multipleTree = new MultipleTree();
        multipleTree.setDataList(infoList);
        String parentId = "0";
        for (int i = 0; i < infoList.size(); i++) {
            HashMap dataRecord = (HashMap) infoList.get(i);
            if ((catId + "").equals(dataRecord.get("id").toString())) {
                parentId = (String) dataRecord.get("parentId");
            }
            ;
        }
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(multipleTree.getMultipleTree(parentId, "" + catId));
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
        }

    }

    @RequestMapping("/bookMobileView")
    public String bookMobileView(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object[] category = this.bookService.searchCateGoryInfo(request.getParameter("cateId"));

        List jsonList = new ArrayList();
        JSONObject jsonObj = new JSONObject();
        JSONObject jobj = new JSONObject();
        JSONObject commentObj = new JSONObject();

        jobj.put("cateID", category[0]);
        jobj.put("name", category[1]);
        jobj.put("price", category[2]);
        jobj.put("publish", category[3]);
        jobj.put("author", category[4]);

        jobj.put("translator", category[5]);
        jobj.put("page", category[6]);
        jobj.put("count", category[11]);

        jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/" + category[7]);
        jobj.put("content", category[8]);
        jobj.put("rightName", category[13]);
        jobj.put("leftName", category[12]);
        if (category[9] != null) {
            if (!"".equals(category[9])) {
                jobj.put("mp3", Keys.STAT_NAME + "wechat/wechatImages/book/mp3/"
                        + category[9].toString().substring(0, category[9].toString().length() - 4));

            }
        } else {
            jobj.put("mp3", "");
        }
        if (category[10] != null) {
            jobj.put("testMp3", Keys.STAT_NAME + "wechat/wechatImages/book/mp3/" + category[10]);
        } else {
            jobj.put("testMp3", "");
        }

        jobj.put("cateID", category[0]);
        jobj.put("cataLog", category[14]);
        jobj.put("mp3Type", category[16]);
        if (category[15] != null) {
            jobj.put("code", category[15]);
        } else {
            jobj.put("code", "");
        }
        if (category[18] != null) {
            String[] tempStr = category[18].toString().split(",");
            List commentList = new ArrayList();
            for (int i = 0; i < tempStr.length; i++) {

                commentObj = new JSONObject();
                commentObj.put("name", tempStr[i].toString().split(">")[0].toString());
                commentObj.put("star", tempStr[i].toString().split(">")[2].toString());
                commentObj.put("content", tempStr[i].toString().split(">")[1].toString());
                commentObj.put("createDate", tempStr[i].toString().split(">")[3].toString());
                commentList.add(commentObj);
            }
            jobj.put("commentList", commentList);

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
            jobj.put("labelList", list);

        }

        jsonList.add(jobj);

        jsonObj.put("infoList", jsonList);
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping("/specialManager")
    public String specialManager(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        List list = this.categoryService.searchSpecialIndex();
        JSONObject jsonObj = new JSONObject();
        for (int i = 0; i < list.size(); i++) {

            ((Special) list.get(i))
                    .setLogo(Keys.STAT_NAME + "/wechat/wechatImages/special/" + ((Special) list.get(i)).getLogo());
        }
        jsonObj.put("infoList", list);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping("/specialManagerView")
    public String specialManagerView(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        List<Object[]> infoList = this.categoryService.searchBooksBySpecial(request.getParameter("specialId"));
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
                jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/" + category[3]);
                jobj.put("content", category[4]);

                if (category[5] != null && !"".equals(category[5])) {

                    jobj.put("fraction",
                            df.format(new Double(category[5].toString()) / new Double(category[7].toString())));
                    jobj.put("star",
                            df.format(new Double(category[6].toString()) / new Double(category[7].toString())));

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

    @RequestMapping("/searchBooksByLabel")
    public String searchBooksByLabel(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        map.put("labelId", request.getParameter("labelId"));
        map.put("leftId", request.getParameter("leftId"));
        map.put("rightId", request.getParameter("rightId"));
        map.put("bookName", request.getParameter("bookName"));
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
                jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/" + category[3]);
                jobj.put("content", category[4]);

                if (category[10] != null && !"".equals(category[10])) {

                    jobj.put("fraction",
                            df.format(new Double(category[10].toString()) / new Double(category[12].toString())));
                    jobj.put("star",
                            df.format(new Double(category[11].toString()) / new Double(category[12].toString())));

                } else {
                    jobj.put("fraction", "");
                    jobj.put("star", "");
                }

                jsonList.add(jobj);
            }
            jsonObj.put("infoList", jsonList);
            jsonObj.put("pageCount", resultPage.getTotalPageCount());
            jsonObj.put("totalCount", resultPage.getTotalCount());
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping("/searchBooksByLabelIdAndKeyWord")
    public String searchBooksByLabelIdAndKeyWord(HttpServletRequest request, HttpServletResponse response,
                                                 QueryDto queryDto) throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        map.put("labelId", request.getParameter("labelId"));
        map.put("leftId", request.getParameter("leftId"));
        map.put("rightId", request.getParameter("rightId"));
        map.put("bookName", request.getParameter("bookName"));
        map.put("page", queryDto.getPage());
        map.put("rowsPerPage", queryDto.getPageSize());
        Page resultPage = this.categoryService.searchBooksByLabelIdAndKeyWord(map);
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
                jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/" + category[3]);
                jobj.put("content", category[4]);

                if (category[10] != null && !"".equals(category[10])) {

                    jobj.put("fraction",
                            df.format(new Double(category[10].toString()) / new Double(category[12].toString())));
                    jobj.put("star",
                            df.format(new Double(category[11].toString()) / new Double(category[12].toString())));

                } else {
                    jobj.put("fraction", "");
                    jobj.put("star", "");
                }

                jsonList.add(jobj);
            }
            jsonObj.put("infoList", jsonList);
            jsonObj.put("pageCount", resultPage.getTotalPageCount());
            jsonObj.put("totalCount", resultPage.getTotalCount());
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping("/getLabel")
    public String getLabel(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }
        map.put("page", queryDto.getPage());
        map.put("rowsPerPage", queryDto.getPageSize());
        Page resultPage = this.bookService.searchLabel(map);

        JSONObject jsonObj = new JSONObject();

        jsonObj.put("infoList", resultPage.getItems());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping("/searchIndexLabel")
    public String searchIndexLabel(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        try {

            List<Object[]> infoList = this.bookService.searchIndexLabel(request.getParameter("type"));

            List jsonList = new ArrayList();
            // 封装成JSON显示对象

            if (infoList != null) {
                JSONArray AgentKeyWordInfo = new JSONArray();

                for (Object[] category : infoList) {
                    JSONObject jobj = new JSONObject();

                    jobj.put("id", category[0]);
                    jobj.put("name", category[1]);

                    if (new Integer(category[3].toString()) > 100) {

                        jobj.put("count", new Integer(category[3].toString()) + 100);

                    } else {
                        jobj.put("count", category[3]);
                    }

                    jsonList.add(jobj);
                }

            }

            JSONObject result = new JSONObject();

            result.put("infoList", jsonList);

            JsonResult.JsonResultInfo(response, result);

        } catch (Exception e) {

            JsonResult.JsonResultError(response, 1000);
        }

        return null;
    }

    @RequestMapping(value = "/saveBookVehicle")
    public String saveBookVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {
            BookVehicle bookVehicle = new BookVehicle();
            bookVehicle.setCateId(request.getParameter("cateId"));
            bookVehicle.setMemberId(new Integer(request.getParameter("memberId")));

            List<Object[]> list = this.bookService.searchaBookVehicle(request.getParameter("memberId"));
            HashMap map = new HashMap();
            for (int i = 0; i < list.size(); i++) {

                map.put(list.get(i)[0].toString(), list.get(i)[0]);
            }

            if (map.get(bookVehicle.getCateId()) == null) {

                this.bookService.saveBookVehicle(bookVehicle);
            }
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;

    }

    @RequestMapping(value = "/bookVehicleManager")
    public String bookVehicleManager(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Object[]> infoList = this.bookService.searchaBookVehicle(request.getParameter("memberId"));
        for (int i = 0; i < infoList.size(); i++) {

            if (infoList.get(i)[3] != null) {
                if (infoList.get(i)[3].toString().length() >= 35) {
                    infoList.get(i)[3] = infoList.get(i)[3].toString().substring(0, 35) + "...";
                }
            }

        }
        // 封装成JSON显示对象
        List jsonList = new ArrayList();
        JSONObject jsonObj = new JSONObject();
        if (infoList != null) {
            JSONArray AgentKeyWordInfo = new JSONArray();

            for (Object[] category : infoList) {
                JSONObject jobj = new JSONObject();

                jobj.put("cateID", category[0]);
                jobj.put("name", category[1]);
                jobj.put("author", category[5]);
                jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/" + category[2]);
                jobj.put("price", category[4]);
                jobj.put("id", category[6]);
                if (category[3].toString().length() >= 35) {

                    jobj.put("content", category[3].toString().substring(0, 35) + "...");
                } else {
                    jobj.put("content", category[3].toString());
                }

                jsonList.add(jobj);
            }
            jsonObj.put("infoList", jsonList);

        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());
        return null;
    }

    @RequestMapping(value = "/deleteShoppingCart")
    public String deleteShoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {
            this.bookService.deleteBookVehicle(request.getParameter("ids"));
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/searchBookByCode")
    public String searchBookByCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Category> infoList = this.categoryService.searchBookByCode(request.getParameter("code"));

        // 封装成JSON显示对象
        List jsonList = new ArrayList();
        JSONObject jsonObj = new JSONObject();
        if (infoList != null) {
            JSONArray AgentKeyWordInfo = new JSONArray();

            for (Category category : infoList) {
                JSONObject jobj = new JSONObject();

                jobj.put("cateID", category.getCateID());
                jobj.put("name", category.getbName());
                jobj.put("author", category.getAuthor());
                jobj.put("cover", Keys.STAT_NAME + "wechat/wechatImages/book/" + category.getCover());
                jobj.put("price", category.getPrice());
                jobj.put("mp3Type", category.getMp3Type());
                if (category.getMp3() != null && !"".equals(category.getMp3())) {
                    jobj.put("mp3", Keys.STAT_NAME + "wechat/wechatImages/book/mp3/"
                            + category.getMp3().substring(0, category.getMp3().length() - 4));

                } else {
                    jobj.put("mp3", "");
                }
                jobj.put("testMp3", Keys.STAT_NAME + "wechat/wechatImages/book/mp3/" + category.getTestMp3());
                jobj.put("code", category.getCode());
                if (category.getContent().length() >= 35) {

                    jobj.put("content", category.getContent().toString().substring(0, 35) + "...");
                } else {
                    jobj.put("content", category.getContent().toString());
                }

                jsonList.add(jobj);
            }
            jsonObj.put("infoList", jsonList);

        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());
        return null;
    }

    @RequestMapping(value = "/saveMemberMp3")
    public String saveMemberMp3(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";

        try {
            MemberMp3 memberMp3 = new MemberMp3();
            memberMp3.setMemberId(new Integer(request.getParameter("memberId")));
            memberMp3.setProductId(new Integer(request.getParameter("productId")));

            this.memberService.saveMemberMp3(memberMp3);
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/saveMemberCollection")
    public String saveMemberCollection(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";

        try {
            MemberCollection memberCollection = new MemberCollection();
            memberCollection.setMemberId(new Integer(request.getParameter("memberId")));
            memberCollection.setProductId(new Integer(request.getParameter("productId")));

            this.memberService.saveMemberCollection(memberCollection);
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/deleteMemberMp3")
    public String deleteMemberMp3(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {
            this.memberService.deleteMemberMp3(request.getParameter("ids"));
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/deleteMemberCollection")
    public String deleteMemberCollection(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {
            this.memberService.deleteMemberCollection(request.getParameter("ids"));
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/deleteMemberCollectionByMemberId")
    public String deleteMemberCollectionByMemberId(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {
            this.mallProductService.deletreProductCollectionByMemberId(request.getParameter("memberId"),
                    request.getParameter("productId"));
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/deleteMemberMp3ByMemberId")
    public String deleteMemberMp3ByMemberId(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {
            this.mallProductService.deletreProductMp3ByMemberId(request.getParameter("memberId"),
                    request.getParameter("productId"));
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/searchMemberMp3")
    public String searchMemberMp3(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        map.put("memberId", request.getParameter("memberId"));

        map.put("mp3Type", request.getParameter("mp3Type"));
        map.put("page", queryDto.getPage());
        map.put("rowsPerPage", queryDto.getPageSize());
        Page resultPage = this.memberService.searchMemberMp3(map);
        List<Object[]> infoList = resultPage.getItems();
        List jsonList = new ArrayList();
        DecimalFormat df = new DecimalFormat("###.0");

        // 封装成JSON显示对象
        JSONObject jsonObj = new JSONObject();
        if (infoList != null) {

            for (Object[] category : infoList) {
                JSONObject jobj = new JSONObject();

                jobj.put("id", category[0]);
                jobj.put("productId", category[1]);
                jobj.put("name", category[2]);
                jobj.put("logo", category[3].toString().indexOf("http://") < 0 ? Keys.STAT_NAME + "wechat/wechatImages/mall/" + category[3] : category[3]);

                jobj.put("mp3Type", category[5]);

                if (category[4] != null && !"".equals(category[4])) {
                    jobj.put("mp3", Keys.STAT_NAME + "wechat/wechatImages/book/mp3/"
                            + category[4].toString().substring(0, category[4].toString().length() - 4));

                } else {
                    jobj.put("mp3", "");
                }

                jsonList.add(jobj);
            }
            jsonObj.put("infoList", jsonList);
            jsonObj.put("pageCount", resultPage.getTotalPageCount());
            jsonObj.put("totalCount", resultPage.getTotalCount());

        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping(value = "/searchMemberCollection")
    public String searchMemberCollection(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            queryDto.setPageSize(request.getParameter("pageSize"));
        }

        map.put("memberId", request.getParameter("memberId"));
        map.put("page", queryDto.getPage());
        map.put("rowsPerPage", queryDto.getPageSize());
        Page resultPage = this.memberService.searchMemberCollection(map);
        List<Object[]> infoList = resultPage.getItems();
        List jsonList = new ArrayList();
        DecimalFormat df = new DecimalFormat("###.0");

        // 封装成JSON显示对象
        JSONObject jsonObj = new JSONObject();
        if (infoList != null) {

            for (Object[] category : infoList) {
                JSONObject jobj = new JSONObject();

                jobj.put("id", category[0]);
                jobj.put("productId", category[1]);
                jobj.put("name", category[2]);
                jobj.put("logo", Keys.STAT_NAME + "wechat/wechatImages/mall/" + category[3]);

                jobj.put("mp3Type", category[5]);

                if (category[4] != null && !"".equals(category[4])) {
                    jobj.put("mp3", Keys.STAT_NAME + "wechat/wechatImages/book/mp3/"
                            + category[4].toString().substring(0, category[4].toString().length() - 4));

                } else {
                    jobj.put("mp3", "");
                }

                jsonList.add(jobj);
            }
            jsonObj.put("infoList", jsonList);
            jsonObj.put("pageCount", resultPage.getTotalPageCount());
            jsonObj.put("totalCount", resultPage.getTotalCount());

        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

        return null;
    }

    @RequestMapping(value = "/searchMemberInfo")
    public String searchMemberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {

            Object[] obj = this.mallProductService.searchMemberInfo(request.getParameter("memberId"));

            MemberAccount memberAccount = this.memberService
                    .searchMemberAccountByMemberId(request.getParameter("memberId"));
            Integer integralCount = this.integralService.getIntegralCount(request.getParameter("memberId"),
                    memberAccount.getType().toString());
            if (obj[3] != null) {
                if ("0".equals(obj[3].toString())) {
                    jsonStr = "{\"mp3Count\":\"" + obj[1] + "\",\"courseCount\":\"" + obj[8]
                            + "\",\"collectionCount\":\"" + obj[2] + "\",\"integralCount\":\"" + integralCount
                            + "\",\"memberType\":\"金卡会员\",\"endDate\":\"" + obj[4].toString().substring(0, 10)
                            + "\",\"bookCount\":\"" + obj[5] + "\"}";

                } else {
                    jsonStr = "{\"mp3Count\":\"" + obj[1] + "\",\"courseCount\":\"" + obj[8]
                            + "\",\"collectionCount\":\"" + obj[2] + "\",\"integralCount\":\"" + integralCount
                            + "\",\"memberType\":\"白金会员\",\"endDate\":\"" + obj[4].toString().substring(0, 10)
                            + "\",\"bookCount\":\"" + obj[5] + "\"}";

                }
            } else {
                jsonStr = "{\"mp3Count\":\"" + obj[1] + "\",\"courseCount\":\"" + obj[8] + "\",\"collectionCount\":\""
                        + obj[2] + "\",\"integralCount\":\"" + integralCount
                        + "\",\"memberType\":\"\",\"endDate\":\"\",\"bookCount\":\"" + obj[5] + "\"}";
            }

        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/saveMemberBook")
    public String saveMemberBook(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {

            Calendar calendar = new GregorianCalendar();
            MemberBook memberBook = new MemberBook();
            memberBook = this.memberService.getMemberBook(request.getParameter("memberId"));
            if (memberBook.getId() != null) {
                memberBook.setType(new Integer(request.getParameter("type")));
                calendar.setTime(memberBook.getEndDate());
                calendar.add(Calendar.YEAR, 1);
                memberBook.setEndDate(calendar.getTime());

            } else {
                memberBook.setCreateDate(new Date());
                memberBook.setMemberid(new Integer(request.getParameter("memberId")));
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

    @RequestMapping("/searchBookCount")
    public String searchBookCount(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Object[]> infoList = this.bookService.searchBookCount(request.getParameter("ids"));
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

    @RequestMapping(value = "/saveBookOrder")
    public String saveBookOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {

            String[] ids = request.getParameter("ids").split(",");
            String[] bookCodes = request.getParameter("bookCodes").split(",");

            BookOrderInfo bookOrderInfo = new BookOrderInfo();
            List<BookOrderInfo> bookList = new ArrayList();
            for (int i = 0; i < ids.length; i++) {

                bookOrderInfo = new BookOrderInfo();
                bookOrderInfo.setBarCode(bookCodes[i]);
                bookOrderInfo.setCateId(ids[i]);
                bookOrderInfo.setCreateDate(new Date());
                bookList.add(bookOrderInfo);
            }

            List<Object[]> showBookList = this.bookService.searchBooksByIds(request.getParameter("cateIds"));

            BookOrder bookOrder = new BookOrder();
            for (int i = 0; i < showBookList.size(); i++) {

                bookOrder.setBookPrice(bookOrder.getBookPrice() + 4);
                bookOrder.setDeposit(bookOrder.getDeposit() + new Double(showBookList.get(i)[4].toString()));

            }
            Object[] obj = this.mallProductService.searchMemberInfo(request.getParameter("memberId"));

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

                    bookOrder.setTotalPrice(new Double(bookOrder.getFreight() + ""));

                } else {
                    // jsonStr =
                    // "{\"mp3Count\":\""+obj[1]+"\",\"collectionCount\":\""+obj[2]+"\",\"integralCount\":\""+integralCount+"\",\"memberType\":\"白金会员\",\"endDate\":\""+obj[4].toString().substring(0,10)+"\"}";

                    bookOrder.setTotalPrice(new Double(1));

                }
            } else {
                if (showBookList.size() < 20) {
                    bookOrder.setFreight(10);
                } else if (showBookList.size() < 25) {
                    bookOrder.setFreight(15);
                } else if (showBookList.size() < 30) {
                    bookOrder.setFreight(20);
                }

                bookOrder.setTotalPrice(
                        bookOrder.getBookPrice() + bookOrder.getDeposit() + new Double(bookOrder.getFreight() + ""));

            }

            bookOrder.setCreateDate(new Date());
            bookOrder.setMemberId(new Integer(request.getParameter("memberId")));
            bookOrder.setIntegral(new Integer(request.getParameter("integral")));
            bookOrder.setAddressId(new Integer(request.getParameter("addressId")));
            bookOrder.setRemarks(request.getParameter("remarks"));
            bookOrder.setOrderNumber(request.getParameter("orderNumber"));

            this.bookOrderService.saveBookOrder(bookOrder);

            for (int i = 0; i < bookList.size(); i++) {

                bookList.get(i).setOrderId(bookOrder.getId());
                this.bookOrderService.saveBookOrderInfo(bookList.get(i));
                this.bookOrderService.deleteBookvehicle(bookList.get(i).getCateId(),
                        bookOrder.getMemberId().toString());

            }
        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/memberBookOrderManager")
    public String memberBookOrderManager(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();

        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            queryDto.setPage(request.getParameter("page"));
        }
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
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
        BookInfo bookInfo = new BookInfo();
        List tempList = new ArrayList();
        List<BookOrderList> bookList = new ArrayList();
        for (int i = 0; i < resultPage.getItems().size(); i++) {

            bookOrderList = new BookOrderList();
            bookOrderList.setMemberId(new Integer(request.getParameter("memberId")));
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
                bookInfo = new BookInfo();
                tempList = new ArrayList();
                for (int j = 0; j < tempCate.length; j++) {

                    tempBook = tempCate[j].split("<");
                    if (tempBook != null && tempBook.length >= 7) {
                        bookInfo = new BookInfo();
                        // //System.out.println("tempBook[0] = "+tempBook[0]);
                        // //System.out.println("tempBook[3] = "+tempBook[3]);
                        bookInfo.setAuthor(tempBook[3]);
                        bookInfo.setName(tempBook[1]);
                        bookInfo.setCateId(tempBook[0]);
                        bookInfo.setCover(Keys.STAT_NAME + "/wechat/wechatImages/book/" + tempBook[2]);
                        bookInfo.setPrice(tempBook[4]);
                        bookInfo.setCode(tempBook[5]);
                        bookInfo.setStatus(tempBook[6]);
                        tempList.add(bookInfo);

                    }
                    bookOrderList.setBookList(tempList);

                }

                bookOrderList.setCount(bookOrderList.getBookList().size());

            }
            bookList.add(bookOrderList);

        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("infoList", bookList);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());
        return null;

    }

    @RequestMapping("/searchMemberBook")
    public void searchMemberBook(HttpServletRequest request, HttpServletResponse response, QueryDto queryDto)
            throws Exception {

        HashMap map = new HashMap();
        String page = "1";
        String rowsPerPage = "50";
        if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
            page = request.getParameter("page");
        }
        if (!"".equals(request.getParameter("rowsPerPage")) && request.getParameter("rowsPerPage") != null) {
            rowsPerPage = request.getParameter("rowsPerPage");
        }

        map.put("page", page);
        map.put("rowsPerPage", rowsPerPage);
        map.put("memberId", request.getParameter("memberId"));
        map.put("status", request.getParameter("status"));

        Page resultPage = this.bookOrderService.searchMemberBookInfo(map);
        List<Object[]> infoList = resultPage.getItems();
        List jsonList = new ArrayList();
        JSONObject jsonObject = new JSONObject();
        JSONObject jobj = new JSONObject();
        if (infoList != null) {

            for (int i = 0; i < infoList.size(); i++) {

                jobj = new JSONObject();
                jobj.put("id", infoList.get(i)[4]);
                jobj.put("cateId", infoList.get(i)[0]);
                jobj.put("name", infoList.get(i)[1]);
                jobj.put("author", infoList.get(i)[2]);
                jobj.put("cover", Keys.STAT_NAME + "/wechat/wechatImages/book/" + infoList.get(i)[3]);
                jobj.put("barcode", infoList.get(i)[6]);
                jobj.put("status", infoList.get(i)[7]);

                jsonList.add(jobj);
            }
            jsonObject.accumulate("infoList", jsonList);
            jsonObject.put("pageCount", resultPage.getTotalPageCount());
            jsonObject.put("totalCount", resultPage.getTotalCount());

        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObject.toString());

    }

    @RequestMapping(value = "/saveMemberBookInfo")
    public String saveMemberBookInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{\"status\":\"ok\"}";
        try {

            String[] cateIds = request.getParameter("cateIds").split(",");
            String[] bookCodes = request.getParameter("bookCodes").split(",");
            String[] ids = request.getParameter("ids").split(",");

            BookExpress bookExpress = new BookExpress();
            bookExpress.setMemberId(new Integer(request.getParameter("memberId")));
            bookExpress.setCreateDate(new Date());
            bookExpress.setExpress(request.getParameter("express"));
            bookExpress.setExpressNumber(request.getParameter("expressNumber"));
            this.bookOrderService.saveBookExpress(bookExpress);

            MemberBookInfo bookOrderInfo = new MemberBookInfo();

            for (int i = 0; i < cateIds.length; i++) {

                bookOrderInfo = new MemberBookInfo();
                bookOrderInfo.setBarCode(bookCodes[i]);
                bookOrderInfo.setCateId(cateIds[i]);
                bookOrderInfo.setMemberId(new Integer(request.getParameter("memberId")));
                bookOrderInfo.setBookExpressId(bookExpress.getId());
                this.bookOrderService.saveMemberBookInfo(bookOrderInfo);
                this.bookOrderService.updateBookOrderInfoStatus(ids[i], "1");
            }

        } catch (Exception e) {
            jsonStr = "{\"status\":\"error\",\"message\":\"缺少参数!\"}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    @RequestMapping(value = "/saveBookCard")
    public String saveBookCard(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String jsonStr = "{data:{\"success\":\"1\"}}";
        try {

            BookCard bookCard = new BookCard();
            bookCard.setCreateDate(new Date());
            bookCard.setMemberId(new Integer(request.getParameter("memberId")));
            bookCard.setCard(this.getStringRandom());
            bookCard.setPrice(new Integer(request.getParameter("price")));
            this.bookCardService.saveBookCard(bookCard);
        } catch (Exception e) {
            jsonStr = "{data:{\"success\":\"0\",\"error\":{\"code\":1000}}}";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    public String getStringRandom() {

        String val = "";
        Random random = new Random();

        // 参数length，表示生成几位随机数
        for (int i = 0; i < 16; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static void main(String[] args) {

        // char[] letters =
        // {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};
        // String tStr = String.valueOf(System.currentTimeMillis());
        // //*因为tStr的字符只有‘0’-‘9’，我们可以把它看成索引到letters中找相应的字符，这样相当于“置换”，所以是不会重复的。*/
        // //System.out.println(tStr);
        // StringBuilder sb = new StringBuilder();
        // for(int i=1;i<tStr.length();i++)
        // {
        // sb.append(letters[tStr.charAt(i)-'0']);
        // }
        // //System.out.println(sb.toString());

        String val = "";
        Random random = new Random();

        // 参数length，表示生成几位随机数
        for (int i = 0; i < 16; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }

        }
        // //System.out.println(val);

    }

    @RequestMapping(value = "/updateMp3Source")
    public void updateMp3Source(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            if (!"".equals(request.getParameter("mp3Url")) && request.getParameter("mp3Url") != null) {
                String mp3Url = request.getParameter("mp3Url");
                String cateID = request.getParameter("cateID");
                InputStream in = new URL(mp3Url).openConnection().getInputStream(); // 创建连接、输入流
                FileOutputStream f = new FileOutputStream(
                        "/data/wechatImages/book/mp3/" + cateID + "/" + cateID + "0/audio/P001/P001.mp3");// 创建文件输出流
                // FileOutputStream f = new
                // FileOutputStream("E:\\data\\wechatImages\\book\\mp3\\"+
                // cateID +"\\"+cateID +"0\\audio\\P001\\P001.mp3");//创建文件输出流

                byte[] bb = new byte[1024]; // 接收缓存
                int len;
                while ((len = in.read(bb)) > 0) { // 接收
                    f.write(bb, 0, len); // 写入文件
                }
                f.close();
                in.close();
            } else {
                JsonResult.JsonResultError(response, 400);
            }
            JsonResult.JsonResultInfo(response, "ok");
        } catch (Exception e) {
            JsonResult.JsonResultError(response, 503);
        }
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // for ( int i = 1 ; i <= 100 ; i++) {
        //
        // // 初始化CommentId索引 SortSet
        // this.redisService.zadd("topicId", i, "commentId"+i);
        // // 初始化Comment数据 Hash
        // this.redisService.hset("Comment_Key","commentId"+i, "comment content
        // .......");
        // }

        // 倒序取 从0条开始取 5条 Id 数据
        LinkedHashSet<String> sets = this.redisService.zrevrangeByScore("topicId", 20, 0);
        String[] items = new String[]{};
        // //System.out.println(sets.toString());
        // 根据id取comment数据

        List<String> list = this.redisService.hmget("Comment_Key", sets.toArray(items));
        for (String str : list) {
            //System.out.println(str);
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println("");
        return null;
    }

    @RequestMapping("/bookIndex")
    public void bookIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {

        long startTime = System.currentTimeMillis(); // 获取开始时间

        Integer pagesize = 12;// 分类显示记录数
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            pagesize = new Integer(request.getParameter("pageSize"));
        }
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();

        BookIndex bookIndex = new BookIndex();// 一级集合实体
        Object[] tempBooKIndex = new Object[2];// 一级集合对象

        BookIndexInfo bookIndexInfo = new BookIndexInfo();// 二级集合实体
        Object[] tempBooKIndexInfo = new Object[2];// 二级集合对象

        Object[] tempBookInfo = new Object[3];// 书籍集合对象

        TempBook tempBook = new TempBook(); // 书籍实体
        Object[] tempBookObject = new Object[3];// 书籍对象

        try {

            if (this.redisService.exists(RedisKeys.APP_BOOK_INDEX)) {
                List<T> dataList = this.redisService.getList(RedisKeys.APP_BOOK_INDEX, BookIndex.class);
                data.put("dataList", dataList);

            } else {

                List<BookIndex> dataList = new ArrayList();
                List<Object[]> appIndexList = this.bookService.searchBooKIndexInfo();

                for (int i = 0; i < appIndexList.size(); i++) {// 一级分类集合

                    bookIndex = new BookIndex();
                    bookIndex.setTitle(appIndexList.get(i)[0].toString());

                    if (appIndexList.get(i)[1] != null) {

                        tempBooKIndex = appIndexList.get(i)[1].toString().split("∪");

                        for (int j = 0; j < tempBooKIndex.length; j++) {// 二级分类集合

                            tempBooKIndexInfo = tempBooKIndex[j].toString().split("⊥");

                            bookIndexInfo = new BookIndexInfo();
                            bookIndexInfo.setId(new Integer(tempBooKIndexInfo[0].toString()));// 二级分类ID
                            bookIndexInfo.setTitle(tempBooKIndexInfo[1].toString());// 二级分类名称

                            tempBookInfo = tempBooKIndexInfo[2].toString().split("∩");// 书籍集合

                            for (int k = 0; k < tempBookInfo.length && k < pagesize; k++) {

                                tempBookObject = tempBookInfo[k].toString().split("⊙");// 书籍属性
                                tempBook = new TempBook();
                                tempBook.setCateId(tempBookObject[0].toString());

                                if (tempBookObject.length >= 2) {
                                    tempBook.setName(tempBookObject[1].toString());
                                } else {
                                    tempBook.setName("");
                                }
                                if (tempBookObject.length >= 3) {
                                    tempBook.setAuthor(tempBookObject[2].toString());
                                } else {
                                    tempBook.setAuthor("");
                                }

                                if (tempBookObject.length >= 4) {
                                    tempBook.setCover(Keys.STAT_NAME + "wechat/wechatImages/book/"
                                            + tempBookObject[3].toString());
                                } else {
                                    tempBook.setCover("");
                                }

                                bookIndexInfo.getBookList().add(tempBook);
                            }

                            bookIndex.getInfoList().add(bookIndexInfo);
                        }

                    }
                    dataList.add(bookIndex);

                }

                this.redisService.setList(RedisKeys.APP_BOOK_INDEX, dataList);
                data.put("dataList", dataList);
            }

            long endTime = System.currentTimeMillis(); // 获取结束时间
            //System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
            data.put("success", 1);

            jsonObj.put("data", data);

        } catch (Exception e) {

            jsonObj = new JSONObject();
            data = new JSONObject();
            JSONObject error = new JSONObject();
            data.put("success", 0);
            error.put("code", 1000);
            data.put("error", error);
            jsonObj.put("data", data);
            e.printStackTrace();

        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());

    }

    @RequestMapping("/bookLabelInfo")
    public void bookLabelInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        long startTime = System.currentTimeMillis(); // 获取开始时间

        Integer pagesize = 12;// 分类显示记录数
        if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
            pagesize = new Integer(request.getParameter("pageSize"));
        }
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();

        Object[] tempObject = new Object[2];// 书籍集合对象
        TempBook tempBook = new TempBook(); // 书籍实体
        Object[] tempBookObject = new Object[3];// 书籍对象
        BookLabelIndex bookLabelIndex = new BookLabelIndex();
        try {

            if (this.redisService.exists(RedisKeys.APP_BOOK_LABEL)) {
                List<T> dataList = this.redisService.getList(RedisKeys.APP_BOOK_LABEL, BookLabelIndex.class);
                data.put("dataList", dataList);

            } else {
                List<Object[]> labelList = this.bookService.searchBookLabelInfo();
                List<BookLabelIndex> dataList = new ArrayList();
                // 分隔符://⊥ ∪
                for (int i = 0; i < labelList.size(); i++) {// 标签集合

                    bookLabelIndex = new BookLabelIndex();
                    bookLabelIndex.setId(new Integer(labelList.get(i)[0].toString()));
                    bookLabelIndex.setTitle(labelList.get(i)[1].toString());
                    if (labelList.get(i).length >= 3) {
                        tempObject = labelList.get(i)[2].toString().split("∪");

                        for (int k = 0; k < tempObject.length && k < pagesize; k++) {// 书籍集合

                            tempBookObject = tempObject[k].toString().split("⊥");// 书籍属性
                            tempBook = new TempBook();
                            tempBook.setCateId(tempBookObject[0].toString());

                            if (tempBookObject.length >= 2) {
                                tempBook.setName(tempBookObject[1].toString());
                            } else {
                                tempBook.setName("");
                            }
                            if (tempBookObject.length >= 3) {
                                tempBook.setAuthor(tempBookObject[2].toString());
                            } else {
                                tempBook.setAuthor("");
                            }

                            if (tempBookObject.length >= 4) {

                                tempBook.setCover(
                                        Keys.STAT_NAME + "wechat/wechatImages/book/" + tempBookObject[3].toString());
                            } else {
                                tempBook.setCover("");
                            }

                            bookLabelIndex.getBookList().add(tempBook);
                        }

                        dataList.add(bookLabelIndex);

                    }

                }

                data.put("dataList", dataList);
                this.redisService.setList(RedisKeys.APP_BOOK_LABEL, dataList);
            }

            long endTime = System.currentTimeMillis(); // 获取结束时间
            //System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
            data.put("success", 1);

            jsonObj.put("data", data);

        } catch (Exception e) {
            jsonObj = new JSONObject();
            data = new JSONObject();
            JSONObject error = new JSONObject();
            data.put("success", 0);
            error.put("code", 1000);
            data.put("error", error);
            jsonObj.put("data", data);
            e.printStackTrace();
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObj.toString());
    }

    /**
     * 查询书籍信息
     *
     * @param request
     * @param response
     */
    @RequestMapping("searchBook")
    public void searchBook(HttpServletRequest request, HttpServletResponse response) {
        try {

            String page = "1";
            String rowsPerPage = "20";
            if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
                page = request.getParameter("page");
            }
            if (!"".equals(request.getParameter("pageSize")) && request.getParameter("pageSize") != null) {
                rowsPerPage = request.getParameter("pageSize");
            }

            HashMap map = new HashMap();
            map.put("name", request.getParameter("name"));
            map.put("labelId", request.getParameter("labelId"));
            map.put("searchType", request.getParameter("searchType"));
            map.put("page", page);
            map.put("rowsPerPage", rowsPerPage);

            Page resultPage = this.bookService.searchBook(map);

            List jsonList = new ArrayList();
            List<Object[]> infoList = resultPage.getItems();
            JSONObject jsonObj = new JSONObject();
            if (infoList != null) {
                JSONArray AgentKeyWordInfo = new JSONArray();

                for (Object[] obj : infoList) {
                    JSONObject jobj = new JSONObject();

                    jobj.put("cateId", obj[0]);
                    jobj.put("name", obj[1]);
                    if (obj.length > 2) {
                        if (obj[2] != null) {
                            jobj.put("publish", obj[2]);
                        } else {
                            jobj.put("publish", "");
                        }
                    } else {
                        jobj.put("publish", "");
                    }
                    if (obj.length > 3) {
                        if (obj[3] != null) {
                            jobj.put("author", obj[3]);
                        } else {
                            jobj.put("author", "");
                        }
                    } else {
                        jobj.put("author", "");
                    }
                    if (obj.length > 4) {
                        if (obj[4] != null) {
                            jobj.put("cover", Keys.STAT_NAME + "/wechat/wechatImages/book/" + obj[4]);
                        } else {
                            jobj.put("cover", "");
                        }
                    } else {
                        jobj.put("cover", "");
                    }

                    jsonList.add(jobj);
                }
                jsonObj.put("dataList", jsonList);
                jsonObj.put("totalCount", resultPage.getTotalCount());
                jsonObj.put("pageCount", resultPage.getTotalPageCount());
            }

            jsonObj.put("success", 1);

            JsonResult.JsonResultInfo(response, jsonObj);

        } catch (Exception e) {
            JsonResult.JsonResultError(response, 1000);
        }

    }

}
