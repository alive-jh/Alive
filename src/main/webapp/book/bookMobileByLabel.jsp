<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html><!DOCTYPE html>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
		<title>图书App</title>
		
		<link rel="stylesheet" href="plug/css/jq-plug.css" />
		<link rel="stylesheet" href="lib/css/font-awesome.min.css" />
        <link rel="stylesheet" href="css/common.css" />
		<link rel="stylesheet" href="plug/css/swiper.css" />
        <link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/bookList.css" />

		<script src="lib/js/jquery-2.1.3.min.js"></script>
		<script src="lib/js/iscroll.js"></script>
		<script src="plug/js/jq-plug.js"></script>
		<script src="js/common.js"></script>

		
	</head>
	<body>
		<!--search-->
		<!--<div class="search-box fixed-bolck">
			<input type="search" placeholder="自出版/图书/杂志"/>
			<button class="book-type-btn"></button>
		</div>-->
        <div class="search-box" style="position: fixed;">
            <button class="head-left-btn"></button>
            <h1>${title}</h1>
		</div>
		
		
		
		<!--book-list-->
        <div class="other-box like-box" id="bookList" style="padding-top:46px;">
            <div class="swiper-object">
                <ul class="book-list clearfix">

					<c:forEach items="${infoList}" var="tempBook">
                    <li>
                        <a href="javascript:void(0);" class="clearfix">
                            <b class="list-num">1</b>
                            <img src="${tempBook[3]}" alt="图1"/>
                            <div class="info-box">
                                <h3 class="book-name">${tempBook[1]}</h3>
                               <!--<h4 class="book-subname">副标题副标题副标题副标题副标题副标题</h4>--> 
                                <p class="author-name">${tempBook[2]}</p>
                                <div class="book-star clearfix">
                                    <ul class="clearfix">
                                       ${tempBook[7]}
                                    </ul>
                                    <b>${tempBook[5]}</b>
                                    <span>(0评价)</span>
                                </div>
                                <div class="book-short">${tempBook[4]}</div>
                            </div>
                        </a>
                    </li>
                   </c:forEach>


                </ul>

            </div>

        </div>
        <!--book-list-end-->

        <!--menu-group-->
        <div class="menu-group off">
            <ul class="clearfix">
                <li class="switch-btn">
                    <a href="javascript:void(0);">

                    </a>
                </li>
                <li class="shop-btn">
                    <a href="javascript:void(0);">

                    </a>
                </li>
                <li class="me-btn">
                    <a href="javascript:void(0);">

                    </a>
                </li>
                <li class="local-btn">
                    <a href="javascript:void(0);">

                    </a>
                </li>
            </ul>
        </div>
        <!--menu-group-end-->
	</body>


</html>


