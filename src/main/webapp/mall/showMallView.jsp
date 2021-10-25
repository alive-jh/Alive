<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>${tempProduct[1]}</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="oldcss/css/commen.css"/>
		<link rel="stylesheet" href="oldcss/css/swiper.css" />
		<link rel="stylesheet" href="oldcss/css/goodsList.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commentList.css" />
		<link rel="shortcut icon" href="images/yz_fc.ico">
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.telNum.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
			<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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



	wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: ${timestamp},
	nonceStr: '${nonceStr}',
	signature: '${signature}',
    jsApiList: [  'checkJsApi',
            'chooseImage',
            'uploadImage',
            'downloadImage',
            'previewImage',
            'getNetworkType'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	
	
	
	


				$(function() {
						var $ul = $('ul');
						$ul.delegate('li', 'click', function() {
							$ul.find('li').each(function(i, dom) {
								$(this).removeClass('active');
							});
							$(this).addClass('active');
						});
					});


				function checkType(index)
				{
					var obj = ${jsonStr};
					
					if(obj.infoList[0].info != undefined)
					{
						var tempStr = obj.infoList[0].info.split(',');
						var tempPrice;
						var tempType;
						var maxCount;
						var str = tempStr[index].split('>');
						tempPrice = str[1];
						maxCount = str[2];
						tempType = str[3];
				
						document.forms['mallForm'].productId.value = obj.infoList[0].id;
						document.forms['mallForm'].price.value = tempPrice;
						document.forms['mallForm'].count.value = 1;
						document.forms['mallForm'].mallMaxCount.value = maxCount;
						document.forms['mallForm'].mallType.value = tempType;
						document.getElementById('dPrice').innerHTML = '<em>￥'+tempPrice+'</em>';
						document.forms['mallForm'].specifications.value = str[0];

					}
				}

				function saveOrder()
				{
					var $numText=$('#num-btn .num-text');
					if(new Number($numText.val()) > new Number(document.forms['mallForm'].mallMaxCount.value))
					{
						alert('该商品最大库存数量为'+document.forms['mallForm'].mallMaxCount.value+',请重新选择数量!');
						return;
					}
					document.forms['mallForm'].count.value =$numText.val();
					document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/toSaveOrder";
					document.forms['mallForm'].submit();
					
					
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
			
			
	wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: ${timestamp},
	nonceStr: '${nonceStr}',
	signature: '${signature}',
    jsApiList: [  'checkJsApi',
            'chooseImage',
            'uploadImage',
            'downloadImage',
            'previewImage',
            'getNetworkType'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	
	function showImg(img)
	{
		
		wx.previewImage({
    	current: img, // 当前显示图片的http链接
   		 urls: [img] // 需要预览的图片http链接列表
		})
	}
	
		    </script>
			
			<meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
		
		<script src="js/swiper.js"></script>
		<script src="js/peterbay.js"></script>
	</head>
	<body>
		<!--内容部分开始-->
		<div class="container ">
			
			
			<!--主要内容部分开始-->
			<div class="content no-margin">
				<div class="content-body">
					<!--图片轮播开始-->
					<div class="image-swiper">
						<!-- Swiper -->
		                <div class="swiper-container">
		                    <div class="swiper-wrapper">
		                    <c:if test="${tempProduct[2] != ''}">
		                        <div class="swiper-slide">
		                        	<a class="swp-page" href="#">
		                        		<img class="" src="<%=request.getContextPath() %>/wechatImages/mall/${tempProduct[2]}"/>
		                        	</a>
		                        </div>
		                      </c:if>  
		                      <c:if test="${tempProduct[3] != ''}">
		                        <div class="swiper-slide">
		                        	<a class="swp-page" href="#">
		                        		<img class="" src="<%=request.getContextPath() %>/wechatImages/mall/${tempProduct[3]}"/>
		                        	</a>
		                        </div>
		                        </c:if>  
		                        <c:if test="${tempProduct[4] != ''}">
		                        <div class="swiper-slide">
		                        	<a class="swp-page" href="#">
		                        		<img class="" src="<%=request.getContextPath() %>/wechatImages/mall/${tempProduct[4]}"/>
		                        	</a>
		                        </div>
		                        </c:if>  
		                       
		                        
		                        
		                    </div>
		                    <!-- Add Pagination -->
			                <div class="swiper-pagination"></div>
						</div>
					</div>
					<!--图片轮播结束-->
					
					<!--产品信息开始-->
					<div class="goods-sinfo">
						<div class="info-header">
							<h3>${tempProduct[1]}</h3>
							<div class="goods-price">
								<em>${tempProduct[6]}</em>
							</div>
						</div>
						<div class="info-text">
							<label>规格：<span>${tempStr}</span></label>
							<label>运费：<span>免运费</span></label>
							<label>人气：<span>${tempProduct[11]}</span></label>
							<label>全网销量：<span>${tempProduct[8]}</span></label>
						</div>
					</div>
					<!--产品信息结束-->
					
					<!--商家信息开始
					<div class="shop-info">
						<div class="shop-name">
							<a href="index.html" class="name-info">
								<span class="shop-icon"></span>
								<h3>PeterBay</h3>
								<div class="enter-shop">进入店铺<i class="right-icon"></i></div>
							</a>
						</div>
						<div class="shop-credit">
							<span><i class="check-icon"></i>担保交易</span>
						</div>
					</div>
					<!--商家信息结束-->
					
					<!--产品详细信息开始-->
					<div  class="goods-linfo">
						<div class="tab-head">
							<ul class="clearfix">
								<li class="active" datatarget="info-pic"><a href="javascript:;">商品详情</a></li>
								<li datatarget="info-rec"><a href="javascript:;">本店成交</a></li>
							</ul>
						</div>
						<div class="tab-body">
							<!--第一部分内容开始-->
							<div class="tab-cont active" name="info-pic">
							
								<div class="linfo-pic">
									${tempProduct[5]}
								</div>
								
								
							</div>
							<!--第一部分内容结束-->
							
							<!--第二部分内容开始-->
							<div class="tab-cont" name="info-rec">
								<table class="info-rec">
									<thead>
										<tr>
											<th style="width:25%;">买家</th>
											<th style="width:50%;">成交时间</th>
											<th style="width:25%;">数量</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${saleList}" var="sale">
										<tr>
											<td>${sale[0]}</td>
											<td>${sale[2]}</td>
											<td>${sale[1]}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
							<!--第二部分内容结束-->
						</div>
					</div>
					<!--产品详细信息结束-->
					
				</div>
				<!--购物车开始-->
				<div class="shoping-cart">
					<a href="<%=request.getContextPath() %>/member/shoppingCartManager?memberId=${memberId}"><span class="shoping-icon"><span class="shoping-icon"></span></span>
				</div>
				<!--购物车结束-->
			</div>
			<!--主要内容部分结束-->
			<!--选购信息框开始-->
			<form name="mallForm" method="post" action="<%=request.getContextPath()%>/member/toSaveOrder">
				<input type="hidden" name="productId">
				<input type="hidden" name="memberId" value="${memberId}">
				<input type="hidden" name="price">
				<input type="hidden" name="count">
				<input type="hidden" name="mallType">
				<input type="hidden" name="mallMaxCount">
				<input type="hidden" name="specifications">
				<input type="hidden" name="productName" value="${tempProduct[1]}">
				<input type="hidden" name="productImg" value="${tempProduct[2]}">
				<input type="hidden" name="orderType" value="0">
				
				

			<div class="shop-box" id="shop-box">
				<div class="shop-message">
					<div class="shop-header">
						<img class="goods-img" src="<%=request.getContextPath() %>/wechatImages/mall/${tempProduct[2]}" />
						<p class="goods-title">${tempProduct[1]}</p>
						<p class="goods-price" id="dPrice"><em>￥${tempProduct[6]}</em></p>
						<a href="javascript:;" class="close-btn" id="close-btn"></a>
					</div>
					<div class="shop-body">
						<h3>尺寸：</h3>
						<ul class="goods-size clearfix" id="shop-size">
						<% int i=0;%>
							<c:forEach items="${tempList}" var= "temp">
							<li onclick="checkType('<%=i%>')">${temp}<span></span></li>
							<%i++;%>
							</c:forEach>
							
						</ul>
						<div class="goods-num">
							<h3>数量</h3>
							<div class="num-btn" id="num-btn">
								<a class="num-minus disabled" href="javascript:;">-</a>
								<input class="num-text" type="text" value="1" />
								<a class="num-plus" href="javascript:;">+</a>
							</div>
						</div>
					</div>
					<div class="shop-footer">

						<input class="active" type="button" onclick = "saveOrder()"value="立即购买" />
						<input type="button" onclick = "saveShoppingCart()" value="加入购物车" />
					
					</div>
				</div>
				<div class="mask"></div>
			</div>
			<!--选购信息框结束-->
			
			<!--选购按钮开始-->
			<div class="order-box clearfix">
				<input class="active buy-response" type="button" value="立即购买" />

				<input class="buy-response" type="button" value="加入购物车" />
			</div>

			</form>
			<!--选购按钮结束-->
		</div>
		<!--内容部分结束-->
		<div class="content">
			
			<!--主要部分开始-->
			<div class="content-body">
				<div class="comment-head">
					商品评价
					<c:if test="${totalCount>0}">
					（<span>${totalCount}</span>）
					</c:if>
				</div>
				<div class="comment-list">
					<ul>
					<c:forEach items="${commentList}" var = "tempComment">
						<li>
							<h3 class="num-star clearfix">

								<span class="tel-num left">${tempComment.memberName}</span>
								<span class="stars right" data-star="${tempComment.star}">
								</span>
							</h3>
							<p class="comment-text">
								${tempComment.content}
							</p>
							<c:forEach items="${tempComment.imgList}" var = "tempImg">
								<img witch="50" height="50" src="${tempImg}" onclick="showImg('${tempImg}')"/>&nbsp;
							</c:forEach>		
							
							<p class="comment-time">${tempComment.createDate} </p>
						</li>
					
							
										
							
						
					
						
					</c:forEach>	
					</ul>
					<c:if test="${totalCount>2}">
					<a class="load-more" href="<%=request.getContextPath()%>/member/commentManager?mallProductId=${tempProduct[0]}&memberId=${memberId}">查看更多评论</a>
					</c:if>
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
		<!--底部开始-->
		
		<!--底部结束-->
	</body>
	<!--页内脚本开始-->
	<script>
		//图片轮播脚本
		var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            spaceBetween: 30,
            autoplay: 2500
        });
        
	</script>
	<script>
		$(function(){
			$('.tel-num').telNum();
			//根据data-star加载星星数量
			$('.stars').each(function(i,item){
				var _starNum=$(this).attr('data-star');
				var _starStr='';
				for(var i=0;i<5;i++){
					if(i<_starNum){
						_starStr+='<img src="<%=request.getContextPath()%>/images/star2.png"/>';
					}else{
						_starStr+='<img src="<%=request.getContextPath()%>/images/star2-2.png"/>';
					}
				}
				$(this).html(_starStr);
			})
		})
	</script>
	<!--页内脚本结束-->
</html>
