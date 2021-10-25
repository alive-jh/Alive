<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>我的优惠卷</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/ticket.css" /> 
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		
	</head>
	<body>
		<!--<div class="content">
			头部开始
			<div class="header">
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					优惠券
				</h3>
				<!--<div class="head-right">
					<a href="javascript:;"></a>
				</div>
			</div>-->
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<ul class="tickets">

				<c:forEach items="${tempList}" var="coupons">
					

					
					<c:if test="${coupons[6] ==0}"><li></c:if>
					<c:if test="${coupons[6] !=0}"><li class="disable"></c:if>


						<c:if test="${coupons[6] ==0}">
						<c:if test="${type == 'order'}">
							<a href="<%=request.getContextPath()%>/member/toSaveOrder?couponsId=${coupons[0]}&memberId=${memberId}&productId=${productId}" class="clearfix border-img">
						</c:if>
						<c:if test="${type != 'order'}">
							<a href="#" class="clearfix border-img">
						</c:if>
						</c:if>
						<c:if test="${coupons[6] ==1}">
						<a href="#" class="clearfix border-img">
						</c:if>
						<c:if test="${coupons[6] ==2}">
						<a href="#" class="clearfix border-img">
						</c:if>
							<div class="ticket-text">
								<h3>${coupons[1]}</h3>
								<h4>
								<c:if test="${coupons[4] ==0}">无使用门槛</c:if>
								<c:if test="${coupons[4] ==1}">满${coupons[2]}元可用</c:if>
								</h4>
								<p>有效期至:<fmt:formatDate value="${coupons[5]}" type="date" dateStyle="long"/><span></span></p>
							</div>
							<div class="ticket-price">
								<h3>￥<span>${coupons[3]}</span></h3>
								<c:if test="${coupons[6] ==0}"><p>立即使用</p></c:if>
								<c:if test="${coupons[6] ==1}"><p>已使用</p></c:if>
								<c:if test="${coupons[6] ==2}"><p>已过期</p></c:if>
								
							</div>


						</a>
					</li>
					
					</c:forEach>
				</ul>
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始
			<div class="footer-nav">
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
			</div>
			<!--底部菜单结束-->
		</div>
	</body>
</html>
