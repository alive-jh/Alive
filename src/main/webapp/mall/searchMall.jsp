<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>凡豆科技</title>
		<link rel="stylesheet" href="css/base.css" />
		<link rel="stylesheet" href="css/commen.css"/>
		<link rel="stylesheet" href="css/cakeList.css"/>
		<link rel="shortcut icon" href="images/yz_fc.ico">
		<script src="js/zepto.js"></script>
		<script src="js/peterbay.js"></script> 
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
		 
			
				function seaechMall(keyword)
				{
					document.forms['searchForm'].name.value = keyword;
					document.forms['searchForm'].submit();
				}

				function deleteKeyword()
				{
						$('#history-list').html('');
						$.ajax({
							type: "POST",
							data:"",
							dataType: "json",
							url: "<%=request.getContextPath()%>/mall/deleteMemberKeyword?memberId=${memberId}",
							context: document.body, 
							beforeSend:function(){
							},
							complete:function(){
							},
							success: function(data){
									
								
							}

						});
				}
				function saveOrder()
				{
					var $numText=$('#num-btn .num-text');
					if(new Number($numText.val()) > new Number(document.forms['mallForm'].mallMaxCount.value))
					{
						alert('该商品最大库存数量为'+document.forms['mallForm'].mallMaxCount.value+',请重新选择数量!');
						return;
					}
					document.forms['mallForm'].count.value = $numText.val();
					document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/toSaveOrder";
					document.forms['mallForm'].submit();
					//alert('数量:'+$numText.val()+'\n单价:'+document.forms['mallForm'].price.value+'\n总金额:'+new Number($numText.val()) *new Number(document.forms['mallForm'].price.value));
				}




		function saveShoppingCart()
			{
					
				var $numText=$('#num-btn .num-text');
				$.ajax({
					type: 'GET',
					url: '<%=request.getContextPath()%>/member/saveShoppingCart?price='+document.forms['mallForm'].price.value+'&count='+$numText.val()+'&specifications='+document.forms['mallForm'].specifications.value+"&memberId="+document.forms['mallForm'].memberId.value+"&productId="+document.forms['mallForm'].productId.value+"&productImg="+document.forms['mallForm'].productImg.value+"&productName="+document.forms['mallForm'].productName.value, 
					success:function(d){
						alert(" 购物车保存成功!");
					},
					error:function(d){
						alert(" 购物车保存失败");
					}
					});
			}



			function toView(id)
			{
				document.forms['searchForm'].action = "<%=request.getContextPath() %>/mall/mallMobileManager?mallProductId="+id+"&memberId=${memberId}";
				document.forms['searchForm'].submit();
			}
			

			function updatePrice(price,maxCount,mallType,index,num)
				{
					
					document.getElementById('dPrice').innerHTML = '<em>￥'+price+'</em>';
					document.forms['mallForm'].price.value = price;
					document.forms['mallForm'].mallMaxCount.value = maxCount;
					document.forms['mallForm'].mallType.value = mallType;
					var obj = ${jsonStr};
					var tempStr = obj.infoList[index].info.split(',');
					var str = tempStr[num].split('>');
					document.forms['mallForm'].specifications.value = str[0];
					
				}


			function checkProduct(index)
				{
					
					
					var obj = ${jsonStr};
					document.getElementById('dName').innerHTML = obj.infoList[index].name;
					document.getElementById('dPrice').innerHTML = '<em>￥'+obj.infoList[index].info.split(',')[0].split('>')[1]+'-'+obj.infoList[index].info.split(',')[obj.infoList[index].info.split(',').length-1].split('>')[1]+'</em>';
					document.getElementById('dLogo').src = obj.infoList[index].logo1;
					$('#shop-size li').remove();
					if(obj.infoList[index].info != undefined)
					{
						var tempStr = obj.infoList[index].info.split(',');
						var tempPrice;
						var tempType;
						var maxCount;
						
						for(var j=0;j<tempStr.length;j++)
						{
							var str = tempStr[j].split('>');
							
							
							if(j==0)
							{
								$("#shop-size").append('<li class="active" onclick="updatePrice('+str[1]+','+str[2]+','+str[3]+','+index+','+j+')">'+str[0]+'<span></span></li>');
								tempPrice = str[1];
								maxCount = str[2];
								tempType = str[3];
								document.forms['mallForm'].specifications.value = str[0];
								

							}
							else
							{
								$("#shop-size").append('<li onclick="updatePrice('+str[1]+','+str[2]+','+str[3]+','+index+','+j+')">'+str[0]+'<span></span></li>');
							}
						}
				
						document.forms['mallForm'].productId.value = obj.infoList[index].id;
						document.forms['mallForm'].price.value = tempPrice;
						document.forms['mallForm'].count.value = 1;
						document.forms['mallForm'].mallMaxCount.value = maxCount;
						document.forms['mallForm'].mallType.value = tempType;
						
						document.forms['mallForm'].productName.value = obj.infoList[index].name;
						document.forms['mallForm'].productImg.value = obj.infoList[index].logo1;

					}
					
				}


			</script>
			<meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<!--内容部分开始-->
		<div class="container">
			<!--头部开始-->
			<div class="header">
				<div class="head-info">
					<a class="pege-info" href="#">
						<img src="<%=request.getContextPath()%>/images/mallLogo.jpg" /><i class="nickname">凡豆科技</i>
					</a>
					<div class="links">
						<!--<span class="search-icon" id="search-btn" onclick="$('#search-content').css('display','block');"></span>
						<a href="#">我的记录</a>
						<a href="#">收藏</a>-->
					</div>
				</div>
			</div>
			<!--头部结束-->
			
			<!--搜索部分-->
			<div class="search-block">
				<input type="search"  value="${name}" placeholder="请输入关键字" onclick="$('#search-content').css('display','block');"/>
			
			</div>
			<!--搜索部分end-->
			
			<!--主要内容部分开始-->
			<div class="content">
				<div class="content-body">
					<!--图片轮播结束-->
					
					<!--产品图片开始-->
					<ul class="cake-list">

					<% int i=0;%>
					<c:forEach items="${infoList}" var="info">

						<li>
							<a href="javascript:void(0)">
								<div class="img-box">
										<img onclick="toView('${info[0]}')" src="${info[2]}" />
								</div>
								<div class="text-info">
									<h3 onclick="toView('${info[0]}')">${info[1]}</h3>
									<p class="price">￥<span>${info[3]}</span></p>
									<i class="shoping-cart-btn buy-response" onclick="checkProduct('<%=i%>')"></i>
								</div>
							</a>
						</li>
						<%i++;%>
						</c:forEach>
					</ul>
					<!--产品图片结束-->
				</div>
			</div>
			<!--主要内容部分结束-->
			
				<!--搜索部分开始-->
			<div class="search" id="search-content" style="display: none;">
				<form name="searchForm" class="search-form" action="seaechMall" method="post">
				<input type="hidden" name="memberId" value="${memberId}">
					<input class="searc-input" ntype="search" name="name" value="${name}" placeholder="搜索本店所有产品" />
					<span class="search-icon"></span>
					<span class="close-icon"></span>
					<a class="cansel-btn" href="javascript:;"  onclick="$('#search-content').css('display','none');">取消</a>
				</form>
				<div class="history-wrap center">
					<ul class="history-list" id="history-list">
						<c:forEach items="${keywordList}" var="testKeyword">
						<li onclick="seaechMall('${testKeyword.keyword}')">${testKeyword.keyword}</li>
					</c:forEach>
					</ul>
					<a class="tag-clear" href="javascript:;deleteKeyword()">清除历史搜索</a>
				</div>
			</div>
			<!--搜索部分结束-->
			
		</div>
		<!--内容部分结束-->
		
		<!--底部开始-->
		<div class="footer">
			<div class="copyright">
				<!--底部导航开始-->
			<form class="search-form" name="mallForm" method="post">
			<input type="hidden" name="productId">
			<input type="hidden" name="price">
			<input type="hidden" name="count">
			<input type="hidden" name="mallType">
			<input type="hidden" name="mallMaxCount">
			<input type="hidden" name="specifications">
			<input type="hidden" name="productName" >
			<input type="hidden" name="productImg" >
			<input type="hidden" name="memberId" value="${memberId}">
			<input type="hidden" name="orderType" value="0">
			</form>
				<!--底部导航结束-->
				<!--版权信息开始-->
				
				<!--版权信息结束-->
			</div>
			<!--选购信息框开始-->
			<div class="shop-box" id="shop-box">
				<div class="shop-message">
					<div class="shop-header">
						<img class="goods-img" id="dLogo" src="images/Fr_96XMKLUQxu10mMplJfftAmd5t.jpg" />
						<p class="goods-title" id="dName">抹茶慕斯</p>
						<p class="goods-price" id="dPrice"><em>￥268.00 - 368.00</em></p>
						<a href="javascript:;" class="close-btn" id="close-btn"></a>
					</div>
					<div class="shop-body">
						<h3>尺寸：</h3>
						<ul class="goods-size clearfix" id="shop-size">
							<li class="active">1磅<span></span></li>
							<li>2磅<span></span></li>
						</ul>
						<div class="goods-num">
							<h3>数量</h3>
							<div class="num-btn"  id="num-btn">
								<a class="num-minus disabled" href="javascript:;">-</a>
								<input class="num-text" type="text" value="1" />
								<a class="num-plus" href="javascript:;">+</a>
							</div>
						</div>
					</div>
					<div class="shop-footer">
						<input class="active" type="button"  onclick = "saveOrder()" value="立即购买" />
						<input type="button" onclick = "saveShoppingCart()"  value="加入购物车" />
					</div>
				</div>
				<div class="mask"></div>
			</div>
			<!--选购信息框结束-->
		</div>
		<!--底部结束-->
	</body>
	<script>
		$(function(){
			$('.shoping-cart-btn').click(function(){
				//alert($(this).parent().parent().parent().index());
			})
		})
	</script>
</html>
