<%@ page language="java" import="com.wechat.util.Keys" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>领取优惠卷</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/ticket2.css" /> 
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		
	</head>


	<script>

	function toMall()
	{
		
		window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=Keys.APP_ID%>&redirect_uri=<%=Keys.STAT_NAME%>/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=mall#wechat_redirect";
	}
	</script>
	<body>
		<div class="content">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body no-padding">
				<div class="ticket-header clearfix">
					<img src="${memberImg}" />
					<p class="header-text">我领到双蛋红包啦~</p>
				</div>
				<div class="ticket-content">
					<div class="ticket-msg clearfix">
						<div class="ticket-left"></div>
						<div class="ticket-right">
							<h1><span>${coupons.money}</span>元</h1>
							<c:if test="${coupons.type == 0}"><p>无使用门槛</p></c:if>
							<c:if test="${coupons.type == 1}"><p>订单满<span>${coupons.price}</span>元可用</p></c:if>
							
						</div>
					</div>
					
					<p>有效期:&nbsp;<font color="black">即日起</font>&nbsp;至&nbsp;<font color="black"><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${coupons.endDate}" /></font></p>
					
					<input type="button" value="立即使用" onclick="toMall()"/>
					<div class="cloud-img">
						<img src="<%=request.getContextPath()%>/images/cloud.png" />
					</div>
				</div>
				
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始-->
		
			<!--底部菜单结束-->
		</div>
	</body>
</html>