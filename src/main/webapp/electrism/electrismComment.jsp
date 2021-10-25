<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>发表评论</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commentElectrician.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

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


		function saveComment()
		{
			document.forms['electrismForm'].action = "./saveComment?memberId=${memberId}&orderId=${orderId}";
			document.forms['electrismForm'].submit();

		}
		    </script>
		    
		    <meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content">
			<!--头部开始-->
		
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body no-padding">
			<form name="electrismForm" method="post" >
			<input type="hidden" name = "star" >
			<input type="hidden" name = "productId" value= "${electrism.id}">
				<div class="electrician-info">
					<div class="info-pic">
						<img src="<%=request.getContextPath()%>/wechatImages/electrism/${electrism.headImg}" />
					</div>
					<div class="info-text">
						<h3>${electrism.nickName}</h3>
						<p>电话：<span>${electrism.mobile}</span></p>
						<p>工号：<span>${electrism.id}</span></p>
						<p class="gray">ta口碑超越了100%的电工</p>
					</div>
				</div>
				<div class="info-com">
					
					<div class="attr-star">
						<p>本次服务评价</p>
						<ul data-star='5'>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					
				</div>
				<div class="info-input clearfix">
					<p>有话要说</p>
					<textarea name="content" placeholder="(选填)请写下您的真心话，120字内"></textarea>
				</div>
				
			</div>
			<!--主要部分结束-->
			
			
			<!--提交按钮开始-->
			<div class="control-bar">
				<input type="button" value="发布评论" onclick="saveComment()"/>
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
			var $starUl=$('.attr-star ul');
			var $star=$('.attr-star ul li');
			
			lightStar($starUl);
			
			//获取星星数量
			function starNum(){
				var starArr=[];
				$starUl.each(function(i,item){
					starArr.push($(this).attr('data-star'));
				});
				//alert('星级评价的数量:'+starArr);
				document.forms['electrismForm'].star.value = starArr;
			}
			starNum();
			
			$star.on('click',function(){
				var _index=$(this).index()+1;
				$(this).parent().attr('data-star',_index);
				lightStar($(this).parent());
				starNum();
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
