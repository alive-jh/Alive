<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<!DOCTYPE html>
<html>
		<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>退货申请</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/swiper.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/dataScorll.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillRefund.css" />
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/swiper.js"></script>
		<script src="<%=request.getContextPath()%>/js/dataScorll.js"></script>
		<script>
			function save()
			{
			
			var temp = "${mallOrder.totalPrice}";
			if(document.forms['orderForm'].payment.value > temp)
			{
				alert("退款金额不能大于:"+temp+"元");
				return;
			}
				document.forms['orderForm'].action = "<%=request.getContextPath()%>/member/saveFillRefund";
				document.forms['orderForm'].submit();

			}
		</script>
	</head>
	<body>
		<div class="content">
			<!--头部开始-->
			<form name="orderForm" method ="post" action = "<%=request.getContextPath()%>/member/saveFillRefund">
				<input type="hidden" name="orderId" value="${mallOrder.id}">
				<input type="hidden" name="memberId" value="${mallOrder.memberId}">
				<input type="hidden" name="orderNumber" value="${mallOrder.orderNumber}">
			
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<div class="info-block">
					<h3 class="sub-title">快递公司</span></h3>
					<div class="info-text">
						<input type="text" name="express" placeholder="请输入快递公司" />
					</div>
				</div>
				<div class="info-block">
					<h3 class="sub-title">快递单号</h3>
					<div class="info-text">
						<input type="text" name="expressNumber" placeholder="请输入快递单号" />
					</div>
				</div>
				
				<div class="reason-info info-block">
					<h3 class="sub-title">退款原因</h3>
					<div class="info-text">
						<input type="text" name="serviceType" placeholder="请选择退款类型" readonly/>
						<a class="arrow tog-arrow active" href="javascript:;"></a>
					</div>
				</div>
				
				<div class="info-block">
					<h3 class="sub-title">退款金额<span class="red">&nbsp;最多(￥<fmt:formatNumber type="number" value="${mallOrder.totalPrice}" pattern="0.00" maxFractionDigits="2"/> ) </span></h3>
					<div class="info-text">
						<input type="text" name="payment" placeholder="请输入退款金额" />
					</div>
				</div>
				
				<div class="info-block">
					<h3 class="sub-title">退款说明<span class="gray small">（可不填）</span></h3>
					<div class="info-text">
						<input type="text" name="remarks" placeholder="请输入退款说明" />
					</div>
				</div>
			</div>
			
			<!--主要部分结束-->
			<div class="btn-group fixed-block">
				<input type="button" value="提交申请" onclick="save()" />
			</div>
			</form>
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
			$('.reason-info').dateRange({
				callback:fn1,//回调函数
	    		mask:true,//是否添加蒙版
	    		loop:false,//是否循环显示
	    		oldData:'收到商品破损',//设置初始值如'222'
	    		listData:['收到商品破损','商品错发/漏发','收到商品与描述不符合']//设置列表显示数据
    		
			});
			
			function fn1(obj){
			
				document.forms['orderForm'].serviceType.value = obj.find('input[type="text"]').attr('value'); 
			}
		})
	</script>
</html>
