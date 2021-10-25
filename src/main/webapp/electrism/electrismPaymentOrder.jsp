<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>我的余额</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/myBill.css" />	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.telNum.js"></script>
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
		        
		        
		        function toAddPayment(id)
		        {

					document.forms['orderForm'].action ="toAddPayment?orderId="+id+"&memberId=${member.id}";
					document.forms['orderForm'].submit();
						
					
		        }
				 function updateOrderPayment(id)
		        {

					document.forms['orderForm'].action ="updateOrderPayment?orderId="+id+"&memberId=${member.id}";
					document.forms['orderForm'].submit();	
					
		        }
		        
		        
				function toMobile(mobile)
				{
					window.location.href = mobile;
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
				<div class="total-num">
					<div class="num-text">
						<span><fmt:formatNumber type="number" value="${sumPayment}" pattern="0.00" maxFractionDigits="2"/></span>元
					</div>
					<div class="type-text">
						<span>
						<c:if test="${electrism.paymentStatus ==0}">
						<a href="<%=request.getContextPath()%>/electrism/updatePaymentStatus?electrismId=${electrism.id}&status=1&memberId=${memberId}">提现</a>
						</c:if>
						<c:if test="${electrism.paymentStatus ==1}">
						 <a href="<%=request.getContextPath()%>/electrism/updatePaymentStatus?electrismId=${electrism.id}&status=0&memberId=${memberId}">取消</a>
						</c:if>
						</span>
					</div>
				</div>
				<h3 class="sub-title-middle"><span>收支明细</span></h3>
				<ul class="num-list-box">
				
				<c:forEach items="${resultPage.items}" var="orderPayment">
					<li>
						<a class="clearfix" href="javascript:void(0);">
							<div class="list-left">
								<h3>+<span><fmt:formatNumber type="number" value="${orderPayment.payment}" pattern="0.00" maxFractionDigits="2"/>  </span></h3>
								<p>
								<c:if test="${orderPayment.serviceItem == 1}">上门服务</c:if>
									<c:if test="${orderPayment.serviceItem == 2}">电器维护</c:if>
									<c:if test="${orderPayment.serviceItem == 3}">线路维护</c:if>
									<c:if test="${orderPayment.serviceItem == 4}">其他服务</c:if></p>
							</div>
							<div class="list-right">
								<p>电工账户</p>
								<p><span><fmt:formatDate value="${orderPayment.createDate}" type="both"/>  </p>
							</div>
						</a>
					</li>
				</c:forEach>
				</ul>
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
	<script>
		$(function(){
			$('.tel-num').telNum();
		})
	</script>
</html>

