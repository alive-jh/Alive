<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>查看订单详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="viewport" content="user-scalable=yes, width=640">
		<script type="text/javascript">
		        if(/Android (\d+\.\d+)/.test(navigator.userAgent)){
		            var version = parseFloat(RegExp.$1);
		            if(version>2.3){
		                var phoneScale = parseInt(window.screen.width)/640;
		                document.write('<meta name="viewport" content="width=640, minimum-scale = '+ phoneScale +', maximum-scale = '+ phoneScale +', target-densitydpi=device-dpi">');
		            }else{
		                document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
		            }
		        }else{
		            document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
		        }


				function saveOrder()
				{
					document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/memberPayment";
					document.forms['mallForm'].submit();
				}
		    </script><meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillOrder.css" />
		<script src="<%=request.getContextPath()%>js/zepto.js"></script>
		
	</head>
	<script type="text/javascript">

	
        
	</script>
	<body>
		<div class="content">
			<!--头部开始-->
			<div class="header">
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					确认订单
				</h3>
				<!--<div class="head-right">
					<a href="javascript:;"></a>
				</div>-->
			</div>
			<!--头部结束-->
			<!--主要部分开始-->
			<form name="mallForm" method="post" action="">
			<input type="hidden" name="productId" value="${productId}">  
			<div class="content-body">
				<div class="goods-list">
					<h3 class="sub-title">商品清单</h3>
					<ul>
						<c:forEach items="${mallOrder.productList}" var="tempProduct">
						<li class="clearfix">
							<div class="goods-img">
								<img src="images/${tempProduct.productImg}" />
							</div>
							<div class="goods-info">
								<h3>
									<span class="goods-name">${tempProduct.productName}</span><br/>
									规格:<span class="goods-type1">${tempProduct.specifications}</span>
									
								</h3>
								<div class="goods-price">
									<span>￥<strong class="price-num">${tempProduct.price}</strong></span>
								</div>
							</div>
							<div class="goods-num-text">
								<span>×<i>${tempProduct.count}</i></span>
							</div>
						</li>
					
					</c:forEach>
					</ul>
				</div>
				
				<div class="consignee-info info-block">
					<h3 class="sub-title">收货人信息<a href="javascript:toUserAddress()">选择地址</a></h3>
					<div class="info-text">
						<span>收货人：${userAddress.userName}</span>&nbsp;&nbsp;&nbsp;<span>${userAddress.mobile}</span><br/>
						<span>${userAddress.area}&nbsp;${userAddress.address}&nbsp;</span>
						
						
					</div>
				</div>
				<!--
				<div class="delivery-info info-block">
					<h3 class="sub-title">配送信息</h3>
					<div class="info-text">
						<span>配送时间</span>
						<span>XXX</span>
						<span>XXX</span>
						<a class="arrow" href="javascript:;"></a>
					</div>
				</div>
				-->
				<div class="payment-info info-block">
					<h3 class="sub-title">支付信息</h3>
					<div class="info-text">
						<span>优惠券</span>
						
						<a class="arrow" href="javascript:;"></a>
					</div>
					<div class="info-text">
						<span>使用积分抵扣</span>
						
						<a class="arrow" href="javascript:;"></a>
					</div>
				</div>
				<!--
				<div class="debit-info info-block">
					<h3 class="sub-title">发票信息</h3>
					<div class="info-text">
						<span>是否需要发票</span>
						<span>XXX</span>
						<span>XXX</span>
						<a class="arrow" href="javascript:;"></a>
					</div>
				</div>
				-->
				<div class="remark-info info-block">
					<h3 class="sub-title">备注</h3>
					<div class="info-text">
						<input type="text" style="width:210px;" name="remarks">
						
					</div>
				</div>
				
				<div class="price-list info-block">
					<h3 class="sub-title">价格清单</h3>
					<div class="info-text">
						<span>商品总价格</span>
						<div class="goods-price-text acitve">
							<span>￥<i>26.80</i></span>
						</div>
					</div>
					<div class="info-text">
						<span>运费</span>
						<div class="goods-price-text acitve">
							<span>￥<i>0</i></span>
						</div>
					</div>
					<div class="info-text">
						<span>积分抵扣</span>
						<div class="goods-price-text">
							<span>￥<i>0</i></span>
						</div>
					</div>
					<div class="info-text">
						<span>优惠券抵扣</span>
						<div class="goods-price-text">
							<span>￥<i>0</i></span>
						</div>
					</div>
				</div>
				<div class="all-price fixed-block">
					<h3>合计:<span>￥</span><strong class="all-price-num">26.80</strong>
						<a href="javascript:dopay()">提交订单</a>
					</h3>
				</div>
			</div>
			</form>
			<!--主要部分结束-->
			<!--底部菜单开始-->
			<!--<div class="footer-nav fixed-block">
				<ul class="clearfix">
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
