<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<link rel="stylesheet" href="css/me.css" />

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
        <!--<div class="search-box">
            <button class="head-left-btn"></button>
            <h1>个人中心</h1>
		</div>-->
		
		<div class="head-info-box">
			<div class="name-info">
				<img src="${member.headImgUrl}" />
				<div class="name-text">
					<span>${member.nickName}</span>
					<!--<input type="text" value="1"/>-->
				</div>
			<!--	<div class="options">
					<i class="bell"></i>
					<i class="config"></i>
				</div>-->
			</div>
			<div class="money-gift money-gift-ticket">
				<ul class="clearfix">
					<li>
						<a href="<%=request.getContextPath()%>/book/toBookPayment?memberId=${member.id}&integralCount=${integralCount}">
							<i class="ticket"></i>
							<div class="text-num">
								<p>余额</p>
								<h3>${integralCount}</h3>
							</div>
						</a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/book/memberBookOrderManager?memberId=${member.id}">
							<i class="money"></i>
							<div class="text-num">
								<p>订单</p>
								
							</div>
						</a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/book/toBookStill?memberId=${member.id}">
							<i class="gift"></i>
							<div class="text-num">
								<p>还书</p>
								
							</div>
						</a>
					</li>
				</ul>
			</div>
			
		</div>
		
		<!--<div class="new-info-box my-book-list">
			<h3>订阅更新<i class="left-arrow"></i></h3>
			<ul>
                <li class="no-more-list">
                    <a class="clearfix" href="javascript:void(0)">
                        暂无订阅更新
                    </a>
                </li>
				<li>
					<a class="clearfix" href="javascript:void(0)">
						<img src="images/book_01.jpg" />
						<div class="book-info">
							<h3 class="book-name">春日便当</h3>
							<p>(日)吉井忍</p>
							<i class="donwload-icon"></i>
						</div>
					</a>
				</li>
				<li>
					<a class="clearfix" href="javascript:void(0)">
						<img src="images/book_01.jpg" />
						<div class="book-info">
							<h3 class="book-name">春日便当</h3>
							<p>(日)吉井忍</p>
							<i class="donwload-icon"></i>
						</div>
					</a>
				</li>
				<li>
					<a class="clearfix" href="javascript:void(0)">
						<img src="images/book_01.jpg" />
						<div class="book-info">
							<h3 class="book-name">春日便当</h3>
							<p>(日)吉井忍</p>
							<i class="donwload-icon"></i>
						</div>
					</a>
				</li>
			</ul>
		</div>
		-->
		<div class="own-info-box my-book-list">
			<h3>我拥有的<label>共<span>${bookCount}</span>部</label><i class="left-arrow"></i></h3>
			<ul>
			<c:forEach items="${bookList}" var="tempBook">
				<li>

					<a class="clearfix" href="javascript:void(0)">
						<img src="<%=request.getContextPath()%>/wechatImages/book/${tempBook[3]}" />
						<div class="book-info">
							<h3 class="book-name">${tempBook[1]}</h3>
							<p>${tempBook[2]}</p>
							<!--<i class="donwload-icon"></i>-->
						</div>
					</a>
				</li>
			</c:forEach>
			</ul>
		</div>
		
		
		
		

        <!--menu-group-->
      <div class="menu-group off">
            <ul class="clearfix">
                <li class="switch-btn">
                    <a href="#">

                    </a>
                </li>
                <li class="shop-btn">
                     <a href="<%=request.getContextPath()%>/book/bookMobileManager?memberId=${memberId}">

                    </a>
                </li>
                <li class="me-btn">
                    <a href="<%=request.getContextPath()%>/book/memberBookInfo?memberId=${memberId}">


                    </a>
                </li>
                <li class="local-btn">
                    <a href="<%=request.getContextPath()%>/book/bookVehicleManager?memberId=${memberId}">

                    </a>
                </li>
            </ul>
        </div>
        <!--menu-group-end-->
	</body>


</html>
