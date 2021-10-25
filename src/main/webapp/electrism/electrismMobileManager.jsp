<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>预约电工</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/electricianList-2.css" />
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=EGkQIeFCh2OwlaMZdVy9pD2k"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>

		
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
		    </script>
		    <meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
		<script src="js/tabPlugin.js"></script>
	</head>
	<body>
	<div id="allmap"></div>
		<div class="content no-margin" id="tabBox">
			<!--头部开始-->
			
			<!--头部结束-->
			
			<div class="addr-text">
				<span class="addr-area">深圳</span><span>
				<a href="<%=request.getContextPath()%>/electrism/searchAddress?memberId=${memberId}">
				<c:if test="${address ==null}">请选择服务地址</c:if>
				<c:if test="${address !=null}">${address}</c:if>
				
				
				</a></span>
			</div>
			<div class="order-by tab-head">
				<ul>
					<li datatarget="ranking"><span>单量</span></li>
					<li datatarget="nearby"><span>好评</span></li>
				</ul>
			</div>
			<!--主要部分开始-->
			<div class="content-body tab-body">
				<div class="tab-cont" name="ranking">
					<ul class="elecrician-lists">

			<c:forEach items="${resultPage.items}" var="electrism">
						<li class="list-box">
							<a href="<%=request.getContextPath()%>/electrism/electrismMobileManagerView?electrismId=${electrism.id}&memberId=${memberId}">
								<div class="head-img">
									<img src="<%=request.getContextPath()%>/wechatImages/electrism/${electrism.headImg}" />
								</div>
								<div class="name-info">
									<h3><b>${electrism.nickName}</b><span>男</span></h3>
									<p class="collect-icon"><span>307</span>收藏</p>
								</div>
								<div class="price">
									<p class="orders-icon"><span>1084</span>单</p>
									<p class="score-icon"><span>5.0</span>分</p>
									<p class="addr-icon"><span id="info${electrism.id}">6.80</span>公里</p>
								</div>
							</a>
						</li>
			</c:forEach>	
						
					</ul>
				</div>

				<div class="tab-cont" name=nearby>
					<ul class="elecrician-lists">
						

						
					</ul>
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
			
			
			//筛选条件切换按钮效果
			$('.rank-count ul li').click(function(){
				if($(this).index()!=2){
					$('.filter-detail').hide();
				}
				$(this).addClass('acitve').siblings().removeClass('acitve');
			})
		})


	$("#allmap").hide();
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom("深圳", 13);  
	var pointA = new BMap.Point(${lngandlat});  
	var juli;

	var data = ${jsonStr};
	if(data !='')
	{
		for(var i=0;i<data.infoList.length;i++){

			
			var pointB = new BMap.Point(data.infoList[i].lng,data.infoList[i].lat);  
			juli = map.getDistance(pointA,pointB).toFixed(2);
			document.getElementById('info'+data.infoList[i].id).innerHTML = 
			toDecimal(juli/1000)+"km";
			
		}
	}
	

	 function toDecimal(x) {    
            var f = parseFloat(x);    
            if (isNaN(f)) {    
                return;    
            }    
            f = Math.round(x*100)/100;    
            return f;    
        }  

	
</script>

</html>
