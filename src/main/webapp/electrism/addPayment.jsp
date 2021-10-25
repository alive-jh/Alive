<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>订单附加费</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/wapcss/addrSelect.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/addrSelectSin.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/swiper.css" />
		<script src="<%=request.getContextPath()%>/js/swiper.js"></script>
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script>

		

	
			

	function addPayment()
	{
		document.forms['mallForm'].action = "<%=request.getContextPath()%>/electrism/addOrderPayment";
		document.forms['mallForm'].submit();
	}

		</script>
	</head>
	<body>
		<div class="content">
			<!--头部开始
			<div class="header">
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					地址管理
				</h3>
				<div class="head-right">
					
				</div>
			</div>
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body no-padding">
			<form name="mallForm" method="post" action="<%=request.getContextPath()%>/member/saveAddress">
				<input type="hidden" name="memberId" value="${memberId}">
				<input type="hidden" name="orderId" value="${orderId}">
				<div class="fill-addr">
					<ul>
						<li class="clearfix">
							<span>金额</span>
							<input name="payment"   type="text" placeholder="请输入附加金额" />
						</li>
						<li class="clearfix">
							<span>备注</span>
							<input name="remarks" type="text" placeholder="请输入费用备注" />
						</li>
						
						
						
					</ul>
				</div>
				</form>
			</div>
			<!--主要部分结束-->
			
			<!--时间选择器开始-->
			<div id="mask"></div>
		
			
			
			<!--底部菜单开始-->
			<div class="control-btn fixed-block">
					
						<input type="button" value="提交" onclick="addPayment()"/>
					 
					
					
			</div>
			<!--底部菜单结束-->
		</div>
	</body>
		
		
	
  
</html>
