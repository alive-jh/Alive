<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>${electrism.name}</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/electricianDetail.css" />
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		
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
		    </script><meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
		<script src="<%=request.getContextPath()%>/js/tabPlugin.js"></script>
	</head>
	<body>
		<div class="content">
			<!--头部开始-->
			<div class="header"><!--
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					智龙
				</h3>
				<div class="head-right">
					<a href="javascript:;"></a>
				</div>-->
			</div>
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<div class="short-info">
					<div class="info-box">
						<div class="head-img">
							<img src="images/zl-head-img.jpg" />
						</div>
						<div class="text-info">
							<h3>${electrism.nickName}</h3>
							<p>
								<ul class="clearfix">
									<li><img src="<%=request.getContextPath()%>/images/star.png"/></li>
									<li><img src="<%=request.getContextPath()%>/images/star.png"/></li>
									<li><img src="<%=request.getContextPath()%>/images/star.png"/></li>
								</ul>
								优秀
							</p>
							<p>均价：￥<span>${electrism.price}</span>&nbsp;&nbsp;接单<span>23</span>次</p>
						</div>
					</div>
				</div>
				<div class="grade">
					<ul class="clearfix">
						<li>
							<p>专业：<span>5.00</span></p>
						</li>
						<li>
							<p>沟通：<span class="acitve">5.00</span></p>
						</li>
						<li>
							<p>守时：<span>5.00</span></p>
						</li>
					</ul>
				</div>
				<!--
				<div class="opr-btn">
					<input type="button" value="收藏超级电工" />
					<input type="button" value="联系Ta" />
				</div>
				-->
				<div class="serve-place">
					<h3>服务圈商</h3>
					<p>${electrism.didstrict}</p>
				</div>
				<div class="detail-info" id="tabBox">
					<div class="tab-head">
						<ul class="clearfix">
							<li datatarget="serve-info"><a href="javascript:;">服务项目</a></li>
							<li datatarget="person-info"><a href="javascript:;">个人介绍</a></li>
						</ul>
					</div>
					<div class="tab-body">
						<div class="tab-cont serve-info" name="serve-info">
							<ul class="clearfix">
							
							<c:forEach items="${itemList}" var="item">
								
								<li>
								<c:if test="${item eq '上门服务'}">
									<a href="<%=request.getContextPath()%>/electrism/electrismAddOrder?electrismId=${electrism.id}&itemType=1&memberId=${memberId}">
									<img src="<%=request.getContextPath()%>/images/e01.jpg" />
									<p>上门服务</p>
								</c:if>
								<c:if test="${item eq '电器维护'}">
								<a href="<%=request.getContextPath()%>/electrism/electrismAddOrder?electrismId=${electrism.id}&itemType=2&memberId=${memberId}">
								<img src="<%=request.getContextPath()%>/images/e02.jpg" />
								<p>电器维护</p>
								</c:if>
								<c:if test="${item eq '线路维护'}">
									<a href="<%=request.getContextPath()%>/electrism/electrismAddOrder?electrismId=${electrism.id}&itemType=3&memberId=${memberId}">
									<img src="<%=request.getContextPath()%>/images/e03.jpg" />
									<p>线路维护</p>
								</c:if>
								<c:if test="${item eq '其他服务'}">
									<a href="<%=request.getContextPath()%>/electrism/electrismAddOrder?electrismId=${electrism.id}&itemType=3&memberId=${memberId}">
									<img src="<%=request.getContextPath()%>/images/e04.jpg" />
									<p>其他服务</p>
								</c:if>
										
										
									</a>
								</li>
							</c:forEach>
							</ul>
						</div>
						<div class="tab-cont person-info" name="person-info">
							<div class="person-content">
								<p>${electrism.content}</p>
								
							</div>
						</div>
					</div>
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
	<script>
		$(function(){
			$('#tabBox').tabPlugin();
		})
	</script>
</html>
