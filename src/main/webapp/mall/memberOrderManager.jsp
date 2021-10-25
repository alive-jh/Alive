<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>订单列表</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillOrder.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillOrder.css" />
		
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

			<meta name="viewport" content="user-scalable=yes, width=640">
		
			 <script src="js/hm.js"></script><script type="text/javascript">
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
		        function updateOrderStatus(id,memberId)
		        {
		        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/updateOrderStatus?&orderId="+id+"&memberId="+memberId;
		        	document.forms['orderForm'].submit();
		        }
		        
		        function updateOrderRefund(id,memberId)
		        {
		        	alert('您的退款请求已取消!');
		        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/updateOrderRefund?&orderId="+id+"&memberId="+memberId;
		        	document.forms['orderForm'].submit();
		        }
		         function orderRefund(id,memberId)
		        {
		        	if(confirm('你确定要退款吗？'))
					{
		        		alert('您的订单退款已提交,客服将在24小时内和您联系!');
		        		document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/orderRefund?status=4&orderId="+id+"&memberId="+memberId;
		        		document.forms['orderForm'].submit();
					}
		        }
		        function toComment(id,memberId)
		        {
		        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/toComment?&orderId="+id+"&memberId="+memberId;
		        	document.forms['orderForm'].submit();
		        }
		    </script>
		    
		    
		    <meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content no-margin">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				
				<div class="goods-list order-list">
					<ul>
					<form name="orderForm" method="post">
					
						<c:forEach items="${memberList}" var="temp"> 
						<li class="clearfix">
						
							<h3 class="order-num clearfix"><span class="left">订单编号：<font color="red"><i>${temp.orderNumber}</i></font></span><span class="right">
							
							<c:if test="${temp.status ==1}">待发货</c:if>
							<c:if test="${temp.status ==2}">待收货</c:if>
							<c:if test="${temp.status ==3}">交易完成</c:if>
							<c:if test="${temp.status ==4}">退款中</c:if>
							<c:if test="${temp.status ==5}">已退款</c:if>
							</span></h3>

						<c:forEach items="${temp.productList}" var="tempProduct"> 
						

							<div class='goods-box'>
								<div class="order-info">
									<div class="goods-img">
										<a href="<%=request.getContextPath() %>/member/memberOrderManager?memberId=${temp.memberId}&orderId=${temp.id}">
									<img src="<%=request.getContextPath() %>/wechatImages/mall/${tempProduct.productImg}" />
								</a>
									</div>
									<div class="goods-info">
										<a href="<%=request.getContextPath() %>/member/memberOrderManager?memberId=${temp.memberId}&orderId=${temp.id}">
									
										<h3>
										<span class="goods-name">${tempProduct.productName}</span>
										<span class="goods-type2">${tempProduct.specifications}</span>
										</h3>
										<div class="goods-price">
											<span>￥<strong class="price-num">
											<fmt:formatNumber type="number" value="${tempProduct.price}" pattern="0.00" maxFractionDigits="2"/>  
											</strong></span>
										</div>
										</a>
									</div>
									<div class="goods-num-text">
										<span>×<i>${tempProduct.count}</i></span>
									</div>
								</div>
								<div class="control-btn clearfix">
									
									<c:if test="${temp.status ==3}">	
									<input type="button" onclick="toComment(${tempProduct.id},${temp.memberId},1)" value="评价" class="evaluate"/>
									</c:if>
								</div>
							</div>
							
							</c:forEach>


							<div class="control-btn clearfix">
								共计<font color="red">${temp.productCount}</font>件商品 实付:<font color="red">￥
								<fmt:formatNumber type="number" value="${temp.totalPrice - temp.couponsMoney}" pattern="0.00" maxFractionDigits="2"/> 
								</font> 
							</div>
							<div class="control-btn clearfix">
							
							<c:if test="${temp.status ==2}">
							<a href="http://m.kuaidi100.com/index_all.html?type=${temp.express}&postid=${temp.expressNumber}#result">
							<input type="button" value="查看物流" class="confirm"/>
							</a>
							&nbsp;<input type="button" value="申请退款" onclick="orderRefund(${temp.id},${temp.memberId})"class="confirm"/>
							&nbsp;
								<input type="button" value="确认收货" onclick="updateOrderStatus(${temp.id},${temp.memberId})"class="confirm"/>
							</c:if>
							<c:if test="${temp.status ==1}">
								<input type="button" value="申请退款" onclick="orderRefund(${temp.id},${temp.memberId})"class="confirm"/>
							</c:if>
							<c:if test="${temp.status ==4}">
								<input type="button" value="取消退款" onclick="updateOrderRefund(${temp.id},${temp.memberId})"class="confirm"/>
							</c:if>
							</div>
						</li>

						</c:forEach>
						</form>
					</ul>
				</div>
				
				
			</div>
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
		<br/><br/><br/>

		<!--<jsp:include page="/sitemesh/mobileFooter.jsp" flush="true"/>-->
	</body>
</html>
