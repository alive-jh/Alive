
<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>我的豆豆币</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/scoreDetail.css" />
		<script src="js/zepto.js"></script>
		
	</head>
	<body>
		<div class="content no-margin">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body no-padding">
				<div class="score-header">
					<p>当前可用豆豆币共计：<span>${integralCount}</span><span>豆豆币</span><i class="right"></i></p>
				</div>
				<div class="main-info">
					<ul>
					<c:forEach items="${tempList}" var="temp">
						<li>
							<div class="score-info">
								<!--<img src="images/card.png" />-->
								<p>${temp[5]}</p>
								<span class="score-num right">${temp[3]}</span>
							</div>
							<div class="score-time">
								<p><fmt:formatDate value="${temp[4]}" pattern="YYYY-MM"/></p>
								<span><fmt:formatDate value="${temp[4]}" pattern="dd"/></span>
							</div>
						</li>
					</c:forEach>	
					</ul>
				</div>
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始-->
			<!--<div class="footer-nav">
				<ul class="clearfix fixed-block">
					<li>
						<a href="index.html">
							<span></span>
							<p>首页</p>
						</a>
					</li>
					<li>
						<a href="order.html">
							<span></span>
							<p>订单</p>
						</a>
					</li>
					<li>
						<a href="me.html">
							<span></span>
							<p>我的</p>
						</a>
					</li>
					<li>
						<a href="more.html">
							<span></span>
							<p>更多</p>
						</a>
					</li>
				</ul>
			</div>-->
			<!--底部菜单结束-->
		</div>
	</body>
</html>
