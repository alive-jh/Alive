<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
		<title>我的余额</title>
		
		<link rel="stylesheet" href="plug/css/jq-plug.css" />
		<link rel="stylesheet" href="lib/css/font-awesome.min.css" />
        <link rel="stylesheet" href="css/common.css" />
		<link rel="stylesheet" href="plug/css/swiper.css" />
        <link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/myMoney.css" />

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
        
		
        <!--page-center-->
        <div class="page-center">
            <div class="d-money-block money-bolck">
                <h3><span class="d-icon-bf">${integralCount}</span></h3>
                <a href="<%=request.getContextPath()%>/book/toRecharge?memberId=${memberId}" class="recharge-btn">充值</a>
                <ul class="recharge-info-btns clearfix">
                    <li>
                        <a href="<%=request.getContextPath()%>/book/integralManager?memberId=${memberId}&typeId=1" class="recharge-record"><span>充值记录</span></a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" class="recharge-matter"><span>充值遇到问题</span></a>
                    </li>
                </ul>
            </div>
		<!-- 
            <div class="r-money-block money-bolck">
                <h3 class="disabled">￥<span>0.00</span></h3>
                <p class="tips">可用于在豆瓣阅读网站购买作品</p>
            </div>
 		-->
            <div class="expend-info">
                 <a href="<%=request.getContextPath()%>/book/integralManager?memberId=${memberId}&typeId=3" class="recharge-record">消费记录
                <i class="left-arrow"></i>
                </a>
            </div>
        </div>
        <!--page-center-end-->

		
		
		

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
