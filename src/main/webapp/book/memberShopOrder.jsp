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
		        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/updateOrderStatus?&orderId="+id+"&memberId="+memberId;
		        	document.forms['orderForm'].submit();
		        }
		         function orderRefund(id,memberId)
		        {
			        if(confirm('你确定要退款吗？'))
			        {
			        	alert('您的订单退款已提交,金额将于3个工作日内原路退回!');
			        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/orderRefund?status=4&orderId="+id+"&memberId="+memberId;
			        	document.forms['orderForm'].submit();
		        	}
		        }
		        
		        function toComment(id,memberId)
		        {
		        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/toComment?orderId="+id+"&memberId="+memberId;
		        	document.forms['orderForm'].submit();
		        }

				function fillRefund(id,memberId)
		        {
		        	document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/fillRefund?orderId="+id+"&memberId="+memberId;
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
					
						<c:forEach items="${bookList}" var="temp"> 
						<li class="clearfix">
						
							<h3 class="order-num clearfix"><span class="left">订单编号：<font color="red"><i>${temp.orderNumber}</i></font></span><span class="right">
							
							<c:if test="${temp.status ==0}">借阅中</c:if>
							<c:if test="${temp.status ==1}">已还书</c:if>
							
							</span></h3>

						<c:forEach items="${temp.bookList}" var="tempCategory"> 
						

							<div class='goods-box'>
								<div class="order-info">
									<div class="goods-img">
										<!--<a href="<%=request.getContextPath() %>/member/memberOrderManager?memberId=${temp.memberId}&orderId=${temp.id}">-->
									<img src="${tempCategory.cover}" />
										<!--</a>-->
									</div>
									<div class="goods-info">
										<!--<a href="<%=request.getContextPath() %>/member/memberOrderManager?memberId=${temp.memberId}&orderId=${temp.id}">-->
									
										<h3>
										<span class="goods-name">${tempCategory.name}</span><br/>
										<span class="goods-type2">${tempCategory.author}</span>
										</h3>
										<!--
										</a>-->
									
									</div>
								<!--	<div class="goods-num-text">
										  <span>×<i>${tempCategory.count}</i></span>
									    </div>
									-->
								</div>
								
							</div>
							
							</c:forEach>


						
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
