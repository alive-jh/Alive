<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>电工</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillComment.css" />
		<script src="<%=request.getContextPath()%>js/zepto.js"></script>
		
	</head>
	<body>
		<div class="content">
			<!--头部开始-->
			<div class="header">
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					订单评论
				</h3>
				<!--<div class="head-right">
					<a href="javascript:;"></a>
				</div>-->
			</div>
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body no-padding">
				<div class="goods-com">
					<div class="goods-pic clearfix">
						<img src="images/goodsImg/01.jpg" />
						<textarea placeholder="请写下对宝贝的感受吧，对他人帮助很大哦"></textarea>
					</div>
					<div class="goods-pic-upload">
						<ul class="clearfix">
							<li>
								<img src="images/goodsImg/02.jpg" />
								<i class="delete">×</i>
							</li>
							<li>
								<img src="images/goodsImg/02.jpg" />
								<i class="delete">×</i>
							</li>
							<li>
								<img src="images/goodsImg/02.jpg" />
								<i class="delete">×</i>
							</li>
							<li class="btn-bg">
								<input type="file" />
							</li>
						</ul>
					</div>
					<div class="attr-star clearfix">
						<label>描述相符</label>
						<ul data-star='3' class="clearfix">
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
						</ul>
					</div>
				</div>
				<div class="shop-com">
					<h3>店铺评分</h3>
					<div class="attr-star clearfix">
						<label>物流服务</label>
						<ul data-star='3' class="clearfix">
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<div class="attr-star clearfix">
						<label>服务态度</label>
						<ul data-star='0' class="clearfix">
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
						</ul>
					</div>
				</div>
			</div>
			<!--主要部分结束-->
			
			
			<!--提交按钮开始-->
			<div class="control-bar">
				<input type="button" value="发布评论" />
			</div>
			<!--提交按钮结束-->
			
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
			var $deletBtn=$('.delete');
			var $starUl=$('.attr-star ul');
			var $star=$('.attr-star ul li');
			
			lightStar($starUl);
			
			$deletBtn.on('click',function(){
				$(this).parent().remove();
			})
			
			$star.on('click',function(){
				var _index=$(this).index()+1;
				$(this).parent().attr('data-star',_index);
				lightStar($(this).parent());
			})
			
			//点亮星星
			function lightStar(obj){
				obj.each(function(){
					var _index=$(this).attr('data-star');
					for(var i=0;i<5;i++){
						if(i<_index){
							$(this).find('li').eq(i).addClass('acitve');
						}else{
							$(this).find('li').eq(i).removeClass('acitve');
						}
						
					}
				})
				
			}
		})
	</script>
</html>
